/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.client.gui.necronomicon;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.PageData;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;

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
		buttonNextPage.visible = currnTurnup < getTurnupLimit() - 1 && isInfo;
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
				if (currnTurnup < getTurnupLimit() -1)
					++currnTurnup;
			} else if (button.id == 2){
				if(currnTurnup == 0 && !isInfo)
					mc.displayGuiScreen(parent);
				else if(currnTurnup == 0 && isInfo){
					initGui();
					isInfo = bool1 = bool2 = bool3 = bool4 = bool5 = false;
					setTurnupLimit(2);
				} else if (currnTurnup > 0)
					--currnTurnup;
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
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		PageData page;
		Object[] icons = null;
		if(bool1){
			page = data.getPageData()[0];
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
			case NORMAL:
				break;
			}
			if(currnTurnup == 0){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[0]);
					break;
				case ENTRY:
					if(icons != null && icons[0] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[0], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[0], 50);
					} else writeText(1, page.getPages()[0]);
					break;
				case INFO:
					if(icons != null && icons[0] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[0]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[0], 100);
					} else writeText(1, page.getPages()[0]);
					break;
				}
				if(page.getPages().length > 1)
					writeText(2, page.getPages()[1]);
			} else if(currnTurnup == 1 && page.getPageAmount() >= 2){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[2]);
					break;
				case ENTRY:
					if(icons != null && icons[1] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[1], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[2], 50);
					} else writeText(1, page.getPages()[2]);
					break;
				case INFO:
					if(icons != null && icons[1] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[1]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[2], 100);
					} else writeText(1, page.getPages()[2]);
					break;
				}
				if(page.getPages().length > 3)
					writeText(2, page.getPages()[3]);
			} else if(currnTurnup == 2 && page.getPageAmount() >= 3){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[4]);
					break;
				case ENTRY:
					if(icons != null && icons[2] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[2], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[4], 50);
					} else writeText(1, page.getPages()[4]);
					break;
				case INFO:
					if(icons != null && icons[2] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[2]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[4], 100);
					} else writeText(1, page.getPages()[4]);
					break;
				}
				if(page.getPages().length > 5)
					writeText(2, page.getPages()[5]);
			} else if(currnTurnup == 3 && page.getPageAmount() >= 4){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[6]);
					break;
				case ENTRY:
					if(icons != null && icons[3] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[3], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[6], 50);
					} else writeText(1, page.getPages()[6]);
					break;
				case INFO:
					if(icons != null && icons[3] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[3]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[6], 100);
					} else writeText(1, page.getPages()[6]);
					break;
				}
				if(page.getPages().length > 7)
					writeText(2, page.getPages()[7]);
			} else if(currnTurnup == 4 && page.getPageAmount() >= 5){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[8]);
					break;
				case ENTRY:
					if(icons != null && icons[4] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[4], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[8], 50);
					} else writeText(1, page.getPages()[8]);
					break;
				case INFO:
					if(icons != null && icons[4] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[4]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[8], 100);
					} else writeText(1, page.getPages()[8]);
					break;
				}
				if(page.getPages().length > 9)
					writeText(2, page.getPages()[9]);
			} else if(currnTurnup == 5 && page.getPageAmount() >= 6){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[10]);
					break;
				case ENTRY:
					if(icons != null && icons[5] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[5], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[10], 50);
					} else writeText(1, page.getPages()[10]);
					break;
				case INFO:
					if(icons != null && icons[5] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[5]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[10], 100);
					} else writeText(1, page.getPages()[10]);
					break;
				}
				if(page.getPages().length > 11)
					writeText(2, page.getPages()[11]);
			} else if(currnTurnup == 6 && page.getPageAmount() >= 7){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[12]);
					break;
				case ENTRY:
					if(icons != null && icons[6] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[6], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[12], 50);
					} else writeText(1, page.getPages()[12]);
					break;
				case INFO:
					if(icons != null && icons[6] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[6]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[12], 100);
					} else writeText(1, page.getPages()[12]);
					break;
				}
				if(page.getPages().length > 13)
					writeText(2, page.getPages()[13]);
			} else if(currnTurnup == 7 && page.getPageAmount() >= 8){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[14]);
					break;
				case ENTRY:
					if(icons != null && icons[7] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[7], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[14], 50);
					} else writeText(1, page.getPages()[14]);
					break;
				case INFO:
					if(icons != null && icons[7] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[7]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[14], 100);
					} else writeText(1, page.getPages()[14]);
					break;
				}
				if(page.getPages().length > 15)
					writeText(2, page.getPages()[15]);
			} else if(currnTurnup == 8 && page.getPageAmount() >= 9){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[16]);
					break;
				case ENTRY:
					if(icons != null && icons[8] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[8], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[16], 50);
					} else writeText(1, page.getPages()[16]);
					break;
				case INFO:
					if(icons != null && icons[8] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[8]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[17], 100);
					} else writeText(1, page.getPages()[16]);
					break;
				}
				if(page.getPages().length > 17)
					writeText(2, page.getPages()[17]);
			} else if(currnTurnup == 9 && page.getPageAmount() >= 10){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[18]);
					break;
				case ENTRY:
					if(icons != null && icons[9] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[9], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[18], 50);
					} else writeText(1, page.getPages()[18]);
					break;
				case INFO:
					if(icons != null && icons[9] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[9]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[18], 100);
					} else writeText(1, page.getPages()[18]);
					break;
				}
				if(page.getPages().length > 19)
					writeText(2, page.getPages()[19]);
			}
		} else if(bool2){
			page = data.getPageData()[1];
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
			case NORMAL:
				break;
			}
			if(currnTurnup == 0){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[0]);
					break;
				case ENTRY:
					if(icons != null && icons[0] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[0], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[0], 50);
					} else writeText(1, page.getPages()[0]);
					break;
				case INFO:
					if(icons != null && icons[0] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[0]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[0], 100);
					} else writeText(1, page.getPages()[0]);
					break;
				}
				if(page.getPages().length > 1)
					writeText(2, page.getPages()[1]);
			} else if(currnTurnup == 1 && page.getPageAmount() >= 2){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[2]);
					break;
				case ENTRY:
					if(icons != null && icons[1] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[1], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[2], 50);
					} else writeText(1, page.getPages()[2]);
					break;
				case INFO:
					if(icons != null && icons[1] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[1]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[2], 100);
					} else writeText(1, page.getPages()[2]);
					break;
				}
				if(page.getPages().length > 3)
					writeText(2, page.getPages()[3]);
			} else if(currnTurnup == 2 && page.getPageAmount() >= 3){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[4]);
					break;
				case ENTRY:
					if(icons != null && icons[2] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[2], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[4], 50);
					} else writeText(1, page.getPages()[4]);
					break;
				case INFO:
					if(icons != null && icons[2] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[2]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[4], 100);
					} else writeText(1, page.getPages()[4]);
					break;
				}
				if(page.getPages().length > 5)
					writeText(2, page.getPages()[5]);
			} else if(currnTurnup == 3 && page.getPageAmount() >= 4){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[6]);
					break;
				case ENTRY:
					if(icons != null && icons[3] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[3], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[6], 50);
					} else writeText(1, page.getPages()[6]);
					break;
				case INFO:
					if(icons != null && icons[3] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[3]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[6], 100);
					} else writeText(1, page.getPages()[6]);
					break;
				}
				if(page.getPages().length > 7)
					writeText(2, page.getPages()[7]);
			} else if(currnTurnup == 4 && page.getPageAmount() >= 5){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[8]);
					break;
				case ENTRY:
					if(icons != null && icons[4] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[4], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[8], 50);
					} else writeText(1, page.getPages()[8]);
					break;
				case INFO:
					if(icons != null && icons[4] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[4]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[8], 100);
					} else writeText(1, page.getPages()[8]);
					break;
				}
				if(page.getPages().length > 9)
					writeText(2, page.getPages()[9]);
			} else if(currnTurnup == 5 && page.getPageAmount() >= 6){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[10]);
					break;
				case ENTRY:
					if(icons != null && icons[5] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[5], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[10], 50);
					} else writeText(1, page.getPages()[10]);
					break;
				case INFO:
					if(icons != null && icons[5] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[5]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[10], 100);
					} else writeText(1, page.getPages()[10]);
					break;
				}
				if(page.getPages().length > 11)
					writeText(2, page.getPages()[11]);
			} else if(currnTurnup == 6 && page.getPageAmount() >= 7){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[12]);
					break;
				case ENTRY:
					if(icons != null && icons[6] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[6], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[12], 50);
					} else writeText(1, page.getPages()[12]);
					break;
				case INFO:
					if(icons != null && icons[6] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[6]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[12], 100);
					} else writeText(1, page.getPages()[12]);
					break;
				}
				if(page.getPages().length > 13)
					writeText(2, page.getPages()[13]);
			} else if(currnTurnup == 7 && page.getPageAmount() >= 8){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[14]);
					break;
				case ENTRY:
					if(icons != null && icons[7] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[7], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[14], 50);
					} else writeText(1, page.getPages()[14]);
					break;
				case INFO:
					if(icons != null && icons[7] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[7]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[14], 100);
					} else writeText(1, page.getPages()[14]);
					break;
				}
				if(page.getPages().length > 15)
					writeText(2, page.getPages()[15]);
			} else if(currnTurnup == 8 && page.getPageAmount() >= 9){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[16]);
					break;
				case ENTRY:
					if(icons != null && icons[8] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[8], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[16], 50);
					} else writeText(1, page.getPages()[16]);
					break;
				case INFO:
					if(icons != null && icons[8] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[8]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[17], 100);
					} else writeText(1, page.getPages()[16]);
					break;
				}
				if(page.getPages().length > 17)
					writeText(2, page.getPages()[17]);
			} else if(currnTurnup == 9 && page.getPageAmount() >= 10){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[18]);
					break;
				case ENTRY:
					if(icons != null && icons[9] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[9], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[18], 50);
					} else writeText(1, page.getPages()[18]);
					break;
				case INFO:
					if(icons != null && icons[9] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[9]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[18], 100);
					} else writeText(1, page.getPages()[18]);
					break;
				}
				if(page.getPages().length > 19)
					writeText(2, page.getPages()[19]);
			}
		} else if(bool3){
			page = data.getPageData()[2];
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
			case NORMAL:
				break;
			}
			if(currnTurnup == 0){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[0]);
					break;
				case ENTRY:
					if(icons != null && icons[0] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[0], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[0], 50);
					} else writeText(1, page.getPages()[0]);
					break;
				case INFO:
					if(icons != null && icons[0] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[0]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[0], 100);
					} else writeText(1, page.getPages()[0]);
					break;
				}
				if(page.getPages().length > 1)
					writeText(2, page.getPages()[1]);
			} else if(currnTurnup == 1 && page.getPageAmount() >= 2){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[2]);
					break;
				case ENTRY:
					if(icons != null && icons[1] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[1], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[2], 50);
					} else writeText(1, page.getPages()[2]);
					break;
				case INFO:
					if(icons != null && icons[1] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[1]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[2], 100);
					} else writeText(1, page.getPages()[2]);
					break;
				}
				if(page.getPages().length > 3)
					writeText(2, page.getPages()[3]);
			} else if(currnTurnup == 2 && page.getPageAmount() >= 3){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[4]);
					break;
				case ENTRY:
					if(icons != null && icons[2] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[2], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[4], 50);
					} else writeText(1, page.getPages()[4]);
					break;
				case INFO:
					if(icons != null && icons[2] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[2]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[4], 100);
					} else writeText(1, page.getPages()[4]);
					break;
				}
				if(page.getPages().length > 5)
					writeText(2, page.getPages()[5]);
			} else if(currnTurnup == 3 && page.getPageAmount() >= 4){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[6]);
					break;
				case ENTRY:
					if(icons != null && icons[3] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[3], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[6], 50);
					} else writeText(1, page.getPages()[6]);
					break;
				case INFO:
					if(icons != null && icons[3] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[3]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[6], 100);
					} else writeText(1, page.getPages()[6]);
					break;
				}
				if(page.getPages().length > 7)
					writeText(2, page.getPages()[7]);
			} else if(currnTurnup == 4 && page.getPageAmount() >= 5){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[8]);
					break;
				case ENTRY:
					if(icons != null && icons[4] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[4], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[8], 50);
					} else writeText(1, page.getPages()[8]);
					break;
				case INFO:
					if(icons != null && icons[4] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[4]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[8], 100);
					} else writeText(1, page.getPages()[8]);
					break;
				}
				if(page.getPages().length > 9)
					writeText(2, page.getPages()[9]);
			} else if(currnTurnup == 5 && page.getPageAmount() >= 6){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[10]);
					break;
				case ENTRY:
					if(icons != null && icons[5] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[5], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[10], 50);
					} else writeText(1, page.getPages()[10]);
					break;
				case INFO:
					if(icons != null && icons[5] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[5]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[10], 100);
					} else writeText(1, page.getPages()[10]);
					break;
				}
				if(page.getPages().length > 11)
					writeText(2, page.getPages()[11]);
			} else if(currnTurnup == 6 && page.getPageAmount() >= 7){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[12]);
					break;
				case ENTRY:
					if(icons != null && icons[6] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[6], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[12], 50);
					} else writeText(1, page.getPages()[12]);
					break;
				case INFO:
					if(icons != null && icons[6] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[6]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[12], 100);
					} else writeText(1, page.getPages()[12]);
					break;
				}
				if(page.getPages().length > 13)
					writeText(2, page.getPages()[13]);
			} else if(currnTurnup == 7 && page.getPageAmount() >= 8){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[14]);
					break;
				case ENTRY:
					if(icons != null && icons[7] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[7], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[14], 50);
					} else writeText(1, page.getPages()[14]);
					break;
				case INFO:
					if(icons != null && icons[7] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[7]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[14], 100);
					} else writeText(1, page.getPages()[14]);
					break;
				}
				if(page.getPages().length > 15)
					writeText(2, page.getPages()[15]);
			} else if(currnTurnup == 8 && page.getPageAmount() >= 9){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[16]);
					break;
				case ENTRY:
					if(icons != null && icons[8] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[8], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[16], 50);
					} else writeText(1, page.getPages()[16]);
					break;
				case INFO:
					if(icons != null && icons[8] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[8]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[17], 100);
					} else writeText(1, page.getPages()[16]);
					break;
				}
				if(page.getPages().length > 17)
					writeText(2, page.getPages()[17]);
			} else if(currnTurnup == 9 && page.getPageAmount() >= 10){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[18]);
					break;
				case ENTRY:
					if(icons != null && icons[9] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[9], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[18], 50);
					} else writeText(1, page.getPages()[18]);
					break;
				case INFO:
					if(icons != null && icons[9] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[9]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[18], 100);
					} else writeText(1, page.getPages()[18]);
					break;
				}
				if(page.getPages().length > 19)
					writeText(2, page.getPages()[19]);
			}
		} else if(bool4){
			page = data.getPageData()[3];
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
			case NORMAL:
				break;
			}
			if(currnTurnup == 0){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[0]);
					break;
				case ENTRY:
					if(icons != null && icons[0] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[0], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[0], 50);
					} else writeText(1, page.getPages()[0]);
					break;
				case INFO:
					if(icons != null && icons[0] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[0]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[0], 100);
					} else writeText(1, page.getPages()[0]);
					break;
				}
				if(page.getPages().length > 1)
					writeText(2, page.getPages()[1]);
			} else if(currnTurnup == 1 && page.getPageAmount() >= 2){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[2]);
					break;
				case ENTRY:
					if(icons != null && icons[1] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[1], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[2], 50);
					} else writeText(1, page.getPages()[2]);
					break;
				case INFO:
					if(icons != null && icons[1] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[1]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[2], 100);
					} else writeText(1, page.getPages()[2]);
					break;
				}
				if(page.getPages().length > 3)
					writeText(2, page.getPages()[3]);
			} else if(currnTurnup == 2 && page.getPageAmount() >= 3){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[4]);
					break;
				case ENTRY:
					if(icons != null && icons[2] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[2], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[4], 50);
					} else writeText(1, page.getPages()[4]);
					break;
				case INFO:
					if(icons != null && icons[2] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[2]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[4], 100);
					} else writeText(1, page.getPages()[4]);
					break;
				}
				if(page.getPages().length > 5)
					writeText(2, page.getPages()[5]);
			} else if(currnTurnup == 3 && page.getPageAmount() >= 4){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[6]);
					break;
				case ENTRY:
					if(icons != null && icons[3] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[3], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[6], 50);
					} else writeText(1, page.getPages()[6]);
					break;
				case INFO:
					if(icons != null && icons[3] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[3]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[6], 100);
					} else writeText(1, page.getPages()[6]);
					break;
				}
				if(page.getPages().length > 7)
					writeText(2, page.getPages()[7]);
			} else if(currnTurnup == 4 && page.getPageAmount() >= 5){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[8]);
					break;
				case ENTRY:
					if(icons != null && icons[4] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[4], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[8], 50);
					} else writeText(1, page.getPages()[8]);
					break;
				case INFO:
					if(icons != null && icons[4] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[4]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[8], 100);
					} else writeText(1, page.getPages()[8]);
					break;
				}
				if(page.getPages().length > 9)
					writeText(2, page.getPages()[9]);
			} else if(currnTurnup == 5 && page.getPageAmount() >= 6){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[10]);
					break;
				case ENTRY:
					if(icons != null && icons[5] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[5], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[10], 50);
					} else writeText(1, page.getPages()[10]);
					break;
				case INFO:
					if(icons != null && icons[5] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[5]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[10], 100);
					} else writeText(1, page.getPages()[10]);
					break;
				}
				if(page.getPages().length > 11)
					writeText(2, page.getPages()[11]);
			} else if(currnTurnup == 6 && page.getPageAmount() >= 7){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[12]);
					break;
				case ENTRY:
					if(icons != null && icons[6] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[6], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[12], 50);
					} else writeText(1, page.getPages()[12]);
					break;
				case INFO:
					if(icons != null && icons[6] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[6]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[12], 100);
					} else writeText(1, page.getPages()[12]);
					break;
				}
				if(page.getPages().length > 13)
					writeText(2, page.getPages()[13]);
			} else if(currnTurnup == 7 && page.getPageAmount() >= 8){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[14]);
					break;
				case ENTRY:
					if(icons != null && icons[7] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[7], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[14], 50);
					} else writeText(1, page.getPages()[14]);
					break;
				case INFO:
					if(icons != null && icons[7] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[7]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[14], 100);
					} else writeText(1, page.getPages()[14]);
					break;
				}
				if(page.getPages().length > 15)
					writeText(2, page.getPages()[15]);
			} else if(currnTurnup == 8 && page.getPageAmount() >= 9){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[16]);
					break;
				case ENTRY:
					if(icons != null && icons[8] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[8], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[16], 50);
					} else writeText(1, page.getPages()[16]);
					break;
				case INFO:
					if(icons != null && icons[8] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[8]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[17], 100);
					} else writeText(1, page.getPages()[16]);
					break;
				}
				if(page.getPages().length > 17)
					writeText(2, page.getPages()[17]);
			} else if(currnTurnup == 9 && page.getPageAmount() >= 10){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[18]);
					break;
				case ENTRY:
					if(icons != null && icons[9] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[9], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[18], 50);
					} else writeText(1, page.getPages()[18]);
					break;
				case INFO:
					if(icons != null && icons[9] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[9]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[18], 100);
					} else writeText(1, page.getPages()[18]);
					break;
				}
				if(page.getPages().length > 19)
					writeText(2, page.getPages()[19]);
			}
		} else if(bool5){
			page = data.getPageData()[4];
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
			case NORMAL:
				break;
			}
			if(currnTurnup == 0){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[0]);
					break;
				case ENTRY:
					if(icons != null && icons[0] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[0], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[0], 50);
					} else writeText(1, page.getPages()[0]);
					break;
				case INFO:
					if(icons != null && icons[0] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[0]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[0], 100);
					} else writeText(1, page.getPages()[0]);
					break;
				}
				if(page.getPages().length > 1)
					writeText(2, page.getPages()[1]);
			} else if(currnTurnup == 1 && page.getPageAmount() >= 2){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[2]);
					break;
				case ENTRY:
					if(icons != null && icons[1] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[1], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[2], 50);
					} else writeText(1, page.getPages()[2]);
					break;
				case INFO:
					if(icons != null && icons[1] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[1]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[2], 100);
					} else writeText(1, page.getPages()[2]);
					break;
				}
				if(page.getPages().length > 3)
					writeText(2, page.getPages()[3]);
			} else if(currnTurnup == 2 && page.getPageAmount() >= 3){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[4]);
					break;
				case ENTRY:
					if(icons != null && icons[2] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[2], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[4], 50);
					} else writeText(1, page.getPages()[4]);
					break;
				case INFO:
					if(icons != null && icons[2] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[2]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[4], 100);
					} else writeText(1, page.getPages()[4]);
					break;
				}
				if(page.getPages().length > 5)
					writeText(2, page.getPages()[5]);
			} else if(currnTurnup == 3 && page.getPageAmount() >= 4){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[6]);
					break;
				case ENTRY:
					if(icons != null && icons[3] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[3], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[6], 50);
					} else writeText(1, page.getPages()[6]);
					break;
				case INFO:
					if(icons != null && icons[3] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[3]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[6], 100);
					} else writeText(1, page.getPages()[6]);
					break;
				}
				if(page.getPages().length > 7)
					writeText(2, page.getPages()[7]);
			} else if(currnTurnup == 4 && page.getPageAmount() >= 5){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[8]);
					break;
				case ENTRY:
					if(icons != null && icons[4] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[4], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[8], 50);
					} else writeText(1, page.getPages()[8]);
					break;
				case INFO:
					if(icons != null && icons[4] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[4]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[8], 100);
					} else writeText(1, page.getPages()[8]);
					break;
				}
				if(page.getPages().length > 9)
					writeText(2, page.getPages()[9]);
			} else if(currnTurnup == 5 && page.getPageAmount() >= 6){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[10]);
					break;
				case ENTRY:
					if(icons != null && icons[5] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[5], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[10], 50);
					} else writeText(1, page.getPages()[10]);
					break;
				case INFO:
					if(icons != null && icons[5] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[5]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[10], 100);
					} else writeText(1, page.getPages()[10]);
					break;
				}
				if(page.getPages().length > 11)
					writeText(2, page.getPages()[11]);
			} else if(currnTurnup == 6 && page.getPageAmount() >= 7){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[12]);
					break;
				case ENTRY:
					if(icons != null && icons[6] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[6], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[12], 50);
					} else writeText(1, page.getPages()[12]);
					break;
				case INFO:
					if(icons != null && icons[6] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[6]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[12], 100);
					} else writeText(1, page.getPages()[12]);
					break;
				}
				if(page.getPages().length > 13)
					writeText(2, page.getPages()[13]);
			} else if(currnTurnup == 7 && page.getPageAmount() >= 8){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[14]);
					break;
				case ENTRY:
					if(icons != null && icons[7] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[7], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[14], 50);
					} else writeText(1, page.getPages()[14]);
					break;
				case INFO:
					if(icons != null && icons[7] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[7]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[14], 100);
					} else writeText(1, page.getPages()[14]);
					break;
				}
				if(page.getPages().length > 15)
					writeText(2, page.getPages()[15]);
			} else if(currnTurnup == 8 && page.getPageAmount() >= 9){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[16]);
					break;
				case ENTRY:
					if(icons != null && icons[8] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[8], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[16], 50);
					} else writeText(1, page.getPages()[16]);
					break;
				case INFO:
					if(icons != null && icons[8] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[8]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[17], 100);
					} else writeText(1, page.getPages()[16]);
					break;
				}
				if(page.getPages().length > 17)
					writeText(2, page.getPages()[17]);
			} else if(currnTurnup == 9 && page.getPageAmount() >= 10){
				switch(page.getPageType()){
				case NORMAL:
					writeText(1, page.getPages()[18]);
					break;
				case ENTRY:
					if(icons != null && icons[9] != null){
						itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), (ItemStack)icons[9], k + 60, b0 + 28);
						RenderHelper.disableStandardItemLighting();
						writeText(1, page.getPages()[18], 50);
					} else writeText(1, page.getPages()[18]);
					break;
				case INFO:
					if(icons != null && icons[9] != null){
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)icons[9]);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						writeText(1, page.getPages()[18], 100);
					} else writeText(1, page.getPages()[18]);
					break;
				}
				if(page.getPages().length > 19)
					writeText(2, page.getPages()[19]);
			}
		}
		updateButtons();
	}

	@Override
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = StatCollector.translateToLocal(data.getTitle());
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		if(data.getInformation() != null)
			writeText(2, data.getInformation());
	}
}
