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
package com.shinoow.abyssalcraft.common.ritual;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityGreaterDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityLesserDreadbeast;

public class NecronomiconDreadSpawnRitual extends NecronomiconRitual {

	public NecronomiconDreadSpawnRitual() {
		super("dreadSpawn", 2, 500F, new Object[]{AbyssalCraft.dreadfragment, AbyssalCraft.dreadstone, AbyssalCraft.abydreadstone,
				AbyssalCraft.abychunk, AbyssalCraft.dreadchunk, Items.rotten_flesh, AbyssalCraft.Dreadshard, AbyssalCraft.abyingot});
	}

	@Override
	public boolean canCompleteRitual(World world, int x, int y, int z, EntityPlayer player) {
		List<EntityDreadSpawn> dreadSpawns = world.getEntitiesWithinAABB(EntityDreadSpawn.class, world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z).expand(16, 3, 16));
		List<EntityGreaterDreadSpawn> greaterDreadSpawns = world.getEntitiesWithinAABB(EntityGreaterDreadSpawn.class, world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z).expand(16, 3, 16));
		List<EntityLesserDreadbeast> lesserDreadBeasts = world.getEntitiesWithinAABB(EntityLesserDreadbeast.class, world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z).expand(16, 3, 16));
		return !dreadSpawns.isEmpty() || !greaterDreadSpawns.isEmpty() || !lesserDreadBeasts.isEmpty();
	}

	@Override
	protected void completeRitualServer(World world, int x, int y, int z, EntityPlayer player) {
		List<EntityDreadSpawn> dreadSpawns = Lists.newArrayList();
		List<EntityGreaterDreadSpawn> greaterDreadSpawns = Lists.newArrayList();
		List<EntityLesserDreadbeast> lesserDreadBeasts = Lists.newArrayList();

		List<Entity> entities = world.getEntitiesWithinAABBExcludingEntity(player, world.getBlock(x, y, z).getCollisionBoundingBoxFromPool(world, x, y, z).expand(16, 3, 16));
		for(Entity entity : entities){
			if(entity instanceof EntityDreadSpawn)
				dreadSpawns.add((EntityDreadSpawn) entity);
			if(entity instanceof EntityGreaterDreadSpawn)
				greaterDreadSpawns.add((EntityGreaterDreadSpawn) entity);
			if(entity instanceof EntityLesserDreadbeast)
				lesserDreadBeasts.add((EntityLesserDreadbeast) entity);
		}

		if(!dreadSpawns.isEmpty())
			for(EntityDreadSpawn spawn : dreadSpawns){
				EntityDreadSpawn copy = new EntityDreadSpawn(world);
				copy.copyLocationAndAnglesFrom(spawn);
				world.spawnEntityInWorld(copy);
			}
		if(!greaterDreadSpawns.isEmpty())
			for(EntityGreaterDreadSpawn spawn : greaterDreadSpawns){
				EntityGreaterDreadSpawn copy = new EntityGreaterDreadSpawn(world);
				copy.copyLocationAndAnglesFrom(spawn);
				world.spawnEntityInWorld(copy);
			}
		if(!lesserDreadBeasts.isEmpty())
			for(EntityLesserDreadbeast spawn : lesserDreadBeasts){
				EntityLesserDreadbeast copy = new EntityLesserDreadbeast(world);
				copy.copyLocationAndAnglesFrom(spawn);
				world.spawnEntityInWorld(copy);
			}
	}

	@Override
	protected void completeRitualClient(World world, int x, int y, int z, EntityPlayer player) {}
}
