/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.init;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.item.*;
import com.shinoow.abyssalcraft.common.items.*;
import com.shinoow.abyssalcraft.common.items.armor.*;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.item.ItemCharm;
import com.shinoow.abyssalcraft.lib.item.ItemMetadata;

public class ItemHandler implements ILifeCycleHandler {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		//"secret" dev stuff
		AbyssalCraft.devsword = new AbyssalCraftTool();

		//Misc items
		ACItems.oblivion_catalyst = new ItemOC();
		ACItems.staff_of_the_gatekeeper = new ItemStaff();
		ACItems.gateway_key = new ItemPortalPlacer(0, "gatewaykey");
		ACItems.liquid_coralium_bucket = new ItemCBucket(ACBlocks.liquid_coralium);
		ACItems.powerstone_tracker = new ItemTrackerPSDL();
		ACItems.eye_of_the_abyss = new ItemEoA();
		ACItems.dreaded_gateway_key = new ItemPortalPlacer(1, "gatewaykeydl");
		ACItems.coralium_brick = new ItemACBasic("cbrick");
		ACItems.cudgel = new ItemCudgel();
		ACItems.carbon_cluster = new ItemACBasic("carboncluster");
		ACItems.dense_carbon_cluster = new ItemACBasic("densecarboncluster");
		ACItems.methane = new ItemACBasic("methane");
		ACItems.nitre = new ItemACBasic("nitre");
		ACItems.sulfur = new ItemACBasic("sulfur");
		ACItems.rlyehian_gateway_key = new ItemPortalPlacer(2, "gatewaykeyjzh");
		ACItems.tin_ingot = new ItemACBasic("tiningot");
		ACItems.copper_ingot = new ItemACBasic("copperingot");
		ACItems.life_crystal = new ItemACBasic("lifecrystal");
		ACItems.shoggoth_flesh = new ItemMetadata("shoggothflesh", "overworld", "abyssalwasteland", "dreadlands", "omothol", "darkrealm");
		ACItems.eldritch_scale = new ItemACBasic("eldritchscale");
		ACItems.omothol_flesh = new ItemOmotholFlesh(3, 0.3F, false);
		ACItems.necronomicon = new ItemNecronomicon("necronomicon", 0);
		ACItems.abyssal_wasteland_necronomicon = new ItemNecronomicon("necronomicon_cor", 1);
		ACItems.dreadlands_necronomicon = new ItemNecronomicon("necronomicon_dre", 2);
		ACItems.omothol_necronomicon = new ItemNecronomicon("necronomicon_omt", 3);
		ACItems.abyssalnomicon = new ItemNecronomicon("abyssalnomicon", 4);
		ACItems.small_crystal_bag = new ItemCrystalBag("crystalbag_small");
		ACItems.medium_crystal_bag = new ItemCrystalBag("crystalbag_medium");
		ACItems.large_crystal_bag = new ItemCrystalBag("crystalbag_large");
		ACItems.huge_crystal_bag = new ItemCrystalBag("crystalbag_huge");
		ACItems.ingot_nugget = new ItemMetadata("nugget", "abyssalnite", "coralium", "dreadium", "ethaxium");
		ACItems.essence = new ItemMetadata("essence", "abyssalwasteland", "dreadlands", "omothol");
		ACItems.skin = new ItemMetadata("skin", "abyssalwasteland", "dreadlands", "omothol");
		ACItems.essence_of_the_gatekeeper = new ItemGatekeeperEssence();
		ACItems.interdimensional_cage = new ItemInterdimensionalCage();

