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
package com.shinoow.abyssalcraft.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterDarkRealm extends Teleporter {

	public TeleporterDarkRealm(WorldServer par1WorldServer){
		super(par1WorldServer);
	}

	@Override
	public void placeInPortal(Entity entity, double par2, double par4, double par6, float par8){
		entity.setPosition(par2, 80, par6);
		entity.setVelocity(0, 0, 0);
	}
}
