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
package com.shinoow.abyssalcraft.integration.jei.rending;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.StatCollector;
import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

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
		localizedName = StatCollector.translateToLocal("container.abyssalcraft.rending.nei");

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
	public void drawAnimations(Minecraft minecraft) {}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		if(recipeWrapper instanceof RendingRecipeWrapper){
			IGuiItemStackGroup itemStacks = recipeLayout.getItemStacks();
			RendingRecipeWrapper wrapper = (RendingRecipeWrapper)recipeWrapper;

			itemStacks.init(output, false, 72, 0);

			itemStacks.setFromRecipe(output, wrapper.getOutputs());
		}
	}
}
