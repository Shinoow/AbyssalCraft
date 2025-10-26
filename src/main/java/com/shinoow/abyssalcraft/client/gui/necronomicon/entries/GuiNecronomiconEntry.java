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
package com.shinoow.abyssalcraft.client.gui.necronomicon.entries;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.necronomicon.GuiInstance;
import com.shinoow.abyssalcraft.api.necronomicon.INecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.gui.GuiButton;

public class GuiNecronomiconEntry extends GuiNecronomicon {

	private ButtonCategory[] buttons;
	public NecroData data;
	private int currentData;

	public GuiNecronomiconEntry(int bookType, NecroData nd, GuiNecronomicon gui){
		super(bookType);
		parent = gui;
		if(nd != null) {
			data = nd;
			buttons = new ButtonCategory[data.getContainedData().size()];
		}
	}

	@Override
	public GuiNecronomicon withBookType(int par1){
		super.withBookType(par1);
		if(data != null && !isUnlocked(data.getResearch()))
			isInvalid = true;
		return this;
	}

	@Override
	public void initGui(){
		if(isInvalid)
			mc.displayGuiScreen(parent.withBookType(getBookType()));
		initCommon();
		getHelper().updateSidebarIndex(this);
	}

	@Override
	protected void initButtonsInner() {
		int i = (width - guiWidth) / 2;
		if(data != null)
			for(int n = 0; n < data.getContainedData().size(); n++){
				INecroData nd = data.getContainedData().get(n);
				buttonList.add(buttons[n] = new ButtonCategory(8 + n, i + (n < 7 ? 14 : 132), 26 + 17* (n < 7 ? n : n - 7),this, nd.getTitle(), getItem(nd.getDisplayIcon())).setLocked(!isUnlocked(nd.getResearch())));
			}
	}

	@Override
	protected void updateButtonsInner() {
		buttonPreviousPage.visible = true;
		buttonHome.visible = true;
		if(data != null)
			for(int i = 0; i < data.getContainedData().size(); i++)
				buttons[i].visible = !isInfo;
	}

	@Override
	protected void actionPerformedInner(GuiButton button) {
		if(button.id >= 8 && data.getContainedData().size() >= button.id - 7){
			int i = button.id - 8;
			INecroData nd = data.getContainedData().get(i);
			if(isUnlocked(nd.getResearch()))
				if(nd instanceof GuiInstance)
					mc.displayGuiScreen(((GuiInstance)nd).getOpenGui(getBookType(), this));
				else if(nd instanceof NecroData)
					mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)nd, this));
				else {
					currentData = i;
					isInfo = true;
				}
		}
	}

	@Override
	protected void drawInformationText(int x, int y){

		if(currentData != -1)
			drawChapterOrPage(data.getContainedData().get(currentData), x, y);
		updateButtons();
	}

	@Override
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		if(data != null) {
			String stuff;
			stuff = localize(data.getTitle());
			boolean b = !isUnlocked(data.getResearch());
			getFontRenderer(b).drawSplitString(b ? NecronomiconText.LABEL_TEST : stuff, k + 17, b0 + 16, 116, 0xC40000);
			if(data.hasText()) writeText(2, b ? unknownFull : data.getText(), b);
		}
	}
}
