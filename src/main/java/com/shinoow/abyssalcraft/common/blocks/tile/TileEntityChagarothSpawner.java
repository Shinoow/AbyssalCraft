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
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.common.entity.EntityChagaroth;
import com.shinoow.abyssalcraft.lib.tileentity.TileEntitySingleMobSpawner;

import net.minecraft.entity.EntityLiving;
import net.minecraft.world.World;

public class TileEntityChagarothSpawner extends TileEntitySingleMobSpawner {

	@Override
	public int getActivationRange() {
		return 32;
	}

	@Override
	public EntityLiving getMob(World world) {
		return new EntityChagaroth(world);
	}
}
