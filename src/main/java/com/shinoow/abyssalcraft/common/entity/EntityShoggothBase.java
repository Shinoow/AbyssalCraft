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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IEliteEntity;
import com.shinoow.abyssalcraft.api.entity.IShoggothEntity;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothOoze;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAIShoggothAttackMelee;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAIShoggothBuildMonolith;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAIWorship;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonPig;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityOmotholGhoul;
import com.shinoow.abyssalcraft.common.handlers.AbyssalCraftEventHooks;
import com.shinoow.abyssalcraft.common.items.armor.ItemEthaxiumArmor;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.ParticleUtil;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFood;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityShoggothBase extends EntityMob implements IShoggothEntity, IEntityMultiPart, IEliteEntity {

	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityShoggothBase.class, DataSerializers.BYTE);
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityShoggothBase.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FOOD = EntityDataManager.createKey(EntityShoggothBase.class, DataSerializers.VARINT);

	private int monolithTimer;
	public boolean isBuilding, isAssisting;

	public MultiPartEntityPart[] shoggothParts;

	public MultiPartEntityPart shoggothHead, shoggothBody;

	public EntityShoggothBase(World worldIn) {
		super(worldIn);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIShoggothAttackMelee(this, 0.35D, true));
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
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityOmotholGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityRemnant.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityLesserShoggoth.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityShoggoth.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGreaterShoggoth.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGatekeeperMinion.class, 8.0F));
		tasks.addTask(8, new EntityAIShoggothBuildMonolith(this));
		tasks.addTask(9, new EntityAIWorship(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 20, true, false, entity -> EntityUtil.isShoggothFood((EntityLivingBase) entity)));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, 10, true, false, entity -> !(entity instanceof EntityZombieVillager)));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, true, false));

		shoggothParts = new MultiPartEntityPart[] {shoggothHead = new MultiPartEntityPart(this, "head", 1.0F, 1.0F), shoggothBody = new MultiPartEntityPart(this, "body", 1.0F, 1.0F)};
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);

	}

	@Override
	protected PathNavigate createNavigator(World worldIn)
	{
		return new PathNavigateClimber(this, worldIn);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(TYPE, 0);
		dataManager.register(FOOD, 0);
		dataManager.register(CLIMBING, (byte)0);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!world.isRemote)
			setBesideClimbableBlock(collidedHorizontally);
	}

	public int getShoggothType() {

		return dataManager.get(TYPE);
	}

	public void setShoggothType(int par1) {

		dataManager.set(TYPE, par1);
	}

	public void setFoodLevel(int par1){

		dataManager.set(FOOD, par1);
	}

	public int getFoodLevel(){

		return dataManager.get(FOOD);
	}

	public void feed(EntityLivingBase entity){
		int food = getFoodLevel() + getPointsFromSize(entity.height * entity.width);
		dataManager.set(FOOD, food);
		playSound(ACSounds.shoggoth_consume, getSoundVolume(), getSoundPitch());
	}

	private int getPointsFromSize(float size) {

		int food = 0;
		for(float sizeNum = size; sizeNum > 0; sizeNum-=0.25)
			food++;
		return food;
	}

	@Override
	public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
	}

	//TODO move this to an actual AI class, or data object?

	/**
	 * Reduces this Shoggoth's monolith timer
	 */
	public void reduceMonolithTimer(){
		monolithTimer = Math.max(monolithTimer - 200, 0);
	}

	public void resetMonolithTimer() {
		monolithTimer = 0;
	}

	public int getMonolithTimer() {
		return monolithTimer;
	}

	@Override
	public boolean isPushedByWater()
	{
		return false;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(isInWater()) {
			motionX += getLookVec().x * 0.005;
			motionY += getLookVec().y * 0.005;
			motionZ += getLookVec().z * 0.005;
		}

		monolithTimer++;

		if(getFoodLevel() >= 8 && !world.isRemote && ticksExisted % 20 == 0){
			setFoodLevel(getFoodLevel() - 8);
			performFoodAction();
		}

		if(!world.isRemote)
			for (int l = 0; l < 1; ++l)
			{
				int x = MathHelper.floor(posX + (l % 2 * 2 - 1) * 0.25F);
				int y = MathHelper.floor(posY);
				int z = MathHelper.floor(posZ + (l / 2 % 2 * 2 - 1) * 0.25F);

				spawnOoze(x, y, z);
				if(isBig()){
					spawnOoze(x - 1, y, z);
					spawnOoze(x, y, z - 1);
					spawnOoze(x - 1, y, z - 1);
				}
			}

		if(ticksExisted % 40 == 0 && ACConfig.consumeItems && !world.isRemote)
			for(EntityItem entity : world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox())) {
				if(entity.getItem().getItem() instanceof ItemFood) {
					int food = getFoodLevel() + entity.getItem().getCount();
					setFoodLevel(food);
					playSound(ACSounds.shoggoth_consume, getSoundVolume(), getSoundPitch());
				} else playSound(SoundEvents.ENTITY_ITEM_BREAK, getSoundVolume(), 1.0F);
				world.removeEntity(entity);
			}

		if(getShoggothType() == 4)
			ParticleUtil.spawnShadowParticles(this);
	}

	/**
	 * Checks if a Lesser Shoggoth can generate ooze at the specific coordinates
	 * @param x X-coord
	 * @param y Y-coord
	 * @param z Z-coord
	 */
	protected void spawnOoze(int x, int y, int z){
		BlockPos pos = new BlockPos(x, y, z);
		if(ACConfig.shoggothOoze)
			if((world.getBlockState(pos).getMaterial() == Material.AIR || world.getBlockState(pos).getBlock().isReplaceable(world, pos)) && ACBlocks.shoggoth_ooze.canPlaceBlockAt(world, pos)
					&& world.getBlockState(pos).getBlock() != ACBlocks.shoggoth_ooze && !world.getBlockState(pos).getMaterial().isLiquid())
				world.setBlockState(pos, ACBlocks.shoggoth_ooze.getDefaultState());
			else if(world.getBlockState(pos).getBlock() == ACBlocks.shoggoth_ooze && world.getBlockState(pos).getValue(BlockShoggothOoze.LAYERS) < 8
					&& ticksExisted % 10 == 0 && rand.nextInt(5) == 0){
				IBlockState state = world.getBlockState(pos);
				world.setBlockState(pos, state.withProperty(BlockShoggothOoze.LAYERS, state.getValue(BlockShoggothOoze.LAYERS) + 1));
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

		dataManager.set(CLIMBING, b0);
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
		if(par1DamageSource.isProjectile() && !ACConfig.no_projectile_damage_immunity){
			playSound(SoundEvents.ENTITY_SLIME_JUMP, getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			return false;
		}
		if(par1DamageSource == DamageSource.IN_WALL)
			if(world.getBlockState(getPosition())  != ACBlocks.monolith_stone && world.getGameRules().getBoolean("mobGriefing"))
				sprayAcid(true);
			else return false;
		if(par1DamageSource == DamageSource.CACTUS) return false;
		
		if(AbyssalCraftEventHooks.isRadiationDamage(par1DamageSource))
			return false;

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
	protected SoundEvent getHurtSound(DamageSource source)
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
		playSound(ACSounds.shoggoth_step, 0.15F, 1.0F);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return getShoggothType() == 4 ? AbyssalCraftAPI.SHADOW : EnumCreatureAttribute.UNDEFINED;
	}

	public void sprayAcidAt(Entity target) {
		EntityAcidProjectile acidprojectile = new EntityAcidProjectile(world, this);
		double d0 = target.posX - posX;
		double d1 = target.posY + target.getEyeHeight() - 1.100000023841858D - acidprojectile.posY;
		double d2 = target.posZ - posZ;
		float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
		acidprojectile.shoot(d0, d1 + f1, d2, 1.0F, 12.0F);
		playSound(ACSounds.shoggoth_shoot, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		world.spawnEntity(acidprojectile);
	}

	public void sprayAcid(boolean above) {
		AxisAlignedBB aabb = getEntityBoundingBox().grow(1, 0, 1);
		world.setEntityState(this, (byte)23);
		for(EntityLivingBase entity : world.getEntitiesWithinAABB(EntityLivingBase.class, aabb, e -> !(e instanceof EntityLesserShoggoth)))
			if(!EntityUtil.canEntityBlockDamageSource(DamageSource.causeMobDamage(this), entity) || !ACConfig.shieldsBlockAcid) {
				if(entity.attackEntityFrom(AbyssalCraftAPI.acid, (float)(4.5D - getDistance(entity))))
					for(ItemStack armor : entity.getArmorInventoryList())
						if(!(armor.getItem() instanceof ItemEthaxiumArmor))
							armor.damageItem(getArmorAcidDamage(), entity);
			} else if(EntityUtil.damageShield(entity, getShieldAcidDamage()))
				entity.attackEntityFrom(AbyssalCraftAPI.acid, 1);
		for(int i = (int)aabb.minX; i < aabb.maxX+1; i++)
			for(int j = (int)aabb.minZ; j < aabb.maxZ+1; j++)
				for(int k = 0; above ? k < 2 : k > -2; k=above ? k + 1 : k - 1){
					BlockPos pos = new BlockPos(i, above ? aabb.maxY : aabb.minY , j);
					if(!world.isAirBlock(pos) && world.getBlockState(pos).getBlockHardness(world, pos) < ACConfig.acidResistanceHardness
							&& world.getBlockState(pos) != ACBlocks.monolith_stone
							&& world.getBlockState(pos).getBlock() != ACBlocks.shoggoth_biomass && !world.getBlockState(pos).getBlock().hasTileEntity(world.getBlockState(pos))
							&& world.getBlockState(pos).getBlockHardness(world, pos) != -1 && world.getBlockState(pos).getBlock().canEntityDestroy(world.getBlockState(pos), world, pos, this))
						world.destroyBlock(pos, false);
				}
	}

	protected void addAcidParticles()
	{
		if (world.isRemote)
		{
			AxisAlignedBB aabb = getEntityBoundingBox();
			Vec3d vector = getLookVec();

			for(int i = (int)aabb.minX; i < aabb.maxX+1; i++)
				for(int j = (int)aabb.minY; j < aabb.maxY+1; j++)
					for(int k = (int)aabb.minZ; k < aabb.maxZ+1; k++){
						double dx = vector.x;
						double dy = vector.y;
						double dz = vector.z;

						double spread = 5.0D + getRNG().nextDouble() * 2.5D;
						double velocity = 0.5D + getRNG().nextDouble() * 0.5D;

						dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
						dy += getRNG().nextGaussian() * 0.007499999832361937D * spread;
						dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
						dx *= velocity;
						dy *= velocity;
						dz *= velocity;

						world.spawnParticle(EnumParticleTypes.SLIME, i, j, k, dx, dy, dz);
					}
		} else
			world.setEntityState(this, (byte)23);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 23) addAcidParticles();
		else
			super.handleStatusUpdate(id);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.hasKey("ShoggothType"))
		{
			int var2 = par1NBTTagCompound.getInteger("ShoggothType");
			setShoggothType(var2);
		}

		if(par1NBTTagCompound.hasKey("FoodLevel")){
			int var2 = par1NBTTagCompound.getInteger("FoodLevel");
			setFoodLevel(var2);
		}

		monolithTimer = par1NBTTagCompound.getInteger("MonolithTimer");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setInteger("ShoggothType", getShoggothType());

		par1NBTTagCompound.setInteger("FoodLevel", getFoodLevel());

		par1NBTTagCompound.setInteger("MonolithTimer", monolithTimer);
	}

	@Override
	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);

		if(EntityUtil.isShoggothFood(par1EntityLivingBase))
			feed(par1EntityLivingBase);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onInitialSpawn(difficulty, par1EntityLivingData);

		setShoggothType(0);

		if(world.provider.getDimension() == ACLib.abyssal_wasteland_id)
			setShoggothType(1);
		if(world.provider.getDimension() == ACLib.dreadlands_id)
			setShoggothType(2);
		if(world.provider.getDimension() == ACLib.omothol_id)
			setShoggothType(3);
		if(world.provider.getDimension() == ACLib.dark_realm_id)
			setShoggothType(4);

		return (IEntityLivingData)data;
	}

	@Override
	public World getWorld() {

		return world;
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage) {

		return attackEntityFrom(source, damage);
	}

	@Override
	public Entity[] getParts()
	{
		return shoggothParts;
	}

	/**
	 * Do something when the food bar reaches its max value<br>
	 * (eg. when the Shoggoth has eaten enough)
	 */
	protected abstract void performFoodAction();

	/**
	 * A pseudo-isChild(), used when spawning ooze, and in the attack AI
	 */
	public abstract boolean isBig();

	/**
	 * Returns the amount of damage acid spraying deals to armor
	 */
	protected abstract int getArmorAcidDamage();

	/**
	 * Returns the amount of damage acid spraying deals to shields<br>
	 * (default is armor acid damage + 50%)
	 */
	protected int getShieldAcidDamage() {
		return (int) (getArmorAcidDamage() * 1.5);
	}
}
