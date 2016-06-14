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
package com.shinoow.abyssalcraft.lib;

import net.minecraft.util.text.translation.I18n;

/**
 * That one place where you keep a billion Strings representing stuff
 * @author shinoow
 *
 */
public class NecronomiconText {

	public static String NECRONOMICON_PAGE_1 = translate("necronomicon.text.necronomicon.1");
	public static String NECRONOMICON_PAGE_2 = translate("necronomicon.text.necronomicon.2");
	public static String NECRONOMICON_PAGE_3 = translate("necronomicon.text.necronomicon.3");
	public static String NECRONOMICON_PAGE_4 = translate("necronomicon.text.necronomicon.4");

	public static String ABYSSALNOMICON_PAGE_1 = translate("necronomicon.text.abyssalnomicon.1");
	public static String ABYSSALNOMICON_PAGE_2 = translate("necronomicon.text.abyssalnomicon.2");

	public static String INFORMATION_ABYSSALCRAFT_PAGE_1 = translate("necronomicon.text.abyssalcraft.1");
	public static String INFORMATION_ABYSSALCRAFT_PAGE_2 = translate("necronomicon.text.abyssalcraft.2");
	public static String INFORMATION_ABYSSALCRAFT_PAGE_3 = translate("necronomicon.text.abyssalcraft.3");
	public static String INFORMATION_ABYSSALCRAFT_PAGE_4 = translate("necronomicon.text.abyssalcraft.4");
	public static String INFORMATION_ABYSSALCRAFT_PAGE_5 = translate("necronomicon.text.abyssalcraft.5");
	public static String INFORMATION_ABYSSALCRAFT_PAGE_6 = translate("necronomicon.text.abyssalcraft.6");

	public static String AZATHOTH_1 = translate("necronomicon.text.azathoth.1");
	public static String AZATHOTH_2 = translate("necronomicon.text.azathoth.2");
	public static String NYARLATHOTEP_1 = translate("necronomicon.text.nyarlathotep.1");
	public static String NYARLATHOTEP_2 = translate("necronomicon.text.nyarlathotep.2");
	public static String YOG_SOTHOTH_1 = translate("necronomicon.text.yog-sothoth.1");
	public static String YOG_SOTHOTH_2 = translate("necronomicon.text.yog-sothoth.2");
	public static String SHUB_NIGGURATH_1 = translate("necronomicon.text.shub-niggurath.1");
	public static String SHUB_NIGGURATH_2 = translate("necronomicon.text.shub-niggurath.2");
	public static String CTHULHU_1 = translate("necronomicon.text.cthulhu.1");
	public static String CTHULHU_2 = translate("necronomicon.text.cthulhu.2");
	public static String HASTUR_1 = translate("necronomicon.text.hastur.1");
	public static String HASTUR_2 = translate("necronomicon.text.hastur.2");
	public static String JZAHAR_1 = translate("necronomicon.text.jzahar.1");
	public static String JZAHAR_2 = translate("necronomicon.text.jzahar.2");

	public static String INFORMATION_GREAT_OLD_ONES = translate("necronomicon.text.greatoldones");

	public static String INFORMATION_ABYSSALNOMICON = translate("necronomicon.text.abyssalnomicon_info");

	public static String INFORMATION_INTEGRATION = translate("necronomicon.text.integration");

	public static String INFORMATION_OVERWORLD = translate("necronomicon.text.overworld");
	public static String INFORMATION_ABYSSAL_WASTELAND = translate("necronomicon.text.abyssal");
	public static String INFORMATION_DREADLANDS = translate("necronomicon.text.dreadlands");
	public static String INFORMATION_OMOTHOL = translate("necronomicon.text.omothol");
	public static String INFORMATION_DARK_REALM = translate("necronomicon.text.darkrealm");

	public static String MISC_INFORMATION = translate("necronomicon.text.miscinformation");

