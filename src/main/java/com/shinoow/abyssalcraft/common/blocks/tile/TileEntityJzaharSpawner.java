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
package com.shinoow.abyssalcraft.common.blocks.tile;

import java.util.List;

import com.shinoow.abyssalcraft.common.entity.EntityJzahar;
import com.shinoow.abyssalcraft.lib.ACAchievements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;

public class TileEntityJzaharSpawner extends TileEntity implements ITickable {

	private int activatingRangeFromPlayer = 12;

	@Override
	public void onLoad()
	{
		if(worldObj.isRemote)
			worldObj.tickableTileEntities.remove(this);
	}

	public boolean isActivated() {
		return worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
			activatingRangeFromPlayer, true) != null &&
			!worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
				activatingRangeFromPlayer, true).capabilities.isCreativeMode &&
			worldObj.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D,
				activatingRangeFromPlayer, true).posY >= pos.getY() -1;
	}

	@Override
	public void update() {
		if (isActivated()) {
			EntityJzahar mob = new EntityJzahar(worldObj);
			mob.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), MathHelper.wrapDegrees(worldObj.rand.nextFloat() * 360.0F), 10.0F);
			mob.onInitialSpawn(worldObj.getDifficultyForLocation(pos), null);
			worldObj.spawnEntityInWorld(mob);
			worldObj.setBlockToAir(pos);
			List<EntityPlayer> players = worldObj.getEntitiesWithinAABB(EntityPlayer.class, mob.getEntityBoundingBox().expand(64, 64, 64));
			for(EntityPlayer player : players)
				player.addStat(ACAchievements.locate_jzahar, 1);
		}
	}
}
