/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.gui;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityResearchTable;
import com.shinoow.abyssalcraft.common.inventory.ContainerResearchTable;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiResearchTable extends GuiContainer {

	private static final ResourceLocation transmutatorGuiTexture = new ResourceLocation("abyssalcraft:textures/gui/container/research_table.png");
	private TileEntityResearchTable tileTransmutator;

	public GuiResearchTable(InventoryPlayer par1InventoryPlayer, TileEntityResearchTable par2TileEntityTransmutator)
	{
		super(new ContainerResearchTable(par1InventoryPlayer, par2TileEntityTransmutator));
		tileTransmutator = par2TileEntityTransmutator;
		ySize = 256;
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
		String s = I18n.format("container.abyssalcraft.research_table", new Object[0]);
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 4, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 92, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(transmutatorGuiTexture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
