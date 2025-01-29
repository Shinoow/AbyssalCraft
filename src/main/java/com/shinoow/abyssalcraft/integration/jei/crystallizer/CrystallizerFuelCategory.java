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
package com.shinoow.abyssalcraft.integration.jei.crystallizer;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeBackgrounds;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;

public class CrystallizerFuelCategory extends CrystallizerRecipeCategory<CrystallizerFuelRecipeWrapper> {

	public CrystallizerFuelCategory(IGuiHelper guiHelper) {
		super(guiHelper, I18n.format("gui.acjei.category.fuel.crystallizer"), ACRecipeCategoryUid.FUEL_CRYSTALLIZATION);
		background = guiHelper.drawableBuilder(ACRecipeBackgrounds.CRYSTALLIZER, 55, 38, 18, 32).addPadding(0, 0, 0, 80).build();
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull CrystallizerFuelRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(fuelSlot, true, 0, 14);
		guiItemStacks.set(ingredients);
	}
}
