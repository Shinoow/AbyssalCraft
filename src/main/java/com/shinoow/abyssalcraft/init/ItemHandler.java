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
package com.shinoow.abyssalcraft.init;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;
import com.shinoow.abyssalcraft.common.items.*;
import com.shinoow.abyssalcraft.common.items.armor.*;
import com.shinoow.abyssalcraft.common.util.SoftDepUtil;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.Crystals;
import com.shinoow.abyssalcraft.lib.item.*;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.event.*;

public class ItemHandler implements ILifeCycleHandler {

	public static Item devsword, shoggoth_projectile;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		//"secret" dev stuff
		devsword = new AbyssalCraftTool();
		shoggoth_projectile = new Item().setTranslationKey("shoggoth_projectile");

		//Misc items
		ACItems.oblivion_catalyst = new ItemOC();
		ACItems.staff_of_the_gatekeeper = new ItemStaff();
		ACItems.gateway_key = new ItemGatewayKey(0, "gatewaykey");
		ACItems.powerstone_tracker = new ItemPowerstoneTracker();
		ACItems.eye_of_the_abyss = new ItemACBasic("eoa", TextFormatting.AQUA).setMaxStackSize(1);
		ACItems.dreadlands_infused_gateway_key = new ItemGatewayKey(1, "gatewaykeydl");
		ACItems.coralium_brick = new ItemACBasic("cbrick");
		ACItems.cudgel = new ItemCudgel();
		ACItems.carbon_cluster = new ItemACBasic("carboncluster");
		ACItems.dense_carbon_cluster = new ItemACBasic("densecarboncluster");
		ACItems.methane = new ItemACBasic("methane");
		ACItems.nitre = new ItemACBasic("nitre");
		ACItems.sulfur = new ItemACBasic("sulfur");
		ACItems.omothol_forged_gateway_key = new ItemGatewayKey(2, "gatewaykeyjzh");
		ACItems.life_crystal = new ItemACBasic("lifecrystal");
		ACItems.overworld_shoggoth_flesh = new ItemACBasic("shoggothflesh.overworld");
		ACItems.abyssal_shoggoth_flesh = new ItemACBasic("shoggothflesh.abyssalwasteland");
		ACItems.dreaded_shoggoth_flesh = new ItemACBasic("shoggothflesh.dreadlands");
		ACItems.omothol_shoggoth_flesh = new ItemACBasic("shoggothflesh.omothol");
		ACItems.shadow_shoggoth_flesh = new ItemACBasic("shoggothflesh.darkrealm");
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
		ACItems.abyssalnite_nugget = new ItemACBasic("nugget.abyssalnite");
		ACItems.refined_coralium_nugget = new ItemACBasic("nugget.coralium");
		ACItems.dreadium_nugget = new ItemACBasic("nugget.dreadium");
		ACItems.ethaxium_nugget = new ItemACBasic("nugget.ethaxium");
		ACItems.abyssal_wasteland_essence = new ItemACBasic("essence.abyssalwasteland");
		ACItems.dreadlands_essence = new ItemACBasic("essence.dreadlands");
		ACItems.omothol_essence = new ItemACBasic("essence.omothol");
		ACItems.skin_of_the_abyssal_wasteland = new ItemACBasic("skin.abyssalwasteland");
		ACItems.skin_of_the_dreadlands = new ItemACBasic("skin.dreadlands");
		ACItems.skin_of_omothol = new ItemACBasic("skin.omothol");
		ACItems.essence_of_the_gatekeeper = new ItemGatekeeperEssence();
		ACItems.interdimensional_cage = new ItemInterdimensionalCage();
		ACItems.stone_tablet = new ItemStoneTablet();
		ACItems.basic_scroll = new ItemScroll("basic_scroll", ScrollType.BASIC);
		ACItems.lesser_scroll = new ItemScroll("lesser_scroll", ScrollType.LESSER);
		ACItems.moderate_scroll = new ItemScroll("moderate_scroll", ScrollType.MODERATE);
		ACItems.greater_scroll = new ItemScroll("greater_scroll", ScrollType.GREATER);
		ACItems.antimatter_scroll = new ItemScroll("antimatter_scroll", ScrollType.UNIQUE);
		ACItems.oblivion_scroll = new ItemScroll("oblivion_scroll", ScrollType.UNIQUE);
		ACItems.coralium_plague_antidote = new ItemAntidote("antidote.coralium");
		ACItems.dread_plague_antidote = new ItemAntidote("antidote.dread");
		ACItems.darklands_oak_door = new ItemDoor(ACBlocks.darklands_oak_door).setTranslationKey("door_dlt");
		ACItems.dreadlands_door = new ItemDoor(ACBlocks.dreadwood_door).setTranslationKey("door_drt");
		ACItems.spirit_tablet_shard_0 = new ItemACBasic("configurator_shard_0");
		ACItems.spirit_tablet_shard_1 = new ItemACBasic("configurator_shard_1");
		ACItems.spirit_tablet_shard_2 = new ItemACBasic("configurator_shard_2");
		ACItems.spirit_tablet_shard_3 = new ItemACBasic("configurator_shard_3");
		ACItems.silver_key = new ItemGatewayKey(3, "silver_key");
		ACItems.book_of_many_faces = new ItemFaceBook("face_book");
		ACItems.lost_page = new ItemPage();
		ACItems.scriptures_of_omniscience = new ItemScriptures();
		ACItems.sealing_key = new ItemACBasic("sealing_key").setCreativeTab(ACTabs.tabTools);

