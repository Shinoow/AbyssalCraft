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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.blocks.ISingletonInventory;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

public class TileEntityRendingPedestal extends TileEntity implements IEnergyContainer, ITickable, ISidedInventory, ISingletonInventory {

	private float energy;
	private ItemStack[] containerItemStacks = new ItemStack[6];
	int ticksExisted, shadowEnergy, abyssalEnergy, dreadEnergy, omotholEnergy;

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

		shadowEnergy = nbttagcompound.getInteger("energyShadow");
		abyssalEnergy = nbttagcompound.getInteger("energyAbyssal");
		dreadEnergy = nbttagcompound.getInteger("energyDread");
		omotholEnergy = nbttagcompound.getInteger("energyOmothol");
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

		nbttagcompound.setInteger("energyShadow", shadowEnergy);
		nbttagcompound.setInteger("energyAbyssal", abyssalEnergy);
		nbttagcompound.setInteger("energyDread", dreadEnergy);
		nbttagcompound.setInteger("energyOmothol", omotholEnergy);

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
		if(input != null)
			if(input.getItem() instanceof IEnergyContainerItem)
				if(!worldObj.isRemote && ((IEnergyContainerItem) input.getItem()).canTransferPE(input) && canAcceptPE())
					addEnergy(((IEnergyContainerItem) input.getItem()).consumeEnergy(input, 1));

		ItemStack stack = getStackInSlot(1);

		if(stack != null)
			if(stack.getItem() instanceof IStaffOfRending)
				if(ticksExisted % 40 == 0 && !worldObj.isRemote){
					IStaffOfRending staff = (IStaffOfRending)stack.getItem();

					if(staff.getEnergy(stack, "Shadow") > 0){
						increaseEnergy(0, staff.getEnergy(stack, "Shadow"));
						staff.setEnergy(0, stack, "Shadow");
					}
					if(staff.getEnergy(stack, "Abyssal") > 0){
						increaseEnergy(1, staff.getEnergy(stack, "Abyssal"));
						staff.setEnergy(0, stack, "Abyssal");
					}
					if(staff.getEnergy(stack, "Dread") > 0){
						increaseEnergy(2, staff.getEnergy(stack, "Dread"));
						staff.setEnergy(0, stack, "Dread");
					}
					if(staff.getEnergy(stack, "Omothol") > 0){
						increaseEnergy(3, staff.getEnergy(stack, "Omothol"));
						staff.setEnergy(0, stack, "Omothol");
					}

					for(EntityLivingBase target : worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).expand(15, 3, 15)))
						if(target.getCreatureAttribute() == AbyssalCraftAPI.SHADOW && target.isNonBoss()){
							if(!target.isDead && getContainedEnergy() >= target.getMaxHealth()/2)
								if(target.attackEntityFrom(DamageSource.magic, staff.getDrainAmount(stack))){
									consumeEnergy(target.getMaxHealth()/2);
									increaseEnergy(0, staff.getDrainAmount(stack));
								}
						} else if(worldObj.provider.getDimension() == ACLib.abyssal_wasteland_id && target instanceof ICoraliumEntity &&
								target.isNonBoss()){
							if(!target.isDead && getContainedEnergy() >= target.getMaxHealth()/2)
								if(target.attackEntityFrom(DamageSource.magic, staff.getDrainAmount(stack))){
									consumeEnergy(target.getMaxHealth()/2);
									increaseEnergy(1, staff.getDrainAmount(stack));
								}
						} else if(worldObj.provider.getDimension() == ACLib.dreadlands_id && target instanceof IDreadEntity &&
								target.isNonBoss()){
							if(!target.isDead && getContainedEnergy() >= target.getMaxHealth()/2)
								if(target.attackEntityFrom(DamageSource.magic, staff.getDrainAmount(stack))){
									consumeEnergy(target.getMaxHealth()/2);
									increaseEnergy(2, staff.getDrainAmount(stack));
								}
						} else if(worldObj.provider.getDimension() == ACLib.omothol_id && target instanceof ICoraliumEntity
								&& target instanceof IDreadEntity && target instanceof IAntiEntity &&
								target.getCreatureAttribute() != AbyssalCraftAPI.SHADOW && target.isNonBoss())
							if(!target.isDead && getContainedEnergy() >= target.getMaxHealth()/2)
								if(target.attackEntityFrom(DamageSource.magic, staff.getDrainAmount(stack))){
									consumeEnergy(target.getMaxHealth()/2);
									increaseEnergy(3, staff.getDrainAmount(stack));
								}
				}

		if(getEnergy(0) >= 200){
			setEnergy(0, 0);
			ItemStack output = getStackInSlot(2);
			if(output != null && output.getItem() == ACItems.shadow_gem)
				output.stackSize++;
			else setInventorySlotContents(2, new ItemStack(ACItems.shadow_gem));
		}
		if(getEnergy(1) >= 100){
			setEnergy(1, 0);
			ItemStack output = getStackInSlot(3);
			if(output != null && output.getItem() == ACItems.essence && output.getItemDamage() == 0)
				output.stackSize++;
			else setInventorySlotContents(3, new ItemStack(ACItems.essence, 1, 0));
		}
		if(getEnergy(2) >= 100){
			setEnergy(2, 0);
			ItemStack output = getStackInSlot(4);
			if(output != null && output.getItem() == ACItems.essence && output.getItemDamage() == 1)
				output.stackSize++;
			else setInventorySlotContents(4, new ItemStack(ACItems.essence, 1, 1));
		}
		if(getEnergy(3) >= 100){
			setEnergy(3, 0);
			ItemStack output = getStackInSlot(5);
			if(output != null && output.getItem() == ACItems.essence && output.getItemDamage() == 2)
				output.stackSize++;
			else setInventorySlotContents(5, new ItemStack(ACItems.essence, 1, 2));
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
		switch(type){
		case 0:
			shadowEnergy += amount;
			break;
		case 1:
			abyssalEnergy += amount;
			break;
		case 2:
			dreadEnergy += amount;
			break;
		case 3:
			omotholEnergy += amount;
			break;
		}
		worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
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
		worldObj.notifyBlockUpdate(pos, worldObj.getBlockState(pos), worldObj.getBlockState(pos), 2);
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

	net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing)
	{
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
		return super.getCapability(capability, facing);
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
	public int getRotation() {

		return 0;
	}
}
