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

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

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

	public static Item devsword, shadowPlate;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		//"secret" dev stuff
		devsword = new AbyssalCraftTool();

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
		shadowPlate = new ItemACBasic("shadowplate");

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
		ACItems.abyssalnite_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.abyssalniteTool, "apick", 4, EnumChatFormatting.DARK_AQUA);
		ACItems.abyssalnite_axe = new ItemACAxe(AbyssalCraftAPI.abyssalniteTool, "aaxe", 4, EnumChatFormatting.DARK_AQUA);
		ACItems.abyssalnite_shovel = new ItemACShovel(AbyssalCraftAPI.abyssalniteTool, "ashovel", 4, EnumChatFormatting.DARK_AQUA);
		ACItems.abyssalnite_sword = new ItemACSword(AbyssalCraftAPI.abyssalniteTool, "asword", EnumChatFormatting.DARK_AQUA);
		ACItems.abyssalnite_hoe = new ItemACHoe(AbyssalCraftAPI.abyssalniteTool, "ahoe", EnumChatFormatting.DARK_AQUA);
		ACItems.refined_coralium_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.refinedCoraliumTool, "corpick", 5, EnumChatFormatting.AQUA);
		ACItems.refined_coralium_axe = new ItemACAxe(AbyssalCraftAPI.refinedCoraliumTool, "coraxe", 5, EnumChatFormatting.AQUA);
		ACItems.refined_coralium_shovel = new ItemACShovel(AbyssalCraftAPI.refinedCoraliumTool, "corshovel", 5, EnumChatFormatting.AQUA);
		ACItems.refined_coralium_sword = new ItemACSword(AbyssalCraftAPI.refinedCoraliumTool, "corsword", EnumChatFormatting.AQUA);
		ACItems.refined_coralium_hoe = new ItemACHoe(AbyssalCraftAPI.refinedCoraliumTool, "corhoe", EnumChatFormatting.AQUA);
		ACItems.dreadium_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.dreadiumTool, "dreadiumpickaxe", 6, EnumChatFormatting.DARK_RED);
		ACItems.dreadium_axe = new ItemACAxe(AbyssalCraftAPI.dreadiumTool, "dreadiumaxe", 6, EnumChatFormatting.DARK_RED);
		ACItems.dreadium_shovel = new ItemACShovel(AbyssalCraftAPI.dreadiumTool, "dreadiumshovel", 6, EnumChatFormatting.DARK_RED);
		ACItems.dreadium_sword = new ItemACSword(AbyssalCraftAPI.dreadiumTool, "dreadiumsword", EnumChatFormatting.DARK_RED);
		ACItems.dreadium_hoe = new ItemACHoe(AbyssalCraftAPI.dreadiumTool, "dreadiumhoe", EnumChatFormatting.DARK_RED);
		ACItems.dreadium_katana_hilt = new ItemDreadiumKatana("dreadhilt", 5.0F, 200);
		ACItems.dreadium_katana = new ItemDreadiumKatana("dreadkatana", 15.0F, 1000);
		ACItems.sacthoths_soul_harvesting_blade = new ItemSoulReaper("soulreaper");
		ACItems.ethaxium_pickaxe = new ItemEthaxiumPickaxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumpickaxe");
		ACItems.ethaxium_axe = new ItemACAxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumaxe", 8, EnumChatFormatting.AQUA);
		ACItems.ethaxium_shovel = new ItemACShovel(AbyssalCraftAPI.ethaxiumTool, "ethaxiumshovel", 8, EnumChatFormatting.AQUA);
		ACItems.ethaxium_sword = new ItemACSword(AbyssalCraftAPI.ethaxiumTool, "ethaxiumsword", EnumChatFormatting.AQUA);
		ACItems.ethaxium_hoe = new ItemACHoe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumhoe", EnumChatFormatting.AQUA);
		ACItems.staff_of_rending = new ItemDrainStaff();

		//Armor
		ACItems.abyssalnite_helmet = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 0, "ahelmet");
		ACItems.abyssalnite_chestplate = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 1, "aplate");
		ACItems.abyssalnite_leggings = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 2, "alegs");
		ACItems.abyssalnite_boots = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, 3, "aboots");
		ACItems.dreaded_abyssalnite_helmet = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 0, "dhelmet");
		ACItems.dreaded_abyssalnite_chestplate = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 1, "dplate");
		ACItems.dreaded_abyssalnite_leggings = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 2, "dlegs");
		ACItems.dreaded_abyssalnite_boots = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, 3, "dboots");
		ACItems.refined_coralium_helmet = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 0, "corhelmet");
		ACItems.refined_coralium_chestplate = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 1, "corplate");
		ACItems.refined_coralium_leggings = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 2, "corlegs");
		ACItems.refined_coralium_boots = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, 3, "corboots");
		ACItems.plated_coralium_helmet = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 0, "corhelmetp");
		ACItems.plated_coralium_chestplate = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 1, "corplatep");
		ACItems.plated_coralium_leggings = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 2, "corlegsp");
		ACItems.plated_coralium_boots = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, 3, "corbootsp");
		ACItems.depths_helmet = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 0, "depthshelmet");
		ACItems.depths_chestplate = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 1, "depthsplate");
		ACItems.depths_leggings = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 2, "depthslegs");
		ACItems.depths_boots = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, 3, "depthsboots");
		ACItems.dreadium_helmet = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 0, "dreadiumhelmet");
		ACItems.dreadium_chestplate = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 1, "dreadiumplate");
		ACItems.dreadium_leggings = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 2, "dreadiumlegs");
		ACItems.dreadium_boots = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, 3, "dreadiumboots");
		ACItems.dreadium_samurai_helmet = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 0, "dreadiumsamuraihelmet");
		ACItems.dreadium_samurai_chestplate = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 1, "dreadiumsamuraiplate");
		ACItems.dreadium_samurai_leggings = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 2, "dreadiumsamurailegs");
		ACItems.dreadium_samurai_boots = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, 3, "dreadiumsamuraiboots");
		ACItems.ethaxium_helmet = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 0, "ethaxiumhelmet");
		ACItems.ethaxium_chestplate = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 1, "ethaxiumplate");
		ACItems.ethaxium_leggings = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 2, "ethaxiumlegs");
		ACItems.ethaxium_boots = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, 3, "ethaxiumboots");

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

		GameRegistry.registerItem(devsword, "devsword");
		GameRegistry.registerItem(ACItems.oblivion_catalyst, "oc");
		GameRegistry.registerItem(ACItems.gateway_key, "gatewaykey");
		GameRegistry.registerItem(ACItems.staff_of_the_gatekeeper, "staff");
		GameRegistry.registerItem(ACItems.liquid_coralium_bucket, "cbucket");
		GameRegistry.registerItem(ACItems.powerstone_tracker, "powerstonetracker");
		GameRegistry.registerItem(ACItems.eye_of_the_abyss, "eoa");
		GameRegistry.registerItem(ACItems.dreaded_gateway_key, "gatewaykeydl");
		GameRegistry.registerItem(ACItems.dreaded_shard_of_abyssalnite, "dreadshard");
		GameRegistry.registerItem(ACItems.dreaded_chunk_of_abyssalnite, "dreadchunk");
		GameRegistry.registerItem(ACItems.chunk_of_abyssalnite, "abychunk");
		GameRegistry.registerItem(ACItems.abyssalnite_ingot, "abyingot");
		GameRegistry.registerItem(ACItems.coralium_gem, "coralium");
		GameRegistry.registerItem(ACItems.coralium_gem_cluster_2, "ccluster2");
		GameRegistry.registerItem(ACItems.coralium_gem_cluster_3, "ccluster3");
		GameRegistry.registerItem(ACItems.coralium_gem_cluster_4, "ccluster4");
		GameRegistry.registerItem(ACItems.coralium_gem_cluster_5, "ccluster5");
		GameRegistry.registerItem(ACItems.coralium_gem_cluster_6, "ccluster6");
		GameRegistry.registerItem(ACItems.coralium_gem_cluster_7, "ccluster7");
		GameRegistry.registerItem(ACItems.coralium_gem_cluster_8, "ccluster8");
		GameRegistry.registerItem(ACItems.coralium_gem_cluster_9, "ccluster9");
		GameRegistry.registerItem(ACItems.coralium_pearl ,"cpearl");
		GameRegistry.registerItem(ACItems.chunk_of_coralium, "cchunk");
		GameRegistry.registerItem(ACItems.refined_coralium_ingot, "cingot");
		GameRegistry.registerItem(ACItems.coralium_plate, "platec");
		GameRegistry.registerItem(ACItems.transmutation_gem, "transmutationgem");
		GameRegistry.registerItem(ACItems.coralium_plagued_flesh, "corflesh");
		GameRegistry.registerItem(ACItems.coralium_plagued_flesh_on_a_bone, "corbone");
		GameRegistry.registerItem(ACItems.darkstone_pickaxe, "dpick");
		GameRegistry.registerItem(ACItems.darkstone_axe, "daxe");
		GameRegistry.registerItem(ACItems.darkstone_shovel, "dshovel");
		GameRegistry.registerItem(ACItems.darkstone_sword, "dsword");
		GameRegistry.registerItem(ACItems.darkstone_hoe, "dhoe");
		GameRegistry.registerItem(ACItems.abyssalnite_pickaxe, "apick");
		GameRegistry.registerItem(ACItems.abyssalnite_axe, "aaxe");
		GameRegistry.registerItem(ACItems.abyssalnite_shovel, "ashovel");
		GameRegistry.registerItem(ACItems.abyssalnite_sword, "asword");
		GameRegistry.registerItem(ACItems.abyssalnite_hoe, "ahoe");
		GameRegistry.registerItem(ACItems.refined_coralium_pickaxe, "corpick");
		GameRegistry.registerItem(ACItems.refined_coralium_axe, "coraxe");
		GameRegistry.registerItem(ACItems.refined_coralium_shovel, "corshovel");
		GameRegistry.registerItem(ACItems.refined_coralium_sword, "corsword");
		GameRegistry.registerItem(ACItems.refined_coralium_hoe, "corhoe");
		GameRegistry.registerItem(ACItems.abyssalnite_helmet, "ahelmet");
		GameRegistry.registerItem(ACItems.abyssalnite_chestplate, "aplate");
		GameRegistry.registerItem(ACItems.abyssalnite_leggings, "alegs");
		GameRegistry.registerItem(ACItems.abyssalnite_boots, "aboots");
		GameRegistry.registerItem(ACItems.dreaded_abyssalnite_helmet, "dhelmet");
		GameRegistry.registerItem(ACItems.dreaded_abyssalnite_chestplate, "dplate");
		GameRegistry.registerItem(ACItems.dreaded_abyssalnite_leggings, "dlegs");
		GameRegistry.registerItem(ACItems.dreaded_abyssalnite_boots, "dboots");
		GameRegistry.registerItem(ACItems.refined_coralium_helmet, "corhelmet");
		GameRegistry.registerItem(ACItems.refined_coralium_chestplate, "corplate");
		GameRegistry.registerItem(ACItems.refined_coralium_leggings, "corlegs");
		GameRegistry.registerItem(ACItems.refined_coralium_boots, "corboots");
		GameRegistry.registerItem(ACItems.plated_coralium_helmet, "corhelmetp");
		GameRegistry.registerItem(ACItems.plated_coralium_chestplate, "corplatep");
		GameRegistry.registerItem(ACItems.plated_coralium_leggings, "corlegsp");
		GameRegistry.registerItem(ACItems.plated_coralium_boots, "corbootsp");
		GameRegistry.registerItem(ACItems.depths_helmet, "depthshelmet");
		GameRegistry.registerItem(ACItems.depths_chestplate, "depthsplate");
		GameRegistry.registerItem(ACItems.depths_leggings, "depthslegs");
		GameRegistry.registerItem(ACItems.depths_boots, "depthsboots");
		GameRegistry.registerItem(ACItems.cobblestone_upgrade_kit, "cobbleu");
		GameRegistry.registerItem(ACItems.iron_upgrade_kit, "ironu");
		GameRegistry.registerItem(ACItems.gold_upgrade_kit, "goldu");
		GameRegistry.registerItem(ACItems.diamond_upgrade_kit, "diamondu");
		GameRegistry.registerItem(ACItems.abyssalnite_upgrade_kit, "abyssalniteu");
		GameRegistry.registerItem(ACItems.coralium_upgrade_kit, "coraliumu");
		GameRegistry.registerItem(ACItems.mre, "mre");
		GameRegistry.registerItem(ACItems.iron_plate, "ironp");
		GameRegistry.registerItem(ACItems.chicken_on_a_plate, "chickenp");
		GameRegistry.registerItem(ACItems.pork_on_a_plate, "porkp");
		GameRegistry.registerItem(ACItems.beef_on_a_plate, "beefp");
		GameRegistry.registerItem(ACItems.fish_on_a_plate, "fishp");
		GameRegistry.registerItem(ACItems.dirty_plate, "dirtyplate");
		GameRegistry.registerItem(ACItems.fried_egg, "friedegg");
		GameRegistry.registerItem(ACItems.fried_egg_on_a_plate, "eggp");
		GameRegistry.registerItem(ACItems.washcloth, "cloth");
		GameRegistry.registerItem(ACItems.shadow_fragment, "shadowfragment");
		GameRegistry.registerItem(ACItems.shadow_shard, "shadowshard");
		GameRegistry.registerItem(ACItems.shadow_gem, "shadowgem");
		GameRegistry.registerItem(ACItems.shard_of_oblivion, "oblivionshard");
		GameRegistry.registerItem(ACItems.coralium_longbow, "corbow");
		GameRegistry.registerItem(ACItems.liquid_antimatter_bucket, "antibucket");
		GameRegistry.registerItem(ACItems.coralium_brick, "cbrick");
		GameRegistry.registerItem(ACItems.cudgel, "cudgel");
		GameRegistry.registerItem(ACItems.dreadium_ingot, "dreadiumingot");
		GameRegistry.registerItem(ACItems.dread_fragment, "dreadfragment");
		GameRegistry.registerItem(ACItems.dreadium_helmet, "dreadiumhelmet");
		GameRegistry.registerItem(ACItems.dreadium_chestplate, "dreadiumplate");
		GameRegistry.registerItem(ACItems.dreadium_leggings, "dreadiumlegs");
		GameRegistry.registerItem(ACItems.dreadium_boots, "dreadiumboots");
		GameRegistry.registerItem(ACItems.dreadium_pickaxe, "dreadiumpickaxe");
		GameRegistry.registerItem(ACItems.dreadium_axe, "dreadiumaxe");
		GameRegistry.registerItem(ACItems.dreadium_shovel, "dreadiumshovel");
		GameRegistry.registerItem(ACItems.dreadium_sword, "dreadiumsword");
		GameRegistry.registerItem(ACItems.dreadium_hoe, "dreadiumhoe");
		GameRegistry.registerItem(ACItems.dreadium_upgrade_kit, "dreadiumu");
		GameRegistry.registerItem(ACItems.carbon_cluster, "carboncluster");
		GameRegistry.registerItem(ACItems.dense_carbon_cluster, "densecarboncluster");
		GameRegistry.registerItem(ACItems.methane, "methane");
		GameRegistry.registerItem(ACItems.nitre, "nitre");
		GameRegistry.registerItem(ACItems.sulfur, "sulfur");
		GameRegistry.registerItem(ACItems.crystal, "crystal");
		GameRegistry.registerItem(ACItems.crystal_shard, "crystalshard");
		GameRegistry.registerItem(ACItems.dread_cloth, "dreadcloth");
		GameRegistry.registerItem(ACItems.dreadium_plate, "dreadplate");
		GameRegistry.registerItem(ACItems.dreadium_katana_blade, "dreadblade");
		GameRegistry.registerItem(ACItems.dreadium_katana_hilt, "dreadhilt");
		GameRegistry.registerItem(ACItems.dreadium_katana, "dreadkatana");
		GameRegistry.registerItem(ACItems.dread_plagued_gateway_key, "dreadkey");
		GameRegistry.registerItem(ACItems.rlyehian_gateway_key, "gatewaykeyjzh");
		GameRegistry.registerItem(ACItems.dreadium_samurai_helmet, "dreadiumsamuraihelmet");
		GameRegistry.registerItem(ACItems.dreadium_samurai_chestplate, "dreadiumsamuraiplate");
		GameRegistry.registerItem(ACItems.dreadium_samurai_leggings, "dreadiumsamurailegs");
		GameRegistry.registerItem(ACItems.dreadium_samurai_boots, "dreadiumsamuraiboots");
		GameRegistry.registerItem(ACItems.tin_ingot, "tiningot");
		GameRegistry.registerItem(ACItems.copper_ingot, "copperingot");
		GameRegistry.registerItem(ACItems.anti_beef, "antibeef");
		GameRegistry.registerItem(ACItems.anti_chicken, "antichicken");
		GameRegistry.registerItem(ACItems.anti_pork, "antipork");
		GameRegistry.registerItem(ACItems.rotten_anti_flesh, "antiflesh");
		GameRegistry.registerItem(ACItems.anti_bone, "antibone");
		GameRegistry.registerItem(ACItems.anti_spider_eye, "antispidereye");
		GameRegistry.registerItem(ACItems.sacthoths_soul_harvesting_blade, "soulreaper");
		GameRegistry.registerItem(ACItems.ethaxium_brick, "ethbrick");
		GameRegistry.registerItem(ACItems.ethaxium_ingot, "ethaxiumingot");
		GameRegistry.registerItem(ACItems.life_crystal, "lifecrystal");
		GameRegistry.registerItem(ACItems.ethaxium_helmet, "ethaxiumhelmet");
		GameRegistry.registerItem(ACItems.ethaxium_chestplate, "ethaxiumplate");
		GameRegistry.registerItem(ACItems.ethaxium_leggings, "ethaxiumlegs");
		GameRegistry.registerItem(ACItems.ethaxium_boots, "ethaxiumboots");
		GameRegistry.registerItem(ACItems.ethaxium_pickaxe, "ethaxiumpickaxe");
		GameRegistry.registerItem(ACItems.ethaxium_axe, "ethaxiumaxe");
		GameRegistry.registerItem(ACItems.ethaxium_shovel, "ethaxiumshovel");
		GameRegistry.registerItem(ACItems.ethaxium_sword, "ethaxiumsword");
		GameRegistry.registerItem(ACItems.ethaxium_hoe, "ethaxiumhoe");
		GameRegistry.registerItem(ACItems.ethaxium_upgrade_kit, "ethaxiumu");
		GameRegistry.registerItem(ACItems.coin, "coin");
		GameRegistry.registerItem(ACItems.cthulhu_engraved_coin, "cthulhucoin");
		GameRegistry.registerItem(ACItems.elder_engraved_coin, "eldercoin");
		GameRegistry.registerItem(ACItems.jzahar_engraved_coin, "jzaharcoin");
		GameRegistry.registerItem(ACItems.blank_engraving, "engraving_blank");
		GameRegistry.registerItem(ACItems.cthulhu_engraving, "engraving_cthulhu");
		GameRegistry.registerItem(ACItems.elder_engraving, "engraving_elder");
		GameRegistry.registerItem(ACItems.jzahar_engraving, "engraving_jzahar");
		GameRegistry.registerItem(ACItems.eldritch_scale, "eldritchscale");
		GameRegistry.registerItem(ACItems.omothol_flesh, "omotholflesh");
		GameRegistry.registerItem(ACItems.anti_plagued_flesh, "anticorflesh");
		GameRegistry.registerItem(ACItems.anti_plagued_flesh_on_a_bone, "anticorbone");
		GameRegistry.registerItem(ACItems.necronomicon, "necronomicon");
		GameRegistry.registerItem(ACItems.abyssal_wasteland_necronomicon, "necronomicon_cor");
		GameRegistry.registerItem(ACItems.dreadlands_necronomicon, "necronomicon_dre");
		GameRegistry.registerItem(ACItems.omothol_necronomicon, "necronomicon_omt");
		GameRegistry.registerItem(ACItems.abyssalnomicon, "abyssalnomicon");
		GameRegistry.registerItem(ACItems.small_crystal_bag, "crystalbag_small");
		GameRegistry.registerItem(ACItems.medium_crystal_bag, "crystalbag_medium");
		GameRegistry.registerItem(ACItems.large_crystal_bag, "crystalbag_large");
		GameRegistry.registerItem(ACItems.huge_crystal_bag, "crystalbag_huge");
		GameRegistry.registerItem(ACItems.shoggoth_flesh, "shoggothflesh");
		GameRegistry.registerItem(ACItems.ingot_nugget, "ingotnugget");
		GameRegistry.registerItem(ACItems.staff_of_rending, "drainstaff");
		GameRegistry.registerItem(ACItems.essence, "essence");
		GameRegistry.registerItem(ACItems.skin, "skin");
		GameRegistry.registerItem(ACItems.ritual_charm, "charm");
		GameRegistry.registerItem(ACItems.cthulhu_charm, "cthulhucharm");
		GameRegistry.registerItem(ACItems.hastur_charm, "hasturcharm");
		GameRegistry.registerItem(ACItems.jzahar_charm, "jzaharcharm");
		GameRegistry.registerItem(ACItems.azathoth_charm, "azathothcharm");
		GameRegistry.registerItem(ACItems.nyarlathotep_charm, "nyarlathotepcharm");
		GameRegistry.registerItem(ACItems.yog_sothoth_charm, "yogsothothcharm");
		GameRegistry.registerItem(ACItems.shub_niggurath_charm, "shubniggurathcharm");
		GameRegistry.registerItem(ACItems.hastur_engraved_coin, "hasturcoin");
		GameRegistry.registerItem(ACItems.azathoth_engraved_coin, "azathothcoin");
		GameRegistry.registerItem(ACItems.nyarlathotep_engraved_coin, "nyarlathotepcoin");
		GameRegistry.registerItem(ACItems.yog_sothoth_engraved_coin, "yogsothothcoin");
		GameRegistry.registerItem(ACItems.shub_niggurath_engraved_coin, "shubniggurathcoin");
		GameRegistry.registerItem(ACItems.hastur_engraving, "engraving_hastur");
		GameRegistry.registerItem(ACItems.azathoth_engraving, "engraving_azathoth");
		GameRegistry.registerItem(ACItems.nyarlathotep_engraving, "engraving_nyarlathotep");
		GameRegistry.registerItem(ACItems.yog_sothoth_engraving, "engraving_yogsothoth");
		GameRegistry.registerItem(ACItems.shub_niggurath_engraving, "engraving_shubniggurath");
		GameRegistry.registerItem(ACItems.essence_of_the_gatekeeper, "gatekeeperessence");
		GameRegistry.registerItem(ACItems.interdimensional_cage, "interdimensionalcage");
		//		GameRegistry.registerItem(shadowPlate, "shadowplate");

		AbyssalCraftAPI.setRepairItems();
	}

	@Override
	public void init(FMLInitializationEvent event) {}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {}
}
