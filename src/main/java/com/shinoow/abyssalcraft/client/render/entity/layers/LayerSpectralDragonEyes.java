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
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.client.render.entity.RenderDragonMinion;
import com.shinoow.abyssalcraft.common.entity.EntityDragonMinion;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerSpectralDragonEyes implements LayerRenderer<EntityDragonMinion>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation("abyssalcraft:textures/model/elite/dragonminion_eyes.png");
	private final RenderDragonMinion dragonRenderer;

	public LayerSpectralDragonEyes(RenderDragonMinion dragonRendererIn)
	{
		dragonRenderer = dragonRendererIn;
	}

	@Override
	public void doRenderLayer(EntityDragonMinion entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		dragonRenderer.bindTexture(TEXTURE);
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.blendFunc(1, 1);
		GlStateManager.disableLighting();
		GlStateManager.depthFunc(514);
		int i = 61680;
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
		GlStateManager.enableLighting();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		dragonRenderer.getMainModel().render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
		dragonRenderer.setLightmap(entitylivingbaseIn);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.depthFunc(515);
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
