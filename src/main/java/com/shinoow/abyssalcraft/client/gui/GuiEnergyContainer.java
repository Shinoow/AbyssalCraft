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
package com.shinoow.abyssalcraft.client.gui;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEnergyContainer;
import com.shinoow.abyssalcraft.common.inventory.ContainerEnergyContainer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiEnergyContainer extends GuiContainer {

	private static final ResourceLocation engraverGuiTexture = new ResourceLocation("abyssalcraft:textures/gui/container/energycontainer.png");
	private TileEntityEnergyContainer tileEnergyContainer;

	public GuiEnergyContainer(InventoryPlayer par1InventoryPlayer, TileEntityEnergyContainer par2TileEntityEnergyContainer) {
		super(new ContainerEnergyContainer(par1InventoryPlayer, par2TileEntityEnergyContainer));
		tileEnergyContainer = par2TileEntityEnergyContainer;
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
		String s = tileEnergyContainer.hasCustomName() ? tileEnergyContainer.getName() : I18n.format(tileEnergyContainer.getName(), new Object[0]);
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
		String s2 = String.format("%d/%d PE", (int)tileEnergyContainer.getContainedEnergy(), tileEnergyContainer.getMaxEnergy());
		fontRenderer.drawString(s2, xSize / 2 - fontRenderer.getStringWidth(s2) / 2, 20, 4210752);
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
