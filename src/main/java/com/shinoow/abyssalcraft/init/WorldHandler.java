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
import static com.shinoow.abyssalcraft.init.InitHandler.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
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
		ACBiomes.darklands_hills = new BiomeGenDarklandsHills(new BiomeProperties("Darklands Highland").setWaterColor(14745518).setBaseHeight(1.1F).setHeightVariation(0.5F));
		ACBiomes.darklands_mountains = new BiomeGenDarklandsMountains(new BiomeProperties("Darklands Mountains").setWaterColor(14745518).setBaseHeight(1.3F).setHeightVariation(0.9F));
		ACBiomes.coralium_infested_swamp = new BiomeGenCorSwamp(new BiomeProperties("Coralium Infested Swamp").setWaterColor(0x24FF83).setBaseHeight(-0.2F).setHeightVariation(0.1F));
		ACBiomes.omothol = new BiomeGenOmothol(new BiomeProperties("Omothol").setWaterColor(14745518).setRainDisabled());
		ACBiomes.dark_realm = new BiomeGenDarkRealm(new BiomeProperties("Dark Realm").setWaterColor(14745518).setRainDisabled());

		if(dark1 == true){
			registerBiomeWithTypes(ACBiomes.darklands, "darklands", darkWeight1, BiomeType.WARM, Type.WASTELAND, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands, true);
		}
		if(dark2 == true){
			registerBiomeWithTypes(ACBiomes.darklands_forest, "darklands_forest", darkWeight2, BiomeType.WARM, Type.FOREST, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_forest, true);
		}
		if(dark3 == true){
			registerBiomeWithTypes(ACBiomes.darklands_plains, "darklands_plains", darkWeight3, BiomeType.WARM, Type.PLAINS, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_plains, true);
		}
		if(dark4 == true)
			registerBiomeWithTypes(ACBiomes.darklands_hills, "darklands_hills", darkWeight4, BiomeType.WARM, Type.HILLS, Type.SPOOKY);
		if(dark5 == true){
			registerBiomeWithTypes(ACBiomes.darklands_mountains, "darklands_mountains", darkWeight5, BiomeType.WARM, Type.MOUNTAIN, Type.SPOOKY);
			BiomeManager.addStrongholdBiome(ACBiomes.darklands_mountains);
		}
		if(coralium1 == true)
			registerBiomeWithTypes(ACBiomes.coralium_infested_swamp, "coralium_infested_swamp", coraliumWeight, BiomeType.WARM, Type.SWAMP);
		if(darkspawn1 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands);
		if(darkspawn2 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_forest);
		if(darkspawn3 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_plains);
		if(darkspawn4 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_hills);
		if(darkspawn5 == true)
			BiomeManager.addSpawnBiome(ACBiomes.darklands_mountains);
		if(coraliumspawn1 == true)
			BiomeManager.addSpawnBiome(ACBiomes.coralium_infested_swamp);

		GameRegistry.register(ACBiomes.abyssal_wastelands.setRegistryName(new ResourceLocation(modid, "abyssal_wastelands")));
		GameRegistry.register(ACBiomes.dreadlands.setRegistryName(new ResourceLocation(modid, "dreadlands")));
		GameRegistry.register(ACBiomes.purified_dreadlands.setRegistryName(new ResourceLocation(modid, "purified_dreadlands")));
		GameRegistry.register(ACBiomes.dreadlands_forest.setRegistryName(new ResourceLocation(modid, "dreadlands_forest")));
		GameRegistry.register(ACBiomes.dreadlands_mountains.setRegistryName(new ResourceLocation(modid, "dreadlands_mountains")));
		GameRegistry.register(ACBiomes.omothol.setRegistryName(new ResourceLocation(modid, "omothol")));
		GameRegistry.register(ACBiomes.dark_realm.setRegistryName(new ResourceLocation(modid, "dark_realm")));

		BiomeDictionary.addTypes(ACBiomes.abyssal_wastelands, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dreadlands, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.purified_dreadlands, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dreadlands_mountains, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dreadlands_forest, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.omothol, Type.DEAD);
		BiomeDictionary.addTypes(ACBiomes.dark_realm, Type.DEAD);

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

	private static void registerBiomeWithTypes(Biome biome, String name, int weight, BiomeType btype, Type...types){
		GameRegistry.register(biome.setRegistryName(new ResourceLocation(modid, name)));
		BiomeDictionary.addTypes(biome, types);
		BiomeManager.addBiome(btype, new BiomeEntry(biome, weight));
	}
}
