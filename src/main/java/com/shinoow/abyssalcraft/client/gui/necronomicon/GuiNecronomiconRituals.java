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

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

public class GuiNecronomiconRituals extends GuiNecronomicon {

	private ButtonNextPage buttonPreviousPage;
	private GuiButton buttonDone;
	private ButtonCategory info, ritual0, ritual1, ritual2, ritual3, ritual4;

	public GuiNecronomiconRituals(int bookType){
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
		buttonList.add(buttonPreviousPage = new ButtonNextPage(1, i + 18, b0 + 154, false));
		buttonList.add(info = new ButtonCategory(2, i + 14, b0 + 24, this, NecronomiconText.LABEL_INFO, ACItems.necronomicon));
		buttonList.add(ritual0 = new ButtonCategory(3, i + 14, b0 + 41, this, NecronomiconText.LABEL_NORMAL, hasRituals(0) ? ACItems.necronomicon : ACItems.oblivion_catalyst));
		if(getBookType() >= 1)
			buttonList.add(ritual1 = new ButtonCategory(4, i + 14, b0 + 58, this, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND, hasRituals(1) ? ACItems.abyssal_wasteland_necronomicon : ACItems.oblivion_catalyst));
		if(getBookType() >= 2)
			buttonList.add(ritual2 = new ButtonCategory(5, i + 14, b0 + 75, this, NecronomiconText.LABEL_INFORMATION_DREADLANDS, hasRituals(2) ? ACItems.dreadlands_necronomicon : ACItems.oblivion_catalyst));
		if(getBookType() >= 3)
			buttonList.add(ritual3 = new ButtonCategory(6, i + 14, b0 + 92, this, NecronomiconText.LABEL_INFORMATION_OMOTHOL, hasRituals(3) ? ACItems.omothol_necronomicon : ACItems.oblivion_catalyst));
		if(getBookType() == 4)
			buttonList.add(ritual4 = new ButtonCategory(7, i + 14, b0 + 109, this, I18n.format(ACItems.abyssalnomicon.getUnlocalizedName() + ".name", new Object[0]), hasRituals(4) ? ACItems.abyssalnomicon : ACItems.oblivion_catalyst));
		updateButtons();
	}

	private void updateButtons()
	{
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		info.visible = true;
		ritual0.visible = true;
		if(getBookType() >= 1)
			ritual1.visible = true;
		if(getBookType() >= 2)
			ritual2.visible = true;
		if(getBookType() >= 3)
			ritual3.visible = true;
		if(getBookType() == 4)
			ritual4.visible = true;

	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			if(button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1)
				mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
			else if(button.id == 2)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("rituals"), this, ACItems.necronomicon));
			else if(button.id == 3){
				if(hasRituals(0))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 0));
			} else if(button.id == 4){
				if(hasRituals(1))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 1));
			} else if(button.id == 5){
				if(hasRituals(2))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 2));
			} else if(button.id == 6){
				if(hasRituals(3))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 3));
			} else if(button.id == 7)
				if(hasRituals(4))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 4));

			updateButtons();
		}
	}

	private boolean hasRituals(int book){

		List<NecronomiconRitual> rituals = Lists.newArrayList();
		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual.getBookType() == book)
				rituals.add(ritual);

		return !rituals.isEmpty();
	}

	@Override
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = NecronomiconText.LABEL_RITUALS;
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		writeText(2, NecronomiconText.RITUAL_INFO);
	}
}
