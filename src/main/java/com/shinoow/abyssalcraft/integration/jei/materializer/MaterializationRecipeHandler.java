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
package com.shinoow.abyssalcraft.integration.jei.materializer;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.shinoow.abyssalcraft.api.recipe.Materialization;
import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

public class MaterializationRecipeHandler implements IRecipeHandler<Materialization> {

	@Override
	public Class<Materialization> getRecipeClass() {

		return Materialization.class;
	}

	@Override
	public String getRecipeCategoryUid(Materialization recipe) {

		return AbyssalCraftRecipeCategoryUid.MATERIALIZATION;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(Materialization recipe) {

		return new MaterializationRecipeWrapper(recipe);
	}

	@Override
	public boolean isRecipeValid(Materialization recipe) {

		return recipe.input.length > 0 && !recipe.output.isEmpty();
	}

}
