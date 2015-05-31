/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.gui.necronomicon;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

public class GuiNecronomiconSpells extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private GuiButton buttonDone;

	public GuiNecronomiconSpells(int bookType){
		super(bookType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 220, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
		//		buttonList.add(buttonCat1 = new ButtonCategory(3, i + 10, b0 + 30, this, 0, "necronomicon.index.information", AbyssalCraft.necronomicon));
		//		buttonList.add(buttonCat2 = new ButtonCategory(4, i + 10, b0 + 55, this, 0, "necronomicon.index.spells", AbyssalCraft.necronomicon));
		//		buttonList.add(buttonCat3 = new ButtonCategory(5, i + 10, b0 + 80, this, 0, "necronomicon.index.rituals", AbyssalCraft.necronomicon));
		//		if(bookType == 4)
		//			buttonList.add(buttonCat4 = new ButtonCategory(6, i + 10, b0 + 105, this, 0, "necronomicon.index.huh", AbyssalCraft.abyssalnomicon));
		//		else buttonList.add(buttonCat4 = new ButtonCategory(6, i + 10, b0 + 105, this, 0, "necronomicon.index.huh", AbyssalCraft.necronomicon));
		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = false;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		//		buttonCat1.visible = true;
		//		buttonCat2.visible = true;
		//		buttonCat3.visible = true;
		//		buttonCat4.visible = true;

	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if (button.id == 2)
				mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
			updateButtons();
		}
	}

	@Override
	protected void drawIndexText(){
		writeText(1, NecronomiconText.WIP);
	}
}
