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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityInkProjectile extends EntityThrowable {

	public EntityInkProjectile(World worldIn) {
		super(worldIn);

	}

	public EntityInkProjectile(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
	}

	public EntityInkProjectile(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
	}

	@Override
	public void onUpdate()
	{
		if(ACConfig.particleEntity)
			for(int i = 0; i < 4; i++){
				world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, posX - motionX * 0.25F + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * 0.25F - 0.5D, posZ - motionZ * 0.25F + rand.nextDouble() * 0.6D - 0.3D, motionX, motionY, motionZ, new int[0]);
				world.spawnParticle(EnumParticleTypes.WATER_SPLASH, posX - motionX * 0.25F + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * 0.25F - 0.5D, posZ - motionZ * 0.25F + rand.nextDouble() * 0.6D - 0.3D, motionX, motionY, motionZ, new int[0]);
				world.spawnParticle(EnumParticleTypes.SPELL_MOB, posX - motionX * 0.25F + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * 0.25F - 0.5D, posZ - motionZ * 0.25F + rand.nextDouble() * 0.6D - 0.3D, 0, 0, 0, new int[0]);
			}
		super.onUpdate();
	}

	@Override
	protected void onImpact(RayTraceResult result) {

		if (result.entityHit != null)
		{
			byte b0 = 2;

			if(result.entityHit instanceof EntityLivingBase && !world.isRemote){
				if(rand.nextBoolean() && !EntityUtil.isEntityCoralium((EntityLivingBase) result.entityHit))
					((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100));
				if(rand.nextInt(4) == 0)
					((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100));
				if(rand.nextInt(5) == 0)
					((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 100));
			}

			if(!(result.entityHit instanceof EntityCoraliumSquid))
				result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()), b0);

			if(ACConfig.hardcoreMode && result.entityHit instanceof EntityPlayer)
				result.entityHit.attackEntityFrom(DamageSource.causeThrownDamage(this, getThrower()).setDamageBypassesArmor().setDamageIsAbsolute(), 1F);
		}

		if (!world.isRemote)
			setDead();
	}
}
