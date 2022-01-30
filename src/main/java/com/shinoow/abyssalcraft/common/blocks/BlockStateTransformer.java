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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityStateTransformer;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockStateTransformer extends BlockACBasic {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyBool TABLET = PropertyBool.create("tablet");

	public BlockStateTransformer() {
		super(Material.ROCK, 2, 4, SoundType.STONE);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TABLET, false));
		setTranslationKey("statetransformer");
		setCreativeTab(ACTabs.tabDecoration);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.10F, 0.0F, 0.10F, 0.9F, 0.75F, 0.9F);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		boolean b = false;
		TileEntity tile = BlockUtil.getTileEntitySafely(worldIn, pos);
		if(tile instanceof TileEntityStateTransformer)
			b = !((TileEntityStateTransformer)tile).getStackInSlot(0).isEmpty();


		return state.withProperty(TABLET, b);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
		setDefaultFacing(worldIn, pos, state);
	}

	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
	{
		if (!worldIn.isRemote)
		{
			IBlockState block = worldIn.getBlockState(pos.north());
			IBlockState block1 = worldIn.getBlockState(pos.south());
			IBlockState block2 = worldIn.getBlockState(pos.west());
			IBlockState block3 = worldIn.getBlockState(pos.east());
			EnumFacing enumfacing = state.getValue(FACING);

			if (enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock())
				enumfacing = EnumFacing.SOUTH;
			else if (enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock())
				enumfacing = EnumFacing.NORTH;
			else if (enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock())
				enumfacing = EnumFacing.EAST;
			else if (enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock())
				enumfacing = EnumFacing.WEST;

			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state){

		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityStateTransformer();
	}

	@Override
	public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {

		par1World.setBlockState(pos, state.withProperty(FACING, par5EntityLivingBase.getHorizontalFacing().getOpposite()), 2);

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
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, EnumFacing side, float par7, float par8, float par9) {

		if(!par1World.isRemote)
			FMLNetworkHandler.openGui(par5EntityPlayer, AbyssalCraft.instance, ACLib.stateTransformerGuiID, par1World, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void breakBlock(World par1World, BlockPos pos, IBlockState state) {

		TileEntityStateTransformer compressorThing = (TileEntityStateTransformer)par1World.getTileEntity(pos);

		if (compressorThing != null)
			InventoryHelper.dropInventoryItems(par1World, pos, compressorThing);

		super.breakBlock(par1World, pos, state);
	}

	@Override
	public boolean hasComparatorInputOverride(IBlockState state)
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(IBlockState state, World par1World, BlockPos pos)
	{

		TileEntity tile = par1World.getTileEntity(pos);

		if(tile instanceof TileEntityStateTransformer){

			TileEntityStateTransformer thing = (TileEntityStateTransformer)tile;

			if(thing.processingTime > 0) return 0;

			int i = 0;
			float f = 0.0F;

			for (int j = 1; j < thing.getSizeInventory(); ++j)
			{
				ItemStack itemstack = thing.getStackInSlot(j);

				if (!itemstack.isEmpty())
				{
					f += (float)itemstack.getCount() / (float)Math.min(thing.getInventoryStackLimit(), itemstack.getMaxStackSize());
					++i;
				}
			}

			f /= thing.getSizeInventory();
			return MathHelper.floor(f * 14.0F) + (i > 0 ? 1 : 0);
		} else return 0;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		EnumFacing enumfacing = EnumFacing.byIndex(meta);

		if (enumfacing.getAxis() == EnumFacing.Axis.Y)
			enumfacing = EnumFacing.NORTH;

		return getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(FACING).add(TABLET).build();
	}
}
