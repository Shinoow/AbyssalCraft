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

import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.world.World;

import com.google.common.collect.Sets;
import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemAbyssalniteCPickaxe extends ItemTool
{

	private static Set<Block> blocksEffectiveAgainst = Sets.newHashSet(new Block[] {Blocks.cobblestone, Blocks.double_stone_slab, Blocks.stone_slab, Blocks.stone, Blocks.sandstone, Blocks.mossy_cobblestone, Blocks.iron_ore, Blocks.iron_block, Blocks.coal_ore, Blocks.gold_block, Blocks.gold_ore, Blocks.diamond_ore, Blocks.diamond_block, Blocks.ice, Blocks.netherrack, Blocks.lapis_ore, Blocks.lapis_block, Blocks.redstone_ore, Blocks.lit_redstone_ore, Blocks.rail, Blocks.detector_rail, Blocks.golden_rail, Blocks.activator_rail});

	public ItemAbyssalniteCPickaxe(ToolMaterial enumToolMaterial)
	{
		super(2, enumToolMaterial, blocksEffectiveAgainst);
		this.setHarvestLevel("pickaxe", 8);
	}
	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B)
	{
		l.add("This pickaxe can transmutate");
		l.add("ores and certain stone types");
	}

	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int l, float f, float f1, float f3){
		if(w.getBlock(x, y, z) == Blocks.coal_ore){
			w.setBlock(x, y, z, Blocks.iron_ore);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.iron_ore){
			w.setBlock(x, y, z, Blocks.gold_ore);
			is.damageItem(60, player);
		}else if(w.getBlock(x, y, z) == Blocks.gold_ore){
			w.setBlock(x, y, z, Blocks.redstone_ore);
			is.damageItem(70, player);
		}else if(w.getBlock(x, y, z) == Blocks.lit_redstone_ore){ 
			w.setBlock(x, y, z, Blocks.lapis_ore);
			is.damageItem(80, player);
		}else if(w.getBlock(x, y, z) == Blocks.lapis_ore){
			w.setBlock(x, y, z, Blocks.emerald_ore);
			is.damageItem(90, player);
		}else if(w.getBlock(x, y, z) == Blocks.emerald_ore){
			w.setBlock(x, y, z, AbyssalCraft.Coraliumore);
			is.damageItem(100, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.Coraliumore){ 
			w.setBlock(x, y, z, Blocks.diamond_ore);
			is.damageItem(110, player);
		}else if(w.getBlock(x, y, z) == Blocks.diamond_ore){ 
			w.setBlock(x, y, z, AbyssalCraft.abyore);
			is.damageItem(120, player);
		}else if(w.getBlock(x,y,z) == AbyssalCraft.abyore) {
			w.setBlock(x, y, z, AbyssalCraft.Coraliumstone);
			is.damageItem(130, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.Coraliumstone){ 
			w.setBlock(x, y, z, Blocks.coal_ore);
			is.damageItem(40, player);
		}else if(w.getBlock(x, y, z) == Blocks.netherrack){ 
			w.setBlock(x, y, z, Blocks.end_stone);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.end_stone){ 
			w.setBlock(x, y, z, Blocks.netherrack);
			is.damageItem(50, player);
		}
		return false;
	}

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
		if (par2Block != null && (par2Block.getMaterial() == Material.rock || par2Block.getMaterial() == Material.iron))
		{
			return efficiencyOnProperMaterial;
		}
		else
		{
			return super.func_150893_a(par1ItemStack, par2Block);
		}
	}

}