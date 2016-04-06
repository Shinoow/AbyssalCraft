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
package com.shinoow.abyssalcraft.common.structures;

import net.minecraft.world.gen.structure.StructureComponent;

public abstract class StructureComponentModded extends StructureComponent {

	/* A bunch of lists to use for metadata block placement in structures generated with this stuff */
	//	protected List<Block> doors = Arrays.asList(Blocks.wooden_door, Blocks.iron_door);
	//	protected List<Block> stairs = Arrays.asList(Blocks.stone_stairs, Blocks.oak_stairs, Blocks.nether_brick_stairs, Blocks.stone_brick_stairs,
	//			Blocks.sandstone_stairs, AbyssalCraft.abystairs, AbyssalCraft.DCstairs, AbyssalCraft.DBstairs, AbyssalCraft.DLTstairs, AbyssalCraft.abydreadbrickstairs,
	//			AbyssalCraft.dreadbrickstairs, AbyssalCraft.cstonebrickstairs, AbyssalCraft.ethaxiumstairs, AbyssalCraft.darkethaxiumstairs);
	//	protected List<Block> buttons = Arrays.asList(Blocks.stone_button, Blocks.wooden_button, AbyssalCraft.Abybutton,
	//			AbyssalCraft.DSbutton, AbyssalCraft.DLTbutton, AbyssalCraft.cstonebutton);
	//	protected List<Block> redstone = Arrays.asList(Blocks.lever, Blocks.piston, Blocks.sticky_piston, Blocks.dispenser);

	public StructureComponentModded() {}

	protected StructureComponentModded(int par1){
		super(par1);
	}

