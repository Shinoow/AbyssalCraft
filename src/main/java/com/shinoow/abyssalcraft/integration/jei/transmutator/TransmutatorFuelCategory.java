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
package com.shinoow.abyssalcraft.integration.jei.transmutator;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.translation.I18n;

import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

public class TransmutatorFuelCategory extends TransmutatorRecipeCategory {
	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;

	public TransmutatorFuelCategory(IGuiHelper guiHelper) {
		super(guiHelper);
		background = guiHelper.createDrawable(backgroundLocation, 55, 38, 18, 32, 0, 0, 0, 80);
		localizedName = I18n.translateToLocal("gui.acjei.category.fuel.transmutator");
	}

	@Override
	@Nonnull
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {

	}

	@Override
	public void drawAnimations(Minecraft minecraft) {

	}

	@Nonnull
	@Override
	public String getUid() {
		return AbyssalCraftRecipeCategoryUid.FUEL_TRANSMUTATION;
	}

	@Nonnull
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(fuelSlot, true, 0, 14);

		guiItemStacks.set(ingredients);
	}

	@Override
	public IDrawable getIcon() {

		return null;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {}
}
