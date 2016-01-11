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

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.PageData;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.GuiRenderHelper;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Tuple;

public class GuiNecronomiconEntry extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private ButtonCategory[] buttons = new ButtonCategory[5];
	private GuiButton buttonDone;
	private NecroData data;
	private GuiNecronomicon parent;
	private Item icon;
	private boolean bool1, bool2, bool3, bool4, bool5, bool6, bool7;

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
				buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 10, b0 + 20 + 17*n,this, data.getPageData()[n].getTitle(), icon));
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
			} else if(button.id == 8){
				bool6 = true;
				isInfo = true;
				drawButtons();
			} else if(button.id == 9){
				bool7 = true;
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
	protected void drawInformationText(int x, int y){
		if(bool1)
			drawPageData(data.getPageData()[0], x, y);
		else if(bool2)
			drawPageData(data.getPageData()[1], x, y);
		else if(bool3)
			drawPageData(data.getPageData()[2], x, y);
		else if(bool4)
			drawPageData(data.getPageData()[3], x, y);
		else if(bool5)
			drawPageData(data.getPageData()[4], x, y);
		else if(bool6)
			drawPageData(data.getPageData()[5], x, y);
		else if(bool7)
			drawPageData(data.getPageData()[6], x, y);
		updateButtons();
	}

	private void drawPageData(PageData page, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		Object[] icons = null;

		stuff = StatCollector.translateToLocal(page.getTitle());
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		setTurnupLimit(page.getPageAmount());
		if(page.getIcons() == null)
			icons = new Object[20];
		else icons = page.getIcons();

		if(currTurnup == 0)
			if(page.getPages().length >= 2)
				addPage(page.getPages()[0], page.getPages()[1], icons[0], page.getPages().length, 1, x, y);
			else addPage(page.getPages()[0], "", icons[0], page.getPages().length, 1, x, y);
		else if(currTurnup == 1 && page.getPageAmount() >= 2)
			if(page.getPages().length >= 4)
				addPage(page.getPages()[2], page.getPages()[3], icons[1], page.getPages().length, 3, x, y);
			else addPage(page.getPages()[2], "", icons[1], page.getPages().length, 3, x, y);
		else if(currTurnup == 2 && page.getPageAmount() >= 3)
			if(page.getPages().length >= 6)
				addPage(page.getPages()[4], page.getPages()[5], icons[2], page.getPages().length, 5, x, y);
			else addPage(page.getPages()[4], "", icons[2], page.getPages().length, 5, x, y);
		else if(currTurnup == 3 && page.getPageAmount() >= 4)
			if(page.getPages().length >= 8)
				addPage(page.getPages()[6], page.getPages()[7], icons[3], page.getPages().length, 7, x, y);
			else addPage(page.getPages()[6], "", icons[3], page.getPages().length, 7, x, y);
		else if(currTurnup == 4 && page.getPageAmount() >= 5)
			if(page.getPages().length >= 10)
				addPage(page.getPages()[8], page.getPages()[9], icons[4], page.getPages().length, 9, x, y);
			else addPage(page.getPages()[8], "", icons[4], page.getPages().length, 9, x, y);
		else if(currTurnup == 5 && page.getPageAmount() >= 6)
			if(page.getPages().length >= 12)
				addPage(page.getPages()[10], page.getPages()[11], icons[5], page.getPages().length, 11, x, y);
			else addPage(page.getPages()[10], "", icons[5], page.getPages().length, 11, x, y);
		else if(currTurnup == 6 && page.getPageAmount() >= 7)
			if(page.getPages().length >= 14)
				addPage(page.getPages()[12], page.getPages()[13], icons[6], page.getPages().length, 13, x, y);
			else addPage(page.getPages()[12], "", icons[6], page.getPages().length, 13, x, y);
		else if(currTurnup == 7 && page.getPageAmount() >= 8)
			if(page.getPages().length >= 16)
				addPage(page.getPages()[14], page.getPages()[15], icons[7], page.getPages().length, 15, x, y);
			else addPage(page.getPages()[14], "", icons[7], page.getPages().length, 15, x, y);
		else if(currTurnup == 8 && page.getPageAmount() >= 9)
			if(page.getPages().length >= 18)
				addPage(page.getPages()[16], page.getPages()[17], icons[8], page.getPages().length, 17, x, y);
			else addPage(page.getPages()[16], "", icons[8], page.getPages().length, 17, x, y);
		else if(currTurnup == 9 && page.getPageAmount() >= 10)
			if(page.getPages().length >= 20)
				addPage(page.getPages()[18], page.getPages()[19], icons[9], page.getPages().length, 19, x, y);
			else addPage(page.getPages()[18], "", icons[9], page.getPages().length, 19, x, y);
		else if(currTurnup == 10 && page.getPageAmount() >= 11)
			if(page.getPages().length >= 22)
				addPage(page.getPages()[20], page.getPages()[21], icons[10], page.getPages().length, 21, x, y);
			else addPage(page.getPages()[20], "", icons[10], page.getPages().length, 21, x, y);
		else if(currTurnup == 11 && page.getPageAmount() >= 12)
			if(page.getPages().length >= 24)
				addPage(page.getPages()[22], page.getPages()[23], icons[11], page.getPages().length, 23, x, y);
			else addPage(page.getPages()[22], "", icons[11], page.getPages().length, 23, x, y);
		else if(currTurnup == 12 && page.getPageAmount() >= 13)
			if(page.getPages().length >= 26)
				addPage(page.getPages()[24], page.getPages()[25], icons[12], page.getPages().length, 25, x, y);
			else addPage(page.getPages()[24], "", icons[12], page.getPages().length, 25, x, y);
		else if(currTurnup == 13 && page.getPageAmount() >= 14)
			if(page.getPages().length >= 28)
				addPage(page.getPages()[26], page.getPages()[27], icons[13], page.getPages().length, 27, x, y);
			else addPage(page.getPages()[26], "", icons[13], page.getPages().length, 27, x, y);
		else if(currTurnup == 14 && page.getPageAmount() >= 15)
			if(page.getPages().length >= 30)
				addPage(page.getPages()[28], page.getPages()[29], icons[14], page.getPages().length, 29, x, y);
			else addPage(page.getPages()[28], "", icons[14], page.getPages().length, 29, x, y);
		else if(currTurnup == 15 && page.getPageAmount() >= 16)
			if(page.getPages().length >= 32)
				addPage(page.getPages()[30], page.getPages()[31], icons[15], page.getPages().length, 31, x, y);
			else addPage(page.getPages()[30], "", icons[15], page.getPages().length, 31, x, y);
		else if(currTurnup == 16 && page.getPageAmount() >= 17)
			if(page.getPages().length >= 34)
				addPage(page.getPages()[32], page.getPages()[33], icons[16], page.getPages().length, 33, x, y);
			else addPage(page.getPages()[32], "", icons[16], page.getPages().length, 33, x, y);
		else if(currTurnup == 17 && page.getPageAmount() >= 18)
			if(page.getPages().length >= 36)
				addPage(page.getPages()[34], page.getPages()[35], icons[17], page.getPages().length, 35, x, y);
			else addPage(page.getPages()[34], "", icons[17], page.getPages().length, 35, x, y);
		else if(currTurnup == 18 && page.getPageAmount() >= 19)
			if(page.getPages().length >= 38)
				addPage(page.getPages()[36], page.getPages()[37], icons[18], page.getPages().length, 37, x, y);
			else addPage(page.getPages()[36], "", icons[18], page.getPages().length, 37, x, y);
		else if(currTurnup == 19 && page.getPageAmount() >= 20)
			if(page.getPages().length >= 40)
				addPage(page.getPages()[38], page.getPages()[39], icons[19], page.getPages().length, 39, x, y);
			else addPage(page.getPages()[38], "", icons[19], page.getPages().length, 39, x, y);
	}

	private void addPage(String text1, String text2, Object icon, int pageAmount, int limit, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;

		tooltipStack = null;

		if(icon != null){
			if(icon instanceof ItemStack){
				if(pageAmount > limit)
					writeText(2, text2);
				writeText(1, text1, 50);
				renderItem(k + 60, b0 + 28,(ItemStack)icon, x, y);
			}
			if(icon instanceof ResourceLocation){
				if(pageAmount > limit)
					writeText(2, text2);
				writeText(1, text1, 100);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture((ResourceLocation)icon);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			}
			if(icon instanceof CraftingStack){
				if(pageAmount > limit)
					writeText(2, text2);
				writeText(1, text1, 95);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				boolean unicode = fontRendererObj.getUnicodeFlag();
				fontRendererObj.setUnicodeFlag(false);
				renderItem(k + 93, b0 + 52,((CraftingStack)icon).getOutput(), x, y);
				fontRendererObj.setUnicodeFlag(unicode);
				for(int i = 0; i <= 2; i++){
					renderItem(k + 24 +i*21, b0 + 31,((CraftingStack)icon).getFirstArray()[i], x, y);
					renderItem(k + 24 +i*21, b0 + 52,((CraftingStack)icon).getSecondArray()[i], x, y);
					renderItem(k + 24 +i*21, b0 + 73,((CraftingStack)icon).getThirdArray()[i], x, y);
				}
			}
			if(icon instanceof Tuple){
				if(((Tuple) icon).getFirst() != null){
					if(((Tuple) icon).getFirst() instanceof ItemStack){
						writeText(1, text1, 50);
						renderItem(k + 60, b0 + 28,(ItemStack)((Tuple) icon).getFirst(), x, y);
					}
					else if(((Tuple) icon).getFirst() instanceof ResourceLocation){
						writeText(1, text1, 100);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)((Tuple) icon).getFirst());
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
					}
					else if(((Tuple) icon).getFirst() instanceof CraftingStack){
						writeText(1, text1, 95);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
						drawTexturedModalRect(k, b0, 0, 0, 256, 256);
						boolean unicode = fontRendererObj.getUnicodeFlag();
						fontRendererObj.setUnicodeFlag(false);
						renderItem(k + 93, b0 + 52,((CraftingStack)((Tuple) icon).getFirst()).getOutput(), x, y);
						fontRendererObj.setUnicodeFlag(unicode);
						for(int i = 0; i <= 2; i++){
							renderItem(k + 24 +i*21, b0 + 31,((CraftingStack)((Tuple) icon).getFirst()).getFirstArray()[i], x, y);
							renderItem(k + 24 +i*21, b0 + 52,((CraftingStack)((Tuple) icon).getFirst()).getSecondArray()[i], x, y);
							renderItem(k + 24 +i*21, b0 + 73,((CraftingStack)((Tuple) icon).getFirst()).getThirdArray()[i], x, y);
						}
					}
				} else writeText(1, text1);
				if(((Tuple) icon).getSecond() != null){
					int n = 123;
					if(((Tuple) icon).getSecond() instanceof ItemStack){
						writeText(2, text2, 50);
						renderItem(k + 60 + n, b0 + 28,(ItemStack)((Tuple) icon).getSecond(), x, y);
					}
					else if(((Tuple) icon).getSecond() instanceof ResourceLocation){
						writeText(2, text2, 100);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture((ResourceLocation)((Tuple) icon).getSecond());
						drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
					}
					else if(((Tuple) icon).getSecond() instanceof CraftingStack){
						writeText(2, text2, 95);
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
						drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
						boolean unicode = fontRendererObj.getUnicodeFlag();
						fontRendererObj.setUnicodeFlag(false);
						renderItem(k + 93 + n, b0 + 52,((CraftingStack)((Tuple) icon).getSecond()).getOutput(), x, y);
						fontRendererObj.setUnicodeFlag(unicode);
						for(int i = 0; i <= 2; i++){
							renderItem(k + 24 + n +i*21, b0 + 31,((CraftingStack)((Tuple) icon).getSecond()).getFirstArray()[i], x, y);
							renderItem(k + 24 + n +i*21, b0 + 52,((CraftingStack)((Tuple) icon).getSecond()).getSecondArray()[i], x, y);
							renderItem(k + 24 + n +i*21, b0 + 73,((CraftingStack)((Tuple) icon).getSecond()).getThirdArray()[i], x, y);
						}
					} else writeText(2, text2);
				}
			}
		} else {
			if(pageAmount > limit)
				writeText(2, text2);
			writeText(1, text1);
		}

		if(tooltipStack != null)
		{
			List<String> tooltipData = tooltipStack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
			List<String> parsedTooltip = new ArrayList();
			boolean first = true;

			for(String s : tooltipData)
			{
				String s_ = s;
				if(!first)
					s_ = EnumChatFormatting.GRAY + s;
				parsedTooltip.add(s_);
				first = false;
			}
			GuiRenderHelper.renderTooltip(x, y, parsedTooltip);
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

	private ItemStack tooltipStack;
	public void renderItem(int xPos, int yPos, ItemStack stack, int mx, int my)
	{
		RenderItem render = new RenderItem();
		if(mx > xPos && mx < xPos+16 && my > yPos && my < yPos+16)
			tooltipStack = stack;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		render.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
	}
}
