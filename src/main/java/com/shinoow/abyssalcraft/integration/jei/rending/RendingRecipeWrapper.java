/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei.rending;

import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.rending.Rending;
import com.shinoow.abyssalcraft.integration.jei.util.JEIUtils;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class RendingRecipeWrapper implements IRecipeWrapper {

	private final ItemStack output;
	private final int dimension;
	private final String type;
	private final String description;

	public RendingRecipeWrapper(@Nonnull Rending entry){
		output = entry.getOutput();
		dimension = entry.getDimension();
		type = I18n.format(entry.getTooltip());
		description = I18n.format(entry.getJeiDescription());
	}

	public int getDimension(){
		return dimension;
	}

	public String getDescription(){
		return description;
	}

	public List<ItemStack> getOutputs(){
		return Collections.singletonList(output);
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

		if(dimension != OreDictionary.WILDCARD_VALUE){
			fr.drawSplitString(I18n.format(NecronomiconText.LABEL_LOCATION, new Object[0]) + ": " + JEIUtils.getDimension(dimension), 2, 20, 180, 0);
			fr.drawSplitString("Energy type: " + type, 2, 40, 180, 0);
			fr.drawSplitString(description, 2, 70, 180, 0);
		} else{
			fr.drawSplitString("Energy type: " + type, 2, 20, 180, 0);
			fr.drawSplitString(description, 2, 40, 180, 0);
		}
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setOutput(VanillaTypes.ITEM, output);
	}
}
