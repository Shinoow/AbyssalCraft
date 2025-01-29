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
package com.shinoow.abyssalcraft.api.recipe;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class CrystallizerRecipes {

	private static final CrystallizerRecipes crystallizationBase = new CrystallizerRecipes();
	/** The list of crystallization results. */
	private final List<Crystallization> crystallizations = new ArrayList<>();

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
		crystallizations.add(new Crystallization(input, output1, output2, xp));
	}

	/**
	 * Returns the crystallization result of an item.
	 */
	public ItemStack[] getCrystallizationResult(ItemStack stack)
	{
		return crystallizations.stream()
				.filter(e -> APIUtils.areStacksEqual(stack, e.INPUT))
				.map(Crystallization::getOutputs)
				.findFirst()
				.orElse(new ItemStack[] {ItemStack.EMPTY, ItemStack.EMPTY});
	}

	public List<Crystallization> getCrystallizationList()
	{
		return crystallizations;
	}

	/**
	 * Removes Crystallization(s) based on the output
	 * @param output ItemStack to check for
	 */
	public void removeCrystallization(ItemStack output) {
		crystallizations.removeIf(e -> APIUtils.areObjectsEqual(output, e.getOutputs(), false));
	}

	/**
	 * Removes a Crystallization based on the input
	 * @param input ItemStack to check for
	 */
	public void removeCrystallizationInput(ItemStack input) {
		crystallizations.removeIf(e -> APIUtils.areStacksEqual(input, e.INPUT));
	}

	public float getExperience(ItemStack stack)
	{
		// Vanilla gets dibs
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1) return ret;

		return crystallizations.stream() // Checks both outputs
				.filter(e -> APIUtils.areObjectsEqual(stack, e.getOutputs(), false))
				.map(e -> e.XP)
				.findFirst()
				.orElse(0.0F);
	}
}
