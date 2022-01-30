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
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.ritual.EnumRitualParticle;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.util.BiomeUtil;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.util.ScheduledProcess;
import com.shinoow.abyssalcraft.lib.util.Scheduler;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.*;

public class NecronomiconCorruptionRitual extends NecronomiconRitual {

	public NecronomiconCorruptionRitual() {
		super("corruption", 4, 0, 10000F, true, new Object[]{new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
				new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
				new ItemStack(ACBlocks.shub_niggurath_statue)}, ACBlocks.darkstone, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
						new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
						new ItemStack(ACBlocks.shub_niggurath_statue)}, ACBlocks.darkstone, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
								new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
								new ItemStack(ACBlocks.shub_niggurath_statue)}, ACBlocks.darkstone, new ItemStack[]{new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.cthulhu_statue),
										new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.yog_sothoth_statue),
										new ItemStack(ACBlocks.shub_niggurath_statue)}, ACBlocks.darkstone});
		setRitualParticle(EnumRitualParticle.PE_STREAM);
	}

	@Override
	public boolean canCompleteRitual(World world, BlockPos pos, EntityPlayer player) {
		for(int x = pos.getX() - 24; x < pos.getX() + 25; x++)
			for(int z = pos.getZ() - 24; z < pos.getZ() + 25; z++){
				BlockPos pos1 = new BlockPos(x, 0, z);
				Biome b = world.getBiome(pos1);
				if(b instanceof BiomePlains || b instanceof BiomeHills || b instanceof BiomeForest || b == Biomes.ICE_MOUNTAINS)
					return true;
			}
		return false;
	}

	@Override
	protected void completeRitualClient(World world, BlockPos pos, EntityPlayer player) {
		SpecialTextUtil.JzaharText(I18n.format("message.jzahar.corrupting"));
	}

	@Override
	protected void completeRitualServer(World world, BlockPos pos, EntityPlayer player) {
		int num = 1, num2 = 0, range = ACConfig.corruptionRitualRange * 8;
		boolean b1 = world.rand.nextBoolean();
		for(int x = pos.getX() - range; x < pos.getX() + range + 1; x++)
			for(int z = pos.getZ() - range; z < pos.getZ() + range + 1; z++){

				BlockPos pos1 = new BlockPos(x, 0, z);

				if(!isApplicable(world, pos1)) continue;

				Scheduler.schedule(new ScheduledProcess(num * 2) {

					@Override
					public void execute() {
						Biome b = getDarklandsBiome(world.getBiome(pos1), b1);

						for(int y = 0; y < 256; y++){
							if(world.isAirBlock(pos1.up(y))) continue;
							IBlockState state = world.getBlockState(pos1.up(y));
							if(state.getBlock() == Blocks.STONE && state.getValue(BlockStone.VARIANT) != BlockStone.EnumType.STONE)
								world.setBlockState(pos1.up(y), ACBlocks.darkstone.getDefaultState(), 2);
							else if(state.getBlock() == Blocks.LEAVES)
								world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_leaves.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, state.getValue(BlockLeaves.CHECK_DECAY)).withProperty(BlockLeaves.DECAYABLE, state.getValue(BlockLeaves.DECAYABLE)), 2);
							else if(state.getBlock() == Blocks.LOG)
								world.setBlockState(pos1.up(y), (world.rand.nextInt(10) == 0 ? ACBlocks.darklands_oak_wood_2 : ACBlocks.darklands_oak_wood).getDefaultState().withProperty(BlockLog.LOG_AXIS, state.getValue(BlockLog.LOG_AXIS)), 2);
							else if(state.getBlock() == Blocks.COBBLESTONE)
								world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone.getDefaultState(), 2);
							else if(state.getBlock() == Blocks.STONEBRICK)
								switch(state.getValue(BlockStoneBrick.VARIANT)){
								case CHISELED:
									world.setBlockState(pos1.up(y), ACBlocks.chiseled_darkstone_brick.getDefaultState(), 2);
									break;
								case CRACKED:
									world.setBlockState(pos1.up(y), ACBlocks.cracked_darkstone_brick.getDefaultState(), 2);
									break;
								default:
									world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick.getDefaultState(), 2);
									break;
								}
							else if(state.getBlock() == Blocks.COBBLESTONE_WALL)
								world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_wall.getDefaultState(), 2);
							else if(state == ACBlocks.ritual_altar_stone.getDefaultState())
								world.setBlockState(pos1.up(y), ACBlocks.ritual_altar_darkstone.getDefaultState(), 2);
							else if(state == ACBlocks.ritual_pedestal_stone.getDefaultState())
								world.setBlockState(pos1.up(y), ACBlocks.ritual_pedestal_darkstone.getDefaultState(), 2);
							else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.SMOOTHBRICK)
								world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
							else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.COBBLESTONE)
								world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
							else if(state.getBlock() == Blocks.STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE)
								world.setBlockState(pos1.up(y), ACBlocks.darkstone_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
							else if(state.getBlock() == Blocks.DOUBLE_STONE_SLAB && state.getValue(BlockStoneSlab.VARIANT) == BlockStoneSlab.EnumType.STONE)
								world.setBlockState(pos1.up(y), BlockHandler.Darkstoneslab2.getDefaultState(), 2);
							else if(state.getBlock() == Blocks.STONE_BRICK_STAIRS)
								world.setBlockState(pos1.up(y), ACBlocks.darkstone_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)), 2);
							else if(state.getBlock() == Blocks.STONE_STAIRS)
								world.setBlockState(pos1.up(y), ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)), 2);
							else if(state.getBlock() == Blocks.PLANKS)
								world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_planks.getDefaultState(), 2);
							else if(state.getBlock() == Blocks.OAK_STAIRS)
								world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_stairs.getDefaultState().withProperty(BlockStairs.FACING, state.getValue(BlockStairs.FACING)).withProperty(BlockStairs.HALF, state.getValue(BlockStairs.HALF)), 2);
							else if(state.getBlock() == Blocks.WOODEN_SLAB)
								world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_slab.getDefaultState().withProperty(BlockSlab.HALF, state.getValue(BlockSlab.HALF)), 2);
							else if(state.getBlock() == Blocks.OAK_FENCE)
								world.setBlockState(pos1.up(y), ACBlocks.darklands_oak_fence.getDefaultState(), 2);
						}

						BiomeUtil.updateBiome(world, pos1, b, true);
					}

				});
				num2++;
				if(num2 % 256 == 0)
					num++;
			}
	}

	private boolean isApplicable(World world, BlockPos pos) {
		Biome b = world.getBiome(pos);
		return b instanceof BiomePlains || b instanceof BiomeHills || b instanceof BiomeForest || b == Biomes.ICE_MOUNTAINS;
	}

	private Biome getDarklandsBiome(Biome b, boolean b1){
		if(b instanceof BiomePlains)
			return b1 ? ACBiomes.darklands_plains : ACBiomes.darklands;
		else if(b instanceof BiomeForest)
			return ACBiomes.darklands_forest;
		else if(b == Biomes.ICE_MOUNTAINS || b instanceof BiomeHills && b1)
			return ACBiomes.darklands_mountains;
		else if(b instanceof BiomeHills)
			return ACBiomes.darklands_hills;
		return ACBiomes.darklands;
	}
}
