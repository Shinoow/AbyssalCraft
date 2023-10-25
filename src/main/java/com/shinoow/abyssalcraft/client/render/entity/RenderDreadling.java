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
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelDreadling;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderDreadling extends RenderLiving {

	private static final ResourceLocation field_110865_p = new ResourceLocation("abyssalcraft:textures/model/dreadling.png");

	public RenderDreadling(RenderManager manager)
	{
		this(manager, new ModelDreadling());
	}

	public RenderDreadling(RenderManager manager, ModelDreadling model){
		super(manager, model, 0.5F);
		addLayer(new LayerCustomHead(model.head));
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return field_110865_p;
	}
}
