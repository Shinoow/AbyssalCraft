/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.tileentity;

import com.shinoow.abyssalcraft.api.energy.IIdol;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

/**
 * Simple base class for Idols. Does all the boring stuff<br>
 * (so you don't have to)
 * @author shinoow
 *
 */
public abstract class TileEntityIdolBase extends TileEntity implements ITickable, IIdol {

	private int cooldown;
	private float energy;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		cooldown = nbttagcompound.getInteger("Cooldown");
		energy = nbttagcompound.getFloat("PotEnergy");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Cooldown", cooldown);
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
	public void onLoad()
	{
		if(world.isRemote)
			world.tickableTileEntities.remove(this);
	}

	@Override
	public void update() {
		if(isValidState()){
			cooldown++;
			if (cooldown >= getMaxCooldown() && getContainedEnergy() >= getPEPerActivation()) {
				cooldown = 0;
				activate();
			}
		}
	}

	public int getCooldown(){
		return cooldown;
	}

	public void setCooldown(int cd){
		cooldown = cd;
	}

	@Override
	public float getContainedEnergy() {

		return energy;
	}

	@Override
	public int getMaxEnergy() {

		return 1000;
	}

	@Override
	public TileEntity getContainerTile() {

		return this;
	}

	@Override
	public void setEnergy(float energy) {

		this.energy = energy;
	}

	/**
	 * Returns if the conditions for the cooldown to count down are met
	 */
	protected abstract boolean isValidState();

	/**
	 * Returns max cooldown
	 */
	protected abstract int getMaxCooldown();

	/**
	 * Returns PE required per activation
	 */
	protected abstract float getPEPerActivation();

	/**
	 * Makes the idol do the thing
	 */
	protected abstract void activate();
}
