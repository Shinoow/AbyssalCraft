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
package com.shinoow.abyssalcraft.common.structures.abyss.stronghold;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;

public class MapGenAbyStronghold extends MapGenStructure
{
	public static ArrayList<BiomeGenBase> allowedBiomes = new ArrayList<BiomeGenBase>(Arrays.asList(ACBiomes.abyssal_wastelands));
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
		allowedBiomeGenBases = allowedBiomes.toArray(new BiomeGenBase[0]);
		structureCoords = new ChunkCoordIntPair[3];
		field_82671_h = 32.0D;
		field_82672_i = 3;
	}

	@SuppressWarnings("rawtypes")
	public MapGenAbyStronghold(Map par1Map)
	{
		this();
		Iterator var2 = par1Map.entrySet().iterator();

		while (var2.hasNext())
		{
			Entry var3 = (Entry)var2.next();

			if (((String)var3.getKey()).equals("distance"))
				field_82671_h = MathHelper.parseDoubleWithDefaultAndMax((String)var3.getValue(), field_82671_h, 1.0D);
			else if (((String)var3.getKey()).equals("count"))
				structureCoords = new ChunkCoordIntPair[MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), structureCoords.length, 1)];
			else if (((String)var3.getKey()).equals("spread"))
				field_82672_i = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), field_82672_i, 1);
		}
	}


	@Override
	public String getStructureName() {

		return "AbyStronghold";
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected boolean canSpawnStructureAtCoords(int par1, int par2)
	{
		if (!ranBiomeCheck)
		{
			Random var3 = new Random();
			var3.setSeed(worldObj.getSeed());
			double var4 = var3.nextDouble() * Math.PI * 2.0D;
			int var6 = 1;

			for (int var7 = 0; var7 < structureCoords.length; ++var7)
			{
				double var8 = (1.25D * var6 + var3.nextDouble()) * field_82671_h * var6;
				int var10 = (int)Math.round(Math.cos(var4) * var8);
				int var11 = (int)Math.round(Math.sin(var4) * var8);
				ArrayList var12 = new ArrayList();
				Collections.addAll(var12, allowedBiomeGenBases);
				BlockPos var13 = worldObj.getWorldChunkManager().findBiomePosition((var10 << 4) + 8, (var11 << 4) + 8, 112, var12, var3);

				if (var13 != null)
				{
					var10 = var13.getX() >> 4;
				var11 = var13.getY() >> 4;
				}

				structureCoords[var7] = new ChunkCoordIntPair(var10, var11);
				var4 += Math.PI * 2D * var6 / field_82672_i;

				if (var7 == field_82672_i)
				{
					var6 += 2 + var3.nextInt(5);
					field_82672_i += 1 + var3.nextInt(2);
				}
			}

			ranBiomeCheck = true;
		}

		ChunkCoordIntPair[] var14 = structureCoords;
		int var15 = var14.length;

		for (int var5 = 0; var5 < var15; ++var5)
		{
			ChunkCoordIntPair var16 = var14[var5];

			if (par1 == var16.chunkXPos && par2 == var16.chunkZPos)
				return true;
		}

		return false;
	}

	/**
	 * Returns a list of other locations at which the structure generation has been run, or null if not relevant to this
	 * structure generator.
	 */
	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected List getCoordList()
	{
		ArrayList var1 = new ArrayList();
		ChunkCoordIntPair[] var2 = structureCoords;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4)
		{
			ChunkCoordIntPair var5 = var2[var4];

			if (var5 != null)
				var1.add(var5.getCenterBlock(64));
		}

		return var1;
	}

	@Override
	protected StructureStart getStructureStart(int par1, int par2)
	{
		MapGenAbyStronghold.Start start;

		for (start = new MapGenAbyStronghold.Start(worldObj, rand, par1, par2); start.getComponents().isEmpty() || ((StructureAbyStrongholdPieces.Stairs2)start.getComponents().get(0)).strongholdPortalRoom == null; start = new MapGenAbyStronghold.Start(worldObj, rand, par1, par2))
			;

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
			components.add(stairs2);
			stairs2.buildComponent(stairs2, components, par2Random);
			List list = stairs2.field_75026_c;

			while (!list.isEmpty())
			{
				int k = par2Random.nextInt(list.size());
				StructureComponent structurecomponent = (StructureComponent)list.remove(k);
				structurecomponent.buildComponent(stairs2, components, par2Random);
			}

			updateBoundingBox();
			markAvailableHeight(par1World, par2Random, 10);
		}
	}
}
