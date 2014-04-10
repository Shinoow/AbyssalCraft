package com.shinoow.abyssalcraft.common.world.biome;

import com.shinoow.abyssalcraft.common.entity.Entityabygolem;
import com.shinoow.abyssalcraft.common.entity.Entitydreadgolem;

import net.minecraft.world.biome.BiomeGenBase;

public class BiomeGenMountainDreadlands extends BiomeGenBase
{
	
	@SuppressWarnings("unchecked")
	public BiomeGenMountainDreadlands(int par1) {
		super(par1);
		this.rootHeight = 1.3F;
		this.heightVariation = 0.9F;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.theBiomeDecorator.treesPerChunk = -1;
		this.theBiomeDecorator.flowersPerChunk= -1;
		this.spawnableMonsterList.add(new SpawnListEntry(Entitydreadgolem.class, 2, 1, 2));
		this.spawnableCreatureList.add(new SpawnListEntry(Entityabygolem.class, 2, 1, 2));
	}
	
}
