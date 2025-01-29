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
package com.shinoow.abyssalcraft.integration.jei.rending;

import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryBase;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;

public class RendingRecipeCategory extends ACRecipeCategoryBase<RendingRecipeWrapper> {

	private static final int output = 0;

	public RendingRecipeCategory(IGuiHelper guiHelper){
		super(I18n.format("container.abyssalcraft.rending.nei"), ACRecipeCategoryUid.RENDING);
		background = guiHelper.createBlankDrawable(166, 100);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, RendingRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		itemStacks.init(output, false, 72, 0);

		itemStacks.set(ingredients);
	}
}