		//Coins
		ACItems.coin = new ItemCoin("blankcoin");
		ACItems.token_of_jzahar = new ItemCoin("jzaharcoin");

		//Charms
		ACItems.ritual_charm = new ItemCharm("ritualcharm", null);
		ACItems.range_ritual_charm = new ItemCharm("ritualcharm", AmplifierType.RANGE);
		ACItems.duration_ritual_charm = new ItemCharm("ritualcharm", AmplifierType.DURATION);
		ACItems.power_ritual_charm = new ItemCharm("ritualcharm", AmplifierType.POWER);
		ACItems.cthulhu_charm = new ItemCharm("cthulhucharm", null, DeityType.CTHULHU);
		ACItems.range_cthulhu_charm = new ItemCharm("cthulhucharm", AmplifierType.RANGE, DeityType.CTHULHU);
		ACItems.duration_cthulhu_charm = new ItemCharm("cthulhucharm", AmplifierType.DURATION, DeityType.CTHULHU);
		ACItems.power_cthulhu_charm = new ItemCharm("cthulhucharm", AmplifierType.POWER, DeityType.CTHULHU);
		ACItems.hastur_charm = new ItemCharm("hasturcharm", null, DeityType.HASTUR);
		ACItems.range_hastur_charm = new ItemCharm("hasturcharm", AmplifierType.RANGE, DeityType.HASTUR);
		ACItems.duration_hastur_charm = new ItemCharm("hasturcharm", AmplifierType.DURATION, DeityType.HASTUR);
		ACItems.power_hastur_charm = new ItemCharm("hasturcharm", AmplifierType.POWER, DeityType.HASTUR);
		ACItems.jzahar_charm = new ItemCharm("jzaharcharm", null, DeityType.JZAHAR);
		ACItems.range_jzahar_charm = new ItemCharm("jzaharcharm", AmplifierType.RANGE, DeityType.JZAHAR);
		ACItems.duration_jzahar_charm = new ItemCharm("jzaharcharm", AmplifierType.DURATION, DeityType.JZAHAR);
		ACItems.power_jzahar_charm = new ItemCharm("jzaharcharm", AmplifierType.POWER, DeityType.JZAHAR);
		ACItems.azathoth_charm = new ItemCharm("azathothcharm", null, DeityType.AZATHOTH);
		ACItems.range_azathoth_charm = new ItemCharm("azathothcharm", AmplifierType.RANGE, DeityType.AZATHOTH);
		ACItems.duration_azathoth_charm = new ItemCharm("azathothcharm", AmplifierType.DURATION, DeityType.AZATHOTH);
		ACItems.power_azathoth_charm = new ItemCharm("azathothcharm", AmplifierType.POWER, DeityType.AZATHOTH);
		ACItems.nyarlathotep_charm = new ItemCharm("nyarlathotepcharm", null, DeityType.NYARLATHOTEP);
		ACItems.range_nyarlathotep_charm = new ItemCharm("nyarlathotepcharm", AmplifierType.RANGE, DeityType.NYARLATHOTEP);
		ACItems.duration_nyarlathotep_charm = new ItemCharm("nyarlathotepcharm", AmplifierType.DURATION, DeityType.NYARLATHOTEP);
		ACItems.power_nyarlathotep_charm = new ItemCharm("nyarlathotepcharm", AmplifierType.POWER, DeityType.NYARLATHOTEP);
		ACItems.yog_sothoth_charm = new ItemCharm("yogsothothcharm", null, DeityType.YOGSOTHOTH);
		ACItems.range_yog_sothoth_charm = new ItemCharm("yogsothothcharm", AmplifierType.RANGE, DeityType.YOGSOTHOTH);
		ACItems.duration_yog_sothoth_charm = new ItemCharm("yogsothothcharm", AmplifierType.DURATION, DeityType.YOGSOTHOTH);
		ACItems.power_yog_sothoth_charm = new ItemCharm("yogsothothcharm", AmplifierType.POWER, DeityType.YOGSOTHOTH);
		ACItems.shub_niggurath_charm = new ItemCharm("shubniggurathcharm", null, DeityType.SHUBNIGGURATH);
		ACItems.range_shub_niggurath_charm = new ItemCharm("shubniggurathcharm", AmplifierType.RANGE, DeityType.SHUBNIGGURATH);
		ACItems.duration_shub_niggurath_charm = new ItemCharm("shubniggurathcharm", AmplifierType.DURATION, DeityType.SHUBNIGGURATH);
		ACItems.power_shub_niggurath_charm = new ItemCharm("shubniggurathcharm", AmplifierType.POWER, DeityType.SHUBNIGGURATH);

