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
package com.shinoow.abyssalcraft.common;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.*;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.rending.Rending;
import com.shinoow.abyssalcraft.api.rending.RendingRegistry;
import com.shinoow.abyssalcraft.api.ritual.*;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;
import com.shinoow.abyssalcraft.api.spell.Spells;
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
		GameRegistry.addSmelting(new ItemStack(ACBlocks.ethaxium), new ItemStack(ACItems.ethaxium_brick), 0.2F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.ethaxium_brick, 1, 0), new ItemStack(ACBlocks.cracked_ethaxium_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0), new ItemStack(ACBlocks.cracked_dark_ethaxium_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.darkstone_brick, 1, 0), new ItemStack(ACBlocks.cracked_darkstone_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.abyssal_stone_brick, 1, 0), new ItemStack(ACBlocks.cracked_abyssal_stone_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.dreadstone_brick, 1, 0), new ItemStack(ACBlocks.cracked_dreadstone_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.elysian_stone_brick, 1, 0), new ItemStack(ACBlocks.cracked_elysian_stone_brick, 1), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.coralium_stone_brick, 1, 0), new ItemStack(ACBlocks.cracked_coralium_stone_brick, 1), 0.1F);
		GameRegistry.addSmelting(ACBlocks.abyssal_sand, new ItemStack(ACBlocks.abyssal_sand_glass), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.abyssal_cobblestone), new ItemStack(ACBlocks.abyssal_stone), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.dreadstone_cobblestone), new ItemStack(ACBlocks.dreadstone), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.elysian_cobblestone), new ItemStack(ACBlocks.elysian_stone), 0.1F);
		GameRegistry.addSmelting(new ItemStack(ACBlocks.coralium_cobblestone), new ItemStack(ACBlocks.coralium_stone), 0.1F);
		GameRegistry.addSmelting(ACBlocks.dreadwood_log, new ItemStack(ACItems.charcoal), 1F);
		GameRegistry.addSmelting(new ItemStack(ACItems.generic_meat), new ItemStack(ACItems.cooked_generic_meat), 0.35F);
		GameRegistry.addSmelting(ACBlocks.dead_tree_log, new ItemStack(Items.COAL, 1, 1), 1F);
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
		AbyssalCraftAPI.addCrystallization(new ItemStack(Blocks.PRISMARINE, 1, 0), new ItemStack(ACItems.crystal_shard_silica, 16), new ItemStack(ACItems.crystal_shard_beryl, 27), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(Blocks.PRISMARINE, 1, 1), new ItemStack(ACItems.crystal_silica, 4), new ItemStack(ACItems.crystal_beryl, 4), 0.1F);
		AbyssalCraftAPI.addCrystallization(new ItemStack(Blocks.PRISMARINE, 1, 2), new ItemStack(ACItems.crystal_shard_silica, 32), new ItemStack(ACItems.crystal_shard_beryl, 32), 0.1F);
		AbyssalCraftAPI.addCrystallization(Items.EGG, new ItemStack(ACItems.crystal_shard_calcium, 4), new ItemStack(ACItems.crystal_shard_phosphorus, 4), 0.1F);

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
	}

	private static void addTransmutation(){

		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.darkstone), new ItemStack(Blocks.STONE), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.dreaded_shard_of_abyssalnite, new ItemStack(ACItems.dreadium_ingot), 0.2F);
		AbyssalCraftAPI.addTransmutation(Blocks.STONE, new ItemStack(ACBlocks.darkstone), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.STONEBRICK, new ItemStack(ACBlocks.darkstone_brick), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.darkstone_brick), new ItemStack(Blocks.STONEBRICK), 0.0F);
		AbyssalCraftAPI.addTransmutation(Blocks.COBBLESTONE, new ItemStack(ACBlocks.darkstone_cobblestone), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.darkstone_cobblestone), new ItemStack(Blocks.COBBLESTONE), 0.0F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.coralium_stone_brick), new ItemStack(ACItems.coralium_brick, 4), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.coralium_brick, new ItemStack(ACBlocks.coralium_stone), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.dense_carbon_cluster, new ItemStack(Items.DIAMOND), 0.5F);
		AbyssalCraftAPI.addTransmutation(ACItems.dread_plagued_gateway_key, new ItemStack(ACItems.omothol_forged_gateway_key), 1.0F);

		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.ethaxium_brick), new ItemStack(ACBlocks.ethaxium), 0.0F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_beef, new ItemStack(Items.COOKED_BEEF), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_pork, new ItemStack(Items.COOKED_PORKCHOP), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_chicken, new ItemStack(Items.COOKED_CHICKEN), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_bone, new ItemStack(Items.BONE, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.rotten_anti_flesh, new ItemStack(Items.ROTTEN_FLESH, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_spider_eye, new ItemStack(Items.SPIDER_EYE, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_plagued_flesh, new ItemStack(ACItems.coralium_plagued_flesh, 2), 0.3F);
		AbyssalCraftAPI.addTransmutation(ACItems.anti_plagued_flesh_on_a_bone, new ItemStack(ACItems.coralium_plagued_flesh_on_a_bone, 2), 0.3F);

		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.abyssalnite_crystal_cluster), new ItemStack(ACItems.abyssalnite_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.coralium_crystal_cluster), new ItemStack(ACItems.refined_coralium_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.dreadium_crystal_cluster), new ItemStack(ACItems.dreadium_ingot, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.iron_crystal_cluster), new ItemStack(Items.IRON_INGOT, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.gold_crystal_cluster), new ItemStack(Items.GOLD_INGOT, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.sulfur_crystal_cluster), new ItemStack(ACItems.sulfur, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.blaze_crystal_cluster), new ItemStack(Items.BLAZE_POWDER, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.redstone_crystal_cluster), new ItemStack(Items.REDSTONE, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACBlocks.methane_crystal_cluster), new ItemStack(ACItems.methane, 1), 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalClusterAluminium", "ingotAluminum", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalClusterAluminium", "ingotAluminium", 0.2F);
		AbyssalCraftAPI.addTransmutation("crystalClusterZinc", "ingotZinc", 0.2F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_abyssalnite), new ItemStack(ACItems.abyssalnite_nugget), 0.1F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_coralium), new ItemStack(ACItems.refined_coralium_nugget), 0.1F);
		AbyssalCraftAPI.addTransmutation(new ItemStack(ACItems.crystal_dreadium), new ItemStack(ACItems.dreadium_nugget), 0.1F);
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
		RitualRegistry.instance().registerRitual(Rituals.TRANSMUTATION_GEM = new NecronomiconInfusionRitual("transmutationGem", 0, 300F, new ItemStack(ACItems.transmutation_gem), new ItemStack(ACItems.coralium_pearl), tgofferings));
		Object[] ocofferings = new Object[]{new ItemStack(Items.REDSTONE), new ItemStack(ACItems.shard_of_oblivion), new ItemStack(Items.REDSTONE), new ItemStack(ACItems.shard_of_oblivion),
				new ItemStack(Items.REDSTONE), new ItemStack(ACItems.shard_of_oblivion), new ItemStack(Items.REDSTONE), new ItemStack(ACItems.shard_of_oblivion)};
		RitualRegistry.instance().registerRitual(Rituals.OBLIVION_CATALYST = new NecronomiconInfusionRitual("oblivionCatalyst", 0, OreDictionary.WILDCARD_VALUE, 5000F, true, new ItemStack(ACItems.oblivion_catalyst), new ItemStack(Items.ENDER_EYE), ocofferings));
		RitualRegistry.instance().registerRitual(Rituals.PORTAL = new NecronomiconPortalRitual());

		//Abyssal Wasteland progression
		Object[] asorahofferings = new Object[]{new ItemStack(Items.GOLD_INGOT), new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Items.GOLD_INGOT), ACItems.liquid_coralium_bucket_stack,
				new ItemStack(Items.GOLD_INGOT), new ItemStack(Blocks.ENCHANTING_TABLE), new ItemStack(Items.GOLD_INGOT)};
		RitualRegistry.instance().registerRitual(Rituals.SUMMON_ASORAH = new NecronomiconSummonRitual("summonAsorah", 1, ACLib.abyssal_wasteland_id, 1000F, EntityDragonBoss.class, asorahofferings).setNBTSensitive());
		Object[] gk2offerings = new Object[]{new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), null, new ItemStack(ACBlocks.dreadlands_infused_powerstone), null, new ItemStack(ACItems.eye_of_the_abyss)};
		RitualRegistry.instance().registerRitual(Rituals.DREADLANDS_INFUSED_GATEWAY_KEY = new NecronomiconInfusionRitual("dreadInfusedGatewayKey", 1, ACLib.abyssal_wasteland_id, 10000F, new ItemStack(ACItems.dreadlands_infused_gateway_key), new ItemStack(ACItems.gateway_key), gk2offerings));

		//Dreadlands Progression
		Object[] sealingkeybofferings = new Object[]{ACItems.dread_cloth, ACItems.crystal_abyssalnite, ACItems.crystal_dreadium, ACBlocks.elysian_stone, ACItems.dread_cloth, ACBlocks.elysian_stone, ACItems.crystal_dreadium, ACItems.crystal_abyssalnite};
		RitualRegistry.instance().registerRitual(Rituals.SEALING_KEY = new NecronomiconInfusionRitual("sealingKey", 2, ACLib.dreadlands_id, 20000F, true, new ItemStack(ACItems.sealing_key), ACItems.dreadlands_infused_gateway_key, sealingkeybofferings));
		Object[] cageofferings = new Object[]{Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS, Blocks.IRON_BARS};
		RitualRegistry.instance().registerRitual(Rituals.INTERDIMENSIONAL_CAGE = new NecronomiconInfusionRitual("interdimensionalcage", 2, 1000F, new ItemStack(ACItems.interdimensional_cage), ACItems.shard_of_oblivion, cageofferings));

		//Omothol progression
		RitualRegistry.instance().registerRitual(Rituals.RESPAWN_JZAHAR = new NecronomiconRespawnJzaharRitual());

		//Everything else
		Object[] depthsofferings = new Object[]{new ItemStack(ACItems.coralium_gem_cluster_9), new ItemStack(ACItems.coralium_gem_cluster_9), ACItems.liquid_coralium_bucket_stack,
				new ItemStack(Blocks.VINE), new ItemStack(Blocks.WATERLILY), new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(ACItems.coralium_plagued_flesh)};
		RitualRegistry.instance().registerRitual(Rituals.DEPTHS_HELMET = new NecronomiconInfusionRitual("depthsHelmet", 1, ACLib.abyssal_wasteland_id, 300F, new ItemStack(ACItems.depths_helmet), new ItemStack(ACItems.refined_coralium_helmet), depthsofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(Rituals.DEPTHS_CHESTPLATE = new NecronomiconInfusionRitual("depthsChestplate", 1, ACLib.abyssal_wasteland_id, 300F, new ItemStack(ACItems.depths_chestplate), new ItemStack(ACItems.refined_coralium_chestplate), depthsofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(Rituals.DEPTHS_LEGGINGS = new NecronomiconInfusionRitual("depthsLeggings", 1, ACLib.abyssal_wasteland_id, 300F, new ItemStack(ACItems.depths_leggings), new ItemStack(ACItems.refined_coralium_leggings), depthsofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(Rituals.DEPTHS_BOOTS = new NecronomiconInfusionRitual("depthsBoots", 1, ACLib.abyssal_wasteland_id, 300F, new ItemStack(ACItems.depths_boots), new ItemStack(ACItems.refined_coralium_boots), depthsofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(Rituals.BREEDING = new NecronomiconBreedingRitual());
		Object[] sacthothofferings = new Object[]{new ItemStack(ACItems.oblivion_catalyst), new ItemStack(Blocks.OBSIDIAN), ACItems.liquid_coralium_bucket_stack, new ItemStack(Blocks.OBSIDIAN),
				ACItems.liquid_antimatter_bucket_stack, new ItemStack(Blocks.OBSIDIAN), new ItemStack(ACBlocks.odb_core), new ItemStack(Blocks.OBSIDIAN)};
		RitualRegistry.instance().registerRitual(Rituals.SUMMON_SACTHOTH = new NecronomiconSummonRitual("summonSacthoth", 1, 1000F, EntitySacthoth.class, sacthothofferings).setNBTSensitive());
		RitualRegistry.instance().registerRitual(Rituals.DREAD_SPAWN = new NecronomiconDreadSpawnRitual());
		Object[] coraoeofferings = new Object[]{new ItemStack(ACItems.coralium_plagued_flesh), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.coralium_plagued_flesh),
				PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.coralium_plagued_flesh), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER),
				new ItemStack(ACItems.coralium_plagued_flesh), new ItemStack(Items.GUNPOWDER)};
		RitualRegistry.instance().registerRitual(Rituals.CORALIUM_PLAUGE_AOE = new NecronomiconPotionAoERitual("corPotionAoE", 1, 300F, AbyssalCraftAPI.coralium_plague, coraoeofferings));
		Object[] dreaoeofferings = new Object[]{new ItemStack(ACItems.dread_fragment), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.dread_fragment),
				PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.dread_fragment), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER),
				new ItemStack(ACItems.dread_fragment), new ItemStack(Items.GUNPOWDER)};
		RitualRegistry.instance().registerRitual(Rituals.DREAD_PLAUGE_AOE = new NecronomiconPotionAoERitual("drePotionAoE", 2, 300F, AbyssalCraftAPI.dread_plague, dreaoeofferings));
		Object[] antiaoeofferings = new Object[]{new ItemStack(ACItems.rotten_anti_flesh), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.rotten_anti_flesh),
				PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER), new ItemStack(ACItems.rotten_anti_flesh), PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER),
				new ItemStack(ACItems.rotten_anti_flesh), new ItemStack(Items.GUNPOWDER)};
		RitualRegistry.instance().registerRitual(Rituals.ANTIMATTER_AOE = new NecronomiconPotionAoERitual("antiPotionAoE", 0, 300F, AbyssalCraftAPI.antimatter_potion, antiaoeofferings));
		Object[] psdlofferings = new Object[]{new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.dreadlands_essence)};
		RitualRegistry.instance().registerRitual(Rituals.DREADLANDS_INFUSED_POWERSTONE = new NecronomiconInfusionRitual("powerStone", 2, ACLib.dreadlands_id, 5000F, new ItemStack(ACBlocks.dreadlands_infused_powerstone), ACBlocks.coralium_infused_stone, psdlofferings));
		RitualRegistry.instance().registerRitual(Rituals.RESURRECTION = new NecronomiconResurrectionRitual());
		Object[] facebookofferings = new Object[] {new ItemStack(ACItems.crystal_carbon), new ItemStack(ACItems.crystal_hydrogen), new ItemStack(ACItems.crystal_nitrogen), new ItemStack(ACItems.crystal_oxygen), new ItemStack(ACItems.crystal_phosphorus),
				new ItemStack(ACItems.crystal_sulfur), Items.FEATHER, Items.DYE};
		RitualRegistry.instance().registerRitual(Rituals.BOOK_OF_MANY_FACES = new NecronomiconInfusionRitual("facebook", 2, 2000F, new ItemStack(ACItems.book_of_many_faces), new ItemStack(Items.BOOK), facebookofferings));
		RitualRegistry.instance().registerRitual(Rituals.CLEANSING = new NecronomiconCleansingRitual());
		RitualRegistry.instance().registerRitual(Rituals.CORRUPTION = new NecronomiconCorruptionRitual());
		RitualRegistry.instance().registerRitual(Rituals.INFESTING = new NecronomiconInfestingRitual());
		RitualRegistry.instance().registerRitual(Rituals.CURING = new NecronomiconCuringRitual());
		RitualRegistry.instance().registerRitual(Rituals.MASS_ENCHANTING = new NecronomiconMassEnchantRitual());
		Object[] spiritTabletOfferings = new Object[] {null, new ItemStack[] {new ItemStack(ACItems.spirit_tablet_shard_0), new ItemStack(ACItems.spirit_tablet_shard_1), new ItemStack(ACItems.spirit_tablet_shard_2), new ItemStack(ACItems.spirit_tablet_shard_3)}, null,
				new ItemStack[] {new ItemStack(ACItems.spirit_tablet_shard_0), new ItemStack(ACItems.spirit_tablet_shard_1), new ItemStack(ACItems.spirit_tablet_shard_2), new ItemStack(ACItems.spirit_tablet_shard_3)},
				null, new ItemStack[] {new ItemStack(ACItems.spirit_tablet_shard_0), new ItemStack(ACItems.spirit_tablet_shard_1), new ItemStack(ACItems.spirit_tablet_shard_2), new ItemStack(ACItems.spirit_tablet_shard_3)}, null,
				new ItemStack[] {new ItemStack(ACItems.spirit_tablet_shard_0), new ItemStack(ACItems.spirit_tablet_shard_1), new ItemStack(ACItems.spirit_tablet_shard_2), new ItemStack(ACItems.spirit_tablet_shard_3)}};
		RitualRegistry.instance().registerRitual(Rituals.SPIRIT_TABLET = new NecronomiconInfusionRitual("spiritTablet", 1, OreDictionary.WILDCARD_VALUE, 5000F, new ItemStack(ACItems.spirit_tablet), new ItemStack(ACItems.shadow_gem), spiritTabletOfferings));
		Object[] spiritAltarOfferings= new Object[] {Items.GOLD_INGOT, Items.GOLD_INGOT, Items.GOLD_INGOT, ACBlocks.darkstone_cobblestone, ACBlocks.darkstone_cobblestone, ACBlocks.darkstone_cobblestone, ACBlocks.darkstone_cobblestone, ACBlocks.darkstone_cobblestone};
		RitualRegistry.instance().registerRitual(Rituals.SPIRIT_ALTAR = new NecronomiconInfusionRitual("spiritAltar", 1, OreDictionary.WILDCARD_VALUE, 1000F, true, new ItemStack(ACBlocks.spirit_altar), new ItemStack(ACItems.shadow_gem), spiritAltarOfferings));
		Object[] cthulhuofferings = new Object[]{new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.overworld_shoggoth_flesh),
				new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(Rituals.CTHULHU_STATUE = new NecronomiconInfusionRitual("cthulhuStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.cthulhu_statue), new ItemStack(ACBlocks.monolith_stone), cthulhuofferings));
		Object[] hasturofferings = new Object[]{new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.abyssal_shoggoth_flesh),
				new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(Rituals.HASTUR_STATUE = new NecronomiconInfusionRitual("hasturStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.hastur_statue), new ItemStack(ACBlocks.monolith_stone), hasturofferings));
		Object[] jzaharofferings = new Object[]{ACItems.eldritch_scale, ACItems.eldritch_scale, ACItems.eldritch_scale, ACItems.eldritch_scale, ACItems.eldritch_scale,
				new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(Rituals.JZAHAR_STATUE = new NecronomiconInfusionRitual("jzaharStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.jzahar_statue), new ItemStack(ACBlocks.monolith_stone), jzaharofferings));
		Object[] azathothofferings = new Object[]{new ItemStack(ACItems.overworld_shoggoth_flesh), new ItemStack(ACItems.abyssal_shoggoth_flesh), new ItemStack(ACItems.dreaded_shoggoth_flesh),
				new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(Rituals.AZATHOTH_STATUE = new NecronomiconInfusionRitual("azathothStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.azathoth_statue), new ItemStack(ACBlocks.monolith_stone), azathothofferings));
		Object[] nyarlathotepofferings = new Object[]{new ItemStack(ACItems.dreaded_shoggoth_flesh), new ItemStack(ACItems.dreaded_shoggoth_flesh), new ItemStack(ACItems.dreaded_shoggoth_flesh),
				new ItemStack(ACItems.dreaded_shoggoth_flesh), new ItemStack(ACItems.dreaded_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(Rituals.NYARLATHOTEP_STATUE = new NecronomiconInfusionRitual("nyarlathotepStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.nyarlathotep_statue), new ItemStack(ACBlocks.monolith_stone), nyarlathotepofferings));
		Object[] yogsothothofferings = new Object[]{new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.omothol_shoggoth_flesh),
				new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.omothol_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(Rituals.YOG_SOTHOTH_STATUE = new NecronomiconInfusionRitual("yogsothothStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.yog_sothoth_statue), new ItemStack(ACBlocks.monolith_stone), yogsothothofferings));
		Object[] shubniggurathofferings = new Object[]{new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.shadow_shoggoth_flesh),
				new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.shadow_shoggoth_flesh), new ItemStack(ACItems.abyssal_wasteland_essence), new ItemStack(ACItems.dreadlands_essence),
				new ItemStack(ACItems.omothol_essence)};
		RitualRegistry.instance().registerRitual(Rituals.SHUB_NIGGURATH_STATUE = new NecronomiconInfusionRitual("shubniggurathStatue", 4, OreDictionary.WILDCARD_VALUE, 20000F, true, new ItemStack(ACBlocks.shub_niggurath_statue), new ItemStack(ACBlocks.monolith_stone), shubniggurathofferings));
		Object[] ethofferings = new Object[]{ACItems.ethaxium_brick, null, ACItems.ethaxium_brick, null, ACItems.ethaxium_brick, null, ACItems.ethaxium_brick};
		RitualRegistry.instance().registerRitual(Rituals.PURGING = new NecronomiconPurgingRitual());
		RitualRegistry.instance().registerRitual(Rituals.ETHAXIUM = new NecronomiconTransformationRitual("ethaxium", 3, ACLib.omothol_id, 10000.0F, new ItemStack(Blocks.SOUL_SAND), new ItemStack(ACBlocks.ethaxium)));
		RitualRegistry.instance().registerRitual(Rituals.ETHAXIUM_INGOT = new NecronomiconInfusionRitual("ethaxiumIngot", 3, ACLib.omothol_id, 1000F, new ItemStack(ACItems.ethaxium_ingot), new ItemStack(ACItems.life_crystal), ethofferings));
		RitualRegistry.instance().registerRitual(Rituals.TOKEN_OF_JZAHAR = new NecronomiconInfusionRitual("jzaharCoin", 3, 500.0F, new ItemStack(ACItems.token_of_jzahar), new ItemStack(ACItems.coin), new ItemStack(ACItems.transmutation_gem, 1, OreDictionary.WILDCARD_VALUE)));
		Object[] rcoffers = new Object[]{ACItems.shadow_fragment, Items.ARROW, ACItems.shadow_fragment, Items.ARROW, ACItems.shadow_fragment, Items.ARROW, ACItems.shadow_fragment, Items.ARROW};
		RitualRegistry.instance().registerRitual(Rituals.RANGE_CHARM = new NecronomiconInfusionRitual("rangeCharm", 0, 100F, new ItemStack(ACItems.range_ritual_charm), new ItemStack(ACItems.ritual_charm), rcoffers));
		Object[] dcoffers = new Object[]{ACItems.shadow_fragment, Items.REDSTONE, ACItems.shadow_fragment, Items.REDSTONE, ACItems.shadow_fragment, Items.REDSTONE, ACItems.shadow_fragment,
				Items.REDSTONE};
		RitualRegistry.instance().registerRitual(Rituals.DURATION_CHARM = new NecronomiconInfusionRitual("durationCharm", 0, 100F, new ItemStack(ACItems.duration_ritual_charm), new ItemStack(ACItems.ritual_charm), dcoffers));
		Object[] pcoffers = new Object[]{ACItems.shadow_fragment, Items.GLOWSTONE_DUST, ACItems.shadow_fragment, Items.GLOWSTONE_DUST, ACItems.shadow_fragment, Items.GLOWSTONE_DUST,
				ACItems.shadow_fragment, Items.GLOWSTONE_DUST};
		RitualRegistry.instance().registerRitual(Rituals.POWER_CHARM = new NecronomiconInfusionRitual("powerCharm", 0, 100F, new ItemStack(ACItems.power_ritual_charm), new ItemStack(ACItems.ritual_charm), pcoffers));
		RitualRegistry.instance().registerRitual(Rituals.CTHULHU_RANGE_CHARM = new NecronomiconInfusionRitual("crangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_cthulhu_charm), new ItemStack(ACItems.cthulhu_charm), rcoffers));
		RitualRegistry.instance().registerRitual(Rituals.CTHULHU_DURATION_CHARM = new NecronomiconInfusionRitual("cdurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_cthulhu_charm), new ItemStack(ACItems.cthulhu_charm), dcoffers));
		RitualRegistry.instance().registerRitual(Rituals.CTHULHU_POWER_CHARM = new NecronomiconInfusionRitual("cpowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_cthulhu_charm), new ItemStack(ACItems.cthulhu_charm), pcoffers));
		RitualRegistry.instance().registerRitual(Rituals.HASTUR_RANGE_CHARM = new NecronomiconInfusionRitual("hrangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_hastur_charm), new ItemStack(ACItems.hastur_charm), rcoffers));
		RitualRegistry.instance().registerRitual(Rituals.HASTUR_DURATION_CHARM = new NecronomiconInfusionRitual("hdurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_hastur_charm), new ItemStack(ACItems.hastur_charm), dcoffers));
		RitualRegistry.instance().registerRitual(Rituals.HASTUR_POWER_CHARM = new NecronomiconInfusionRitual("hpowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_hastur_charm), new ItemStack(ACItems.hastur_charm), pcoffers));
		RitualRegistry.instance().registerRitual(Rituals.JZAHAR_RANGE_CHARM = new NecronomiconInfusionRitual("jrangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_jzahar_charm), new ItemStack(ACItems.jzahar_charm), rcoffers));
		RitualRegistry.instance().registerRitual(Rituals.JZAHAR_DURATION_CHARM = new NecronomiconInfusionRitual("jdurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_jzahar_charm), new ItemStack(ACItems.jzahar_charm), dcoffers));
		RitualRegistry.instance().registerRitual(Rituals.JZAHAR_POWER_CHARM = new NecronomiconInfusionRitual("jpowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_jzahar_charm), new ItemStack(ACItems.jzahar_charm), pcoffers));
		RitualRegistry.instance().registerRitual(Rituals.AZATHOTH_RANGE_CHARM = new NecronomiconInfusionRitual("arangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_azathoth_charm), new ItemStack(ACItems.azathoth_charm), rcoffers));
		RitualRegistry.instance().registerRitual(Rituals.AZATHOTH_DURATION_CHARM = new NecronomiconInfusionRitual("adurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_azathoth_charm), new ItemStack(ACItems.azathoth_charm), dcoffers));
		RitualRegistry.instance().registerRitual(Rituals.AZATHOTH_POWER_CHARM = new NecronomiconInfusionRitual("apowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_azathoth_charm), new ItemStack(ACItems.azathoth_charm), pcoffers));
		RitualRegistry.instance().registerRitual(Rituals.NYARLATHOTEP_RANGE_CHARM = new NecronomiconInfusionRitual("nrangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_nyarlathotep_charm), new ItemStack(ACItems.nyarlathotep_charm), rcoffers));
		RitualRegistry.instance().registerRitual(Rituals.NYARLATHOTEP_DURATION_CHARM = new NecronomiconInfusionRitual("ndurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_nyarlathotep_charm), new ItemStack(ACItems.nyarlathotep_charm), dcoffers));
		RitualRegistry.instance().registerRitual(Rituals.NYARLATHOTEP_POWER_CHARM = new NecronomiconInfusionRitual("npowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_nyarlathotep_charm), new ItemStack(ACItems.nyarlathotep_charm), pcoffers));
		RitualRegistry.instance().registerRitual(Rituals.YOG_SOTHOTH_RANGE_CHARM = new NecronomiconInfusionRitual("yrangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_yog_sothoth_charm), new ItemStack(ACItems.yog_sothoth_charm), rcoffers));
		RitualRegistry.instance().registerRitual(Rituals.YOG_SOTHOTH_DURATION_CHARM = new NecronomiconInfusionRitual("ydurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_yog_sothoth_charm), new ItemStack(ACItems.yog_sothoth_charm), dcoffers));
		RitualRegistry.instance().registerRitual(Rituals.YOG_SOTHOTH_POWER_CHARM = new NecronomiconInfusionRitual("ypowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_yog_sothoth_charm), new ItemStack(ACItems.yog_sothoth_charm), pcoffers));
		RitualRegistry.instance().registerRitual(Rituals.SHUB_NIGGURATH_RANGE_CHARM = new NecronomiconInfusionRitual("srangeCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.range_shub_niggurath_charm), new ItemStack(ACItems.shub_niggurath_charm), rcoffers));
		RitualRegistry.instance().registerRitual(Rituals.SHUB_NIGGURATH_DURATION_CHARM = new NecronomiconInfusionRitual("sdurationCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.duration_shub_niggurath_charm), new ItemStack(ACItems.shub_niggurath_charm), dcoffers));
		RitualRegistry.instance().registerRitual(Rituals.SHUB_NIGGURATH_POWER_CHARM = new NecronomiconInfusionRitual("spowerCharm", 3, ACLib.omothol_id, 200F, new ItemStack(ACItems.power_shub_niggurath_charm), new ItemStack(ACItems.shub_niggurath_charm), pcoffers));
		Object[] ccoffers = new Object[]{new ItemStack(Items.DYE, 1, 6), new ItemStack(Items.DYE, 1, 6), new ItemStack(Items.DYE, 1, 6), new ItemStack(Items.DYE, 1, 6), new ItemStack(Items.DYE, 1, 6), new ItemStack(Items.DYE, 1, 6),
				new ItemStack(Items.DYE, 1, 6), new ItemStack(Items.DYE, 1, 6)};
		RitualRegistry.instance().registerRitual(Rituals.CTHULHU_CHARM = new NecronomiconInfusionRitual("cthulhuCharm", 4, 2000F, new ItemStack(ACItems.cthulhu_charm), new ItemStack(ACItems.ritual_charm), ccoffers));
		Object[] hcoffers = new Object[]{new ItemStack(Items.DYE, 1, 11), new ItemStack(Items.DYE, 1, 11), new ItemStack(Items.DYE, 1, 11), new ItemStack(Items.DYE, 1, 11), new ItemStack(Items.DYE, 1, 11), new ItemStack(Items.DYE, 1, 11),
				new ItemStack(Items.DYE, 1, 11), new ItemStack(Items.DYE, 1, 11)};
		RitualRegistry.instance().registerRitual(Rituals.HASTUR_CHARM = new NecronomiconInfusionRitual("hasturCharm", 4, 2000F, new ItemStack(ACItems.hastur_charm), new ItemStack(ACItems.ritual_charm), hcoffers));
		Object[] jcoffers = new Object[]{new ItemStack(Items.DYE, 1, 8), new ItemStack(Items.DYE, 1, 8), new ItemStack(Items.DYE, 1, 8), new ItemStack(Items.DYE, 1, 8), new ItemStack(Items.DYE, 1, 8), new ItemStack(Items.DYE, 1, 8),
				new ItemStack(Items.DYE, 1, 8), new ItemStack(Items.DYE, 1, 8)};
		RitualRegistry.instance().registerRitual(Rituals.JZAHAR_CHARM = new NecronomiconInfusionRitual("jzaharCharm", 4, 2000F, new ItemStack(ACItems.jzahar_charm), new ItemStack(ACItems.ritual_charm), jcoffers));
		Object[] acoffers = new Object[]{new ItemStack(Items.DYE, 1, 5), new ItemStack(Items.DYE, 1, 5), new ItemStack(Items.DYE, 1, 5), new ItemStack(Items.DYE, 1, 5), new ItemStack(Items.DYE, 1, 5), new ItemStack(Items.DYE, 1, 5),
				new ItemStack(Items.DYE, 1, 5), new ItemStack(Items.DYE, 1, 5)};
		RitualRegistry.instance().registerRitual(Rituals.AZATHOTH_CHARM = new NecronomiconInfusionRitual("azathothCharm", 4, 2000F, new ItemStack(ACItems.azathoth_charm), new ItemStack(ACItems.ritual_charm), acoffers));
		Object[] ncoffers = new Object[]{new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4),
				new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 4)};
		RitualRegistry.instance().registerRitual(Rituals.NYARLATHOTEP_CHARM = new NecronomiconInfusionRitual("nyarlathotepCharm", 4, 2000F, new ItemStack(ACItems.nyarlathotep_charm), new ItemStack(ACItems.ritual_charm), ncoffers));
		Object[] ycoffers = new Object[]{new ItemStack(Items.DYE, 1, 14), new ItemStack(Items.DYE, 1, 14), new ItemStack(Items.DYE, 1, 14), new ItemStack(Items.DYE, 1, 14), new ItemStack(Items.DYE, 1, 14),
				new ItemStack(Items.DYE, 1, 14), new ItemStack(Items.DYE, 1, 14), new ItemStack(Items.DYE, 1, 14)};
		RitualRegistry.instance().registerRitual(Rituals.YOG_SOTHOTH_CHARM = new NecronomiconInfusionRitual("yogsothothCharm", 4, 2000F, new ItemStack(ACItems.yog_sothoth_charm), new ItemStack(ACItems.ritual_charm), ycoffers));
		Object[] scoffers = new Object[]{new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 0),
				new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 0)};
		RitualRegistry.instance().registerRitual(Rituals.SHUB_NIGGURATH_CHARM = new NecronomiconInfusionRitual("shubniggurathCharm", 4, 2000F, new ItemStack(ACItems.shub_niggurath_charm), new ItemStack(ACItems.ritual_charm), scoffers));
		Object[] owoffers = new Object[]{ACItems.shadow_shard, Blocks.COBBLESTONE, ACItems.coralium_gem, new ItemStack(ACBlocks.darkstone_cobblestone), ACItems.shadow_shard, Blocks.COBBLESTONE,
				ACItems.coralium_gem, new ItemStack(ACBlocks.darkstone_cobblestone)};
		Object[] awoffers = new Object[]{ACItems.shadow_shard, new ItemStack(ACBlocks.abyssal_stone_brick, 1, 0), ACItems.coralium_gem, new ItemStack(ACBlocks.coralium_stone_brick, 1, 0), ACItems.shadow_shard, new ItemStack(ACBlocks.abyssal_stone_brick, 1, 0),
				ACItems.coralium_gem, new ItemStack(ACBlocks.coralium_stone_brick, 1, 0)};
		Object[] dloffers = new Object[]{ACItems.shadow_shard, ACBlocks.dreadstone_brick, ACItems.coralium_gem, new ItemStack(ACBlocks.elysian_stone_brick, 1, 0), ACItems.shadow_shard, new ItemStack(ACBlocks.dreadstone_brick, 1, 0),
				ACItems.coralium_gem, new ItemStack(ACBlocks.elysian_stone_brick, 1, 0)};
		Object[] omtoffers = new Object[]{ACItems.shadow_shard, new ItemStack(ACBlocks.ethaxium_brick, 1, 0), ACItems.coralium_gem, new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0),
				ACItems.shadow_shard, new ItemStack(ACBlocks.ethaxium_brick, 1, 0), ACItems.coralium_gem, new ItemStack(ACBlocks.dark_ethaxium_brick, 1, 0)};
		RitualRegistry.instance().registerRitual(Rituals.OVERWORLD_ENERGY_PEDESTAL = new NecronomiconInfusionRitual("epOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_energy_pedestal), ACBlocks.energy_pedestal, owoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.ABYSSAL_WASTELAND_ENERGY_PEDESTAL = new NecronomiconInfusionRitual("epAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_energy_pedestal), new ItemStack(ACBlocks.overworld_energy_pedestal), awoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.DREADLANDS_ENERGY_PEDESTAL = new NecronomiconInfusionRitual("epDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_energy_pedestal), new ItemStack(ACBlocks.abyssal_wasteland_energy_pedestal), dloffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.OMOTHOL_ENERGY_PEDESTAL = new NecronomiconInfusionRitual("epOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_energy_pedestal), new ItemStack(ACBlocks.dreadlands_energy_pedestal), omtoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.OVERWORLD_SACRIFICAL_ALTAR = new NecronomiconInfusionRitual("saOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_sacrificial_altar), ACBlocks.sacrificial_altar, owoffers).setTags("PotEnergy", "CollectionLimit", "CoolDown"));
		RitualRegistry.instance().registerRitual(Rituals.ABYSSAL_WASTELAND_SACRIFICAL_ALTAR = new NecronomiconInfusionRitual("saAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_sacrificial_altar), new ItemStack(ACBlocks.overworld_sacrificial_altar), awoffers).setTags("PotEnergy", "CollectionLimit", "CoolDown"));
		RitualRegistry.instance().registerRitual(Rituals.DREADLANDS_SACRIFICAL_ALTAR = new NecronomiconInfusionRitual("saDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_sacrificial_altar), new ItemStack(ACBlocks.abyssal_wasteland_sacrificial_altar), dloffers).setTags("PotEnergy", "CollectionLimit", "CoolDown"));
		RitualRegistry.instance().registerRitual(Rituals.OMOTHOL_SACRIFICAL_ALTAR = new NecronomiconInfusionRitual("saOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_sacrificial_altar), new ItemStack(ACBlocks.dreadlands_sacrificial_altar), omtoffers).setTags("PotEnergy", "CollectionLimit", "CoolDown"));
		Object[] staffofferings = new Object[]{new ItemStack(ACItems.dreadlands_essence), new ItemStack(ACItems.omothol_essence), ACItems.eldritch_scale, ACItems.ethaxium_ingot,
				ACItems.omothol_forged_gateway_key, ACItems.ethaxium_ingot, ACItems.eldritch_scale, new ItemStack(ACItems.abyssal_wasteland_essence)};
		String[] tags = {"energyShadow", "energyAbyssal", "energyDread", "energyOmothol", "ench"};
		RitualRegistry.instance().registerRitual(Rituals.STAFF_OF_THE_GATEKEEPER = new NecronomiconInfusionRitual("jzaharStaff", 4, ACLib.omothol_id, 15000F, new ItemStack(ACItems.staff_of_the_gatekeeper), new ItemStack(ACItems.omothol_staff_of_rending), staffofferings).setTags(tags));
		RitualRegistry.instance().registerRitual(Rituals.SILVER_KEY = new NecronomiconInfusionRitual("silverKey", 4, ACLib.omothol_id, 20000F, true, new ItemStack(ACItems.silver_key), new ItemStack(ACItems.omothol_forged_gateway_key),
				new Object[] {ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot, ACItems.ethaxium_ingot}));
		RitualRegistry.instance().registerRitual(Rituals.WEATHER = new NecronomiconWeatherRitual());
		Object[] containerofferings = new Object[]{ACBlocks.energy_collector, ACItems.shadow_shard, ACBlocks.energy_collector, ACItems.shadow_shard, ACBlocks.energy_collector,
				ACItems.shadow_shard, ACBlocks.energy_collector, ACItems.shadow_shard};
		RitualRegistry.instance().registerRitual(Rituals.ENERGY_CONTAINER = new NecronomiconInfusionRitual("energyContainer", 0, 100F, new ItemStack(ACBlocks.energy_container), ACBlocks.energy_pedestal, containerofferings).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.OVERWORLD_ENERGY_COLLECTOR = new NecronomiconInfusionRitual("ecolOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_energy_collector), ACBlocks.energy_collector, owoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.ABYSSAL_WASTELAND_ENERGY_COLLECTOR = new NecronomiconInfusionRitual("ecolAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_energy_collector), new ItemStack(ACBlocks.overworld_energy_collector), awoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.DREADLANDS_ENERGY_COLLECTOR = new NecronomiconInfusionRitual("ecolDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_energy_collector), new ItemStack(ACBlocks.abyssal_wasteland_energy_collector), dloffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.OMOTHOL_ENERGY_COLLECTOR = new NecronomiconInfusionRitual("ecolOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_energy_collector), new ItemStack(ACBlocks.dreadlands_energy_collector), omtoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.OVERWORLD_ENERGY_RELAY = new NecronomiconInfusionRitual("erOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_energy_relay), ACBlocks.energy_relay, owoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.ABYSSAL_WASTELAND_ENERGY_RELAY = new NecronomiconInfusionRitual("erAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_energy_relay), new ItemStack(ACBlocks.overworld_energy_relay), awoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.DREADLANDS_ENERGY_RELAY = new NecronomiconInfusionRitual("erDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_energy_relay), new ItemStack(ACBlocks.abyssal_wasteland_energy_relay), dloffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.OMOTHOL_ENERGY_RELAY = new NecronomiconInfusionRitual("erOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_energy_relay), new ItemStack(ACBlocks.dreadlands_energy_relay), omtoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.OVERWORLD_ENERGY_CONTAINER = new NecronomiconInfusionRitual("econOWupgrade", 0, 400F, new ItemStack(ACBlocks.overworld_energy_container), ACBlocks.energy_container, owoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.ABYSSAL_WASTELAND_ENERGY_CONTAINER = new NecronomiconInfusionRitual("econAWupgrade", 1, 800F, new ItemStack(ACBlocks.abyssal_wasteland_energy_container), new ItemStack(ACBlocks.overworld_energy_container), awoffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.DREADLANDS_ENERGY_CONTAINER = new NecronomiconInfusionRitual("econDLupgrade", 2, 1200F, new ItemStack(ACBlocks.dreadlands_energy_container), new ItemStack(ACBlocks.abyssal_wasteland_energy_container), dloffers).setTags("PotEnergy"));
		RitualRegistry.instance().registerRitual(Rituals.OMOTHOL_ENERGY_CONTAINER = new NecronomiconInfusionRitual("econOMTupgrade", 3, 1600F, new ItemStack(ACBlocks.omothol_energy_container), new ItemStack(ACBlocks.dreadlands_energy_container), omtoffers).setTags("PotEnergy"));
		Object[] sorawofferings = new Object[]{ACItems.shadow_gem, new ItemStack(ACBlocks.abyssal_stone), ACItems.coralium_plagued_flesh, new ItemStack(ACBlocks.abyssal_stone), ACItems.coralium_plagued_flesh,
				new ItemStack(ACBlocks.abyssal_stone), ACItems.coralium_plagued_flesh, new ItemStack(ACBlocks.abyssal_stone)};
		RitualRegistry.instance().registerRitual(Rituals.ABYSSAL_WASTELAND_STAFF_OF_RENDING = new NecronomiconInfusionRitual("sorAWupgrade", 1, ACLib.abyssal_wasteland_id, 1000F, new ItemStack(ACItems.abyssal_wasteland_staff_of_rending), new ItemStack(ACItems.staff_of_rending), sorawofferings).setTags(tags));
		Object[] sordlofferings = new Object[]{ACItems.shadow_gem, new ItemStack(ACBlocks.dreadstone), ACItems.dread_fragment, new ItemStack(ACBlocks.dreadstone), ACItems.dread_fragment,
				new ItemStack(ACBlocks.dreadstone), ACItems.dread_fragment, new ItemStack(ACBlocks.dreadstone)};
		RitualRegistry.instance().registerRitual(Rituals.DREADLANDS_STAFF_OF_RENDING = new NecronomiconInfusionRitual("sorDLupgrade", 2, ACLib.dreadlands_id, 2000F, new ItemStack(ACItems.dreadlands_staff_of_rending), new ItemStack(ACItems.abyssal_wasteland_staff_of_rending), sordlofferings).setTags(tags));
		Object[] soromtofferings = new Object[]{ACItems.shadow_gem, new ItemStack(ACBlocks.omothol_stone), ACItems.omothol_flesh, new ItemStack(ACBlocks.omothol_stone), ACItems.omothol_flesh,
				new ItemStack(ACBlocks.omothol_stone), ACItems.omothol_flesh, new ItemStack(ACBlocks.omothol_stone)};
		RitualRegistry.instance().registerRitual(Rituals.OMOTHOL_STAFF_OF_RENDING = new NecronomiconInfusionRitual("sorOMTupgrade", 3, ACLib.omothol_id, 3000F, new ItemStack(ACItems.omothol_staff_of_rending), new ItemStack(ACItems.dreadlands_staff_of_rending), soromtofferings).setTags(tags));
		RitualRegistry.instance().registerRitual(new NecronomiconHouseRitual());
		Object[] basicscrollofferings = {Items.BOOK, null, Items.BOOK, null, Items.BOOK, null, Items.BOOK};
		RitualRegistry.instance().registerRitual(Rituals.BASIC_SCROLL = new NecronomiconCreationRitual("basicScroll", 0, 100.0F, new ItemStack(ACItems.basic_scroll), basicscrollofferings));
		Object[] lesserscrollofferings = {Items.BOOK, null, ACBlocks.wastelands_thorn, null, Items.BOOK, null, ACBlocks.luminous_thistle};
		RitualRegistry.instance().registerRitual(Rituals.LESSER_SCROLL = new NecronomiconCreationRitual("lesserScroll", 1, 1000F, new ItemStack(ACItems.lesser_scroll), lesserscrollofferings));
		Object[] moderatescrollofferings = {Items.BOOK, null, ACItems.dread_fragment, null, Items.BOOK, null, ACItems.dreaded_shard_of_abyssalnite};
		RitualRegistry.instance().registerRitual(Rituals.MODERATE_SCROLL = new NecronomiconCreationRitual("moderateScroll", 2, 2000F, new ItemStack(ACItems.moderate_scroll), moderatescrollofferings));
	}

	private static void addDisruptions(){
		DisruptionHandler.instance().registerDisruption(Disruptions.LIGHTNING = new DisruptionLightning());
		DisruptionHandler.instance().registerDisruption(Disruptions.FIRE = new DisruptionFire());
		DisruptionHandler.instance().registerDisruption(Disruptions.SHOGGOTH = new DisruptionSpawn("spawnShoggoth", null, EntityLesserShoggoth.class));
		DisruptionHandler.instance().registerDisruption(Disruptions.POISON = new DisruptionPotion("poisonPotion", null, MobEffects.POISON));
		DisruptionHandler.instance().registerDisruption(Disruptions.SLOWNESS = new DisruptionPotion("slownessPotion", null, MobEffects.SLOWNESS));
		DisruptionHandler.instance().registerDisruption(Disruptions.WEAKNESS = new DisruptionPotion("weaknessPotion", null, MobEffects.WEAKNESS));
		DisruptionHandler.instance().registerDisruption(Disruptions.WITHER = new DisruptionPotion("witherPotion", null, MobEffects.WITHER));
		DisruptionHandler.instance().registerDisruption(Disruptions.CORALIUM = new DisruptionPotion("coraliumPotion", null, AbyssalCraftAPI.coralium_plague));
		DisruptionHandler.instance().registerDisruption(Disruptions.POTENTIAL_ENERGY = new DisruptionPotentialEnergy());
		DisruptionHandler.instance().registerDisruption(Disruptions.FREEZE = new DisruptionFreeze());
		DisruptionHandler.instance().registerDisruption(Disruptions.SHADOW_SWARM = new DisruptionSwarm("swarmShadow", null, EntityShadowCreature.class, EntityShadowMonster.class, EntityShadowBeast.class));
		DisruptionHandler.instance().registerDisruption(Disruptions.FIRE_RAIN = new DisruptionFireRain());
		DisruptionHandler.instance().registerDisruption(Disruptions.DISPLACE_ENTITIES = new DisruptionDisplaceEntities());
		//		DisruptionHandler.instance().registerDisruption(new DisruptionMonolith()); //uncomment at some point when there's a lot more disruptions
		DisruptionHandler.instance().registerDisruption(Disruptions.TELEPORT_RANDOMLY = new DisruptionTeleportRandomly());
		DisruptionHandler.instance().registerDisruption(Disruptions.DRAIN_POTENTIAL_ENERGY = new DisruptionDrainNearbyPE());
		DisruptionHandler.instance().registerDisruption(Disruptions.SHEEP_SWARM = new DisruptionSwarm("swarmSheep", DeityType.SHUBNIGGURATH, EntityEvilSheep.class, EntitySheep.class));
		DisruptionHandler.instance().registerDisruption(Disruptions.ANIMAL_CORRUPTION = new DisruptionAnimalCorruption());
		//		DisruptionHandler.instance().registerDisruption(new DisruptionCorruption());
		DisruptionHandler.instance().registerDisruption(Disruptions.OOZE = new DisruptionOoze());
		DisruptionHandler.instance().registerDisruption(Disruptions.RANDOM_SWARM = new DisruptionRandomSwarm());
		DisruptionHandler.instance().registerDisruption(Disruptions.RANDOM_SPAWN = new DisruptionRandomSpawn());
		DisruptionHandler.instance().registerDisruption(Disruptions.SHUB_OFFSPRING = new DisruptionSpawn("spawnShubOffspring", DeityType.SHUBNIGGURATH, EntityShubOffspring.class));
		DisruptionHandler.instance().registerDisruption(Disruptions.INVISIBLE_SWARM_HASTUR = new DisruptionInvisibleSwarm("invisibleSwarmHastur", DeityType.HASTUR));
		DisruptionHandler.instance().registerDisruption(Disruptions.INVISIBLE_SWARM_NYARLATHOTEP = new DisruptionInvisibleSwarm("invisibleSwarmNyarlathotep", DeityType.NYARLATHOTEP));
		DisruptionHandler.instance().registerDisruption(Disruptions.SACRIFICE_CORRUPTION_JZAHAR = new DisruptionSacrificeCorruption("sacrificeCorruptionJzahar", DeityType.JZAHAR));
		DisruptionHandler.instance().registerDisruption(Disruptions.SACRIFICE_CORRUPTION_YOG_SOTHOTH = new DisruptionSacrificeCorruption("sacrificeCorruptionYogSothoth", DeityType.YOGSOTHOTH));
		DisruptionHandler.instance().registerDisruption(Disruptions.FAMINE_AZATHOTH = new DisruptionFamine("famineAzathoth", DeityType.AZATHOTH));
		DisruptionHandler.instance().registerDisruption(Disruptions.FAMINE_SHUB_NIGGURATH = new DisruptionFamine("famineShuNiggurath", DeityType.SHUBNIGGURATH));
	}

	private static void addSpells(){
		if(ACConfig.entropy_spell)
			SpellRegistry.instance().registerSpell(Spells.ENTROPY = new EntropySpell());
		if(ACConfig.life_drain_spell)
			SpellRegistry.instance().registerSpell(Spells.LIFE_DRAIN = new LifeDrainSpell());
		if(ACConfig.mining_spell)
			SpellRegistry.instance().registerSpell(Spells.MINING = new MiningSpell());
		if(ACConfig.grasp_of_cthulhu_spell)
			SpellRegistry.instance().registerSpell(Spells.GRASP_OF_CTHULHU = new GraspofCthulhuSpell());
		if(ACConfig.invisibility_spell)
			SpellRegistry.instance().registerSpell(Spells.INVISIBILITY = new InvisibilitySpell());
		if(ACConfig.detachment_spell)
			SpellRegistry.instance().registerSpell(Spells.DETACHMENT = new DetachmentSpell());
		if(ACConfig.steal_vigor_spell)
			SpellRegistry.instance().registerSpell(Spells.STEAL_VIGOR = new StealVigorSpell());
		if(ACConfig.sirens_song_spell)
			SpellRegistry.instance().registerSpell(Spells.SIRENS_SONG = new SirensSongSpell());
		if(ACConfig.undeath_to_dust_spell)
			SpellRegistry.instance().registerSpell(Spells.UNDEATH_TO_DUST = new UndeathtoDustSpell());
		if(ACConfig.ooze_removal_spell)
			SpellRegistry.instance().registerSpell(Spells.OOZE_REMOVAL = new OozeRemovalSpell());
		if(ACConfig.teleport_hostile_spell)
			SpellRegistry.instance().registerSpell(Spells.TELEPORT_HOSOTILES = new TeleportHostilesSpell());
		if(ACConfig.floating_spell)
			SpellRegistry.instance().registerSpell(Spells.FLOATING = new FloatingSpell());
		if(ACConfig.teleport_home_spell)
			SpellRegistry.instance().registerSpell(Spells.TELEPORT_HOME = new TeleportHomeSpell());
		if(ACConfig.compass_spell)
			SpellRegistry.instance().registerSpell(Spells.COMPASS = new CompassSpell());
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
