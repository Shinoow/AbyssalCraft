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
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityEnergyContainer extends TileEntity implements IEnergyContainer, ITickable, IInventory {

	private float energy;
	private NonNullList<ItemStack> containerItemStacks = NonNullList.withSize(2, ItemStack.EMPTY);

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		energy = nbttagcompound.getFloat("PotEnergy");
		containerItemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbttagcompound, containerItemStacks);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setFloat("PotEnergy", energy);
		ItemStackHelper.saveAllItems(nbttagcompound, containerItemStacks);

		return nbttagcompound;
	}

	@Override
	public void onLoad()
	{
		if(world.isRemote)
			world.tickableTileEntities.remove(this);
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

		ItemStack input = getStackInSlot(0);
		if(!input.isEmpty())
			if(input.getItem() instanceof IEnergyContainerItem)
				if(((IEnergyContainerItem) input.getItem()).canTransferPE(input) && canAcceptPE())
					addEnergy(((IEnergyContainerItem) input.getItem()).consumeEnergy(input, 1));
		ItemStack output = getStackInSlot(1);
		if(!output.isEmpty())
			if(output.getItem() instanceof IEnergyContainerItem)
				if(((IEnergyContainerItem) output.getItem()).canAcceptPE(output) && canTransferPE())
					((IEnergyContainerItem) output.getItem()).addEnergy(output, consumeEnergy(1));
	}

	@Override
	public float getContainedEnergy() {

		return energy;
	}

	@Override
	public int getMaxEnergy() {

		return 10000;
	}

	@Override
	public void addEnergy(float energy) {
		this.energy += energy;
		if(this.energy > getMaxEnergy()) this.energy = getMaxEnergy();
	}

	@Override
	public float consumeEnergy(float energy) {
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

	@Override
	public String getName() {

		return "container.abyssalcraft.energycontainer";
	}

	@Override
	public boolean hasCustomName() {

		return false;
	}

	@Override
	public ITextComponent getDisplayName() {

		return new TextComponentTranslation(getName());
	}

	@Override
	public int getSizeInventory() {

		return containerItemStacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return containerItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {

		return ItemStackHelper.getAndSplit(containerItemStacks, var1, var2);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		return ItemStackHelper.getAndRemove(containerItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		containerItemStacks.set(index, stack);

		if(!stack.isEmpty() && stack.getCount() > getInventoryStackLimit())
			stack.setCount(getInventoryStackLimit());
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {

		return world.getTileEntity(pos) != this ? false : player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		return stack.getItem() instanceof IEnergyContainerItem;
	}

	@Override
	public int getField(int id) {

		if(id == 0)
			return (int) energy;
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if(id == 0)
			energy = value;
	}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : containerItemStacks)
			if (!itemstack.isEmpty())
				return false;

		return true;
	}
}
