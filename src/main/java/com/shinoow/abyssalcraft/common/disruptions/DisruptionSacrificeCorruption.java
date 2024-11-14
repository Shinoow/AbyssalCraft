/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DisruptionSacrificeCorruption extends DisruptionEntry {

	public DisruptionSacrificeCorruption(String unlocalizedName, DeityType deity) {
		super(unlocalizedName, deity);
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {

		if(world.isRemote) return;

		for(EntityLiving target : world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(pos).grow(64), this::isSacrifice)) {
			EntityDepthsGhoul ghoul = new EntityDepthsGhoul(world);
			ghoul.copyLocationAndAnglesFrom(target);
			ghoul.onInitialSpawn(world.getDifficultyForLocation(pos), null);
			if(target.hasCustomName())
				ghoul.setCustomNameTag(target.getCustomNameTag());
			ghoul.enablePersistence();
			ghoul.setChild(target.isChild());
			world.removeEntity(target);
			world.spawnEntity(ghoul);
		}
	}

	private boolean isSacrifice(EntityLiving entity) {
		return EntityUtil.isShoggothFood(entity) || entity instanceof EntityVillager;
	}
}
