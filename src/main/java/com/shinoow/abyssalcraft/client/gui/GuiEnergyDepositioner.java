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

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityEnergyDepositioner;
import com.shinoow.abyssalcraft.common.inventory.ContainerEnergyDepositioner;

public class GuiEnergyDepositioner extends GuiContainer {

	private static final ResourceLocation engraverGuiTexture = new ResourceLocation("abyssalcraft:textures/gui/container/energydepositioner.png");
	private TileEntityEnergyDepositioner tilePEGenerator;

	public GuiEnergyDepositioner(InventoryPlayer par1InventoryPlayer, TileEntityEnergyDepositioner par2TileEntityEnergyContainer) {
		super(new ContainerEnergyDepositioner(par1InventoryPlayer, par2TileEntityEnergyContainer));
		tilePEGenerator = par2TileEntityEnergyContainer;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String s = tilePEGenerator.hasCustomName() ? tilePEGenerator.getName() : I18n.format(tilePEGenerator.getName(), new Object[0]);
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
		String s2 = String.format("%d/%d PE", tilePEGenerator.getField(1), 10000);
		fontRendererObj.drawString(s2, xSize / 2 - fontRendererObj.getStringWidth(s2) / 2, 20, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(engraverGuiTexture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		int i1 = tilePEGenerator.getField(0) * 24 / 200;
		drawTexturedModalRect(k + 76, l + 38, 176, 14, i1 + 1, 16);
	}
}
