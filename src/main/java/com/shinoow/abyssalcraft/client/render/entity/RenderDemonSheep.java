/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelDemonSheep2;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerDemonSheepWool;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonSheep;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderDemonSheep extends RenderLiving<EntityDemonSheep> {

	private static final ResourceLocation shearedSheepTextures = new ResourceLocation("abyssalcraft:textures/model/demon_sheep.png");

	public RenderDemonSheep(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelDemonSheep2(), 0.7F);
		addLayer(new LayerDemonSheepWool(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityDemonSheep entity) {

		return shearedSheepTextures;
	}
}
