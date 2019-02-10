/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.gui;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityRendingPedestal;
import com.shinoow.abyssalcraft.common.inventory.ContainerRendingPedestal;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiRendingPedestal extends GuiContainer {

	private static final ResourceLocation engraverGuiTexture = new ResourceLocation("abyssalcraft:textures/gui/container/rendingpedestal.png");
	private TileEntityRendingPedestal tileRendingPedestal;

	public GuiRendingPedestal(InventoryPlayer par1InventoryPlayer, TileEntityRendingPedestal par2TileEntityRendingPedestal) {
		super(new ContainerRendingPedestal(par1InventoryPlayer, par2TileEntityRendingPedestal));
		tileRendingPedestal = par2TileEntityRendingPedestal;
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
		String s = tileRendingPedestal.hasCustomName() ? tileRendingPedestal.getName() : I18n.format(tileRendingPedestal.getName(), new Object[0]);
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
		String s2 = String.format("%d/%d PE", (int)tileRendingPedestal.getContainedEnergy(), tileRendingPedestal.getMaxEnergy());
		fontRenderer.drawString(s2, xSize / 2 - fontRenderer.getStringWidth(s2) / 2, 20, 4210752);
		fontRenderer.drawString("A: " + tileRendingPedestal.getEnergy(1) + "/100", 55, 29, 4210752);
		fontRenderer.drawString("D: " + tileRendingPedestal.getEnergy(2) + "/100", 108, 29, 4210752);
		fontRenderer.drawString("O: " + tileRendingPedestal.getEnergy(3) + "/100", 55, 37, 4210752);
		fontRenderer.drawString("S: " + tileRendingPedestal.getEnergy(0) + "/200", 108, 37, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(engraverGuiTexture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		//		i1 = tileEnergyContainer.getProcessProgressScaled(24);
		//		drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}
