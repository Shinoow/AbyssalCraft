/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft;

import java.util.*;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class StructureDreadlandsMinePieces
{

	public static void registerStructurePieces()
	{
		MapGenStructureIO.registerStructureComponent(StructureDreadlandsMinePieces.Corridor.class, "DLMSCorridor");
		MapGenStructureIO.registerStructureComponent(StructureDreadlandsMinePieces.Cross.class, "DLMSCrossing");
		MapGenStructureIO.registerStructureComponent(StructureDreadlandsMinePieces.Room.class, "DLMSRoom");
		MapGenStructureIO.registerStructureComponent(StructureDreadlandsMinePieces.Stairs.class, "DLMSStairs");
	}

	private static StructureComponent getRandomComponent(List<StructureComponent> par0List, Random par1Random, int par2, int par3, int par4, EnumFacing par5, int par6)
	{
		int j1 = par1Random.nextInt(100);
		StructureBoundingBox structureboundingbox;

		if (j1 >= 80)
		{
			structureboundingbox = StructureDreadlandsMinePieces.Cross.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);

			if (structureboundingbox != null)
				return new StructureDreadlandsMinePieces.Cross(par6, par1Random, structureboundingbox, par5);
		}
		else if (j1 >= 70)
		{
			structureboundingbox = StructureDreadlandsMinePieces.Stairs.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);

			if (structureboundingbox != null)
				return new StructureDreadlandsMinePieces.Stairs(par6, par1Random, structureboundingbox, par5);
		}
		else
		{
			structureboundingbox = StructureDreadlandsMinePieces.Corridor.findValidPlacement(par0List, par1Random, par2, par3, par4, par5);

			if (structureboundingbox != null)
				return new StructureDreadlandsMinePieces.Corridor(par6, par1Random, structureboundingbox, par5);
		}

		return null;
	}

	private static StructureComponent getNextMineShaftComponent(StructureComponent par0StructureComponent, List<StructureComponent> par1List, Random par2Random, int par3, int par4, int par5, EnumFacing par6, int par7)
	{
		if (par7 > 8)
			return null;
		else if (Math.abs(par3 - par0StructureComponent.getBoundingBox().minX) <= 80 && Math.abs(par5 - par0StructureComponent.getBoundingBox().minZ) <= 80)
		{
			StructureComponent structurecomponent1 = getRandomComponent(par1List, par2Random, par3, par4, par5, par6, par7 + 1);

			if (structurecomponent1 != null)
			{
				par1List.add(structurecomponent1);
				structurecomponent1.buildComponent(par0StructureComponent, par1List, par2Random);
			}

			return structurecomponent1;
		} else
			return null;
	}

	public static class Corridor extends StructureComponent
	{
		private boolean hasRails;
		/**
		 * A count of the different sections of this mine. The space between ceiling supports.
		 */
		private int sectionCount;
		public Corridor() {}

		@Override
		protected void writeStructureToNBT(NBTTagCompound par1NBTTagCompound)
		{
			par1NBTTagCompound.setBoolean("hr", hasRails);
			par1NBTTagCompound.setInteger("Num", sectionCount);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound par1NBTTagCompound, TemplateManager p_143011_2_)
		{
			hasRails = par1NBTTagCompound.getBoolean("hr");
			sectionCount = par1NBTTagCompound.getInteger("Num");
		}

		public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, EnumFacing par4)
		{
			super(par1);
			setCoordBaseMode(par4);
			boundingBox = par3StructureBoundingBox;
			hasRails = par2Random.nextInt(3) == 0;

			if (getCoordBaseMode() != EnumFacing.NORTH && getCoordBaseMode() != EnumFacing.SOUTH)
				sectionCount = par3StructureBoundingBox.getXSize() / 5;
			else
				sectionCount = par3StructureBoundingBox.getZSize() / 5;
		}

		@SuppressWarnings("incomplete-switch")
		public static StructureBoundingBox findValidPlacement(List<StructureComponent> par0List, Random par1Random, int par2, int par3, int par4, EnumFacing par5)
		{
			StructureBoundingBox structureboundingbox = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);
			int i1;

			for (i1 = par1Random.nextInt(3) + 2; i1 > 0; --i1)
			{
				int j1 = i1 * 5;

				switch (par5)
				{
				case SOUTH:
					structureboundingbox.maxX = par2 + 2;
					structureboundingbox.maxZ = par4 + j1 - 1;
					break;
				case WEST:
					structureboundingbox.minX = par2 - (j1 - 1);
					structureboundingbox.maxZ = par4 + 2;
					break;
				case NORTH:
					structureboundingbox.maxX = par2 + 2;
					structureboundingbox.minZ = par4 - (j1 - 1);
					break;
				case EAST:
					structureboundingbox.maxX = par2 + j1 - 1;
					structureboundingbox.maxZ = par4 + 2;
				}

				if (StructureComponent.findIntersecting(par0List, structureboundingbox) == null)
					break;
			}

			return i1 > 0 ? structureboundingbox : null;
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings("incomplete-switch")
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			int i = getComponentType();
			int j = par3Random.nextInt(4);

			switch (getCoordBaseMode())
			{
			case SOUTH:
				if (j <= 1)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ + 1, getCoordBaseMode(), i);
				else if (j == 2)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ - 3, EnumFacing.WEST, i);
				else
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ - 3, EnumFacing.EAST, i);

				break;
			case WEST:
				if (j <= 1)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ, getCoordBaseMode(), i);
				else if (j == 2)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ - 1, EnumFacing.NORTH, i);
				else
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ + 1, EnumFacing.SOUTH, i);

				break;
			case NORTH:
				if (j <= 1)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ - 1, getCoordBaseMode(), i);
				else if (j == 2)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ, EnumFacing.WEST, i);
				else
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ, EnumFacing.EAST, i);

				break;
			case EAST:
				if (j <= 1)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ, getCoordBaseMode(), i);
				else if (j == 2)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX - 3, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ - 1, EnumFacing.NORTH, i);
				else
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX - 3, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
			}

			if (i < 8)
			{
				int k;
				int l;

				if (getCoordBaseMode() != EnumFacing.NORTH && getCoordBaseMode() != EnumFacing.SOUTH)
					for (k = boundingBox.minX + 3; k + 3 <= boundingBox.maxX; k += 5)
					{
						l = par3Random.nextInt(5);

						if (l == 0)
							StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, k, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i + 1);
						else if (l == 1)
							StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, k, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i + 1);
					}
				else
					for (k = boundingBox.minZ + 3; k + 3 <= boundingBox.maxZ; k += 5)
					{
						l = par3Random.nextInt(5);

						if (l == 0)
							StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, k, EnumFacing.SOUTH, i + 1);
						else if (l == 1)
							StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, k, EnumFacing.WEST, i + 1);
					}
			}
		}

		/**
		 * Used to generate chests with items in it. ex: Temple Chests, Village Blacksmith Chests, Mineshaft Chests.
		 */
		@Override
		protected boolean generateChest(World world, StructureBoundingBox structureboundingbox, Random rand, int x, int y, int z, ResourceLocation loot)
		{
			BlockPos blockpos = new BlockPos(getXWithOffset(x, z), getYWithOffset(y), getZWithOffset(x, z));

			if (structureboundingbox.isVecInside(blockpos) && world.getBlockState(blockpos).getMaterial() == Material.AIR)
			{
				IBlockState iblockstate = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE, rand.nextBoolean() ? BlockRailBase.EnumRailDirection.NORTH_SOUTH : BlockRailBase.EnumRailDirection.EAST_WEST);
				setBlockState(world, iblockstate, x, y, z, structureboundingbox);
				EntityMinecartChest entityminecartchest = new EntityMinecartChest(world, blockpos.getX() + 0.5F, blockpos.getY() + 0.5F, blockpos.getZ() + 0.5F);
				entityminecartchest.setLootTable(loot, rand.nextLong());
				world.spawnEntity(entityminecartchest);
				return true;
			} else
				return false;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
		{
			if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox))
				return false;
			else
			{
				int i = sectionCount * 5 - 1;
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 1, i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				generateMaybeBox(par1World, par3StructureBoundingBox, par2Random, 0.8F, 0, 2, 0, 2, 2, i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false, 0);

				int j;
				int k;

				for (j = 0; j < sectionCount; ++j)
				{
					k = 2 + j * 5;
					fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, k, 0, 1, k, ACBlocks.dreadlands_wood_fence.getDefaultState(), Blocks.AIR.getDefaultState(), false);
					fillWithBlocks(par1World, par3StructureBoundingBox, 2, 0, k, 2, 1, k, ACBlocks.dreadlands_wood_fence.getDefaultState(), Blocks.AIR.getDefaultState(), false);

					if (par2Random.nextInt(4) == 0)
					{
						fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, k, 0, 2, k, ACBlocks.dreadlands_planks.getDefaultState(), Blocks.AIR.getDefaultState(), false);
						fillWithBlocks(par1World, par3StructureBoundingBox, 2, 2, k, 2, 2, k, ACBlocks.dreadlands_planks.getDefaultState(), Blocks.AIR.getDefaultState(), false);
					} else
						fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, k, 2, 2, k, ACBlocks.dreadlands_planks.getDefaultState(), Blocks.AIR.getDefaultState(), false);

					randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, k - 1, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH));
					randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, k + 1, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.NORTH));

					if (par2Random.nextInt(100) == 0)
						generateChest(par1World, par3StructureBoundingBox, par2Random, 2, 0, k - 1, ACLoot.CHEST_DREADLANDS_MINESHAFT);

					if (par2Random.nextInt(100) == 0)
						generateChest(par1World, par3StructureBoundingBox, par2Random, 2, 0, k + 1, ACLoot.CHEST_DREADLANDS_MINESHAFT);
				}

				for (j = 0; j <= 2; ++j)
					for (k = 0; k <= i; ++k)
					{
						byte b0 = -1;
						IBlockState block1 = getBlockStateFromPos(par1World, j, b0, k, par3StructureBoundingBox);

						if (block1.getMaterial() == Material.AIR)
						{
							byte b1 = -1;
							setBlockState(par1World, ACBlocks.dreadlands_planks.getDefaultState(), j, b1, k, par3StructureBoundingBox);
						}
					}

				if (hasRails){

					IBlockState iblockstate = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);

					for (j = 0; j <= i; ++j)
					{
						IBlockState block = getBlockStateFromPos(par1World, 1, -1, j, par3StructureBoundingBox);

						if (block.getMaterial() != Material.AIR && block.isFullBlock())
							randomlyPlaceBlock(par1World, par3StructureBoundingBox, par2Random, 0.7F, 1, 0, j, iblockstate);
					}
				}

				return true;
			}
		}
	}

	public static class Cross extends StructureComponent
	{
		private EnumFacing corridorDirection;
		private boolean isMultipleFloors;
		public Cross() {}

		@Override
		protected void writeStructureToNBT(NBTTagCompound par1NBTTagCompound)
		{
			par1NBTTagCompound.setBoolean("tf", isMultipleFloors);
			par1NBTTagCompound.setInteger("D", corridorDirection.getHorizontalIndex());
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound par1NBTTagCompound, TemplateManager p_143011_2_)
		{
			isMultipleFloors = par1NBTTagCompound.getBoolean("tf");
			corridorDirection = EnumFacing.getHorizontal(par1NBTTagCompound.getInteger("D"));
		}

		public Cross(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, EnumFacing par4)
		{
			super(par1);
			corridorDirection = par4;
			boundingBox = par3StructureBoundingBox;
			isMultipleFloors = par3StructureBoundingBox.getYSize() > 3;
		}

		@SuppressWarnings("incomplete-switch")
		public static StructureBoundingBox findValidPlacement(List<StructureComponent> par0List, Random par1Random, int par2, int par3, int par4, EnumFacing par5)
		{
			StructureBoundingBox structureboundingbox = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);

			if (par1Random.nextInt(4) == 0)
				structureboundingbox.maxY += 4;

			switch (par5)
			{
			case SOUTH:
				structureboundingbox.minX = par2 - 1;
				structureboundingbox.maxX = par2 + 3;
				structureboundingbox.maxZ = par4 + 4;
				break;
			case WEST:
				structureboundingbox.minX = par2 - 4;
				structureboundingbox.minZ = par4 - 1;
				structureboundingbox.maxZ = par4 + 3;
				break;
			case NORTH:
				structureboundingbox.minX = par2 - 1;
				structureboundingbox.maxX = par2 + 3;
				structureboundingbox.minZ = par4 - 4;
				break;
			case EAST:
				structureboundingbox.maxX = par2 + 4;
				structureboundingbox.minZ = par4 - 1;
				structureboundingbox.maxZ = par4 + 3;
			}

			return StructureComponent.findIntersecting(par0List, structureboundingbox) != null ? null : structureboundingbox;
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings("incomplete-switch")
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			int i = getComponentType();

			switch (corridorDirection)
			{
			case SOUTH:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.WEST, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.EAST, i);
				break;
			case WEST:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.WEST, i);
				break;
			case NORTH:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.WEST, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.EAST, i);
				break;
			case EAST:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, EnumFacing.EAST, i);
			}

			if (isMultipleFloors)
			{
				if (par3Random.nextBoolean())
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ - 1, EnumFacing.NORTH, i);

				if (par3Random.nextBoolean())
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, EnumFacing.WEST, i);

				if (par3Random.nextBoolean())
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, EnumFacing.EAST, i);

				if (par3Random.nextBoolean())
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
			}
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
		{
			if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox))
				return false;
			else
			{
				if (isMultipleFloors)
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.minY + 3 - 1, boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.minY + 3 - 1, boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.maxY - 2, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.maxY - 2, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY + 3, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.minY + 3, boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				}
				else
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				}

				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.minZ + 1, ACBlocks.dreadlands_planks.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.maxZ - 1, ACBlocks.dreadlands_planks.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.maxX - 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.minZ + 1, ACBlocks.dreadlands_planks.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.maxX - 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ - 1, ACBlocks.dreadlands_planks.getDefaultState(), Blocks.AIR.getDefaultState(), false);

				for (int i = boundingBox.minX; i <= boundingBox.maxX; ++i)
					for (int j = boundingBox.minZ; j <= boundingBox.maxZ; ++j)
						if (getBlockStateFromPos(par1World, i, boundingBox.minY - 1, j, par3StructureBoundingBox).getMaterial() == Material.AIR)
							setBlockState(par1World, ACBlocks.dreadlands_planks.getDefaultState(), i, boundingBox.minY - 1, j, par3StructureBoundingBox);

				return true;
			}
		}
	}

	public static class Room extends StructureComponent
	{
		/** List of other Mineshaft components linked to this room. */
		private List<StructureBoundingBox> roomsLinkedToTheRoom = new LinkedList<StructureBoundingBox>();
		public Room() {}

		public Room(int par1, Random par2Random, int par3, int par4)
		{
			super(par1);
			boundingBox = new StructureBoundingBox(par3, 50, par4, par3 + 7 + par2Random.nextInt(6), 54 + par2Random.nextInt(6), par4 + 7 + par2Random.nextInt(6));
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			int i = getComponentType();
			int k = boundingBox.getYSize() - 3 - 1;

			if (k <= 0)
				k = 1;

			int j;
			StructureComponent structurecomponent1;
			StructureBoundingBox structureboundingbox;

			for (j = 0; j < boundingBox.getXSize(); j += 4)
			{
				j += par3Random.nextInt(boundingBox.getXSize());

				if (j + 3 > boundingBox.getXSize())
					break;

				structurecomponent1 = StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + j, boundingBox.minY + par3Random.nextInt(k) + 1, boundingBox.minZ - 1, EnumFacing.NORTH, i);

				if (structurecomponent1 != null)
				{
					structureboundingbox = structurecomponent1.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox.minX, structureboundingbox.minY, boundingBox.minZ, structureboundingbox.maxX, structureboundingbox.maxY, boundingBox.minZ + 1));
				}
			}

			for (j = 0; j < boundingBox.getXSize(); j += 4)
			{
				j += par3Random.nextInt(boundingBox.getXSize());

				if (j + 3 > boundingBox.getXSize())
					break;

				structurecomponent1 = StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + j, boundingBox.minY + par3Random.nextInt(k) + 1, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);

				if (structurecomponent1 != null)
				{
					structureboundingbox = structurecomponent1.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(structureboundingbox.minX, structureboundingbox.minY, boundingBox.maxZ - 1, structureboundingbox.maxX, structureboundingbox.maxY, boundingBox.maxZ));
				}
			}

			for (j = 0; j < boundingBox.getZSize(); j += 4)
			{
				j += par3Random.nextInt(boundingBox.getZSize());

				if (j + 3 > boundingBox.getZSize())
					break;

				structurecomponent1 = StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par3Random.nextInt(k) + 1, boundingBox.minZ + j, EnumFacing.WEST, i);

				if (structurecomponent1 != null)
				{
					structureboundingbox = structurecomponent1.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.minX, structureboundingbox.minY, structureboundingbox.minZ, boundingBox.minX + 1, structureboundingbox.maxY, structureboundingbox.maxZ));
				}
			}

			for (j = 0; j < boundingBox.getZSize(); j += 4)
			{
				j += par3Random.nextInt(boundingBox.getZSize());

				if (j + 3 > boundingBox.getZSize())
					break;

				structurecomponent1 = StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par3Random.nextInt(k) + 1, boundingBox.minZ + j, EnumFacing.EAST, i);

				if (structurecomponent1 != null)
				{
					structureboundingbox = structurecomponent1.getBoundingBox();
					roomsLinkedToTheRoom.add(new StructureBoundingBox(boundingBox.maxX - 1, structureboundingbox.minY, structureboundingbox.minZ, boundingBox.maxX, structureboundingbox.maxY, structureboundingbox.maxZ));
				}
			}
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
		{
			if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox))
				return false;
			else
			{
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, ACBlocks.dreadlands_grass.getDefaultState(), Blocks.AIR.getDefaultState(), true);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY + 1, boundingBox.minZ, boundingBox.maxX, Math.min(boundingBox.minY + 3, boundingBox.maxY), boundingBox.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				Iterator<StructureBoundingBox> iterator = roomsLinkedToTheRoom.iterator();

				while (iterator.hasNext())
				{
					StructureBoundingBox structureboundingbox1 = iterator.next();
					fillWithBlocks(par1World, par3StructureBoundingBox, structureboundingbox1.minX, structureboundingbox1.maxY - 2, structureboundingbox1.minZ, structureboundingbox1.maxX, structureboundingbox1.maxY, structureboundingbox1.maxZ, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				}

				randomlyRareFillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY + 4, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, Blocks.AIR.getDefaultState(), false);
				return true;
			}
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound par1NBTTagCompound)
		{
			NBTTagList nbttaglist = new NBTTagList();
			Iterator<StructureBoundingBox> iterator = roomsLinkedToTheRoom.iterator();

			while (iterator.hasNext())
			{
				StructureBoundingBox structureboundingbox = iterator.next();
				nbttaglist.appendTag(structureboundingbox.toNBTTagIntArray());
			}

			par1NBTTagCompound.setTag("Entrances", nbttaglist);
		}

		@Override
		protected void readStructureFromNBT(NBTTagCompound par1NBTTagCompound, TemplateManager p_143011_2_)
		{
			NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Entrances", 11);

			for (int i = 0; i < nbttaglist.tagCount(); ++i)
				roomsLinkedToTheRoom.add(new StructureBoundingBox(nbttaglist.getIntArrayAt(i)));
		}
	}

	public static class Stairs extends StructureComponent
	{
		public Stairs() {}

		public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, EnumFacing par4)
		{
			super(par1);
			setCoordBaseMode(par4);
			boundingBox = par3StructureBoundingBox;
		}

		@Override
		protected void writeStructureToNBT(NBTTagCompound par1NBTTagCompound) {}

		@Override
		protected void readStructureFromNBT(NBTTagCompound par1NBTTagCompound, TemplateManager p_143011_2_) {}

		/**
		 * Trys to find a valid place to put this component.
		 */
		@SuppressWarnings("incomplete-switch")
		public static StructureBoundingBox findValidPlacement(List<StructureComponent> par0List, Random par1Random, int par2, int par3, int par4, EnumFacing par5)
		{
			StructureBoundingBox structureboundingbox = new StructureBoundingBox(par2, par3 - 5, par4, par2, par3 + 2, par4);

			switch (par5)
			{
			case SOUTH:
				structureboundingbox.maxX = par2 + 2;
				structureboundingbox.maxZ = par4 + 8;
				break;
			case WEST:
				structureboundingbox.minX = par2 - 8;
				structureboundingbox.maxZ = par4 + 2;
				break;
			case NORTH:
				structureboundingbox.maxX = par2 + 2;
				structureboundingbox.minZ = par4 - 8;
				break;
			case EAST:
				structureboundingbox.maxX = par2 + 8;
				structureboundingbox.maxZ = par4 + 2;
			}

			return StructureComponent.findIntersecting(par0List, structureboundingbox) != null ? null : structureboundingbox;
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings("incomplete-switch")
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			int i = getComponentType();

			switch (getCoordBaseMode())
			{
			case SOUTH:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
				break;
			case WEST:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, EnumFacing.WEST, i);
				break;
			case NORTH:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, EnumFacing.NORTH, i);
				break;
			case EAST:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, EnumFacing.EAST, i);
			}
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
		{
			if (isLiquidInStructureBoundingBox(par1World, par3StructureBoundingBox))
				return false;
			else
			{
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 2, 7, 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 7, 2, 2, 8, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

				for (int i = 0; i < 5; ++i)
					fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

				return true;
			}
		}
	}
}
