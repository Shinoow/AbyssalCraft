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

import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class BlockACOre extends BlockACBasic {

	public BlockACOre(int harvestlevel, float hardness, float resistance) {
		super(Material.rock, "pickaxe", harvestlevel, hardness, resistance, soundTypeStone);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return this == AbyssalCraft.CoraliumInfusedStone ? AbyssalCraft.Cpearl: this == AbyssalCraft.Coraliumore ? AbyssalCraft.Coralium: this == AbyssalCraft.nitreOre ? AbyssalCraft.nitre: this == AbyssalCraft.AbyNitOre ? AbyssalCraft.nitre: this == AbyssalCraft.AbyDiaOre ? Items.diamond:
			this == AbyssalCraft.AbyCorOre ? AbyssalCraft.Coralium: this == AbyssalCraft.AbyPCorOre ? AbyssalCraft.Cpearl: Item.getItemFromBlock(this);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return this == AbyssalCraft.Coraliumore ? 1 + par1Random.nextInt(3): this == AbyssalCraft.nitreOre ? 1 + par1Random.nextInt(3): this == AbyssalCraft.AbyNitOre ? 1 + par1Random.nextInt(3):
			this == AbyssalCraft.AbyCorOre ? 1 + par1Random.nextInt(3): this == AbyssalCraft.AbyPCorOre ? 1 + par1Random.nextInt(2): 1;
	}

	@Override
	public int quantityDroppedWithBonus(int par1, Random par2Random)
	{
		if (par1 > 0 && Item.getItemFromBlock(this) != getItemDropped(0, par2Random, par1))
		{
			int j = par2Random.nextInt(par1 + 2) - 1;

			if (j < 0)
				j = 0;

			return quantityDropped(par2Random) * (j + 1);
		} else if(this == AbyssalCraft.Coraliumore || this == AbyssalCraft.nitreOre || this == AbyssalCraft.AbyNitOre || this == AbyssalCraft.AbyCorOre)
			return quantityDropped(par2Random);
		else
			return quantityDropped(par2Random);
	}

	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, par7);
	}

	private Random rand = new Random();

	@Override
	public int getExpDrop(IBlockAccess par1BlockAccess, int par2, int par3)
	{
		if (getItemDropped(par2, rand, par3) != Item.getItemFromBlock(this))
		{
			int j1 = 0;

			if (this == AbyssalCraft.Coraliumore)
				j1 = MathHelper.getRandomIntegerInRange(rand, 0, 2);
			else if (this == AbyssalCraft.nitreOre)
				j1 = MathHelper.getRandomIntegerInRange(rand, 0, 2);
			else if (this == AbyssalCraft.CoraliumInfusedStone)
				j1 = MathHelper.getRandomIntegerInRange(rand, 2, 5);
			else if (this == AbyssalCraft.AbyCorOre)
				j1 = MathHelper.getRandomIntegerInRange(rand, 0, 2);
			else if (this == AbyssalCraft.AbyDiaOre)
				j1 = MathHelper.getRandomIntegerInRange(rand, 3, 7);
			else if (this == AbyssalCraft.AbyNitOre)
				j1 = MathHelper.getRandomIntegerInRange(rand, 0, 2);
			else if (this == AbyssalCraft.AbyPCorOre)
				j1 = MathHelper.getRandomIntegerInRange(rand, 2, 5);

			return j1;
		}
		return 0;
	}
}
