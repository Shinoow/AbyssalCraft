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
package com.shinoow.abyssalcraft.init;

import static com.shinoow.abyssalcraft.AbyssalCraft.instance;
import static com.shinoow.abyssalcraft.lib.ACConfig.*;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class EntityHandler implements ILifeCycleHandler {

	static int startEntityId = 300;

	@Override
	public void preInit(FMLPreInitializationEvent event) {

		if(SharedMonsterAttributes.MAX_HEALTH.clampValue(Integer.MAX_VALUE) <= 2000)
			try{
				Field f = ReflectionHelper.findField(RangedAttribute.class, "maximumValue", "field_111118_b");
				Field modifiersField = Field.class.getDeclaredField("modifiers");
				modifiersField.setAccessible(true);
				modifiersField.setInt(f, f.getModifiers() & ~Modifier.FINAL);
				f.set(SharedMonsterAttributes.MAX_HEALTH, Integer.MAX_VALUE);
			} catch(Exception e){}

		registerEntityWithEgg(EntityDepthsGhoul.class, "depthsghoul", 25, 80, 3, true, 0x36A880, 0x012626);

		registerEntityWithEgg(EntityEvilpig.class, "evilpig", 26, 80, 3, true, 15771042, 14377823);

		registerEntityWithEgg(EntityAbyssalZombie.class , "abyssalzombie", 27, 80, 3, true, 0x36A880, 0x052824);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "primedodb"), EntityODBPrimed.class, "Primed ODB", 28, instance, 80, 3, true);

		registerEntityWithEgg(EntityJzahar.class, "Jzahar", 29, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityAbygolem.class, "abygolem", 30, 80, 3, true, 0x8A00E6, 0x6100A1);

		registerEntityWithEgg(EntityDreadgolem.class, "dreadgolem", 31, 80, 3, true, 0x1E60000, 0xCC0000);

		registerEntityWithEgg(EntityDreadguard.class, "dreadguard", 32, 80, 3, true, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "powerstonetracker"), EntityPSDLTracker.class, "PowerstoneTracker", 33, instance, 64, 10, true);

		registerEntityWithEgg(EntityDragonMinion.class, "dragonminion", 34, 80, 3, true, 0x433434, 0x344344);

		registerEntityWithEgg(EntityDragonBoss.class, "dragonboss", 35, 80, 3, true, 0x476767, 0x768833);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "primedodbcore"), EntityODBcPrimed.class, "Primed ODB Core", 36, instance, 80, 3, true);

		registerEntityWithEgg(EntityShadowCreature.class, "shadowcreature", 37, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityShadowMonster.class, "shadowmonster", 38, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityDreadling.class, "dreadling", 39, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityDreadSpawn.class, "dreadspawn", 40, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityDemonPig.class, "demonpig", 41, 80, 3, true, 15771042, 14377823);

		registerEntityWithEgg(EntitySkeletonGoliath.class, "gskeleton", 42, 80, 3, true, 0xD6D6C9, 0xC6C7AD);

		registerEntityWithEgg(EntityChagarothSpawn.class, "chagarothspawn", 43, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityChagarothFist.class, "chagarothfist", 44, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityChagaroth.class, "chagaroth", 45, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityShadowBeast.class, "shadowbeast", 46, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntitySacthoth.class, "shadowboss", 47, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiAbyssalZombie.class, "antiabyssalzombie", 48, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiBat.class, "antibat", 49, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiChicken.class, "antichicken", 50, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiCow.class, "anticow", 51, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiCreeper.class, "anticreeper", 52, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiGhoul.class, "antighoul", 53, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiPig.class, "antipig", 54, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiPlayer.class, "antiplayer", 55, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiSkeleton.class, "antiskeleton", 56, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiSpider.class, "antispider", 57, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityAntiZombie.class, "antizombie", 58, 80, 3, true, 0xFFFFFF, 0xFFFFFF);

		registerEntityWithEgg(EntityRemnant.class, "remnant", 59, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityOmotholGhoul.class, "omotholghoul", 60, 80, 3, true, 0x133133, 0x342122);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "coraliumarrow"), EntityCoraliumArrow.class, "CoraliumArrow", 61, instance, 64, 10, true);

		registerEntityWithEgg(EntityGatekeeperMinion.class, "jzaharminion", 62, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityGreaterDreadSpawn.class, "greaterdreadspawn", 63, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityLesserDreadbeast.class, "lesserdreadbeast", 64, 80, 3, true, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "dreadslug"), EntityDreadSlug.class, "DreadSlug", 65, instance, 64, 10, true);

		registerEntityWithEgg(EntityLesserShoggoth.class, "lessershoggoth", 66, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityEvilCow.class, "evilcow", 67, 80, 3, true, 4470310, 10592673);

		registerEntityWithEgg(EntityEvilChicken.class, "evilchicken", 68, 80, 3, true, 10592673, 16711680);

		registerEntityWithEgg(EntityDemonCow.class, "demoncow", 69, 80, 3, true, 4470310, 10592673);

		registerEntityWithEgg(EntityDemonChicken.class, "demonchicken", 70, 80, 3, true, 10592673, 16711680);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "gatekeeperessence"), EntityGatekeeperEssence.class, "GatekeeperEssence", 71, instance, 64, 10, true);

		registerEntityWithEgg(EntityEvilSheep.class, "evilsheep", 72, 80, 3, true, 15198183, 16758197);

		registerEntityWithEgg(EntityDemonSheep.class, "demonsheep", 73, 80, 3, true, 15198183, 16758197);

		registerEntityWithEgg(EntityCoraliumSquid.class, "coraliumsquid", 74, 63, 3, true, 0x014e43, 0x148f7e);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "inkprojectile"), EntityInkProjectile.class, "inkprojectile", 75, instance, 64, 10, true);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "dreadedcharge"), EntityDreadedCharge.class, "dreadedcharge", 76, instance, 64, 10, true);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "acidprojectile"), EntityAcidProjectile.class, "acidprojectile", 77, instance, 64, 10, true);

		//		registerEntityWithEgg(EntityShadowTitan.class, "shadowtitan", 74, 80, 3, true, 0, 0xFFFFFF);
		//
		//		registerEntityWithEgg(EntityOmotholWarden.class, "omotholwarden", 75, 80, 3, true, 0x133133, 0x342122);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "blackhole"), EntityBlackHole.class, "blackhole", 78, instance, 64, 10, true);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "implosion"), EntityImplosion.class, "implosion", 79, instance, 64, 10, true);

		registerEntityWithEgg(EntityShubOffspring.class, "shuboffspring", 80, 80, 3, true, 0x2b2929, 0x211f1d);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "spirititem"), EntitySpiritItem.class, "spirititem", 81, instance, 64, 10, true);

		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", "portal"), EntityPortal.class, "portal", 82, instance, 64, 10, true);

		registerEntityWithEgg(EntityShoggoth.class, "shoggoth", 83, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityGreaterShoggoth.class, "greatershoggoth", 84, 80, 3, true, 0x133133, 0x342122);

		EntityUtil.addShoggothFood(EntityAnimal.class);
		EntityUtil.addShoggothFood(EntityAmbientCreature.class);
		EntityUtil.addShoggothFood(EntityWaterMob.class);
		EntityUtil.addShoggothFood(EntityEvilAnimal.class);
		EntityUtil.addShoggothFood(EntityDemonAnimal.class);
		EntityUtil.addShoggothFood(EntitySpider.class);
		EntityUtil.addShoggothFood(EntityCaveSpider.class);

		EntitySpawnPlacementRegistry.setPlacementType(EntityCoraliumSquid.class, EntityLiving.SpawnPlacementType.IN_WATER);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		if(depthsGhoulBiomeDictSpawn) {
			EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.WATER).toArray(new Biome[0]));
			EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.BEACH).toArray(new Biome[0]));
			EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.SWAMP).toArray(new Biome[0]));
			EntityRegistry.removeSpawn(EntityDepthsGhoul.class, EnumCreatureType.MONSTER, Biomes.MUSHROOM_ISLAND_SHORE );
		} else
			EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, Biomes.OCEAN, Biomes.DEEP_OCEAN,
					Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER, Biomes.BEACH, Biomes.COLD_BEACH, Biomes.STONE_BEACH,
					Biomes.SWAMPLAND, Biomes.MUTATED_SWAMPLAND);

		if(evilAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityEvilpig.class, evilAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER,
					Biomes.TAIGA, Biomes.PLAINS, Biomes.FOREST, Biomes.SAVANNA, Biomes.BEACH,
					Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.SAVANNA_PLATEAU, Biomes.SWAMPLAND,
					Biomes.ICE_PLAINS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS,
					Biomes.ROOFED_FOREST);

		if(abyssalZombieBiomeDictSpawn) {
			EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.WATER).toArray(new Biome[0]));
			EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.BEACH).toArray(new Biome[0]));
			EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.SWAMP).toArray(new Biome[0]));
			EntityRegistry.removeSpawn(EntityAbyssalZombie.class, EnumCreatureType.MONSTER, Biomes.MUSHROOM_ISLAND_SHORE);
		} else
			EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, Biomes.OCEAN, Biomes.DEEP_OCEAN,
					Biomes.FROZEN_OCEAN, Biomes.RIVER, Biomes.FROZEN_RIVER, Biomes.BEACH, Biomes.COLD_BEACH, Biomes.STONE_BEACH,
					Biomes.SWAMPLAND, Biomes.MUTATED_SWAMPLAND);

		if(demonAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityDemonPig.class, demonAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.NETHER).toArray(new Biome[0]));

		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, ACBiomes.abyssal_wastelands, ACBiomes.dreadlands,
				ACBiomes.purified_dreadlands, ACBiomes.dreadlands_mountains, ACBiomes.dreadlands_forest, ACBiomes.omothol, ACBiomes.dark_realm);

		EntityRegistry.addSpawn(EntityShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, ACBiomes.abyssal_wastelands, ACBiomes.dreadlands,
				ACBiomes.purified_dreadlands, ACBiomes.dreadlands_mountains, ACBiomes.dreadlands_forest, ACBiomes.omothol, ACBiomes.dark_realm);

		EntityRegistry.addSpawn(EntityGreaterShoggoth.class, 1, 1, 1, EnumCreatureType.MONSTER, ACBiomes.abyssal_wastelands, ACBiomes.dreadlands,
				ACBiomes.purified_dreadlands, ACBiomes.dreadlands_mountains, ACBiomes.dreadlands_forest, ACBiomes.omothol, ACBiomes.dark_realm);

		if(evilAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityEvilCow.class, evilAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER,
					Biomes.TAIGA, Biomes.PLAINS, Biomes.FOREST, Biomes.SAVANNA, Biomes.BEACH,
					Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.SAVANNA_PLATEAU, Biomes.SWAMPLAND,
					Biomes.ICE_PLAINS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS,
					Biomes.ROOFED_FOREST);

		if(evilAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityEvilChicken.class, evilAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER,
					Biomes.TAIGA, Biomes.PLAINS, Biomes.FOREST, Biomes.SAVANNA, Biomes.BEACH,
					Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.SAVANNA_PLATEAU, Biomes.SWAMPLAND,
					Biomes.ICE_PLAINS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS,
					Biomes.ROOFED_FOREST);

		if(demonAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityDemonCow.class, demonAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.NETHER).toArray(new Biome[0]));

		if(demonAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityDemonChicken.class, demonAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.NETHER).toArray(new Biome[0]));

		if(evilAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityEvilSheep.class, evilAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER,
					Biomes.TAIGA, Biomes.PLAINS, Biomes.FOREST, Biomes.SAVANNA, Biomes.BEACH,
					Biomes.EXTREME_HILLS, Biomes.JUNGLE, Biomes.SAVANNA_PLATEAU, Biomes.SWAMPLAND,
					Biomes.ICE_PLAINS, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS,
					Biomes.ROOFED_FOREST);

		if(demonAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityDemonSheep.class, demonAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomes(Type.NETHER).toArray(new Biome[0]));

		if(darkOffspringSpawnWeight > 0) {
			EntityRegistry.addSpawn(EntityShubOffspring.class, darkOffspringSpawnWeight, 1, 3, EnumCreatureType.MONSTER,
					Biomes.FOREST, Biomes.REDWOOD_TAIGA, Biomes.REDWOOD_TAIGA_HILLS, Biomes.FOREST_HILLS,
					Biomes.MUTATED_REDWOOD_TAIGA, Biomes.MUTATED_REDWOOD_TAIGA_HILLS, Biomes.MUTATED_FOREST,
					Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.MUTATED_BIRCH_FOREST,
					Biomes.MUTATED_BIRCH_FOREST_HILLS);
			EntityRegistry.addSpawn(EntityShubOffspring.class, darkOffspringSpawnWeight * 2, 1, 3, EnumCreatureType.MONSTER,
					ACBiomes.darklands_forest, Biomes.ROOFED_FOREST, Biomes.MUTATED_ROOFED_FOREST);
		}
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {
		if(purgeMobSpawns) {
			EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, ACBiomes.abyssal_wastelands, ACBiomes.dreadlands,
					ACBiomes.purified_dreadlands, ACBiomes.dreadlands_mountains, ACBiomes.dreadlands_forest, ACBiomes.omothol, ACBiomes.dark_realm);

			EntityRegistry.addSpawn(EntityShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, ACBiomes.abyssal_wastelands, ACBiomes.dreadlands,
					ACBiomes.purified_dreadlands, ACBiomes.dreadlands_mountains, ACBiomes.dreadlands_forest, ACBiomes.omothol, ACBiomes.dark_realm);

			EntityRegistry.addSpawn(EntityGreaterShoggoth.class, 1, 1, 1, EnumCreatureType.MONSTER, ACBiomes.abyssal_wastelands, ACBiomes.dreadlands,
					ACBiomes.purified_dreadlands, ACBiomes.dreadlands_mountains, ACBiomes.dreadlands_forest, ACBiomes.omothol, ACBiomes.dark_realm);
		}
	}

	private static void registerEntityWithEgg(Class<? extends Entity> entity, String name, int modid, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int primaryColor, int secondaryColor) {
		EntityRegistry.registerModEntity(new ResourceLocation("abyssalcraft", name.toLowerCase(Locale.ENGLISH)), entity,"abyssalcraft."+ name, modid, instance, trackingRange, updateFrequency, sendsVelocityUpdates, primaryColor, secondaryColor);
	}
}
