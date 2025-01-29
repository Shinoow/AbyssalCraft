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
package com.shinoow.abyssalcraft.integration.jei.util;

import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.recipe.*;
import com.shinoow.abyssalcraft.api.rending.RendingRegistry;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTransmutator;
import com.shinoow.abyssalcraft.integration.jei.crystallizer.CrystallizationRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.crystallizer.CrystallizerFuelRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.materializer.MaterializationRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.rending.RendingRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.ritual.CreationRitualRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.ritual.RitualRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.spell.SpellRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.transmutator.TransmutationRecipeWrapper;
import com.shinoow.abyssalcraft.integration.jei.transmutator.TransmutatorFuelRecipeWrapper;
import com.shinoow.abyssalcraft.lib.util.IHiddenRitual;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Behold... everything in one place...
 *
 */
public class ACRecipeMaker {

	@Nonnull
	public static List<CrystallizationRecipeWrapper> getCrystallizerRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
		CrystallizerRecipes crystallizerRecipes = CrystallizerRecipes.instance();
		List<Crystallization> crystallizationMap = crystallizerRecipes.getCrystallizationList();

		List<CrystallizationRecipeWrapper> recipes = new ArrayList<>();

		for (Crystallization itemStackItemStackEntry : crystallizationMap) {
			ItemStack input = itemStackItemStackEntry.INPUT;
			ItemStack output = itemStackItemStackEntry.OUTPUT1;
			ItemStack output2 = itemStackItemStackEntry.OUTPUT2;

			float experience = crystallizerRecipes.getExperience(output);

			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			CrystallizationRecipeWrapper recipe = new CrystallizationRecipeWrapper(inputs, output, output2, experience);
			if(isRecipeValid(recipe))
				recipes.add(recipe);
		}

