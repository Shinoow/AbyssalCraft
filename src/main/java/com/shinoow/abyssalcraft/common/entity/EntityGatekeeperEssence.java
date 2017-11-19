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

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.item.ItemExpireEvent;

import com.shinoow.abyssalcraft.api.item.ACItems;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityGatekeeperEssence extends EntityItem {

	public EntityGatekeeperEssence(World worldIn) {
		super(worldIn);
		motionX = motionY = motionZ = 0;
	}

	public EntityGatekeeperEssence(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z, new ItemStack(ACItems.essence_of_the_gatekeeper));
		motionX = motionY = motionZ = 0;
	}

	public EntityGatekeeperEssence(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, new ItemStack(ACItems.essence_of_the_gatekeeper));
		motionX = motionY = motionZ = 0;
	}

	@Override
	public void onUpdate()
	{
		ItemStack stack = getDataWatcher().getWatchableObjectItemStack(10);
		if (stack != null && stack.getItem() != null && stack.getItem().onEntityItemUpdate(this)) return;
		if (getEntityItem() == null)
			setDead();
		else
		{
			onEntityUpdate();

			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			noClip = func_145771_j(posX, (boundingBox.minY + boundingBox.maxY) / 2.0D, posZ);

			if (age != -32768)
				++age;

			handleWaterMovement();

			ItemStack item = getDataWatcher().getWatchableObjectItemStack(10);

			if (!this.worldObj.isRemote && this.age >= lifespan)
			{
				if (item != null)
				{   
					ItemExpireEvent event = new ItemExpireEvent(this, (item.getItem() == null ? 6000 : item.getItem().getEntityLifespan(item, worldObj)));
					if (MinecraftForge.EVENT_BUS.post(event))
					{
						lifespan += event.extraLife;
					}
					else
					{
						this.setDead();
					}
				}
				else
				{
					this.setDead();
				}
			}

			if (item != null && item.stackSize <= 0)
			{
				this.setDead();
			}
		}
	}
}