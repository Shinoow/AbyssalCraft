/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.internal;

import java.util.Random;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DummyMethodHandler implements IInternalMethodHandler {

	@Override
	public void sendDisruption(DeityType deity, String name, BlockPos pos, int id) {}

	@Override
	public void spawnParticle(String particleName, World world, double posX, double posY, double posZ, double velX, double velY, double velZ) {}

	@Override
	public void spawnPEStream(BlockPos posFrom, BlockPos posTo, int dimension) {}

	@Override
	public void spawnPEStream(BlockPos posFrom, Entity target, int dimension) {}

	@Override
	public void generateDarklandsStructure(int type, World world, Random random, BlockPos pos) {}

	@Override
	public void generateDarklandsStructure(int type, World world, Random random, BlockPos pos, IBlockState spawnBlock, IBlockState... extra) {}
}