		return recipes;
	}

	private static boolean isRecipeValid(@Nonnull CrystallizationRecipeWrapper recipe) {
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}

	@Nonnull
	public static List<CrystallizerFuelRecipeWrapper> getCrystallizerFuelRecipes(JEIUtils itemRegistry, IJeiHelpers helpers) {
		IGuiHelper guiHelper = helpers.getGuiHelper();
		IStackHelper stackHelper = helpers.getStackHelper();
		List<ItemStack> fuelStacks = itemRegistry.getCrystallizerFuels();
		Set<String> oreDictNames = new HashSet<>();
		List<CrystallizerFuelRecipeWrapper> fuelRecipes = new ArrayList<>(fuelStacks.size());
		for (ItemStack fuelStack : fuelStacks) {
			if (fuelStack == null)
				continue;

			int[] oreIDs = OreDictionary.getOreIDs(fuelStack);
			if (oreIDs.length > 0)
				for (int oreID : oreIDs) {
					String name = OreDictionary.getOreName(oreID);
					if (oreDictNames.contains(name))
						continue;

					oreDictNames.add(name);
					List<ItemStack> oreDictFuels = OreDictionary.getOres(name);
					List<ItemStack> oreDictFuelsSet = stackHelper.getAllSubtypes(oreDictFuels);
					removeNoBurnTimeC(oreDictFuelsSet);
					if (oreDictFuelsSet.isEmpty())
						continue;
					int burnTime = getBurnTimeC(oreDictFuelsSet.get(0));

					if(burnTime > 0)
						fuelRecipes.add(new CrystallizerFuelRecipeWrapper(guiHelper, oreDictFuelsSet, burnTime));
				}
			else {
				List<ItemStack> subtypes = stackHelper.getSubtypes(fuelStack);
				List<ItemStack> fuels = new ArrayList<>();
				for (ItemStack subtype : subtypes)
					if (TileEntityCrystallizer.getCrystallizationTime(subtype) > 0)
						fuels.add(subtype);
				if (fuels.isEmpty())
					continue;
				int burnTime = getBurnTimeC(fuels.get(0));
				if(burnTime > 0)
					fuelRecipes.add(new CrystallizerFuelRecipeWrapper(guiHelper, fuels, burnTime));
			}
		}
		return fuelRecipes;
	}

	private static void removeNoBurnTimeC(Collection<ItemStack> itemStacks) {
		itemStacks.removeIf(stack -> getBurnTimeC(stack) == 0);
	}

	private static int getBurnTimeC(ItemStack itemStack) {
		return TileEntityCrystallizer.getCrystallizationTime(itemStack);
	}

	@Nonnull
	public static List<RendingRecipeWrapper> getRending(){
		return RendingRegistry.instance().getRendings().stream()
				.map(RendingRecipeWrapper::new)
				.collect(Collectors.toList());
	}

	@Nonnull
	public static List<CreationRitualRecipeWrapper> getCreationRituals(){
		List<CreationRitualRecipeWrapper> recipes = new ArrayList();

		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual instanceof NecronomiconCreationRitual && !(ritual instanceof IHiddenRitual))
				recipes.add(new CreationRitualRecipeWrapper((NecronomiconCreationRitual) ritual));

		return recipes;
	}

	@Nonnull
	public static List<RitualRecipeWrapper> getRituals(){
		List<RitualRecipeWrapper> recipes = new ArrayList();

		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(!(ritual instanceof NecronomiconCreationRitual) && !(ritual instanceof IHiddenRitual))
				recipes.add(new RitualRecipeWrapper(ritual));

		return recipes;
	}

	@Nonnull
	public static List<MaterializationRecipeWrapper> getMaterializerRecipes(){
		return MaterializerRecipes.instance().getMaterializationList().stream()
				.map(MaterializationRecipeWrapper::new)
				.collect(Collectors.toList());
	}

	@Nonnull
	public static List<SpellRecipeWrapper> getSpells(){
		return SpellRegistry.instance().getSpells().stream()
				.map(SpellRecipeWrapper::new)
				.collect(Collectors.toList());
	}

	@Nonnull
	public static List<TransmutationRecipeWrapper> getTransmutatorRecipes(IJeiHelpers helpers) {
		IStackHelper stackHelper = helpers.getStackHelper();
		TransmutatorRecipes transmutatorRecipes = TransmutatorRecipes.instance();
		List<Transmutation> transmutationMap = getTransmutations(transmutatorRecipes);

		List<TransmutationRecipeWrapper> recipes = new ArrayList<>();

		for (Transmutation itemStackItemStackEntry : transmutationMap) {
			ItemStack input = itemStackItemStackEntry.INPUT;
			ItemStack output = itemStackItemStackEntry.OUTPUT;

			float experience = transmutatorRecipes.getExperience(output);

			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			TransmutationRecipeWrapper recipe = new TransmutationRecipeWrapper(inputs, output, experience);
			if(isRecipeValid(recipe))
				recipes.add(recipe);
		}

		return recipes;
	}

	private static boolean isRecipeValid(@Nonnull TransmutationRecipeWrapper recipe) {
		return recipe.getInputs().size() != 0 && recipe.getOutputs().size() > 0;
	}

	private static List<Transmutation> getTransmutations(@Nonnull TransmutatorRecipes transmutatorRecipes) {
		return transmutatorRecipes.getTransmutationList();
	}
	@Nonnull
	public static List<TransmutatorFuelRecipeWrapper> getTransmutatorFuelRecipes(JEIUtils itemRegistry, IJeiHelpers helpers) {
		IGuiHelper guiHelper = helpers.getGuiHelper();
		IStackHelper stackHelper = helpers.getStackHelper();
		List<ItemStack> fuelStacks = itemRegistry.getTransmutatorFuels();
		Set<String> oreDictNames = new HashSet<>();
		List<TransmutatorFuelRecipeWrapper> fuelRecipes = new ArrayList<>(fuelStacks.size());
		for (ItemStack fuelStack : fuelStacks) {
			if (fuelStack == null)
				continue;

			int[] oreIDs = OreDictionary.getOreIDs(fuelStack);
			if (oreIDs.length > 0)
				for (int oreID : oreIDs) {
					String name = OreDictionary.getOreName(oreID);
					if (oreDictNames.contains(name))
						continue;

					oreDictNames.add(name);
					List<ItemStack> oreDictFuels = OreDictionary.getOres(name);
					List<ItemStack> oreDictFuelsSet = stackHelper.getAllSubtypes(oreDictFuels);
					removeNoBurnTimeT(oreDictFuelsSet);
					if (oreDictFuelsSet.isEmpty())
						continue;
					int burnTime = getBurnTimeT(oreDictFuelsSet.get(0));

					if(burnTime > 0)
						fuelRecipes.add(new TransmutatorFuelRecipeWrapper(guiHelper, oreDictFuelsSet, burnTime));
				}
			else {
				List<ItemStack> subtypes = stackHelper.getSubtypes(fuelStack);
				List<ItemStack> fuels = new ArrayList<>();
				for (ItemStack subtype : subtypes)
					if (TileEntityTransmutator.getItemBurnTime(subtype) > 0)
						fuels.add(subtype);

				if (fuels.isEmpty())
					continue;
				int burnTime = getBurnTimeT(fuels.get(0));
				if(burnTime > 0)
					fuelRecipes.add(new TransmutatorFuelRecipeWrapper(guiHelper, fuels, burnTime));
			}
		}
		return fuelRecipes;
	}


	private static void removeNoBurnTimeT(Collection<ItemStack> itemStacks) {
		itemStacks.removeIf(stack -> getBurnTimeT(stack) == 0);
	}

	private static int getBurnTimeT(ItemStack itemStack) {
		return TileEntityTransmutator.getItemBurnTime(itemStack);
	}
}
