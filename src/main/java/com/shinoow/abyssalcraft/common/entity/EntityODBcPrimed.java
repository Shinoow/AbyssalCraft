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

import net.minecraft.entity.*;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.client.lib.ParticleEffects;
import com.shinoow.abyssalcraft.common.util.ExplosionUtil;

import cpw.mods.fml.relauncher.*;

public class EntityODBcPrimed extends Entity {

	/** How long the fuse is */
	public int fuse;
	private EntityLivingBase odbcPlacedBy;

	public EntityODBcPrimed(World par1World)
	{
		super(par1World);
		fuse = 0;
		preventEntitySpawning = true;
		setSize(0.98F, 0.98F);
		yOffset = height / 2.0F;
	}

	public EntityODBcPrimed(World par1World, double par2, double par4, double par6, EntityLivingBase par8EntityLivingBase)
	{
		this(par1World);
		setPosition(par2, par4, par6);
		float var8 = (float)(Math.random() * Math.PI * 2.0D);
		motionX = -((float)Math.sin(var8)) * 0.02F;
		motionY = 0.20000000298023224D;
		motionZ = -((float)Math.cos(var8)) * 0.02F;
		fuse = 40;
		prevPosX = par2;
		prevPosY = par4;
		prevPosZ = par6;
		odbcPlacedBy = par8EntityLivingBase;
	}

	@Override
	protected void entityInit() {}

	@Override
	protected boolean canTriggerWalking()
	{
		return true;
	}

	@Override
	public boolean canBeCollidedWith()
	{
		return !isDead;
	}

	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;
		motionY -= 0.03999999910593033D;
		moveEntity(motionX, motionY, motionZ);
		motionX *= 0.9800000190734863D;
		motionY *= 0.9800000190734863D;
		motionZ *= 0.9800000190734863D;

		if (onGround)
		{
			motionX *= 0.699999988079071D;
			motionZ *= 0.699999988079071D;
			motionY *= -0.5D;
		}

		if (fuse-- <= 0)
		{
			setDead();

			if (!worldObj.isRemote)
				explode();
		} else if(worldObj.isRemote)
			ParticleEffects.spawnParticle("CorBlood", posX + (rand.nextDouble() - 0.5D) * width, posY + rand.nextDouble() * height - 0.25D, posZ + (rand.nextDouble() - 0.5D) * width, (rand.nextDouble() - 0.5D) * 100.0D, -rand.nextDouble(), (rand.nextDouble() - 0.5D) * 100.0D);
	}

	private void explode()
	{
		Blocks.obsidian.setResistance(5.0F);
		Blocks.lava.setResistance(5.0F);
		Blocks.flowing_lava.setResistance(5.0F);
		Blocks.water.setResistance(5.0F);
		Blocks.flowing_water.setResistance(5.0F);
		AbyssalCraft.Cwater.setResistance(50.0F);
		float var0 = 20.0F;
		ExplosionUtil.newODBExplosion(worldObj, this, posX, posY, posZ, var0, 32, false, true);
		Blocks.obsidian.setResistance(2000.0F);
		Blocks.lava.setResistance(500.0F);
		Blocks.flowing_lava.setResistance(500.0F);
		Blocks.water.setResistance(500.0F);
		Blocks.flowing_water.setResistance(500.0F);
		AbyssalCraft.Cwater.setResistance(500.0F);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		par1NBTTagCompound.setByte("Fuse", (byte)fuse);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		fuse = par1NBTTagCompound.getByte("Fuse");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getShadowSize()
	{
		return 0.0F;
	}

	/**
	 * returns null or the entityliving it was placed or ignited by
	 */
	public EntityLivingBase getODBCPlacedBy()
	{
		return odbcPlacedBy;
	}
}