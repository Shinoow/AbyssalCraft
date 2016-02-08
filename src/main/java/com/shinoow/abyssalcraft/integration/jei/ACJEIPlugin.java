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
package com.shinoow.abyssalcraft.integration.jei;

import mezz.jei.api.*;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.inventory.ContainerCrystallizer;
import com.shinoow.abyssalcraft.common.inventory.ContainerEngraver;
import com.shinoow.abyssalcraft.common.inventory.ContainerTransmutator;
import com.shinoow.abyssalcraft.integration.jei.ritual.*;
import com.shinoow.abyssalcraft.integration.jei.transmutator.*;
import com.shinoow.abyssalcraft.integration.jei.crystallizer.*;
import com.shinoow.abyssalcraft.integration.jei.engraver.*;

@JEIPlugin
public class ACJEIPlugin implements IModPlugin {

	private IJeiHelpers jeiHelpers;

	@Override
	public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
		this.jeiHelpers = jeiHelpers;
	}

	@Override
	public void onItemRegistryAvailable(IItemRegistry itemRegistry) {
	}

	@Override
	public void register(IModRegistry registry) {
		if(!Loader.isModLoaded("abyssalcraft")) return;

		JEIUtils utils = new JEIUtils();

		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new TransmutatorFuelCategory(guiHelper),
				new TransmutationCategory(guiHelper),
				new CrystallizerFuelCategory(guiHelper),
				new CrystallizationCategory(guiHelper),
				new RitualRecipeCategory(guiHelper),
				new EngraverRecipeCategory(guiHelper));

		registry.addRecipeHandlers(new TransmutatorFuelRecipeHandler(),
				new TransmutationRecipeHandler(),
				new CrystallizerFuelRecipeHandler(),
				new CrystallizationRecipeHandler(),
				new RitualRecipeHandler(),
				new EngravingRecipeHandler());

		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		recipeTransferRegistry.addRecipeTransferHandler(ContainerTransmutator.class, AbyssalCraftRecipeCategoryUid.TRANSMUTATION, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTransmutator.class, AbyssalCraftRecipeCategoryUid.FUEL_TRANSMUTATION, 1, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCrystallizer.class, AbyssalCraftRecipeCategoryUid.CRYSTALLIZATION, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCrystallizer.class, AbyssalCraftRecipeCategoryUid.FUEL_CRYSTALLIZATION, 1, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerEngraver.class, AbyssalCraftRecipeCategoryUid.ENGRAVING, 0, 1, 1, 36);

		registry.addRecipes(TransmutationRecipeMaker.getTransmutatorRecipes(jeiHelpers));
		registry.addRecipes(TransmutatorFuelRecipeMaker.getFuelRecipes(utils, jeiHelpers));
		registry.addRecipes(CrystallizationRecipeMaker.getCrystallizerRecipes(jeiHelpers));
		registry.addRecipes(CrystallizerFuelRecipeMaker.getFuelRecipes(utils, jeiHelpers));
		registry.addRecipes(RitualRecipeMaker.getRituals());
		registry.addRecipes(EngravingRecipeMaker.getEngraverRecipes());

		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(AbyssalCraft.devsword));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(AbyssalCraft.crystallizer_on));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(AbyssalCraft.transmutator_on));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(AbyssalCraft.house));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(AbyssalCraft.Altar));
	}

	public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}
}
