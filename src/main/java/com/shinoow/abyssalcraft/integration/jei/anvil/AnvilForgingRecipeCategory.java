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
package com.shinoow.abyssalcraft.integration.jei.anvil;

import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeBackgrounds;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryBase;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;

public class AnvilForgingRecipeCategory extends ACRecipeCategoryBase<AnvilForgingRecipeWrapper> {

	private static final int input1 = 0;
	private static final int input2 = 1;
	private static final int output = 2;

	public AnvilForgingRecipeCategory(IGuiHelper guiHelper) {
		super(I18n.format("container.abyssalcraft.anvil.jei"), ACRecipeCategoryUid.ANVIL);
		background = guiHelper.createDrawable(ACRecipeBackgrounds.ANVIL, 15, 46, 146, 26);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, AnvilForgingRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

		itemStacks.init(input1, true, 11, 0);
		itemStacks.init(input2, true, 60, 0);
		itemStacks.init(output, false, 118, 0);

		itemStacks.set(ingredients);
	}

}
