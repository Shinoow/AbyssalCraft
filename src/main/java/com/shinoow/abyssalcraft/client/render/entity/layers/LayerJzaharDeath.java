/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.entity.layers;

import java.util.Random;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.common.entity.EntityJzahar;

@SideOnly(Side.CLIENT)
public class LayerJzaharDeath implements LayerRenderer<EntityJzahar>
{
	@Override
	public void doRenderLayer(EntityJzahar entitylivingbaseIn, float p_177141_2_, float p_177141_3_, float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale)
	{
		if (entitylivingbaseIn.deathTicks > 400)
		{
			Tessellator tessellator = Tessellator.getInstance();
			VertexBuffer worldrenderer = tessellator.getBuffer();
			RenderHelper.disableStandardItemLighting();
			float f = (entitylivingbaseIn.deathTicks + partialTicks) / 400.0F;
			float f1 = 0.0F;

			if (f > 0.8F)
				f1 = (f - 0.8F) / 0.2F;

			Random random = new Random(432L);
			GlStateManager.disableTexture2D();
			GlStateManager.shadeModel(7425);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(770, 1);
			GlStateManager.disableAlpha();
			GlStateManager.enableCull();
			GlStateManager.depthMask(false);
			GlStateManager.pushMatrix();
			GlStateManager.scale(0.25, 0.25, 0.25);

			for (int i = 0; i < (f + f * f) / 2.0F * 30.0F; ++i)
			{
				GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 360.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
				float f2 = random.nextFloat() * 20.0F + 5.0F + f1;
				float f3 = random.nextFloat() * 2.0F + 1.0F + f1;
				worldrenderer.begin(6, DefaultVertexFormats.POSITION_COLOR);
				worldrenderer.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, (int)(255.0F * (1.0F - f1))).endVertex();
				worldrenderer.pos(-0.866D * f3, f2, -0.5F * f3).color(81, 189, 178, 0).endVertex();
				worldrenderer.pos(0.866D * f3, f2, -0.5F * f3).color(81, 189, 178, 0).endVertex();
				worldrenderer.pos(0.0D, f2, 1.0F * f3).color(81, 189, 178, 0).endVertex();
				worldrenderer.pos(-0.866D * f3, f2, -0.5F * f3).color(81, 189, 178, 0).endVertex();
				tessellator.draw();
			}

			GlStateManager.popMatrix();
			GlStateManager.depthMask(true);
			GlStateManager.disableCull();
			GlStateManager.disableBlend();
			GlStateManager.shadeModel(7424);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			GlStateManager.enableTexture2D();
			GlStateManager.enableAlpha();
			RenderHelper.enableStandardItemLighting();
		}
	}

	@Override
	public boolean shouldCombineTextures()
	{
		return false;
	}
}
