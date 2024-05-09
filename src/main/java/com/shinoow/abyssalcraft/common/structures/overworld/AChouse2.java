/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures.overworld;

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AChouse2 extends StructureDarklandsBase {

	@Override
	public boolean generate(World world, Random random, BlockPos pos) {

		pos = pos.add(-6, 1, -6);

		int i = pos.getX();
		int j = pos.getY();
		int k = pos.getZ();

		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 0, j + 0, k + 4), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 0, j + 0, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 0, j + 0, k + 6), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 0, j + 0, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 0, j + 0, k + 8), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 1, j + 0, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 1, j + 0, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 1, j + 0, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 2, j + 0, k + 2), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 0, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 2, j + 0, k + 10), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 1, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 2, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 3, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 3, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 3, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 3, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 2, j + 3, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 2, j + 4, k + 4), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 2, j + 4, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 2, j + 4, k + 6), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 2, j + 4, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 2, j + 4, k + 8), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 3, j + 0, k + 1), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 0, k + 10), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 3, j + 0, k + 11), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 1, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 3, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 3, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 3, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 3, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 3, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 3, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 3, j + 3, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 3, j + 4, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 3, j + 4, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 4, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 4, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 4, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 4, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 3, j + 4, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 3, j + 5, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 3, j + 5, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 5, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 5, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 5, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 5, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 3, j + 5, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 3, j + 6, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 3, j + 6, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 6, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 6, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 6, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 6, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 3, j + 6, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 3, j + 7, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 3, j + 7, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 7, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 7, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 7, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 3, j + 7, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 3, j + 7, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 3, j + 8, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 3, j + 8, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 3, j + 8, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 3, j + 8, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 4, j + 0, k + 0), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		if(ACConfig.darkstone_brick_stairs) {
			world.setBlockState(new BlockPos(i + 3, j + 8, k + 4), ACBlocks.darkstone_brick_stairs.getStateFromMeta(0), 2);
			world.setBlockState(new BlockPos(i + 3, j + 8, k + 6), ACBlocks.darkstone_brick_stairs.getStateFromMeta(0), 2);
			world.setBlockState(new BlockPos(i + 3, j + 8, k + 8), ACBlocks.darkstone_brick_stairs.getStateFromMeta(0), 2);
		}
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 1), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 0, k + 11), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 4, j + 0, k + 12), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 1, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 3, k + 10), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 4, j + 4, k + 2), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 4, j + 4, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 4, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 4, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 4, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 4, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 4, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 4, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 4, j + 4, k + 10), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 4, j + 5, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 5, k + 4), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 5, k + 5), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 5, k + 6), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 5, k + 7), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 5, k + 8), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 5, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 6, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 6, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 6, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 6, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 6, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 6, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 6, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 9), getBrick(random), 2);
		if(ACConfig.dreadstone_brick_stairs)
			world.setBlockState(new BlockPos(i + 4, j + 8, k + 3), ACBlocks.darkstone_brick_stairs.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 4, j + 8, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 8, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 8, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 8, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 8, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_stairs)
			world.setBlockState(new BlockPos(i + 4, j + 8, k + 9), ACBlocks.darkstone_brick_stairs.getStateFromMeta(3), 2);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 4, j + 9, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 4, j + 10, k + 4), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 4, j + 10, k + 6), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 4, j + 10, k + 8), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		if(ACConfig.darkstone_brick_stairs) {
			world.setBlockState(new BlockPos(i + 4, j + 10, k + 5), ACBlocks.darkstone_brick_stairs.getStateFromMeta(0), 2);
			world.setBlockState(new BlockPos(i + 4, j + 10, k + 7), ACBlocks.darkstone_brick_stairs.getStateFromMeta(0), 2);
			world.setBlockState(new BlockPos(i + 5, j + 0, k + 0), ACBlocks.darkstone_brick_stairs.getStateFromMeta(2), 2);
		}
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 1), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 0, k + 11), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 5, j + 0, k + 12), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 1, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 3, k + 10), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 5, j + 4, k + 2), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 5, j + 4, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 4, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 4, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 4, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 4, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 4, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 4, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 5, j + 4, k + 10), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 5, j + 5, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 5, k + 4), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 5, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 5, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 5, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 5, k + 8), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 5, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 6, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 6, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 6, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 6, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 6, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 6, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 6, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 7, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 5, j + 8, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 5, j + 8, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 5, j + 8, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 4), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 5, j + 9, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(13), 2);
			world.setBlockState(new BlockPos(i + 5, j + 9, k + 6), ACBlocks.darkstone_brick_slab.getStateFromMeta(13), 2);
			world.setBlockState(new BlockPos(i + 5, j + 9, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(13), 2);
		}
		world.setBlockState(new BlockPos(i + 5, j + 9, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_stairs)
			world.setBlockState(new BlockPos(i + 5, j + 10, k + 4), ACBlocks.darkstone_brick_stairs.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 5, j + 10, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 10, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 5, j + 10, k + 7), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 5, j + 11, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 5, j + 11, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		if(ACConfig.darkstone_brick_stairs) {
			world.setBlockState(new BlockPos(i + 5, j + 10, k + 8), ACBlocks.darkstone_brick_stairs.getStateFromMeta(3), 2);
			world.setBlockState(new BlockPos(i + 5, j + 11, k + 6), ACBlocks.darkstone_brick_stairs.getStateFromMeta(0), 2);
			world.setBlockState(new BlockPos(i + 6, j + 0, k + 0), ACBlocks.darkstone_brick_stairs.getStateFromMeta(2), 2);
		}
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 1), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 2), Blocks.WOOL.getStateFromMeta(14), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 3), Blocks.WOOL.getStateFromMeta(14), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 4), Blocks.WOOL.getStateFromMeta(14), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 5), Blocks.WOOL.getStateFromMeta(14), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 6), Blocks.WOOL.getStateFromMeta(14), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 7), Blocks.WOOL.getStateFromMeta(14), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 0, k + 11), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 6, j + 0, k + 12), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 2), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 2), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 10), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 6, j + 4, k + 2), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 6, j + 4, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 4, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 4, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 4, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 4, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 4, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 6, j + 4, k + 10), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 6, j + 5, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 5, k + 4), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 5, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 5, k + 6), random.nextBoolean() ? Blocks.OBSIDIAN.getDefaultState() : Blocks.ENCHANTING_TABLE.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 5, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 5, k + 8), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 5, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 6, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 6, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 6, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 6, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 6, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 6, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 6, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_stairs)
			world.setBlockState(new BlockPos(i + 6, j + 8, k + 3), ACBlocks.darkstone_brick_stairs.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 8, k + 9), ACBlocks.darkstone_brick_stairs.getStateFromMeta(3), 2);
		world.setBlockState(new BlockPos(i + 6, j + 9, k + 4), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 6, j + 9, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(13), 2);
		world.setBlockState(new BlockPos(i + 6, j + 9, k + 6), ACBlocks.glowing_darkstone_bricks.getDefaultState(), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 6, j + 9, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(13), 2);
		world.setBlockState(new BlockPos(i + 6, j + 9, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 6, j + 10, k + 4), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 6, j + 10, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 6, j + 10, k + 6), ACBlocks.stone.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 6, j + 10, k + 7), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 6, j + 10, k + 8), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		if(ACConfig.darkstone_brick_stairs)
			world.setBlockState(new BlockPos(i + 6, j + 11, k + 5), ACBlocks.darkstone_brick_stairs.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 6, j + 11, k + 6), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 6, j + 12, k + 6), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		if(ACConfig.darkstone_brick_stairs) {
			world.setBlockState(new BlockPos(i + 6, j + 11, k + 7), ACBlocks.darkstone_brick_stairs.getStateFromMeta(3), 2);
			world.setBlockState(new BlockPos(i + 7, j + 0, k + 0), ACBlocks.darkstone_brick_stairs.getStateFromMeta(2), 2);
		}
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 1), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 0, k + 11), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 7, j + 0, k + 12), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 1, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 2, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 2, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 2, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 2, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 2, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 2, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 2, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 2, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 3, k + 10), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 7, j + 4, k + 2), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 7, j + 4, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 4, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 4, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 4, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 4, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 4, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 4, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 7, j + 4, k + 10), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 7, j + 5, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 5, k + 4), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 5, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 5, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 5, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 5, k + 8), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 5, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 6, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 6, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 6, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 6, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 6, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 6, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 6, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 7, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 7, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 7, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 7, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 7, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 7, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 7, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 7, j + 8, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 7, j + 8, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 8, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 8, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 8, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 7, j + 8, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 7, j + 8, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 7, j + 9, k + 4), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 7, j + 9, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(13), 2);
			world.setBlockState(new BlockPos(i + 7, j + 9, k + 6), ACBlocks.darkstone_brick_slab.getStateFromMeta(13), 2);
			world.setBlockState(new BlockPos(i + 7, j + 9, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(13), 2);
		}
		world.setBlockState(new BlockPos(i + 7, j + 9, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_stairs)
			world.setBlockState(new BlockPos(i + 7, j + 10, k + 4), ACBlocks.darkstone_brick_stairs.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 7, j + 10, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 10, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 7, j + 10, k + 7), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 7, j + 11, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 7, j + 11, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 8, j + 0, k + 0), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		if(ACConfig.darkstone_brick_stairs) {
			world.setBlockState(new BlockPos(i + 7, j + 10, k + 8), ACBlocks.darkstone_brick_stairs.getStateFromMeta(3), 2);
			world.setBlockState(new BlockPos(i + 7, j + 11, k + 6), ACBlocks.darkstone_brick_stairs.getStateFromMeta(1), 2);
		}
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 1), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 0, k + 11), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 8, j + 0, k + 12), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 8, j + 1, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 1, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 1, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 1, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 1, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 1, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 1, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 1, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 1, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 2, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 2, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 2, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 2, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 2, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 2, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 2, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 2, k + 10), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 3, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 3, k + 3), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 3, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 3, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 3, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 3, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 3, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 3, k + 9), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 3, k + 10), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 8, j + 4, k + 2), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 8, j + 4, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 4, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 4, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 4, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 4, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 4, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 4, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 8, j + 4, k + 10), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 8, j + 5, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 5, k + 4), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 5, k + 5), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 5, k + 6), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 5, k + 7), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 5, k + 8), random.nextBoolean() ? Blocks.PLANKS.getDefaultState() : Blocks.BOOKSHELF.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 5, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 6, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 6, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 6, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 6, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 6, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 6, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 6, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 7, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 7, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 7, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 7, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 8, j + 7, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 7, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_stairs)
			world.setBlockState(new BlockPos(i + 8, j + 8, k + 3), ACBlocks.darkstone_brick_stairs.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 8, j + 8, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 8, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 8, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 8, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 8, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_stairs)
			world.setBlockState(new BlockPos(i + 8, j + 8, k + 9), ACBlocks.darkstone_brick_stairs.getStateFromMeta(3), 2);
		world.setBlockState(new BlockPos(i + 8, j + 9, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 9, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 9, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 9, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 8, j + 9, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 8, j + 10, k + 4), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 8, j + 10, k + 6), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 8, j + 10, k + 8), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 9, j + 0, k + 1), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 8, j + 10, k + 5), ACBlocks.darkstone_brick_stairs.getStateFromMeta(1), 2);
			world.setBlockState(new BlockPos(i + 8, j + 10, k + 7), ACBlocks.darkstone_brick_stairs.getStateFromMeta(1), 2);
		}
		world.setBlockState(new BlockPos(i + 9, j + 0, k + 2), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 0, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 0, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 0, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 0, k + 10), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 9, j + 0, k + 11), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 9, j + 1, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 1, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 1, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 1, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 1, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 1, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 1, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 2, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 2, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 2, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 2, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 2, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 2, k + 9), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 3, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 3, k + 4), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 3, k + 5), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 3, k + 6), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 3, k + 7), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 3, k + 8), Blocks.AIR.getDefaultState(), 2);
		world.setBlockState(new BlockPos(i + 9, j + 3, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 9, j + 4, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 9, j + 4, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 4, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 4, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 4, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 4, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 9, j + 4, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 9, j + 5, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 9, j + 5, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 5, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 5, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 5, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 5, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 9, j + 5, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 9, j + 6, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 9, j + 6, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 6, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 6, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 6, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 6, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 9, j + 6, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 9, j + 7, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 9, j + 7, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 7, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 7, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 7, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 9, j + 7, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 9, j + 7, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 9, j + 8, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 9, j + 8, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 9, j + 8, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 9, j + 8, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 10, j + 0, k + 2), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		if(ACConfig.darkstone_brick_stairs) {
			world.setBlockState(new BlockPos(i + 9, j + 8, k + 4), ACBlocks.darkstone_brick_stairs.getStateFromMeta(1), 2);
			world.setBlockState(new BlockPos(i + 9, j + 8, k + 6), ACBlocks.darkstone_brick_stairs.getStateFromMeta(1), 2);
			world.setBlockState(new BlockPos(i + 9, j + 8, k + 8), ACBlocks.darkstone_brick_stairs.getStateFromMeta(1), 2);
		}
		world.setBlockState(new BlockPos(i + 10, j + 0, k + 3), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 0, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 0, k + 9), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab)
			world.setBlockState(new BlockPos(i + 10, j + 0, k + 10), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		world.setBlockState(new BlockPos(i + 10, j + 1, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 1, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 1, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 1, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 1, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 2, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 2, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 2, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 2, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 2, k + 8), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 3, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 3, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 3, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 3, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 10, j + 3, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 10, j + 4, k + 4), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 10, j + 4, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 10, j + 4, k + 6), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 10, j + 4, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 10, j + 4, k + 8), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 11, j + 0, k + 3), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 11, j + 0, k + 4), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 11, j + 0, k + 5), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 11, j + 0, k + 6), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 11, j + 0, k + 7), getBrick(random), 2);
		world.setBlockState(new BlockPos(i + 11, j + 0, k + 8), getBrick(random), 2);
		if(ACConfig.darkstone_brick_slab) {
			world.setBlockState(new BlockPos(i + 11, j + 0, k + 9), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 12, j + 0, k + 4), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 12, j + 0, k + 5), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 12, j + 0, k + 6), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 12, j + 0, k + 7), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
			world.setBlockState(new BlockPos(i + 12, j + 0, k + 8), ACBlocks.darkstone_brick_slab.getStateFromMeta(5), 2);
		}
		world.setBlockState(new BlockPos(i + 3, j + 2, k + 6), Blocks.REDSTONE_TORCH.getStateFromMeta(1), 2);
		world.setBlockState(new BlockPos(i + 4, j + 2, k + 9), Blocks.REDSTONE_TORCH.getStateFromMeta(4), 2);
		world.setBlockState(new BlockPos(i + 4, j + 7, k + 6), Blocks.REDSTONE_TORCH.getStateFromMeta(1), 2);
		world.setBlockState(new BlockPos(i + 5, j + 2, k + 3), Blocks.REDSTONE_TORCH.getStateFromMeta(3), 2);
		world.setBlockState(new BlockPos(i + 6, j + 1, k + 7), Blocks.LADDER.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 6, j + 2, k + 7), Blocks.LADDER.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 6, j + 3, k + 7), Blocks.LADDER.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 6, j + 4, k + 7), Blocks.LADDER.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 4), Blocks.REDSTONE_TORCH.getStateFromMeta(3), 2);
		world.setBlockState(new BlockPos(i + 6, j + 7, k + 8), Blocks.REDSTONE_TORCH.getStateFromMeta(4), 2);
		world.setBlockState(new BlockPos(i + 7, j + 2, k + 3), Blocks.REDSTONE_TORCH.getStateFromMeta(3), 2);
		world.setBlockState(new BlockPos(i + 8, j + 2, k + 9), Blocks.REDSTONE_TORCH.getStateFromMeta(4), 2);
		world.setBlockState(new BlockPos(i + 8, j + 7, k + 6), Blocks.REDSTONE_TORCH.getStateFromMeta(2), 2);
		world.setBlockState(new BlockPos(i + 9, j + 2, k + 6), Blocks.REDSTONE_TORCH.getStateFromMeta(2), 2);

		return true;
	}
}
