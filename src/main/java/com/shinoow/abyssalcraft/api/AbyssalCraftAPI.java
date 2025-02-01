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
package com.shinoow.abyssalcraft.api;

import java.util.*;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.event.FuelBurnTimeEvent;
import com.shinoow.abyssalcraft.api.internal.*;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.recipe.*;

import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.Potion;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Main API class for AbyssalCraft, has child classes for most features.<br>
 * Check {@link IMCHelper} for InterModComms registration
 *
 * @author shinoow
 *
 */
public class AbyssalCraftAPI {

	/**
	 * String used to specify the API version in the "package-info.java" classes
	 */
	public static final String API_VERSION = "2.0.0";

	public static Enchantment light_pierce, iron_wall, sapping, multi_rend, blinding_light;

	public static Potion coralium_plague, dread_plague, antimatter_potion, coralium_antidote, dread_antidote;

	public static final DamageSource coralium = new DamageSource("coralium").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource dread = new DamageSource("dread").setDamageBypassesArmor().setMagicDamage();
	public static final DamageSource antimatter = new DamageSource("antimatter").setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage();
	public static final DamageSource shadow = new DamageSource("shadow").setDamageBypassesArmor().setDamageIsAbsolute();
	public static final DamageSource acid = new DamageSource("shoggoth_acid").setDamageBypassesArmor().setDamageIsAbsolute();

	private static final List<ItemStack> crystals = new ArrayList<>();

	private static final Map<NecroData, Integer> necroData = new HashMap<>();

	/**
	 *  {@link EnumCreatureAttribute} used for the Shadow mobs
	 */
	public static final EnumCreatureAttribute SHADOW = EnumHelper.addCreatureAttribute("SHADOW");