		//Ethaxium
		ACItems.ethaxium_brick = new ItemACBasic("ethbrick");
		ACItems.ethaxium_ingot = new ItemACBasic("ethaxiumingot");

		//anti-items
		ACItems.anti_beef = new ItemAntiFood("antibeef");
		ACItems.anti_chicken = new ItemAntiFood("antichicken");
		ACItems.anti_pork = new ItemAntiFood("antipork");
		ACItems.rotten_anti_flesh = new ItemAntiFood("antiflesh");
		ACItems.anti_bone = new ItemACBasic("antibone");
		ACItems.anti_spider_eye = new ItemAntiFood("antispidereye", false);
		ACItems.anti_plagued_flesh = new ItemCorflesh(0, 0, false, "anticorflesh");
		ACItems.anti_plagued_flesh_on_a_bone = new ItemCorbone(0, 0, false, "anticorbone");

		//crystals
		ACItems.crystal_iron = new ItemCrystal("crystal", Crystals.IRON);
		ACItems.crystal_gold = new ItemCrystal("crystal", Crystals.GOLD);
		ACItems.crystal_sulfur = new ItemCrystal("crystal", Crystals.SULFUR);
		ACItems.crystal_carbon = new ItemCrystal("crystal", Crystals.CARBON);
		ACItems.crystal_oxygen = new ItemCrystal("crystal", Crystals.OXYGEN);
		ACItems.crystal_hydrogen = new ItemCrystal("crystal", Crystals.HYDROGEN);
		ACItems.crystal_nitrogen = new ItemCrystal("crystal", Crystals.NITROGEN);
		ACItems.crystal_phosphorus = new ItemCrystal("crystal", Crystals.PHOSPHORUS);
		ACItems.crystal_potassium = new ItemCrystal("crystal", Crystals.POTASSIUM);
		ACItems.crystal_nitrate = new ItemCrystal("crystal", Crystals.NITRATE);
		ACItems.crystal_methane = new ItemCrystal("crystal", Crystals.METHANE);
		ACItems.crystal_redstone = new ItemCrystal("crystal", Crystals.REDSTONE);
		ACItems.crystal_abyssalnite = new ItemCrystal("crystal", Crystals.ABYSSALNITE);
		ACItems.crystal_coralium = new ItemCrystal("crystal", Crystals.CORALIUM);
		ACItems.crystal_dreadium = new ItemCrystal("crystal", Crystals.DREADIUM);
		ACItems.crystal_blaze = new ItemCrystal("crystal", Crystals.BLAZE);
		ACItems.crystal_silicon = new ItemCrystal("crystal", Crystals.SILICON);
		ACItems.crystal_magnesium = new ItemCrystal("crystal", Crystals.MAGNESIUM);
		ACItems.crystal_aluminium = new ItemCrystal("crystal", Crystals.ALUMINIUM);
		ACItems.crystal_silica = new ItemCrystal("crystal", Crystals.SILICA);
		ACItems.crystal_alumina = new ItemCrystal("crystal", Crystals.ALUMINA);
		ACItems.crystal_magnesia = new ItemCrystal("crystal", Crystals.MAGNESIA);
		ACItems.crystal_zinc = new ItemCrystal("crystal", Crystals.ZINC);
		ACItems.crystal_calcium = new ItemCrystal("crystal", Crystals.CALCIUM);
		ACItems.crystal_beryllium = new ItemCrystal("crystal", Crystals.BERYLLIUM);
		ACItems.crystal_beryl = new ItemCrystal("crystal", Crystals.BERYL);
		ACItems.crystal_shard_iron = new ItemCrystalShard("crystalshard", Crystals.IRON);
		ACItems.crystal_shard_gold = new ItemCrystalShard("crystalshard", Crystals.GOLD);
		ACItems.crystal_shard_sulfur = new ItemCrystalShard("crystalshard", Crystals.SULFUR);
		ACItems.crystal_shard_carbon = new ItemCrystalShard("crystalshard", Crystals.CARBON);
		ACItems.crystal_shard_oxygen = new ItemCrystalShard("crystalshard", Crystals.OXYGEN);
		ACItems.crystal_shard_hydrogen = new ItemCrystalShard("crystalshard", Crystals.HYDROGEN);
		ACItems.crystal_shard_nitrogen = new ItemCrystalShard("crystalshard", Crystals.NITROGEN);
		ACItems.crystal_shard_phosphorus = new ItemCrystalShard("crystalshard", Crystals.PHOSPHORUS);
		ACItems.crystal_shard_potassium = new ItemCrystalShard("crystalshard", Crystals.POTASSIUM);
		ACItems.crystal_shard_nitrate = new ItemCrystalShard("crystalshard", Crystals.NITRATE);
		ACItems.crystal_shard_methane = new ItemCrystalShard("crystalshard", Crystals.METHANE);
		ACItems.crystal_shard_redstone = new ItemCrystalShard("crystalshard", Crystals.REDSTONE);
		ACItems.crystal_shard_abyssalnite = new ItemCrystalShard("crystalshard", Crystals.ABYSSALNITE);
		ACItems.crystal_shard_coralium = new ItemCrystalShard("crystalshard", Crystals.CORALIUM);
		ACItems.crystal_shard_dreadium = new ItemCrystalShard("crystalshard", Crystals.DREADIUM);
		ACItems.crystal_shard_blaze = new ItemCrystalShard("crystalshard", Crystals.BLAZE);
		ACItems.crystal_shard_silicon = new ItemCrystalShard("crystalshard", Crystals.SILICON);
		ACItems.crystal_shard_magnesium = new ItemCrystalShard("crystalshard", Crystals.MAGNESIUM);
		ACItems.crystal_shard_aluminium = new ItemCrystalShard("crystalshard", Crystals.ALUMINIUM);
		ACItems.crystal_shard_silica = new ItemCrystalShard("crystalshard", Crystals.SILICA);
		ACItems.crystal_shard_alumina = new ItemCrystalShard("crystalshard", Crystals.ALUMINA);
		ACItems.crystal_shard_magnesia = new ItemCrystalShard("crystalshard", Crystals.MAGNESIA);
		ACItems.crystal_shard_zinc = new ItemCrystalShard("crystalshard", Crystals.ZINC);
		ACItems.crystal_shard_calcium = new ItemCrystalShard("crystalshard", Crystals.CALCIUM);
		ACItems.crystal_shard_beryllium = new ItemCrystalShard("crystalshard", Crystals.BERYLLIUM);
		ACItems.crystal_shard_beryl = new ItemCrystalShard("crystalshard", Crystals.BERYL);
		ACItems.crystal_fragment_iron = new ItemCrystalFragment("crystalfragment", Crystals.IRON);
		ACItems.crystal_fragment_gold = new ItemCrystalFragment("crystalfragment", Crystals.GOLD);
		ACItems.crystal_fragment_sulfur = new ItemCrystalFragment("crystalfragment", Crystals.SULFUR);
		ACItems.crystal_fragment_carbon = new ItemCrystalFragment("crystalfragment", Crystals.CARBON);
		ACItems.crystal_fragment_oxygen = new ItemCrystalFragment("crystalfragment", Crystals.OXYGEN);
		ACItems.crystal_fragment_hydrogen = new ItemCrystalFragment("crystalfragment", Crystals.HYDROGEN);
		ACItems.crystal_fragment_nitrogen = new ItemCrystalFragment("crystalfragment", Crystals.NITROGEN);
		ACItems.crystal_fragment_phosphorus = new ItemCrystalFragment("crystalfragment", Crystals.PHOSPHORUS);
		ACItems.crystal_fragment_potassium = new ItemCrystalFragment("crystalfragment", Crystals.POTASSIUM);
		ACItems.crystal_fragment_nitrate = new ItemCrystalFragment("crystalfragment", Crystals.NITRATE);
		ACItems.crystal_fragment_methane = new ItemCrystalFragment("crystalfragment", Crystals.METHANE);
		ACItems.crystal_fragment_redstone = new ItemCrystalFragment("crystalfragment", Crystals.REDSTONE);
		ACItems.crystal_fragment_abyssalnite = new ItemCrystalFragment("crystalfragment", Crystals.ABYSSALNITE);
		ACItems.crystal_fragment_coralium = new ItemCrystalFragment("crystalfragment", Crystals.CORALIUM);
		ACItems.crystal_fragment_dreadium = new ItemCrystalFragment("crystalfragment", Crystals.DREADIUM);
		ACItems.crystal_fragment_blaze = new ItemCrystalFragment("crystalfragment", Crystals.BLAZE);
		ACItems.crystal_fragment_silicon = new ItemCrystalFragment("crystalfragment", Crystals.SILICON);
		ACItems.crystal_fragment_magnesium = new ItemCrystalFragment("crystalfragment", Crystals.MAGNESIUM);
		ACItems.crystal_fragment_aluminium = new ItemCrystalFragment("crystalfragment", Crystals.ALUMINIUM);
		ACItems.crystal_fragment_silica = new ItemCrystalFragment("crystalfragment", Crystals.SILICA);
		ACItems.crystal_fragment_alumina = new ItemCrystalFragment("crystalfragment", Crystals.ALUMINA);
		ACItems.crystal_fragment_magnesia = new ItemCrystalFragment("crystalfragment", Crystals.MAGNESIA);
		ACItems.crystal_fragment_zinc = new ItemCrystalFragment("crystalfragment", Crystals.ZINC);
		ACItems.crystal_fragment_calcium = new ItemCrystalFragment("crystalfragment", Crystals.CALCIUM);
		ACItems.crystal_fragment_beryllium = new ItemCrystalFragment("crystalfragment", Crystals.BERYLLIUM);
		ACItems.crystal_fragment_beryl = new ItemCrystalFragment("crystalfragment", Crystals.BERYL);

