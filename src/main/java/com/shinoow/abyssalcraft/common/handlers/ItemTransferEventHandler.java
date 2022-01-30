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
package com.shinoow.abyssalcraft.common.handlers;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;
import com.shinoow.abyssalcraft.api.transfer.caps.IItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapabilityProvider;
import com.shinoow.abyssalcraft.common.entity.EntitySpiritItem;
import com.shinoow.abyssalcraft.init.InitHandler;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public class ItemTransferEventHandler {

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<TileEntity> event) {
		ResourceLocation rl = TileEntity.getKey(event.getObject().getClass());
		if(rl != null && !InitHandler.INSTANCE.isTileBlackListed(rl.toString()))
			event.addCapability(new ResourceLocation("abyssalcraft", "itemtransfer"), new ItemTransferCapabilityProvider());
	}

	@SubscribeEvent
	public void onTick(WorldTickEvent event) {
		if(event.side == Side.SERVER && event.type == Type.WORLD && event.phase == Phase.END)
		{
			World world = event.world;
			if(world.getTotalWorldTime() % 20 != 0) return;
			List<TileEntity> list = new ArrayList<>(world.loadedTileEntityList);
			list.stream()
			.filter(t -> world.isBlockLoaded(t.getPos()))
			.filter(this::hasCap)
			.filter(ItemTransferEventHandler::hasInventory)//maybe remove
			.forEach(tile -> {
				IItemTransferCapability cap = ItemTransferCapability.getCap(tile);
				for(ItemTransferConfiguration cfg : cap.getTransferConfigurations()) {
					IItemHandler inventory = getInventory(tile, cfg.getExitFacing());
					if(inventory != null) {//sided inventories, you never know
						boolean hasFilter = !cfg.getFilter().isEmpty() && cfg.getFilter().stream().anyMatch(i -> !i.isEmpty());
						ItemStack stack = ItemStack.EMPTY;
						int slot = -1;
						for(int i = 0; i < inventory.getSlots(); i++) {
							stack = inventory.getStackInSlot(i);
							if(!stack.isEmpty())
								if(!hasFilter || isInFilter(cfg.getFilter(), stack, cfg.filterByNBT())) {
									stack = inventory.extractItem(i, 1, true);
									slot = i;
									break;
								}
						}
						if(!stack.isEmpty() && slot > -1) {
							BlockPos exitPos = cfg.getRoute()[cfg.getRoute().length-1];
							TileEntity te = world.getTileEntity(exitPos);
							if(te != null) {
								IItemHandler exitInv = getInventory(te, cfg.getEntryFacing());
								if(exitInv != null && ItemHandlerHelper.insertItem(exitInv, stack, true).isEmpty()) {//insertion worked
									stack = inventory.extractItem(slot, 1, false);
									BlockPos pos = tile.getPos();
									EntitySpiritItem spirit = new EntitySpiritItem(world, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, stack.copy());
									spirit.setRoute(cfg.getRoute());
									spirit.setFacing(cfg.getEntryFacing());
									world.spawnEntity(spirit);
								}
							}
						}
					}
				}
			});
		}
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
		return getInventory(te, EnumFacing.DOWN) != null;
	}

	private boolean hasCap(TileEntity te) {
		if(te.hasCapability(ItemTransferCapabilityProvider.ITEM_TRANSFER_CAP, null)) {
			IItemTransferCapability cap = ItemTransferCapability.getCap(te);
			return cap != null && cap.isRunning();
		}
		return false;
	}

	private boolean isInFilter(NonNullList<ItemStack> filter, ItemStack stack, boolean nbt) {

		for(ItemStack stack1 : filter)
			if(APIUtils.areStacksEqual(stack, stack1, nbt))
				return true;

		return false;
	}
}
