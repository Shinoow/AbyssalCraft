/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei.transmutator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.recipe.TransmutatorRecipes;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public class TransmutationRecipeMaker {

	@Nonnull
	public static List<TransmutationRecipe> getTransmutatorRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
		TransmutatorRecipes transmutatorRecipes = TransmutatorRecipes.instance();
		Map<ItemStack, ItemStack> transmutationMap = getTransmutationMap(transmutatorRecipes);

		List<TransmutationRecipe> recipes = new ArrayList<>();

		for (Map.Entry<ItemStack, ItemStack> itemStackItemStackEntry : transmutationMap.entrySet()) {
			ItemStack input = itemStackItemStackEntry.getKey();
			ItemStack output = itemStackItemStackEntry.getValue();

			float experience = transmutatorRecipes.getExperience(output);

			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			TransmutationRecipe recipe = new TransmutationRecipe(inputs, output, experience);
			if(isRecipeValid(recipe))
				recipes.add(recipe);
		}

		return recipes;
	}

	private static boolean isRecipeValid(@Nonnull TransmutationRecipe recipe) {
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}

	private static Map<ItemStack, ItemStack> getTransmutationMap(@Nonnull TransmutatorRecipes transmutatorRecipes) {
		return transmutatorRecipes.getTransmutationList();
	}
}
