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
import net.minecraft.util.ResourceLocation;

public class CrystallizationCategory extends CrystallizerRecipeCategory {
	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;

	public CrystallizationCategory(IGuiHelper guiHelper) {
		super(guiHelper);
		ResourceLocation location = new ResourceLocation("abyssalcraft", "textures/gui/container/crystallizer_NEI.png");
		background = guiHelper.createDrawable(location, 55, 16, 116, 54);
		localizedName = I18n.format("container.abyssalcraft.crystallizer.nei");
	}

	@Override
	@Nonnull
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		flame.draw(minecraft, 2, 20);
		arrow.draw(minecraft, 24, 18);
	}

	@Nonnull
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Nonnull
	@Override
	public String getUid() {
		return AbyssalCraftRecipeCategoryUid.CRYSTALLIZATION;
	}

	@Override
	public void setRecipe(@Nonnull IRecipeLayout recipeLayout, @Nonnull IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 0, 0);
		guiItemStacks.init(outputSlot, false, 60, 18);
		guiItemStacks.init(outputSlot2, false, 77, 18);

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