	//	@Override
	//	protected int getMetadataWithOffset(Block blockIn, int meta)
	//	{
	//		if (blockIn == Blocks.rail)
	//		{
	//			if (coordBaseMode == EnumFacing.WEST || coordBaseMode == EnumFacing.EAST)
	//			{
	//				if (meta == 1)
	//					return 0;
	//
	//				return 1;
	//			}
	//		}
	//		else if (blockIn instanceof BlockDoor)
	//		{
	//			if (coordBaseMode == EnumFacing.SOUTH)
	//			{
	//				if (meta == 0)
	//					return 2;
	//
	//				if (meta == 2)
	//					return 0;
	//			}
	//			else
	//			{
	//				if (coordBaseMode == EnumFacing.WEST)
	//					return meta + 1 & 3;
	//
	//				if (coordBaseMode == EnumFacing.EAST)
	//					return meta + 3 & 3;
	//			}
	//		}
	//		else if (!stairs.contains(blockIn))
	//		{
	//			if (blockIn == Blocks.ladder)
	//			{
	//				if (coordBaseMode == EnumFacing.SOUTH)
	//				{
	//					if (meta == EnumFacing.NORTH.getIndex())
	//						return EnumFacing.SOUTH.getIndex();
	//
	//					if (meta == EnumFacing.SOUTH.getIndex())
	//						return EnumFacing.NORTH.getIndex();
	//				}
	//				else if (coordBaseMode == EnumFacing.WEST)
	//				{
	//					if (meta == EnumFacing.NORTH.getIndex())
	//						return EnumFacing.WEST.getIndex();
	//
	//					if (meta == EnumFacing.SOUTH.getIndex())
	//						return EnumFacing.EAST.getIndex();
	//
	//					if (meta == EnumFacing.WEST.getIndex())
	//						return EnumFacing.NORTH.getIndex();
	//
	//					if (meta == EnumFacing.EAST.getIndex())
	//						return EnumFacing.SOUTH.getIndex();
	//				}
	//				else if (coordBaseMode == EnumFacing.EAST)
	//				{
	//					if (meta == EnumFacing.NORTH.getIndex())
	//						return EnumFacing.EAST.getIndex();
	//
	//					if (meta == EnumFacing.SOUTH.getIndex())
	//						return EnumFacing.WEST.getIndex();
	//
	//					if (meta == EnumFacing.WEST.getIndex())
	//						return EnumFacing.NORTH.getIndex();
	//
	//					if (meta == EnumFacing.EAST.getIndex())
	//						return EnumFacing.SOUTH.getIndex();
	//				}
	//			}
	//			else if (buttons.contains(blockIn))
	//			{
	//				if (coordBaseMode == EnumFacing.SOUTH)
	//				{
	//					if (meta == 3)
	//						return 4;
	//
	//					if (meta == 4)
	//						return 3;
	//				}
	//				else if (coordBaseMode == EnumFacing.WEST)
	//				{
	//					if (meta == 3)
	//						return 1;
	//
	//					if (meta == 4)
	//						return 2;
	//
	//					if (meta == 2)
	//						return 3;
	//
	//					if (meta == 1)
	//						return 4;
	//				}
	//				else if (coordBaseMode == EnumFacing.EAST)
	//				{
	//					if (meta == 3)
	//						return 2;
	//
	//					if (meta == 4)
	//						return 1;
	//
	//					if (meta == 2)
	//						return 3;
	//
	//					if (meta == 1)
	//						return 4;
	//				}
	//			}
	//			else if (blockIn != Blocks.tripwire_hook && !(blockIn instanceof BlockDirectional))
	//			{
	//				if (redstone.contains(blockIn))
	//					if (coordBaseMode == EnumFacing.SOUTH)
	//					{
	//						if (meta == EnumFacing.NORTH.getIndex() || meta == EnumFacing.SOUTH.getIndex())
	//							return EnumFacing.getFront(meta).getOpposite().getIndex();
	//					}
	//					else if (coordBaseMode == EnumFacing.WEST)
	//					{
	//						if (meta == EnumFacing.NORTH.getIndex())
	//							return EnumFacing.WEST.getIndex();
	//
	//						if (meta == EnumFacing.SOUTH.getIndex())
	//							return EnumFacing.EAST.getIndex();
	//
	//						if (meta == EnumFacing.WEST.getIndex())
	//							return EnumFacing.NORTH.getIndex();
	//
	//						if (meta == EnumFacing.EAST.getIndex())
	//							return EnumFacing.SOUTH.getIndex();
	//					}
	//					else if (coordBaseMode == EnumFacing.EAST)
	//					{
	//						if (meta == EnumFacing.NORTH.getIndex())
	//							return EnumFacing.EAST.getIndex();
	//
	//						if (meta == EnumFacing.SOUTH.getIndex())
	//							return EnumFacing.WEST.getIndex();
	//
	//						if (meta == EnumFacing.WEST.getIndex())
	//							return EnumFacing.NORTH.getIndex();
	//
	//						if (meta == EnumFacing.EAST.getIndex())
	//							return EnumFacing.SOUTH.getIndex();
	//					}
	//			}
	//			else
	//			{
	//				EnumFacing enumfacing = EnumFacing.getHorizontal(meta);
	//
	//				if (coordBaseMode == EnumFacing.SOUTH)
	//				{
	//					if (enumfacing == EnumFacing.SOUTH || enumfacing == EnumFacing.NORTH)
	//						return enumfacing.getOpposite().getHorizontalIndex();
	//				}
	//				else if (coordBaseMode == EnumFacing.WEST)
	//				{
	//					if (enumfacing == EnumFacing.NORTH)
	//						return EnumFacing.WEST.getHorizontalIndex();
	//
	//					if (enumfacing == EnumFacing.SOUTH)
	//						return EnumFacing.EAST.getHorizontalIndex();
	//
	//					if (enumfacing == EnumFacing.WEST)
	//						return EnumFacing.NORTH.getHorizontalIndex();
	//
	//					if (enumfacing == EnumFacing.EAST)
	//						return EnumFacing.SOUTH.getHorizontalIndex();
	//				}
	//				else if (coordBaseMode == EnumFacing.EAST)
	//				{
	//					if (enumfacing == EnumFacing.NORTH)
	//						return EnumFacing.EAST.getHorizontalIndex();
	//
	//					if (enumfacing == EnumFacing.SOUTH)
	//						return EnumFacing.WEST.getHorizontalIndex();
	//
	//					if (enumfacing == EnumFacing.WEST)
	//						return EnumFacing.NORTH.getHorizontalIndex();
	//
	//					if (enumfacing == EnumFacing.EAST)
	//						return EnumFacing.SOUTH.getHorizontalIndex();
	//				}
	//			}
	//		}
	//		else if (coordBaseMode == EnumFacing.SOUTH)
	//		{
	//			if (meta == 2)
	//				return 3;
	//
	//			if (meta == 3)
	//				return 2;
	//		}
	//		else if (coordBaseMode == EnumFacing.WEST)
	//		{
	//			if (meta == 0)
	//				return 2;
	//
	//			if (meta == 1)
	//				return 3;
	//
	//			if (meta == 2)
	//				return 0;
	//
	//			if (meta == 3)
	//				return 1;
	//		}
	//		else if (coordBaseMode == EnumFacing.EAST)
	//		{
	//			if (meta == 0)
	//				return 2;
	//
	//			if (meta == 1)
	//				return 3;
	//
	//			if (meta == 2)
	//				return 1;
	//
	//			if (meta == 3)
	//				return 0;
	//		}
	//
	//		return meta;
	//	}
}