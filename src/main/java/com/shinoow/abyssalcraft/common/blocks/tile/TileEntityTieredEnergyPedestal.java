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

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.lib.util.blocks.ISingletonInventory;

public class TileEntityTieredEnergyPedestal extends TileEntity implements IEnergyContainer, ISingletonInventory, ITickable {

	private ItemStack item;
	private int rot;
	private float energy;
	private boolean isDirty;
	Random rand = new Random();

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagCompound nbtItem = nbttagcompound.getCompoundTag("Item");
		item = ItemStack.loadItemStackFromNBT(nbtItem);
		rot = nbttagcompound.getInteger("Rot");
		energy = nbttagcompound.getFloat("PotEnergy");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		NBTTagCompound nbtItem = new NBTTagCompound();
		if(item != null)
			item.writeToNBT(nbtItem);
		nbttagcompound.setTag("Item", nbtItem);
		nbttagcompound.setInteger("Rot", rot);
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
	public void update()
	{
		if(isDirty){
			worldObj.markBlockForUpdate(pos);
			isDirty = false;
		}

		if(rot == 360)
			rot = 0;
		if(item != null)
			rot++;

		if(worldObj.canBlockSeeSky(pos))
			if(rand.nextInt(40) == 0 && getContainedEnergy() < getMaxEnergy()){
				if(worldObj.isDaytime()){
					addEnergy(1);
					worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 0.95, pos.getZ() + 0.5, 0, 0, 0);
				} else {
					if(worldObj.getCurrentMoonPhaseFactor() == 1)
						addEnergy(3);
					else if(worldObj.getCurrentMoonPhaseFactor() == 0)
						addEnergy(1);
					else addEnergy(2);
					worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.5, pos.getY() + 0.95, pos.getZ() + 0.5, 0, 0, 0);
				}
				if(getContainedEnergy() > getMaxEnergy())
					energy = getMaxEnergy();
			}

		if(item != null)
			if(item.getItem() instanceof IEnergyContainerItem)
				if(((IEnergyContainerItem) item.getItem()).canAcceptPE(item) && getContainedEnergy() > 0 && ((IEnergyContainerItem) item.getItem()).getContainedEnergy(item) < ((IEnergyContainerItem) item.getItem()).getMaxEnergy(item)){
					((IEnergyContainerItem) item.getItem()).addEnergy(item, 1);
					consumeEnergy(1);
				}
	}

	@Override
	public int getRotation(){
		return rot;
	}

	@Override
	public ItemStack getItem(){
		return item;
	}

	@Override
	public void setItem(ItemStack item){
		this.item = item;
		isDirty = true;
	}

	@Override
	public float getContainedEnergy() {
		return energy;
	}

	@Override
	public int getMaxEnergy() {
		int base = 5000;
		switch(getBlockMetadata()){
		case 0:
			return  (int) (base * 1.5);
		case 1:
			return base * 2;
		case 2:
			return (int) (base * 2.5);
		case 3:
			return base * 3;
		default:
			return base;
		}
	}

	@Override
	public void addEnergy(float energy) {
		int multiplier = 1;
		switch(getBlockMetadata()){
		case 0:
			multiplier = 2;
			break;
		case 1:
			multiplier = 4;
			break;
		case 2:
			multiplier = 6;
			break;
		case 3:
			multiplier = 8;
			break;
		}
		this.energy += energy * multiplier;
	}

	@Override
	public void consumeEnergy(float energy) {
		this.energy -= energy;
	}

	@Override
	public boolean canAcceptPE() {
		return true;
	}

	@Override
	public boolean canTransferPE() {
		return true;
	}
}
