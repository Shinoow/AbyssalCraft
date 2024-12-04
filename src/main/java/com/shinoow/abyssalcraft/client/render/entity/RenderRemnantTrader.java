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
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.model.entity.ModelRemnantTrader;
import com.shinoow.abyssalcraft.common.entity.EntityRemnantTrader;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderRemnantTrader extends RenderLiving<EntityRemnantTrader> {

	private static ResourceLocation defaultTexture = new ResourceLocation("abyssalcraft:textures/model/remnant/trader/villager.png");

	public RenderRemnantTrader(RenderManager manager)
	{
		this(manager, new ModelRemnantTrader());
	}

	public RenderRemnantTrader(RenderManager manager, ModelRemnantTrader model)
	{
		super(manager, model, 0.5F);
		addLayer(new LayerCustomHead(model.head));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRemnantTrader entity){

		return defaultTexture;
	}
}
