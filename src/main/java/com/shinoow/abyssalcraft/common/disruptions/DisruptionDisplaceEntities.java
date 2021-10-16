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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisruptionDisplaceEntities extends DisruptionEntry {

	public DisruptionDisplaceEntities() {
		super("displace", null);
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(pos).grow(16, 16, 16));

		if(!entities.isEmpty())
			for(EntityLivingBase entity : entities){
				EntityLivingBase other = entities.get(world.rand.nextInt(entities.size()));
				double posX = entity.posX;
				double posY = entity.posY;
				double posZ = entity.posZ;
				if(!world.isRemote){
					entity.setPositionAndUpdate(other.posX, other.posY, other.posZ);
					other.setPositionAndUpdate(posX, posY, posZ);
				}
			}
	}
}
