/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.ritual;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenAntimatterLake;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.util.ScheduledProcess;
import com.shinoow.abyssalcraft.lib.util.Scheduler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class NecronomiconInfestingRitual extends NecronomiconRitual {

	public NecronomiconInfestingRitual() {
		super("infesting", 3, 0, 10000F, true, new Object[]{APIUtils.getAllStatues(), ACBlocks.coralium_stone, APIUtils.getAllStatues(),
				ACBlocks.coralium_stone, APIUtils.getAllStatues(), ACBlocks.coralium_stone, APIUtils.getAllStatues(), ACBlocks.coralium_stone});
		setRitualParticle(EnumRitualParticle.PE_STREAM);
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
		for(int x = pos.getX() - 24; x < pos.getX() + 25; x++)
			for(int z = pos.getZ() - 24; z < pos.getZ() + 25; z++){
				BlockPos pos1 = new BlockPos(x, 0, z);
				Biome b = world.getBiome(pos1);
				if(b == Biomes.SWAMPLAND || b == Biomes.MUTATED_SWAMPLAND)
					return true;
			}
		return false;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {
		//TODO message?
	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		int num = 1, num2 = 0, range = ACConfig.infestingRitualRange * 8;
		WorldGenAntimatterLake lake = new WorldGenAntimatterLake(ACBlocks.liquid_antimatter);
		for(int x = pos.getX() - range; x < pos.getX() + range + 1; x++)
			for(int z = pos.getZ() - range; z < pos.getZ() + range + 1; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(!isApplicable(world, pos1)) continue;

				int RandPosX = world.rand.nextInt(16) + 8;
				int RandPosY = world.rand.nextInt(40) + 40;
				int RandPosZ = world.rand.nextInt(16) + 8;
				if(world.rand.nextInt(3000) == 0)
					lake.generate(world, world.rand, pos1.add(RandPosX, RandPosY, RandPosZ));

				Scheduler.schedule(new ScheduledProcess(num * 2) {

					@Override
					public void execute() {
						BiomeUtil.updateBiome(world, pos1, ACBiomes.coralium_infested_swamp, true);
					}

				});
				num2++;
				if(num2 % 256 == 0)
					num++;
			}
	}

	private boolean isApplicable(World world, BlockPos pos) {
		Biome b = world.getBiome(pos);
		return b == Biomes.SWAMPLAND || b == Biomes.MUTATED_SWAMPLAND;
	}

}
