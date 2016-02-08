/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDrT;

public class BlockDreadSapling extends BlockBush implements IGrowable {

	public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 1);

	public BlockDreadSapling()
	{
		super();
		float f = 0.4F;
		setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		setCreativeTab(AbyssalCraft.tabDecoration);
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
		{
			super.updateTick(worldIn, pos, state, rand);

			if (worldIn.getLightFromNeighbors(pos.up()) >= 9 && rand.nextInt(7) == 0)
				grow(worldIn, rand, pos, state);
		}
	}

	public void growTree(World world, BlockPos pos, IBlockState state, Random rand)
	{
		if (!net.minecraftforge.event.terraingen.TerrainGen.saplingGrowTree(world, rand, pos)) return;

		world.setBlockState(pos, Blocks.air.getDefaultState(), 1);
		Object obj = new WorldGenDrT(true);
		if(!((WorldGenerator) obj).generate(world, rand, pos))
			world.setBlockState(pos, getDefaultState(), 4);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state,
			boolean isClient) {

		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos,
			IBlockState state) {

		return worldIn.rand.nextFloat() < 0.45D;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {

		if (state.getValue(STAGE).intValue() == 0)
			worldIn.setBlockState(pos, state.cycleProperty(STAGE), 4);
		else
			growTree(worldIn, pos, state, rand);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(STAGE, Integer.valueOf((meta & 8) >> 3));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;
		i = i | state.getValue(STAGE).intValue() << 3;
		return i;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {STAGE});
	}
}