package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsghoul;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenDLT;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenDarklandsHills extends BiomeGenBase
{

	private WorldGenerator theWorldGenerator;
	private WorldGenerator theSecondWorldGenerator;
	private WorldGenerator theThirdWorldGenerator;
	private WorldGenTrees WorldGenDarkTrees;

	@SuppressWarnings("unchecked")
	public BiomeGenDarklandsHills(int par1)
	{
		super(par1);
		this.rootHeight = 1.1F;
		this.heightVariation = 0.5F;
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.topBlock = (Block)AbyssalCraft.Darkgrass;
		this.fillerBlock = (Block)AbyssalCraft.Darkstone;
		this.waterColorMultiplier = 14745518;
		this.theWorldGenerator = new WorldGenMinable(AbyssalCraft.Darkstone, 64);
		this.theSecondWorldGenerator = new WorldGenMinable(AbyssalCraft.abydreadstone, 1);
		this.theThirdWorldGenerator = new WorldGenMinable(AbyssalCraft.Darkstone, 32);
		this.WorldGenDarkTrees = new WorldGenDLT(false);
		this.theBiomeDecorator.treesPerChunk = 1;
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 5, 1, 5));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 5, 1, 5));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityDepthsghoul.class, 5, 1, 5));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityDepthsZombie.class, 3, 1, 3));
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

			if (var10 == Blocks.stone)
			{
				par1World.setBlock(var7, var8, var9, AbyssalCraft.abyore);
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

	public WorldGenAbstractTree func_150567_a(Random par1Random)
	{
		return (par1Random.nextInt(5) == 0 ? this.worldGeneratorTrees : (par1Random.nextInt(10) == 0 ? this.WorldGenDarkTrees : this.worldGeneratorTrees));
	}

	@SideOnly(Side.CLIENT)

	public int getSkyColorByTemp(float par1)
	{
		return 0;
	}
}
