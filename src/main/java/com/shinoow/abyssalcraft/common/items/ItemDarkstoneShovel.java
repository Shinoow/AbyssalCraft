package com.shinoow.abyssalcraft.common.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemTool;

import com.google.common.collect.Sets;
import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemDarkstoneShovel extends ItemTool
{


	private static Set<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] {Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow_layer, Blocks.snow, Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium, AbyssalCraft.Darkgrass});
	public ItemDarkstoneShovel(ToolMaterial enumToolMaterial)
	{
		super(1, enumToolMaterial, blocksEffectiveAgainst);
		this.setHarvestLevel("shovel", 1);

	}
	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	public boolean canHarvestBlock(Block par1Block)
	{
		if (par1Block == Blocks.snow_layer)
		{
			return true;
		}
		return par1Block == Blocks.snow;
	} 

}