/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACLib;

public class WorldProviderOmothol extends WorldProvider {

	@Override
	public void registerWorldChunkManager()
	{
		worldChunkMgr = new WorldChunkManagerHell(ACBiomes.omothol, 0.0F);
		dimensionId = ACLib.omothol_id;
		hasNoSky = true;
	}

	@Override
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderOmothol(worldObj, 1251393890L);
	}

	@Override
	public String getDimensionName()
	{
		return "Omothol";
	}

	@Override
	protected void generateLightBrightnessTable() {
		float f = 0.25F;

		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - i / 15.0F;
			lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}
	}

	@Override
	public float calculateCelestialAngle(long par1, float par3)
	{
		return 0.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float par1, float par2)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3 getFogColor(float par1, float par2)
	{
		int i = 10518688;
		float f2 = MathHelper.cos(par1 * (float)Math.PI * 2.0F) * 2.0F + 0.5F;

		if (f2 < 0.0F)
			f2 = 0.0F;

		if (f2 > 1.0F)
			f2 = 1.0F;

		float f3 = (i >> 16 & 255) / 255.0F;
		float f4 = (i >> 8 & 255) / 255.0F;
		float f5 = (i & 255) / 255.0F;
		f3 *= f2 * 0.0F + 0.15F;
		f4 *= f2 * 0.0F + 0.15F;
		f5 *= f2 * 0.0F + 0.15F;
		return new Vec3(f3, f4, f5);
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isSkyColored()
	{
		return false;
	}

	@Override
	public boolean canRespawnHere()
	{
		return false;
	}

	@Override
	public boolean isSurfaceWorld()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight()
	{
		return 8.0F;
	}

	@Override
	public boolean canCoordinateBeSpawn(int x, int z)
	{
		return worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)) == ACBlocks.omothol_stone;
	}

	@Override
	public String getSaveFolder() {
		return "Omothol";
	}

	@Override
	public int getAverageGroundLevel()
	{
		return 50;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int par1, int par2)
	{
		return true;
	}

	@Override
	public String getInternalNameSuffix() {

		return "_omt";
	}
}