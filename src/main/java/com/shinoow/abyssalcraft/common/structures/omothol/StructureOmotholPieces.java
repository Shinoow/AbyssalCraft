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
package com.shinoow.abyssalcraft.common.structures.omothol;

import java.util.*;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityRemnant;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

@SuppressWarnings("incomplete-switch")
public class StructureOmotholPieces
{
	public static void registerOmotholPieces()
	{
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.House1.class, "OmtBH");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.Field1.class, "OmtDF");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.Field2.class, "OmtF");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.Torch.class, "OmtL");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.Hall.class, "OmtPH");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.House4Garden.class, "OmtSH");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.WoodHut.class, "OmtSmH");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.Church.class, "OmtST");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.House2.class, "OmtS");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.Start.class, "OmtStart");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.Path.class, "OmtSR");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.House3.class, "OmtTRH");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.HouseBanker.class, "OmtBH");
		MapGenStructureIO.registerStructureComponent(StructureOmotholPieces.Well.class, "OmtW");
	}

	public static List<StructureOmotholPieces.PieceWeight> getStructureVillageWeightedPieceList(Random random, int p_75084_1_)
	{
		List<StructureOmotholPieces.PieceWeight> list = new ArrayList<>();
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.House4Garden.class, 4, MathHelper.getInt(random, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.Church.class, 20, MathHelper.getInt(random, 0 + p_75084_1_, 1 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.House1.class, 20, MathHelper.getInt(random, 0 + p_75084_1_, 2 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.WoodHut.class, 3, MathHelper.getInt(random, 2 + p_75084_1_, 5 + p_75084_1_ * 3)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.Hall.class, 15, MathHelper.getInt(random, 0 + p_75084_1_, 2 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.Field1.class, 3, MathHelper.getInt(random, 1 + p_75084_1_, 4 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.Field2.class, 3, MathHelper.getInt(random, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.House2.class, 15, MathHelper.getInt(random, 0, 1 + p_75084_1_)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.House3.class, 8, MathHelper.getInt(random, 0 + p_75084_1_, 3 + p_75084_1_ * 2)));
		list.add(new StructureOmotholPieces.PieceWeight(StructureOmotholPieces.HouseBanker.class, 8, MathHelper.getInt(random, 2 + p_75084_1_, 4 + p_75084_1_ * 2)));
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

	private static StructureOmotholPieces.Omothol func_176065_a(StructureOmotholPieces.Start start, StructureOmotholPieces.PieceWeight weight, List<StructureComponent> p_176065_2_, Random rand, int p_176065_4_, int p_176065_5_, int p_176065_6_, EnumFacing facing, int p_176065_8_)
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

	private static StructureOmotholPieces.Omothol func_176067_c(StructureOmotholPieces.Start start, List<StructureComponent> p_176067_1_, Random rand, int p_176067_3_, int p_176067_4_, int p_176067_5_, EnumFacing facing, int p_176067_7_)
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

	private static StructureComponent func_176066_d(StructureOmotholPieces.Start start, List<StructureComponent> p_176066_1_, Random rand, int p_176066_3_, int p_176066_4_, int p_176066_5_, EnumFacing facing, int p_176066_7_)
	{
		if (p_176066_7_ > 250 || !(Math.abs(p_176066_3_ - start.getBoundingBox().minX) <= 4000 && Math.abs(p_176066_5_ - start.getBoundingBox().minZ) <= 4000))
			return null;
		else {
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
		}
	}

	private static StructureComponent func_176069_e(StructureOmotholPieces.Start start, List<StructureComponent> p_176069_1_, Random rand, int p_176069_3_, int p_176069_4_, int p_176069_5_, EnumFacing facing, int p_176069_7_)
	{
		if (p_176069_7_ > 3 + start.terrainType || !(Math.abs(p_176069_3_ - start.getBoundingBox().minX) <= 4000 && Math.abs(p_176069_5_ - start.getBoundingBox().minZ) <= 4000))
			return null;
		else {
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
		}
	}

	public static class Church extends StructureOmotholPieces.Omothol
	{
		public Church()
		{
		}

		public Church(StructureOmotholPieces.Start start, int p_i45564_2_, Random rand, StructureBoundingBox p_i45564_4_, EnumFacing facing)
		{
			super(start, p_i45564_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45564_4_;
		}

		public static StructureOmotholPieces.Church func_175854_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175854_1_, Random rand, int p_175854_3_, int p_175854_4_, int p_175854_5_, EnumFacing facing, int p_175854_7_)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 7, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 3, 9, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 3, 0, 8, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 3, 10, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 10, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 10, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 4, 0, 4, 7, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 4, 4, 4, 7, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 8, 3, 4, 8, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 4, 3, 10, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 5, 3, 5, 7, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 11, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 11, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 2, 11, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 2, 11, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 1, 6, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 1, 7, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 2, 1, 7, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 1, 6, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 1, 7, structureBoundingBoxIn);
			if(ACConfig.ethaxium_brick_stairs) {
				IBlockState iblockstate = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
				IBlockState iblockstate1 = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST);
				IBlockState iblockstate2 = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST);
				setBlockState(worldIn, iblockstate, 1, 1, 5, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 2, 1, 6, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 3, 1, 5, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate1, 1, 2, 7, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate2, 3, 2, 7, structureBoundingBoxIn);
			}
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 6, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 7, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 6, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 7, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 6, 4, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 4, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 6, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 6, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 3, 8, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, getCoordBaseMode().getOpposite()), 2, 4, 7, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, getCoordBaseMode().rotateY()), 1, 4, 6, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, getCoordBaseMode().rotateYCCW()), 3, 4, 6, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, getCoordBaseMode()), 2, 4, 5, structureBoundingBoxIn);
			IBlockState iblockstate3 = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.WEST);

			for (int j = 1; j <= 9; ++j)
				setBlockState(worldIn, iblockstate3, 3, j, 3, structureBoundingBoxIn);

			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
			placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);

			if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR && ACConfig.ethaxium_brick_stairs)
				setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 2, 0, -1, structureBoundingBoxIn);

			for (int l = 0; l < 9; ++l)
				for (int k = 0; k < 5; ++k)
				{
					clearCurrentPositionBlocksUpwards(worldIn, k, 12, l, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), k, -1, l, structureBoundingBoxIn);
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

		public Field1(StructureOmotholPieces.Start start, int p_i45570_2_, Random rand, StructureBoundingBox p_i45570_4_, EnumFacing facing)
		{
			super(start, p_i45570_2_);
			setCoordBaseMode(facing);
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
		protected void writeStructureToNBT(NBTTagCompound tagCompound)
		{
			super.writeStructureToNBT(tagCompound);
			tagCompound.setInteger("CA", Block.REGISTRY.getIDForObject(cropTypeA));
			tagCompound.setInteger("CB", Block.REGISTRY.getIDForObject(cropTypeB));
			tagCompound.setInteger("CC", Block.REGISTRY.getIDForObject(cropTypeC));
			tagCompound.setInteger("CD", Block.REGISTRY.getIDForObject(cropTypeD));
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
		{
			super.readStructureFromNBT(tagCompound, p_143011_2_);
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
				return Blocks.CARROTS;
			case 1:
				return Blocks.POTATOES;
			default:
				return Blocks.WHEAT;
			}
		}

		public static StructureOmotholPieces.Field1 func_175851_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175851_1_, Random rand, int p_175851_3_, int p_175851_4_, int p_175851_5_, EnumFacing facing, int p_175851_7_)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 12, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 0, 1, 8, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 10, 0, 1, 11, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 12, 0, 0, 12, 0, 8, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 11, 0, 0, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 11, 0, 8, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 0, 1, 9, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);

			for (int i = 1; i <= 7; ++i)
			{
				setBlockState(worldIn, cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 1, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 2, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 4, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 5, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeC.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 7, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeC.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 8, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeD.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 10, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeD.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 11, 1, i, structureBoundingBoxIn);
			}

			for (int k = 0; k < 9; ++k)
				for (int j = 0; j < 13; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 4, k, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), j, -1, k, structureBoundingBoxIn);
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

		public Field2(StructureOmotholPieces.Start start, int p_i45569_2_, Random rand, StructureBoundingBox p_i45569_4_, EnumFacing facing)
		{
			super(start, p_i45569_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45569_4_;
			cropTypeA = func_151560_a(rand);
			cropTypeB = func_151560_a(rand);
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound)
		{
			super.writeStructureToNBT(tagCompound);
			tagCompound.setInteger("CA", Block.REGISTRY.getIDForObject(cropTypeA));
			tagCompound.setInteger("CB", Block.REGISTRY.getIDForObject(cropTypeB));
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
		{
			super.readStructureFromNBT(tagCompound, p_143011_2_);
			cropTypeA = Block.getBlockById(tagCompound.getInteger("CA"));
			cropTypeB = Block.getBlockById(tagCompound.getInteger("CB"));
		}

		private Block func_151560_a(Random rand)
		{
			switch (rand.nextInt(5))
			{
			case 0:
				return Blocks.CARROTS;
			case 1:
				return Blocks.POTATOES;
			default:
				return Blocks.WHEAT;
			}
		}

		public static StructureOmotholPieces.Field2 func_175852_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175852_1_, Random rand, int p_175852_3_, int p_175852_4_, int p_175852_5_, EnumFacing facing, int p_175852_7_)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 6, 4, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 0, 1, 5, 0, 7, Blocks.FARMLAND.getDefaultState(), Blocks.FARMLAND.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 0, 8, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 0, 0, 6, 0, 8, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 5, 0, 0, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 8, 5, 0, 8, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 1, 3, 0, 7, Blocks.WATER.getDefaultState(), Blocks.WATER.getDefaultState(), false);

			for (int i = 1; i <= 7; ++i)
			{
				setBlockState(worldIn, cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 1, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeA.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 2, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 4, 1, i, structureBoundingBoxIn);
				setBlockState(worldIn, cropTypeB.getStateFromMeta(MathHelper.getInt(randomIn, 2, 7)), 5, 1, i, structureBoundingBoxIn);
			}

			for (int k = 0; k < 9; ++k)
				for (int j = 0; j < 7; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 4, k, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, Blocks.DIRT.getDefaultState(), j, -1, k, structureBoundingBoxIn);
				}

			return true;
		}
	}

	public static class Hall extends StructureOmotholPieces.Omothol
	{
		public Hall()
		{
		}

		public Hall(StructureOmotholPieces.Start start, int p_i45567_2_, Random rand, StructureBoundingBox p_i45567_4_, EnumFacing facing)
		{
			super(start, p_i45567_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45567_4_;
		}

		public static StructureOmotholPieces.Hall func_175857_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175857_1_, Random rand, int p_175857_3_, int p_175857_4_, int p_175857_5_, EnumFacing facing, int p_175857_7_)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 8, 0, 10, Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 6, 0, 6, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 2, 1, 10, ACBlocks.ethaxium_brick_fence.getDefaultState(), ACBlocks.ethaxium_brick_fence.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 6, 8, 1, 10, ACBlocks.ethaxium_brick_fence.getDefaultState(), ACBlocks.ethaxium_brick_fence.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 10, 7, 1, 10, ACBlocks.ethaxium_brick_fence.getDefaultState(), ACBlocks.ethaxium_brick_fence.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 1, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 7, 1, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 3, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 8, 4, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
			if(ACConfig.ethaxium_brick_stairs) {
				IBlockState iblockstate = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
				IBlockState iblockstate1 = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
				IBlockState iblockstate2 = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST);

				for (int k = -1; k <= 2; ++k)
					for (int l = 0; l <= 8; ++l)
					{
						setBlockState(worldIn, iblockstate, l, 4 + k, k, structureBoundingBoxIn);
						setBlockState(worldIn, iblockstate1, l, 4 + k, 5 - k, structureBoundingBoxIn);
					}
				setBlockState(worldIn, iblockstate, 2, 1, 4, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate2, 1, 1, 3, structureBoundingBoxIn);
			}

			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 2, 1, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 2, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 1, 4, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 0, 1, 7, 0, 3, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), Blocks.DOUBLE_STONE_SLAB.getDefaultState(), false);
			setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 6, 1, 1, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 6, 1, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, getCoordBaseMode()), 2, 3, 1, structureBoundingBoxIn);
			placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);

			if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR && ACConfig.ethaxium_brick_stairs)
				setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 2, 0, -1, structureBoundingBoxIn);

			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 6, 1, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, getCoordBaseMode().getOpposite()), 6, 3, 4, structureBoundingBoxIn);
			placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 6, 1, 5, EnumFacing.SOUTH);

			for (int i1 = 0; i1 < 5; ++i1)
				for (int j1 = 0; j1 < 9; ++j1)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j1, 7, i1, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), j1, -1, i1, structureBoundingBoxIn);
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

		public House1(StructureOmotholPieces.Start start, int p_i45571_2_, Random rand, StructureBoundingBox p_i45571_4_, EnumFacing facing)
		{
			super(start, p_i45571_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45571_4_;
		}

		public static StructureOmotholPieces.House1 func_175850_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175850_1_, Random rand, int p_175850_3_, int p_175850_4_, int p_175850_5_, EnumFacing facing, int p_175850_7_)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 8, 0, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 8, 5, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 8, 6, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 7, 2, 8, 7, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			if(ACConfig.ethaxium_brick_stairs)
				for (int k = -1; k <= 2; ++k)
					for (int l = 0; l <= 8; ++l)
					{
						setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), l, 6 + k, k, structureBoundingBoxIn);
						setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH), l, 6 + k, 5 - k, structureBoundingBoxIn);
					}

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 1, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 5, 8, 1, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 1, 0, 8, 1, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 0, 7, 1, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 0, 0, 4, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 5, 0, 4, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 5, 8, 4, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 0, 8, 4, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 2, 1, 0, 4, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 7, 4, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 2, 1, 8, 4, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 4, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 3, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 3, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 6, 2, 5, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 7, 4, 1, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 4, 7, 4, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 3, 4, 7, 3, 4, Blocks.BOOKSHELF.getDefaultState(), Blocks.BOOKSHELF.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 7, 1, 4, structureBoundingBoxIn);
			if(ACConfig.ethaxium_brick_stairs) {
				setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST), 7, 1, 3, structureBoundingBoxIn);
				IBlockState iblockstate = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
				setBlockState(worldIn, iblockstate, 6, 1, 4, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 5, 1, 4, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 4, 1, 4, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 3, 1, 4, structureBoundingBoxIn);
			}
			setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 4, 1, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 4, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.CRAFTING_TABLE.getDefaultState(), 7, 1, 1, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
			placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.NORTH);

			if (getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR && ACConfig.ethaxium_brick_stairs)
				setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 1, 0, -1, structureBoundingBoxIn);

			for (int k1 = 0; k1 < 6; ++k1)
				for (int i1 = 0; i1 < 9; ++i1)
				{
					clearCurrentPositionBlocksUpwards(worldIn, i1, 9, k1, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), i1, -1, k1, structureBoundingBoxIn);
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
		private boolean hasMadeChest;

		public House2()
		{
		}

		static
		{ //TODO: loot tables
			//			net.minecraftforge.common.ChestGenHooks.init(net.minecraftforge.common.ChestGenHooks.VILLAGE_BLACKSMITH, villageBlacksmithChestContents, 3, 8);
		}

		public House2(StructureOmotholPieces.Start start, int p_i45563_2_, Random rand, StructureBoundingBox p_i45563_4_, EnumFacing facing)
		{
			super(start, p_i45563_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45563_4_;
		}

		public static StructureOmotholPieces.House2 func_175855_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175855_1_, Random rand, int p_175855_3_, int p_175855_4_, int p_175855_5_, EnumFacing facing, int p_175855_7_)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(p_175855_3_, p_175855_4_, p_175855_5_, 0, 0, 0, 10, 6, 7, facing);
			return canVillageGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(p_175855_1_, structureboundingbox) == null ? new StructureOmotholPieces.House2(start, p_175855_7_, rand, structureboundingbox, facing) : null;
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound)
		{
			super.writeStructureToNBT(tagCompound);
			tagCompound.setBoolean("Chest", hasMadeChest);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
		{
			super.readStructureFromNBT(tagCompound, p_143011_2_);
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 9, 4, 6, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 9, 0, 6, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 9, 4, 6, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 9, 5, 6, Blocks.STONE_SLAB.getDefaultState(), Blocks.STONE_SLAB.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 8, 5, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 4, 0, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 4, 0, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 6, 0, 4, 6, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 3, 1, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 2, 3, 3, 2, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 3, 5, 3, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 6, 5, 3, 6, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 1, 0, 5, 3, 0, ACBlocks.ethaxium_brick_fence.getDefaultState(), ACBlocks.ethaxium_brick_fence.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 9, 1, 0, 9, 3, 0, ACBlocks.ethaxium_brick_fence.getDefaultState(), ACBlocks.ethaxium_brick_fence.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 1, 4, 9, 4, 6, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, Blocks.FLOWING_LAVA.getDefaultState(), 7, 1, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.FLOWING_LAVA.getDefaultState(), 8, 1, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 9, 2, 5, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.IRON_BARS.getDefaultState(), 9, 2, 4, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 2, 4, 8, 2, 5, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 6, 1, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.FURNACE.getDefaultState(), 6, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.FURNACE.getDefaultState(), 6, 3, 3, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.DOUBLE_STONE_SLAB.getDefaultState(), 8, 1, 1, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 6, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), 2, 1, 4, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 1, 5, structureBoundingBoxIn);
			if(ACConfig.ethaxium_brick_stairs) {
				setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 2, 1, 5, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST), 1, 1, 4, structureBoundingBoxIn);
			}

			if (!hasMadeChest && structureBoundingBoxIn.isVecInside(new BlockPos(getXWithOffset(5, 5), getYWithOffset(1), getZWithOffset(5, 5))))
				hasMadeChest = true; //TODO: loot tables
			//				generateChestContents(worldIn, structureBoundingBoxIn, randomIn, 5, 1, 5, net.minecraftforge.common.ChestGenHooks.getItems(net.minecraftforge.common.ChestGenHooks.VILLAGE_BLACKSMITH, randomIn), net.minecraftforge.common.ChestGenHooks.getCount(net.minecraftforge.common.ChestGenHooks.VILLAGE_BLACKSMITH, randomIn));
			if(ACConfig.ethaxium_brick_stairs)
				for (int i = 6; i <= 8; ++i)
					if (getBlockStateFromPos(worldIn, i, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, i, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
						setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), i, 0, -1, structureBoundingBoxIn);

			for (int k = 0; k < 7; ++k)
				for (int j = 0; j < 10; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), j, -1, k, structureBoundingBoxIn);
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

		public House3(StructureOmotholPieces.Start start, int p_i45561_2_, Random rand, StructureBoundingBox p_i45561_4_, EnumFacing facing)
		{
			super(start, p_i45561_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45561_4_;
		}

		public static StructureOmotholPieces.House3 func_175849_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175849_1_, Random rand, int p_175849_3_, int p_175849_4_, int p_175849_5_, EnumFacing facing, int p_175849_7_)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 7, 4, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 1, 6, 8, 4, 10, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 5, 8, 0, 10, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 7, 0, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 0, 3, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 8, 0, 0, 8, 3, 10, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 0, 7, 2, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 5, 2, 1, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 2, 0, 6, 2, 3, 10, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 0, 10, 7, 3, 10, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 0, 7, 3, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 2, 5, 2, 3, 5, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 1, 8, 4, 1, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 4, 3, 4, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 2, 8, 5, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 8, 4, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 8, 4, 3, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 8, 4, 4, structureBoundingBoxIn);
			if(ACConfig.ethaxium_brick_stairs) {
				IBlockState iblockstate = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
				IBlockState iblockstate1 = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.SOUTH);
				IBlockState iblockstate2 = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST);
				IBlockState iblockstate3 = ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST);

				for (int k = -1; k <= 2; ++k)
					for (int l = 0; l <= 8; ++l)
					{
						setBlockState(worldIn, iblockstate, l, 4 + k, k, structureBoundingBoxIn);

						if ((k > -1 || l <= 1) && (k > 0 || l <= 3) && (k > 1 || l <= 4 || l >= 6))
							setBlockState(worldIn, iblockstate1, l, 4 + k, 5 - k, structureBoundingBoxIn);
					}
				for (int l1 = 4; l1 >= 1; --l1)
				{
					setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), l1, 2 + l1, 7 - l1, structureBoundingBoxIn);

					for (int i1 = 8 - l1; i1 <= 10; ++i1)
						setBlockState(worldIn, iblockstate3, l1, 2 + l1, i1, structureBoundingBoxIn);
				}
				setBlockState(worldIn, iblockstate2, 6, 6, 4, structureBoundingBoxIn);

				for (int j2 = 6; j2 <= 8; ++j2)
					for (int j1 = 5; j1 <= 10; ++j1)
						setBlockState(worldIn, iblockstate2, j2, 12 - j2, j1, structureBoundingBoxIn);

				if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR)
					setBlockState(worldIn, iblockstate, 2, 0, -1, structureBoundingBoxIn);
			}


			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 4, 5, 3, 4, 10, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 7, 4, 2, 7, 4, 10, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 5, 4, 4, 5, 10, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 6, 5, 4, 6, 5, 10, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 5, 6, 3, 5, 6, 10, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);

			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 6, 6, 3, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 7, 5, 4, structureBoundingBoxIn);

			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 0, 2, 1, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 6, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 8, 2, 1, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 8, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 8, 2, 5, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 8, 2, 6, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 7, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 8, 2, 8, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 8, 2, 9, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 2, 2, 6, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 7, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 8, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 2, 2, 9, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 4, 4, 10, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 5, 4, 10, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 6, 4, 10, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 5, 5, 10, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 2, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, getCoordBaseMode()), 2, 3, 1, structureBoundingBoxIn);
			placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 2, 1, 0, EnumFacing.NORTH);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, -1, 3, 2, -1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

			for (int k2 = 0; k2 < 5; ++k2)
				for (int i3 = 0; i3 < 9; ++i3)
				{
					clearCurrentPositionBlocksUpwards(worldIn, i3, 7, k2, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), i3, -1, k2, structureBoundingBoxIn);
				}

			for (int l2 = 5; l2 < 11; ++l2)
				for (int j3 = 2; j3 < 9; ++j3)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j3, 7, l2, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), j3, -1, l2, structureBoundingBoxIn);
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

		public House4Garden(StructureOmotholPieces.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, EnumFacing facing)
		{
			super(start, p_i45566_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45566_4_;
			isRoofAccessible = rand.nextBoolean();
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound)
		{
			super.writeStructureToNBT(tagCompound);
			tagCompound.setBoolean("Terrace", isRoofAccessible);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
		{
			super.readStructureFromNBT(tagCompound, p_143011_2_);
			isRoofAccessible = tagCompound.getBoolean("Terrace");
		}

		public static StructureOmotholPieces.House4Garden func_175858_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, EnumFacing facing, int p_175858_7_)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 1, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 3, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 3, 4, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 2, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 1, 0, structureBoundingBoxIn);

			if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR && ACConfig.ethaxium_brick_stairs)
				setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 2, 0, -1, structureBoundingBoxIn);

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

			if (isRoofAccessible)
			{
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 5, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 1, 5, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 2, 5, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 3, 5, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 5, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 5, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 1, 5, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 2, 5, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 3, 5, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 5, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 5, 1, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 5, 2, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 5, 3, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 5, 1, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 5, 2, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 5, 3, structureBoundingBoxIn);
			}

			if (isRoofAccessible)
			{
				IBlockState iblockstate = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH);
				setBlockState(worldIn, iblockstate, 3, 1, 3, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 3, 2, 3, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 3, 3, 3, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 3, 4, 3, structureBoundingBoxIn);
			}

			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, getCoordBaseMode()), 2, 3, 1, structureBoundingBoxIn);

			for (int k = 0; k < 5; ++k)
				for (int j = 0; j < 5; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 6, k, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), j, -1, k, structureBoundingBoxIn);
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

		public HouseBanker(StructureOmotholPieces.Start start, int p_i45566_2_, Random rand, StructureBoundingBox p_i45566_4_, EnumFacing facing)
		{
			super(start, p_i45566_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45566_4_;
			isRoofAccessible = rand.nextBoolean();
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound)
		{
			super.writeStructureToNBT(tagCompound);
			tagCompound.setBoolean("Terrace", isRoofAccessible);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
		{
			super.readStructureFromNBT(tagCompound, p_143011_2_);
			isRoofAccessible = tagCompound.getBoolean("Terrace");
		}

		public static StructureOmotholPieces.HouseBanker func_175858_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175858_1_, Random rand, int p_175858_3_, int p_175858_4_, int p_175858_5_, EnumFacing facing, int p_175858_7_)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 4, 0, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 4, 0, 4, 4, 4, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 3, 4, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 1, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 3, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 1, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 3, 4, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 1, 1, 4, 3, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 3, 3, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 2, 4, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 2, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 1, 0, structureBoundingBoxIn);

			if (getBlockStateFromPos(worldIn, 2, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 2, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR && ACConfig.ethaxium_brick_stairs)
				setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 2, 0, -1, structureBoundingBoxIn);

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 3, 3, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 4, 5, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 9, 0, 4, 9, 4, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 9, 1, 3, 9, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 6, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 7, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 8, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 6, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 7, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 8, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 6, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 7, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 0, 8, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 6, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 7, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 4, 8, 4, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 6, 1, 0, 8, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, 0, 3, 8, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 4, 6, 1, 4, 8, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 6, 4, 3, 8, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 7, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 4, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 4, 7, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 2, 7, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 6, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 7, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 1, 8, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 2, 8, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 8, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 7, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), 3, 6, 0, structureBoundingBoxIn);

			if (isRoofAccessible)
			{
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 10, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 1, 10, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 2, 10, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 3, 10, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 10, 0, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 10, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 1, 10, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 2, 10, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 3, 10, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 10, 4, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 10, 1, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 10, 2, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 4, 10, 3, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 10, 1, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 10, 2, structureBoundingBoxIn);
				setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 0, 10, 3, structureBoundingBoxIn);
			}

			IBlockState iblockstate = Blocks.LADDER.getDefaultState().withProperty(BlockLadder.FACING, EnumFacing.SOUTH);
			setBlockState(worldIn, iblockstate, 3, 1, 3, structureBoundingBoxIn);
			setBlockState(worldIn, iblockstate, 3, 2, 3, structureBoundingBoxIn);
			setBlockState(worldIn, iblockstate, 3, 3, 3, structureBoundingBoxIn);
			setBlockState(worldIn, iblockstate, 3, 4, 3, structureBoundingBoxIn);
			setBlockState(worldIn, iblockstate, 3, 5, 3, structureBoundingBoxIn);

			if (isRoofAccessible)
			{
				setBlockState(worldIn, iblockstate, 3, 6, 3, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 3, 7, 3, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 3, 8, 3, structureBoundingBoxIn);
				setBlockState(worldIn, iblockstate, 3, 9, 3, structureBoundingBoxIn);
			}

			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, getCoordBaseMode()), 2, 3, 1, structureBoundingBoxIn);

			for (int k = 0; k < 5; ++k)
				for (int j = 0; j < 5; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 11, k, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), j, -1, k, structureBoundingBoxIn);
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

		public Path(StructureOmotholPieces.Start start, int p_i45562_2_, Random rand, StructureBoundingBox p_i45562_4_, EnumFacing facing)
		{
			super(start, p_i45562_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45562_4_;
			length = Math.max(p_i45562_4_.getXSize(), p_i45562_4_.getZSize());
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound)
		{
			super.writeStructureToNBT(tagCompound);
			tagCompound.setInteger("Length", length);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
		{
			super.readStructureFromNBT(tagCompound, p_143011_2_);
			length = tagCompound.getInteger("Length");
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
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

			if (flag && rand.nextInt(3) > 0 && getCoordBaseMode() != null)
				switch (getCoordBaseMode())
				{
				case NORTH:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, EnumFacing.WEST, getComponentType());
					break;
				case SOUTH:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX - 1, boundingBox.minY, boundingBox.maxZ - 2, EnumFacing.WEST, getComponentType());
					break;
				case WEST:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
					break;
				case EAST:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX - 2, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
				}

			if (flag && rand.nextInt(3) > 0 && getCoordBaseMode() != null)
				switch (getCoordBaseMode())
				{
				case NORTH:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, EnumFacing.EAST, getComponentType());
					break;
				case SOUTH:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX + 1, boundingBox.minY, boundingBox.maxZ - 2, EnumFacing.EAST, getComponentType());
					break;
				case WEST:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
					break;
				case EAST:
					StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX - 2, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
				}
		}

		public static StructureBoundingBox func_175848_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175848_1_, Random rand, int p_175848_3_, int p_175848_4_, int p_175848_5_, EnumFacing facing)
		{
			for (int i = 7 * MathHelper.getInt(rand, 3, 5); i >= 7; i -= 7)
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
				{
					BlockPos blockpos = new BlockPos(i, 64, j);

					if (structureBoundingBoxIn.isVecInside(blockpos))
					{

						BlockPos blockpos1 = blockpos;
						switch(getCoordBaseMode()){
						case NORTH:
							blockpos1 = blockpos.south();
							break;
						case SOUTH:
							blockpos1 = blockpos.north();
							break;
						case EAST:
							blockpos1 = blockpos.west();
							break;
						case WEST:
							blockpos1 = blockpos.east();
							break;
						}
						blockpos = worldIn.getTopSolidOrLiquidBlock(blockpos).down();
						blockpos1 = worldIn.getTopSolidOrLiquidBlock(blockpos1).down();
						if(blockpos1.getY() > blockpos.getY() && blockpos.getY() <= 40)
							blockpos = new BlockPos(blockpos.getX(), blockpos1.getY(), blockpos.getZ());

						if(worldIn.getTopSolidOrLiquidBlock(blockpos).down().getY() <= 40)
							if(blockpos.getY() <= 49)
								blockpos = new BlockPos(blockpos.getX(), 49, blockpos.getZ());

						worldIn.setBlockState(blockpos, ACBlocks.stone.getStateFromMeta(5), 2);
					}
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
		public BiomeProvider worldChunkMngr;
		/** Boolean that determines if the village is in a desert or not. */
		public boolean inDesert;
		/** World terrain type, 0 for normal, 1 for flap map */
		public int terrainType;
		public StructureOmotholPieces.PieceWeight structVillagePieceWeight;
		public List<StructureOmotholPieces.PieceWeight> structureVillageWeightedPieceList;
		public List<StructureComponent> field_74932_i = new ArrayList<>();
		public List<StructureComponent> field_74930_j = new ArrayList<>();
		public Biome biome;

		public Start()
		{
		}

		public Start(BiomeProvider chunkManagerIn, int p_i2104_2_, Random rand, int p_i2104_4_, int p_i2104_5_, List<StructureOmotholPieces.PieceWeight> p_i2104_6_, int p_i2104_7_)
		{
			super((StructureOmotholPieces.Start)null, 0, rand, p_i2104_4_, p_i2104_5_);
			worldChunkMngr = chunkManagerIn;
			structureVillageWeightedPieceList = p_i2104_6_;
			terrainType = p_i2104_7_;
			Biome biomegenbase = chunkManagerIn.getBiome(new BlockPos(p_i2104_4_, 0, p_i2104_5_), Biomes.OCEAN);
			//			inDesert = biomegenbase == Biome.desert || biomegenbase == Biome.desertHills;
			biome = biomegenbase;
		}

		public BiomeProvider getWorldChunkManager()
		{
			return worldChunkMngr;
		}
	}

	public static class Torch extends StructureOmotholPieces.Omothol
	{
		public Torch()
		{
		}

		public Torch(StructureOmotholPieces.Start start, int p_i45568_2_, Random rand, StructureBoundingBox p_i45568_4_, EnumFacing facing)
		{
			super(start, p_i45568_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45568_4_;
		}

		public static StructureBoundingBox func_175856_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175856_1_, Random rand, int p_175856_3_, int p_175856_4_, int p_175856_5_, EnumFacing facing)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 3, 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 1, 0, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_brick_fence.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.WOOL.getStateFromMeta(EnumDyeColor.WHITE.getDyeDamage()), 1, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.EAST), 2, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.NORTH), 1, 3, 1, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.WEST), 0, 3, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH), 1, 3, -1, structureBoundingBoxIn);
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
		protected void writeStructureToNBT(NBTTagCompound tagCompound)
		{
			tagCompound.setInteger("HPos", field_143015_k);
			tagCompound.setInteger("VCount", remnantsSpawned);
			tagCompound.setBoolean("Desert", isDesertVillage);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
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
			if (getCoordBaseMode() != null)
				switch (getCoordBaseMode())
				{
				case NORTH:
					return StructureOmotholPieces.func_176066_d(start, p_74891_2_, rand, boundingBox.minX - 1, boundingBox.minY + p_74891_4_, boundingBox.minZ + p_74891_5_, EnumFacing.WEST, getComponentType());
				case SOUTH:
					return StructureOmotholPieces.func_176066_d(start, p_74891_2_, rand, boundingBox.minX - 1, boundingBox.minY + p_74891_4_, boundingBox.minZ + p_74891_5_, EnumFacing.WEST, getComponentType());
				case WEST:
					return StructureOmotholPieces.func_176066_d(start, p_74891_2_, rand, boundingBox.minX + p_74891_5_, boundingBox.minY + p_74891_4_, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
				case EAST:
					return StructureOmotholPieces.func_176066_d(start, p_74891_2_, rand, boundingBox.minX + p_74891_5_, boundingBox.minY + p_74891_4_, boundingBox.minZ - 1, EnumFacing.NORTH, getComponentType());
				}

			return null;
		}

		/**
		 * Gets the next village component, with the bounding box shifted +1 in the X and Z direction.
		 */
		protected StructureComponent getNextComponentPP(StructureOmotholPieces.Start start, List<StructureComponent> p_74894_2_, Random rand, int p_74894_4_, int p_74894_5_)
		{
			if (getCoordBaseMode() != null)
				switch (getCoordBaseMode())
				{
				case NORTH:
					return StructureOmotholPieces.func_176066_d(start, p_74894_2_, rand, boundingBox.maxX + 1, boundingBox.minY + p_74894_4_, boundingBox.minZ + p_74894_5_, EnumFacing.EAST, getComponentType());
				case SOUTH:
					return StructureOmotholPieces.func_176066_d(start, p_74894_2_, rand, boundingBox.maxX + 1, boundingBox.minY + p_74894_4_, boundingBox.minZ + p_74894_5_, EnumFacing.EAST, getComponentType());
				case WEST:
					return StructureOmotholPieces.func_176066_d(start, p_74894_2_, rand, boundingBox.minX + p_74894_5_, boundingBox.minY + p_74894_4_, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
				case EAST:
					return StructureOmotholPieces.func_176066_d(start, p_74894_2_, rand, boundingBox.minX + p_74894_5_, boundingBox.minY + p_74894_4_, boundingBox.maxZ + 1, EnumFacing.SOUTH, getComponentType());
				}

			return null;
		}

		/**
		 * Discover the y coordinate that will serve as the ground level of the supplied BoundingBox. (A median of
		 * all the levels in the BB's horizontal rectangle).
		 */
		protected int getAverageGroundLevel(World worldIn, StructureBoundingBox p_74889_2_)
		{
			int i = 0;
			int j = 0;
			BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

			for (int k = boundingBox.minZ; k <= boundingBox.maxZ; ++k)
				for (int l = boundingBox.minX; l <= boundingBox.maxX; ++l)
				{
					blockpos$mutableblockpos.setPos(l, 64, k);

					if (p_74889_2_.isVecInside(blockpos$mutableblockpos))
					{
						i += Math.max(worldIn.getTopSolidOrLiquidBlock(blockpos$mutableblockpos).getY(), worldIn.provider.getAverageGroundLevel());
						++j;
					}
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

					if (!p_74893_2_.isVecInside(new BlockPos(j, k, l)))
						break;

					++remnantsSpawned;
					EntityRemnant remnant = new EntityRemnant(worldIn);
					remnant.setLocationAndAngles(j + 0.5D, k, l + 0.5D, 0.0F, 0.0F);
					remnant.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos(remnant)), (IEntityLivingData)null);
					remnant.setProfession(func_180779_c(i, remnant.getProfession()));
					worldIn.spawnEntity(remnant);
				}
		}

		protected int func_180779_c(int p_180779_1_, int p_180779_2_)
		{
			return p_180779_2_;
		}

		@Override
		protected void setBlockState(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
		{
			super.setBlockState(worldIn, blockstateIn, x, y, z, boundingboxIn);
		}

		protected void placeDoorCurrentPosition(World p_189927_1_, StructureBoundingBox p_189927_2_, Random p_189927_3_, int p_189927_4_, int p_189927_5_, int p_189927_6_, EnumFacing p_189927_7_)
		{
			generateDoor(p_189927_1_, p_189927_2_, p_189927_3_, p_189927_4_, p_189927_5_, p_189927_6_, EnumFacing.NORTH, (BlockDoor) (p_189927_3_.nextBoolean() ? ACBlocks.darklands_oak_door : ACBlocks.dreadlands_door));
		}

		/**
		 * Fill the given area with the selected blocks
		 */
		@Override
		protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly)
		{
			super.fillWithBlocks(worldIn, boundingboxIn, xMin, yMin, zMin, xMax, yMax, zMax, boundaryBlockState, insideBlockState, existingOnly);
		}

		/**
		 * Replaces air and liquid from given position downwards. Stops when hitting anything else than air or
		 * liquid
		 */
		@Override
		protected void replaceAirAndLiquidDownwards(World worldIn, IBlockState blockstateIn, int x, int y, int z, StructureBoundingBox boundingboxIn)
		{
			super.replaceAirAndLiquidDownwards(worldIn, blockstateIn, x, y, z, boundingboxIn);
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
			setCoordBaseMode(EnumFacing.Plane.HORIZONTAL.random(rand));

			switch (getCoordBaseMode())
			{
			case NORTH:
			case SOUTH:
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
		public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
		{
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX + 36, boundingBox.maxY - 4, boundingBox.minZ - 6, EnumFacing.WEST, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX - 36, boundingBox.maxY - 4, boundingBox.minZ - 6, EnumFacing.EAST, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX - 5, boundingBox.maxY - 4, boundingBox.minZ + 30, EnumFacing.WEST, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX + 5, boundingBox.maxY - 4, boundingBox.minZ + 30, EnumFacing.EAST, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX - 5, boundingBox.maxY - 4, boundingBox.maxZ - 30, EnumFacing.WEST, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.maxX + 5, boundingBox.maxY - 4, boundingBox.maxZ - 30, EnumFacing.EAST, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX + 39, boundingBox.maxY - 4, boundingBox.minZ - 8, EnumFacing.NORTH, getComponentType());
			StructureOmotholPieces.func_176069_e((StructureOmotholPieces.Start)componentIn, listIn, rand, boundingBox.minX + 39, boundingBox.maxY - 4, boundingBox.maxZ + 5, EnumFacing.SOUTH, getComponentType());
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
		{
			StructureJzaharTemple temple = new StructureJzaharTemple();
			temple.generate(worldIn, randomIn, new BlockPos(4, getYWithOffset(0), 7));

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

		public WoodHut(StructureOmotholPieces.Start start, int p_i45565_2_, Random rand, StructureBoundingBox p_i45565_4_, EnumFacing facing)
		{
			super(start, p_i45565_2_);
			setCoordBaseMode(facing);
			boundingBox = p_i45565_4_;
			isTallHouse = rand.nextBoolean();
			tablePosition = rand.nextInt(3);
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		@Override
		protected void writeStructureToNBT(NBTTagCompound tagCompound)
		{
			super.writeStructureToNBT(tagCompound);
			tagCompound.setInteger("T", tablePosition);
			tagCompound.setBoolean("C", isTallHouse);
		}

		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		@Override
		protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
		{
			super.readStructureFromNBT(tagCompound, p_143011_2_);
			tablePosition = tagCompound.getInteger("T");
			isTallHouse = tagCompound.getBoolean("C");
		}

		public static StructureOmotholPieces.WoodHut func_175853_a(StructureOmotholPieces.Start start, List<StructureComponent> p_175853_1_, Random rand, int p_175853_3_, int p_175853_4_, int p_175853_5_, EnumFacing facing, int p_175853_7_)
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

			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, Blocks.DIRT.getDefaultState(), Blocks.DIRT.getDefaultState(), false);

			if (isTallHouse)
				fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			else
				fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);

			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 1, 4, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 2, 4, 0, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 1, 4, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 2, 4, 4, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 0, 4, 1, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 0, 4, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 0, 4, 3, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 3, 4, 1, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 3, 4, 2, structureBoundingBoxIn);
			setBlockState(worldIn, ACBlocks.ethaxium_pillar.getDefaultState(), 3, 4, 3, structureBoundingBoxIn);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, ACBlocks.ethaxium_pillar.getDefaultState(), ACBlocks.ethaxium_pillar.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, ACBlocks.ethaxium_brick.getDefaultState(), ACBlocks.ethaxium_brick.getDefaultState(), false);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 2, structureBoundingBoxIn);

			if (tablePosition > 0)
			{
				setBlockState(worldIn, Blocks.OAK_FENCE.getDefaultState(), tablePosition, 1, 3, structureBoundingBoxIn);
				setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), tablePosition, 2, 3, structureBoundingBoxIn);
			}

			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
			setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
			placeDoorCurrentPosition(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.NORTH);

			if (getBlockStateFromPos(worldIn, 1, 0, -1, structureBoundingBoxIn).getMaterial() == Material.AIR && getBlockStateFromPos(worldIn, 1, -1, -1, structureBoundingBoxIn).getMaterial() != Material.AIR && ACConfig.ethaxium_brick_stairs)
				setBlockState(worldIn, ACBlocks.ethaxium_brick_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH), 1, 0, -1, structureBoundingBoxIn);

			for (int i = 0; i < 5; ++i)
				for (int j = 0; j < 4; ++j)
				{
					clearCurrentPositionBlocksUpwards(worldIn, j, 6, i, structureBoundingBoxIn);
					replaceAirAndLiquidDownwards(worldIn, ACBlocks.ethaxium_brick.getDefaultState(), j, -1, i, structureBoundingBoxIn);
				}

			spawnRemnants(worldIn, structureBoundingBoxIn, 1, 1, 2, 1);
			return true;
		}
	}
}
