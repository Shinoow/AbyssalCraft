/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ISingletonInventory;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

import net.minecraft.entity.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntityRendingPedestal extends TileEntity implements IEnergyContainer, ITickable, ISidedInventory, ISingletonInventory {

	private float energy;
	private NonNullList<ItemStack> containerItemStacks = NonNullList.withSize(6, ItemStack.EMPTY);
	int ticksExisted, shadowEnergy, abyssalEnergy, dreadEnergy, omotholEnergy;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		energy = nbttagcompound.getFloat("PotEnergy");
		containerItemStacks = NonNullList.withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbttagcompound, containerItemStacks);

		for(EnergyType type : EnergyType.values())
			setEnergy(type.index, nbttagcompound.getInteger(type.energyName));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setFloat("PotEnergy", energy);
		ItemStackHelper.saveAllItems(nbttagcompound, containerItemStacks);

		for(EnergyType type : EnergyType.values())
			nbttagcompound.setInteger(type.energyName, getEnergy(type.index));

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

		ItemStack input = getStackInSlot(0);
		if(!world.isRemote)
			PEUtils.transferPEToContainer(input, this, 20);

		ItemStack stack = getStackInSlot(1);

		if(!stack.isEmpty())
			if(stack.getItem() instanceof IStaffOfRending)
				if(ticksExisted % 40 == 0 && !world.isRemote){
					IStaffOfRending staff = (IStaffOfRending)stack.getItem();

					for(EnergyType type : EnergyType.values()) // drain staff
						if(staff.getEnergy(stack, type.name) > 0) {
							increaseEnergy(type.index, staff.getEnergy(stack, type.name));
							staff.setEnergy(0, stack, type.name);
						}

					for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(15, 3, 15), EntityLivingBase::isNonBoss))
						if(target.getCreatureAttribute() == AbyssalCraftAPI.SHADOW)
							consumeIfApplicable(target, stack, 0);
						else if(world.provider.getDimension() == ACLib.abyssal_wasteland_id && EntityUtil.isCoraliumPlagueCarrier(target))
							consumeIfApplicable(target, stack, 1);
						else if(world.provider.getDimension() == ACLib.dreadlands_id && EntityUtil.isDreadPlagueCarrier(target))
							consumeIfApplicable(target, stack, 2);
						else if(world.provider.getDimension() == ACLib.omothol_id && target instanceof IOmotholEntity)
							consumeIfApplicable(target, stack, 3);
					for(MultiPartEntityPart target : world.getEntitiesWithinAABB(MultiPartEntityPart.class, new AxisAlignedBB(pos).grow(15, 3, 15), e -> ((Entity) e.parent).isNonBoss()))
						if(target.parent instanceof EntityLiving)
							if(((EntityLiving) target.parent).getCreatureAttribute() == AbyssalCraftAPI.SHADOW)
								consumeIfApplicable(target, stack, 0);
							else if(world.provider.getDimension() == ACLib.abyssal_wasteland_id && EntityUtil.isCoraliumPlagueCarrier((EntityLiving) target.parent))
								consumeIfApplicable(target, stack, 1);
							else if(world.provider.getDimension() == ACLib.dreadlands_id && EntityUtil.isDreadPlagueCarrier((EntityLiving) target.parent))
								consumeIfApplicable(target, stack, 2);
							else if(world.provider.getDimension() == ACLib.omothol_id && target.parent instanceof IOmotholEntity)
								consumeIfApplicable(target, stack, 3);
				}

		for(EnergyType type : EnergyType.values())
			if(getEnergy(type.index) >= type.limit){
				setEnergy(type.index, getEnergy(type.index) - type.limit); // save overflow
				ItemStack output = getStackInSlot(type.slot);
				if(!output.isEmpty() && output.getItem() == type.item)
					output.grow(1);
				else setInventorySlotContents(type.slot, new ItemStack(type.item));
			}
	}

	@Override
	public float getContainedEnergy() {

		return energy;
	}

	@Override
	public int getMaxEnergy() {

		return 5000;
	}

	@Override
	public boolean canTransferPE() {

		return false;
	}

	@Override
	public TileEntity getContainerTile() {

		return this;
	}

	public int getEnergy(int type){
		switch(type){
		case 0:
			return shadowEnergy;
		case 1:
			return abyssalEnergy;
		case 2:
			return dreadEnergy;
		case 3:
			return omotholEnergy;
		default:
			return 0;
		}
	}

	public void increaseEnergy(int type, int amount){
		setEnergy(type, getEnergy(type) + amount);
	}

	public void setEnergy(int type, int amount){
		switch(type){
		case 0:
			shadowEnergy = amount;
			break;
		case 1:
			abyssalEnergy = amount;
			break;
		case 2:
			dreadEnergy = amount;
			break;
		case 3:
			omotholEnergy = amount;
			break;
		}
	}

	public void consumeIfApplicable(EntityLivingBase target, ItemStack stack, int type) {
		IStaffOfRending staff = (IStaffOfRending)stack.getItem();
		if(!target.isDead && getContainedEnergy() >= target.getMaxHealth()/2)
			if(target.attackEntityFrom(DamageSource.GENERIC, staff.getDrainAmount(stack))){
				consumeEnergy(target.getMaxHealth()/2);
				increaseEnergy(type, staff.getDrainAmount(stack));
			}
	}

	public void consumeIfApplicable(MultiPartEntityPart target, ItemStack stack, int type) {
		IStaffOfRending staff = (IStaffOfRending)stack.getItem();
		if(!target.isDead && getContainedEnergy() >= ((EntityLivingBase) target.parent).getMaxHealth()/2)
			if(target.attackEntityFrom(DamageSource.GENERIC, staff.getDrainAmount(stack))){
				consumeEnergy(((EntityLivingBase) target.parent).getMaxHealth()/2);
				increaseEnergy(type, staff.getDrainAmount(stack));
			}
	}

	@Override
	public String getName() {

		return "container.abyssalcraft.rendingpedestal";
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

		if(index == 1)
			world.notifyBlockUpdate(pos, world.getBlockState(pos), world.getBlockState(pos), 2);
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

		switch(index){
		case 0:
			return stack.getItem() instanceof IEnergyContainerItem;
		case 1:
			return stack.getItem() instanceof IStaffOfRending;
		default:
			return false;
		}

	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {

		return side == EnumFacing.DOWN ? new int[]{2, 3, 4, 5} : new int[0];
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {

		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {

		return direction == EnumFacing.DOWN && index > 1;
	}

	@Override
	public int getField(int id) {

		if(id < 4)
			return getEnergy(id);
		if(id == 4)
			return (int)energy;
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		if(id < 4)
			setEnergy(id, value);
		if(id == 4)
			energy = value;
	}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {

	}

	IItemHandler handlerBottom = new SidedInvWrapper(this, EnumFacing.DOWN);

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing)
	{
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing)
	{
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && facing == EnumFacing.DOWN || super.hasCapability(capability, facing);
	}

	@Override
	public ItemStack getItem() {

		return getStackInSlot(1);
	}

	@Override
	public void setItem(ItemStack item) {
		setInventorySlotContents(1, item);
	}

	@Override
	public boolean shouldItemRotate() {
		return false;
	}

	@Override
	public boolean isEmpty() {

		for (ItemStack itemstack : containerItemStacks)
			if (!itemstack.isEmpty())
				return false;

		return true;
	}

	enum EnergyType {

		SHADOW(0, "Shadow", 2, ACItems.shadow_gem, 200),
		ABYSSAL(1, "Abyssal", 3, ACItems.abyssal_wasteland_essence, 100),
		DREAD(2, "Dread", 4, ACItems.dreadlands_essence, 100),
		OMOTHOL(3, "Omothol", 5, ACItems.omothol_essence, 100);

		public final int index, slot, limit;
		public final String name, energyName;
		public final Item item;
		private EnergyType(int index, String name, int slot, Item item, int limit) {
			this.index = index;
			this.name = name;
			energyName = "energy" + name;
			this.slot = slot;
			this.item = item;
			this.limit = limit;
		}
	}

	@Override
	public void setEnergy(float energy) {

		this.energy = energy;
	}
}