		//Coins
		ACItems.coin = new ItemCoin("coin");
		ACItems.cthulhu_engraved_coin = new ItemCoin("cthulhucoin");
		ACItems.elder_engraved_coin = new ItemCoin("eldercoin");
		ACItems.jzahar_engraved_coin = new ItemCoin("jzaharcoin");
		ACItems.blank_engraving = new ItemEngraving("blank", 50).setCreativeTab(ACTabs.tabCoins);
		ACItems.cthulhu_engraving = new ItemEngraving("cthulhu", 10).setCreativeTab(ACTabs.tabCoins);
		ACItems.elder_engraving = new ItemEngraving("elder", 10).setCreativeTab(ACTabs.tabCoins);
		ACItems.jzahar_engraving = new ItemEngraving("jzahar", 10).setCreativeTab(ACTabs.tabCoins);
		ACItems.hastur_engraved_coin = new ItemCoin("hasturcoin");
		ACItems.azathoth_engraved_coin = new ItemCoin("azathothcoin");
		ACItems.nyarlathotep_engraved_coin = new ItemCoin("nyarlathotepcoin");
		ACItems.yog_sothoth_engraved_coin = new ItemCoin("yogsothothcoin");
		ACItems.shub_niggurath_engraved_coin = new ItemCoin("shubniggurathcoin");
		ACItems.hastur_engraving = new ItemEngraving("hastur", 10).setCreativeTab(ACTabs.tabCoins);
		ACItems.azathoth_engraving = new ItemEngraving("azathoth", 10).setCreativeTab(ACTabs.tabCoins);
		ACItems.nyarlathotep_engraving = new ItemEngraving("nyarlathotep", 10).setCreativeTab(ACTabs.tabCoins);
		ACItems.yog_sothoth_engraving = new ItemEngraving("yogsothoth", 10).setCreativeTab(ACTabs.tabCoins);
		ACItems.shub_niggurath_engraving = new ItemEngraving("shubniggurath", 10).setCreativeTab(ACTabs.tabCoins);

		//Charms
		ACItems.ritual_charm = new ItemCharm("ritualcharm", null);
		ACItems.cthulhu_charm = new ItemCharm("cthulhucharm", DeityType.CTHULHU);
		ACItems.hastur_charm = new ItemCharm("hasturcharm", DeityType.HASTUR);
		ACItems.jzahar_charm = new ItemCharm("jzaharcharm", DeityType.JZAHAR);
		ACItems.azathoth_charm = new ItemCharm("azathothcharm", DeityType.AZATHOTH);
		ACItems.nyarlathotep_charm = new ItemCharm("nyarlathotepcharm", DeityType.NYARLATHOTEP);
		ACItems.yog_sothoth_charm = new ItemCharm("yogsothothcharm", DeityType.YOGSOTHOTH);
		ACItems.shub_niggurath_charm = new ItemCharm("shubniggurathcharm", DeityType.SHUBNIGGURATH);

		//Ethaxium
		ACItems.ethaxium_brick = new ItemACBasic("ethbrick");
		ACItems.ethaxium_ingot = new ItemACBasic("ethaxiumingot");

		//anti-items
		ACItems.liquid_antimatter_bucket = new ItemAntiBucket(ACBlocks.liquid_antimatter);
		ACItems.anti_beef = new ItemAntiFood("antibeef");
		ACItems.anti_chicken = new ItemAntiFood("antichicken");
		ACItems.anti_pork = new ItemAntiFood("antipork");
		ACItems.rotten_anti_flesh = new ItemAntiFood("antiflesh");
		ACItems.anti_bone = new ItemACBasic("antibone");
		ACItems.anti_spider_eye = new ItemAntiFood("antispidereye", false);
		ACItems.anti_plagued_flesh = new ItemCorflesh(0, 0, false, "anticorflesh");
		ACItems.anti_plagued_flesh_on_a_bone = new ItemCorbone(0, 0, false, "anticorbone");

		//crystals
		ACItems.crystal = new ItemCrystal("crystal");
		ACItems.crystal_shard = new ItemCrystal("crystalshard");

		//Shadow items
		ACItems.shadow_fragment = new ItemACBasic("shadowfragment");
		ACItems.shadow_shard = new ItemACBasic("shadowshard");
		ACItems.shadow_gem = new ItemACBasic("shadowgem");
		ACItems.shard_of_oblivion = new ItemACBasic("oblivionshard");
		AbyssalCraft.shadowPlate = new ItemACBasic("shadowplate");

		//Dread items
		ACItems.dreaded_shard_of_abyssalnite = new ItemACBasic("dreadshard");
		ACItems.dreaded_chunk_of_abyssalnite = new ItemACBasic("dreadchunk");
		ACItems.dreadium_ingot = new ItemACBasic("dreadiumingot");
		ACItems.dread_fragment = new ItemACBasic("dreadfragment");
		ACItems.dread_cloth = new ItemACBasic("dreadcloth");
		ACItems.dreadium_plate = new ItemACBasic("dreadplate");
		ACItems.dreadium_katana_blade = new ItemACBasic("dreadblade");
		ACItems.dread_plagued_gateway_key = new ItemACBasic("dreadkey");

