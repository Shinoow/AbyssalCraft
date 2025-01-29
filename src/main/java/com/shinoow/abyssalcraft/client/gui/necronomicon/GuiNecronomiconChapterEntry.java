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
package com.shinoow.abyssalcraft.client.gui.necronomicon;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiNecronomiconChapterEntry extends GuiNecronomiconEntry {

	private GuiButton buttonDone;
	private ButtonNextPage buttonPreviousPage;
	private Chapter chapter;

	public GuiNecronomiconChapterEntry(int bookType, Chapter chapter, GuiNecronomicon gui) {
		super(bookType, null, gui);
		parent = gui;
		this.chapter = chapter;
		isInfo = true;
	}

	@Override
	public GuiNecronomicon withBookType(int par1){
		super.withBookType(par1);
		return this;
	}


	@Override
	public void initGui(){
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(1, i + 18, b0 + 154, false, false));

		buttonDone.visible = true;
		buttonPreviousPage.visible = true;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1)
				mc.displayGuiScreen(parent.withBookType(getBookType()));
	}

	@Override
	protected void drawInformationText(int x, int y){
		if(chapter != null)
			drawChapter(chapter, x, y);
	}
}
