/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei.rending;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class RendingRecipeCategory implements IRecipeCategory {

	private static final int output = 0;

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final IDrawable slotDrawable;
	@Nonnull
	private final String localizedName;

	public RendingRecipeCategory(IGuiHelper guiHelper){
		background = guiHelper.createBlankDrawable(166, 100);
		localizedName = I18n.format("container.abyssalcraft.rending.nei");

		slotDrawable = guiHelper.getSlotDrawable();
	}

	@Override
	public String getUid() {

		return AbyssalCraftRecipeCategoryUid.RENDING;
	}

	@Override
	public String getTitle() {

		return localizedName;
	}

	@Override
	public IDrawable getBackground() {

		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		if(recipeWrapper instanceof RendingRecipeWrapper){
			IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
			itemStacks.init(output, false, 72, 0);

			itemStacks.set(ingredients);
		}
	}

	@Override
	public IDrawable getIcon() {

		return null;
	}

	@Override
	public String getModName() {

		return AbyssalCraft.name;
	}

	@Override
	public List getTooltipStrings(int mouseX, int mouseY) {

		return Collections.emptyList();
	}
}
