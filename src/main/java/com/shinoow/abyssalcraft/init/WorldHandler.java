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

import static com.shinoow.abyssalcraft.init.InitHandler.*;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeEntry;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.MapGenAbyStronghold;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.StructureAbyStrongholdPieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMinePieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMineStart;
import com.shinoow.abyssalcraft.common.structures.omothol.MapGenOmothol;
import com.shinoow.abyssalcraft.common.structures.omothol.StructureOmotholPieces;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.common.world.*;
import com.shinoow.abyssalcraft.common.world.biome.*;
import com.shinoow.abyssalcraft.lib.ACLib;

public class WorldHandler implements ILifeCycleHandler {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		checkBiomeIds(true);

		ACBiomes.darklands = new BiomeGenDarklands(configBiomeId1).setColor(522674).setBiomeName("Darklands");
		ACBiomes.abyssal_wastelands = new BiomeGenAbywasteland(configBiomeId2).setColor(522674).setBiomeName("Abyssal Wastelands").setDisableRain();
		ACBiomes.dreadlands = new BiomeGenDreadlands(configBiomeId3).setColor(522674).setBiomeName("Dreadlands").setDisableRain();
		ACBiomes.purified_dreadlands = new BiomeGenAbyDreadlands(configBiomeId4).setColor(522674).setBiomeName("Purified Dreadlands").setDisableRain();
		ACBiomes.dreadlands_forest = new BiomeGenForestDreadlands(configBiomeId5).setColor(522674).setBiomeName("Dreadlands Forest").setDisableRain();
		ACBiomes.dreadlands_mountains = new BiomeGenMountainDreadlands(configBiomeId6).setColor(522674).setBiomeName("Dreadlands Mountains").setDisableRain();
		ACBiomes.darklands_forest = new BiomeGenDarklandsForest(configBiomeId7).setColor(522674).setBiomeName("Darklands Forest");
		ACBiomes.darklands_plains = new BiomeGenDarklandsPlains(configBiomeId8).setColor(522674).setBiomeName("Darklands Plains").setDisableRain();
		ACBiomes.darklands_hills = new BiomeGenDarklandsHills(configBiomeId9).setColor(522674).setBiomeName("Darklands Highland");
		ACBiomes.darklands_mountains = new BiomeGenDarklandsMountains(configBiomeId10).setColor(522674).setBiomeName("Darklands Mountains").setDisableRain();
		ACBiomes.coralium_infested_swamp = new BiomeGenCorSwamp(configBiomeId11).setColor(522674).setBiomeName("Coralium Infested Swamp");
		ACBiomes.omothol = new BiomeGenOmothol(configBiomeId12).setColor(5522674).setBiomeName("Omothol").setDisableRain();
		ACBiomes.dark_realm = new BiomeGenDarkRealm(configBiomeId13).setColor(522674).setBiomeName("Dark Realm").setDisableRain();

		if(dark1 == true){
			registerBiomeWithTypes(ACBiomes.darklands, darkWeight1, BiomeType.WARM, Type.WASTELAND, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands, true);
		}
		if(dark2 == true){
			registerBiomeWithTypes(ACBiomes.darklands_forest, darkWeight2, BiomeType.WARM, Type.FOREST, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_forest, true);
		}
		if(dark3 == true){
			registerBiomeWithTypes(ACBiomes.darklands_plains, darkWeight3, BiomeType.WARM, Type.PLAINS, Type.SPOOKY);
			BiomeManager.addVillageBiome(ACBiomes.darklands_plains, true);
		}
		if(dark4 == true)
			registerBiomeWithTypes(ACBiomes.darklands_hills, darkWeight4, BiomeType.WARM, Type.HILLS, Type.SPOOKY);
		if(dark5 == true){
			registerBiomeWithTypes(ACBiomes.darklands_mountains, darkWeight5, BiomeType.WARM, Type.MOUNTAIN, Type.SPOOKY);
			BiomeManager.addStrongholdBiome(ACBiomes.darklands_mountains);
		}
		if(coralium1 == true)
			registerBiomeWithTypes(ACBiomes.coralium_infested_swamp, coraliumWeight, BiomeType.WARM, Type.SWAMP);
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

		BiomeDictionary.registerBiomeType(ACBiomes.abyssal_wastelands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.purified_dreadlands, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dreadlands_mountains, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dreadlands_forest, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.omothol, Type.DEAD);
		BiomeDictionary.registerBiomeType(ACBiomes.dark_realm, Type.DEAD);

		registerDimension(ACLib.abyssal_wasteland_id, WorldProviderAbyss.class, AbyssalCraft.keepLoaded1);
		registerDimension(ACLib.dreadlands_id, WorldProviderDreadlands.class, AbyssalCraft.keepLoaded2);
		registerDimension(ACLib.omothol_id, WorldProviderOmothol.class, AbyssalCraft.keepLoaded3);
		registerDimension(ACLib.dark_realm_id, WorldProviderDarkRealm.class, AbyssalCraft.keepLoaded4);
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
	public void postInit(FMLPostInitializationEvent event) {
		checkBiomeIds(false);
	}

	private static void registerBiomeWithTypes(BiomeGenBase biome, int weight, BiomeType btype, Type...types){
		BiomeDictionary.registerBiomeType(biome, types);
		BiomeManager.addBiome(btype, new BiomeEntry(biome, weight));
	}

	private static void registerDimension(int id, Class<? extends WorldProvider> provider, boolean keepLoaded){
		DimensionManager.registerProviderType(id, provider, keepLoaded);
		DimensionManager.registerDimension(id, id);
	}

