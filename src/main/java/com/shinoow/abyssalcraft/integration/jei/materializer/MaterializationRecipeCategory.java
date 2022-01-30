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
package com.shinoow.abyssalcraft.integration.jei.materializer;

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
import net.minecraft.util.ResourceLocation;

public class MaterializationRecipeCategory implements IRecipeCategory {

	private static final int input1 = 0;
	private static final int input2 = 1;
	private static final int input3 = 2;
	private static final int input4 = 3;
	private static final int input5 = 4;
	private static final int output = 5;

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final IDrawable slotDrawable;
	@Nonnull
	private final String localizedName;

	public MaterializationRecipeCategory(IGuiHelper guiHelper){
		ResourceLocation location = new ResourceLocation("abyssalcraft", "textures/gui/container/materializer_NEI.png");
		background = guiHelper.createDrawable(location, 32, 47, 116, 72);
		localizedName = I18n.format("container.abyssalcraft.materializer.nei");

		slotDrawable = guiHelper.getSlotDrawable();
	}

	@Override
	public String getUid() {

		return AbyssalCraftRecipeCategoryUid.MATERIALIZATION;
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
		if(recipeWrapper instanceof MaterializationRecipeWrapper){
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
