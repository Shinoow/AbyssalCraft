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
package com.shinoow.abyssalcraft.lib.util;

import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapabilityProvider;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

/**
 * Utility class for Spirit Item-related stuff
 * @author shinoow
 *
 */
public class SpiritItemUtil {

	public static boolean validPos(BlockPos pos, World world) {
		return validPos(pos, world, EnumFacing.DOWN);
	}

	public static boolean validPos(BlockPos pos, World world, EnumFacing face) {
		if(world.isBlockLoaded(pos))
			return validTE(world.getTileEntity(pos), face);
		return false;
	}

	public static boolean validTE(TileEntity te) {
		return validTE(te, EnumFacing.DOWN);
	}

	public static boolean validTE(TileEntity te, EnumFacing face) {

		if(te == null) return false;

		return hasCap(te) && hasInventory(te, face);
	}

	public static IItemHandler getInventory(TileEntity te, EnumFacing face) {

		if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face))
			return te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, face);
		else if(te instanceof ISidedInventory)
			return new SidedInvWrapper((ISidedInventory)te, face);
		else if(te instanceof IInventory)
			return new InvWrapper((IInventory)te);

		return null;
	}

	public static boolean hasInventory(TileEntity te) {
		return hasInventory(te, EnumFacing.DOWN);
	}

	public static boolean hasInventory(TileEntity te, EnumFacing face) {
		return getInventory(te, face) != null;
	}

	private static boolean hasCap(TileEntity te) {
		if(te.hasCapability(ItemTransferCapabilityProvider.ITEM_TRANSFER_CAP, null)) {
			IItemTransferCapability cap = ItemTransferCapability.getCap(te);
			return cap != null;
		}
		return false;
	}
}