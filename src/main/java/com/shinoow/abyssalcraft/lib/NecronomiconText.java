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
package com.shinoow.abyssalcraft.lib;

import java.util.Random;

/**
 * That one place where you keep a billion Strings representing stuff
 * @author shinoow
 *
 */
public class NecronomiconText {

	public static String NECRONOMICON_PAGE_1 = "necronomicon.text.necronomicon.1";
	public static String NECRONOMICON_PAGE_2 = "necronomicon.text.necronomicon.2";
	public static String NECRONOMICON_PAGE_3 = "necronomicon.text.necronomicon.3";
	public static String NECRONOMICON_PAGE_4 = "necronomicon.text.necronomicon.4";

	public static String ABYSSALNOMICON_PAGE_1 = "necronomicon.text.abyssalnomicon.1";
	public static String ABYSSALNOMICON_PAGE_2 = "necronomicon.text.abyssalnomicon.2";

	public static String INFORMATION_ABYSSALCRAFT_PAGE_1 = "necronomicon.text.abyssalcraft.1";
	public static String INFORMATION_ABYSSALCRAFT_PAGE_2 = "necronomicon.text.abyssalcraft.2";
	public static String INFORMATION_ABYSSALCRAFT_PAGE_3 = "necronomicon.text.abyssalcraft.3";
	public static String INFORMATION_ABYSSALCRAFT_PAGE_4 = "necronomicon.text.abyssalcraft.4";
	public static String INFORMATION_ABYSSALCRAFT_PAGE_5 = "necronomicon.text.abyssalcraft.5";
	public static String INFORMATION_ABYSSALCRAFT_PAGE_6 = "necronomicon.text.abyssalcraft.6";

	public static String AZATHOTH_1 = "necronomicon.text.azathoth.1";
	public static String AZATHOTH_2 = "necronomicon.text.azathoth.2";
	public static String NYARLATHOTEP_1 = "necronomicon.text.nyarlathotep.1";
	public static String NYARLATHOTEP_2 = "necronomicon.text.nyarlathotep.2";
	public static String YOG_SOTHOTH_1 = "necronomicon.text.yog-sothoth.1";
	public static String YOG_SOTHOTH_2 = "necronomicon.text.yog-sothoth.2";
	public static String SHUB_NIGGURATH_1 = "necronomicon.text.shub-niggurath.1";
	public static String SHUB_NIGGURATH_2 = "necronomicon.text.shub-niggurath.2";
	public static String CTHULHU_1 = "necronomicon.text.cthulhu.1";
	public static String CTHULHU_2 = "necronomicon.text.cthulhu.2";
	public static String HASTUR_1 = "necronomicon.text.hastur.1";
	public static String HASTUR_2 = "necronomicon.text.hastur.2";
	public static String JZAHAR_1 = "necronomicon.text.jzahar.1";
	public static String JZAHAR_2 = "necronomicon.text.jzahar.2";

	public static String INFORMATION_GREAT_OLD_ONES = "necronomicon.text.greatoldones";

	public static String INFORMATION_ABYSSALNOMICON = "necronomicon.text.abyssalnomicon_info";

	public static String INFORMATION_INTEGRATION = "necronomicon.text.integration";

	public static String INFORMATION_OVERWORLD = "necronomicon.text.overworld";
	public static String INFORMATION_ABYSSAL_WASTELAND = "necronomicon.text.abyssal";
	public static String INFORMATION_DREADLANDS = "necronomicon.text.dreadlands";
	public static String INFORMATION_OMOTHOL = "necronomicon.text.omothol";
	public static String INFORMATION_DARK_REALM = "necronomicon.text.darkrealm";

	public static String MISC_INFORMATION = "necronomicon.text.miscinformation";

	public static String MATERIAL_ABYSSALNITE_1 = "necronomicon.text.materials.abyssalnite.1";
	public static String MATERIAL_ABYSSALNITE_2 = "necronomicon.text.materials.abyssalnite.2";
	public static String MATERIAL_DARKSTONE_1 = "necronomicon.text.materials.darkstone.1";
	public static String MATERIAL_DARKSTONE_2 = "necronomicon.text.materials.darkstone.2";
	public static String MATERIAL_CORALIUM_1 = "necronomicon.text.materials.coralium.1";
	public static String MATERIAL_CORALIUM_2 = "necronomicon.text.materials.coralium.2";
	public static String MATERIAL_DARKLANDS_OAK_1 = "necronomicon.text.materials.darklandsoak.1";
	public static String MATERIAL_DARKLANDS_OAK_2 = "necronomicon.text.materials.darklandsoak.2";
	public static String MATERIAL_NITRE_1 = "necronomicon.text.materials.nitre.1";
	public static String MATERIAL_NITRE_2 = "necronomicon.text.materials.nitre.2";
	public static String MATERIAL_LIQUID_ANTIMATTER_1 = "necronomicon.text.materials.liquidantimatter.1";
	public static String MATERIAL_LIQUID_ANTIMATTER_2 = "necronomicon.text.materials.liquidantimatter.2";
	public static String MATERIAL_ABYSSAL_STONE_1 = "necronomicon.text.materials.abyssalstone.1";
	public static String MATERIAL_ABYSSAL_ORES_1 = "necronomicon.text.materials.abyssalores.1";
	public static String MATERIAL_ABYSSAL_ORES_2 = "necronomicon.text.materials.abyssalores.2";
	public static String MATERIAL_ABYSSAL_CORALIUM_1 = "necronomicon.text.materials.abyssalcoralium.1";
	public static String MATERIAL_LIQUIFIED_CORALIUM_1 = "necronomicon.text.materials.liquifiedcoralium.1";
	public static String MATERIAL_PEARLESCENT_CORALIUM_1 = "necronomicon.text.materials.pearlescentcoralium.1";
	public static String MATERIAL_LIQUID_CORALIUM_1 = "necronomicon.text.materials.liquidcoralium.1";
	public static String MATERIAL_LIQUID_CORALIUM_2 = "necronomicon.text.materials.liquidcoralium.2";
	public static String MATERIAL_DREADLANDS_INFUSED_POWERSTONE_1 = "necronomicon.text.materials.powerstonedreadlands.1";
	public static String MATERIAL_DREADSTONE_1 = "necronomicon.text.materials.dreadstone.1";
	public static String MATERIAL_ABYSSALNITE_STONE_1 = "necronomicon.text.materials.abyssalnitestone.1";
	public static String MATERIAL_ABYSSALNITE_STONE_2 = "necronomicon.text.materials.abyssalnitestone.2";
	public static String MATERIAL_DREADLANDS_ABYSSALNITE_1 = "necronomicon.text.materials.dreadlandsabyssalnite.1";
	public static String MATERIAL_DREADED_ABYSSALNITE_1 = "necronomicon.text.materials.dreadedabyssalnite.1";
	public static String MATERIAL_DREADLANDS_GRASS_1 = "necronomicon.text.materials.dreadlandsgrass.1";
	public static String MATERIAL_DREADLANDS_TREE_1 = "necronomicon.text.materials.dreadlandstree.1";
	public static String MATERIAL_OMOTHOL_STONE_1 = "necronomicon.text.materials.omotholstone.1";
	public static String MATERIAL_ETHAXIUM_1 = "necronomicon.text.materials.ethaxium.1";
	public static String MATERIAL_ETHAXIUM_2 = "necronomicon.text.materials.ethaxium.2";
	public static String MATERIAL_DARK_ETHAXIUM_1 = "necronomicon.text.materials.darkethaxium.1";
	public static String MATERIAL_RITUAL_ALTAR_1 = "necronomicon.text.materials.ritualaltar.1";
	public static String MATERIAL_RITUAL_PEDESTAL_1 = "necronomicon.text.materials.ritualpedestal.1";
	public static String MATERIAL_MONOLITH_STONE_1 = "necronomicon.text.materials.monolithstone.1";
	public static String MATERIAL_ABYSSAL_SAND = "necronomicon.text.materials.abyssalsand";
	public static String MATERIAL_FUSED_ABYSSAL_SAND = "necronomicon.text.materials.fusedabyssalsand";
	public static String MATERIAL_IDOL_OF_FADING = "necronomicon.text.materials.idoloffading";

