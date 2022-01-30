/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.structures.pe;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.AmplifierType;
import com.shinoow.abyssalcraft.api.energy.structure.IPlaceOfPower;
import com.shinoow.abyssalcraft.api.energy.structure.IStructureBase;
import com.shinoow.abyssalcraft.api.energy.structure.IStructureComponent;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.common.blocks.BlockStatue;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockSlab.EnumBlockHalf;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockStairs.EnumHalf;
import net.minecraft.block.BlockWall;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ArchwayStructure implements IPlaceOfPower {

	private IBlockState[][][] data;

	public ArchwayStructure() {

		data = new IBlockState[][][] {
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}, new IBlockState[] {null}, new IBlockState[] {ACBlocks.monolith_stone.getDefaultState()},
				new IBlockState[] {null}, new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}, new IBlockState[] {null}, new IBlockState[] {ACBlocks.cthulhu_statue.getDefaultState()}, new IBlockState[] {null},
					new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}, new IBlockState[] {null}, new IBlockState[] {null}, new IBlockState[] {null},
						new IBlockState[] {ACBlocks.darkstone_cobblestone_wall.getDefaultState()}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST)},
							new IBlockState[] {ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST).withProperty(BlockStairs.HALF, EnumHalf.TOP)},
							new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState().withProperty(BlockSlab.HALF, EnumBlockHalf.TOP)},
							new IBlockState[] {ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.EAST).withProperty(BlockStairs.HALF, EnumHalf.TOP)},
							new IBlockState[] {ACBlocks.darkstone_cobblestone_stairs.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.WEST)}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()}, new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()},
								new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()}, new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()},
								new IBlockState[] {ACBlocks.darkstone_cobblestone_slab.getDefaultState()}}};
	}

	@Override
	public String getIdentifier() {

		return "archway";
	}

	@Override
	public int getBookType() {

		return 0;
	}

	@Override
	public IUnlockCondition getUnlockCondition() {

		return new DefaultCondition();
	}

	@Override
	public float getAmplifier(AmplifierType type) {

		if(type == AmplifierType.RANGE)
			return 1;
		return 0;
	}

	@Override
	public void construct(World world, BlockPos pos) {
		world.setBlockState(pos, ACBlocks.multi_block.getDefaultState());
		((IStructureBase) world.getTileEntity(pos)).setMultiblock(this);
		if(world.getTileEntity(pos.up()) instanceof IStructureComponent) {
			((IStructureComponent) world.getTileEntity(pos.up())).setInMultiblock(true);
			((IStructureComponent) world.getTileEntity(pos.up())).setBasePosition(pos);
		}
	}

	@Override
	public void validate(World world, BlockPos pos) {
		boolean valid = true;
		if(world.getBlockState(pos).getBlock() == ACBlocks.multi_block && world.getBlockState(pos.up()).getBlock() instanceof BlockStatue) {
			for(int i = 0; i < 3; i++)
				if(!(world.getBlockState(pos.east(2).up(i)).getBlock() instanceof BlockWall)
						&& !(world.getBlockState(pos.west(2).up(i)).getBlock() instanceof BlockWall) &&
						!(world.getBlockState(pos.north(2).up(i)).getBlock() instanceof BlockWall)
						&& !(world.getBlockState(pos.south(2).up(i)).getBlock() instanceof BlockWall))
					valid = false;
			if(!(world.getBlockState(pos.up(3).north(2)).getBlock() instanceof BlockStairs) && !(world.getBlockState(pos.up(3).north(1)).getBlock() instanceof BlockStairs)
					&& !(world.getBlockState(pos.up(3)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(3).south(1)).getBlock() instanceof BlockStairs)
					&& !(world.getBlockState(pos.up(3).south(2)).getBlock() instanceof BlockStairs) && !(world.getBlockState(pos.up(3).east(2)).getBlock() instanceof BlockStairs)
					&& !(world.getBlockState(pos.up(3).east(1)).getBlock() instanceof BlockStairs) && !(world.getBlockState(pos.up(3).west(1)).getBlock() instanceof BlockStairs)
					&& !(world.getBlockState(pos.up(3).west(2)).getBlock() instanceof BlockStairs))
				valid = false;
			else if(!(world.getBlockState(pos.up(4).north(2)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(4).north(1)).getBlock() instanceof BlockSlab)
					&& !(world.getBlockState(pos.up(4)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(4).south(1)).getBlock() instanceof BlockSlab)
					&& !(world.getBlockState(pos.up(4).south(2)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(4).east(2)).getBlock() instanceof BlockSlab)
					&& !(world.getBlockState(pos.up(4).east(1)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(4).west(1)).getBlock() instanceof BlockSlab)
					&& !(world.getBlockState(pos.up(4).west(2)).getBlock() instanceof BlockSlab))
				valid = false;
		} else
			valid = false;
		if(world.getTileEntity(pos.up()) instanceof IStructureComponent) {
			((IStructureComponent) world.getTileEntity(pos.up())).setInMultiblock(valid);
			((IStructureComponent) world.getTileEntity(pos.up())).setBasePosition(valid ? pos : null);
		}
	}

	@Override
	public boolean canConstruct(World world, BlockPos pos, EntityPlayer player) {

		IBlockState state = world.getBlockState(pos);
		if(state.getBlock() == ACBlocks.monolith_stone && world.getBlockState(pos.up()).getBlock() instanceof BlockStatue) {
			boolean temp = true;
			for(int i = 0; i < 3; i++)
				if(!(world.getBlockState(pos.east(2).up(i)).getBlock() instanceof BlockWall)
						&& !(world.getBlockState(pos.west(2).up(i)).getBlock() instanceof BlockWall) &&
						!(world.getBlockState(pos.north(2).up(i)).getBlock() instanceof BlockWall)
						&& !(world.getBlockState(pos.south(2).up(i)).getBlock() instanceof BlockWall))
					temp = false;
			if(temp) {

			}
			if(!(world.getBlockState(pos.up(3).north(2)).getBlock() instanceof BlockStairs) && !(world.getBlockState(pos.up(3).north(1)).getBlock() instanceof BlockStairs)
					&& !(world.getBlockState(pos.up(3)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(3).south(1)).getBlock() instanceof BlockStairs)
					&& !(world.getBlockState(pos.up(3).south(2)).getBlock() instanceof BlockStairs) && !(world.getBlockState(pos.up(3).east(2)).getBlock() instanceof BlockStairs)
					&& !(world.getBlockState(pos.up(3).east(1)).getBlock() instanceof BlockStairs) && !(world.getBlockState(pos.up(3).west(1)).getBlock() instanceof BlockStairs)
					&& !(world.getBlockState(pos.up(3).west(2)).getBlock() instanceof BlockStairs))
				temp = false;
			else if(!(world.getBlockState(pos.up(4).north(2)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(4).north(1)).getBlock() instanceof BlockSlab)
					&& !(world.getBlockState(pos.up(4)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(4).south(1)).getBlock() instanceof BlockSlab)
					&& !(world.getBlockState(pos.up(4).south(2)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(4).east(2)).getBlock() instanceof BlockSlab)
					&& !(world.getBlockState(pos.up(4).east(1)).getBlock() instanceof BlockSlab) && !(world.getBlockState(pos.up(4).west(1)).getBlock() instanceof BlockSlab)
					&& !(world.getBlockState(pos.up(4).west(2)).getBlock() instanceof BlockSlab))
				temp = false;

			return temp;
		}

		return false;
	}

	@Override
	public IBlockState[][][] getRenderData() {

		return data;
	}

	@Override
	public BlockPos getActivationPointForRender() {

		return new BlockPos(2, 0, 0);
	}
}
