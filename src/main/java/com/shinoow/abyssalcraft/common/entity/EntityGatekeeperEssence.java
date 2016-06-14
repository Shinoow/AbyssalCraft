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
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.base.Optional;
import com.shinoow.abyssalcraft.api.item.ACItems;

public class EntityGatekeeperEssence extends EntityItem {

	private int age;
	private static final DataParameter<Optional<ItemStack>> ITEM = EntityDataManager.<Optional<ItemStack>>createKey(EntityItem.class, DataSerializers.OPTIONAL_ITEM_STACK);

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
	protected void entityInit()
	{
		getDataManager().register(ITEM, Optional.<ItemStack>absent());
	}

	@Override
	public void onUpdate()
	{
		ItemStack stack = getDataManager().get(ITEM).orNull();
		if (stack != null && stack.getItem() != null && stack.getItem().onEntityItemUpdate(this)) return;
		if (getEntityItem() == null)
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

			ItemStack item = getDataManager().get(ITEM).orNull();

			if (!worldObj.isRemote && age >= lifespan)
			{
				int hook = net.minecraftforge.event.ForgeEventFactory.onItemExpire(this, item);
				if (hook < 0) setDead();
				else          lifespan += hook;
			}
			if (item != null && item.stackSize <= 0)
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

		ItemStack item = getDataManager().get(ITEM).orNull();
		if (item == null || item.stackSize <= 0) setDead();
	}

	@Override
	public void setEntityItemStack(ItemStack stack)
	{
		getDataManager().set(ITEM, Optional.fromNullable(stack));
		getDataManager().setDirty(ITEM);
	}

	@Override
	public ItemStack getEntityItem()
	{
		ItemStack itemstack = (ItemStack)((Optional)getDataManager().get(ITEM)).orNull();

		if (itemstack == null)
			return new ItemStack(Blocks.STONE);
		else
			return itemstack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getAge()
	{
		return age;
	}
}
