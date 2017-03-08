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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;

public class EntityGreaterDreadSpawn extends EntityMob implements IDreadEntity, IRangedAttackMob {

	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityGreaterDreadSpawn.class, DataSerializers.BYTE);
	private static boolean hasMerged;

	private EntityAIAttackRanged arrowAttack = new EntityAIAttackRanged(this, 0.6D, 20, 8.0F);
	private EntityAIAttackMelee attackOnCollide = new EntityAIAttackMelee(this, 0.35D, true);

	public EntityGreaterDreadSpawn(World par1World) {
		super(par1World);
		setSize(1.2F, 1.2F);
		tasks.addTask(2, arrowAttack);
		tasks.addTask(3, attackOnCollide);
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.5D));
		tasks.addTask(5, new EntityAIWander(this, 0.5D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(24.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
		}
	}

	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}

	@Override
	protected PathNavigate getNewNavigator(World worldIn)
	{
		return new PathNavigateClimber(this, worldIn);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity){

		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag)
			if (par1Entity instanceof EntityLivingBase)
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 3 * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(CLIMBING, Byte.valueOf((byte)0));
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!worldObj.isRemote)
			setBesideClimbableBlock(isCollidedHorizontally);
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.6F;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return ACSounds.dread_spawn_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return ACSounds.dread_spawn_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ACSounds.dread_spawn_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
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
	public void fall(float par1, float par2) {}

	@Override
	protected Item getDropItem()
	{
		return ACItems.dreaded_shard_of_abyssalnite;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_GREATER_DREAD_SPAWN;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(getAttackTarget() != null && getDistanceToEntity(getAttackTarget()) >= 5)
			if(worldObj.rand.nextInt(1000) == 0)
				attackEntityWithRangedAttack(getAttackTarget(), 4);


		List<EntityGreaterDreadSpawn> greaterspawns = worldObj.getEntitiesWithinAABB(getClass(), getEntityBoundingBox().expand(5D, 5D, 5D));

		if(!worldObj.isRemote)
			if(!greaterspawns.isEmpty())
				if(greaterspawns.size() >= 5 && !hasMerged){
					hasMerged = true;
					for(int i = 0; i < 5; i++)
						worldObj.removeEntity(greaterspawns.get(i));
					EntityLesserDreadbeast lesserdreadbeast = new EntityLesserDreadbeast(worldObj);
					lesserdreadbeast.copyLocationAndAnglesFrom(this);
					worldObj.removeEntity(this);
					worldObj.spawnEntityInWorld(lesserdreadbeast);
					hasMerged = false;
				}

		if(worldObj.rand.nextInt(2000) == 0)
			if(!worldObj.isRemote){
				EntityDreadSpawn spawn = new EntityDreadSpawn(worldObj);
				spawn.copyLocationAndAnglesFrom(this);
				worldObj.spawnEntityInWorld(spawn);
			}
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {

		if(!worldObj.isRemote){
			EntityDreadSpawn spawn1 = new EntityDreadSpawn(worldObj);
			EntityDreadSpawn spawn2 = new EntityDreadSpawn(worldObj);
			spawn1.copyLocationAndAnglesFrom(this);
			spawn2.copyLocationAndAnglesFrom(this);
			worldObj.spawnEntityInWorld(spawn1);
			worldObj.spawnEntityInWorld(spawn2);
		}
		super.onDeath(par1DamageSource);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_,
			float p_82196_2_) {
		EntityDreadSlug entitydreadslug = new EntityDreadSlug(worldObj, this);
		double d0 = p_82196_1_.posX - posX;
		double d1 = p_82196_1_.posY + p_82196_1_.getEyeHeight() - 1.100000023841858D - entitydreadslug.posY;
		double d2 = p_82196_1_.posZ - posZ;
		float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
		entitydreadslug.setThrowableHeading(d0, d1 + f1, d2, 1.6F, 12.0F);
		playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(entitydreadslug);
	}
}
