/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity;

import com.shinoow.abyssalcraft.client.render.entity.layers.LayerAntiSpiderEyes;
import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSpider;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderAntiSpider extends RenderLiving<EntityAntiSpider>
{
	private static final ResourceLocation spiderTextures = new ResourceLocation("abyssalcraft:textures/model/anti/spider.png");

	public RenderAntiSpider(RenderManager manager)
	{
		super(manager, new ModelSpider(), 1.0F);
		addLayer(new LayerAntiSpiderEyes(this));
	}

	@Override
	protected float getDeathMaxRotation(EntityAntiSpider par1EntityAntiSpider)
	{
		return 180.0F;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAntiSpider par1EntityAntiSpider)
	{
		return spiderTextures;
	}
}
