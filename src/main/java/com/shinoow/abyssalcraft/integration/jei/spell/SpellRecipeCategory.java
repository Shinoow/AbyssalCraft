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
package com.shinoow.abyssalcraft.integration.jei.spell;

import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryBase;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;

public class SpellRecipeCategory extends ACRecipeCategoryBase<SpellRecipeWrapper> {

	public SpellRecipeCategory(IGuiHelper guiHelper) {
		super(I18n.format("container.abyssalcraft.spells.nei"), ACRecipeCategoryUid.SPELL);
		background = guiHelper.createBlankDrawable(166, 100);
		//TODO do the thing
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SpellRecipeWrapper recipeWrapper, IIngredients ingredients) {

	}
}
