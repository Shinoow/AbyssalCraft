/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2026 Shinoow.
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
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.entity.IEliteEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.base.EntityClimbingMobBase;
import com.shinoow.abyssalcraft.common.entity.projectile.EntityDreadSlug;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityLesserDreadbeast extends EntityClimbingMobBase implements IDreadEntity, IRangedAttackMob, IEliteEntity {

	private EntityAIAttackRanged arrowAttack = new EntityAIAttackRanged(this, 0.4D, 20, 15.0F);
	private EntityAIAttackMelee attackOnCollide = new EntityAIAttackMelee(this, 0.35D, true);

	public EntityLesserDreadbeast(World par1World) {
		super(par1World);
		setSize(1.8F, 1.8F);
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.4D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 200.0D : 100.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 36.0D : 18.0D);
	}

	@Override
	public boolean canBreatheUnderwater()
	{
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity){

		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag)
			if (par1Entity instanceof EntityLivingBase && !EntityUtil.isEntityDread((EntityLivingBase) par1Entity))
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));

		return flag;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return world.getEntitiesWithinAABB(EntityLesserDreadbeast.class, getEntityBoundingBox().grow(32)).size() < 4 ? super.getCanSpawnHere() : false;
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.3F;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return ACSounds.dread_spawn_ambient;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
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
	protected Item getDropItem()
	{
		return ACItems.dreaded_shard_of_abyssalnite;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_LESSER_DREADBEAST;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		if(getAttackTarget() != null)
			if(getDistanceSq(getAttackTarget()) > 15D || getAttackTarget() instanceof EntityFlying || getAttackTarget().posY > posY + 4D){
				tasks.addTask(2, arrowAttack);
				tasks.removeTask(attackOnCollide);
			} else {
				tasks.addTask(2, attackOnCollide);
				tasks.removeTask(arrowAttack);
			}

		if(ticksExisted % 400 == 0)
			if(!world.isRemote && world.getEntitiesWithinAABB(EntityDreadSpawn.class, getEntityBoundingBox().grow(32)).size() < ACConfig.dreadSpawnSpawnLimit){
				EntityDreadSpawn spawn = new EntityDreadSpawn(world);
				spawn.copyLocationAndAnglesFrom(this);
				world.spawnEntity(spawn);
			}
		if(ticksExisted % 10000 == 0)
			if(!world.isRemote && world.getEntitiesWithinAABB(EntityGreaterDreadSpawn.class, getEntityBoundingBox().grow(32)).size() < ACConfig.greaterDreadSpawnSpawnLimit){
				EntityGreaterDreadSpawn spawn = new EntityGreaterDreadSpawn(world);
				spawn.copyLocationAndAnglesFrom(this);
				world.spawnEntity(spawn);
			}
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {

		if(!world.isRemote){
			EntityGreaterDreadSpawn spawn1 = new EntityGreaterDreadSpawn(world);
			EntityGreaterDreadSpawn spawn2 = new EntityGreaterDreadSpawn(world);
			spawn1.copyLocationAndAnglesFrom(this);
			spawn2.copyLocationAndAnglesFrom(this);
			world.spawnEntity(spawn1);
			world.spawnEntity(spawn2);
		}
		super.onDeath(par1DamageSource);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_,
			float p_82196_2_) {
		EntityDreadSlug entitydreadslug = new EntityDreadSlug(world, this);
		double d0 = p_82196_1_.posX - posX;
		double d1 = p_82196_1_.posY + p_82196_1_.getEyeHeight() - 1.100000023841858D - entitydreadslug.posY;
		double d2 = p_82196_1_.posZ - posZ;
		float f1 = MathHelper.sqrt(d0 * d0 + d2 * d2) * 0.2F;
		entitydreadslug.shoot(d0, d1 + f1, d2, 1.6F, 12.0F);
		playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		world.spawnEntity(entitydreadslug);
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {

	}
}
