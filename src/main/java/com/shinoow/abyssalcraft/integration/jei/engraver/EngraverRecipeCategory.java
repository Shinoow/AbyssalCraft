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
package com.shinoow.abyssalcraft.integration.jei.engraver;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.integration.jei.AbyssalCraftRecipeCategoryUid;

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

		localizedName = Translator.translateToLocal("container.abyssalcraft.engraver.nei");
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

	}

	@Override
	public void drawAnimations(Minecraft minecraft) {
		arrow.draw(minecraft, 24, 18);
	}

	@Nonnull
	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper) {
		if(recipeWrapper instanceof EngravingRecipeWrapper){
			IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

			guiItemStacks.init(inputSlot, true, 0, 0);
			guiItemStacks.init(engravingSlot, true, 0, 36);
			guiItemStacks.init(outputSlot, false, 60, 18);

			guiItemStacks.setFromRecipe(inputSlot, recipeWrapper.getInputs());
			guiItemStacks.setFromRecipe(engravingSlot, ((EngravingRecipeWrapper) recipeWrapper).getEngraving());
			guiItemStacks.setFromRecipe(outputSlot, recipeWrapper.getOutputs());
		}
	}
}
