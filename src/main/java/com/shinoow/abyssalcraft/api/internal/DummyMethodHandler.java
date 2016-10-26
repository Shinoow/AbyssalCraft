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
package com.shinoow.abyssalcraft.api.internal;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.energy.EnergyEnum.DeityType;

public class DummyMethodHandler implements IInternalMethodHandler {

	@Override
	public void sendDisruption(DeityType deity, String name, BlockPos pos, int id) {}

	@Override
	public void spawnParticle(String particleName, World world, double posX, double posY,double posZ, double velX, double velY, double velZ) {}
}
