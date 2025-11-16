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
package com.shinoow.abyssalcraft.client.gui.necronomicon.entries;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.api.recipe.Transmutation;
import com.shinoow.abyssalcraft.api.recipe.TransmutatorRecipes;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomiconRecipeBase;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;

public class GuiNecronomiconTransmutatorEntry extends GuiNecronomiconRecipeBase<Transmutation> {

	public GuiNecronomiconTransmutatorEntry(int bookType, GuiNecronomicon parent) {
		super(bookType);
		this.parent = parent;
	}

	@Override
	protected void drawRecipes(int x, int y) {
		drawTitle(localize("container.abyssalcraft.transmutator"));

		drawTexture(NecronomiconResources.TRANSMUTATION);

		for(int n = 0; n < recipes.size(); n++){
			Transmutation entry = recipes.get(n);
			drawItem(entry, n, currTurnup*12, currTurnup*12+6, (currTurnup+1)*12, x, y);
		}
	}

	@Override
	protected void drawItem(Transmutation entry, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		if(num < mid && num > low-1){
			renderItem(k + 18, 30 + (num-low)*20 + num-low, entry.INPUT, x, y);
			renderItem(k + 62, 30 + (num-low)*20 + num-low, entry.OUTPUT, x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 141, 30 + (num-mid)*20 + num-mid, entry.INPUT, x, y);
			renderItem(k + 185, 30 + (num-mid)*20 + num-mid, entry.OUTPUT, x, y);
		}
		fontRenderer.setUnicodeFlag(unicode);
	}

	@Override
	protected void initStuff() {
		recipes = ImmutableList.copyOf(TransmutatorRecipes.instance().getTransmutationList());
		setTurnups(recipes.size());
	}
}
