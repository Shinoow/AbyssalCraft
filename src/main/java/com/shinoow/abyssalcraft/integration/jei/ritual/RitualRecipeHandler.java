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
package com.shinoow.abyssalcraft.integration.jei.ritual;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

public class RitualRecipeHandler implements IRecipeHandler<RitualRecipeWrapper>{

	@Nonnull
	@Override
	public Class<RitualRecipeWrapper> getRecipeClass() {

		return RitualRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {

		return AbyssalCraftRecipeCategoryUid.RITUAL;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull RitualRecipeWrapper recipe) {

		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull RitualRecipeWrapper recipe) {

		return recipe.getOfferings().length == 8 && recipe.getOutputs().size() == 1;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid(@Nonnull RitualRecipeWrapper recipe) {
		return AbyssalCraftRecipeCategoryUid.RITUAL;
	}
}
