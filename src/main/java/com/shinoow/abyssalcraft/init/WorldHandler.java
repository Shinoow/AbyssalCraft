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
package com.shinoow.abyssalcraft.init;

import static com.shinoow.abyssalcraft.AbyssalCraft.modid;
import static com.shinoow.abyssalcraft.init.InitHandler.*;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.MapGenAbyStronghold;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.StructureAbyStrongholdPieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMinePieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMineStart;
import com.shinoow.abyssalcraft.common.structures.omothol.MapGenOmothol;
import com.shinoow.abyssalcraft.common.structures.omothol.StructureOmotholPieces;
import com.shinoow.abyssalcraft.common.world.*;
import com.shinoow.abyssalcraft.common.world.biome.*;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldHandler implements ILifeCycleHandler {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		ACBiomes.darklands = new BiomeGenDarklands(new BiomeProperties("Darklands").setWaterColor(14745518));
		ACBiomes.abyssal_wastelands = new BiomeGenAbywasteland(new BiomeProperties("Abyssal Wastelands").setWaterColor(0x24FF83).setRainDisabled());
		ACBiomes.dreadlands = new BiomeGenDreadlands(new BiomeProperties("Dreadlands").setRainDisabled());
		ACBiomes.purified_dreadlands = new BiomeGenAbyDreadlands(new BiomeProperties("Purified Dreadlands").setRainDisabled());
		ACBiomes.dreadlands_forest = new BiomeGenForestDreadlands(new BiomeProperties("Dreadlands Forest").setRainDisabled());
		ACBiomes.dreadlands_mountains = new BiomeGenMountainDreadlands(new BiomeProperties("Dreadlands Mountains").setBaseHeight(1.3F).setHeightVariation(0.9F).setRainDisabled());
		ACBiomes.darklands_forest = new BiomeGenDarklandsForest(new BiomeProperties("Darklands Forest").setWaterColor(14745518));
		ACBiomes.darklands_plains = new BiomeGenDarklandsPlains(new BiomeProperties("Darklands Plains").setWaterColor(14745518));
		ACBiomes.darklands_hills = new BiomeGenDarklandsHills(new BiomeProperties("Darklands Highland").setWaterColor(14745518).setBaseHeight(1.1F).setHeightVariation(0.5F).setTemperature(0.2F).setRainfall(0.3F));
		ACBiomes.darklands_mountains = new BiomeGenDarklandsMountains(new BiomeProperties("Darklands Mountains").setWaterColor(14745518).setBaseHeight(1.3F).setHeightVariation(0.9F).setTemperature(0.2F).setRainfall(0.3F));
		ACBiomes.coralium_infested_swamp = new BiomeGenCorSwamp(new BiomeProperties("Coralium Infested Swamp").setWaterColor(0x24FF83).setBaseHeight(-0.2F).setHeightVariation(0.1F));
		ACBiomes.omothol = new BiomeGenOmothol(new BiomeProperties("Omothol").setWaterColor(14745518).setRainDisabled());
		ACBiomes.dark_realm = new BiomeGenDarkRealm(new BiomeProperties("Dark Realm").setWaterColor(14745518).setRainDisabled());
		ACBiomes.purged = new BiomePurged(new BiomeProperties("Purged").setWaterColor(0xD7D8D9).setRainDisabled());

		GameRegistry.register(ACBiomes.darklands.setRegistryName(new ResourceLocation(modid, "darklands")));
		GameRegistry.register(ACBiomes.darklands_forest.setRegistryName(new ResourceLocation(modid, "darklands_forest")));
		GameRegistry.register(ACBiomes.darklands_plains.setRegistryName(new ResourceLocation(modid, "darklands_plains")));
		GameRegistry.register(ACBiomes.darklands_hills.setRegistryName(new ResourceLocation(modid, "darklands_hills")));
		GameRegistry.register(ACBiomes.darklands_mountains.setRegistryName(new ResourceLocation(modid, "darklands_mountains")));
		GameRegistry.register(ACBiomes.coralium_infested_swamp.setRegistryName(new ResourceLocation(modid, "coralium_infested_swamp")));

		if(dark1){
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(ACBiomes.darklands, darkWeight1));
			BiomeManager.addVillageBiome(ACBiomes.darklands, true);
		}
		if(dark2){
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(ACBiomes.darklands_forest, darkWeight2));
			BiomeManager.addVillageBiome(ACBiomes.darklands_forest, true);
		}
		if(dark3){
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(ACBiomes.darklands_plains, darkWeight3));
			BiomeManager.addVillageBiome(ACBiomes.darklands_plains, true);
		}
		if(dark4)
			BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(ACBiomes.darklands_hills, darkWeight4));
		if(dark5){
			BiomeManager.addBiome(BiomeType.COOL, new BiomeEntry(ACBiomes.darklands_mountains, darkWeight5));
			BiomeManager.addStrongholdBiome(ACBiomes.darklands_mountains);
		}
		if(coralium1)
			BiomeManager.addBiome(BiomeType.WARM, new BiomeEntry(ACBiomes.coralium_infested_swamp, coraliumWeight));
		if(darkspawn1)
			BiomeManager.addSpawnBiome(ACBiomes.darklands);
		if(darkspawn2)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_forest);
		if(darkspawn3)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_plains);
		if(darkspawn4)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_hills);
		if(darkspawn5)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_mountains);
		if(coraliumspawn1)
			BiomeManager.addSpawnBiome(ACBiomes.coralium_infested_swamp);

		GameRegistry.register(ACBiomes.abyssal_wastelands.setRegistryName(new ResourceLocation(modid, "abyssal_wastelands")));
		GameRegistry.register(ACBiomes.dreadlands.setRegistryName(new ResourceLocation(modid, "dreadlands")));
		GameRegistry.register(ACBiomes.purified_dreadlands.setRegistryName(new ResourceLocation(modid, "purified_dreadlands")));
		GameRegistry.register(ACBiomes.dreadlands_forest.setRegistryName(new ResourceLocation(modid, "dreadlands_forest")));
		GameRegistry.register(ACBiomes.dreadlands_mountains.setRegistryName(new ResourceLocation(modid, "dreadlands_mountains")));
		GameRegistry.register(ACBiomes.omothol.setRegistryName(new ResourceLocation(modid, "omothol")));
		GameRegistry.register(ACBiomes.dark_realm.setRegistryName(new ResourceLocation(modid, "dark_realm")));
		GameRegistry.register(ACBiomes.purged.setRegistryName(new ResourceLocation(modid, "purged")));

		BiomeDictionary.addTypes(ACBiomes.darklands, Type.WASTELAND, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.darklands_forest, Type.FOREST, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.darklands_plains, Type.PLAINS, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.darklands_hills, Type.HILLS, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.darklands_mountains, Type.MOUNTAIN, Type.SPOOKY);
		BiomeDictionary.addTypes(ACBiomes.coralium_infested_swamp, Type.SWAMP);

		BiomeDictionary.addTypes(ACBiomes.abyssal_wastelands, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dreadlands, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.purified_dreadlands, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dreadlands_mountains, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dreadlands_forest, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.omothol, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dark_realm, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.purged, Type.DEAD);

		ACLib.THE_ABYSSAL_WASTELAND = DimensionType.register("The Abyssal Wasteland", "_aw", ACLib.abyssal_wasteland_id, WorldProviderAbyss.class, ACConfig.keepLoaded1);
		ACLib.THE_DREADLANDS = DimensionType.register("The Dreadlands", "_dl", ACLib.dreadlands_id, WorldProviderDreadlands.class, ACConfig.keepLoaded2);
		ACLib.OMOTHOL = DimensionType.register("Omothol", "_omt", ACLib.omothol_id, WorldProviderOmothol.class, ACConfig.keepLoaded3);
		ACLib.THE_DARK_REALM = DimensionType.register("The Dark Realm", "_dl", ACLib.dark_realm_id, WorldProviderDarkRealm.class, ACConfig.keepLoaded4);

		DimensionManager.registerDimension(ACLib.abyssal_wasteland_id, ACLib.THE_ABYSSAL_WASTELAND);
		DimensionManager.registerDimension(ACLib.dreadlands_id, ACLib.THE_DREADLANDS);
		DimensionManager.registerDimension(ACLib.omothol_id, ACLib.OMOTHOL);
		DimensionManager.registerDimension(ACLib.dark_realm_id, ACLib.THE_DARK_REALM);
	}

	@Override
	public void init(FMLInitializationEvent event) {
		MapGenStructureIO.registerStructure(MapGenAbyStronghold.Start.class, "AbyStronghold");
		StructureAbyStrongholdPieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(StructureDreadlandsMineStart.class, "DreadMine");
		StructureDreadlandsMinePieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(MapGenOmothol.Start.class, "Omothol");
		StructureOmotholPieces.registerOmotholPieces();
		GameRegistry.registerWorldGenerator(new AbyssalCraftWorldGenerator(), 0);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {
		if(ACConfig.purgeMobSpawns){
			((BiomeGenAbywasteland) ACBiomes.abyssal_wastelands).setMobSpawns();
			((BiomeGenDreadlands) ACBiomes.dreadlands).setMobSpawns();
			((BiomeGenAbyDreadlands) ACBiomes.purified_dreadlands).setMobSpawns();
			((BiomeGenForestDreadlands) ACBiomes.dreadlands_forest).setMobSpawns();
			((BiomeGenMountainDreadlands) ACBiomes.dreadlands_mountains).setMobSpawns();
			((BiomeGenOmothol) ACBiomes.omothol).setMobSpawns();
			((BiomeGenDarkRealm) ACBiomes.dark_realm).setMobSpawns();
		}
	}
}
