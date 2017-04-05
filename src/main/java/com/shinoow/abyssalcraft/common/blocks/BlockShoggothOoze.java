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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

public class BlockShoggothOoze extends BlockACBasic {

	public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);
	protected static final AxisAlignedBB[] OOZE_AABB = new AxisAlignedBB[] {new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};

	public BlockShoggothOoze(){
		super(Material.GROUND, 1.0F, 1.0F, SoundType.SAND);
		setDefaultState(blockState.getBaseState().withProperty(LAYERS, Integer.valueOf(1)));
		setTickRandomly(ACConfig.oozeExpire);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return OOZE_AABB[state.getValue(LAYERS).intValue()];
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos).getValue(LAYERS).intValue() < 5;
	}

	@Override
	public boolean isFullyOpaque(IBlockState state)
	{
		return state.getValue(LAYERS).intValue() == 8;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		int i = blockState.getValue(LAYERS).intValue() - 1;
		AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
		return new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX, i * 0.125F, axisalignedbb.maxZ);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
	{
		if(!(entity instanceof EntityLesserShoggoth)){
			entity.motionX *= 0.4D;
			entity.motionZ *= 0.4D;
		}
	}

	@Override
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
		if(ACConfig.oozeExpire)
			if (!par1World.isRemote && par5Random.nextInt(10) == 0 && par1World.getLightFromNeighbors(pos.up()) >= 13)
				if(state.getValue(LAYERS).intValue() == 8)
					par1World.setBlockState(pos, getState(par1World));
	}

	private IBlockState getState(World world){
		if(world.provider.getDimension() == ACLib.abyssal_wasteland_id)
			return ACBlocks.abyssal_sand.getDefaultState();
		if(world.provider.getDimension() == ACLib.dreadlands_id)
			return ACBlocks.dreadlands_dirt.getDefaultState();
		if(world.provider.getDimension() == ACLib.omothol_id)
			return ACBlocks.omothol_stone.getDefaultState();
		if(world.provider.getDimension() == ACLib.dark_realm_id)
			return ACBlocks.darkstone.getDefaultState();
		if(world.provider.getDimension() == -1)
			return Blocks.NETHERRACK.getDefaultState();
		if(world.provider.getDimension() == 1)
			return Blocks.END_STONE.getDefaultState();
		return Blocks.DIRT.getDefaultState();
	}

	@Override
	public int tickRate(World worldIn)
	{
		return ACConfig.oozeExpire ? 200 : super.tickRate(worldIn);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		IBlockState iblockstate = worldIn.getBlockState(pos.down());
		Block block = iblockstate.getBlock();
		return block != ACBlocks.shoggoth_biomass ? iblockstate.getBlock().isLeaves(iblockstate, worldIn, pos.down()) ? true : block == this && iblockstate.getValue(LAYERS).intValue() == 8 ? true : iblockstate.isOpaqueCube() && iblockstate.getMaterial().blocksMovement() : false;
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn)
	{
		if(state.getValue(LAYERS) < 8)
			checkAndDropBlock(worldIn, pos, state);
	}

	private boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!canPlaceBlockAt(worldIn, pos))
		{
			worldIn.setBlockToAir(pos);
			return false;
		} else
			return true;
	}

	@Override
	public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack)
	{
		super.harvestBlock(worldIn, player, pos, state, te, stack);
		worldIn.setBlockToAir(pos);
	}

	@Override
	@Nullable
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side)
	{
		if (side == EnumFacing.UP)
			return true;
		else
		{
			IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
			return iblockstate.getBlock() == this && iblockstate.getValue(LAYERS).intValue() >= blockState.getValue(LAYERS).intValue() ? true : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
		}
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(LAYERS, Integer.valueOf((meta & 7) + 1));
	}

	@Override
	public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos)
	{
		return worldIn.getBlockState(pos).getValue(LAYERS).intValue() == 1;
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(LAYERS).intValue() - 1;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(LAYERS).build();
	}
}
