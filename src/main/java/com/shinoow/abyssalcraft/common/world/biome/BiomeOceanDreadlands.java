package com.shinoow.abyssalcraft.common.world.biome;

import java.util.Random;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACClientVars;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeOceanDreadlands extends BiomeDreadlandsBase {

	public BiomeOceanDreadlands(BiomeProperties par1) {
		super(par1);
		this.topBlock = Blocks.SAND.getDefaultState(); //TODO replace
	}


	@Override
	public void setMobSpawns(){
		spawnableMonsterList.clear();
		spawnableCreatureList.clear();
		spawnableWaterCreatureList.clear();
		spawnableCaveCreatureList.clear();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getDreadlandsSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDreadlandsGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getDreadlandsFoliageColor();
	}
	
	@Override
	public void genTerrainBlocks(World world, Random rand, ChunkPrimer chunkPrimer, int x, int z, double d)
	{
		super.genTerrainBlocks(world, rand, chunkPrimer, x, z, d);
	}
}
