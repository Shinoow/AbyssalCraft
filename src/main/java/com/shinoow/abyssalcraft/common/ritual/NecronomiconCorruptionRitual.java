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
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.blocks.BlockACBrick;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.oredict.OreDictionary;

public class NecronomiconCorruptionRitual extends NecronomiconRitual {

	public NecronomiconCorruptionRitual() {
		super("corruption", 4, 0, 10000F, true, new Object[]{new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACBlocks.stone, 1, 0), new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE),
				new ItemStack(ACBlocks.stone, 1, 0), new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACBlocks.stone, 1, 0), new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACBlocks.stone, 1, 0)});
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
		for(int x = pos.getX() - 24; x < pos.getX() + 25; x++)
			for(int z = pos.getZ() - 24; z < pos.getZ() + 25; z++){
				BlockPos pos1 = new BlockPos(x, 0, z);
				Biome b = world.getBiome(pos1);
				if(b == Biomes.PLAINS || b == Biomes.EXTREME_HILLS || b == Biomes.FOREST || b == Biomes.ICE_MOUNTAINS ||
						b == Biomes.MUTATED_PLAINS || b == Biomes.MUTATED_EXTREME_HILLS || b == Biomes.MUTATED_FOREST ||
						b == Biomes.FOREST_HILLS)
					return true;
			}
		return false;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {
		SpecialTextUtil.JzaharText("Behold, as the darkness consumes the Overworld! Bit by bit, this world will crumble.");
	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		boolean b1 = world.rand.nextBoolean();
		for(int x = pos.getX() -64; x < pos.getX() + 65; x++)
			for(int z = pos.getZ() - 64; z < pos.getZ() + 65; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(!isApplicable(world, pos1)) continue;
				Biome b = getDarklandsBiome(world.getBiome(pos1), b1);

				for(int y = 0; y < 256; y++){
					if(world.isAirBlock(pos1.up(y))) continue;
					IBlockState state = world.getBlockState(pos1.up(y));
					if(state.getBlock() == Blocks.STONE && state.getValue(BlockStone.VARIANT) != BlockStone.EnumType.STONE)
						world.setBlockState(pos1.up(y), ACBlocks.stone.getDefaultState(), 2);
					else if(state.getBlock() == Blocks.LEAVES)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, state.getValue(BlockLeaves.CHECK_DECAY)).withProperty(BlockLeaves.DECAYABLE, state.getValue(BlockLeaves.DECAYABLE)), 2);
					else if(state.getBlock() == Blocks.LOG)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_wood.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)), 2);
					else if(state.getBlock() == Blocks.COBBLESTONE)
						world.setBlockState(pos1.up(y), ACBlocks.cobblestone.getDefaultState(), 2);
					else if(state.getBlock() == Blocks.STONEBRICK)
						switch(state.getValue(BlockStoneBrick.VARIANT)){
						case CHISELED:
							world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick.getDefaultState().withProperty(BlockACBrick.TYPE, BlockACBrick.EnumBrickType.CHISELED), 2);
							break;
						case CRACKED:
							world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick.getDefaultState().withProperty(BlockACBrick.TYPE, BlockACBrick.EnumBrickType.CRACKED), 2);
							break;
						default:
							world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick.getDefaultState(), 2);
							break;
						}
					else if(state.getBlock() == Blocks.COBBLESTONE_WALL && ACConfig.darkstone_cobblestone_wall)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_wall.getDefaultState(), 2);
					else if(state == ACBlocks.ritual_altar.getDefaultState())
						world.setBlockState(pos1.up(y), ACBlocks.ritual_altar.getDefaultState().withProperty(BlockRitualAltar.MATERIAL, BlockRitualAltar.EnumRitualMatType.DARKSTONE_COBBLESTONE), 2);
					else if(state == ACBlocks.ritual_pedestal.getDefaultState())
						world.setBlockState(pos1.up(y), ACBlocks.ritual_pedestal.getDefaultState().withProperty(BlockRitualAltar.MATERIAL, BlockRitualAltar.EnumRitualMatType.DARKSTONE_COBBLESTONE), 2);
					else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SMOOTHBRICK && ACConfig.darkstone_brick_slab)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
					else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.COBBLESTONE && ACConfig.darkstone_cobblestone_slab)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
					else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE && ACConfig.darkstone_slab)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
					else if(state.getBlock() == Blocks.DOUBLE_STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE && ACConfig.darkstone_slab)
						world.setBlockState(pos1.up(y), BlockHandler.Darkstoneslab2.getDefaultState(), 2);
					else if(state.getBlock() == Blocks.STONE_BRICK_STAIRS && ACConfig.darkstone_brick_stairs)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)), 2);
					else if(state.getBlock() == Blocks.STONE_STAIRS && ACConfig.darkstone_cobblestone_stairs)
						world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)), 2);
					else if(state.getBlock() == Blocks.PLANKS)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_planks.getDefaultState(), 2);
					else if(state.getBlock() == Blocks.OAK_STAIRS && ACConfig.darklands_oak_stairs)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)), 2);
					else if(state.getBlock() == Blocks.WOODEN_SLAB && ACConfig.darklands_oak_slab)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
					else if(state.getBlock() == Blocks.OAK_FENCE)
						world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_fence.getDefaultState(), 2);
				}
				
				BiomeUtil.updateBiome(world, pos1, b, true);
			}
	}

	private boolean isApplicable(World world, BlockPos pos) {
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
