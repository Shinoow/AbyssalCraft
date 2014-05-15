/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.world;

import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.chunk.IChunkProvider;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class WorldProviderDreadlands extends WorldProvider
{
	/**
	 * creates a new world chunk manager for WorldProvider
	 */
	public void registerWorldChunkManager()
	{
		this.worldChunkMgr = new WorldChunkManagerDreadlands(worldObj.getSeed(), terrainType);
		this.hasNoSky = true;
		this.dimensionId = AbyssalCraft.dimension2;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Return Vec3D with biome specific fog color
	 */
	public Vec3 getFogColor(float par1, float par2)
	{
		return this.worldObj.getWorldVec3Pool().getVecFromPool(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
	}

	/**
	 * Creates the light to brightness table
	 */
	protected void generateLightBrightnessTable()
	{
		float f = 0.35F;

		for (int i = 0; i <= 15; ++i)
		{
			float f1 = 1.0F - (float)i / 15.0F;
			this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - f) + f;
		}
	}

	/**
	 * Returns a new chunk provider which generates chunks for this world
	 */
	public IChunkProvider createChunkGenerator()
	{
		return new ChunkProviderDreadlands(this.worldObj, this.worldObj.getSeed(), true);
	}

	/**
	 * Returns 'true' if in the "main surface world", but 'false' if in the Nether or End dimensions.
	 */
	public boolean isSurfaceWorld()
	{
		return false;
	}

	/**
	 * Will check if the x, z position specified is alright to be set as the map spawn point
	 */
	public boolean canCoordinateBeSpawn(int par1, int par2)
	{
		return false;
	}

	/**
	 * Calculates the angle of sun and moon in the sky relative to a specified time (usually worldTime)
	 */
	public float calculateCelestialAngle(long par1, float par3)
	{
		return 0.5F;
	}

	/**
	 * True if the player can respawn in this dimension (true = overworld, false = nether).
	 */
	public boolean canRespawnHere()
	{
		return false;
	}

	@SideOnly(Side.CLIENT)

	/**
	 * Returns true if the given X,Z coordinate should show environmental fog.
	 */
	public boolean doesXZShowFog(int par1, int par2)
	{
		return true;
	}

	public String getSaveFolder()
	{
		return "The_Dreadlands";
	}

	public int getAverageGroundLevel()
	{
		return 50;
	}

	/**
	 * Returns the dimension's name, e.g. "The End", "Nether", or "Overworld".
	 */
	public String getDimensionName()
	{
		return "The Dreadlands";
	}
}
