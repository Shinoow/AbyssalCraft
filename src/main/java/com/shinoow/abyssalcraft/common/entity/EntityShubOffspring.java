/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityShubOffspring extends EntityMob {

	public EntityShubOffspring(World worldIn) {
		super(worldIn);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(3, new EntityAIAttackMelee(this, 0.35D, true));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		setSize(1.0F, 2.9F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 80.0D : 40.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 8.0D : 4.0D);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return rand.nextInt(5) == 0 ? SoundEvents.ENTITY_GHAST_HURT : SoundEvents.ENTITY_SHEEP_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return rand.nextBoolean() ? SoundEvents.ENTITY_GHAST_HURT : SoundEvents.ENTITY_SHEEP_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_SHEEP_DEATH;
	}

	@Override
	protected float getSoundVolume()
	{
		return 3.0F;
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextBoolean() ? (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.5F : (rand.nextFloat() - rand.nextFloat()) * 0.2F + 0.5F;
	}

	@Override
	public float getEyeHeight()
	{
		return height * 0.70F;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_SHUB_OFFSPRING;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean getCanSpawnHere()
	{
		if(!world.isDaytime() && posY >= world.getSeaLevel())
			if(world.getCurrentMoonPhaseFactor() == 0 || rand.nextFloat() + 0.01f > world.getCurrentMoonPhaseFactor())
				return super.getCanSpawnHere();

		return false;
	}
}
