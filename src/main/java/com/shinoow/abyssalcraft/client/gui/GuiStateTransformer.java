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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityStateTransformer;
import com.shinoow.abyssalcraft.common.inventory.ContainerStateTransformer;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.UpdateModeMessage;

public class GuiStateTransformer extends GuiContainer {

	private static final ResourceLocation engraverGuiTexture = new ResourceLocation("abyssalcraft:textures/gui/container/statetransformer.png");
	private TileEntityStateTransformer tileCompressorThing;

	public GuiStateTransformer(InventoryPlayer par1InventoryPlayer, TileEntityStateTransformer par2TileEntityEnergyContainer) {
		super(new ContainerStateTransformer(par1InventoryPlayer, par2TileEntityEnergyContainer));
		tileCompressorThing = par2TileEntityEnergyContainer;
		ySize = 238;
	}

	@Override
	public void initGui(){
		super.initGui();

		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		buttonList.add(new GuiButton(0, i + 2, j + 95, 40, 20, I18n.format("gui.abyssalcraft.statetransformer."+ (tileCompressorThing.mode == 0 ? "insert" : "extract"))));
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if(button.enabled && button.id == 0){
			tileCompressorThing.mode = tileCompressorThing.mode == 0 ? 1 : 0;
			buttonList.clear();
			int i = (width - xSize) / 2;
			int j = (height - ySize) / 2;
			buttonList.add(new GuiButton(0, i + 2, j + 95, 40, 20, I18n.format("gui.abyssalcraft.statetransformer."+ (tileCompressorThing.mode == 0 ? "insert" : "extract"))));
			PacketDispatcher.sendToServer(new UpdateModeMessage(tileCompressorThing.mode));
		}
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();

		buttonList.get(0).enabled = tileCompressorThing.processingTime == 0;
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
		String s = tileCompressorThing.hasCustomName() ? tileCompressorThing.getName() : I18n.format(tileCompressorThing.getName(), new Object[0]);
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(engraverGuiTexture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		int i1 = tileCompressorThing.processingTime * 24 / 200;
		drawTexturedModalRect(k + 6, l + 43, 176, 14, i1 + 1, 16);
	}
}
