/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.structures.dreadlands.mineshaft;

import static net.minecraftforge.common.ChestGenHooks.MINESHAFT_CORRIDOR;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.ChestGenHooks;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class StructureDreadlandsMinePieces
{
	/** List of contents that can generate in Mineshafts. */
	public static final WeightedRandomChestContent[] mineshaftChestContents = new WeightedRandomChestContent[] {new WeightedRandomChestContent(AbyssalCraft.abyingot, 0, 1, 5, 10), new WeightedRandomChestContent(Items.diamond, 0, 1, 3, 5), new WeightedRandomChestContent(AbyssalCraft.Coralium, 0, 4, 9, 5), new WeightedRandomChestContent(AbyssalCraft.shadowshard, 0, 4, 9, 5), new WeightedRandomChestContent(Items.diamond, 0, 1, 2, 3), new WeightedRandomChestContent(AbyssalCraft.Dreadshard, 0, 3, 8, 10), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(AbyssalCraft.Corpickaxe, 0, 1, 1, 1), new WeightedRandomChestContent(Item.getItemFromBlock(Blocks.rail), 0, 4, 8, 1), new WeightedRandomChestContent(AbyssalCraft.abychunk, 0, 2, 4, 10), new WeightedRandomChestContent(AbyssalCraft.dreadchunk, 0, 2, 4, 10), new WeightedRandomChestContent(AbyssalCraft.Corb, 0, 1, 1, 3), new WeightedRandomChestContent(AbyssalCraft.OC, 0, 1, 1, 1), new WeightedRandomChestContent(new ItemStack(AbyssalCraft.crystalZinc), 1, 5, 8)};

	public static void registerStructurePieces()
	{
		MapGenStructureIO.func_143031_a(StructureDreadlandsMinePieces.Corridor.class, "DLMSCorridor");
		MapGenStructureIO.func_143031_a(StructureDreadlandsMinePieces.Cross.class, "DLMSCrossing");
		MapGenStructureIO.func_143031_a(StructureDreadlandsMinePieces.Room.class, "DLMSRoom");
		MapGenStructureIO.func_143031_a(StructureDreadlandsMinePieces.Stairs.class, "DLMSStairs");
	}

	private static StructureComponent getRandomComponent(List<StructureComponent> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
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

	private static StructureComponent getNextMineShaftComponent(StructureComponent par0StructureComponent, List<StructureComponent> par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
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
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			par1NBTTagCompound.setBoolean("hr", hasRails);
			par1NBTTagCompound.setInteger("Num", sectionCount);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			hasRails = par1NBTTagCompound.getBoolean("hr");
			sectionCount = par1NBTTagCompound.getInteger("Num");
		}

		public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			boundingBox = par3StructureBoundingBox;
			hasRails = par2Random.nextInt(3) == 0;

			if (coordBaseMode != 2 && coordBaseMode != 0)
				sectionCount = par3StructureBoundingBox.getXSize() / 5;
			else
				sectionCount = par3StructureBoundingBox.getZSize() / 5;
		}

		public static StructureBoundingBox findValidPlacement(List<StructureComponent> par0List, Random par1Random, int par2, int par3, int par4, int par5)
		{
			StructureBoundingBox structureboundingbox = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);
			int i1;

			for (i1 = par1Random.nextInt(3) + 2; i1 > 0; --i1)
			{
				int j1 = i1 * 5;

				switch (par5)
				{
				case 0:
					structureboundingbox.maxX = par2 + 2;
					structureboundingbox.maxZ = par4 + j1 - 1;
					break;
				case 1:
					structureboundingbox.minX = par2 - (j1 - 1);
					structureboundingbox.maxZ = par4 + 2;
					break;
				case 2:
					structureboundingbox.maxX = par2 + 2;
					structureboundingbox.minZ = par4 - (j1 - 1);
					break;
				case 3:
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
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			int i = getComponentType();
			int j = par3Random.nextInt(4);

			switch (coordBaseMode)
			{
			case 0:
				if (j <= 1)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ + 1, coordBaseMode, i);
				else if (j == 2)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ - 3, 1, i);
				else
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ - 3, 3, i);

				break;
			case 1:
				if (j <= 1)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ, coordBaseMode, i);
				else if (j == 2)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ - 1, 2, i);
				else
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ + 1, 0, i);

				break;
			case 2:
				if (j <= 1)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ - 1, coordBaseMode, i);
				else if (j == 2)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ, 1, i);
				else
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ, 3, i);

				break;
			case 3:
				if (j <= 1)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ, coordBaseMode, i);
				else if (j == 2)
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX - 3, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.minZ - 1, 2, i);
				else
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX - 3, boundingBox.minY - 1 + par3Random.nextInt(3), boundingBox.maxZ + 1, 0, i);
			}

			if (i < 8)
			{
				int k;
				int l;

				if (coordBaseMode != 2 && coordBaseMode != 0)
					for (k = boundingBox.minX + 3; k + 3 <= boundingBox.maxX; k += 5)
					{
						l = par3Random.nextInt(5);

						if (l == 0)
							StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, k, boundingBox.minY, boundingBox.minZ - 1, 2, i + 1);
						else if (l == 1)
							StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, k, boundingBox.minY, boundingBox.maxZ + 1, 0, i + 1);
					}
				else
					for (k = boundingBox.minZ + 3; k + 3 <= boundingBox.maxZ; k += 5)
					{
						l = par3Random.nextInt(5);

						if (l == 0)
							StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, k, 1, i + 1);
						else if (l == 1)
							StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, k, 3, i + 1);
					}
			}
		}

		/**
		 * Used to generate chests with items in it. ex: Temple Chests, Village Blacksmith Chests, Mineshaft Chests.
		 */
		@Override
		protected boolean generateStructureChestContents(World par1World, StructureBoundingBox par2StructureBoundingBox, Random par3Random, int par4, int par5, int par6, WeightedRandomChestContent[] par7ArrayOfWeightedRandomChestContent, int par8)
		{
			int i1 = getXWithOffset(par4, par6);
			int j1 = getYWithOffset(par5);
			int k1 = getZWithOffset(par4, par6);

			if (par2StructureBoundingBox.isVecInside(i1, j1, k1) && par1World.getBlock(i1, j1, k1).getMaterial() == Material.air)
			{
				int l1 = par3Random.nextBoolean() ? 1 : 0;
				par1World.setBlock(i1, j1, k1, Blocks.rail, getMetadataWithOffset(Blocks.rail, l1), 2);
				EntityMinecartChest entityminecartchest = new EntityMinecartChest(par1World, i1 + 0.5F, j1 + 0.5F, k1 + 0.5F);
				WeightedRandomChestContent.generateChestContents(par3Random, par7ArrayOfWeightedRandomChestContent, entityminecartchest, par8);
				par1World.spawnEntityInWorld(entityminecartchest);
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
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 2, 1, i, Blocks.air, Blocks.air, false);
				randomlyFillWithBlocks(par1World, par3StructureBoundingBox, par2Random, 0.8F, 0, 2, 0, 2, 2, i, Blocks.air, Blocks.air, false);

				int j;
				int k;

				for (j = 0; j < sectionCount; ++j)
				{
					k = 2 + j * 5;
					fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, k, 0, 1, k, AbyssalCraft.DrTfence, Blocks.air, false);
					fillWithBlocks(par1World, par3StructureBoundingBox, 2, 0, k, 2, 1, k, AbyssalCraft.DrTfence, Blocks.air, false);

					if (par2Random.nextInt(4) == 0)
					{
						fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, k, 0, 2, k, AbyssalCraft.dreadplanks, Blocks.air, false);
						fillWithBlocks(par1World, par3StructureBoundingBox, 2, 2, k, 2, 2, k, AbyssalCraft.dreadplanks, Blocks.air, false);
					} else
						fillWithBlocks(par1World, par3StructureBoundingBox, 0, 2, k, 2, 2, k, AbyssalCraft.dreadplanks, Blocks.air, false);

					func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, k - 1, Blocks.torch, 0);
					func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.05F, 1, 2, k + 1, Blocks.torch, 0);

					ChestGenHooks info = ChestGenHooks.getInfo(MINESHAFT_CORRIDOR);
					if (par2Random.nextInt(100) == 0)
						generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 2, 0, k - 1, mineshaftChestContents, info.getCount(par2Random));

					if (par2Random.nextInt(100) == 0)
						generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 0, 0, k + 1, mineshaftChestContents, info.getCount(par2Random));
				}

				for (j = 0; j <= 2; ++j)
					for (k = 0; k <= i; ++k)
					{
						byte b0 = -1;
						Block block1 = getBlockAtCurrentPosition(par1World, j, b0, k, par3StructureBoundingBox);

						if (block1.getMaterial() == Material.air)
						{
							byte b1 = -1;
							placeBlockAtCurrentPosition(par1World, AbyssalCraft.dreadplanks, 0, j, b1, k, par3StructureBoundingBox);
						}
					}

				if (hasRails)
					for (j = 0; j <= i; ++j)
					{
						Block block = getBlockAtCurrentPosition(par1World, 1, -1, j, par3StructureBoundingBox);

						if (block.getMaterial() != Material.air && block.func_149730_j())
							func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.7F, 1, 0, j, Blocks.rail, getMetadataWithOffset(Blocks.rail, 0));
					}

				return true;
			}
		}
	}

	public static class Cross extends StructureComponent
	{
		private int corridorDirection;
		private boolean isMultipleFloors;
		public Cross() {}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			par1NBTTagCompound.setBoolean("tf", isMultipleFloors);
			par1NBTTagCompound.setInteger("D", corridorDirection);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			isMultipleFloors = par1NBTTagCompound.getBoolean("tf");
			corridorDirection = par1NBTTagCompound.getInteger("D");
		}

		public Cross(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			corridorDirection = par4;
			boundingBox = par3StructureBoundingBox;
			isMultipleFloors = par3StructureBoundingBox.getYSize() > 3;
		}

		public static StructureBoundingBox findValidPlacement(List<StructureComponent> par0List, Random par1Random, int par2, int par3, int par4, int par5)
		{
			StructureBoundingBox structureboundingbox = new StructureBoundingBox(par2, par3, par4, par2, par3 + 2, par4);

			if (par1Random.nextInt(4) == 0)
				structureboundingbox.maxY += 4;

			switch (par5)
			{
			case 0:
				structureboundingbox.minX = par2 - 1;
				structureboundingbox.maxX = par2 + 3;
				structureboundingbox.maxZ = par4 + 4;
				break;
			case 1:
				structureboundingbox.minX = par2 - 4;
				structureboundingbox.minZ = par4 - 1;
				structureboundingbox.maxZ = par4 + 3;
				break;
			case 2:
				structureboundingbox.minX = par2 - 1;
				structureboundingbox.maxX = par2 + 3;
				structureboundingbox.minZ = par4 - 4;
				break;
			case 3:
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
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			int i = getComponentType();

			switch (corridorDirection)
			{
			case 0:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, i);
				break;
			case 1:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, i);
				break;
			case 2:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ + 1, 1, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, i);
				break;
			case 3:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ - 1, 2, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ + 1, 0, i);
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ + 1, 3, i);
			}

			if (isMultipleFloors)
			{
				if (par3Random.nextBoolean())
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ - 1, 2, i);

				if (par3Random.nextBoolean())
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, 1, i);

				if (par3Random.nextBoolean())
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + 3 + 1, boundingBox.minZ + 1, 3, i);

				if (par3Random.nextBoolean())
					StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + 1, boundingBox.minY + 3 + 1, boundingBox.maxZ + 1, 0, i);
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
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.minY + 3 - 1, boundingBox.maxZ, Blocks.air, Blocks.air, false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.minY + 3 - 1, boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.maxY - 2, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, Blocks.air, Blocks.air, false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.maxY - 2, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY + 3, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.minY + 3, boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
				}
				else
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ, Blocks.air, Blocks.air, false);
					fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ - 1, Blocks.air, Blocks.air, false);
				}

				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.minZ + 1, AbyssalCraft.dreadplanks, Blocks.air, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX + 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.minX + 1, boundingBox.maxY, boundingBox.maxZ - 1, AbyssalCraft.dreadplanks, Blocks.air, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.maxX - 1, boundingBox.minY, boundingBox.minZ + 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.minZ + 1, AbyssalCraft.dreadplanks, Blocks.air, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.maxX - 1, boundingBox.minY, boundingBox.maxZ - 1, boundingBox.maxX - 1, boundingBox.maxY, boundingBox.maxZ - 1, AbyssalCraft.dreadplanks, Blocks.air, false);

				for (int i = boundingBox.minX; i <= boundingBox.maxX; ++i)
					for (int j = boundingBox.minZ; j <= boundingBox.maxZ; ++j)
						if (getBlockAtCurrentPosition(par1World, i, boundingBox.minY - 1, j, par3StructureBoundingBox).getMaterial() == Material.air)
							placeBlockAtCurrentPosition(par1World, AbyssalCraft.dreadplanks, 0, i, boundingBox.minY - 1, j, par3StructureBoundingBox);

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
		@SuppressWarnings({ "rawtypes", "unchecked" })
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

				structurecomponent1 = StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + j, boundingBox.minY + par3Random.nextInt(k) + 1, boundingBox.minZ - 1, 2, i);

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

				structurecomponent1 = StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX + j, boundingBox.minY + par3Random.nextInt(k) + 1, boundingBox.maxZ + 1, 0, i);

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

				structurecomponent1 = StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par3Random.nextInt(k) + 1, boundingBox.minZ + j, 1, i);

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

				structurecomponent1 = StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par3Random.nextInt(k) + 1, boundingBox.minZ + j, 3, i);

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
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY, boundingBox.minZ, boundingBox.maxX, boundingBox.minY, boundingBox.maxZ, AbyssalCraft.dreadgrass, Blocks.air, true);
				fillWithBlocks(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY + 1, boundingBox.minZ, boundingBox.maxX, Math.min(boundingBox.minY + 3, boundingBox.maxY), boundingBox.maxZ, Blocks.air, Blocks.air, false);
				Iterator<StructureBoundingBox> iterator = roomsLinkedToTheRoom.iterator();

				while (iterator.hasNext())
				{
					StructureBoundingBox structureboundingbox1 = iterator.next();
					fillWithBlocks(par1World, par3StructureBoundingBox, structureboundingbox1.minX, structureboundingbox1.maxY - 2, structureboundingbox1.minZ, structureboundingbox1.maxX, structureboundingbox1.maxY, structureboundingbox1.maxZ, Blocks.air, Blocks.air, false);
				}

				func_151547_a(par1World, par3StructureBoundingBox, boundingBox.minX, boundingBox.minY + 4, boundingBox.minZ, boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ, Blocks.air, false);
				return true;
			}
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			NBTTagList nbttaglist = new NBTTagList();
			Iterator<StructureBoundingBox> iterator = roomsLinkedToTheRoom.iterator();

			while (iterator.hasNext())
			{
				StructureBoundingBox structureboundingbox = iterator.next();
				nbttaglist.appendTag(structureboundingbox.func_151535_h());
			}

			par1NBTTagCompound.setTag("Entrances", nbttaglist);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			NBTTagList nbttaglist = par1NBTTagCompound.getTagList("Entrances", 11);

			for (int i = 0; i < nbttaglist.tagCount(); ++i)
				roomsLinkedToTheRoom.add(new StructureBoundingBox(nbttaglist.func_150306_c(i)));
		}
	}

	public static class Stairs extends StructureComponent
	{
		public Stairs() {}

		public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			boundingBox = par3StructureBoundingBox;
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound) {}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound) {}

		/**
		 * Trys to find a valid place to put this component.
		 */
		public static StructureBoundingBox findValidPlacement(List<StructureComponent> par0List, Random par1Random, int par2, int par3, int par4, int par5)
		{
			StructureBoundingBox structureboundingbox = new StructureBoundingBox(par2, par3 - 5, par4, par2, par3 + 2, par4);

			switch (par5)
			{
			case 0:
				structureboundingbox.maxX = par2 + 2;
				structureboundingbox.maxZ = par4 + 8;
				break;
			case 1:
				structureboundingbox.minX = par2 - 8;
				structureboundingbox.maxZ = par4 + 2;
				break;
			case 2:
				structureboundingbox.maxX = par2 + 2;
				structureboundingbox.minZ = par4 - 8;
				break;
			case 3:
				structureboundingbox.maxX = par2 + 8;
				structureboundingbox.maxZ = par4 + 2;
			}

			return StructureComponent.findIntersecting(par0List, structureboundingbox) != null ? null : structureboundingbox;
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			int i = getComponentType();

			switch (coordBaseMode)
			{
			case 0:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.maxZ + 1, 0, i);
				break;
			case 1:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY, boundingBox.minZ, 1, i);
				break;
			case 2:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.minX, boundingBox.minY, boundingBox.minZ - 1, 2, i);
				break;
			case 3:
				StructureDreadlandsMinePieces.getNextMineShaftComponent(par1StructureComponent, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY, boundingBox.minZ, 3, i);
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
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 0, 2, 7, 1, Blocks.air, Blocks.air, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 0, 7, 2, 2, 8, Blocks.air, Blocks.air, false);

				for (int i = 0; i < 5; ++i)
					fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i, 2 + i, Blocks.air, Blocks.air, false);

				return true;
			}
		}
	}
}