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
package com.shinoow.abyssalcraft.integration.jei.crystallizer;

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
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

public class CrystallizerFuelCategory extends CrystallizerRecipeCategory {
	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;

	public CrystallizerFuelCategory(IGuiHelper guiHelper) {
		super(guiHelper);
		background = guiHelper.drawableBuilder(backgroundLocation, 55, 38, 18, 32).addPadding(0, 0, 0, 80).build();
		localizedName = I18n.format("gui.acjei.category.fuel.crystallizer");
	}

	@Override
	@Nonnull
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {

	}

	@Nonnull
	@Override
	public String getUid() {
		return AbyssalCraftRecipeCategoryUid.FUEL_CRYSTALLIZATION;
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
	public String getModName() {

		return AbyssalCraft.name;
	}

	@Override
	public List getTooltipStrings(int mouseX, int mouseY) {

		return Collections.emptyList();
	}
}
