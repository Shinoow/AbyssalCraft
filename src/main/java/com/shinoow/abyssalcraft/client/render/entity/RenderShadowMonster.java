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

import com.shinoow.abyssalcraft.client.model.entity.ModelShadowMonster;
import com.shinoow.abyssalcraft.client.render.entity.layers.LayerEyes;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderShadowMonster extends RenderLiving<EntityShadowMonster> {

	private static final ResourceLocation mobTexture = new ResourceLocation("abyssalcraft:textures/model/shadowmonster.png");

	public RenderShadowMonster(RenderManager manager)
	{
		this(manager, new ModelShadowMonster());
	}

	public RenderShadowMonster(RenderManager manager, ModelShadowMonster model)
	{
		super(manager, model, 0.0F);
		addLayer(new LayerCustomHead(model.Head));
		addLayer(new LayerEyes<>(this, new ResourceLocation("abyssalcraft", "textures/model/shadowmonster_eyes.png")).addAlpha(EntityShadowMonster::getBrightness));
	}

	@Override
	protected void renderModel(EntityShadowMonster entitylivingbaseIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, entitylivingbaseIn.getBrightness());
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		super.renderModel(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
		GlStateManager.disableBlend();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityShadowMonster entity) {

		return mobTexture;
	}
}
