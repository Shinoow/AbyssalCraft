/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.handlers;

import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapabilityProvider;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.NecroDataCapMessage;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

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
			if(player.ticksExisted % 200 == 0 && ForgeRegistries.BIOMES.getKey(b) != null)
				NecroDataCapability.getCap(player).triggerBiomeUnlock(ForgeRegistries.BIOMES.getKey(b).toString());
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerChangedDimensionEvent event){
		NecroDataCapability.getCap(event.player).triggerDimensionUnlock(event.toDim);
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event){
		if(!(event.getEntityLiving() instanceof EntityPlayer) && !event.getEntityLiving().world.isRemote){
			EntityLivingBase e = event.getEntityLiving();
			if(event.getSource() != null && event.getSource().getEntity() instanceof EntityPlayer && EntityList.getKey(e) != null)
				NecroDataCapability.getCap((EntityPlayer)event.getSource().getEntity()).triggerEntityUnlock(EntityList.getKey(e).toString());
		}
	}

	@SubscribeEvent
	public void onEntityJoin(EntityJoinWorldEvent event){
		if(event.getEntity() instanceof EntityPlayerMP)
			PacketDispatcher.sendTo(new NecroDataCapMessage((EntityPlayer)event.getEntity()), (EntityPlayerMP) event.getEntity());
	}

	@SubscribeEvent
	public void onClonePlayer(PlayerEvent.Clone event) {
		if(event.isWasDeath())
			NecroDataCapability.getCap(event.getEntityPlayer()).copy(NecroDataCapability.getCap(event.getOriginal()));
	}
}
