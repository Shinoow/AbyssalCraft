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

import java.util.List;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;

import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDreadedCharge extends EntityFireball
{
	public EntityDreadedCharge(World worldIn)
	{
		super(worldIn);
		setSize(1.0F, 1.0F);
	}

	@SideOnly(Side.CLIENT)
	public EntityDreadedCharge(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
	{
		super(worldIn, x, y, z, accelX, accelY, accelZ);
		setSize(1.0F, 1.0F);
	}

	public EntityDreadedCharge(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
	{
		super(worldIn, shooter, accelX, accelY, accelZ);
		setSize(1.0F, 1.0F);
	}

	/**
	 * Returns true if other Entities should be prevented from moving through this Entity.
	 */
	@Override
	public boolean canBeCollidedWith()
	{
		return false;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return false;
	}

	@Override
	protected EnumParticleTypes getParticleType()
	{
		return EnumParticleTypes.FLAME;
	}

	@Override
	protected boolean isFireballFiery()
	{
		return false;
	}


	@Override
	protected void onImpact(RayTraceResult movingObject)
	{
		if (ticksExisted > 5)
		{
			if(!world.isRemote)
				for(int x = getPosition().getX() -4; x < getPosition().getX() + 4; x++)
					for(int z = getPosition().getZ() - 4; z < getPosition().getZ() + 4; z++)
						if(!(world.getBiome(new BlockPos(x, 0, z)) instanceof IDreadlandsBiome)
								&& world.getBiome(new BlockPos(x, 0, z)) != ACBiomes.purged)
							BiomeUtil.updateBiome(world, new BlockPos(x, 0, z), ACBiomes.dreadlands);

			if (movingObject.entityHit != null)
			{
				movingObject.entityHit.attackEntityFrom(AbyssalCraftAPI.dread, 4);
				movingObject.entityHit.hurtResistantTime = 0;
			}

			List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(8.0D));
			EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(world, posX, posY, posZ);
			entityareaeffectcloud.addEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 400));
			entityareaeffectcloud.setRadius(2.0F);
			entityareaeffectcloud.setDuration(200 + rand.nextInt(200));
			entityareaeffectcloud.setRadiusPerTick((3F - entityareaeffectcloud.getRadius()) / entityareaeffectcloud.getDuration());

			for (EntityLivingBase entitylivingbase : list)
			{
				double d0 = getDistanceSq(entitylivingbase);

				if (shootingEntity != null && d0 < 64.0D)
					entityareaeffectcloud.setPosition(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ);
			}

			for (int k = 0; k < 200; ++k)
			{
				float f2 = rand.nextFloat() * 4.0F;
				float f3 = rand.nextFloat() * ((float)Math.PI * 2F);
				double d3 = MathHelper.cos(f3) * f2;
				double d4 = 0.01D + rand.nextDouble() * 0.5D;
				double d5 = MathHelper.sin(f3) * f2;
				world.spawnParticle(EnumParticleTypes.FLAME, getPosition().getX() + d3 * 0.1D, getPosition().getY() + 0.3D, getPosition().getZ() + d5 * 0.1D, d3 * f2, d4, d5 * f2, new int[0]);
			}

			world.playSound((EntityPlayer)null, getPosition(), SoundEvents.ENTITY_ENDERDRAGON_FIREBALL_EPLD, SoundCategory.MASTER, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F);

			if (!world.isRemote)
				world.spawnEntity(entityareaeffectcloud);
			world.newExplosion(this, posX, posY + 1.0D, posZ, 3.0F, false, false);
			setDead();
		}
	}
}
