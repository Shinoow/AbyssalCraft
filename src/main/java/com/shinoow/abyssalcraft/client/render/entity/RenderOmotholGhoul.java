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

import com.shinoow.abyssalcraft.client.model.entity.ModelGhoul;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerGhoulArmor;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerGhoulHeldItem;
import com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOmotholGhoul extends RenderLiving<EntityOmotholGhoul> {

	private float scale = 1.2F;

	private static final ResourceLocation ghoulResource = new ResourceLocation("abyssalcraft:textures/model/ghoul/omothol_ghoul.png");

	public RenderOmotholGhoul(RenderManager manager)
	{
		this(manager, new ModelGhoul());
	}

	public RenderOmotholGhoul(RenderManager manager, ModelGhoul model){
		super(manager, model, 0.8F);
		addLayer(new LayerGhoulHeldItem(this));
		addLayer(new LayerGhoulArmor(this));
		addLayer(new LayerCustomHead(model.head));
	}

	/**
	 * Applies the scale to the transform matrix
	 */
	protected void preRenderScale(EntityOmotholGhoul par1EntityOmotholGhoul, float par2)
	{
		GlStateManager.scale(scale, scale, scale);
	}

	protected ResourceLocation getGhoulTexture(EntityOmotholGhoul par1EntityLiving)
	{
		return ghoulResource;
	}

	@Override
	protected void preRenderCallback(EntityOmotholGhoul par1EntityLivingBase, float par2)
	{
		preRenderScale(par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityOmotholGhoul entity) {

		return getGhoulTexture(entity);
	}
}
