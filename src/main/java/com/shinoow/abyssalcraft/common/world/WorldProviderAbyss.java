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

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.biome.BiomeProviderSingle;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.lib.ACLib;

public class WorldProviderAbyss extends WorldProvider {

	@Override
	public IChunkGenerator createChunkGenerator() {
		return new ChunkProviderAbyss(worldObj, worldObj.getSeed(), true);
	}

	@Override
	public void createBiomeProvider() {
		biomeProvider = new BiomeProviderSingle(ACBiomes.abyssal_wastelands);
		isHellWorld= false;
		setDimension(ACLib.abyssal_wasteland_id);
		hasNoSky = true;
	}

	@Override
	public boolean canRespawnHere() {
		return false;
	}

	/**
	 * Creates the light to brightness table
	 */
	@Override
	protected void generateLightBrightnessTable() {
		float f = 0.25F;

		for (int i = 0; i <= 15; ++i) {
			float f1 = 1.0F - i / 15.0F;
			lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}
	}

	@Override
	public boolean canDoRainSnowIce(Chunk chunk) {
		return false;
	}

	@Override
	public boolean isSurfaceWorld() {
		return false;
	}

	@Override
	public float calculateCelestialAngle(long par1, float par3) {
		return 0.0F;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float par1, float par2) {
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isSkyColored() {
		return true;
	}

	@Override
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		return new Vec3d(0, 5, 0.2);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getCloudHeight() {
		return 8.0F;
	}

	@Override
	public String getSaveFolder() {
		return "Abyssal_Wasteland";
	}

	@Override
	public int getAverageGroundLevel() {
		return 50;
	}

	@Override
	public DimensionType getDimensionType() {

		return AbyssalCraft.THE_ABYSSAL_WASTELAND;
	}
}