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
package com.shinoow.abyssalcraft.integration.nei;

import net.minecraft.item.ItemStack;
import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class NEIACConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new CrystallizerRecipeHandler());
		API.registerUsageHandler(new CrystallizerRecipeHandler());

		API.registerRecipeHandler(new TransmutatorRecipeHandler());
		API.registerUsageHandler(new TransmutatorRecipeHandler());

		API.registerRecipeHandler(new EngraverRecipeHandler());
		API.registerUsageHandler(new EngraverRecipeHandler());

		API.registerRecipeHandler(new RitualRecipeHandler());
		API.registerUsageHandler(new RitualRecipeHandler());

		API.hideItem(new ItemStack(AbyssalCraft.devsword));
		API.hideItem(new ItemStack(AbyssalCraft.crystallizer_on));
		API.hideItem(new ItemStack(AbyssalCraft.transmutator_on));
		API.hideItem(new ItemStack(AbyssalCraft.house));
		API.hideItem(new ItemStack(AbyssalCraft.Altar));
	}

	@Override
	public String getName() {

		return "AbyssalCraft Plugin";
	}

	@Override
	public String getVersion() {

		return AbyssalCraft.version;
	}
}
