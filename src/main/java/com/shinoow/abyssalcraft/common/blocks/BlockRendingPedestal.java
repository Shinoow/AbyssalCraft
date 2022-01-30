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
import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.IEnergyBlock;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRendingPedestal;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRendingPedestal extends BlockContainer implements IEnergyBlock {

	public BlockRendingPedestal() {
		super(Material.ROCK);
		setTranslationKey("rendingpedestal");
		setHardness(6.0F);
		setResistance(12.0F);
		setSoundType(SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {

		return new TileEntityRendingPedestal();
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
			FMLNetworkHandler.openGui(par5EntityPlayer, AbyssalCraft.instance, ACLib.rendingPedestalGuiID, par1World, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state)
	{
		Random rand = new Random();
		TileEntityRendingPedestal pedestal = (TileEntityRendingPedestal) world.getTileEntity(pos);

		if(pedestal != null){

			InventoryHelper.dropInventoryItems(world, pos, pedestal);

			ItemStack stack = new ItemStack(getItemDropped(state, rand, 1), 1, damageDropped(state));
			if(!stack.hasTagCompound())
				stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setFloat("PotEnergy", pedestal.getContainedEnergy());
			stack.getTagCompound().setInteger("energyShadow", pedestal.getEnergy(0));
			stack.getTagCompound().setInteger("energyAbyssal", pedestal.getEnergy(1));
			stack.getTagCompound().setInteger("energyDread", pedestal.getEnergy(2));
			stack.getTagCompound().setInteger("energyOmothol", pedestal.getEnergy(3));
			float f = rand.nextFloat() * 0.8F + 0.1F;
			float f1 = rand.nextFloat() * 0.8F + 0.1F;
			float f2 = rand.nextFloat() * 0.8F + 0.1F;

			EntityItem item = new EntityItem(world, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, stack);
			float f3 = 0.05F;
			item.motionX = (float)rand.nextGaussian() * f3;
			item.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
			item.motionZ = (float)rand.nextGaussian() * f3;
			world.spawnEntity(item);
		}

		super.breakBlock(world, pos, state);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("PotEnergy")){
			TileEntity tile = worldIn.getTileEntity(pos);
			if(tile != null && tile instanceof TileEntityRendingPedestal){
				((TileEntityRendingPedestal)tile).addEnergy(stack.getTagCompound().getFloat("PotEnergy"));
				((TileEntityRendingPedestal)tile).setEnergy(0, stack.getTagCompound().getInteger("energyShadow"));
				((TileEntityRendingPedestal)tile).setEnergy(1, stack.getTagCompound().getInteger("energyAbyssal"));
				((TileEntityRendingPedestal)tile).setEnergy(2, stack.getTagCompound().getInteger("energyDread"));
				((TileEntityRendingPedestal)tile).setEnergy(3, stack.getTagCompound().getInteger("energyOmothol"));
			}
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

		return 5000;
	}

	@Override
	public boolean canTransferPE() {
		return false;
	}
}
