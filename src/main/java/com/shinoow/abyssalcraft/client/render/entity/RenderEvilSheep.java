/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelEvilSheep2;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerEvilSheepWool;
import com.shinoow.abyssalcraft.common.entity.demon.EntityEvilSheep;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderEvilSheep extends RenderLiving<EntityEvilSheep> {

	private static final ResourceLocation shearedSheepTextures = new ResourceLocation("textures/entity/sheep/sheep.png");

	public RenderEvilSheep(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelEvilSheep2(), 0.7F);
		addLayer(new LayerEvilSheepWool(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEvilSheep entity) {

		return shearedSheepTextures;
	}
}
