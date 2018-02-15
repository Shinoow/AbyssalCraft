/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei.transmutator;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class TransmutatorFuelRecipeHandler implements IRecipeHandler<TransmutatorFuelRecipe> {
	@Override
	@Nonnull
	public Class<TransmutatorFuelRecipe> getRecipeClass() {
		return TransmutatorFuelRecipe.class;
	}

	@Override
	@Nonnull
	public IRecipeWrapper getRecipeWrapper(@Nonnull TransmutatorFuelRecipe recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull TransmutatorFuelRecipe recipe) {
		return recipe.getInputs().size() > 0;
	}

	@Override
	public String getRecipeCategoryUid(TransmutatorFuelRecipe recipe) {

		return AbyssalCraftRecipeCategoryUid.FUEL_TRANSMUTATION;
	}
}
