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
package com.shinoow.abyssalcraft.integration.jei.ritual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconTransformationRitual;
import com.shinoow.abyssalcraft.integration.jei.JEIUtils;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.item.ItemStack;


public class TransformationRitualRecipeWrapper extends RitualRecipeWrapper {

	private final ItemStack input, output;
	private final int bookType;

	public TransformationRitualRecipeWrapper(@Nonnull NecronomiconTransformationRitual ritual){
		super(ritual);
		bookType = ritual.getBookType();
		input = ritual.getInput();
		output = ritual.getOutput();
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		List<List<ItemStack>> inputStacks = new ArrayList<>();
		Object[] offerings = APIUtils.makeArrayOf(new ItemStack[]{input, output}, 8);

		for(Object obj : offerings)
			inputStacks.add(JEIUtils.parseAsList(obj));
		inputStacks.add(Collections.singletonList(input));
		// Reject the altar sacrifice until the day when I suddenly need it here too
		inputStacks.add(Collections.singletonList(JEIUtils.getItem(bookType)));
		ingredients.setInputLists(VanillaTypes.ITEM, inputStacks);
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}
}