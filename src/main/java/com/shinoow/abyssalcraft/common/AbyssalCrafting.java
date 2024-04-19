/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.*;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.rending.Rending;
import com.shinoow.abyssalcraft.api.rending.RendingRegistry;
import com.shinoow.abyssalcraft.api.ritual.*;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;
import com.shinoow.abyssalcraft.common.disruptions.*;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilSheep;
import com.shinoow.abyssalcraft.common.ritual.*;
import com.shinoow.abyssalcraft.common.spells.*;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.init.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class AbyssalCrafting {

	public static void addRecipes()
	{
		addBlockSmelting();
		addItemSmelting();
		addCrystallization();
		addTransmutation();
		addEngraving();
		addMaterialization();
		addRitualRecipes();
		addDisruptions();
		addSpells();
		addRendings();
	}

	private static void addBlockSmelting(){

		GameRegistry.addSmelting(new ItemStack(ACBlocks.darkstone_cobblestone), new ItemStack(ACBlocks.darkstone), 0.1F);
		AbyssalCraftAPI.addOreSmelting("oreAbyssalnite", "ingotAbyssalnite", 3F);
		AbyssalCraftAPI.addOreSmelting("oreCoralium", "gemCoralium", 3F);
		GameRegistry.addSmelting(ACBlocks.darklands_oak_wood, new ItemStack(Items.COAL, 1, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.darklands_oak_wood_2, new ItemStack(Items.COAL, 1, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.coralium_infused_stone, new ItemStack(ACItems.coralium_pearl), 3F);
		GameRegistry.addSmelting(ACBlocks.pearlescent_coralium_ore, new ItemStack(ACItems.coralium_pearl), 3F);
		GameRegistry.addSmelting(ACBlocks.liquified_coralium_ore, new ItemStack(ACItems.refined_coralium_ingot), 3F);
		GameRegistry.addSmelting(ACBlocks.dreaded_abyssalnite_ore, new ItemStack(ACItems.abyssalnite_ingot, 1), 3F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.coralium_stone), new ItemStack(ACItems.coralium_brick, 1), 0.1F);
		GameRegistry.addSmelting(ACBlocks.nitre_ore, new ItemStack(ACItems.nitre, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_iron_ore, new ItemStack(Items.IRON_INGOT, 1), 0.7F);
		GameRegistry.addSmelting(ACBlocks.abyssal_gold_ore, new ItemStack(Items.GOLD_INGOT, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_diamond_ore, new ItemStack(Items.DIAMOND, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_nitre_ore, new ItemStack(ACItems.nitre, 1), 1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_tin_ore, new ItemStack(ACItems.tin_ingot, 1), 0.7F);
		GameRegistry.addSmelting(ACBlocks.abyssal_copper_ore, new ItemStack(ACItems.copper_ingot, 1), 0.7F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.ethaxium), new ItemStack(ACItems.ethaxium_brick), 0.2F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.ethaxium_brick, 1, 0), new ItemStack(ACBlocks.cracked_ethaxium_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0), new ItemStack(ACBlocks.cracked_dark_ethaxium_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.darkstone_brick, 1, 0), new ItemStack(ACBlocks.cracked_darkstone_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.abyssal_stone_brick, 1, 0), new ItemStack(ACBlocks.cracked_abyssal_stone_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.dreadstone_brick, 1, 0), new ItemStack(ACBlocks.cracked_dreadstone_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.abyssalnite_stone_brick, 1, 0), new ItemStack(ACBlocks.cracked_abyssalnite_stone_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.coralium_stone_brick, 1, 0), new ItemStack(ACBlocks.cracked_coralium_stone_brick, 1), 0.1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_sand, new ItemStack(ACBlocks.abyssal_sand_glass), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.abyssal_cobblestone), new ItemStack(ACBlocks.abyssal_stone), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.dreadstone_cobblestone), new ItemStack(ACBlocks.dreadstone), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.abyssalnite_cobblestone), new ItemStack(ACBlocks.abyssalnite_stone), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.coralium_cobblestone), new ItemStack(ACBlocks.coralium_stone), 0.1F);
		GameRegistry.addSmelting(ACBlocks.dreadlands_log, new ItemStack(ACItems.charcoal), 1F);
		GameRegistry.addSmelting(new ItemStack(ACItems.generic_meat), new ItemStack(ACItems.cooked_generic_meat), 0.35F);
	}

	private static void addItemSmelting(){

		GameRegistry.addSmelting(ACItems.chunk_of_abyssalnite, new ItemStack(ACItems.abyssalnite_ingot), 3F);
		GameRegistry.addSmelting(ACItems.chunk_of_coralium, new ItemStack(ACItems.refined_coralium_ingot, 2), 3F);
		GameRegistry.addSmelting(ACItems.dreaded_chunk_of_abyssalnite, new ItemStack(ACItems.abyssalnite_ingot), 3F);
		GameRegistry.addSmelting(ACItems.coin, new ItemStack(Items.IRON_INGOT, 4), 0.5F);

		if(ACConfig.smeltingRecipes){
			addArmorSmelting(Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS, new ItemStack(Items.LEATHER));
			addArmorSmelting(ACItems.abyssalnite_helmet, ACItems.abyssalnite_chestplate, ACItems.abyssalnite_leggings, ACItems.abyssalnite_boots, new ItemStack(ACItems.abyssalnite_ingot));
			addArmorSmelting(ACItems.refined_coralium_helmet, ACItems.refined_coralium_chestplate, ACItems.refined_coralium_leggings, ACItems.refined_coralium_boots, new ItemStack(ACItems.refined_coralium_ingot));
			addArmorSmelting(ACItems.dreadium_helmet, ACItems.dreadium_chestplate, ACItems.dreadium_leggings, ACItems.dreadium_boots, new ItemStack(ACItems.dreadium_ingot));
			addArmorSmelting(ACItems.ethaxium_helmet, ACItems.ethaxium_chestplate, ACItems.ethaxium_leggings, ACItems.ethaxium_boots, new ItemStack(ACItems.ethaxium_nugget));
		}
	}

	private static void addCrystallization(){

		AbyssalCraftAPI.addSingleCrystallization(Items.BLAZE_POWDER, new ItemStack(ACItems.crystal_shard_blaze, 4), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(new ItemStack(Items.DYE, 1, 15), new ItemStack(ACItems.crystal_shard_phosphorus, 4), 0.0F);
		AbyssalCraftAPI.addCrystallization(ACItems.dreaded_chunk_of_abyssalnite, new ItemStack(ACItems.crystal_shard_abyssalnite, 4), new ItemStack(ACItems.crystal_shard_dreadium, 4), 0.2F);
		AbyssalCraftAPI.addCrystallization(Items.WATER_BUCKET, new ItemStack(ACItems.crystal_hydrogen, 12), new ItemStack(ACItems.crystal_shard_oxygen, 6), 0.1F);
		AbyssalCraftAPI.addCrystallization(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.crystal_shard_hydrogen, 6), new ItemStack(ACItems.crystal_shard_oxygen, 3), 0.1F);
		AbyssalCraftAPI.addCrystallization(ACItems.methane, new ItemStack(ACItems.crystal_shard_oxygen, 4), new ItemStack(ACItems.crystal_hydrogen, 16), 0.1F);
		AbyssalCraftAPI.addCrystallization(Items.GUNPOWDER, new ItemStack(ACItems.crystal_shard_nitrate, 16), new ItemStack(ACItems.crystal_shard_sulfur, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(Blocks.OBSIDIAN, new ItemStack(ACItems.crystal_shard_silica, 4), new ItemStack(ACItems.crystal_shard_magnesia, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(Blocks.STONE, new ItemStack(ACItems.crystal_shard_silica, 4), new ItemStack(ACItems.crystal_shard_magnesia, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_silicon), new ItemStack(ACItems.crystal_oxygen, 2), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard_silica), new ItemStack(ACItems.crystal_shard_silicon), new ItemStack(ACItems.crystal_shard_oxygen, 2), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_alumina), new ItemStack(ACItems.crystal_aluminium, 2), new ItemStack(ACItems.crystal_oxygen, 3), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard_alumina), new ItemStack(ACItems.crystal_shard_aluminium, 2), new ItemStack(ACItems.crystal_shard_oxygen, 3), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_magnesium), new ItemStack(ACItems.crystal_oxygen), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard_magnesia), new ItemStack(ACItems.crystal_shard_magnesium), new ItemStack(ACItems.crystal_shard_oxygen), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_methane), new ItemStack(ACItems.crystal_carbon), new ItemStack(ACItems.crystal_hydrogen, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard_methane), new ItemStack(ACItems.crystal_shard_carbon), new ItemStack(ACItems.crystal_shard_hydrogen, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_nitrate), new ItemStack(ACItems.crystal_nitrogen), new ItemStack(ACItems.crystal_oxygen, 3), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.crystal_shard_nitrate), new ItemStack(ACItems.crystal_shard_nitrogen), new ItemStack(ACItems.crystal_shard_oxygen, 3), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(Items.ROTTEN_FLESH, new ItemStack(ACItems.crystal_shard_phosphorus, 8), 0.1F);
		AbyssalCraftAPI.addSingleCrystallization(ACItems.overworld_shoggoth_flesh, new ItemStack(ACItems.crystal_shard_phosphorus, 8), 0.2F);
		AbyssalCraftAPI.addCrystallization(ACItems.abyssal_shoggoth_flesh, new ItemStack(ACItems.crystal_shard_phosphorus, 8), new ItemStack(ACItems.crystal_fragment_coralium, 2), 0.2F);
		AbyssalCraftAPI.addCrystallization(ACItems.dreaded_shoggoth_flesh, new ItemStack(ACItems.crystal_shard_phosphorus, 8), new ItemStack(ACItems.crystal_fragment_dreadium, 2), 0.2F);
		AbyssalCraftAPI.addCrystallization(ACItems.omothol_shoggoth_flesh, new ItemStack(ACItems.crystal_shard_phosphorus, 8), new ItemStack(ACItems.crystal_fragment_carbon, 2), 0.2F);
		AbyssalCraftAPI.addCrystallization(ACItems.shadow_shoggoth_flesh, new ItemStack(ACItems.crystal_shard_phosphorus, 8), new ItemStack(ACItems.shadow_gem, 1), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.coralium_plagued_flesh), new ItemStack(ACItems.crystal_shard_phosphorus, 8), new ItemStack(ACItems.crystal_shard_coralium), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.coralium_plagued_flesh_on_a_bone), new ItemStack(ACItems.crystal_shard_phosphorus, 12), new ItemStack(ACItems.crystal_shard_coralium), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.skin_of_the_abyssal_wasteland), new ItemStack(ACItems.crystal_shard_phosphorus, 8), new ItemStack(ACItems.abyssal_wasteland_essence), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.skin_of_the_dreadlands), new ItemStack(ACItems.crystal_shard_phosphorus, 8), new ItemStack(ACItems.dreadlands_essence), 0.2F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(ACItems.skin_of_omothol), new ItemStack(ACItems.crystal_shard_phosphorus, 8), new ItemStack(ACItems.omothol_essence), 0.2F);
		AbyssalCraftAPI.addCrystallization(ACItems.dreaded_shard_of_abyssalnite, new ItemStack(ACItems.crystal_shard_abyssalnite), new ItemStack(ACItems.crystal_shard_dreadium, 4), 0.2F);
		AbyssalCraftAPI.addSingleCrystallization(Items.BONE, new ItemStack(ACItems.crystal_shard_calcium, 4), 0.2F);
		AbyssalCraftAPI.addCrystallization(Items.PRISMARINE_SHARD, new ItemStack(ACItems.crystal_shard_silica, 4), new ItemStack(ACItems.crystal_shard_beryl, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(Items.PRISMARINE_CRYSTALS, new ItemStack(ACItems.crystal_shard_silica, 4), new ItemStack(ACItems.crystal_shard_beryl, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(Blocks.PRISMARINE, 1, 0), new ItemStack(ACItems.crystal_shard_silica, 16), new ItemStack(ACItems.crystal_shard_tin, 27), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(Blocks.PRISMARINE, 1, 1), new ItemStack(ACItems.crystal_silica, 4), new ItemStack(ACItems.crystal_beryl, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(Blocks.PRISMARINE, 1, 2), new ItemStack(ACItems.crystal_shard_silica, 32), new ItemStack(ACItems.crystal_shard_beryl, 32), 0.1F);
		AbyssalCraftAPI.addCrystallization(Items.EGG, new ItemStack(ACItems.crystal_shard_calcium, 4), new ItemStack(ACItems.crystal_shard_phosphorus, 4), 0.1F);

		if(ACConfig.crystal_rework) {
			AbyssalCraftAPI.addSingleCrystallization(ACItems.refined_coralium_ingot, new ItemStack(ACItems.crystal_coralium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.chunk_of_coralium, new ItemStack(ACItems.crystal_coralium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACBlocks.liquified_coralium_ore, new ItemStack(ACItems.crystal_coralium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.abyssalnite_ingot, new ItemStack(ACItems.crystal_abyssalnite), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.chunk_of_abyssalnite, new ItemStack(ACItems.crystal_abyssalnite), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.dreadium_ingot, new ItemStack(ACItems.crystal_dreadium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Items.IRON_INGOT, new ItemStack(ACItems.crystal_iron), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Items.GOLD_INGOT, new ItemStack(ACItems.crystal_gold), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Items.REDSTONE, new ItemStack(ACItems.crystal_redstone), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.charcoal, new ItemStack(ACItems.crystal_carbon), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.crystal_carbon), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreAbyssalnite", "crystalAbyssalnite", 2, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreCoralium", "crystalCoralium", 2, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreIron", "crystalIron", 2, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreGold", "crystalGold", 2, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotTin", "crystalTin", 1, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreTin", "crystalTin", 2, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotCopper", "crystalCopper", 1, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreCopper", "crystalCopper", 2, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotAluminum", "crystalAluminium", 1, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotAluminium", "crystalAluminium", 1, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreAluminum", "crystalAluminium", 2, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("blockCopper", "crystalClusterCopper", 1, 0.9F);
			AbyssalCraftAPI.addSingleCrystallization("blockTin", "crystalClusterTin", 1, 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.GOLD_BLOCK, new ItemStack(ACBlocks.gold_crystal_cluster), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.IRON_BLOCK, new ItemStack(ACBlocks.iron_crystal_cluster), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACBlocks.block_of_abyssalnite), new ItemStack(ACBlocks.abyssalnite_crystal_cluster), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACBlocks.block_of_refined_coralium), new ItemStack(ACBlocks.coralium_crystal_cluster), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACBlocks.block_of_dreadium), new ItemStack(ACBlocks.dreadium_crystal_cluster), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.COAL_ORE, new ItemStack(ACItems.crystal_carbon, 2), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.COAL_BLOCK, new ItemStack(ACBlocks.carbon_crystal_cluster), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.REDSTONE_ORE, new ItemStack(ACItems.crystal_redstone, 2), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.REDSTONE_BLOCK, new ItemStack(ACBlocks.redstone_crystal_cluster), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization("ingotZinc", "crystalZinc", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreZinc", "crystalZinc", 2, 0.1F);
			AbyssalCraftAPI.addCrystallization(ACBlocks.dreaded_abyssalnite_ore, new ItemStack(ACItems.crystal_abyssalnite, 2), new ItemStack(ACItems.crystal_dreadium, 2), 0.2F);
			AbyssalCraftAPI.addCrystallization("dustSaltpeter", "crystalPotassium", 1, "crystalNitrate", 1, 0.1F);
			AbyssalCraftAPI.addCrystallization("oreSaltpeter", "crystalPotassium", 2, "crystalNitrate", 2, 0.1F);
			AbyssalCraftAPI.addCrystallization("ingotBronze", "crystalCopper", 3, "crystalTin", 1, 0.4F);
			AbyssalCraftAPI.addCrystallization(new ItemStack(Items.DYE, 1, 4), new ItemStack(ACItems.crystal_silica, 6), new ItemStack(ACItems.crystal_sulfur, 4), 0.15F);
			AbyssalCraftAPI.addCrystallization(Blocks.LAPIS_ORE, new ItemStack(ACItems.crystal_silica, 21), new ItemStack(ACItems.crystal_potassium, 8), 0.15F);
			AbyssalCraftAPI.addCrystallization(Blocks.LAPIS_BLOCK, new ItemStack(ACBlocks.silica_crystal_cluster, 6), new ItemStack(ACBlocks.sulfur_crystal_cluster, 4), 1.0F);
			AbyssalCraftAPI.addCrystallization("ingotBrass", "crystalCopper", 3, "crystalZinc", 2, 0.5F);
			AbyssalCraftAPI.addCrystallization("oreBrass", "crystalCopper", 6, "crystalZinc", 4, 0.5F);
			AbyssalCraftAPI.addSingleCrystallization(Items.GOLD_NUGGET, new ItemStack(ACItems.crystal_shard_gold), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.abyssalnite_ingot), new ItemStack(ACItems.crystal_shard_abyssalnite), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.refined_coralium_ingot), new ItemStack(ACItems.crystal_shard_coralium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.dreadium_ingot), new ItemStack(ACItems.crystal_shard_dreadium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetIron", "crystalShardIron", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetCopper", "crystalShardCopper", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetTin", "crystalShardTin", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetAluminium", "crystalShardAluminium", 0.1F);
			AbyssalCraftAPI.addCrystallization("nuggetBronze", "crystalShardCopper", 3, "crystalShardTin", 1, 0.1F);
			AbyssalCraftAPI.addCrystallization("nuggetBrass", "crystalShardCopper", 3, "crystalShardZinc", 2, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.coralium_gem, new ItemStack(ACItems.crystal_shard_coralium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetZinc", "crystalShardZinc", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetMagnesium", "crystalShardMagnesium", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotMagnesium", "crystalMagnesium", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetCalcium", "crystalShardCalcium", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotCalcium", "crystalCalcium", 0.1F);

			//Crystallization for dusts
			AbyssalCraftAPI.addSingleCrystallization("dustIron", "crystalIron", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustGold", "crystalGold", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustTin", "crystalTin", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustCopper", "crystalCopper", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustCoal", "crystalCarbon", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustAluminium", "crystalAluminium", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustSulfur", "crystalSulfur", 0.1F);
			AbyssalCraftAPI.addCrystallization("dustBronze", "crystalCopper", 3, "crystalTin", 1, 0.1F);
			AbyssalCraftAPI.addCrystallization("dustBrass", "crystalCopper", 3, "crystalZinc", 2, 0.1F);
		} else {
			AbyssalCraftAPI.addSingleCrystallization(ACItems.refined_coralium_ingot, new ItemStack(ACItems.crystal_shard_coralium, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.chunk_of_coralium, new ItemStack(ACItems.crystal_shard_coralium, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACBlocks.liquified_coralium_ore, new ItemStack(ACItems.crystal_shard_coralium, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.abyssalnite_ingot, new ItemStack(ACItems.crystal_shard_abyssalnite, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.chunk_of_abyssalnite, new ItemStack(ACItems.crystal_shard_abyssalnite, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.dreadium_ingot, new ItemStack(ACItems.crystal_shard_dreadium, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Items.IRON_INGOT, new ItemStack(ACItems.crystal_shard_iron, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Items.GOLD_INGOT, new ItemStack(ACItems.crystal_shard_gold, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Items.REDSTONE, new ItemStack(ACItems.crystal_shard_redstone, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.charcoal, new ItemStack(ACItems.crystal_shard_carbon, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(Items.COAL, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.crystal_shard_carbon, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreAbyssalnite", "crystalShardAbyssalnite", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreCoralium", "crystalShardCoralium", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreIron", "crystalShardIron", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreGold", "crystalShardGold", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotTin", "crystalShardTin", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreTin", "crystalShardTin", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotCopper", "crystalShardCopper", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreCopper", "crystalShardCopper", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotAluminum", "crystalShardAluminium", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotAluminium", "crystalShardAluminium", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreAluminum", "crystalShardAluminium", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("blockCopper", "crystalCopper", 4, 0.9F);
			AbyssalCraftAPI.addSingleCrystallization("blockTin", "crystalTin", 4, 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.GOLD_BLOCK, new ItemStack(ACItems.crystal_gold, 4), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.IRON_BLOCK, new ItemStack(ACItems.crystal_iron, 4), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACBlocks.block_of_abyssalnite), new ItemStack(ACItems.crystal_abyssalnite, 4), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACBlocks.block_of_refined_coralium), new ItemStack(ACItems.crystal_coralium, 4), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACBlocks.block_of_dreadium), new ItemStack(ACItems.crystal_dreadium, 4), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.COAL_ORE, new ItemStack(ACItems.crystal_shard_carbon, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.COAL_BLOCK, new ItemStack(ACItems.crystal_carbon, 4), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.REDSTONE_ORE, new ItemStack(ACItems.crystal_shard_redstone, 4), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(Blocks.REDSTONE_BLOCK, new ItemStack(ACItems.crystal_redstone, 4), 0.9F);
			AbyssalCraftAPI.addSingleCrystallization("ingotZinc", "crystalZinc", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("oreZinc", "crystalZinc", 0.1F);
			AbyssalCraftAPI.addCrystallization(ACBlocks.dreaded_abyssalnite_ore, new ItemStack(ACItems.crystal_shard_abyssalnite, 4), new ItemStack(ACItems.crystal_shard_dreadium, 4), 0.2F);
			AbyssalCraftAPI.addCrystallization("dustSaltpeter", "crystalShardPotassium", 4, "crystalShardNitrate", 4, 0.1F);
			AbyssalCraftAPI.addCrystallization("oreSaltpeter", "crystalShardPotassium", 4, "crystalShardNitrate", 4, 0.1F);
			AbyssalCraftAPI.addCrystallization("ingotBronze", "crystalShardCopper", 3, "crystalShardTin", 1, 0.4F);
			AbyssalCraftAPI.addCrystallization(new ItemStack(Items.DYE, 1, 4), new ItemStack(ACItems.crystal_silica, 2), new ItemStack(ACItems.crystal_shard_tin, 2), 0.15F);
			AbyssalCraftAPI.addCrystallization(Blocks.LAPIS_ORE, new ItemStack(ACItems.crystal_shard_silica, 24), new ItemStack(ACItems.crystal_shard_tin, 2), 0.15F);
			AbyssalCraftAPI.addCrystallization(Blocks.LAPIS_BLOCK, new ItemStack(ACItems.crystal_silica, 24), new ItemStack(ACItems.crystal_sulfur, 16), 1.0F);
			AbyssalCraftAPI.addCrystallization("ingotBrass", "crystalShardCopper", 12, "crystalShardZinc", 8, 0.5F);
			AbyssalCraftAPI.addCrystallization("oreBrass", "crystalShardCopper", 12, "crystalShardZinc", 8, 0.5F);
			AbyssalCraftAPI.addSingleCrystallization(Items.GOLD_NUGGET, new ItemStack(ACItems.crystal_shard_gold), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.abyssalnite_ingot), new ItemStack(ACItems.crystal_shard_abyssalnite), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.refined_coralium_ingot), new ItemStack(ACItems.crystal_shard_coralium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(new ItemStack(ACItems.dreadium_ingot), new ItemStack(ACItems.crystal_shard_dreadium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetIron", "crystalShardIron", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetCopper", "crystalShardCopper", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetTin", "crystalShardTin", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetAluminium", "crystalShardAluminium", 0.1F);
			AbyssalCraftAPI.addCrystallization("nuggetBronze", "crystalShardCopper", 3, "crystalShardTin", 1, 0.1F);
			AbyssalCraftAPI.addCrystallization("nuggetBrass", "crystalShardCopper", 3, "crystalShardZinc", 2, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization(ACItems.coralium_gem, new ItemStack(ACItems.crystal_shard_coralium), 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetZinc", "crystalShardZinc", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetMagnesium", "crystalShardMagnesium", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotMagnesium", "crystalShardMagnesium", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("nuggetCalcium", "crystalShardCalcium", 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("ingotCalcium", "crystalShardCalcium", 4, 0.1F);

			//Crystallization for dusts
			AbyssalCraftAPI.addSingleCrystallization("dustIron", "crystalShardIron", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustGold", "crystalShardGold", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustTin", "crystalShardTin", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustCopper", "crystalShardCopper", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustCoal", "crystalShardCarbon", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustAluminium", "crystalShardAluminium", 4, 0.1F);
			AbyssalCraftAPI.addSingleCrystallization("dustSulfur", "crystalShardSulfur", 4, 0.1F);
			AbyssalCraftAPI.addCrystallization("dustBronze", "crystalShardCopper", 12, "crystalShardTin", 4, 0.1F);
			AbyssalCraftAPI.addCrystallization("dustBrass", "crystalShardCopper", 12, "crystalShardZinc", 8, 0.1F);
		}
	}

	private static void addTransmutation(){

		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.darkstone), new ItemStack(Blocks.STONE, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.dreaded_shard_of_abyssalnite, new ItemStack(ACItems.dreadium_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(Blocks.STONE, new ItemStack(ACBlocks.darkstone), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.STONEBRICK, new ItemStack(ACBlocks.darkstone_brick, 1, 0), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.darkstone_brick, 1, 0), new ItemStack(Blocks.STONEBRICK), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.COBBLESTONE, new ItemStack(ACBlocks.darkstone_cobblestone), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.darkstone_cobblestone), new ItemStack(Blocks.COBBLESTONE, 1), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.coralium_stone_brick, 1, 0), new ItemStack(ACItems.coralium_brick, 4), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.coralium_brick, new ItemStack(ACBlocks.coralium_stone), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.dense_carbon_cluster, new ItemStack(Items.DIAMOND), 0.5F);
		AbyssalCraftAPI.addTransmutation(ACItems.dread_plagued_gateway_key, new ItemStack(ACItems.rlyehian_gateway_key), 1.0F);

		AbyssalCraftAPI.addTransmutation(Items.LAVA_BUCKET, new ItemStack(ACBlocks.solid_lava), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.END_STONE, new ItemStack(ACBlocks.ethaxium), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.ethaxium), new ItemStack(Blocks.END_STONE), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.ethaxium_brick, 1, 0), new ItemStack(ACBlocks.ethaxium), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_beef, new ItemStack(Items.COOKED_BEEF), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_pork, new ItemStack(Items.COOKED_PORKCHOP), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_chicken, new ItemStack(Items.COOKED_CHICKEN), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_bone, new ItemStack(Items.BONE, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.rotten_anti_flesh, new ItemStack(Items.ROTTEN_FLESH, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_spider_eye, new ItemStack(Items.SPIDER_EYE, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_plagued_flesh, new ItemStack(ACItems.coralium_plagued_flesh, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_plagued_flesh_on_a_bone, new ItemStack(ACItems.coralium_plagued_flesh_on_a_bone, 2), 0.3F);

		if(ACConfig.crystal_rework) {
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.abyssalnite_crystal_cluster), new ItemStack(ACItems.abyssalnite_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.coralium_crystal_cluster), new ItemStack(ACItems.refined_coralium_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.dreadium_crystal_cluster), new ItemStack(ACItems.dreadium_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.iron_crystal_cluster), new ItemStack(Items.IRON_INGOT, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.gold_crystal_cluster), new ItemStack(Items.GOLD_INGOT, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.sulfur_crystal_cluster), new ItemStack(ACItems.sulfur, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.blaze_crystal_cluster), new ItemStack(Items.BLAZE_POWDER, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.redstone_crystal_cluster), new ItemStack(Items.REDSTONE, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.methane_crystal_cluster), new ItemStack(ACItems.methane, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.tin_crystal_cluster), new ItemStack(ACItems.tin_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.copper_crystal_cluster), new ItemStack(ACItems.copper_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalClusterAluminium", "ingotAluminum", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalClusterAluminium", "ingotAluminium", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalClusterZinc", "ingotZinc", 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_abyssalnite), new ItemStack(ACItems.abyssalnite_ingot), 0.1F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_coralium), new ItemStack(ACItems.refined_coralium_ingot), 0.1F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_dreadium), new ItemStack(ACItems.dreadium_ingot), 0.1F);
			AbyssalCraftAPI.addTransmutation("crystalAluminium", "nuggetAluminum", 0.1F);
			AbyssalCraftAPI.addTransmutation("crystalAluminium", "nuggetAluminium", 0.1F);
			AbyssalCraftAPI.addTransmutation("crystalIron", "nuggetIron", 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_gold), new ItemStack(Items.GOLD_NUGGET), 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalCopper", "nuggetCopper", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalTin", "nuggetTin", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalZinc", "nuggetZinc", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalMagnesium", "nuggetMagnesium", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalClusterMagnesium", "ingotMagnesium", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalCalcium", "nuggetCalcium", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalClusterCalcium", "ingotCalcium", 0.2F);
		} else {
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_abyssalnite), new ItemStack(ACItems.abyssalnite_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_coralium), new ItemStack(ACItems.refined_coralium_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_dreadium), new ItemStack(ACItems.dreadium_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_iron), new ItemStack(Items.IRON_INGOT, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_gold), new ItemStack(Items.GOLD_INGOT, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_sulfur), new ItemStack(ACItems.sulfur, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_blaze), new ItemStack(Items.BLAZE_POWDER, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_redstone), new ItemStack(Items.REDSTONE, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_hydrogen), new ItemStack(ACItems.crystal_hydrogen), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_oxygen), new ItemStack(ACItems.crystal_oxygen), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_nitrogen), new ItemStack(ACItems.crystal_nitrogen), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_methane), new ItemStack(ACItems.methane, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_tin), new ItemStack(ACItems.tin_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_copper), new ItemStack(ACItems.copper_ingot, 1), 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalAluminium", "ingotAluminum", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalAluminium", "ingotAluminium", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalZinc", "ingotZinc", 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_shard_abyssalnite), new ItemStack(ACItems.abyssalnite_ingot), 0.1F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_shard_coralium), new ItemStack(ACItems.refined_coralium_ingot), 0.1F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_shard_dreadium), new ItemStack(ACItems.dreadium_ingot), 0.1F);
			AbyssalCraftAPI.addTransmutation("crystalShardAluminium", "nuggetAluminum", 0.1F);
			AbyssalCraftAPI.addTransmutation("crystalShardAluminium", "nuggetAluminium", 0.1F);
			AbyssalCraftAPI.addTransmutation("crystalShardIron", "nuggetIron", 0.2F);
			AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_shard_gold), new ItemStack(Items.GOLD_NUGGET), 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalShardCopper", "nuggetCopper", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalShardTin", "nuggetTin", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalShardZinc", "nuggetZinc", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalShardMagnesium", "nuggetMagnesium", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalMagnesium", "ingotMagnesium", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalShardCalcium", "nuggetCalcium", 0.2F);
			AbyssalCraftAPI.addTransmutation("crystalCalcium", "ingotCalcium", 0.2F);
		}
	}

	private static void addEngraving(){

		AbyssalCraftAPI.addCoin(ACItems.coin);
		AbyssalCraftAPI.addCoin(ACItems.cthulhu_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.elder_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.jzahar_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.hastur_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.azathoth_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.nyarlathotep_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.yog_sothoth_engraved_coin);
		AbyssalCraftAPI.addCoin(ACItems.shub_niggurath_engraved_coin);
		AbyssalCraftAPI.addEngraving(ACItems.coin, (ItemEngraving)ACItems.blank_engraving, 0.0F);
		AbyssalCraftAPI.addEngraving(ACItems.cthulhu_engraved_coin, (ItemEngraving)ACItems.cthulhu_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.elder_engraved_coin, (ItemEngraving)ACItems.elder_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.jzahar_engraved_coin, (ItemEngraving)ACItems.jzahar_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.hastur_engraved_coin, (ItemEngraving)ACItems.hastur_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.azathoth_engraved_coin, (ItemEngraving)ACItems.azathoth_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.nyarlathotep_engraved_coin, (ItemEngraving)ACItems.nyarlathotep_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.yog_sothoth_engraved_coin, (ItemEngraving)ACItems.yog_sothoth_engraving, 0.5F);
		AbyssalCraftAPI.addEngraving(ACItems.shub_niggurath_engraved_coin, (ItemEngraving)ACItems.shub_niggurath_engraving, 0.5F);
	}

	private static void addMaterialization(){
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.BONE), new ItemStack(ACItems.crystal_calcium));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(ACItems.crystal_phosphorus));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.coralium_plagued_flesh), new ItemStack(ACItems.crystal_phosphorus), new ItemStack(ACItems.crystal_coralium));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.coralium_plagued_flesh_on_a_bone), new ItemStack(ACItems.crystal_phosphorus), new ItemStack(ACItems.crystal_calcium), new ItemStack(ACItems.crystal_coralium));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.COAL, 1, 0), new ItemStack(ACItems.crystal_carbon));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.COAL, 1, 1), new ItemStack(ACItems.crystal_carbon));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.IRON_INGOT), new ItemStack(ACItems.crystal_iron));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.GOLD_INGOT), new ItemStack(ACItems.crystal_gold));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.BLAZE_POWDER), new ItemStack(ACItems.crystal_blaze));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.BLAZE_ROD), new ItemStack(ACItems.crystal_blaze, 2));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.abyssalnite_ingot), new ItemStack(ACItems.crystal_abyssalnite));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.refined_coralium_ingot), new ItemStack(ACItems.crystal_coralium));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.dreadium_ingot), new ItemStack(ACItems.crystal_dreadium));
		AbyssalCraftAPI.addMaterialization("ingotTin", new ItemStack(ACItems.crystal_tin));
		AbyssalCraftAPI.addMaterialization("ingotCopper", new ItemStack(ACItems.crystal_copper));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.methane), new ItemStack(ACItems.crystal_methane));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.nitre), new ItemStack(ACItems.crystal_potassium), new ItemStack(ACItems.crystal_nitrate));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.sulfur), new ItemStack(ACItems.crystal_sulfur));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.REDSTONE), new ItemStack(ACItems.crystal_redstone));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.dread_fragment), new ItemStack(ACItems.crystal_phosphorus, 2), new ItemStack(ACItems.crystal_dreadium));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.STONE), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia));
		AbyssalCraftAPI.addMaterialization("oreGold", new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_gold));
		AbyssalCraftAPI.addMaterialization("oreIron", new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_iron));
		AbyssalCraftAPI.addMaterialization("oreCoal", new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_carbon));
		AbyssalCraftAPI.addMaterialization("oreRedstone", new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_redstone));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACBlocks.coralium_ore), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_coralium));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACBlocks.abyssalnite_ore), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_abyssalnite));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACBlocks.coralium_infused_stone), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_coralium, 2));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACBlocks.nitre_ore), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_potassium), new ItemStack(ACItems.crystal_nitrate));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.SPONGE), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_carbon));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.ELYTRA), new ItemStack(ACItems.crystal_carbon, 8), new ItemStack(ACItems.crystal_hydrogen, 13), new ItemStack(ACItems.crystal_oxygen, 5), new ItemStack(ACItems.crystal_nitrogen));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.PRISMARINE_SHARD), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_beryl));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.PRISMARINE_CRYSTALS), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_beryl));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.PRISMARINE, 1, 0), new ItemStack(ACItems.crystal_silica, 4), new ItemStack(ACItems.crystal_beryl, 4));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.PRISMARINE, 1, 1), new ItemStack(ACItems.crystal_silica, 9), new ItemStack(ACItems.crystal_beryl, 9));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.PRISMARINE, 1, 2), new ItemStack(ACItems.crystal_silica, 8), new ItemStack(ACItems.crystal_beryl, 8), new ItemStack(ACItems.crystal_carbon));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.SEA_LANTERN), new ItemStack(ACItems.crystal_silica, 9), new ItemStack(ACItems.crystal_beryl, 9));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.EGG), new ItemStack(ACItems.crystal_calcium), new ItemStack(ACItems.crystal_phosphorus));
		AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.charcoal), new ItemStack(ACItems.crystal_carbon), new ItemStack(ACItems.crystal_dreadium));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.COAL_BLOCK), new ItemStack(ACItems.crystal_carbon, 9));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.REDSTONE_BLOCK), new ItemStack(ACItems.crystal_redstone, 9));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.DIAMOND), new ItemStack(ACItems.crystal_carbon, 64));
		AbyssalCraftAPI.addMaterialization("oreDiamond", new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia), new ItemStack(ACItems.crystal_carbon, 64));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.STONE, 1, 1), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.STONE, 1, 2), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.STONE, 1, 3), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.STONE, 1, 4), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.STONE, 1, 5), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.STONE, 1, 6), new ItemStack(ACItems.crystal_silica), new ItemStack(ACItems.crystal_magnesia));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.LEATHER), new ItemStack(ACItems.crystal_phosphorus));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.RABBIT_HIDE), new ItemStack(ACItems.crystal_phosphorus));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.RABBIT_FOOT), new ItemStack(ACItems.crystal_phosphorus), new ItemStack(ACItems.crystal_calcium));
		AbyssalCraftAPI.addMaterialization("logWood", new ItemStack(ACItems.crystal_carbon, 6), new ItemStack(ACItems.crystal_hydrogen, 10), new ItemStack(ACItems.crystal_oxygen, 5));
		AbyssalCraftAPI.addMaterialization("plankWood", new ItemStack(ACItems.crystal_carbon, 6), new ItemStack(ACItems.crystal_hydrogen, 10), new ItemStack(ACItems.crystal_oxygen, 5));
		AbyssalCraftAPI.addMaterialization("treeSapling", new ItemStack(ACItems.crystal_carbon, 6), new ItemStack(ACItems.crystal_hydrogen, 10), new ItemStack(ACItems.crystal_oxygen, 5));
		AbyssalCraftAPI.addMaterialization("treeLeaves", new ItemStack(ACItems.crystal_carbon, 6), new ItemStack(ACItems.crystal_hydrogen, 10), new ItemStack(ACItems.crystal_oxygen, 5));
		AbyssalCraftAPI.addMaterialization("vine", new ItemStack(ACItems.crystal_carbon, 6), new ItemStack(ACItems.crystal_hydrogen, 10), new ItemStack(ACItems.crystal_oxygen, 5));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.DEADBUSH), new ItemStack(ACItems.crystal_carbon, 6), new ItemStack(ACItems.crystal_hydrogen, 10), new ItemStack(ACItems.crystal_oxygen, 5));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Items.DYE, 1, 4), new ItemStack(ACItems.crystal_silica, 6), new ItemStack(ACItems.crystal_sulfur, 4));
		AbyssalCraftAPI.addMaterialization("oreLapis", new ItemStack(ACItems.crystal_silica, 7), new ItemStack(ACItems.crystal_sulfur, 4), new ItemStack(ACItems.crystal_magnesia));
		AbyssalCraftAPI.addMaterialization(new ItemStack(Blocks.LAPIS_BLOCK), new ItemStack(ACItems.crystal_silica, 54), new ItemStack(ACItems.crystal_sulfur, 36));
		if(OreDictionary.getOres("listAllmeatraw", false).size() > 1)
			AbyssalCraftAPI.addMaterialization("listAllmeatraw", new ItemStack(ACItems.crystal_hydrogen, 14), new ItemStack(ACItems.crystal_carbon, 5), new ItemStack(ACItems.crystal_oxygen, 6), new ItemStack(ACItems.crystal_nitrogen));
		else {
			AbyssalCraftAPI.addMaterialization(new ItemStack(Items.BEEF), new ItemStack(ACItems.crystal_hydrogen, 14), new ItemStack(ACItems.crystal_carbon, 5), new ItemStack(ACItems.crystal_oxygen, 6), new ItemStack(ACItems.crystal_nitrogen));
			AbyssalCraftAPI.addMaterialization(new ItemStack(Items.CHICKEN), new ItemStack(ACItems.crystal_hydrogen, 14), new ItemStack(ACItems.crystal_carbon, 5), new ItemStack(ACItems.crystal_oxygen, 6), new ItemStack(ACItems.crystal_nitrogen));
			AbyssalCraftAPI.addMaterialization(new ItemStack(Items.PORKCHOP), new ItemStack(ACItems.crystal_hydrogen, 14), new ItemStack(ACItems.crystal_carbon, 5), new ItemStack(ACItems.crystal_oxygen, 6), new ItemStack(ACItems.crystal_nitrogen));
			AbyssalCraftAPI.addMaterialization(new ItemStack(Items.MUTTON), new ItemStack(ACItems.crystal_hydrogen, 14), new ItemStack(ACItems.crystal_carbon, 5), new ItemStack(ACItems.crystal_oxygen, 6), new ItemStack(ACItems.crystal_nitrogen));
			AbyssalCraftAPI.addMaterialization(new ItemStack(Items.RABBIT), new ItemStack(ACItems.crystal_hydrogen, 14), new ItemStack(ACItems.crystal_carbon, 5), new ItemStack(ACItems.crystal_oxygen, 6), new ItemStack(ACItems.crystal_nitrogen));
			AbyssalCraftAPI.addMaterialization(new ItemStack(ACItems.generic_meat), new ItemStack(ACItems.crystal_hydrogen, 14), new ItemStack(ACItems.crystal_carbon, 5), new ItemStack(ACItems.crystal_oxygen, 6), new ItemStack(ACItems.crystal_nitrogen));
		}
	}

	private static void addRitualRecipes(){
		//Overworld progression
		Object[] tgofferings = new Object[]{new ItemStack(Items.DIAMOND), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.BLAZE_POWDER),
				new ItemStack(Items.DIAMOND), new ItemStack(Items.BLAZE_POWDER), new ItemStack(Items.ENDER_PEARL), new ItemStack(Items.BLAZE_POWDER)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("transmutationGem", 0, 300F, new ItemStack(ACItems.transmutation_gem), new ItemStack(ACItems.coralium_pearl), tgofferings));
		Object[] ocofferings = new Object[]{new ItemStack(Items.REDSTONE), new ItemStack(ACItems.shard_of_oblivion), new ItemStack(Items.REDSTONE), new ItemStack(ACItems.shard_of_oblivion),
				new ItemStack(Items.REDSTONE), new ItemStack(ACItems.shard_of_oblivion), new ItemStack(Items.REDSTONE), new ItemStack(ACItems.shard_of_oblivion)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("oblivionCatalyst", 0, OreDictionary.WILDCARD_VALUE, 5000F, true, new ItemStack(ACItems.oblivion_catalyst), new ItemStack(Items.ENDER_EYE), ocofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconPortalRitual());

		//Abyssal Wasteland progression
		Object[] asorahofferings = new Object[]{new ItemStack(Items.GOLD_INGOT), new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.GOLD_INGOT), ACItems.liquid_coralium_bucket_stack,
				new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.ENCHANTING_TABLE), new ItemStack(Items.GOLD_INGOT)};
		RitualRegistry.instance().registerRitual(new NecronomiconSummonRitual("summonAsorah", 1, ACLib.abyssal_wasteland_id, 1000F, EntityDragonBoss.class, asorahofferings).setNBTSensitive());
		Object[] gk2offerings = new Object[]{new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), null, new ItemStack(ACBlocks.dreadlands_infused_powerstone), null, new ItemStack(ACItems.eye_of_the_abyss)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("asorahGatewayKey", 1, ACLib.abyssal_wasteland_id, 10000F, new ItemStack(ACItems.dreaded_gateway_key), new ItemStack(ACItems.gateway_key), gk2offerings));

		//Dreadlands Progression
		Object[] dreadaltarbofferings = new Object[]{ACItems.dread_cloth, Items.BONE, ACItems.dreadium_ingot, ACBlocks.dreadstone, ACItems.dreaded_gateway_key, ACBlocks.dreadstone, ACItems.dreadium_ingot, Items.BONE};
		RitualRegistry.instance().registerRitual(new NecronomiconCreationRitual("altarOfChagarothBottom", 2, ACLib.dreadlands_id, 20000F, true, new ItemStack(ACBlocks.chagaroth_altar_bottom), dreadaltarbofferings));
		Object[] dreadaltartofferings = new Object[]{Items.BUCKET, "stickWood", ACItems.dread_cloth, ACItems.dreadium_ingot, ACItems.dread_cloth, ACItems.dreadium_ingot, ACItems.dread_cloth, "stickWood"};
		RitualRegistry.instance().registerRitual(new NecronomiconCreationRitual("altarOfChagarothTop", 2, ACLib.dreadlands_id, 20000F, true, new ItemStack(ACBlocks.chagaroth_altar_top), dreadaltartofferings));
		Object[] cageofferings = new Object[]{Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("interdimensionalcage", 2, 1000F, new ItemStack(ACItems.interdimensional_cage), ACItems.shard_of_oblivion, cageofferings));

		//Omothol progression
		RitualRegistry.instance().registerRitual(new NecronomiconRespawnJzaharRitual());

		//Everything else
		Object[] depthsofferings = new Object[]{new ItemStack(ACItems.coralium_gem_cluster_9), new ItemStack(ACItems.coralium_gem_cluster_9), ACItems.liquid_coralium_bucket_stack,
				new ItemStack(Blocks.VINE), new ItemStack(Blocks.WATERLILY), new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.coralium_plagued_flesh)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsHelmet", 1, ACLib.abyssal_wasteland_id, 300F, new ItemStack(ACItems.depths_helmet), new ItemStack(ACItems.refined_coralium_helmet), depthsofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsChestplate", 1, ACLib.abyssal_wasteland_id, 300F, new ItemStack(ACItems.depths_chestplate), new ItemStack(ACItems.refined_coralium_chestplate), depthsofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsLeggings", 1, ACLib.abyssal_wasteland_id, 300F, new ItemStack(ACItems.depths_leggings), new ItemStack(ACItems.refined_coralium_leggings), depthsofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("depthsBoots", 1, ACLib.abyssal_wasteland_id, 300F, new ItemStack(ACItems.depths_boots), new ItemStack(ACItems.refined_coralium_boots), depthsofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(new NecronomiconBreedingRitual());
		Object[] sacthothofferings = new Object[]{new ItemStack(ACItems.oblivion_catalyst), new ItemStack(Blocks.OBSIDIAN), ACItems.liquid_coralium_bucket_stack, new ItemStack(Blocks.OBSIDIAN),
				ACItems.liquid_antimatter_bucket_stack, new ItemStack(Blocks.OBSIDIAN), new ItemStack(ACBlocks.odb_core), new ItemStack(Blocks.OBSIDIAN)};
		RitualRegistry.instance().registerRitual(new NecronomiconSummonRitual("summonSacthoth", 1, 1000F, EntitySacthoth.class, sacthothofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(new NecronomiconDreadSpawnRitual());
		Object[] coraoeofferings = new Object[]{new ItemStack(ACItems.coralium_plagued_flesh), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.coralium_plagued_flesh),
				PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.coralium_plagued_flesh), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER),
				new ItemStack(ACItems.coralium_plagued_flesh), new ItemStack(Items.GUNPOWDER)};
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("corPotionAoE", 1, 300F, AbyssalCraftAPI.coralium_plague, coraoeofferings));
		Object[] dreaoeofferings = new Object[]{new ItemStack(ACItems.dread_fragment), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.dread_fragment),
				PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.dread_fragment), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER),
				new ItemStack(ACItems.dread_fragment), new ItemStack(Items.GUNPOWDER)};
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("drePotionAoE", 2, 300F, AbyssalCraftAPI.dread_plague, dreaoeofferings));
		Object[] antiaoeofferings = new Object[]{new ItemStack(ACItems.rotten_anti_flesh), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.rotten_anti_flesh),
				PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.rotten_anti_flesh), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER),
				new ItemStack(ACItems.rotten_anti_flesh), new ItemStack(Items.GUNPOWDER)};
		RitualRegistry.instance().registerRitual(new NecronomiconPotionAoERitual("antiPotionAoE", 0, 300F, AbyssalCraftAPI.antimatter_potion, antiaoeofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconResurrectionRitual());
		Object[] facebookofferings = new Object[] {new ItemStack(ACItems.crystal_carbon), new ItemStack(ACItems.crystal_hydrogen), new ItemStack(ACItems.crystal_nitrogen), new ItemStack(ACItems.crystal_oxygen), new ItemStack(ACItems.crystal_phosphorus),
				new ItemStack(ACItems.crystal_sulfur), Items.FEATHER, Items.DYE};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("facebook", 2, 2000F, new ItemStack(ACItems.book_of_many_faces), new ItemStack(Items.BOOK), facebookofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconCleansingRitual());
		RitualRegistry.instance().registerRitual(new NecronomiconCorruptionRitual());
		RitualRegistry.instance().registerRitual(new NecronomiconCuringRitual());
		RitualRegistry.instance().registerRitual(new NecronomiconMassEnchantRitual());
		Object[] spiritTabletOfferings = new Object[] {null, new ItemStack[] {new ItemStack(ACItems.configurator_shard_0), new ItemStack(ACItems.configurator_shard_1), new ItemStack(ACItems.configurator_shard_2), new ItemStack(ACItems.configurator_shard_3)}, null,
				new ItemStack[] {new ItemStack(ACItems.configurator_shard_0), new ItemStack(ACItems.configurator_shard_1), new ItemStack(ACItems.configurator_shard_2), new ItemStack(ACItems.configurator_shard_3)},
				null, new ItemStack[] {new ItemStack(ACItems.configurator_shard_0), new ItemStack(ACItems.configurator_shard_1), new ItemStack(ACItems.configurator_shard_2), new ItemStack(ACItems.configurator_shard_3)}, null,
				new ItemStack[] {new ItemStack(ACItems.configurator_shard_0), new ItemStack(ACItems.configurator_shard_1), new ItemStack(ACItems.configurator_shard_2), new ItemStack(ACItems.configurator_shard_3)}};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("spiritTablet", 3, OreDictionary.WILDCARD_VALUE, 5000F, new ItemStack(ACItems.configurator), new ItemStack(ACItems.life_crystal), spiritTabletOfferings));
		Object[] cthulhuofferings = new Object[]{new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.overworld_shoggoth_flesh),
				new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cthulhuStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.cthulhu_statue), new ItemStack(ACBlocks.monolith_stone), cthulhuofferings));
		Object[] hasturofferings = new Object[]{new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.abyssal_shoggoth_flesh),
				new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hasturStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.monolith_stone), hasturofferings));
		Object[] jzaharofferings = new Object[]{ACItems.eldritch_scale, ACItems.eldritch_scale, ACItems.eldritch_scale, ACItems.eldritch_scale, ACItems.eldritch_scale,
				new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.monolith_stone), jzaharofferings));
		Object[] azathothofferings = new Object[]{new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.dreaded_shoggoth_flesh),
				new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("azathothStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.monolith_stone), azathothofferings));
		Object[] nyarlathotepofferings = new Object[]{new ItemStack(ACItems.dreaded_shoggoth_flesh), new ItemStack(ACItems.dreaded_shoggoth_flesh), new ItemStack(ACItems.dreaded_shoggoth_flesh),
				new ItemStack(ACItems.dreaded_shoggoth_flesh), new ItemStack(ACItems.dreaded_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nyarlathotepStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.monolith_stone), nyarlathotepofferings));
		Object[] yogsothothofferings = new Object[]{new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.omothol_shoggoth_flesh),
				new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yogsothothStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.yog_sothoth_statue), new ItemStack(ACBlocks.monolith_stone), yogsothothofferings));
		Object[] shubniggurathofferings = new Object[]{new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.shadow_shoggoth_flesh),
				new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("shubniggurathStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.shub_niggurath_statue), new ItemStack(ACBlocks.monolith_stone), shubniggurathofferings));
		Object[] psdlofferings = new Object[]{new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("powerStone", 4, ACLib.dreadlands_id, 5000F, new ItemStack(ACBlocks.dreadlands_infused_powerstone), ACBlocks.coralium_infused_stone, psdlofferings));
		Object[] ethofferings = new Object[]{ACItems.ethaxium_brick, ACItems.ethaxium_brick, ACItems.life_crystal, ACItems.ethaxium_brick, ACItems.ethaxium_brick};
		RitualRegistry.instance().registerRitual(new NecronomiconPurgingRitual());
		RitualRegistry.instance().registerRitual(new NecronomiconCreationRitual("ethaxiumIngot", 3, ACLib.omothol_id, 1000F, new ItemStack(ACItems.ethaxium_ingot), ethofferings));
		Object[] dreadofferings = new Object[]{new ItemStack(ACItems.dreadlands_essence), ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite,
				ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite, ACItems.dreaded_shard_of_abyssalnite};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadHelmet", 2, ACLib.dreadlands_id, 500F, new ItemStack(ACItems.dreaded_abyssalnite_helmet), ACItems.abyssalnite_helmet, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadChestplate", 2, ACLib.dreadlands_id, 500F, new ItemStack(ACItems.dreaded_abyssalnite_chestplate), ACItems.abyssalnite_chestplate, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadLeggings", 2, ACLib.dreadlands_id, 500F, new ItemStack(ACItems.dreaded_abyssalnite_leggings), ACItems.abyssalnite_leggings, dreadofferings));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("dreadBoots", 2, ACLib.dreadlands_id, 500F, new ItemStack(ACItems.dreaded_abyssalnite_boots), ACItems.abyssalnite_boots, dreadofferings));
		Object[] rcoffers = new Object[]{ACItems.shadow_fragment, Items.ARROW, ACItems.shadow_fragment, Items.ARROW, ACItems.shadow_fragment, Items.ARROW, ACItems.shadow_fragment, Items.ARROW};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("rangeCharm", 0, 100F, new ItemStack(ACItems.range_ritual_charm), new ItemStack(ACItems.ritual_charm), rcoffers));
		Object[] dcoffers = new Object[]{ACItems.shadow_fragment, Items.REDSTONE, ACItems.shadow_fragment, Items.REDSTONE, ACItems.shadow_fragment, Items.REDSTONE, ACItems.shadow_fragment,
				Items.REDSTONE};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("durationCharm", 0, 100F, new ItemStack(ACItems.duration_ritual_charm), new ItemStack(ACItems.ritual_charm), dcoffers));
		Object[] pcoffers = new Object[]{ACItems.shadow_fragment, Items.GLOWSTONE_DUST, ACItems.shadow_fragment, Items.GLOWSTONE_DUST, ACItems.shadow_fragment, Items.GLOWSTONE_DUST,
				ACItems.shadow_fragment, Items.GLOWSTONE_DUST};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("powerCharm", 0, 100F, new ItemStack(ACItems.power_ritual_charm), new ItemStack(ACItems.ritual_charm), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("crangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_cthulhu_charm), new ItemStack(ACItems.cthulhu_charm), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cdurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_cthulhu_charm), new ItemStack(ACItems.cthulhu_charm), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cpowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_cthulhu_charm), new ItemStack(ACItems.cthulhu_charm), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hrangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_hastur_charm), new ItemStack(ACItems.hastur_charm), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hdurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_hastur_charm), new ItemStack(ACItems.hastur_charm), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hpowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_hastur_charm), new ItemStack(ACItems.hastur_charm), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jrangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_jzahar_charm), new ItemStack(ACItems.jzahar_charm), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jdurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_jzahar_charm), new ItemStack(ACItems.jzahar_charm), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jpowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_jzahar_charm), new ItemStack(ACItems.jzahar_charm), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("arangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_azathoth_charm), new ItemStack(ACItems.azathoth_charm), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("adurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_azathoth_charm), new ItemStack(ACItems.azathoth_charm), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("apowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_azathoth_charm), new ItemStack(ACItems.azathoth_charm), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nrangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_nyarlathotep_charm), new ItemStack(ACItems.nyarlathotep_charm), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ndurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_nyarlathotep_charm), new ItemStack(ACItems.nyarlathotep_charm), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("npowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_nyarlathotep_charm), new ItemStack(ACItems.nyarlathotep_charm), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yrangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_yog_sothoth_charm), new ItemStack(ACItems.yog_sothoth_charm), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ydurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_yog_sothoth_charm), new ItemStack(ACItems.yog_sothoth_charm), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ypowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_yog_sothoth_charm), new ItemStack(ACItems.yog_sothoth_charm), pcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("srangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_shub_niggurath_charm), new ItemStack(ACItems.shub_niggurath_charm), rcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("sdurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_shub_niggurath_charm), new ItemStack(ACItems.shub_niggurath_charm), dcoffers));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("spowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_shub_niggurath_charm), new ItemStack(ACItems.shub_niggurath_charm), pcoffers));
		Object[] ccoffers = new Object[]{ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin,
				ACItems.cthulhu_engraved_coin, ACItems.cthulhu_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("cthulhuCharm", 4, 2000F, new ItemStack(ACItems.cthulhu_charm), new ItemStack(ACItems.ritual_charm), ccoffers));
		Object[] hcoffers = new Object[]{ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin,
				ACItems.hastur_engraved_coin, ACItems.hastur_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("hasturCharm", 4, 2000F, new ItemStack(ACItems.hastur_charm), new ItemStack(ACItems.ritual_charm), hcoffers));
		Object[] jcoffers = new Object[]{ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin,
				ACItems.jzahar_engraved_coin, ACItems.jzahar_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharCharm", 4, 2000F, new ItemStack(ACItems.jzahar_charm), new ItemStack(ACItems.ritual_charm), jcoffers));
		Object[] acoffers = new Object[]{ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin,
				ACItems.azathoth_engraved_coin, ACItems.azathoth_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("azathothCharm", 4, 2000F, new ItemStack(ACItems.azathoth_charm), new ItemStack(ACItems.ritual_charm), acoffers));
		Object[] ncoffers = new Object[]{ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin,
				ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin, ACItems.nyarlathotep_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("nyarlathotepCharm", 4, 2000F, new ItemStack(ACItems.nyarlathotep_charm), new ItemStack(ACItems.ritual_charm), ncoffers));
		Object[] ycoffers = new Object[]{ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin,
				ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin, ACItems.yog_sothoth_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("yogsothothCharm", 4, 2000F, new ItemStack(ACItems.yog_sothoth_charm), new ItemStack(ACItems.ritual_charm), ycoffers));
		Object[] scoffers = new Object[]{ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin,
				ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin, ACItems.shub_niggurath_engraved_coin};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("shubniggurathCharm", 4, 2000F, new ItemStack(ACItems.shub_niggurath_charm), new ItemStack(ACItems.ritual_charm), scoffers));
		Object[] owoffers = new Object[]{ACItems.shadow_shard, Blocks.COBBLESTONE, ACItems.coralium_gem, new ItemStack(ACBlocks.darkstone_cobblestone), ACItems.shadow_shard, Blocks.COBBLESTONE,
				ACItems.coralium_gem, new ItemStack(ACBlocks.darkstone_cobblestone)};
		Object[] awoffers = new Object[]{ACItems.shadow_shard, new ItemStack(ACBlocks.abyssal_stone_brick, 1, 0), ACItems.coralium_gem, new ItemStack(ACBlocks.coralium_stone_brick, 1, 0), ACItems.shadow_shard, new ItemStack(ACBlocks.abyssal_stone_brick, 1, 0),
				ACItems.coralium_gem, new ItemStack(ACBlocks.coralium_stone_brick, 1, 0)};
		Object[] dloffers = new Object[]{ACItems.shadow_shard, ACBlocks.dreadstone_brick, ACItems.coralium_gem, new ItemStack(ACBlocks.abyssalnite_stone_brick, 1, 0), ACItems.shadow_shard, new ItemStack(ACBlocks.dreadstone_brick, 1, 0),
				ACItems.coralium_gem, new ItemStack(ACBlocks.abyssalnite_stone_brick, 1, 0)};
		Object[] omtoffers = new Object[]{ACItems.shadow_shard, new ItemStack(ACBlocks.ethaxium_brick, 1, 0), ACItems.coralium_gem, new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0),
				ACItems.shadow_shard, new ItemStack(ACBlocks.ethaxium_brick, 1, 0), ACItems.coralium_gem, new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_energy_pedestal), ACBlocks.energy_pedestal, owoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_energy_pedestal), new ItemStack(ACBlocks.overworld_energy_pedestal), awoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_energy_pedestal), new ItemStack(ACBlocks.abyssal_wasteland_energy_pedestal), dloffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("epOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_energy_pedestal), new ItemStack(ACBlocks.dreadlands_energy_pedestal), omtoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_sacrificial_altar), ACBlocks.sacrificial_altar, owoffers).setTags("PotEnergy", "CollectionLimit", "CoolDown"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_sacrificial_altar), new ItemStack(ACBlocks.overworld_sacrificial_altar), awoffers).setTags("PotEnergy", "CollectionLimit", "CoolDown"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_sacrificial_altar), new ItemStack(ACBlocks.abyssal_wasteland_sacrificial_altar), dloffers).setTags("PotEnergy", "CollectionLimit", "CoolDown"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("saOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_sacrificial_altar), new ItemStack(ACBlocks.dreadlands_sacrificial_altar), omtoffers).setTags("PotEnergy", "CollectionLimit", "CoolDown"));
		Object[] staffofferings = new Object[]{new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.omothol_essence), ACItems.eldritch_scale, ACItems.ethaxium_ingot,
				ACItems.rlyehian_gateway_key, ACItems.ethaxium_ingot, ACItems.eldritch_scale, new ItemStack(ACItems.abyssal_wasteland_essence)};
		String[] tags = {"energyShadow", "energyAbyssal", "energyDread", "energyOmothol", "ench"};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("jzaharStaff", 4, ACLib.omothol_id, 15000F, new ItemStack(ACItems.staff_of_the_gatekeeper), new ItemStack(ACItems.omothol_staff_of_rending), staffofferings).setTags(tags));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("silverKey", 4, ACLib.omothol_id, 20000F, true, new ItemStack(ACItems.silver_key), new ItemStack(ACItems.rlyehian_gateway_key),
				new Object[] {ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot}));
		RitualRegistry.instance().registerRitual(new NecronomiconWeatherRitual());
		Object[] containerofferings = new Object[]{ACBlocks.energy_collector, ACItems.shadow_shard, ACBlocks.energy_collector, ACItems.shadow_shard, ACBlocks.energy_collector,
				ACItems.shadow_shard, ACBlocks.energy_collector, ACItems.shadow_shard};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("energyContainer", 0, 100F, new ItemStack(ACBlocks.energy_container), ACBlocks.energy_pedestal, containerofferings).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ecolOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_energy_collector), ACBlocks.energy_collector, owoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ecolAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_energy_collector), new ItemStack(ACBlocks.overworld_energy_collector), awoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ecolDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_energy_collector), new ItemStack(ACBlocks.abyssal_wasteland_energy_collector), dloffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("ecolOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_energy_collector), new ItemStack(ACBlocks.dreadlands_energy_collector), omtoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("erOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_energy_relay), ACBlocks.energy_relay, owoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("erAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_energy_relay), new ItemStack(ACBlocks.overworld_energy_relay), awoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("erDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_energy_relay), new ItemStack(ACBlocks.abyssal_wasteland_energy_relay), dloffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("erOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_energy_relay), new ItemStack(ACBlocks.dreadlands_energy_relay), omtoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("econOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_energy_container), ACBlocks.energy_container, owoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("econAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_energy_container), new ItemStack(ACBlocks.overworld_energy_container), awoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("econDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_energy_container), new ItemStack(ACBlocks.abyssal_wasteland_energy_container), dloffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("econOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_energy_container), new ItemStack(ACBlocks.dreadlands_energy_container), omtoffers).setTags("PotEnergy"));
		Object[] sorawofferings = new Object[]{ACItems.shadow_gem, new ItemStack(ACBlocks.abyssal_stone), ACItems.coralium_plagued_flesh, new ItemStack(ACBlocks.abyssal_stone), ACItems.coralium_plagued_flesh,
				new ItemStack(ACBlocks.abyssal_stone), ACItems.coralium_plagued_flesh, new ItemStack(ACBlocks.abyssal_stone)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("sorAWupgrade", 1, ACLib.abyssal_wasteland_id, 1000F, new ItemStack(ACItems.abyssal_wasteland_staff_of_rending), new ItemStack(ACItems.staff_of_rending), sorawofferings).setTags(tags));
		Object[] sordlofferings = new Object[]{ACItems.shadow_gem, new ItemStack(ACBlocks.dreadstone), ACItems.dread_fragment, new ItemStack(ACBlocks.dreadstone), ACItems.dread_fragment,
				new ItemStack(ACBlocks.dreadstone), ACItems.dread_fragment, new ItemStack(ACBlocks.dreadstone)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("sorDLupgrade", 2, ACLib.dreadlands_id, 2000F, new ItemStack(ACItems.dreadlands_staff_of_rending), new ItemStack(ACItems.abyssal_wasteland_staff_of_rending), sordlofferings).setTags(tags));
		Object[] soromtofferings = new Object[]{ACItems.shadow_gem, new ItemStack(ACBlocks.omothol_stone), ACItems.omothol_flesh, new ItemStack(ACBlocks.omothol_stone), ACItems.omothol_flesh,
				new ItemStack(ACBlocks.omothol_stone), ACItems.omothol_flesh, new ItemStack(ACBlocks.omothol_stone)};
		RitualRegistry.instance().registerRitual(new NecronomiconInfusionRitual("sorOMTupgrade", 3, ACLib.omothol_id, 3000F, new ItemStack(ACItems.omothol_staff_of_rending), new ItemStack(ACItems.dreadlands_staff_of_rending), soromtofferings).setTags(tags));
		RitualRegistry.instance().registerRitual(new NecronomiconHouseRitual());
		Object[] basicscrollofferings = {Items.BOOK, null, Items.BOOK, null, Items.BOOK, null, Items.BOOK};
		RitualRegistry.instance().registerRitual(new NecronomiconCreationRitual("basicScroll", 0, 100.0F, new ItemStack(ACItems.basic_scroll), basicscrollofferings));
		Object[] lesserscrollofferings = {Items.BOOK, null, ACBlocks.wastelands_thorn, null, Items.BOOK, null, ACBlocks.luminous_thistle};
		RitualRegistry.instance().registerRitual(new NecronomiconCreationRitual("lesserScroll", 1, 1000F, new ItemStack(ACItems.lesser_scroll), lesserscrollofferings));
		Object[] moderatescrollofferings = {Items.BOOK, null, ACItems.dread_fragment, null, Items.BOOK, null, ACItems.dreaded_shard_of_abyssalnite};
		RitualRegistry.instance().registerRitual(new NecronomiconCreationRitual("moderateScroll", 2, 2000F, new ItemStack(ACItems.moderate_scroll), moderatescrollofferings));
	}

	private static void addDisruptions(){
		DisruptionHandler.instance().registerDisruption(new DisruptionLightning());
		DisruptionHandler.instance().registerDisruption(new DisruptionFire());
		DisruptionHandler.instance().registerDisruption(new DisruptionSpawn("spawnShoggoth", null, EntityLesserShoggoth.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("poisonPotion", null, MobEffects.POISON));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("slownessPotion", null, MobEffects.SLOWNESS));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("weaknessPotion", null, MobEffects.WEAKNESS));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("witherPotion", null, MobEffects.WITHER));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotion("coraliumPotion", null, AbyssalCraftAPI.coralium_plague));
		DisruptionHandler.instance().registerDisruption(new DisruptionPotentialEnergy());
		DisruptionHandler.instance().registerDisruption(new DisruptionFreeze());
		DisruptionHandler.instance().registerDisruption(new DisruptionSwarm("swarmShadow", null, EntityShadowCreature.class, EntityShadowMonster.class, EntityShadowBeast.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionFireRain());
		DisruptionHandler.instance().registerDisruption(new DisruptionDisplaceEntities());
		//		DisruptionHandler.instance().registerDisruption(new DisruptionMonolith()); //uncomment at some point when there's a lot more disruptions
		DisruptionHandler.instance().registerDisruption(new DisruptionTeleportRandomly());
		DisruptionHandler.instance().registerDisruption(new DisruptionDrainNearbyPE());
		DisruptionHandler.instance().registerDisruption(new DisruptionSwarm("swarmSheep", DeityType.SHUBNIGGURATH, EntityEvilSheep.class, EntitySheep.class));
		DisruptionHandler.instance().registerDisruption(new DisruptionAnimalCorruption());
		//		DisruptionHandler.instance().registerDisruption(new DisruptionCorruption());
		DisruptionHandler.instance().registerDisruption(new DisruptionOoze());
		DisruptionHandler.instance().registerDisruption(new DisruptionRandomSwarm());
		DisruptionHandler.instance().registerDisruption(new DisruptionRandomSpawn());
		DisruptionHandler.instance().registerDisruption(new DisruptionSpawn("spawnShubOffspring", DeityType.SHUBNIGGURATH, EntityShubOffspring.class));
	}

	private static void addSpells(){
		if(ACConfig.entropy_spell)
			SpellRegistry.instance().registerSpell(new EntropySpell());
		if(ACConfig.life_drain_spell)
			SpellRegistry.instance().registerSpell(new LifeDrainSpell());
		if(ACConfig.mining_spell)
			SpellRegistry.instance().registerSpell(new MiningSpell());
		if(ACConfig.grasp_of_cthulhu_spell)
			SpellRegistry.instance().registerSpell(new GraspofCthulhuSpell());
		if(ACConfig.invisibility_spell)
			SpellRegistry.instance().registerSpell(new InvisibilitySpell());
		if(ACConfig.detachment_spell)
			SpellRegistry.instance().registerSpell(new DetachmentSpell());
		if(ACConfig.steal_vigor_spell)
			SpellRegistry.instance().registerSpell(new StealVigorSpell());
		if(ACConfig.sirens_song_spell)
			SpellRegistry.instance().registerSpell(new SirensSongSpell());
		if(ACConfig.undeath_to_dust_spell)
			SpellRegistry.instance().registerSpell(new UndeathtoDustSpell());
		if(ACConfig.ooze_removal_spell)
			SpellRegistry.instance().registerSpell(new OozeRemovalSpell());
		if(ACConfig.teleport_hostile_spell)
			SpellRegistry.instance().registerSpell(new TeleportHostilesSpell());
		if(ACConfig.display_routes_spell)
			SpellRegistry.instance().registerSpell(new DisplayRoutesSpell());
		if(ACConfig.toggle_state_spell)
			SpellRegistry.instance().registerSpell(new ToggleStateSpell());
		if(ACConfig.floating_spell)
			SpellRegistry.instance().registerSpell(new FloatingSpell());
		if(ACConfig.teleport_home_spell)
			SpellRegistry.instance().registerSpell(new TeleportHomeSpell());
	}

	private static void addRendings() {
		RendingRegistry.instance().registerRending(new Rending("Abyssal", 100, new ItemStack(ACItems.abyssal_wasteland_essence),
				e -> e.world.provider.getDimension() == ACLib.abyssal_wasteland_id && EntityUtil.isCoraliumPlagueCarrier(e) && e.isNonBoss(),
				"ac.rending.essence_aw", ACLib.abyssal_wasteland_id));
		RendingRegistry.instance().registerRending(new Rending("Dread", 100, new ItemStack(ACItems.dreadlands_essence),
				e -> e.world.provider.getDimension() == ACLib.dreadlands_id && EntityUtil.isDreadPlagueCarrier(e) && e.isNonBoss(),
				"ac.rending.essence_dl", ACLib.dreadlands_id));
		RendingRegistry.instance().registerRending(new Rending("Omothol", 100, new ItemStack(ACItems.omothol_essence),
				e -> e.world.provider.getDimension() == ACLib.omothol_id && e instanceof IOmotholEntity && e.getCreatureAttribute() != AbyssalCraftAPI.SHADOW && e.isNonBoss(),
				"ac.rending.essence_omt", ACLib.omothol_id));
		RendingRegistry.instance().registerRending(new Rending("Shadow", 200, new ItemStack(ACItems.shadow_gem),
				e -> e.getCreatureAttribute() == AbyssalCraftAPI.SHADOW && e.isNonBoss(),
				"ac.rending.shadowgem", OreDictionary.WILDCARD_VALUE));
	}

	private static void addArmorSmelting(Item helmet, Item chestplate, Item pants, Item boots, ItemStack nugget){
		GameRegistry.addSmelting(helmet, nugget, 1F);
		GameRegistry.addSmelting(chestplate, nugget, 1F);
		GameRegistry.addSmelting(pants, nugget, 1F);
		GameRegistry.addSmelting(boots, nugget, 1F);
	}
}