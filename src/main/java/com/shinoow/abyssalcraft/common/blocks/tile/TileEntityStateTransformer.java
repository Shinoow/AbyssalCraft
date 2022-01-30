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

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.common.blocks.BlockStateTransformer;
import com.shinoow.abyssalcraft.common.items.ItemStoneTablet;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityStateTransformer extends TileEntity implements ITickable, ISidedInventory {

	public int processingTime;
	private NonNullList<ItemStack> containerItemStacks = NonNullList.withSize(50, ItemStack.EMPTY);
	public int mode;
	private int[][] slots = {new int[]{0}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19,
			20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
			47, 48, 49}, new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,23,
					24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49}};
	boolean flag1;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		ItemStackHelper.loadAllItems(nbttagcompound, containerItemStacks);
		processingTime = nbttagcompound.getInteger("ProcessingTime");
		mode = nbttagcompound.getInteger("Mode");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		ItemStackHelper.saveAllItems(nbttagcompound, containerItemStacks);
		nbttagcompound.setInteger("ProcessingTime", processingTime);
		nbttagcompound.setInteger("Mode", mode);

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
	public String getName() {

		return "container.abyssalcraft.statetransformer";
	}

	@Override
	public boolean hasCustomName() {

		return false;
	}

	@Override
	public int getSizeInventory() {

		return containerItemStacks.size();
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemstack : containerItemStacks)
			if (!itemstack.isEmpty())
				return false;

		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return containerItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {

		if(var1 == 0)
			flag1 = true;
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
		if(index == 0)
			flag1 = true;
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

		if(index == 0) return stack.getItem() instanceof ItemStoneTablet;
		return !(stack.getItem() instanceof ItemStoneTablet);
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
		containerItemStacks.clear();
	}

	@Override
	public void update() {

		boolean flag = false;

		if(!world.isRemote)
			if(canDoThing()){
				++processingTime;
				if(processingTime == 200){
					processingTime = 0;
					processItem();
					flag = true;
				}
			} else processingTime = 0;

		if(flag)
			markDirty();

		if(flag1){
			if(!world.isRemote){
				IBlockState state = world.getBlockState(pos);
				boolean b = !containerItemStacks.get(0).isEmpty();

				if(state.getValue(BlockStateTransformer.TABLET).booleanValue() != b){

					TileEntity te = world.getTileEntity(pos);

					world.setBlockState(pos, state.withProperty(BlockStateTransformer.TABLET, b));

					if(te != null){
						te.validate();
						world.setTileEntity(pos, te);
					}
				}
			}
			flag1 = false;
		}
	}

	private void processItem(){

		ItemStack stack = containerItemStacks.get(0);
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		if(mode == 0){

			NBTTagList items = new NBTTagList();
			float pe = 0;
			for (int i = 1; i < getSizeInventory(); ++i)
				if (!getStackInSlot(i).isEmpty())
				{
					NBTTagCompound item = new NBTTagCompound();
					item.setInteger("Slot", i);
					getStackInSlot(i).writeToNBT(item);

					items.appendTag(item);
					ItemStack is = getStackInSlot(i);
					pe += is.getCount() * (64/is.getMaxStackSize());
					removeStackFromSlot(i);
				}
			stack.getTagCompound().setTag("ItemInventory", items);
			stack.getTagCompound().setFloat("PotEnergy", pe);
		} else if(mode == 1){

			NBTTagList items = stack.getTagCompound().getTagList("ItemInventory", Constants.NBT.TAG_COMPOUND);

			for (int i = 0; i < items.tagCount(); ++i)
			{
				NBTTagCompound item = items.getCompoundTagAt(i);
				byte slot = item.getByte("Slot");

				if (slot >= 0 && slot < getSizeInventory())
					setInventorySlotContents(slot, new ItemStack(item));
			}
			stack.getTagCompound().removeTag("ItemInventory");
			stack.getTagCompound().removeTag("PotEnergy");
		}
	}

	private boolean canDoThing(){

		ItemStack stack = containerItemStacks.get(0);

		if(stack.isEmpty()) return false;

		if(mode == 0){

			boolean b = false;

			for(int i = 1; i < containerItemStacks.size(); i++)
				if(!containerItemStacks.get(i).isEmpty())
					b = true;
			if(b)
				return !stack.hasTagCompound() || stack.getTagCompound().isEmpty();
		} else if(mode == 1){

			boolean b = true;

			for(int i = 1; i < containerItemStacks.size(); i++)
				if(!containerItemStacks.get(i).isEmpty())
					b = false;

			return b && ((ItemStoneTablet)stack.getItem()).hasInventory(stack);
		}

		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {

		switch(mode){
		case 0:
			return side == EnumFacing.UP ? slots[2] : slots[0];
		case 1:
			return side == EnumFacing.UP ? slots[0] : side == EnumFacing.DOWN ? slots[2] : new int[0];
		default:
			return new int[0];
		}
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {

		if(processingTime > 0) return false;
		switch(mode){
		case 0:
			return index > 0 && direction == EnumFacing.UP || index == 0 &&
			direction != EnumFacing.UP && direction != EnumFacing.DOWN &&
			itemStackIn.getItem() instanceof ItemStoneTablet &&
			!((ItemStoneTablet)itemStackIn.getItem()).hasInventory(itemStackIn);
		case 1:
			return index == 0 && itemStackIn.getItem() instanceof ItemStoneTablet &&
			((ItemStoneTablet)itemStackIn.getItem()).hasInventory(itemStackIn) &&
			direction != EnumFacing.DOWN;
		default:
			return false;
		}
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {

		if(processingTime > 0) return false;
		switch(mode){
		case 0:
			return index == 0 && stack.getItem() instanceof ItemStoneTablet &&
			((ItemStoneTablet)stack.getItem()).hasInventory(stack) &&
			direction == EnumFacing.DOWN;
		case 1:
			return direction == EnumFacing.DOWN && index == 0 ? stack.getItem() instanceof ItemStoneTablet &&
					!((ItemStoneTablet)stack.getItem()).hasInventory(stack) : true;
		default:
			return false;
		}
	}

	IItemHandler handlerTop = new SidedInvWrapper(this, EnumFacing.UP);
	IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);
	IItemHandler handlerSide = new SidedInvWrapper(this, EnumFacing.WEST);

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
			else if (facing == EnumFacing.UP)
				return (T) handlerTop;
			else
				return (T) handlerSide;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing != null || super.hasCapability(capability, facing);
	}
}
