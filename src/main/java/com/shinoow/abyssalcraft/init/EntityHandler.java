/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.init;

import static com.shinoow.abyssalcraft.AbyssalCraft.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.common.entity.anti.*;
import com.shinoow.abyssalcraft.common.entity.demon.*;

public class EntityHandler implements ILifeCycleHandler {

	static int startEntityId = 300;

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		registerEntityWithEgg(EntityDepthsGhoul.class, "depthsghoul", 25, 80, 3, true, 0x36A880, 0x012626);
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityDepthsGhoul.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SWAMP));
		EntityRegistry.removeSpawn(EntityDepthsGhoul.class, EnumCreatureType.MONSTER, BiomeGenBase.mushroomIslandShore);

		registerEntityWithEgg(EntityEvilpig.class, "evilpig", 26, 80, 3, true, 15771042, 14377823);
		if(evilAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityEvilpig.class, evilAnimalSpawnWeight, 1, 3, evilAnimalCreatureType ? EnumCreatureType.MONSTER : EnumCreatureType.CREATURE,
					BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna,
					BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau,
					BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest,
					BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest);

		registerEntityWithEgg(EntityAbyssalZombie.class , "abyssalzombie", 27, 80, 3, true, 0x36A880, 0x052824);
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityAbyssalZombie.class, 10, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SWAMP));
		if(endAbyssalZombieSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityAbyssalZombie.class, endAbyssalZombieSpawnWeight, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.END));
		EntityRegistry.removeSpawn(EntityAbyssalZombie.class, EnumCreatureType.MONSTER, BiomeGenBase.mushroomIslandShore);

		EntityRegistry.registerModEntity(EntityODBPrimed.class, "Primed ODB", 28, instance, 80, 3, true);

		registerEntityWithEgg(EntityJzahar.class, "Jzahar", 29, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityAbygolem.class, "abygolem", 30, 80, 3, true, 0x8A00E6, 0x6100A1);

		registerEntityWithEgg(EntityDreadgolem.class, "dreadgolem", 31, 80, 3, true, 0x1E60000, 0xCC0000);

		registerEntityWithEgg(EntityDreadguard.class, "dreadguard", 32, 80, 3, true, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityPSDLTracker.class, "PowerstoneTracker", 33, instance, 64, 10, true);

		registerEntityWithEgg(EntityDragonMinion.class, "dragonminion", 34, 80, 3, true, 0x433434, 0x344344);

		registerEntityWithEgg(EntityDragonBoss.class, "dragonboss", 35, 80, 3, true, 0x476767, 0x768833);

		EntityRegistry.registerModEntity(EntityODBcPrimed.class, "Primed ODB Core", 36, instance, 80, 3, true);

		registerEntityWithEgg(EntityShadowCreature.class, "shadowcreature", 37, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityShadowMonster.class, "shadowmonster", 38, 80, 3, true, 0, 0xFFFFFF);

		registerEntityWithEgg(EntityDreadling.class, "dreadling", 39, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityDreadSpawn.class, "dreadspawn", 40, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityDemonPig.class, "demonpig", 41, 80, 3, true, 15771042, 14377823);
		if(demonAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityDemonPig.class, demonAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.NETHER));

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

		EntityRegistry.registerModEntity(EntityCoraliumArrow.class, "CoraliumArrow", 61, instance, 64, 10, true);

		registerEntityWithEgg(EntityGatekeeperMinion.class, "jzaharminion", 62, 80, 3, true, 0x133133, 0x342122);

		registerEntityWithEgg(EntityGreaterDreadSpawn.class, "greaterdreadspawn", 63, 80, 3, true, 0xE60000, 0xCC0000);

		registerEntityWithEgg(EntityLesserDreadbeast.class, "lesserdreadbeast", 64, 80, 3, true, 0xE60000, 0xCC0000);

		EntityRegistry.registerModEntity(EntityDreadSlug.class, "DreadSlug", 65, instance, 64, 10, true);

		registerEntityWithEgg(EntityLesserShoggoth.class, "lessershoggoth", 66, 80, 3, true, 0x133133, 0x342122);
		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.WATER));
		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.BEACH));
		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.SWAMP));
		EntityRegistry.addSpawn(EntityLesserShoggoth.class, 3, 1, 1, EnumCreatureType.MONSTER, ACBiomes.abyssal_wastelands, ACBiomes.dreadlands,
				ACBiomes.purified_dreadlands, ACBiomes.dreadlands_mountains, ACBiomes.dreadlands_forest, ACBiomes.omothol, ACBiomes.dark_realm);
		EntityRegistry.removeSpawn(EntityLesserShoggoth.class, EnumCreatureType.MONSTER, BiomeGenBase.mushroomIslandShore);

		registerEntityWithEgg(EntityEvilCow.class, "evilcow", 67, 80, 3, true, 4470310, 10592673);
		if(evilAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityEvilCow.class, evilAnimalSpawnWeight, 1, 3, evilAnimalCreatureType ? EnumCreatureType.MONSTER : EnumCreatureType.CREATURE,
					BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna,
					BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau,
					BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest,
					BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest);

		registerEntityWithEgg(EntityEvilChicken.class, "evilchicken", 68, 80, 3, true, 10592673, 16711680);
		if(evilAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityEvilChicken.class, evilAnimalSpawnWeight, 1, 3, evilAnimalCreatureType ? EnumCreatureType.MONSTER : EnumCreatureType.CREATURE,
					BiomeGenBase.taiga, BiomeGenBase.plains, BiomeGenBase.forest, BiomeGenBase.savanna,
					BiomeGenBase.beach, BiomeGenBase.extremeHills, BiomeGenBase.jungle, BiomeGenBase.savannaPlateau,
					BiomeGenBase.swampland, BiomeGenBase.icePlains, BiomeGenBase.birchForest,
					BiomeGenBase.birchForestHills, BiomeGenBase.roofedForest);

		registerEntityWithEgg(EntityDemonCow.class, "demoncow", 69, 80, 3, true, 4470310, 10592673);
		if(demonAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityDemonCow.class, demonAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.NETHER));

		registerEntityWithEgg(EntityDemonChicken.class, "demonchicken", 70, 80, 3, true, 10592673, 16711680);
		if(demonAnimalSpawnWeight > 0)
			EntityRegistry.addSpawn(EntityDemonChicken.class, demonAnimalSpawnWeight, 1, 3, EnumCreatureType.MONSTER, BiomeDictionary.getBiomesForType(Type.NETHER));

		EntityRegistry.registerModEntity(EntityGatekeeperEssence.class, "GatekeeperEssence", 71, instance, 64, 10, true);

		//		registerEntityWithEgg(EntityShadowTitan.class, "shadowtitan", 71, 80, 3, true, 0, 0xFFFFFF);
		//
		//		registerEntityWithEgg(EntityOmotholWarden.class, "omotholwarden", 72, 80, 3, true, 0x133133, 0x342122);

		EntityUtil.addShoggothFood(EntityAnimal.class);
		EntityUtil.addShoggothFood(EntityAmbientCreature.class);
		EntityUtil.addShoggothFood(EntityWaterMob.class);
		EntityUtil.addShoggothFood(EntityEvilpig.class);
		EntityUtil.addShoggothFood(EntityEvilCow.class);
		EntityUtil.addShoggothFood(EntityEvilChicken.class);
		EntityUtil.addShoggothFood(EntityDemonAnimal.class);
	}

	@Override
	public void init(FMLInitializationEvent event) {}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	private static int getUniqueEntityId() {
		do
			startEntityId++;
		while (EntityList.getStringFromID(startEntityId) != null);

		return startEntityId;
	}

	@SuppressWarnings("unchecked")
	private static void registerEntityWithEgg(Class<? extends Entity> entity, String name, int modid, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates, int primaryColor, int secondaryColor) {
		int id = getUniqueEntityId();
		stringtoIDMapping.put(name, id);
		EntityRegistry.registerModEntity(entity, name, modid, instance, trackingRange, updateFrequency, sendsVelocityUpdates);
		EntityList.idToClassMapping.put(id, entity);
		EntityList.entityEggs.put(id, new EntityEggInfo(id, primaryColor, secondaryColor));
	}
}