	public static String CRAFTING_CORALIUM_INFUSED_STONE_1 = "necronomicon.text.crafting.coraliuminfusedstone.1";
	public static String CRAFTING_CORALIUM_INFUSED_STONE_2 = "necronomicon.text.crafting.coraliuminfusedstone.2";
	public static String CRAFTING_SHADOW_GEM_1 = "necronomicon.text.crafting.shadowgem.1";
	public static String CRAFTING_SHADOW_GEM_2 = "necronomicon.text.crafting.shadowgem.2";
	public static String CRAFTING_SHARD_OF_OBLIVION = "necronomicon.text.crafting.oblivionshard";
	public static String CRAFTING_GATEWAY_KEY = "necronomicon.text.crafting.gk1";
	public static String CRAFTING_NECRONOMICON_C = "necronomicon.text.crafting.necro_c";
	public static String CRAFTING_POWERSTONE_TRACKER = "necronomicon.text.crafting.psdltracker";
	public static String CRAFTING_NECRONOMICON_D = "necronomicon.text.crafting.necro_d";
	public static String CRAFTING_TRANSMUTATOR_1 = "necronomicon.text.crafting.transmutator.1";
	public static String CRAFTING_TRANSMUTATOR_2 = "necronomicon.text.crafting.transmutator.2";
	public static String CRAFTING_CRYSTALLIZER_1 = "necronomicon.text.crafting.crystallizer.1";
	public static String CRAFTING_CRYSTALLIZER_2 = "necronomicon.text.crafting.crystallizer.2";
	public static String CRAFTING_NECRONOMICON_O = "necronomicon.text.crafting.necro_o";
	public static String CRAFTING_LIFE_CRYSTAL_1 = "necronomicon.text.crafting.lifecrystal.1";
	public static String CRAFTING_LIFE_CRYSTAL_2 = "necronomicon.text.crafting.lifecrystal.2";
	public static String CRAFTING_ETHAXIUM_INGOT_1 = "necronomicon.text.crafting.ethaxiumingot.1";
	public static String CRAFTING_ETHAXIUM_INGOT_2 = "necronomicon.text.crafting.ethaxiumingot.2";
	public static String CRAFTING_COIN = "necronomicon.text.crafting.coin";
	public static String CRAFTING_CRYSTAL_BAG_1 = "necronomicon.text.crafting.crystalbag.1";
	public static String CRAFTING_CRYSTAL_BAG_2 = "necronomicon.text.crafting.crystalbag.2";
	public static String CRAFTING_MATERIALIZER_1 = "necronomicon.text.crafting.materializer.1";
	public static String CRAFTING_MATERIALIZER_2 = "necronomicon.text.crafting.materializer.2";
	public static String CRAFTING_ABYSSALNOMICON_1 = "necronomicon.text.crafting.abyssalnomicon.1";
	public static String CRAFTING_ABYSSALNOMICON_2 = "necronomicon.text.crafting.abyssalnomicon.2";
	public static String CRAFTING_STAFF_OF_RENDING_1 = "necronomicon.text.crafting.rendingstaff.1";
	public static String CRAFTING_STAFF_OF_RENDING_2 = "necronomicon.text.crafting.rendingstaff.2";
	public static String CRAFTING_SKIN_OF_THE_ABYSSAL_WASTELAND_1 = "necronomicon.text.crafting.skin_aby";
	public static String CRAFTING_SKIN_OF_THE_DREADLANDS_1 = "necronomicon.text.crafting.skin_dre";
	public static String CRAFTING_SKIN_OF_OMOTHOL_1 = "necronomicon.text.crafting.skin_omt";
	public static String CRAFTING_DREAD_CLOTH = "necronomicon.text.crafting.dreadcloth";
	public static String CRAFTING_DREADIUM_PLATE = "necronomicon.text.crafting.dreadplate";
	public static String CRAFTING_DREADIUM_HILT = "necronomicon.text.crafting.dreadiumhilt";
	public static String CRAFTING_DREADIUM_BLADE = "necronomicon.text.crafting.dreadiumblade";
	public static String CRAFTING_DREADIUM_SAMURAI_HELMET = "necronomicon.text.crafting.dreadiumshelmet";
	public static String CRAFTING_DREADIUM_SAMURAI_CHESTPLATE = "necronomicon.text.crafting.dreadiumsplate";
	public static String CRAFTING_DREADIUM_SAMURAI_LEGGINGS = "necronomicon.text.crafting.dreadiumslegs";
	public static String CRAFTING_DREADIUM_SAMURAI_BOOTS = "necronomicon.text.crafting.dreadiumsboots";
	public static String CRAFTING_DREADIUM_KATANA = "necronomicon.text.crafting.dreadiumkatana";
	public static String CRAFTING_CORALIUM_CHUNK = "necronomicon.text.crafting.coraliumchunk";
	public static String CRAFTING_CORALIUM_PLATE = "necronomicon.text.crafting.coraliumplate";
	public static String CRAFTING_PLATED_CORALIUM_HELMET = "necronomicon.text.crafting.corhelmetp";
	public static String CRAFTING_PLATED_CORALIUM_CHESTPLATE = "necronomicon.text.crafting.corplatep";
	public static String CRAFTING_PLATED_CORALIUM_LEGGINGS = "necronomicon.text.crafting.corlegsp";
	public static String CRAFTING_PLATED_CORALIUM_BOOTS = "necronomicon.text.crafting.corbootsp";
	public static String CRAFTING_CORALIUM_LONGBOW = "necronomicon.text.crafting.corbow";
	public static String CRAFTING_ENERGY_PEDESTAL_1 = "necronomicon.text.crafting.energypedestal.1";
	public static String CRAFTING_ENERGY_PEDESTAL_2 = "necronomicon.text.crafting.energypedestal.2";
	public static String CRAFTING_DREADIUM_1 = "necronomicon.text.crafting.dreadium.1";
	public static String CRAFTING_DREADIUM_2 = "necronomicon.text.crafting.dreadium.2";
	public static String CRAFTING_MONOLITH_PILLAR_1 = "necronomicon.text.crafting.monolithpillar.1";
	public static String CRAFTING_RITUAL_CHARM_1 = "necronomicon.text.crafting.ritualcharm.1";
	public static String CRAFTING_SACRIFICIAL_ALTAR_1 = "necronomicon.text.crafting.sacrificialaltar.1";
	public static String CRAFTING_SACRIFICIAL_ALTAR_2 = "necronomicon.text.crafting.sacrificialaltar.2";
	public static String CRAFTING_ENERGY_COLLECTOR = "necronomicon.text.crafting.energycollector";
	public static String CRAFTING_ENERGY_RELAY = "necronomicon.text.crafting.energyrelay";
	public static String CRAFTING_RENDING_PEDESTAL = "necronomicon.text.crafting.rendingpedestal";
	public static String CRAFTING_STONE_TABLET = "necronomicon.text.crafting.stonetablet";
	public static String CRAFTING_STATE_TRANSFORMER_1 = "necronomicon.text.crafting.statetransformer.1";
	public static String CRAFTING_STATE_TRANSFORMER_2 = "necronomicon.text.crafting.statetransformer.2";
	public static String CRAFTING_ENERGY_DEPOSITIONER_1 = "necronomicon.text.crafting.energydepositioner.1";
	public static String CRAFTING_ENERGY_DEPOSITIONER_2 = "necronomicon.text.crafting.energydepositioner.2";
	public static String CRAFTING_ETHAXIUM_PILLAR = "necronomicon.text.crafting.ethaxiumpillar";
	public static String CRAFTING_DARK_ETHAXIUM_PILLAR = "necronomicon.text.crafting.darkethaxiumpillar";
	public static String CRAFTING_SEQUENTIAL_BREWING_STAND_1 = "necronomicon.text.crafting.sequential_brewing_stand.1";
	public static String CRAFTING_SEQUENTIAL_BREWING_STAND_2 = "necronomicon.text.crafting.sequential_brewing_stand.2";
	public static String CRAFTING_PORTAL_ANCHOR_1 = "necronomicon.text.crafting.portalanchor.1";
	public static String CRAFTING_PORTAL_ANCHOR_2 = "necronomicon.text.crafting.portalanchor.2";

