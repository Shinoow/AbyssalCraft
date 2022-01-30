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

import java.util.Arrays;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.common.blocks.BlockSequentialBrewingStand;
import com.shinoow.abyssalcraft.common.inventory.ContainerSequentialBrewingStand;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class TileEntitySequentialBrewingStand extends TileEntityLockable implements ITickable, ISidedInventory
{
	/** an array of the input slot indices */
	private static final int[] SLOTS_FOR_UP = {3};
	private static final int[] SLOTS_FOR_DOWN = {5, 6, 7};
	/** an array of the output slot indices */
	private static final int[] OUTPUT_SLOTS = {0, 1, 2, 4};
	/** The ItemStacks currently placed in the slots of the brewing stand */
	private NonNullList<ItemStack> brewingItemStacks = NonNullList.<ItemStack>withSize(8, ItemStack.EMPTY);
	private int brewTime;
	/** an integer with each bit specifying whether that slot of the stand contains a potion */
	private boolean[] filledSlots;
	/** used to check if the current ingredient has been removed from the brewing stand during brewing */
	private Item ingredientID;
	private String customName;
	private int fuel;
	private EnumFacing direction;

	/**
	 * Get the name of this object. For players this returns their username
	 */
	@Override
	public String getName()
	{
		return hasCustomName() ? customName : "container.abyssalcraft.sequential_brewing";
	}

	/**
	 * Returns true if this thing is named
	 */
	@Override
	public boolean hasCustomName()
	{
		return customName != null && !customName.isEmpty();
	}

	public void setName(String name)
	{
		customName = name;
	}

	/**
	 * Returns the number of slots in the inventory.
	 */
	@Override
	public int getSizeInventory()
	{
		return brewingItemStacks.size();
	}

	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : brewingItemStacks)
			if (!itemstack.isEmpty())
				return false;

		return true;
	}

	/**
	 * Like the old updateEntity(), except more generic.
	 */
	@Override
	public void update()
	{
		ItemStack itemstack = brewingItemStacks.get(4);

		if (fuel <= 0 && itemstack.getItem() == Items.BLAZE_POWDER)
		{
			fuel = 20;
			itemstack.shrink(1);
			markDirty();
		}

		boolean flag = canBrew();
		boolean flag1 = brewTime > 0;
		ItemStack itemstack1 = brewingItemStacks.get(3);

		if (flag1)
		{
			--brewTime;
			boolean flag2 = brewTime == 0;

			if (flag2 && flag)
			{
				brewPotions();
				markDirty();
			}
			else if (!flag || ingredientID != itemstack1.getItem())
			{
				brewTime = 0;
				markDirty();
			}
		}
		else if (flag && fuel > 0)
		{
			--fuel;
			brewTime = 400;
			ingredientID = itemstack1.getItem();
			markDirty();
		}

		if (!world.isRemote)
		{
			boolean[] aboolean = createFilledSlotsArray();

			if (!Arrays.equals(aboolean, filledSlots))
			{
				filledSlots = aboolean;
				IBlockState iblockstate = world.getBlockState(getPos());

				if (!(iblockstate.getBlock() instanceof BlockSequentialBrewingStand))
					return;

				BlockSequentialBrewingStand.updateBlockState(aboolean, world, pos);
			}

			if(world.getWorldTime() % 20 == 0)
			{
				TileEntity te = world.getTileEntity(getPos().offset(getDirection()));

				if(te instanceof TileEntitySequentialBrewingStand) //move output to nearby stand if present
				{
					TileEntitySequentialBrewingStand stand = (TileEntitySequentialBrewingStand) te;
					for(int slot : SLOTS_FOR_DOWN)
						if(stand.isSlotEmpty(slot-5))
							stand.setInventorySlotContents(slot-5, ItemStackHelper.getAndRemove(brewingItemStacks, slot));
				}
			}
		}
	}

	/**
	 * Creates an array of boolean values, each value represents a potion input slot, value is true if the slot is not
	 * null.
	 */
	public boolean[] createFilledSlotsArray()
	{
		boolean[] aboolean = new boolean[3];

		for (int i = 0; i < 3; ++i)
			if (!brewingItemStacks.get(i).isEmpty())
				aboolean[i] = true;

		return aboolean;
	}

	private boolean canBrew()
	{
		return BrewingRecipeRegistry.canBrew(brewingItemStacks, brewingItemStacks.get(3), OUTPUT_SLOTS); // divert to VanillaBrewingRegistry
	}

	private void brewPotions()
	{
		if (ForgeEventFactory.onPotionAttemptBrew(brewingItemStacks)) return;
		ItemStack itemstack = brewingItemStacks.get(3);

		BrewingRecipeRegistry.brewPotions(brewingItemStacks, brewingItemStacks.get(3), OUTPUT_SLOTS);

		itemstack.shrink(1);
		BlockPos blockpos = getPos();

		if (itemstack.getItem().hasContainerItem(itemstack))
		{
			ItemStack itemstack1 = itemstack.getItem().getContainerItem(itemstack);

			if (itemstack.isEmpty())
				itemstack = itemstack1;
			else
				InventoryHelper.spawnItemStack(world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), itemstack1);
		}

		brewingItemStacks.set(3, itemstack);
		world.playEvent(1035, blockpos, 0);
		ForgeEventFactory.onPotionBrewed(brewingItemStacks);

		//transfer results to the output slots if they're not empty
		for(int slot : SLOTS_FOR_DOWN)
			if(isSlotEmpty(slot))
				setInventorySlotContents(slot, ItemStackHelper.getAndRemove(brewingItemStacks, slot-5));
	}

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		brewingItemStacks = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, brewingItemStacks);
		brewTime = compound.getShort("BrewTime");

		if (compound.hasKey("CustomName", 8))
			customName = compound.getString("CustomName");

		fuel = compound.getByte("Fuel");
		if(compound.hasKey("Direction"))
			direction = EnumFacing.byIndex(compound.getInteger("Direction"));
		else direction = EnumFacing.NORTH;

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setShort("BrewTime", (short)brewTime);
		ItemStackHelper.saveAllItems(compound, brewingItemStacks);

		if (hasCustomName())
			compound.setString("CustomName", customName);

		compound.setByte("Fuel", (byte)fuel);
		compound.setInteger("Direction", direction != null ? direction.getIndex() : 2);
		return compound;
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

	/**
	 * Returns the stack in the given slot.
	 */
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return index >= 0 && index < brewingItemStacks.size() ? (ItemStack)brewingItemStacks.get(index) : ItemStack.EMPTY;
	}

	/**
	 * Removes up to a specified number of items from an inventory slot and returns them in a new stack.
	 */
	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(brewingItemStacks, index, count);
	}

	/**
	 * Removes a stack from the given slot and returns it.
	 */
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(brewingItemStacks, index);
	}

	/**
	 * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
	 */
	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		if (index >= 0 && index < brewingItemStacks.size())
			brewingItemStacks.set(index, stack);
	}

	public boolean isSlotEmpty(int index)
	{
		return brewingItemStacks.get(index).isEmpty();
	}

	public void setDirection(EnumFacing direction)
	{
		this.direction = direction;
	}

	public EnumFacing getDirection()
	{
		return direction != null ? direction : EnumFacing.NORTH;
	}

	/**
	 * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/**
	 * Don't rename this method to canInteractWith due to conflicts with Container
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		if (world.getTileEntity(pos) != this)
			return false;
		else
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot. For
	 * guis use Slot.isItemValid
	 */
	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index == 3)
			return BrewingRecipeRegistry.isValidIngredient(stack);
		else
		{
			Item item = stack.getItem();

			if (index == 4)
				return item == Items.BLAZE_POWDER;
			else
				return BrewingRecipeRegistry.isValidInput(stack) && getStackInSlot(index).isEmpty();
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		if (side == EnumFacing.UP)
			return SLOTS_FOR_UP;
		else
			return side == EnumFacing.DOWN ? SLOTS_FOR_DOWN : OUTPUT_SLOTS;
	}

	/**
	 * Returns true if automation can insert the given item in the given slot from the given side.
	 */
	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return isItemValidForSlot(index, itemStackIn);
	}

	/**
	 * Returns true if automation can extract the given item in the given slot from the given side.
	 */
	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		if (index == 3)
			return stack.getItem() == Items.GLASS_BOTTLE;
		else
			return index >= 5 && direction == EnumFacing.DOWN;
	}

	@Override
	public String getGuiID()
	{
		return "";
	}

	@Override
	public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
	{
		return new ContainerSequentialBrewingStand(playerInventory, this);
	}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return brewTime;
		case 1:
			return fuel;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			brewTime = value;
			break;
		case 1:
			fuel = value;
		}
	}

	IItemHandler handlerInput = new SidedInvWrapper(this, EnumFacing.UP);
	IItemHandler handlerOutput = new SidedInvWrapper(this, EnumFacing.DOWN);
	IItemHandler handlerSides = new SidedInvWrapper(this, EnumFacing.NORTH);

	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing)
	{
		if (facing != null && capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.UP)
				return (T) handlerInput;
			else if (facing == EnumFacing.DOWN)
				return (T) handlerOutput;
			else
				return (T) handlerSides;
		return super.getCapability(capability, facing);
	}

	@Override
	public int getFieldCount()
	{
		return 2;
	}

	@Override
	public void clear()
	{
		brewingItemStacks.clear();
	}
}
