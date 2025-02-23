/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world;

import com.shinoow.abyssalcraft.api.dimension.IAbyssalWorldProvider;
import com.shinoow.abyssalcraft.client.render.sky.ACSkyRenderer;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WorldProviderDreadlands extends WorldProvider implements IAbyssalWorldProvider {

	@Override
	public void init() {
		biomeProvider = new BiomeProviderDreadlands(world.getSeed(), world.getWorldInfo().getTerrainType());
		//		hasNoSky = true;
		setDimension(ACLib.dreadlands_id);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getFogColor(float par1, float par2) {
		return new Vec3d(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
	}

	@Override
	protected void generateLightBrightnessTable() {
		if(ACConfig.hcdarkness_dl && Loader.isModLoaded("hardcoredarkness"))
			super.generateLightBrightnessTable();
		else {
			float f = 0.35F;

			for (int i = 0; i <= 15; ++i) {
				float f1 = 1.0F - i / 15.0F;
				lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
			}
		}
	}

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkGeneratorDreadlands(world, world.getSeed(), true);
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	public void calculateInitialWeather()
	{
		world.getWorldInfo().setRaining(false);
		world.rainingStrength = 0;
		world.getWorldInfo().setThundering(false);
		world.thunderingStrength = 0;
	}

	@Override
	public void updateWeather()
	{
		world.getWorldInfo().setRaining(false);
		world.rainingStrength = 0;
		world.getWorldInfo().setThundering(false);
		world.thunderingStrength = 0;
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public boolean canCoordinateBeSpawn(int par1, int par2) {
		return false;
	}

	@Override
	public float calculateCelestialAngle(long par1, float par3) {
		return 0.5F;
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean doesXZShowFog(int par1, int par2) {
		return true;
	}

	@Override
	public String getSaveFolder() {
		return "The_Dreadlands";
	}

	@Override
	public int getAverageGroundLevel() {
		return 50;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public net.minecraftforge.client.IRenderHandler getSkyRenderer()
	{
		return new ACSkyRenderer(new ResourceLocation("abyssalcraft:textures/environment/dreadlands_sky.png"), ACClientVars.getDreadlandsR(), ACClientVars.getDreadlandsG(), ACClientVars.getDreadlandsB());
	}

	@Override
	public DimensionType getDimensionType() {

		return ACLib.THE_DREADLANDS;
	}
}
