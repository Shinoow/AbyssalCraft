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
package com.shinoow.abyssalcraft.integration.jei.ritual;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

public class RitualRecipeWrapper extends BlankRecipeWrapper {

	private final Object[] offerings;
	private final Object sacrifice;
	private final ItemStack output;
	private final int bookType;
	private final NecronomiconRitual ritual;
	private Map<Integer, String> dimToString = Maps.newHashMap();

	public RitualRecipeWrapper(@Nonnull NecronomiconCreationRitual ritual){
		if(ritual.getOfferings().length < 8){
			offerings = new Object[8];
			for(int i = 0; i < ritual.getOfferings().length; i++)
				offerings[i] = ritual.getOfferings()[i];
		}else offerings = ritual.getOfferings();
		sacrifice = ritual.getSacrifice();
		output = ritual.getItem();
		bookType = ritual.getBookType();
		this.ritual = ritual;
	}

	public Object[] getOfferings(){
		return offerings;
	}

	public Object getSacrifice(){
		return sacrifice;
	}

	public int getBookType(){
		return bookType;
	}

	@Override
	public List<ItemStack> getOutputs(){
		return Collections.singletonList(output);
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		dimToString.put(-1, NecronomiconText.LABEL_ANYWHERE);
		dimToString.putAll(RitualRegistry.instance().getDimensionNameMappings());

		FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;

		boolean unicode = fr.getUnicodeFlag();
		fr.setUnicodeFlag(true);

		if(ritual.requiresSacrifice())
			fr.drawString(NecronomiconText.LABEL_SACRIFICE, 93, 124, 0xC40000);
		fr.drawSplitString(NecronomiconText.LABEL_LOCATION + ": " + getDimension(ritual.getDimension()), 93, 85, 70, 0);
		fr.drawSplitString(NecronomiconText.LABEL_REQUIRED_ENERGY + ": " + ritual.getReqEnergy() + " PE", 93, 108, 70, 0);

		fr.setUnicodeFlag(unicode);
	}

	private String getDimension(int dim){
		if(!dimToString.containsKey(dim))
			dimToString.put(dim, "DIM"+dim);
		return dimToString.get(dim);
	}
}
