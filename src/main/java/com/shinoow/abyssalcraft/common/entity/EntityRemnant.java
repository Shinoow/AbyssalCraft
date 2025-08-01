/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import java.util.*;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAIWorship;
import com.shinoow.abyssalcraft.common.items.ItemCoin;
import com.shinoow.abyssalcraft.common.items.ItemNecronomicon;
import com.shinoow.abyssalcraft.common.items.ItemStaffOfRending;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityRemnant extends EntityMob implements IMerchant, IOmotholEntity, IShearable {

	private static final DataParameter<Integer> PROFESSION = EntityDataManager.<Integer>createKey(EntityRemnant.class, DataSerializers.VARINT);
	private EntityPlayer tradingPlayer;
	private MerchantRecipeList tradingList;
	private Class<? extends EntityLivingBase> target;
	private int timeUntilReset;
	private boolean needsInitilization;
	private int wealth;
	private int timer;
	private boolean sheared;
	private float field_82191_bN;
	public static final Map<Item, Tuple<Integer, Integer>> itemSellingList = new HashMap<>();
	public static final Map<Item, Tuple<Integer, Integer>> coinSellingList = new HashMap<>();

	public EntityRemnant(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(3, new EntityAIAttackMelee(this, 0.35D, false));
		tasks.addTask(4, new EntityAIOpenDoor(this, true));
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(6, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityRemnant.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGatekeeperMinion.class, 8.0F));
		tasks.addTask(8, new EntityAIWorship(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 20, true, false, entity -> entity.getClass() == target));
		setSize(0.6F, 1.95F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 100.0D : 50.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 20.0D: 10.0D);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		swingArm(EnumHand.MAIN_HAND);
		swingArm(EnumHand.OFF_HAND);
		boolean flag = super.attackEntityAsMob(par1Entity);

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 3 * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	protected void updateAITasks() {

		if (!isTrading() && timeUntilReset > 0)
		{
			--timeUntilReset;

			if (timeUntilReset <= 0)
			{
				if (needsInitilization)
				{
					if (tradingList.size() > 1)
					{
						Iterator iterator = tradingList.iterator();

						while (iterator.hasNext())
						{
							MerchantRecipe merchantrecipe = (MerchantRecipe)iterator.next();

							if (merchantrecipe.isRecipeDisabled() && !isSpiritTabletShard(merchantrecipe.getItemToSell()))
								merchantrecipe.increaseMaxTradeUses(rand.nextInt(6) + rand.nextInt(6) + 2);
						}
					}

					addDefaultEquipmentAndRecipies(1);
					needsInitilization = false;
				}

				addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 200, 0));
			}
		}

		super.updateAITasks();
	}

	@Override
	public boolean processInteract(EntityPlayer par1EntityPlayer, EnumHand hand)
	{
		if(isEntityAlive() && !par1EntityPlayer.isSneaking() && !isAngry())
			if(EntityUtil.hasNecronomicon(par1EntityPlayer)){
				if(!isTrading()){
					if(!world.isRemote){
						setCustomer(par1EntityPlayer);
						par1EntityPlayer.displayVillagerTradeGui(this);
						return true;
					}
				} else if(!tradingPlayer.getUniqueID().equals(par1EntityPlayer.getUniqueID())) par1EntityPlayer.sendMessage(new TextComponentString(getName()+": "+TranslationUtil.toLocal("message.remnant.busy")));
			} else {
				insult(par1EntityPlayer);
				return true;
			}

		return super.processInteract(par1EntityPlayer, hand);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(PROFESSION, 0);
	}

	/**
	 * Used by Remnants to insult oblivious Players
	 * @param player The target to insult
	 */
	private void insult(EntityPlayer player){
		int insultNum = world.rand.nextInt(3);
		String insult = getName()+": "+String.format(getInsult(insultNum), player.getName());
		String translated = getName()+": "+String.format(TranslationUtil.toLocal("message.remnant.insult."+insultNum), player.getName());

		if(world.isRemote){
			List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, player.getEntityBoundingBox().grow(16D, 16D, 16D));
			if(players != null){
				Iterator<EntityPlayer> i = players.iterator();
				while(i.hasNext()){
					EntityPlayer player1 = i.next();
					if(EntityUtil.hasNecronomicon(player1))
						player1.sendMessage(new TextComponentString(translated));
					else player1.sendMessage(new TextComponentString(insult));
				}
			}
		}
	}

	/**
	 * Insult generator
	 * @param num Which insult to pick, number should be generated through Random.nextInt(3)
	 * @return A insult that humans don't understand
	 */
	private String getInsult(int num){
		switch(num){
		case 0:
			return "%s, Aklo g'ai ftrr nfto lagln f'tifh mtli ot aishgft 'ai.";
		case 1:
			return "%s ukhoyah g'ka-dish-tu.";
		case 2:
			return "%s go-tha ukhoyah Necronomicon g'mnahn'.";
			//		case 3:
			//			return "test %s";
			//		case 4:
			//			return "test %s";
		default:
			return getInsult(0);
		}
	}

	/**
	 * Used by Minions of The Gatekeeper to set the attack target and enrage the Remnant
	 * @param call If the Remnant should call for backup
	 * @param enemy The target
	 */
	public void enrage(boolean call, EntityLivingBase enemy){
		setAttackTarget(enemy);
		enrage(call);
	}

	/**
	 * Calling this method will make the Remnant hostile.
	 * Ever wanted to see a angry Remnant? No you don't.
	 * @param call If the Remnant should call for backup
	 */
	public void enrage(boolean call){
		if(call){
			for(EntityRemnant rem : world.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().grow(16D, 16D, 16D)))
				rem.enrage(false, getAttackTarget());

			playSound(ACSounds.remnant_scream, 3F, 1F);
		}

		setAngry();
		setAttackAI();
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par1DamageSource.getTrueSource() instanceof EntityLivingBase){
			if(getAttackTarget() != par1DamageSource.getTrueSource()){
				setAttackTarget((EntityLivingBase) par1DamageSource.getTrueSource());
				enrage(true, getAttackTarget());
			}
			if(!isAngry()) enrage(true);
			else enrage(rand.nextInt(10) == 0);
		}
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public boolean isPotionApplicable(PotionEffect potioneffectIn) {
		if(potioneffectIn.getPotion() == MobEffects.POISON)
			return false;
		return super.isPotionApplicable(potioneffectIn);
	}

	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		if(isAngry()){
			setAttackAI();
			timer--;
			if(timer <= 0)
				timer = 0;
		} else target = null;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("Profession", getProfession());
		par1NBTTagCompound.setInteger("Money", wealth);
		par1NBTTagCompound.setInteger("AngerTimer", timer);
		par1NBTTagCompound.setBoolean("Sheared", sheared);

		if (tradingList != null)
			par1NBTTagCompound.setTag("Offers", tradingList.getRecipiesAsTags());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setProfession(par1NBTTagCompound.getInteger("Profession"));
		wealth = par1NBTTagCompound.getInteger("Money");
		timer = par1NBTTagCompound.getInteger("AngerTimer");
		sheared = par1NBTTagCompound.getBoolean("Sheared");

		if (par1NBTTagCompound.hasKey("Offers", 10))
		{
			NBTTagCompound nbttagcompound1 = par1NBTTagCompound.getCompoundTag("Offers");
			tradingList = new MerchantRecipeList(nbttagcompound1);
			tradingList.removeIf(r -> r.getItemToSell().isEmpty());
		}
	}

	public boolean isAngry(){
		return timer > 0;
	}

	public void setAngry(){
		timer = 600;
	}

	private void setAttackAI(){
		if(getAttackTarget() != null)
			target = getAttackTarget().getClass();
	}

	@Override
	protected ResourceLocation getLootTable(){
		switch(getProfession()) {
		case 0:
			return ACLoot.ENTITY_REMNANT;
		case 1:
			return ACLoot.ENTITY_REMNANT_LIBRARIAN;
		case 2:
			return ACLoot.ENTITY_REMNANT_PRIEST;
		case 3:
			return ACLoot.ENTITY_REMNANT_BLACKSMITH;
		case 4:
			return ACLoot.ENTITY_REMNANT_BUTCHER;
		case 5:
			return ACLoot.ENTITY_REMNANT_BANKER;
		case 6:
			return ACLoot.ENTITY_REMNANT_MASTER_BLACKSMITH;
		default:
			return ACLoot.ENTITY_REMNANT;
		}
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	protected SoundEvent getAmbientSound(){
		return getProfession() == 2 && rand.nextBoolean() ? ACSounds.remnant_priest_chant : null;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ACSounds.shadow_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
	}

	public void setProfession(int par1)
	{
		dataManager.set(PROFESSION, par1);
	}

	public int getProfession()
	{
		return dataManager.get(PROFESSION);
	}

	@Override
	public void setCustomer(EntityPlayer var1) {

		tradingPlayer = var1;
	}

	@Override
	public EntityPlayer getCustomer() {

		return tradingPlayer;
	}

	public boolean isTrading(){
		return tradingPlayer != null;
	}

	@Override
	public MerchantRecipeList getRecipes(EntityPlayer var1) {

		if(tradingList == null)
			addDefaultEquipmentAndRecipies(1);

		return tradingList;
	}

	private float adjustProbability(float par1)
	{
		float f1 = par1 + field_82191_bN;
		return f1 > 0.9F ? 0.9F - (f1 - 0.9F) : f1;
	}

	private void addDefaultEquipmentAndRecipies(int par1)
	{
		if (tradingList != null)
			field_82191_bN = MathHelper.sqrt(tradingList.size()) * 0.2F;
		else
			field_82191_bN = 0.0F;

		MerchantRecipeList list;
		list = new MerchantRecipeList();
		int k;
		label50:

			switch (getProfession())
			{
			case 0:
				addItemTrade(list, Items.WHEAT, rand, adjustProbability(0.9F));
				addItemTrade(list, Item.getItemFromBlock(Blocks.WOOL), rand, adjustProbability(0.5F));
				addItemTrade(list, Items.CHICKEN, rand, adjustProbability(0.5F));
				addItemTrade(list, Items.COOKED_FISH, rand, adjustProbability(0.4F));
				addCoinTrade(list, Items.BREAD, rand, adjustProbability(0.9F));
				addCoinTrade(list, Items.MELON, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.APPLE, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.COOKIE, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.SHEARS, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.FLINT_AND_STEEL, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.ARROW, rand, adjustProbability(0.5F));

				if (rand.nextFloat() < adjustProbability(0.5F))
					list.add(new MerchantRecipe(new ItemStack(Blocks.GRAVEL, 10), new ItemStack(ACItems.token_of_jzahar), new ItemStack(Items.FLINT, 4 + rand.nextInt(2), 0)));

				break;
			case 1:
				addItemTrade(list, Items.PAPER, rand, adjustProbability(0.8F));
				addItemTrade(list, Items.BOOK, rand, adjustProbability(0.8F));
				addItemTrade(list, Items.WRITTEN_BOOK, rand, adjustProbability(0.3F));
				addCoinTrade(list, Item.getItemFromBlock(Blocks.BOOKSHELF), rand, adjustProbability(0.8F));
				addCoinTrade(list, Item.getItemFromBlock(Blocks.GLASS), rand, adjustProbability(0.2F));
				addCoinTrade(list, Items.COMPASS, rand, adjustProbability(0.2F));
				addCoinTrade(list, Items.CLOCK, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.necronomicon, rand, adjustProbability(0.3F));
				addCoinTrade(list, ACItems.abyssal_wasteland_necronomicon, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.dreadlands_necronomicon, rand, adjustProbability(0.1F));
				if(rand.nextFloat() < adjustProbability(0.1F)) {
					k = 3 * (rand.nextInt(10) + 1) + 1;
					list.add(new MerchantRecipe(new ItemStack(Items.BOOK), new ItemStack(ACItems.token_of_jzahar, k), new ItemStack(ACItems.basic_scroll)));
				}
				if(rand.nextFloat() < adjustProbability(0.1F)) {
					k = 3 * (rand.nextInt(10) + 1) + 1;
					list.add(new MerchantRecipe(new ItemStack(ACItems.basic_scroll), new ItemStack(ACItems.token_of_jzahar, k), new ItemStack(ACItems.lesser_scroll)));
				}
				if(rand.nextFloat() < adjustProbability(0.1F)) {
					k = 3 * (rand.nextInt(10) + 1) + 1;
					list.add(new MerchantRecipe(new ItemStack(ACItems.lesser_scroll), new ItemStack(ACItems.token_of_jzahar, k), new ItemStack(ACItems.moderate_scroll)));
				}

				if (rand.nextFloat() < adjustProbability(0.07F))
				{
					Enchantment enchantment = Enchantment.REGISTRY.getRandomObject(rand);
					int i1 = MathHelper.getInt(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
					ItemStack itemstack = ItemEnchantedBook.getEnchantedItemStack(new EnchantmentData(enchantment, i1));
					k = 2 + rand.nextInt(5 + i1 * 10) + 3 * i1;
					list.add(new MerchantRecipe(new ItemStack(Items.BOOK), new ItemStack(ACItems.token_of_jzahar, k), itemstack));
				}

				break;
			case 2:
				addCoinTrade(list, Items.ENDER_EYE, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.EXPERIENCE_BOTTLE, rand, adjustProbability(0.2F));
				addCoinTrade(list, Items.REDSTONE, rand, adjustProbability(0.4F));
				addCoinTrade(list, Item.getItemFromBlock(Blocks.GLOWSTONE), rand, adjustProbability(0.3F));
				addItemTrade(list, Items.ROTTEN_FLESH, rand, adjustProbability(0.7F));
				addItemTrade(list, ACItems.coralium_plagued_flesh, rand, adjustProbability(0.7F));
				addItemTrade(list, ACItems.dread_fragment, rand, adjustProbability(0.7F));
				addItemTrade(list, ACItems.omothol_ghoul_flesh, rand, adjustProbability(0.7F));
				addItemTrade(list, ACItems.rotten_anti_flesh, rand, adjustProbability(0.3F));
				addCoinTrade(list, ACItems.ritual_charm, rand, adjustProbability(0.4F));
				addCoinTrade(list, ACItems.token_of_jzahar, 8, ACItems.cthulhu_charm, 1);
				addCoinTrade(list, ACItems.token_of_jzahar, 8, ACItems.hastur_charm, 1);
				addCoinTrade(list, ACItems.token_of_jzahar, 8, ACItems.jzahar_charm, 1);
				addCoinTrade(list, ACItems.token_of_jzahar, 8, ACItems.azathoth_charm, 1);
				addCoinTrade(list, ACItems.token_of_jzahar, 8, ACItems.nyarlathotep_charm, 1);
				addCoinTrade(list, ACItems.token_of_jzahar, 8, ACItems.yog_sothoth_charm, 1);
				addCoinTrade(list, ACItems.token_of_jzahar, 8, ACItems.shub_niggurath_charm, 1);
				addCoinTrade(list, ACItems.staff_of_rending, rand, adjustProbability(0.1F));
				if(rand.nextFloat() < adjustProbability(0.1F))
					list.add(new MerchantRecipe(new ItemStack(ACItems.greater_scroll), ACItems.liquid_antimatter_bucket_stack, new ItemStack(ACItems.antimatter_scroll)));
				if(rand.nextFloat() < adjustProbability(0.1F))
					list.add(new MerchantRecipe(new ItemStack(ACItems.greater_scroll), new ItemStack(ACItems.oblivion_catalyst), new ItemStack(ACItems.oblivion_scroll)));
				Item[] aitem = new Item[] {ACItems.ethaxium_sword, ACItems.ethaxium_chestplate, ACItems.ethaxium_axe, ACItems.ethaxium_pickaxe, ACItems.ethaxium_shovel};
				Item[] aitem1 = aitem;
				int j = aitem.length;
				k = 0;

				while (true)
				{
					if (k >= j)
						break label50;

					Item item = aitem1[k];

					if (rand.nextFloat() < adjustProbability(0.05F))
						list.add(new MerchantRecipe(new ItemStack(item, 1, 0), new ItemStack(ACItems.token_of_jzahar, 2 + rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(item, 1, 0), 5 + rand.nextInt(15), true)));

					++k;
				}
			case 3:
				addItemTrade(list, Items.COAL, rand, adjustProbability(0.7F));
				addItemTrade(list, ACItems.abyssalnite_ingot, rand, adjustProbability(0.5F));
				addItemTrade(list, ACItems.refined_coralium_ingot, rand, adjustProbability(0.5F));
				addItemTrade(list, ACItems.dreadium_ingot, rand, adjustProbability(0.5F));
				addItemTrade(list, ACItems.ethaxium_ingot, rand, adjustProbability(0.3F));
				addCoinTrade(list, ACItems.ethaxium_sword, rand, adjustProbability(0.5F));
				addCoinTrade(list, ACItems.ethaxium_axe, rand, adjustProbability(0.3F));
				addCoinTrade(list, ACItems.ethaxium_pickaxe, rand, adjustProbability(0.5F));
				addCoinTrade(list, ACItems.ethaxium_shovel, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.ethaxium_hoe, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.ethaxium_boots, rand, adjustProbability(0.1F));
				addCoinTrade(list, ACItems.ethaxium_helmet, rand, adjustProbability(0.1F));
				addCoinTrade(list, ACItems.ethaxium_chestplate, rand, adjustProbability(0.1F));
				addCoinTrade(list, ACItems.ethaxium_leggings, rand, adjustProbability(0.1F));
				//				addCoinTrade(list, ACItems.blank_engraving, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.spirit_tablet_shard_0, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.spirit_tablet_shard_1, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.spirit_tablet_shard_2, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.spirit_tablet_shard_3, rand, adjustProbability(0.2F));
				break;
			case 4:
				addItemTrade(list, Items.COAL, rand, adjustProbability(0.7F));
				addItemTrade(list, Items.PORKCHOP, rand, adjustProbability(0.5F));
				addItemTrade(list, Items.BEEF, rand, adjustProbability(0.5F));
				addItemTrade(list, Items.CHICKEN, rand, adjustProbability(0.5F));

				addCoinTrade(list, Items.COOKIE, rand, adjustProbability(0.4F));
				addCoinTrade(list, Items.GOLDEN_CARROT, rand, adjustProbability(0.1F));
				addCoinTrade(list, Items.PUMPKIN_PIE, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.RABBIT_STEW, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.CAKE, rand, adjustProbability(0.3F));
				break;
			case 5:
				//TODO: give banker trades!
				addCoinTrade(list, Items.IRON_INGOT, 3, ACItems.coin, 6);
				addCoinTrade(list, Items.IRON_INGOT, 6, ACItems.coin, 12);
				addCoinTrade(list, Items.GOLD_INGOT, 3, ACItems.coin, 12);
				addCoinTrade(list, Items.GOLD_INGOT, 6, ACItems.coin, 24);
				addCoinTrade(list, Items.EMERALD, 1, ACItems.coin, 8);
				addCoinTrade(list, Items.EMERALD, 2, ACItems.coin, 16);
				addCoinTrade(list, Items.DIAMOND, 1, ACItems.coin, 32);
				addCoinTrade(list, Items.DIAMOND, 2, ACItems.coin, 64);
				addCoinTrade(list, ACItems.coin, 32, ACItems.token_of_jzahar, 1);
				addCoinTrade(list, ACItems.coin, 64, ACItems.token_of_jzahar, 2);
				break;
			case 6:
				addItemTrade(list, Items.COAL, rand, adjustProbability(0.7F));
				addItemTrade(list, ACItems.abyssalnite_ingot, rand, adjustProbability(0.5F));
				addItemTrade(list, ACItems.refined_coralium_ingot, rand, adjustProbability(0.5F));
				addItemTrade(list, ACItems.dreadium_ingot, rand, adjustProbability(0.5F));
				addItemTrade(list, ACItems.ethaxium_ingot, rand, adjustProbability(0.3F));
				addCoinTrade(list, ACItems.ethaxium_sword, rand, adjustProbability(0.5F));
				addCoinTrade(list, ACItems.ethaxium_axe, rand, adjustProbability(0.3F));
				addCoinTrade(list, ACItems.ethaxium_pickaxe, rand, adjustProbability(0.5F));
				addCoinTrade(list, ACItems.ethaxium_shovel, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.ethaxium_hoe, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.plated_coralium_boots, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.dreadium_samurai_boots, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.plated_coralium_helmet, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.dreadium_samurai_helmet, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.plated_coralium_chestplate, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.dreadium_samurai_chestplate, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.plated_coralium_leggings, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.dreadium_samurai_leggings, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.ethaxium_boots, rand, adjustProbability(0.1F));
				addCoinTrade(list, ACItems.ethaxium_helmet, rand, adjustProbability(0.1F));
				addCoinTrade(list, ACItems.ethaxium_chestplate, rand, adjustProbability(0.1F));
				addCoinTrade(list, ACItems.ethaxium_leggings, rand, adjustProbability(0.1F));
				addCoinTrade(list, ACItems.spirit_tablet_shard_0, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.spirit_tablet_shard_1, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.spirit_tablet_shard_2, rand, adjustProbability(0.2F));
				addCoinTrade(list, ACItems.spirit_tablet_shard_3, rand, adjustProbability(0.2F));
			}

		if (list.isEmpty())
			addItemTrade(list, Items.GOLD_INGOT, rand, 1.0F);

		Collections.shuffle(list);

		if (tradingList == null)
			tradingList = new MerchantRecipeList();

		for (int l = 0; l < par1 && l < list.size(); ++l)
			addToListWithCheck(tradingList,list.get(l));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setRecipes(MerchantRecipeList var1) {}

	@Override
	public World getWorld()
	{
		return world;
	}

	@Override
	public BlockPos getPos()
	{
		return new BlockPos(this);
	}

	@Override
	public void useRecipe(MerchantRecipe var1) {
		var1.incrementToolUses();
		if(var1.getItemToSell().getItem() instanceof ItemTool ||
				var1.getItemToSell().getItem() instanceof ItemArmor){
			var1.incrementToolUses();
			var1.incrementToolUses();
			var1.incrementToolUses();
		}
		if(isSpiritTabletShard(var1.getItemToSell()))
			var1.incrementToolUses();
		if(var1.getItemToBuy().getItem() instanceof ItemNecronomicon ||
				var1.getItemToBuy().getItem() instanceof ItemStaffOfRending)
			var1.compensateToolUses();
		livingSoundTime = -getTalkInterval();
		playSound(ACSounds.remnant_yes, getSoundVolume(), getSoundPitch());

		if (hasSameIDsAs(var1, tradingList.get(tradingList.size() - 1)))
		{
			timeUntilReset = 40;
			needsInitilization = true;
		}

		//TODO: do stuff
		if (var1.getItemToBuy().getItem() instanceof ItemCoin)
			wealth += var1.getItemToBuy().getCount();
	}

	/****************** START OF VANILLA CODE FROM 1.7.10 ******************/

	private void addToListWithCheck(MerchantRecipeList list, MerchantRecipe recipe)
	{
		if(recipe.getItemToSell().isEmpty()) return;
		for (int i = 0; i < list.size(); ++i)
		{
			MerchantRecipe merchantrecipe1 = list.get(i);

			if (hasSameIDsAs(recipe, merchantrecipe1))
			{
				if (hasSameItemsAs(recipe, merchantrecipe1))
					list.set(i, recipe);

				return;
			}
		}

		list.add(recipe);
	}

	private boolean hasSameIDsAs(MerchantRecipe r1, MerchantRecipe r2)
	{
		return r1.getItemToBuy().getItem() == r2.getItemToBuy().getItem() && r1.getItemToSell().getItem() == r2.getItemToSell().getItem() ?
				r1.getSecondItemToBuy().isEmpty() && r2.getSecondItemToBuy().isEmpty() || !r1.getSecondItemToBuy().isEmpty() && !r2.getSecondItemToBuy().isEmpty()
				&& r1.getSecondItemToBuy().getItem() == r2.getSecondItemToBuy().getItem() : false;
	}

	private boolean hasSameItemsAs(MerchantRecipe r1, MerchantRecipe r2)
	{
		return hasSameIDsAs(r1, r2) && (r1.getItemToBuy().getCount() < r2.getItemToBuy().getCount() ||
				!r1.getSecondItemToBuy().isEmpty() && r1.getSecondItemToBuy().getCount() < r2.getSecondItemToBuy().getCount());
	}

	/****************** END OF VANILLA CODE FROM 1.7.10 ******************/

	public static void addItemTrade(MerchantRecipeList list, Item item, Random rand, float probability)
	{
		if (rand.nextFloat() < probability)
			list.add(new MerchantRecipe(getItemStackWithQuantity(item, rand), ACItems.token_of_jzahar));
	}

	private static ItemStack getItemStackWithQuantity(Item item, Random rand)
	{
		return new ItemStack(item, getQuantity(item, rand), 0);
	}

	private static int getQuantity(Item item, Random rand)
	{
		Tuple<Integer, Integer> tuple = itemSellingList.get(item);
		return tuple == null ? 1 : tuple.getFirst().intValue() >= tuple.getSecond().intValue() ? tuple.getFirst() : tuple.getFirst().intValue() + rand.nextInt(tuple.getSecond().intValue() - tuple.getFirst().intValue());
	}

	public static void addCoinTrade(MerchantRecipeList list, Item item, Random rand, float probability)
	{
		addCoinTrade(list, new ItemStack(item), rand, probability);
	}

	public static void addCoinTrade(MerchantRecipeList list, ItemStack stack, Random rand, float probability)
	{
		if (rand.nextFloat() < probability)
		{
			int i = getRarity(stack.getItem(), rand);
			ItemStack itemstack;
			ItemStack itemstack1;

			if (i < 0)
			{
				itemstack = new ItemStack(ACItems.token_of_jzahar, 1, 0);
				itemstack1 = stack;
				itemstack1.setCount(i);
			}
			else
			{
				itemstack = new ItemStack(ACItems.token_of_jzahar, i, 0);
				itemstack1 = stack;
				itemstack1.setCount(1);
			}

			if(isSpiritTabletShard(stack))
				list.add(new MerchantRecipe(itemstack, ItemStack.EMPTY, itemstack1, 0, 1));
			else
				list.add(new MerchantRecipe(itemstack, itemstack1));
		}
	}

	private static int getRarity(Item par1, Random par2)
	{
		Tuple<Integer, Integer> tuple = coinSellingList.get(par1);
		return tuple == null ? 1 : tuple.getFirst().intValue() >= tuple.getSecond().intValue() ? tuple.getFirst() : tuple.getFirst().intValue() + par2.nextInt(tuple.getSecond().intValue() - tuple.getFirst().intValue());
	}

	@Override
	public void verifySellingItem(ItemStack par1ItemStack)
	{
		if (!world.isRemote && livingSoundTime > -getTalkInterval() + 20)
		{
			livingSoundTime = -getTalkInterval();

			if (!par1ItemStack.isEmpty())
				playSound(ACSounds.remnant_yes, getSoundVolume(), getSoundPitch());
			else
				playSound(ACSounds.remnant_no, getSoundVolume(), getSoundPitch());
		}
	}

	public void addCoinTrade(MerchantRecipeList list, Item buy, int q1, Item sell, int q2){
		addCoinTrade(list, new ItemStack(buy, q1), new ItemStack(sell, q2));
	}

	public void addCoinTrade(MerchantRecipeList list, Item buy1, int q1, Item buy2, int q2, Item sell, int q3){
		addCoinTrade(list, new ItemStack(buy1, q1), new ItemStack(buy2, q2), new ItemStack(sell, q3));
	}

	public void addCoinTrade(MerchantRecipeList list, ItemStack buy, ItemStack sell){
		addCoinTrade(list, buy, ItemStack.EMPTY, sell);
	}

	public void addCoinTrade(MerchantRecipeList list, ItemStack buy1, ItemStack buy2, ItemStack sell){
		list.add(new MerchantRecipe(buy1, buy2, sell));
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos){
		return !sheared;
	}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess w, BlockPos pos, int fortune)
	{
		int i = 1 + rand.nextInt(3);

		List<ItemStack> ret = new ArrayList<>();
		for (int j = 0; j < i; ++j)
			ret.add(new ItemStack(ACItems.eldritch_scale));

		playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
		playSound(SoundEvents.ENTITY_ITEM_BREAK, 1.0F, 1.0F);
		item.damageItem(5, this);
		sheared = true;

		return ret;
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData data){
		data = super.onInitialSpawn(difficulty, data);
		applyRandomTrade(world.rand);

		EntityUtil.hahaPumpkinGoesBrrr(this, rand);

		return data;
	}

	@Override
	public float getEyeHeight()
	{
		return height * 0.9F;
	}

	static
	{
		itemSellingList.put(Items.COAL, new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
		itemSellingList.put(ACItems.abyssalnite_ingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		itemSellingList.put(ACItems.refined_coralium_ingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		itemSellingList.put(ACItems.dreadium_ingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		itemSellingList.put(ACItems.ethaxium_ingot, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		itemSellingList.put(Items.PAPER, new Tuple(Integer.valueOf(24), Integer.valueOf(36)));
		itemSellingList.put(Items.BOOK, new Tuple(Integer.valueOf(11), Integer.valueOf(13)));
		itemSellingList.put(Items.WRITTEN_BOOK, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
		itemSellingList.put(Items.ENDER_PEARL, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		itemSellingList.put(Items.ENDER_EYE, new Tuple(Integer.valueOf(2), Integer.valueOf(3)));
		itemSellingList.put(Items.PORKCHOP, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
		itemSellingList.put(Items.BEEF, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
		itemSellingList.put(Items.CHICKEN, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
		itemSellingList.put(Items.COOKED_FISH, new Tuple(Integer.valueOf(9), Integer.valueOf(13)));
		itemSellingList.put(Items.WHEAT_SEEDS, new Tuple(Integer.valueOf(34), Integer.valueOf(48)));
		itemSellingList.put(Items.MELON_SEEDS, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
		itemSellingList.put(Items.PUMPKIN_SEEDS, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
		itemSellingList.put(Items.WHEAT, new Tuple(Integer.valueOf(18), Integer.valueOf(22)));
		itemSellingList.put(Item.getItemFromBlock(Blocks.WOOL), new Tuple(Integer.valueOf(14), Integer.valueOf(22)));
		itemSellingList.put(Items.ROTTEN_FLESH, new Tuple(Integer.valueOf(16), Integer.valueOf(28)));
		itemSellingList.put(ACItems.coralium_plagued_flesh, new Tuple(Integer.valueOf(16), Integer.valueOf(28)));
		itemSellingList.put(ACItems.dread_fragment, new Tuple(Integer.valueOf(16), Integer.valueOf(28)));
		itemSellingList.put(ACItems.omothol_ghoul_flesh, new Tuple(Integer.valueOf(32), Integer.valueOf(60)));
		itemSellingList.put(ACItems.rotten_anti_flesh, new Tuple(Integer.valueOf(8), Integer.valueOf(14)));
		coinSellingList.put(Items.FLINT_AND_STEEL, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		coinSellingList.put(Items.SHEARS, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		coinSellingList.put(ACItems.ethaxium_sword, new Tuple(Integer.valueOf(12), Integer.valueOf(14)));
		coinSellingList.put(ACItems.ethaxium_axe, new Tuple(Integer.valueOf(9), Integer.valueOf(12)));
		coinSellingList.put(ACItems.ethaxium_pickaxe, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		coinSellingList.put(ACItems.ethaxium_shovel, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(ACItems.ethaxium_hoe, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(ACItems.ethaxium_boots, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
		coinSellingList.put(ACItems.ethaxium_helmet, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
		coinSellingList.put(ACItems.ethaxium_chestplate, new Tuple(Integer.valueOf(11), Integer.valueOf(15)));
		coinSellingList.put(ACItems.ethaxium_leggings, new Tuple(Integer.valueOf(9), Integer.valueOf(11)));
		coinSellingList.put(Items.BREAD, new Tuple(Integer.valueOf(-4), Integer.valueOf(-2)));
		coinSellingList.put(Items.MELON, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
		coinSellingList.put(Items.APPLE, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
		coinSellingList.put(Items.COOKIE, new Tuple(Integer.valueOf(-10), Integer.valueOf(-7)));
		coinSellingList.put(Item.getItemFromBlock(Blocks.GLASS), new Tuple(Integer.valueOf(-5), Integer.valueOf(-3)));
		coinSellingList.put(Item.getItemFromBlock(Blocks.BOOKSHELF), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		coinSellingList.put(Items.EXPERIENCE_BOTTLE, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
		coinSellingList.put(Items.REDSTONE, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
		coinSellingList.put(Items.COMPASS, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		coinSellingList.put(Items.CLOCK, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		coinSellingList.put(ACItems.necronomicon, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		coinSellingList.put(ACItems.abyssal_wasteland_necronomicon, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		coinSellingList.put(ACItems.dreadlands_necronomicon, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		coinSellingList.put(Item.getItemFromBlock(Blocks.GLOWSTONE), new Tuple(Integer.valueOf(-3), Integer.valueOf(-1)));
		coinSellingList.put(Items.COOKED_CHICKEN, new Tuple(Integer.valueOf(-8), Integer.valueOf(-6)));
		coinSellingList.put(Items.ENDER_EYE, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
		coinSellingList.put(Items.ARROW, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
		coinSellingList.put(ACItems.plated_coralium_boots, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		coinSellingList.put(ACItems.dreadium_samurai_boots, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(ACItems.plated_coralium_helmet, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		coinSellingList.put(ACItems.dreadium_samurai_helmet, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(ACItems.plated_coralium_chestplate, new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
		coinSellingList.put(ACItems.dreadium_samurai_chestplate, new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
		coinSellingList.put(ACItems.plated_coralium_leggings, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		coinSellingList.put(ACItems.dreadium_samurai_leggings, new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
		coinSellingList.put(ACItems.staff_of_rending, new Tuple(Integer.valueOf(20), Integer.valueOf(25)));
		coinSellingList.put(ACItems.spirit_tablet_shard_0, new Tuple(Integer.valueOf(64), Integer.valueOf(64)));
		coinSellingList.put(ACItems.spirit_tablet_shard_1, new Tuple(Integer.valueOf(64), Integer.valueOf(64)));
		coinSellingList.put(ACItems.spirit_tablet_shard_2, new Tuple(Integer.valueOf(64), Integer.valueOf(64)));
		coinSellingList.put(ACItems.spirit_tablet_shard_3, new Tuple(Integer.valueOf(64), Integer.valueOf(64)));
	}

	public void applyRandomTrade(Random rand){
		int trade = rand.nextInt(7);
		setProfession(trade);
	}

	private static boolean isSpiritTabletShard(ItemStack stack) {
		return stack.getItem() == ACItems.spirit_tablet_shard_0 || stack.getItem() == ACItems.spirit_tablet_shard_1
				|| stack.getItem() == ACItems.spirit_tablet_shard_2 || stack.getItem() == ACItems.spirit_tablet_shard_3;
	}
}
