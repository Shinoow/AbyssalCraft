/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.IPlantable;

public class NecronomiconPurgingRitual extends NecronomiconRitual {

	public NecronomiconPurgingRitual() {
		super("purging", 3, 10000F, new ItemStack(ACBlocks.crystal_cluster2, 1, 9), null, new ItemStack(ACBlocks.crystal_cluster2, 1, 9),
				null, new ItemStack(ACBlocks.crystal_cluster2, 1, 9), null, new ItemStack(ACBlocks.crystal_cluster2, 1, 9));
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
		for(int x = pos.getX() - 64; x < pos.getX() + 65; x++)
			for(int z = pos.getZ() - 64; z < pos.getZ() + 65; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(!(world.getBiome(pos1) instanceof IDreadlandsBiome)) continue;

				for(EntityLivingBase e : world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos1).grow(0, 128, 0), EntitySelectors.IS_ALIVE))
					if(!(e instanceof EntityPlayer))
						world.removeEntity(e);

				for(int y = 255; y > -1; y--){
					if(world.isAirBlock(pos1.up(y))) continue;
					IBlockState state = world.getBlockState(pos1.up(y));
					if(state.getBlock().hasTileEntity(state)) continue;
					if(state.getBlockHardness(world, pos1.up(y)) == -1) continue;
					if(state.getBlock() instanceof IPlantable)
						world.setBlockState(pos1.up(y), Blocks.AIR.getDefaultState(), 2);
					else if(state.getMaterial().isLiquid())
						world.setBlockState(pos1.up(y), Blocks.AIR.getDefaultState(), 2);
					else if(!state.isFullCube())
						world.setBlockState(pos1.up(y), Blocks.AIR.getDefaultState(), 2);
					else world.setBlockState(pos1.up(y), ACBlocks.calcified_stone.getDefaultState(), 2);
				}
				
				Biome b = ACBiomes.purged;
				BiomeUtil.updateBiome(world, pos1, b, true);
			}
	}
}
