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
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.inventory.ContainerCrystalBag;
import com.shinoow.abyssalcraft.common.inventory.InventoryCrystalBag;

public class GuiCrystalBag extends GuiContainer
{

	private static final ResourceLocation iconLocation = new ResourceLocation("abyssalcraft:textures/gui/container/crystalbag.png");

	private final InventoryCrystalBag inventory;

	private int invRows;

	public GuiCrystalBag(ContainerCrystalBag container)
	{
		super(container);
		inventory = container.getBagInventory();
		short short1 = 256;
		int i = short1 - 138;
		invRows = inventory.getSizeInventory() / 9;
		ySize = i + invRows * 18;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String s = inventory.hasCustomName() ? inventory.getName() : I18n.format(inventory.getName());
		fontRenderer.drawString(s, 6, 6, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory"), 6, ySize - 102 + 4, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(iconLocation);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, invRows * 18 + 17);
		drawTexturedModalRect(k, l + invRows * 18 + 17, 0, 160, xSize, 96);
	}
}
