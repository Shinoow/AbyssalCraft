/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.gui;

import com.shinoow.abyssalcraft.common.inventory.ContainerSequentialBrewingStand;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiSequentialBrewingStand extends GuiContainer
{
	private static final ResourceLocation BREWING_STAND_GUI_TEXTURES = new ResourceLocation("abyssalcraft", "textures/gui/container/sequential_brewing_stand.png");
	private static final int[] BUBBLELENGTHS = new int[] {29, 24, 20, 16, 11, 6, 0};
	/** The player inventory bound to this GUI. */
	private final InventoryPlayer playerInventory;
	private final IInventory tileBrewingStand;

	public GuiSequentialBrewingStand(InventoryPlayer playerInv, IInventory inventoryIn)
	{
		super(new ContainerSequentialBrewingStand(playerInv, inventoryIn));
		playerInventory = playerInv;
		tileBrewingStand = inventoryIn;
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);
	}

	/**
	 * Draw the foreground layer for the GuiContainer (everything in front of the items)
	 */
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		String s = tileBrewingStand.getDisplayName().getUnformattedText();
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		fontRenderer.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, ySize - 96 + 2, 4210752);
	}

	/**
	 * Draws the background layer of this container (behind the items).
	 */
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(BREWING_STAND_GUI_TEXTURES);
		int i = (width - xSize) / 2;
		int j = (height - ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, xSize, ySize);
		int k = tileBrewingStand.getField(1);
		int l = MathHelper.clamp((18 * k + 20 - 1) / 20, 0, 18);

		if (l > 0)
			this.drawTexturedModalRect(i + 60, j + 44, 176, 29, l, 4);

		int i1 = tileBrewingStand.getField(0);

		if (i1 > 0)
		{
			int j1 = (int)(28.0F * (1.0F - i1 / 400.0F));

			if (j1 > 0)
				this.drawTexturedModalRect(i + 97, j + 16, 176, 0, 9, j1);

			j1 = BUBBLELENGTHS[i1 / 2 % 7];

			if (j1 > 0)
				this.drawTexturedModalRect(i + 63, j + 14 + 29 - j1, 185, 29 - j1, 12, j1);
		}
	}
}
