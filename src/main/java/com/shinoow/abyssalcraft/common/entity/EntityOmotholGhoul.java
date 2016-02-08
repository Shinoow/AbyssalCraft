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
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.*;

public class EntityOmotholGhoul extends EntityMob implements IAntiEntity, ICoraliumEntity, IDreadEntity {

	public EntityOmotholGhoul(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, false));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		setSize(1.6F, 4.0F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.2D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(30.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(150.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(15.0D);
		}
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if(super.attackEntityAsMob(par1Entity))
			if(par1Entity instanceof EntityLivingBase){
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 100));
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.blindness.id, 20));
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(Potion.nightVision.id, 20));
			}


		swingItem();
		boolean flag = super.attackEntityAsMob(par1Entity);

		return flag;
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.6F;
	}

	@Override
	protected String getLivingSound()
	{
		return "abyssalcraft:ghoul.normal.idle";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "abyssalcraft:ghoul.death";
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return AbyssalCraft.omotholFlesh;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
}
