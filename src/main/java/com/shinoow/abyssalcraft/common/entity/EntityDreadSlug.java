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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;

public class EntityDreadSlug extends EntityThrowable {

	public EntityDreadSlug(World p_i1776_1_) {
		super(p_i1776_1_);
	}

	public EntityDreadSlug(World p_i1777_1_, EntityLivingBase p_i1777_2_) {
		super(p_i1777_1_, p_i1777_2_);
	}

	public EntityDreadSlug(World p_i1778_1_, double p_i1778_2_,
			double p_i1778_4_, double p_i1778_6_) {
		super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
	}

	@Override
	protected void onImpact(RayTraceResult mop) {

		if (mop.entityHit != null)
		{
			byte b0 = 6;

			if(mop.entityHit instanceof EntityLivingBase && !EntityUtil.isEntityDread((EntityLivingBase) mop.entityHit))
				((EntityLivingBase)mop.entityHit).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));

			mop.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), b0);
		}

		if (!worldObj.isRemote)
			setDead();
	}

}
