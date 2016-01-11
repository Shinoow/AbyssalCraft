/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.render.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.shinoow.abyssalcraft.common.entity.EntityCoraliumArrow;

@SideOnly(Side.CLIENT)
public class RenderCoraliumArrow extends Render
{
	private static final ResourceLocation arrowTextures = new ResourceLocation("abyssalcraft:textures/model/corarrow.png");

	public void doRender(EntityCoraliumArrow par1EntityArrow, double par2, double par4, double par6, float par8, float par9)
	{
		bindEntityTexture(par1EntityArrow);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)par2, (float)par4, (float)par6);
		GL11.glRotatef(par1EntityArrow.prevRotationYaw + (par1EntityArrow.rotationYaw - par1EntityArrow.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(par1EntityArrow.prevRotationPitch + (par1EntityArrow.rotationPitch - par1EntityArrow.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		byte b0 = 0;
		float f2 = 0.0F;
		float f3 = 0.5F;
		float f4 = (0 + b0 * 10) / 32.0F;
		float f5 = (5 + b0 * 10) / 32.0F;
		float f6 = 0.0F;
		float f7 = 0.15625F;
		float f8 = (5 + b0 * 10) / 32.0F;
		float f9 = (10 + b0 * 10) / 32.0F;
		float f10 = 0.05625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float f11 = par1EntityArrow.arrowShake - par9;

		if (f11 > 0.0F)
		{
			float f12 = -MathHelper.sin(f11 * 3.0F) * f11;
			GL11.glRotatef(f12, 0.0F, 0.0F, 1.0F);
		}

		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(f10, f10, f10);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);
		GL11.glNormal3f(f10, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, f6, f8);
		tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, f7, f8);
		tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, f7, f9);
		tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, f6, f9);
		tessellator.draw();
		GL11.glNormal3f(-f10, 0.0F, 0.0F);
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(-7.0D, 2.0D, -2.0D, f6, f8);
		tessellator.addVertexWithUV(-7.0D, 2.0D, 2.0D, f7, f8);
		tessellator.addVertexWithUV(-7.0D, -2.0D, 2.0D, f7, f9);
		tessellator.addVertexWithUV(-7.0D, -2.0D, -2.0D, f6, f9);
		tessellator.draw();

		for (int i = 0; i < 4; ++i)
		{
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, f10);
			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(-8.0D, -2.0D, 0.0D, f2, f4);
			tessellator.addVertexWithUV(8.0D, -2.0D, 0.0D, f3, f4);
			tessellator.addVertexWithUV(8.0D, 2.0D, 0.0D, f3, f5);
			tessellator.addVertexWithUV(-8.0D, 2.0D, 0.0D, f2, f5);
			tessellator.draw();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	protected ResourceLocation getEntityTexture(EntityCoraliumArrow par1EntityArrow)
	{
		return arrowTextures;
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityCoraliumArrow)par1Entity);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRender((EntityCoraliumArrow)par1Entity, par2, par4, par6, par8, par9);
	}
}
