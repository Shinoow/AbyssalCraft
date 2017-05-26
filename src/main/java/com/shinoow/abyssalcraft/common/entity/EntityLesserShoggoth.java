/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothOoze;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonPig;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenShoggothMonolith;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;

public class EntityLesserShoggoth extends EntityMob implements ICoraliumEntity, IDreadEntity {

	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityLesserShoggoth.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> CHILD = EntityDataManager.createKey(EntityLesserShoggoth.class, DataSerializers.BYTE);
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityLesserShoggoth.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FOOD = EntityDataManager.createKey(EntityLesserShoggoth.class, DataSerializers.VARINT);
	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);

	private int monolithTimer;
	private float shoggothWidth = -1.0F;
	private float shoggothHeight;

	public EntityLesserShoggoth(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.35D, true));
		tasks.addTask(3, new EntityAIFleeSun(this, 0.35D));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadling.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadSpawn.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDemonPig.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadguard.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityOmotholWarden.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityOmotholGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityRemnant.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityLesserShoggoth.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGatekeeperMinion.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		for(Class<? extends EntityLivingBase> c : EntityUtil.getShoggothFood())
			targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, c, true));
		setSize(1.5F, 2.6F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
		}
	}

	@Override
	protected PathNavigate getNewNavigator(World worldIn)
	{
		return new PathNavigateClimber(this, worldIn);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(CHILD, Byte.valueOf((byte)0));
		dataManager.register(TYPE, Integer.valueOf(0));
		dataManager.register(FOOD, Integer.valueOf(0));
		dataManager.register(CLIMBING, Byte.valueOf((byte)0));
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!worldObj.isRemote)
			setBesideClimbableBlock(isCollidedHorizontally);
	}

	@Override
	public boolean isChild()
	{
		return dataManager.get(CHILD).byteValue() == 1;
	}

	public void setChild(boolean par1)
	{
		dataManager.set(CHILD, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (worldObj != null && !worldObj.isRemote)
		{
			IAttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			attributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1)
				attributeinstance.applyModifier(babySpeedBoostModifier);
		}

		setChildSize(par1);
	}

	public int getShoggothType() {

		return dataManager.get(TYPE);
	}

	public void setShoggothType(int par1) {

		dataManager.set(TYPE, Integer.valueOf(par1));
	}

	public void setFoodLevel(int par1){

		dataManager.set(FOOD, Integer.valueOf(par1));
	}

	public int getFoodLevel(){

		return dataManager.get(FOOD);
	}

	public void feed(){
		int food = getFoodLevel() + 1;
		dataManager.set(FOOD, Integer.valueOf(food));
	}

	@Override
	public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
	}

	/**
	 * Reduces this Shoggoth's monolith timer
	 */
	public void reduceMonolithTimer(){
		if(monolithTimer - 200 >= 200)
			monolithTimer -= 200;
		else monolithTimer = 0;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(worldObj.isRemote)
			setChildSize(isChild());

		monolithTimer++;

		if(getFoodLevel() == 3 && !worldObj.isRemote){
			setFoodLevel(0);
			if(!isChild()){
				EntityLesserShoggoth shoggoth = new EntityLesserShoggoth(worldObj);
				shoggoth.copyLocationAndAnglesFrom(this);
				shoggoth.onInitialSpawn(worldObj.getDifficultyForLocation(new BlockPos(posX, posY, posZ)),(IEntityLivingData)null);
				shoggoth.setChild(true);
				shoggoth.setShoggothType(getShoggothType());
				worldObj.spawnEntityInWorld(shoggoth);
				playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			}
			else setChild(false);
		}

		if(!worldObj.isRemote)
			for (int l = 0; l < 1; ++l)
			{
				int x = MathHelper.floor_double(posX + (l % 2 * 2 - 1) * 0.25F);
				int y = MathHelper.floor_double(posY);
				int z = MathHelper.floor_double(posZ + (l / 2 % 2 * 2 - 1) * 0.25F);

				spawnOoze(x, y, z);
				if(!isChild()){
					spawnOoze(x - 1, y, z);
					spawnOoze(x, y, z - 1);
					spawnOoze(x - 1, y, z - 1);
				}
			}

		if(monolithTimer >= 1800){
			monolithTimer = 0;
			if(worldObj.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().expand(32D, 32D, 32D)).size() > 5 && !isChild()){
				for(EntityLesserShoggoth shoggoth : worldObj.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().expand(32D, 32D, 32D)))
					shoggoth.reduceMonolithTimer();
				if(!worldObj.isRemote)
					new WorldGenShoggothMonolith().generate(worldObj, rand, new BlockPos(MathHelper.floor_double(posX) + 3, MathHelper.floor_double(posY), MathHelper.floor_double(posZ) + 3));
			}
		}

		for (int i = 0; i < 2 && getShoggothType() == 4 && ACConfig.particleEntity && worldObj.provider.getDimension() != ACLib.dark_realm_id; ++i)
			worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);
	}

	/**
	 * Checks if a Lesser Shoggoth can generate ooze at the specific coordinates
	 * @param x X-coord
	 * @param y Y-coord
	 * @param z Z-coord
	 */
	private void spawnOoze(int x, int y, int z){
		BlockPos pos = new BlockPos(x, y, z);
		if(ACConfig.shoggothOoze)
			if((worldObj.getBlockState(pos).getMaterial() == Material.AIR || worldObj.getBlockState(pos).getBlock().isReplaceable(worldObj, pos)) && ACBlocks.shoggoth_ooze.canPlaceBlockAt(worldObj, pos)
					&& worldObj.getBlockState(pos).getBlock() != ACBlocks.shoggoth_ooze && !worldObj.getBlockState(pos).getMaterial().isLiquid())
				worldObj.setBlockState(pos, ACBlocks.shoggoth_ooze.getDefaultState());
			else if(worldObj.getBlockState(pos).getBlock() == ACBlocks.shoggoth_ooze && worldObj.getBlockState(pos).getValue(BlockShoggothOoze.LAYERS) < 8
					&& ticksExisted % 10 == 0 && rand.nextInt(5) == 0){
				IBlockState state = worldObj.getBlockState(pos);
				worldObj.setBlockState(pos, state.withProperty(BlockShoggothOoze.LAYERS, state.getValue(BlockShoggothOoze.LAYERS) + 1));
			}
	}

	/**
	 * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	 * setBesideClimableBlock.
	 */
	public boolean isBesideClimbableBlock()
	{
		return (dataManager.get(CLIMBING) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
	public void setBesideClimbableBlock(boolean par1)
	{
		byte b0 = dataManager.get(CLIMBING);

		if (par1)
			b0 = (byte)(b0 | 1);
		else
			b0 &= -2;

		dataManager.set(CLIMBING, Byte.valueOf(b0));
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag)
			if (par1Entity instanceof EntityLivingBase)
				switch(getShoggothType()){
				case 1:
					if(!EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
						((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100));
					break;
				case 2:
					if(!EntityUtil.isEntityDread((EntityLivingBase)par1Entity))
						((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
					break;
				case 3:
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
					break;
				case 4:
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
					break;
				}

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 3 * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if(par1DamageSource.isProjectile()){
			playSound(SoundEvents.ENTITY_SMALL_SLIME_JUMP, getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			return false;
		}
		if(par1DamageSource == DamageSource.cactus) return false;

		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	public void fall(float par1, float par2) {}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return ACSounds.shoggoth_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return ACSounds.shoggoth_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ACSounds.shoggoth_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		ItemStack item = new ItemStack(ACItems.shoggoth_flesh, 1, getShoggothType());

		if (item != null)
		{
			int i = rand.nextInt(3);

			if (par2 > 0)
				i += rand.nextInt(par2 + 1);

			for (int j = 0; j < i; ++j)
				entityDropItem(item, 0);
		}
	}

	@Override
	protected ResourceLocation getLootTable(){
		switch(getShoggothType()){
		case 0:
			return ACLoot.ENTITY_LESSER_SHOGGOTH;
		case 1:
			return ACLoot.ENTITY_LESSER_ABYSSAL_SHOGGOTH;
		case 2:
			return ACLoot.ENTITY_LESSER_DREADED_SHOGGOTH;
		case 3:
			return ACLoot.ENTITY_LESSER_OMOTHOL_SHOGGOTH;
		case 4:
			return ACLoot.ENTITY_LESSER_SHADOW_SHOGGOTH;
		}
		return null;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return getShoggothType() == 4 ? AbyssalCraftAPI.SHADOW : EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if(par1NBTTagCompound.getBoolean("IsBaby"))
			setChild(true);

		if (par1NBTTagCompound.hasKey("ShoggothType"))
		{
			byte var2 = par1NBTTagCompound.getByte("ShoggothType");
			setShoggothType(var2);
		}

		if(par1NBTTagCompound.hasKey("FoodLevel")){
			byte var2 = par1NBTTagCompound.getByte("FoodLevel");
			setFoodLevel(var2);
		}

		monolithTimer = par1NBTTagCompound.getInteger("MonolithTimer");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (isChild())
			par1NBTTagCompound.setBoolean("IsBaby", true);

		par1NBTTagCompound.setByte("ShoggothType", (byte)getShoggothType());

		par1NBTTagCompound.setByte("FoodLevel", (byte)getFoodLevel());

		par1NBTTagCompound.setInteger("MonolithTimer", monolithTimer);
	}

	@Override
	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);

		if(EntityUtil.isShoggothFood(par1EntityLivingBase))
			feed();
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onInitialSpawn(difficulty, par1EntityLivingData);

		setShoggothType(0);

		if(worldObj.provider.getDimension() == ACLib.abyssal_wasteland_id)
			setShoggothType(1);
		if(worldObj.provider.getDimension() == ACLib.dreadlands_id)
			setShoggothType(2);
		if(worldObj.provider.getDimension() == ACLib.omothol_id)
			setShoggothType(3);
		if(worldObj.provider.getDimension() == ACLib.dark_realm_id)
			setShoggothType(4);

		if (data == null)
			data = new EntityLesserShoggoth.GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);

		if (data instanceof EntityLesserShoggoth.GroupData)
		{
			EntityLesserShoggoth.GroupData groupdata = (EntityLesserShoggoth.GroupData)data;

			if (groupdata.isBaby)
				setChild(true);
		}

		return (IEntityLivingData)data;
	}

	public void setChildSize(boolean p_146071_1_)
	{
		multiplySize(p_146071_1_ ? 0.5F : 1.0F);
	}

	@Override
	protected final void setSize(float p_70105_1_, float p_70105_2_)
	{
		boolean flag = shoggothWidth > 0.0F && shoggothHeight > 0.0F;
		shoggothWidth = p_70105_1_;
		shoggothHeight = p_70105_2_;

		if (!flag)
			multiplySize(1.0F);
	}

	protected final void multiplySize(float p_146069_1_)
	{
		super.setSize(shoggothWidth * p_146069_1_, shoggothHeight * p_146069_1_);
	}

	class GroupData implements IEntityLivingData
	{
		public boolean isBaby;
		private GroupData(boolean par2)
		{
			isBaby = false;
			isBaby = par2;
		}

		GroupData(boolean par2, Object par4EntityLesserShoggothINNER1)
		{
			this(par2);
		}
	}
}
