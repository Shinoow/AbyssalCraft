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
package com.shinoow.abyssalcraft.integration.jei.upgrades;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class UpgradeRecipeHandler implements IRecipeHandler<UpgradeRecipeWrapper>{

	@Nonnull
	@Override
	public Class<UpgradeRecipeWrapper> getRecipeClass() {

		return UpgradeRecipeWrapper.class;
	}

	@Override
	public String getRecipeCategoryUid() {

		return AbyssalCraftRecipeCategoryUid.UPGRADE;
	}

	@Nonnull
	@Override
	public IRecipeWrapper getRecipeWrapper(@Nonnull UpgradeRecipeWrapper recipe) {

		return recipe;
	}

	@Override
	public boolean isRecipeValid(@Nonnull UpgradeRecipeWrapper recipe) {

		return !recipe.getOutputs().isEmpty() && recipe.getInput() != null && recipe.getUpgradeKit() != null;
	}

	@Override
	@Nonnull
	public String getRecipeCategoryUid(UpgradeRecipeWrapper recipe) {

		return AbyssalCraftRecipeCategoryUid.UPGRADE;
	}
}