	//Misc crafting
	public static String CRAFTING_ODB_CORE = "necronomicon.text.crafting.odbcore";
	public static String CRAFTING_ODB = "necronomicon.text.crafting.odb";
	public static String CRAFTING_CARBON_CLUSTER = "necronomicon.text.crafting.carboncluster";
	public static String CRAFTING_DENSE_CARBON_CLUSTER = "necronomicon.text.crafting.densecarboncluster";
	public static String CRAFTING_CRATE = "necronomicon.text.crafting.crate";

	//Decorative Statue crafting
	public static String CRAFTING_DECORATIVE_AZATHOTH_STATUE = "necronomicon.text.crafting.decorativeazathothstatue";
	public static String CRAFTING_DECORATIVE_CTHULHU_STATUE = "necronomicon.text.crafting.decorativecthulhustatue";
	public static String CRAFTING_DECORATIVE_HASTUR_STATUE = "necronomicon.text.crafting.decorativehasturstatue";
	public static String CRAFTING_DECORATIVE_JZAHAR_STATUE = "necronomicon.text.crafting.decorativejzaharstatue";
	public static String CRAFTING_DECORATIVE_NYARLATHOTEP_STATUE = "necronomicon.text.crafting.decorativenyarlathotepstatue";
	public static String CRAFTING_DECORATIVE_SHUB_NIGGURATH_STATUE = "necronomicon.text.crafting.decorativeshubniggurathstatue";
	public static String CRAFTING_DECORATIVE_YOG_SOTHOTH_STATUE = "necronomicon.text.crafting.decorativeyogsothothstatue";

	//Enchantments
	public static String ENCHANTMENT_CORALIUM = "necronomicon.text.enchantment.coralium";
	public static String ENCHANTMENT_DREAD = "necronomicon.text.enchantment.dread";
	public static String ENCHANTMENT_LIGHT_PIERCE = "necronomicon.text.enchantment.lightpierce";
	public static String ENCHANTMENT_IRON_WALL = "necronomicon.text.enchantment.ironwall";
	public static String ENCHANTMENT_SAPPING = "necronomicon.text.enchantment.sapping";
	public static String ENCHANTMENT_MULTI_REND = "necronomicon.text.enchantment.multirend";
	public static String ENCHANTMENT_BLINDING_LIGHT = "necronomicon.text.enchantment.blindinglight";

