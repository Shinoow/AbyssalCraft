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
package com.shinoow.abyssalcraft.client.lib;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public final class GuiRenderHelper
{

	public static void renderTooltip(int x, int y, List<String> tooltipData)
	{
		int color = 0x505000ff;
		int color2 = 0xf0100010;

		renderTooltip(x, y, tooltipData, color, color2);
	}

	public static void renderTooltip(int x, int y, List<String> tooltipData, int colour, int color2) {
		boolean lighting = GL11.glGetBoolean(GL11.GL_LIGHTING);
		if(lighting)
			net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();

		if (!tooltipData.isEmpty()) {
			int var5 = 0;
			int var6;
			int var7;
			FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
			for (var6 = 0; var6 < tooltipData.size(); ++var6) {
				var7 = fontRenderer.getStringWidth(tooltipData.get(var6));
				if (var7 > var5)
					var5 = var7;
			}
			var6 = x + 12;
			var7 = y - 12;
			int var9 = 8;
			if (tooltipData.size() > 1)
				var9 += 2 + (tooltipData.size() - 1) * 10;
			float z = 300F;
			drawGradientRect(var6 - 3, var7 - 4, z, var6 + var5 + 3, var7 - 3, color2, color2);
			drawGradientRect(var6 - 3, var7 + var9 + 3, z, var6 + var5 + 3, var7 + var9 + 4, color2, color2);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 - 4, var7 - 3, z, var6 - 3, var7 + var9 + 3, color2, color2);
			drawGradientRect(var6 + var5 + 3, var7 - 3, z, var6 + var5 + 4, var7 + var9 + 3, color2, color2);
			int var12 = (colour & 0xFFFFFF) >> 1 | colour & -16777216;
			drawGradientRect(var6 - 3, var7 - 3 + 1, z, var6 - 3 + 1, var7 + var9 + 3 - 1, colour, var12);
			drawGradientRect(var6 + var5 + 2, var7 - 3 + 1, z, var6 + var5 + 3, var7 + var9 + 3 - 1, colour, var12);
			drawGradientRect(var6 - 3, var7 - 3, z, var6 + var5 + 3, var7 - 3 + 1, colour, colour);
			drawGradientRect(var6 - 3, var7 + var9 + 2, z, var6 + var5 + 3, var7 + var9 + 3, var12, var12);

			GlStateManager.disableDepth();
			for (int var13 = 0; var13 < tooltipData.size(); ++var13) {
				String var14 = tooltipData.get(var13);
				fontRenderer.drawStringWithShadow(var14, var6, var7, -1);
				if (var13 == 0)
					var7 += 2;
				var7 += 10;
			}
			GlStateManager.enableDepth();
		}
		if(!lighting)
			net.minecraft.client.renderer.RenderHelper.disableStandardItemLighting();
		GlStateManager.color(1F, 1F, 1F, 1F);
	}

	public static void drawGradientRect(int left, int top, float z, int right, int bottom, int startColor, int endColor)
	{
		float f = (startColor >> 24 & 255) / 255.0F;
		float f1 = (startColor >> 16 & 255) / 255.0F;
		float f2 = (startColor >> 8 & 255) / 255.0F;
		float f3 = (startColor & 255) / 255.0F;
		float f4 = (endColor >> 24 & 255) / 255.0F;
		float f5 = (endColor >> 16 & 255) / 255.0F;
		float f6 = (endColor >> 8 & 255) / 255.0F;
		float f7 = (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
		worldrenderer.pos(right, top, z).color(f1, f2, f3, f).endVertex();
		worldrenderer.pos(left, top, z).color(f1, f2, f3, f).endVertex();
		worldrenderer.pos(left, bottom, z).color(f5, f6, f7, f4).endVertex();
		worldrenderer.pos(right, bottom, z).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	public static void drawTexturedModalRect(int x, int y, float z, int textureX, int textureY, int width, int height) {
		drawTexturedModalRect(x, y, z, textureX, textureY, width, height, 0.00390625F, 0.00390625F);
	}

	public static void drawTexturedModalRect(int x, int y, float z, int textureX, int textureY, int width, int height, float f, float f1) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder worldrenderer = tessellator.getBuffer();
		worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
		worldrenderer.pos(x + 0, y + height, z).tex((textureX + 0) * f, (textureY + height) * f1).endVertex();
		worldrenderer.pos(x + width, y + height, z).tex((textureX + width) * f, (textureY + height) * f1).endVertex();
		worldrenderer.pos(x + width, y + 0, z).tex((textureX + width) * f, (textureY + 0) * f1).endVertex();
		worldrenderer.pos(x + 0, y + 0, z).tex((textureX + 0) * f, (textureY + 0) * f1).endVertex();
		tessellator.draw();
	}

	public static String getKeyDisplayString(String keyName) {
		String key = null;
		KeyBinding[] keys = Minecraft.getMinecraft().gameSettings.keyBindings;
		for(KeyBinding otherKey : keys)
			if(otherKey.getKeyDescription().equals(keyName)) {
				key = Keyboard.getKeyName(otherKey.getKeyCode());
				break;
			}

		return key;
	}
}
