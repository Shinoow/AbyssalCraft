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
package com.shinoow.abyssalcraft.client.handlers;

import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class AbyssalCraftClientEventHooks {

	@SubscribeEvent
	public void onUpdateFOV(FOVUpdateEvent event) {
		float fov = event.getFov();

		if( event.getEntity().isHandActive() && event.getEntity().getActiveItemStack().getItem() == AbyssalCraft.corbow) {
			int duration = event.getEntity().getItemInUseCount();
			float multiplier = duration / 20.0F;

			if( multiplier > 1.0F )
				multiplier = 1.0F;
			else
				multiplier *= multiplier;

			fov *= 1.0F - multiplier * 0.15F;
		}

		event.setNewfov(fov);
	}

	@SubscribeEvent
	public void onModelBakeEvent(ModelBakeEvent event)
	{
		event.getModelManager().getBlockModelShapes().registerBuiltInBlocks(AbyssalCraft.dreadaltarbottom, AbyssalCraft.dreadaltartop, AbyssalCraft.ODB,
				AbyssalCraft.engraver, AbyssalCraft.cthulhuStatue, AbyssalCraft.hasturStatue, AbyssalCraft.jzaharStatue, AbyssalCraft.azathothStatue,
				AbyssalCraft.nyarlathotepStatue, AbyssalCraft.yogsothothStatue, AbyssalCraft.shubniggurathStatue);
	}
}