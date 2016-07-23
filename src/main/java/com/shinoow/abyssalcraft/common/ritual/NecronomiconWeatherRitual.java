package com.shinoow.abyssalcraft.common.ritual;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;

public class NecronomiconWeatherRitual extends NecronomiconRitual {

	public NecronomiconWeatherRitual() {//TODO WIP, implement in next version
		super("weather", 0, 100F, Items.feather);

	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {

		return true;
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