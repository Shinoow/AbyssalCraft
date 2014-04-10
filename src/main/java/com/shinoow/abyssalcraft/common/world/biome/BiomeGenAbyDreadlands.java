package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.Entityabygolem;
import com.shinoow.abyssalcraft.common.entity.Entitydreadgolem;

public class BiomeGenAbyDreadlands extends BiomeGenBase
{

	private WorldGenerator theWorldGenerator;
	private WorldGenerator theSecondWorldGenerator;
	private WorldGenerator theThirdWorldGenerator;

	@SuppressWarnings("unchecked")
	public BiomeGenAbyDreadlands(int par1) {
		super(par1);
		this.topBlock = (Block)AbyssalCraft.abydreadstone;
		this.fillerBlock = (Block)AbyssalCraft.abydreadstone;
		this.spawnableMonsterList.clear();
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
		this.theBiomeDecorator.treesPerChunk = -1;
		this.theBiomeDecorator.flowersPerChunk= -1;
		this.theWorldGenerator = new WorldGenMinable(AbyssalCraft.abydreadstone, 96, AbyssalCraft.dreadstone);
		this.theSecondWorldGenerator = new WorldGenMinable(AbyssalCraft.abydreadstone, 64, AbyssalCraft.dreadstone);
		this.theThirdWorldGenerator = new WorldGenMinable(AbyssalCraft.abydreadstone, 32, AbyssalCraft.dreadstone);
		this.spawnableMonsterList.add(new SpawnListEntry(Entitydreadgolem.class, 2, 1, 2));
		this.spawnableCreatureList.add(new SpawnListEntry(Entityabygolem.class, 5, 1, 5));	
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
				par1World.setBlock(var7, var8, var9, AbyssalCraft.abydreadore);
			}
		}
		for (var5 = 0; var5 < 7; ++var5)
		{
			var6 = par3 + par2Random.nextInt(16);
			var7 = par2Random.nextInt(64);
			var8 = par4 + par2Random.nextInt(16);
			this.theWorldGenerator.generate(par1World, par2Random, var6, var7, var8);
		}
		for (var5 = 0; var5 < 7; ++var5)
		{
			var6 = par3 + par2Random.nextInt(16);
			var7 = par2Random.nextInt(64);
			var8 = par4 + par2Random.nextInt(16);
			this.theSecondWorldGenerator.generate(par1World, par2Random, var6, var7, var8);
		}
		for (var5 = 0; var5 < 7; ++var5)
		{
			var6 = par3 + par2Random.nextInt(16);
			var7 = par2Random.nextInt(64);
			var8 = par4 + par2Random.nextInt(16);
			this.theThirdWorldGenerator.generate(par1World, par2Random, var6, var7, var8);
		}
	}
}
