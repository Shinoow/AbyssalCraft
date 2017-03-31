/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
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
import java.util.Map;

import javax.annotation.Nonnull;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

public class RendingRecipeWrapper extends BlankRecipeWrapper {

	private final ItemStack output;
	private final int dimension;
	private final String description;
	private final RendingEntry entry;
	private Map<Integer, String> dimToString = Maps.newHashMap();

	public RendingRecipeWrapper(@Nonnull RendingEntry entry){
		output = entry.output;
		dimension = entry.dimension;
		description = entry.description;
		this.entry = entry;
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
		dimToString.putAll(RitualRegistry.instance().getDimensionNameMappings());

		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

		if(entry.dimension != -1){
			fr.drawSplitString(I18n.format(NecronomiconText.LABEL_LOCATION, new Object[0]) + ": " + getDimension(entry.dimension), 2, 20, 180, 0);
			fr.drawSplitString("Energy type: " + entry.type, 2, 40, 180, 0);
			fr.drawSplitString(entry.description, 2, 70, 180, 0);
		} else{
			fr.drawSplitString("Energy type: " + entry.type, 2, 20, 180, 0);
			fr.drawSplitString(entry.description, 2, 40, 180, 0);
		}
	}

	private String getDimension(int dim){
		if(!dimToString.containsKey(dim))
			dimToString.put(dim, "DIM"+dim);
		return dimToString.get(dim);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setOutput(ItemStack.class, output);
	}
}
