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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockPortal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.BlockWorldState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.google.common.cache.LoadingCache;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.world.TeleporterOmothol;

public class BlockOmotholPortal extends BlockBreakable {

	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z});
	protected static final AxisAlignedBB field_185683_b = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
	protected static final AxisAlignedBB field_185684_c = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
	protected static final AxisAlignedBB field_185685_d = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);

	public BlockOmotholPortal()
	{
		super(Material.PORTAL , false);
		setDefaultState(blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
		setTickRandomly(true);
		setHardness(-1.0F);
		setLightLevel(0.75F);
	}


	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		switch (state.getValue(AXIS))
		{
		case X:
			return field_185683_b;
		case Y:
		default:
			return field_185685_d;
		case Z:
			return field_185684_c;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos)
	{
		return NULL_AABB;
	}

	public static int getMetaForAxis(EnumFacing.Axis axis)
	{
		return axis == EnumFacing.Axis.X ? 1 : axis == EnumFacing.Axis.Z ? 2 : 0;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	public static boolean tryToCreatePortal(World par1World, BlockPos pos)
	{
		BlockOmotholPortal.Size blockportal$size = new BlockOmotholPortal.Size(par1World, pos, EnumFacing.Axis.X);

		if (blockportal$size.func_150860_b() && blockportal$size.field_150864_e == 0)
		{
			blockportal$size.func_150859_c();
			return true;
		}
		else
		{
			BlockOmotholPortal.Size blockportal$size1 = new BlockOmotholPortal.Size(par1World, pos, EnumFacing.Axis.Z);

			if (blockportal$size1.func_150860_b() && blockportal$size1.field_150864_e == 0)
			{
				blockportal$size1.func_150859_c();
				return true;
			} else
				return false;
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block neighborBlock)
	{
		EnumFacing.Axis enumfacing$axis = state.getValue(AXIS);

		if (enumfacing$axis == EnumFacing.Axis.X)
		{
			BlockOmotholPortal.Size blockportal$size = new BlockOmotholPortal.Size(worldIn, pos, EnumFacing.Axis.X);

			if (!blockportal$size.func_150860_b() || blockportal$size.field_150864_e < blockportal$size.field_150868_h * blockportal$size.field_150862_g)
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
		else if (enumfacing$axis == EnumFacing.Axis.Z)
		{
			BlockOmotholPortal.Size blockportal$size1 = new BlockOmotholPortal.Size(worldIn, pos, EnumFacing.Axis.Z);

			if (!blockportal$size1.func_150860_b() || blockportal$size1.field_150864_e < blockportal$size1.field_150868_h * blockportal$size1.field_150862_g)
				worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockState state, IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		EnumFacing.Axis enumfacing$axis = null;

		if (state.getBlock() == this)
		{
			enumfacing$axis = state.getValue(AXIS);

			if (enumfacing$axis == null)
				return false;

			if (enumfacing$axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST)
				return false;

			if (enumfacing$axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH)
				return false;
		}

		boolean flag = worldIn.getBlockState(pos.west()).getBlock() == this && worldIn.getBlockState(pos.west(2)).getBlock() != this;
		boolean flag1 = worldIn.getBlockState(pos.east()).getBlock() == this && worldIn.getBlockState(pos.east(2)).getBlock() != this;
		boolean flag2 = worldIn.getBlockState(pos.north()).getBlock() == this && worldIn.getBlockState(pos.north(2)).getBlock() != this;
		boolean flag3 = worldIn.getBlockState(pos.south()).getBlock() == this && worldIn.getBlockState(pos.south(2)).getBlock() != this;
		boolean flag4 = flag || flag1 || enumfacing$axis == EnumFacing.Axis.X;
		boolean flag5 = flag2 || flag3 || enumfacing$axis == EnumFacing.Axis.Z;
		return flag4 && side == EnumFacing.WEST ? true : flag4 && side == EnumFacing.EAST ? true : flag5 && side == EnumFacing.NORTH ? true : flag5 && side == EnumFacing.SOUTH;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, BlockPos pos, IBlockState state, Entity par5Entity)
	{

		if (!par5Entity.isRiding() && !par5Entity.isBeingRidden() && par5Entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP thePlayer = (EntityPlayerMP)par5Entity;
			thePlayer.addStat(AbyssalCraft.enterOmothol, 1);
			if (thePlayer.timeUntilPortal > 0)
				thePlayer.timeUntilPortal = 10;
			else if (thePlayer.dimension != AbyssalCraft.configDimId3)
			{
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, AbyssalCraft.configDimId3, new TeleporterOmothol(thePlayer.mcServer.worldServerForDimension(AbyssalCraft.configDimId3)));
			}
			else {
				thePlayer.timeUntilPortal = 10;
				thePlayer.mcServer.getPlayerList().transferPlayerToDimension(thePlayer, AbyssalCraft.configDimId2, new TeleporterOmothol(thePlayer.mcServer.worldServerForDimension(AbyssalCraft.configDimId2)));
			}
		}
	}


	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getItem(World par1World, BlockPos pos, IBlockState state)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.TRANSLUCENT;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(AXIS, (meta & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return getMetaForAxis(state.getValue(AXIS));
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {AXIS});
	}

	public BlockPattern.PatternHelper func_181089_f(World p_181089_1_, BlockPos p_181089_2_)
	{
		EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
		BlockOmotholPortal.Size blockportal$size = new BlockOmotholPortal.Size(p_181089_1_, p_181089_2_, EnumFacing.Axis.X);
		LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(p_181089_1_, true);

		if (!blockportal$size.func_150860_b())
		{
			enumfacing$axis = EnumFacing.Axis.X;
			blockportal$size = new BlockOmotholPortal.Size(p_181089_1_, p_181089_2_, EnumFacing.Axis.Z);
		}

		if (!blockportal$size.func_150860_b())
			return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
		else
		{
			int[] aint = new int[EnumFacing.AxisDirection.values().length];
			EnumFacing enumfacing = blockportal$size.field_150866_c.rotateYCCW();
			BlockPos blockpos = blockportal$size.field_150861_f.up(blockportal$size.func_181100_a() - 1);

			for (EnumFacing.AxisDirection enumfacing$axisdirection : EnumFacing.AxisDirection.values())
			{
				BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection ? blockpos : blockpos.offset(blockportal$size.field_150866_c, blockportal$size.func_181101_b() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.func_181101_b(), blockportal$size.func_181100_a(), 1);

				for (int i = 0; i < blockportal$size.func_181101_b(); ++i)
					for (int j = 0; j < blockportal$size.func_181100_a(); ++j)
					{
						BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(i, j, 1);

						if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getMaterial() != Material.AIR)
							++aint[enumfacing$axisdirection.ordinal()];
					}
			}

			EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;

			for (EnumFacing.AxisDirection enumfacing$axisdirection2 : EnumFacing.AxisDirection.values())
				if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()])
					enumfacing$axisdirection1 = enumfacing$axisdirection2;

			return new BlockPattern.PatternHelper(enumfacing.getAxisDirection() == enumfacing$axisdirection1 ? blockpos : blockpos.offset(blockportal$size.field_150866_c, blockportal$size.func_181101_b() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.func_181101_b(), blockportal$size.func_181100_a(), 1);
		}
	}

	public static class Size
	{
		private final World world;
		private final EnumFacing.Axis axis;
		private final EnumFacing field_150866_c;
		private final EnumFacing field_150863_d;
		private int field_150864_e = 0;
		private BlockPos field_150861_f;
		private int field_150862_g;
		private int field_150868_h;

		public Size(World worldIn, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_)
		{
			world = worldIn;
			axis = p_i45694_3_;

			if (p_i45694_3_ == EnumFacing.Axis.X)
			{
				field_150863_d = EnumFacing.EAST;
				field_150866_c = EnumFacing.WEST;
			}
			else
			{
				field_150863_d = EnumFacing.NORTH;
				field_150866_c = EnumFacing.SOUTH;
			}

			for (BlockPos blockpos = p_i45694_2_; p_i45694_2_.getY() > blockpos.getY() - 21 && p_i45694_2_.getY() > 0 && func_150857_a(worldIn.getBlockState(p_i45694_2_.down()).getBlock()); p_i45694_2_ = p_i45694_2_.down())
				;

			int i = func_180120_a(p_i45694_2_, field_150863_d) - 1;

			if (i >= 0)
			{
				field_150861_f = p_i45694_2_.offset(field_150863_d, i);
				field_150868_h = func_180120_a(field_150861_f, field_150866_c);

				if (field_150868_h < 2 || field_150868_h > 21)
				{
					field_150861_f = null;
					field_150868_h = 0;
				}
			}

			if (field_150861_f != null)
				field_150862_g = func_150858_a();
		}

		protected int func_180120_a(BlockPos p_180120_1_, EnumFacing p_180120_2_)
		{
			int i;

			for (i = 0; i < 22; ++i)
			{
				BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);

				if (!func_150857_a(world.getBlockState(blockpos).getBlock()) || world.getBlockState(blockpos.down()).getBlock() != ACBlocks.omothol_stone)
					break;
			}

			Block block = world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
			return block == ACBlocks.omothol_stone ? i : 0;
		}

		public int func_181100_a()
		{
			return field_150862_g;
		}

		public int func_181101_b()
		{
			return field_150868_h;
		}

		protected int func_150858_a()
		{
			label24:

				for (field_150862_g = 0; field_150862_g < 21; ++field_150862_g)
					for (int i = 0; i < field_150868_h; ++i)
					{
						BlockPos blockpos = field_150861_f.offset(field_150866_c, i).up(field_150862_g);
						Block block = world.getBlockState(blockpos).getBlock();

						if (!func_150857_a(block))
							break label24;

						if (block == ACBlocks.omothol_gateway)
							++field_150864_e;

						if (i == 0)
						{
							block = world.getBlockState(blockpos.offset(field_150863_d)).getBlock();

							if (block != ACBlocks.omothol_stone)
								break label24;
						}
						else if (i == field_150868_h - 1)
						{
							block = world.getBlockState(blockpos.offset(field_150866_c)).getBlock();

							if (block != ACBlocks.omothol_stone)
								break label24;
						}
					}

		for (int j = 0; j < field_150868_h; ++j)
			if (world.getBlockState(field_150861_f.offset(field_150866_c, j).up(field_150862_g)).getBlock() != ACBlocks.omothol_stone)
			{
				field_150862_g = 0;
				break;
			}

		if (field_150862_g <= 21 && field_150862_g >= 3)
			return field_150862_g;
		else
		{
			field_150861_f = null;
			field_150868_h = 0;
			field_150862_g = 0;
			return 0;
		}
		}

		protected boolean func_150857_a(Block p_150857_1_)
		{
			return p_150857_1_.getMaterial(p_150857_1_.getDefaultState()) == Material.AIR || p_150857_1_ == ACBlocks.omothol_fire || p_150857_1_ == ACBlocks.omothol_gateway;
		}

		public boolean func_150860_b()
		{
			return field_150861_f != null && field_150868_h >= 2 && field_150868_h <= 21 && field_150862_g >= 3 && field_150862_g <= 21;
		}

		public void func_150859_c()
		{
			for (int i = 0; i < field_150868_h; ++i)
			{
				BlockPos blockpos = field_150861_f.offset(field_150866_c, i);

				for (int j = 0; j < field_150862_g; ++j)
					world.setBlockState(blockpos.up(j), ACBlocks.omothol_gateway.getDefaultState().withProperty(BlockPortal.AXIS, axis), 2);
			}
		}
	}
}