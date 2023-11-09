/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.PEUtils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;

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
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		getWorld().markBlockRangeForRenderUpdate(getPos(), getPos());
		super.onDataPacket(net, packet);
	}

	@Override
	public void onLoad()
	{
		if(world.isRemote)
			world.tickableTileEntities.remove(this);
		ticksExisted = world.rand.nextInt(100);
	}

	@Override
	public void update() {
		if(world.isBlockPowered(pos))
			return;

		++ticksExisted;

		if(ticksExisted % 20 == 0)
			PEUtils.collectNearbyPE(this, world, pos, EnumFacing.byIndex(facing).getOpposite(), getDrainQuanta());

		if(ticksExisted % 40 == 0 && canTransferPE())
			transferPE(EnumFacing.byIndex(facing), getTransferQuanta());
	}

	@Override
	public void transferPE(EnumFacing facing, float energy) {

		if(PEUtils.canTransfer(world, pos, facing, getRange())){
			IEnergyContainer container = PEUtils.getContainer(world, pos, facing, getRange());
			if(container != null)
				if(container.canAcceptPE()){
					container.addEnergy(consumeEnergy(energy));
					AbyssalCraftAPI.getInternalMethodHandler().spawnPEStream(pos, container.getContainerTile().getPos(), world.provider.getDimension());
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
			return 15;
		case 1:
			return 25;
		case 2:
			return 35;
		case 3:
			return 45;
		default:
			return 0;
		}
	}

	protected float getTransferQuanta(){
		switch(getBlockMetadata()){
		case 0:
			return 20;
		case 1:
			return 30;
		case 2:
			return 40;
		case 3:
			return 50;
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
