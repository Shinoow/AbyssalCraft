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
package com.shinoow.abyssalcraft.client.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class ItemRenderHelper
{
	private static final ResourceLocation glintPNG = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	public static void renderItem(EntityLivingBase living, ItemStack stack, int layer) {
		renderItem(living, stack, layer, false);
	}

	public static void renderItem(EntityLivingBase living, ItemStack stack, int layer, boolean isGlowing) {
		GL11.glPushMatrix();

		IIcon icon = living.getItemIcon(stack, layer);

		if( icon == null ) {
			GL11.glPopMatrix();
			return;
		}

		float minU = icon.getMinU();
		float maxU = icon.getMaxU();
		float minV = icon.getMinV();
		float maxV = icon.getMaxV();
		float transX = 0.0F;
		float transY = 0.3F;
		float scale = 1.5F;
		Tessellator tessellator = Tessellator.instance;

		Minecraft.getMinecraft().renderEngine.bindTexture(
				Minecraft.getMinecraft().renderEngine.getResourceLocation(stack.getItemSpriteNumber())
				);

		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-transX, -transY, 0.0F);
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);

		renderItemIn2D(tessellator, maxU, minV, minU, maxV, icon.getIconWidth(), icon.getIconHeight(), 0.0625F, isGlowing);

		if( stack != null && stack.hasEffect(layer) ) {
			float baseClr = 0.76F;
			float glintScale = 0.125F;
			float glintTransX = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;

			GL11.glDepthFunc(GL11.GL_EQUAL);
			GL11.glDisable(GL11.GL_LIGHTING);

			Minecraft.getMinecraft().renderEngine.bindTexture(glintPNG);

			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
			GL11.glColor4f(0.5F * baseClr, 0.25F * baseClr, 0.8F * baseClr, 1.0F);
			GL11.glMatrixMode(GL11.GL_TEXTURE);
			GL11.glPushMatrix();
			GL11.glScalef(glintScale, glintScale, glintScale);
			GL11.glTranslatef(glintTransX, 0.0F, 0.0F);
			GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);

			renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F, false);

			GL11.glPopMatrix();
			GL11.glPushMatrix();
			GL11.glScalef(glintScale, glintScale, glintScale);

			glintTransX = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;

			GL11.glTranslatef(-glintTransX, 0.0F, 0.0F);
			GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);

			renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F, false);

			GL11.glPopMatrix();
			GL11.glColor4f(1F, 1F, 1F, 1.0F);
			GL11.glMatrixMode(GL11.GL_MODELVIEW);
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glDepthFunc(GL11.GL_LEQUAL);
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
	}

	private static void renderItemIn2D(Tessellator tess, float minU, float minV, float maxU, float maxV, int scaleX, int scaleY, float negZLevel, boolean isGlowing) {
		if( isGlowing ) {
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_LIGHT0);
			GL11.glDisable(GL11.GL_LIGHT1);
			GL11.glDisable(GL11.GL_COLOR_MATERIAL);

			float prevLGTX = OpenGlHelper.lastBrightnessX;
			float prevLGTY = OpenGlHelper.lastBrightnessY;
			char bright = 0x000F0;
			int brightX = bright % 65536;
			int brightY = bright / 65536;

			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightX / 1.0F, brightY / 1.0F);
			ItemRenderer.renderItemIn2D(tess, minU, minV, maxU, maxV, scaleX, scaleY, negZLevel);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, prevLGTX, prevLGTY);

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_LIGHT0);
			GL11.glEnable(GL11.GL_LIGHT1);
			GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		} else
			ItemRenderer.renderItemIn2D(tess, minU, minV, maxU, maxV, scaleX, scaleY, negZLevel);
	}

	public static void renderGlint(int par1, int minU, int minV, int maxU, int maxV, double zLevel) {
		for( int j1 = 0; j1 < 2; ++j1 ) {
			if( j1 == 0 )
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);

			if( j1 == 1 )
				GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);

			float f = 0.00390625F;
			float f1 = 0.00390625F;
			float f2 = Minecraft.getSystemTime() % (3000 + j1 * 1873) / (3000.0F + j1 * 1873) * 256.0F;
			float f3 = 0.0F;
			Tessellator tessellator = Tessellator.instance;
			float f4 = 4.0F;

			if( j1 == 1 )
				f4 = -1.0F;

			tessellator.startDrawingQuads();
			tessellator.addVertexWithUV(minU + 0, minV + maxV, zLevel, (f2 + maxV * f4) * f, (f3 + maxV) * f1);
			tessellator.addVertexWithUV(minU + maxU, minV + maxV, zLevel, (f2 + maxU + maxV * f4) * f, (f3 + maxV) * f1);
			tessellator.addVertexWithUV(minU + maxU, minV + 0, zLevel, (f2 + maxU) * f, (f3 + 0.0F) * f1);
			tessellator.addVertexWithUV(minU + 0, minV + 0, zLevel, (f2 + 0.0F) * f, (f3 + 0.0F) * f1);
			tessellator.draw();
		}
	}
}
