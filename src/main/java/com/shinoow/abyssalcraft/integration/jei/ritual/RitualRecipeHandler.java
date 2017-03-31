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
package com.shinoow.abyssalcraft.integration.jei.ritual;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

public class RitualRecipeHandler implements IRecipeHandler<NecronomiconCreationRitual>{

	@Nonnull
	@Override
	public Class<NecronomiconCreationRitual> getRecipeClass() {

		return NecronomiconCreationRitual.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {

		return AbyssalCraftRecipeCategoryUid.RITUAL;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull NecronomiconCreationRitual recipe) {

		return new RitualRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(@Nonnull NecronomiconCreationRitual recipe) {

		return recipe.getOfferings().length > 0 && recipe.getItem() != null;
	}

	@Override
	public String getRecipeCategoryUid(NecronomiconCreationRitual recipe) {

		return AbyssalCraftRecipeCategoryUid.RITUAL;
	}
}
