/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.client.gui.necronomicon.buttons;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ButtonNextPage extends GuiButton
{
	private final boolean field_146151_o;

	public ButtonNextPage(int par1, int par2, int par3, boolean par4)
	{
		super(par1, par2, par3, 23, 13, "");
		field_146151_o = par4;
	}

	/**
	 * Draws this button to the screen.
	 */
	@Override
	public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
	{
		if (visible)
		{
			boolean flag = p_146112_2_ >= xPosition && p_146112_3_ >= yPosition && p_146112_2_ < xPosition + width && p_146112_3_ < yPosition + height;
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			p_146112_1_.getTextureManager().bindTexture(new ResourceLocation("abyssalcraft:textures/gui/necronomicon.png"));
			int k = 0;
			int l = 192;

			if (flag)
				k += 23;

			if (!field_146151_o)
				l += 13;

			drawTexturedModalRect(xPosition, yPosition, k, l, 23, 13);
		}
	}
}