		//Abyssalnite items
		ACItems.chunk_of_abyssalnite = new ItemACBasic("abychunk");
		ACItems.abyssalnite_ingot = new ItemACBasic("abyingot");

		//Coralium items
		ACItems.coralium_gem_cluster_2 = new ItemCoraliumcluster("ccluster2", "2");
		ACItems.coralium_gem_cluster_3 = new ItemCoraliumcluster("ccluster3", "3");
		ACItems.coralium_gem_cluster_4 = new ItemCoraliumcluster("ccluster4", "4");
		ACItems.coralium_gem_cluster_5 = new ItemCoraliumcluster("ccluster5", "5");
		ACItems.coralium_gem_cluster_6 = new ItemCoraliumcluster("ccluster6", "6");
		ACItems.coralium_gem_cluster_7 = new ItemCoraliumcluster("ccluster7", "7");
		ACItems.coralium_gem_cluster_8 = new ItemCoraliumcluster("ccluster8", "8");
		ACItems.coralium_gem_cluster_9 = new ItemCoraliumcluster("ccluster9", "9");
		ACItems.coralium_pearl = new ItemACBasic("cpearl");
		ACItems.chunk_of_coralium = new ItemACBasic("cchunk");
		ACItems.refined_coralium_ingot = new ItemACBasic("cingot");
		ACItems.coralium_plate = new ItemACBasic("platec");
		ACItems.coralium_gem = new ItemACBasic("coralium");
		ACItems.transmutation_gem = new ItemCorb();
		ACItems.coralium_plagued_flesh = new ItemCorflesh(2, 0.1F, false, "corflesh");
		ACItems.coralium_plagued_flesh_on_a_bone = new ItemCorbone(2, 0.1F, false, "corbone");
		ACItems.coralium_longbow = new ItemCoraliumBow(20.0F, 0, 8, 16);

