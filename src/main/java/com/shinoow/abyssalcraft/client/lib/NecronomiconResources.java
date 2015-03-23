/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.client.lib;

import net.minecraft.util.ResourceLocation;

/**
 * That one place where you keep a billion ResourceLocations representing stuff
 * @author shinoow
 *
 */
public class NecronomiconResources {

	//Overworld entities (only going to use one picture for the anti-entities)
	public static final ResourceLocation ANTI_ENTITIES = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/anti-entities.png");
	public static final ResourceLocation EVIL_PIG = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/evil-pig.png");

	//Abyssal Wasteland entities (making entries for vanilla mobs is lethally hipster)
	public static final ResourceLocation ABYSSAL_ZOMBIE = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/abyssal-zombie.png");
	public static final ResourceLocation DEPTHS_GHOUL = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/depths-ghoul.png");
	public static final ResourceLocation SKELETON_GOLIATH = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/skeleton-goliath.png");
	public static final ResourceLocation SPECTRAL_DRAGON = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/spectral-dragon.png");
	public static final ResourceLocation ASORAH = null;

	//Dreadlands entities
	public static final ResourceLocation ABYSSALNITE_GOLEM = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/abyssalnite-golem.png");
	public static final ResourceLocation DREADED_ABYSSALNITE_GOLEM = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/dreaded-abyssalnite-golem.png");
	public static final ResourceLocation DREADLING = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/dreadling.png");
	public static final ResourceLocation DREAD_SPAWN = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/dread-spawn.png");
	public static final ResourceLocation DEMON_PIG = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/demon-pig.png");
	public static final ResourceLocation SPAWN_OF_CHAGAROTH = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/spawn-of-chagaroth.png");
	public static final ResourceLocation FIST_OF_CHAGAROTH = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/fist-of-chagaroth.png");
	public static final ResourceLocation DREADGUARD = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/dreadguard.png");
	public static final ResourceLocation CHAGAROTH = null;

	//Omothol entities
	public static final ResourceLocation REMNANT = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/remnant.png");
	public static final ResourceLocation OMOTHOL_GHOUL = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/omothol-ghoul.png");
	public static final ResourceLocation OMOTHOL_WARDEN = null;
	public static final ResourceLocation MINION_OF_THE_GATEKEEPER = null;
	public static final ResourceLocation JZAHAR = null;
	public static final ResourceLocation LESSER_SHOGGOTH = null;

	//Dark Realm entities
	public static final ResourceLocation SHADOW_CREATURE = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/shadow-creature.png");
	public static final ResourceLocation SHADOW_MONSTER = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/shadow-monster.png");
	public static final ResourceLocation SHADOW_BEAST = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/shadow-beast.png");
	public static final ResourceLocation SHADOW_TITAN = null;
	public static final ResourceLocation SACTHOTH = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/sacthoth.png");

	//Seals representing The Great Old Ones, no idea how many I'm going to do
	public static final ResourceLocation AZATHOTH_SEAL = null;
	public static final ResourceLocation NYARLATHOTEP_SEAL = null;
	public static final ResourceLocation YOG_SOTHOTH_SEAL = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/yog-sothoth");
	public static final ResourceLocation SHUB_NIGGURATH_SEAL = new ResourceLocation("abyssalcraft:textures/gui/necronomicon/shub-niggurath");
	public static final ResourceLocation CTHULHU_SEAL = null;
	public static final ResourceLocation HASTUR_SEAL = null;
	public static final ResourceLocation JZAHAR_SEAL = null;

	public static final ResourceLocation[] OVERWORLD_ENTITIES = {ABYSSAL_ZOMBIE, DEPTHS_GHOUL, SHADOW_CREATURE, SHADOW_MONSTER, SHADOW_BEAST,
		ANTI_ENTITIES, EVIL_PIG};

	public static final ResourceLocation[] ABYSSAL_WASTELAND_ENTITIES = {ABYSSAL_ZOMBIE, DEPTHS_GHOUL, SKELETON_GOLIATH, SPECTRAL_DRAGON, ASORAH};

	public static final ResourceLocation[] DREADLANDS_ENTITIES = {ABYSSALNITE_GOLEM, DREADED_ABYSSALNITE_GOLEM, DREADLING, DREAD_SPAWN,
		DEMON_PIG, SPAWN_OF_CHAGAROTH, FIST_OF_CHAGAROTH, DREADGUARD, CHAGAROTH};

	public static final ResourceLocation[] OMOTHOL_ENTITIES = {REMNANT, OMOTHOL_GHOUL, OMOTHOL_WARDEN, MINION_OF_THE_GATEKEEPER, JZAHAR,
		LESSER_SHOGGOTH};

	public static final ResourceLocation[] DARK_REALM_ENTITIES = {SHADOW_CREATURE, SHADOW_MONSTER, SHADOW_BEAST, SHADOW_TITAN, SACTHOTH};

	//This one will be inactive for a while, got tons of junk to write
	protected static final ResourceLocation[] GREAT_OLD_ONES = {AZATHOTH_SEAL, NYARLATHOTEP_SEAL, YOG_SOTHOTH_SEAL, SHUB_NIGGURATH_SEAL,
		CTHULHU_SEAL, HASTUR_SEAL, JZAHAR_SEAL};
}