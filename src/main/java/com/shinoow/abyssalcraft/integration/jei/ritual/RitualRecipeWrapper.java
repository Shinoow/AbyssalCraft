/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei.ritual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.integration.jei.util.JEIUtils;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class RitualRecipeWrapper implements IRecipeWrapper {

	private final Object[] offerings;
	private final Object sacrifice;
	private final int bookType;
	private final NecronomiconRitual ritual;

	public RitualRecipeWrapper(@Nonnull NecronomiconRitual ritual){
		if(ritual.getOfferings().length < 8){
			offerings = new Object[8];
			for(int i = 0; i < ritual.getOfferings().length; i++)
				offerings[i] = ritual.getOfferings()[i];
		}else offerings = ritual.getOfferings();
		sacrifice = ritual.getSacrifice();
		bookType = ritual.getBookType();
		this.ritual = ritual;
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {

		FontRenderer fr = Minecraft.getMinecraft().fontRenderer;

		boolean unicode = fr.getUnicodeFlag();
		fr.setUnicodeFlag(true);

		if(ritual.requiresSacrifice())
			fr.drawString(I18n.format(NecronomiconText.LABEL_SACRIFICE, new Object[0]), 93, 124, 0xC40000);
		fr.drawSplitString(I18n.format(NecronomiconText.LABEL_LOCATION, new Object[0]) + ": " + JEIUtils.getDimension(ritual.getDimension()), 93, 85, 70, 0);
		fr.drawSplitString(I18n.format(NecronomiconText.LABEL_REQUIRED_ENERGY, new Object[0]) + ": " + ritual.getReqEnergy() + " PE", 93, 108, 70, 0);

		fr.setUnicodeFlag(unicode);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		List<List<ItemStack>> input = new ArrayList<>();

		for(Object obj : offerings)
			input.add(JEIUtils.parseAsList(obj));
		input.add(JEIUtils.parseAsList(sacrifice));
		input.add(Collections.singletonList(JEIUtils.getItem(bookType)));
		ingredients.setInputLists(VanillaTypes.ITEM, input);
	}
}
