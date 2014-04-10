package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDrT;

public class BiomeGenForestDreadlands extends BiomeGenBase
{

	private WorldGenTrees WorldGenDreadTrees;
	
	public BiomeGenForestDreadlands(int par1) {
		super(par1);
		this.topBlock = (Block)AbyssalCraft.dreadgrass;
		this.fillerBlock = (Block)AbyssalCraft.dreadstone;
		this.WorldGenDreadTrees = new WorldGenDrT(false);
		this.theBiomeDecorator.treesPerChunk = 20;
		this.theBiomeDecorator.flowersPerChunk= -1;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		
	}

	public WorldGenAbstractTree func_150567_a(Random par1Random)
	{
		return (par1Random.nextInt(5) == 0 ? this.worldGeneratorTrees : (par1Random.nextInt(10) == 0 ? this.WorldGenDreadTrees : this.worldGeneratorTrees));
	}
	
}
