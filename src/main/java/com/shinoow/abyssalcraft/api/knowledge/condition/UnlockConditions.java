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
package com.shinoow.abyssalcraft.api.knowledge.condition;

import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;

/**
 * Contains all unlock conditions added in AbyssalCraft
 *
 * @author shinoow
 *
 */
public class UnlockConditions {

	//Biome conditions
	public static IUnlockCondition DARKLANDS_BIOME = new BiomePredicateCondition(b -> b instanceof IDarklandsBiome);
	public static IUnlockCondition CORALIUM_INFESTED_SWAMP;
	public static IUnlockCondition CORALIUM_BIOMES;

	//Dimension conditions
	public static IUnlockCondition ABYSSAL_WASTELAND;
	public static IUnlockCondition DREADLANDS;
	public static IUnlockCondition OMOTHOL;
	public static IUnlockCondition DARK_REALM;
	public static IUnlockCondition NETHER = new DimensionCondition(-1);

	//Entity conditions
	public static IUnlockCondition ABYSSAL_ZOMBIE = new EntityCondition("abyssalcraft:abyssalzombie");
	public static IUnlockCondition DEPTHS_GHOUL = new EntityCondition("abyssalcraft:depthsghoul");
	public static IUnlockCondition SACTHOTH = new EntityCondition("abyssalcraft:shadowboss");
	public static IUnlockCondition SHADOW_MOBS = new MultiEntityCondition("abyssalcraft:shadowcreature", "abyssalcraft:shadowmonster","abyssalcraft:shadowbeast");
	public static IUnlockCondition SHADOW_CREATURE = new EntityCondition("abyssalcraft:shadowcreature");
	public static IUnlockCondition SHADOW_MONSTER = new EntityCondition("abyssalcraft:shadowmonster");
	public static IUnlockCondition SHADOW_BEAST = new EntityCondition("abyssalcraft:shadowbeast");
	public static IUnlockCondition SKELETON_GOLIATH = new EntityCondition("abyssalcraft:gskeleton");
	public static IUnlockCondition SPECTRAL_DRAGON = new EntityCondition("abyssalcraft:dragonminion");
	public static IUnlockCondition OMOTHOL_GHOUL = new EntityCondition("abyssalcraft:omotholghoul");
	public static IUnlockCondition DREADED_ABYSSALNITE_GOLEM = new EntityCondition("abyssalcraft:dreadgolem");
	public static IUnlockCondition ABYSSALNITE_GOLEM = new EntityCondition("abyssalcraft:abygolem");
	public static IUnlockCondition ELITE_DREAD_MOB = new MultiEntityCondition("abyssalcraft:dreadguard", "abyssalcraft:greaterdreadspawn", "abyssalcraft:lesserdreadbeast");
	public static IUnlockCondition DREAD_MOB = new EntityPredicateCondition(e -> IDreadEntity.class.isAssignableFrom(e));
	public static IUnlockCondition KILLED_ALL_BOSSES = new MandatoryMultiEntityCondition("abyssalcraft:dragonboss", "abyssalcraft:chagaroth", "abyssalcraft:jzahar", "abyssalcraft:shadowboss");
	public static IUnlockCondition ANTI_MOB = new EntityPredicateCondition(e -> IAntiEntity.class.isAssignableFrom(e));
	public static IUnlockCondition EVIL_ANIMAL;
	public static IUnlockCondition SHOGGOTH;
	public static IUnlockCondition SHUB_OFFSPRING = new EntityCondition("abyssalcraft:shuboffspring");
	public static IUnlockCondition CORALIUM_INFESTED_SQUID = new EntityCondition("abyssalcraft:coraliumsquid");
	public static IUnlockCondition DREAD_SPAWN = new MultiEntityCondition("abyssalcraft:dreadspawn", "abyssalcraft:greaterdreadspawn", "abyssalcraft:lesserdreadbeast");
	public static IUnlockCondition DREADLING = new EntityCondition("abyssalcraft:dreadling");
	public static IUnlockCondition DEMON_ANIMAL;
	public static IUnlockCondition SPAWN_OF_CHAGAROTH = new EntityCondition("abyssalcraft:chagarothspawn");
	public static IUnlockCondition FIST_OF_CHAGAROTH = new EntityCondition("abyssalcraft:chagarothfist");
	public static IUnlockCondition DREADGUARD = new EntityCondition("abyssalcraft:dreadguard");
	public static IUnlockCondition MINION_OF_THE_GATEKEEPER = new EntityCondition("abyssalcraft:jzaharminion");

	//Misc conditions
	public static IUnlockCondition CORALIUM_PLAGUE = new MiscCondition("coralium_plague");
	public static IUnlockCondition DREAD_PLAGUE = new MiscCondition("dread_plague");

	//Book conditions
	public static IUnlockCondition ABYSSAL_WASTELAND_NECRO = new NecronomiconCondition(1);
	public static IUnlockCondition DREADLANDS_NECRO = new NecronomiconCondition(2);
	public static IUnlockCondition OMOTHOL_NECRO = new NecronomiconCondition(3);
	public static IUnlockCondition ABYSSALNOMICON = new NecronomiconCondition(4);

}