	public static String PROGRESSION_OVERWORLD_1 = "necronomicon.text.overworld.progression.1";
	public static String PROGRESSION_OVERWORLD_2 = "necronomicon.text.overworld.progression.2";
	public static String PROGRESSION_OVERWORLD_3 = "necronomicon.text.overworld.progression.3";
	public static String PROGRESSION_OVERWORLD_4 = "necronomicon.text.overworld.progression.4";
	public static String PROGRESSION_OVERWORLD_5 = "necronomicon.text.overworld.progression.5";
	public static String PROGRESSION_OVERWORLD_6 = "necronomicon.text.overworld.progression.6";

	public static String PROGRESSION_ABYSSAL_1 = "necronomicon.text.abyssal.progression.1";
	public static String PROGRESSION_ABYSSAL_2 = "necronomicon.text.abyssal.progression.2";
	public static String PROGRESSION_ABYSSAL_3 = "necronomicon.text.abyssal.progression.3";

	public static String PROGRESSION_DREADLANDS_1 = "necronomicon.text.dreadlands.progression.1";
	public static String PROGRESSION_DREADLANDS_2 = "necronomicon.text.dreadlands.progression.2";
	public static String PROGRESSION_DREADLANDS_3 = "necronomicon.text.dreadlands.progression.3";

	public static String PROGRESSION_OMOTHOL_1 = "necronomicon.text.omothol.progression.1";
	public static String PROGRESSION_OMOTHOL_2 = "necronomicon.text.omothol.progression.2";
	public static String PROGRESSION_OMOTHOL_3 = "necronomicon.text.omothol.progression.3";
	public static String PROGRESSION_OMOTHOL_4 = "necronomicon.text.omothol.progression.4";
	public static String PROGRESSION_OMOTHOL_5 = "necronomicon.text.omothol.progression.5";
	public static String PROGRESSION_OMOTHOL_6 = "necronomicon.text.omothol.progression.6";

	public static String PROGRESSION_DARK_REALM_1 = "necronomicon.text.darkrealm.progression.1";
	public static String PROGRESSION_DARK_REALM_2 = "necronomicon.text.darkrealm.progression.2";

	public static String ENTITY_ANTI_1 = "necronomicon.text.entity.anti.1";
	public static String ENTITY_ANTI_2 = "necronomicon.text.entity.anti.2";
	public static String ENTITY_EVIL_ANIMALS_1 = "necronomicon.text.entity.evilanimals.1";
	public static String ENTITY_EVIL_ANIMALS_2 = "necronomicon.text.entity.evilanimals.2";
	public static String ENTITY_ABYSSAL_ZOMBIE_1 = "necronomicon.text.entity.abyssalzombie.1";
	public static String ENTITY_ABYSSAL_ZOMBIE_2 = "necronomicon.text.entity.abyssalzombie.2";
	public static String ENTITY_DEPTHS_GHOUL_1 = "necronomicon.text.entity.depthsghoul.1";
	public static String ENTITY_DEPTHS_GHOUL_2 = "necronomicon.text.entity.depthsghoul.2";
	public static String ENTITY_SKELETON_GOLIATH_1 = "necronomicon.text.entity.skeletongoliath.1";
	public static String ENTITY_SKELETON_GOLIATH_2 = "necronomicon.text.entity.skeletongoliath.2";
	public static String ENTITY_SPECTRAL_DRAGON_1 = "necronomicon.text.entity.spectraldragon.1";
	public static String ENTITY_SPECTRAL_DRAGON_2 = "necronomicon.text.entity.spectraldragon.2";
	public static String ENTITY_ASORAH_1 = "necronomicon.text.entity.asorah.1";
	public static String ENTITY_ASORAH_2 = "necronomicon.text.entity.asorah.2";
	public static String ENTITY_DREADLING_1 = "necronomicon.text.entity.dreadling.1";
	public static String ENTITY_DREADLING_2 = "necronomicon.text.entity.dreadling.2";
	public static String ENTITY_DREAD_SPAWN_1 = "necronomicon.text.entity.dreadspawn.1";
	public static String ENTITY_DREAD_SPAWN_2 = "necronomicon.text.entity.dreadspawn.2";
	public static String ENTITY_DEMON_ANIMALS_1 = "necronomicon.text.entity.demonanimals.1";
	public static String ENTITY_DEMON_ANIMALS_2 = "necronomicon.text.entity.demonanimals.2";
	public static String ENTITY_SPAWN_OF_CHAGAROTH_1 = "necronomicon.text.entity.chagarothspawn.1";
	public static String ENTITY_SPAWN_OF_CHAGAROTH_2 = "necronomicon.text.entity.chagarothspawn.2";
	public static String ENTITY_FIST_OF_CHAGAROTH_1 = "necronomicon.text.entity.chagarothfist.1";
	public static String ENTITY_FIST_OF_CHAGAROTH_2 = "necronomicon.text.entity.chagarothfist.2";
	public static String ENTITY_DREADGUARD_1 = "necronomicon.text.entity.dreadguard.1";
	public static String ENTITY_DREADGUARD_2 = "necronomicon.text.entity.dreadguard.2";
	public static String ENTITY_CHAGAROTH_1 = "necronomicon.text.entity.chagaroth.1";
	public static String ENTITY_CHAGAROTH_2 = "necronomicon.text.entity.chagaroth.2";
	public static String ENTITY_REMNANT_1 = "necronomicon.text.entity.remnant.1";
	public static String ENTITY_REMNANT_2 = "necronomicon.text.entity.remnant.2";
	public static String ENTITY_OMOTHOL_GHOUL_1 = "necronomicon.text.entity.omotholghoul.1";
	public static String ENTITY_OMOTHOL_GHOUL_2 = "necronomicon.text.entity.omotholghoul.2";
	public static String ENTITY_OMOTHOL_WARDEN_1 = "necronomicon.text.entity.omotholwarden.1";
	public static String ENTITY_OMOTHOL_WARDEN_2 = "necronomicon.text.entity.omotholwarden.2";
	public static String ENTITY_MINION_OF_THE_GATEKEEPER_1 = "necronomicon.text.entity.gatekeeperminion.1";
	public static String ENTITY_MINION_OF_THE_GATEKEEPER_2 = "necronomicon.text.entity.gatekeeperminion.2";
	public static String ENTITY_JZAHAR_1 = "necronomicon.text.entity.jzahar.1";
	public static String ENTITY_JZAHAR_2 = "necronomicon.text.entity.jzahar.2";
	public static String ENTITY_LESSER_SHOGGOTH_1 = "necronomicon.text.entity.lessershoggoth.1";
	public static String ENTITY_LESSER_SHOGGOTH_2 = "necronomicon.text.entity.lessershoggoth.2";
	public static String ENTITY_SHADOW_CREATURE_1 = "necronomicon.text.entity.shadowcreature.1";
	public static String ENTITY_SHADOW_CREATURE_2 = "necronomicon.text.entity.shadowcreature.2";
	public static String ENTITY_SHADOW_MONSTER_1 = "necronomicon.text.entity.shadowmonster.1";
	public static String ENTITY_SHADOW_MONSTER_2 = "necronomicon.text.entity.shadowmonster.2";
	public static String ENTITY_SHADOW_BEAST_1 = "necronomicon.text.entity.shadowbeast.1";
	public static String ENTITY_SHADOW_BEAST_2 = "necronomicon.text.entity.shadowbeast.2";
	public static String ENTITY_SHADOW_TITAN_1 = "necronomicon.text.entity.shadowtitan.1";
	public static String ENTITY_SHADOW_TITAN_2 = "necronomicon.text.entity.shadowtitan.2";
	public static String ENTITY_SACTHOTH_1 = "necronomicon.text.entity.sacthoth.1";
	public static String ENTITY_SACTHOTH_2 = "necronomicon.text.entity.sacthoth.2";
	public static String ENTITY_CORALIUM_INFESTED_SQUID_1 = "necronomicon.text.entity.coraliumsquid.1";
	public static String ENTITY_CORALIUM_INFESTED_SQUID_2 = "necronomicon.text.entity.coraliumsquid.2";
	public static String ENTITY_SHUB_OFFSPRING_1 = "necronomicon.text.entity.shuboffspring.1";
	public static String ENTITY_SHUB_OFFSPRING_2 = "necronomicon.text.entity.shuboffspring.2";
	public static String ENTITY_GHOUL_1 = "necronomicon.text.entity.ghoul.1";
	public static String ENTITY_GHOUL_2 = "necronomicon.text.entity.ghoul.2";
	public static String ENTITY_DREADED_GHOUL_1 = "necronomicon.text.entity.dreadedghoul.1";
	public static String ENTITY_DREADED_GHOUL_2 = "necronomicon.text.entity.dreadedghoul.2";
	public static String ENTITY_SHADOW_GHOUL_1 = "necronomicon.text.entity.shadowghoul.1";
	public static String ENTITY_SHADOW_GHOUL_2 = "necronomicon.text.entity.shadowghoul.2";

