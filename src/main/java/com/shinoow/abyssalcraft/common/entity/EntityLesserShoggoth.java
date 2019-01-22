/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.BlockACStone;
import com.shinoow.abyssalcraft.common.blocks.BlockACStone.EnumStoneType;
import com.shinoow.abyssalcraft.common.blocks.BlockShoggothOoze;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAILesserShoggothAttackMelee;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAILesserShoggothBuildMonolith;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAIWorship;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonPig;
import com.shinoow.abyssalcraft.common.items.armor.ItemEthaxiumArmor;
import com.shinoow.abyssalcraft.lib.*;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
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
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Interface(iface = "com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues", modid = "iceandfire")
public class EntityLesserShoggoth extends EntityMob implements IOmotholEntity, IEntityMultiPart, com.github.alexthe666.iceandfire.entity.IBlacklistedFromStatues {

	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityLesserShoggoth.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> CHILD = EntityDataManager.createKey(EntityLesserShoggoth.class, DataSerializers.BYTE);
	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityLesserShoggoth.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> FOOD = EntityDataManager.createKey(EntityLesserShoggoth.class, DataSerializers.VARINT);
	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.3D, 1);

	private int monolithTimer;
	private float shoggothWidth = -1.0F;
	private float shoggothHeight;
	public boolean isBuilding, isAssisting;
	public MultiPartEntityPart[] shoggothParts;

	public MultiPartEntityPart shoggothHead, shoggothBody;

	public EntityLesserShoggoth(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAILesserShoggothAttackMelee(this, 0.35D, true));
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
		tasks.addTask(8, new EntityAILesserShoggothBuildMonolith(this));
		tasks.addTask(9, new EntityAIWorship(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 20, true, false, entity -> EntityUtil.isShoggothFood((EntityLivingBase) entity)));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 10, true, false, null));
		setSize(1.8F, 2.6F);

		shoggothParts = new MultiPartEntityPart[] {shoggothHead = new MultiPartEntityPart(this, "head", 1.0F, 1.0F), shoggothBody = new MultiPartEntityPart(this, "body", 1.0F, 1.0F)};
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 200.0D : 100.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 16.0D : 8.0D);

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

		if (!world.isRemote)
			setBesideClimbableBlock(collidedHorizontally);
	}

	@Override
	public boolean isChild()
	{
		return dataManager.get(CHILD).byteValue() == 1;
	}

	public void setChild(boolean par1)
	{
		dataManager.set(CHILD, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (world != null && !world.isRemote)
		{
			IAttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			attributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1) {
				attributeinstance.applyModifier(babySpeedBoostModifier);
				getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 100.0D : 50.0D);
				getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 8.0D : 4.0D);
			} else {
				getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 200.0D : 100.0D);
				getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 16.0D : 8.0D);
				setHealth(ACConfig.hardcoreMode ? 200.0F : 100.0F);
			}
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

	public void feed(EntityLivingBase entity){
		int food = getFoodLevel() + getPointsFromSize(entity.height * entity.width);
		dataManager.set(FOOD, Integer.valueOf(food));
		playSound(SoundEvents.ENTITY_PLAYER_BURP, getSoundVolume(), getSoundPitch());
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

	/**
	 * Reduces this Shoggoth's monolith timer
	 */
	public void reduceMonolithTimer(){
		if(monolithTimer - 200 >= 200)
			monolithTimer -= 200;
		else monolithTimer = 0;
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

		if(world.isRemote)
			setChildSize(isChild());

		if(!isChild())
			monolithTimer++;

		if(getFoodLevel() >= 8 && !world.isRemote && ticksExisted % 20 == 0){
			setFoodLevel(getFoodLevel() - 8);
			if(!isChild() && !world.isRemote){
				EntityLesserShoggoth shoggoth = new EntityLesserShoggoth(world);
				shoggoth.copyLocationAndAnglesFrom(this);
				shoggoth.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY, posZ)),(IEntityLivingData)null);
				shoggoth.setChild(true);
				shoggoth.setShoggothType(getShoggothType());
				world.spawnEntity(shoggoth);
				playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
				if(getAttackTarget() != null && getAttackTarget().isEntityAlive() && getRNG().nextInt(3) == 0) {
					EntityLivingBase target = getAttackTarget();
					EntityAcidProjectile acidprojectile = new EntityAcidProjectile(world, this);
					double d0 = target.posX - posX;
					double d1 = target.posY + target.getEyeHeight() - 1.100000023841858D - acidprojectile.posY;
					double d2 = target.posZ - posZ;
					float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
					acidprojectile.shoot(d0, d1 + f1, d2, 0.8F, 8.0F);
					shoggoth.startRiding(acidprojectile);
					playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
					world.spawnEntity(acidprojectile);
				}
			}
			else setChild(false);
		}

		if(!world.isRemote)
			for (int l = 0; l < 1; ++l)
			{
				int x = MathHelper.floor(posX + (l % 2 * 2 - 1) * 0.25F);
				int y = MathHelper.floor(posY);
				int z = MathHelper.floor(posZ + (l / 2 % 2 * 2 - 1) * 0.25F);

				spawnOoze(x, y, z);
				if(!isChild()){
					spawnOoze(x - 1, y, z);
					spawnOoze(x, y, z - 1);
					spawnOoze(x - 1, y, z - 1);
				}
			}

		if(ticksExisted % 40 == 0 && ACConfig.consumeItems && !world.isRemote)
			for(EntityItem entity : world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox())) {
				if(entity.getItem().getItem() instanceof ItemFood) {
					int food = getFoodLevel() + entity.getItem().getCount();
					dataManager.set(FOOD, Integer.valueOf(food));
					playSound(SoundEvents.ENTITY_PLAYER_BURP, getSoundVolume(), getSoundPitch());
				} else playSound(SoundEvents.ENTITY_ITEM_BREAK, getSoundVolume(), 1.0F);
				world.removeEntity(entity);
			}

		for (int i = 0; i < 2 * getBrightness() && getShoggothType() == 4 && ACConfig.particleEntity && world.provider.getDimension() != ACLib.dark_realm_id; ++i)
			world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height, posZ + (rand.nextDouble() - 0.5D) * width, 0.0D, 0.0D, 0.0D);

		double a = Math.toRadians(rotationYaw);
		double offsetx = -Math.sin(a);
		double offsetz = Math.cos(a);

		if(!isChild()) {
			//sizes
			shoggothBody.height = 2.2F;
			shoggothBody.width = 1.8F;

			shoggothHead.width = 1.6F;
			shoggothHead.height = 1.2F;

			//rotations
			shoggothBody.onUpdate();
			shoggothBody.setLocationAndAngles(posX - offsetx * 0.1F, posY, posZ - offsetz * 0.1F, 0.0F, 0.0F);

			shoggothHead.onUpdate();
			shoggothHead.setLocationAndAngles(posX - offsetx * -0.5d, posY + 1.5f, posZ - offsetz * -0.5d, 0.0F, 0.0F);
		} else {
			//sizes
			shoggothBody.height = 1.1F;
			shoggothBody.width = 0.9F;

			shoggothHead.width = 0.8F;
			shoggothHead.height = 0.6F;

			//rotations
			shoggothBody.onUpdate();
			shoggothBody.setLocationAndAngles(posX - offsetx * 0.1F, posY, posZ - offsetz * 0.1F, 0.0F, 0.0F);

			shoggothHead.onUpdate();
			shoggothHead.setLocationAndAngles(posX - offsetx * -0.25d, posY + 0.75f, posZ - offsetz * -0.25d, 0.0F, 0.0F);
		}
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
		if(par1DamageSource == DamageSource.IN_WALL)
			if(world.getBlockState(getPosition())  != ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.MONOLITH_STONE) && world.getGameRules().getBoolean("mobGriefing"))
				sprayAcid(true);

		return false;
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

	public void sprayAcidAt(Entity target) {
		EntityAcidProjectile acidprojectile = new EntityAcidProjectile(world, this);
		double d0 = target.posX - posX;
		double d1 = target.posY + target.getEyeHeight() - 1.100000023841858D - acidprojectile.posY;
		double d2 = target.posZ - posZ;
		float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
		acidprojectile.shoot(d0, d1 + f1, d2, 1.0F, 12.0F);
		playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
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
							armor.damageItem(isChild() ? 4 : 8, entity);
			} else if(EntityUtil.damageShield(entity, isChild() ? 6 : 12))
				entity.attackEntityFrom(AbyssalCraftAPI.acid, 1);
		for(int i = (int)aabb.minX; i < aabb.maxX+1; i++)
			for(int j = (int)aabb.minZ; j < aabb.maxZ+1; j++)
				for(int k = 0; above ? k < 2 : k > -2; k=above ? k + 1 : k - 1){
					BlockPos pos = new BlockPos(i, above ? aabb.maxY : aabb.minY , j);
					if(!world.isAirBlock(pos) && world.getBlockState(pos).getBlockHardness(world, pos) < ACConfig.acidResistanceHardness
							&& world.getBlockState(pos) != ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.MONOLITH_STONE)
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

		if (data == null)
			data = new EntityLesserShoggoth.GroupData(world.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);

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

	@Override
	public World getWorld() {

		return world;
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage) {
		if(source.isProjectile()){
			playSound(SoundEvents.ENTITY_SLIME_JUMP, getSoundVolume(), ((rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
			return false;
		}

		if(source == DamageSource.CACTUS) return false;
		return super.attackEntityFrom(source, damage);
	}

	@Override
	public Entity[] getParts()
	{
		return shoggothParts;
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

	@Override
	public boolean canBeTurnedToStone() {

		return false;
	}
}
