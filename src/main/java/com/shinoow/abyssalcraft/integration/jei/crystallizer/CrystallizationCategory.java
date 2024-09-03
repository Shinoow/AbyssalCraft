/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class CrystallizationCategory extends CrystallizerRecipeCategory<CrystallizationRecipeWrapper> {

	public CrystallizationCategory(IGuiHelper guiHelper) {
		super(guiHelper, I18n.format("container.abyssalcraft.crystallizer.nei"), ACRecipeCategoryUid.CRYSTALLIZATION);
		background = guiHelper.createDrawable(ACRecipeBackgrounds.CRYSTALLIZER, 55, 16, 116, 54);
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		flame.draw(minecraft, 2, 20);
		arrow.draw(minecraft, 24, 18);
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull CrystallizationRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 0, 0);
		guiItemStacks.init(outputSlot, false, 60, 18);
		guiItemStacks.init(outputSlot2, false, 77, 18);

		guiItemStacks.set(ingredients);
	}
}
