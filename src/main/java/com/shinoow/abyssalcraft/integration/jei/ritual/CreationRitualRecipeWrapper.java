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
package com.shinoow.abyssalcraft.integration.jei.ritual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;

public class CreationRitualRecipeWrapper extends RitualRecipeWrapper {

	private final ItemStack output;

	public CreationRitualRecipeWrapper(@Nonnull NecronomiconCreationRitual ritual){
		super(ritual);
		output = ritual.getItem();
	}

	public List<ItemStack> getOutputs(){
		return Collections.singletonList(output);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		super.getIngredients(ingredients);
		List<List<ItemStack>> input = new ArrayList<>();
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}
}
