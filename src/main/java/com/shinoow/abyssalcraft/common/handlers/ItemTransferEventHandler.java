/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.handlers;

import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapability;
import com.shinoow.abyssalcraft.api.transfer.caps.ItemTransferCapabilityProvider;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.Type;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.relauncher.Side;

public class ItemTransferEventHandler {

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<TileEntity> event) {
		//TODO blacklist
		event.addCapability(new ResourceLocation("abyssalcraft", "itemtransfer"), new ItemTransferCapabilityProvider());
	}

	@SubscribeEvent
	public void onTick(WorldTickEvent event) {
		if(event.side == Side.SERVER && event.type == Type.SERVER && event.phase == Phase.END)
		{
			World world = event.world;
			world.tickableTileEntities.stream()
			.filter(t -> world.isBlockLoaded(t.getPos()))
			.filter(t -> ItemTransferCapability.getCap(t) != null)
			.forEach(tile -> {
				//TODO run logic
			});
		}
	}
}
