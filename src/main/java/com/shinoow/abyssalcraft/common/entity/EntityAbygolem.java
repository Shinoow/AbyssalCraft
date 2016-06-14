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
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.item.ACItems;

public class EntityAbygolem extends EntityMob {

	public EntityAbygolem(World par1World) {
		super(par1World);
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityDreadgolem.class, 0.35D, true));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, false));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(5, new EntityAIWander(this, 0.35D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityDreadgolem.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
		} else getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
	}

	@Override
	protected String getLivingSound()
	{
		return "abyssalcraft:golem.idle";
	}

	@Override
	protected String getHurtSound()
	{
		return "abyssalcraft:golem.hit";
	}

	@Override
	protected String getDeathSound()
	{
		return "abyssalcraft:golem.death";
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		if(par1DamageSource.getEntity() instanceof EntityPlayer)
			dropItem(ACItems.chunk_of_abyssalnite, worldObj.rand.nextInt(3));
		super.onDeath(par1DamageSource);
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
	}
}
