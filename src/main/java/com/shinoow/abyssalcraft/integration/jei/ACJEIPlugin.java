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
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
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
	private IItemRegistry itemRegistry;

	@Override
	public void register(IModRegistry registry) {
		if(!Loader.isModLoaded("abyssalcraft")) return;

		jeiHelpers = registry.getJeiHelpers();
		itemRegistry = registry.getItemRegistry();
		JEIUtils utils = new JEIUtils(itemRegistry);

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

		registry.addRecipeCategoryCraftingItem(new ItemStack(ACBlocks.transmutator_idle), AbyssalCraftRecipeCategoryUid.TRANSMUTATION,
				AbyssalCraftRecipeCategoryUid.FUEL_TRANSMUTATION);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACBlocks.crystallizer_idle), AbyssalCraftRecipeCategoryUid.CRYSTALLIZATION,
				AbyssalCraftRecipeCategoryUid.FUEL_CRYSTALLIZATION);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACBlocks.engraver), AbyssalCraftRecipeCategoryUid.ENGRAVING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.necronomicon), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.abyssal_wasteland_necronomicon), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.dreadlands_necronomicon), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.omothol_necronomicon), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.abyssalnomicon), AbyssalCraftRecipeCategoryUid.RITUAL);

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
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ACBlocks.crystallizer_active));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ACBlocks.transmutator_active));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(AbyssalCraft.house));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(AbyssalCraft.Altar));
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}
}
