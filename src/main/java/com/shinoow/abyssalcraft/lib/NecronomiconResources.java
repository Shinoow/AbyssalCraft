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

import net.minecraft.util.ResourceLocation;

/**
 * That one place where you keep a billion ResourceLocations representing stuff
 * @author shinoow
 *
 */
public class NecronomiconResources {

	public static final ResourceLocation ABYSSALCRAFT_1 = res("abyssalcraft_1");
	public static final ResourceLocation ABYSSALCRAFT_2 = res("abyssalcraft_2");
	public static final ResourceLocation ABYSSALCRAFT_3 = res("abyssalcraft_3");

	//Overworld entities (only going to use one picture for the anti-entities)
	public static final ResourceLocation ANTI_ENTITIES = res("anti-entities");
	public static final ResourceLocation EVIL_ANIMALS = res("evil-animals");
	public static final ResourceLocation LESSER_SHOGGOTH = res("lesser-shoggoth");
	public static final ResourceLocation SHUB_OFFSPRING = res("shub-offspring");

	//Abyssal Wasteland entities (making entries for vanilla mobs is lethally hipster)
	public static final ResourceLocation ABYSSAL_ZOMBIE = res("abyssal-zombie");
	public static final ResourceLocation DEPTHS_GHOUL = res("depths-ghoul");
	public static final ResourceLocation SKELETON_GOLIATH = res("skeleton-goliath");
	public static final ResourceLocation SPECTRAL_DRAGON = res("spectral-dragon");
	public static final ResourceLocation ASORAH = res("asorah");
	public static final ResourceLocation CORALIUM_INFESTED_SQUID = res("coralium-infested-squid");

	//Dreadlands entities
	public static final ResourceLocation ABYSSALNITE_GOLEM = res("abyssalnite-golem");
	public static final ResourceLocation DREADED_ABYSSALNITE_GOLEM = res("dreaded-abyssalnite-golem");
	public static final ResourceLocation DREADLING = res("dreadling");
	public static final ResourceLocation DREAD_SPAWN = res("dread-spawn");
	public static final ResourceLocation DEMON_ANIMALS = res("demon-animals");
	public static final ResourceLocation SPAWN_OF_CHAGAROTH = res("spawn-of-chagaroth");
	public static final ResourceLocation FIST_OF_CHAGAROTH = res("fist-of-chagaroth");
	public static final ResourceLocation DREADGUARD = res("dreadguard");
	public static final ResourceLocation CHAGAROTH = res("chagaroth");

	//Omothol entities
	public static final ResourceLocation REMNANT = res("remnant");
	public static final ResourceLocation OMOTHOL_GHOUL = res("omothol-ghoul");
	public static final ResourceLocation OMOTHOL_WARDEN = res("missing"); //TODO: fix
	public static final ResourceLocation MINION_OF_THE_GATEKEEPER = res("minion-of-the-gatekeeper");
	public static final ResourceLocation JZAHAR = res("jzahar");

	//Dark Realm entities
	public static final ResourceLocation SHADOW_CREATURE = res("shadow-creature");
	public static final ResourceLocation SHADOW_MONSTER = res("shadow-monster");
	public static final ResourceLocation SHADOW_BEAST = res("shadow-beast");
	public static final ResourceLocation SHADOW_TITAN = res("missing"); //TODO: fix
	public static final ResourceLocation SACTHOTH = res("sacthoth");

	//Seals representing The Great Old Ones, no idea how many I'm going to do
	public static final ResourceLocation AZATHOTH_SEAL = res("azathoth");
	public static final ResourceLocation NYARLATHOTEP_SEAL = res("nyarlathotep");
	public static final ResourceLocation YOG_SOTHOTH_SEAL = res("yog-sothoth");
	public static final ResourceLocation SHUB_NIGGURATH_SEAL = res("shub-niggurath");
	public static final ResourceLocation CTHULHU_SEAL = res("cthulhu");
	public static final ResourceLocation HASTUR_SEAL = res("hastur");
	public static final ResourceLocation JZAHAR_SEAL = res("j-zahar");

	//Crafting grid
	public static final ResourceLocation CRAFTING = res("crafting");

	//Item frame
	public static final ResourceLocation ITEM = res("item");

	//Ritual grid
	public static final ResourceLocation RITUAL = res("ritual");

	//Creation Ritual slot
	public static final ResourceLocation RITUAL_CREATION = res("ritual_creation");

	//Infusion Ritual slot
	public static final ResourceLocation RITUAL_INFUSION = res("ritual_infusion");

	public static final ResourceLocation RITUAL_TUT_1 = res("ritual_1");
	public static final ResourceLocation RITUAL_TUT_2 = res("ritual_2");
	public static final ResourceLocation RITUAL_TUT_3 = res("ritual_3");

	public static final ResourceLocation PE_TUT_1 = res("pe/shoggoth_lair");
	public static final ResourceLocation PE_TUT_2 = res("pe/monoliths");
	public static final ResourceLocation PE_TUT_3 = res("pe/shoggoth_infestation");
	public static final ResourceLocation PE_TUT_4 = res("pe/statues");
	public static final ResourceLocation PE_TUT_5 = res("pe/statue_range");
	public static final ResourceLocation PE_TUT_6 = res("pe/statue_range_2");
	public static final ResourceLocation PE_TUT_7 = res("pe/energy_depositioner");
	public static final ResourceLocation PE_TUT_8 = res("pe/charms");
	public static final ResourceLocation PE_TUT_9 = res("pe/energy_pedestal");
	public static final ResourceLocation PE_TUT_10 = res("pe/sacrificial_altar");
	public static final ResourceLocation PE_TUT_11 = res("pe/energy_collector");
	public static final ResourceLocation PE_TUT_12 = res("pe/energy_relay");
	public static final ResourceLocation PE_TUT_13 = res("pe/relays");
	public static final ResourceLocation PE_TUT_14 = res("pe/energy_container");
	public static final ResourceLocation PE_TUT_15 = res("pe/tiers");

	public static final ResourceLocation ITEM_TRANSPORT_TUT_1 = res("transport/transport_1");
	public static final ResourceLocation ITEM_TRANSPORT_TUT_2 = res("transport/transport_2");

	//Transmutation grid
	public static final ResourceLocation TRANSMUTATION = res("transmutation");

	//Crystallization grid (additional slot to Transmutation grid)
	public static final ResourceLocation CRYSTALLIZATION = res("crystallization");

	//Materialization grid
	public static final ResourceLocation MATERIALIZATION = res("materialization");

	//Spell grid
	public static final ResourceLocation SPELL = res("spell");

	//Place of Power frame
	public static final ResourceLocation PLACE_OF_POWER = res("placeofpower");

	//Blank image
	public static final ResourceLocation BLANK = res("blank");

	private static ResourceLocation res(String name){
		return new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/" + name + ".png");
	}
}
