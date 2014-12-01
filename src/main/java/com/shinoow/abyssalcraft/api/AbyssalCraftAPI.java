/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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

import java.lang.reflect.*;
import java.util.*;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.item.*;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.recipe.*;

import cpw.mods.fml.common.IFuelHandler;
import cpw.mods.fml.common.registry.*;

/**
 * Main API class for AbyssalCraft, has child classes for most features.
 * 
 * @author shinoow
 *
 */
public class AbyssalCraftAPI {

	/**
	 * Enchantment IDs, first one is the Coralium enchantment, second Dread enchantment,
	 * third the Light Pierce enchantment, and the fourth is the Iron Wall enchantment.
	 */
	public static int enchId1, enchId2, enchId3, enchId4;

	private static List<IFuelHandler> crystallizerFuelHandlers = Lists.newArrayList();
	private static List<IFuelHandler> transmutatorFuelHandlers = Lists.newArrayList();

	private static HashMap<Integer, String> potionRequirements = null;
	private static HashMap<Integer, String> potionAmplifiers = null;

	public static DamageSource coralium = new DamageSource("coralium").setDamageBypassesArmor().setMagicDamage();
	public static DamageSource dread = new DamageSource("dread").setDamageBypassesArmor().setMagicDamage();
	public static DamageSource antimatter = new DamageSource("antimatter").setDamageBypassesArmor().setMagicDamage();

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
					} catch (IllegalArgumentException e) {
						System.err.println("Whoops, something screwed up here, please report this to shinoow:");
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						System.err.println("Whoops, something screwed up here, please report this to shinoow:");
						e.printStackTrace();
					}
				}
				if(f.getName().equals("potionAmplifiers") || f.getName().equals("field_77928_m")){
					f.setAccessible(true);
					try {
						potionAmplifiers = (HashMap<Integer, String>)f.get(null);
					} catch (IllegalArgumentException e) {
						System.err.println("Whoops, something screwed up here, please report this to shinoow:");
						e.printStackTrace();
					} catch (IllegalAccessException e) {
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
	 * Adds a bit sequence used to calculate the status on a potion.
	 * This description probably hardly makes any sense, deal with it.
	 * @param id The potion id
	 * @param requirements A bit sequence
	 */
	public static void addPotionRequirements(int id, String requirements){
		potionRequirements.put(Integer.valueOf(id), requirements);
	}

	/**
	 * Adds an amplifier to a potion.
	 * This description probably hardly makes any sense, deal with it.
	 * @param id The potion id
	 * @param amplifier The potion amplifier value (usually 5)
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
	 * Basic Engraving
	 * @param input The ItemStack to engrave
	 * @param output The ItemStack output
	 * @param engraving The engraving template (must be an {@link ItemEngraving})
	 * @param xp Amount of exp given
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
	 * Adds biomes for a Depths Ghoul to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addDepthsGhoulSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[0], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Evil Pig to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addEvilPigSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[1], weightedProb, min, max, EnumCreatureType.creature, biomes);
	}

	/**
	 * Adds biomes for a Abyssal Zombie to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addAbyssalZombieSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[2], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Abyssalnite Golem to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addAbyssalniteGolemSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[4], weightedProb, min, max, EnumCreatureType.creature, biomes);
	}

	/**
	 * Adds biomes for a Dreaded Abyssalnite Golem to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addDreadedAbyssalniteGolemSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[5], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Dreadguard to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addDreadguardSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[6], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Spectral Dragon to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addSpectralDragonSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[7], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Shadow Creature to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addShadowCreatureSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[9], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Shadow Monster to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addShadowMonsterSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[10], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Dreadling to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addDreadlingSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[11], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Dread Spawn to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addDreadSpawnSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[12], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Demon Pig to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addDemonPigSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[13], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Skeleton Goliath to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addSkeletonGoliathSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[14], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Spawn of Cha'garoth to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addSpawnofChagarothSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[15], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Fist of Cha'garoth to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addFistofChagarothSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[16], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Shadow Beast to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addShadowBeastSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[18], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Lesser Shoggoth to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addLesserShoggothSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[31], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Shadow Titan to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addShadowTitanSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[32], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Omothol Warden to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addOmotholWardenSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[33], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Minion of The Gatekeeper to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addGatekeeperMinionSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[34], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Omothol Ghoul to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addOmotholGhoulSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[35], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Adds biomes for a Remnant to spawn in
	 * @param weightedProb The chance of this mob spawning
	 * @param min Min mobs to spawn
	 * @param max Max mobs to spawn
	 * @param biomes Biomes where the mob will spawn
	 */
	public static void addRemnantSpawning(int weightedProb, int min, int max, BiomeGenBase[] biomes){
		EntityRegistry.addSpawn(ACEntities.mobNames[36], weightedProb, min, max, EnumCreatureType.monster, biomes);
	}

	/**
	 * Contains the names of all mobs added in AbyssalCraft.
	 * Use the methods in the main API class to make them spawn in your biome(s).
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
	 * Contains all potion effects added in AbyssalCraft
	 * 
	 * @author shinoow
	 *
	 */
	public static class ACPotions {

		public static Potion Coralium_plague = Potion.potionTypes[100];
		public static Potion Dread_plague = Potion.potionTypes[101];
		public static Potion Antimatter = Potion.potionTypes[102];
	}
}