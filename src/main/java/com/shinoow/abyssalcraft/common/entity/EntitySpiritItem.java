/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.lib.util.SpiritItemUtil;

import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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
		noClip = true;
		setNoGravity(true);
	}

	public EntitySpiritItem(World worldIn, double x, double y, double z) {
		super(worldIn, x, y, z);
		setNoDespawn();
		setInfinitePickupDelay();
		motionX = motionY = motionZ = 0;
		setSize(0.1F, 0.1F);
		noClip = true;
		setNoGravity(true);
	}

	public EntitySpiritItem(World worldIn, double x, double y, double z, ItemStack stack) {
		super(worldIn, x, y, z, stack);
		setNoDespawn();
		setInfinitePickupDelay();
		motionX = motionY = motionZ = 0;
		setSize(0.1F, 0.1F);
		noClip = true;
		setNoGravity(true);
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
		collided = collidedHorizontally = collidedVertically = false;
		if(world.isRemote) return;
		if(pathIndex > route.length - 1)
			pathIndex = route.length - 1;
		target = route[pathIndex];
		double d0 = posX - target.getX();
		double d1 = posY - target.getY();
		double d2 = posZ - target.getZ();
		double d = d0 * d0 + d1 * d1 + d2 * d2; // more precise distanceSq
		if(MathHelper.floor(d) <= 1D) { // makes the item "enter" at a closer proximity
			//we're here?
			if(pathIndex == route.length - 1 && getPosition().equals(target)) {
				//journey is complete!
				TileEntity te = world.getTileEntity(target);
				if(te != null) {
					IItemHandler inventory = SpiritItemUtil.getInventory(te, facing);
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
					} else { // block doesn't have an inventory, drop item
						entityDropItem(getItem().copy(), 1);
						setDead();
					}
				} else { // block doesn't exist (or doesn't have a TE), drop item
					entityDropItem(getItem().copy(), 1);
					setDead();
				}
			} else if(pathIndex < route.length - 1) {
				pathIndex++;
				dX = dY = dZ = 0;
			}
		} else {// try to align the position to the middle of a block while moving

			if(target.getX() != getPosition().getX()) {
				if(target.getX() + 0.5D > posX)
					dX = 0.1;
				else if(target.getX() + 0.5D < posX)
					dX = -0.1;
			} else {
				posX = getPosition().getX() + 0.5D;
				dX = 0;
			}
			if(target.getY() != getPosition().getY()) {

				if(target.getY() + 0.3D > posY)
					dY = 0.05;
				else if(target.getY() + 0.3D < posY)
					dY = -0.05;
			} else {
				posY = getPosition().getY() + 0.2D;
				dY = 0;
			}
			if(target.getZ() != getPosition().getZ()) {
				if(target.getZ() + 0.5D > posZ)
					dZ = 0.1;
				else if(target.getZ() + 0.5D < posZ)
					dZ = -0.1;
			} else {
				posZ = getPosition().getZ() + 0.5D;
				dZ = 0;
			}

			motionX = dX;
			if(target.getY() > posY || target.getY() < posY)
				motionY = dY;
			motionZ = dZ;
		}

		// preemptive self-destruction if target isn't valid
		BlockPos pos = route[route.length - 1];
		if(!SpiritItemUtil.validPos(pos, world, facing)) {
			entityDropItem(getItem().copy(), 1);
			setDead();
		}
	}

	@Override
	public void move(MoverType type, double x, double y, double z)
	{
		setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
		resetPositionToBB();
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
