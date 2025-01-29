/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityResearchTable;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class BlockResearchTable extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

	public BlockResearchTable() {
		super(Material.WOOD, MapColor.BROWN);
		setCreativeTab(ACTabs.tabDecoration);
		setHardness(2.0F);
		setResistance(5.0F);
		setSoundType(SoundType.WOOD);
		setTranslationKey("research_table");
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setLightLevel(0.375F);
	}

	@Override
	public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.05F, 0.0F, 0.05F, 0.95F, 0.9F, 0.95F);
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void randomDisplayTick(IBlockState state, World par1World, BlockPos pos, Random par5Random) {
		super.randomDisplayTick(state, par1World, pos, par5Random);
		if(ACConfig.particleBlock){

			EnumFacing facing = state.getValue(FACING);

			switch(facing) {
			case EAST:
				//large
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.2, pos.getY() + 1.05, pos.getZ() + 0.75, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.2, pos.getY() + 1.05, pos.getZ() + 0.75, 0.0D, 0.0D, 0.0D);
				//medium
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.3, pos.getY() + 1.0, pos.getZ() + 0.77, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.3, pos.getY() + 1.0, pos.getZ() + 0.77, 0.0D, 0.0D, 0.0D);
				//small
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.2, pos.getY() + 0.95, pos.getZ() + 0.65, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.2, pos.getY() + 0.95, pos.getZ() + 0.65, 0.0D, 0.0D, 0.0D);
				break;
			case NORTH:
				//large
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.75, pos.getY() + 1.05, pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.75, pos.getY() + 1.05, pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D);
				//medium
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.75, pos.getY() + 1.0, pos.getZ() + 0.68, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.75, pos.getY() + 1.0, pos.getZ() + 0.68, 0.0D, 0.0D, 0.0D);
				//small
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.65, pos.getY() + 0.95, pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX()  + 0.65, pos.getY() + 0.95, pos.getZ() + 0.8, 0.0D, 0.0D, 0.0D);
				break;
			case SOUTH:
				//large
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.25, pos.getY() + 1.05, pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.25, pos.getY() + 1.05, pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D);
				//medium
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.25, pos.getY() + 1.0, pos.getZ() + 0.32, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.25, pos.getY() + 1.0, pos.getZ() + 0.32, 0.0D, 0.0D, 0.0D);
				//small
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.35, pos.getY() + 0.95, pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.35, pos.getY() + 0.95, pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D);
				break;
			case WEST:
				//large
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.8, pos.getY() + 1.05, pos.getZ() + 0.25, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.8, pos.getY() + 1.05, pos.getZ() + 0.25, 0.0D, 0.0D, 0.0D);
				//medium
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.7, pos.getY() + 1.0, pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.7, pos.getY() + 1.0, pos.getZ() + 0.2, 0.0D, 0.0D, 0.0D);
				//small
				par1World.spawnParticle(EnumParticleTypes.FLAME, pos.getX() + 0.8, pos.getY() + 0.95, pos.getZ() + 0.35, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, pos.getX() + 0.8, pos.getY() + 0.95, pos.getZ() + 0.35, 0.0D, 0.0D, 0.0D);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, EnumFacing side, float par7, float par8, float par9) {
		if(!par1World.isRemote)
			FMLNetworkHandler.openGui(par5EntityPlayer, AbyssalCraft.instance, ACLib.researchTableGuiID, par1World, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityResearchTable();
	}

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

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer.Builder(this).add(FACING).build();
	}
}
