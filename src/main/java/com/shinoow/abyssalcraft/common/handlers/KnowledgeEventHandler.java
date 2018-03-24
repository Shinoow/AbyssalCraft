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

import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.condition.caps.NecroDataCapabilityProvider;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.KnowledgeUnlockMessage;
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
			if(player.ticksExisted % 200 == 0 && ForgeRegistries.BIOMES.getKey(b) != null) {
				String name = ForgeRegistries.BIOMES.getKey(b).toString();
				INecroDataCapability cap = NecroDataCapability.getCap(player);
				if(!cap.getBiomeTriggers().contains(name)) {
					cap.triggerBiomeUnlock(ForgeRegistries.BIOMES.getKey(b).toString());
					PacketDispatcher.sendTo(new KnowledgeUnlockMessage(0, name), (EntityPlayerMP) player);
				}
			}
		}
	}

	@SubscribeEvent
	public void onPlayerChangedDimension(PlayerChangedDimensionEvent event){
		INecroDataCapability cap = NecroDataCapability.getCap(event.player);
		if(!cap.getDimensionTriggers().contains(event.toDim)) {
			cap.triggerDimensionUnlock(event.toDim);
			PacketDispatcher.sendTo(new KnowledgeUnlockMessage(2, event.toDim), (EntityPlayerMP) event.player);
		}
	}

	@SubscribeEvent
	public void onDeath(LivingDeathEvent event){
		if(!(event.getEntityLiving() instanceof EntityPlayer) && !event.getEntityLiving().world.isRemote){
			EntityLivingBase e = event.getEntityLiving();
			if(event.getSource() != null && event.getSource().getTrueSource() instanceof EntityPlayer && EntityList.getKey(e) != null) {
				String name = EntityList.getKey(e).toString();
				INecroDataCapability cap = NecroDataCapability.getCap((EntityPlayer)event.getSource().getTrueSource());
				if(!cap.getEntityTriggers().contains(name)) {
					cap.triggerEntityUnlock(name);
					PacketDispatcher.sendTo(new KnowledgeUnlockMessage(1, name), (EntityPlayerMP) event.getSource().getTrueSource());
				}
			}
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
