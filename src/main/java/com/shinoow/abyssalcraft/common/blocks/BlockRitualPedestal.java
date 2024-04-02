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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Supplier;

import com.shinoow.abyssalcraft.common.blocks.BlockRitualAltar.EnumRitualMatType;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRitualPedestal;
import com.shinoow.abyssalcraft.lib.util.RitualUtil;
import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;
import com.shinoow.abyssalcraft.lib.util.blocks.SingletonInventoryUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRitualPedestal extends BlockContainer {

	//	public static final PropertyEnum<EnumRitualMatType> MATERIAL = PropertyEnum.create("material", EnumRitualMatType.class);
	public static final Map<EnumRitualMatType, Block> VARIANTS = new HashMap<>();
	private Supplier<IBlockState> dropState;

	public BlockRitualPedestal(Supplier<IBlockState> dropState, int bookType, EnumRitualMatType type) {
		super(Material.ROCK);
		setHardness(6.0F);
		setResistance(12.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(null);
		setHarvestLevel("pickaxe", 0);
		this.dropState = dropState;
		RitualUtil.addPedestalTransformation(dropState, getDefaultState(), bookType);
		VARIANTS.put(type, this);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
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
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {

		return new TileEntityRitualPedestal();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		return SingletonInventoryUtil.handleBlockActivation(world, pos, player, player.getHeldItem(hand));
	}

	@Override
	public Item getItemDropped(IBlockState state, Random random, int j)
	{
		return Item.getItemFromBlock(dropState.get().getBlock());
	}

	@Override
	public int damageDropped(IBlockState state){
		return dropState.get().getBlock().getMetaFromState(dropState.get());
	}

	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
	{
		return new ItemStack(Item.getItemFromBlock(this));
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
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
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
}
