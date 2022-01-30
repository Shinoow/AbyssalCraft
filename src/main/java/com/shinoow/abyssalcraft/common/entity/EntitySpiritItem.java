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

import java.util.*;

import com.shinoow.abyssalcraft.common.handlers.ItemTransferEventHandler;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

public class EntitySpiritItem extends EntityItem {

	private BlockPos[] route;
	private EnumFacing facing;
	private int pathIndex;
	private BlockPos target;
	private double dX, dY, dZ;

	public EntitySpiritItem(World worldIn) {
		super(worldIn);
		setNoDespawn();
		setInfinitePickupDelay();
		motionX = motionY = motionZ = 0;
		setSize(0.1F, 0.1F);
	}

	public EntitySpiritItem(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
		setNoDespawn();
		setInfinitePickupDelay();
		motionX = motionY = motionZ = 0;
		setSize(0.1F, 0.1F);
	}

	public EntitySpiritItem(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
		setNoDespawn();
		setInfinitePickupDelay();
		motionX = motionY = motionZ = 0;
		setSize(0.1F, 0.1F);
	}

	public EntitySpiritItem setRoute(BlockPos[] route) {
		this.route = route;
		return this;
	}

	public EntitySpiritItem setFacing(EnumFacing facing) {
		this.facing = facing;
		return this;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		noClip = true;
		setNoGravity(true);
		collided = collidedHorizontally = collidedVertically = false;
		if(world.isRemote) return;
		target = route[pathIndex];
		if(getPosition().distanceSq(target) < 1.0D) {
			//we're here?
			if(pathIndex == route.length - 1) {
				//journey is complete!
				TileEntity te = world.getTileEntity(target);
				if(te != null) {
					IItemHandler inventory = ItemTransferEventHandler.getInventory(te, facing);
					if(inventory != null) {
						ItemStack res = ItemStack.EMPTY;
						ItemStack stack = getItem().copy();
						if(stack.getMetadata() != 0 && !stack.getHasSubtypes())
							res = ItemHandlerHelper.insertItem(inventory, stack, false);
						else
							res = ItemHandlerHelper.insertItemStacked(inventory, stack, false);
						if(res.isEmpty())
							setDead();
						else {
							entityDropItem(res, 1);//drop it on the ground because we failed?
							setDead();
						}
					}
				}
			} else {
				pathIndex++;
				dX = dY = dZ = 0;
			}
		} else {
			dX = target.getX() + 0.5D > posX ? 0.1 : -0.1;
			dY = target.getY() + 0.3D > posY ? 0.1 : -0.1;
			dZ = target.getZ() + 0.5D > posZ ? 0.1 : -0.1;

			move(MoverType.SELF, dX, dY, dZ);
		}
	}

	@Override
	protected boolean pushOutOfBlocks(double x, double y, double z)
	{
		return false;
	}

	@Override
	protected void doBlockCollisions()
	{
		//NO-OP
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound tagCompound)
	{
		super.writeEntityToNBT(tagCompound);
		NBTTagList list = new NBTTagList();
		Arrays.stream(route).map(b -> new NBTTagLong(b.toLong())).forEach(list::appendTag);
		tagCompound.setTag("route", list);
		tagCompound.setInteger("pathIndex", pathIndex);
		if(facing != null)
			tagCompound.setInteger("Facing", facing.getIndex());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound tagCompound)
	{
		super.readEntityFromNBT(tagCompound);
		NBTTagList list = tagCompound.getTagList("route", NBT.TAG_LONG);
		List<BlockPos> positions = new ArrayList<>();
		for(Iterator<NBTBase> i = list.iterator(); i.hasNext();)
			positions.add(BlockPos.fromLong(((NBTTagLong)i.next()).getLong()));
		route = positions.toArray(new BlockPos[0]);
		pathIndex = tagCompound.getInteger("pathIndex");
		if(tagCompound.hasKey("Facing"))
			facing = EnumFacing.byIndex(tagCompound.getInteger("Facing"));
	}
}
