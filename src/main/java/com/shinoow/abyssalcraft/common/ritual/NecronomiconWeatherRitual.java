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
package com.shinoow.abyssalcraft.common.ritual;

import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class NecronomiconWeatherRitual extends NecronomiconRitual {

	public NecronomiconWeatherRitual() {
		super("weather", 0, 100F, Items.FEATHER, Items.FEATHER, Items.FEATHER, Items.FEATHER,
				Items.FEATHER, Items.FEATHER, Items.FEATHER, Items.FEATHER);
		setRitualParticle(EnumRitualParticle.PE_STREAM);
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		return world.getBiome(pos).canRain();
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
