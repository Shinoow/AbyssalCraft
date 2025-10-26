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
package com.shinoow.abyssalcraft.client.gui.necronomicon.buttons;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.client.GuiRenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ButtonCategory extends GuiButton {

	private GuiNecronomicon gui;
	private Item icon;
	private boolean locked;

	public ButtonCategory(int par1, int par2, int par3, GuiNecronomicon gui, String label, Item icon) {
		super(par1, par2, par3, 110, 16, I18n.format(label, new Object[0]));
		this.gui = gui;
		this.icon = icon;
	}

	public ButtonCategory(int par1, int par2, int par3, GuiNecronomicon gui, String label) {
		this(par1, par2, par3, gui, label, ACItems.necronomicon);
	}

	public ButtonCategory setLocked(boolean locked) {
		this.locked = locked;
		return this;
	}

	@Override
	public void drawButton(Minecraft mc, int mx, int my, float f) {
		if(!visible) return;

		boolean inside = mx >= x && my >= y && mx < x + width && my < y + height;

		ResourceLocation res = locked ? getTexture(ACItems.oblivion_catalyst) : getTexture(icon);
		if(res == null)
			res = getTexture(ACItems.necronomicon);

		mc.renderEngine.bindTexture(res);

		float s = 1F / 16F;

		GlStateManager.pushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		GlStateManager.enableBlend();
		GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
		GlStateManager.blendFunc(770, 771);
		if(inside)
			GuiRenderHelper.drawGradientRect(x, y, zLevel, x + width, y + height, -2130706433, 0x505000);
		GuiRenderHelper.drawTexturedModalRect(x, y, zLevel, 0, 0, 16, 16, s, s);
		GlStateManager.popMatrix();
		gui.getFontRenderer(locked).drawString(locked ? NecronomiconText.LABEL_TEST : displayString, x + 17, y + 3, 0);
	}

	ResourceLocation getTexture(Item par1){
		if(par1 == ACItems.abyssalnomicon)
			return new ResourceLocation("abyssalcraft:textures/items/abyssalnomicon.png");
		else if(par1 == ACItems.abyssal_wasteland_necronomicon)
			return new ResourceLocation("abyssalcraft:textures/items/necronomicon_cor.png");
		else if(par1 == ACItems.dreadlands_necronomicon)
			return new ResourceLocation("abyssalcraft:textures/items/necronomicon_dre.png");
		else if(par1 == ACItems.omothol_necronomicon)
			return new ResourceLocation("abyssalcraft:textures/items/necronomicon_omt.png");
		else if(par1 == ACItems.oblivion_catalyst)
			return new ResourceLocation("abyssalcraft:textures/items/necronahicon.png");
		else return new ResourceLocation("abyssalcraft:textures/items/necronomicon.png");
	}
}
