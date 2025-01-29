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
package com.shinoow.abyssalcraft.api.energy.disruption;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;

/**
 * A Swarm Disruption Entry
 * @author shinoow
 *
 * @since 1.5
 */
public class DisruptionSwarm extends DisruptionEntry {

	private Class<? extends EntityLivingBase>[] entities;

	/**
	 * A Spawning Disruption Entry
	 * @param unlocalizedName A String representing the disruption name (mainly used for Necronomicon categorization)
	 * @param deity Deity whose image must be present for this to happen
	 * @param entities An array of entities to spawn
	 */
	public DisruptionSwarm(String unlocalizedName, DeityType deity, Class<? extends EntityLivingBase>...entities) {
		super(unlocalizedName, deity);
		this.entities = entities;
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {

		if(!world.isRemote)
			for(Class<? extends EntityLivingBase> clazz : entities)
				for(int i2 = 0; i2 < 4; i2++)
					try {
						EntityLivingBase entity = clazz.getConstructor(World.class).newInstance(world);
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
								((EntityLiving) entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
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
							((EntityLiving) entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData)null);
							world.spawnEntity(entity);
						}
					} catch (InstantiationException | IllegalAccessException
							| IllegalArgumentException
							| InvocationTargetException | NoSuchMethodException
							| SecurityException e) {
						e.printStackTrace();
					}
	}
}