	public static String RITUAL_INFO = "necronomicon.text.rituals.info";

	public static String RITUAL_TUT_1 = "necronomicon.text.rituals.1";
	public static String RITUAL_TUT_2 = "necronomicon.text.rituals.2";
	public static String RITUAL_TUT_3 = "necronomicon.text.rituals.3";
	public static String RITUAL_TUT_4 = "necronomicon.text.rituals.4";
	public static String RITUAL_TUT_5 = "necronomicon.text.rituals.5";
	public static String RITUAL_TUT_6 = "necronomicon.text.rituals.6";
	public static String RITUAL_TUT_7 = "necronomicon.text.rituals.7";

	public static String PE_TUT_1 = "necronomicon.text.pe.1";
	public static String PE_TUT_2 = "necronomicon.text.pe.2";
	public static String PE_TUT_3 = "necronomicon.text.pe.3";
	public static String PE_TUT_4 = "necronomicon.text.pe.4";
	public static String PE_TUT_5 = "necronomicon.text.pe.5";
	public static String PE_TUT_6 = "necronomicon.text.pe.6";
	public static String PE_TUT_7 = "necronomicon.text.pe.7";
	public static String PE_TUT_8 = "necronomicon.text.pe.8";
	public static String PE_TUT_9 = "necronomicon.text.pe.9";
	public static String PE_TUT_10 = "necronomicon.text.pe.10";
	public static String PE_TUT_11 = "necronomicon.text.pe.11";
	public static String PE_TUT_12 = "necronomicon.text.pe.12";
	public static String PE_TUT_13 = "necronomicon.text.pe.13";
	public static String PE_TUT_14 = "necronomicon.text.pe.14";
	public static String PE_TUT_15 = "necronomicon.text.pe.15";
	public static String PE_TUT_16 = "necronomicon.text.pe.16";
	public static String PE_TUT_17 = "necronomicon.text.pe.17";
	public static String PE_TUT_18 = "necronomicon.text.pe.18";
	public static String PE_TUT_19 = "necronomicon.text.pe.19";

	public static String MACHINES_INFO = "necronomicon.text.machines.info";

	public static String MACHINE_INFO_1 = "necronomicon.text.machines.1";
	public static String MACHINE_INFO_2 = "necronomicon.text.machines.2";
	public static String MACHINE_INFO_3 = "necronomicon.text.machines.3";
	public static String MACHINE_INFO_4 = "necronomicon.text.machines.4";

	public static String KNOWLEDGE_INFO_1 = "necronomicon.text.knowledge.1";
	public static String KNOWLEDGE_INFO_2 = "necronomicon.text.knowledge.2";
	public static String KNOWLEDGE_INFO_3 = "necronomicon.text.knowledge.3";

	public static String CORALIUM_PLAGUE_INFO_1 = "necronomicon.text.coraliumplague.1";
	public static String CORALIUM_PLAGUE_INFO_2 = "necronomicon.text.coraliumplague.2";

	public static String DREAD_PLAGUE_INFO_1 = "necronomicon.text.dreadplague.1";
	public static String DREAD_PLAGUE_INFO_2 = "necronomicon.text.dreadplague.2";

