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
package com.shinoow.abyssalcraft.common.blocks.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyRelay;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.common.blocks.BlockEnergyRelay;

public class TileEntityEnergyRelay extends TileEntity implements IEnergyRelay, ITickable {

	private float energy;
	private int ticksExisted;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		energy = nbttagcompound.getFloat("PotEnergy");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setFloat("PotEnergy", energy);

		return nbttagcompound;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 1, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag()
	{
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet)
	{
		readFromNBT(packet.getNbtCompound());
	}

	@Override
	public void update() {
		++ticksExisted;

		if(ticksExisted % 20 == 0)
			if(worldObj.getBlockState(pos).getProperties().containsKey(BlockEnergyRelay.FACING))
				PEUtils.collectNearbyPE(this, worldObj, pos, worldObj.getBlockState(pos).getValue(BlockEnergyRelay.FACING).getOpposite(), 5);

		if(ticksExisted % 40 == 0 && canTransferPE())
			if(worldObj.getBlockState(pos).getProperties().containsKey(BlockEnergyRelay.FACING))
				transferPE(worldObj.getBlockState(pos).getValue(BlockEnergyRelay.FACING), 10);
	}

	@Override
	public void transferPE(EnumFacing facing, float energy) {

		int xp = pos.getX();
		int yp = pos.getY();
		int zp = pos.getZ();

		if(PEUtils.canTransfer(worldObj, pos, facing, 4)){
			IEnergyContainer container = PEUtils.getContainer(worldObj, pos, facing, 4);
			if(container != null)
				if(container.canAcceptPE()){
					if(!worldObj.isRemote)
						container.addEnergy(consumeEnergy(energy));
					BlockPos p = container.getContainerTile().getPos();
					for(double i = 0; i <= 0.7; i += 0.03) {
						int xPos = xp < p.getX() ? 1 : xp > p.getX() ? -1 : 0;
						int yPos = yp < p.getY() ? 1 : yp > p.getY() ? -1 : 0;
						int zPos = zp < p.getZ() ? 1 : zp > p.getZ() ? -1 : 0;
						double x = i * xPos;
						double y = i * yPos;
						double z = i * zPos;
						worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, xp + 0.5, yp + 0.5, zp + 0.5, x, y, z);
					}
				}
		}
	}

	@Override
	public float getContainedEnergy() {

		return energy;
	}

	@Override
	public int getMaxEnergy() {

		return 500;
	}

	@Override
	public void addEnergy(float energy) {
		this.energy += energy;
		if(this.energy > getMaxEnergy()) this.energy = getMaxEnergy();
		worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
	}

	@Override
	public float consumeEnergy(float energy) {
		if(energy < this.energy){
			this.energy -= energy;
			worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
			return energy;
		} else {
			float ret = this.energy;
			this.energy = 0;
			worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
			return ret;
		}
	}

	@Override
	public boolean canAcceptPE() {

		return getContainedEnergy() < getMaxEnergy();
	}

	@Override
	public boolean canTransferPE() {

		return getContainedEnergy() > 0;
	}

	@Override
	public TileEntity getContainerTile() {

		return this;
	}
}
