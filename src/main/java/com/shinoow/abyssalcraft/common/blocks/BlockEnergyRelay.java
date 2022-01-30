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

import java.util.List;

import com.shinoow.abyssalcraft.api.energy.IEnergyRelayBlock;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEnergyRelay;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEnergyRelay extends BlockContainer implements IEnergyRelayBlock {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");

	public BlockEnergyRelay() {
		super(Material.ROCK);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setTranslationKey("energyrelay");
		setHardness(6.0F);
		setResistance(12.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		EnumFacing enumfacing = state.getValue(FACING);
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
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityEnergyRelay();
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
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("PotEnergy")){
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof TileEntityEnergyRelay)
				((TileEntityEnergyRelay)tile).addEnergy(stack.getTagCompound().getFloat("PotEnergy"));
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new java.util.ArrayList<>();
	}

	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer, hand).withProperty(FACING, facing);
	}


	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, EnumFacing side, float par7, float par8, float par9) {
		if(!par1World.isRemote) {
			TileEntity tileentity = par1World.getTileEntity(pos);
			par1World.setBlockState(pos, state.cycleProperty(FACING), 3);
			if(tileentity != null) {
				tileentity.validate();
				par1World.setTileEntity(pos, tileentity);
			}
		}
		return true;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

		return getDefaultState().withProperty(FACING, enumfacing);
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(FACING).build();
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		return 500;
	}

	@Override
	public int getRange() {
		return 4;
	}
}
