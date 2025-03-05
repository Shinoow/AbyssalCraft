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
package com.shinoow.abyssalcraft.common;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.biome.IAbyssalWastelandBiome;
import com.shinoow.abyssalcraft.api.knowledge.ResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.ResearchItems;
import com.shinoow.abyssalcraft.api.knowledge.condition.*;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.common.entity.EntityShoggothBase;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonAnimal;
import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilAnimal;
import com.shinoow.abyssalcraft.common.handlers.InternalNecroDataHandler;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {

	public void preInit() {
		initUnlockConditions();
		initResearchItems();
		AbyssalCraftAPI.setInternalNDHandler(new InternalNecroDataHandler());
	}

	public void init() {
		RitualRegistry.instance().addDimensionToBookTypeAndName(0, 0, NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.abyssal_wasteland_id, 1, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.dreadlands_id, 2, NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.omothol_id, 3, NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE);
		RitualRegistry.instance().addDimensionToBookTypeAndName(ACLib.dark_realm_id, 0, NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE);
	}

	public void postInit() {}

	protected void initUnlockConditions() {
		UnlockConditions.CORALIUM_INFESTED_SWAMP = new BiomeCondition(ACBiomes.coralium_infested_swamp);
		UnlockConditions.CORALIUM_BIOMES = new BiomePredicateCondition(b -> b instanceof IAbyssalWastelandBiome || b == ACBiomes.coralium_infested_swamp);

		UnlockConditions.ABYSSAL_WASTELAND = new DimensionCondition(ACLib.abyssal_wasteland_id);
		UnlockConditions.DREADLANDS = new DimensionCondition(ACLib.dreadlands_id);
		UnlockConditions.OMOTHOL = new DimensionCondition(ACLib.omothol_id);
		UnlockConditions.DARK_REALM = new DimensionCondition(ACLib.dark_realm_id);

		UnlockConditions.EVIL_ANIMAL = new EntityPredicateCondition(e -> EntityEvilAnimal.class.isAssignableFrom(e));
		UnlockConditions.SHOGGOTH = new EntityPredicateCondition(e -> EntityShoggothBase.class.isAssignableFrom(e));
		UnlockConditions.DEMON_ANIMAL = new EntityPredicateCondition(e -> EntityDemonAnimal.class.isAssignableFrom(e));
	}

	protected void initResearchItems() {
		ResearchItems.DARKLANDS_BIOME = new ResearchItem("darklands", 0, rl("darklands")).setUnlockConditions(UnlockConditions.DARKLANDS_BIOME);
		ResearchItems.CORALIUM_INFESTED_SWAMP = new ResearchItem("coralium_infused_swamp", 0, rl("coralium_infused_swamp")).setUnlockConditions(UnlockConditions.CORALIUM_INFESTED_SWAMP);
		ResearchItems.CORALIUM_BIOMES = new ResearchItem("coralium_biomes", 0, rl("coralium_biomes")).setUnlockConditions(UnlockConditions.CORALIUM_BIOMES);
		ResearchItems.ABYSSAL_WASTELAND = new ResearchItem("abyssal_wasteland", 0, rl("abyssal_wasteland")).setUnlockConditions(UnlockConditions.ABYSSAL_WASTELAND);
		ResearchItems.DREADLANDS = new ResearchItem("dreadlands", 0, rl("dreadlands")).setUnlockConditions(UnlockConditions.DREADLANDS);
		ResearchItems.OMOTHOL = new ResearchItem("omothol", 0, rl("omothol")).setUnlockConditions(UnlockConditions.OMOTHOL);
		ResearchItems.DARK_REALM = new ResearchItem("dark_realm", 0, rl("dark_realm")).setUnlockConditions(UnlockConditions.DARK_REALM);
		ResearchItems.NETHER = new ResearchItem("nether", 0, rl("nether")).setUnlockConditions(UnlockConditions.NETHER);
		ResearchItems.ABYSSAL_ZOMBIE = new ResearchItem("abyssal_zombie", 0, rl("abyssal_zombie")).setUnlockConditions(UnlockConditions.ABYSSAL_ZOMBIE);
		ResearchItems.DEPTHS_GHOUL = new ResearchItem("depths_ghoul", 0, rl("depths_ghoul")).setUnlockConditions(UnlockConditions.DEPTHS_GHOUL);
		ResearchItems.SACTHOTH = new ResearchItem("sacthoth", 0, rl("sacthoth")).setUnlockConditions(UnlockConditions.SACTHOTH);
		ResearchItems.SHADOW_MOBS = new ResearchItem("shadow_mobs", 0, rl("shadow_mobs")).setUnlockConditions(UnlockConditions.SHADOW_MOBS);
		ResearchItems.SHADOW_CREATURE = new ResearchItem("shadow_creature", 0, rl("shadow_creature")).setUnlockConditions(UnlockConditions.SHADOW_CREATURE);
		ResearchItems.SHADOW_MONSTER = new ResearchItem("shadow_monster", 0, rl("shadow_monster")).setUnlockConditions(UnlockConditions.SHADOW_MONSTER);
		ResearchItems.SHADOW_BEAST = new ResearchItem("shadow_beast", 0, rl("shadow_beast")).setUnlockConditions(UnlockConditions.SHADOW_BEAST);
		ResearchItems.SKELETON_GOLIATH = new ResearchItem("skeleton_goliath", 0, rl("skeleton_goliath")).setUnlockConditions(UnlockConditions.SKELETON_GOLIATH);
		ResearchItems.SPECTRAL_DRAGON = new ResearchItem("spectral_dragon", 0, rl("spectral_dragon")).setUnlockConditions(UnlockConditions.SPECTRAL_DRAGON);
		ResearchItems.OMOTHOL_GHOUL = new ResearchItem("omothol_ghoul", 0, rl("omothol_ghoul")).setUnlockConditions(UnlockConditions.OMOTHOL_GHOUL);
		ResearchItems.DREADED_ABYSSALNITE_GOLEM = new ResearchItem("dreaded_abyssalnite_golem", 0, rl("dreaded_abyssalnite_golem")).setUnlockConditions(UnlockConditions.DREADED_ABYSSALNITE_GOLEM);
		ResearchItems.ABYSSALNITE_GOLEM = new ResearchItem("abyssalnite_golem", 0, rl("abyssalnite_golem")).setUnlockConditions(UnlockConditions.ABYSSALNITE_GOLEM);
		ResearchItems.ELITE_DREAD_MOB = new ResearchItem("elite_dread_mob", 0, rl("elite_dread_mob")).setUnlockConditions(UnlockConditions.ELITE_DREAD_MOB);
		ResearchItems.DREAD_MOB = new ResearchItem("dread_mob", 0, rl("dread_mob")).setUnlockConditions(UnlockConditions.DREAD_MOB);
		ResearchItems.KILLED_ALL_BOSSES = new ResearchItem("kill_all_bosses", 0, rl("kill_all_bosses")).setUnlockConditions(UnlockConditions.KILLED_ALL_BOSSES);
		ResearchItems.ANTI_MOB = new ResearchItem("anti_mob", 0, rl("anti_mob")).setUnlockConditions(UnlockConditions.ANTI_MOB);
		ResearchItems.EVIL_ANIMAL = new ResearchItem("evil_animal", 0, rl("evil_animal")).setUnlockConditions(UnlockConditions.EVIL_ANIMAL);
		ResearchItems.SHOGGOTH = new ResearchItem("shoggoth", 0, rl("shoggoth")).setUnlockConditions(UnlockConditions.SHOGGOTH);
		ResearchItems.SHUB_OFFSPRING = new ResearchItem("shub_offspring", 0, rl("shub_offspring")).setUnlockConditions(UnlockConditions.SHUB_OFFSPRING);
		ResearchItems.CORALIUM_INFESTED_SQUID = new ResearchItem("coralium_infested_squid", 0, rl("coralium_infested_squid")).setUnlockConditions(UnlockConditions.CORALIUM_INFESTED_SQUID);
		ResearchItems.DREAD_SPAWN = new ResearchItem("dread_spawn", 0, rl("dread_spawn")).setUnlockConditions(UnlockConditions.DREAD_SPAWN);
		ResearchItems.DREADLING = new ResearchItem("dreadling", 0, rl("dreadling")).setUnlockConditions(UnlockConditions.DREADLING);
		ResearchItems.DEMON_ANIMAL = new ResearchItem("demon_animal", 0, rl("demon_animal")).setUnlockConditions(UnlockConditions.DEMON_ANIMAL);
		ResearchItems.SPAWN_OF_CHAGAROTH = new ResearchItem("spawn_of_chagaroth", 0, rl("spawn_of_chagaroth")).setUnlockConditions(UnlockConditions.SPAWN_OF_CHAGAROTH);
		ResearchItems.FIST_OF_CHAGAROTH = new ResearchItem("fist_of_chagaroth", 0, rl("fist_of_chagaroth")).setUnlockConditions(UnlockConditions.FIST_OF_CHAGAROTH);
		ResearchItems.DREADGUARD = new ResearchItem("dreadguard", 0, rl("dreadguard")).setUnlockConditions(UnlockConditions.DREADGUARD);
		ResearchItems.MINION_OF_THE_GATEKEEPER = new ResearchItem("minion_of_the_gatekeeper", 0, rl("minion_of_the_gatekeeper")).setUnlockConditions(UnlockConditions.MINION_OF_THE_GATEKEEPER);
		ResearchItems.CORALIUM_PLAGUE = new ResearchItem("coralium_plague", 0, rl("coralium_plague")).setUnlockConditions(UnlockConditions.CORALIUM_PLAGUE);
		ResearchItems.DREAD_PLAGUE = new ResearchItem("dread_plague", 0, rl("dread_plague")).setUnlockConditions(UnlockConditions.DREAD_PLAGUE);
		ResearchItems.ABYSSAL_WASTELAND_NECRO = new ResearchItem("abyssal_wasteland_necro", 0, rl("abyssal_wasteland_necro")).setUnlockConditions(UnlockConditions.ABYSSAL_WASTELAND_NECRO);
		ResearchItems.DREADLANDS_NECRO = new ResearchItem("dreadlands_necro", 0, rl("dreadlands_necro")).setUnlockConditions(UnlockConditions.DREADLANDS_NECRO);
		ResearchItems.OMOTHOL_NECRO = new ResearchItem("omothol_necro", 0, rl("omothol_necro")).setUnlockConditions(UnlockConditions.OMOTHOL_NECRO);
		ResearchItems.ABYSSALNOMICON = new ResearchItem("abyssalnomicon", 0, rl("abyssalnomicon")).setUnlockConditions(UnlockConditions.ABYSSALNOMICON);
		ResearchItems.GHOUL = new ResearchItem("ghoul", 0, rl("ghoul")).setUnlockConditions(UnlockConditions.GHOUL);
		ResearchItems.DREADED_GHOUL = new ResearchItem("dreaded_ghoul", 0, rl("dreaded_ghoul")).setUnlockConditions(UnlockConditions.DREADED_GHOUL);
		ResearchItems.SHADOW_GHOUL = new ResearchItem("shadow_ghoul", 0, rl("shadow_ghoul")).setUnlockConditions(UnlockConditions.SHADOW_GHOUL);
	}

	private ResourceLocation rl(String str) {
		return new ResourceLocation("abyssalcraft", str);
	}

	public ModelBiped getArmorModel(int id){
		return null;
	}

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}

	/**
	 * Returns the current thread based on side during message handling,
	 * used for ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().player.getServer();
	}

	public void spawnParticle(String particleName, double posX, double posY, double posZ, double velX, double velY, double velZ) {}

	public void spawnItemParticle(double posX, double posY, double posZ, double velX, double velY, double velZ, int[] data) {}

	public int getParticleCount() {
		return 0;
	}

	public void incrementParticleCount() {}

	public void decrementParticleCount() {}

	public void resetParticleCount() {}

	public RayTraceResult rayTraceEntity(float dist) {
		return null;
	}
}
