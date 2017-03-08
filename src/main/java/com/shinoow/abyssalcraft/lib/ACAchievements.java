/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib;

import net.minecraft.stats.Achievement;

/**
 * Achievement references
 * @author shinoow
 *
 */
public class ACAchievements {

	public static Achievement mine_abyssalnite;
	public static Achievement kill_depths_ghoul;
	public static Achievement enter_abyssal_wasteland;
	public static Achievement kill_spectral_dragon;
	public static Achievement summon_asorah;
	public static Achievement kill_asorah;
	public static Achievement enter_dreadlands;
	public static Achievement kill_dreadguard;
	public static Achievement depths_ghoul_head;
	public static Achievement pete_head;
	public static Achievement mr_wilson_head;
	public static Achievement dr_orange_head;
	public static Achievement mine_coralium;
	public static Achievement mine_abyssal_coralium;
	public static Achievement find_powerstone;
	public static Achievement gateway_key;
	public static Achievement dreaded_gateway_key;
	public static Achievement rlyehian_gateway_key;
	public static Achievement summon_chagaroth;
	public static Achievement kill_chagaroth;
	public static Achievement enter_omothol;
	public static Achievement enter_dark_realm;
	public static Achievement kill_jzahar;
	public static Achievement kill_omothol_elite;
	public static Achievement locate_jzahar;
	public static Achievement necronomicon;
	public static Achievement abyssal_wasteland_necronomicon;
	public static Achievement dreadlands_necronomicon;
	public static Achievement omothol_necronomicon;
	public static Achievement abyssalnomicon;
	public static Achievement ritual_altar;
	public static Achievement summoning_ritual;
	public static Achievement creation_ritual;
	public static Achievement shadow_gems;
	public static Achievement mine_abyssal_ores;
	public static Achievement mine_dreadlands_ores;
	public static Achievement dreadium;
	public static Achievement ethaxium;
	public static Achievement make_transmutator;
	public static Achievement make_crystallizer;
	public static Achievement make_materializer;
	public static Achievement make_crystal_bag;
	public static Achievement make_engraver;
	public static Achievement breeding_ritual;
	public static Achievement potion_ritual;
	public static Achievement aoe_potion_ritual;
	public static Achievement infusion_ritual;

	public static Achievement[] getAchievements(){
		return new Achievement[] {necronomicon, mine_abyssalnite, kill_depths_ghoul, enter_abyssal_wasteland,
				kill_spectral_dragon, summon_asorah, kill_asorah, enter_dreadlands, kill_dreadguard, depths_ghoul_head, pete_head,
				mr_wilson_head, dr_orange_head, mine_coralium, mine_abyssal_coralium, find_powerstone, gateway_key,
				dreaded_gateway_key, rlyehian_gateway_key, summon_chagaroth, kill_chagaroth,enter_omothol, enter_dark_realm,
				abyssal_wasteland_necronomicon, dreadlands_necronomicon, omothol_necronomicon, abyssalnomicon, ritual_altar,
				summoning_ritual, creation_ritual, kill_omothol_elite, locate_jzahar, kill_jzahar, shadow_gems, mine_abyssal_ores,
				mine_dreadlands_ores, dreadium, ethaxium, make_transmutator, make_crystallizer, make_materializer, make_crystal_bag,
				make_engraver, breeding_ritual, potion_ritual, aoe_potion_ritual, infusion_ritual};
	}
}
