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
package com.shinoow.abyssalcraft.integration.jei.ritual;

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

public class RitualRecipeCategory implements IRecipeCategory {

	private static final int pedestal1 = 0;
	private static final int pedestal2 = 1;
	private static final int pedestal3 = 2;
	private static final int pedestal4 = 3;
	private static final int pedestal5 = 4;
	private static final int pedestal6 = 5;
	private static final int pedestal7 = 6;
	private static final int pedestal8 = 7;
	private static final int sacrifice = 8;
	private static final int reward = 9;
	private static final int necronomicon = 10;

	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final IDrawable slotDrawable;
	@Nonnull
	private final String localizedName;

	public RitualRecipeCategory(IGuiHelper guiHelper){
		ResourceLocation location = new ResourceLocation("abyssalcraft", "textures/gui/container/ritual_NEI.png");
		background = guiHelper.createDrawable(location, 5, 11, 166, 140);
		localizedName = I18n.format("container.abyssalcraft.rituals.nei");

		slotDrawable = guiHelper.getSlotDrawable();
	}

	@Override
	public String getUid() {

		return AbyssalCraftRecipeCategoryUid.RITUAL;
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
		if(recipeWrapper instanceof RitualRecipeWrapper){
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
			itemStacks.init(sacrifice, true, 58 + xBoost, 66 + yBoost);
			itemStacks.init(necronomicon, true, 0 + xBoost, 133 + yBoost);
			itemStacks.init(reward, false, 58 + xBoost, 139 + yBoost);

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
