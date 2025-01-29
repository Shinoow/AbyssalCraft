/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
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
	public void generateDarklandsStructure(int type, World world, Random random, BlockPos pos, float chance) {}

	@Override
	public void generateDarklandsStructure(int type, World world, Random random, BlockPos pos, float chance, IBlockState spawnBlock, IBlockState... extra) {}

	@Override
	public void completeRitualClient(BlockPos pos, EntityPlayer player, String ritual) {}

	@Override
	public boolean isImmuneOrCarrier(String entity, int list) { return false; }

	@Override
	public RayTraceResult rayTraceTarget(float dist) { return null; }

	@Override
	public void processEntitySpell(int id, String spell, ScrollType scrollType) {}
}
