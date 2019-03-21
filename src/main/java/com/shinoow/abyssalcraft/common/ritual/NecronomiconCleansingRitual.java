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
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.blocks.BlockACBrick;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.oredict.OreDictionary;

public class NecronomiconCleansingRitual extends NecronomiconRitual {

	public NecronomiconCleansingRitual() {
		super("cleansing", 4, 0, 100000F, true, new Object[]{new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE),
				new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE),
				new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACBlocks.statue, 1, OreDictionary.WILDCARD_VALUE)});
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
		for(int x = pos.getX() - 24; x < pos.getX() + 25; x++)
			for(int z = pos.getZ() - 24; z < pos.getZ() + 25; z++){
				BlockPos pos1 = new BlockPos(x, 0, z);
				if(world.getBiome(pos1) instanceof IDarklandsBiome)
					return true;
			}
		return false;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {
		SpecialTextUtil.JzaharText("Undoing my malice on your "+TextFormatting.ITALIC+"beloved"+TextFormatting.RESET+" world? What a shame.");
	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		for(int x = pos.getX() - 64; x < pos.getX() + 65; x++)
			for(int z = pos.getZ() - 64; z < pos.getZ() + 65; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(!(world.getBiome(pos1) instanceof IDarklandsBiome)) continue;
				Biome b = getRealBiome(world.getBiome(pos1));

				for(int y = 0; y < 256; y++){
					if(world.isAirBlock(pos1.up(y))) continue;
					IBlockState state = world.getBlockState(pos1.up(y));
					if(state == ACBlocks.stone.getDefaultState())
						world.setBlockState(pos1.up(y), Blocks.STONE.getDefaultState(), 2);
					else if(state.getBlock() == ACBlocks.darklands_oak_leaves)
						world.setBlockState(pos1.up(y), Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, state.getValue(BlockLeaves.CHECK_DECAY)).withProperty(BlockLeaves.DECAYABLE, state.getValue(BlockLeaves.DECAYABLE)), 2);
					else if(state.getBlock() == ACBlocks.darklands_oak_wood)
						world.setBlockState(pos1.up(y), Blocks.LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)), 2);
					else if(state.getBlock() == ACBlocks.abyssalnite_ore)
						world.setBlockState(pos1.up(y), Blocks.IRON_ORE.getDefaultState(), 2);
					else if(state == ACBlocks.cobblestone.getDefaultState())
						world.setBlockState(pos1.up(y), Blocks.COBBLESTONE.getDefaultState(), 2);
					else if(state.getBlock() == ACBlocks.darkstone_brick)
						switch((BlockACBrick.EnumBrickType)state.getValue(BlockACBrick.TYPE)){
						case CHISELED:
							world.setBlockState(pos1.up(y), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 2);
							break;
						case CRACKED:
							world.setBlockState(pos1.up(y), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED), 2);
							break;
						default:
							world.setBlockState(pos1.up(y), Blocks.STONEBRICK.getDefaultState(), 2);
							break;
						}
					else if(state.getBlock() == ACBlocks.darkstone_cobblestone_wall)
						world.setBlockState(pos1.up(y), Blocks.COBBLESTONE_WALL.getDefaultState(), 2);
					else if(state == ACBlocks.ritual_altar.getDefaultState().withProperty(BlockRitualAltar.MATERIAL, BlockRitualAltar.EnumRitualMatType.DARKSTONE_COBBLESTONE))
						world.setBlockState(pos1.up(y), ACBlocks.ritual_altar.getDefaultState(), 2);
					else if(state == ACBlocks.ritual_pedestal.getDefaultState().withProperty(BlockRitualAltar.MATERIAL, BlockRitualAltar.EnumRitualMatType.DARKSTONE_COBBLESTONE))
						world.setBlockState(pos1.up(y), ACBlocks.ritual_pedestal.getDefaultState(), 2);
					else if(state.getBlock() == ACBlocks.darkstone_brick_slab)
						world.setBlockState(pos1.up(y), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)).withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK), 2);
					else if(state.getBlock() == ACBlocks.darkstone_cobblestone_slab)
						world.setBlockState(pos1.up(y), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)).withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.COBBLESTONE), 2);
					else if(state.getBlock() == ACBlocks.darkstone_slab)
						world.setBlockState(pos1.up(y), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
					else if(state.getBlock() == BlockHandler.Darkstoneslab2)
						world.setBlockState(pos1.up(y), Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 2);
					else if(state.getBlock() == ACBlocks.glowing_darkstone_bricks)
						world.setBlockState(pos1.up(y), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED), 2);
					else if(state.getBlock() == ACBlocks.darkstone_brick_stairs)
						world.setBlockState(pos1.up(y), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)), 2);
					else if(state.getBlock() == ACBlocks.darkstone_cobblestone_stairs)
						world.setBlockState(pos1.up(y), Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)), 2);
					else if(state.getBlock() == ACBlocks.darklands_oak_planks)
						world.setBlockState(pos1.up(y), Blocks.PLANKS.getDefaultState(), 2);
					else if(state.getBlock() == ACBlocks.darklands_oak_stairs)
						world.setBlockState(pos1.up(y), Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)), 2);
					else if(state.getBlock() == ACBlocks.darklands_oak_slab)
						world.setBlockState(pos1.up(y), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
					else if(state.getBlock() == ACBlocks.darklands_oak_fence)
						world.setBlockState(pos1.up(y), Blocks.OAK_FENCE.getDefaultState(), 2);
				}
				
				BiomeUtil.updateBiome(world, pos1, b, true);
			}
	}

	private Biome getRealBiome(Biome b){
		if(b == ACBiomes.darklands)
			return Biomes.PLAINS;
		else if(b == ACBiomes.darklands_plains)
			return Biomes.PLAINS;
		else if(b == ACBiomes.darklands_forest)
			return Biomes.FOREST;
		else if(b == ACBiomes.darklands_hills)
			return Biomes.EXTREME_HILLS;
		else if(b == ACBiomes.darklands_mountains)
			return Biomes.ICE_MOUNTAINS;
		return Biomes.PLAINS;
	}
}
