/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei.crystallizer;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

public class CrystallizationRecipeHandler implements IRecipeHandler<CrystallizationRecipe> {

	@Override
	@Nonnull
	public Class<CrystallizationRecipe> getRecipeClass() {
		return CrystallizationRecipe.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return AbyssalCraftRecipeCategoryUid.CRYSTALLIZATION;
	}

	@Override
	@Nonnull
	public IRecipeWrapper getRecipeWrapper(@Nonnull CrystallizationRecipe recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull CrystallizationRecipe recipe) {
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}
}
