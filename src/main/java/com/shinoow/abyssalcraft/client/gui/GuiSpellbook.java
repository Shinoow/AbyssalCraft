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
package com.shinoow.abyssalcraft.client.gui;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.common.inventory.ContainerSpellbook;

public class GuiSpellbook extends GuiContainer {

	private static final ResourceLocation iconLocation = new ResourceLocation("abyssalcraft:textures/gui/container/spellcraft_test.png");

	private ContainerSpellbook spellbook;

	public GuiSpellbook(ContainerSpellbook spellbook) {
		super(spellbook);
		this.spellbook = spellbook;
		ySize = 256;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		IEnergyContainerItem container = (IEnergyContainerItem)spellbook.book.getItem();
		String s = String.format("%d/%d PE", (int)container.getContainedEnergy(spellbook.book), container.getMaxEnergy(spellbook.book));
		fontRendererObj.drawString(s, 15, 15, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 6, ySize - 92, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(iconLocation);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		//		drawTexturedModalRect(k, l + invRows * 18 + 17, 0, 160, xSize, 96);
	}
}
