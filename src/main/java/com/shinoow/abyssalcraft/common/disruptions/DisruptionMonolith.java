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
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenShoggothMonolith;

public class DisruptionMonolith extends DisruptionEntry {

	public DisruptionMonolith() {
		super("monolith", null);
	}

	private int randomNum(Random rand){
		int num = 1;
		if(rand.nextInt(10) == 0)
			num *= rand.nextBoolean() ? 3 : 2;
		return rand.nextBoolean() ? num : num * -1;
	}

	@Override
	public void disrupt(World world, int x, int y, int z, List<EntityPlayer> players) {
		if(!world.isRemote){

			int xPos = x + world.rand.nextInt(32) * randomNum(world.rand);
			int zPos = z + world.rand.nextInt(32) * randomNum(world.rand);
			int yPos = world.getHeightValue(xPos, zPos);

			world.setBlock(xPos, yPos, zPos, AbyssalCraft.shoggothBlock);

			new WorldGenShoggothMonolith().generate(world, world.rand, xPos, yPos, zPos);
		}
	}
}