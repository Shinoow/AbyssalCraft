/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.init;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ItemEngraving;
import com.shinoow.abyssalcraft.api.necronomicon.condition.*;
import com.shinoow.abyssalcraft.common.items.*;
import com.shinoow.abyssalcraft.common.items.armor.*;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.item.ItemCharm;
import com.shinoow.abyssalcraft.lib.item.ItemMetadata;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.*;

public class ItemHandler implements ILifeCycleHandler {

	public static Item devsword, shadowPlate, shoggoth_projectile;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		//"secret" dev stuff
		devsword = new AbyssalCraftTool();
		shoggoth_projectile = new Item().setTranslationKey("shoggoth_projectile");

		//Misc items
		ACItems.oblivion_catalyst = new ItemOC();
		ACItems.staff_of_the_gatekeeper = new ItemStaff().setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.gateway_key = new ItemGatewayKey(0, "gatewaykey");
		ACItems.powerstone_tracker = new ItemTrackerPSDL().setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.eye_of_the_abyss = new ItemEoA().setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.dreaded_gateway_key = new ItemGatewayKey(1, "gatewaykeydl").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.coralium_brick = new ItemACBasic("cbrick").setUnlockCondition(new MultiBiomeCondition(ACBiomes.abyssal_wastelands, ACBiomes.coralium_infested_swamp));
		ACItems.cudgel = new ItemCudgel().setUnlockCondition(new EntityCondition("abyssalcraft:gskeleton"));
		ACItems.carbon_cluster = new ItemACBasic("carboncluster").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dense_carbon_cluster = new ItemACBasic("densecarboncluster").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.methane = new ItemACBasic("methane");
		ACItems.nitre = new ItemACBasic("nitre");
		ACItems.sulfur = new ItemACBasic("sulfur");
		ACItems.rlyehian_gateway_key = new ItemGatewayKey(2, "gatewaykeyjzh").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.tin_ingot = new ItemACBasic("tiningot");
		ACItems.copper_ingot = new ItemACBasic("copperingot");
		ACItems.life_crystal = new ItemACBasic("lifecrystal").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.shoggoth_flesh = new ItemMetadata("shoggothflesh", "overworld", "abyssalwasteland", "dreadlands", "omothol", "darkrealm");
		ACItems.eldritch_scale = new ItemACBasic("eldritchscale").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.omothol_flesh = new ItemOmotholFlesh(3, 0.3F, false).setUnlockCondition(new EntityCondition("abyssalcraft:omotholghoul"));
		ACItems.necronomicon = new ItemNecronomicon("necronomicon", 0);
		ACItems.abyssal_wasteland_necronomicon = new ItemNecronomicon("necronomicon_cor", 1).setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.dreadlands_necronomicon = new ItemNecronomicon("necronomicon_dre", 2).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.omothol_necronomicon = new ItemNecronomicon("necronomicon_omt", 3).setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.abyssalnomicon = new ItemNecronomicon("abyssalnomicon", 4).setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.small_crystal_bag = new ItemCrystalBag("crystalbag_small").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.medium_crystal_bag = new ItemCrystalBag("crystalbag_medium").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.large_crystal_bag = new ItemCrystalBag("crystalbag_large").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.huge_crystal_bag = new ItemCrystalBag("crystalbag_huge").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.ingot_nugget = new ItemMetadataMisc("nugget", "abyssalnite", "coralium", "dreadium", "ethaxium");
		ACItems.essence = new ItemMetadataMisc("essence", "abyssalwasteland", "dreadlands", "omothol");
		ACItems.skin = new ItemMetadataMisc("skin", "abyssalwasteland", "dreadlands", "omothol");
		ACItems.essence_of_the_gatekeeper = new ItemGatekeeperEssence().setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.interdimensional_cage = new ItemInterdimensionalCage().setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.stone_tablet = new ItemStoneTablet();
		ACItems.scroll = new ItemScroll("scroll", "basic", "lesser", "moderate", "greater");
		ACItems.unique_scroll = new ItemScroll("unique_scroll", "antimatter", "oblivion");
		ACItems.antidote = new ItemAntidote();
		ACItems.darklands_oak_door = new ItemDoor(ACBlocks.darklands_oak_door).setTranslationKey("door_dlt");
		ACItems.dreadlands_door = new ItemDoor(ACBlocks.dreadlands_door).setTranslationKey("door_drt");
		ACItems.configurator_shard = new ItemMetadataMisc("configurator_shard", "0", "1", "2", "3").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.silver_key = new ItemGatewayKey(3, "silver_key");
		ACItems.book_of_many_faces = new ItemFaceBook("face_book");

