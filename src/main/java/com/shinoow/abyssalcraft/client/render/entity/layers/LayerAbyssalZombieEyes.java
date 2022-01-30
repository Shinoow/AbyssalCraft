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
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.client.render.entity.RenderAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerAbyssalZombieEyes extends LayerEyes<EntityAbyssalZombie> {

	private static final ResourceLocation EYES = new ResourceLocation("abyssalcraft:textures/model/abyssal_zombie_eyes.png");
	private static final ResourceLocation EYES_ALT = new ResourceLocation("abyssalcraft:textures/model/abyssal_zombie_old_eyes.png");


	public LayerAbyssalZombieEyes(RenderAbyssalZombie rendererIn) {
		super(rendererIn, EYES);

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAbyssalZombie entity)
	{
		return entity.getName().equalsIgnoreCase("shinoow") ? EYES_ALT : EYES;
	}
}
