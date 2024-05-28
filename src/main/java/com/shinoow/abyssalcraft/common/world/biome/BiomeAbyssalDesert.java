package com.shinoow.abyssalcraft.common.world.biome;

import com.shinoow.abyssalcraft.api.biome.IAbyssalWastelandBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACClientVars;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeAbyssalDesert extends BiomeAbyssalWastelandBase {

	public BiomeAbyssalDesert(BiomeProperties properties) {
		super(properties);
		topBlock = ACBlocks.abyssal_sand.getDefaultState();
		fillerBlock = ACBlocks.abyssal_sand.getDefaultState();
		barren = true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float par1)
	{
		return ACClientVars.getAbyssalWastelandSkyColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos)
	{
		return ACClientVars.getAbyssalWastelandGrassColor();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos)
	{
		return ACClientVars.getAbyssalWastelandFoliageColor();
	}
}
