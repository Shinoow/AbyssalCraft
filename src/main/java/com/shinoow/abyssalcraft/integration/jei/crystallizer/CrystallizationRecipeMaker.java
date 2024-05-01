/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei.crystallizer;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.recipe.Crystallization;
import com.shinoow.abyssalcraft.api.recipe.CrystallizerRecipes;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;

public class CrystallizationRecipeMaker {

	@Nonnull
	public static List<CrystallizationRecipe> getCrystallizerRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
		CrystallizerRecipes crystallizerRecipes = CrystallizerRecipes.instance();
		List<Crystallization> crystallizationMap = getCrystallizations(crystallizerRecipes);

		List<CrystallizationRecipe> recipes = new ArrayList<>();

		for (Crystallization itemStackItemStackEntry : crystallizationMap) {
			ItemStack input = itemStackItemStackEntry.INPUT;
			ItemStack output = itemStackItemStackEntry.OUTPUT1;
			ItemStack output2 = itemStackItemStackEntry.OUTPUT2;

			float experience = crystallizerRecipes.getExperience(output);

			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			CrystallizationRecipe recipe = new CrystallizationRecipe(inputs, output, output2, experience);
			if(isRecipeValid(recipe))
				recipes.add(recipe);
		}

		return recipes;
	}

	private static boolean isRecipeValid(@Nonnull CrystallizationRecipe recipe) {
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}

	private static List<Crystallization> getCrystallizations(@Nonnull CrystallizerRecipes crystallizerRecipes) {
		return crystallizerRecipes.getCrystallizationList();
	}
}