	public static String PLACES_OF_POWER_INFO = "necronomicon.text.placesofpower.info";

	public static String PLACES_OF_POWER_INFO_1 = "necronomicon.text.placesofpower.1";
	public static String PLACES_OF_POWER_INFO_2 = "necronomicon.text.placesofpower.2";

	public static String SPELL_INFO = "necronomicon.text.spells.info";

	public static String SPELL_TUT_1 = "necronomicon.text.spells.1";
	public static String SPELL_TUT_2 = "necronomicon.text.spells.2";
	public static String SPELL_TUT_3 = "necronomicon.text.spells.3";

	public static String ITEM_TRANSPORT_TUT_1 = "necronomicon.text.itemtransportsystem.1";
	public static String ITEM_TRANSPORT_TUT_2 = "necronomicon.text.itemtransportsystem.2";
	public static String ITEM_TRANSPORT_TUT_3 = "necronomicon.text.itemtransportsystem.3";
	public static String ITEM_TRANSPORT_TUT_4 = "necronomicon.text.itemtransportsystem.4";
	public static String ITEM_TRANSPORT_TUT_5 = "necronomicon.text.itemtransportsystem.5";
	public static String ITEM_TRANSPORT_TUT_6 = "necronomicon.text.itemtransportsystem.6";
	public static String ITEM_TRANSPORT_TUT_7 = "necronomicon.text.itemtransportsystem.7";
	public static String ITEM_TRANSPORT_TUT_8 = "necronomicon.text.itemtransportsystem.8";
	public static String ITEM_TRANSPORT_TUT_9 = "necronomicon.text.itemtransportsystem.9";
	public static String ITEM_TRANSPORT_TUT_10 = "necronomicon.text.itemtransportsystem.10";

	public static String IDOLS_INFO = "necronomicon.text.idols.info";

	public static String RITUAL_CHARMS_INFO_1 = "necronomicon.text.ritualcharms.1";
	public static String RITUAL_CHARMS_INFO_2 = "necronomicon.text.ritualcharms.2";

	public static String PE_UPGRADING_INFO_1 = "necronomicon.text.peupgrading.1";
	public static String PE_UPGRADING_INFO_2 = "necronomicon.text.peupgrading.2";
	public static String PE_UPGRADING_INFO_3 = "necronomicon.text.peupgrading.3";
	
	public static String WIP = "necronomicon.text.wip";

	/** Text limit */
	public static String TEST = "Lorem ipsum dolor sit amet, nam an mutat eripuit temporibus. Eu eius luptatum similique eam. Erat euismod bonorum cu vis, malis salutatus neglegentur mea no. His et abhorreant conclusionemque, has prima movet dignissim an, vitae deleniti theophrastus ad mea. Est no offendit incorrupte, ferri illum labores qui ut. Sanctus adipisci eum ex, cu falli virtute dissentias.";

	public static String TEST_95 = "Fortemne possumus dicere eundem illum Torquatum? Habes, inquam, Cato, formam eorum, de quibus loquor, philosophorum. Quis tibi ergo istud dabit praeter Pyrrhonem, Aristonem eorumve similes, quos tu non probas? Hic quoque suus est de summoque bono dissentiens dici vere Peripateticus non potest.";
	public static String TEST_50_1 = "Ut in geometria, prima si dederis, danda sunt omnia. Nihil opus est exemplis hoc facere longius. Egone non intellego, quid sit don Graece, Latine voluptas?";
	public static String TEST_50_2 = "Recte dicis; Ne in odium veniam, si amicum destitero tueri. Aliter homines, aliter philosophos loqui putas oportere? Non est igitur voluptas bonum.";

	public static String LABEL_TEST = "...What's this?";

