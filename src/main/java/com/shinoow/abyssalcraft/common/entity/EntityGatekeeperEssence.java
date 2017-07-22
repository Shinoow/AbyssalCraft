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

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.item.ACItems;

public class EntityGatekeeperEssence extends EntityItem {

	private int age;
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
		ItemStack stack = getItem();
		if (!stack.isEmpty() && stack.getItem().onEntityItemUpdate(this)) return;
		if (getItem().isEmpty())
			setDead();
		else
		{
			onEntityUpdate();

			prevPosX = posX;
			prevPosY = posY;
			prevPosZ = posZ;
			noClip = pushOutOfBlocks(posX, (getEntityBoundingBox().minY + getEntityBoundingBox().maxY) / 2.0D, posZ);

			if (age != -32768)
				++age;

			handleWaterMovement();

			ItemStack item = getItem();

			if (!world.isRemote && age >= lifespan)
			{
				int hook = net.minecraftforge.event.ForgeEventFactory.onItemExpire(this, item);
				if (hook < 0) setDead();
				else          lifespan += hook;
			}
			if (item != null && item.getCount() <= 0)
				setDead();
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		tagCompound.setShort("Age", (short)age);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);
		age = tagCompound.getShort("Age");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getAge()
	{
		return age;
	}
}
