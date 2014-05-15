/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Sets;

public class ItemAbyssalnitePickaxe extends ItemTool
{

	private static Set<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});

	public ItemAbyssalnitePickaxe(ToolMaterial enumToolMaterial)
	{
		super(2, enumToolMaterial, blocksEffectiveAgainst);
		this.setHarvestLevel("pickaxe", 4);
	}
	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	public boolean canHarvestBlock(Block par1Block)
	{
		if (par1Block == Blocks.obsidian)
		{
			return toolMaterial.getHarvestLevel() == 3;
		}
		if (par1Block == Blocks.diamond_block || par1Block == Blocks.diamond_ore)
		{
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (par1Block == Blocks.gold_block || par1Block == Blocks.gold_ore)
		{
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (par1Block == Blocks.iron_block || par1Block == Blocks.iron_ore)
		{
			return toolMaterial.getHarvestLevel() >= 1;
		}
		if (par1Block == Blocks.lapis_block || par1Block == Blocks.lapis_ore)
		{
			return toolMaterial.getHarvestLevel() >= 1;
		}
		if (par1Block == Blocks.redstone_block || par1Block == Blocks.redstone_ore)
		{
			return toolMaterial.getHarvestLevel() >= 2;
		}
		if (par1Block.getMaterial() == Material.rock)
		{
			return true;
		}
		return par1Block.getMaterial() == Material.iron;
	}
	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	public float func_150893_a(ItemStack par1ItemStack, Block par2Block)
	{
		if (par2Block != null && (par2Block.getMaterial() == Material.iron || par2Block.getMaterial() == Material.rock))
		{
			return efficiencyOnProperMaterial;
		}
		else
		{
			return super.func_150893_a(par1ItemStack, par2Block);
		}
	}

}