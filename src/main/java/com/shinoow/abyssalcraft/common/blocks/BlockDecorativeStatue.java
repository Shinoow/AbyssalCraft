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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.HashMap;
import java.util.Map;

import com.shinoow.abyssalcraft.common.blocks.BlockStatue.EnumDeityType;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJModel;

public class BlockDecorativeStatue extends Block {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	public static final Map<EnumDeityType, Block> VARIANTS = new HashMap<>();

	public BlockDecorativeStatue(EnumDeityType type) {
		super(Material.ROCK);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setHardness(6.0F);
		setResistance(12.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
		VARIANTS.put(type, this);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	//	@Override
	//	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	//	{
	//		EnumFacing facing = EnumFacing.NORTH;
	//
	//		TileEntity tile = BlockUtil.getTileEntitySafely(worldIn, pos);
	//		if(tile instanceof TileEntityDecorativeStatue)
	//			facing = EnumFacing.getFront(((TileEntityDecorativeStatue) tile).getFacing());
	//
	//		return state.withProperty(FACING, facing);
	//	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.byIndex(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}
	//
	//	@Override
	//	public int damageDropped (IBlockState state) {
	//		return state.getValue(TYPE).getMeta();
	//	}
	//
	//	@Override
	//	public void getSubBlocks(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
	//		for(int i = 0; i < EnumDeityType.values().length; i++)
	//			par3List.add(new ItemStack(this, 1, i));
	//	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
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
	//
	//	@Override
	//	public EnumBlockRenderType getRenderType(IBlockState state) {
	//		return EnumBlockRenderType.MODEL;
	//	}

	//	@Override
	//	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	//	{
	//		TileEntity tile = worldIn.getTileEntity(pos);
	//		if(tile instanceof TileEntityDecorativeStatue)
	//			((TileEntityDecorativeStatue) tile).setFacing(state.getValue(FACING).getIndex());
	//	}
	//
	//	@Override
	//	public TileEntity createNewTileEntity(World worldIn, int meta) {
	//
	//		return new TileEntityDecorativeStatue();
	//	}

	@SuppressWarnings("deprecation")
	@Override
	public BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(FACING).add(OBJModel.OBJProperty.INSTANCE).build();
	}
}
