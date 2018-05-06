/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
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
	particleEntity, hardcoreMode, evilAnimalCreatureType, antiItemDisintegration, smeltingRecipes, purgeMobSpawns,
	overworldShoggoths, mimicFire, armorPotionEffects, nuclearAntimatterExplosions, syncDataOnBookOpening;
	public static int evilAnimalSpawnWeight, portalCooldown, demonAnimalSpawnWeight, shoggothLairSpawnRate;
	public static double damageAmpl, depthsHelmetOverlayOpacity;
	public static boolean shoggothOoze, oozeExpire, consumeItems, shieldsBlockAcid;
	public static double acidResistanceHardness;
	public static boolean generateDarklandsStructures, generateShoggothLairs, generateAbyssalWastelandPillars,
	generateAbyssalWastelandRuins, generateAntimatterLake, generateCoraliumLake, generateDreadlandsStalagmite;
	public static boolean generateCoraliumOre, generateNitreOre, generateAbyssalniteOre, generateAbyssalCoraliumOre,
	generateDreadlandsAbyssalniteOre, generateDreadedAbyssalniteOre, generateAbyssalIronOre, generateAbyssalGoldOre,
	generateAbyssalDiamondOre, generateAbyssalNitreOre, generateAbyssalTinOre, generateAbyssalCopperOre,
	generatePearlescentCoraliumOre, generateLiquifiedCoraliumOre;

	@Deprecated
	public static int endAbyssalZombieSpawnWeight;
}
