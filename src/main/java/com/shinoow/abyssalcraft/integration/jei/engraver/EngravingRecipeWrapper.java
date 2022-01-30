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

import java.awt.Color;
import java.util.ArrayList;
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

public class EngravingRecipeWrapper implements IRecipeWrapper {

	@Nonnull
	private final List<List<ItemStack>> input;
	private final ItemStack engraving;
	@Nonnull
	private final List<ItemStack> output;

	@Nullable
	private final String experienceString;

	public EngravingRecipeWrapper(@Nonnull List<ItemStack> input, @Nonnull ItemStack engraving, @Nonnull ItemStack output, float experience) {
		this.input = Collections.singletonList(input);
		this.engraving = engraving;
		this.output = Collections.singletonList(output);

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
	public ItemStack getEngraving(){
		return engraving;
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		return output;
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
		List<List<ItemStack>> inputs = new ArrayList<>(input);
		inputs.add(Collections.singletonList(engraving));
		ingredients.setInputLists(ItemStack.class, inputs);
		ingredients.setOutputs(ItemStack.class, output);
	}
}
