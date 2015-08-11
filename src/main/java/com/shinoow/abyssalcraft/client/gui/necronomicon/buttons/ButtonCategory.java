/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
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
		FontRenderer fr = mc.fontRenderer;
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
		GL11.glColor4f(1F, 1F, 1F, 1);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		drawTexturedModalRect(xPosition + 5, yPosition + 5, zLevel, 0, 0, 16, 16, s, s);
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

	public static void drawTexturedModalRect(int par1, int par2, float z, int par3, int par4, int par5, int par6, float f, float f1) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV(par1 + 0, par2 + par6, z, (par3 + 0) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + par6, z, (par3 + par5) * f, (par4 + par6) * f1);
		tessellator.addVertexWithUV(par1 + par5, par2 + 0, z, (par3 + par5) * f, (par4 + 0) * f1);
		tessellator.addVertexWithUV(par1 + 0, par2 + 0, z, (par3 + 0) * f, (par4 + 0) * f1);
		tessellator.draw();
	}

}
