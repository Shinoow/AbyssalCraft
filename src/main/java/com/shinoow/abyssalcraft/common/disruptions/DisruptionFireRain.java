/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DisruptionFireRain extends DisruptionEntry {

	public DisruptionFireRain() {
		super("fireRain", null);
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		EntityLargeFireball fireball1 = positionFireball(world, x + 2.5, y + 10, z + 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball2 = positionFireball(world, x - 2.5, y + 10, z + 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball3 = positionFireball(world, x + 2.5, y + 10, z - 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball4 = positionFireball(world, x - 2.5, y + 10, z - 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball5 = positionFireball(world, x + 2.5, y + 10, z, 0, -0.5, 0);
		EntityLargeFireball fireball6 = positionFireball(world, x - 2.5, y + 10, z, 0, -0.5, 0);
		EntityLargeFireball fireball7 = positionFireball(world, x, y + 10, z + 2.5, 0, -0.5, 0);
		EntityLargeFireball fireball8 = positionFireball(world, x, y + 10, z - 2.5, 0, -0.5, 0);

		if(!world.isRemote){
			if(world.rand.nextBoolean())
				world.spawnEntity(fireball1);
			if(world.rand.nextBoolean())
				world.spawnEntity(fireball2);
			if(world.rand.nextBoolean())
				world.spawnEntity(fireball3);
			if(world.rand.nextBoolean())
				world.spawnEntity(fireball4);
			if(world.rand.nextBoolean())
				world.spawnEntity(fireball5);
			if(world.rand.nextBoolean())
				world.spawnEntity(fireball6);
			if(world.rand.nextBoolean())
				world.spawnEntity(fireball7);
			if(world.rand.nextBoolean())
				world.spawnEntity(fireball8);
		}
	}

	private EntityLargeFireball positionFireball(World world, double x, double y, double z, double accx, double accy, double accz){
		EntityLargeFireball fireball = new EntityLargeFireball(world);
		fireball.setLocationAndAngles(x, y, z, fireball.rotationYaw, fireball.rotationPitch);
		fireball.setPosition(x, y, z);
		double d6 = MathHelper.sqrt(accx * accx + accy * accy + accz * accz);
		fireball.accelerationX = accx / d6 * 0.1D;
		fireball.accelerationY = accy / d6 * 0.1D;
		fireball.accelerationZ = accz / d6 * 0.1D;

		return fireball;
	}
}
