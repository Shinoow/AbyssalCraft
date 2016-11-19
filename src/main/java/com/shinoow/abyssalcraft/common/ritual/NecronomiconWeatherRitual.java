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
package com.shinoow.abyssalcraft.common.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;

public class NecronomiconWeatherRitual extends NecronomiconRitual {

	public NecronomiconWeatherRitual() {
		super("weather", 0, 100F, Items.feather, Items.feather, Items.feather, Items.feather,
				Items.feather, Items.feather, Items.feather, Items.feather);
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		return world.getBiomeGenForCoords(pos).canSpawnLightningBolt();
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {

	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		if(!world.isRaining())
			world.getWorldInfo().setRaining(true);
		else if(world.isRaining() && !world.isThundering())
			world.getWorldInfo().setThundering(true);
		else if(world.isThundering()){
			world.getWorldInfo().setRaining(false);
			world.getWorldInfo().setThundering(false);
		}
	}
}
