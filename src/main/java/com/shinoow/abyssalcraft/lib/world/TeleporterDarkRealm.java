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
package com.shinoow.abyssalcraft.lib.world;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

/**
 * Very basic "no portal" teleporter implementation,<br>
 * used for teleporting to the Dark Realm.
 * @author shinoow
 *
 */
public class TeleporterDarkRealm extends Teleporter {

	public TeleporterDarkRealm(WorldServer par1WorldServer){
		super(par1WorldServer);
	}

	@Override
	public void placeInPortal(Entity entity, float par8){
		entity.setPosition(MathHelper.floor(entity.posX), 80, MathHelper.floor(entity.posZ));
		entity.motionX = entity.motionY = entity.motionZ = 0;
	}
}
