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

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;

public class StructureOmotholPieces
{
	public static void registerOmotholPieces()
	{
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.House1.class, "OmtBH");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.Field1.class, "OmtDF");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.Field2.class, "OmtF");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.Torch.class, "OmtL");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.Hall.class, "OmtPH");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.House4Garden.class, "OmtSH");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.WoodHut.class, "OmtSmH");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.Church.class, "OmtST");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.House2.class, "OmtS");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.Start.class, "OmtStart");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.Path.class, "OmtSR");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.House3.class, "OmtTRH");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.HouseBanker.class, "OmtBH");
		MapGenStructureIO.func_143031_a(StructureOmotholPieces.Well.class, "OmtW");
	}

	public static List<StructureOmotholPieces.PieceWeight> getStructureVillageWeightedPieceList(Random random, int p_75084_1_)
	{
		List<StructureOmotholPieces.PieceWeight> list = Lists.<StructureOmotholPieces.PieceWeight>newArrayList();
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.House4Garden.class, 4, MathHelper.getRandomIntegerInRange(random, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.Church.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + p_75084_1_, 1 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.House1.class, 20, MathHelper.getRandomIntegerInRange(random, 0 + p_75084_1_, 2 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.WoodHut.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + p_75084_1_, 5 + p_75084_1_ * 3)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.Hall.class, 15, MathHelper.getRandomIntegerInRange(random, 0 + p_75084_1_, 2 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.Field1.class, 3, MathHelper.getRandomIntegerInRange(random, 1 + p_75084_1_, 4 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.Field2.class, 3, MathHelper.getRandomIntegerInRange(random, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.House2.class, 15, MathHelper.getRandomIntegerInRange(random, 0, 1 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.House3.class, 8, MathHelper.getRandomIntegerInRange(random, 0 + p_75084_1_, 3 + p_75084_1_ * 2)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.HouseBanker.class, 8, MathHelper.getRandomIntegerInRange(random, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
		//        net.minecraftforge.fml.common.registry.VillagerRegistry.addExtraVillageComponents(list, random, p_75084_1_);
		Iterator<StructureOmotholPieces.PieceWeight> iterator = list.iterator();

		while (iterator.hasNext())
			if (iterator.next().villagePiecesLimit == 0)
				iterator.remove();

		return list;
	}

	private static int func_75079_a(List<StructureOmotholPieces.PieceWeight> p_75079_0_)
	{
		boolean flag = false;
		int i = 0;

		for (StructureOmotholPieces.PieceWeight structurevillagepieces$pieceweight : p_75079_0_)
		{
			if (structurevillagepieces$pieceweight.villagePiecesLimit > 0 && structurevillagepieces$pieceweight.villagePiecesSpawned < structurevillagepieces$pieceweight.villagePiecesLimit)
				flag = true;

			i += structurevillagepieces$pieceweight.villagePieceWeight;
		}

		return flag ? i : -1;
	}

	private static StructureOmotholPieces.Omothol func_176065_a(StructureOmotholPieces.Start start, StructureOmotholPieces.PieceWeight weight, List<StructureComponent> p_176065_2_, Random rand, int p_176065_4_, int p_176065_5_, int p_176065_6_, int facing, int p_176065_8_)
	{
		Class <? extends StructureOmotholPieces.Omothol > oclass = weight.villagePieceClass;
		StructureOmotholPieces.Omothol structurevillagepieces$village = null;

		if (oclass == StructureOmotholPieces.House4Garden.class)
			structurevillagepieces$village = StructureOmotholPieces.House4Garden.func_175858_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
		else if (oclass == StructureOmotholPieces.Church.class)
			structurevillagepieces$village = StructureOmotholPieces.Church.func_175854_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
		else if (oclass == StructureOmotholPieces.House1.class)
			structurevillagepieces$village = StructureOmotholPieces.House1.func_175850_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
		else if (oclass == StructureOmotholPieces.WoodHut.class)
			structurevillagepieces$village = StructureOmotholPieces.WoodHut.func_175853_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
		else if (oclass == StructureOmotholPieces.Hall.class)
			structurevillagepieces$village = StructureOmotholPieces.Hall.func_175857_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
		else if (oclass == StructureOmotholPieces.Field1.class)
			structurevillagepieces$village = StructureOmotholPieces.Field1.func_175851_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
		else if (oclass == StructureOmotholPieces.Field2.class)
			structurevillagepieces$village = StructureOmotholPieces.Field2.func_175852_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
		else if (oclass == StructureOmotholPieces.House2.class)
			structurevillagepieces$village = StructureOmotholPieces.House2.func_175855_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
		else if (oclass == StructureOmotholPieces.House3.class)
			structurevillagepieces$village = StructureOmotholPieces.House3.func_175849_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);
		else if (oclass == StructureOmotholPieces.HouseBanker.class)
			structurevillagepieces$village = StructureOmotholPieces.HouseBanker.func_175858_a(start, p_176065_2_, rand, p_176065_4_, p_176065_5_, p_176065_6_, facing, p_176065_8_);

		return structurevillagepieces$village;
	}

	private static StructureOmotholPieces.Omothol func_176067_c(StructureOmotholPieces.Start start, List<StructureComponent> p_176067_1_, Random rand, int p_176067_3_, int p_176067_4_, int p_176067_5_, int facing, int p_176067_7_)
	{
		int i = func_75079_a(start.structureVillageWeightedPieceList);

		if (i <= 0)
			return null;
		else
		{
			int j = 0;

			while (j < 5)
			{
				++j;
				int k = rand.nextInt(i);

				for (StructureOmotholPieces.PieceWeight structurevillagepieces$pieceweight : start.structureVillageWeightedPieceList)
				{
					k -= structurevillagepieces$pieceweight.villagePieceWeight;

					if (k < 0)
					{
						if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePiecesOfType(p_176067_7_) || structurevillagepieces$pieceweight == start.structVillagePieceWeight && start.structureVillageWeightedPieceList.size() > 1)
							break;

						StructureOmotholPieces.Omothol structurevillagepieces$village = func_176065_a(start, structurevillagepieces$pieceweight, p_176067_1_, rand, p_176067_3_, p_176067_4_, p_176067_5_, facing, p_176067_7_);

						if (structurevillagepieces$village != null)
						{
							++structurevillagepieces$pieceweight.villagePiecesSpawned;
							start.structVillagePieceWeight = structurevillagepieces$pieceweight;

							if (!structurevillagepieces$pieceweight.canSpawnMoreVillagePieces())
								start.structureVillageWeightedPieceList.remove(structurevillagepieces$pieceweight);

							return structurevillagepieces$village;
						}
					}
				}
			}

			StructureBoundingBox structureboundingbox = StructureOmotholPieces.Torch.func_175856_a(start, p_176067_1_, rand, p_176067_3_, p_176067_4_, p_176067_5_, facing);

			if (structureboundingbox != null)
				return new StructureOmotholPieces.Torch(start, p_176067_7_, rand, structureboundingbox, facing);
			else
				return null;
		}
	}

	private static StructureComponent func_176066_d(StructureOmotholPieces.Start start, List<StructureComponent> p_176066_1_, Random rand, int p_176066_3_, int p_176066_4_, int p_176066_5_, int facing, int p_176066_7_)
	{
		if (p_176066_7_ > 50)
			return null;
		else if (Math.abs(p_176066_3_ - start.getBoundingBox().minX) <= 4000 && Math.abs(p_176066_5_ - start.getBoundingBox().minZ) <= 4000)
		{
			StructureComponent structurecomponent = func_176067_c(start, p_176066_1_, rand, p_176066_3_, p_176066_4_, p_176066_5_, facing, p_176066_7_ + 1);

			if (structurecomponent != null)
			{
				int i = (structurecomponent.getBoundingBox().minX + structurecomponent.getBoundingBox().maxX) / 2;
				int j = (structurecomponent.getBoundingBox().minZ + structurecomponent.getBoundingBox().maxZ) / 2;
				int k = structurecomponent.getBoundingBox().maxX - structurecomponent.getBoundingBox().minX;
				int l = structurecomponent.getBoundingBox().maxZ - structurecomponent.getBoundingBox().minZ;
				int i1 = k > l ? k : l;

				if (start.getWorldChunkManager().areBiomesViable(i, j, i1 / 2 + 4, MapGenOmothol.villageSpawnBiomes))
				{
					p_176066_1_.add(structurecomponent);
					start.field_74932_i.add(structurecomponent);
					return structurecomponent;
				}
			}

			return null;
		} else
			return null;
	}

	private static StructureComponent func_176069_e(StructureOmotholPieces.Start start, List<StructureComponent> p_176069_1_, Random rand, int p_176069_3_, int p_176069_4_, int p_176069_5_, int facing, int p_176069_7_)
	{
		if (p_176069_7_ > 3 + start.terrainType)
			return null;
		else if (Math.abs(p_176069_3_ - start.getBoundingBox().minX) <= 4000 && Math.abs(p_176069_5_ - start.getBoundingBox().minZ) <= 4000)
		{
			StructureBoundingBox structureboundingbox = StructureOmotholPieces.Path.func_175848_a(start, p_176069_1_, rand, p_176069_3_, p_176069_4_, p_176069_5_, facing);

			if (structureboundingbox != null && structureboundingbox.minY > 10)
			{
				StructureComponent structurecomponent = new StructureOmotholPieces.Path(start, p_176069_7_, rand, structureboundingbox, facing);
				int i = (structurecomponent.getBoundingBox().minX + structurecomponent.getBoundingBox().maxX) / 2;
				int j = (structurecomponent.getBoundingBox().minZ + structurecomponent.getBoundingBox().maxZ) / 2;
				int k = structurecomponent.getBoundingBox().maxX - structurecomponent.getBoundingBox().minX;
				int l = structurecomponent.getBoundingBox().maxZ - structurecomponent.getBoundingBox().minZ;
				int i1 = k > l ? k : l;

				if (start.getWorldChunkManager().areBiomesViable(i, j, i1 / 2 + 4, MapGenOmothol.villageSpawnBiomes))
				{
					p_176069_1_.add(structurecomponent);
					start.field_74930_j.add(structurecomponent);
					return structurecomponent;
				}
			}

			return null;
		} else
			return null;
	}

	public static class Church extends StructureOmotholPieces.Omothol
	{
		public Church()
		{
		}

		public Church(StructureOmotholPieces.Start start, int p_i45564_2_, Random rand, StructureBoundingBox p_i45564_4_, int facing)
		{
			super(start, p_i45564_2_);
			coordBaseMode = facing;
			boundingBox = p_i45564_4_;
		}

		public static StructureOmotholPieces.Church func_175854_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175854_1_, Random rand, int p_175854_3_, int p_175854_4_, int p_175854_5_, int facing, int p_175854_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175854_3_, p_175854_4_, p_175854_5_, 0, 0, 0, 5, 12, 9, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175854_1_, structureboundingbox) == null ? new StructureOmotholPieces.Church(start, p_175854_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 12 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 11, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 11, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 2, 11, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 2, 11, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 1, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 1, 7, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 2, 1, 7, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 1, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 1, 7, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 3), 1, 1, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 3), 2, 1, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 3), 3, 1, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 1), 1, 2, 7, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 0), 3, 2, 7, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 3, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 3, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 6, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 7, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 6, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 7, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 6, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 7, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 6, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 7, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 3, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 3, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 3, 8, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 2, 4, 7, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 1, 4, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 3, 4, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 2, 4, 5, structureBoundingBoxIn);
			int i = getMetadataWithOffset(Blocks.ladder, 4);

			for (int j = 1; j <= 9; ++j)
				placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, j, 3, structureBoundingBoxIn);

			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 2, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 2, 2, 0, structureBoundingBoxIn);
			placeDoorAtCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, getMetadataWithOffset(Blocks.wooden_door, 1));

			if (getBlockAtCurrentPosition(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.air && getBlockAtCurrentPosition(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.air)
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 3), 2, 0, -1, structureBoundingBoxIn);

			for (int l = 0; l < 9; ++l)
				for (int k = 0; k < 5; ++k)
				{
					clearCurrentPositionBlocksUpwards(worldIn, k, 12, l, structureBoundingBoxIn);
					func_151554_b(worldIn, AbyssalCraft.ethaxiumbrick, 0, k, -1, l, structureBoundingBoxIn);
				}

			spawnRemnants(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
			return true;
		}

		@Override
		protected int func_180779_c(int p_180779_1_, int p_180779_2_)
		{
			return 2;
		}
	}

	public static class Field1 extends StructureOmotholPieces.Omothol
	{
		/** First crop type for this field. */
		private Block cropTypeA;
		/** Second crop type for this field. */
		private Block cropTypeB;
		/** Third crop type for this field. */
		private Block cropTypeC;
		/** Fourth crop type for this field. */
		private Block cropTypeD;

		public Field1()
		{
		}

		public Field1(StructureOmotholPieces.Start start, int p_i45570_2_, Random rand, StructureBoundingBox p_i45570_4_, int facing)
		{
			super(start, p_i45570_2_);
			coordBaseMode = facing;
			boundingBox = p_i45570_4_;
			cropTypeA = func_151559_a(rand);
			cropTypeB = func_151559_a(rand);
			cropTypeC = func_151559_a(rand);
			cropTypeD = func_151559_a(rand);
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void func_143012_a(NBTTagCompound tagCompound)
		{
			super.func_143012_a(tagCompound);
			tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(cropTypeA));
			tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(cropTypeB));
			tagCompound.setInteger("CC", Block.blockRegistry.getIDForObject(cropTypeC));
			tagCompound.setInteger("CD", Block.blockRegistry.getIDForObject(cropTypeD));
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void func_143011_b(NBTTagCompound tagCompound)
		{
			super.func_143011_b(tagCompound);
			cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
			cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
			cropTypeC = Block.getBlockById(tagCompound.getInteger("CC"));
			cropTypeD = Block.getBlockById(tagCompound.getInteger("CD"));
		}

		private Block func_151559_a(Random rand)
		{
			switch (rand.nextInt(5))
			{
			case 0:
				return Blocks.carrots;
			case 1:
				return Blocks.potatoes;
			default:
				return Blocks.wheat;
			}
		}

		public static StructureOmotholPieces.Field1 func_175851_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, int facing, int p_175851_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175851_3_, p_175851_4_, p_175851_5_, 0, 0, 0, 13, 4, 9, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175851_1_, structureboundingbox) == null ? new StructureOmotholPieces.Field1(start, p_175851_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 4 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland, Blocks.farmland, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland, Blocks.farmland, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.farmland, Blocks.farmland, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.farmland, Blocks.farmland, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water, Blocks.water, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.water, Blocks.water, false);

			for (int i = 1; i <= 7; ++i)
			{
				placeBlockAtCurrentPosition(worldIn, cropTypeA, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 1, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeA, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 2, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeB, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 4, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeB, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 5, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeC, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 7, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeC, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 8, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeD, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 10, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeD, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 11, 1, i, structureBoundingBoxIn);
			}

			for (int k = 0; k < 9; ++k)
				for (int j = 0; j < 13; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 4, k, structureBoundingBoxIn);
					func_151554_b(worldIn, Blocks.dirt, 0, j, -1, k, structureBoundingBoxIn);
				}

			return true;
		}
	}

	public static class Field2 extends StructureOmotholPieces.Omothol
	{
		/** First crop type for this field. */
		private Block cropTypeA;
		/** Second crop type for this field. */
		private Block cropTypeB;

		public Field2()
		{
		}

		public Field2(StructureOmotholPieces.Start start, int p_i45569_2_, Random rand, StructureBoundingBox p_i45569_4_, int facing)
		{
			super(start, p_i45569_2_);
			coordBaseMode = facing;
			boundingBox = p_i45569_4_;
			cropTypeA = func_151560_a(rand);
			cropTypeB = func_151560_a(rand);
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void func_143012_a(NBTTagCompound tagCompound)
		{
			super.func_143012_a(tagCompound);
			tagCompound.setInteger("CA", Block.blockRegistry.getIDForObject(cropTypeA));
			tagCompound.setInteger("CB", Block.blockRegistry.getIDForObject(cropTypeB));
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void func_143011_b(NBTTagCompound tagCompound)
		{
			super.func_143011_b(tagCompound);
			cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
			cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
		}

		private Block func_151560_a(Random rand)
		{
			switch (rand.nextInt(5))
			{
			case 0:
				return Blocks.carrots;
			case 1:
				return Blocks.potatoes;
			default:
				return Blocks.wheat;
			}
		}

		public static StructureOmotholPieces.Field2 func_175852_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175852_1_, Random rand, int p_175852_3_, int p_175852_4_, int p_175852_5_, int facing, int p_175852_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175852_3_, p_175852_4_, p_175852_5_, 0, 0, 0, 7, 4, 9, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175852_1_, structureboundingbox) == null ? new StructureOmotholPieces.Field2(start, p_175852_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 4 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 6, 4, 8, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.farmland, Blocks.farmland, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.farmland, Blocks.farmland, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 0, 0, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 5, 0, 8, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.water, Blocks.water, false);

			for (int i = 1; i <= 7; ++i)
			{
				placeBlockAtCurrentPosition(worldIn, cropTypeA, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 1, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeA, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 2, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeB, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 4, 1, i, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, cropTypeB, MathHelper.getRandomIntegerInRange(randomIn, 2, 7), 5, 1, i, structureBoundingBoxIn);
			}

			for (int k = 0; k < 9; ++k)
				for (int j = 0; j < 7; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 4, k, structureBoundingBoxIn);
					func_151554_b(worldIn, Blocks.dirt, 0, j, -1, k, structureBoundingBoxIn);
				}

			return true;
		}
	}

	public static class Hall extends StructureOmotholPieces.Omothol
	{
		public Hall()
		{
		}

		public Hall(StructureOmotholPieces.Start start, int p_i45567_2_, Random rand, StructureBoundingBox p_i45567_4_, int facing)
		{
			super(start, p_i45567_2_);
			coordBaseMode = facing;
			boundingBox = p_i45567_4_;
		}

		public static StructureOmotholPieces.Hall func_175857_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175857_1_, Random rand, int p_175857_3_, int p_175857_4_, int p_175857_5_, int facing, int p_175857_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175857_3_, p_175857_4_, p_175857_5_, 0, 0, 0, 9, 7, 11, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175857_1_, structureboundingbox) == null ? new StructureOmotholPieces.Hall(start, p_175857_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 7 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 8, 0, 10, Blocks.dirt, Blocks.dirt, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 6, 0, 6, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 1, 10, AbyssalCraft.ethaxiumfence, AbyssalCraft.ethaxiumfence, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 6, 8, 1, 10, AbyssalCraft.ethaxiumfence, AbyssalCraft.ethaxiumfence, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 10, 7, 1, 10, AbyssalCraft.ethaxiumfence, AbyssalCraft.ethaxiumfence, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 1, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 1, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 3, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 8, 4, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 4, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 4, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 8, 4, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 8, 4, 3, structureBoundingBoxIn);
			int i = getMetadataWithOffset(Blocks.oak_stairs, 3);
			int j = getMetadataWithOffset(Blocks.oak_stairs, 2);

			for (int k = -1; k <= 2; ++k)
				for (int l = 0; l <= 8; ++l)
				{
					placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, i, l, 4 + k, k, structureBoundingBoxIn);
					placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, j, l, 4 + k, 5 - k, structureBoundingBoxIn);
				}

			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 0, 2, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 0, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 8, 2, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 8, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 2, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 3, 2, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 5, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 6, 2, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.fence, 0, 2, 1, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.wooden_pressure_plate, 0, 2, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 3), 2, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 1), 1, 1, 3, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 1, 7, 0, 3, Blocks.double_stone_slab, Blocks.double_stone_slab, false);
			placeBlockAtCurrentPosition(worldIn, Blocks.double_stone_slab, 0, 6, 1, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.double_stone_slab, 0, 6, 1, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 2, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 2, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 2, 3, 1, structureBoundingBoxIn);
			placeDoorAtCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, getMetadataWithOffset(Blocks.wooden_door, 1));

			if (getBlockAtCurrentPosition(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.air && getBlockAtCurrentPosition(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.air)
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 3), 2, 0, -1, structureBoundingBoxIn);

			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 6, 1, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 6, 2, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 6, 3, 4, structureBoundingBoxIn);
			placeDoorAtCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 6, 1, 5, getMetadataWithOffset(Blocks.wooden_door, 1));

			for (int i1 = 0; i1 < 5; ++i1)
				for (int j1 = 0; j1 < 9; ++j1)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j1, 7, i1, structureBoundingBoxIn);
					func_151554_b(worldIn, AbyssalCraft.ethaxiumbrick, 0, j1, -1, i1, structureBoundingBoxIn);
				}

			spawnRemnants(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
			return true;
		}

		@Override
		protected int func_180779_c(int p_180779_1_, int p_180779_2_)
		{

			return p_180779_1_ == 0 ? 4 : super.func_180779_c(p_180779_1_, p_180779_2_);
		}
	}

	public static class House1 extends StructureOmotholPieces.Omothol
	{
		public House1()
		{
		}

		public House1(StructureOmotholPieces.Start start, int p_i45571_2_, Random rand, StructureBoundingBox p_i45571_4_, int facing)
		{
			super(start, p_i45571_2_);
			coordBaseMode = facing;
			boundingBox = p_i45571_4_;
		}

		public static StructureOmotholPieces.House1 func_175850_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175850_1_, Random rand, int p_175850_3_, int p_175850_4_, int p_175850_5_, int facing, int p_175850_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175850_3_, p_175850_4_, p_175850_5_, 0, 0, 0, 9, 9, 6, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175850_1_, structureboundingbox) == null ? new StructureOmotholPieces.House1(start, p_175850_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 9 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			int i = getMetadataWithOffset(Blocks.oak_stairs, 3);
			int j = getMetadataWithOffset(Blocks.oak_stairs, 2);

			for (int k = -1; k <= 2; ++k)
				for (int l = 0; l <= 8; ++l)
				{
					placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, i, l, 6 + k, k, structureBoundingBoxIn);
					placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, j, l, 6 + k, 5 - k, structureBoundingBoxIn);
				}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 5, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 6, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 5, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 6, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 3, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 3, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 3, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 3, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 2, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 3, 2, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 5, 2, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 6, 2, 5, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.bookshelf, Blocks.bookshelf, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 7, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 0), 7, 1, 3, structureBoundingBoxIn);
			int j1 = getMetadataWithOffset(Blocks.oak_stairs, 3);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, j1, 6, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, j1, 5, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, j1, 4, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, j1, 3, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.fence, 0, 6, 1, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.wooden_pressure_plate, 0, 6, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.fence, 0, 4, 1, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.wooden_pressure_plate, 0, 4, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.crafting_table, 0, 7, 1, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 1, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 1, 2, 0, structureBoundingBoxIn);
			placeDoorAtCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, getMetadataWithOffset(Blocks.wooden_door, 1));

			if (getBlockAtCurrentPosition(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.air && getBlockAtCurrentPosition(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.air)
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 3), 1, 0, -1, structureBoundingBoxIn);

			for (int k1 = 0; k1 < 6; ++k1)
				for (int i1 = 0; i1 < 9; ++i1)
				{
					clearCurrentPositionBlocksUpwards(worldIn, i1, 9, k1, structureBoundingBoxIn);
					func_151554_b(worldIn, AbyssalCraft.ethaxiumbrick, 0, i1, -1, k1, structureBoundingBoxIn);
				}

			spawnRemnants(worldIn, structureBoundingBoxIn, 2, 1, 2, 1);
			return true;
		}

		@Override
		protected int func_180779_c(int p_180779_1_, int p_180779_2_)
		{
			return 1;
		}
	}

	public static class House2 extends StructureOmotholPieces.Omothol
	{
		public static final WeightedRandomChestContent[] villageBlacksmithChestContents = new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 3), new WeightedRandomChestContent(Items.iron_ingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.gold_ingot, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.apple, 0, 1, 3, 15), new WeightedRandomChestContent(Items.iron_pickaxe, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_sword, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_chestplate, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_helmet, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_leggings, 0, 1, 1, 5), new WeightedRandomChestContent(Items.iron_boots, 0, 1, 1, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.obsidian), 0, 3, 7, 5), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.sapling), 0, 3, 7, 5), new WeightedRandomChestContent(Items.saddle, 0, 1, 1, 3), new WeightedRandomChestContent(Items.iron_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.golden_horse_armor, 0, 1, 1, 1), new WeightedRandomChestContent(Items.diamond_horse_armor, 0, 1, 1, 1)};
		private boolean hasMadeChest;

		public House2()
		{
		}

		public House2(StructureOmotholPieces.Start start, int p_i45563_2_, Random rand, StructureBoundingBox p_i45563_4_, int facing)
		{
			super(start, p_i45563_2_);
			coordBaseMode = facing;
			boundingBox = p_i45563_4_;
		}

		public static StructureOmotholPieces.House2 func_175855_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175855_1_, Random rand, int p_175855_3_, int p_175855_4_, int p_175855_5_, int facing, int p_175855_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175855_1_, structureboundingbox) == null ? new StructureOmotholPieces.House2(start, p_175855_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void func_143012_a(NBTTagCompound tagCompound)
		{
			super.func_143012_a(tagCompound);
			tagCompound.setBoolean("Chest", hasMadeChest);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void func_143011_b(NBTTagCompound tagCompound)
		{
			super.func_143011_b(tagCompound);
			hasMadeChest = tagCompound.getBoolean("Chest");
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 6 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 0, 6, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 9, 4, 6, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.stone_slab, Blocks.stone_slab, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 4, 0, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 4, 0, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 4, 6, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 3, 1, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 3, 2, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 5, 3, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 6, 5, 3, 6, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 5, 3, 0, AbyssalCraft.ethaxiumfence, AbyssalCraft.ethaxiumfence, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 9, 3, 0, AbyssalCraft.ethaxiumfence, AbyssalCraft.ethaxiumfence, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 9, 4, 6, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, Blocks.flowing_lava, 0, 7, 1, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.flowing_lava, 0, 8, 1, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.iron_bars, 0, 9, 2, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.iron_bars, 0, 9, 2, 4, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.air, Blocks.air, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 6, 1, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.furnace, 0, 6, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.furnace, 0, 6, 3, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.double_stone_slab, 0, 8, 1, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 2, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 2, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.fence, 0, 2, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.wooden_pressure_plate, 0, 2, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 1, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 3), 2, 1, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 1), 1, 1, 4, structureBoundingBoxIn);

			if (!hasMadeChest){

				int i = getYWithOffset(1);
				int j = getXWithOffset(5, 5);
				int k = getZWithOffset(5, 5);

				if(structureBoundingBoxIn.isVecInside(j, i, k)){
					hasMadeChest = true;
					generateStructureChestContents(worldIn, structureBoundingBoxIn, randomIn, 5, 1, 5, net.minecraftforge.common.ChestGenHooks.getItems(net.minecraftforge.common.ChestGenHooks.VILLAGE_BLACKSMITH, randomIn), net.minecraftforge.common.ChestGenHooks.getCount(net.minecraftforge.common.ChestGenHooks.VILLAGE_BLACKSMITH, randomIn));
				}
			}

			for (int i = 6; i <= 8; ++i)
				if (getBlockAtCurrentPosition(worldIn, i, 0, -1, structureBoundingBoxIn).getMaterial() == Material.air && getBlockAtCurrentPosition(worldIn, i, -1, -1, structureBoundingBoxIn).getMaterial() != Material.air)
					placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 3), i, 0, -1, structureBoundingBoxIn);

			for (int k = 0; k < 7; ++k)
				for (int j = 0; j < 10; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
					func_151554_b(worldIn, AbyssalCraft.ethaxiumbrick, 0, j, -1, k, structureBoundingBoxIn);
				}

			spawnRemnants(worldIn, structureBoundingBoxIn, 7, 1, 1, 1);
			return true;
		}

		@Override
		protected int func_180779_c(int p_180779_1_, int p_180779_2_)
		{
			Random rand = new Random();
			return rand.nextInt(5) == 0 ? 6 : 3;
		}
	}

	public static class House3 extends StructureOmotholPieces.Omothol
	{
		public House3()
		{
		}

		public House3(StructureOmotholPieces.Start start, int p_i45561_2_, Random rand, StructureBoundingBox p_i45561_4_, int facing)
		{
			super(start, p_i45561_2_);
			coordBaseMode = facing;
			boundingBox = p_i45561_4_;
		}

		public static StructureOmotholPieces.House3 func_175849_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175849_1_, Random rand, int p_175849_3_, int p_175849_4_, int p_175849_5_, int facing, int p_175849_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175849_3_, p_175849_4_, p_175849_5_, 0, 0, 0, 9, 7, 12, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175849_1_, structureboundingbox) == null ? new StructureOmotholPieces.House3(start, p_175849_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 7 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 4, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 4, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 8, 4, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 8, 4, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 8, 4, 4, structureBoundingBoxIn);

			for (int k = -1; k <= 2; ++k)
				for (int l = 0; l <= 8; ++l)
				{
					placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 3), l, 4 + k, k, structureBoundingBoxIn);

					if ((k > -1 || l <= 1) && (k > 0 || l <= 3) && (k > 1 || l <= 4 || l >= 6))
						placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 2), l, 4 + k, 5 - k, structureBoundingBoxIn);
				}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);

			for (int l1 = 4; l1 >= 1; --l1)
			{
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, l1, 2 + l1, 7 - l1, structureBoundingBoxIn);

				for (int i1 = 8 - l1; i1 <= 10; ++i1)
					placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 0), l1, 2 + l1, i1, structureBoundingBoxIn);
			}

			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 6, 6, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 7, 5, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 1), 6, 6, 4, structureBoundingBoxIn);

			for (int j2 = 6; j2 <= 8; ++j2)
				for (int j1 = 5; j1 <= 10; ++j1)
					placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 1), j2, 12 - j2, j1, structureBoundingBoxIn);

			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 0, 2, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 0, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 4, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 5, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 6, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 8, 2, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 8, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 8, 2, 5, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 8, 2, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 2, 7, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 8, 2, 8, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 8, 2, 9, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 2, 2, 6, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 2, 7, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 2, 8, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 2, 2, 9, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 4, 4, 10, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 5, 4, 10, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 6, 4, 10, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 5, 5, 10, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 2, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 2, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 2, 3, 1, structureBoundingBoxIn);
			placeDoorAtCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, getMetadataWithOffset(Blocks.wooden_door, 1));
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.air, Blocks.air, false);

			if (getBlockAtCurrentPosition(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.air && getBlockAtCurrentPosition(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.air)
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 3), 2, 0, -1, structureBoundingBoxIn);

			for (int k2 = 0; k2 < 5; ++k2)
				for (int i3 = 0; i3 < 9; ++i3)
				{
					clearCurrentPositionBlocksUpwards(worldIn, i3, 7, k2, structureBoundingBoxIn);
					func_151554_b(worldIn, AbyssalCraft.ethaxiumbrick, 0, i3, -1, k2, structureBoundingBoxIn);
				}

			for (int l2 = 5; l2 < 11; ++l2)
				for (int j3 = 2; j3 < 9; ++j3)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j3, 7, l2, structureBoundingBoxIn);
					func_151554_b(worldIn, AbyssalCraft.ethaxiumbrick, 0, j3, -1, l2, structureBoundingBoxIn);
				}

			spawnRemnants(worldIn, structureBoundingBoxIn, 4, 1, 2, 2);
			return true;
		}
	}

	public static class House4Garden extends StructureOmotholPieces.Omothol
	{
		private boolean isRoofAccessible;

		public House4Garden()
		{
		}

		public House4Garden(StructureOmotholPieces.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, int facing)
		{
			super(start, p_i45566_2_);
			coordBaseMode = facing;
			boundingBox = p_i45566_4_;
			isRoofAccessible = rand.nextBoolean();
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void func_143012_a(NBTTagCompound tagCompound)
		{
			super.func_143012_a(tagCompound);
			tagCompound.setBoolean("Terrace", isRoofAccessible);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void func_143011_b(NBTTagCompound tagCompound)
		{
			super.func_143011_b(tagCompound);
			isRoofAccessible = tagCompound.getBoolean("Terrace");
		}

		public static StructureOmotholPieces.House4Garden func_175858_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, int facing, int p_175858_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, facing);
			return StructureComponent.findIntersecting(p_175858_1_, structureboundingbox) != null ? null : new StructureOmotholPieces.House4Garden(start, p_175858_7_, rand, structureboundingbox, facing);
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 6 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 3, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 3, 4, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 2, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 1, 0, structureBoundingBoxIn);

			if (getBlockAtCurrentPosition(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.air && getBlockAtCurrentPosition(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.air)
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 3), 2, 0, -1, structureBoundingBoxIn);

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.air, Blocks.air, false);

			if (isRoofAccessible)
			{
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 5, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 1, 5, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 2, 5, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 3, 5, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 5, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 5, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 1, 5, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 2, 5, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 3, 5, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 5, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 5, 1, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 5, 2, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 5, 3, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 5, 1, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 5, 2, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 5, 3, structureBoundingBoxIn);
			}

			if (isRoofAccessible)
			{
				int i = getMetadataWithOffset(Blocks.ladder, 3);
				placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 1, 3, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 2, 3, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 3, 3, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 4, 3, structureBoundingBoxIn);
			}

			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 2, 3, 1, structureBoundingBoxIn);

			for (int k = 0; k < 5; ++k)
				for (int j = 0; j < 5; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
					func_151554_b(worldIn, AbyssalCraft.ethaxiumbrick, 0, j, -1, k, structureBoundingBoxIn);
				}

			spawnRemnants(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
			return true;
		}
	}

	public static class HouseBanker extends StructureOmotholPieces.Omothol
	{
		private boolean isRoofAccessible;

		public HouseBanker()
		{
		}

		public HouseBanker(StructureOmotholPieces.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, int facing)
		{
			super(start, p_i45566_2_);
			coordBaseMode = facing;
			boundingBox = p_i45566_4_;
			isRoofAccessible = rand.nextBoolean();
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void func_143012_a(NBTTagCompound tagCompound)
		{
			super.func_143012_a(tagCompound);
			tagCompound.setBoolean("Terrace", isRoofAccessible);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void func_143011_b(NBTTagCompound tagCompound)
		{
			super.func_143011_b(tagCompound);
			isRoofAccessible = tagCompound.getBoolean("Terrace");
		}

		public static StructureOmotholPieces.HouseBanker func_175858_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, int facing, int p_175858_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175858_3_, p_175858_4_, p_175858_5_, 0, 0, 0, 5, 6, 5, facing);
			return StructureComponent.findIntersecting(p_175858_1_, structureboundingbox) != null ? null : new StructureOmotholPieces.HouseBanker(start, p_175858_7_, rand, structureboundingbox, facing);
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 6 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 3, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 1, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 3, 4, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 2, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 2, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 1, 0, structureBoundingBoxIn);

			if (getBlockAtCurrentPosition(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.air && getBlockAtCurrentPosition(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.air)
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.stone_stairs, 3), 2, 0, -1, structureBoundingBoxIn);

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.air, Blocks.air, false);

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 4, 5, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 9, 1, 3, 9, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 6, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 7, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 8, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 6, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 7, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 8, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 6, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 7, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 0, 8, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 6, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 7, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 4, 8, 4, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 0, 8, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, 0, 3, 8, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 1, 4, 8, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, 4, 3, 8, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 7, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 7, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 4, 7, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 2, 7, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 6, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 7, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 1, 8, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 2, 8, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 8, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 7, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumbrick, 0, 3, 6, 0, structureBoundingBoxIn);

			if (isRoofAccessible)
			{
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 10, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 1, 10, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 2, 10, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 3, 10, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 10, 0, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 10, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 1, 10, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 2, 10, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 3, 10, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 10, 4, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 10, 1, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 10, 2, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 4, 10, 3, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 10, 1, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 10, 2, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 0, 10, 3, structureBoundingBoxIn);
			}

			int i = getMetadataWithOffset(Blocks.ladder, 3);
			placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 1, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 2, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 3, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 4, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 5, 3, structureBoundingBoxIn);

			if (isRoofAccessible)
			{
				placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 6, 3, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 7, 3, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 8, 3, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, Blocks.ladder, i, 3, 9, 3, structureBoundingBoxIn);
			}

			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 2, 3, 1, structureBoundingBoxIn);

			for (int k = 0; k < 5; ++k)
				for (int j = 0; j < 5; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 11, k, structureBoundingBoxIn);
					func_151554_b(worldIn, AbyssalCraft.ethaxiumbrick, 0, j, -1, k, structureBoundingBoxIn);
				}

			spawnRemnants(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
			return true;
		}

		@Override
		protected int func_180779_c(int p_180779_1_, int p_180779_2_)
		{
			return 5;
		}
	}

	public static class Path extends StructureOmotholPieces.Road
	{
		private int length;

		public Path()
		{
		}

		public Path(StructureOmotholPieces.Start start, int p_i45562_2_, Random rand, StructureBoundingBox p_i45562_4_, int facing)
		{
			super(start, p_i45562_2_);
			coordBaseMode = facing;
			boundingBox = p_i45562_4_;
			length = Math.max(p_i45562_4_.getXSize(), p_i45562_4_.getZSize());
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void func_143012_a(NBTTagCompound tagCompound)
		{
			super.func_143012_a(tagCompound);
			tagCompound.setInteger("Length", length);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void func_143011_b(NBTTagCompound tagCompound)
		{
			super.func_143011_b(tagCompound);
			length = tagCompound.getInteger("Length");
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		public void buildComponent(StructureComponent componentIn, List listIn, Random rand)
		{
			boolean flag = false;

			for (int i = rand.nextInt(5); i < length - 8; i += 2 + rand.nextInt(5))
			{
				StructureComponent structurecomponent = getNextComponentNN((StructureOmotholPieces.Start)componentIn, listIn, rand, 0, i);

				if (structurecomponent != null)
				{
					i += Math.max(structurecomponent.getBoundingBox().getXSize(), structurecomponent.getBoundingBox().getZSize());
					flag = true;
				}
			}

			for (int j = rand.nextInt(5); j < length - 8; j += 2 + rand.nextInt(5))
			{
				StructureComponent structurecomponent1 = getNextComponentPP((StructureOmotholPieces.Start)componentIn, listIn, rand, 0, j);

				if (structurecomponent1 != null)
				{
					j += Math.max(structurecomponent1.getBoundingBox().getXSize(), structurecomponent1.getBoundingBox().getZSize());
					flag = true;
				}
			}

			if (flag && rand.nextInt(3) > 0)
				switch (coordBaseMode)
				{
				case 0:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.maxZ - 2, 1, getComponentType());
					break;
				case 1:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, 2, getComponentType());
					break;
				case 2:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, 1, getComponentType());
					break;
				case 3:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX - 2, boundingBox.minY, boundingBox.minZ - 1, 2, getComponentType());
				}

			if (flag && rand.nextInt(3) > 0)
				switch (coordBaseMode)
				{
				case 0:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.maxZ - 2, 3, getComponentType());
					break;
				case 1:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, 0, getComponentType());
					break;
				case 2:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, 3, getComponentType());
					break;
				case 3:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX - 2, boundingBox.minY, boundingBox.maxZ + 1, 0, getComponentType());
				}
		}

		public static StructureBoundingBox func_175848_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, int facing)
		{
			for (int i = 7 * MathHelper.getRandomIntegerInRange(rand, 3, 5); i >= 7; i -= 7)
			{
				StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175848_3_, p_175848_4_, p_175848_5_, 0, 0, 0, 3, 3, i, facing);

				if (StructureComponent.findIntersecting(p_175848_1_, structureboundingbox) == null)
					return structureboundingbox;
			}

			return null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{

			for (int i = boundingBox.minX; i <= boundingBox.maxX; ++i)
				for (int j = boundingBox.minZ; j <= boundingBox.maxZ; ++j)
					if (structureBoundingBoxIn.isVecInside(i, 64, j))
					{
						int x = i;
						int z = j;
						switch(coordBaseMode){
						case 0:
							z++;
							break;
						case 1:
							x--;
							break;
						case 2:
							z--;
							break;
						case 3:
							x++;
							break;
						}
						int k = worldIn.getTopSolidOrLiquidBlock(i, j) -1;
						int k1 = worldIn.getTopSolidOrLiquidBlock(x, z) -1;
						if(k1 > k && k <= 40)
							k = k1;

						if(k <= 45)
							if(k <= 49)
								k = 49;

						worldIn.setBlock(i, k, j, AbyssalCraft.ethaxium, 0, 2);
					}

			return true;
		}
	}

	public static class PieceWeight
	{
		public Class <? extends StructureOmotholPieces.Omothol > villagePieceClass;
		public final int villagePieceWeight;
		public int villagePiecesSpawned;
		public int villagePiecesLimit;

		public PieceWeight(Class <? extends StructureOmotholPieces.Omothol > p_i2098_1_, int p_i2098_2_, int p_i2098_3_)
		{
			villagePieceClass = p_i2098_1_;
			villagePieceWeight = p_i2098_2_;
			villagePiecesLimit = p_i2098_3_;
		}

		public boolean canSpawnMoreVillagePiecesOfType(int p_75085_1_)
		{
			return villagePiecesLimit == 0 || villagePiecesSpawned < villagePiecesLimit;
		}

		public boolean canSpawnMoreVillagePieces()
		{
			return villagePiecesLimit == 0 || villagePiecesSpawned < villagePiecesLimit;
		}
	}

	public abstract static class Road extends StructureOmotholPieces.Omothol
	{
		public Road()
		{
		}

		protected Road(StructureOmotholPieces.Start start, int type)
		{
			super(start, type);
		}
	}

	public static class Start extends StructureOmotholPieces.Well
	{
		public WorldChunkManager worldChunkMngr;
		/** Boolean that determines if the village is in a desert or not. */
		public boolean inDesert;
		/** World terrain type, 0 for normal, 1 for flap map */
		public int terrainType;
		public StructureOmotholPieces.PieceWeight structVillagePieceWeight;
		public List<StructureOmotholPieces.PieceWeight> structureVillageWeightedPieceList;
		public List<StructureComponent> field_74932_i = Lists.<StructureComponent>newArrayList();
		public List<StructureComponent> field_74930_j = Lists.<StructureComponent>newArrayList();
		public BiomeGenBase biome;

		public Start()
		{
		}

		public Start(WorldChunkManager chunkManagerIn, int p_i2104_2_, Random rand, int p_i2104_4_, int p_i2104_5_, List<StructureOmotholPieces.PieceWeight> p_i2104_6_, int p_i2104_7_)
		{
			super((StructureOmotholPieces.Start)null, 0, rand, p_i2104_4_, p_i2104_5_);
			worldChunkMngr = chunkManagerIn;
			structureVillageWeightedPieceList = p_i2104_6_;
			terrainType = p_i2104_7_;
			BiomeGenBase biomegenbase = chunkManagerIn.getBiomeGenAt(p_i2104_4_, p_i2104_5_);
			inDesert = biomegenbase == BiomeGenBase.desert || biomegenbase == BiomeGenBase.desertHills;
			biome = biomegenbase;
		}

		public WorldChunkManager getWorldChunkManager()
		{
			return worldChunkMngr;
		}
	}

	public static class Torch extends StructureOmotholPieces.Omothol
	{
		public Torch()
		{
		}

		public Torch(StructureOmotholPieces.Start start, int p_i45568_2_, Random rand, StructureBoundingBox p_i45568_4_, int facing)
		{
			super(start, p_i45568_2_);
			coordBaseMode = facing;
			boundingBox = p_i45568_4_;
		}

		public static StructureBoundingBox func_175856_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175856_1_, Random rand, int p_175856_3_, int p_175856_4_, int p_175856_5_, int facing)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175856_3_, p_175856_4_, p_175856_5_, 0, 0, 0, 3, 4, 2, facing);
			return StructureComponent.findIntersecting(p_175856_1_, structureboundingbox) != null ? null : structureboundingbox;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 4 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 3, 1, Blocks.air, Blocks.air, false);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 1, 0, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 1, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumfence, 0, 1, 2, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.wool, 15, 1, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 0, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 1, 3, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 2, 3, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.torch, 0, 1, 3, -1, structureBoundingBoxIn);
			return true;
		}
	}

	public abstract static class Omothol extends StructureComponent
	{
		protected int field_143015_k = -1;
		/** The number of villagers that have been spawned in this component. */
		private int remnantsSpawned;
		private boolean isDesertVillage;
		public Omothol()
		{
		}

		protected Omothol(StructureOmotholPieces.Start start, int type)
		{
			super(type);

			if (start != null)
				isDesertVillage = start.inDesert;
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void func_143012_a(NBTTagCompound tagCompound)
		{
			tagCompound.setInteger("HPos", field_143015_k);
			tagCompound.setInteger("VCount", remnantsSpawned);
			tagCompound.setBoolean("Desert", isDesertVillage);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void func_143011_b(NBTTagCompound tagCompound)
		{
			field_143015_k = tagCompound.getInteger("HPos");
			remnantsSpawned = tagCompound.getInteger("VCount");
			isDesertVillage = tagCompound.getBoolean("Desert");
		}

		/**
		 * Gets the next village component, with the bounding box shifted -1 in the X and Z direction.
		 */
		protected StructureComponent getNextComponentNN(StructureOmotholPieces.Start start, List<StructureComponent> p_74891_2_, Random rand, int p_74891_4_, int p_74891_5_)
		{
			switch (coordBaseMode)
			{
			case 0:
				return StructureOmotholPieces.func_176066_d(start, p_74891_2_, rand, boundingBox.minX - 1, boundingBox.minY + p_74891_4_, boundingBox.minZ + p_74891_5_, 1, getComponentType());
			case 1:
				return StructureOmotholPieces.func_176066_d(start, p_74891_2_, rand, boundingBox.minX + p_74891_5_, boundingBox.minY + p_74891_4_, boundingBox.minZ - 1, 2, getComponentType());
			case 2:
				return StructureOmotholPieces.func_176066_d(start, p_74891_2_, rand, boundingBox.minX - 1, boundingBox.minY + p_74891_4_, boundingBox.minZ + p_74891_5_, 1, getComponentType());
			case 3:
				return StructureOmotholPieces.func_176066_d(start, p_74891_2_, rand, boundingBox.minX + p_74891_5_, boundingBox.minY + p_74891_4_, boundingBox.minZ - 1, 2, getComponentType());
			}

			return null;
		}

		/**
		 * Gets the next village component, with the bounding box shifted +1 in the X and Z direction.
		 */
		protected StructureComponent getNextComponentPP(StructureOmotholPieces.Start start, List<StructureComponent> p_74894_2_, Random rand, int p_74894_4_, int p_74894_5_)
		{
			switch (coordBaseMode)
			{
			case 0:
				return StructureOmotholPieces.func_176066_d(start, p_74894_2_, rand, boundingBox.maxX + 1, boundingBox.minY + p_74894_4_, boundingBox.minZ + p_74894_5_, 3, getComponentType());
			case 1:
				return StructureOmotholPieces.func_176066_d(start, p_74894_2_, rand, boundingBox.minX + p_74894_5_, boundingBox.minY + p_74894_4_, boundingBox.maxZ + 1, 0, getComponentType());
			case 2:
				return StructureOmotholPieces.func_176066_d(start, p_74894_2_, rand, boundingBox.maxX + 1, boundingBox.minY + p_74894_4_, boundingBox.minZ + p_74894_5_, 3, getComponentType());
			case 3:
				return StructureOmotholPieces.func_176066_d(start, p_74894_2_, rand, boundingBox.minX + p_74894_5_, boundingBox.minY + p_74894_4_, boundingBox.maxZ + 1, 0, getComponentType());
			default:
				return null;
			}
		}

		/**
		 * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox. (A median of
		 * all the levels in the BB's horizontal rectangle).
		 */
		protected int getAverageGroundLevel(World worldIn, StructureBoundingBox p_74889_2_)
		{
			int i = 0;
			int j = 0;

			for (int k = boundingBox.minZ; k <= boundingBox.maxZ; ++k)
				for (int l = boundingBox.minX; l <= boundingBox.maxX; ++l)
					if (p_74889_2_.isVecInside(l, 64, k))
					{
						i += Math.max(worldIn.getTopSolidOrLiquidBlock(l, k), worldIn.provider.getAverageGroundLevel());
						++j;
					}

			if (j == 0)
				return -1;
			else
				return i / j;
		}

		protected static boolean canVillageGoDeeper(StructureBoundingBox p_74895_0_)
		{
			return p_74895_0_ != null && p_74895_0_.minY > 10;
		}

		/**
		 * Spawns a number of villagers in this component. Parameters: world, component bounding box, x offset, y
		 * offset, z offset, number of villagers
		 */
		protected void spawnRemnants(World worldIn, StructureBoundingBox p_74893_2_, int p_74893_3_, int p_74893_4_, int p_74893_5_, int p_74893_6_)
		{
			if (remnantsSpawned < p_74893_6_)
				for (int i = remnantsSpawned; i < p_74893_6_; ++i)
				{
					int j = getXWithOffset(p_74893_3_ + i, p_74893_5_);
					int k = getYWithOffset(p_74893_4_);
					int l = getZWithOffset(p_74893_3_ + i, p_74893_5_);

					if (!p_74893_2_.isVecInside(j, k, l))
						break;

					++remnantsSpawned;
					EntityRemnant remnant = new EntityRemnant(worldIn);
					remnant.setLocationAndAngles(j + 0.5D, k, l + 0.5D, 0.0F, 0.0F);
					remnant.onSpawnWithEgg((IEntityLivingData)null);
					remnant.setProfession(func_180779_c(i, remnant.getProfession()));
					worldIn.spawnEntityInWorld(remnant);
				}
		}

		protected int func_180779_c(int p_180779_1_, int p_180779_2_)
		{
			return p_180779_2_;
		}

		@Override
		protected void placeBlockAtCurrentPosition(World worldIn, Block blockstateIn, int meta, int x, int y, int z, StructureBoundingBox boundingboxIn)
		{
			super.placeBlockAtCurrentPosition(worldIn, blockstateIn, meta, x, y, z, boundingboxIn);
		}

		/**
		 * Fill the given area with the selected blocks
		 */
		@Override
		protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, Block boundaryBlockState, Block insideBlockState, boolean existingOnly)
		{
			super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, boundaryBlockState, insideBlockState, existingOnly);
		}

		/**
		 * Replaces air and liquid from given position downwards. Stops when hitting anything else than air or
		 * liquid
		 */
		@Override
		protected void func_151554_b(World worldIn, Block blockstateIn, int meta, int x, int y, int z, StructureBoundingBox boundingboxIn)
		{
			super.func_151554_b(worldIn, blockstateIn, meta, x, y, z, boundingboxIn);
		}
	}

	public static class Well extends StructureOmotholPieces.Omothol
	{
		public Well()
		{
		}

		public Well(StructureOmotholPieces.Start start, int p_i2109_2_, Random rand, int p_i2109_4_, int p_i2109_5_)
		{
			super(start, p_i2109_2_);
			coordBaseMode = rand.nextInt(4);

			switch (coordBaseMode)
			{
			case 0:
			case 2:
				boundingBox = new StructureBoundingBox(4 - 40, 54, 7, 4 + 40, 81, 7 + 91);
				break;
			default:
				boundingBox = new StructureBoundingBox(4 - 40, 54, 7, 4 + 40, 81, 7 + 91);
			}
			//			boundingBox.offset(42, -10, 0);
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		public void buildComponent(StructureComponent componentIn, List listIn, Random rand)
		{
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX + 36, boundingBox.maxY - 4, boundingBox.minZ - 6, 1, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX - 36, boundingBox.maxY - 4, boundingBox.minZ - 6, 3, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX - 5, boundingBox.maxY - 4, boundingBox.minZ + 30, 1, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX + 5, boundingBox.maxY - 4, boundingBox.minZ + 30, 3, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX - 5, boundingBox.maxY - 4, boundingBox.maxZ - 30, 1, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX + 5, boundingBox.maxY - 4, boundingBox.maxZ - 30, 3, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX + 39, boundingBox.maxY - 4, boundingBox.minZ - 8, 2, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX + 39, boundingBox.maxY - 4, boundingBox.maxZ + 5, 0, getComponentType());
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			//                if (this.field_143015_k < 0)
			//                {
			//                    this.field_143015_k = this.getAverageGroundLevel(worldIn, structureBoundingBoxIn);
			//
			//                    if (this.field_143015_k < 0)
			//                    {
			//                        return true;
			//                    }
			//
			//                    this.boundingBox.offset(0, this.field_143015_k - this.boundingBox.maxY + 3, 0);
			//                }
			StructureJzaharTemple temple = new StructureJzaharTemple();
			temple.generate(worldIn, randomIn, 4, 53, 7);
			//                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 4, 12, 4, AbyssalCraft.ethaxiumbrick, Blocks.flowing_water, false);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.air, 2, 12, 2, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.air, 3, 12, 2, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.air, 2, 12, 3, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.air, 3, 12, 3, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.oak_fence, 1, 13, 1, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.oak_fence, 1, 14, 1, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.oak_fence, 4, 13, 1, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.oak_fence, 4, 14, 1, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.oak_fence, 1, 13, 4, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.oak_fence, 1, 14, 4, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.oak_fence, 4, 13, 4, structureBoundingBoxIn);
			//                this.placeBlockAtCurrentPosition(worldIn, Blocks.oak_fence, 4, 14, 4, structureBoundingBoxIn);
			//                this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 15, 1, 4, 15, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			//
			//                for (int i = 0; i <= 5; ++i)
			//                {
			//                    for (int j = 0; j <= 5; ++j)
			//                    {
			//                        if (j == 0 || j == 5 || i == 0 || i == 5)
			//                        {
			//                            this.placeBlockAtCurrentPosition(worldIn, Blocks.gravel, j, 11, i, structureBoundingBoxIn);
			//                            this.clearCurrentPositionBlocksUpwards(worldIn, j, 12, i, structureBoundingBoxIn);
			//                        }
			//                    }
			//                }

			return true;
		}
	}

	public static class WoodHut extends StructureOmotholPieces.Omothol
	{
		private boolean isTallHouse;
		private int tablePosition;

		public WoodHut()
		{
		}

		public WoodHut(StructureOmotholPieces.Start start, int p_i45565_2_, Random rand, StructureBoundingBox p_i45565_4_, int facing)
		{
			super(start, p_i45565_2_);
			coordBaseMode = facing;
			boundingBox = p_i45565_4_;
			isTallHouse = rand.nextBoolean();
			tablePosition = rand.nextInt(3);
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void func_143012_a(NBTTagCompound tagCompound)
		{
			super.func_143012_a(tagCompound);
			tagCompound.setInteger("T", tablePosition);
			tagCompound.setBoolean("C", isTallHouse);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void func_143011_b(NBTTagCompound tagCompound)
		{
			super.func_143011_b(tagCompound);
			tablePosition = tagCompound.getInteger("T");
			isTallHouse = tagCompound.getBoolean("C");
		}

		public static StructureOmotholPieces.WoodHut func_175853_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175853_1_, Random rand, int p_175853_3_, int p_175853_4_, int p_175853_5_, int facing, int p_175853_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175853_3_, p_175853_4_, p_175853_5_, 0, 0, 0, 4, 6, 5, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175853_1_, structureboundingbox) == null ? new StructureOmotholPieces.WoodHut(start, p_175853_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			if (field_143015_k < 0)
			{
				field_143015_k = getAverageGroundLevel(worldIn, structureBoundingBoxIn);

				if (field_143015_k < 0)
					return true;

				boundingBox.offset(0, field_143015_k - boundingBox.maxY + 6 - 1, 0);
			}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.air, Blocks.air, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, Blocks.dirt, Blocks.dirt, false);

			if (isTallHouse)
				fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			else
				fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);

			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 1, 4, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 2, 4, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 1, 4, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 2, 4, 4, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 0, 4, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 0, 4, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 0, 4, 3, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 3, 4, 1, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 3, 4, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumpillar, 0, 3, 4, 3, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumpillar, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumbrick, false);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 0, 2, 2, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.glass_pane, 0, 3, 2, 2, structureBoundingBoxIn);

			if (tablePosition > 0)
			{
				placeBlockAtCurrentPosition(worldIn, Blocks.fence, 0, tablePosition, 1, 3, structureBoundingBoxIn);
				placeBlockAtCurrentPosition(worldIn, Blocks.wooden_pressure_plate, 0, tablePosition, 2, 3, structureBoundingBoxIn);
			}

			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 1, 1, 0, structureBoundingBoxIn);
			placeBlockAtCurrentPosition(worldIn, Blocks.air, 0, 1, 2, 0, structureBoundingBoxIn);
			placeDoorAtCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, getMetadataWithOffset(Blocks.wooden_door, 1));

			if (getBlockAtCurrentPosition(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.air && getBlockAtCurrentPosition(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.air)
				placeBlockAtCurrentPosition(worldIn, AbyssalCraft.ethaxiumstairs, getMetadataWithOffset(Blocks.oak_stairs, 3), 1, 0, -1, structureBoundingBoxIn);

			for (int i = 0; i < 5; ++i)
				for (int j = 0; j < 4; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 6, i, structureBoundingBoxIn);
					func_151554_b(worldIn, AbyssalCraft.ethaxiumbrick, 0, j, -1, i, structureBoundingBoxIn);
				}

			spawnRemnants(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
			return true;
		}
	}
}
