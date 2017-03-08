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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;

public class EntityCoraliumSquid extends EntitySquid implements ICoraliumEntity, IRangedAttackMob {

	public EntityCoraliumSquid(World worldIn) {
		super(worldIn);
		tasks.addTask(1, new EntityAIArrowAttack(this, 0.6D, 80, 8.0F));
	}

	@Override
	public void collideWithEntity(Entity entity){
		if(entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)entity))
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague.id, 100));
		super.collideWithEntity(entity);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();

		EntityPlayer player = worldObj.getClosestPlayer(posX, posY, posZ, 7);
		if(player != null && !player.capabilities.isCreativeMode)
			setAttackTarget(player);

		if(getAttackTarget() != null && getAttackTarget() instanceof EntityPlayer && getDistanceToEntity(getAttackTarget()) > 8)
			setAttackTarget(null);
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target,
			float p_82196_2_) {
		EntityInkProjectile entityinkprojectile = new EntityInkProjectile(worldObj, this);
		double d0 = target.posX - posX;
		double d1 = target.posY + target.getEyeHeight() - 1.100000023841858D - entityinkprojectile.posY;
		double d2 = target.posZ - posZ;
		float f1 = MathHelper.sqrt_double(d0 * d0 + d2 * d2) * 0.2F;
		entityinkprojectile.setThrowableHeading(d0, d1 + f1, d2, 1.6F, 12.0F);
		playSound(getSplashSound(), 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(entityinkprojectile);
	}
}
