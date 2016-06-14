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
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.world.TeleporterAC;
import com.shinoow.abyssalcraft.lib.ACLib;

public class BlockDreadlandsPortal extends BlockBreakable {

	public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.<EnumFacing.Axis>create("axis", EnumFacing.Axis.class, new EnumFacing.Axis[] {EnumFacing.Axis.X, EnumFacing.Axis.Z});

	public BlockDreadlandsPortal()
	{
		super(Material.portal , false);
		setDefaultState(blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.X));
		setTickRandomly(true);
		setHardness(-1.0F);
		setLightLevel(0.75F);
		setStepSound(soundTypeGlass);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World par1World, BlockPos pos, IBlockState state)
	{
		return null;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{
		EnumFacing.Axis enumfacing$axis = worldIn.getBlockState(pos).getValue(AXIS);
		float f = 0.125F;
		float f1 = 0.125F;

		if (enumfacing$axis == EnumFacing.Axis.X)
			f = 0.5F;

		if (enumfacing$axis == EnumFacing.Axis.Z)
			f1 = 0.5F;

		setBlockBounds(0.5F - f, 0.0F, 0.5F - f1, 0.5F + f, 1.0F, 0.5F + f1);
	}

	public static int getMetaForAxis(EnumFacing.Axis axis)
	{
		return axis == EnumFacing.Axis.X ? 1 : axis == EnumFacing.Axis.Z ? 2 : 0;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	public static boolean tryToCreatePortal(World par1World, BlockPos pos)
	{
		BlockDreadlandsPortal.Size blockportal$size = new BlockDreadlandsPortal.Size(par1World, pos, EnumFacing.Axis.X);

		if (blockportal$size.func_150860_b() && blockportal$size.field_150864_e == 0)
		{
			blockportal$size.func_150859_c();
			return true;
		}
		else
		{
			BlockDreadlandsPortal.Size blockportal$size1 = new BlockDreadlandsPortal.Size(par1World, pos, EnumFacing.Axis.Z);

			if (blockportal$size1.func_150860_b() && blockportal$size1.field_150864_e == 0)
			{
				blockportal$size1.func_150859_c();
				return true;
			} else
				return false;
		}
	}

	@Override
	public void onNeighborBlockChange(World worldIn, BlockPos pos, IBlockState state, Block neighborBlock)
	{
		EnumFacing.Axis enumfacing$axis = state.getValue(AXIS);

		if (enumfacing$axis == EnumFacing.Axis.X)
		{
			BlockDreadlandsPortal.Size blockportal$size = new BlockDreadlandsPortal.Size(worldIn, pos, EnumFacing.Axis.X);

			if (!blockportal$size.func_150860_b() || blockportal$size.field_150864_e < blockportal$size.field_150868_h * blockportal$size.field_150862_g)
				worldIn.setBlockState(pos, Blocks.air.getDefaultState());
		}
		else if (enumfacing$axis == EnumFacing.Axis.Z)
		{
			BlockDreadlandsPortal.Size blockportal$size1 = new BlockDreadlandsPortal.Size(worldIn, pos, EnumFacing.Axis.Z);

			if (!blockportal$size1.func_150860_b() || blockportal$size1.field_150864_e < blockportal$size1.field_150868_h * blockportal$size1.field_150862_g)
				worldIn.setBlockState(pos, Blocks.air.getDefaultState());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
	{
		EnumFacing.Axis enumfacing$axis = null;
		IBlockState iblockstate = worldIn.getBlockState(pos);

		if (worldIn.getBlockState(pos).getBlock() == this)
		{
			enumfacing$axis = iblockstate.getValue(AXIS);

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
		if (par5Entity.ridingEntity == null && par5Entity.riddenByEntity == null && par5Entity instanceof EntityPlayerMP)
		{
			EntityPlayerMP thePlayer = (EntityPlayerMP)par5Entity;
			thePlayer.addStat(AbyssalCraft.enterdreadlands, 1);
			if (thePlayer.timeUntilPortal > 0)
				thePlayer.timeUntilPortal = thePlayer.getPortalCooldown();
			else if (thePlayer.dimension != ACLib.dreadlands_id)
			{
				thePlayer.timeUntilPortal = AbyssalCraft.portalCooldown;
				thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, ACLib.dreadlands_id, new TeleporterAC(thePlayer.mcServer.worldServerForDimension(ACLib.dreadlands_id), this, ACBlocks.dreadstone));
			}
			else {
				thePlayer.timeUntilPortal = AbyssalCraft.portalCooldown;
				thePlayer.mcServer.getConfigurationManager().transferPlayerToDimension(thePlayer, ACLib.abyssal_wasteland_id, new TeleporterAC(thePlayer.mcServer.worldServerForDimension(ACLib.abyssal_wasteland_id), this, ACBlocks.dreadstone));
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, BlockPos pos)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return EnumWorldBlockLayer.TRANSLUCENT;
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
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {AXIS});
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

				if (!func_150857_a(world.getBlockState(blockpos).getBlock()) || world.getBlockState(blockpos.down()).getBlock() != ACBlocks.dreadstone)
					break;
			}

			Block block = world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
			return block == ACBlocks.dreadstone ? i : 0;
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

						if (block == ACBlocks.dreaded_gateway)
							++field_150864_e;

						if (i == 0)
						{
							block = world.getBlockState(blockpos.offset(field_150863_d)).getBlock();

							if (block != ACBlocks.dreadstone)
								break label24;
						}
						else if (i == field_150868_h - 1)
						{
							block = world.getBlockState(blockpos.offset(field_150866_c)).getBlock();

							if (block != ACBlocks.dreadstone)
								break label24;
						}
					}

		for (int j = 0; j < field_150868_h; ++j)
			if (world.getBlockState(field_150861_f.offset(field_150866_c, j).up(field_150862_g)).getBlock() != ACBlocks.dreadstone)
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
			return p_150857_1_.getMaterial() == Material.air || p_150857_1_ == ACBlocks.dreaded_fire || p_150857_1_ == ACBlocks.dreaded_gateway;
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
					world.setBlockState(blockpos.up(j), ACBlocks.dreaded_gateway.getDefaultState().withProperty(BlockPortal.AXIS, axis), 2);
			}
		}
	}
}