	public static String LABEL_INDEX = "necronomicon.index";
	public static String LABEL_INFORMATION = "necronomicon.information";
	public static String LABEL_SPELLBOOK = "necronomicon.spells";
	public static String LABEL_RITUALS = "necronomicon.rituals";
	public static String LABEL_HUH = "necronomicon.huh";
	public static String LABEL_INFORMATION_ABYSSALCRAFT = "necronomicon.information.ac";
	public static String LABEL_INFORMATION_GREAT_OLD_ONES = "necronomicon.information.goo";
	public static String LABEL_INFORMATION_ABYSSALNOMICON = "necronomicon.information.abyssalnomicon";
	public static String LABEL_INFORMATION_OVERWORLD = "necronomicon.information.overworld";
	public static String LABEL_INFORMATION_OVERWORLD_TITLE = "necronomicon.information.overworld.title";
	public static String LABEL_INFORMATION_ABYSSAL_WASTELAND = "necronomicon.information.abyssal";
	public static String LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE = "necronomicon.information.abyssal.title";
	public static String LABEL_INFORMATION_DREADLANDS = "necronomicon.information.dreadlands";
	public static String LABEL_INFORMATION_DREADLANDS_TITLE = "necronomicon.information.dreadlands.title";
	public static String LABEL_INFORMATION_OMOTHOL = "necronomicon.information.omothol";
	public static String LABEL_INFORMATION_OMOTHOL_TITLE = "necronomicon.information.omothol.title";
	public static String LABEL_INFORMATION_DARK_REALM = "necronomicon.information.darkrealm";
	public static String LABEL_INFORMATION_DARK_REALM_TITLE = "necronomicon.information.darkrealm.title";
	public static String LABEL_INFORMATION_MATERIALS = "necronomicon.information.materials";
	public static String LABEL_INFORMATION_PROGRESSION = "necronomicon.information.progression";
	public static String LABEL_INFORMATION_ENTITIES = "necronomicon.information.entities";
	public static String LABEL_OUTER_GODS = "necronomicon.information.outergods";
	public static String LABEL_OTHER = "necronomicon.other";
	public static String LABEL_LOCKED = "necronomicon.locked";
	public static String LABEL_INFORMATION_SPECIAL_MATERIALS = "necronomicon.information.specialmaterials";
	public static String LABEL_ANYWHERE = "necronomicon.anywhere";
	public static String LABEL_LOCATION = "necronomicon.location";
	public static String LABEL_NORMAL = "necronomicon.normal";
	public static String LABEL_PATRONS = "necronomicon.patrons";
	public static String LABEL_INFORMATION_ARMOR_TOOLS = "necronomicon.information.armortools";
	public static String LABEL_INFORMATION_MACHINES = "necronomicon.information.machines";
	public static String LABEL_REQUIRED_ENERGY = "necronomicon.reqenergy";
	public static String LABEL_SACRIFICE = "necronomicon.sacrifice";
	public static String LABEL_GETTING_STARTED = "necronomicon.gettingstarted";
	public static String LABEL_POTENTIAL_ENERGY = "necronomicon.potentialenergy";
	public static String LABEL_MISC_INFORMATION = "necronomicon.miscinformation";
	public static String LABEL_INFORMATION_ENCHANTMENTS = "necronomicon.information.enchantments";
	public static String LABEL_INFORMATION_DECORATIVE_STATUES = "necronomicon.information.decorativestatues";
	public static String LABEL_KNOWLEDGE = "necronomicon.information.knowledge";
	public static String LABEL_CORALIUM_PLAGUE = "necronomicon.information.coraliumplague";
	public static String LABEL_DREAD_PLAGUE = "necronomicon.information.dreadplague";
	public static String LABEL_PLACES_OF_POWER = "necronomicon.information.placesofpower";
	public static String LABEL_STRUCTURES = "necronomicon.information.structures";
	public static String LABEL_PANTHEON = "necronomicon.information.pantheon";
	public static String LABEL_CASTING = "necronomicon.information.casting";
	public static String LABEL_BUILT_WITH = "necronomicon.builtwith";
	public static String LABEL_INFORMATION_ITEM_TRANSPORT_SYSTEM = "necronomicon.information.itemtransportsystem";
	public static String LABEL_CREATE_SPELLS = "necronomicon.createspells";
	public static String LABEL_OPEN_COMPENDIUM = "necronomicon.opencompendium";
	public static String LABEL_SPELL_NAME = "necronomicon.spell.name";
	public static String LABEL_SPELL_PE = "necronomicon.spell.pe";
	public static String LABEL_SPELL_TYPE = "necronomicon.spell.type";
	public static String LABEL_HEIGHT = "necronomicon.height";
	public static String LABEL_WIDTH = "necronomicon.width";
	public static String LABEL_DEPTH = "necronomicon.depth";
	public static String LABEL_RANGE_AMPLIFIER = "necronomicon.rangeamplifier";
	public static String LABEL_DURATION_AMPLIFIER = "necronomicon.durationamplifier";
	public static String LABEL_POWER_AMPLIFIER = "necronomicon.poweramplifier";
	public static String LABEL_INFORMATION_IDOLS = "necronomicon.information.idols";
	public static String LABEL_INFORMATION_DIMENSIONS = "necronomicon.information.dimensions";
	public static String LABEL_INFORMATION_BIOMES = "necronomicon.information.biomes";
	public static String LABEL_INFORMATION_RITUAL_CHARMS = "necronomicon.information.ritualcharms";
	public static String LABEL_INFORMATION_CRAFTING_RITUAL_CHARMS = "necronomicon.information.craftingritualcharms";
	public static String LABEL_UPGRADING_PE = "necronomicon.information.upgradingpe";

	// Alternating texts to display for locked pages
	// Lyric snippets of songs from the following bands (not in order):
	// The Black Dahlia Murder
	// Aborted
	// Cattle Decapitation
	// NecroticGoreBeast
	// Analepsy
	// Osiah
	// At The Gates
	public static String AKLO_FULL_1 = "I, a monolithic silver tongue will lead you to your death. I, a monolithic silver tongue will put sorrow to rest. Turn a blind eye bleeding, I know what you want best. To watch you crippled and broken. I, a monolithic silver tongue will lead you to your death. The final absolution from within.";
	public static String AKLO_FULL_2 = "A deafening rumble, upon the solving of a puzzle. We will tear your soul apart! Oh sweet serenade of torture! As pleasure and pain unite, we become one, Cenobite! Tearing flesh from bone, desires are born. The spasms of orgasms, uncontrolled. The reaping has begun, through pain and suffering I cum! Divine serrated torment, as Heaven and Hell unite.";
	public static String AKLO_FULL_3 = "Now for the fun part I introduce this: My new Cradle of Judas Perineal puncturing device also juices the fruit of the human As force is applied, grinding and winding intestines and Inside around it like a drill bit turns anatomy into a visceral Swill grating, excavating and making its way through the Body like a bore finding its way out the Mouth announcing the gore";
	public static String AKLO_FULL_4 = "We rise to the slaughter... of the essence of our being We're ripe for the slaughter... but live to ruin another day A species that boasts of overdose That takes pride in deriding all other forms of life Even our own The advent of technology - beginning the disease There is no survival when we are liable for everything that is Wrong in this world The lifestalker";
	public static String AKLO_FULL_5 = "The universe, it always finds a way to purge The sustainably inappropriate numbers that once surged Death always wins, his molten torch forever burns And to the ashes and the ground we are returned Life exists to infuriate, berate, and subjugate The hapless mortals shit-birthed on a human-altered planet Earth Fuck the future Fuck all mankind";
	public static String AKLO_FULL_6 = "The clouds will be stained in blood, forming a grim red glow at night, from the angels with their wings torn from their backs. Man's hope, Man's hope It's sealed By my hands, Betrayed by Satan. Karma's a fucking bitch, His guilt weighs more than my fists, Like father like son, compassion is weak, my job is done. I've drank the blood of Gods. God, damned his world.";
	public static String AKLO_FULL_7 = "I fucking hate, all of you Transcribing wrath upon your face Anthropogenic I fucking hate, all of you cleansing, this cancer Cold steel, reflections of a monster Cold steel, reflections of a monster cold steel was the canvas that revealed the face of a monster How strong I become They fear me and they should For I am The Reaper of Souls The Reaper of Souls";
	public static String AKLO_FULL_8 = "Never again On your force fed illusions to choke You feed off my pain Feed off my life There won't be another dawn We will reap as we have sown Always the same My tired eyes have seen enough Of all your lies My hate is blind There won't be another dawn We will reap as we have sown Slaughter of the soul Suicidal final art Children - born of sin Tear your soul apart";
	public static String AKLO_FULL_9 = "Through the impenetrable haze Through shapeless fog Scattered through these desolate plains White shine the bones: Eater of gods As if mercy were a skin of water We fall into a prior dream The number of the grains of sand Exiled from the memory of men And as the smoke shifts in black Only ashes remain And as the smoke shifts in black Eater of gods Eater of gods";

