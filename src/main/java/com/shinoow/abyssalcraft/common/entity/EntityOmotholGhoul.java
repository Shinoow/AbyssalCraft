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
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityOmotholGhoul extends EntityMob implements IAntiEntity, ICoraliumEntity, IDreadEntity {

	public EntityOmotholGhoul(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.35D, false));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		setSize(1.3F, 3.7F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(30.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
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
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.moveSlowdown, 100));
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.blindness, 20));
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.nightVision, 20));
			}


		swingArm(EnumHand.MAIN_HAND);
		swingArm(EnumHand.OFF_HAND);
		boolean flag = super.attackEntityAsMob(par1Entity);

		return flag;
	}

	@Override
	protected float getSoundPitch()
	{
		return rand.nextFloat() - rand.nextFloat() * 0.2F + 0.6F;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return AbyssalCraft.ghoul_normal_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return AbyssalCraft.ghoul_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return AbyssalCraft.ghoul_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.entity_zombie_step, 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.omothol_flesh;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_OMOTHOL_GHOUL;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected void updateEquipmentIfNeeded(EntityItem itemEntity)
	{
		if(!AbyssalCraft.isItemBlacklisted(this, itemEntity.getEntityItem()))
			super.updateEquipmentIfNeeded(itemEntity);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

		float f = difficulty.getClampedAdditionalDifficulty();
		setCanPickUpLoot(rand.nextFloat() < 0.55F * f);

		return par1EntityLivingData;
	}
}