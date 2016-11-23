/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Arrays;
import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.TRSRTransformation;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEngraver;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockEngraver extends BlockContainer {

	public static final PropertyDirection FACING = PropertyDirection.create("facing");
	private ExtendedBlockState state = new ExtendedBlockState(this, new IProperty[] {FACING}, new IUnlistedProperty[]{OBJModel.OBJProperty.instance});

	private final Random rand = new Random();
	private static boolean keepInventory;

	public BlockEngraver() {
		super(Material.rock);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
		setCreativeTab(ACTabs.tabDecoration);
	}

	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
	{
		return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(FACING).getIndex();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IBlockState getStateForEntityRender(IBlockState state)
	{
		return getDefaultState().withProperty(FACING, EnumFacing.NORTH);
	}

	@Override
	public int getRenderType() {
		return 3;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random par1Random, int par3)
	{
		return Item.getItemFromBlock(ACBlocks.engraver);
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumFacing side, float par7, float par8, float par9) {
		if(!par1World.isRemote)
			FMLNetworkHandler.openGui(par5EntityPlayer, AbyssalCraft.instance, ACLib.engraverGuiID, par1World, pos.getX(), pos.getY(), pos.getZ());
		return true;
	}

	public static void updateEngraverBlockState(World par1World, BlockPos pos) {

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityEngraver();
	}

	@Override
	public void onBlockPlacedBy(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {

		if (par5EntityLivingBase == null)
			return;

		TileEntityEngraver tile = (TileEntityEngraver) par1World.getTileEntity(pos);

		if (par6ItemStack.hasDisplayName())
			tile.func_145951_a(par6ItemStack.getDisplayName());
	}

	@Override
	public void breakBlock(World par1World, BlockPos pos, IBlockState state) {
		if (!keepInventory){
			TileEntityEngraver tileentityengraver = (TileEntityEngraver)par1World.getTileEntity(pos);

			if (tileentityengraver != null){
				for (int i1 = 0; i1 < tileentityengraver.getSizeInventory(); ++i1){
					ItemStack itemstack = tileentityengraver.getStackInSlot(i1);

					if (itemstack != null){
						float f = rand.nextFloat() * 0.8F + 0.1F;
						float f1 = rand.nextFloat() * 0.8F + 0.1F;
						float f2 = rand.nextFloat() * 0.8F + 0.1F;

						while (itemstack.stackSize > 0){
							int j1 = rand.nextInt(21) + 10;

							if (j1 > itemstack.stackSize)
								j1 = itemstack.stackSize;

							itemstack.stackSize -= j1;
							EntityItem entityitem = new EntityItem(par1World, pos.getX() + f, pos.getY() + f1, pos.getZ() + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

							if (itemstack.hasTagCompound())
								entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());

							float f3 = 0.05F;
							entityitem.motionX = (float)rand.nextGaussian() * f3;
							entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
							entityitem.motionZ = (float)rand.nextGaussian() * f3;
							par1World.spawnEntityInWorld(entityitem);
						}
					}
				}

				par1World.updateComparatorOutputLevel(pos, this);
			}
		}

		super.breakBlock(par1World, pos, state);
	}

	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(World par1World, BlockPos pos)
	{
		return Container.calcRedstoneFromInventory((IInventory)par1World.getTileEntity(pos));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, BlockPos pos)
	{
		return Item.getItemFromBlock(ACBlocks.engraver);
	}

	@Override
	public IBlockState getExtendedState(IBlockState state, IBlockAccess world, BlockPos pos)
	{
		EnumFacing facing = state.getValue(FACING);
		TRSRTransformation transform = new TRSRTransformation(facing);
		OBJModel.OBJState retState = new OBJModel.OBJState(Arrays.asList(new String[]{OBJModel.Group.ALL}), true, transform);
		return ((IExtendedBlockState) state).withProperty(OBJModel.OBJProperty.instance, retState);
	}

	@Override
	public BlockState createBlockState()
	{
		return new ExtendedBlockState(this, new IProperty[] {FACING}, new IUnlistedProperty[] {OBJModel.OBJProperty.instance});
	}
}