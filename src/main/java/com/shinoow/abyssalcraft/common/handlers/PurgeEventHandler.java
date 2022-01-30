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

import com.shinoow.abyssalcraft.api.biome.ACBiomes;

import net.minecraft.block.IGrowable;
import net.minecraft.entity.player.EntityPlayer.SleepResult;
import net.minecraftforge.event.entity.living.AnimalTameEvent;
import net.minecraftforge.event.entity.living.BabyEntitySpawnEvent;
import net.minecraftforge.event.entity.player.*;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PurgeEventHandler {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void stopBirth(BabyEntitySpawnEvent event) {
		if(event.getParentA().getEntityWorld().getBiome(event.getParentA().getPosition()) == ACBiomes.purged)
			event.setCanceled(true);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void stopBonemeal(BonemealEvent event) {
		if(event.getWorld().getBiome(event.getPos()) == ACBiomes.purged) {
			if(!event.getWorld().isRemote && event.getBlock().getBlock() instanceof IGrowable && event.getStack().isStackable())
				event.getStack().shrink(1);
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void stopHoeUsage(UseHoeEvent event) {
		if(event.getWorld().getBiome(event.getPos()) == ACBiomes.purged) {
			if(!event.getWorld().isRemote)
				event.getCurrent().damageItem(1, event.getEntityPlayer());
			event.setCanceled(true);
		}
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void stopTaming(AnimalTameEvent event) {
		if(event.getTamer().getEntityWorld().getBiome(event.getTamer().getPosition()) == ACBiomes.purged)
			event.setCanceled(true);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void stopSleeping(PlayerSleepInBedEvent event) {
		if(event.getEntityPlayer().getEntityWorld().getBiome(event.getPos()) == ACBiomes.purged)
			event.setResult(SleepResult.NOT_POSSIBLE_HERE);
	}

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void stopSpawnPoint(PlayerSetSpawnEvent event) {
		if(event.getNewSpawn() != null && event.getEntityPlayer().getEntityWorld().getBiome(event.getNewSpawn()) == ACBiomes.purged)
			event.setCanceled(true);
	}
}
