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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenShoggothMonolith;

public class DisruptionMonolith extends DisruptionEntry {

	public DisruptionMonolith() {
		super("monolith", null);
	}

	private int randomNum(Random rand){
		int num = 16;
		if(rand.nextInt(10) == 0)
			num *= rand.nextBoolean() ? 3 : 2;
		return rand.nextBoolean() ? num : num * -1;
	}

	@Override
	public void disrupt(World world, int x, int y, int z, List<EntityPlayer> players) {
		if(!world.isRemote){
			Chunk chunk = world.getChunkFromBlockCoords(x, z);
			int chunkX = chunk.xPosition*randomNum(world.rand);
			int chunkZ = chunk.zPosition*randomNum(world.rand);

			int xPos = chunkX + world.rand.nextInt(32);
			int zPos = chunkZ + world.rand.nextInt(32);
			int yPos = world.getHeightValue(xPos, zPos);
			int ytemp = yPos;

			while(world.isAirBlock(xPos, ytemp, zPos) && ytemp > 2)
				--ytemp;

			world.setBlock(xPos, ytemp, zPos, AbyssalCraft.shoggothBlock);

			new WorldGenShoggothMonolith().generate(world, world.rand, xPos, yPos, zPos);
		}
	}
}
