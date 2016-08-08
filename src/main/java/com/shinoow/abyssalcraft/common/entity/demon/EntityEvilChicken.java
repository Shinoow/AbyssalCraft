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
package com.shinoow.abyssalcraft.common.entity.demon;

import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityEvilChicken extends EntityMob {

	public float field_70886_e;
	public float destPos;
	public float field_70884_g;
	public float field_70888_h;
	public float field_70889_i = 1.0F;

	public EntityEvilChicken(World par1World) {
		super(par1World);
		setSize(0.3F, 0.7F);
		isImmuneToFire = true;
		double var2 = 0.35D;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, var2, true));
		tasks.addTask(3, new EntityAIWander(this, var2));
		tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		} else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
	}

	@Override
	public String getName()
	{
		return I18n.translateToLocal("entity.Chicken.name");
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.entity_chicken_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.entity_ghast_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.entity_chicken_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.entity_chicken_step, 0.15F, 1.0F);
	}

	@Override
	public void fall(float p_70069_1_, float par2) {}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		field_70888_h = field_70886_e;
		field_70884_g = destPos;
		destPos = (float)(destPos + (onGround ? -1 : 4) * 0.3D);

		if (destPos < 0.0F)
			destPos = 0.0F;

		if (destPos > 1.0F)
			destPos = 1.0F;

		if (!onGround && field_70889_i < 1.0F)
			field_70889_i = 1.0F;

		field_70889_i = (float)(field_70889_i * 0.9D);

		if (!onGround && motionY < 0.0D)
			motionY *= 0.6D;

		field_70886_e += field_70889_i * 2.0F;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if(!worldObj.isRemote)
			if(!(par1DamageSource.getEntity() instanceof EntityLesserShoggoth))
			{
				EntityDemonChicken demonchicken = new EntityDemonChicken(worldObj);
				demonchicken.copyLocationAndAnglesFrom(this);
				worldObj.removeEntity(this);
				demonchicken.onInitialSpawn(worldObj.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
				worldObj.spawnEntityInWorld(demonchicken);
			}
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_EVIL_CHICKEN;
	}
}