package com.shinoow.abyssalcraft.common.handlers;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiome;
import net.minecraft.world.gen.layer.GenLayerRiverMix;
import net.minecraftforge.event.terraingen.WorldTypeEvent.InitBiomeGens;

import com.shinoow.abyssalcraft.common.util.EnumBiomeType;

import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class BiomeEventHandler 
{
	private static ArrayList<BiomeGenBase> hotBiomes = new ArrayList<BiomeGenBase>();
	private static ArrayList<BiomeGenBase> warmBiomes = new ArrayList<BiomeGenBase>();
	private static ArrayList<BiomeGenBase> coolBiomes = new ArrayList<BiomeGenBase>();
	private static ArrayList<BiomeGenBase> icyBiomes = new ArrayList<BiomeGenBase>();

	@SubscribeEvent
	public void onInitBiomeGens(InitBiomeGens event)
	{
		GenLayer[] originalBiomeGens = event.originalBiomeGens;

		GenLayer parent = originalBiomeGens[0];
		GenLayerBiome genLayerBiome = null;

		if (parent instanceof GenLayerRiverMix)
		{
			GenLayerRiverMix genLayerRiverMix = (GenLayerRiverMix)parent;
			GenLayer biomePatternGeneratorChain = ObfuscationReflectionHelper.getPrivateValue(GenLayerRiverMix.class, genLayerRiverMix, "biomePatternGeneratorChain", "field_75910_b");

			if (biomePatternGeneratorChain != null) parent = biomePatternGeneratorChain;
		}

		while (genLayerBiome == null)
		{
			if (parent instanceof GenLayerBiome)
			{
				genLayerBiome = (GenLayerBiome)parent;
			}

			GenLayer newParent = ObfuscationReflectionHelper.getPrivateValue(GenLayer.class, parent, "parent", "field_75909_a");

			if (newParent == null)
			{
				throw new RuntimeException("Failed to find GenLayerBiome in chain");
			}
			else
			{
				parent = newParent;
			}
		}

		if (genLayerBiome != null)
		{
			ArrayList<BiomeGenBase> tempHotBiomes = new ArrayList<BiomeGenBase>(Arrays.asList((BiomeGenBase[])ObfuscationReflectionHelper.getPrivateValue(GenLayerBiome.class, genLayerBiome, "field_151623_c")));
			ArrayList<BiomeGenBase> tempWarmBiomes = new ArrayList<BiomeGenBase>(Arrays.asList((BiomeGenBase[])ObfuscationReflectionHelper.getPrivateValue(GenLayerBiome.class, genLayerBiome, "field_151621_d")));
			ArrayList<BiomeGenBase> tempCoolBiomes = new ArrayList<BiomeGenBase>(Arrays.asList((BiomeGenBase[])ObfuscationReflectionHelper.getPrivateValue(GenLayerBiome.class, genLayerBiome, "field_151622_e")));
			ArrayList<BiomeGenBase> tempIcyBiomes = new ArrayList<BiomeGenBase>(Arrays.asList((BiomeGenBase[])ObfuscationReflectionHelper.getPrivateValue(GenLayerBiome.class, genLayerBiome, "field_151620_f")));

			for (BiomeGenBase biome : hotBiomes) tempHotBiomes.add(biome);
			for (BiomeGenBase biome : warmBiomes) tempWarmBiomes.add(biome);
			for (BiomeGenBase biome : coolBiomes) tempCoolBiomes.add(biome);
			for (BiomeGenBase biome : icyBiomes) tempIcyBiomes.add(biome);

			ObfuscationReflectionHelper.setPrivateValue(GenLayerBiome.class, genLayerBiome, Arrays.copyOf(tempHotBiomes.toArray(), tempHotBiomes.size(), BiomeGenBase[].class), "field_151623_c");
			ObfuscationReflectionHelper.setPrivateValue(GenLayerBiome.class, genLayerBiome, Arrays.copyOf(tempWarmBiomes.toArray(), tempWarmBiomes.size(), BiomeGenBase[].class), "field_151621_d");
			ObfuscationReflectionHelper.setPrivateValue(GenLayerBiome.class, genLayerBiome, Arrays.copyOf(tempIcyBiomes.toArray(), tempCoolBiomes.size(), BiomeGenBase[].class), "field_151622_e");
			ObfuscationReflectionHelper.setPrivateValue(GenLayerBiome.class, genLayerBiome, Arrays.copyOf(tempCoolBiomes.toArray(), tempIcyBiomes.size(), BiomeGenBase[].class), "field_151620_f");
		}
	}

	public static void registerBiome(BiomeGenBase biome, EnumBiomeType type)
	{
		switch (type)
		{
		case HOT:
			hotBiomes.add(biome);
			break;

		case WARM:
			warmBiomes.add(biome);
			break;

		case COOL:
			coolBiomes.add(biome);
			break;

		case ICY:
			icyBiomes.add(biome);
			break;

		default:
			warmBiomes.add(biome);
			break;
		}
	}
}
