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

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class GuiNecronomiconOther extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private ButtonCategory[] buttons = new ButtonCategory[AbyssalCraftAPI.getNecronomiconData().size()];
	private GuiButton buttonDone;

	private Map<NecroData, Integer> map = new HashMap<>();

	public GuiNecronomiconOther(int bookType){
		super(bookType);
		if(map.isEmpty())
			map.putAll(AbyssalCraftAPI.getNecronomiconData());
		if(map.size() > 10 && map.size() <= 20)
			setTurnupLimit(2);
		else if(map.size() > 20 && map.size() <= 30)
			setTurnupLimit(3);
		else setTurnupLimit(1);
	}

	@Override
	public void initGui(){
		currentNecro = this;
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true, false));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false, false));
		if(!map.isEmpty())
			for(int n = 0; n < map.size(); n++) {
				Entry<NecroData, Integer> entry = map.entrySet().toArray(new Entry[0])[n];
				if(currTurnup == 0){
					if(n < 7)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 14, b0 + 24 + 17*n,this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n > 6 && n < 14)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 132, b0 + 24 + 17*(n - 7),this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n == 14)
						break;
				} else if(currTurnup == 1){
					if(n < 21)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 14, b0 + 24 + 17*(n - 14),this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n > 20 && n < 28)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 132, b0 + 24 + 17*(n - 21),this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n == 28)
						break;
				} else if(currTurnup == 2)
					if(n < 35)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 14, b0 + 24 + 17*(n - 28),this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n > 34 && n < 42)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 132, b0 + 24 + 17*(n - 35),this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n == 42)
						break;
			}

		updateButtons();
	}

	private void updateButtons(){
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;

		if(!map.isEmpty())
			for(int i = 0; i < map.size(); i++)
				buttons[i].visible = equalsOrLower((int)map.values().toArray()[i], getKnowledgeLevel());
	}

	private boolean equalsOrLower(int index, int knowledgeLevel){
		boolean t1 = index == knowledgeLevel;
		boolean t2 = index == knowledgeLevel - 1 && knowledgeLevel - 1 >= 0;
		boolean t3 = index == knowledgeLevel - 2 && knowledgeLevel - 2 >= 0;
		boolean t4 = index == knowledgeLevel - 3 && knowledgeLevel - 3 >= 0;
		boolean t5 = index == knowledgeLevel - 4 && knowledgeLevel - 4 >= 0;
		return t1 || t2 || t3 || t4 || t5;
	}

	private String getTitle(String par1, int par2){
		return par2 > getKnowledgeLevel() ? NecronomiconText.LABEL_LOCKED : par1;
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
				drawButtons();
			} else if (button.id == 2){
				if(currTurnup == 0)
					mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
				else if (currTurnup > 0)
					--currTurnup;
				drawButtons();
			} else if(button.id >= 3 && map.size() >= button.id - 2)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[button.id - 3], this));
			updateButtons();
		}
	}

	private void drawButtons(){
		buttonList.clear();
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true, false));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false, false));

		if(!map.isEmpty())
			for(int n = 0; n < map.size(); n++) {
				Entry<NecroData, Integer> entry = map.entrySet().toArray(new Entry[0])[n];
				if(currTurnup == 0){
					if(n < 5)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 30 + 25*n,this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n > 4 && n < 10)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 130, b0 + 30 + 25*(n-5) + n,this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n == 10)
						break;
				} else if(currTurnup == 1){
					if(n < 15 && n > 9)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 30 + 25*(n-10) + n,this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n > 14 && n < 20)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 130, b0 + 30 + 25*(n-15) + n,this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n == 20)
						break;
				} else if(currTurnup == 2)
					if(n < 25 && n > 19)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 30 + 25*(n-20) + n,this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n > 24 && n < 30)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 130, b0 + 30 + 25*(n-25) + n,this, getTitle(entry.getKey().getTitle(), entry.getValue()), getItem(entry.getValue())));
					else if(n == 30)
						break;
			}
	}
}