	public static String MATERIAL_ABYSSALNITE_1 = translate("necronomicon.text.materials.abyssalnite.1");
	public static String MATERIAL_ABYSSALNITE_2 = translate("necronomicon.text.materials.abyssalnite.2");
	public static String MATERIAL_DARKSTONE_1 = translate("necronomicon.text.materials.darkstone.1");
	public static String MATERIAL_DARKSTONE_2 = translate("necronomicon.text.materials.darkstone.2");
	public static String MATERIAL_CORALIUM_1 = translate("necronomicon.text.materials.coralium.1");
	public static String MATERIAL_CORALIUM_2 = translate("necronomicon.text.materials.coralium.2");
	public static String MATERIAL_DARKLANDS_OAK_1 = translate("necronomicon.text.materials.darklandsoak.1");
	public static String MATERIAL_DARKLANDS_OAK_2 = translate("necronomicon.text.materials.darklandsoak.2");
	public static String MATERIAL_NITRE_1 = translate("necronomicon.text.materials.nitre.1");
	public static String MATERIAL_NITRE_2 = translate("necronomicon.text.materials.nitre.2");
	public static String MATERIAL_LIQUID_ANTIMATTER_1 = translate("necronomicon.text.materials.liquidantimatter.1");
	public static String MATERIAL_LIQUID_ANTIMATTER_2 = translate("necronomicon.text.materials.liquidantimatter.2");
	public static String MATERIAL_DARKLANDS_GRASS_1 = translate("necronomicon.text.materials.darklandsgrass.1");
	public static String MATERIAL_DARKLANDS_GRASS_2 = translate("necronomicon.text.materials.darklandsgrass.2");
	public static String MATERIAL_ABYSSAL_STONE_1 = translate("necronomicon.text.materials.abyssalstone.1");
	public static String MATERIAL_ABYSSAL_STONE_2 = translate("necronomicon.text.materials.abyssalstone.2");
	public static String MATERIAL_ABYSSAL_ORES_1 = translate("necronomicon.text.materials.abyssalores.1");
	public static String MATERIAL_ABYSSAL_ORES_2 = translate("necronomicon.text.materials.abyssalores.2");
	public static String MATERIAL_ABYSSAL_CORALIUM_1 = translate("necronomicon.text.materials.abyssalcoralium.1");
	public static String MATERIAL_ABYSSAL_CORALIUM_2 = translate("necronomicon.text.materials.abyssalcoralium.2");
	public static String MATERIAL_LIQUIFIED_CORALIUM_1 = translate("necronomicon.text.materials.liquifiedcoralium.1");
	public static String MATERIAL_LIQUIFIED_CORALIUM_2 = translate("necronomicon.text.materials.liquifiedcoralium.2");
	public static String MATERIAL_PEARLESCENT_CORALIUM_1 = translate("necronomicon.text.materials.pearlescentcoralium.1");
	public static String MATERIAL_PEARLESCENT_CORALIUM_2 = translate("necronomicon.text.materials.pearlescentcoralium.2");
	public static String MATERIAL_LIQUID_CORALIUM_1 = translate("necronomicon.text.materials.liquidcoralium.1");
	public static String MATERIAL_LIQUID_CORALIUM_2 = translate("necronomicon.text.materials.liquidcoralium.2");
	public static String MATERIAL_DREADLANDS_INFUSED_POWERSTONE_1 = translate("necronomicon.text.materials.powerstonedreadlands.1");
	public static String MATERIAL_DREADLANDS_INFUSED_POWERSTONE_2 = translate("necronomicon.text.materials.powerstonedreadlands.2");
	public static String MATERIAL_DREADSTONE_1 = translate("necronomicon.text.materials.dreadstone.1");
	public static String MATERIAL_DREADSTONE_2 = translate("necronomicon.text.materials.dreadstone.2");
	public static String MATERIAL_ABYSSALNITE_STONE_1 = translate("necronomicon.text.materials.abyssalnitestone.1");
	public static String MATERIAL_ABYSSALNITE_STONE_2 = translate("necronomicon.text.materials.abyssalnitestone.2");
	public static String MATERIAL_DREADLANDS_ABYSSALNITE_1 = translate("necronomicon.text.materials.dreadlandsabyssalnite.1");
	public static String MATERIAL_DREADLANDS_ABYSSALNITE_2 = translate("necronomicon.text.materials.dreadlandsabyssalnite.2");
	public static String MATERIAL_DREADED_ABYSSALNITE_1 = translate("necronomicon.text.materials.dreadedabyssalnite.1");
	public static String MATERIAL_DREADED_ABYSSALNITE_2 = translate("necronomicon.text.materials.dreadedabyssalnite.2");
	public static String MATERIAL_DREADLANDS_GRASS_1 = translate("necronomicon.text.materials.dreadlandsgrass.1");
	public static String MATERIAL_DREADLANDS_GRASS_2 = translate("necronomicon.text.materials.dreadlandsgrass.2");
	public static String MATERIAL_DREADLANDS_TREE_1 = translate("necronomicon.text.materials.dreadlandstree.1");
	public static String MATERIAL_DREADLANDS_TREE_2 = translate("necronomicon.text.materials.dreadlandstree.2");
	public static String MATERIAL_OMOTHOL_STONE_1 = translate("necronomicon.text.materials.omotholstone.1");
	public static String MATERIAL_OMOTHOL_STONE_2 = translate("necronomicon.text.materials.omotholstone.2");
	public static String MATERIAL_ETHAXIUM_1 = translate("necronomicon.text.materials.ethaxium.1");
	public static String MATERIAL_ETHAXIUM_2 = translate("necronomicon.text.materials.ethaxium.2");
	public static String MATERIAL_DARK_ETHAXIUM_1 = translate("necronomicon.text.materials.darkethaxium.1");
	public static String MATERIAL_DARK_ETHAXIUM_2 = translate("necronomicon.text.materials.darkethaxium.2");
	public static String MATERIAL_RITUAL_ALTAR_1 = translate("necronomicon.text.materials.ritualaltar.1");
	public static String MATERIAL_RITUAL_PEDESTAL_1 = translate("necronomicon.text.materials.ritualpedestal.1");
	public static String MATERIAL_MONOLITH_STONE_1 = translate("necronomicon.text.materials.monolithstone.1");

