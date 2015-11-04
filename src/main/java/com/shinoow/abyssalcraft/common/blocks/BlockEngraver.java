/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEngraver;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEngraver extends BlockContainer {

	private final Random rand = new Random();
	private static boolean keepInventory;

	public BlockEngraver() {
		super(Material.rock);
		setBlockTextureName("anvil_top_damaged_0");
		setCreativeTab(AbyssalCraft.tabDecoration);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -6;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public Item getItemDropped(int par1, Random par1Random, int par3)
	{
		return Item.getItemFromBlock(AbyssalCraft.engraver);
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(!par1World.isRemote)
			FMLNetworkHandler.openGui(par5EntityPlayer, AbyssalCraft.instance, AbyssalCraft.engraverGuiID, par1World, par2, par3, par4);
		return true;
	}

	public static void updateEngraverBlockState(World par1World, int par2, int par3, int par4) {

		TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
		keepInventory = true;

		par1World.setBlock(par2, par3, par4, AbyssalCraft.engraver);

		keepInventory = false;

		if (tileentity != null){
			tileentity.validate();
			par1World.setTileEntity(par2, par3, par4, tileentity);
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityEngraver();
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {

		if (par5EntityLivingBase == null)
			return;

		TileEntityEngraver tile = (TileEntityEngraver) par1World.getTileEntity(par2, par3, par4);
		tile.direction = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (par6ItemStack.hasDisplayName())
			tile.func_145951_a(par6ItemStack.getDisplayName());
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5Block, int par6) {
		if (!keepInventory){
			TileEntityEngraver tileentityengraver = (TileEntityEngraver)par1World.getTileEntity(par2, par3, par4);

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
							EntityItem entityitem = new EntityItem(par1World, par2 + f, par3 + f1, par4 + f2, new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

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

				par1World.func_147453_f(par2, par3, par4, par5Block);
			}
		}

		super.breakBlock(par1World, par2, par3, par4, par5Block, par6);
	}

	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	@Override
	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		return Container.calcRedstoneFromInventory((IInventory)par1World.getTileEntity(par2, par3, par4));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return Item.getItemFromBlock(AbyssalCraft.engraver);
	}
}
