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
package com.shinoow.abyssalcraft.client.render.entity.layers;

import java.util.function.Function;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerEyes<E extends EntityLiving> implements LayerRenderer<E>
{
	private final ResourceLocation EYES;
	private final RenderLiving<E> renderer;
	private Function<E, Float> alphaFunc = e -> 1.0F;

	public LayerEyes(RenderLiving<E> rendererIn, ResourceLocation eyes)
	{
		renderer = rendererIn;
		EYES = eyes;
	}

	public LayerEyes addAlpha(Function<E, Float> alphaFunc) {
		this.alphaFunc = alphaFunc;
		return this;
	}

	@Override
	public void doRenderLayer(E entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		renderer.bindTexture(getEntityTexture(entitylivingbaseIn));
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(1, 1);

		if (entitylivingbaseIn.isInvisible())
			GlStateManager.depthMask(false);
		else
			GlStateManager.depthMask(true);

		int i = 61680;
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
		GlStateManager.color(1.0F, 1.0F, 1.0F, alphaFunc.apply(entitylivingbaseIn));
		renderer.getMainModel().render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
		i = entitylivingbaseIn.getBrightnessForRender();
		j = i % 65536;
		k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
		renderer.setLightmap(entitylivingbaseIn);
		GlStateManager.disableBlend();
	}

	protected ResourceLocation getEntityTexture(E entityLivingBaseIn) {
		return EYES;
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
