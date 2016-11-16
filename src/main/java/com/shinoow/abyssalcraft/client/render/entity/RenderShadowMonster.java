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
package com.shinoow.abyssalcraft.client.render.entity;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.client.model.entity.ModelShadowMonster;

@SideOnly(Side.CLIENT)
public class RenderShadowMonster extends RenderLiving {

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/ShadowMonster.png");

	public RenderShadowMonster(RenderManager manager)
	{
		this(manager, new ModelShadowMonster());
	}

	public RenderShadowMonster(RenderManager manager, ModelShadowMonster model)
	{
		super(manager, model, 0.5F);
		addLayer(new LayerCustomHead(model.Head));
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {

		return mobTexture;
	}
}