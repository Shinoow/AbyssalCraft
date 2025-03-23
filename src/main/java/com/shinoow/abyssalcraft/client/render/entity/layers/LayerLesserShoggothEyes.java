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
package com.shinoow.abyssalcraft.client.render.entity.layers;

import com.shinoow.abyssalcraft.client.render.entity.RenderLesserShoggoth;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerLesserShoggothEyes implements LayerRenderer<EntityLesserShoggoth>
{
	private static final ResourceLocation SHOGGOTH_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/lessershoggoth_eyes.png");
	private static final ResourceLocation ABYSSAL_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/abyssalshoggoth_eyes.png");
	private static final ResourceLocation DREADED_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/dreadedshoggoth_eyes.png");
	private static final ResourceLocation OMOTHOL_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/omotholshoggoth_eyes.png");
	private static final ResourceLocation DARK_EYES = new ResourceLocation("abyssalcraft:textures/model/shoggoth/shadowshoggoth_eyes.png");
	private final RenderLesserShoggoth shoggothRenderer;

	public LayerLesserShoggothEyes(RenderLesserShoggoth shoggothRendererIn)
	{
		shoggothRenderer = shoggothRendererIn;
	}

	@Override
	public void doRenderLayer(EntityLesserShoggoth entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		if(!ACConfig.shoggothGlowingEyes) return;
		shoggothRenderer.bindTexture(getEntityTexture(entitylivingbaseIn));
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
		GlStateManager.color(1.0F, 1.0F, 1.0F, entitylivingbaseIn.getShoggothType() == 4 ? entitylivingbaseIn.getBrightness() : 1.0F);
		shoggothRenderer.getMainModel().render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
		i = entitylivingbaseIn.getBrightnessForRender();
		j = i % 65536;
		k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j / 1.0F, k / 1.0F);
		shoggothRenderer.setLightmap(entitylivingbaseIn);
		GlStateManager.disableBlend();
	}

	protected ResourceLocation getEntityTexture(EntityLesserShoggoth par1EntityLiving) {

		switch (par1EntityLiving.getShoggothType())
		{
		case 0:
			return SHOGGOTH_EYES;
		case 1:
			return ABYSSAL_EYES;
		case 2:
			return DREADED_EYES;
		case 3:
			return OMOTHOL_EYES;
		case 4:
			return DARK_EYES;
		default:
			return SHOGGOTH_EYES;
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