		//Shadow items
		ACItems.shadow_fragment = new ItemACBasic("shadowfragment");
		ACItems.shadow_shard = new ItemACBasic("shadowshard");
		ACItems.shadow_gem = new ItemACBasic("shadowgem");
		ACItems.shard_of_oblivion = new ItemACBasic("oblivionshard");

		//Dread items
		ACItems.dreaded_shard_of_abyssalnite = new ItemACBasic("dreadshard");
		ACItems.dreaded_chunk_of_abyssalnite = new ItemACBasic("dreadchunk");
		ACItems.dreadium_ingot = new ItemACBasic("dreadiumingot");
		ACItems.dread_fragment = new ItemACBasic("dreadfragment");
		ACItems.dread_cloth = new ItemACBasic("dreadcloth");
		ACItems.dreadium_plate = new ItemACBasic("dreadplate");
		ACItems.dreadium_katana_blade = new ItemACBasic("dreadblade");
		ACItems.dread_plagued_gateway_key = new ItemACBasic("dreadkey");
		ACItems.charcoal = new ItemACBasic("cha_rcoal");

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
		ACItems.staff_of_rending = new ItemStaffOfRending("drainstaff.normal", 0);
		ACItems.abyssal_wasteland_staff_of_rending = new ItemStaffOfRending("drainstaff.aw", 1);
		ACItems.dreadlands_staff_of_rending = new ItemStaffOfRending("drainstaff.dl", 2);
		ACItems.omothol_staff_of_rending = new ItemStaffOfRending("drainstaff.omt", 3);
		ACItems.spirit_tablet = new ItemSpiritTablet();