		//Tools
		ACItems.darkstone_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.darkstoneTool, "dpick", 1);
		ACItems.darkstone_axe = new ItemACAxe(AbyssalCraftAPI.darkstoneTool, "daxe", 1);
		ACItems.darkstone_shovel = new ItemACShovel(AbyssalCraftAPI.darkstoneTool, "dshovel", 1);
		ACItems.darkstone_sword = new ItemACSword(AbyssalCraftAPI.darkstoneTool, "dsword");
		ACItems.darkstone_hoe = new ItemACHoe(AbyssalCraftAPI.darkstoneTool, "dhoe");
		ACItems.abyssalnite_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.abyssalniteTool, "apick", 4, TextFormatting.DARK_AQUA);
		ACItems.abyssalnite_axe = new ItemACAxe(AbyssalCraftAPI.abyssalniteTool, "aaxe", 4, TextFormatting.DARK_AQUA);
		ACItems.abyssalnite_shovel = new ItemACShovel(AbyssalCraftAPI.abyssalniteTool, "ashovel", 4, TextFormatting.DARK_AQUA);
		ACItems.abyssalnite_sword = new ItemACSword(AbyssalCraftAPI.abyssalniteTool, "asword", TextFormatting.DARK_AQUA);
		ACItems.abyssalnite_hoe = new ItemACHoe(AbyssalCraftAPI.abyssalniteTool, "ahoe", TextFormatting.DARK_AQUA);
		ACItems.refined_coralium_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.refinedCoraliumTool, "corpick", 5, TextFormatting.AQUA);
		ACItems.refined_coralium_axe = new ItemACAxe(AbyssalCraftAPI.refinedCoraliumTool, "coraxe", 5, TextFormatting.AQUA);
		ACItems.refined_coralium_shovel = new ItemACShovel(AbyssalCraftAPI.refinedCoraliumTool, "corshovel", 5, TextFormatting.AQUA);
		ACItems.refined_coralium_sword = new ItemACSword(AbyssalCraftAPI.refinedCoraliumTool, "corsword", TextFormatting.AQUA);
		ACItems.refined_coralium_hoe = new ItemACHoe(AbyssalCraftAPI.refinedCoraliumTool, "corhoe", TextFormatting.AQUA);
		ACItems.dreadium_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.dreadiumTool, "dreadiumpickaxe", 6, TextFormatting.DARK_RED);
		ACItems.dreadium_axe = new ItemACAxe(AbyssalCraftAPI.dreadiumTool, "dreadiumaxe", 6, TextFormatting.DARK_RED);
		ACItems.dreadium_shovel = new ItemACShovel(AbyssalCraftAPI.dreadiumTool, "dreadiumshovel", 6, TextFormatting.DARK_RED);
		ACItems.dreadium_sword = new ItemACSword(AbyssalCraftAPI.dreadiumTool, "dreadiumsword", TextFormatting.DARK_RED);
		ACItems.dreadium_hoe = new ItemACHoe(AbyssalCraftAPI.dreadiumTool, "dreadiumhoe", TextFormatting.DARK_RED);
		ACItems.dreadium_katana_hilt = new ItemDreadiumKatana("dreadhilt", ItemDreadiumKatana.hilt);
		ACItems.dreadium_katana = new ItemDreadiumKatana("dreadkatana", ItemDreadiumKatana.katana);
		ACItems.sacthoths_soul_harvesting_blade = new ItemSoulReaper("soulreaper");
		ACItems.ethaxium_pickaxe = new ItemEthaxiumPickaxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumpickaxe");
		ACItems.ethaxium_axe = new ItemACAxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumaxe", 8, TextFormatting.AQUA);
		ACItems.ethaxium_shovel = new ItemACShovel(AbyssalCraftAPI.ethaxiumTool, "ethaxiumshovel", 8, TextFormatting.AQUA);
		ACItems.ethaxium_sword = new ItemACSword(AbyssalCraftAPI.ethaxiumTool, "ethaxiumsword", TextFormatting.AQUA);
		ACItems.ethaxium_hoe = new ItemACHoe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumhoe", TextFormatting.AQUA);
		ACItems.staff_of_rending = new ItemDrainStaff();

		//Armor
		ACItems.abyssalnite_helmet = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.HEAD, "ahelmet");
		ACItems.abyssalnite_chestplate = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.CHEST, "aplate");
		ACItems.abyssalnite_leggings = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.LEGS, "alegs");
		ACItems.abyssalnite_boots = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.FEET, "aboots");
		ACItems.dreaded_abyssalnite_helmet = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.HEAD, "dhelmet");
		ACItems.dreaded_abyssalnite_chestplate = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.CHEST, "dplate");
		ACItems.dreaded_abyssalnite_leggings = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.LEGS, "dlegs");
		ACItems.dreaded_abyssalnite_boots = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.FEET, "dboots");
		ACItems.refined_coralium_helmet = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.HEAD, "corhelmet");
		ACItems.refined_coralium_chestplate = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.CHEST, "corplate");
		ACItems.refined_coralium_leggings = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.LEGS, "corlegs");
		ACItems.refined_coralium_boots = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.FEET, "corboots");
		ACItems.plated_coralium_helmet = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.HEAD, "corhelmetp");
		ACItems.plated_coralium_chestplate = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.CHEST, "corplatep");
		ACItems.plated_coralium_leggings = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.LEGS, "corlegsp");
		ACItems.plated_coralium_boots = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.FEET, "corbootsp");
		ACItems.depths_helmet = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.HEAD, "depthshelmet");
		ACItems.depths_chestplate = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.CHEST, "depthsplate");
		ACItems.depths_leggings = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.LEGS, "depthslegs");
		ACItems.depths_boots = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.FEET, "depthsboots");
		ACItems.dreadium_helmet = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.HEAD, "dreadiumhelmet");
		ACItems.dreadium_chestplate = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.CHEST, "dreadiumplate");
		ACItems.dreadium_leggings = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.LEGS, "dreadiumlegs");
		ACItems.dreadium_boots = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.FEET, "dreadiumboots");
		ACItems.dreadium_samurai_helmet = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.HEAD, "dreadiumsamuraihelmet");
		ACItems.dreadium_samurai_chestplate = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.CHEST, "dreadiumsamuraiplate");
		ACItems.dreadium_samurai_leggings = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.LEGS, "dreadiumsamurailegs");
		ACItems.dreadium_samurai_boots = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.FEET, "dreadiumsamuraiboots");
		ACItems.ethaxium_helmet = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.HEAD, "ethaxiumhelmet");
		ACItems.ethaxium_chestplate = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.CHEST, "ethaxiumplate");
		ACItems.ethaxium_leggings = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.LEGS, "ethaxiumlegs");
		ACItems.ethaxium_boots = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.FEET, "ethaxiumboots");

		//Upgrade kits
		ACItems.cobblestone_upgrade_kit = new ItemUpgradeKit("Wood", "Cobblestone").setUnlocalizedName("cobbleu").setCreativeTab(ACTabs.tabItems);
		ACItems.iron_upgrade_kit = new ItemUpgradeKit("Cobblestone", "Iron").setUnlocalizedName("ironu").setCreativeTab(ACTabs.tabItems);
		ACItems.gold_upgrade_kit = new ItemUpgradeKit("Iron", "Gold").setUnlocalizedName("goldu").setCreativeTab(ACTabs.tabItems);
		ACItems.diamond_upgrade_kit = new ItemUpgradeKit("Gold", "Diamond").setUnlocalizedName("diamondu").setCreativeTab(ACTabs.tabItems);
		ACItems.abyssalnite_upgrade_kit = new ItemUpgradeKit("Diamond", "Abyssalnite").setUnlocalizedName("abyssalniteu").setCreativeTab(ACTabs.tabItems);
		ACItems.coralium_upgrade_kit = new ItemUpgradeKit("Abyssalnite", "Coralium").setUnlocalizedName("coraliumu").setCreativeTab(ACTabs.tabItems);
		ACItems.dreadium_upgrade_kit = new ItemUpgradeKit("Coralium", "Dreadium").setUnlocalizedName("dreadiumu").setCreativeTab(ACTabs.tabItems);
		ACItems.ethaxium_upgrade_kit = new ItemUpgradeKit("Dreadium", "Ethaxium").setUnlocalizedName("ethaxiumu").setCreativeTab(ACTabs.tabItems);

		//Foodstuffs
		ACItems.iron_plate = new ItemACBasic("ironp");
		ACItems.mre = new ItemPlatefood(20, 1F, false, "mre");
		ACItems.chicken_on_a_plate = new ItemPlatefood(12, 1.2F, false, "chickenp");
		ACItems.pork_on_a_plate = new ItemPlatefood(16, 1.6F, false, "porkp");
		ACItems.beef_on_a_plate = new ItemPlatefood(6, 0.6F, false, "beefp");
		ACItems.fish_on_a_plate = new ItemPlatefood(10, 1.2F, false, "fishp");
		ACItems.dirty_plate = new ItemACBasic("dirtyplate");
		ACItems.fried_egg = new ItemFood(5, 0.6F, false).setCreativeTab(ACTabs.tabFood).setUnlocalizedName("friedegg");
		ACItems.fried_egg_on_a_plate = new ItemPlatefood(10, 1.2F, false, "eggp");
		ACItems.washcloth = new ItemWashCloth();

		registerItem(AbyssalCraft.devsword, "devsword");
		registerItem(ACItems.oblivion_catalyst, "oc");
		registerItem(ACItems.gateway_key, "gatewaykey");
		registerItem(ACItems.staff_of_the_gatekeeper, "staff");
		registerItem(ACItems.liquid_coralium_bucket, "cbucket");
		registerItem(ACItems.powerstone_tracker, "powerstonetracker");
		registerItem(ACItems.eye_of_the_abyss, "eoa");
		registerItem(ACItems.dreaded_gateway_key, "gatewaykeydl");
		registerItem(ACItems.dreaded_shard_of_abyssalnite, "dreadshard");
		registerItem(ACItems.dreaded_chunk_of_abyssalnite, "dreadchunk");
		registerItem(ACItems.chunk_of_abyssalnite, "abychunk");
		registerItem(ACItems.abyssalnite_ingot, "abyingot");
		registerItem(ACItems.coralium_gem, "coralium");
		registerItem(ACItems.coralium_gem_cluster_2, "ccluster2");
		registerItem(ACItems.coralium_gem_cluster_3, "ccluster3");
		registerItem(ACItems.coralium_gem_cluster_4, "ccluster4");
		registerItem(ACItems.coralium_gem_cluster_5, "ccluster5");
		registerItem(ACItems.coralium_gem_cluster_6, "ccluster6");
		registerItem(ACItems.coralium_gem_cluster_7, "ccluster7");
		registerItem(ACItems.coralium_gem_cluster_8, "ccluster8");
		registerItem(ACItems.coralium_gem_cluster_9, "ccluster9");
		registerItem(ACItems.coralium_pearl ,"cpearl");
		registerItem(ACItems.chunk_of_coralium, "cchunk");
		registerItem(ACItems.refined_coralium_ingot, "cingot");
		registerItem(ACItems.coralium_plate, "platec");
		registerItem(ACItems.transmutation_gem, "transmutationgem");
		registerItem(ACItems.coralium_plagued_flesh, "corflesh");
		registerItem(ACItems.coralium_plagued_flesh_on_a_bone, "corbone");
		registerItem(ACItems.darkstone_pickaxe, "dpick");
		registerItem(ACItems.darkstone_axe, "daxe");
		registerItem(ACItems.darkstone_shovel, "dshovel");
		registerItem(ACItems.darkstone_sword, "dsword");
		registerItem(ACItems.darkstone_hoe, "dhoe");
		registerItem(ACItems.abyssalnite_pickaxe, "apick");
		registerItem(ACItems.abyssalnite_axe, "aaxe");
		registerItem(ACItems.abyssalnite_shovel, "ashovel");
		registerItem(ACItems.abyssalnite_sword, "asword");
		registerItem(ACItems.abyssalnite_hoe, "ahoe");
		registerItem(ACItems.refined_coralium_pickaxe, "corpick");
		registerItem(ACItems.refined_coralium_axe, "coraxe");
		registerItem(ACItems.refined_coralium_shovel, "corshovel");
		registerItem(ACItems.refined_coralium_sword, "corsword");
		registerItem(ACItems.refined_coralium_hoe, "corhoe");
		registerItem(ACItems.abyssalnite_helmet, "ahelmet");
		registerItem(ACItems.abyssalnite_chestplate, "aplate");
		registerItem(ACItems.abyssalnite_leggings, "alegs");
		registerItem(ACItems.abyssalnite_boots, "aboots");
		registerItem(ACItems.dreaded_abyssalnite_helmet, "dhelmet");
		registerItem(ACItems.dreaded_abyssalnite_chestplate, "dplate");
		registerItem(ACItems.dreaded_abyssalnite_leggings, "dlegs");
		registerItem(ACItems.dreaded_abyssalnite_boots, "dboots");
		registerItem(ACItems.refined_coralium_helmet, "corhelmet");
		registerItem(ACItems.refined_coralium_chestplate, "corplate");
		registerItem(ACItems.refined_coralium_leggings, "corlegs");
		registerItem(ACItems.refined_coralium_boots, "corboots");
		registerItem(ACItems.plated_coralium_helmet, "corhelmetp");
		registerItem(ACItems.plated_coralium_chestplate, "corplatep");
		registerItem(ACItems.plated_coralium_leggings, "corlegsp");
		registerItem(ACItems.plated_coralium_boots, "corbootsp");
		registerItem(ACItems.depths_helmet, "depthshelmet");
		registerItem(ACItems.depths_chestplate, "depthsplate");
		registerItem(ACItems.depths_leggings, "depthslegs");
		registerItem(ACItems.depths_boots, "depthsboots");
		registerItem(ACItems.cobblestone_upgrade_kit, "cobbleu");
		registerItem(ACItems.iron_upgrade_kit, "ironu");
		registerItem(ACItems.gold_upgrade_kit, "goldu");
		registerItem(ACItems.diamond_upgrade_kit, "diamondu");
		registerItem(ACItems.abyssalnite_upgrade_kit, "abyssalniteu");
		registerItem(ACItems.coralium_upgrade_kit, "coraliumu");
		registerItem(ACItems.mre, "mre");
		registerItem(ACItems.iron_plate, "ironp");
		registerItem(ACItems.chicken_on_a_plate, "chickenp");
		registerItem(ACItems.pork_on_a_plate, "porkp");
		registerItem(ACItems.beef_on_a_plate, "beefp");
		registerItem(ACItems.fish_on_a_plate, "fishp");
		registerItem(ACItems.dirty_plate, "dirtyplate");
		registerItem(ACItems.fried_egg, "friedegg");
		registerItem(ACItems.fried_egg_on_a_plate, "eggp");
		registerItem(ACItems.washcloth, "cloth");
		registerItem(ACItems.shadow_fragment, "shadowfragment");
		registerItem(ACItems.shadow_shard, "shadowshard");
		registerItem(ACItems.shadow_gem, "shadowgem");
		registerItem(ACItems.shard_of_oblivion, "oblivionshard");
		registerItem(ACItems.coralium_longbow, "corbow");
		registerItem(ACItems.liquid_antimatter_bucket, "antibucket");
		registerItem(ACItems.coralium_brick, "cbrick");
		registerItem(ACItems.cudgel, "cudgel");
		registerItem(ACItems.dreadium_ingot, "dreadiumingot");
		registerItem(ACItems.dread_fragment, "dreadfragment");
		registerItem(ACItems.dreadium_helmet, "dreadiumhelmet");
		registerItem(ACItems.dreadium_chestplate, "dreadiumplate");
		registerItem(ACItems.dreadium_leggings, "dreadiumlegs");
		registerItem(ACItems.dreadium_boots, "dreadiumboots");
		registerItem(ACItems.dreadium_pickaxe, "dreadiumpickaxe");
		registerItem(ACItems.dreadium_axe, "dreadiumaxe");
		registerItem(ACItems.dreadium_shovel, "dreadiumshovel");
		registerItem(ACItems.dreadium_sword, "dreadiumsword");
		registerItem(ACItems.dreadium_hoe, "dreadiumhoe");
		registerItem(ACItems.dreadium_upgrade_kit, "dreadiumu");
		registerItem(ACItems.carbon_cluster, "carboncluster");
		registerItem(ACItems.dense_carbon_cluster, "densecarboncluster");
		registerItem(ACItems.methane, "methane");
		registerItem(ACItems.nitre, "nitre");
		registerItem(ACItems.sulfur, "sulfur");
		registerItem(ACItems.crystal, "crystal");
		registerItem(ACItems.crystal_shard, "crystalshard");
		registerItem(ACItems.dread_cloth, "dreadcloth");
		registerItem(ACItems.dreadium_plate, "dreadplate");
		registerItem(ACItems.dreadium_katana_blade, "dreadblade");
		registerItem(ACItems.dreadium_katana_hilt, "dreadhilt");
		registerItem(ACItems.dreadium_katana, "dreadkatana");
		registerItem(ACItems.dread_plagued_gateway_key, "dreadkey");
		registerItem(ACItems.rlyehian_gateway_key, "gatewaykeyjzh");
		registerItem(ACItems.dreadium_samurai_helmet, "dreadiumsamuraihelmet");
		registerItem(ACItems.dreadium_samurai_chestplate, "dreadiumsamuraiplate");
		registerItem(ACItems.dreadium_samurai_leggings, "dreadiumsamurailegs");
		registerItem(ACItems.dreadium_samurai_boots, "dreadiumsamuraiboots");
		registerItem(ACItems.tin_ingot, "tiningot");
		registerItem(ACItems.copper_ingot, "copperingot");
		registerItem(ACItems.anti_beef, "antibeef");
		registerItem(ACItems.anti_chicken, "antichicken");
		registerItem(ACItems.anti_pork, "antipork");
		registerItem(ACItems.rotten_anti_flesh, "antiflesh");
		registerItem(ACItems.anti_bone, "antibone");
		registerItem(ACItems.anti_spider_eye, "antispidereye");
		registerItem(ACItems.sacthoths_soul_harvesting_blade, "soulreaper");
		registerItem(ACItems.ethaxium_brick, "ethbrick");
		registerItem(ACItems.ethaxium_ingot, "ethaxiumingot");
		registerItem(ACItems.life_crystal, "lifecrystal");
		registerItem(ACItems.ethaxium_helmet, "ethaxiumhelmet");
		registerItem(ACItems.ethaxium_chestplate, "ethaxiumplate");
		registerItem(ACItems.ethaxium_leggings, "ethaxiumlegs");
		registerItem(ACItems.ethaxium_boots, "ethaxiumboots");
		registerItem(ACItems.ethaxium_pickaxe, "ethaxiumpickaxe");
		registerItem(ACItems.ethaxium_axe, "ethaxiumaxe");
		registerItem(ACItems.ethaxium_shovel, "ethaxiumshovel");
		registerItem(ACItems.ethaxium_sword, "ethaxiumsword");
		registerItem(ACItems.ethaxium_hoe, "ethaxiumhoe");
		registerItem(ACItems.ethaxium_upgrade_kit, "ethaxiumu");
		registerItem(ACItems.coin, "coin");
		registerItem(ACItems.cthulhu_engraved_coin, "cthulhucoin");
		registerItem(ACItems.elder_engraved_coin, "eldercoin");
		registerItem(ACItems.jzahar_engraved_coin, "jzaharcoin");
		registerItem(ACItems.blank_engraving, "engraving_blank");
		registerItem(ACItems.cthulhu_engraving, "engraving_cthulhu");
		registerItem(ACItems.elder_engraving, "engraving_elder");
		registerItem(ACItems.jzahar_engraving, "engraving_jzahar");
		registerItem(ACItems.eldritch_scale, "eldritchscale");
		registerItem(ACItems.omothol_flesh, "omotholflesh");
		registerItem(ACItems.anti_plagued_flesh, "anticorflesh");
		registerItem(ACItems.anti_plagued_flesh_on_a_bone, "anticorbone");
		registerItem(ACItems.necronomicon, "necronomicon");
		registerItem(ACItems.abyssal_wasteland_necronomicon, "necronomicon_cor");
		registerItem(ACItems.dreadlands_necronomicon, "necronomicon_dre");
		registerItem(ACItems.omothol_necronomicon, "necronomicon_omt");
		registerItem(ACItems.abyssalnomicon, "abyssalnomicon");
		registerItem(ACItems.small_crystal_bag, "crystalbag_small");
		registerItem(ACItems.medium_crystal_bag, "crystalbag_medium");
		registerItem(ACItems.large_crystal_bag, "crystalbag_large");
		registerItem(ACItems.huge_crystal_bag, "crystalbag_huge");
		registerItem(ACItems.shoggoth_flesh, "shoggothflesh");
		registerItem(ACItems.ingot_nugget, "ingotnugget");
		registerItem(ACItems.staff_of_rending, "drainstaff");
		registerItem(ACItems.essence, "essence");
		registerItem(ACItems.skin, "skin");
		registerItem(ACItems.ritual_charm, "charm");
		registerItem(ACItems.cthulhu_charm, "cthulhucharm");
		registerItem(ACItems.hastur_charm, "hasturcharm");
		registerItem(ACItems.jzahar_charm, "jzaharcharm");
		registerItem(ACItems.azathoth_charm, "azathothcharm");
		registerItem(ACItems.nyarlathotep_charm, "nyarlathotepcharm");
		registerItem(ACItems.yog_sothoth_charm, "yogsothothcharm");
		registerItem(ACItems.shub_niggurath_charm, "shubniggurathcharm");
		registerItem(ACItems.hastur_engraved_coin, "hasturcoin");
		registerItem(ACItems.azathoth_engraved_coin, "azathothcoin");
		registerItem(ACItems.nyarlathotep_engraved_coin, "nyarlathotepcoin");
		registerItem(ACItems.yog_sothoth_engraved_coin, "yogsothothcoin");
		registerItem(ACItems.shub_niggurath_engraved_coin, "shubniggurathcoin");
		registerItem(ACItems.hastur_engraving, "engraving_hastur");
		registerItem(ACItems.azathoth_engraving, "engraving_azathoth");
		registerItem(ACItems.nyarlathotep_engraving, "engraving_nyarlathotep");
		registerItem(ACItems.yog_sothoth_engraving, "engraving_yogsothoth");
		registerItem(ACItems.shub_niggurath_engraving, "engraving_shubniggurath");
		registerItem(ACItems.essence_of_the_gatekeeper, "gatekeeperessence");
		registerItem(ACItems.interdimensional_cage, "interdimensionalcage");
		//		registerItem(shadowPlate, "shadowplate");

		AbyssalCraftAPI.setRepairItems();
	}

	@Override
	public void init(FMLInitializationEvent event) {}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	private static void registerItem(Item item, String name){
		GameRegistry.register(item.setRegistryName(new ResourceLocation(AbyssalCraft.modid, name)));
	}
}
