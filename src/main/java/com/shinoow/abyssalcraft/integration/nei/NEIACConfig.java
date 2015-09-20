/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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

		API.hideItem(new ItemStack(AbyssalCraft.crystalIron));
		API.hideItem(new ItemStack(AbyssalCraft.crystalGold));
		API.hideItem(new ItemStack(AbyssalCraft.crystalSulfur));
		API.hideItem(new ItemStack(AbyssalCraft.crystalCarbon));
		API.hideItem(new ItemStack(AbyssalCraft.crystalOxygen));
		API.hideItem(new ItemStack(AbyssalCraft.crystalHydrogen));
		API.hideItem(new ItemStack(AbyssalCraft.crystalNitrogen));
		API.hideItem(new ItemStack(AbyssalCraft.crystalPhosphorus));
		API.hideItem(new ItemStack(AbyssalCraft.crystalPotassium));
		API.hideItem(new ItemStack(AbyssalCraft.crystalNitrate));
		API.hideItem(new ItemStack(AbyssalCraft.crystalMethane));
		API.hideItem(new ItemStack(AbyssalCraft.crystalRedstone));
		API.hideItem(new ItemStack(AbyssalCraft.crystalAbyssalnite));
		API.hideItem(new ItemStack(AbyssalCraft.crystalCoralium));
		API.hideItem(new ItemStack(AbyssalCraft.crystalDreadium));
		API.hideItem(new ItemStack(AbyssalCraft.crystalBlaze));
		API.hideItem(new ItemStack(AbyssalCraft.crystalTin));
		API.hideItem(new ItemStack(AbyssalCraft.crystalCopper));
		API.hideItem(new ItemStack(AbyssalCraft.crystalSilicon));
		API.hideItem(new ItemStack(AbyssalCraft.crystalMagnesium));
		API.hideItem(new ItemStack(AbyssalCraft.crystalAluminium));
		API.hideItem(new ItemStack(AbyssalCraft.crystalSilica));
		API.hideItem(new ItemStack(AbyssalCraft.crystalAlumina));
		API.hideItem(new ItemStack(AbyssalCraft.crystalMagnesia));
		API.hideItem(new ItemStack(AbyssalCraft.crystalZinc));
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