	public static String CRAFTING_CORALIUM_INFUSED_STONE_1 = translate("necronomicon.text.crafting.coraliuminfusedstone.1");
	public static String CRAFTING_CORALIUM_INFUSED_STONE_2 = translate("necronomicon.text.crafting.coraliuminfusedstone.2");
	public static String CRAFTING_SHADOW_GEM_1 = translate("necronomicon.text.crafting.shadowgem.1");
	public static String CRAFTING_SHADOW_GEM_2 = translate("necronomicon.text.crafting.shadowgem.2");
	public static String CRAFTING_SHARD_OF_OBLIVION = translate("necronomicon.text.crafting.oblivionshard");
	public static String CRAFTING_GATEWAY_KEY = translate("necronomicon.text.crafting.gk1");
	public static String CRAFTING_NECRONOMICON_C = translate("necronomicon.text.crafting.necro_c");
	public static String CRAFTING_POWERSTONE_TRACKER = translate("necronomicon.text.crafting.psdltracker");
	public static String CRAFTING_NECRONOMICON_D = translate("necronomicon.text.crafting.necro_d");
	public static String CRAFTING_TRANSMUTATOR_1 = translate("necronomicon.text.crafting.transmutator.1");
	public static String CRAFTING_TRANSMUTATOR_2 = translate("necronomicon.text.crafting.transmutator.2");
	public static String CRAFTING_CRYSTALLIZER_1 = translate("necronomicon.text.crafting.crystallizer.1");
	public static String CRAFTING_CRYSTALLIZER_2 = translate("necronomicon.text.crafting.crystallizer.2");
	public static String CRAFTING_NECRONOMICON_O = translate("necronomicon.text.crafting.necro_o");
	public static String CRAFTING_LIFE_CRYSTAL_1 = translate("necronomicon.text.crafting.lifecrystal.1");
	public static String CRAFTING_LIFE_CRYSTAL_2 = translate("necronomicon.text.crafting.lifecrystal.2");
	public static String CRAFTING_ETHAXIUM_INGOT_1 = translate("necronomicon.text.crafting.ethaxiumingot.1");
	public static String CRAFTING_ETHAXIUM_INGOT_2 = translate("necronomicon.text.crafting.ethaxiumingot.2");
	public static String CRAFTING_BLANK_ENGRAVING_1 = translate("necronomicon.text.crafting.engravingblank.1");
	public static String CRAFTING_BLANK_ENGRAVING_2 = translate("necronomicon.text.crafting.engravingblank.2");
	public static String CRAFTING_COIN = translate("necronomicon.text.crafting.coin");
	public static String CRAFTING_ENGRAVER = translate("necronomicon.text.crafting.engraver");
	public static String CRAFTING_CRYSTAL_BAG_1 = translate("necronomicon.text.crafting.crystalbag.1");
	public static String CRAFTING_CRYSTAL_BAG_2 = translate("necronomicon.text.crafting.crystalbag.2");
	public static String CRAFTING_MATERIALIZER_1 = translate("necronomicon.text.crafting.materializer.1");
	public static String CRAFTING_MATERIALIZER_2 = translate("necronomicon.text.crafting.materializer.2");
	public static String CRAFTING_ABYSSALNOMICON_1 = translate("necronomicon.text.crafting.abyssalnomicon.1");
	public static String CRAFTING_ABYSSALNOMICON_2 = translate("necronomicon.text.crafting.abyssalnomicon.2");
	public static String CRAFTING_STAFF_OF_RENDING_1 = translate("necronomicon.text.crafting.rendingstaff.1");
	public static String CRAFTING_STAFF_OF_RENDING_2 = translate("necronomicon.text.crafting.rendingstaff.2");
	public static String CRAFTING_SKIN_OF_THE_ABYSSAL_WASTELAND_1 = translate("necronomicon.text.crafting.skin_aby");
	public static String CRAFTING_SKIN_OF_THE_DREADLANDS_1 = translate("necronomicon.text.crafting.skin_dre");
	public static String CRAFTING_SKIN_OF_OMOTHOL_1 = translate("necronomicon.text.crafting.skin_omt");
	public static String CRAFTING_DREAD_CLOTH = translate("necronomicon.text.crafting.dreadcloth");
	public static String CRAFTING_DREADIUM_PLATE = translate("necronomicon.text.crafting.dreadplate");
	public static String CRAFTING_DREADIUM_HILT = translate("necronomicon.text.crafting.dreadiumhilt");
	public static String CRAFTING_DREADIUM_BLADE = translate("necronomicon.text.crafting.dreadiumblade");
	public static String CRAFTING_DREADIUM_SAMURAI_HELMET = translate("necronomicon.text.crafting.dreadiumshelmet");
	public static String CRAFTING_DREADIUM_SAMURAI_CHESTPLATE = translate("necronomicon.text.crafting.dreadiumsplate");
	public static String CRAFTING_DREADIUM_SAMURAI_LEGGINGS = translate("necronomicon.text.crafting.dreadiumslegs");
	public static String CRAFTING_DREADIUM_SAMURAI_BOOTS = translate("necronomicon.text.crafting.dreadiumsboots");
	public static String CRAFTING_DREADIUM_KATANA = translate("necronomicon.text.crafting.dreadiumkatana");
	public static String CRAFTING_CORALIUM_CHUNK = translate("necronomicon.text.crafting.coraliumchunk");
	public static String CRAFTING_CORALIUM_PLATE = translate("necronomicon.text.crafting.coraliumplate");
	public static String CRAFTING_PLATED_CORALIUM_HELMET = translate("necronomicon.text.crafting.corhelmetp");
	public static String CRAFTING_PLATED_CORALIUM_CHESTPLATE = translate("necronomicon.text.crafting.corplatep");
	public static String CRAFTING_PLATED_CORALIUM_LEGGINGS = translate("necronomicon.text.crafting.corlegsp");
	public static String CRAFTING_PLATED_CORALIUM_BOOTS = translate("necronomicon.text.crafting.corbootsp");
	public static String CRAFTING_CORALIUM_LONGBOW = translate("necronomicon.text.crafting.corbow");
	public static String CRAFTING_ENERGY_PEDESTAL_1 = translate("necronomicon.text.crafting.energypedestal.1");
	public static String CRAFTING_ENERGY_PEDESTAL_2 = translate("necronomicon.text.crafting.energypedestal.2");
	public static String CRAFTING_DREADIUM_1 = translate("necronomicon.text.crafting.dreadium.1");
	public static String CRAFTING_DREADIUM_2 = translate("necronomicon.text.crafting.dreadium.2");
	public static String CRAFTING_MONOLITH_PILLAR_1 = translate("necronomicon.text.crafting.monolithpillar.1");
	public static String CRAFTING_MONOLITH_PILLAR_2 = translate("necronomicon.text.crafting.monolithpillar.2");
	public static String CRAFTING_RITUAL_CHARM_1 = translate("necronomicon.text.crafting.ritualcharm.1");
	public static String CRAFTING_RITUAL_CHARM_2 = translate("necronomicon.text.crafting.ritualcharm.2");
	public static String CRAFTING_SACRIFICIAL_ALTAR_1 = translate("necronomicon.text.crafting.sacrificialaltar.1");
	public static String CRAFTING_SACRIFICIAL_ALTAR_2 = translate("necronomicon.text.crafting.sacrificialaltar.2");

