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

import com.shinoow.abyssalcraft.client.model.entity.ModelShubOffspring;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerShubOffspringEyes;
import com.shinoow.abyssalcraft.common.entity.EntityShubOffspring;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShubOffspring extends RenderLiving<EntityShubOffspring>
{
	private static final ResourceLocation offspringTextures = new ResourceLocation("abyssalcraft:textures/model/shub_offspring.png");

	public RenderShubOffspring(RenderManager manager)
	{
		super(manager, new ModelShubOffspring(), 1.0F);
		addLayer(new LayerShubOffspringEyes(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShubOffspring par1EntityAntiSpider)
	{
		return offspringTextures;
	}
}
