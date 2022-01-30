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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;

public class DisruptionRandomSwarm extends DisruptionEntry {

	public DisruptionRandomSwarm() {
		super("randomSwarm", null);
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {
		if(world.isRemote) return;
		List<SpawnListEntry> list = world.getBiome(pos).getSpawnableList(EnumCreatureType.MONSTER);
		if(!list.isEmpty())
			for(int i2 = 0; i2 < 4; i2++)
				try {
					Biome.SpawnListEntry entry = WeightedRandom.getRandomItem(world.rand, list);
					if(entry.entityClass == EntityLesserDreadbeast.class) {
						int num = 0;
						while(entry.entityClass == EntityLesserDreadbeast.class && num < 10) {
							num++;
							entry = WeightedRandom.getRandomItem(world.rand, list);
						}
						if(num == 10 && entry.entityClass == EntityLesserDreadbeast.class)
							continue;
					}
					EntityLiving entity = entry.newInstance(world);

					int i = pos.getX() + world.rand.nextInt(16);
					int j = pos.getY() + world.rand.nextInt(16);
					int k = pos.getZ() + world.rand.nextInt(16);
					int l = i;
					int m = j;
					int i1 = k;

					boolean flag = false;

					for (int k1 = 0; !flag && k1 < 10; ++k1)
					{
						BlockPos blockpos = new BlockPos(i, j, k);
						if(world.canBlockSeeSky(blockpos))
							blockpos = world.getTopSolidOrLiquidBlock(blockpos);

						if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, world, blockpos))
						{

							entity.setLocationAndAngles(i + 0.5F, j + 0.5F, k + 0.5F, world.rand.nextFloat() * 360.0F, 0.0F);
							entity.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
							world.spawnEntity(entity);
							flag = true;
						}

						i += world.rand.nextInt(5) - world.rand.nextInt(5);

						for (k += world.rand.nextInt(5) - world.rand.nextInt(5); i < pos.getX() - 16 || i >= pos.getX() + 16 || k < pos.getZ() - 16 || k >= pos.getZ() + 16 || j < pos.getY() - 16 || j >= pos.getY() + 16; k = i1 + world.rand.nextInt(5) - world.rand.nextInt(16))
						{
							i = l + world.rand.nextInt(5) - world.rand.nextInt(16);
							j = m + world.rand.nextInt(5) - world.rand.nextInt(16);
						}
					}

					if(!flag) {
						double d0 = pos.getX() + (world.rand.nextDouble() - world.rand.nextDouble()) * 4 + 0.5D;
						double d1 = pos.getY() + world.rand.nextInt(3) - 1;
						double d2 = pos.getZ() + (world.rand.nextDouble() - world.rand.nextDouble()) * 4 + 0.5D;

						entity.setLocationAndAngles(d0, d1, d2, world.rand.nextFloat() * 360.0F, 0.0F);
						entity.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
						world.spawnEntity(entity);
					}
				} catch(Exception e) {
					e.printStackTrace();
				}
	}

}
