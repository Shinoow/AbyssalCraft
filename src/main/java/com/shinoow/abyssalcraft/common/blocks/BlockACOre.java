/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockACOre extends BlockACBasic {

	public BlockACOre(int harvestlevel, float hardness, float resistance) {
		super(Material.ROCK, "pickaxe", harvestlevel, hardness, resistance, SoundType.STONE);
	}

	@SuppressWarnings("deprecation")
	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_)
	{
		return this == ACBlocks.abyssal_coralium_ore || this == ACBlocks.pearlescent_coralium_ore || this == ACBlocks.liquified_coralium_ore || this == ACBlocks.abyssal_iron_ore || this == ACBlocks.abyssal_gold_ore || this == ACBlocks.abyssal_diamond_ore
				|| this == ACBlocks.abyssal_nitre_ore || this == ACBlocks.abyssal_tin_ore || this == ACBlocks.abyssal_copper_ore ? MapColor.GREEN : this == ACBlocks.dreaded_abyssalnite_ore || this == ACBlocks.dreadlands_abyssalnite_ore ? MapColor.RED : super.getMapColor(state, p_180659_2_, p_180659_3_);
	}

	@Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
	{
		return this == ACBlocks.coralium_infused_stone ? ACItems.coralium_pearl: this == ACBlocks.coralium_ore ? ACItems.coralium_gem: this == ACBlocks.nitre_ore ? ACItems.nitre: this == ACBlocks.abyssal_nitre_ore ? ACItems.nitre: this == ACBlocks.abyssal_diamond_ore ? Items.DIAMOND:
			this == ACBlocks.abyssal_coralium_ore ? ACItems.coralium_gem: this == ACBlocks.pearlescent_coralium_ore ? ACItems.coralium_pearl: Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return this == ACBlocks.coralium_ore ? 1 + par1Random.nextInt(3): this == ACBlocks.nitre_ore ? 1 + par1Random.nextInt(3): this == ACBlocks.abyssal_nitre_ore ? 1 + par1Random.nextInt(3):
			this == ACBlocks.abyssal_coralium_ore ? 1 + par1Random.nextInt(3): this == ACBlocks.pearlescent_coralium_ore ? 1 + par1Random.nextInt(2): 1;
	}

	@Override
	public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		if (par1 > 0 && Item.getItemFromBlock(this) != getItemDropped(null, par2Random, par1))
		{
			int j = par2Random.nextInt(par1 + 2) - 1;

			if (j < 0)
				j = 0;

			return quantityDropped(par2Random) * (j + 1);
		} else if(this == ACBlocks.coralium_ore || this == ACBlocks.nitre_ore || this == ACBlocks.abyssal_nitre_ore || this == ACBlocks.abyssal_coralium_ore)
			return quantityDropped(par2Random);
		else
			return quantityDropped(par2Random);
	}

	@Override
	public void dropBlockAsItemWithChance(World par1World, BlockPos pos, IBlockState state, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, pos, state, par6, par7);
	}

	private Random rand = new Random();

	@Override
	public int getExpDrop(IBlockState state, IBlockAccess par1BlockAccess, BlockPos par2, int par3)
	{
		if (getItemDropped(null, rand, par3) != Item.getItemFromBlock(this))
			if (this == ACBlocks.coralium_ore || this == ACBlocks.nitre_ore ||
			this == ACBlocks.abyssal_coralium_ore || this == ACBlocks.abyssal_nitre_ore)
				return MathHelper.getInt(rand, 0, 2);
			else if (this == ACBlocks.coralium_infused_stone || this == ACBlocks.pearlescent_coralium_ore)
				return MathHelper.getInt(rand, 2, 5);
			else if (this == ACBlocks.abyssal_diamond_ore)
				return MathHelper.getInt(rand, 3, 7);
		return 0;
	}
}
