/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
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
public class ButtonNextPage extends GuiButton
{
	private final boolean invert, isLong;

	public ButtonNextPage(int par1, int par2, int par3, boolean par4, boolean isLong)
	{
		super(par1, par2, par3, isLong ? 30 : 23, 13, "");
		invert = par4;
		this.isLong = isLong;
	}

	/**
	 * Draws this button to the screen.
	 */
	@Override
	public void drawButton(Minecraft mc, int mx, int mz, float f)
	{
		if (visible)
		{
			boolean flag = mx >= x && mz >= y && mx < x + width && mz < y + height;
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(new ResourceLocation("abyssalcraft:textures/gui/necronomicon.png"));
			int k = 0;
			int l = 192;

			if(isLong)
				k += 46;
			if (flag)
				k += isLong ? 30 : 23;

			if (!invert)
				l += 13;

			drawTexturedModalRect(x, y, k, l, isLong ? 30 : 23, 13);
		}
	}
}
