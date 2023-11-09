/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common;

import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IThreadListener;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class CommonProxy {

	public void preInit() {}

	public void init() {
		RitualRegistry.instance().addDimensionToBookType(0, 0);
		RitualRegistry.instance().addDimensionToBookType(ACLib.abyssal_wasteland_id, 1);
		RitualRegistry.instance().addDimensionToBookType(ACLib.dreadlands_id, 2);
		RitualRegistry.instance().addDimensionToBookType(ACLib.omothol_id, 3);
		RitualRegistry.instance().addDimensionToBookType(ACLib.dark_realm_id, 3);
	}

	public void postInit() {}

	public ModelBiped getArmorModel(int id){
		return null;
	}

	/**
	 * Returns a side-appropriate EntityPlayer for use during message handling
	 */
	public EntityPlayer getPlayerEntity(MessageContext ctx) {
		return ctx.getServerHandler().player;
	}

	/**
	 * Returns the current thread based on side during message handling,
	 * used for ensuring that the message is being handled by the main thread
	 */
	public IThreadListener getThreadFromContext(MessageContext ctx) {
		return ctx.getServerHandler().player.getServer();
	}

	public void spawnParticle(String particleName, double posX, double posY, double posZ, double velX, double velY, double velZ) {}

	public int getParticleCount() {
		return 0;
	}

	public void incrementParticleCount() {}

	public void decrementParticleCount() {}

	public void resetParticleCount() {}
}
