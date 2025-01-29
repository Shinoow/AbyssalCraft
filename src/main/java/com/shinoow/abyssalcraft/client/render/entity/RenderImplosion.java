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

import java.util.Random;

import com.shinoow.abyssalcraft.common.entity.EntityImplosion;
import com.shinoow.abyssalcraft.lib.ACClientVars;

import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderImplosion extends Render<EntityImplosion>
{
	public RenderImplosion(RenderManager renderManagerIn)
	{
		super(renderManagerIn);
	}

	/**
	 * Renders the desired {@code T} type Entity.
	 */
	@Override
	public void doRender(EntityImplosion entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		if (entity.ticksExisted > 0)
		{
			Tessellator tessellator = Tessellator.getInstance();
			BufferBuilder vertexbuffer = tessellator.getBuffer();
			RenderHelper.disableStandardItemLighting();
			float f = (entity.ticksExisted + partialTicks) / 380.0F;
			float f1 = 0.0F;

			if (f > 1.0F)
				f1 = (f - 1.0F) / 0.2F;

			Random random = new Random(432L);
			GlStateManager.disableTexture2D();
			GlStateManager.shadeModel(7425);
			GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE);
			GlStateManager.disableAlpha();
			GlStateManager.enableCull();
			GlStateManager.depthMask(false);
			GlStateManager.pushMatrix();
			GlStateManager.translate((float)x, (float)y, (float)z);
			GlStateManager.scale(2F + f, 2F + f, 2F + f);
			GlStateManager.translate(0.0F, 0.375F, 0.0F);
			GlStateManager.rotate(entity.ticksExisted * 2F, 1.0F, 1.0F, 1.0F);
			for (int i = 0; i < (f + f * f) / 2.0F * 160.0F; ++i)
			{
				GlStateManager.rotate(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 720.0F, 0.0F, 0.0F, 1.0F);
				GlStateManager.rotate(random.nextFloat() * 720.0F, 1.0F, 0.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 720.0F, 0.0F, 1.0F, 0.0F);
				GlStateManager.rotate(random.nextFloat() * 720.0F + f * 90.0F, 0.0F, 0.0F, 1.0F);
				float f2 = random.nextFloat() * 40.0F + 5.0F + f1 * 10.0F;
				float f3 = random.nextFloat() * 2.0F + 1.0F + f1 * 2.0F;
				vertexbuffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
				vertexbuffer.pos(0.0D, 0.0D, 0.0D).color(255, 255, 255, (int)(255.0F * (1.0F - f1))).endVertex();
				vertexbuffer.pos(-1.0D * f3, f2, -1.0F * f3).color(ACClientVars.getImplosionR(), ACClientVars.getImplosionG(), ACClientVars.getImplosionB(), 0).endVertex();
				vertexbuffer.pos(1.0D * f3, f2, -1.0F * f3).color(ACClientVars.getImplosionR(), ACClientVars.getImplosionG(), ACClientVars.getImplosionB(), 0).endVertex();
				vertexbuffer.pos(0.0D, f2, 1.0F * f3).color(ACClientVars.getImplosionR(), ACClientVars.getImplosionG(), ACClientVars.getImplosionB(), 0).endVertex();
				vertexbuffer.pos(-1.0D * f3, f2, -1.0F * f3).color(ACClientVars.getImplosionR(), ACClientVars.getImplosionG(), ACClientVars.getImplosionB(), 0).endVertex();
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

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(EntityImplosion entity)
	{
		return null;
	}
}
