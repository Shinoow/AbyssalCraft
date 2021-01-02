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

import java.io.IOException;

import org.lwjgl.input.Mouse;

import com.shinoow.abyssalcraft.api.recipe.MaterializerRecipes;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityMaterializer;
import com.shinoow.abyssalcraft.common.inventory.ContainerMaterializer;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiMaterializer extends GuiContainer {

	private static final ResourceLocation materializerGuiTexture = new ResourceLocation("abyssalcraft:textures/gui/container/materializer.png");
	private TileEntityMaterializer tileMaterializer;

	private Scrollbar scrollbar;

	public GuiMaterializer(InventoryPlayer par1InventoryPlayer, TileEntityMaterializer par2TileEntityMaterializer)
	{
		super(new ContainerMaterializer(par1InventoryPlayer, par2TileEntityMaterializer));
		tileMaterializer = par2TileEntityMaterializer;
		scrollbar = new Scrollbar(151, 15, 12, 54);
		scrollbar.setMaxOffset(MaterializerRecipes.instance().getMaterializationList().size() / 6);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		renderHoveredToolTip(mouseX, mouseY);

		scrollbar.update(this, mouseX - guiLeft, mouseY - guiTop);
		scrollbar.setEnabled(((ContainerMaterializer) inventorySlots).canScroll());
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		String s = tileMaterializer.hasCustomName() ? tileMaterializer.getName() : I18n.format(tileMaterializer.getName(), new Object[0]);
		fontRenderer.drawString(s, xSize / 2 - fontRenderer.getStringWidth(s) / 2, 6, 4210752);
		String s1 = tileMaterializer.hasSecondCustomName() ? tileMaterializer.getSecondName() : I18n.format(tileMaterializer.getSecondName(), new Object[0]);
		fontRenderer.drawString(s1, 8, ySize - 96 + 2, 4210752);

		((ContainerMaterializer)inventorySlots).scrollTo(scrollbar.getOffset());
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(materializerGuiTexture);
		int k = (width - xSize) / 2;
		int l = (height - ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);

		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

		scrollbar.draw(this);

		if(tileMaterializer.hasCustomName()){
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.getTextureManager().bindTexture(materializerGuiTexture);
			drawTexturedModalRect(guiLeft - 17, guiTop + 2, 176, 29, 15, 22);
		}
	}

	@Override
	public void handleMouseInput() throws IOException {
		super.handleMouseInput();

		int d = Mouse.getEventDWheel();

		if (scrollbar != null && d != 0) scrollbar.wheel(d);
	}

	public boolean inBounds(int x, int y, int w, int h, int ox, int oy) {
		return ox >= x && ox <= x + w && oy >= y && oy <= y + h;
	}

	public class Scrollbar {
		private static final int SCROLLER_HEIGHT = 15;

		private int x;
		private int y;
		private int width;
		private int height;
		private boolean enabled = false;

		private int offset;
		private int maxOffset;

		private boolean wasClicking = false;
		private boolean isScrolling = false;

		public Scrollbar(int x, int y, int width, int height) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		public void setEnabled(boolean enabled) {
			this.enabled = enabled;
		}

		public boolean isEnabled() {
			return enabled;
		}

		public void draw(GuiMaterializer gui) {
			mc.getTextureManager().bindTexture(materializerGuiTexture);
			gui.drawTexturedModalRect(gui.guiLeft + x, gui.guiTop + y + (int) Math.min(height - SCROLLER_HEIGHT, (float) offset / (float) maxOffset * (height - SCROLLER_HEIGHT)), isEnabled() ? 176 : 188, 14, 12, 15);
		}

		public void update(GuiMaterializer gui, int mouseX, int mouseY) {
			if (!isEnabled()) {
				isScrolling = false;
				wasClicking = false;
			} else {
				boolean down = Mouse.isButtonDown(0);

				if (!wasClicking && down && gui.inBounds(x, y, width, height, mouseX, mouseY)) isScrolling = true;

				if (!down) isScrolling = false;

				wasClicking = down;

				if (isScrolling) setOffset((int) Math.floor((float) (mouseY - SCROLLER_HEIGHT) / (float) (height - SCROLLER_HEIGHT) * maxOffset));
			}
		}

		public void wheel(int delta) {
			if (isEnabled()) setOffset(offset + Math.max(Math.min(-delta, 1), -1));
		}

		public void setMaxOffset(int maxOffset) {
			this.maxOffset = maxOffset;

			if (offset > maxOffset) offset = Math.max(0, maxOffset);
		}

		public int getOffset() {
			return offset;
		}

		public void setOffset(int offset) {
			if (offset >= 0 && offset <= maxOffset) this.offset = offset;
		}
	}
}