	private void checkBiomeIds(boolean first){
		if(first){
			ACLogger.info("Scanning biome IDs to see if the ones needed are available.");

			if(BiomeGenBase.getBiomeGenArray()[configBiomeId1] != null && dark1)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId1, BiomeGenBase.getBiome(configBiomeId1).biomeName,
						BiomeGenBase.getBiome(configBiomeId1).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId2] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId2, BiomeGenBase.getBiome(configBiomeId2).biomeName,
						BiomeGenBase.getBiome(configBiomeId2).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId3] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId3, BiomeGenBase.getBiome(configBiomeId3).biomeName,
						BiomeGenBase.getBiome(configBiomeId3).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId4] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId4, BiomeGenBase.getBiome(configBiomeId4).biomeName,
						BiomeGenBase.getBiome(configBiomeId4).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId5] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId5, BiomeGenBase.getBiome(configBiomeId5).biomeName,
						BiomeGenBase.getBiome(configBiomeId5).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId6] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId6, BiomeGenBase.getBiome(configBiomeId6).biomeName,
						BiomeGenBase.getBiome(configBiomeId6).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId7] != null && dark2)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId7, BiomeGenBase.getBiome(configBiomeId7).biomeName,
						BiomeGenBase.getBiome(configBiomeId7).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId8] != null && dark3)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId8, BiomeGenBase.getBiome(configBiomeId8).biomeName,
						BiomeGenBase.getBiome(configBiomeId8).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId9] != null && dark4)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId9, BiomeGenBase.getBiome(configBiomeId9).biomeName,
						BiomeGenBase.getBiome(configBiomeId9).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId10] != null && dark5)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId10, BiomeGenBase.getBiome(configBiomeId10).biomeName,
						BiomeGenBase.getBiome(configBiomeId10).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId11] != null && coralium1)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId11, BiomeGenBase.getBiome(configBiomeId11).biomeName,
						BiomeGenBase.getBiome(configBiomeId11).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId12] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId12, BiomeGenBase.getBiome(configBiomeId12).biomeName,
						BiomeGenBase.getBiome(configBiomeId12).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId13] != null)
				throw new RuntimeException(String.format("Biome ID %d is already occupied by the biome %s (%s)!", configBiomeId13, BiomeGenBase.getBiome(configBiomeId13).biomeName,
						BiomeGenBase.getBiome(configBiomeId13).getBiomeClass().getName()));

			ACLogger.info("None of the needed biome IDs are occupied!");
		} else {
			ACLogger.info("Checking so that no other mod has overridden a used biome ID.");

			if(BiomeGenBase.getBiomeGenArray()[configBiomeId1] != ACBiomes.darklands && dark1)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId1, BiomeGenBase.getBiome(configBiomeId1).biomeName,
						BiomeGenBase.getBiome(configBiomeId1).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId2] != ACBiomes.abyssal_wastelands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId2, BiomeGenBase.getBiome(configBiomeId2).biomeName,
						BiomeGenBase.getBiome(configBiomeId2).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId3] != ACBiomes.dreadlands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId3, BiomeGenBase.getBiome(configBiomeId3).biomeName,
						BiomeGenBase.getBiome(configBiomeId3).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId4] != ACBiomes.purified_dreadlands)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId4, BiomeGenBase.getBiome(configBiomeId4).biomeName,
						BiomeGenBase.getBiome(configBiomeId4).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId5] != ACBiomes.dreadlands_forest)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId5, BiomeGenBase.getBiome(configBiomeId5).biomeName,
						BiomeGenBase.getBiome(configBiomeId5).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId6] != ACBiomes.dreadlands_mountains)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId6, BiomeGenBase.getBiome(configBiomeId6).biomeName,
						BiomeGenBase.getBiome(configBiomeId6).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId7] != ACBiomes.darklands_forest && dark2)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId7, BiomeGenBase.getBiome(configBiomeId7).biomeName,
						BiomeGenBase.getBiome(configBiomeId7).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId8] != ACBiomes.darklands_plains && dark3)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId8, BiomeGenBase.getBiome(configBiomeId8).biomeName,
						BiomeGenBase.getBiome(configBiomeId8).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId9] != ACBiomes.darklands_hills && dark4)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId9, BiomeGenBase.getBiome(configBiomeId9).biomeName,
						BiomeGenBase.getBiome(configBiomeId9).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId10] != ACBiomes.darklands_mountains && dark5)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId10, BiomeGenBase.getBiome(configBiomeId10).biomeName,
						BiomeGenBase.getBiome(configBiomeId10).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId11] != ACBiomes.coralium_infested_swamp && coralium1)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId11, BiomeGenBase.getBiome(configBiomeId11).biomeName,
						BiomeGenBase.getBiome(configBiomeId11).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId12] != ACBiomes.omothol)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId12, BiomeGenBase.getBiome(configBiomeId12).biomeName,
						BiomeGenBase.getBiome(configBiomeId12).getBiomeClass().getName()));
			if(BiomeGenBase.getBiomeGenArray()[configBiomeId13] != ACBiomes.dark_realm)
				throw new RuntimeException(String.format("Biome ID %d was overridden by the biome %s (%s)!", configBiomeId13, BiomeGenBase.getBiome(configBiomeId13).biomeName,
						BiomeGenBase.getBiome(configBiomeId13).getBiomeClass().getName()));

			ACLogger.info("None of the biome IDs has been overridden.");
		}
	}
}
