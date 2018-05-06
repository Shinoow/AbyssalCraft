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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;
import java.util.Random;

import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;

public class DisruptionRandomSwarm extends DisruptionEntry {

	public DisruptionRandomSwarm() {
		super("randomSwarm", null);
	}

	private int randomNum(Random rand){
		return rand.nextBoolean() ? 3 : -3;
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {
		if(world.isRemote) return;
		List<SpawnListEntry> l = world.getBiome(pos).getSpawnableList(EnumCreatureType.MONSTER);
		if(!l.isEmpty())
			for(int i = 0; i < 4; i++)
				try {
					Biome.SpawnListEntry entry = WeightedRandom.getRandomItem(world.rand, l);
					EntityLiving entity = entry.entityClass.getConstructor(World.class).newInstance(world);
					BlockPos pos1 = new BlockPos(pos.getX() + randomNum(world.rand), pos.getY() + 1, pos.getZ() + randomNum(world.rand));
					entity.setLocationAndAngles(pos1.getX(), pos1.getY(), pos1.getZ(), entity.rotationYaw, entity.rotationPitch);
					entity.onInitialSpawn(world.getDifficultyForLocation(pos1), (IEntityLivingData)null);
					world.spawnEntity(entity);
				} catch(Exception e) {
					e.printStackTrace();
				}
	}

}
