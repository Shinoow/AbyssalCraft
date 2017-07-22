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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionSpawn;
import com.shinoow.abyssalcraft.common.blocks.BlockACBrick;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.CleansingRitualMessage;
import com.shinoow.abyssalcraft.init.BlockHandler;

public class DisruptionCorruption extends DisruptionSpawn {

	public DisruptionCorruption() {
		super("corruption", null, EntityLesserShoggoth.class);
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {

		if(!isCorrectBiome(world, pos)){
			super.disrupt(world, pos, players);
			return;
		}

		if(world.isRemote) return;

		boolean b1 = world.rand.nextBoolean();
		for(int x = pos.getX() -8; x < pos.getX() + 9; x++)
			for(int z = pos.getZ() - 8; z < pos.getZ() + 9; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(!isCorrectBiome(world, pos1)) continue;
				Biome b = getDarklandsBiome(world.getBiome(pos1), b1);

				for(int y = 0; y < 256; y++){
					IBlockState state = world.getBlockState(pos1.up(y));
					if(state.getBlock() == Blocks.STONE && state.getValue(BlockStone.VARIANT) != BlockStone.EnumType.STONE)
						world.setBlockState(pos1.up(y), ACBlocks.stone.getDefaultState());
					else if(state.getBlock() == Blocks.LEAVES)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, state.getValue(BlockLeaves.CHECK_DECAY)).withProperty(BlockLeaves.DECAYABLE, state.getValue(BlockLeaves.DECAYABLE)));
					else if(state.getBlock() == Blocks.LOG)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_wood.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
					else if(state.getBlock() == Blocks.COBBLESTONE)
						world.setBlockState(pos1.up(y), ACBlocks.cobblestone.getDefaultState());
					else if(state.getBlock() == Blocks.STONEBRICK)
						switch(state.getValue(BlockStoneBrick.VARIANT)){
						case CHISELED:
							world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick.getDefaultState().withProperty(BlockACBrick.TYPE, BlockACBrick.EnumBrickType.CHISELED));
							break;
						case CRACKED:
							world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick.getDefaultState().withProperty(BlockACBrick.TYPE, BlockACBrick.EnumBrickType.CRACKED));
							break;
						default:
							world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick.getDefaultState());
							break;
						}
					else if(state.getBlock() == Blocks.COBBLESTONE_WALL)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_wall.getDefaultState());
					else if(state == ACBlocks.ritual_altar.getDefaultState())
						world.setBlockState(pos1.up(y), ACBlocks.ritual_altar.getDefaultState().withProperty(BlockRitualAltar.MATERIAL, BlockRitualAltar.EnumRitualMatType.DARKSTONE_COBBLESTONE));
					else if(state == ACBlocks.ritual_pedestal.getDefaultState())
						world.setBlockState(pos1.up(y), ACBlocks.ritual_pedestal.getDefaultState().withProperty(BlockRitualAltar.MATERIAL, BlockRitualAltar.EnumRitualMatType.DARKSTONE_COBBLESTONE));
					else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SMOOTHBRICK)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
					else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.COBBLESTONE)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
					else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
					else if(state.getBlock() == Blocks.DOUBLE_STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE)
						world.setBlockState(pos1.up(y), BlockHandler.Darkstoneslab2.getDefaultState());
					else if(state.getBlock() == Blocks.STONE_BRICK_STAIRS)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)));
					else if(state.getBlock() == Blocks.STONE_STAIRS)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)));
					else if(state.getBlock() == Blocks.PLANKS)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_planks.getDefaultState());
					else if(state.getBlock() == Blocks.OAK_STAIRS)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)));
					else if(state.getBlock() == Blocks.WOODEN_SLAB)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
					else if(state.getBlock() == Blocks.OAK_FENCE)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_fence.getDefaultState());
				}

				Chunk c = world.getChunkFromBlockCoords(pos1);
				c.getBiomeArray()[(z & 0xF) << 4 | x & 0xF] = (byte)Biome.getIdForBiome(b);
				c.setModified(true);
				PacketDispatcher.sendToDimension(new CleansingRitualMessage(x, z, Biome.getIdForBiome(b)), world.provider.getDimension());
			}
	}

	private boolean isCorrectBiome(World world, BlockPos pos) {
		Biome b = world.getBiome(pos);
		return b == Biomes.PLAINS || b == Biomes.EXTREME_HILLS || b == Biomes.FOREST || b == Biomes.ICE_MOUNTAINS ||
				b == Biomes.MUTATED_PLAINS || b == Biomes.MUTATED_EXTREME_HILLS || b == Biomes.MUTATED_FOREST ||
				b == Biomes.FOREST_HILLS;
	}

	private Biome getDarklandsBiome(Biome b, boolean b1){
		if(b == Biomes.PLAINS || b == Biomes.MUTATED_PLAINS)
			return b1 ? ACBiomes.darklands_plains : ACBiomes.darklands;
		else if(b == Biomes.FOREST || b == Biomes.MUTATED_FOREST || b == Biomes.FOREST_HILLS)
			return ACBiomes.darklands_forest;
		else if(b == Biomes.ICE_MOUNTAINS || b == Biomes.MUTATED_EXTREME_HILLS && b1)
			return ACBiomes.darklands_mountains;
		else if(b == Biomes.EXTREME_HILLS || b == Biomes.MUTATED_EXTREME_HILLS)
			return ACBiomes.darklands_hills;
		return ACBiomes.darklands;
	}
}
