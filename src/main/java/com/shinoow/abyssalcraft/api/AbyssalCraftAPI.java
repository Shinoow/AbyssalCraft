/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
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
package com.shinoow.abyssalcraft.api;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionHelper;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.integration.IACPlugin;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.recipe.*;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 * Main API class for AbyssalCraft, has child classes for most features.
 * Check {@link IMCHelper} for InterModComms registration
 * 
 * @author shinoow
 *
 */
public class AbyssalCraftAPI {

	/**
	 * String used to specify the API version in the "package-info.java" classes
	 */
	public static final String API_VERSION = "1.3.0";

	/**
	 * Enchantment IDs, first one is the Coralium enchantment, second Dread enchantment,
	 * third the Light Pierce enchantment, and the fourth is the Iron Wall enchantment.
	 */
	public static int enchId1, enchId2, enchId3, enchId4;

	/**
	 * Potion IDs, first one is the Coralium Plague, second Dread Plague, and third Antimatter
	 */
	public static int potionId1, potionId2, potionId3;

	private static List<IFuelHandler> crystallizerFuelHandlers = Lists.newArrayList();
	private static List<IFuelHandler> transmutatorFuelHandlers = Lists.newArrayList();

	private static HashMap<Integer, String> potionRequirements = null;
	private static HashMap<Integer, String> potionAmplifiers = null;

	public static DamageSource coralium = new DamageSource("coralium").setDamageBypassesArmor().setMagicDamage();
	public static DamageSource dread = new DamageSource("dread").setDamageBypassesArmor().setMagicDamage();
	public static DamageSource antimatter = new DamageSource("antimatter").setDamageBypassesArmor().setMagicDamage();

	private static List<Class<? extends EntityLivingBase>> shoggothFood = Lists.newArrayList();

	private static List<ItemStack> crystals = Lists.newArrayList();

	private static List<IACPlugin> integrations = Lists.newArrayList();

	/**
	 *  {@link EnumCreatureAttribute} used for the Shadow mobs
	 */
	public static EnumCreatureAttribute SHADOW = EnumHelper.addCreatureAttribute("SHADOW");

	public static ArmorMaterial abyssalniteArmor = EnumHelper.addArmorMaterial("Abyssalnite", 35, new int[]{3, 8, 6, 3}, 13);
	public static ArmorMaterial coraliumInfusedAbyssalniteArmor = EnumHelper.addArmorMaterial("AbyssalniteC", 36, new int[]{3, 8, 6, 3}, 30);
	public static ArmorMaterial dreadedAbyssalniteArmor = EnumHelper.addArmorMaterial("Dread", 36, new int[]{3, 8, 6, 3}, 15);
	public static ArmorMaterial refinedCoraliumArmor = EnumHelper.addArmorMaterial("Coralium", 37, new int[]{3, 8, 6, 3}, 14);
	public static ArmorMaterial platedCoraliumArmor = EnumHelper.addArmorMaterial("CoraliumP", 55, new int[]{4, 9, 7, 4}, 14);
	public static ArmorMaterial depthsArmor = EnumHelper.addArmorMaterial("Depths", 33, new int[]{3, 8, 6, 3}, 25);
	public static ArmorMaterial dreadiumArmor = EnumHelper.addArmorMaterial("Dreadium", 40, new int[]{3, 8, 6, 3}, 15);
	public static ArmorMaterial dreadiumSamuraiArmor = EnumHelper.addArmorMaterial("DreadiumS", 45, new int[]{3, 8, 6, 3}, 20);
	public static ArmorMaterial ethaxiumArmor = EnumHelper.addArmorMaterial("Ethaxium", 50, new int[]{3, 8, 6, 3}, 25);

