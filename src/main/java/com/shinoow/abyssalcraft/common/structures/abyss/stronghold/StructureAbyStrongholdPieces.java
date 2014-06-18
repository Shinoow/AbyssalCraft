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

import static net.minecraftforge.common.ChestGenHooks.STRONGHOLD_CORRIDOR;
import static net.minecraftforge.common.ChestGenHooks.STRONGHOLD_CROSSING;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraftforge.common.ChestGenHooks;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class StructureAbyStrongholdPieces
{
	private static final StructureAbyStrongholdPieces.PieceWeight[] pieceWeightArray = new StructureAbyStrongholdPieces.PieceWeight[] {new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.Straight.class, 40, 0), new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.Prison.class, 5, 5), new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.LeftTurn.class, 20, 0), new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.RightTurn.class, 20, 0), new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.RoomCrossing.class, 10, 6), new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.StairsStraight.class, 5, 5), new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.Stairs.class, 5, 5), new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.Crossing.class, 5, 4), new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.ChestCorridor.class, 5, 4)
	{

		@Override
		public boolean canSpawnMoreStructuresOfType(int par1)
		{
			return super.canSpawnMoreStructuresOfType(par1) && par1 > 4;
		}
	}, new StructureAbyStrongholdPieces.PieceWeight(StructureAbyStrongholdPieces.PortalRoom.class, 20, 1)
	{

		@Override
		public boolean canSpawnMoreStructuresOfType(int par1)
		{
			return super.canSpawnMoreStructuresOfType(par1) && par1 > 5;
		}
	}
	};
	private static List<PieceWeight> structurePieceList;
	private static Class<?> strongComponentType;
	static int totalWeight;
	private static final StructureAbyStrongholdPieces.Stones strongholdStones = new StructureAbyStrongholdPieces.Stones(null);
	public static void registerStructurePieces()
	{
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.ChestCorridor.class, "SHACC");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.Corridor.class, "SHAFC");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.Crossing.class, "SHA5C");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.LeftTurn.class, "SHALT");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.PortalRoom.class, "SHAPR");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.Prison.class, "SHAPH");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.RightTurn.class, "SHART");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.RoomCrossing.class, "SHARC");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.Stairs.class, "SHASD");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.Stairs2.class, "SHAStart");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.Straight.class, "SHAS");
		MapGenStructureIO.func_143031_a(StructureAbyStrongholdPieces.StairsStraight.class, "SHASSD");
	}

	/**
	 * sets up Arrays with the Structure pieces and their weights
	 */
	public static void prepareStructurePieces()
	{
		structurePieceList = new ArrayList<PieceWeight>();
		StructureAbyStrongholdPieces.PieceWeight[] apieceweight = pieceWeightArray;
		int i = apieceweight.length;

		for (int j = 0; j < i; ++j)
		{
			StructureAbyStrongholdPieces.PieceWeight pieceweight = apieceweight[j];
			pieceweight.instancesSpawned = 0;
			structurePieceList.add(pieceweight);
		}

		strongComponentType = null;
	}

	private static boolean canAddStructurePieces()
	{
		boolean flag = false;
		totalWeight = 0;
		StructureAbyStrongholdPieces.PieceWeight pieceweight;

		for (Iterator<PieceWeight> iterator = structurePieceList.iterator(); iterator.hasNext(); totalWeight += pieceweight.pieceWeight)
		{
			pieceweight = iterator.next();

			if (pieceweight.instancesLimit > 0 && pieceweight.instancesSpawned < pieceweight.instancesLimit)
			{
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * translates the PieceWeight class to the Component class
	 */
	private static StructureAbyStrongholdPieces.Stronghold getStrongholdComponentFromWeightedPiece(Class<?> par0Class, List<Stronghold> par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
	{
		Object object = null;

		if (par0Class == StructureAbyStrongholdPieces.Straight.class)
		{
			object = StructureAbyStrongholdPieces.Straight.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
		}
		else if (par0Class == StructureAbyStrongholdPieces.Prison.class)
		{
			object = StructureAbyStrongholdPieces.Prison.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
		}
		else if (par0Class == StructureAbyStrongholdPieces.LeftTurn.class)
		{
			object = StructureAbyStrongholdPieces.LeftTurn.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
		}
		else if (par0Class == StructureAbyStrongholdPieces.RightTurn.class)
		{
			object = LeftTurn.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
		}
		else if (par0Class == StructureAbyStrongholdPieces.RoomCrossing.class)
		{
			object = StructureAbyStrongholdPieces.RoomCrossing.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
		}
		else if (par0Class == StructureAbyStrongholdPieces.StairsStraight.class)
		{
			object = StructureAbyStrongholdPieces.StairsStraight.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
		}
		else if (par0Class == StructureAbyStrongholdPieces.Stairs.class)
		{
			object = StructureAbyStrongholdPieces.Stairs.getStrongholdStairsComponent(par1List, par2Random, par3, par4, par5, par6, par7);
		}
		else if (par0Class == StructureAbyStrongholdPieces.Crossing.class)
		{
			object = StructureAbyStrongholdPieces.Crossing.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
		}
		else if (par0Class == StructureAbyStrongholdPieces.ChestCorridor.class)
		{
			object = StructureAbyStrongholdPieces.ChestCorridor.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
		}
		else if (par0Class == StructureAbyStrongholdPieces.PortalRoom.class)
		{
			object = StructureAbyStrongholdPieces.PortalRoom.findValidPlacement(par1List, par2Random, par3, par4, par5, par6, par7);
		}

		return (StructureAbyStrongholdPieces.Stronghold)object;
	}

	private static StructureAbyStrongholdPieces.Stronghold getNextComponent(StructureAbyStrongholdPieces.Stairs2 par0ComponentStrongholdStairs2, List<Stronghold> par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
	{
		if (!canAddStructurePieces())
			return null;
		else
		{
			if (strongComponentType != null)
			{
				StructureAbyStrongholdPieces.Stronghold stronghold = getStrongholdComponentFromWeightedPiece(strongComponentType, par1List, par2Random, par3, par4, par5, par6, par7);
				strongComponentType = null;

				if (stronghold != null)
					return stronghold;
			}

			int k1 = 0;

			while (k1 < 5)
			{
				++k1;
				int j1 = par2Random.nextInt(totalWeight);
				Iterator<PieceWeight> iterator = structurePieceList.iterator();

				while (iterator.hasNext())
				{
					StructureAbyStrongholdPieces.PieceWeight pieceweight = iterator.next();
					j1 -= pieceweight.pieceWeight;

					if (j1 < 0)
					{
						if (!pieceweight.canSpawnMoreStructuresOfType(par7) || pieceweight == par0ComponentStrongholdStairs2.strongholdPieceWeight)
						{
							break;
						}

						StructureAbyStrongholdPieces.Stronghold stronghold1 = getStrongholdComponentFromWeightedPiece(pieceweight.pieceClass, par1List, par2Random, par3, par4, par5, par6, par7);

						if (stronghold1 != null)
						{
							++pieceweight.instancesSpawned;
							par0ComponentStrongholdStairs2.strongholdPieceWeight = pieceweight;

							if (!pieceweight.canSpawnMoreStructures())
							{
								structurePieceList.remove(pieceweight);
							}

							return stronghold1;
						}
					}
				}
			}

			StructureBoundingBox structureboundingbox = StructureAbyStrongholdPieces.Corridor.func_74992_a(par1List, par2Random, par3, par4, par5, par6);

			if (structureboundingbox != null && structureboundingbox.minY > 1)
				return new StructureAbyStrongholdPieces.Corridor(par7, par2Random, structureboundingbox, par6);
			else
				return null;
		}
	}

	private static StructureComponent getNextValidComponent(StructureAbyStrongholdPieces.Stairs2 par0ComponentStrongholdStairs2, List<Stronghold> par1List, Random par2Random, int par3, int par4, int par5, int par6, int par7)
	{
		if (par7 > 50)
			return null;
		else if (Math.abs(par3 - par0ComponentStrongholdStairs2.getBoundingBox().minX) <= 112 && Math.abs(par5 - par0ComponentStrongholdStairs2.getBoundingBox().minZ) <= 112)
		{
			StructureAbyStrongholdPieces.Stronghold stronghold = getNextComponent(par0ComponentStrongholdStairs2, par1List, par2Random, par3, par4, par5, par6, par7 + 1);

			if (stronghold != null)
			{
				par1List.add(stronghold);
				par0ComponentStrongholdStairs2.field_75026_c.add(stronghold);
			}

			return stronghold;
		} else
			return null;
	}

	public static class Stairs extends StructureAbyStrongholdPieces.Stronghold
	{
		private boolean field_75024_a;
		public Stairs() {}

		public Stairs(int par1, Random par2Random, int par3, int par4)
		{
			super(par1);
			field_75024_a = true;
			coordBaseMode = par2Random.nextInt(4);
			field_143013_d = StructureAbyStrongholdPieces.Stronghold.Door.OPENING;

			switch (coordBaseMode)
			{
			case 0:
			case 2:
				boundingBox = new StructureBoundingBox(par3, 64, par4, par3 + 5 - 1, 74, par4 + 5 - 1);
				break;
			default:
				boundingBox = new StructureBoundingBox(par3, 64, par4, par3 + 5 - 1, 74, par4 + 5 - 1);
			}
		}

		public Stairs(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			field_75024_a = false;
			coordBaseMode = par4;
			field_143013_d = getRandomDoor(par2Random);
			boundingBox = par3StructureBoundingBox;
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143012_a(par1NBTTagCompound);
			par1NBTTagCompound.setBoolean("Source", field_75024_a);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143011_b(par1NBTTagCompound);
			field_75024_a = par1NBTTagCompound.getBoolean("Source");
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			if (field_75024_a)
			{
				StructureAbyStrongholdPieces.strongComponentType = StructureAbyStrongholdPieces.Crossing.class;
			}

			getNextComponentNormal((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
		}

		/**
		 * performs some checks, then gives out a fresh Stairs component
		 */
		public static StructureAbyStrongholdPieces.Stairs getStrongholdStairsComponent(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 11, 5, par5);
			/**
			 * returns false if the Structure Bounding Box goes below 10
			 */
			return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new StructureAbyStrongholdPieces.Stairs(par6, par1Random, structureboundingbox, par5) : null;
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
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 4, true, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, field_143013_d, 1, 7, 0);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureAbyStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 4);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 2, 6, 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 1, 5, 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 1, 6, 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 1, 5, 2, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 1, 4, 3, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 1, 5, 3, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 2, 4, 3, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 3, 3, 3, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 3, 4, 3, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 3, 3, 2, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 3, 2, 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 3, 3, 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 2, 2, 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 1, 1, 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 1, 2, 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 1, 1, 2, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 1, 1, 3, par3StructureBoundingBox);
				return true;
			}
		}
	}

	public static class Straight extends StructureAbyStrongholdPieces.Stronghold
	{
		private boolean expandsX;
		private boolean expandsZ;
		public Straight() {}

		public Straight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			field_143013_d = getRandomDoor(par2Random);
			boundingBox = par3StructureBoundingBox;
			expandsX = par2Random.nextInt(2) == 0;
			expandsZ = par2Random.nextInt(2) == 0;
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143012_a(par1NBTTagCompound);
			par1NBTTagCompound.setBoolean("Left", expandsX);
			par1NBTTagCompound.setBoolean("Right", expandsZ);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143011_b(par1NBTTagCompound);
			expandsX = par1NBTTagCompound.getBoolean("Left");
			expandsZ = par1NBTTagCompound.getBoolean("Right");
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			getNextComponentNormal((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);

			if (expandsX)
			{
				getNextComponentX((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 2);
			}

			if (expandsZ)
			{
				getNextComponentZ((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 2);
			}
		}

		public static StructureAbyStrongholdPieces.Straight findValidPlacement(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
			/**
			 * returns false if the Structure Bounding Box goes below 10
			 */
			return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new StructureAbyStrongholdPieces.Straight(par6, par1Random, structureboundingbox, par5) : null;
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
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, field_143013_d, 1, 1, 0);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureAbyStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
				func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 1, 2, 1, Blocks.torch, 0);
				func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 3, 2, 1, Blocks.torch, 0);
				func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 1, 2, 5, Blocks.torch, 0);
				func_151552_a(par1World, par3StructureBoundingBox, par2Random, 0.1F, 3, 2, 5, Blocks.torch, 0);

				if (expandsX)
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 2, 0, 3, 4, Blocks.air, Blocks.air, false);
				}

				if (expandsZ)
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 2, 4, 3, 4, Blocks.air, Blocks.air, false);
				}

				return true;
			}
		}
	}

	public static class PortalRoom extends StructureAbyStrongholdPieces.Stronghold
	{
		private boolean hasSpawner;
		public PortalRoom() {}

		public PortalRoom(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			boundingBox = par3StructureBoundingBox;
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143012_a(par1NBTTagCompound);
			par1NBTTagCompound.setBoolean("Mob", hasSpawner);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143011_b(par1NBTTagCompound);
			hasSpawner = par1NBTTagCompound.getBoolean("Mob");
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings("rawtypes")
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			if (par1StructureComponent != null)
			{
				((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent).strongholdPortalRoom = this;
			}
		}

		public static StructureAbyStrongholdPieces.PortalRoom findValidPlacement(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 11, 8, 16, par5);
			/**
			 * returns false if the Structure Bounding Box goes below 10
			 */
			return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new StructureAbyStrongholdPieces.PortalRoom(par6, par1Random, structureboundingbox, par5) : null;
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes
		 * Mineshafts at the end, it adds Fences...
		 */
		@Override
		public boolean addComponentParts(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox)
		{
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 7, 15, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureAbyStrongholdPieces.Stronghold.Door.GRATES, 4, 1, 0);
			byte b0 = 6;
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, b0, 1, 1, b0, 14, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 9, b0, 1, 9, b0, 14, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, b0, 1, 8, b0, 2, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 2, b0, 14, 8, b0, 14, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 2, 1, 4, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 8, 1, 1, 9, 1, 4, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 1, 1, 1, 3, AbyssalCraft.Cwater, AbyssalCraft.Cwater, false);
			fillWithBlocks(par1World, par3StructureBoundingBox, 9, 1, 1, 9, 1, 3, AbyssalCraft.Cwater, AbyssalCraft.Cwater, false);
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 3, 1, 8, 7, 1, 12, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 9, 6, 1, 11, AbyssalCraft.Cwater, AbyssalCraft.Cwater, false);
			int i;

			for (i = 3; i < 14; i += 2)
			{
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, i, 0, 4, i, AbyssalCraft.abyfence, AbyssalCraft.abyfence, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 10, 3, i, 10, 4, i, AbyssalCraft.abyfence, AbyssalCraft.abyfence, false);
			}

			for (i = 2; i < 9; i += 2)
			{
				fillWithBlocks(par1World, par3StructureBoundingBox, i, 3, 15, i, 4, 15, AbyssalCraft.abyfence, AbyssalCraft.abyfence, false);
			}

			i = getMetadataWithOffset(AbyssalCraft.abystairs, 3);
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 5, 6, 1, 7, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 2, 6, 6, 2, 7, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
			fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 3, 7, 6, 3, 7, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);

			for (int j = 4; j <= 6; ++j)
			{
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abystairs, i, j, 1, 4, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abystairs, i, j, 2, 5, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abystairs, i, j, 3, 6, par3StructureBoundingBox);
			}

			byte b4 = 2;
			byte b1 = 0;
			byte b2 = 3;
			byte b3 = 1;

			switch (coordBaseMode)
			{
			case 0:
				b4 = 0;
				b1 = 2;
				break;
			case 1:
				b4 = 1;
				b1 = 3;
				b2 = 0;
				b3 = 2;
			case 2:
			default:
				break;
			case 3:
				b4 = 3;
				b1 = 1;
				b2 = 0;
				b3 = 2;
			}

			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b4 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 3, 8, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b4 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 3, 8, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b4 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 3, 8, par3StructureBoundingBox);

			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 3, 12, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 3, 12, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 3, 12, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 4, 12, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 4, 12, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 4, 12, par3StructureBoundingBox);


			placeBlockAtCurrentPosition(par1World, AbyssalCraft.PSDL, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 4, 10, par3StructureBoundingBox);

			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 3, 10, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 3, 10, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 3, 10, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 3, 9, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 3, 9, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 3, 9, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 5, 3, 11, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 4, 3, 11, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 6, 3, 11, par3StructureBoundingBox);

			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b2 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 9, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b2 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 10, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b2 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 11, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b2 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 4, 9, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b2 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 4, 10, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b2 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 4, 11, par3StructureBoundingBox);

			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b3 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 9, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b3 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 10, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b3 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 11, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b3 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 4, 9, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b3 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 4, 10, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b3 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 4, 11, par3StructureBoundingBox);

			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 12, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 4, 12, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 12, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 4, 12, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 3, 8, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 7, 4, 8, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 3, 8, par3StructureBoundingBox);
			placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, b1 + (par2Random.nextFloat() > 0.9F ? 4 : 0), 3, 4, 8, par3StructureBoundingBox);

			if (!hasSpawner)
			{
				int i1 = getYWithOffset(3);
				int k = getXWithOffset(5, 6);
				int l = getZWithOffset(5, 6);

				if (par3StructureBoundingBox.isVecInside(k, i1, l))
				{
					hasSpawner = true;
					par1World.setBlock(k, i1, l, Blocks.mob_spawner, 0, 2);
					TileEntityMobSpawner tileentitymobspawner = (TileEntityMobSpawner)par1World.getTileEntity(k, i1, l);

					if (tileentitymobspawner != null)
					{
						tileentitymobspawner.func_145881_a().setEntityName("Zombie");
					}
				}
			}

			return true;
		}
	}

	public static class ChestCorridor extends StructureAbyStrongholdPieces.Stronghold
	{
		/**
		 * List of items that Stronghold chests can contain.
		 */
		public static final WeightedRandomChestContent[] strongholdChestContents = new WeightedRandomChestContent[] {new WeightedRandomChestContent(Items.ender_pearl, 0, 1, 4, 10), new WeightedRandomChestContent(AbyssalCraft.Corb, 0, 1, 3, 3), new WeightedRandomChestContent(AbyssalCraft.abyingot, 0, 1, 5, 10), new WeightedRandomChestContent(AbyssalCraft.Cingot, 0, 1, 3, 5), new WeightedRandomChestContent(AbyssalCraft.Cpearl, 0, 1, 3, 5), new WeightedRandomChestContent(Items.bread, 0, 1, 3, 15), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 3, 15), new WeightedRandomChestContent(AbyssalCraft.pickaxeA, 0, 1, 1, 5), new WeightedRandomChestContent(AbyssalCraft.pickaxeA, 0, 1, 1, 5), new WeightedRandomChestContent(AbyssalCraft.plate, 0, 1, 1, 5), new WeightedRandomChestContent(AbyssalCraft.helmet, 0, 1, 1, 5), new WeightedRandomChestContent(AbyssalCraft.legs, 0, 1, 1, 5), new WeightedRandomChestContent(AbyssalCraft.boots, 0, 1, 1, 5), new WeightedRandomChestContent(AbyssalCraft.OC, 0, 1, 1, 1)};
		private boolean hasMadeChest;
		public ChestCorridor() {}

		public ChestCorridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			field_143013_d = getRandomDoor(par2Random);
			boundingBox = par3StructureBoundingBox;
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143012_a(par1NBTTagCompound);
			par1NBTTagCompound.setBoolean("Chest", hasMadeChest);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143011_b(par1NBTTagCompound);
			hasMadeChest = par1NBTTagCompound.getBoolean("Chest");
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			getNextComponentNormal((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
		}

		public static StructureAbyStrongholdPieces.ChestCorridor findValidPlacement(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 7, par5);
			/**
			 * returns false if the Structure Bounding Box goes below 10
			 */
			return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new StructureAbyStrongholdPieces.ChestCorridor(par6, par1Random, structureboundingbox, par5) : null;
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
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 6, true, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, field_143013_d, 1, 1, 0);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureAbyStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 6);
				fillWithBlocks(par1World, par3StructureBoundingBox, 3, 1, 2, 3, 1, 4, AbyssalCraft.abybrick, AbyssalCraft.abybrick, false);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 5, 3, 1, 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 5, 3, 1, 5, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 5, 3, 2, 2, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 5, 3, 2, 4, par3StructureBoundingBox);
				int i;

				for (i = 2; i <= 4; ++i)
				{
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 5, 2, 1, i, par3StructureBoundingBox);
				}

				if (!hasMadeChest)
				{
					i = getYWithOffset(2);
					int j = getXWithOffset(3, 3);
					int k = getZWithOffset(3, 3);

					if (par3StructureBoundingBox.isVecInside(j, i, k))
					{
						hasMadeChest = true;
						generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 2, 3, strongholdChestContents, ChestGenHooks.getCount(STRONGHOLD_CORRIDOR, par2Random));
					}
				}

				return true;
			}
		}
	}

	public static class RoomCrossing extends StructureAbyStrongholdPieces.Stronghold
	{
		/**
		 * Items that could generate in the chest that is located in Stronghold Room Crossing.
		 */
		public static final WeightedRandomChestContent[] strongholdRoomCrossingChestContents = new WeightedRandomChestContent[] {new WeightedRandomChestContent(AbyssalCraft.abyingot, 0, 1, 5, 10), new WeightedRandomChestContent(AbyssalCraft.Cingot, 0, 1, 5, 5), new WeightedRandomChestContent(AbyssalCraft.Cpearl, 0, 1, 3, 5), new WeightedRandomChestContent(AbyssalCraft.Coralium, 0, 3, 8, 10), new WeightedRandomChestContent(Items.golden_apple, 0, 1, 3, 15), new WeightedRandomChestContent(AbyssalCraft.MRE, 0, 1, 1, 10), new WeightedRandomChestContent(AbyssalCraft.pickaxeA, 0, 1, 1, 1)};
		protected int roomType;
		public RoomCrossing() {}

		public RoomCrossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			field_143013_d = getRandomDoor(par2Random);
			boundingBox = par3StructureBoundingBox;
			roomType = par2Random.nextInt(5);
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143012_a(par1NBTTagCompound);
			par1NBTTagCompound.setInteger("Type", roomType);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143011_b(par1NBTTagCompound);
			roomType = par1NBTTagCompound.getInteger("Type");
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			getNextComponentNormal((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 4, 1);
			getNextComponentX((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 4);
			getNextComponentZ((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 4);
		}

		public static StructureAbyStrongholdPieces.RoomCrossing findValidPlacement(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -1, 0, 11, 7, 11, par5);
			/**
			 * returns false if the Structure Bounding Box goes below 10
			 */
			return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new StructureAbyStrongholdPieces.RoomCrossing(par6, par1Random, structureboundingbox, par5) : null;
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
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 10, 6, 10, true, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, field_143013_d, 4, 1, 0);
				fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 10, 6, 3, 10, Blocks.air, Blocks.air, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 4, 0, 3, 6, Blocks.air, Blocks.air, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 10, 1, 4, 10, 3, 6, Blocks.air, Blocks.air, false);
				int i;

				switch (roomType)
				{
				case 0:
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 5, 1, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 5, 2, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 5, 3, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, Blocks.torch, 0, 4, 3, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, Blocks.torch, 0, 6, 3, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, Blocks.torch, 0, 5, 3, 4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, Blocks.torch, 0, 5, 3, 6, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 4, 1, 4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 4, 1, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 4, 1, 6, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 6, 1, 4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 6, 1, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 6, 1, 6, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 5, 1, 4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyslab1, 0, 5, 1, 6, par3StructureBoundingBox);
					break;
				case 1:
					for (i = 0; i < 5; ++i)
					{
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 3, 1, 3 + i, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 7, 1, 3 + i, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 3 + i, 1, 3, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 3 + i, 1, 7, par3StructureBoundingBox);
					}

					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 5, 1, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 5, 2, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 5, 3, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, Blocks.flowing_water, 0, 5, 4, 5, par3StructureBoundingBox);
					break;
				case 2:
					for (i = 1; i <= 9; ++i)
					{
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 1, 3, i, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 9, 3, i, par3StructureBoundingBox);
					}

					for (i = 1; i <= 9; ++i)
					{
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, i, 3, 1, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, i, 3, 9, par3StructureBoundingBox);
					}

					placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 5, 1, 4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 5, 1, 6, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 5, 3, 4, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 5, 3, 6, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 4, 1, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 6, 1, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 4, 3, 5, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 6, 3, 5, par3StructureBoundingBox);

					for (i = 1; i <= 3; ++i)
					{
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 4, i, 4, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 6, i, 4, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 4, i, 6, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.Darkstone_cobble, 0, 6, i, 6, par3StructureBoundingBox);
					}

					placeBlockAtCurrentPosition(par1World, Blocks.torch, 0, 5, 3, 5, par3StructureBoundingBox);

					for (i = 2; i <= 8; ++i)
					{
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.DLTplank, 0, 2, 3, i, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.DLTplank, 0, 3, 3, i, par3StructureBoundingBox);

						if (i <= 3 || i >= 7)
						{
							placeBlockAtCurrentPosition(par1World, AbyssalCraft.DLTplank, 0, 4, 3, i, par3StructureBoundingBox);
							placeBlockAtCurrentPosition(par1World, AbyssalCraft.DLTplank, 0, 5, 3, i, par3StructureBoundingBox);
							placeBlockAtCurrentPosition(par1World, AbyssalCraft.DLTplank, 0, 6, 3, i, par3StructureBoundingBox);
						}

						placeBlockAtCurrentPosition(par1World, AbyssalCraft.DLTplank, 0, 7, 3, i, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.DLTplank, 0, 8, 3, i, par3StructureBoundingBox);
					}

					placeBlockAtCurrentPosition(par1World, Blocks.ladder, getMetadataWithOffset(Blocks.ladder, 4), 9, 1, 3, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, Blocks.ladder, getMetadataWithOffset(Blocks.ladder, 4), 9, 2, 3, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, Blocks.ladder, getMetadataWithOffset(Blocks.ladder, 4), 9, 3, 3, par3StructureBoundingBox);
					generateStructureChestContents(par1World, par3StructureBoundingBox, par2Random, 3, 4, 8, strongholdRoomCrossingChestContents, ChestGenHooks.getCount(STRONGHOLD_CROSSING, par2Random));
				}

				return true;
			}
		}
	}

	public static class StairsStraight extends StructureAbyStrongholdPieces.Stronghold
	{
		public StairsStraight() {}

		public StairsStraight(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			field_143013_d = getRandomDoor(par2Random);
			boundingBox = par3StructureBoundingBox;
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			getNextComponentNormal((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
		}

		public static StructureAbyStrongholdPieces.StairsStraight findValidPlacement(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -7, 0, 5, 11, 8, par5);
			/**
			 * returns false if the Structure Bounding Box goes below 10
			 */
			return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new StructureAbyStrongholdPieces.StairsStraight(par6, par1Random, structureboundingbox, par5) : null;
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
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 10, 7, true, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, field_143013_d, 1, 7, 0);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, StructureAbyStrongholdPieces.Stronghold.Door.OPENING, 1, 1, 7);
				int i = getMetadataWithOffset(AbyssalCraft.DCstairs, 2);

				for (int j = 0; j < 6; ++j)
				{
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.DCstairs, i, 1, 6 - j, 1 + j, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.DCstairs, i, 2, 6 - j, 1 + j, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.DCstairs, i, 3, 6 - j, 1 + j, par3StructureBoundingBox);

					if (j < 5)
					{
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 1, 5 - j, 1 + j, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 2, 5 - j, 1 + j, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 3, 5 - j, 1 + j, par3StructureBoundingBox);
					}
				}

				return true;
			}
		}
	}

	public static class Stairs2 extends StructureAbyStrongholdPieces.Stairs
	{
		public StructureAbyStrongholdPieces.PieceWeight strongholdPieceWeight;
		public StructureAbyStrongholdPieces.PortalRoom strongholdPortalRoom;
		public List<Stronghold> field_75026_c = new ArrayList<Stronghold>();
		public Stairs2() {}

		public Stairs2(int par1, Random par2Random, int par3, int par4)
		{
			super(0, par2Random, par3, par4);
		}

		@Override
		public ChunkPosition func_151553_a()
		{
			return strongholdPortalRoom != null ? strongholdPortalRoom.func_151553_a() : super.func_151553_a();
		}
	}

	public static class Prison extends StructureAbyStrongholdPieces.Stronghold
	{
		public Prison() {}

		public Prison(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			field_143013_d = getRandomDoor(par2Random);
			boundingBox = par3StructureBoundingBox;
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			getNextComponentNormal((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
		}

		public static StructureAbyStrongholdPieces.Prison findValidPlacement(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 9, 5, 11, par5);
			/**
			 * returns false if the Structure Bounding Box goes below 10
			 */
			return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new StructureAbyStrongholdPieces.Prison(par6, par1Random, structureboundingbox, par5) : null;
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
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 8, 4, 10, true, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, field_143013_d, 1, 1, 0);
				fillWithBlocks(par1World, par3StructureBoundingBox, 1, 1, 10, 3, 3, 10, Blocks.air, Blocks.air, false);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 1, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 3, 4, 3, 3, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 7, 4, 3, 7, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 9, 4, 3, 9, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 4, 4, 3, 6, AbyssalCraft.abyfence, AbyssalCraft.abyfence, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 5, 1, 5, 7, 3, 5, AbyssalCraft.abyfence, AbyssalCraft.abyfence, false);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, 0, 4, 3, 2, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, 0, 4, 3, 8, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Blocks.iron_door, getMetadataWithOffset(Blocks.iron_door, 3), 4, 1, 2, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Blocks.iron_door, getMetadataWithOffset(Blocks.iron_door, 3) + 8, 4, 2, 2, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Blocks.iron_door, getMetadataWithOffset(Blocks.iron_door, 3), 4, 1, 8, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Blocks.iron_door, getMetadataWithOffset(Blocks.iron_door, 3) + 8, 4, 2, 8, par3StructureBoundingBox);
				return true;
			}
		}
	}

	public static class LeftTurn extends StructureAbyStrongholdPieces.Stronghold
	{
		public LeftTurn() {}

		public LeftTurn(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			field_143013_d = getRandomDoor(par2Random);
			boundingBox = par3StructureBoundingBox;
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			if (coordBaseMode != 2 && coordBaseMode != 3)
			{
				getNextComponentZ((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
			}
			else
			{
				getNextComponentX((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
			}
		}

		public static StructureAbyStrongholdPieces.LeftTurn findValidPlacement(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 5, par5);
			/**
			 * returns false if the Structure Bounding Box goes below 10
			 */
			return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new StructureAbyStrongholdPieces.LeftTurn(par6, par1Random, structureboundingbox, par5) : null;
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
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 4, true, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, field_143013_d, 1, 1, 0);

				if (coordBaseMode != 2 && coordBaseMode != 3)
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air, Blocks.air, false);
				}
				else
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air, Blocks.air, false);
				}

				return true;
			}
		}
	}

	public static class RightTurn extends StructureAbyStrongholdPieces.LeftTurn
	{
		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			if (coordBaseMode != 2 && coordBaseMode != 3)
			{
				getNextComponentX((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
			}
			else
			{
				getNextComponentZ((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 1, 1);
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
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 4, 4, 4, true, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, field_143013_d, 1, 1, 0);

				if (coordBaseMode != 2 && coordBaseMode != 3)
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 0, 1, 1, 0, 3, 3, Blocks.air, Blocks.air, false);
				}
				else
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 4, 1, 1, 4, 3, 3, Blocks.air, Blocks.air, false);
				}

				return true;
			}
		}
	}

	static class Stones extends StructureComponent.BlockSelector
	{
		private Stones() {}

		/**
		 * picks Block Ids and Metadata (Silverfish)
		 */
		@Override
		public void selectBlocks(Random par1Random, int par2, int par3, int par4, boolean par5)
		{
			if (par5)
			{
				field_151562_a = AbyssalCraft.abybrick;
				float f = par1Random.nextFloat();

				if (f < 0.2F)
				{
					selectedBlockMetaData = 0;
				}
				else if (f < 0.5F)
				{
					selectedBlockMetaData = 0;
				}
				else if (f < 0.55F)
				{
					field_151562_a = AbyssalCraft.abybrick;
					selectedBlockMetaData = 0;
				}
				else
				{
					selectedBlockMetaData = 0;
				}
			}
			else
			{
				field_151562_a = Blocks.air;
				selectedBlockMetaData = 0;
			}
		}

		Stones(Object par1StructureStrongholdPieceWeight2)
		{
			this();
		}
	}

	public abstract static class Stronghold extends StructureComponent
	{
		protected StructureAbyStrongholdPieces.Stronghold.Door field_143013_d;
		public Stronghold()
		{
			field_143013_d = StructureAbyStrongholdPieces.Stronghold.Door.OPENING;
		}

		protected Stronghold(int par1)
		{
			super(par1);
			field_143013_d = StructureAbyStrongholdPieces.Stronghold.Door.OPENING;
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			par1NBTTagCompound.setString("EntryDoor", field_143013_d.name());
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			field_143013_d = StructureAbyStrongholdPieces.Stronghold.Door.valueOf(par1NBTTagCompound.getString("EntryDoor"));
		}

		/**
		 * builds a door of the enumerated types (empty opening is a door)
		 */
		protected void placeDoor(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox, StructureAbyStrongholdPieces.Stronghold.Door par4EnumDoor, int par5, int par6, int par7)
		{
			switch (StructureAbyStrongholdPieces.SwitchDoor.doorEnum[par4EnumDoor.ordinal()])
			{
			case 1:
			default:
				fillWithBlocks(par1World, par3StructureBoundingBox, par5, par6, par7, par5 + 3 - 1, par6 + 3 - 1, par7, Blocks.air, Blocks.air, false);
				break;
			case 2:
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5, par6, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Blocks.wooden_door, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Blocks.wooden_door, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
				break;
			case 3:
				placeBlockAtCurrentPosition(par1World, Blocks.air, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Blocks.air, 0, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, 0, par5, par6, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abyfence, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
				break;
			case 4:
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5, par6, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5, par6 + 1, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5, par6 + 2, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5 + 1, par6 + 2, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5 + 2, par6 + 2, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5 + 2, par6 + 1, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, par5 + 2, par6, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Blocks.iron_door, 0, par5 + 1, par6, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, Blocks.iron_door, 8, par5 + 1, par6 + 1, par7, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.Abybutton, getMetadataWithOffset(AbyssalCraft.Abybutton, 4), par5 + 2, par6 + 1, par7 + 1, par3StructureBoundingBox);
				placeBlockAtCurrentPosition(par1World, AbyssalCraft.Abybutton, getMetadataWithOffset(AbyssalCraft.Abybutton, 3), par5 + 2, par6 + 1, par7 - 1, par3StructureBoundingBox);
			}

		}

		protected StructureAbyStrongholdPieces.Stronghold.Door getRandomDoor(Random par1Random)
		{
			int i = par1Random.nextInt(5);

			switch (i)
			{
			case 0:
			case 1:
			default:
				return StructureAbyStrongholdPieces.Stronghold.Door.OPENING;
			case 2:
				return StructureAbyStrongholdPieces.Stronghold.Door.WOOD_DOOR;
			case 3:
				return StructureAbyStrongholdPieces.Stronghold.Door.GRATES;
			case 4:
				return StructureAbyStrongholdPieces.Stronghold.Door.IRON_DOOR;
			}
		}

		/**
		 * Gets the next component in any cardinal direction
		 */
		protected StructureComponent getNextComponentNormal(StructureAbyStrongholdPieces.Stairs2 par1ComponentStrongholdStairs2, List<Stronghold> par2List, Random par3Random, int par4, int par5)
		{
			switch (coordBaseMode)
			{
			case 0:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par4, boundingBox.minY + par5, boundingBox.maxZ + 1, coordBaseMode, getComponentType());
			case 1:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par5, boundingBox.minZ + par4, coordBaseMode, getComponentType());
			case 2:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par4, boundingBox.minY + par5, boundingBox.minZ - 1, coordBaseMode, getComponentType());
			case 3:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par5, boundingBox.minZ + par4, coordBaseMode, getComponentType());
			default:
				return null;
			}
		}

		/**
		 * Gets the next component in the +/- X direction
		 */
		protected StructureComponent getNextComponentX(StructureAbyStrongholdPieces.Stairs2 par1ComponentStrongholdStairs2, List<Stronghold> par2List, Random par3Random, int par4, int par5)
		{
			switch (coordBaseMode)
			{
			case 0:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par4, boundingBox.minZ + par5, 1, getComponentType());
			case 1:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.minZ - 1, 2, getComponentType());
			case 2:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX - 1, boundingBox.minY + par4, boundingBox.minZ + par5, 1, getComponentType());
			case 3:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.minZ - 1, 2, getComponentType());
			default:
				return null;
			}
		}

		/**
		 * Gets the next component in the +/- Z direction
		 */
		protected StructureComponent getNextComponentZ(StructureAbyStrongholdPieces.Stairs2 par1ComponentStrongholdStairs2, List<Stronghold> par2List, Random par3Random, int par4, int par5)
		{
			switch (coordBaseMode)
			{
			case 0:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par4, boundingBox.minZ + par5, 3, getComponentType());
			case 1:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.maxZ + 1, 0, getComponentType());
			case 2:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.maxX + 1, boundingBox.minY + par4, boundingBox.minZ + par5, 3, getComponentType());
			case 3:
				return StructureAbyStrongholdPieces.getNextValidComponent(par1ComponentStrongholdStairs2, par2List, par3Random, boundingBox.minX + par5, boundingBox.minY + par4, boundingBox.maxZ + 1, 0, getComponentType());
			default:
				return null;
			}
		}

		/**
		 * returns false if the Structure Bounding Box goes below 10
		 */
		protected static boolean canStrongholdGoDeeper(StructureBoundingBox par0StructureBoundingBox)
		{
			return par0StructureBoundingBox != null && par0StructureBoundingBox.minY > 10;
		}

		public static enum Door
		{
			OPENING,
			WOOD_DOOR,
			GRATES,
			IRON_DOOR
		}
	}

	public static class Crossing extends StructureAbyStrongholdPieces.Stronghold
	{
		private boolean field_74996_b;
		private boolean field_74997_c;
		private boolean field_74995_d;
		private boolean field_74999_h;
		public Crossing() {}

		public Crossing(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			field_143013_d = getRandomDoor(par2Random);
			boundingBox = par3StructureBoundingBox;
			field_74996_b = par2Random.nextBoolean();
			field_74997_c = par2Random.nextBoolean();
			field_74995_d = par2Random.nextBoolean();
			field_74999_h = par2Random.nextInt(3) > 0;
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143012_a(par1NBTTagCompound);
			par1NBTTagCompound.setBoolean("leftLow", field_74996_b);
			par1NBTTagCompound.setBoolean("leftHigh", field_74997_c);
			par1NBTTagCompound.setBoolean("rightLow", field_74995_d);
			par1NBTTagCompound.setBoolean("rightHigh", field_74999_h);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143011_b(par1NBTTagCompound);
			field_74996_b = par1NBTTagCompound.getBoolean("leftLow");
			field_74997_c = par1NBTTagCompound.getBoolean("leftHigh");
			field_74995_d = par1NBTTagCompound.getBoolean("rightLow");
			field_74999_h = par1NBTTagCompound.getBoolean("rightHigh");
		}

		/**
		 * Initiates construction of the Structure Component picked, at the current Location of StructGen
		 */
		@Override
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void buildComponent(StructureComponent par1StructureComponent, List par2List, Random par3Random)
		{
			int i = 3;
			int j = 5;

			if (coordBaseMode == 1 || coordBaseMode == 2)
			{
				i = 8 - i;
				j = 8 - j;
			}

			getNextComponentNormal((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, 5, 1);

			if (field_74996_b)
			{
				getNextComponentX((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, i, 1);
			}

			if (field_74997_c)
			{
				getNextComponentX((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, j, 7);
			}

			if (field_74995_d)
			{
				getNextComponentZ((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, i, 1);
			}

			if (field_74999_h)
			{
				getNextComponentZ((StructureAbyStrongholdPieces.Stairs2)par1StructureComponent, par2List, par3Random, j, 7);
			}
		}

		public static StructureAbyStrongholdPieces.Crossing findValidPlacement(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5, int par6)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -4, -3, 0, 10, 9, 11, par5);
			/**
			 * returns false if the Structure Bounding Box goes below 10
			 */
			return canStrongholdGoDeeper(structureboundingbox) && StructureComponent.findIntersecting(par0List, structureboundingbox) == null ? new StructureAbyStrongholdPieces.Crossing(par6, par1Random, structureboundingbox, par5) : null;
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
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 0, 0, 0, 9, 8, 10, true, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				placeDoor(par1World, par2Random, par3StructureBoundingBox, field_143013_d, 4, 3, 0);

				if (field_74996_b)
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 0, 3, 1, 0, 5, 3, Blocks.air, Blocks.air, false);
				}

				if (field_74995_d)
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 9, 3, 1, 9, 5, 3, Blocks.air, Blocks.air, false);
				}

				if (field_74997_c)
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 0, 5, 7, 0, 7, 9, Blocks.air, Blocks.air, false);
				}

				if (field_74999_h)
				{
					fillWithBlocks(par1World, par3StructureBoundingBox, 9, 5, 7, 9, 7, 9, Blocks.air, Blocks.air, false);
				}

				fillWithBlocks(par1World, par3StructureBoundingBox, 5, 1, 10, 7, 3, 10, Blocks.air, Blocks.air, false);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 2, 1, 8, 2, 6, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 4, 1, 5, 4, 4, 9, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 8, 1, 5, 8, 4, 9, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 4, 7, 3, 4, 9, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 1, 3, 5, 3, 3, 6, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithBlocks(par1World, par3StructureBoundingBox, 1, 3, 4, 3, 3, 4, AbyssalCraft.abyslab1, AbyssalCraft.abyslab1, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 1, 4, 6, 3, 4, 6, AbyssalCraft.abyslab1, AbyssalCraft.abyslab1, false);
				fillWithRandomizedBlocks(par1World, par3StructureBoundingBox, 5, 1, 7, 7, 1, 8, false, par2Random, StructureAbyStrongholdPieces.strongholdStones);
				fillWithBlocks(par1World, par3StructureBoundingBox, 5, 1, 9, 7, 1, 9, AbyssalCraft.abyslab1, AbyssalCraft.abyslab1, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 5, 2, 7, 7, 2, 7, AbyssalCraft.abyslab1, AbyssalCraft.abyslab1, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 4, 5, 7, 4, 5, 9, AbyssalCraft.abyslab1, AbyssalCraft.abyslab1, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 8, 5, 7, 8, 5, 9, AbyssalCraft.abyslab1, AbyssalCraft.abyslab1, false);
				fillWithBlocks(par1World, par3StructureBoundingBox, 5, 5, 7, 7, 5, 9, AbyssalCraft.abyslab2, AbyssalCraft.abyslab2, false);
				placeBlockAtCurrentPosition(par1World, Blocks.torch, 0, 6, 5, 6, par3StructureBoundingBox);
				return true;
			}
		}
	}

	public static class Corridor extends StructureAbyStrongholdPieces.Stronghold
	{
		private int field_74993_a;
		public Corridor() {}

		public Corridor(int par1, Random par2Random, StructureBoundingBox par3StructureBoundingBox, int par4)
		{
			super(par1);
			coordBaseMode = par4;
			boundingBox = par3StructureBoundingBox;
			field_74993_a = par4 != 2 && par4 != 0 ? par3StructureBoundingBox.getXSize() : par3StructureBoundingBox.getZSize();
		}

		@Override
		protected void func_143012_a(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143012_a(par1NBTTagCompound);
			par1NBTTagCompound.setInteger("Steps", field_74993_a);
		}

		@Override
		protected void func_143011_b(NBTTagCompound par1NBTTagCompound)
		{
			super.func_143011_b(par1NBTTagCompound);
			field_74993_a = par1NBTTagCompound.getInteger("Steps");
		}

		public static StructureBoundingBox func_74992_a(List<Stronghold> par0List, Random par1Random, int par2, int par3, int par4, int par5)
		{
			StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, 4, par5);
			StructureComponent structurecomponent = StructureComponent.findIntersecting(par0List, structureboundingbox);

			if (structurecomponent == null)
				return null;
			else
			{
				if (structurecomponent.getBoundingBox().minY == structureboundingbox.minY)
				{
					for (int i1 = 3; i1 >= 1; --i1)
					{
						structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, i1 - 1, par5);

						if (!structurecomponent.getBoundingBox().intersectsWith(structureboundingbox))
							return StructureBoundingBox.getComponentToAddBoundingBox(par2, par3, par4, -1, -1, 0, 5, 5, i1, par5);
					}
				}

				return null;
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
				for (int i = 0; i < field_74993_a; ++i)
				{
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 0, 0, i, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 1, 0, i, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 2, 0, i, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 3, 0, i, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 4, 0, i, par3StructureBoundingBox);

					for (int j = 1; j <= 3; ++j)
					{
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 0, j, i, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, Blocks.air, 0, 1, j, i, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, Blocks.air, 0, 2, j, i, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, Blocks.air, 0, 3, j, i, par3StructureBoundingBox);
						placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 4, j, i, par3StructureBoundingBox);
					}

					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 0, 4, i, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 1, 4, i, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 2, 4, i, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 3, 4, i, par3StructureBoundingBox);
					placeBlockAtCurrentPosition(par1World, AbyssalCraft.abybrick, 0, 4, 4, i, par3StructureBoundingBox);
				}

				return true;
			}
		}
	}

	static final class SwitchDoor
	{
		static final int[] doorEnum = new int[StructureAbyStrongholdPieces.Stronghold.Door.values().length];
		static
		{
			try
			{
				doorEnum[StructureAbyStrongholdPieces.Stronghold.Door.OPENING.ordinal()] = 1;
			}
			catch (NoSuchFieldError var4)
			{
				;
			}

			try
			{
				doorEnum[StructureAbyStrongholdPieces.Stronghold.Door.WOOD_DOOR.ordinal()] = 2;
			}
			catch (NoSuchFieldError var3)
			{
				;
			}

			try
			{
				doorEnum[StructureAbyStrongholdPieces.Stronghold.Door.GRATES.ordinal()] = 3;
			}
			catch (NoSuchFieldError var2)
			{
				;
			}

			try
			{
				doorEnum[StructureAbyStrongholdPieces.Stronghold.Door.IRON_DOOR.ordinal()] = 4;
			}
			catch (NoSuchFieldError var1)
			{
				;
			}
		}
	}

	static class PieceWeight
	{
		public Class<?> pieceClass;
		/**
		 * This basically keeps track of the 'epicness' of a structure. Epic structure components have a higher
		 * 'weight', and Structures may only grow up to a certain 'weight' before generation is stopped
		 */
		public final int pieceWeight;
		public int instancesSpawned;
		/**
		 * How many Structure Pieces of this type may spawn in a structure
		 */
		public int instancesLimit;
		public PieceWeight(Class<?> par1Class, int par2, int par3)
		{
			pieceClass = par1Class;
			pieceWeight = par2;
			instancesLimit = par3;
		}

		public boolean canSpawnMoreStructuresOfType(int par1)
		{
			return instancesLimit == 0 || instancesSpawned < instancesLimit;
		}

		public boolean canSpawnMoreStructures()
		{
			return instancesLimit == 0 || instancesSpawned < instancesLimit;
		}
	}
}