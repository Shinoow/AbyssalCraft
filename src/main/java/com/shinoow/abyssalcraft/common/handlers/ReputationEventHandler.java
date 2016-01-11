/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

import com.shinoow.abyssalcraft.common.entity.props.ReputationProps;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.SyncPlayerPropsMessage;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

/**
 * Handles everything event-based related to the reputation system, ALL OF IT.
 * @author shinoow
 *
 */
public class ReputationEventHandler {

	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event) {
		if (event.entity instanceof EntityPlayer)
			if (ReputationProps.get((EntityPlayer) event.entity) == null)
				ReputationProps.register((EntityPlayer) event.entity);
	}

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event) {
		if (event.entity instanceof EntityPlayer && !event.entity.worldObj.isRemote)
			PacketDispatcher.sendTo(new SyncPlayerPropsMessage((EntityPlayer) event.entity), (EntityPlayerMP) event.entity);
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		ReputationProps.get(event.entityPlayer).copy(ReputationProps.get(event.original));
	}
}
