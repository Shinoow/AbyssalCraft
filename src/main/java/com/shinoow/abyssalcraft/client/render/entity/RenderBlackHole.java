/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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

import com.shinoow.abyssalcraft.common.entity.EntityBlackHole;

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
public class RenderBlackHole extends Render<EntityBlackHole> {

	private static final ResourceLocation texture = new ResourceLocation("abyssalcraft", "textures/model/black_hole.png");

	public RenderBlackHole(RenderManager manager){
		super(manager);
	}

	int ticks;

	@Override
	public void doRender(EntityBlackHole entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		ticks++;

		float wobbleScale = 0.05f;
		float wobble = ticks / 10f;
		float wobbleX = (float) (Math.sin(wobble) * wobbleScale) + 1;
		float wobbleY = (float) (Math.sin(wobble) * -1 * wobbleScale) + 1;

		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 0.5, z + 0.5);
		GlStateManager.scale(wobbleX, wobbleY, 1);

		bindEntityTexture(entity);

		float rot = ticks * 2;
		double scale = 2;

		GlStateManager.pushMatrix();

		GlStateManager.rotate(-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(renderManager.playerViewX, 1.0F, 0.0F, 0.0F);

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
		GlStateManager.popMatrix();
		GlStateManager.popMatrix();

		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBlackHole entity) {

		return texture;
	}
}
