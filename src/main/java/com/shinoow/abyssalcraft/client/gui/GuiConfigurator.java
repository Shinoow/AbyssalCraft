/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.gui;

import com.shinoow.abyssalcraft.common.inventory.ContainerConfigurator;
import com.shinoow.abyssalcraft.common.inventory.InventoryConfigurator;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.UpdateModeMessage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuiConfigurator extends GuiContainer
{

	private static final ResourceLocation iconLocation = new ResourceLocation("abyssalcraft:textures/gui/container/configurator.png");

	private final InventoryConfigurator inventory;

	public GuiConfigurator(ContainerConfigurator container)
	{
		super(container);
		inventory = container.getConfiguratorInventory();
	}

	@Override
	public void initGui(){
		super.initGui();

		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		buttonList.add(new GuiButton(0, i + 122, j + 36, 40, 20, inventory.getField(0) == 1 ? "true" : "false"));
		buttonList.add(new GuiButton(1, i + 122, j + 57, 40, 20, inventory.getField(1) == 1 ? "true" : "false"));
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.enabled && (button.id == 0 || button.id == 1)){
			button.displayString = inventory.getField(button.id) == 0 ? "true" : "false";
			inventory.setField(button.id, inventory.getField(button.id) == 1 ? 0 : 1);

			PacketDispatcher.sendToServer(new UpdateModeMessage(button.id, 1));
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String s = inventory.hasCustomName() ? inventory.getName() : I18n.format(inventory.getName());
		fontRenderer.drawString(s, xSize /2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
		fontRenderer.drawString(I18n.format("tooltip.configurator.filter.0"), 8, ySize - 125 + 2, 4210752);
		fontRenderer.drawString(I18n.format("tooltip.configurator.filter.1"), 8, ySize - 108 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(iconLocation);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
	}
}
