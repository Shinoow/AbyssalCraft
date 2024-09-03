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
package com.shinoow.abyssalcraft.integration.jei.materializer;

import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeBackgrounds;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryBase;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;

public class MaterializationRecipeCategory extends ACRecipeCategoryBase<MaterializationRecipeWrapper> {

	private static final int input1 = 0;
	private static final int input2 = 1;
	private static final int input3 = 2;
	private static final int input4 = 3;
	private static final int input5 = 4;
	private static final int output = 5;

	public MaterializationRecipeCategory(IGuiHelper guiHelper){
		super(I18n.format("container.abyssalcraft.materializer.nei"), ACRecipeCategoryUid.MATERIALIZATION);
		background = guiHelper.createDrawable(ACRecipeBackgrounds.MATERIALIZER, 32, 47, 116, 72);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, MaterializationRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();

		itemStacks.init(input1, true, 2, 2);
		itemStacks.init(input2, true, 25, 2);
		itemStacks.init(input3, true, 49, 2);
		itemStacks.init(input4, true, 72, 2);
		itemStacks.init(input5, true, 95, 2);
		itemStacks.init(output, false, 49, 54);

		itemStacks.set(ingredients);
	}
}
