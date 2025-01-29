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
package com.shinoow.abyssalcraft.api.knowledge;

/**
 * Contains all research items added in AbyssalCraft<br>
 * (WIP at this stage, direct copy from UnlockConditions)
 *
 * @author shinoow
 *
 */
public class ResearchItems {

	// Default Research Item
	public static IResearchItem DEFAULT = new DefaultResearchItem();

	// Biome conditions
	public static IResearchItem DARKLANDS_BIOME;
	public static IResearchItem CORALIUM_INFESTED_SWAMP;
	public static IResearchItem CORALIUM_BIOMES;

	// Dimension conditions
	public static IResearchItem ABYSSAL_WASTELAND;
	public static IResearchItem DREADLANDS;
	public static IResearchItem OMOTHOL;
	public static IResearchItem DARK_REALM;
	public static IResearchItem NETHER;

	// Entity conditions
	public static IResearchItem ABYSSAL_ZOMBIE;
	public static IResearchItem DEPTHS_GHOUL;
	public static IResearchItem SACTHOTH;
	public static IResearchItem SHADOW_MOBS;
	public static IResearchItem SHADOW_CREATURE;
	public static IResearchItem SHADOW_MONSTER;
	public static IResearchItem SHADOW_BEAST;
	public static IResearchItem SKELETON_GOLIATH;
	public static IResearchItem SPECTRAL_DRAGON;
	public static IResearchItem OMOTHOL_GHOUL;
	public static IResearchItem DREADED_ABYSSALNITE_GOLEM;
	public static IResearchItem ABYSSALNITE_GOLEM;
	public static IResearchItem ELITE_DREAD_MOB;
	public static IResearchItem DREAD_MOB;
	public static IResearchItem KILLED_ALL_BOSSES;
	public static IResearchItem ANTI_MOB;
	public static IResearchItem EVIL_ANIMAL;
	public static IResearchItem SHOGGOTH;
	public static IResearchItem SHUB_OFFSPRING;
	public static IResearchItem CORALIUM_INFESTED_SQUID;
	public static IResearchItem DREAD_SPAWN;
	public static IResearchItem DREADLING;
	public static IResearchItem DEMON_ANIMAL;
	public static IResearchItem SPAWN_OF_CHAGAROTH;
	public static IResearchItem FIST_OF_CHAGAROTH;
	public static IResearchItem DREADGUARD;
	public static IResearchItem MINION_OF_THE_GATEKEEPER;

	// Misc conditions
	public static IResearchItem CORALIUM_PLAGUE;
	public static IResearchItem DREAD_PLAGUE;

	// Book conditions
	public static IResearchItem ABYSSAL_WASTELAND_NECRO;
	public static IResearchItem DREADLANDS_NECRO;
	public static IResearchItem OMOTHOL_NECRO;
	public static IResearchItem ABYSSALNOMICON;

	public static IResearchItem getBookResearch(int book) {
		switch (book) {
		case 1:
			return ABYSSAL_WASTELAND_NECRO;
		case 2:
			return DREADLANDS_NECRO;
		case 3:
			return OMOTHOL_NECRO;
		case 4:
			return ABYSSALNOMICON;
		default:
			return DEFAULT;
		}
	}
}
