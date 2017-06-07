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
package com.shinoow.abyssalcraft.common.ritual;

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
import net.minecraft.world.chunk.Chunk;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.blocks.BlockACBrick;
import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.CleansingRitualMessage;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

public class NecronomiconCleansingRitual extends NecronomiconRitual {

	public NecronomiconCleansingRitual() {
		super("cleansing", 4, 0, 100000F, true, new Object[]{new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
				new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
				new ItemStack(ACBlocks.shub_niggurath_statue)}, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
				new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
				new ItemStack(ACBlocks.shub_niggurath_statue)}, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
				new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
				new ItemStack(ACBlocks.shub_niggurath_statue)}, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
				new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
				new ItemStack(ACBlocks.shub_niggurath_statue)}, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
				new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
				new ItemStack(ACBlocks.shub_niggurath_statue)}, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
				new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
				new ItemStack(ACBlocks.shub_niggurath_statue)}, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
				new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
				new ItemStack(ACBlocks.shub_niggurath_statue)}, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
				new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
				new ItemStack(ACBlocks.shub_niggurath_statue)}});
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
		return world.getBiome(pos) instanceof IDarklandsBiome;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {
		SpecialTextUtil.JzaharText("Undoing my malice on your "+TextFormatting.ITALIC+"beloved"+TextFormatting.RESET+" world? What a shame.");
	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		for(int x = pos.getX() - 24; x < pos.getX() + 25; x++)
			for(int z = pos.getZ() - 24; z < pos.getZ() + 25; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(!(world.getBiome(pos1) instanceof IDarklandsBiome)) continue;
				Biome b = getRealBiome(world.getBiome(pos1));

				for(int y = 0; y < 256; y++){
					IBlockState state = world.getBlockState(pos1.up(y));
					if(state.getBlock() == ACBlocks.darklands_grass)
						world.setBlockState(pos1.up(y), Blocks.GRASS.getDefaultState());
					else if(state.getBlock() == ACBlocks.darkstone)
						world.setBlockState(pos1.up(y), Blocks.STONE.getDefaultState());
					else if(state.getBlock() == ACBlocks.darklands_oak_leaves)
						world.setBlockState(pos1.up(y), Blocks.LEAVES.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, state.getValue(BlockLeaves.CHECK_DECAY)).withProperty(BlockLeaves.DECAYABLE, state.getValue(BlockLeaves.DECAYABLE)));
					else if(state.getBlock() == ACBlocks.darklands_oak_wood)
						world.setBlockState(pos1.up(y), Blocks.LOG.getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)));
					else if(state.getBlock() == ACBlocks.abyssalnite_ore)
						world.setBlockState(pos1.up(y), Blocks.IRON_ORE.getDefaultState());
					else if(state.getBlock() == ACBlocks.darkstone_cobblestone)
						world.setBlockState(pos1.up(y), Blocks.COBBLESTONE.getDefaultState());
					else if(state.getBlock() == ACBlocks.darkstone_brick)
						switch((BlockACBrick.EnumBrickType)state.getValue(BlockACBrick.TYPE)){
						case CHISELED:
							world.setBlockState(pos1.up(y), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
							break;
						case CRACKED:
							world.setBlockState(pos1.up(y), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CRACKED));
							break;
						default:
							world.setBlockState(pos1.up(y), Blocks.STONEBRICK.getDefaultState());
							break;
						}
					else if(state.getBlock() == ACBlocks.darkstone_cobblestone_wall)
						world.setBlockState(pos1.up(y), Blocks.COBBLESTONE_WALL.getDefaultState());
					else if(state == ACBlocks.ritual_altar.getDefaultState().withProperty(BlockRitualAltar.MATERIAL, BlockRitualAltar.EnumRitualMatType.DARKSTONE_COBBLESTONE))
						world.setBlockState(pos1.up(y), ACBlocks.ritual_altar.getDefaultState());
					else if(state == ACBlocks.ritual_pedestal.getDefaultState().withProperty(BlockRitualAltar.MATERIAL, BlockRitualAltar.EnumRitualMatType.DARKSTONE_COBBLESTONE))
						world.setBlockState(pos1.up(y), ACBlocks.ritual_pedestal.getDefaultState());
					else if(state.getBlock() == ACBlocks.darkstone_brick_slab)
						world.setBlockState(pos1.up(y), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)).withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.SMOOTHBRICK));
					else if(state.getBlock() == ACBlocks.darkstone_cobblestone_slab)
						world.setBlockState(pos1.up(y), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)).withProperty(BlockStoneSlab.VARIANT, BlockStoneSlab.EnumType.COBBLESTONE));
					else if(state.getBlock() == ACBlocks.darkstone_slab)
						world.setBlockState(pos1.up(y), Blocks.STONE_SLAB.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
					else if(state.getBlock() == BlockHandler.Darkstoneslab2)
						world.setBlockState(pos1.up(y), Blocks.DOUBLE_STONE_SLAB.getDefaultState());
					else if(state.getBlock() == ACBlocks.glowing_darkstone_bricks)
						world.setBlockState(pos1.up(y), Blocks.STONEBRICK.getDefaultState().withProperty(BlockStoneBrick.VARIANT, BlockStoneBrick.EnumType.CHISELED));
					else if(state.getBlock() == ACBlocks.darkstone_brick_stairs)
						world.setBlockState(pos1.up(y), Blocks.STONE_BRICK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)));
					else if(state.getBlock() == ACBlocks.darkstone_cobblestone_stairs)
						world.setBlockState(pos1.up(y), Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)));
					else if(state.getBlock() == ACBlocks.darklands_oak_planks)
						world.setBlockState(pos1.up(y), Blocks.PLANKS.getDefaultState());
					else if(state.getBlock() == ACBlocks.darklands_oak_stairs)
						world.setBlockState(pos1.up(y), Blocks.OAK_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)));
					else if(state.getBlock() == ACBlocks.darklands_oak_slab)
						world.setBlockState(pos1.up(y), Blocks.WOODEN_SLAB.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)));
					else if(state.getBlock() == ACBlocks.darklands_oak_fence)
						world.setBlockState(pos1.up(y), Blocks.OAK_FENCE.getDefaultState());
				}

				Chunk c = world.getChunkFromBlockCoords(pos1);
				c.getBiomeArray()[(z & 0xF) << 4 | x & 0xF] = (byte)Biome.getIdForBiome(b);
				c.setModified(true);
				PacketDispatcher.sendToDimension(new CleansingRitualMessage(x, z, Biome.getIdForBiome(b)), world.provider.getDimension());
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
