/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class TransmutatorRecipes {

	private static final TransmutatorRecipes transmutationBase = new TransmutatorRecipes();
	/** The list of transmutation results. */
	private final Map<ItemStack, ItemStack> transmutationList = new HashMap<>();
	private final Map<ItemStack, Float> experienceList = new HashMap<>();

	public static TransmutatorRecipes instance()
	{
		return transmutationBase;
	}

	private TransmutatorRecipes()
	{
		transmute(Items.DIAMOND, new ItemStack(Items.COAL, 64), 0.2F);
		transmute(Items.WATER_BUCKET, new ItemStack(Blocks.ICE, 8), 0.0F);
		transmute(Blocks.WOOL, new ItemStack(Items.STRING, 4), 0.0F);
		transmute(Blocks.GRAVEL, new ItemStack(Items.FLINT, 2), 0.0F);
		transmute(Blocks.QUARTZ_BLOCK, new ItemStack(Items.QUARTZ, 4), 0.0F);
		transmute(Blocks.NETHER_BRICK, new ItemStack(Items.NETHERBRICK, 4), 0.0F);
		transmute(Items.NETHERBRICK, new ItemStack(Blocks.NETHERRACK), 0.0F);
		transmute(Items.WHEAT, new ItemStack(Items.WHEAT_SEEDS), 0.0F);
		transmute(Items.WHEAT_SEEDS, new ItemStack(Items.WHEAT), 0.0F);
	}

	public void transmute(Block input, ItemStack output, float xp)
	{
		transmute(Item.getItemFromBlock(input), output, xp);
	}

	public void transmute(Item input, ItemStack output, float xp)
	{
		transmute(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output, xp);
	}

	public void transmute(ItemStack input, ItemStack output, float xp)
	{
		transmutationList.put(input, output);
		experienceList.put(output, xp);
	}

	/**
	 * Returns the transmutation result of an item.
	 */
	public ItemStack getTransmutationResult(ItemStack stack)
	{
		return transmutationList.entrySet().stream()
				.filter(e -> APIUtils.areStacksEqual(stack, e.getKey()))
				.map(Entry::getValue)
				.findFirst()
				.orElse(ItemStack.EMPTY);
	}

	public Map<ItemStack, ItemStack> getTransmutationList()
	{
		return transmutationList;
	}

	public float getExperience(ItemStack stack)
	{
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1) return ret;

		return experienceList.entrySet().stream()
				.filter(e -> APIUtils.areStacksEqual(stack, e.getKey()))
				.map(Entry::getValue)
				.findFirst()
				.orElse(0.0F);
	}
}
