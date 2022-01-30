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
import java.util.List;
import java.util.Map;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.IEnergyBlock;
import com.shinoow.abyssalcraft.common.blocks.BlockTieredEnergyPedestal.EnumDimType;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTieredEnergyContainer;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockTieredEnergyContainer extends BlockContainer implements IEnergyBlock {

	public static final Map<EnumDimType, Block> VARIANTS = new HashMap<>();

	public EnumDimType TYPE;

	public BlockTieredEnergyContainer(EnumDimType type) {
		super(Material.ROCK);
		setHardness(6.0F);
		setResistance(12.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
		setHarvestLevel("pickaxe", 0);
		TYPE = type;
		VARIANTS.put(type, this);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityTieredEnergyContainer();
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
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, EnumFacing side, float par7, float par8, float par9) {
		if(!par1World.isRemote)
			FMLNetworkHandler.openGui(par5EntityPlayer, AbyssalCraft.instance, ACLib.energycontainerGuiID, par1World, pos.getX(), pos.getY(), pos.getZ());
		return true;
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
			if(tile instanceof TileEntityTieredEnergyContainer)
				((TileEntityTieredEnergyContainer)tile).addEnergy(stack.getTagCompound().getFloat("PotEnergy"));
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new java.util.ArrayList<>();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		int base = 10000;
		switch(TYPE.getMeta()){
		case 0:
			return base * 2;
		case 1:
			return base * 6;
		case 2:
			return base * 24;
		case 3:
			return base * 120;
		default:
			return base;
		}
	}
}
