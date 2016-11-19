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
package com.shinoow.abyssalcraft.common.disruptions;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.energy.disruption.DisruptionEntry;

public class DisruptionPotentialEnergy extends DisruptionEntry {

	public DisruptionPotentialEnergy() {
		super("potentialEnergy", null);
	}

	@Override
	public void disrupt(World world, BlockPos pos, List<EntityPlayer> players) {

		for(EntityPlayer player : players)
			for(ItemStack item : player.inventory.mainInventory)
				if(item != null && item.getItem() instanceof IEnergyContainerItem &&
				((IEnergyContainerItem) item.getItem()).getContainedEnergy(item) > 0){
					if(!world.isRemote)
						((IEnergyContainerItem) item.getItem()).consumeEnergy(item, ((IEnergyContainerItem) item.getItem()).getContainedEnergy(item)/10);
					player.attackEntityFrom(DamageSource.magic, 2);
					break;
				}
	}
}
