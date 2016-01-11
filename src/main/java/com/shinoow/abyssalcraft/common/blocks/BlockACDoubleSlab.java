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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockACDoubleSlab extends BlockSlab {

	private Block singleSlab;

	public BlockACDoubleSlab(Block par1SingleSlab, Material par3Material, String tooltype, int harvestlevel)
	{
		super(true, par3Material);
		setCreativeTab(null);
		setLightOpacity(0);
		singleSlab = par1SingleSlab;
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACDoubleSlab(Block par1SingleSlab, Material par3Material)
	{
		super(true, par3Material);
		setCreativeTab(null);
		setLightOpacity(0);
		singleSlab = par1SingleSlab;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return singleSlab.getItemDropped(par1, par2Random, par3);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return Item.getItemFromBlock(singleSlab);
	}

	@Override
	public String func_150002_b(int var1)
	{
		return super.getUnlocalizedName();
	}
}
