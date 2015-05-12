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
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDLTLog extends BlockRotatedPillar
{
	@SideOnly(Side.CLIENT)
	private IIcon iconLogTop;
	@SideOnly(Side.CLIENT)
	private IIcon iconLogSide;
	@SideOnly(Side.CLIENT)
	private static IIcon iconLogSideOverlay;

	public BlockDLTLog()
	{
		super(Material.wood);
		setCreativeTab(AbyssalCraft.tabBlock);
	}

	@Override
	public int getRenderType()
	{
		return 31;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(this);
	}

	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		byte var7 = 4;
		int var8 = var7 + 1;

		if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
			for (int var9 = -var7; var9 <= var7; ++var9)
				for (int var10 = -var7; var10 <= var7; ++var10)
					for (int var11 = -var7; var11 <= var7; ++var11)
					{
						Block var12 = par1World.getBlock(par2 + var9, par3 + var10, par4 + var11);

						if (var12.isLeaves(par1World, par2 + var9, par3 + var10, par4 + var11))
							var12.beginLeavesDecay(par1World, par2 + var9, par3 + var10, par4 + var11);
					}
	}

	public void updateBlockMetadata(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8)
	{
		int var9 = par1World.getBlockMetadata(par2, par3, par4) & 3;
		byte var10 = 0;

		switch (par5)
		{
		case 0:
		case 1:
			var10 = 0;
			break;
		case 2:
		case 3:
			var10 = 8;
			break;
		case 4:
		case 5:
			var10 = 4;
		}

		par1World.setBlockMetadataWithNotify(par2, par3, par4, var9 | var10, var9);
	}


	@Override
	public int damageDropped(int par1)
	{
		return par1 & 3;
	}

	public static int limitToValidMetadata(int par0)
	{
		return par0 & 3;
	}

	@Override
	protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(this, 1, limitToValidMetadata(par1));
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public IIcon getIcon(int par1, int par2)
	{
		return par1 == 1 ? iconLogTop : par1 == 0 ? iconLogTop : blockIcon;
	}

	@Override
	public boolean canSustainLeaves(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean isWood(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DLTTside");
		iconLogTop = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DLTTtop");
		iconLogSide = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DLTTside");
		BlockDLTLog.iconLogSideOverlay = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + "DLTTside");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getIconSideOverlay()
	{
		return iconLogSideOverlay;
	}

	@Override
	@SideOnly(Side.CLIENT)
	protected IIcon getSideIcon(int i) {

		return iconLogSide;
	}

}