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

import com.shinoow.abyssalcraft.client.model.entity.ModelSkeletonGoliath;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerSkeletonGoliathArmor;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerSkeletonGoliathHeldItem;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderSkeletonGoliath extends RenderLiving<EntitySkeletonGoliath> {

	private float scale = 1.5F;

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/elite/skeletongoliath.png");

	public RenderSkeletonGoliath(RenderManager manager)
	{
		this(manager, new ModelSkeletonGoliath());
	}

	public RenderSkeletonGoliath(RenderManager manager, ModelSkeletonGoliath model)
	{
		super(manager, model, 0.8F);
		addLayer(new LayerSkeletonGoliathHeldItem(this));
		addLayer(new LayerSkeletonGoliathArmor(this));
		addLayer(new LayerCustomHead(model.head));
	}

	protected void preRenderScale(EntitySkeletonGoliath par1EntitySkeletonGoliath, float par2)
	{
		GlStateManager.scale(scale, scale, scale);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntitySkeletonGoliath entity) {

		return mobTexture;
	}

	@Override
	protected void preRenderCallback(EntitySkeletonGoliath par1EntityLivingBase, float par2)
	{
		preRenderScale(par1EntityLivingBase, par2);
	}
}
