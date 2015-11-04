/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;

public class DisruptionFireRain extends DisruptionEntry {

	public DisruptionFireRain() {
		super("fireRain", null);
	}

	@Override
	public void disrupt(World world, int x, int y, int z, List<EntityPlayer> players) {
		EntityLargeFireball fireball1 = new EntityLargeFireball(world, x + 5, y + 10, z + 5, 0, -0.5, 0);
		EntityLargeFireball fireball2 = new EntityLargeFireball(world, x - 5, y + 10, z + 5, 0, -0.5, 0);
		EntityLargeFireball fireball3 = new EntityLargeFireball(world, x + 5, y + 10, z - 5, 0, -0.5, 0);
		EntityLargeFireball fireball4 = new EntityLargeFireball(world, x - 5, y + 10, z - 5, 0, -0.5, 0);
		EntityLargeFireball fireball5 = new EntityLargeFireball(world, x + 2.5, y + 10, z + 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball6 = new EntityLargeFireball(world, x - 2.5, y + 10, z + 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball7 = new EntityLargeFireball(world, x + 2.5, y + 10, z - 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball8 = new EntityLargeFireball(world, x - 2.5, y + 10, z - 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball9 = new EntityLargeFireball(world, x + 2.5, y + 10, z, 0, -0.5, 0);
		EntityLargeFireball fireball10 = new EntityLargeFireball(world, x + 5, y + 10, z + 1, 0, -0.5, 0);
		EntityLargeFireball fireball11 = new EntityLargeFireball(world, x + 5, y + 10, z - 1, 0, -0.5, 0);
		EntityLargeFireball fireball12 = new EntityLargeFireball(world, x - 2.5, y + 10, z, 0, -0.5, 0);
		EntityLargeFireball fireball13 = new EntityLargeFireball(world, x - 5, y + 10, z + 1, 0, -0.5, 0);
		EntityLargeFireball fireball14 = new EntityLargeFireball(world, x - 5, y + 10, z - 1, 0, -0.5, 0);
		EntityLargeFireball fireball15 = new EntityLargeFireball(world, x, y + 10, z + 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball16 = new EntityLargeFireball(world, x + 1, y + 10, z + 5, 0, -0.5, 0);
		EntityLargeFireball fireball17 = new EntityLargeFireball(world, x - 1, y + 10, z + 5, 0, -0.5, 0);
		EntityLargeFireball fireball18 = new EntityLargeFireball(world, x, y + 10, z - 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball19 = new EntityLargeFireball(world, x + 1, y + 10, z - 5, 0, -0.5, 0);
		EntityLargeFireball fireball20 = new EntityLargeFireball(world, x - 1, y + 10, z - 5, 0, -0.5, 0);

		world.spawnEntityInWorld(fireball1);
		world.spawnEntityInWorld(fireball2);
		world.spawnEntityInWorld(fireball3);
		world.spawnEntityInWorld(fireball4);
		world.spawnEntityInWorld(fireball5);
		world.spawnEntityInWorld(fireball6);
		world.spawnEntityInWorld(fireball7);
		world.spawnEntityInWorld(fireball8);
		world.spawnEntityInWorld(fireball9);
		world.spawnEntityInWorld(fireball10);
		world.spawnEntityInWorld(fireball11);
		world.spawnEntityInWorld(fireball12);
		world.spawnEntityInWorld(fireball13);
		world.spawnEntityInWorld(fireball14);
		world.spawnEntityInWorld(fireball15);
		world.spawnEntityInWorld(fireball16);
		world.spawnEntityInWorld(fireball17);
		world.spawnEntityInWorld(fireball18);
		world.spawnEntityInWorld(fireball19);
		world.spawnEntityInWorld(fireball20);
	}
}
