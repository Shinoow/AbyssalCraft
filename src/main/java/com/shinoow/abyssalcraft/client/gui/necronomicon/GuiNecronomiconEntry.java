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

import com.shinoow.abyssalcraft.api.necronomicon.GuiInstance;
import com.shinoow.abyssalcraft.api.necronomicon.INecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.*;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiNecronomiconEntry extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage, buttonNextPageLong;
	private ButtonNextPage buttonPreviousPage, buttonPreviousPageLong;
	private ButtonCategory[] buttons;
	private GuiButton buttonDone;
	private ButtonHome buttonHome;
	private ButtonInfo showNoteButtonLeft, showNoteButtonRight;
	public NecroData data;
	public GuiNecronomicon parent;
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
		currentNecro = this;
		if(isInfo)
			drawButtons();
		else {
			buttonList.clear();
			Keyboard.enableRepeatEvents(true);

			buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

			int i = (width - guiWidth) / 2;
			byte b0 = 2;
			buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true, false));
			buttonList.add(buttonNextPageLong = new ButtonNextPage(2, i + 203, b0 + 167, true, true));
			buttonList.add(buttonPreviousPage = new ButtonNextPage(3, i + 18, b0 + 154, false, false));
			buttonList.add(buttonPreviousPageLong = new ButtonNextPage(4, i + 23, b0 + 167, false, true));
			buttonList.add(buttonHome = new ButtonHome(5, i + 118, b0 + 167));
			buttonList.add(showNoteButtonLeft = new ButtonInfo(6, i + 102, b0 + 168));
			buttonList.add(showNoteButtonRight = new ButtonInfo(7, i + 142, b0 + 168));
			if(data != null)
				for(int n = 0; n < data.getContainedData().size(); n++){
					INecroData nd = data.getContainedData().get(n);
					buttonList.add(buttons[n] = new ButtonCategory(8 + n, i + (n < 7 ? 14 : 132), b0 + 24 + 17* (n < 7 ? n : n - 7),this, nd.getTitle(), getItem(nd.getDisplayIcon())).setLocked(!isUnlocked(nd.getResearch())));
				}
		}
		updateButtons();
		getHelper().updateSidebarIndex(this);
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo;
		buttonNextPageLong.visible = currTurnup < getTurnupLimit() -5;
		buttonPreviousPage.visible = true;
		buttonPreviousPageLong.visible = currTurnup > 4;
		buttonDone.visible = true;
		buttonHome.visible = true;
		showNoteButtonLeft.visible = reference1 != null && isInfo;
		showNoteButtonRight.visible = reference2 != null && isInfo;
		if(data != null)
			for(int i = 0; i < data.getContainedData().size(); i++)
				buttons[i].visible = !isInfo;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1){
				if (currTurnup < getTurnupLimit() -1)
					++currTurnup;
			} else if(button.id == 2){
				if(currTurnup < getTurnupLimit() -5)
					currTurnup += 5;
			} else if (button.id == 3){
				if(currTurnup == 0 && !isInfo)
					mc.displayGuiScreen(parent.withBookType(getBookType()));
				else if(currTurnup == 0 && isInfo){
					isInfo = false;
					currentData = -1;
					initGui();
					setTurnupLimit(2);
				} else if (currTurnup > 0)
					--currTurnup;
			} else if(button.id == 4){
				if(currTurnup > 4)
					currTurnup -= 5;
			} else if(button.id == 5){
				if(!isInfo)
					mc.displayGuiScreen(parent.withBookType(getBookType()));
				else {
					currTurnup = 0;
					isInfo = false;
					currentData = -1;
					initGui();
					setTurnupLimit(2);
				}
			} else if(button.id == 6) {
				if(reference1 != null)
					mc.displayGuiScreen(new GuiNecronomiconChapterEntry(getBookType(), reference1, this));
			} else if(button.id == 7) {
				if(reference2 != null)
					mc.displayGuiScreen(new GuiNecronomiconChapterEntry(getBookType(), reference2, this));
			} else if(button.id >= 8 && data.getContainedData().size() >= button.id - 7){
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
						drawButtons();
					}
			}
			updateButtons();
		}
	}

	private void drawButtons(){
		buttonList.clear();
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true, false));
		buttonList.add(buttonNextPageLong = new ButtonNextPage(2, i + 203, b0 + 167, true, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(3, i + 18, b0 + 154, false, false));
		buttonList.add(buttonPreviousPageLong = new ButtonNextPage(4, i + 23, b0 + 167, false, true));
		buttonList.add(buttonHome = new ButtonHome(5, i + 118, b0 + 167));
		buttonList.add(showNoteButtonLeft = new ButtonInfo(6, i + 102, b0 + 168));
		buttonList.add(showNoteButtonRight = new ButtonInfo(7, i + 142, b0 + 168));
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
