/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilpig;

@SideOnly(Side.CLIENT)
public class RenderEvilPig extends RenderLiving<EntityEvilpig> {

	private static final ResourceLocation mobTexture = new ResourceLocation("textures/entity/pig/pig.png");

	public RenderEvilPig (RenderManager manager)
	{
		super(manager, new ModelPig(), 0.5F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEvilpig entity) {

		return mobTexture;
	}
}
