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
package com.shinoow.abyssalcraft.integration.jei.upgrades;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.item.ItemUpgradeKit;
import com.shinoow.abyssalcraft.api.recipe.UpgradeKitRecipes;

import net.minecraft.item.ItemStack;

public class UpgradeRecipeMaker {

	@Nonnull
	public static List<UpgradeRecipeWrapper> getUpgrades(){
		List<UpgradeRecipeWrapper> recipes = new ArrayList();

		for(Entry<ItemUpgradeKit, Map<ItemStack, ItemStack>> upgrades : UpgradeKitRecipes.instance().getAllUpgrades().entrySet())
			for(Entry<ItemStack, ItemStack> e : upgrades.getValue().entrySet()){
				UpgradeRecipeWrapper recipe = new UpgradeRecipeWrapper(upgrades.getKey(), e.getKey(), e.getValue());
				if(isRecipeValid(recipe))
					recipes.add(recipe);
			}

		return recipes;
	}

	private static boolean isRecipeValid(@Nonnull UpgradeRecipeWrapper recipe) {

		return !recipe.getOutputs().isEmpty() && !recipe.getInput().isEmpty() && !recipe.getUpgradeKit().isEmpty();
	}
}
