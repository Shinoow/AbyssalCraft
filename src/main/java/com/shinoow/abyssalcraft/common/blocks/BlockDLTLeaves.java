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

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDLTLeaves extends BlockLeavesBase implements IShearable {

	int[] adjacentTreeBlocks;
	@SideOnly(Side.CLIENT)
	private int iconType;
	public static final String[][] textureNames = new String[][] {{"DLT_L"}, {"DLT_L_opaque"}};
	private IIcon[][] iconArray = new IIcon[2][];

	public BlockDLTLeaves()
	{
		super(Material.leaves , false);
		setTickRandomly(true);
		setCreativeTab(AbyssalCraft.tabDecoration);
	}

	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, Block par5, int par6)
	{
		byte var7 = 1;
		int var8 = var7 + 1;

		if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
			for (int var9 = -var7; var9 <= var7; ++var9)
				for (int var10 = -var7; var10 <= var7; ++var10)
					for (int var11 = -var7; var11 <= var7; ++var11)
					{
						Block var12 = par1World.getBlock(par2 + var9, par3 + var10, par4 + var11);

						if (var12.isLeaves(par1World, par2 +var9, par3 +var10, par4 +var11))
							var12.beginLeavesDecay(par1World, par2 + var9, par3 + var10, par4 + var11);
					}
	}

	@Override
	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (!par1World.isRemote)
		{
			int var6 = par1World.getBlockMetadata(par2, par3, par4);

			if ((var6 & 8) != 0 && (var6 & 4) == 0)
			{
				byte var7 = 4;
				int var8 = var7 + 1;
				byte var9 = 32;
				int var10 = var9 * var9;
				int var11 = var9 / 2;

				if (adjacentTreeBlocks == null)
					adjacentTreeBlocks = new int[var9 * var9 * var9];

				int var12;

				if (par1World.checkChunksExist(par2 - var8, par3 - var8, par4 - var8, par2 + var8, par3 + var8, par4 + var8))
				{
					int var13;
					int var14;

					for (var12 = -var7; var12 <= var7; ++var12)
						for (var13 = -var7; var13 <= var7; ++var13)
							for (var14 = -var7; var14 <= var7; ++var14)
							{

								Block block = par1World.getBlock(par2 + var12, par3 + var13, par4 + var14);

								if (block.canSustainLeaves(par1World, par2 + var12, par3 + var13, par4 + var14))
									adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = 0;
								else if (block.isLeaves(par1World, par2 + var12, par3 + var13, par4 + var14))
									adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -2;
								else
									adjacentTreeBlocks[(var12 + var11) * var10 + (var13 + var11) * var9 + var14 + var11] = -1;
							}

					for (var12 = 1; var12 <= 4; ++var12)
						for (var13 = -var7; var13 <= var7; ++var13)
							for (var14 = -var7; var14 <= var7; ++var14)
								for (int var15 = -var7; var15 <= var7; ++var15)
									if (adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11] == var12 - 1)
									{
										if (adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
											adjacentTreeBlocks[(var13 + var11 - 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;

										if (adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] == -2)
											adjacentTreeBlocks[(var13 + var11 + 1) * var10 + (var14 + var11) * var9 + var15 + var11] = var12;

										if (adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] == -2)
											adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 - 1) * var9 + var15 + var11] = var12;

										if (adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] == -2)
											adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11 + 1) * var9 + var15 + var11] = var12;

										if (adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 - 1] == -2)
											adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 - 1] = var12;

										if (adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] == -2)
											adjacentTreeBlocks[(var13 + var11) * var10 + (var14 + var11) * var9 + var15 + var11 + 1] = var12;
									}
				}

				var12 = adjacentTreeBlocks[var11 * var10 + var11 * var9 + var11];

				if (var12 >= 0)
					par1World.setBlockMetadataWithNotify(par2, par3, par4, var6 & -9, 4);
				else
					removeLeaves(par1World, par2, par3, par4);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		if (par1World.canLightningStrikeAt(par2, par3 + 1, par4) && !World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4) && par5Random.nextInt(15) == 1)
		{
			double var6 = par2 + par5Random.nextFloat();
			double var8 = par3 - 0.05D;
			double var10 = par4 + par5Random.nextFloat();
			par1World.spawnParticle("dripWater", var6, var8, var10, 0.0D, 0.0D, 0.0D);
		}
	}

	private void removeLeaves(World par1World, int par2, int par3, int par4)
	{
		this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
		par1World.setBlock(par2, par3, par4, Blocks.air);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(20) == 0 ? 1 : 0;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(AbyssalCraft.DLTSapling);
	}

	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if (!par1World.isRemote)
		{
			byte var8 = 20;

			if ((par5 & 3) == 3)
				var8 = 40;

			if (par1World.rand.nextInt(var8) == 0)
			{
				Item var9 = getItemDropped(par5, par1World.rand, par7);
				this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(var9, 1, damageDropped(par5)));
			}

			if ((par5 & 3) == 0 && par1World.rand.nextInt(200) == 0)
				this.dropBlockAsItem(par1World, par2, par3, par4, new ItemStack(AbyssalCraft.DLTSapling, 1, 0));
		}
	}

	@Override
	public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6)
	{
		super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
	}

	@Override
	public int damageDropped(int par1)
	{
		return par1 & 3;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int par1, int par2)
	{
		setGraphicsLevel(Minecraft.getMinecraft().gameSettings.fancyGraphics);

		return (par2 & 3) == 1 ? iconArray[iconType][1] : iconArray[iconType][0];
	}

	@SideOnly(Side.CLIENT)
	public void setGraphicsLevel(boolean par1)
	{
		field_150121_P = par1;
		iconType = par1 ? 0 : 1;
	}

	@Override
	protected ItemStack createStackedBlock(int par1)
	{
		return new ItemStack(this, 1, par1 & 3);
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
		return ret;
	}

	@Override
	public void beginLeavesDecay(World world, int x, int y, int z)
	{
		world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8, 4);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		for (int i = 0; i < textureNames.length; ++i)
		{
			iconArray[i] = new IIcon[textureNames[i].length];

			for (int j = 0; j < textureNames[i].length; ++j)
				iconArray[i][j] = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + textureNames[i][j]);
		}
	}
}