package com.shinoow.abyssalcraft.common.structures;

import java.util.Arrays;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.init.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.Facing;
import net.minecraft.world.gen.structure.StructureComponent;

import com.shinoow.abyssalcraft.AbyssalCraft;

public abstract class StructureComponentModded extends StructureComponent {

	/* A bunch of lists to use for metadata block placement in structures generated with this stuff */
	protected List<Block> doors = Arrays.asList(Blocks.wooden_door, Blocks.iron_door);
	protected List<Block> stairs = Arrays.asList(Blocks.stone_stairs, Blocks.oak_stairs, Blocks.nether_brick_stairs, Blocks.stone_brick_stairs,
			Blocks.sandstone_stairs, AbyssalCraft.abystairs, AbyssalCraft.DCstairs, AbyssalCraft.DBstairs, AbyssalCraft.DLTstairs, AbyssalCraft.abydreadbrickstairs,
			AbyssalCraft.dreadbrickstairs, AbyssalCraft.cstonebrickstairs, AbyssalCraft.ethaxiumstairs, AbyssalCraft.darkethaxiumstairs);
	protected List<Block> buttons = Arrays.asList(Blocks.stone_button, Blocks.wooden_button, AbyssalCraft.Abybutton,
			AbyssalCraft.DSbutton, AbyssalCraft.DLTbutton, AbyssalCraft.cstonebutton);
	protected List<Block> redstone = Arrays.asList(Blocks.lever, Blocks.piston, Blocks.sticky_piston, Blocks.dispenser);

	public StructureComponentModded() {}

	protected StructureComponentModded(int par1){
		super(par1);
	}

	@Override
	protected int getMetadataWithOffset(Block block, int meta)
	{
		if (block == Blocks.rail)
		{
			if (coordBaseMode == 1 || coordBaseMode == 3)
			{
				if (meta == 1)
					return 0;

				return 1;
			}
		}
		else if (!doors.contains(block))
		{
			if (!stairs.contains(block))
			{
				if (block == Blocks.ladder)
				{
					if (coordBaseMode == 0)
					{
						if (meta == 2)
							return 3;

						if (meta == 3)
							return 2;
					}
					else if (coordBaseMode == 1)
					{
						if (meta == 2)
							return 4;

						if (meta == 3)
							return 5;

						if (meta == 4)
							return 2;

						if (meta == 5)
							return 3;
					}
					else if (coordBaseMode == 3)
					{
						if (meta == 2)
							return 5;

						if (meta == 3)
							return 4;

						if (meta == 4)
							return 2;

						if (meta == 5)
							return 3;
					}
				}
				else if (buttons.contains(block))
				{
					if (coordBaseMode == 0)
					{
						if (meta == 3)
							return 4;

						if (meta == 4)
							return 3;
					}
					else if (coordBaseMode == 1)
					{
						if (meta == 3)
							return 1;

						if (meta == 4)
							return 2;

						if (meta == 2)
							return 3;

						if (meta == 1)
							return 4;
					}
					else if (coordBaseMode == 3)
					{
						if (meta == 3)
							return 2;

						if (meta == 4)
							return 1;

						if (meta == 2)
							return 3;

						if (meta == 1)
							return 4;
					}
				}
				else if (block != Blocks.tripwire_hook && !(block instanceof BlockDirectional))
				{
					if (redstone.contains(block))
						if (coordBaseMode == 0)
						{
							if (meta == 2 || meta == 3)
								return Facing.oppositeSide[meta];
						}
						else if (coordBaseMode == 1)
						{
							if (meta == 2)
								return 4;

							if (meta == 3)
								return 5;

							if (meta == 4)
								return 2;

							if (meta == 5)
								return 3;
						}
						else if (coordBaseMode == 3)
						{
							if (meta == 2)
								return 5;

							if (meta == 3)
								return 4;

							if (meta == 4)
								return 2;

							if (meta == 5)
								return 3;
						}
				}
				else if (coordBaseMode == 0)
				{
					if (meta == 0 || meta == 2)
						return Direction.rotateOpposite[meta];
				}
				else if (coordBaseMode == 1)
				{
					if (meta == 2)
						return 1;

					if (meta == 0)
						return 3;

					if (meta == 1)
						return 2;

					if (meta == 3)
						return 0;
				}
				else if (coordBaseMode == 3)
				{
					if (meta == 2)
						return 3;

					if (meta == 0)
						return 1;

					if (meta == 1)
						return 2;

					if (meta == 3)
						return 0;
				}
			}
			else if (coordBaseMode == 0)
			{
				if (meta == 2)
					return 3;

				if (meta == 3)
					return 2;
			}
			else if (coordBaseMode == 1)
			{
				if (meta == 0)
					return 2;

				if (meta == 1)
					return 3;

				if (meta == 2)
					return 0;

				if (meta == 3)
					return 1;
			}
			else if (coordBaseMode == 3)
			{
				if (meta == 0)
					return 2;

				if (meta == 1)
					return 3;

				if (meta == 2)
					return 1;

				if (meta == 3)
					return 0;
			}
		}
		else if (coordBaseMode == 0)
		{
			if (meta == 0)
				return 2;

			if (meta == 2)
				return 0;
		}
		else
		{
			if (coordBaseMode == 1)
				return meta + 1 & 3;

			if (coordBaseMode == 3)
				return meta + 3 & 3;
		}

		return meta;
	}
}