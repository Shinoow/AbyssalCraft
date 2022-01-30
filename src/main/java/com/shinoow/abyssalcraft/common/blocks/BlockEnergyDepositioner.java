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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.PEUtils;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEnergyDepositioner;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.item.ItemCharm;
import com.shinoow.abyssalcraft.lib.util.blocks.BlockUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEnergyDepositioner extends BlockACBasic {

	public BlockEnergyDepositioner() {
		super(Material.ROCK, 2, 4, SoundType.STONE);
		setTranslationKey("energydepositioner");
		setCreativeTab(ACTabs.tabDecoration);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.12F, 0.0F, 0.12F, 0.88F, 0.75F, 0.88F);
	}

	@Override
	public boolean hasTileEntity(IBlockState state)
	{
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state)
	{
		return new TileEntityEnergyDepositioner();
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

		if(par5EntityPlayer.getHeldItem(hand).getItem() instanceof ItemCharm) return false;

		if(!par1World.isRemote)
			FMLNetworkHandler.openGui(par5EntityPlayer, AbyssalCraft.instance, ACLib.energyDepositionerGuiID, par1World, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer()
	{
		return BlockRenderLayer.CUTOUT;
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
		if(stack.hasTagCompound()){
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile instanceof TileEntityEnergyDepositioner){
				NBTTagCompound data = new NBTTagCompound();
				tile.writeToNBT(data);
				data.setInteger("Timer", stack.getTagCompound().getInteger("Timer"));
				data.setInteger("Tolerance", stack.getTagCompound().getInteger("Tolerance"));
				data.setFloat("PotEnergy", stack.getTagCompound().getFloat("PotEnergy"));
				tile.readFromNBT(data);
				PEUtils.readManipulatorNBT((TileEntityEnergyDepositioner)tile, stack.getTagCompound());
			}
		}
	}

	@Override
	public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
	{
		return new java.util.ArrayList<>();
	}
}
