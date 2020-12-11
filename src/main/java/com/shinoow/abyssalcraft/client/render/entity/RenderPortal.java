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

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.api.dimension.DimensionData;
import com.shinoow.abyssalcraft.common.entity.EntityPortal;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPortal extends Render<EntityPortal> {

	private static final ResourceLocation texture = new ResourceLocation("abyssalcraft", "textures/model/black_hole.png");

	public RenderPortal(RenderManager manager){
		super(manager);
	}


	@Override
	public void doRender(EntityPortal entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		entity.clientTicks++;

		float wobbleScale = 0.05f;
		float wobble = entity.clientTicks / 10f;
		float wobbleX = (float) (Math.sin(wobble) * wobbleScale) + 1;
		float wobbleY = (float) (Math.sin(wobble) * -1 * wobbleScale) + 1;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y + 1, z);
		if(entity.ticksExisted <= 30) {
			float f = entity.ticksExisted / 30f;
			GlStateManager.scale(f, f, f);
		}
		else GlStateManager.scale(wobbleX, wobbleY, 1);

		DimensionData data = entity.getDimensionData();

		if(data != null)
			GlStateManager.color(data.getR()/255F, data.getG()/255F, data.getB()/255F, 1);

		GlStateManager.scale(1, 1.5, 1);

		bindEntityTexture(entity);

		float rot = entity.clientTicks;
		double scale = 1;

		GlStateManager.pushMatrix();

		GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		//		GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

		GlStateManager.pushMatrix();

		GlStateManager.rotate(rot, 0, 0, 1);

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder tes = tessellator.getBuffer();
		tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		tes.pos(-scale, -scale, 0).tex(0, 0).endVertex();
		tes.pos(-scale, scale, 0).tex(0, 1).endVertex();
		tes.pos(scale, scale, 0).tex(1, 1).endVertex();
		tes.pos(scale, -scale, 0).tex(1, 0).endVertex();
		tessellator.draw();

		if(data != null && data.getOverlay() != null) {
			GlStateManager.color(1, 1, 1, 1);
			bindTexture(data.getOverlay());

			tes.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
			tes.pos(-scale, -scale, 0).tex(0, 0).endVertex();
			tes.pos(-scale, scale, 0).tex(0, 1).endVertex();
			tes.pos(scale, scale, 0).tex(1, 1).endVertex();
			tes.pos(scale, -scale, 0).tex(1, 0).endVertex();
			tessellator.draw();
		}

		GlStateManager.popMatrix();
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();

	}

	@Override
	protected ResourceLocation getEntityTexture(EntityPortal entity) {

		return texture;
	}
}
