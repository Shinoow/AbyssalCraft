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

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.PageData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.PageData.PageType;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class GuiNecronomiconEntry extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private ButtonCategory[] buttons = new ButtonCategory[5];
	private GuiButton buttonDone;
	private NecroData data;
	private GuiNecronomicon parent;
	private Item icon;
	private boolean bool1, bool2, bool3, bool4, bool5;

	public GuiNecronomiconEntry(int bookType, NecroData nd, GuiNecronomicon gui, Item item){
		super(bookType);
		data = nd;
		parent = gui;
		icon = item;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui(){
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
		if(data != null)
			for(int n = 0; n < data.getPageData().length; n++)
				buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 30 + 25*n,this, data.getPageData()[n].getTitle(), icon));
		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		if(data != null)
			for(int i = 0; i < data.getPageData().length; i++)
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
			} else if (button.id == 2){
				if(currTurnup == 0 && !isInfo)
					mc.displayGuiScreen(parent);
				else if(currTurnup == 0 && isInfo){
					initGui();
					isInfo = bool1 = bool2 = bool3 = bool4 = bool5 = false;
					setTurnupLimit(2);
				} else if (currTurnup > 0)
					--currTurnup;
			} else if(button.id == 3){
				bool1 = true;
				isInfo = true;
				drawButtons();
			} else if(button.id == 4){
				bool2 = true;
				isInfo = true;
				drawButtons();
			} else if(button.id == 5){
				bool3 = true;
				isInfo = true;
				drawButtons();
			} else if(button.id == 6){
				bool4 = true;
				isInfo = true;
				drawButtons();
			} else if(button.id == 7){
				bool5 = true;
				isInfo = true;
				drawButtons();
			}
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
	}

	@Override
	protected void drawInformationText(){
		if(bool1)
			drawPageData(data.getPageData()[0]);
		else if(bool2)
			drawPageData(data.getPageData()[1]);
		else if(bool3)
			drawPageData(data.getPageData()[2]);
		else if(bool4)
			drawPageData(data.getPageData()[3]);
		else if(bool5)
			drawPageData(data.getPageData()[4]);
		updateButtons();
	}

	private void drawPageData(PageData page){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		Object[] icons = null;

		stuff = StatCollector.translateToLocal(page.getTitle());
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		setTurnupLimit(page.getPageAmount());
		switch(page.getPageType()){
		case ENTRY:
			icons = page.getIcons();
			break;
		case INFO:
			icons = page.getIcons();
			break;
		case CRAFTING:
			icons = page.getIcons();
			break;
		case NORMAL:
			icons = new Object[10];
			break;
		}
		if(currTurnup == 0)
			addPage(page.getPageType(), page.getPages()[0], page.getPages()[1], icons[0], page.getPages().length, 1);
		else if(currTurnup == 1 && page.getPageAmount() >= 2)
			addPage(page.getPageType(), page.getPages()[2], page.getPages()[3], icons[1], page.getPages().length, 3);
		else if(currTurnup == 2 && page.getPageAmount() >= 3)
			addPage(page.getPageType(), page.getPages()[4], page.getPages()[5], icons[2], page.getPages().length, 5);
		else if(currTurnup == 3 && page.getPageAmount() >= 4)
			addPage(page.getPageType(), page.getPages()[6], page.getPages()[7], icons[3], page.getPages().length, 7);
		else if(currTurnup == 4 && page.getPageAmount() >= 5)
			addPage(page.getPageType(), page.getPages()[8], page.getPages()[9], icons[4], page.getPages().length, 9);
		else if(currTurnup == 5 && page.getPageAmount() >= 6)
			addPage(page.getPageType(), page.getPages()[10], page.getPages()[11], icons[5], page.getPages().length, 11);
		else if(currTurnup == 6 && page.getPageAmount() >= 7)
			addPage(page.getPageType(), page.getPages()[12], page.getPages()[13], icons[6], page.getPages().length, 13);
		else if(currTurnup == 7 && page.getPageAmount() >= 8)
			addPage(page.getPageType(), page.getPages()[14], page.getPages()[15], icons[7], page.getPages().length, 15);
		else if(currTurnup == 8 && page.getPageAmount() >= 9)
			addPage(page.getPageType(), page.getPages()[16], page.getPages()[17], icons[8], page.getPages().length, 17);
		else if(currTurnup == 9 && page.getPageAmount() >= 10)
			addPage(page.getPageType(), page.getPages()[18], page.getPages()[19], icons[9], page.getPages().length, 19);
	}

	private void addPage(PageType pageType, String text1, String text2, Object icon, int pageAmount, int limit){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		if(pageAmount > limit)
			writeText(2, text2);
		switch(pageType){
		case NORMAL:
			writeText(1, text1);
			break;
		case ENTRY:
			if(icon != null){
				writeText(1, text1, 50);
				itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icon, k + 60, b0 + 28);
				RenderHelper.disableStandardItemLighting();
			} else writeText(1, text1);
			break;
		case INFO:
			if(icon != null){
				writeText(1, text1, 100);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture((ResourceLocation)icon);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			} else writeText(1, text1);
			break;
		case CRAFTING:
			if(icon != null){
				writeText(1, text1, 100);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), ((CraftingStack)icon).getOutput(), k + 93, b0 + 52);
				for(int i = 0; i <= 2; i++){
					itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), ((CraftingStack)icon).getFirstArray()[i], k + 24 +i*21, b0 + 31);
					RenderHelper.disableStandardItemLighting();
					itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), ((CraftingStack)icon).getSecondArray()[i], k + 24 +i*21, b0 + 52);
					RenderHelper.disableStandardItemLighting();
					itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), ((CraftingStack)icon).getThirdArray()[i], k + 24 +i*21, b0 + 73);
					RenderHelper.disableStandardItemLighting();
				}
			} else writeText(1, text1);
			break;
		}
	}

	@Override
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = StatCollector.translateToLocal(data.getTitle());
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		if(data.getInformation() != null) writeText(2, data.getInformation());
	}
}