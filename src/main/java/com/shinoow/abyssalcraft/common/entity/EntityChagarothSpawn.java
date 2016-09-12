/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityChagarothSpawn extends EntityMob implements IDreadEntity {

	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityDreadSpawn.class, DataSerializers.BYTE);

	public EntityChagarothSpawn(World par1World)
	{
		super(par1World);
		setSize(0.6F, 0.6F);
		tasks.addTask(1, new EntityAIAttackMelee(this, 0.35D, true));
		tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(3, new EntityAIWander(this, 0.35D));
		tasks.addTask(4, new EntityAILookIdle(this));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.1D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.45D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(16.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
		}
	}

	@Override
	protected PathNavigate getNewNavigator(World worldIn)
	{
		return new PathNavigateClimber(this, worldIn);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity){

		if (super.attackEntityAsMob(par1Entity))
			if (par1Entity instanceof EntityLivingBase)
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.register(CLIMBING, Byte.valueOf((byte)0));
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
		return AbyssalCraft.dread_spawn_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return AbyssalCraft.dread_spawn_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return AbyssalCraft.dread_spawn_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.entity_zombie_step, 0.15F, 1.0F);
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
		return (dataWatcher.get(CLIMBING) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
	public void setBesideClimbableBlock(boolean par1)
	{
		byte b0 = dataWatcher.get(CLIMBING);

		if (par1)
			b0 = (byte)(b0 | 1);
		else
			b0 &= -2;

		dataWatcher.set(CLIMBING, Byte.valueOf(b0));
	}

	@Override
	public void fall(float par1, float par2) {}

	@Override
	protected Item getDropItem()
	{
		return ACItems.dread_fragment;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_SPAWN_OF_CHAGAROTH;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
}
