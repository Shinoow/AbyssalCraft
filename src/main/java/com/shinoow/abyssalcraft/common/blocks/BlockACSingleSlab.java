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

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockACSingleSlab extends BlockSlab {

	public BlockACSingleSlab(Material par3Material, String tooltype, int harvestlevel)
	{
		super(false, par3Material);
		setCreativeTab(AbyssalCraft.tabBlock);
		setLightOpacity(0);
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACSingleSlab(Material par3Material)
	{
		super(false, par3Material);
		setCreativeTab(AbyssalCraft.tabBlock);
		setLightOpacity(0);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	public String func_150002_b(int var1) {

		return super.getUnlocalizedName();
	}
}
