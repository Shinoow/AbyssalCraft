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

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TotemPoleStructure implements IPlaceOfPower {

	private IBlockState[][][] data;

	public TotemPoleStructure() {

		data = new IBlockState[][][] {
			new IBlockState[][] {new IBlockState[] {ACBlocks.monolith_stone.getDefaultState()}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.yog_sothoth_statue.getDefaultState()}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.azathoth_statue.getDefaultState()}},
			new IBlockState[][] {new IBlockState[] {ACBlocks.nyarlathotep_statue.getDefaultState()}}};
	}

	@Override
	public String getIdentifier() {

		return "totempole";
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
			return 3;
		return 0;
	}

	@Override
	public void construct(World world, BlockPos pos) {
		world.setBlockState(pos, ACBlocks.multi_block.getDefaultState());
		((IStructureBase) world.getTileEntity(pos)).setMultiblock(this);
		for(int i = 1; i < 4; i++)
			if(world.getTileEntity(pos.up(i)) instanceof IStructureComponent) {
				((IStructureComponent) world.getTileEntity(pos.up(i))).setInMultiblock(true);
				((IStructureComponent) world.getTileEntity(pos.up(i))).setBasePosition(pos);
			}
	}

	@Override
	public void validate(World world, BlockPos pos) {

		boolean valid = false;
		if(world.getBlockState(pos).getBlock() == ACBlocks.multi_block)
			if(world.getBlockState(pos.up()).getBlock() instanceof BlockStatue
					&& world.getBlockState(pos.up(2)).getBlock() instanceof BlockStatue
					&& world.getBlockState(pos.up(3)).getBlock() instanceof BlockStatue)
				valid = true;
		for(int i = 1; i < 4; i++)
			if(world.getTileEntity(pos.up(i)) instanceof IStructureComponent) {
				((IStructureComponent) world.getTileEntity(pos.up(i))).setInMultiblock(valid);
				((IStructureComponent) world.getTileEntity(pos.up(i))).setBasePosition(valid ? pos : null);
			}
	}

	@Override
	public boolean canConstruct(World world, BlockPos pos, EntityPlayer player) {

		IBlockState state = world.getBlockState(pos);
		if(state.getBlock() == ACBlocks.monolith_stone)
			return world.getBlockState(pos.up()).getBlock() instanceof BlockStatue
					&& world.getBlockState(pos.up(2)).getBlock() instanceof BlockStatue
					&& world.getBlockState(pos.up(3)).getBlock() instanceof BlockStatue;

		return false;
	}

	@Override
	public IBlockState[][][] getRenderData() {

		return data;
	}

	@Override
	public BlockPos getActivationPointForRender() {

		return new BlockPos(0, 0, 0);
	}

}
