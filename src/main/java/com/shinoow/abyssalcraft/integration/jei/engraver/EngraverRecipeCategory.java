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
package com.shinoow.abyssalcraft.integration.jei.engraver;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.*;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class EngraverRecipeCategory implements IRecipeCategory {
	protected static final int inputSlot = 0;
	protected static final int engravingSlot = 1;
	protected static final int outputSlot = 2;

	protected final ResourceLocation backgroundLocation;
	@Nonnull
	protected final IDrawableAnimated arrow;
	@Nonnull
	private final IDrawable background;
	@Nonnull
	private final String localizedName;

	public EngraverRecipeCategory(IGuiHelper guiHelper) {
		backgroundLocation = new ResourceLocation("abyssalcraft", "textures/gui/container/engraver_NEI.png");

		IDrawableStatic arrowDrawable = guiHelper.createDrawable(backgroundLocation, 176, 14, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);


		ResourceLocation location = new ResourceLocation("abyssalcraft", "textures/gui/container/engraver_NEI.png");
		background = guiHelper.createDrawable(location,  55, 16, 82, 54);

		localizedName = I18n.format("container.abyssalcraft.engraver.nei");
	}

	@Override
	public String getUid() {

		return AbyssalCraftRecipeCategoryUid.ENGRAVING;
	}

	@Override
	@Nonnull
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 24, 18);
	}

	@Nonnull
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		if(recipeWrapper instanceof EngravingRecipeWrapper){
			IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

			guiItemStacks.init(inputSlot, true, 0, 0);
			guiItemStacks.init(engravingSlot, true, 0, 36);
			guiItemStacks.init(outputSlot, false, 60, 18);

			guiItemStacks.set(ingredients);
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
