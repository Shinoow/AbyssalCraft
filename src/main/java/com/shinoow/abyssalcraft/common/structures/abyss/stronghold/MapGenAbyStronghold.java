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
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

import com.shinoow.abyssalcraft.api.biome.ACBiomes;

public class MapGenAbyStronghold extends MapGenStructure
{
	public static ArrayList<Biome> allowedBiomes = new ArrayList<Biome>(Arrays.asList(ACBiomes.abyssal_wastelands));
	private Biome[] allowedBiomeGenBases;

	/**
	 * is spawned false and set true once the defined BiomeGenBases were compared with the present ones
	 */
	private boolean ranBiomeCheck;
	private ChunkPos[] structureCoords;
	private double field_82671_h;
	private int field_82672_i;

	public MapGenAbyStronghold()
	{
		allowedBiomeGenBases = allowedBiomes.toArray(new Biome[0]);
		structureCoords = new ChunkPos[128];
		field_82671_h = 32.0D;
		field_82672_i = 3;
	}

	@SuppressWarnings("rawtypes")
	public MapGenAbyStronghold(Map<String, String> par1Map)
	{
		this();

		for (Entry var3 : par1Map.entrySet())
			if (((String)var3.getKey()).equals("distance"))
				field_82671_h = MathHelper.parseDoubleWithDefaultAndMax((String)var3.getValue(), field_82671_h, 1.0D);
			else if (((String)var3.getKey()).equals("count"))
				structureCoords = new ChunkPos[MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), structureCoords.length, 1)];
			else if (((String)var3.getKey()).equals("spread"))
				field_82672_i = MathHelper.parseIntWithDefaultAndMax((String)var3.getValue(), field_82672_i, 1);
	}


	@Override
	public String getStructureName() {

		return "AbyStronghold";
	}

	@Override
	public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos)
	{
		if (!ranBiomeCheck)
		{
			checkBiomes();
			ranBiomeCheck = true;
		}

		BlockPos blockpos = null;
		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);
		double d0 = Double.MAX_VALUE;

		for (ChunkPos chunkcoordintpair : structureCoords)
		{
			blockpos$mutableblockpos.setPos((chunkcoordintpair.chunkXPos << 4) + 8, 32, (chunkcoordintpair.chunkZPos << 4) + 8);
			double d1 = blockpos$mutableblockpos.distanceSq(pos);

			if (blockpos == null)
			{
				blockpos = new BlockPos(blockpos$mutableblockpos);
				d0 = d1;
			}
			else if (d1 < d0)
			{
				blockpos = new BlockPos(blockpos$mutableblockpos);
				d0 = d1;
			}
		}

		return blockpos;
	}

	@Override
	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected boolean canSpawnStructureAtCoords(int par1, int par2)
	{
		if (!ranBiomeCheck)
		{
			checkBiomes();
			ranBiomeCheck = true;
		}

		ChunkPos[] var14 = structureCoords;
		int var15 = var14.length;

		for (int var5 = 0; var5 < var15; ++var5)
		{
			ChunkPos var16 = var14[var5];

			if (par1 == var16.chunkXPos && par2 == var16.chunkZPos)
				return true;
		}

		return false;
	}

	private void checkBiomes()
	{
		initializeStructureData(worldObj);
		int i = 0;

		for (StructureStart structurestart : structureMap.values())
			if (i < structureCoords.length)
				structureCoords[i++] = new ChunkPos(structurestart.getChunkPosX(), structurestart.getChunkPosZ());

		Random random = new Random();
		random.setSeed(worldObj.getSeed());
		double d1 = random.nextDouble() * Math.PI * 2.0D;
		int j = 0;
		int k = 0;
		int l = structureMap.size();

		if (l < structureCoords.length)
			for (int i1 = 0; i1 < structureCoords.length; ++i1)
			{
				double d0 = 4.0D * field_82671_h + field_82671_h * j * 6.0D + (random.nextDouble() - 0.5D) * field_82671_h * 2.5D;
				int j1 = (int)Math.round(Math.cos(d1) * d0);
				int k1 = (int)Math.round(Math.sin(d1) * d0);
				BlockPos blockpos = worldObj.getBiomeProvider().findBiomePosition((j1 << 4) + 8, (k1 << 4) + 8, 112, allowedBiomes, random);

				if (blockpos != null)
				{
					j1 = blockpos.getX() >> 4;
				k1 = blockpos.getZ() >> 4;
				}

				if (i1 >= l)
					structureCoords[i1] = new ChunkPos(j1, k1);

				d1 += Math.PI * 2D / field_82672_i;
				++k;

				if (k == field_82672_i)
				{
					++j;
					k = 0;
					field_82672_i += 2 * field_82672_i / (j + 1);
					field_82672_i = Math.min(field_82672_i, structureCoords.length - i1);
					d1 += random.nextDouble() * Math.PI * 2.0D;
				}
			}
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
		ChunkPos[] var2 = structureCoords;
		int var3 = var2.length;

		for (int var4 = 0; var4 < var3; ++var4)
		{
			ChunkPos var5 = var2[var4];

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
