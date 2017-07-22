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
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyTransporter;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.common.blocks.BlockEnergyRelay;

public class TileEntityEnergyRelay extends TileEntity implements IEnergyTransporter, ITickable {

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
			if(world.getBlockState(pos).getProperties().containsKey(BlockEnergyRelay.FACING))
				PEUtils.collectNearbyPE(this, world, pos, world.getBlockState(pos).getValue(BlockEnergyRelay.FACING).getOpposite(), 5);

		if(ticksExisted % 40 == 0 && canTransferPE())
			if(world.getBlockState(pos).getProperties().containsKey(BlockEnergyRelay.FACING))
				transferPE(world.getBlockState(pos).getValue(BlockEnergyRelay.FACING), 10);
	}

	@Override
	public void transferPE(EnumFacing facing, float energy) {

		if(PEUtils.canTransfer(world, pos, facing, 4)){
			IEnergyContainer container = PEUtils.getContainer(world, pos, facing, 4);
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
	}

	@Override
	public float consumeEnergy(float energy) {
		world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
		if(energy < this.energy){
			this.energy -= energy;
			return energy;
		} else {
			float ret = this.energy;
			this.energy = 0;
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
