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

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;
import com.shinoow.abyssalcraft.common.inventory.ContainerCrystallizer;

public class GuiCrystallizer extends GuiContainer {

	private static final ResourceLocation crystallizerGuiTextures = new ResourceLocation("abyssalcraft:textures/gui/container/crystallizer.png");
	private TileEntityCrystallizer tileCrystallizer;

	public GuiCrystallizer(InventoryPlayer par1InventoryPlayer, TileEntityCrystallizer par2TileEntityCrystallizer)
	{
		super(new ContainerCrystallizer(par1InventoryPlayer, par2TileEntityCrystallizer));
		tileCrystallizer = par2TileEntityCrystallizer;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String s = tileCrystallizer.hasCustomName() ? tileCrystallizer.getName() : I18n.format(tileCrystallizer.getName(), new Object[0]);
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 0xFFFFFF);
		fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 0xFFFFFF);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(crystallizerGuiTextures);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		int i1;

		if (tileCrystallizer.isCrystallizing())
		{
			i1 = tileCrystallizer.getShapeTimeRemainingScaled(12);
			drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		}

		i1 = tileCrystallizer.getFormProgressScaled(24);
		drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}
