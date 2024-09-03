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
package com.shinoow.abyssalcraft.integration.jei.materializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.shinoow.abyssalcraft.api.recipe.Materialization;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class MaterializationRecipeWrapper implements IRecipeWrapper {

	private final ItemStack[] input;
	private final ItemStack output;

	public MaterializationRecipeWrapper(Materialization materialization) {
		if(materialization.input.length < 5){
			input = new ItemStack[5];
			for(int i = 0; i < materialization.input.length; i++)
				input[i] = materialization.input[i];
		} else input = materialization.input;
		output = materialization.output;

	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputs = new ArrayList<>();

		for(ItemStack stack : input)
			inputs.add(Collections.singletonList(stack));
		ingredients.setInputLists(VanillaTypes.ITEM, inputs);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}
}
