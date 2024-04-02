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
package com.shinoow.abyssalcraft.common.world;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class TeleporterHomeSpell extends Teleporter {

	private BlockPos pos;

	public TeleporterHomeSpell(WorldServer par1WorldServer, BlockPos pos){
		super(par1WorldServer);
		this.pos = pos;
	}

	@Override
	public void placeInPortal(Entity entity, float par8){
		if(entity instanceof EntityPlayerMP && !((EntityPlayerMP)entity).capabilities.isCreativeMode) {
			ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, true, "field_184851_cj");
			ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, -1, "field_71144_ck");
			ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, -1.0F, "field_71149_ch");
			ObfuscationReflectionHelper.setPrivateValue(EntityPlayerMP.class, (EntityPlayerMP)entity, -1, "field_71146_ci");
		}
		entity.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		entity.motionX = entity.motionY = entity.motionZ = 0;
	}

	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw)
	{
		entity.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
		entity.motionX = entity.motionY = entity.motionZ = 0;
		return true;
	}
}