	//Misc crafting
	public static String CRAFTING_UPGRADE_KIT_1 = translate("necronomicon.text.crafting.upgradekit.1");
	public static String CRAFTING_UPGRADE_KIT_2 = translate("necronomicon.text.crafting.upgradekit.2");
	public static String CRAFTING_IRON_PLATE = translate("necronomicon.text.crafting.ironplate");
	public static String CRAFTING_WASHCLOTH = translate("necronomicon.text.crafting.washcloth");
	public static String CRAFTING_MRE = translate("necronomicon.text.crafting.mre");
	public static String CRAFTING_PLATE_FOOD = translate("necronomicon.text.crafting.platefood");
	public static String CRAFTING_ODB_CORE = translate("necronomicon.text.crafting.odbcore");
	public static String CRAFTING_ODB = translate("necronomicon.text.crafting.odb");
	public static String CRAFTING_CARBON_CLUSTER = translate("necronomicon.text.crafting.carboncluster");
	public static String CRAFTING_DENSE_CARBON_CLUSTER = translate("necronomicon.text.crafting.densecarboncluster");
	public static String CRAFTING_CRATE = translate("necronomicon.text.crafting.crate");

	//Enchantments
	public static String ENCHANTMENT_CORALIUM = translate("necronomicon.text.enchantment.coralium");
	public static String ENCHANTMENT_DREAD = translate("necronomicon.text.enchantment.dread");
	public static String ENCHANTMENT_LIGHT_PIERCE = translate("necronomicon.text.enchantment.lightpierce");
	public static String ENCHANTMENT_IRON_WALL = translate("necronomicon.text.enchantment.ironwall");

