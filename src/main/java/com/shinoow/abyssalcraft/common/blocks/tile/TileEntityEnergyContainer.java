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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;

public class TileEntityEnergyContainer extends TileEntity implements IEnergyContainer, ITickable, IInventory {

	private float energy;
	private ItemStack[] containerItemStacks = new ItemStack[2];

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		energy = nbttagcompound.getFloat("PotEnergy");
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		containerItemStacks = new ItemStack[getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < containerItemStacks.length)
				containerItemStacks[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setFloat("PotEnergy", energy);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < containerItemStacks.length; ++i)
			if (containerItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				containerItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}

		nbttagcompound.setTag("Items", nbttaglist);

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

		ItemStack input = getStackInSlot(0);
		if(input != null)
			if(input.getItem() instanceof IEnergyContainerItem)
				if(!worldObj.isRemote && ((IEnergyContainerItem) input.getItem()).canTransferPE(input) && canAcceptPE())
					addEnergy(((IEnergyContainerItem) input.getItem()).consumeEnergy(input, 1));
		ItemStack output = getStackInSlot(1);
		if(output != null)
			if(output.getItem() instanceof IEnergyContainerItem)
				if(!worldObj.isRemote && ((IEnergyContainerItem) output.getItem()).canAcceptPE(output) && canTransferPE())
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

		return containerItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return containerItemStacks[index];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {

		if(containerItemStacks[var1] != null){
			ItemStack itemstack;
			if(containerItemStacks[var1].stackSize <= var2){
				itemstack = containerItemStacks[var1];
				containerItemStacks[var1] = null;
				return itemstack;
			} else {
				itemstack = containerItemStacks[var1].splitStack(var2);
				if(containerItemStacks[var1].stackSize == 0)
					containerItemStacks[var1] = null;

				return itemstack;
			}
		}
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		if(containerItemStacks[index] != null){
			ItemStack itemstack = containerItemStacks[index];
			containerItemStacks[index] = null;
			return itemstack;
		} else
			return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		containerItemStacks[index] = stack;

		if(stack != null && stack.stackSize > getInventoryStackLimit())
			stack.stackSize = getInventoryStackLimit();
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {

		return worldObj.getTileEntity(pos) != this ? false : player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
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

		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {

	}
}
