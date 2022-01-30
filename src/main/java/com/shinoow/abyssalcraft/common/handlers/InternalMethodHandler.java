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
package com.shinoow.abyssalcraft.common.handlers;

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.internal.DummyMethodHandler;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.client.DisruptionMessage;
import com.shinoow.abyssalcraft.common.network.client.PEStreamMessage;
import com.shinoow.abyssalcraft.common.network.client.RitualMessage;
import com.shinoow.abyssalcraft.common.world.DarklandsStructureGenerator;
import com.shinoow.abyssalcraft.init.InitHandler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InternalMethodHandler extends DummyMethodHandler {

	@Override
	public void sendDisruption(DeityType deity, String name, BlockPos pos, int id){
		PacketDispatcher.sendToDimension(new DisruptionMessage(deity, name, pos), id);
	}

	@Override
	public void spawnParticle(String particleName, World world, double posX, double posY, double posZ, double velX, double velY, double velZ) {
		AbyssalCraft.proxy.spawnParticle(particleName, posX, posY, posZ, velX, velY, velZ);
	}

	@Override
	public void spawnPEStream(BlockPos posFrom, BlockPos posTo, int dimension) {
		PacketDispatcher.sendToAllAround(new PEStreamMessage(posFrom, posTo), dimension, posFrom.getX(), posFrom.getY(), posFrom.getZ(), 30);
	}

	@Override
	public void spawnPEStream(BlockPos posFrom, Entity target, int dimension) {
		PacketDispatcher.sendToAllAround(new PEStreamMessage(posFrom, target), dimension, posFrom.getX(), posFrom.getY(), posFrom.getZ(), 30);
	}

	@Override
	public void generateDarklandsStructure(int type, World world, Random random, BlockPos pos) {
		DarklandsStructureGenerator.generate(type, world, random, pos);
	}

	@Override
	public void generateDarklandsStructure(int type, World world, Random random, BlockPos pos, IBlockState spawnBlock, IBlockState... extra) {
		DarklandsStructureGenerator.generate(type, world, random, pos, spawnBlock, extra);
	}

	@Override
	public void completeRitualClient(BlockPos pos, EntityPlayer player, String ritual) {
		PacketDispatcher.sendToAllAround(new RitualMessage(ritual, pos), player, 5);
	}

	@Override
	public boolean isImmuneOrCarrier(String entity, int list) {
		return InitHandler.INSTANCE.isImmuneOrCarrier(entity, list);
	}
}
