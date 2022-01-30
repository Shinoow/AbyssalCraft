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

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDreadlandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.ScheduledProcess;
import com.shinoow.abyssalcraft.lib.util.Scheduler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;

public class NecronomiconPurgingRitual extends NecronomiconRitual {

	public NecronomiconPurgingRitual() {
		super("purging", 2, 10000F, new ItemStack(ACBlocks.calcium_crystal_cluster), null, new ItemStack(ACBlocks.calcium_crystal_cluster),
				null, new ItemStack(ACBlocks.calcium_crystal_cluster), null, new ItemStack(ACBlocks.calcium_crystal_cluster));
		setRitualParticle(EnumRitualParticle.PE_STREAM);
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
		if(world.provider.getDimension() != ACLib.dreadlands_id)
			for(int x = pos.getX() - 24; x < pos.getX() + 25; x++)
				for(int z = pos.getZ() - 24; z < pos.getZ() + 25; z++){
					BlockPos pos1 = new BlockPos(x, 0, z);
					if(world.getBiome(pos1) instanceof IDreadlandsBiome)
						return true;
				}
		return false;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		int num = 1, num2 = 0, range = ACConfig.purgingRitualRange * 8;
		for(int x = pos.getX() - range; x < pos.getX() + range + 1; x++)
			for(int z = pos.getZ() - range; z < pos.getZ() + range + 1; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(!(world.getBiome(pos1) instanceof IDreadlandsBiome)) continue;

				Scheduler.schedule(new ScheduledProcess(num * 2) {

					@Override
					public void execute() {
						for(EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos1).grow(0, 128, 0), EntitySelectors.IS_ALIVE))
							if(!(e instanceof EntityPlayer))
								world.removeEntity(e);

						for(int y = 255; y > -1; y--){
							if(world.isAirBlock(pos1.up(y))) continue;
							IBlockState state = world.getBlockState(pos1.up(y));
							if(state.getBlock().hasTileEntity(state)) continue;
							if(state.getBlockHardness(world, pos1.up(y)) == -1) continue;
							if(state.getBlock() instanceof IPlantable || state.getMaterial().isLiquid() || !state.isFullCube())
								world.setBlockState(pos1.up(y), Blocks.AIR.getDefaultState(), 2);
							else
								world.setBlockState(pos1.up(y), ACBlocks.calcified_stone.getDefaultState(), 2);
						}

						BiomeUtil.updateBiome(world, pos1, ACBiomes.purged, true);
					}

				});
				num2++;
				if(num2 % 256 == 0)
					num++;
			}
	}
}
