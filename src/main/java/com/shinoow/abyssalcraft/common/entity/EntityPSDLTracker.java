/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
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

public class EntityPSDLTracker extends Entity
{
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
		this.setSize(0.25F, 0.25F);
	}

	protected void entityInit() {}

	@SideOnly(Side.CLIENT)

	/**
	 * Checks if the entity is in range to render by using the past in distance and comparing it to its average edge
	 * length * 64 * renderDistanceWeight Args: distance
	 */
	public boolean isInRangeToRenderDist(double par1)
	{
		double d1 = this.boundingBox.getAverageEdgeLength() * 4.0D;
		d1 *= 64.0D;
		return par1 < d1 * d1;
	}

	public EntityPSDLTracker(World par1World, double par2, double par4, double par6)
	{
		super(par1World);
		this.despawnTimer = 0;
		this.setSize(0.25F, 0.25F);
		this.setPosition(par2, par4, par6);
		this.yOffset = 0.0F;
	}

	/**
	 * The location the eye should float/move towards. Currently used for moving towards the nearest stronghold. Args:
	 * strongholdX, strongholdY, strongholdZ
	 */
	public void moveTowards(double par1, int par3, double par4)
	{
		double d2 = par1 - this.posX;
		double d3 = par4 - this.posZ;
		float f = MathHelper.sqrt_double(d2 * d2 + d3 * d3);

		if (f > 12.0F)
		{
			this.targetX = this.posX + d2 / (double)f * 12.0D;
			this.targetZ = this.posZ + d3 / (double)f * 12.0D;
			this.targetY = this.posY + 8.0D;
		}
		else
		{
			this.targetX = par1;
			this.targetY = (double)par3;
			this.targetZ = par4;
		}

		this.despawnTimer = 0;
		this.shatterOrDrop = this.rand.nextInt(5) > 0;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Sets the velocity to the args. Args: x, y, z
	 */
	public void setVelocity(double par1, double par3, double par5)
	{
		this.motionX = par1;
		this.motionY = par3;
		this.motionZ = par5;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
			this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
			this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f) * 180.0D / Math.PI);
		}
	}

	/**
	 * Called to update the entity's position/logic.
	 */
	public void onUpdate()
	{
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		super.onUpdate();
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
		{
			;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
		{
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F)
		{
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
		{
			this.prevRotationYaw += 360.0F;
		}

		this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
		this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;

		if (!this.worldObj.isRemote)
		{
			double d0 = this.targetX - this.posX;
			double d1 = this.targetZ - this.posZ;
			float f1 = (float)Math.sqrt(d0 * d0 + d1 * d1);
			float f2 = (float)Math.atan2(d1, d0);
			double d2 = (double)f + (double)(f1 - f) * 0.0025D;

			if (f1 < 1.0F)
			{
				d2 *= 0.8D;
				this.motionY *= 0.8D;
			}

			this.motionX = Math.cos((double)f2) * d2;
			this.motionZ = Math.sin((double)f2) * d2;

			if (this.posY < this.targetY)
			{
				this.motionY += (1.0D - this.motionY) * 0.014999999664723873D;
			}
			else
			{
				this.motionY += (-1.0D - this.motionY) * 0.014999999664723873D;
			}
		}

		float f3 = 0.25F;

		if (this.isInWater())
		{
			for (int i = 0; i < 4; ++i)
			{
				this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f3, this.posY - this.motionY * (double)f3, this.posZ - this.motionZ * (double)f3, this.motionX, this.motionY, this.motionZ);
			}
		}
		else
		{
			this.worldObj.spawnParticle("smoke", this.posX - this.motionX * (double)f3 + this.rand.nextDouble() * 0.6D - 0.3D, this.posY - this.motionY * (double)f3 - 0.5D, this.posZ - this.motionZ * (double)f3 + this.rand.nextDouble() * 0.6D - 0.3D, this.motionX, this.motionY, this.motionZ);
		}

		if (!this.worldObj.isRemote)
		{
			this.setPosition(this.posX, this.posY, this.posZ);
			++this.despawnTimer;

			if (this.despawnTimer > 80 && !this.worldObj.isRemote)
			{
				this.setDead();

				if (this.shatterOrDrop)
				{
					this.worldObj.spawnEntityInWorld(new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(AbyssalCraft.PSDLfinder)));
				}
				else
				{
					this.worldObj.playAuxSFX(2003, (int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ), 0);
				}
			}
		}
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {}

	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	/**
	 * Gets how bright this entity is.
	 */
	public float getBrightness(float par1)
	{
		return 1.0F;
	}

	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float par1)
	{
		return 15728880;
	}

	/**
	 * If returns false, the item will not inflict any damage against entities.
	 */
	public boolean canAttackWithItem()
	{
		return false;
	}
}
