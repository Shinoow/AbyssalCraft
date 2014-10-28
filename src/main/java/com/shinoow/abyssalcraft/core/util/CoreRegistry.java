/**AbyssalCraft Core
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.core.util;

import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.*;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.core.Core;
import com.shinoow.abyssalcraft.core.util.recipes.*;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Core's equivalent to FML's {@link GameRegistry}
 * @author shinoow
 *
 */
public class CoreRegistry {

	private static List<IFuelHandler> crystallizerFuelHandlers = Lists.newArrayList();
	private static List<IFuelHandler> transmutatorFuelHandlers = Lists.newArrayList();

	/** Hashtable of world providers, will be used for dimension-specific generation */
	private static Hashtable<String, Class<? extends WorldProvider>> dimensions = new Hashtable<String, Class<? extends WorldProvider>>();

	/**
	 * Fuel types, also has support for the vanilla furnace.
	 * @author shinoow
	 *
	 */
	public enum FuelType{
		CRYSTALLIZER, TRANSMUTATOR, FURNACE
	}

	/**
	 * Basic Crystallization
	 * @param input The block to crystallize
	 * @param output1 The first crystal output
	 * @param output2 The second crystal output
	 * @param xp Amount of exp given
	 */
	public static void addCrystallization(Block input, ItemStack output1, ItemStack output2, float xp){
		CrystallizerRecipes.crystallization().crystallize(input, output1, output2, xp);
	}

	/**
	 * Basic Crystallization
	 * @param input The item to crystallize
	 * @param output1 The first crystal output
	 * @param output2 The second crystal output
	 * @param xp Amount of exp given
	 */
	public static void addCrystallization(Item input, ItemStack output1, ItemStack output2, float xp){
		CrystallizerRecipes.crystallization().crystallize(input, output1, output2, xp);
	}

	/**
	 * Basic Crystallization
	 * @param input The itemstack to crystallize
	 * @param output1 The first crystal output
	 * @param output2 The second crystal output
	 * @param xp Amount of exp given
	 */
	public static void addCrystallization(ItemStack input, ItemStack output1, ItemStack output2, float xp){
		CrystallizerRecipes.crystallization().crystallize(input, output1, output2, xp);
	}

	/**
	 * Single-output Crystallization
	 * @param input The block to crystallize
	 * @param output The crystal output
	 * @param xp Amount of exp given
	 */
	public static void addSingleCrystallization(Block input, ItemStack output, float xp){
		addCrystallization(input, output, null, xp);
	}

	/**
	 * Single-output Crystallization
	 * @param input The item to crystallize
	 * @param output The crystal output
	 * @param xp Amount of exp given
	 */
	public static void addSingleCrystallization(Item input, ItemStack output, float xp){
		addCrystallization(input, output, null, xp);
	}

	/**
	 * Single-output Crystallization
	 * @param input The itemstack to crystallize
	 * @param output The crystal output
	 * @param xp Amount of exp given
	 */
	public static void addSingleCrystallization(ItemStack input, ItemStack output, float xp){
		addCrystallization(input, output, null, xp);
	}

	/**
	 * Basic Transmutation
	 * @param input The block to transmutate
	 * @param output The transmutated output
	 * @param xp Amount of exp given
	 */
	public static void addTransmutation(Block input, ItemStack output, float xp){
		TransmutatorRecipes.transmutation().transmutate(input, output, xp);
	}

	/**
	 * Basic Transmutation
	 * @param input The item to transmutate
	 * @param output The transmutated output
	 * @param xp Amount of exp given
	 */
	public static void addTransmutation(Item input, ItemStack output, float xp){
		TransmutatorRecipes.transmutation().transmutate(input, output, xp);
	}

	/**
	 * Basic Transmutation
	 * @param input The itemstack to transmutate
	 * @param output The transmutated output
	 * @param xp Amount of exp given
	 */
	public static void addTransmutation(ItemStack input, ItemStack output, float xp){
		TransmutatorRecipes.transmutation().transmutate(input, output, xp);
	}

	/**
	 * Registers a fuel handler for an AbyssalCraft fuel type
	 * @param handler The file that implements {@link IFuelHandler}
	 * @param type The fuel type
	 */
	public static void registerFuelHandler(IFuelHandler handler, FuelType type){
		switch(type){
		case CRYSTALLIZER:
			crystallizerFuelHandlers.add(handler);
			break;
		case TRANSMUTATOR:
			transmutatorFuelHandlers.add(handler);
			break;
		case FURNACE:
			GameRegistry.registerFuelHandler(handler);
		}
	}

	/**
	 * Gets the fuel value from an ItemStack, depending on the fuel type
	 * @param itemStack The ItemStack getting checked
	 * @param type The fuel type
	 * @return The fuel value for the specified machine
	 */
	public static int getFuelValue(ItemStack itemStack, FuelType type){
		int fuelValue = 0;
		switch(type){
		case CRYSTALLIZER:
			for (IFuelHandler handler : crystallizerFuelHandlers)
				fuelValue = Math.max(fuelValue, handler.getBurnTime(itemStack));
			break;
		case TRANSMUTATOR:
			for (IFuelHandler handler : transmutatorFuelHandlers)
				fuelValue = Math.max(fuelValue, handler.getBurnTime(itemStack));
			break;
		case FURNACE:
			GameRegistry.getFuelValue(itemStack);
		}
		return fuelValue;
	}

