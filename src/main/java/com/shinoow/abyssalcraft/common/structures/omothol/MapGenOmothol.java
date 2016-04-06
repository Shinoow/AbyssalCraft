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
package com.shinoow.abyssalcraft.common.structures.omothol;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenOmothol extends MapGenStructure
{
	public static List<BiomeGenBase> villageSpawnBiomes = Arrays.<BiomeGenBase>asList(new BiomeGenBase[] {AbyssalCraft.omothol});
	/** World terrain type, 0 for normal, 1 for flat map */
	private int terrainType;
	private int field_82665_g;
	private int field_82666_h;

	public MapGenOmothol()
	{
		field_82665_g = 32;
		field_82666_h = 8;
	}

	public MapGenOmothol(Map<String, String> p_i2093_1_)
	{
		this();

		for (Entry<String, String> entry : p_i2093_1_.entrySet())
			if (entry.getKey().equals("size"))
				terrainType = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), terrainType, 0);
			else if (entry.getKey().equals("distance"))
				field_82665_g = MathHelper.parseIntWithDefaultAndMax(entry.getValue(), field_82665_g, field_82666_h + 1);
	}

	@Override
	public String func_143025_a()
	{
		return "Omothol";
	}

	@Override
	protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
	{
		if(chunkX == 0 && chunkZ == 0) return true;

		//        int i = chunkX;
		//        int j = chunkZ;
		//
		//        if (chunkX < 0)
		//        {
		//            chunkX -= this.field_82665_g - 1;
		//        }
		//
		//        if (chunkZ < 0)
		//        {
		//            chunkZ -= this.field_82665_g - 1;
		//        }
		//
		//        int k = chunkX / this.field_82665_g;
		//        int l = chunkZ / this.field_82665_g;
		//        Random random = this.worldObj.setRandomSeed(k, l, 10387312);
		//        k = k * this.field_82665_g;
		//        l = l * this.field_82665_g;
		//        k = k + random.nextInt(this.field_82665_g - this.field_82666_h);
		//        l = l + random.nextInt(this.field_82665_g - this.field_82666_h);
		//
		//        if (i == k && j == l)
		//        {
		//            boolean flag = this.worldObj.getWorldChunkManager().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, villageSpawnBiomes);
		//
		//            if (flag)
		//            {
		//                return true;
		//            }
		//        }

		return false;
	}

	@Override
	protected StructureStart getStructureStart(int chunkX, int chunkZ)
	{
		return new MapGenOmothol.Start(worldObj, rand, chunkX, chunkZ, terrainType);
	}

	public static class Start extends StructureStart
	{
		/** well ... thats what it does */
		private boolean hasMoreThanTwoComponents;

		public Start()
		{
		}

		public Start(World worldIn, Random rand, int x, int z, int p_i2092_5_)
		{
			super(x, z);
			List<StructureOmotholPieces.PieceWeight> list = StructureOmotholPieces.getStructureVillageWeightedPieceList(rand, 100);
			StructureOmotholPieces.Start StructureOmotholPieces$start = new StructureOmotholPieces.Start(worldIn.getWorldChunkManager(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, p_i2092_5_);
			components.add(StructureOmotholPieces$start);
			StructureOmotholPieces$start.buildComponent(StructureOmotholPieces$start, components, rand);
			List<StructureComponent> list1 = StructureOmotholPieces$start.field_74930_j;
			List<StructureComponent> list2 = StructureOmotholPieces$start.field_74932_i;

			while (!list1.isEmpty() || !list2.isEmpty())
				if (list1.isEmpty())
				{
					int i = rand.nextInt(list2.size());
					StructureComponent structurecomponent = list2.remove(i);
					structurecomponent.buildComponent(StructureOmotholPieces$start, components, rand);
				}
				else
				{
					int j = rand.nextInt(list1.size());
					StructureComponent structurecomponent2 = list1.remove(j);
					structurecomponent2.buildComponent(StructureOmotholPieces$start, components, rand);
				}

			updateBoundingBox();
			int k = 0;

			for (StructureComponent structurecomponent1 : (LinkedList<StructureComponent>)components)
				if (!(structurecomponent1 instanceof StructureOmotholPieces.Road))
					++k;

			hasMoreThanTwoComponents = k > 2;
		}

		/**
		 * currently only defined for Villages, returns true if Village has more than 2 non-road components
		 */
		@Override
		public boolean isSizeableStructure()
		{
			return hasMoreThanTwoComponents;
		}

		@Override
		public void func_143022_a(NBTTagCompound tagCompound)
		{
			super.func_143022_a(tagCompound);
			tagCompound.setBoolean("Valid", hasMoreThanTwoComponents);
		}

		@Override
		public void func_143017_b(NBTTagCompound tagCompound)
		{
			super.func_143017_b(tagCompound);
			hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
		}
	}
}
