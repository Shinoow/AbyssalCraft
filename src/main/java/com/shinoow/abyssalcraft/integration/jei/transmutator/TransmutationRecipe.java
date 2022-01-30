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
package com.shinoow.abyssalcraft.integration.jei.transmutator;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class TransmutationRecipe implements IRecipeWrapper {
	@Nonnull
	private final List<List<ItemStack>> input;
	@Nonnull
	private final List<ItemStack> outputs;

	@Nullable
	private final String experienceString;

	public TransmutationRecipe(@Nonnull List<ItemStack> input, @Nonnull ItemStack output, float experience) {
		this.input = Collections.singletonList(input);
		outputs = Collections.singletonList(output);

		if (experience > 0.0)
			experienceString = I18n.format("gui.jei.category.smelting.experience", experience);
		else
			experienceString = null;
	}

	@Nonnull
	public List<List<ItemStack>> getInputs() {
		return input;
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		return outputs;
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		if (experienceString != null) {
			FontRenderer fontRendererObj = minecraft.fontRenderer;
			fontRendererObj.drawString(experienceString, 69 - fontRendererObj.getStringWidth(experienceString) / 2, 0, Color.gray.getRGB());
		}
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, input);
		ingredients.setOutputs(ItemStack.class, outputs);
	}
}