	public static String PROGRESSION_OVERWORLD_1 = translate("necronomicon.text.overworld.progression.1");
	public static String PROGRESSION_OVERWORLD_2 = translate("necronomicon.text.overworld.progression.2");
	public static String PROGRESSION_OVERWORLD_3 = translate("necronomicon.text.overworld.progression.3");
	public static String PROGRESSION_OVERWORLD_4 = translate("necronomicon.text.overworld.progression.4");
	public static String PROGRESSION_OVERWORLD_5 = translate("necronomicon.text.overworld.progression.5");

	public static String PROGRESSION_ABYSSAL_1 = translate("necronomicon.text.abyssal.progression.1");
	public static String PROGRESSION_ABYSSAL_2 = translate("necronomicon.text.abyssal.progression.2");
	public static String PROGRESSION_ABYSSAL_3 = translate("necronomicon.text.abyssal.progression.3");

	public static String PROGRESSION_DREADLANDS_1 = translate("necronomicon.text.dreadlands.progression.1");
	public static String PROGRESSION_DREADLANDS_2 = translate("necronomicon.text.dreadlands.progression.2");
	public static String PROGRESSION_DREADLANDS_3 = translate("necronomicon.text.dreadlands.progression.3");

	public static String PROGRESSION_OMOTHOL_1 = translate("necronomicon.text.omothol.progression.1");
	public static String PROGRESSION_OMOTHOL_2 = translate("necronomicon.text.omothol.progression.2");
	public static String PROGRESSION_OMOTHOL_3 = translate("necronomicon.text.omothol.progression.3");
	public static String PROGRESSION_OMOTHOL_4 = translate("necronomicon.text.omothol.progression.4");
	public static String PROGRESSION_OMOTHOL_5 = translate("necronomicon.text.omothol.progression.5");
	public static String PROGRESSION_OMOTHOL_6 = translate("necronomicon.text.omothol.progression.6");

	public static String PROGRESSION_DARK_REALM_1 = translate("necronomicon.text.darkrealm.progression.1");
	public static String PROGRESSION_DARK_REALM_2 = translate("necronomicon.text.darkrealm.progression.2");

