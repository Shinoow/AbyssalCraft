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

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.client.model.entity.ModelJzahar;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerJzaharDeath;
import com.shinoow.abyssalcraft.common.entity.EntityJzahar;

@SideOnly(Side.CLIENT)
public class RenderJzahar extends RenderLiving<EntityJzahar> {

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/boss/j'zahar.png");

	public RenderJzahar(RenderManager manager)
	{
		super(manager, new ModelJzahar(true), 1.0F);
		addLayer(new LayerJzaharDeath());
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityJzahar entity) {

		return mobTexture;
	}

	@Override
	public void preRenderCallback(EntityJzahar entity, float par2){
		GlStateManager.scale(1.5F, 1.5F, 1.5F);
	}
}