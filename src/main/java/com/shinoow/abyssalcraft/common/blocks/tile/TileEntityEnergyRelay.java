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
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.Vec3;

import com.shinoow.abyssalcraft.AbyssalCraft;
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
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setFloat("PotEnergy", energy);
	}

	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound nbtTag = new NBTTagCompound();
		writeToNBT(nbtTag);
		return new S35PacketUpdateTileEntity(pos, 1, nbtTag);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet)
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
					Vec3 vec = new Vec3(xp, yp, zp);
					Vec3 t = new Vec3(p.getX(), p.getY(), p.getZ());
					double vx = vec.xCoord > t.xCoord ? vec.xCoord - t.xCoord : vec.xCoord < t.xCoord ? t.xCoord - vec.xCoord : 0;
					double vy = vec.yCoord > t.yCoord ? vec.yCoord - t.yCoord : vec.yCoord < t.yCoord ? t.yCoord - vec.yCoord : 0;
					double vz = vec.zCoord > t.zCoord ? vec.zCoord - t.zCoord : vec.zCoord < t.zCoord ? t.zCoord - vec.zCoord : 0;

					for(int i = 1; i < 11; i++){
						Vec3 v = new Vec3(vec.xCoord > t.xCoord ? vec.xCoord - vx/i : vec.xCoord < t.xCoord ? vec.xCoord + vx/i : t.xCoord,
								vec.yCoord > t.yCoord ? vec.yCoord - vy/i : vec.yCoord < t.yCoord ? vec.yCoord + vy/i : t.yCoord,
										vec.zCoord > t.zCoord ? vec.zCoord - vz/i : vec.zCoord < t.zCoord ? vec.zCoord + vz/i : t.zCoord);
						Vec3 v2 = new Vec3(vec.xCoord > t.xCoord ? t.xCoord + vx/i : vec.xCoord < t.xCoord ? t.xCoord - vx/i : t.xCoord,
								vec.yCoord > t.yCoord ? t.yCoord + vy/i : vec.yCoord < t.yCoord ? t.yCoord - vy/i : t.yCoord,
										vec.zCoord > t.zCoord ? t.zCoord + vz/i : vec.zCoord < t.zCoord ? t.zCoord - vz/i : t.zCoord);
						for(double d = 0; d < 0.1; d += 0.05){
							int x = vec.xCoord > t.xCoord ? -1 : vec.xCoord < t.xCoord ? 1 : 0;
							int y = vec.yCoord > t.yCoord ? -1 : vec.yCoord < t.yCoord ? 1 : 0;
							int z = vec.zCoord > t.zCoord ? -1 : vec.zCoord < t.zCoord ? 1 : 0;
							AbyssalCraft.proxy.spawnParticle("PEStream", worldObj, v.xCoord + 0.5 + x*d, v.yCoord + 0.5 + y*d, v.zCoord + 0.5 + z*d, 0,0,0);
							AbyssalCraft.proxy.spawnParticle("PEStream", worldObj, v2.xCoord + 0.5 + x*d, v2.yCoord + 0.5 + y*d, v2.zCoord + 0.5 + z*d, 0,0,0);
						}
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
		worldObj.markBlockForUpdate(pos);
	}

	@Override
	public float consumeEnergy(float energy) {
		if(energy < this.energy){
			this.energy -= energy;
			worldObj.markBlockForUpdate(pos);
			return energy;
		} else {
			float ret = this.energy;
			this.energy = 0;
			worldObj.markBlockForUpdate(pos);
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