	public static String ENTITY_ANTI_1 = translate("necronomicon.text.entity.anti.1");
	public static String ENTITY_ANTI_2 = translate("necronomicon.text.entity.anti.2");
	public static String ENTITY_EVIL_ANIMALS_1 = translate("necronomicon.text.entity.evilanimals.1");
	public static String ENTITY_EVIL_ANIMALS_2 = translate("necronomicon.text.entity.evilanimals.2");
	public static String ENTITY_ABYSSAL_ZOMBIE_1 = translate("necronomicon.text.entity.abyssalzombie.1");
	public static String ENTITY_ABYSSAL_ZOMBIE_2 = translate("necronomicon.text.entity.abyssalzombie.2");
	public static String ENTITY_DEPTHS_GHOUL_1 = translate("necronomicon.text.entity.depthsghoul.1");
	public static String ENTITY_DEPTHS_GHOUL_2 = translate("necronomicon.text.entity.depthsghoul.2");
	public static String ENTITY_SKELETON_GOLIATH_1 = translate("necronomicon.text.entity.skeletongoliath.1");
	public static String ENTITY_SKELETON_GOLIATH_2 = translate("necronomicon.text.entity.skeletongoliath.2");
	public static String ENTITY_SPECTRAL_DRAGON_1 = translate("necronomicon.text.entity.spectraldragon.1");
	public static String ENTITY_SPECTRAL_DRAGON_2 = translate("necronomicon.text.entity.spectraldragon.2");
	public static String ENTITY_ASORAH_1 = translate("necronomicon.text.entity.asorah.1");
	public static String ENTITY_ASORAH_2 = translate("necronomicon.text.entity.asorah.2");
	public static String ENTITY_ABYSSALNITE_GOLEM_1 = translate("necronomicon.text.entity.abyssalnitegolem.1");
	public static String ENTITY_ABYSSALNITE_GOLEM_2 = translate("necronomicon.text.entity.abyssalnitegolem.2");
	public static String ENTITY_DREADED_ABYSSALNITE_GOLEM_1 = translate("necronomicon.text.entity.dreadgolem.1");
	public static String ENTITY_DREADED_ABYSSALNITE_GOLEM_2 = translate("necronomicon.text.entity.dreadgolem.2");
	public static String ENTITY_DREADLING_1 = translate("necronomicon.text.entity.dreadling.1");
	public static String ENTITY_DREADLING_2 = translate("necronomicon.text.entity.dreadling.2");
	public static String ENTITY_DREAD_SPAWN_1 = translate("necronomicon.text.entity.dreadspawn.1");
	public static String ENTITY_DREAD_SPAWN_2 = translate("necronomicon.text.entity.dreadspawn.2");
	public static String ENTITY_DEMON_ANIMALS_1 = translate("necronomicon.text.entity.demonanimals.1");
	public static String ENTITY_DEMON_ANIMALS_2 = translate("necronomicon.text.entity.demonanimals.2");
	public static String ENTITY_SPAWN_OF_CHAGAROTH_1 = translate("necronomicon.text.entity.chagarothspawn.1");
	public static String ENTITY_SPAWN_OF_CHAGAROTH_2 = translate("necronomicon.text.entity.chagarothspawn.2");
	public static String ENTITY_FIST_OF_CHAGAROTH_1 = translate("necronomicon.text.entity.chagarothfist.1");
	public static String ENTITY_FIST_OF_CHAGAROTH_2 = translate("necronomicon.text.entity.chagarothfist.2");
	public static String ENTITY_DREADGUARD_1 = translate("necronomicon.text.entity.dreadguard.1");
	public static String ENTITY_DREADGUARD_2 = translate("necronomicon.text.entity.dreadguard.2");
	public static String ENTITY_CHAGAROTH_1 = translate("necronomicon.text.entity.chagaroth.1");
	public static String ENTITY_CHAGAROTH_2 = translate("necronomicon.text.entity.chagaroth.2");
	public static String ENTITY_REMNANT_1 = translate("necronomicon.text.entity.remnant.1");
	public static String ENTITY_REMNANT_2 = translate("necronomicon.text.entity.remnant.2");
	public static String ENTITY_OMOTHOL_GHOUL_1 = translate("necronomicon.text.entity.omotholghoul.1");
	public static String ENTITY_OMOTHOL_GHOUL_2 = translate("necronomicon.text.entity.omotholghoul.2");
	public static String ENTITY_OMOTHOL_WARDEN_1 = translate("necronomicon.text.entity.omotholwarden.1");
	public static String ENTITY_OMOTHOL_WARDEN_2 = translate("necronomicon.text.entity.omotholwarden.2");
	public static String ENTITY_MINION_OF_THE_GATEKEEPER_1 = translate("necronomicon.text.entity.gatekeeperminion.1");
	public static String ENTITY_MINION_OF_THE_GATEKEEPER_2 = translate("necronomicon.text.entity.gatekeeperminion.2");
	public static String ENTITY_JZAHAR_1 = translate("necronomicon.text.entity.jzahar.1");
	public static String ENTITY_JZAHAR_2 = translate("necronomicon.text.entity.jzahar.2");
	public static String ENTITY_LESSER_SHOGGOTH_1 = translate("necronomicon.text.entity.lessershoggoth.1");
	public static String ENTITY_LESSER_SHOGGOTH_2 = translate("necronomicon.text.entity.lessershoggoth.2");
	public static String ENTITY_SHADOW_CREATURE_1 = translate("necronomicon.text.entity.shadowcreature.1");
	public static String ENTITY_SHADOW_CREATURE_2 = translate("necronomicon.text.entity.shadowcreature.2");
	public static String ENTITY_SHADOW_MONSTER_1 = translate("necronomicon.text.entity.shadowmonster.1");
	public static String ENTITY_SHADOW_MONSTER_2 = translate("necronomicon.text.entity.shadowmonster.2");
	public static String ENTITY_SHADOW_BEAST_1 = translate("necronomicon.text.entity.shadowbeast.1");
	public static String ENTITY_SHADOW_BEAST_2 = translate("necronomicon.text.entity.shadowbeast.2");
	public static String ENTITY_SHADOW_TITAN_1 = translate("necronomicon.text.entity.shadowtitan.1");
	public static String ENTITY_SHADOW_TITAN_2 = translate("necronomicon.text.entity.shadowtitan.2");
	public static String ENTITY_SACTHOTH_1 = translate("necronomicon.text.entity.sacthoth.1");
	public static String ENTITY_SACTHOTH_2 = translate("necronomicon.text.entity.sacthoth.2");

