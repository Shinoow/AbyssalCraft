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
package com.shinoow.abyssalcraft.common.world.gen;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.BlockStatue;
import com.shinoow.abyssalcraft.common.blocks.BlockStatue.EnumDeityType;

public class WorldGenShoggothMonolith extends WorldGenerator {

	@Override
	public boolean generate(World world, Random rand, BlockPos pos) {

		while(world.isAirBlock(pos) && pos.getY() > 2)
			pos = pos.down();

		//	if(world.getBlock(x, y, z).getMaterial() != Material.grass ||
		//	world.getBlock(x, y, z).getMaterial() != Material.ground)
		if(world.getBlockState(pos) != ACBlocks.shoggoth_ooze.getDefaultState())
			return false;
		else {

			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();

			//	for(int x1 = 0; x1 < 7; x1++)
			//	for(int z1 = 0; z1 < 7; z1++)
			//	for(int x2 = 0; x2 < 7; x2++)
			//	for(int z2 = 0; z2 < 7; z2++)
			//	for(int y1 = -1; y1 < 4; y1++){
			//	if(world.getBlock(x + x1, y + y1, z + z1).getMaterial() == Material.grass ||
			//	world.getBlock(x + x1, y + y1, z + z1).getMaterial() == Material.ground)
			//	func_150515_a(world,x + x1, y + y1, z + z1, AbyssalCraft.shoggothBlock);
			//	if(world.getBlock(x - x2, y + y1, z - z2).getMaterial() == Material.grass ||
			//	world.getBlock(x - x2, y + y1, z - z2).getMaterial() == Material.ground)
			//	func_150515_a(world,x - x2, y + y1, z - z2, AbyssalCraft.shoggothBlock);
			//	if(world.getBlock(x + x1, y + y1, z - z2).getMaterial() == Material.grass ||
			//	world.getBlock(x + x1, y + y1, z - z2).getMaterial() == Material.ground)
			//	func_150515_a(world,x + x1, y + y1, z - z2, AbyssalCraft.shoggothBlock);
			//	if(world.getBlock(x - x2, y + y1, z  + z1).getMaterial() == Material.grass ||
			//	world.getBlock(x - x2, y + y1, z  + z1).getMaterial() == Material.ground)
			//	func_150515_a(world,x - x2, y + y1, z  + z1, AbyssalCraft.shoggothBlock);
			//	}

			int max = rand.nextInt(8) + 5;
			for(int i = 0; i < max; i++){
				setBlockAndNotifyAdequately(world, new BlockPos(x, y + i, z), ACBlocks.stone.getStateFromMeta(7));
				setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + i, z), ACBlocks.stone.getStateFromMeta(7));
				setBlockAndNotifyAdequately(world, new BlockPos(x -1, y + i, z), ACBlocks.stone.getStateFromMeta(7));
				setBlockAndNotifyAdequately(world, new BlockPos(x, y + i, z + 1), ACBlocks.stone.getStateFromMeta(7));
				setBlockAndNotifyAdequately(world, new BlockPos(x, y + i, z -1), ACBlocks.stone.getStateFromMeta(7));
				setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + i, z + 1), ACBlocks.stone.getStateFromMeta(7));
				setBlockAndNotifyAdequately(world, new BlockPos(x -1, y + i, z -1), ACBlocks.stone.getStateFromMeta(7));
				setBlockAndNotifyAdequately(world, new BlockPos(x + 1, y + i, z -1), ACBlocks.stone.getStateFromMeta(7));
				setBlockAndNotifyAdequately(world, new BlockPos(x -1, y + i, z + 1), ACBlocks.stone.getStateFromMeta(7));
			}
			setBlockAndNotifyAdequately(world, pos, ACBlocks.shoggoth_biomass.getDefaultState());
			setBlockAndNotifyAdequately(world, new BlockPos(x, y + max, z), getStatue(rand).withProperty(BlockStatue.FACING, EnumFacing.getHorizontal(rand.nextInt(3))));

			world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 2, world.rand.nextFloat() * 0.1F * 0.9F);

			return true;
		}
	}

	private IBlockState getStatue(Random rand){
		return ACBlocks.statue.getDefaultState().withProperty(BlockStatue.TYPE, EnumDeityType.byMetadata(rand.nextInt(7)));
	}
}
