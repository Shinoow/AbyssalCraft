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

import com.shinoow.abyssalcraft.client.model.entity.ModelGhoul;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerEyes;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerGhoulArmor;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerGhoulHeldItem;
import com.shinoow.abyssalcraft.common.entity.ghoul.EntityGhoul;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderGhoul extends RenderGhoulBase<EntityGhoul> {

	private static final ResourceLocation ghoulResource = new ResourceLocation("abyssalcraft:textures/model/ghoul/ghoul.png");

	public RenderGhoul(RenderManager manager)
	{
		this(manager, new ModelGhoul());
		addLayer(new LayerEyes(this, new ResourceLocation("abyssalcraft", "textures/model/ghoul/ghoul_eyes.png")));
	}

	public RenderGhoul(RenderManager manager, ModelGhoul model){
		super(manager, model, 0.6F);
		addLayer(new LayerGhoulHeldItem(this));
		addLayer(new LayerGhoulArmor(this));
		addLayer(new LayerCustomHead(model.head));
	}

	@Override
	protected void preRenderCallback(EntityGhoul par1EntityLivingBase, float par2)
	{
		GlStateManager.scale(0.7F, 0.7F, 0.7F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityGhoul par1EntityLiving)
	{
		return ghoulResource;
	}
}
