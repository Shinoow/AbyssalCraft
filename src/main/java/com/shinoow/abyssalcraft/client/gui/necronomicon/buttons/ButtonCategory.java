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
package com.shinoow.abyssalcraft.client.gui.necronomicon.buttons;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.lib.GuiRenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ButtonCategory extends GuiButton {

	GuiNecronomicon gui;
	Item icon;
	float ticksHovered = 0F;

	public ButtonCategory(int par1, int par2, int par3, GuiNecronomicon gui, String label, Item icon) {
		super(par1, par2, par3, 120, 24, label);
		this.gui = gui;
		this.icon = icon;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my) {
		FontRenderer fr = mc.fontRendererObj;
		boolean inside = mx >= xPosition && my >= yPosition && mx < xPosition + width && my < yPosition + height;
		float time = 5F;
		if(inside)
			ticksHovered = Math.min(time, ticksHovered);
		else ticksHovered = Math.max(0F, ticksHovered);

		ResourceLocation res = getTexture(icon);
		if(res == null)
			res = getTexture(AbyssalCraft.necronomicon);

		mc.renderEngine.bindTexture(res);

		float s = 1F / 16F;

		GL11.glPushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.blendFunc(770, 771);
		GuiRenderHelper.drawTexturedModalRect(xPosition + 5, yPosition + 5, zLevel, 0, 0, 16, 16, s, s);
		GL11.glPopMatrix();
		fr.drawString(displayString, xPosition + 20, yPosition + 10, 0);
	}

	ResourceLocation getTexture(Item par1){
		if(par1 == AbyssalCraft.abyssalnomicon)
			return new ResourceLocation("abyssalcraft:textures/items/abyssalnomicon.png");
		else if(par1 == AbyssalCraft.necronomicon_cor)
			return new ResourceLocation("abyssalcraft:textures/items/necronomicon_cor.png");
		else if(par1 == AbyssalCraft.necronomicon_dre)
			return new ResourceLocation("abyssalcraft:textures/items/necronomicon_dre.png");
		else if(par1 == AbyssalCraft.necronomicon_omt)
			return new ResourceLocation("abyssalcraft:textures/items/necronomicon_omt.png");
		else if(par1 == AbyssalCraft.OC)
			return new ResourceLocation("abyssalcraft:textures/items/necronahicon.png");
		else return new ResourceLocation("abyssalcraft:textures/items/necronomicon.png");
	}
}