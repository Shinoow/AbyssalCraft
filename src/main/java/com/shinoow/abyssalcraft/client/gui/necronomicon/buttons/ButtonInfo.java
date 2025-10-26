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

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ButtonInfo extends GuiButton
{

	public ButtonInfo(int par1, int par2, int par3)
	{
		super(par1, par2, par3, 11, 11, "");
	}

	/**
	 * Draws this button to the screen.
	 */
	@Override
	public void drawButton(Minecraft mc, int mx, int mz, float f)
	{
		if(!visible) return;

		boolean flag = mx >= x && mz >= y && mx < x + width && mz < y + height;
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(new ResourceLocation("abyssalcraft:textures/gui/necronomicon.png"));
		int k = 110;
		int l = 206;

		if (flag)
			k += 18;

		drawTexturedModalRect(x, y, k, l, 10, 10);
	}
}
