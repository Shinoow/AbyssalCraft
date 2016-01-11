/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;

public class GuiNecronomiconOther extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private ButtonCategory[] buttons = new ButtonCategory[AbyssalCraftAPI.getNecronomiconData().size()];
	private GuiButton buttonDone;

	private HashMap<NecroData, Integer> map = Maps.newHashMap();

	public GuiNecronomiconOther(int bookType){
		super(bookType);
		map.putAll(AbyssalCraftAPI.getNecronomiconData());
		if(map.size() > 10 && map.size() <= 20)
			setTurnupLimit(2);
		else if(map.size() > 20 && map.size() <= 30)
			setTurnupLimit(3);
		else setTurnupLimit(1);
	}

	@Override
	public void initGui(){
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
		if(!map.isEmpty())
			for(int n = 0; n < map.size(); n++)
				if(currTurnup == 0){
					if(n < 7)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 20 + 17*n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n > 6 && n < 14)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 130, b0 + 20 + 17*n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n == 14)
						break;
				} else if(currTurnup == 1){
					if(n < 21)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 20 + 17*n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n > 20 && n < 28)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 130, b0 + 20 + 17*n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n == 28)
						break;
				} else if(currTurnup == 2)
					if(n < 35)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 20 + 17*n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n > 34 && n < 42)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 130, b0 + 20 + 17*n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n == 42)
						break;

		updateButtons();
	}

	private void updateButtons(){
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;

		if(!map.isEmpty())
			for(int i = 0; i < map.size(); i++)
				buttons[i].visible = equalsOrLower((int)map.values().toArray()[i], getBookType());
	}

	private boolean equalsOrLower(int par1, int par2){
		boolean t1 = par1 == par2;
		boolean t2 = par1 == par2 - 1 && par2 - 1 >= 0;
		boolean t3 = par1 == par2 - 2 && par2 - 2 >= 0;
		boolean t4 = par1 == par2 - 3 && par2 - 3 >= 0;
		boolean t5 = par1 == par2 - 4 && par2 - 4 >= 0;
		return t1 || t2 || t3 || t4 || t5;
	}

	private Item getItem(int par1){
		if(par1 > getBookType())
			return AbyssalCraft.OC;
		switch(par1){
		case 0:
			return AbyssalCraft.necronomicon;
		case 1:
			return AbyssalCraft.necronomicon_cor;
		case 2:
			return AbyssalCraft.necronomicon_dre;
		case 3:
			return AbyssalCraft.necronomicon_omt;
		case 4:
			return AbyssalCraft.abyssalnomicon;
		default:
			return AbyssalCraft.necronomicon;
		}
	}

	private String getTitle(String par1, int par2){
		return par2 > getBookType() ? NecronomiconText.LABEL_LOCKED : par1;
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
			} else if(button.id == 3)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[0], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[0])));
			else if(button.id == 4)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[1], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[1])));
			else if(button.id == 5)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[2], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[2])));
			else if(button.id == 6)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[3], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[3])));
			else if(button.id == 7)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[4], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[4])));
			else if(button.id == 8)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[5], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[5])));
			else if(button.id == 9)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[6], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[6])));
			else if(button.id == 10)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[7], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[7])));
			else if(button.id == 11)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[8], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[8])));
			else if(button.id == 12)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[9], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[9])));
			else if(button.id == 13)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[10], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[10])));
			else if(button.id == 14)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[11], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[11])));
			else if(button.id == 15)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[12], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[12])));
			else if(button.id == 16)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[13], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[13])));
			else if(button.id == 17)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[14], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[14])));
			else if(button.id == 18)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[15], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[15])));
			else if(button.id == 19)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[16], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[16])));
			else if(button.id == 20)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[17], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[17])));
			else if(button.id == 21)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[18], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[18])));
			else if(button.id == 22)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[19], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[19])));
			else if(button.id == 22)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[20], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[20])));
			else if(button.id == 23)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[21], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[21])));
			else if(button.id == 24)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[22], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[22])));
			else if(button.id == 25)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[23], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[23])));
			else if(button.id == 26)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[24], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[24])));
			else if(button.id == 27)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[25], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[25])));
			else if(button.id == 28)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[26], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[26])));
			else if(button.id == 29)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[27], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[27])));
			else if(button.id == 30)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[28], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[28])));
			else if(button.id == 31)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)map.keySet().toArray()[29], new GuiNecronomiconOther(getBookType()), getItem((int)map.values().toArray()[29])));
			updateButtons();
		}
	}

	@SuppressWarnings("unchecked")
	private void drawButtons(){
		buttonList.clear();
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));

		if(!map.isEmpty())
			for(int n = 0; n < map.size(); n++)
				if(currTurnup == 0){
					if(n < 5)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 30 + 25*n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n > 4 && n < 10)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 130, b0 + 30 + 25*(n-5) + n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n == 10)
						break;
				} else if(currTurnup == 1){
					if(n < 15 && n > 9)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 30 + 25*(n-10) + n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n > 14 && n < 20)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 130, b0 + 30 + 25*(n-15) + n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n == 20)
						break;
				} else if(currTurnup == 2)
					if(n < 25 && n > 19)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 30 + 25*(n-20) + n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n > 24 && n < 30)
						buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 130, b0 + 30 + 25*(n-25) + n,this, getTitle(((NecroData)map.keySet().toArray()[n]).getTitle(), (int)map.values().toArray()[n]), getItem((int)map.values().toArray()[n])));
					else if(n == 30)
						break;
	}
}
