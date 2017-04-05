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
package com.shinoow.abyssalcraft.common.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;

import com.shinoow.abyssalcraft.common.caps.NecroDataCapabilityProvider;

public class KnowledgeEventHandler {

	@SubscribeEvent
	public void attachCapability(AttachCapabilitiesEvent<Entity> event){
		if(event.getObject() instanceof EntityPlayer)
			event.addCapability(new ResourceLocation("abyssalcraft", "necrodata"), new NecroDataCapabilityProvider());
	}

	@SubscribeEvent
	public void onLivingUpdate(LivingUpdateEvent event){
		if(event.getEntityLiving() instanceof EntityPlayer && !event.getEntityLiving().world.isRemote){
			EntityPlayer player = (EntityPlayer)event.getEntityLiving();
			Biome b = player.world.getBiome(player.getPosition());
			if(player.ticksExisted % 200 == 0)
				player.getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null).triggerBiomeUnlock(Biome.REGISTRY.getNameForObject(b).toString());
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerChangedDimensionEvent event){
		event.player.getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null).triggerDimensionUnlock(event.toDim);
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event){
		if(!(event.getEntityLiving() instanceof EntityPlayer) && !event.getEntityLiving().world.isRemote){
			EntityLivingBase e = event.getEntityLiving();
			if(event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EntityPlayer)
				event.getSource().getEntity().getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null).triggerEntityUnlock(EntityList.getKey(e).toString());
		}
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		event.getEntityPlayer().getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null).copy(event.getOriginal().getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null));
	}
}
