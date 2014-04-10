package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsZombie;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsghoul;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BiomeGenDarklandsMountains extends BiomeGenBase
{

	private WorldGenerator theWorldGenerator;
	private WorldGenerator theSecondWorldGenerator;
	private WorldGenerator theThirdWorldGenerator;
	private WorldGenerator theFourthWorldGenerator;

	@SuppressWarnings("unchecked")
	public BiomeGenDarklandsMountains(int par1)
	{
		super(par1);
		this.rootHeight = 1.3F;
		this.heightVariation = 0.9F;
		this.spawnableCreatureList.clear();
		this.spawnableMonsterList.clear();
		this.topBlock = (Block)AbyssalCraft.Darkstone;
		this.fillerBlock = (Block)AbyssalCraft.Darkstone;
		this.waterColorMultiplier = 14745518;
		this.theWorldGenerator = new WorldGenMinable(AbyssalCraft.Darkstone, 96);
		this.theSecondWorldGenerator = new WorldGenMinable(AbyssalCraft.abydreadstone, 1);
		this.theThirdWorldGenerator = new WorldGenMinable(AbyssalCraft.Darkstone, 64);
		this.theFourthWorldGenerator = new WorldGenMinable(AbyssalCraft.Darkstone, 32);
		this.theBiomeDecorator.treesPerChunk = 0;
		this.spawnableMonsterList.add(new SpawnListEntry(EntityZombie.class, 2, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntitySkeleton.class, 2, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityDepthsghoul.class, 2, 1, 2));
		this.spawnableMonsterList.add(new SpawnListEntry(EntityDepthsZombie.class, 2, 1, 2));
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

		for (var5 = 0; var5 < 7; ++var5)
		{
			var6 = par3 + par2Random.nextInt(16);
			var7 = par2Random.nextInt(64);
			var8 = par4 + par2Random.nextInt(16);
			this.theFourthWorldGenerator.generate(par1World, par2Random, var6, var7, var8);
		}
	}

	@SideOnly(Side.CLIENT)

	public int getSkyColorByTemp(float par1)
	{
		return 0;
	}
}
