package com.shinoow.abyssalcraft.common.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Sets;

public class ItemCoraliumAxe extends ItemTool
{
	private static Set<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] {Blocks.planks, Blocks.bookshelf, Blocks.log, Blocks.chest, Blocks.pumpkin, Blocks.lit_pumpkin});

	public ItemCoraliumAxe(ToolMaterial enumToolMaterial)
	{
		super(3, enumToolMaterial, blocksEffectiveAgainst);
		this.setHarvestLevel("axe", 6);
	}
	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	public float func_150893_a(ItemStack par1ItemStack, Block par2Block)
	{
		if (par2Block != null && par2Block.getMaterial() == Material.wood)
		{
			return efficiencyOnProperMaterial;
		}
		else
		{
			return super.func_150893_a(par1ItemStack, par2Block);
		}
	}

}