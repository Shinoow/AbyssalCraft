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
package com.shinoow.abyssalcraft.lib;

import net.minecraft.advancements.Advancement;

/**
 * Achievement references
 * @author shinoow
 *
 */
public class ACAchievements {

	public static Advancement mine_abyssalnite;
	public static Advancement kill_depths_ghoul;
	public static Advancement enter_abyssal_wasteland;
	public static Advancement kill_spectral_dragon;
	public static Advancement summon_asorah;
	public static Advancement kill_asorah;
	public static Advancement enter_dreadlands;
	public static Advancement kill_dreadguard;
	public static Advancement depths_ghoul_head;
	public static Advancement pete_head;
	public static Advancement mr_wilson_head;
	public static Advancement dr_orange_head;
	public static Advancement mine_coralium;
	public static Advancement mine_abyssal_coralium;
	public static Advancement find_powerstone;
	public static Advancement gateway_key;
	public static Advancement dreaded_gateway_key;
	public static Advancement rlyehian_gateway_key;
	public static Advancement summon_chagaroth;
	public static Advancement kill_chagaroth;
	public static Advancement enter_omothol;
	public static Advancement enter_dark_realm;
	public static Advancement kill_jzahar;
	public static Advancement kill_omothol_elite;
	public static Advancement locate_jzahar;
	public static Advancement necronomicon;
	public static Advancement abyssal_wasteland_necronomicon;
	public static Advancement dreadlands_necronomicon;
	public static Advancement omothol_necronomicon;
	public static Advancement abyssalnomicon;
	public static Advancement ritual_altar;
	public static Advancement summoning_ritual;
	public static Advancement creation_ritual;
	public static Advancement shadow_gems;
	public static Advancement mine_abyssal_ores;
	public static Advancement mine_dreadlands_ores;
	public static Advancement dreadium;
	public static Advancement ethaxium;
	public static Advancement make_transmutator;
	public static Advancement make_crystallizer;
	public static Advancement make_materializer;
	public static Advancement make_crystal_bag;
	public static Advancement make_engraver;
	public static Advancement breeding_ritual;
	public static Advancement potion_ritual;
	public static Advancement aoe_potion_ritual;
	public static Advancement infusion_ritual;

	public static Advancement[] getAchievements(){
		return new Advancement[] {necronomicon, mine_abyssalnite, kill_depths_ghoul, enter_abyssal_wasteland,
				kill_spectral_dragon, summon_asorah, kill_asorah, enter_dreadlands, kill_dreadguard, depths_ghoul_head, pete_head,
				mr_wilson_head, dr_orange_head, mine_coralium, mine_abyssal_coralium, find_powerstone, gateway_key,
				dreaded_gateway_key, rlyehian_gateway_key, summon_chagaroth, kill_chagaroth,enter_omothol, enter_dark_realm,
				abyssal_wasteland_necronomicon, dreadlands_necronomicon, omothol_necronomicon, abyssalnomicon, ritual_altar,
				summoning_ritual, creation_ritual, kill_omothol_elite, locate_jzahar, kill_jzahar, shadow_gems, mine_abyssal_ores,
				mine_dreadlands_ores, dreadium, ethaxium, make_transmutator, make_crystallizer, make_materializer, make_crystal_bag,
				make_engraver, breeding_ritual, potion_ritual, aoe_potion_ritual, infusion_ritual};
	}
}