	public static ToolMaterial darkstoneTool = EnumHelper.addToolMaterial("DARKSTONE", 1, 180, 5.0F, 1, 5);
	public static ToolMaterial abyssalniteTool = EnumHelper.addToolMaterial("ABYSSALNITE", 4, 1261, 13.0F, 4, 13);
	public static ToolMaterial refinedCoraliumTool = EnumHelper.addToolMaterial("CORALIUM", 5, 2000, 14.0F, 5, 14);
	public static ToolMaterial dreadiumTool = EnumHelper.addToolMaterial("DREADIUM", 6, 3000, 15.0F, 6, 15);
	public static ToolMaterial coraliumInfusedAbyssalniteTool = EnumHelper.addToolMaterial("ABYSSALNITE_C", 8, 8000, 20.0F, 8, 30);
	public static ToolMaterial ethaxiumTool = EnumHelper.addToolMaterial("ETHAXIUM", 8, 4000, 16.0F, 8, 20);

	/**
	 * Initializes the reflection required for the Potion code, ignore it
	 */
	@SuppressWarnings("unchecked")
	public static void initPotionReflection(){
		Potion[] potionTypes = null;
		for (Field f : Potion.class.getDeclaredFields()) {
			f.setAccessible(true);
			try {
				if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
					Field modfield = Field.class.getDeclaredField("modifiers");
					modfield.setAccessible(true);
					modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

					potionTypes = (Potion[])f.get(null);
					final Potion[] newPotionTypes = new Potion[256];
					System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
					f.set(null, newPotionTypes);
				}
			}
			catch (Exception e) {
				System.err.println("Whoops, something screwed up here, please report this to shinoow:");
				System.err.println(e);
			}
		}
		for(Field f : PotionHelper.class.getDeclaredFields())
			try {
				if(f.getName().equals("potionRequirements") || f.getName().equals("field_77927_l")){
					f.setAccessible(true);
					try {
						potionRequirements = (HashMap<Integer, String>)f.get(null);
					} catch (IllegalArgumentException
							| IllegalAccessException e) {
						System.err.println("Whoops, something screwed up here, please report this to shinoow:");
						e.printStackTrace();
					}
				}
				if(f.getName().equals("potionAmplifiers") || f.getName().equals("field_77928_m")){
					f.setAccessible(true);
					try {
						potionAmplifiers = (HashMap<Integer, String>)f.get(null);
					} catch (IllegalArgumentException
							| IllegalAccessException e) {
						System.err.println("Whoops, something screwed up here, please report this to shinoow:");
						e.printStackTrace();
					}
				}
			} catch (SecurityException e) {
				System.err.println("Whoops, something screwed up here, please report this to shinoow:");
				e.printStackTrace();
			}
	}

	/**
	 * Sets the repair items for each armor/tool material
	 */
	public static void setRepairItems(){

		abyssalniteArmor.customCraftingMaterial = ACItems.abyssalnite_ingot;
		coraliumInfusedAbyssalniteArmor.customCraftingMaterial = ACItems.transmutation_gem;
		dreadedAbyssalniteArmor.customCraftingMaterial = ACItems.dreaded_shard_of_abyssalnite;
		refinedCoraliumArmor.customCraftingMaterial = ACItems.refined_coralium_ingot;
		platedCoraliumArmor.customCraftingMaterial = ACItems.coralium_plate;
		depthsArmor.customCraftingMaterial = ACItems.coralium_gem_cluster_9;
		dreadiumArmor.customCraftingMaterial = ACItems.dreadium_ingot;
		dreadiumSamuraiArmor.customCraftingMaterial = ACItems.dreadium_plate;
		ethaxiumArmor.customCraftingMaterial = ACItems.ethaxium_ingot;

		darkstoneTool.setRepairItem(new ItemStack(ACBlocks.darkstone_cobblestone));
		abyssalniteTool.setRepairItem(new ItemStack(ACItems.abyssalnite_ingot));
		refinedCoraliumTool.setRepairItem(new ItemStack(ACItems.refined_coralium_ingot));
		dreadiumTool.setRepairItem(new ItemStack(ACItems.dreadium_ingot));
		coraliumInfusedAbyssalniteTool.setRepairItem(new ItemStack(ACItems.transmutation_gem));
		ethaxiumTool.setRepairItem(new ItemStack(ACItems.ethaxium_ingot));
	}

	/**
	 * Adds a bit sequence used to calculate the status on a potion.
	 * This description probably hardly makes any sense, deal with it.
	 * @param id The potion id
	 * @param requirements A bit sequence
	 * 
	 * @since 1.1
	 */
	public static void addPotionRequirements(int id, String requirements){
		potionRequirements.put(Integer.valueOf(id), requirements);
	}

	/**
	 * Adds an amplifier to a potion.
	 * This description probably hardly makes any sense, deal with it.
	 * @param id The potion id
	 * @param amplifier The potion amplifier value (usually 5)
	 * 
	 * @since 1.1
	 */
	public static void addPotionAmplifiers(int id, String amplifier){
		potionAmplifiers.put(Integer.valueOf(id), amplifier);
	}

	/**
	 * Basic Crystallization
	 * @param input The block to crystallize
	 * @param output1 The first crystal output
	 * @param output2 The second crystal output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
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
	 * 
	 * @since 1.0
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
	 * 
	 * @since 1.0
	 */
	public static void addCrystallization(ItemStack input, ItemStack output1, ItemStack output2, float xp){
		CrystallizerRecipes.crystallization().crystallize(input, output1, output2, xp);
	}

	/**
	 * Single-output Crystallization
	 * @param input The block to crystallize
	 * @param output The crystal output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addSingleCrystallization(Block input, ItemStack output, float xp){
		addCrystallization(input, output, null, xp);
	}

	/**
	 * Single-output Crystallization
	 * @param input The item to crystallize
	 * @param output The crystal output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addSingleCrystallization(Item input, ItemStack output, float xp){
		addCrystallization(input, output, null, xp);
	}

	/**
	 * Single-output Crystallization
	 * @param input The itemstack to crystallize
	 * @param output The crystal output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addSingleCrystallization(ItemStack input, ItemStack output, float xp){
		addCrystallization(input, output, null, xp);
	}

	/**
	 * Basic Transmutation
	 * @param input The block to transmutate
	 * @param output The transmutated output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addTransmutation(Block input, ItemStack output, float xp){
		TransmutatorRecipes.transmutation().transmutate(input, output, xp);
	}

	/**
	 * Basic Transmutation
	 * @param input The item to transmutate
	 * @param output The transmutated output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addTransmutation(Item input, ItemStack output, float xp){
		TransmutatorRecipes.transmutation().transmutate(input, output, xp);
	}

	/**
	 * Basic Transmutation
	 * @param input The itemstack to transmutate
	 * @param output The transmutated output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addTransmutation(ItemStack input, ItemStack output, float xp){
		TransmutatorRecipes.transmutation().transmutate(input, output, xp);
	}

	/**
	 * Smelting through the OreDictionary
	 * @param input The ore input
	 * @param output The ore output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
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
	 * 
	 * @since 1.0
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
	 * 
	 * @since 1.0
	 */
	public static void addCrystallization(String input, String output1, int out1, String output2, int out2, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output1).isEmpty() && !OreDictionary.getOres(output2).isEmpty())
			while(inputIter.hasNext())
				addCrystallization(inputIter.next(), new ItemStack(OreDictionary.getOres(output1).iterator().next().getItem(), out1), new ItemStack(OreDictionary.getOres(output2).iterator().next().getItem(), out2), xp);
	}

	/**
	 * OreDictionary specific Crystallization
	 * @param input The ore input
	 * @param output1 The first ore output
	 * @param out1 Quantity of the first output
	 * @param meta1 Metadata for the first output
	 * @param output2 The second ore output
	 * @param out2 Quantity of the second output
	 * @param meta2 Metadata for the second output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addCrystallization(String input, String output1, int out1, int meta1, String output2, int out2, int meta2, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output1).isEmpty() && !OreDictionary.getOres(output2).isEmpty())
			while(inputIter.hasNext())
				addCrystallization(inputIter.next(), new ItemStack(OreDictionary.getOres(output1).iterator().next().getItem(), out1, meta1), new ItemStack(OreDictionary.getOres(output2).iterator().next().getItem(), out2, meta2), xp);
	}

	/**
	 * OreDictionary specific single-output Crystallization
	 * @param input The ore input
	 * @param output The ore output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addSingleCrystallization(String input, String output, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addSingleCrystallization(inputIter.next(), OreDictionary.getOres(output).iterator().next(), xp);
	}

	/**
	 * OreDictionary specific single-output Crystallization
	 * @param input The ore input
	 * @param output The ore output
	 * @param out The output quantity
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addSingleCrystallization(String input, String output, int out, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addSingleCrystallization(inputIter.next(), new ItemStack(OreDictionary.getOres(output).iterator().next().getItem(), out), xp);
	}

	/**
	 * OreDictionary specific single-output Crystallization
	 * @param input The ore input
	 * @param output The ore output
	 * @param out The output quantity
	 * @param meta The output metadata
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addSingleCrystallization(String input, String output, int out, int meta, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addSingleCrystallization(inputIter.next(), new ItemStack(OreDictionary.getOres(output).iterator().next().getItem(), out, meta), xp);
	}

	/**
	 * OreDictionary specific Transmutation
	 * @param input The ore input
	 * @param output The ore output
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
	 */
	public static void addTransmutation(String input, String output, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addTransmutation(inputIter.next(), OreDictionary.getOres(output).iterator().next(), xp);
	}

	/**
	 * OreDictionary specific Transmutation
	 * @param input The ore input
	 * @param output The ore output
	 * @param out The output quantity
	 * @param xp Amount of exp given
	 * 
	 * @since 1.0
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
	 * 
	 * @since 1.0
	 */
	public static void addTransmutation(String input, String output, int out, int meta, float xp){
		Iterator<ItemStack> inputIter = OreDictionary.getOres(input).iterator();
		if(!OreDictionary.getOres(output).isEmpty())
			while(inputIter.hasNext())
				addTransmutation(inputIter.next(), new ItemStack(OreDictionary.getOres(output).iterator().next().getItem(), out, meta), xp);
	}

	/**
	 * Basic Engraving
	 * @param input The ItemStack to engrave
	 * @param output The ItemStack output
	 * @param engraving The engraving template (must be an {@link ItemEngraving})
	 * @param xp Amount of exp given
	 * 
	 * @since 1.1
	 */
	public static void addEngraving(ItemStack input, ItemStack output, Item engraving, float xp){
		try{
			EngraverRecipes.engraving().engrave(input, output, (ItemEngraving)engraving, xp);
		} catch(ClassCastException e){
			System.err.println("You're doing it wrong!");
			e.printStackTrace();
		}
	}

	/**
	 * Basic Engraving
	 * @param input The Item to engrave
	 * @param output The ItemStack output
	 * @param engraving The engraving template (must be an {@link ItemEngraving})
	 * @param xp Amount of exp given
	 * 
	 * @since 1.1
	 */
	public static void addEngraving(Item input, ItemStack output, Item engraving, float xp){
		try{
			EngraverRecipes.engraving().engrave(input, output, (ItemEngraving)engraving, xp);
		} catch(ClassCastException e){
			System.err.println("You're doing it wrong!");
			e.printStackTrace();
		}
	}

	/**
	 * Basic Materialization.
	 * Note: all inputs has to be {@link ICrystal}s
	 * @param input1 The first input
	 * @param input2 The second input
	 * @param input3 The third input
	 * @param input4 The fourth input
	 * @param input5 The fifth input
	 * @param output The output
	 * @param level Required Necronomicon level
	 * 
	 * @since 1.3
	 */
	public static void addMaterialization(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack input5, ItemStack output, int level){
		//do stuff
	}

	/**
	 * Basic Materialization.
	 * Note: all inputs has to be {@link ICrystal}s
	 * @param input1 The first input
	 * @param input2 The second input
	 * @param input3 The third input
	 * @param input4 The fourth input
	 * @param output The output
	 * @param level Required Necronomicon level
	 * 
	 * @since 1.3
	 */
	public static void addMaterialization(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack input4, ItemStack output, int level){
		addMaterialization(input1, input2, input3, input4, null, output, level);
	}

	/**
	 * Basic Materialization.
	 * Note: all inputs has to be {@link ICrystal}s
	 * @param input1 The first input
	 * @param input2 The second input
	 * @param input3 The third input
	 * @param output The output
	 * @param level Required Necronomicon level
	 * 
	 * @since 1.3
	 */
	public static void addMaterialization(ItemStack input1, ItemStack input2, ItemStack input3, ItemStack output, int level){
		addMaterialization(input1, input2, input3, null, output, level);
	}

	/**
	 * Basic Materialization.
	 * Note: all inputs has to be {@link ICrystal}s
	 * @param input1 The first input
	 * @param input2 The second input
	 * @param output The output
	 * @param level Required Necronomicon level
	 * 
	 * @since 1.3
	 */
	public static void addMaterialization(ItemStack input1, ItemStack input2, ItemStack output, int level){
		addMaterialization(input1, input2, null, output, level);
	}

	/**
	 * Basic Materialization.
	 * Note: all inputs has to be {@link ICrystal}s
	 * @param input The input
	 * @param output The output
	 * @param level Required Necronomicon level
	 * 
	 * @since 1.3
	 */
	public static void addMaterialization(ItemStack input, ItemStack output, int level){
		addMaterialization(input, null, output, level);
	}

	/**
	 * Basic OreDictionary Materialization.
	 * Note: all inputs has to be {@link ICrystal}s
	 * @param input1 The first input
	 * @param input2 The second input
	 * @param input3 The third input
	 * @param input4 The fourth input
	 * @param input5 The fifth input
	 * @param output The output
	 * @param level Required Necronomicon level
	 * 
	 * @since 1.3
	 */
	public static void addMaterialization(String input1, String input2, String input3, String input4, String input5, String output, int level){
		//do stuff
	}

	/**
	 * Fuel types, also has support for the vanilla furnace.
	 * @author shinoow
	 *
	 */
	public enum FuelType{
		CRYSTALLIZER, TRANSMUTATOR, FURNACE
	}

	/**
	 * Registers a fuel handler for an AbyssalCraft fuel type
	 * @param handler The file that implements {@link IFuelHandler}
	 * @param type The fuel type
	 * 
	 * @since 1.0
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
	 * 
	 * @since 1.0
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
	 * Adds the entity to a list of entities that the Lesser Shoggoth eats
	 * (Note: It's useless to add your entity here if it extends {@link EntityAnimal}, {@link EntityAmbientCreature}, {@link EntityWaterMob} or {@link EntityTameable}).
	 * If your Entity's superclass is a subclass of EntityTameable, you will need to add the superclass.
	 * @param clazz The potential "food" for the Lesser Shoggoth
	 * 
	 * @since 1.2
	 */
	public static void addShoggothFood(Class<? extends EntityLivingBase> clazz){
		shoggothFood.add(clazz);
	}

	/**
	 * Used by the Lesser Shoggoth to fetch a list of things to eat
	 * @return An ArrayList containing Entity classes
	 * 
	 * @since 1.2
	 */
	public static List<Class<? extends EntityLivingBase>> getShoggothFood(){
		return shoggothFood;
	}

	/**
	 * Adds the ItemStack to the crystal list. Anything added to this list will function like a {@link ICrystal}
	 * @param crystal The ItemStack to be added
	 * 
	 * @since 1.3
	 */
	public static void addCrystal(ItemStack crystal){
		crystals.add(crystal);
	}

	/**
	 * Used by various things to fetch a list of ItemStacks that should function like {@link ICrystal}s
	 * @return An ArrayList of ItemStacks
	 * 
	 * @since 1.3
	 */
	public static List<ItemStack> getCrystals(){
		return crystals;
	}

	/**
	 * Method used to register AbyssalCraft integrations by other mods.
	 * This can be useful if you want to have a weak dependency (you could do all the
	 * integration stuff in a class that's only called when AC is loaded)
	 * NOTE: Should be registered in either Pre-init or Init
	 * @param plugin A class that implements the {@link IACPlugin} interface
	 * 
	 * @since 1.3
	 */
	public static void registerACIntegration(IACPlugin plugin){
		integrations.add(plugin);
	}

	/**
	 * Used by the IntegrationHandler to fetch a list of integrations made
	 * by other mods
	 * @return An ArrayList of IACPlugins
	 * 
	 * @since 1.3
	 */
	public static List<IACPlugin> getIntegrations(){
		return integrations;
	}

	/**
	 * Contains the names of all mobs added in AbyssalCraft.
	 * 
	 * @author shinoow
	 *
	 */
	public static class ACEntities {

		private static String[] mobNames = {"depthsghoul", "evilpig", "abyssalzombie", "Jzahar", "abygolem", "dreadgolem",
			"dreadguard", "dragonminion", "dragonboss", "shadowcreature", "shadowmonster", "dreadling", "dreadspawn",
			"demonpig", "gskeleton", "chagarothspawn", "chagarothfist", "chagaroth", "shadowbeast", "shadowboss",
			"antiabyssalzombie", "antibat", "antichicken", "anticow", "anticreeper", "antighoul", "antipig", "antiplayer",
			"antiskeleton", "antispider", "antizombie", "lessershoggoth", "shadowtitan", "omotholwarden", "jzaharminion",
			"omotholghoul", "remnant"};

		public static String depths_ghoul = mobNames[0];
		public static String evil_pig = mobNames[1];
		public static String abyssal_zombie = mobNames[2];
		public static String jzahar = mobNames[3];
		public static String abyssalnite_golem = mobNames[4];
		public static String dreaded_abyssalnite_golem = mobNames[5];
		public static String dreadguard = mobNames[6];
		public static String spectral_dragon = mobNames[7];
		public static String asorah = mobNames[8];
		public static String shadow_creature = mobNames[9];
		public static String shadow_monster = mobNames[10];
		public static String dreadling = mobNames[11];
		public static String dread_spawn = mobNames[12];
		public static String demon_pig = mobNames[13];
		public static String skeleton_goliath = mobNames[14];
		public static String spawn_of_chagaroth = mobNames[15];
		public static String fist_of_chagaroth = mobNames[16];
		public static String chagaroth = mobNames[17];
		public static String shadow_beast = mobNames[18];
		public static String sacthoth = mobNames[19];
		public static String abyssal_anti_zombie = mobNames[20];
		public static String anti_bat = mobNames[21];
		public static String anti_chicken = mobNames[22];
		public static String anti_cow = mobNames[23];
		public static String anti_creeper = mobNames[24];
		public static String anti_ghoul = mobNames[25];
		public static String anti_pig = mobNames[26];
		public static String anti_player = mobNames[27];
		public static String anti_skeleton = mobNames[28];
		public static String anti_spider = mobNames[29];
		public static String anti_zombie = mobNames[30];
		public static String lesser_shoggoth = mobNames[31];
		public static String shadow_titan = mobNames[32];
		public static String omothol_warden = mobNames[33];
		public static String minion_of_the_gatekeeper = mobNames[34];
		public static String omothol_ghoul = mobNames[35];
		public static String remnant = mobNames[36];
	}

	/**
	 * Contains all potion effects added in AbyssalCraft.
	 * You can reference them from here, or use the ID directly
	 * 
	 * @author shinoow
	 *
	 */
	public static class ACPotions {

		public static Potion Coralium_plague = Potion.potionTypes[potionId1];
		public static Potion Dread_plague = Potion.potionTypes[potionId2];
		public static Potion Antimatter = Potion.potionTypes[potionId3];
	}
}