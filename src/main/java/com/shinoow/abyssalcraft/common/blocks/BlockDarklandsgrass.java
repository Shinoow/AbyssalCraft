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

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockDarklandsgrass extends Block implements IGrowable {

	public static final PropertyBool SNOWY = PropertyBool.create("snowy");

	public BlockDarklandsgrass()
	{
		super(Material.grass);
		setDefaultState(blockState.getBaseState().withProperty(SNOWY, Boolean.valueOf(false)));
		setTickRandomly(true);
		setCreativeTab(ACTabs.tabBlock);
		setStepSound(SoundType.PLANT);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		Block block = worldIn.getBlockState(pos.up()).getBlock();
		return state.withProperty(SNOWY, Boolean.valueOf(block == Blocks.snow || block == Blocks.snow_layer));
	}

	@Override
	public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random)
	{
		super.randomDisplayTick(state, par1World, pos, par5Random);

		if(AbyssalCraft.particleBlock)
			if (par5Random.nextInt(10) == 0)
				par1World.spawnParticle(EnumParticleTypes.PORTAL, pos.getX() + par5Random.nextFloat(), pos.getY() + 1.1F, pos.getZ() + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
	}
	/**
	 * Ticks the block if it's been scheduled
	 */
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
			if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getBlock().getLightOpacity(worldIn.getBlockState(pos.up()), worldIn, pos.up()) > 2)
				worldIn.setBlockState(pos, Blocks.dirt.getDefaultState());
			else if (worldIn.getLightFromNeighbors(pos.up()) >= 9)
				for (int i = 0; i < 4; ++i)
				{
					BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
					Block block = worldIn.getBlockState(blockpos.up()).getBlock();
					IBlockState iblockstate = worldIn.getBlockState(blockpos);

					if (iblockstate.getBlock() == Blocks.dirt && iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && block.getLightOpacity(worldIn.getBlockState(blockpos.up()), worldIn, blockpos.up()) <= 2)
						worldIn.setBlockState(blockpos, ACBlocks.darklands_grass.getDefaultState());
				}
	}

	@Override
	public boolean canSustainPlant(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing direction, IPlantable plantable)
	{
		Block plant = plantable.getPlant(world, pos.up()).getBlock();
		if (plant == ACBlocks.dreadlands_sapling || plant == ACBlocks.darklands_oak_sapling || plant instanceof BlockFlower ||
				plant instanceof BlockMushroom || plant instanceof BlockTallGrass)
			return true;
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
	{
		return Blocks.dirt.getItemDropped(state, par2Random, par3);
	}

	@Override
	public boolean canGrow(World worldIn, BlockPos pos, IBlockState state,
			boolean isClient) {

		return true;
	}

	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos,
			IBlockState state) {

		return true;
	}

	@Override
	public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state)
	{
		BlockPos blockpos = pos.up();

		for (int i = 0; i < 128; ++i)
		{
			BlockPos blockpos1 = blockpos;
			int j = 0;

			while (true)
			{
				if (j >= i / 16)
				{
					if (worldIn.isAirBlock(blockpos1))
						if (rand.nextInt(8) == 0)
						{
							BlockFlower.EnumFlowerType blockflower$enumflowertype = worldIn.getBiomeGenForCoords(blockpos1).pickRandomFlower(rand, blockpos1);
							BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
							IBlockState iblockstate = blockflower.getDefaultState().withProperty(blockflower.getTypeProperty(), blockflower$enumflowertype);

							if (blockflower.canBlockStay(worldIn, blockpos1, iblockstate))
								worldIn.setBlockState(blockpos1, iblockstate, 3);
						}
						else
						{
							IBlockState iblockstate1 = Blocks.tallgrass.getDefaultState().withProperty(BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);

							if (Blocks.tallgrass.canBlockStay(worldIn, blockpos1, iblockstate1))
								worldIn.setBlockState(blockpos1, iblockstate1, 3);
						}

					break;
				}

				blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);

				if (worldIn.getBlockState(blockpos1.down()).getBlock() != ACBlocks.darklands_grass || worldIn.getBlockState(blockpos1).isNormalCube())
					break;

				++j;
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT_MIPPED;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {SNOWY});
	}
}