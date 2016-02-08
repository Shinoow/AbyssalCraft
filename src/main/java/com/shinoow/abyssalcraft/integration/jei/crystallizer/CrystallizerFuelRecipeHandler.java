/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

public class CrystallizerFuelRecipeHandler implements IRecipeHandler<CrystallizerFuelRecipe> {
	@Override
	@Nonnull
	public Class<CrystallizerFuelRecipe> getRecipeClass() {
		return CrystallizerFuelRecipe.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return VanillaRecipeCategoryUid.FUEL;
	}

	@Override
	@Nonnull
	public IRecipeWrapper getRecipeWrapper(@Nonnull CrystallizerFuelRecipe recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull CrystallizerFuelRecipe recipe) {
		return recipe.getInputs().size() > 0 && recipe.getOutputs().size() == 0;
	}
}
