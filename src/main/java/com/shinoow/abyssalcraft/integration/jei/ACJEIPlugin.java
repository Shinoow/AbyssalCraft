/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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
import com.shinoow.abyssalcraft.api.recipe.Materialization;
import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.common.inventory.*;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.init.ItemHandler;
import com.shinoow.abyssalcraft.integration.jei.crystallizer.*;
import com.shinoow.abyssalcraft.integration.jei.engraver.EngraverRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.engraver.EngravingRecipeMaker;
import com.shinoow.abyssalcraft.integration.jei.materializer.MaterializationRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.materializer.MaterializationRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.rending.RendingRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.rending.RendingRecipeMaker;
import com.shinoow.abyssalcraft.integration.jei.ritual.RitualRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.ritual.RitualRecipeMaker;
import com.shinoow.abyssalcraft.integration.jei.ritual.RitualRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.transmutator.*;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;

@JEIPlugin
public class ACJEIPlugin implements IModPlugin {

	@Override
	public void register(IModRegistry registry) {
		if(!Loader.isModLoaded("abyssalcraft")) return;

		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		JEIUtils utils = new JEIUtils(registry.getIngredientRegistry());

		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ItemHandler.devsword));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ItemHandler.shoggoth_projectile));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ACBlocks.crystallizer_active));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ACBlocks.transmutator_active));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(BlockHandler.house));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(BlockHandler.Altar));
		jeiHelpers.getIngredientBlacklist().addIngredientToBlacklist(new ItemStack(ACBlocks.multi_block));

		registry.addRecipeCatalyst(new ItemStack(ACBlocks.transmutator_idle), AbyssalCraftRecipeCategoryUid.TRANSMUTATION,
				AbyssalCraftRecipeCategoryUid.FUEL_TRANSMUTATION);
		registry.addRecipeCatalyst(new ItemStack(ACBlocks.crystallizer_idle), AbyssalCraftRecipeCategoryUid.CRYSTALLIZATION,
				AbyssalCraftRecipeCategoryUid.FUEL_CRYSTALLIZATION);
		registry.addRecipeCatalyst(new ItemStack(ACBlocks.engraver), AbyssalCraftRecipeCategoryUid.ENGRAVING);
		registry.addRecipeCatalyst(new ItemStack(ACItems.necronomicon), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipeCatalyst(new ItemStack(ACItems.abyssal_wasteland_necronomicon), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipeCatalyst(new ItemStack(ACItems.dreadlands_necronomicon), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipeCatalyst(new ItemStack(ACItems.omothol_necronomicon), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipeCatalyst(new ItemStack(ACItems.abyssalnomicon), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipeCatalyst(new ItemStack(ACItems.staff_of_rending, 1, 0), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACItems.staff_of_rending, 1, 1), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACItems.staff_of_rending, 1, 2), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACItems.staff_of_rending, 1, 3), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACItems.staff_of_the_gatekeeper), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACBlocks.materializer), AbyssalCraftRecipeCategoryUid.MATERIALIZATION);

		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		recipeTransferRegistry.addRecipeTransferHandler(ContainerTransmutator.class, AbyssalCraftRecipeCategoryUid.TRANSMUTATION, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTransmutator.class, AbyssalCraftRecipeCategoryUid.FUEL_TRANSMUTATION, 1, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCrystallizer.class, AbyssalCraftRecipeCategoryUid.CRYSTALLIZATION, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCrystallizer.class, AbyssalCraftRecipeCategoryUid.FUEL_CRYSTALLIZATION, 1, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerEngraver.class, AbyssalCraftRecipeCategoryUid.ENGRAVING, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerMaterializer.class, AbyssalCraftRecipeCategoryUid.MATERIALIZATION, 0, 1, 1, 36);

		registry.addRecipes(TransmutationRecipeMaker.getTransmutatorRecipes(jeiHelpers), AbyssalCraftRecipeCategoryUid.TRANSMUTATION);
		registry.addRecipes(TransmutatorFuelRecipeMaker.getFuelRecipes(utils, jeiHelpers), AbyssalCraftRecipeCategoryUid.FUEL_TRANSMUTATION);
		registry.addRecipes(CrystallizationRecipeMaker.getCrystallizerRecipes(jeiHelpers), AbyssalCraftRecipeCategoryUid.CRYSTALLIZATION);
		registry.addRecipes(CrystallizerFuelRecipeMaker.getFuelRecipes(utils, jeiHelpers), AbyssalCraftRecipeCategoryUid.FUEL_CRYSTALLIZATION);
		registry.addRecipes(RitualRecipeMaker.getRituals(), AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.addRecipes(EngravingRecipeMaker.getEngraverRecipes(), AbyssalCraftRecipeCategoryUid.ENGRAVING);
		registry.addRecipes(RendingRecipeMaker.getRending(), AbyssalCraftRecipeCategoryUid.RENDING);
		registry.addRecipes(MaterializerRecipes.instance().getMaterializationList(), AbyssalCraftRecipeCategoryUid.MATERIALIZATION);

		registry.handleRecipes(NecronomiconCreationRitual.class, RitualRecipeWrapper::new, AbyssalCraftRecipeCategoryUid.RITUAL);
		registry.handleRecipes(Materialization.class, MaterializationRecipeWrapper::new, AbyssalCraftRecipeCategoryUid.MATERIALIZATION);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {}

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {
		IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
		registry.addRecipeCategories(new TransmutatorFuelCategory(guiHelper),
				new TransmutationCategory(guiHelper),
				new CrystallizerFuelCategory(guiHelper),
				new CrystallizationCategory(guiHelper),
				new RitualRecipeCategory(guiHelper),
				new EngraverRecipeCategory(guiHelper),
				new RendingRecipeCategory(guiHelper),
				new MaterializationRecipeCategory(guiHelper));
	}
}
