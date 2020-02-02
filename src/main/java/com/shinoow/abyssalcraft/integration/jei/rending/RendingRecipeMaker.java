/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei.rending;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.item.ItemStack;

public class RendingRecipeMaker {

	@Nonnull
	public static List<RendingRecipeWrapper> getRending(){
		List<RendingRecipeWrapper> recipes = new ArrayList();

		recipes.add(new RendingRecipeWrapper(new RendingEntry(new ItemStack(ACItems.shadow_gem), "tooltip.drainstaff.energy.4", "ac.rending.shadowgem")));
		recipes.add(new RendingRecipeWrapper(new RendingEntry(new ItemStack(ACItems.essence, 1, 0), ACLib.abyssal_wasteland_id, "tooltip.drainstaff.energy.1", "ac.rending.essence_aw")));
		recipes.add(new RendingRecipeWrapper(new RendingEntry(new ItemStack(ACItems.essence, 1, 1), ACLib.dreadlands_id, "tooltip.drainstaff.energy.2", "ac.rending.essence_dl")));
		recipes.add(new RendingRecipeWrapper(new RendingEntry(new ItemStack(ACItems.essence, 1, 2), ACLib.omothol_id, "tooltip.drainstaff.energy.3", "ac.rending.essence_omt")));

		return recipes;
	}
}