	public static String RITUAL_INFO = translate("necronomicon.text.rituals.info");

	public static String RITUAL_TUT_1 = translate("necronomicon.text.rituals.1");
	public static String RITUAL_TUT_2 = translate("necronomicon.text.rituals.2");
	public static String RITUAL_TUT_3 = translate("necronomicon.text.rituals.3");
	public static String RITUAL_TUT_4 = translate("necronomicon.text.rituals.4");
	public static String RITUAL_TUT_5 = translate("necronomicon.text.rituals.5");
	public static String RITUAL_TUT_6 = translate("necronomicon.text.rituals.6");
	public static String RITUAL_TUT_7 = translate("necronomicon.text.rituals.7");

	public static String PE_TUT_1 = translate("necronomicon.text.pe.1");
	public static String PE_TUT_2 = translate("necronomicon.text.pe.2");
	public static String PE_TUT_3 = translate("necronomicon.text.pe.3");
	public static String PE_TUT_4 = translate("necronomicon.text.pe.4");
	public static String PE_TUT_5 = translate("necronomicon.text.pe.5");
	public static String PE_TUT_6 = translate("necronomicon.text.pe.6");
	public static String PE_TUT_7 = translate("necronomicon.text.pe.7");
	public static String PE_TUT_8 = translate("necronomicon.text.pe.8");
	public static String PE_TUT_9 = translate("necronomicon.text.pe.9");
	public static String PE_TUT_10 = translate("necronomicon.text.pe.10");
	public static String PE_TUT_11 = translate("necronomicon.text.pe.11");
	public static String PE_TUT_12 = translate("necronomicon.text.pe.12");
	public static String PE_TUT_13 = translate("necronomicon.text.pe.13");

	public static String MACHINES_INFO = translate("necronomicon.text.machines.info");

	public static String MACHINE_INFO_1 = translate("necronomicon.text.machines.1");
	public static String MACHINE_INFO_2 = translate("necronomicon.text.machines.2");
	public static String MACHINE_INFO_3 = translate("necronomicon.text.machines.3");
	public static String MACHINE_INFO_4 = translate("necronomicon.text.machines.4");

	public static String WIP = translate("necronomicon.text.wip");