		//Armor
		ACItems.abyssalnite_helmet = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.HEAD, "ahelmet");
		ACItems.abyssalnite_chestplate = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.CHEST, "aplate");
		ACItems.abyssalnite_leggings = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.LEGS, "alegs");
		ACItems.abyssalnite_boots = new ItemAbyssalniteArmor(AbyssalCraftAPI.abyssalniteArmor, 5, EntityEquipmentSlot.FEET, "aboots");
		ACItems.refined_coralium_helmet = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.HEAD, "corhelmet");
		ACItems.refined_coralium_chestplate = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.CHEST, "corplate");
		ACItems.refined_coralium_leggings = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.LEGS, "corlegs");
		ACItems.refined_coralium_boots = new ItemCoraliumArmor(AbyssalCraftAPI.refinedCoraliumArmor, 5, EntityEquipmentSlot.FEET, "corboots");
		ACItems.plated_coralium_helmet = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.HEAD, "corhelmetp");
		ACItems.plated_coralium_chestplate = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.CHEST, "corplatep");
		ACItems.plated_coralium_leggings = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.LEGS, "corlegsp");
		ACItems.plated_coralium_boots = new ItemCoraliumPArmor(AbyssalCraftAPI.platedCoraliumArmor, 5, EntityEquipmentSlot.FEET, "corbootsp");
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

		//Food
		ACItems.generic_meat = new ItemFood(4, 0.4f, true).setTranslationKey("generic_meat").setCreativeTab(ACTabs.tabFood);
		ACItems.cooked_generic_meat = new ItemFood(9, 0.9f, true).setTranslationKey("cooked_generic_meat").setCreativeTab(ACTabs.tabFood);

		SoftDepUtil.declareItems();

		registerItem(devsword, "devsword");
		registerItem(shoggoth_projectile, "shoggoth_projectile");

		registerItem(ACItems.oblivion_catalyst, "oc");
		registerItem(ACItems.gateway_key, "gatewaykey");
		registerItem(ACItems.staff_of_the_gatekeeper, "staff");
		registerItem(ACItems.powerstone_tracker, "powerstonetracker");
		registerItem(ACItems.eye_of_the_abyss, "eoa");
		registerItem(ACItems.dreadlands_infused_gateway_key, "gatewaykeydl");
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
		registerItem(ACItems.crystal_iron, "iron_crystal");
		registerItem(ACItems.crystal_gold, "gold_crystal");
		registerItem(ACItems.crystal_sulfur, "sulfur_crystal");
		registerItem(ACItems.crystal_carbon, "carbon_crystal");
		registerItem(ACItems.crystal_oxygen, "oxygen_crystal");
		registerItem(ACItems.crystal_hydrogen, "hydrogen_crystal");
		registerItem(ACItems.crystal_nitrogen, "nitrogen_crystal");
		registerItem(ACItems.crystal_phosphorus, "phosphorus_crystal");
		registerItem(ACItems.crystal_potassium, "potassium_crystal");
		registerItem(ACItems.crystal_nitrate, "nitrate_crystal");
		registerItem(ACItems.crystal_methane, "methane_crystal");
		registerItem(ACItems.crystal_redstone, "redstone_crystal");
		registerItem(ACItems.crystal_abyssalnite, "abyssalnite_crystal");
		registerItem(ACItems.crystal_coralium, "coralium_crystal");
		registerItem(ACItems.crystal_dreadium, "dreadium_crystal");
		registerItem(ACItems.crystal_blaze, "blaze_crystal");
		registerItem(ACItems.crystal_silicon, "silicon_crystal");
		registerItem(ACItems.crystal_magnesium, "magnesium_crystal");
		registerItem(ACItems.crystal_aluminium, "aluminium_crystal");
		registerItem(ACItems.crystal_silica, "silica_crystal");
		registerItem(ACItems.crystal_alumina, "alumina_crystal");
		registerItem(ACItems.crystal_magnesia, "magnesia_crystal");
		registerItem(ACItems.crystal_zinc, "zinc_crystal");
		registerItem(ACItems.crystal_calcium, "calcium_crystal");
		registerItem(ACItems.crystal_beryllium, "beryllium_crystal");
		registerItem(ACItems.crystal_beryl, "beryl_crystal");
		registerItem(ACItems.crystal_shard_iron, "iron_crystal_shard");
		registerItem(ACItems.crystal_shard_gold, "gold_crystal_shard");
		registerItem(ACItems.crystal_shard_sulfur, "sulfur_crystal_shard");
		registerItem(ACItems.crystal_shard_carbon, "carbon_crystal_shard");
		registerItem(ACItems.crystal_shard_oxygen, "oxygen_crystal_shard");
		registerItem(ACItems.crystal_shard_hydrogen, "hydrogen_crystal_shard");
		registerItem(ACItems.crystal_shard_nitrogen, "nitrogen_crystal_shard");
		registerItem(ACItems.crystal_shard_phosphorus, "phosphorus_crystal_shard");
		registerItem(ACItems.crystal_shard_potassium, "potassium_crystal_shard");
		registerItem(ACItems.crystal_shard_nitrate, "nitrate_crystal_shard");
		registerItem(ACItems.crystal_shard_methane, "methane_crystal_shard");
		registerItem(ACItems.crystal_shard_redstone, "redstone_crystal_shard");
		registerItem(ACItems.crystal_shard_abyssalnite, "abyssalnite_crystal_shard");
		registerItem(ACItems.crystal_shard_coralium, "coralium_crystal_shard");
		registerItem(ACItems.crystal_shard_dreadium, "dreadium_crystal_shard");
		registerItem(ACItems.crystal_shard_blaze, "blaze_crystal_shard");
		registerItem(ACItems.crystal_shard_silicon, "silicon_crystal_shard");
		registerItem(ACItems.crystal_shard_magnesium, "magnesium_crystal_shard");
		registerItem(ACItems.crystal_shard_aluminium, "aluminium_crystal_shard");
		registerItem(ACItems.crystal_shard_silica, "silica_crystal_shard");
		registerItem(ACItems.crystal_shard_alumina, "alumina_crystal_shard");
		registerItem(ACItems.crystal_shard_magnesia, "magnesia_crystal_shard");
		registerItem(ACItems.crystal_shard_zinc, "zinc_crystal_shard");
		registerItem(ACItems.crystal_shard_calcium, "calcium_crystal_shard");
		registerItem(ACItems.crystal_shard_beryllium, "beryllium_crystal_shard");
		registerItem(ACItems.crystal_shard_beryl, "beryl_crystal_shard");
		registerItem(ACItems.dread_cloth, "dreadcloth");
		registerItem(ACItems.dreadium_plate, "dreadplate");
		registerItem(ACItems.dreadium_katana_blade, "dreadblade");
		registerItem(ACItems.dreadium_katana_hilt, "dreadhilt");
		registerItem(ACItems.dreadium_katana, "dreadkatana");
		registerItem(ACItems.dread_plagued_gateway_key, "dreadkey");
		registerItem(ACItems.omothol_forged_gateway_key, "gatewaykeyjzh");
		registerItem(ACItems.dreadium_samurai_helmet, "dreadiumsamuraihelmet");
		registerItem(ACItems.dreadium_samurai_chestplate, "dreadiumsamuraiplate");
		registerItem(ACItems.dreadium_samurai_leggings, "dreadiumsamurailegs");
		registerItem(ACItems.dreadium_samurai_boots, "dreadiumsamuraiboots");
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
		registerItem(ACItems.token_of_jzahar, "jzaharcoin");
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
		registerItem(ACItems.overworld_shoggoth_flesh, "shoggothflesh_overworld");
		registerItem(ACItems.abyssal_shoggoth_flesh, "shoggothflesh_abyssal");
		registerItem(ACItems.dreaded_shoggoth_flesh, "shoggothflesh_dreaded");
		registerItem(ACItems.omothol_shoggoth_flesh, "shoggothflesh_omothol");
		registerItem(ACItems.shadow_shoggoth_flesh, "shoggothflesh_shadow");
		registerItem(ACItems.abyssalnite_nugget, "nugget_abyssalnite");
		registerItem(ACItems.refined_coralium_nugget, "nugget_coralium");
		registerItem(ACItems.dreadium_nugget, "nugget_dreadium");
		registerItem(ACItems.ethaxium_nugget, "nugget_ethaxium");
		registerItem(ACItems.staff_of_rending, "drainstaff");
		registerItem(ACItems.abyssal_wasteland_staff_of_rending, "drainstaff_aw");
		registerItem(ACItems.dreadlands_staff_of_rending, "drainstaff_dl");
		registerItem(ACItems.omothol_staff_of_rending, "drainstaff_omt");
		registerItem(ACItems.abyssal_wasteland_essence, "essence_abyssalwasteland");
		registerItem(ACItems.dreadlands_essence, "essence_dreadlands");
		registerItem(ACItems.omothol_essence, "essence_omothol");
		registerItem(ACItems.skin_of_the_abyssal_wasteland, "skin_abyssalwasteland");
		registerItem(ACItems.skin_of_the_dreadlands, "skin_dreadlands");
		registerItem(ACItems.skin_of_omothol, "skin_omothol");
		registerItem(ACItems.ritual_charm, "charm");
		registerItem(ACItems.range_ritual_charm, "charm_range");
		registerItem(ACItems.duration_ritual_charm, "charm_duration");
		registerItem(ACItems.power_ritual_charm, "charm_power");
		registerItem(ACItems.cthulhu_charm, "cthulhucharm");
		registerItem(ACItems.range_cthulhu_charm, "cthulhucharm_range");
		registerItem(ACItems.duration_cthulhu_charm, "cthulhucharm_duration");
		registerItem(ACItems.power_cthulhu_charm, "cthulhucharm_power");
		registerItem(ACItems.hastur_charm, "hasturcharm");
		registerItem(ACItems.range_hastur_charm, "hasturcharm_range");
		registerItem(ACItems.duration_hastur_charm, "hasturcharm_duration");
		registerItem(ACItems.power_hastur_charm, "hasturcharm_power");
		registerItem(ACItems.jzahar_charm, "jzaharcharm");
		registerItem(ACItems.range_jzahar_charm, "jzaharcharm_range");
		registerItem(ACItems.duration_jzahar_charm, "jzaharcharm_duration");
		registerItem(ACItems.power_jzahar_charm, "jzaharcharm_power");
		registerItem(ACItems.azathoth_charm, "azathothcharm");
		registerItem(ACItems.range_azathoth_charm, "azathothcharm_range");
		registerItem(ACItems.duration_azathoth_charm, "azathothcharm_duration");
		registerItem(ACItems.power_azathoth_charm, "azathothcharm_power");
		registerItem(ACItems.nyarlathotep_charm, "nyarlathotepcharm");
		registerItem(ACItems.range_nyarlathotep_charm, "nyarlathotepcharm_range");
		registerItem(ACItems.duration_nyarlathotep_charm, "nyarlathotepcharm_duration");
		registerItem(ACItems.power_nyarlathotep_charm, "nyarlathotepcharm_power");
		registerItem(ACItems.yog_sothoth_charm, "yogsothothcharm");
		registerItem(ACItems.range_yog_sothoth_charm, "yogsothothcharm_range");
		registerItem(ACItems.duration_yog_sothoth_charm, "yogsothothcharm_duration");
		registerItem(ACItems.power_yog_sothoth_charm, "yogsothothcharm_power");
		registerItem(ACItems.shub_niggurath_charm, "shubniggurathcharm");
		registerItem(ACItems.range_shub_niggurath_charm, "shubniggurathcharm_range");
		registerItem(ACItems.duration_shub_niggurath_charm, "shubniggurathcharm_duration");
		registerItem(ACItems.power_shub_niggurath_charm, "shubniggurathcharm_power");
		registerItem(ACItems.essence_of_the_gatekeeper, "gatekeeperessence");
		registerItem(ACItems.interdimensional_cage, "interdimensionalcage");
		registerItem(ACItems.crystal_fragment_iron, "iron_crystal_fragment");
		registerItem(ACItems.crystal_fragment_gold, "gold_crystal_fragment");
		registerItem(ACItems.crystal_fragment_sulfur, "sulfur_crystal_fragment");
		registerItem(ACItems.crystal_fragment_carbon, "carbon_crystal_fragment");
		registerItem(ACItems.crystal_fragment_oxygen, "oxygen_crystal_fragment");
		registerItem(ACItems.crystal_fragment_hydrogen, "hydrogen_crystal_fragment");
		registerItem(ACItems.crystal_fragment_nitrogen, "nitrogen_crystal_fragment");
		registerItem(ACItems.crystal_fragment_phosphorus, "phosphorus_crystal_fragment");
		registerItem(ACItems.crystal_fragment_potassium, "potassium_crystal_fragment");
		registerItem(ACItems.crystal_fragment_nitrate, "nitrate_crystal_fragment");
		registerItem(ACItems.crystal_fragment_methane, "methane_crystal_fragment");
		registerItem(ACItems.crystal_fragment_redstone, "redstone_crystal_fragment");
		registerItem(ACItems.crystal_fragment_abyssalnite, "abyssalnite_crystal_fragment");
		registerItem(ACItems.crystal_fragment_coralium, "coralium_crystal_fragment");
		registerItem(ACItems.crystal_fragment_dreadium, "dreadium_crystal_fragment");
		registerItem(ACItems.crystal_fragment_blaze, "blaze_crystal_fragment");
		registerItem(ACItems.crystal_fragment_silicon, "silicon_crystal_fragment");
		registerItem(ACItems.crystal_fragment_magnesium, "magnesium_crystal_fragment");
		registerItem(ACItems.crystal_fragment_aluminium, "aluminium_crystal_fragment");
		registerItem(ACItems.crystal_fragment_silica, "silica_crystal_fragment");
		registerItem(ACItems.crystal_fragment_alumina, "alumina_crystal_fragment");
		registerItem(ACItems.crystal_fragment_magnesia, "magnesia_crystal_fragment");
		registerItem(ACItems.crystal_fragment_zinc, "zinc_crystal_fragment");
		registerItem(ACItems.crystal_fragment_calcium, "calcium_crystal_fragment");
		registerItem(ACItems.crystal_fragment_beryllium, "beryllium_crystal_fragment");
		registerItem(ACItems.crystal_fragment_beryl, "beryl_crystal_fragment");
		registerItem(ACItems.stone_tablet, "stonetablet");
		registerItem(ACItems.basic_scroll, "scroll_basic");
		registerItem(ACItems.lesser_scroll, "scroll_lesser");
		registerItem(ACItems.moderate_scroll, "scroll_moderate");
		registerItem(ACItems.greater_scroll, "scroll_greater");
		registerItem(ACItems.antimatter_scroll, "scroll_unique_anti");
		registerItem(ACItems.oblivion_scroll, "scroll_unique_oblivion");
		registerItem(ACItems.coralium_plague_antidote, "coralium_antidote");
		registerItem(ACItems.dread_plague_antidote, "dread_antidote");
		registerItem(ACItems.darklands_oak_door, "door_dlt");
		registerItem(ACItems.dreadlands_door, "door_drt");
		registerItem(ACItems.charcoal, "charcoal");
		registerItem(ACItems.spirit_tablet, "spirit_tablet");
		registerItem(ACItems.spirit_tablet_shard_0, "spirit_tablet_shard_0");
		registerItem(ACItems.spirit_tablet_shard_1, "spirit_tablet_shard_1");
		registerItem(ACItems.spirit_tablet_shard_2, "spirit_tablet_shard_2");
		registerItem(ACItems.spirit_tablet_shard_3, "spirit_tablet_shard_3");
		registerItem(ACItems.silver_key, "silver_key");
		registerItem(ACItems.book_of_many_faces, "face_book");
		registerItem(ACItems.generic_meat, "generic_meat");
		registerItem(ACItems.cooked_generic_meat, "cooked_generic_meat");
		registerItem(ACItems.lost_page, "lost_page");
		registerItem(ACItems.scriptures_of_omniscience, "scriptures_omniscience");
		registerItem(ACItems.sealing_key, "sealing_key");
	}

	@Override
	public void init(FMLInitializationEvent event) {
		((ItemAntidote) ACItems.coralium_plague_antidote).setCure(AbyssalCraftAPI.coralium_antidote);
		((ItemAntidote) ACItems.dread_plague_antidote).setCure(AbyssalCraftAPI.dread_antidote);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {}

	private static void registerItem(Item item, String name){
		InitHandler.INSTANCE.ITEMS.add(item.setRegistryName(new ResourceLocation(AbyssalCraft.modid, name)));
	}
}