	public static String AKLO_95_1 = "Into the Everblack, from where there's no coming back. Six feet below the earth, rotting food for the fetid worms. Into the harrowed grave, your mortal soul cannot be saved. Into the nether realm, dead as slate and cold as Hell.";
	public static String AKLO_95_2 = "Obey the terrorvision Demonic manipulation Spreading hate Spreading death Fear Praise the lord Sever the whore The masses want more Bow to me Oh nephilim Emisarry of perversity Praise the whore Nu Babylon The masses need more Crawl for me oh idiocy Emisarry of stupidity";
	public static String AKLO_95_3 = "The scriptures of the dead, satisfy my curiosity. So fascinating, so captivating! Enigmatic paths to Rigor Mortis. Necrotic manifesto! Macerated limbs are dislodged, upon tissue I viciously gorge. Celebration of flesh and bone, my fascination is far gone.";
	public static String AKLO_95_4 = "Oh luscious torture, these little deaths I die all over. With hooks and chains torn apart. These little deaths are mere fine art. Cenobites, explorers of flesh. Cenobites, the wretched living dead. Cenobites, emissaries of Hell. Cenobites, practitioners of eternal torment.";
	public static String AKLO_95_5 = "You alone are your disposal A lifetime of stains wasting away slowly down the drain No mercy, no reprisal No second chance From junk we have emerged Slaves willing to serve Our own damning demands from our own damning hands De-evolved man";
	public static String AKLO_95_6 = "Deformity Rectal tissues ripped Bulging mass leaking out Blood, cum, and piss falling out Inserting fingers, in blood-dripping Rectal prolapse, gorging my lips With some sweet juice-leaking ass Dangling rectum, interconnection There's a special place in hell To fuck with myself";
	public static String AKLO_95_7 = "Locus of Dawning, A ceaseless catastrophe Scalding fragments Amassing in clusters Thriving in darkness Ingesting the glare Gestational agony Throbbing radio waves Destruction by autolysis Imploding genesis Locus of Dawning The place where all ends... ";
	public static String AKLO_95_8 = "As I stand upon the edge of life I look down on the shadows that I've cast in time Across the wastelands I see myself, devoid of colour A void of life, a void of life You are my fight before I kneel to eternal silence I see myself, devoid of colour A void of life, a void of life A void of life";
	public static String AKLO_95_9 = "Blind is the mind that denies our hands We’re the champions of these sands You’re the victim You’ve lost so give up the fight L'appel du Vide. Oasis waters I now dwell my daemons saved me from my Hell And; now I rot. A safe space I now dwell, until this place was all I knew";

	public static String AKLO_50_1 = "Rest in festering slime! Here burns the souls, of a thousand generations. Join the club!";
	public static String AKLO_50_2 = "Masses of mongrels are screaming Common sense has been depleted As you wait for a miracle For a God that will never show";
	public static String AKLO_50_3 = "Hate Fuelling the masterplan Hate Moving the pawns around Hate Our embodiment Hate Fuelling the masterplan Hate Blissfully ignorant Hate Our decadence";
	public static String AKLO_50_4 = "Necrotic manifesto! Decrypting semiology! This book of the dead, a symposium indeed. Celebration of flesh and bone, my fascination is far gone.";
	public static String AKLO_50_5 = "Unnatural and unclean! Lord, please forgive them as this is not what they mean Are you there and are you listening? Why would you even let this happen to me?";
	public static String AKLO_50_6 = "...Ave Delphinus ...Selachimorpha ...Dinoflagellata ...Cheloniidae ...Cetacea Certain doom - assured destruction of finite ecosystems";
	public static String AKLO_50_7 = "We eat our young We eat our young We eat our young We eat our young We eat our young We eat our young We eat our young We eat our young";
	public static String AKLO_50_8 = "I held the world In my hands, betrayed by the Gods; of man. Nailed to the altar of his demise. The sharpened blades tear into his skin, where will they begin?";
	public static String AKLO_50_9 = "The walls of a poem Like the folding of wings They burn through the base of the skull Under the eyelids The night eternal Down fell the city of words";

	/**
	 * Provides a random AKLO_xx string
	 * @param type	<li>0 = 155~ letters</li>
	 * <li>1 = 294~ letters</li>
	 * <li>2 = 368~ letters</li>
	 * @return A song lyric snippet
	 */
	public static String getRandomAklo(int type) {
		Random r = new Random();
		switch(r.nextInt(9)) {
		case 0:
			return type == 0 ? AKLO_50_1 : type == 1 ? AKLO_95_1 : AKLO_FULL_1;
		case 1:
			return type == 0 ? AKLO_50_2 : type == 1 ? AKLO_95_2 : AKLO_FULL_2;
		case 2:
			return type == 0 ? AKLO_50_3 : type == 1 ? AKLO_95_3 : AKLO_FULL_3;
		case 3:
			return type == 0 ? AKLO_50_4 : type == 1 ? AKLO_95_4 : AKLO_FULL_4;
		case 4:
			return type == 0 ? AKLO_50_5 : type == 1 ? AKLO_95_5 : AKLO_FULL_5;
		case 5:
			return type == 0 ? AKLO_50_6 : type == 1 ? AKLO_95_6 : AKLO_FULL_6;
		case 6:
			return type == 0 ? AKLO_50_7 : type == 1 ? AKLO_95_7 : AKLO_FULL_7;
		case 7:
			return type == 0 ? AKLO_50_8 : type == 1 ? AKLO_95_8 : AKLO_FULL_8;
		case 8:
			return type == 0 ? AKLO_50_9 : type == 1 ? AKLO_95_9 : AKLO_FULL_9;
		}

		return TEST;
	}

	public static String getSpellType(boolean requiresCharging) {
		return "necronomicon.spell.type."+(requiresCharging ? "charging" : "instant");
	}
}
