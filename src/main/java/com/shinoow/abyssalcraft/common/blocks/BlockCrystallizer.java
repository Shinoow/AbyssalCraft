/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrystallizer extends BlockContainer
{
	private final Random rand = new Random();
	private final boolean isLit;
	private static boolean keepInventory;
	@SideOnly(Side.CLIENT)
	private IIcon iconTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconFront;

	public BlockCrystallizer(boolean par1)
	{
		super(Material.rock);
		isLit = par1;
		if(!isLit)
			setCreativeTab(AbyssalCraft.tabDecoration);
	}

	@Override
	public Item getItemDropped(int par1, Random par1Random, int par3)
	{
		return Item.getItemFromBlock(AbyssalCraft.crystallizer);
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
		getDirection(par1World, par2, par3, par4);
	}

	private void getDirection(World par1World, int par2, int par3, int par4)
	{
		if (!par1World.isRemote)
		{
			Block block = par1World.getBlock(par2, par3, par4 - 1);
			Block block1 = par1World.getBlock(par2, par3, par4 + 1);
			Block block2 = par1World.getBlock(par2 - 1, par3, par4);
			Block block3 = par1World.getBlock(par2 + 1, par3, par4);
			byte b0 = 3;

			if (block.func_149730_j() && !block1.func_149730_j())
				b0 = 3;

			if (block1.func_149730_j() && !block.func_149730_j())
				b0 = 2;

			if (block2.func_149730_j() && !block3.func_149730_j())
				b0 = 5;

			if (block3.func_149730_j() && !block2.func_149730_j())
				b0 = 4;

			par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 2);
		}
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		return par1 == 1 ? iconTop : par1 == 0 ? iconTop : par1 != par2 ? blockIcon : iconFront;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IIconRegister)
	{
		blockIcon = par1IIconRegister.registerIcon("abyssalcraft:crystallizer_side");
		iconFront = par1IIconRegister.registerIcon(isLit ? "abyssalcraft:crystallizer_front_on" : "abyssalcraft:crystallizer_front_off");
		iconTop = par1IIconRegister.registerIcon("abyssalcraft:crystallizer_top");
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(!par1World.isRemote)
			FMLNetworkHandler.openGui(par5EntityPlayer, AbyssalCraft.instance, AbyssalCraft.crystallizerGuiID, par1World, par2, par3, par4);
		return true;
	}

	public static void updateCrystallizerBlockState(boolean par0, World par1World, int par2, int par3, int par4)
	{
		int l = par1World.getBlockMetadata(par2, par3, par4);
		TileEntity tileentity = par1World.getTileEntity(par2, par3, par4);
		keepInventory = true;

		if (par0)
			par1World.setBlock(par2, par3, par4, AbyssalCraft.crystallizer_on);
		else
			par1World.setBlock(par2, par3, par4, AbyssalCraft.crystallizer);

		keepInventory = false;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);

		if (tileentity != null)
		{
			tileentity.validate();
			par1World.setTileEntity(par2, par3, par4, tileentity);
		}
	}

	/**
	 * Returns a new instance of a block's tile entity class. Called on placing the block.
	 */
	@Override
	public TileEntity createNewTileEntity(World par1World, int par2)
	{
		return new TileEntityCrystallizer();
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int l = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l == 0)
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);

		if (l == 1)
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);

		if (l == 2)
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);

		if (l == 3)
			par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);

		if (par6ItemStack.hasDisplayName())
			((TileEntityCrystallizer)par1World.getTileEntity(par2, par3, par4)).func_145951_a(par6ItemStack.getDisplayName());
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5Block, int par6)
	{
		if (!keepInventory)
		{
			TileEntityCrystallizer tileentitycrystallizer = (TileEntityCrystallizer)par1World.getTileEntity(par2, par3, par4);

			if (tileentitycrystallizer != null)
			{
				for (int i1 = 0; i1 < tileentitycrystallizer.getSizeInventory(); ++i1)
				{
					ItemStack itemstack = tileentitycrystallizer.getStackInSlot(i1);

					if (itemstack != null)
					{
						float f = rand.nextFloat() * 0.8F + 0.1F;
						float f1 = rand.nextFloat() * 0.8F + 0.1F;
						float f2 = rand.nextFloat() * 0.8F + 0.1F;

						while (itemstack.stackSize > 0)
						{
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

	/**
	 * A randomly called display update to be able to add particles or other items for display
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (isLit)
		{
			int l = par1World.getBlockMetadata(par2, par3, par4);
			float f = par2 + 0.5F;
			float f1 = par3 + 0.0F + par5Random.nextFloat() * 6.0F / 16.0F;
			float f2 = par4 + 0.5F;
			float f3 = 0.52F;
			float f4 = par5Random.nextFloat() * 0.6F - 0.3F;

			if (l == 4)
			{
				par1World.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			}
			else if (l == 5)
			{
				par1World.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
			}
			else if (l == 2)
			{
				par1World.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
			}
			else if (l == 3)
			{
				par1World.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
				par1World.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	/**
	 * If this returns true, then comparators facing away from this block will use the value from
	 * getComparatorInputOverride instead of the actual redstone signal strength.
	 */
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	/**
	 * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
	 * strength when this block inputs to a comparator.
	 */
	@Override
	public int getComparatorInputOverride(World par1World, int par2, int par3, int par4, int par5)
	{
		return Container.calcRedstoneFromInventory((IInventory)par1World.getTileEntity(par2, par3, par4));
	}

	/**
	 * Gets an item for the block being called on. Args: world, x, y, z
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return Item.getItemFromBlock(AbyssalCraft.crystallizer);
	}
}