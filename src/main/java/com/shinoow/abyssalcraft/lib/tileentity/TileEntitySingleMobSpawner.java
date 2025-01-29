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
package com.shinoow.abyssalcraft.lib.tileentity;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

/**
 * Simple Tile Entity that spawns a single mob when a player is nearby
 * @author shinoow
 *
 */
public abstract class TileEntitySingleMobSpawner extends TileEntity implements ITickable {

	@Override
	public void onLoad()
	{
		if(world.isRemote)
			world.tickableTileEntities.remove(this);
	}

	public abstract int getActivationRange();

	public abstract EntityLiving getMob(World world);

	public boolean isActivated() {

		EntityPlayer player = world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
				getActivationRange(), true);

		return player != null && !player.capabilities.isCreativeMode;
	}

	@Override
	public void update() {
		if (isActivated()) {
			EntityLiving mob = getMob(world);
			mob.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(world.rand.nextFloat() * 360.0F), 10.0F);
			mob.onInitialSpawn(world.getDifficultyForLocation(pos), null);
			world.spawnEntity(mob);
			world.setBlockToAir(pos);
		}
	}
}
