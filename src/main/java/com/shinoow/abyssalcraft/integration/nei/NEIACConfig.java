package com.shinoow.abyssalcraft.integration.nei;

import codechicken.nei.api.API;
import codechicken.nei.api.IConfigureNEI;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.client.gui.*;

public class NEIACConfig implements IConfigureNEI {

	@Override
	public void loadConfig() {
		API.registerRecipeHandler(new CrystallizerRecipeHandler());
		API.registerUsageHandler(new CrystallizerRecipeHandler());
		API.setGuiOffset(GuiCrystallizer.class, 0, 0);

		API.registerRecipeHandler(new TransmutatorRecipeHandler());
		API.registerUsageHandler(new TransmutatorRecipeHandler());
		API.setGuiOffset(GuiTransmutator.class, 0, 0);
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