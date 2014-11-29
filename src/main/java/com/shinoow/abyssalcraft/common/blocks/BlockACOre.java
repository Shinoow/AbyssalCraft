/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;
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