		//Coins
		ACItems.coin = new ItemCoin("blankcoin").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.cthulhu_engraved_coin = new ItemCoin("cthulhucoin").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.elder_engraved_coin = new ItemCoin("eldercoin").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.jzahar_engraved_coin = new ItemCoin("jzaharcoin").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.blank_engraving = new ItemEngraving("blank", 50).setUnlockCondition(new DimensionCondition(ACLib.omothol_id)).setCreativeTab(ACTabs.tabCoins);
		ACItems.cthulhu_engraving = new ItemEngraving("cthulhu", 10).setUnlockCondition(new DimensionCondition(ACLib.omothol_id)).setCreativeTab(ACTabs.tabCoins);
		ACItems.elder_engraving = new ItemEngraving("elder", 10).setUnlockCondition(new DimensionCondition(ACLib.omothol_id)).setCreativeTab(ACTabs.tabCoins);
		ACItems.jzahar_engraving = new ItemEngraving("jzahar", 10).setUnlockCondition(new DimensionCondition(ACLib.omothol_id)).setCreativeTab(ACTabs.tabCoins);
		ACItems.hastur_engraved_coin = new ItemCoin("hasturcoin").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.azathoth_engraved_coin = new ItemCoin("azathothcoin").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.nyarlathotep_engraved_coin = new ItemCoin("nyarlathotepcoin").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.yog_sothoth_engraved_coin = new ItemCoin("yogsothothcoin").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.shub_niggurath_engraved_coin = new ItemCoin("shubniggurathcoin").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.hastur_engraving = new ItemEngraving("hastur", 10).setUnlockCondition(new DimensionCondition(ACLib.omothol_id)).setCreativeTab(ACTabs.tabCoins);
		ACItems.azathoth_engraving = new ItemEngraving("azathoth", 10).setUnlockCondition(new DimensionCondition(ACLib.omothol_id)).setCreativeTab(ACTabs.tabCoins);
		ACItems.nyarlathotep_engraving = new ItemEngraving("nyarlathotep", 10).setUnlockCondition(new DimensionCondition(ACLib.omothol_id)).setCreativeTab(ACTabs.tabCoins);
		ACItems.yog_sothoth_engraving = new ItemEngraving("yogsothoth", 10).setUnlockCondition(new DimensionCondition(ACLib.omothol_id)).setCreativeTab(ACTabs.tabCoins);
		ACItems.shub_niggurath_engraving = new ItemEngraving("shubniggurath", 10).setUnlockCondition(new DimensionCondition(ACLib.omothol_id)).setCreativeTab(ACTabs.tabCoins);

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
		ACItems.ethaxium_brick = new ItemACBasic("ethbrick").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.ethaxium_ingot = new ItemACBasic("ethaxiumingot").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));

		//anti-items
		ACItems.anti_beef = new ItemAntiFood("antibeef").setUnlockCondition(new BiomeCondition(ACBiomes.coralium_infested_swamp));
		ACItems.anti_chicken = new ItemAntiFood("antichicken").setUnlockCondition(new BiomeCondition(ACBiomes.coralium_infested_swamp));
		ACItems.anti_pork = new ItemAntiFood("antipork").setUnlockCondition(new BiomeCondition(ACBiomes.coralium_infested_swamp));
		ACItems.rotten_anti_flesh = new ItemAntiFood("antiflesh").setUnlockCondition(new BiomeCondition(ACBiomes.coralium_infested_swamp));
		ACItems.anti_bone = new ItemACBasic("antibone").setUnlockCondition(new BiomeCondition(ACBiomes.coralium_infested_swamp));
		ACItems.anti_spider_eye = new ItemAntiFood("antispidereye", false).setUnlockCondition(new BiomeCondition(ACBiomes.coralium_infested_swamp));
		ACItems.anti_plagued_flesh = new ItemCorflesh(0, 0, false, "anticorflesh").setUnlockCondition(new BiomeCondition(ACBiomes.coralium_infested_swamp));
		ACItems.anti_plagued_flesh_on_a_bone = new ItemCorbone(0, 0, false, "anticorbone").setUnlockCondition(new BiomeCondition(ACBiomes.coralium_infested_swamp));

		//crystals
		ACItems.crystal = new ItemCrystal("crystal").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.crystal_shard = new ItemCrystal("crystalshard", true).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.crystal_fragment = new ItemCrystal("crystalfragment", true).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));

		//Shadow items
		ACItems.shadow_fragment = new ItemACBasic("shadowfragment").setUnlockCondition(new MultiEntityCondition("abyssalcraft:shadowcreature", "abyssalcraft:shadowmonster","abyssalcraft:shadowbeast"));
		ACItems.shadow_shard = new ItemACBasic("shadowshard").setUnlockCondition(new MultiEntityCondition("abyssalcraft:shadowcreature", "abyssalcraft:shadowmonster","abyssalcraft:shadowbeast"));
		ACItems.shadow_gem = new ItemACBasic("shadowgem").setUnlockCondition(new MultiEntityCondition("abyssalcraft:shadowcreature", "abyssalcraft:shadowmonster","abyssalcraft:shadowbeast"));
		ACItems.shard_of_oblivion = new ItemACBasic("oblivionshard").setUnlockCondition(new MultiEntityCondition("abyssalcraft:shadowcreature", "abyssalcraft:shadowmonster","abyssalcraft:shadowbeast"));
		shadowPlate = new ItemACBasic("shadowplate");

		//Dread items
		ACItems.dreaded_shard_of_abyssalnite = new ItemACBasic("dreadshard").setUnlockCondition(new MultiEntityCondition("abyssalcraft:dreadguard", "abyssalcraft:greaterdreadspawn", "abyssalcraft:lesserdreadbeast"));
		ACItems.dreaded_chunk_of_abyssalnite = new ItemACBasic("dreadchunk").setUnlockCondition(new EntityCondition("abyssalcraft:dreadgolem"));
		ACItems.dreadium_ingot = new ItemACBasic("dreadiumingot").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dread_fragment = new ItemACBasic("dreadfragment").setUnlockCondition(new EntityPredicateCondition(e -> IDreadEntity.class.isAssignableFrom(e)));
		ACItems.dread_cloth = new ItemACBasic("dreadcloth").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_plate = new ItemACBasic("dreadplate").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_katana_blade = new ItemACBasic("dreadblade").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dread_plagued_gateway_key = new ItemACBasic("dreadkey").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.charcoal = new ItemACBasic("cha_rcoal").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));

		//Abyssalnite items
		ACItems.chunk_of_abyssalnite = new ItemACBasic("abychunk").setUnlockCondition(new EntityCondition("abyssalcraft:abygolem"));
		ACItems.abyssalnite_ingot = new ItemACBasic("abyingot").setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));

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
		ACItems.chunk_of_coralium = new ItemACBasic("cchunk").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.refined_coralium_ingot = new ItemACBasic("cingot").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.coralium_plate = new ItemACBasic("platec").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.coralium_gem = new ItemACBasic("coralium");
		ACItems.transmutation_gem = new ItemCorb();
		ACItems.coralium_plagued_flesh = new ItemCorflesh(2, 0.1F, false, "corflesh").setUnlockCondition(new EntityCondition("abyssalcraft:abyssalzombie"));
		ACItems.coralium_plagued_flesh_on_a_bone = new ItemCorbone(2, 0.1F, false, "corbone").setUnlockCondition(new EntityCondition("abyssalcraft:depthsghoul"));
		ACItems.coralium_longbow = new ItemCoraliumBow(20.0F, 0, 8, 16).setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));

		//Tools
		ACItems.darkstone_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.darkstoneTool, "dpick", 1);
		ACItems.darkstone_axe = new ItemACAxe(AbyssalCraftAPI.darkstoneTool, "daxe", 1);
		ACItems.darkstone_shovel = new ItemACShovel(AbyssalCraftAPI.darkstoneTool, "dshovel", 1);
		ACItems.darkstone_sword = new ItemACSword(AbyssalCraftAPI.darkstoneTool, "dsword");
		ACItems.darkstone_hoe = new ItemACHoe(AbyssalCraftAPI.darkstoneTool, "dhoe");
		ACItems.abyssalnite_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.abyssalniteTool, "apick", 4, TextFormatting.DARK_AQUA).setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));
		ACItems.abyssalnite_axe = new ItemACAxe(AbyssalCraftAPI.abyssalniteTool, "aaxe", 4, TextFormatting.DARK_AQUA).setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));
		ACItems.abyssalnite_shovel = new ItemACShovel(AbyssalCraftAPI.abyssalniteTool, "ashovel", 4, TextFormatting.DARK_AQUA).setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));
		ACItems.abyssalnite_sword = new ItemACSword(AbyssalCraftAPI.abyssalniteTool, "asword", TextFormatting.DARK_AQUA).setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));
		ACItems.abyssalnite_hoe = new ItemACHoe(AbyssalCraftAPI.abyssalniteTool, "ahoe", TextFormatting.DARK_AQUA).setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));
		ACItems.refined_coralium_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.refinedCoraliumTool, "corpick", 5, TextFormatting.AQUA).setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.refined_coralium_axe = new ItemACAxe(AbyssalCraftAPI.refinedCoraliumTool, "coraxe", 5, TextFormatting.AQUA).setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.refined_coralium_shovel = new ItemACShovel(AbyssalCraftAPI.refinedCoraliumTool, "corshovel", 5, TextFormatting.AQUA).setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.refined_coralium_sword = new ItemACSword(AbyssalCraftAPI.refinedCoraliumTool, "corsword", TextFormatting.AQUA).setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.refined_coralium_hoe = new ItemACHoe(AbyssalCraftAPI.refinedCoraliumTool, "corhoe", TextFormatting.AQUA).setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.dreadium_pickaxe = new ItemACPickaxe(AbyssalCraftAPI.dreadiumTool, "dreadiumpickaxe", 6, TextFormatting.DARK_RED).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_axe = new ItemACAxe(AbyssalCraftAPI.dreadiumTool, "dreadiumaxe", 6, TextFormatting.DARK_RED).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_shovel = new ItemACShovel(AbyssalCraftAPI.dreadiumTool, "dreadiumshovel", 6, TextFormatting.DARK_RED).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_sword = new ItemACSword(AbyssalCraftAPI.dreadiumTool, "dreadiumsword", TextFormatting.DARK_RED).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_hoe = new ItemACHoe(AbyssalCraftAPI.dreadiumTool, "dreadiumhoe", TextFormatting.DARK_RED).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_katana_hilt = new ItemDreadiumKatana("dreadhilt", ItemDreadiumKatana.hilt).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_katana = new ItemDreadiumKatana("dreadkatana", ItemDreadiumKatana.katana).setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.sacthoths_soul_harvesting_blade = new ItemSoulReaper("soulreaper").setUnlockCondition(new EntityCondition("abyssalcraft:shadowboss"));
		ACItems.ethaxium_pickaxe = new ItemEthaxiumPickaxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumpickaxe").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.ethaxium_axe = new ItemACAxe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumaxe", 8, TextFormatting.AQUA).setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.ethaxium_shovel = new ItemACShovel(AbyssalCraftAPI.ethaxiumTool, "ethaxiumshovel", 8, TextFormatting.AQUA).setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.ethaxium_sword = new ItemACSword(AbyssalCraftAPI.ethaxiumTool, "ethaxiumsword", TextFormatting.AQUA).setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.ethaxium_hoe = new ItemACHoe(AbyssalCraftAPI.ethaxiumTool, "ethaxiumhoe", TextFormatting.AQUA).setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.staff_of_rending = new ItemStaffOfRending().setUnlockCondition(new MultiEntityCondition("abyssalcraft:shadowcreature", "abyssalcraft:shadowmonster","abyssalcraft:shadowbeast"));
		ACItems.configurator = new ItemConfigurator().setUnlockCondition(new DimensionCondition(ACLib.omothol_id));

		//Armor
		ACItems.abyssalnite_helmet = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.HEAD, "ahelmet").setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));
		ACItems.abyssalnite_chestplate = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.CHEST, "aplate").setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));
		ACItems.abyssalnite_leggings = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.LEGS, "alegs").setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));
		ACItems.abyssalnite_boots = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.FEET, "aboots").setUnlockCondition(new BiomePredicateCondition(b -> b instanceof IDarklandsBiome));
		ACItems.dreaded_abyssalnite_helmet = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.HEAD, "dhelmet").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreaded_abyssalnite_chestplate = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.CHEST, "dplate").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreaded_abyssalnite_leggings = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.LEGS, "dlegs").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreaded_abyssalnite_boots = new ItemDreadArmor(AbyssalCraftAPI.dreadedAbyssalniteArmor, 5, EntityEquipmentSlot.FEET, "dboots").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.refined_coralium_helmet = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.HEAD, "corhelmet").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.refined_coralium_chestplate = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.CHEST, "corplate").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.refined_coralium_leggings = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.LEGS, "corlegs").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.refined_coralium_boots = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.FEET, "corboots").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.plated_coralium_helmet = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.HEAD, "corhelmetp").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.plated_coralium_chestplate = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.CHEST, "corplatep").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.plated_coralium_leggings = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.LEGS, "corlegsp").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.plated_coralium_boots = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.FEET, "corbootsp").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.depths_helmet = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.HEAD, "depthshelmet").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.depths_chestplate = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.CHEST, "depthsplate").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.depths_leggings = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.LEGS, "depthslegs").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.depths_boots = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.FEET, "depthsboots").setUnlockCondition(new DimensionCondition(ACLib.abyssal_wasteland_id));
		ACItems.dreadium_helmet = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.HEAD, "dreadiumhelmet").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_chestplate = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.CHEST, "dreadiumplate").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_leggings = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.LEGS, "dreadiumlegs").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_boots = new ItemDreadiumArmor(AbyssalCraftAPI.dreadiumArmor, 5, EntityEquipmentSlot.FEET, "dreadiumboots").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_samurai_helmet = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.HEAD, "dreadiumsamuraihelmet").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_samurai_chestplate = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.CHEST, "dreadiumsamuraiplate").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_samurai_leggings = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.LEGS, "dreadiumsamurailegs").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.dreadium_samurai_boots = new ItemDreadiumSamuraiArmor(AbyssalCraftAPI.dreadiumSamuraiArmor, 5, EntityEquipmentSlot.FEET, "dreadiumsamuraiboots").setUnlockCondition(new DimensionCondition(ACLib.dreadlands_id));
		ACItems.ethaxium_helmet = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.HEAD, "ethaxiumhelmet").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.ethaxium_chestplate = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.CHEST, "ethaxiumplate").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.ethaxium_leggings = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.LEGS, "ethaxiumlegs").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));
		ACItems.ethaxium_boots = new ItemEthaxiumArmor(AbyssalCraftAPI.ethaxiumArmor, 5, EntityEquipmentSlot.FEET, "ethaxiumboots").setUnlockCondition(new DimensionCondition(ACLib.omothol_id));

		//Food
		ACItems.generic_meat = new ItemFood(4, 0.4f, true).setTranslationKey("generic_meat").setCreativeTab(ACTabs.tabFood);
		ACItems.cooked_generic_meat = new ItemFood(9, 0.9f, true).setTranslationKey("cooked_generic_meat").setCreativeTab(ACTabs.tabFood);

		registerItem(devsword, "devsword");
		registerItem(shoggoth_projectile, "shoggoth_projectile");

		registerItem(ACItems.oblivion_catalyst, "oc");
		registerItem(ACItems.gateway_key, "gatewaykey");
		registerItem(ACItems.staff_of_the_gatekeeper, "staff");
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
		registerItem(ACItems.shadow_fragment, "shadowfragment");
		registerItem(ACItems.shadow_shard, "shadowshard");
		registerItem(ACItems.shadow_gem, "shadowgem");
		registerItem(ACItems.shard_of_oblivion, "oblivionshard");
		registerItem(ACItems.coralium_longbow, "corbow");
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
		registerItem(ACItems.crystal_fragment, "crystalfragment");
		registerItem(ACItems.stone_tablet, "stonetablet");
		registerItem(ACItems.scroll, "scroll");
		registerItem(ACItems.unique_scroll, "unique_scroll");
		registerItem(ACItems.antidote, "antidote");
		registerItem(ACItems.darklands_oak_door, "door_dlt");
		registerItem(ACItems.dreadlands_door, "door_drt");
		registerItem(ACItems.charcoal, "charcoal");
		registerItem(ACItems.configurator, "configurator");
		registerItem(ACItems.configurator_shard, "configurator_shard");
		registerItem(ACItems.silver_key, "silver_key");
		registerItem(ACItems.book_of_many_faces, "face_book");
		registerItem(ACItems.generic_meat, "generic_meat");
		registerItem(ACItems.cooked_generic_meat, "cooked_generic_meat");
		//		registerItem(shadowPlate, "shadowplate");
	}

	@Override
	public void init(FMLInitializationEvent event) {

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {}

	private static void registerItem(Item item, String name){
		InitHandler.INSTANCE.ITEMS.add(item.setRegistryName(new ResourceLocation(AbyssalCraft.modid, name)));
	}
}
