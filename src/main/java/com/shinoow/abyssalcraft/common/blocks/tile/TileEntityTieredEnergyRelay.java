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
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.common.blocks.BlockEnergyRelay;

public class TileEntityTieredEnergyRelay extends TileEntityEnergyRelay {

	private int facing;
	private int ticksExisted;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		facing = nbttagcompound.getInteger("Facing");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Facing", facing);

		return nbttagcompound;
	}

	@Override
	public void update() {
		++ticksExisted;

		if(ticksExisted % 20 == 0)
			PEUtils.collectNearbyPE(this, world, pos, EnumFacing.getFront(facing).getOpposite(), getDrainQuanta());

		if(ticksExisted % 40 == 0 && canTransferPE())
			transferPE(EnumFacing.getFront(facing), getTransferQuanta());
	}

	@Override
	public void transferPE(EnumFacing facing, float energy) {

		if(PEUtils.canTransfer(world, pos, facing, getRange())){
			IEnergyContainer container = PEUtils.getContainer(world, pos, facing, getRange());
			if(container != null)
				if(container.canAcceptPE()){
					if(!world.isRemote)
						container.addEnergy(consumeEnergy(energy));
					BlockPos p = container.getContainerTile().getPos();

					Vec3d vec = new Vec3d(p.subtract(pos)).normalize();

					double d = Math.sqrt(p.distanceSq(pos));

					for(int i = 0; i < d * 15; i++){
						double i1 = i / 15D;
						double xp = pos.getX() + vec.x * i1 + .5;
						double yp = pos.getY() + vec.y * i1 + .5;
						double zp = pos.getZ() + vec.z * i1 + .5;
						AbyssalCraft.proxy.spawnParticle("PEStream", world, xp, yp, zp, vec.x * .1, .15, vec.z * .1);
					}
				}
		}
	}

	@Override
	public TileEntity getContainerTile() {

		return this;
	}

	protected int getRange(){
		switch(getBlockMetadata()){
		case 0:
			return 6;
		case 1:
			return 8;
		case 2:
			return 10;
		case 3:
			return 12;
		default:
			return 0;
		}
	}

	protected float getDrainQuanta(){
		switch(getBlockMetadata()){
		case 0:
			return 10;
		case 1:
			return 15;
		case 2:
			return 20;
		case 3:
			return 25;
		default:
			return 0;
		}
	}

	protected float getTransferQuanta(){
		switch(getBlockMetadata()){
		case 0:
			return 15;
		case 1:
			return 20;
		case 2:
			return 25;
		case 3:
			return 30;
		default:
			return 0;
		}
	}

	public int getFacing(){
		return facing;
	}

	public void setFacing(int face){
		facing = face;
	}
}