	public static final ArmorMaterial abyssalniteArmor = EnumHelper.addArmorMaterial("Abyssalnite", "abyssalcraft:abyssalnite", 35, new int[]{3, 6, 8, 3}, 13, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
	public static final ArmorMaterial dreadedAbyssalniteArmor = EnumHelper.addArmorMaterial("Dread", "abyssalcraft:dread", 36, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
	public static final ArmorMaterial refinedCoraliumArmor = EnumHelper.addArmorMaterial("Coralium", "abyssalcraft:coralium", 37, new int[]{3, 6, 8, 3}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
	public static final ArmorMaterial platedCoraliumArmor = EnumHelper.addArmorMaterial("CoraliumP", "abyssalcraft:coraliump", 55, new int[]{4, 7, 9, 4}, 14, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 3.0F);
	public static final ArmorMaterial depthsArmor = EnumHelper.addArmorMaterial("Depths", "abyssalcraft:depths", 33, new int[]{3, 6, 8, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.5F);
	public static final ArmorMaterial dreadiumArmor = EnumHelper.addArmorMaterial("Dreadium", "abyssalcraft:dreadium", 40, new int[]{3, 6, 8, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.0F);
	public static final ArmorMaterial dreadiumSamuraiArmor = EnumHelper.addArmorMaterial("DreadiumS", "abyssalcraft:dreadiums", 45, new int[]{3, 6, 8, 3}, 20, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 1.5F);
	public static final ArmorMaterial ethaxiumArmor = EnumHelper.addArmorMaterial("Ethaxium", "abyssalcraft:ethaxium", 50, new int[]{3, 6, 8, 3}, 25, SoundEvents.ITEM_ARMOR_EQUIP_IRON, 2.0F);

	public static final ToolMaterial abyssalniteTool = EnumHelper.addToolMaterial("ABYSSALNITE", 4, 1261, 10.0F, 4, 12);
	public static final ToolMaterial refinedCoraliumTool = EnumHelper.addToolMaterial("CORALIUM", 5, 1800, 12.0F, 5, 13);
	public static final ToolMaterial dreadiumTool = EnumHelper.addToolMaterial("DREADIUM", 6, 2300, 14.0F, 6, 14);
	public static final ToolMaterial ethaxiumTool = EnumHelper.addToolMaterial("ETHAXIUM", 8, 2800, 16.0F, 8, 20);

	public static EnumEnchantmentType STAFF_OF_RENDING;

	private static IInternalNecroDataHandler internalNDHandler = new DummyNecroDataHandler();
	private static IInternalMethodHandler internalMethodHandler = new DummyMethodHandler();

	public static Fluid liquid_coralium_fluid, liquid_antimatter_fluid;

	public static final Logger logger = LogManager.getLogger("AbyssalCraftAPI");

	@SideOnly(Side.CLIENT)
	private static FontRenderer aklo_font;

	/**
	 * Used by AbyssalCraft to set the Internal NecroData Handler.<br>
	 * If any other mod tries to use this method, nothing will happen.
	 * @param handler Handler instance
	 */
	public static void setInternalNDHandler(IInternalNecroDataHandler handler){
		if(internalNDHandler.getClass().getName().equals(DummyNecroDataHandler.class.getName())
				&& Loader.instance().getLoaderState() == LoaderState.PREINITIALIZATION
				&& Loader.instance().activeModContainer().getModId().equals("abyssalcraft"))
			internalNDHandler = handler;
	}

	/**
	 * Used by AbyssalCraft to set the Internal Method Handler.<br>
	 * If any other mod tries to use this method, nothing will happen.
	 * @param handler Handler instance
	 */
	public static void setInternalMethodHandler(IInternalMethodHandler handler){
		if(internalMethodHandler.getClass().getName().equals(DummyMethodHandler.class.getName())
				&& Loader.instance().getLoaderState() == LoaderState.PREINITIALIZATION
				&& Loader.instance().activeModContainer().getModId().equals("abyssalcraft"))
			internalMethodHandler = handler;
	}

	/**
	 * Internal NecroData handler.<br>
	 * Use this to alter the internal NecroData instances.
	 */
	public static IInternalNecroDataHandler getInternalNDHandler(){
		return internalNDHandler;
	}

	/**
	 * Internal Method handler.<br>
	 * Use this to handle internal method calls.
	 */
	public static IInternalMethodHandler getInternalMethodHandler(){
		return internalMethodHandler;
	}

	/**
	 * Sets the repair items for each armor/tool material
	 */
	public static void setRepairItems(){

		abyssalniteArmor.setRepairItem(new ItemStack(ACItems.abyssalnite_ingot));
		dreadedAbyssalniteArmor.setRepairItem(new ItemStack(ACItems.dreaded_shard_of_abyssalnite));
		refinedCoraliumArmor.setRepairItem(new ItemStack(ACItems.refined_coralium_ingot));
		platedCoraliumArmor.setRepairItem(new ItemStack(ACItems.coralium_plate));
		depthsArmor.setRepairItem(new ItemStack(ACItems.coralium_gem_cluster_9));
		dreadiumArmor.setRepairItem(new ItemStack(ACItems.dreadium_ingot));
		dreadiumSamuraiArmor.setRepairItem(new ItemStack(ACItems.dreadium_plate));
		ethaxiumArmor.setRepairItem(new ItemStack(ACItems.ethaxium_ingot));

		abyssalniteTool.setRepairItem(new ItemStack(ACItems.abyssalnite_ingot));
		refinedCoraliumTool.setRepairItem(new ItemStack(ACItems.refined_coralium_ingot));
		dreadiumTool.setRepairItem(new ItemStack(ACItems.dreadium_ingot));
		ethaxiumTool.setRepairItem(new ItemStack(ACItems.ethaxium_ingot));
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
		CrystallizerRecipes.instance().crystallize(input, output1, output2, xp);
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
		CrystallizerRecipes.instance().crystallize(input, output1, output2, xp);
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
		CrystallizerRecipes.instance().crystallize(input, output1, output2, xp);
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
		addCrystallization(input, output, ItemStack.EMPTY, xp);
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
		addCrystallization(input, output, ItemStack.EMPTY, xp);
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
		addCrystallization(input, output, ItemStack.EMPTY, xp);
	}

	/**
	 * Removes Crystallization(s) based on the output
	 * @param input ItemStack to check for
	 *
	 * @since 2.0
	 */
	public static void removeCrystallization(ItemStack output){
		CrystallizerRecipes.instance().removeCrystallization(output);
	}

	/**
	 * Removes a Crystallization based on the input
	 * @param input ItemStack to check for
	 *
	 * @since 2.0
	 */
	public static void removeCrystallizationInput(ItemStack input){
		CrystallizerRecipes.instance().removeCrystallizationInput(input);
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
		TransmutatorRecipes.instance().transmute(input, output, xp);
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
		TransmutatorRecipes.instance().transmute(input, output, xp);
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
		TransmutatorRecipes.instance().transmute(input, output, xp);
	}

	/**
	 * Removes Transmutation(s) based on the output
	 * @param input ItemStack to check for
	 *
	 * @since 2.0
	 */
	public static void removeTransmutation(ItemStack output){
		TransmutatorRecipes.instance().removeTransmutation(output);
	}

	/**
	 * Removes a Transmutation based on the input
	 * @param input ItemStack to check for
	 *
	 * @since 2.0
	 */
	public static void removeTransmutationInput(ItemStack input){
		TransmutatorRecipes.instance().removeTransmutationInput(input);
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
		if(!OreDictionary.getOres(output).isEmpty())
			for(ItemStack stack : OreDictionary.getOres(input))
				FurnaceRecipes.instance().addSmeltingRecipe(stack, OreDictionary.getOres(output).get(0), xp);
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
		if(!OreDictionary.getOres(output1).isEmpty() && !OreDictionary.getOres(output2).isEmpty())
			for(ItemStack stack : OreDictionary.getOres(input))
				addCrystallization(stack, OreDictionary.getOres(output1).get(0), OreDictionary.getOres(output2).get(0), xp);
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
		if(!OreDictionary.getOres(output1).isEmpty() && !OreDictionary.getOres(output2).isEmpty()){
			ItemStack o1 = OreDictionary.getOres(output1).get(0).copy();
			o1.setCount(out1);
			ItemStack o2 = OreDictionary.getOres(output2).get(0).copy();
			o2.setCount(out2);
			for(ItemStack stack : OreDictionary.getOres(input))
				addCrystallization(stack, o1, o2, xp);
		}
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
		if(!OreDictionary.getOres(output).isEmpty())
			for(ItemStack stack : OreDictionary.getOres(input))
				addSingleCrystallization(stack, OreDictionary.getOres(output).get(0), xp);
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
		if(!OreDictionary.getOres(output).isEmpty()){
			ItemStack o = OreDictionary.getOres(output).get(0).copy();
			o.setCount(out);
			for(ItemStack stack : OreDictionary.getOres(input))
				addSingleCrystallization(stack, o, xp);
		}
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
		if(!OreDictionary.getOres(output).isEmpty())
			for(ItemStack stack : OreDictionary.getOres(input))
				addTransmutation(stack, OreDictionary.getOres(output).get(0), xp);
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
		if(!OreDictionary.getOres(output).isEmpty()){
			ItemStack o = OreDictionary.getOres(output).get(0).copy();
			o.setCount(out);
			for(ItemStack stack : OreDictionary.getOres(input))
				addTransmutation(stack, o, xp);
		}
	}

	/**
	 * Basic Materialization.<br>
	 * Note: all inputs has to be either {@link ICrystal}s or be registered in the Crystal List {@link AbyssalCraftAPI#addCrystal(ItemStack)}
	 * @param output The output
	 * @param input An array of ItemStacks (maximum is 5)
	 *
	 * @since 1.11.0
	 */
	public static void addMaterialization(ItemStack output, ItemStack...input){
		for(ItemStack item : input)
			if(!APIUtils.isCrystal(item)) throw new ClassCastException("All of the input items has to be Crystals!");
		if(input.length > 0 && input != null)
			if(input.length <= 5)
				MaterializerRecipes.instance().materialize(input, output);
			else logger.log(Level.ERROR, "This Materializer recipe has more than 5 inputs! ({})", input.length);
		else logger.log(Level.ERROR, "This Materializer recipe has no inputs!");
	}

	/**
	 * Basic Materialization.
	 * @param materialization A Materializer Recipe
	 *
	 * @since 1.5
	 */
	public static void addMaterialization(Materialization materialization){
		MaterializerRecipes.instance().materialize(materialization);
	}

	/**
	 * OreDictionary specific Materialization.<br>
	 * Note: all inputs has to be either {@link ICrystal}s or be registered in the Crystal List {@link AbyssalCraftAPI#addCrystal(ItemStack)}
	 * @param output The output (everything registered to the ore name will be added)
	 * @param input An array of ItemStacks (maximum is 5)
	 *
	 * @since 1.27.0
	 */
	public static void addMaterialization(String output, ItemStack...input) {
		OreDictionary.getOres(output, false).forEach(stack -> {
			if(stack.getHasSubtypes() && stack.getMetadata() == OreDictionary.WILDCARD_VALUE) {
				NonNullList<ItemStack> list = NonNullList.create();
				stack.getItem().getSubItems(stack.getItem().getCreativeTab(), list);
				list.stream().filter(Predicates.not(ItemStack::isEmpty)).forEach(is -> addMaterialization(is, input));
			}
			else AbyssalCraftAPI.addMaterialization(stack, input);
		});
	}

	/**
	 * Removes Materialization(s) based on the output
	 * @param output ItemStack output to check for
	 *
	 * @since 2.0
	 */
	public static void removeMaterialization(ItemStack output){
		MaterializerRecipes.instance().removeMaterialization(output);
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
	 * Gets the fuel value from an ItemStack, depending on the fuel type
	 * @param itemStack The ItemStack getting checked
	 * @param type The fuel type
	 * @return The fuel value for the specified machine
	 *
	 * @since 1.0
	 */
	public static int getFuelValue(ItemStack itemStack, FuelType type){
		if(type == FuelType.FURNACE)
			return ForgeEventFactory.getItemBurnTime(itemStack);
		else {
			FuelBurnTimeEvent event = new FuelBurnTimeEvent(itemStack, type);
			MinecraftForge.EVENT_BUS.post(event);

			return event.getBurnTime();
		}
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
	 * Method used to register pages to the Necronomicon
	 * @param data The NecroData instance, with page data
	 * @param bookType The Necronomicon "level" required in order to read this.
	 * <ul>
	 * <li>0 = Necronomicon</li>
	 * <li>1 = Abyssal Wasteland Necronomicon</li>
	 * <li>2 = Dreadlands Necronomicon</li>
	 * <li>3 = Omothol Necronomicon</li>
	 * <li>4 = Abyssalnomicon</li>
	 * </ul>
	 *
	 * @since 1.3.5
	 */
	public static void registerNecronomiconData(NecroData data, int bookType){
		if(bookType <= 4 && bookType >= 0)
			necroData.put(data, bookType);
		else logger.log(Level.ERROR, "Necronomicon book type does not exist: {}", bookType);
	}

	/**
	 * Used by the Necronomicon for fetching pages made outside of AbyssalCraft
	 * @return A HashMap of NecroDatas and Integers
	 *
	 * @since 1.3.5
	 */
	public static Map<NecroData,Integer> getNecronomiconData(){
		return necroData;
	}

	/**
	 * Used by AbyssalCraft to set the Aklo Font.<br>
	 * If any other mod tries to use this method, nothing will happen.
	 * @param font Font instance
	 */
	@SideOnly(Side.CLIENT)
	public static void setAkloFont(FontRenderer font){
		if(aklo_font == null && Loader.instance().getLoaderState() == LoaderState.INITIALIZATION
				&& Loader.instance().activeModContainer().getModId().equals("abyssalcraft"))
			aklo_font = font;
	}

	/**
	 * Getter for the Aklo Font
	 *
	 * @since 1.12.0
	 */
	@SideOnly(Side.CLIENT)
	public static FontRenderer getAkloFont(){
		return aklo_font;
	}
}
