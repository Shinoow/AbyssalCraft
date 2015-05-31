/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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

import net.minecraft.block.Block;
import net.minecraft.enchantment.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.*;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityRemnant extends EntityMob implements IMerchant, IAntiEntity, ICoraliumEntity, IDreadEntity {

	private EntityPlayer tradingPlayer;
	private MerchantRecipeList tradingList;
	private int timeUntilReset;
	private boolean needsInitilization;
	private int wealth;
	private boolean isAngry;
	private int timer;
	private float field_82191_bN;
	public static final Map<Item, Tuple> itemSellingList = new HashMap<Item, Tuple>();
	public static final Map<Item, Tuple> coinSellingList = new HashMap<Item, Tuple>();

	public EntityRemnant(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityLivingBase.class, 0.35D, false));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityRemnant.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGatekeeperMinion.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		swingItem();
		boolean flag = super.attackEntityAsMob(par1Entity);

		return flag;
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected void updateAITick() {

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

							if (merchantrecipe.isRecipeDisabled())
								merchantrecipe.func_82783_a(rand.nextInt(6) + rand.nextInt(6) + 2);
						}
					}

					addDefaultEquipmentAndRecipies(1);
					needsInitilization = false;
				}

				addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
			}
		}

		super.updateAITick();
	}

	@Override
	public boolean interact(EntityPlayer par1EntityPlayer)
	{
		if(isEntityAlive() && !isTrading() && !par1EntityPlayer.isSneaking() && !isAngry){
			if(!worldObj.isRemote){
				setCustomer(par1EntityPlayer);
				par1EntityPlayer.displayGUIMerchant(this, StatCollector.translateToLocal("entity.abyssalcraft.remnant.name"));
			}

			return true;
		}

		return super.interact(par1EntityPlayer);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Integer.valueOf(0));
	}

	/**
	 * Used by Minions of The Gatekeeper to set the attack target and enrage the Remnant
	 * @param call If the Remnant should call for backup
	 * @param enemy The target
	 */
	public void enrage(boolean call, EntityLivingBase enemy){
		setTarget(enemy);
		enrage(call);
	}

	/**
	 * Calling this method will make the Remnant hostile.
	 * Ever wanted to see a angry Remnant? No you don't.
	 * @param call If the Remnant should call for backup
	 */
	public void enrage(boolean call){
		if(call){
			List<EntityRemnant> friends = worldObj.getEntitiesWithinAABB(getClass(), boundingBox.expand(16D, 16D, 16D));
			if(friends != null){
				Iterator<EntityRemnant> iter = friends.iterator();
				while(iter.hasNext())
					iter.next().enrage(false, (EntityLivingBase) entityToAttack);
			}
			worldObj.playSoundAtEntity(this, "abyssalcraft:remnant.scream", 3F, 1F);
		}

		isAngry = true;
		timer = 0;
		if(entityToAttack != null)
			targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, entityToAttack.getClass(), 0, true));
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par1DamageSource.getSourceOfDamage() instanceof EntityLivingBase)
			if(entityToAttack != par1DamageSource.getSourceOfDamage()){
				entityToAttack = par1DamageSource.getEntity();
				enrage(true, (EntityLivingBase) entityToAttack);
			}
		if(!isAngry) enrage(true);
		else enrage(rand.nextInt(10) == 0);
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public void onLivingUpdate(){
		super.onLivingUpdate();
		if(isAngry){
			if(entityToAttack != null)
				targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, entityToAttack.getClass(), 0, true));
			timer++;
			if(timer == 2400){
				isAngry = false;
				targetTasks.removeTask(new EntityAINearestAttackableTarget(this, entityToAttack.getClass(), 0, true));
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setInteger("Profession", getProfession());
		par1NBTTagCompound.setInteger("Money", wealth);
		par1NBTTagCompound.setBoolean("IsAngry", isAngry);

		if (tradingList != null)
			par1NBTTagCompound.setTag("Offers", tradingList.getRecipiesAsTags());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		setProfession(par1NBTTagCompound.getInteger("Profession"));
		wealth = par1NBTTagCompound.getInteger("Money");
		isAngry = par1NBTTagCompound.getBoolean("IsAngry");

		if (par1NBTTagCompound.hasKey("Offers", 10))
		{
			NBTTagCompound nbttagcompound1 = par1NBTTagCompound.getCompoundTag("Offers");
			tradingList = new MerchantRecipeList(nbttagcompound1);
		}
	}

	@Override
	protected Item getDropItem()
	{
		return AbyssalCraft.eldritchScale;
	}

	@Override
	protected String getDeathSound()
	{
		return "abyssalcraft:shadow.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
	{
		playSound("mob.spider.step", 0.15F, 1.0F);
	}

	public void setProfession(int par1)
	{
		dataWatcher.updateObject(16, Integer.valueOf(par1));
	}

	public int getProfession()
	{
		return dataWatcher.getWatchableObjectInt(16);
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

	@SuppressWarnings("unchecked")
	private void addDefaultEquipmentAndRecipies(int par1)
	{
		if (tradingList != null)
			field_82191_bN = MathHelper.sqrt_float(tradingList.size()) * 0.2F;
		else
			field_82191_bN = 0.0F;

		MerchantRecipeList list;
		list = new MerchantRecipeList();
		int k;
		label50:

			switch (getProfession())
			{
			case 0:
				addItemTrade(list, Items.wheat, rand, adjustProbability(0.9F));
				addItemTrade(list, Item.getItemFromBlock(Blocks.wool), rand, adjustProbability(0.5F));
				addItemTrade(list, Items.chicken, rand, adjustProbability(0.5F));
				addItemTrade(list, Items.cooked_fished, rand, adjustProbability(0.4F));
				addCoinTrade(list, Items.bread, rand, adjustProbability(0.9F));
				addCoinTrade(list, Items.melon, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.apple, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.cookie, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.shears, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.flint_and_steel, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.fishp, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.arrow, rand, adjustProbability(0.5F));

				if (rand.nextFloat() < adjustProbability(0.5F))
					list.add(new MerchantRecipe(new ItemStack(Blocks.gravel, 10), new ItemStack(AbyssalCraft.jzaharCoin), new ItemStack(Items.flint, 4 + rand.nextInt(2), 0)));

				break;
			case 1:
				addItemTrade(list, Items.paper, rand, adjustProbability(0.8F));
				addItemTrade(list, Items.book, rand, adjustProbability(0.8F));
				addItemTrade(list, Items.written_book, rand, adjustProbability(0.3F));
				addCoinTrade(list, Item.getItemFromBlock(Blocks.bookshelf), rand, adjustProbability(0.8F));
				addCoinTrade(list, Item.getItemFromBlock(Blocks.glass), rand, adjustProbability(0.2F));
				addCoinTrade(list, Items.compass, rand, adjustProbability(0.2F));
				addCoinTrade(list, Items.clock, rand, adjustProbability(0.2F));

				if (rand.nextFloat() < adjustProbability(0.07F))
				{
					Enchantment enchantment = Enchantment.enchantmentsBookList[rand.nextInt(Enchantment.enchantmentsBookList.length)];
					int i1 = MathHelper.getRandomIntegerInRange(rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
					ItemStack itemstack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i1));
					k = 2 + rand.nextInt(5 + i1 * 10) + 3 * i1;
					list.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(AbyssalCraft.jzaharCoin, k), itemstack));
				}

				break;
			case 2:
				addCoinTrade(list, Items.ender_eye, rand, adjustProbability(0.3F));
				addCoinTrade(list, Items.experience_bottle, rand, adjustProbability(0.2F));
				addCoinTrade(list, Items.redstone, rand, adjustProbability(0.4F));
				addCoinTrade(list, Item.getItemFromBlock(Blocks.glowstone), rand, adjustProbability(0.3F));
				Item[] aitem = new Item[] {AbyssalCraft.Corsword, AbyssalCraft.dreadiumsword, AbyssalCraft.Corplate, AbyssalCraft.dreadiumplate, AbyssalCraft.Coraxe, AbyssalCraft.dreadiumaxe, AbyssalCraft.Corpickaxe, AbyssalCraft.dreadiumpickaxe};
				Item[] aitem1 = aitem;
				int j = aitem.length;
				k = 0;

				while (true)
				{
					if (k >= j)
						break label50;

					Item item = aitem1[k];

					if (rand.nextFloat() < adjustProbability(0.05F))
						list.add(new MerchantRecipe(new ItemStack(item, 1, 0), new ItemStack(AbyssalCraft.jzaharCoin, 2 + rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(rand, new ItemStack(item, 1, 0), 5 + rand.nextInt(15))));

					++k;
				}
			case 3:
				addItemTrade(list, Items.coal, rand, adjustProbability(0.7F));
				addItemTrade(list, AbyssalCraft.abyingot, rand, adjustProbability(0.5F));
				addItemTrade(list, AbyssalCraft.Cingot, rand, adjustProbability(0.5F));
				addItemTrade(list, AbyssalCraft.dreadiumingot, rand, adjustProbability(0.5F));
				addItemTrade(list, AbyssalCraft.ethaxiumIngot, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.Corsword, rand, adjustProbability(0.5F));
				addCoinTrade(list, AbyssalCraft.dreadiumsword, rand, adjustProbability(0.5F));
				addCoinTrade(list, AbyssalCraft.Coraxe, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.dreadiumaxe, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.Corpickaxe, rand, adjustProbability(0.5F));
				addCoinTrade(list, AbyssalCraft.dreadiumpickaxe, rand, adjustProbability(0.5F));
				addCoinTrade(list, AbyssalCraft.Corshovel, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumshovel, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.Corhoe, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumhoe, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.Corboots, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumboots, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.Corhelmet, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumhelmet, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.Corplate, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumplate, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.Corlegs, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumlegs, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.ethBoots, rand, adjustProbability(0.1F));
				addCoinTrade(list, AbyssalCraft.ethHelmet, rand, adjustProbability(0.1F));
				addCoinTrade(list, AbyssalCraft.ethPlate, rand, adjustProbability(0.1F));
				addCoinTrade(list, AbyssalCraft.ethLegs, rand, adjustProbability(0.1F));
				break;
			case 4:
				addItemTrade(list, Items.coal, rand, adjustProbability(0.7F));
				addItemTrade(list, Items.porkchop, rand, adjustProbability(0.5F));
				addItemTrade(list, Items.beef, rand, adjustProbability(0.5F));
				addCoinTrade(list, AbyssalCraft.cloth, rand, adjustProbability(0.4F));
				addCoinTrade(list, AbyssalCraft.MRE, rand, adjustProbability(0.1F));
				addCoinTrade(list, AbyssalCraft.plate, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.boots, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.helmet, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.legs, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.porkp, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.beefp, rand, adjustProbability(0.3F));
				break;
			case 5:
				addCoinTrade(list, AbyssalCraft.cthulhuCoin, 2, AbyssalCraft.elderCoin, 1);
				addCoinTrade(list, AbyssalCraft.cthulhuCoin, 4, AbyssalCraft.jzaharCoin, 1);
				addCoinTrade(list, AbyssalCraft.elderCoin, 1, AbyssalCraft.cthulhuCoin, 2);
				addCoinTrade(list, AbyssalCraft.elderCoin, 2, AbyssalCraft.jzaharCoin, 1);
				addCoinTrade(list, AbyssalCraft.jzaharCoin, 1, AbyssalCraft.cthulhuCoin, 4);
				addCoinTrade(list, AbyssalCraft.jzaharCoin, 1, AbyssalCraft.elderCoin, 2);
				break;

			case 6:
				addItemTrade(list, Items.coal, rand, adjustProbability(0.7F));
				addItemTrade(list, AbyssalCraft.abyingot, rand, adjustProbability(0.5F));
				addItemTrade(list, AbyssalCraft.Cingot, rand, adjustProbability(0.5F));
				addItemTrade(list, AbyssalCraft.dreadiumingot, rand, adjustProbability(0.5F));
				addItemTrade(list, AbyssalCraft.ethaxiumIngot, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.Corsword, rand, adjustProbability(0.5F));
				addCoinTrade(list, AbyssalCraft.dreadiumsword, rand, adjustProbability(0.5F));
				addCoinTrade(list, AbyssalCraft.Coraxe, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.dreadiumaxe, rand, adjustProbability(0.3F));
				addCoinTrade(list, AbyssalCraft.Corpickaxe, rand, adjustProbability(0.5F));
				addCoinTrade(list, AbyssalCraft.dreadiumpickaxe, rand, adjustProbability(0.5F));
				addCoinTrade(list, AbyssalCraft.Corshovel, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumshovel, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.Corhoe, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumhoe, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.CorbootsP, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumSboots, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.CorhelmetP, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumShelmet, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.CorplateP, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumSplate, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.CorlegsP, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.dreadiumSlegs, rand, adjustProbability(0.2F));
				addCoinTrade(list, AbyssalCraft.ethBoots, rand, adjustProbability(0.1F));
				addCoinTrade(list, AbyssalCraft.ethHelmet, rand, adjustProbability(0.1F));
				addCoinTrade(list, AbyssalCraft.ethPlate, rand, adjustProbability(0.1F));
				addCoinTrade(list, AbyssalCraft.ethLegs, rand, adjustProbability(0.1F));
			}

		if (list.isEmpty())
			addItemTrade(list, Items.gold_ingot, rand, 1.0F);

		Collections.shuffle(list);

		if (tradingList == null)
			tradingList = new MerchantRecipeList();

		for (int l = 0; l < par1 && l < list.size(); ++l)
			tradingList.addToListWithCheck((MerchantRecipe)list.get(l));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setRecipes(MerchantRecipeList var1) {}

	@Override
	public void useRecipe(MerchantRecipe var1) {
		var1.incrementToolUses();
		livingSoundTime = -getTalkInterval();
		playSound("mob.villager.yes", getSoundVolume(), getSoundPitch() * 0.5F);

		if (var1.hasSameIDsAs((MerchantRecipe)tradingList.get(tradingList.size() - 1)))
		{
			timeUntilReset = 40;
			needsInitilization = true;
		}

		if (var1.getItemToBuy().getItem() == AbyssalCraft.jzaharCoin)
			wealth += var1.getItemToBuy().stackSize;
	}

	@SuppressWarnings("unchecked")
	public static void addItemTrade(MerchantRecipeList list, Item item, Random rand, float probability)
	{
		if (rand.nextFloat() < probability)
			list.add(new MerchantRecipe(getItemStackWithQuantity(item, rand), AbyssalCraft.jzaharCoin));
	}

	private static ItemStack getItemStackWithQuantity(Item item, Random rand)
	{
		return new ItemStack(item, getQuantity(item, rand), 0);
	}

	private static int getQuantity(Item item, Random rand)
	{
		Tuple tuple = itemSellingList.get(item);
		return tuple == null ? 1 : ((Integer)tuple.getFirst()).intValue() >= ((Integer)tuple.getSecond()).intValue() ? ((Integer)tuple.getFirst()).intValue() : ((Integer)tuple.getFirst()).intValue() + rand.nextInt(((Integer)tuple.getSecond()).intValue() - ((Integer)tuple.getFirst()).intValue());
	}

	@SuppressWarnings("unchecked")
	public static void addCoinTrade(MerchantRecipeList list, Item item, Random rand, float probability)
	{
		if (rand.nextFloat() < probability)
		{
			int i = getRarity(item, rand);
			ItemStack itemstack;
			ItemStack itemstack1;

			if (i < 0)
			{
				itemstack = new ItemStack(AbyssalCraft.jzaharCoin, 1, 0);
				itemstack1 = new ItemStack(item, -i, 0);
			}
			else
			{
				itemstack = new ItemStack(AbyssalCraft.jzaharCoin, i, 0);
				itemstack1 = new ItemStack(item, 1, 0);
			}

			list.add(new MerchantRecipe(itemstack, itemstack1));
		}
	}

	private static int getRarity(Item par1, Random par2)
	{
		Tuple tuple = coinSellingList.get(par1);
		return tuple == null ? 1 : ((Integer)tuple.getFirst()).intValue() >= ((Integer)tuple.getSecond()).intValue() ? ((Integer)tuple.getFirst()).intValue() : ((Integer)tuple.getFirst()).intValue() + par2.nextInt(((Integer)tuple.getSecond()).intValue() - ((Integer)tuple.getFirst()).intValue());
	}

	@Override
	public void func_110297_a_(ItemStack par1ItemStack)
	{
		if (!worldObj.isRemote && livingSoundTime > -getTalkInterval() + 20)
		{
			livingSoundTime = -getTalkInterval();

			if (par1ItemStack != null)
				playSound("mob.villager.yes", getSoundVolume(), getSoundPitch() * 0.5F);
			else
				playSound("mob.villager.no", getSoundVolume(), getSoundPitch() * 0.5F);
		}
	}

	public void addCoinTrade(MerchantRecipeList list, Item buy, int q1, Item sell, int q2){
		addCoinTrade(list, new ItemStack(buy, q1), new ItemStack(sell, q2));
	}

	public void addCoinTrade(MerchantRecipeList list, Item buy1, int q1, Item buy2, int q2, Item sell, int q3){
		addCoinTrade(list, new ItemStack(buy1, q1), new ItemStack(buy2, q2), new ItemStack(sell, q3));
	}

	public void addCoinTrade(MerchantRecipeList list, ItemStack buy, ItemStack sell){
		addCoinTrade(list, buy, (ItemStack)null, sell);
	}

	@SuppressWarnings("unchecked")
	public void addCoinTrade(MerchantRecipeList list, ItemStack buy1, ItemStack buy2, ItemStack sell){
		list.add(new MerchantRecipe(buy1, buy2, sell));
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData data){
		data = super.onSpawnWithEgg(data);
		applyRandomTrade(worldObj.rand);
		return data;
	}

	static
	{
		itemSellingList.put(Items.coal, new Tuple(Integer.valueOf(16), Integer.valueOf(24)));
		itemSellingList.put(AbyssalCraft.abyingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		itemSellingList.put(AbyssalCraft.Cingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		itemSellingList.put(AbyssalCraft.dreadiumingot, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		itemSellingList.put(AbyssalCraft.ethaxiumIngot, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		itemSellingList.put(Items.paper, new Tuple(Integer.valueOf(24), Integer.valueOf(36)));
		itemSellingList.put(Items.book, new Tuple(Integer.valueOf(11), Integer.valueOf(13)));
		itemSellingList.put(Items.written_book, new Tuple(Integer.valueOf(1), Integer.valueOf(1)));
		itemSellingList.put(Items.ender_pearl, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		itemSellingList.put(Items.ender_eye, new Tuple(Integer.valueOf(2), Integer.valueOf(3)));
		itemSellingList.put(Items.porkchop, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
		itemSellingList.put(Items.beef, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
		itemSellingList.put(Items.chicken, new Tuple(Integer.valueOf(14), Integer.valueOf(18)));
		itemSellingList.put(Items.cooked_fished, new Tuple(Integer.valueOf(9), Integer.valueOf(13)));
		itemSellingList.put(Items.wheat_seeds, new Tuple(Integer.valueOf(34), Integer.valueOf(48)));
		itemSellingList.put(Items.melon_seeds, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
		itemSellingList.put(Items.pumpkin_seeds, new Tuple(Integer.valueOf(30), Integer.valueOf(38)));
		itemSellingList.put(Items.wheat, new Tuple(Integer.valueOf(18), Integer.valueOf(22)));
		itemSellingList.put(Item.getItemFromBlock(Blocks.wool), new Tuple(Integer.valueOf(14), Integer.valueOf(22)));
		itemSellingList.put(AbyssalCraft.Corflesh, new Tuple(Integer.valueOf(36), Integer.valueOf(64)));
		coinSellingList.put(Items.flint_and_steel, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		coinSellingList.put(Items.shears, new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		coinSellingList.put(AbyssalCraft.Corsword, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
		coinSellingList.put(AbyssalCraft.dreadiumsword, new Tuple(Integer.valueOf(12), Integer.valueOf(14)));
		coinSellingList.put(AbyssalCraft.Coraxe, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
		coinSellingList.put(AbyssalCraft.dreadiumaxe, new Tuple(Integer.valueOf(9), Integer.valueOf(12)));
		coinSellingList.put(AbyssalCraft.Corpickaxe, new Tuple(Integer.valueOf(7), Integer.valueOf(9)));
		coinSellingList.put(AbyssalCraft.dreadiumpickaxe, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		coinSellingList.put(AbyssalCraft.Corshovel, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		coinSellingList.put(AbyssalCraft.dreadiumshovel, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(AbyssalCraft.Corhoe, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		coinSellingList.put(AbyssalCraft.dreadiumhoe, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(AbyssalCraft.Corboots, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		coinSellingList.put(AbyssalCraft.dreadiumboots, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(AbyssalCraft.Corhelmet, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		coinSellingList.put(AbyssalCraft.dreadiumhelmet, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(AbyssalCraft.Corplate, new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
		coinSellingList.put(AbyssalCraft.dreadiumplate, new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
		coinSellingList.put(AbyssalCraft.Corlegs, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		coinSellingList.put(AbyssalCraft.dreadiumlegs, new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
		coinSellingList.put(AbyssalCraft.ethBoots, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
		coinSellingList.put(AbyssalCraft.ethHelmet, new Tuple(Integer.valueOf(5), Integer.valueOf(7)));
		coinSellingList.put(AbyssalCraft.ethPlate, new Tuple(Integer.valueOf(11), Integer.valueOf(15)));
		coinSellingList.put(AbyssalCraft.ethLegs, new Tuple(Integer.valueOf(9), Integer.valueOf(11)));
		coinSellingList.put(Items.bread, new Tuple(Integer.valueOf(-4), Integer.valueOf(-2)));
		coinSellingList.put(Items.melon, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
		coinSellingList.put(Items.apple, new Tuple(Integer.valueOf(-8), Integer.valueOf(-4)));
		coinSellingList.put(Items.cookie, new Tuple(Integer.valueOf(-10), Integer.valueOf(-7)));
		coinSellingList.put(Item.getItemFromBlock(Blocks.glass), new Tuple(Integer.valueOf(-5), Integer.valueOf(-3)));
		coinSellingList.put(Item.getItemFromBlock(Blocks.bookshelf), new Tuple(Integer.valueOf(3), Integer.valueOf(4)));
		coinSellingList.put(AbyssalCraft.plate, new Tuple(Integer.valueOf(4), Integer.valueOf(5)));
		coinSellingList.put(AbyssalCraft.boots, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
		coinSellingList.put(AbyssalCraft.helmet, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
		coinSellingList.put(AbyssalCraft.legs, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
		coinSellingList.put(AbyssalCraft.cloth, new Tuple(Integer.valueOf(2), Integer.valueOf(4)));
		coinSellingList.put(AbyssalCraft.MRE, new Tuple(Integer.valueOf(6), Integer.valueOf(8)));
		coinSellingList.put(Items.experience_bottle, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
		coinSellingList.put(Items.redstone, new Tuple(Integer.valueOf(-4), Integer.valueOf(-1)));
		coinSellingList.put(Items.compass, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		coinSellingList.put(Items.clock, new Tuple(Integer.valueOf(10), Integer.valueOf(12)));
		coinSellingList.put(Item.getItemFromBlock(Blocks.glowstone), new Tuple(Integer.valueOf(-3), Integer.valueOf(-1)));
		coinSellingList.put(AbyssalCraft.porkp, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
		coinSellingList.put(AbyssalCraft.beefp, new Tuple(Integer.valueOf(-7), Integer.valueOf(-5)));
		coinSellingList.put(Items.cooked_chicken, new Tuple(Integer.valueOf(-8), Integer.valueOf(-6)));
		coinSellingList.put(Items.ender_eye, new Tuple(Integer.valueOf(7), Integer.valueOf(11)));
		coinSellingList.put(Items.arrow, new Tuple(Integer.valueOf(-12), Integer.valueOf(-8)));
		coinSellingList.put(AbyssalCraft.CorbootsP, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		coinSellingList.put(AbyssalCraft.dreadiumSboots, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(AbyssalCraft.CorhelmetP, new Tuple(Integer.valueOf(4), Integer.valueOf(6)));
		coinSellingList.put(AbyssalCraft.dreadiumShelmet, new Tuple(Integer.valueOf(7), Integer.valueOf(8)));
		coinSellingList.put(AbyssalCraft.CorplateP, new Tuple(Integer.valueOf(10), Integer.valueOf(14)));
		coinSellingList.put(AbyssalCraft.dreadiumSplate, new Tuple(Integer.valueOf(16), Integer.valueOf(19)));
		coinSellingList.put(AbyssalCraft.CorlegsP, new Tuple(Integer.valueOf(8), Integer.valueOf(10)));
		coinSellingList.put(AbyssalCraft.dreadiumSlegs, new Tuple(Integer.valueOf(11), Integer.valueOf(14)));
	}

	public void applyRandomTrade(Random rand){
		int trade = rand.nextInt(7);
		setProfession(trade);
	}
}
