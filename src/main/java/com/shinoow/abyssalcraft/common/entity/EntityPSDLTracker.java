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

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityPSDLTracker extends Entity {

	/** 'x' location the eye should float towards. */
	private double targetX;

	/** 'y' location the eye should float towards. */
	private double targetY;

	/** 'z' location the eye should float towards. */
	private double targetZ;
	private int despawnTimer;
	private boolean shatterOrDrop;

	public EntityPSDLTracker(World par1World)
	{
		super(par1World);
		setSize(0.25F, 0.25F);
	}

	@Override
	protected void entityInit() {}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double par1)
	{
		double d1 = boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return par1 < d1 * d1;
	}

	public EntityPSDLTracker(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		despawnTimer = 0;
		setSize(0.25F, 0.25F);
		setPosition(par2, par4, par6);
		yOffset = 0.0F;
	}

	/**
	 * The location the eye should float/move towards. Currently used for moving towards the nearest stronghold. Args:
	 * strongholdX, strongholdY, strongholdZ
	 */
	public void moveTowards(double par1, int par3, double par4)
	{
		double d2 = par1 - posX;
		double d3 = par4 - posZ;
		float f = MathHelper.sqrt_double(d2 * d2 + d3 * d3);

		if (f > 12.0F)
		{
			targetX = posX + d2 / f * 12.0D;
			targetZ = posZ + d3 / f * 12.0D;
			targetY = posY + 8.0D;
		}
		else
		{
			targetX = par1;
			targetY = par3;
			targetZ = par4;
		}

		despawnTimer = 0;
		shatterOrDrop = rand.nextInt(5) > 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double par1, double par3, double par5)
	{
		motionX = par1;
		motionY = par3;
		motionZ = par5;

		if (prevRotationPitch == 0.0F && prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			prevRotationYaw = rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
			prevRotationPitch = rotationPitch = (float)(Math.atan2(par3, f) * 180.0D / Math.PI);
		}
	}

	@Override
	public void onUpdate()
	{
		lastTickPosX = posX;
		lastTickPosY = posY;
		lastTickPosZ = posZ;
		super.onUpdate();
		posX += motionX;
		posY += motionY;
		posZ += motionZ;
		float f = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
		rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / Math.PI);

		for (rotationPitch = (float)(Math.atan2(motionY, f) * 180.0D / Math.PI); rotationPitch - prevRotationPitch < -180.0F; prevRotationPitch -= 360.0F)
			;

		while (rotationPitch - prevRotationPitch >= 180.0F)
			prevRotationPitch += 360.0F;

		while (rotationYaw - prevRotationYaw < -180.0F)
			prevRotationYaw -= 360.0F;

		while (rotationYaw - prevRotationYaw >= 180.0F)
			prevRotationYaw += 360.0F;

		rotationPitch = prevRotationPitch + (rotationPitch - prevRotationPitch) * 0.2F;
		rotationYaw = prevRotationYaw + (rotationYaw - prevRotationYaw) * 0.2F;

		if (!worldObj.isRemote)
		{
			double d0 = targetX - posX;
			double d1 = targetZ - posZ;
			float f1 = (float)Math.sqrt(d0 * d0 + d1 * d1);
			float f2 = (float)Math.atan2(d1, d0);
			double d2 = f + (f1 - f) * 0.0025D;

			if (f1 < 1.0F)
			{
				d2 *= 0.8D;
				motionY *= 0.8D;
			}

			motionX = Math.cos(f2) * d2;
			motionZ = Math.sin(f2) * d2;

			if (posY < targetY)
				motionY += (1.0D - motionY) * 0.014999999664723873D;
			else
				motionY += (-1.0D - motionY) * 0.014999999664723873D;
		}

		float f3 = 0.25F;

		if (isInWater()){
			for (int i = 0; i < 4; ++i)
				if(AbyssalCraft.particleEntity)
					worldObj.spawnParticle("bubble", posX - motionX * f3, posY - motionY * f3, posZ - motionZ * f3, motionX, motionY, motionZ);
		} else
			if(AbyssalCraft.particleEntity)
				worldObj.spawnParticle("smoke", posX - motionX * f3 + rand.nextDouble() * 0.6D - 0.3D, posY - motionY * f3 - 0.5D, posZ - motionZ * f3 + rand.nextDouble() * 0.6D - 0.3D, motionX, motionY, motionZ);

		if (!worldObj.isRemote)
		{
			setPosition(posX, posY, posZ);
			++despawnTimer;

			if (despawnTimer > 80 && !worldObj.isRemote)
			{
				setDead();

				if (shatterOrDrop)
					worldObj.spawnEntityInWorld(new EntityItem(worldObj, posX, posY, posZ, new ItemStack(AbyssalCraft.PSDLfinder)));
				else
					worldObj.playAuxSFX(2003, (int)Math.round(posX), (int)Math.round(posY), (int)Math.round(posZ), 0);
			}
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	@Override
	public float getBrightness(float par1)
	{
		return 1.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1)
	{
		return 15728880;
	}

	@Override
	public boolean canAttackWithItem()
	{
		return false;
	}
}