	/**
	 * Smelting through the OreDictionary
	 * @param input The ore input
	 * @param output The ore output
	 * @param xp Amount of exp given
	 */
	public static void addOreSmelting(String input, String output, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				FurnaceRecipes.smelting().func_151394_a(inputIter.next(), OreDictionary.getOres(output).iterator().next(), xp);
	}

	/**
	 * OreDictionary specific Crystallization
	 * @param input The ore input
	 * @param output1 The first ore output
	 * @param output2 The second ore output
	 * @param xp Amount of exp given
	 */
	public static void addCrystallization(String input, String output1, String output2, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output1).isEmpty() && !OreDictionary.getOres(output2).isEmpty())
			while(inputIter.hasNext())
				addCrystallization(inputIter.next(), OreDictionary.getOres(output1).iterator().next(), OreDictionary.getOres(output2).iterator().next(), xp);
	}

	/**
	 * OreDictionary specific Crystallization
	 * @param input The ore input
	 * @param output1 The first ore output
	 * @param out1 Quantity of the first output
	 * @param output2 The second ore output
	 * @param out2 Quantity of the second output
	 * @param xp Amount of exp given
	 */
	public static void addCrystallization(String input, String output1, int out1, String output2, int out2, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output1).isEmpty() && !OreDictionary.getOres(output2).isEmpty())
			while(inputIter.hasNext())
				addCrystallization(inputIter.next(), new ItemStack(OreDictionary.getOres(output1).iterator().next().getItem(), out1), new ItemStack(OreDictionary.getOres(output2).iterator().next().getItem(), out2), xp);
	}

	/**
	 * OreDictionary specific single-output Crystallization
	 * @param input The ore input
	 * @param output The ore output
	 * @param xp Amount of exp given
	 */
	public static void addSingleCrystallization(String input, String output, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addSingleCrystallization(inputIter.next(), new ItemStack(OreDictionary.getOres(output).iterator().next().getItem()), xp);
	}

	/**
	 * OreDictionary specific single-output Crystallization
	 * @param input The ore input
	 * @param output The ore output
	 * @param out The output quantity
	 * @param xp Amount of exp given
	 */
	public static void addSingleCrystallization(String input, String output, int out, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addSingleCrystallization(inputIter.next(), new ItemStack(OreDictionary.getOres(output).iterator().next().getItem(), out), xp);
	}

	/**
	 * OreDictionary specific Transmutation
	 * @param input The ore input
	 * @param output The ore output
	 * @param xp Amount of exp given
	 */
	public static void addTransmutation(String input, String output, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addTransmutation(inputIter.next(), new ItemStack(OreDictionary.getOres(output).iterator().next().getItem()), xp);
	}

	/**
	 * OreDictionary specific Transmutation
	 * @param input The ore input
	 * @param output The ore output
	 * @param out The output quantity
	 * @param xp Amount of exp given
	 */
	public static void addTransmutation(String input, String output, int out, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addTransmutation(inputIter.next(), new ItemStack(OreDictionary.getOres(output).iterator().next().getItem(), out), xp);
	}

	/**
	 * OreDictionary specific Transmutation
	 * @param input The ore input
	 * @param output The ore output
	 * @param out The output quantity
	 * @param meta The output metadata
	 * @param xp Amount of exp given
	 */
	public static void addTransmutation(String input, String output, int out, int meta, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addTransmutation(inputIter.next(), new ItemStack(OreDictionary.getOres(output).iterator().next().getItem(), out, meta), xp);
	}

	/**
	 * Adds a bit sequence used to calculate the status on a potion.
	 * This description probably hardly makes any sense, deal with it.
	 * @param id The potion id
	 * @param requirements A bit sequence
	 */
	public static void addPotionRequirements(int id, String requirements){
		Core.potionRequirements.put(Integer.valueOf(id), requirements);
	}

	/**
	 * Adds an amplifier to a potion.
	 * This description probably hardly makes any sense, deal with it.
	 * @param id The potion id
	 * @param amplifier The potion amplifier value (usually 5)
	 */
	public static void addPotionAmplifiers(int id, String amplifier){
		Core.potionAmplifiers.put(Integer.valueOf(id), amplifier);
	}

	/**
	 * Bridge method for registering a dimension, used to store dimensions in Core
	 * @param name The dimension name
	 * @param id The dimension id
	 * @param provider World provider used for the dimension
	 * @param keepLoaded Whether the world should be kept loaded at all times
	 */
	public static void registerDimension(String name, int id, Class<? extends WorldProvider> provider, boolean keepLoaded){
		registerWorldProvider(name, provider);
		DimensionManager.registerProviderType(id, provider, keepLoaded);
		DimensionManager.registerDimension(id, id);
	}

	/**
	 * Used to add a dimension to {@link Core}'s dimension list
	 * @param name Dimension name, used to fetch the dimension later
	 * @param provider The world provider used for the dimension
	 * @return True if the world provider was successfully added.
	 */
	public static boolean registerWorldProvider(String name, Class<? extends WorldProvider> provider){
		if(dimensions.containsKey(name.toLowerCase()))
			return false;
		dimensions.put(name.toLowerCase(), provider);
		return true;
	}

	public static void registerVanillaDimensions(){
		registerWorldProvider("overworld", WorldProviderSurface.class);
		registerWorldProvider("nether", WorldProviderHell.class);
		registerWorldProvider("end", WorldProviderEnd.class);
	}
}