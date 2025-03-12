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
package com.shinoow.abyssalcraft.integration.jei.ritual;

import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeBackgrounds;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryBase;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;

public class TransformationRitualRecipeCategory extends ACRecipeCategoryBase<TransformationRitualRecipeWrapper> {

	private static final int pedestal1 = 0;
	private static final int pedestal2 = 1;
	private static final int pedestal3 = 2;
	private static final int pedestal4 = 3;
	private static final int pedestal5 = 4;
	private static final int pedestal6 = 5;
	private static final int pedestal7 = 6;
	private static final int pedestal8 = 7;
	private static final int input = 8;
	private static final int output = 9;
	private static final int necronomicon = 10;

	public TransformationRitualRecipeCategory(IGuiHelper guiHelper){
		super(I18n.format("container.abyssalcraft.transformationrituals.jei"), ACRecipeCategoryUid.TRANSFORMATION_RITUAL);
		background = guiHelper.createDrawable(ACRecipeBackgrounds.TRANSFORMATION_RITUAL, 5, 11, 166, 140);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, TransformationRitualRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
		int xBoost = 14, yBoost = -25;

		itemStacks.init(pedestal1, true, 58 + xBoost, 30 + yBoost);
		itemStacks.init(pedestal2, true, 84 + xBoost, 40 + yBoost);
		itemStacks.init(pedestal3, true, 94 + xBoost, 66 + yBoost);
		itemStacks.init(pedestal4, true, 84 + xBoost, 92 + yBoost);
		itemStacks.init(pedestal5, true, 58 + xBoost, 103 + yBoost);
		itemStacks.init(pedestal6, true, 32 + xBoost, 92 + yBoost);
		itemStacks.init(pedestal7, true, 22 + xBoost, 66 + yBoost);
		itemStacks.init(pedestal8, true, 32 + xBoost, 40 + yBoost);
		itemStacks.init(input, true, 6 + xBoost, 139 + yBoost);
		itemStacks.init(output, false, 58 + xBoost, 139 + yBoost);
		itemStacks.init(necronomicon, true, 0 + xBoost, 112 + yBoost);

		itemStacks.set(ingredients);
	}
}
