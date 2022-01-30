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
package com.shinoow.abyssalcraft.integration.jei.engraver;

import java.util.*;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.recipe.EngraverRecipes;

import net.minecraft.item.ItemStack;

public class EngravingRecipeMaker {

	@Nonnull
	public static List<EngravingRecipeWrapper> getEngraverRecipes() {
		EngraverRecipes engraverRecipes = EngraverRecipes.instance();
		Map<ItemStack, ItemStack> engravingMap = getEngravingMap(engraverRecipes);

		List<EngravingRecipeWrapper> recipes = new ArrayList<>();

		for (Map.Entry<ItemStack, ItemStack> itemStackItemStackEntry : engravingMap.entrySet()) {
			ItemStack engraving = itemStackItemStackEntry.getKey();
			ItemStack output = itemStackItemStackEntry.getValue();

			float experience = engraverRecipes.getExperience(output);

			List<ItemStack> inputs = new ArrayList();

			if(output.getItem() == ACItems.coin){
				inputs = engraverRecipes.getCoinList();
				for(ItemStack stack : inputs)
					if(stack.getItem() == ACItems.coin){
						inputs.remove(stack);
						break;
					}
			} else inputs = Collections.singletonList(new ItemStack(ACItems.coin));

			EngravingRecipeWrapper recipe = new EngravingRecipeWrapper(inputs, engraving, output, experience);
			if(isRecipeValid(recipe))
				recipes.add(recipe);
		}

		return recipes;
	}

	private static boolean isRecipeValid(@Nonnull EngravingRecipeWrapper recipe) {
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}

	private static Map<ItemStack, ItemStack> getEngravingMap(@Nonnull EngraverRecipes engraverRecipes) {
		return engraverRecipes.getEngravingList();
	}
}
