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

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class BlockDLTLeaves extends BlockLeavesBase implements IShearable {

	public static final PropertyBool DECAYABLE = PropertyBool.create("decayable");
	public static final PropertyBool CHECK_DECAY = PropertyBool.create("check_decay");
	int[] surroundings;
	@SideOnly(Side.CLIENT)
	protected int iconIndex;
	@SideOnly(Side.CLIENT)
	protected boolean isTransparent;

	public BlockDLTLeaves()
	{
		super(Material.leaves , false);
		setTickRandomly(true);
		setCreativeTab(AbyssalCraft.tabDecoration);
		setDefaultState(blockState.getBaseState().withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));

		if(FMLCommonHandler.instance().getEffectiveSide().equals(Side.CLIENT))
			setGraphicsLevel(Minecraft.getMinecraft().isFancyGraphicsEnabled());
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
	{
		int i = 1;
		int j = i + 1;
		int k = pos.getX();
		int l = pos.getY();
		int i1 = pos.getZ();

		if (worldIn.isAreaLoaded(new BlockPos(k - j, l - j, i1 - j), new BlockPos(k + j, l + j, i1 + j)))
			for (int j1 = -i; j1 <= i; ++j1)
				for (int k1 = -i; k1 <= i; ++k1)
					for (int l1 = -i; l1 <= i; ++l1)
					{
						BlockPos blockpos = pos.add(j1, k1, l1);
						IBlockState iblockstate = worldIn.getBlockState(blockpos);

						if (iblockstate.getBlock().isLeaves(worldIn, blockpos))
							iblockstate.getBlock().beginLeavesDecay(worldIn, blockpos);
					}
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (!worldIn.isRemote)
			if (state.getValue(CHECK_DECAY).booleanValue() && state.getValue(DECAYABLE).booleanValue())
			{
				int i = 4;
				int j = i + 1;
				int k = pos.getX();
				int l = pos.getY();
				int i1 = pos.getZ();
				int j1 = 32;
				int k1 = j1 * j1;
				int l1 = j1 / 2;

				if (surroundings == null)
					surroundings = new int[j1 * j1 * j1];

				if (worldIn.isAreaLoaded(new BlockPos(k - j, l - j, i1 - j), new BlockPos(k + j, l + j, i1 + j)))
				{
					BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

					for (int i2 = -i; i2 <= i; ++i2)
						for (int j2 = -i; j2 <= i; ++j2)
							for (int k2 = -i; k2 <= i; ++k2)
							{
								Block block = worldIn.getBlockState(blockpos$mutableblockpos.set(k + i2, l + j2, i1 + k2)).getBlock();

								if (!block.canSustainLeaves(worldIn, blockpos$mutableblockpos.set(k + i2, l + j2, i1 + k2)))
								{
									if (block.isLeaves(worldIn, blockpos$mutableblockpos.set(k + i2, l + j2, i1 + k2)))
										surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = -2;
									else
										surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = -1;
								} else
									surroundings[(i2 + l1) * k1 + (j2 + l1) * j1 + k2 + l1] = 0;
							}

					for (int i3 = 1; i3 <= 4; ++i3)
						for (int j3 = -i; j3 <= i; ++j3)
							for (int k3 = -i; k3 <= i; ++k3)
								for (int l3 = -i; l3 <= i; ++l3)
									if (surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1] == i3 - 1)
									{
										if (surroundings[(j3 + l1 - 1) * k1 + (k3 + l1) * j1 + l3 + l1] == -2)
											surroundings[(j3 + l1 - 1) * k1 + (k3 + l1) * j1 + l3 + l1] = i3;

										if (surroundings[(j3 + l1 + 1) * k1 + (k3 + l1) * j1 + l3 + l1] == -2)
											surroundings[(j3 + l1 + 1) * k1 + (k3 + l1) * j1 + l3 + l1] = i3;

										if (surroundings[(j3 + l1) * k1 + (k3 + l1 - 1) * j1 + l3 + l1] == -2)
											surroundings[(j3 + l1) * k1 + (k3 + l1 - 1) * j1 + l3 + l1] = i3;

										if (surroundings[(j3 + l1) * k1 + (k3 + l1 + 1) * j1 + l3 + l1] == -2)
											surroundings[(j3 + l1) * k1 + (k3 + l1 + 1) * j1 + l3 + l1] = i3;

										if (surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 - 1] == -2)
											surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 - 1] = i3;

										if (surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 + 1] == -2)
											surroundings[(j3 + l1) * k1 + (k3 + l1) * j1 + l3 + l1 + 1] = i3;
									}
				}

				int l2 = surroundings[l1 * k1 + l1 * j1 + l1];

				if (l2 >= 0)
					worldIn.setBlockState(pos, state.withProperty(CHECK_DECAY, Boolean.valueOf(false)), 4);
				else
					destroy(worldIn, pos);
			}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		if (worldIn.canLightningStrike(pos.up()) && !World.doesBlockHaveSolidTopSurface(worldIn, pos.down()) && rand.nextInt(15) == 1)
		{
			double d0 = pos.getX() + rand.nextFloat();
			double d1 = pos.getY() - 0.05D;
			double d2 = pos.getZ() + rand.nextFloat();
			worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	private void destroy(World worldIn, BlockPos pos)
	{
		dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
		worldIn.setBlockToAir(pos);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return par1Random.nextInt(20) == 0 ? 1 : 0;
	}

	@Override
	public Item getItemDropped(IBlockState state, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(AbyssalCraft.DLTSapling);
	}

	@Override
	public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune)
	{
		super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
	}

	protected int getSaplingDropChance(IBlockState state)
	{
		return 20;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return !fancyGraphics;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	public void setGraphicsLevel(boolean fancy)
	{
		isTransparent = fancy;
		fancyGraphics = fancy;
		iconIndex = fancy ? 0 : 1;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumWorldBlockLayer getBlockLayer()
	{
		return isTransparent ? EnumWorldBlockLayer.CUTOUT_MIPPED : EnumWorldBlockLayer.SOLID;
	}

	@Override
	public boolean isVisuallyOpaque()
	{
		return false;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1));
		return ret;
	}

	@Override
	public void beginLeavesDecay(World world, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		if (!(Boolean)state.getValue(CHECK_DECAY))
			world.setBlockState(pos, state.withProperty(CHECK_DECAY, true), 4);
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(DECAYABLE, Boolean.valueOf((meta & 4) == 0)).withProperty(CHECK_DECAY, Boolean.valueOf((meta & 8) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		int i = 0;

		if (!state.getValue(DECAYABLE).booleanValue())
			i |= 4;

		if (state.getValue(CHECK_DECAY).booleanValue())
			i |= 8;

		return i;
	}

	@Override
	protected BlockState createBlockState()
	{
		return new BlockState(this, new IProperty[] {CHECK_DECAY, DECAYABLE});
	}
}