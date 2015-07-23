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

import java.util.List;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

public class GuiNecronomiconRituals extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private GuiButton buttonDone;
	private ButtonCategory info, ritual0, ritual1, ritual2, ritual3, ritual4;
	private boolean isRitualInfo;

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
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 220, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
		buttonList.add(info = new ButtonCategory(3, i + 10, b0 + 30, this, NecronomiconText.LABEL_INFO, AbyssalCraft.necronomicon));
		buttonList.add(ritual0 = new ButtonCategory(4, i + 10, b0 + 55, this, NecronomiconText.LABEL_NORMAL, hasRituals(0) ? AbyssalCraft.necronomicon : AbyssalCraft.OC));
		if(getBookType() >= 1)
			buttonList.add(ritual1 = new ButtonCategory(5, i + 10, b0 + 80, this, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND, hasRituals(1) ? AbyssalCraft.necronomicon_cor : AbyssalCraft.OC));
		if(getBookType() >= 2)
			buttonList.add(ritual2 = new ButtonCategory(6, i + 10, b0 + 105, this, NecronomiconText.LABEL_INFORMATION_DREADLANDS, hasRituals(2) ? AbyssalCraft.necronomicon_dre : AbyssalCraft.OC));
		if(getBookType() >= 3)
			buttonList.add(ritual3 = new ButtonCategory(7, i + 10, b0 + 130, this, NecronomiconText.LABEL_INFORMATION_OMOTHOL, hasRituals(3) ? AbyssalCraft.necronomicon_omt : AbyssalCraft.OC));
		if(getBookType() == 4)
			buttonList.add(ritual4 = new ButtonCategory(8, i + 130, b0 + 130, this, StatCollector.translateToLocal(AbyssalCraft.abyssalnomicon.getUnlocalizedName() + ".name"), hasRituals(4) ? AbyssalCraft.abyssalnomicon : AbyssalCraft.OC));
		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo && isRitualInfo;
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
			else if(button.id == 1){
				if (currTurnup < getTurnupLimit() -1)
					++currTurnup;
			} else if(button.id == 2){
				if(currTurnup == 0 && !isInfo)
					mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
				else if(currTurnup == 0 && isInfo){
					initGui();
					isInfo = isRitualInfo = false;
					setTurnupLimit(2);
				} else if (currTurnup > 0)
					--currTurnup;
			} else if(button.id == 3){
				isInfo = true;
				isRitualInfo = true;
				drawButtons();
			} else if(button.id == 4){
				if(hasRituals(0))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 0));
			} else if(button.id == 5){
				if(hasRituals(1))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 1));
			} else if(button.id == 6){
				if(hasRituals(2))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 2));
			} else if(button.id == 7){
				if(hasRituals(3))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 3));
			} else if(button.id == 8)
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

	@SuppressWarnings("unchecked")
	private void drawButtons(){
		buttonList.clear();
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
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

	@Override
	protected void drawInformationText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		setTurnupLimit(3);
		if(isRitualInfo){
			stuff = NecronomiconText.LABEL_INFO;
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			if(currTurnup == 0){
				writeText(1, NecronomiconText.RITUAL_TUT_1, 100);
				writeText(2, NecronomiconText.RITUAL_TUT_2);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.RITUAL_TUT_1);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			} if(currTurnup == 1){
				writeText(1, NecronomiconText.RITUAL_TUT_3, 100);
				writeText(2, NecronomiconText.RITUAL_TUT_4);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.RITUAL_TUT_2);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			} if(currTurnup == 2){
				writeText(1, NecronomiconText.RITUAL_TUT_5, 100);
				writeText(2, NecronomiconText.RITUAL_TUT_6);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.RITUAL_TUT_3);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			}
		}
	}
}