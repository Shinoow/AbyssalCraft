/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.integration.nei;

import net.minecraft.item.ItemStack;
import codechicken.nei.api.*;

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

		API.hideItem(new ItemStack(AbyssalCraft.devsword));
		API.hideItem(new ItemStack(AbyssalCraft.crystallizer_on));
		API.hideItem(new ItemStack(AbyssalCraft.transmutator_on));
		API.hideItem(new ItemStack(AbyssalCraft.engraver_on));
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