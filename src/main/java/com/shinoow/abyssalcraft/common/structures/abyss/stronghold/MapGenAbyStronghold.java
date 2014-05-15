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
package com.shinoow.abyssalcraft.common.structures.abyss.stronghold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class MapGenAbyStronghold extends MapGenStructure
{
	public static ArrayList<BiomeGenBase> allowedBiomes = new ArrayList<BiomeGenBase>(Arrays.asList(AbyssalCraft.Wastelands)); 
	private BiomeGenBase[] allowedBiomeGenBases;

	/**
	 * is spawned false and set true once the defined BiomeGenBases were compared with the present ones
	 */
	private boolean ranBiomeCheck;
	private ChunkCoordIntPair[] structureCoords;
	private double field_82671_h;
	private int field_82672_i;

	public MapGenAbyStronghold()
	{
		this.allowedBiomeGenBases = allowedBiomes.toArray(new BiomeGenBase[0]);
		this.structureCoords = new ChunkCoordIntPair[3];
		this.field_82671_h = 32.0D;
		this.field_82672_i = 3;
	}

	@SuppressWarnings("rawtypes")
	public MapGenAbyStronghold(Map par1Map)
	{
		this.allowedBiomeGenBases = allowedBiomes.toArray(new BiomeGenBase[0]);
		this.structureCoords = new ChunkCoordIntPair[3];
		this.field_82671_h = 32.0D;
		this.field_82672_i = 3;
		Iterator var2 = par1Map.entrySet().iterator();

		while (var2.hasNext())
		{
			Entry var3 = (Entry)var2.next();

			if (((String)var3.getKey()).equals("distance"))
			{
				this.field_82671_h = MathHelper.parseDoubleWithDefaultAndMax((String)var3.getValue(), this.field_82671_h, 1.0D);
			}
			else if (((String)var3.getKey()).equals("count"))
			{
				this.structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.structureCoords.length, 1)];
			}
			else if (((String)var3.getKey()).equals("spread"))
			{
				this.field_82672_i = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), this.field_82672_i, 1);
			}
		}
	}


	public String func_143025_a() {

		return "AbyStronghold";
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected boolean canSpawnStructureAtCoords(int par1, int par2)
	{
		if (!this.ranBiomeCheck)
		{
			Random var3 = new Random();
			var3.setSeed(this.worldObj.getSeed());
			double var4 = var3.nextDouble() * Math.PI * 2.0D;
			int var6 = 1;

			for (int var7 = 0; var7 < this.structureCoords.length; ++var7)
			{
				double var8 = (1.25D * (double)var6 + var3.nextDouble()) * this.field_82671_h * (double)var6;
				int var10 = (int)Math.round(Math.cos(var4) * var8);
				int var11 = (int)Math.round(Math.sin(var4) * var8);
				ArrayList var12 = new ArrayList();
				Collections.addAll(var12, this.allowedBiomeGenBases);
				ChunkPosition var13 = this.worldObj.getWorldChunkManager().findBiomePosition((var10 << 4) + 8, (var11 << 4) + 8, 112, var12, var3);

				if (var13 != null)
				{
					var10 = var13.chunkPosX >> 4;
				var11 = var13.chunkPosY >> 4;
				}

				this.structureCoords[var7] = new ChunkCoordIntPair(var10, var11);
				var4 += (Math.PI * 2D) * (double)var6 / (double)this.field_82672_i;

				if (var7 == this.field_82672_i)
				{
					var6 += 2 + var3.nextInt(5);
					this.field_82672_i += 1 + var3.nextInt(2);
				}
			}

			this.ranBiomeCheck = true;
		}

		ChunkCoordIntPair[] var14 = this.structureCoords;
		int var15 = var14.length;

		for (int var5 = 0; var5 < var15; ++var5)
		{
			ChunkCoordIntPair var16 = var14[var5];

			if (par1 == var16.chunkXPos && par2 == var16.chunkZPos)
			{
				return true;
			}
		}

		return false;
	}

	/**
	 * Returns a list of other locations at which the structure generation has been run, or null if not relevant to this
	 * structure generator.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getCoordList()
	{
		ArrayList var1 = new ArrayList();
		ChunkCoordIntPair[] var2 = this.structureCoords;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4)
		{
			ChunkCoordIntPair var5 = var2[var4];

			if (var5 != null)
			{
				var1.add(var5.func_151349_a(64));
			}
		}

		return var1;
	}

	protected StructureStart getStructureStart(int par1, int par2)
	{
		MapGenAbyStronghold.Start start;

		for (start = new MapGenAbyStronghold.Start(this.worldObj, this.rand, par1, par2); start.getComponents().isEmpty() || ((StructureAbyStrongholdPieces.Stairs2)start.getComponents().get(0)).strongholdPortalRoom == null; start = new MapGenAbyStronghold.Start(this.worldObj, this.rand, par1, par2))
		{
			;
		}

		return start;
	}

	public static class Start extends StructureStart
	{

		public Start() {}

		@SuppressWarnings({ "unchecked", "rawtypes" })
		public Start(World par1World, Random par2Random, int par3, int par4)
		{
			super(par3, par4);
			StructureAbyStrongholdPieces.prepareStructurePieces();
			StructureAbyStrongholdPieces.Stairs2 stairs2 = new StructureAbyStrongholdPieces.Stairs2(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
			this.components.add(stairs2);
			stairs2.buildComponent(stairs2, this.components, par2Random);
			List list = stairs2.field_75026_c;

			while (!list.isEmpty())
			{
				int k = par2Random.nextInt(list.size());
				StructureComponent structurecomponent = (StructureComponent)list.remove(k);
				structurecomponent.buildComponent(stairs2, this.components, par2Random);
			}

			this.updateBoundingBox();
			this.markAvailableHeight(par1World, par2Random, 10);
		}
	}
}
