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

import java.awt.Color;
import java.text.NumberFormat;
import java.util.*;

import javax.annotation.Nonnull;

import com.google.common.base.Preconditions;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class CrystallizerFuelRecipe implements IRecipeWrapper {
	@Nonnull
	private final List<List<ItemStack>> inputs;
	@Nonnull
	private final String smeltCountString;
	@Nonnull
	private final IDrawableAnimated flame;

	public CrystallizerFuelRecipe(@Nonnull IGuiHelper guiHelper, @Nonnull Collection<ItemStack> input, int burnTime) {
		Preconditions.checkArgument(burnTime > 0, "burn time must be greater than 0");
		List<ItemStack> inputList = new ArrayList<>(input);
		inputs = Collections.singletonList(inputList);

		if (burnTime == 200)
			smeltCountString = I18n.format("gui.jei.category.fuel.smeltCount.single");
		else {
			NumberFormat numberInstance = NumberFormat.getNumberInstance();
			numberInstance.setMaximumFractionDigits(2);
			String smeltCount = numberInstance.format(burnTime / 200f);
			smeltCountString = I18n.format("gui.jei.category.fuel.smeltCount", smeltCount);
		}
		ResourceLocation furnaceBackgroundLocation = new ResourceLocation("abyssalcraft", "textures/gui/container/crystallizer_NEI.png");
		flame = guiHelper.drawableBuilder(furnaceBackgroundLocation, 176, 0, 14, 14)
				.buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		flame.draw(minecraft, 1, -2);
		minecraft.fontRenderer.drawString(smeltCountString, 24, 13, Color.gray.getRGB());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, inputs);
	}
}
