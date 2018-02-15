/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;
import com.shinoow.abyssalcraft.common.inventory.*;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.init.ItemHandler;
import com.shinoow.abyssalcraft.integration.jei.crystallizer.*;
import com.shinoow.abyssalcraft.integration.jei.engraver.EngraverRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.engraver.EngravingRecipeHandler;
import com.shinoow.abyssalcraft.integration.jei.engraver.EngravingRecipeMaker;
import com.shinoow.abyssalcraft.integration.jei.materializer.MaterializationRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.materializer.MaterializationRecipeHandler;
import com.shinoow.abyssalcraft.integration.jei.rending.RendingRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.rending.RendingRecipeHandler;
import com.shinoow.abyssalcraft.integration.jei.rending.RendingRecipeMaker;
import com.shinoow.abyssalcraft.integration.jei.ritual.RitualRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.ritual.RitualRecipeHandler;
import com.shinoow.abyssalcraft.integration.jei.ritual.RitualRecipeMaker;
import com.shinoow.abyssalcraft.integration.jei.transmutator.*;
import com.shinoow.abyssalcraft.integration.jei.upgrades.UpgradeRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.upgrades.UpgradeRecipeHandler;
import com.shinoow.abyssalcraft.integration.jei.upgrades.UpgradeRecipeMaker;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

@JEIPlugin
public class ACJEIPlugin implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {
		if(!Loader.isModLoaded("abyssalcraft")) return;

		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		JEIUtils utils = new JEIUtils(registry.getIngredientRegistry());

		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		registry.addRecipeCategories(new TransmutatorFuelCategory(guiHelper),
			new TransmutationCategory(guiHelper),
			new CrystallizerFuelCategory(guiHelper),
			new CrystallizationCategory(guiHelper),
			new RitualRecipeCategory(guiHelper),
			new EngraverRecipeCategory(guiHelper),
			new RendingRecipeCategory(guiHelper),
			new UpgradeRecipeCategory(guiHelper),
			new MaterializationRecipeCategory(guiHelper));

		registry.addRecipeHandlers(new TransmutatorFuelRecipeHandler(),
			new TransmutationRecipeHandler(),
			new CrystallizerFuelRecipeHandler(),
			new CrystallizationRecipeHandler(),
			new RitualRecipeHandler(),
			new EngravingRecipeHandler(),
			new RendingRecipeHandler(),
			new UpgradeRecipeHandler(),
			new MaterializationRecipeHandler());

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
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.staff_of_rending, 1, 0), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.staff_of_rending, 1, 1), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.staff_of_rending, 1, 2), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.staff_of_rending, 1, 3), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACItems.staff_of_the_gatekeeper), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(Blocks.ANVIL, 1, 0), AbyssalCraftRecipeCategoryUid.UPGRADE);
		registry.addRecipeCategoryCraftingItem(new ItemStack(Blocks.ANVIL, 1, 1), AbyssalCraftRecipeCategoryUid.UPGRADE);
		registry.addRecipeCategoryCraftingItem(new ItemStack(Blocks.ANVIL, 1, 2), AbyssalCraftRecipeCategoryUid.UPGRADE);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ACBlocks.materializer), AbyssalCraftRecipeCategoryUid.MATERIALIZATION);

		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		recipeTransferRegistry.addRecipeTransferHandler(ContainerTransmutator.class, AbyssalCraftRecipeCategoryUid.TRANSMUTATION, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTransmutator.class, AbyssalCraftRecipeCategoryUid.FUEL_TRANSMUTATION, 1, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCrystallizer.class, AbyssalCraftRecipeCategoryUid.CRYSTALLIZATION, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCrystallizer.class, AbyssalCraftRecipeCategoryUid.FUEL_CRYSTALLIZATION, 1, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerEngraver.class, AbyssalCraftRecipeCategoryUid.ENGRAVING, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerMaterializer.class, AbyssalCraftRecipeCategoryUid.MATERIALIZATION, 0, 1, 1, 36);

		registry.addRecipes(TransmutationRecipeMaker.getTransmutatorRecipes(jeiHelpers));
		registry.addRecipes(TransmutatorFuelRecipeMaker.getFuelRecipes(utils, jeiHelpers));
		registry.addRecipes(CrystallizationRecipeMaker.getCrystallizerRecipes(jeiHelpers));
		registry.addRecipes(CrystallizerFuelRecipeMaker.getFuelRecipes(utils, jeiHelpers));
		registry.addRecipes(RitualRecipeMaker.getRituals());
		registry.addRecipes(EngravingRecipeMaker.getEngraverRecipes());
		registry.addRecipes(RendingRecipeMaker.getRending());
		registry.addRecipes(UpgradeRecipeMaker.getUpgrades());
		registry.addRecipes(MaterializerRecipes.instance().getMaterializationList());

		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ItemHandler.devsword));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ACBlocks.crystallizer_active));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(ACBlocks.transmutator_active));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(BlockHandler.house));
		jeiHelpers.getItemBlacklist().addItemToBlacklist(new ItemStack(BlockHandler.Altar));
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {}
}
