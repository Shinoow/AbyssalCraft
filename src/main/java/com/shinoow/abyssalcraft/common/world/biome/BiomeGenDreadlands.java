package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.Entityabygolem;
import com.shinoow.abyssalcraft.common.entity.Entitydreadgolem;

public class BiomeGenDreadlands extends BiomeGenBase
{

	@SuppressWarnings("unchecked")
	public BiomeGenDreadlands(int par1) {
		super(par1);
		this.topBlock = (Block)AbyssalCraft.dreadstone;
		this.fillerBlock = (Block)AbyssalCraft.dreadstone;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.theBiomeDecorator.treesPerChunk = -1;
		this.theBiomeDecorator.flowersPerChunk= -1;
		this.spawnableMonsterList.add(new SpawnListEntry(Entitydreadgolem.class, 5, 1, 5));
		this.spawnableCreatureList.add(new SpawnListEntry(Entityabygolem.class, 2, 1, 2));
	}

	public void decorate(World par1World, Random par2Random, int par3, int par4)
	{
		super.decorate(par1World, par2Random, par3, par4);
		int var5 = 3 + par2Random.nextInt(6);
		int var6;
		int var7;
		int var8;

		for (var6 = 0; var6 < var5; ++var6)
		{
			var7 = par3 + par2Random.nextInt(16);
			var8 = par2Random.nextInt(28) + 4;
			int var9 = par4 + par2Random.nextInt(16);
			Block var10 = par1World.getBlock(var7, var8, var9);

			if (var10 == AbyssalCraft.dreadstone)
			{
				par1World.setBlock(var7, var8, var9, AbyssalCraft.dreadore);
			}
		}
	}
}
