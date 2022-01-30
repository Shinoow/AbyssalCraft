/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

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
		if(entity instanceof EntityPlayerMP && !((EntityPlayerMP)entity).capabilities.isCreativeMode) {
			ReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, true, "invulnerableDimensionChange", "field_184851_cj");
			ReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, -1, "lastExperience", "field_71144_ck");
			ReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, -1.0F, "lastHealth", "field_71149_ch");
			ReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, -1, "lastFoodLevel", "field_71146_ci");
		}
		entity.setPosition(MathHelper.floor(entity.posX), 80, MathHelper.floor(entity.posZ));
		entity.motionX = entity.motionY = entity.motionZ = 0;
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw)
	{
		entity.setPosition(MathHelper.floor(entity.posX), 80, MathHelper.floor(entity.posZ));
		entity.motionX = entity.motionY = entity.motionZ = 0;
		return true;
	}
}
