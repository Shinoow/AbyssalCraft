/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.client.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityMaterializer;
import com.shinoow.abyssalcraft.common.inventory.ContainerMaterializer;

public class GuiMaterializer extends GuiContainer {

	private static final ResourceLocation materializerGuiTexture = new ResourceLocation("abyssalcraft:textures/gui/container/materializer.png");
	private TileEntityMaterializer tileMaterializer;

	public GuiMaterializer(InventoryPlayer par1InventoryPlayer, TileEntityMaterializer par2TileEntityMaterializer)
	{
		super(new ContainerMaterializer(par1InventoryPlayer, par2TileEntityMaterializer));
		tileMaterializer = par2TileEntityMaterializer;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String s = tileMaterializer.hasCustomInventoryName() ? tileMaterializer.getInventoryName() : I18n.format(tileMaterializer.getInventoryName(), new Object[0]);
		fontRendererObj.drawString(s, xSize / 2 - fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, ySize - 96 + 2, 4210752);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(materializerGuiTexture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		//		int i1;
		//
		//		if (tileMaterializer.isTransmutating())
		//		{
		//			i1 = tileMaterializer.getBurnTimeRemainingScaled(12);
		//			drawTexturedModalRect(k + 56, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
		//		}
		//
		//		i1 = tileMaterializer.getProcessProgressScaled(24);
		//		drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
	}
}