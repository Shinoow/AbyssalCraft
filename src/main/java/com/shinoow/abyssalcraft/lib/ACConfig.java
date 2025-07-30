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

/**
 * Config option references (except dimension IDs, they're in {@link ACLib})
 * @author shinoow
 *
 */
public class ACConfig {


	// General Category

	public static boolean shouldSpread, shouldInfect, destroyOcean, hardcoreMode,
	antiItemDisintegration, smeltingRecipes, purgeMobSpawns, mimicFire,
	armorPotionEffects, syncDataOnBookOpening, portalSpawnsNearPlayer,
	showBossDialogs, lootTableContent, nightVisionEverywhere,
	no_potion_clouds, vanilla_portals;

	public static double damageAmpl;

	public static int knowledgeSyncDelay;

	// Dimensions Category

	public static boolean keepLoaded1, keepLoaded2, keepLoaded3, keepLoaded4;

	public static int portalCooldown, startDimension;

	// Mobs Category

	public static boolean demonAnimalFire, depthsGhoulBiomeDictSpawn, abyssalZombieBiomeDictSpawn,
	demonAnimalsSpawnOnDeath, evilAnimalNewMoonSpawning, antiPlayersPickupLoot;

	public static int evilAnimalSpawnWeight, demonAnimalSpawnWeight, darkOffspringSpawnWeight,
	dreadSpawnSpawnLimit, greaterDreadSpawnSpawnLimit, jzaharHealingPace, jzaharHealingAmount,
	chagarothHealingPace, chagarothHealingAmount, sacthothHealingPace, sacthothHealingAmount;

	// Client Category

	public static boolean particleBlock, particleEntity, darkRealmSmokeParticles;

	public static double depthsHelmetOverlayOpacity;

	// Ritual Category

	public static boolean enchantBooks, enchantMergedBooks;

	public static int corruptionRitualRange, cleansingRitualRange, purgingRitualRange,
	enchantmentMaxLevel, curingRitualRange, infestingRitualRange;

	// Shoggoth Category

	public static boolean shoggothOoze, oozeExpire, consumeItems, shieldsBlockAcid, shoggothGlowingEyes;

	public static double acidResistanceHardness;

	public static int acidSpitFrequency, monolithBuildingCooldown, biomassPlayerDistance,
	biomassMaxSpawn, biomassCooldown, biomassShoggothDistance;

	// World Generation Category

	// Structures, lakes etc
	public static boolean generateDarklandsStructures, generateShoggothLairs, useAmplifiedWorldType,
	generateAbyssalWastelandPillars, generateAbyssalWastelandRuins, generateAntimatterLake,
	generateCoraliumLake, generateDreadlandsStalagmite, generateStatuesInLairs, generateGraveyards;

	// Ores
	public static boolean generateCoraliumOre, generateNitreOre, generateAbyssalniteOre, generateAbyssalCoraliumOre,
	generateDreadlandsAbyssalniteOre, generateDreadedAbyssalniteOre, generateAbyssalIronOre, generateAbyssalGoldOre,
	generateAbyssalDiamondOre, generateAbyssalNitreOre, generatePearlescentCoraliumOre, generateLiquifiedCoraliumOre;

	public static int shoggothLairSpawnRate, shoggothLairSpawnRateRivers, shoggothLairGenerationDistance,
	darkShrineSpawnRate, darkRitualGroundsSpawnRate, graveyardGenerationDistance, graveyardGenerationChance;

	// Silly Settings Category

	public static boolean breakLogic, nuclearAntimatterExplosions, jzaharBreaksFourthWall;

	public static int odbExplosionSize, antimatterExplosionSize;

	// Wet Noodle Category

	public static boolean no_dreadlands_spread, no_acid_breaking_blocks, no_spectral_dragons, no_projectile_damage_immunity,
	no_disruptions, no_black_holes, no_odb_explosions;

	// Mod compatibility Category

	public static boolean hcdarkness_aw, hcdarkness_dl, hcdarkness_omt, hcdarkness_dr;

	// Spells Category

	public static boolean entropy_spell, life_drain_spell, mining_spell, grasp_of_cthulhu_spell, invisibility_spell,
	detachment_spell, steal_vigor_spell, sirens_song_spell, undeath_to_dust_spell, ooze_removal_spell, teleport_hostile_spell,
	floating_spell, teleport_home_spell, compass_spell;

	//Modules Category

	public static boolean spirit_items;

	// Ghoul Category

	public static boolean ghouls_burn;

	public static int tombstoneMaxSpawn, tombstoneGhoulDistance, tombstoneCooldown;

	// Category names

	public static final String CATEGORY_DIMENSIONS = "dimensions";
	public static final String CATEGORY_SHOGGOTH = "shoggoth";
	public static final String CATEGORY_WORLDGEN = "worldgen";
	public static final String CATEGORY_SILLY_SETTINGS = "silly_settings";
	public static final String CATEGORY_MOD_COMPAT = "mod_compat";
	public static final String CATEGORY_WET_NOODLE = "wet_noodle";
	public static final String CATEGORY_MODULES = "modules";
	public static final String CATEGORY_SPELLS = "spells";
	public static final String CATEGORY_MOBS = "mobs";
	public static final String CATEGORY_RITUALS = "rituals";
	public static final String CATEGORY_GHOUL = "ghoul";
}