	/** Text limit */
	public static String TEST = "Lorem ipsum dolor sit amet, nam an mutat eripuit temporibus. Eu eius luptatum similique eam. Erat euismod bonorum cu vis, malis salutatus neglegentur mea no. His et abhorreant conclusionemque, has prima movet dignissim an, vitae deleniti theophrastus ad mea. Est no offendit incorrupte, ferri illum labores qui ut. Sanctus adipisci eum ex, cu falli virtute dissentias.";

	public static String LABEL_TEST = "Lorem ipsum";

	public static String LABEL_INDEX = translate("necronomicon.index");
	public static String LABEL_INFORMATION = translate("necronomicon.information");
	public static String LABEL_SPELLBOOK = translate("necronomicon.spells");
	public static String LABEL_RITUALS = translate("necronomicon.rituals");
	public static String LABEL_HUH = translate("necronomicon.huh");
	public static String LABEL_INFORMATION_ABYSSALCRAFT = translate("necronomicon.information.ac");
	public static String LABEL_INFORMATION_GREAT_OLD_ONES = translate("necronomicon.information.goo");
	public static String LABEL_INFORMATION_ABYSSALNOMICON = translate("necronomicon.information.abyssalnomicon");
	public static String LABEL_INFORMATION_OVERWORLD = translate("necronomicon.information.overworld");
	public static String LABEL_INFORMATION_OVERWORLD_TITLE = translate("necronomicon.information.overworld.title");
	public static String LABEL_INFORMATION_ABYSSAL_WASTELAND = translate("necronomicon.information.abyssal");
	public static String LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE = translate("necronomicon.information.abyssal.title");
	public static String LABEL_INFORMATION_DREADLANDS = translate("necronomicon.information.dreadlands");
	public static String LABEL_INFORMATION_DREADLANDS_TITLE = translate("necronomicon.information.dreadlands.title");
	public static String LABEL_INFORMATION_OMOTHOL = translate("necronomicon.information.omothol");
	public static String LABEL_INFORMATION_OMOTHOL_TITLE = translate("necronomicon.information.omothol.title");
	public static String LABEL_INFORMATION_DARK_REALM = translate("necronomicon.information.darkrealm");
	public static String LABEL_INFORMATION_DARK_REALM_TITLE = translate("necronomicon.information.darkrealm.title");
	public static String LABEL_INFORMATION_MATERIALS = translate("necronomicon.information.materials");
	public static String LABEL_INFORMATION_PROGRESSION = translate("necronomicon.information.progression");
	public static String LABEL_INFORMATION_ENTITIES = translate("necronomicon.information.entities");
	public static String LABEL_OUTER_GODS = translate("necronomicon.information.outergods");
	public static String LABEL_OTHER = translate("necronomicon.other");
	public static String LABEL_LOCKED = translate("necronomicon.locked");
	public static String LABEL_INFORMATION_SPECIAL_MATERIALS = translate("necronomicon.information.specialmaterials");
	public static String LABEL_ANYWHERE = translate("necronomicon.anywhere");
	public static String LABEL_LOCATION = translate("necronomicon.location");
	public static String LABEL_INFO = translate("necronomicon.info");
	public static String LABEL_NORMAL = translate("necronomicon.normal");
	public static String LABEL_PATRONS = translate("necronomicon.patrons");
	public static String LABEL_INFORMATION_ARMOR_TOOLS = translate("necronomicon.information.armortools");
	public static String LABEL_INFORMATION_MACHINES = translate("necronomicon.information.machines");
	public static String LABEL_REQUIRED_ENERGY = translate("necronomicon.reqenergy");
	public static String LABEL_SACRIFICE = translate("necronomicon.sacrifice");
	public static String LABEL_GETTING_STARTED = translate("necronomicon.gettingstarted");
	public static String LABEL_POTENTIAL_ENERGY = translate("necronomicon.potentialenergy");
	public static String LABEL_MISC_INFORMATION = translate("necronomicon.miscinformation");
	public static String LABEL_INFORMATION_ENCHANTMENTS = translate("necronomicon.information.enchantments");

	/**
	 * The best way to drown an array of strings with nonsense
	 * @param par1 How many Lorem Ipsums you would like to order
	 * @return A bag full of free Lorem Ipsums, take it and leave!
	 */
	public static String[] getIpsums(int par1){
		String[] temp = new String[par1];
		for(int i = 0; i < par1; i++)
			temp[i] = TEST;
		return temp;
	}

	private static String translate(String text){
		return I18n.translateToLocal(text);
	}
}