/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import static com.shinoow.abyssalcraft.common.blocks.BlockEnergyRelay.FACING;

import java.util.List;

import com.shinoow.abyssalcraft.common.blocks.BlockTieredEnergyPedestal.EnumDimType;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTieredEnergyRelay;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTieredEnergyRelay extends BlockContainer {

	public static final PropertyEnum<EnumDimType> DIMENSION = PropertyEnum.create("dimension", EnumDimType.class);

	public BlockTieredEnergyRelay(String name) {
		super(Material.ROCK);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DIMENSION, EnumDimType.OVERWORLD));
		setTranslationKey(name);
		setHardness(6.0F);
		setResistance(12.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		IBlockState realstate = getActualState(state, source, pos);
		EnumFacing enumfacing = realstate.getValue(FACING);
		switch (enumfacing)
		{
		case EAST:
			return new AxisAlignedBB(0.0F, 0.1F, 0.1F, 0.6F, 0.9F, 0.9F);
		case WEST:
			return new AxisAlignedBB(0.4F, 0.1F, 0.1F, 1.0F, 0.9F, 0.9F);
		case SOUTH:
			return new AxisAlignedBB(0.1F, 0.1F, 0.0F, 0.9F, 0.9F, 0.6F);
		case NORTH:
			return new AxisAlignedBB(0.1F, 0.1F, 0.4F, 0.9F, 0.9F, 1.0F);
		case UP:
			return new AxisAlignedBB(0.1F, 0.0F, 0.1F, 0.9F, 0.6F, 0.9F);
		case DOWN:
			return new AxisAlignedBB(0.1F, 0.4F, 0.1F, 0.9F, 1.0F, 0.9F);
		default:
			return new AxisAlignedBB(0.1F, 0.0F, 0.1F, 0.9F, 0.6F, 0.9F);
		}
	}

	@Override
	public void getSubBlocks(CreativeTabs par2CreativeTabs, NonNullList<ItemStack> par3List) {
		par3List.add(new ItemStack(this, 1, 0));
		par3List.add(new ItemStack(this, 1, 1));
		par3List.add(new ItemStack(this, 1, 2));
		par3List.add(new ItemStack(this, 1, 3));
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public int damageDropped (IBlockState state) {
		return state.getValue(DIMENSION).getMeta();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		BlockUtil.dropTileEntityAsItemWithExtra(world, pos, state, this);

		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile instanceof TileEntityTieredEnergyRelay){
			((TileEntityTieredEnergyRelay) tile).setFacing(state.getValue(FACING).getIndex());
			if(stack.hasTagCompound() && stack.getTagCompound().hasKey("PotEnergy"))
				((TileEntityTieredEnergyRelay)tile).addEnergy(stack.getTagCompound().getFloat("PotEnergy"));
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new java.util.ArrayList<ItemStack>();
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, facing).withProperty(DIMENSION, EnumDimType.byMetadata(meta));
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, EnumFacing side, float par7, float par8, float par9) {
		if(!par1World.isRemote) {
			TileEntity tileentity = par1World.getTileEntity(pos);
			EnumFacing facing = state.getValue(FACING);
			if(tileentity instanceof TileEntityTieredEnergyRelay) {
				facing = EnumFacing.byIndex(((TileEntityTieredEnergyRelay) tileentity).getFacing());
				facing = EnumFacing.byIndex(facing.ordinal() + 1);
				((TileEntityTieredEnergyRelay) tileentity).setFacing(facing.getIndex());
			}
			par1World.setBlockState(pos, state.withProperty(FACING, facing), 3);
			if(tileentity != null) {
				tileentity.validate();
				par1World.setTileEntity(pos, tileentity);
			}
		}
		return true;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing facing = EnumFacing.NORTH;

		TileEntity tile = BlockUtil.getTileEntitySafely(worldIn, pos);
		if(tile instanceof TileEntityTieredEnergyRelay)
			facing = EnumFacing.byIndex(((TileEntityTieredEnergyRelay) tile).getFacing());

		return state.withProperty(FACING, facing);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(DIMENSION, EnumDimType.byMetadata(meta));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(DIMENSION).getMeta();
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityTieredEnergyRelay();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(FACING).add(DIMENSION).build();
	}
}
