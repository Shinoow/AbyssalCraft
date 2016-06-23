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
package com.shinoow.abyssalcraft.integration.jei.engraver;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

public class EngravingRecipeHandler implements IRecipeHandler<EngravingRecipeWrapper> {

	@Override
	@Nonnull
	public Class<EngravingRecipeWrapper> getRecipeClass() {
		return EngravingRecipeWrapper.class;
	}

	@Nonnull
	@Override
	public String getRecipeCategoryUid() {
		return AbyssalCraftRecipeCategoryUid.ENGRAVING;
	}

	@Override
	@Nonnull
	public IRecipeWrapper getRecipeWrapper(@Nonnull EngravingRecipeWrapper recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull EngravingRecipeWrapper recipe) {
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}

	@Override
	public String getRecipeCategoryUid(EngravingRecipeWrapper recipe) {

		return AbyssalCraftRecipeCategoryUid.ENGRAVING;
	}
}
