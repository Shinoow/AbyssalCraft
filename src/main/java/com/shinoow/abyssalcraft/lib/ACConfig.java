/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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

	public static boolean keepLoaded1, keepLoaded2, keepLoaded3, keepLoaded4;

	public static boolean shouldSpread, shouldInfect, breakLogic, destroyOcean, demonAnimalFire, particleBlock,
	particleEntity, hardcoreMode, antiItemDisintegration, smeltingRecipes, purgeMobSpawns, mimicFire,
	armorPotionEffects, nuclearAntimatterExplosions, syncDataOnBookOpening, dreadGrassSpread, portalSpawnsNearPlayer,
	showBossDialogs, jzaharBreaksFourthWall, lootTableContent, depthsGhoulBiomeDictSpawn, abyssalZombieBiomeDictSpawn;
	public static int evilAnimalSpawnWeight, portalCooldown, demonAnimalSpawnWeight, shoggothLairSpawnRate, acidSpitFrequency,
	knowledgeSyncDelay, shoggothLairSpawnRateRivers, darkOffspringSpawnWeight;
	public static double damageAmpl, depthsHelmetOverlayOpacity;
	public static boolean shoggothOoze, oozeExpire, consumeItems, shieldsBlockAcid;
	public static double acidResistanceHardness;
	public static boolean generateDarklandsStructures, generateShoggothLairs, generateAbyssalWastelandPillars,
	generateAbyssalWastelandRuins, generateAntimatterLake, generateCoraliumLake, generateDreadlandsStalagmite;
	public static boolean generateCoraliumOre, generateNitreOre, generateAbyssalniteOre, generateAbyssalCoraliumOre,
	generateDreadlandsAbyssalniteOre, generateDreadedAbyssalniteOre, generateAbyssalIronOre, generateAbyssalGoldOre,
	generateAbyssalDiamondOre, generateAbyssalNitreOre, generateAbyssalTinOre, generateAbyssalCopperOre,
	generatePearlescentCoraliumOre, generateLiquifiedCoraliumOre;
	public static boolean hcdarkness_aw, hcdarkness_dl, hcdarkness_omt, hcdarkness_dr;
	public static boolean no_dreadlands_spread, no_acid_breaking_blocks, no_spectral_dragons, no_projectile_damage_immunity, no_disruptions, no_black_holes;

	public static boolean darkstone_brick_slab, darkstone_cobblestone_slab, darkstone_brick_stairs, darkstone_cobblestone_stairs, darkstone_slab,
	darklands_oak_slab, darklands_oak_stairs, abyssal_stone_brick_slab, abyssal_stone_brick_stairs, coralium_stone_brick_slab, coralium_stone_brick_stairs,
	dreadstone_brick_slab, dreadstone_brick_stairs, abyssalnite_stone_brick_slab, abyssalnite_stone_brick_stairs, ethaxium_brick_slab, ethaxium_brick_stairs,
	abyssal_cobblestone_slab, abyssal_cobblestone_stairs, coralium_cobblestone_slab, coralium_cobblestone_stairs, dreadstone_cobblestone_slab,
	dreadstone_cobblestone_stairs, abyssalnite_cobblestone_slab, abyssalnite_cobblestone_stairs, darkstone_cobblestone_wall, abyssal_cobbblestone_wall,
	coralium_cobblestone_wall, dreadstone_cobblestone_wall, abyssalnite_cobblestone_wall;

	@Deprecated
	public static int endAbyssalZombieSpawnWeight;
	@Deprecated
	public static boolean evilAnimalCreatureType, overworldShoggoths;
}
