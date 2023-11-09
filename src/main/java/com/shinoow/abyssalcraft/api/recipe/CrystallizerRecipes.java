/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CrystallizerRecipes {

	private static final CrystallizerRecipes crystallizationBase = new CrystallizerRecipes();
	/** The list of crystallization results. */
	private final Map<ItemStack, ItemStack[]> crystallizationList = new HashMap<>();
	private final Map<ItemStack, Float> experienceList = new HashMap<>();

	public static CrystallizerRecipes instance()
	{
		return crystallizationBase;
	}

	private CrystallizerRecipes(){}

	public void crystallize(Block input, ItemStack output1, ItemStack output2, float xp)
	{
		crystallize(Item.getItemFromBlock(input), output1, output2, xp);
	}

	public void crystallize(Item input, ItemStack output1, ItemStack output2, float xp)
	{
		crystallize(new ItemStack(input, 1, OreDictionary.WILDCARD_VALUE), output1, output2, xp);
	}

	public void crystallize(ItemStack input, ItemStack output1, ItemStack output2, float xp)
	{
		crystallizationList.put(input, new ItemStack[]{output1, output2});
		experienceList.put(output1, xp);
	}

	/**
	 * Returns the crystallization result of an item.
	 */
	public ItemStack[] getCrystallizationResult(ItemStack stack)
	{
		return crystallizationList.entrySet().stream()
				.filter(e -> APIUtils.areStacksEqual(stack, e.getKey()))
				.map(Entry::getValue)
				.findFirst()
				.orElse(new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY});
	}

	public Map<ItemStack, ItemStack[]> getCrystallizationList()
	{
		return crystallizationList;
	}

	public float getExperience(ItemStack stack)
	{
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1) return ret;

		return experienceList.entrySet().stream()
				.filter(e -> APIUtils.areStacksEqual(stack, e.getKey()))
				.map(e -> e.getValue())
				.findFirst()
				.orElse(0.0F);
	}
}
