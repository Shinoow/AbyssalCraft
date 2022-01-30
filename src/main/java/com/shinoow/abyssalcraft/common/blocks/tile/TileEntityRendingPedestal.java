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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.blocks.ISingletonInventory;
import com.shinoow.abyssalcraft.lib.util.items.IStaffOfRending;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
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
		ItemStackHelper.saveAllItems(nbttagcompound, containerItemStacks);

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
		if(!input.isEmpty())
			if(input.getItem() instanceof IEnergyContainerItem)
				if(!world.isRemote && ((IEnergyContainerItem) input.getItem()).canTransferPE(input) && canAcceptPE())
					addEnergy(((IEnergyContainerItem) input.getItem()).consumeEnergy(input, 1));

		ItemStack stack = getStackInSlot(1);

		if(!stack.isEmpty())
			if(stack.getItem() instanceof IStaffOfRending)
				if(ticksExisted % 40 == 0 && !world.isRemote){
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

					for(EntityLivingBase target : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(15, 3, 15)))
						if(target.getCreatureAttribute() == AbyssalCraftAPI.SHADOW && target.isNonBoss()){
							if(!target.isDead && getContainedEnergy() >= target.getMaxHealth()/2)
								if(target.attackEntityFrom(DamageSource.MAGIC, staff.getDrainAmount(stack))){
									consumeEnergy(target.getMaxHealth()/2);
									increaseEnergy(0, staff.getDrainAmount(stack));
								}
						} else if(world.provider.getDimension() == ACLib.abyssal_wasteland_id && EntityUtil.isCoraliumPlagueCarrier(target) &&
								target.isNonBoss()){
							if(!target.isDead && getContainedEnergy() >= target.getMaxHealth()/2)
								if(target.attackEntityFrom(DamageSource.MAGIC, staff.getDrainAmount(stack))){
									consumeEnergy(target.getMaxHealth()/2);
									increaseEnergy(1, staff.getDrainAmount(stack));
								}
						} else if(world.provider.getDimension() == ACLib.dreadlands_id && EntityUtil.isDreadPlagueCarrier(target) &&
								target.isNonBoss()){
							if(!target.isDead && getContainedEnergy() >= target.getMaxHealth()/2)
								if(target.attackEntityFrom(DamageSource.MAGIC, staff.getDrainAmount(stack))){
									consumeEnergy(target.getMaxHealth()/2);
									increaseEnergy(2, staff.getDrainAmount(stack));
								}
						} else if(world.provider.getDimension() == ACLib.omothol_id && target instanceof IOmotholEntity &&
								target.getCreatureAttribute() != AbyssalCraftAPI.SHADOW && target.isNonBoss())
							if(!target.isDead && getContainedEnergy() >= target.getMaxHealth()/2)
								if(target.attackEntityFrom(DamageSource.MAGIC, staff.getDrainAmount(stack))){
									consumeEnergy(target.getMaxHealth()/2);
									increaseEnergy(3, staff.getDrainAmount(stack));
								}
					for(MultiPartEntityPart target : world.getEntitiesWithinAABB(MultiPartEntityPart.class, new AxisAlignedBB(pos).grow(15, 3, 15)))
						if(target.parent instanceof EntityLiving)
							if(((EntityLiving) target.parent).getCreatureAttribute() == AbyssalCraftAPI.SHADOW && target.isNonBoss()){
								if(!target.isDead && getContainedEnergy() >= ((EntityLiving) target.parent).getMaxHealth()/2)
									if(target.attackEntityFrom(DamageSource.MAGIC, staff.getDrainAmount(stack))){
										consumeEnergy(((EntityLiving) target.parent).getMaxHealth()/2);
										increaseEnergy(0, staff.getDrainAmount(stack));
									}
							} else if(world.provider.getDimension() == ACLib.abyssal_wasteland_id && EntityUtil.isCoraliumPlagueCarrier((EntityLiving) target.parent) &&
									target.isNonBoss()){
								if(!target.isDead && getContainedEnergy() >= ((EntityLiving) target.parent).getMaxHealth()/2)
									if(target.attackEntityFrom(DamageSource.MAGIC, staff.getDrainAmount(stack))){
										consumeEnergy(((EntityLiving) target.parent).getMaxHealth()/2);
										increaseEnergy(1, staff.getDrainAmount(stack));
									}
							} else if(world.provider.getDimension() == ACLib.dreadlands_id && EntityUtil.isDreadPlagueCarrier((EntityLiving) target.parent) &&
									target.isNonBoss()){
								if(!target.isDead && getContainedEnergy() >= ((EntityLiving) target.parent).getMaxHealth()/2)
									if(target.attackEntityFrom(DamageSource.MAGIC, staff.getDrainAmount(stack))){
										consumeEnergy(((EntityLiving) target.parent).getMaxHealth()/2);
										increaseEnergy(2, staff.getDrainAmount(stack));
									}
							} else if(world.provider.getDimension() == ACLib.omothol_id && target.parent instanceof IOmotholEntity &&
									((EntityLiving) target.parent).getCreatureAttribute() != AbyssalCraftAPI.SHADOW && target.isNonBoss())
								if(!target.isDead && getContainedEnergy() >= ((EntityLiving) target.parent).getMaxHealth()/2)
									if(target.attackEntityFrom(DamageSource.MAGIC, staff.getDrainAmount(stack))){
										consumeEnergy(((EntityLiving) target.parent).getMaxHealth()/2);
										increaseEnergy(3, staff.getDrainAmount(stack));
									}
				}

		if(getEnergy(0) >= 200){
			setEnergy(0, 0);
			ItemStack output = getStackInSlot(2);
			if(!output.isEmpty() && output.getItem() == ACItems.shadow_gem)
				output.grow(1);
			else setInventorySlotContents(2, new ItemStack(ACItems.shadow_gem));
		}
		if(getEnergy(1) >= 100){
			setEnergy(1, 0);
			ItemStack output = getStackInSlot(3);
			if(!output.isEmpty() && output.getItem() == ACItems.essence && output.getItemDamage() == 0)
				output.grow(1);
			else setInventorySlotContents(3, new ItemStack(ACItems.essence, 1, 0));
		}
		if(getEnergy(2) >= 100){
			setEnergy(2, 0);
			ItemStack output = getStackInSlot(4);
			if(!output.isEmpty() && output.getItem() == ACItems.essence && output.getItemDamage() == 1)
				output.grow(1);
			else setInventorySlotContents(4, new ItemStack(ACItems.essence, 1, 1));
		}
		if(getEnergy(3) >= 100){
			setEnergy(3, 0);
			ItemStack output = getStackInSlot(5);
			if(!output.isEmpty() && output.getItem() == ACItems.essence && output.getItemDamage() == 2)
				output.grow(1);
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
}
