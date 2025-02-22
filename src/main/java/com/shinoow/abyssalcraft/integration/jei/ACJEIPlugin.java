/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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
import com.shinoow.abyssalcraft.client.gui.GuiCrystallizer;
import com.shinoow.abyssalcraft.client.gui.GuiTransmutator;
import com.shinoow.abyssalcraft.common.inventory.ContainerCrystallizer;
import com.shinoow.abyssalcraft.common.inventory.ContainerMaterializer;
import com.shinoow.abyssalcraft.common.inventory.ContainerTransmutator;
import com.shinoow.abyssalcraft.init.BlockHandler;
import com.shinoow.abyssalcraft.init.ItemHandler;
import com.shinoow.abyssalcraft.integration.jei.crystallizer.CrystallizationCategory;
import com.shinoow.abyssalcraft.integration.jei.crystallizer.CrystallizerFuelCategory;
import com.shinoow.abyssalcraft.integration.jei.materializer.MaterializationRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.rending.RendingRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.ritual.CreationRitualRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.ritual.RitualRecipeCategory;
import com.shinoow.abyssalcraft.integration.jei.transmutator.TransmutationCategory;
import com.shinoow.abyssalcraft.integration.jei.transmutator.TransmutatorFuelCategory;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryUid;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeMaker;
import com.shinoow.abyssalcraft.integration.jei.util.JEIUtils;

import mezz.jei.api.*;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import net.minecraft.client.gui.inventory.GuiFurnace;
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

		String[] rituals = {ACRecipeCategoryUid.CREATION_RITUAL, ACRecipeCategoryUid.RITUAL};

		registry.addRecipeCatalyst(new ItemStack(ACBlocks.transmutator_idle), ACRecipeCategoryUid.TRANSMUTATION,
				ACRecipeCategoryUid.FUEL_TRANSMUTATION);
		registry.addRecipeCatalyst(new ItemStack(ACBlocks.crystallizer_idle), ACRecipeCategoryUid.CRYSTALLIZATION,
				ACRecipeCategoryUid.FUEL_CRYSTALLIZATION);
		registry.addRecipeCatalyst(new ItemStack(ACItems.necronomicon), rituals);
		registry.addRecipeCatalyst(new ItemStack(ACItems.abyssal_wasteland_necronomicon), rituals);
		registry.addRecipeCatalyst(new ItemStack(ACItems.dreadlands_necronomicon), rituals);
		registry.addRecipeCatalyst(new ItemStack(ACItems.omothol_necronomicon), rituals);
		registry.addRecipeCatalyst(new ItemStack(ACItems.abyssalnomicon), rituals);
		registry.addRecipeCatalyst(new ItemStack(ACItems.staff_of_rending), ACRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACItems.abyssal_wasteland_staff_of_rending), ACRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACItems.dreadlands_staff_of_rending), ACRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACItems.omothol_staff_of_rending), ACRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACItems.staff_of_the_gatekeeper), ACRecipeCategoryUid.RENDING);
		registry.addRecipeCatalyst(new ItemStack(ACBlocks.materializer), ACRecipeCategoryUid.MATERIALIZATION);
		//		registry.addRecipeCatalyst(new ItemStack(ACItems.basic_scroll), ACRecipeCategoryUid.SPELL);
		//		registry.addRecipeCatalyst(new ItemStack(ACItems.lesser_scroll), ACRecipeCategoryUid.SPELL);
		//		registry.addRecipeCatalyst(new ItemStack(ACItems.moderate_scroll), ACRecipeCategoryUid.SPELL);
		//		registry.addRecipeCatalyst(new ItemStack(ACItems.greater_scroll), ACRecipeCategoryUid.SPELL);
		//		registry.addRecipeCatalyst(new ItemStack(ACItems.antimatter_scroll), ACRecipeCategoryUid.SPELL);
		//		registry.addRecipeCatalyst(new ItemStack(ACItems.oblivion_scroll), ACRecipeCategoryUid.SPELL);

		registry.addRecipeClickArea(GuiTransmutator.class, 78, 32, 28, 23, ACRecipeCategoryUid.TRANSMUTATION, ACRecipeCategoryUid.FUEL_TRANSMUTATION);
		registry.addRecipeClickArea(GuiCrystallizer.class, 78, 32, 28, 23, ACRecipeCategoryUid.CRYSTALLIZATION, ACRecipeCategoryUid.FUEL_CRYSTALLIZATION);
		
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		recipeTransferRegistry.addRecipeTransferHandler(ContainerTransmutator.class, ACRecipeCategoryUid.TRANSMUTATION, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerTransmutator.class, ACRecipeCategoryUid.FUEL_TRANSMUTATION, 1, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCrystallizer.class, ACRecipeCategoryUid.CRYSTALLIZATION, 0, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCrystallizer.class, ACRecipeCategoryUid.FUEL_CRYSTALLIZATION, 1, 1, 1, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerMaterializer.class, ACRecipeCategoryUid.MATERIALIZATION, 0, 1, 1, 36);

		registry.addRecipes(ACRecipeMaker.getTransmutatorRecipes(jeiHelpers), ACRecipeCategoryUid.TRANSMUTATION);
		registry.addRecipes(ACRecipeMaker.getTransmutatorFuelRecipes(utils, jeiHelpers), ACRecipeCategoryUid.FUEL_TRANSMUTATION);
		registry.addRecipes(ACRecipeMaker.getCrystallizerRecipes(jeiHelpers), ACRecipeCategoryUid.CRYSTALLIZATION);
		registry.addRecipes(ACRecipeMaker.getCrystallizerFuelRecipes(utils, jeiHelpers), ACRecipeCategoryUid.FUEL_CRYSTALLIZATION);
		registry.addRecipes(ACRecipeMaker.getCreationRituals(), ACRecipeCategoryUid.CREATION_RITUAL);
		registry.addRecipes(ACRecipeMaker.getRituals(), ACRecipeCategoryUid.RITUAL);
		registry.addRecipes(ACRecipeMaker.getRending(), ACRecipeCategoryUid.RENDING);
		registry.addRecipes(ACRecipeMaker.getMaterializerRecipes(), ACRecipeCategoryUid.MATERIALIZATION);
		//		registry.addRecipes(ACRecipeMaker.getSpells(), ACRecipeCategoryUid.SPELL);
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
				new CreationRitualRecipeCategory(guiHelper),
				new RitualRecipeCategory(guiHelper),
				new RendingRecipeCategory(guiHelper),
				new MaterializationRecipeCategory(guiHelper));
	}
}
