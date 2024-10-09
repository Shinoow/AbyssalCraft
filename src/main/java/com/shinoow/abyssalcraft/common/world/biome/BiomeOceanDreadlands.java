package com.shinoow.abyssalcraft.common.world.biome;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACClientVars;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeOceanDreadlands extends BiomeDreadlandsBase {

	public BiomeOceanDreadlands(BiomeProperties par1) {
		super(par1);
		this.topBlock = Blocks.AIR.getDefaultState();
		this.fillerBlock = ACBlocks.dreadlands_muck.getDefaultState();
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
}
