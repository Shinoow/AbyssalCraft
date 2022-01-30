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

import static com.shinoow.abyssalcraft.AbyssalCraft.modid;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.dimension.DimensionData;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperMinion;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.MapGenAbyStronghold;
import com.shinoow.abyssalcraft.common.structures.abyss.stronghold.StructureAbyStrongholdPieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMinePieces;
import com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft.StructureDreadlandsMineStart;
import com.shinoow.abyssalcraft.common.world.*;
import com.shinoow.abyssalcraft.common.world.biome.*;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class WorldHandler implements ILifeCycleHandler {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		ACBiomes.darklands = new BiomeDarklands(new BiomeProperties("Darklands").setWaterColor(ACClientVars.getDarklandsWaterColor()));
		ACBiomes.abyssal_wastelands = new BiomeAbywasteland(new BiomeProperties("Abyssal Wastelands").setWaterColor(ACClientVars.getAbyssalWastelandWaterColor()).setRainDisabled());
		ACBiomes.dreadlands = new BiomeDreadlands(new BiomeProperties("Dreadlands").setRainDisabled());
		ACBiomes.purified_dreadlands = new BiomeAbyDreadlands(new BiomeProperties("Purified Dreadlands").setRainDisabled());
		ACBiomes.dreadlands_forest = new BiomeForestDreadlands(new BiomeProperties("Dreadlands Forest").setRainDisabled());
		ACBiomes.dreadlands_mountains = new BiomeMountainDreadlands(new BiomeProperties("Dreadlands Mountains").setBaseHeight(1.3F).setHeightVariation(0.9F).setRainDisabled());
		ACBiomes.darklands_forest = new BiomeDarklandsForest(new BiomeProperties("Darklands Forest").setWaterColor(ACClientVars.getDarklandsForestWaterColor()));
		ACBiomes.darklands_plains = new BiomeDarklandsPlains(new BiomeProperties("Darklands Plains").setWaterColor(ACClientVars.getDarklandsPlainsWaterColor()));
		ACBiomes.darklands_hills = new BiomeDarklandsHills(new BiomeProperties("Darklands Highland").setWaterColor(ACClientVars.getDarklandsHighlandsWaterColor()).setBaseHeight(1.1F).setHeightVariation(0.5F).setTemperature(0.2F).setRainfall(0.3F));
		ACBiomes.darklands_mountains = new BiomeDarklandsMountains(new BiomeProperties("Darklands Mountains").setWaterColor(ACClientVars.getDarklandsMountainsWaterColor()).setBaseHeight(1.3F).setHeightVariation(0.9F).setTemperature(0.2F).setRainfall(0.3F));
		ACBiomes.coralium_infested_swamp = new BiomeCorSwamp(new BiomeProperties("Coralium Infested Swamp").setWaterColor(ACClientVars.getCoraliumInfestedSwampWaterColor()).setBaseHeight(-0.2F).setHeightVariation(0.1F));
		ACBiomes.omothol = new BiomeOmothol(new BiomeProperties("Omothol").setWaterColor(ACClientVars.getOmotholWaterColor()).setRainDisabled());
		ACBiomes.dark_realm = new BiomeDarkRealm(new BiomeProperties("Dark Realm").setWaterColor(ACClientVars.getDarkRealmWaterColor()).setRainDisabled());
		ACBiomes.purged = new BiomePurged(new BiomeProperties("Purged").setWaterColor(ACClientVars.getPurgedWaterColor()).setRainDisabled());

		InitHandler.INSTANCE.BIOMES.add(ACBiomes.darklands.setRegistryName(new ResourceLocation(modid, "darklands")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.darklands_forest.setRegistryName(new ResourceLocation(modid, "darklands_forest")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.darklands_plains.setRegistryName(new ResourceLocation(modid, "darklands_plains")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.darklands_hills.setRegistryName(new ResourceLocation(modid, "darklands_hills")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.darklands_mountains.setRegistryName(new ResourceLocation(modid, "darklands_mountains")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.coralium_infested_swamp.setRegistryName(new ResourceLocation(modid, "coralium_infested_swamp")));

		InitHandler.INSTANCE.BIOMES.add(ACBiomes.abyssal_wastelands.setRegistryName(new ResourceLocation(modid, "abyssal_wastelands")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.dreadlands.setRegistryName(new ResourceLocation(modid, "dreadlands")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.purified_dreadlands.setRegistryName(new ResourceLocation(modid, "purified_dreadlands")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.dreadlands_forest.setRegistryName(new ResourceLocation(modid, "dreadlands_forest")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.dreadlands_mountains.setRegistryName(new ResourceLocation(modid, "dreadlands_mountains")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.omothol.setRegistryName(new ResourceLocation(modid, "omothol")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.dark_realm.setRegistryName(new ResourceLocation(modid, "dark_realm")));
		InitHandler.INSTANCE.BIOMES.add(ACBiomes.purged.setRegistryName(new ResourceLocation(modid, "purged")));

		ACLib.THE_ABYSSAL_WASTELAND = DimensionType.register("The Abyssal Wasteland", "_aw", ACLib.abyssal_wasteland_id, WorldProviderAbyss.class, ACConfig.keepLoaded1);
		ACLib.THE_DREADLANDS = DimensionType.register("The Dreadlands", "_dl", ACLib.dreadlands_id, WorldProviderDreadlands.class, ACConfig.keepLoaded2);
		ACLib.OMOTHOL = DimensionType.register("Omothol", "_omt", ACLib.omothol_id, WorldProviderOmothol.class, ACConfig.keepLoaded3);
		ACLib.THE_DARK_REALM = DimensionType.register("The Dark Realm", "_dl", ACLib.dark_realm_id, WorldProviderDarkRealm.class, ACConfig.keepLoaded4);

		DimensionManager.registerDimension(ACLib.abyssal_wasteland_id, ACLib.THE_ABYSSAL_WASTELAND);
		DimensionManager.registerDimension(ACLib.dreadlands_id, ACLib.THE_DREADLANDS);
		DimensionManager.registerDimension(ACLib.omothol_id, ACLib.OMOTHOL);
		DimensionManager.registerDimension(ACLib.dark_realm_id, ACLib.THE_DARK_REALM);

		DimensionDataRegistry.instance().registerDimensionData(new DimensionData.Builder(ACLib.abyssal_wasteland_id)
				.addConnectedDimension(ACConfig.startDimension)
				.addConnectedDimension(ACLib.dreadlands_id)
				.setColor(0, 255, 0)
				.setGatewayKey(0)
				.setMob(EntityAbyssalZombie.class)
				.build());
		DimensionDataRegistry.instance().registerDimensionData(new DimensionData.Builder(ACLib.dreadlands_id)
				.addConnectedDimension(ACLib.abyssal_wasteland_id)
				.addConnectedDimension(ACLib.omothol_id)
				.setColor(255, 0, 0)
				.setGatewayKey(1)
				.setMob(EntityDreadling.class)
				.build());
		DimensionDataRegistry.instance().registerDimensionData(new DimensionData.Builder(ACLib.omothol_id)
				.addConnectedDimension(ACLib.dark_realm_id)
				.addConnectedDimension(ACLib.dreadlands_id)
				.setColor(0, 255, 255)
				.setGatewayKey(2)
				.setMob(EntityGatekeeperMinion.class)
				.setOverlay(new ResourceLocation("abyssalcraft", "textures/model/omothol_portal.png"))
				.build());
		DimensionDataRegistry.instance().registerDimensionData(new DimensionData.Builder(0)
				.setColor(0, 0, 255)
				.setGatewayKey(0)
				.build());
	}

	@Override
	public void init(FMLInitializationEvent event) {
		MapGenStructureIO.registerStructure(MapGenAbyStronghold.Start.class, "AbyStronghold");
		StructureAbyStrongholdPieces.registerStructurePieces();
		MapGenStructureIO.registerStructure(StructureDreadlandsMineStart.class, "DreadMine");
		StructureDreadlandsMinePieces.registerStructurePieces();
		GameRegistry.registerWorldGenerator(new AbyssalCraftWorldGenerator(), 0);
	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {}

	@Override
	public void loadComplete(FMLLoadCompleteEvent event) {
		if(ACConfig.purgeMobSpawns){
			((BiomeAbywasteland) ACBiomes.abyssal_wastelands).setMobSpawns();
			((BiomeDreadlands) ACBiomes.dreadlands).setMobSpawns();
			((BiomeAbyDreadlands) ACBiomes.purified_dreadlands).setMobSpawns();
			((BiomeForestDreadlands) ACBiomes.dreadlands_forest).setMobSpawns();
			((BiomeMountainDreadlands) ACBiomes.dreadlands_mountains).setMobSpawns();
			((BiomeOmothol) ACBiomes.omothol).setMobSpawns();
			((BiomeDarkRealm) ACBiomes.dark_realm).setMobSpawns();
		}
	}
}
