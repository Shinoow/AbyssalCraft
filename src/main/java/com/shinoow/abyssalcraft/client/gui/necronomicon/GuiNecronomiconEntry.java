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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.GuiRenderHelper;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;

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
			for(int n = 0; n < data.getChapters().length; n++)
				buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + 14, b0 + 24 + 17*n,this, data.getChapters()[n].getTitle(), icon));
		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		if(data != null)
			for(int i = 0; i < data.getChapters().length; i++)
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
			drawChapter(data.getChapters()[0], x, y);
		else if(bool2)
			drawChapter(data.getChapters()[1], x, y);
		else if(bool3)
			drawChapter(data.getChapters()[2], x, y);
		else if(bool4)
			drawChapter(data.getChapters()[3], x, y);
		else if(bool5)
			drawChapter(data.getChapters()[4], x, y);
		else if(bool6)
			drawChapter(data.getChapters()[5], x, y);
		else if(bool7)
			drawChapter(data.getChapters()[6], x, y);
		updateButtons();
	}

	private void drawChapter(Chapter chapter, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;

		stuff = I18n.format(chapter.getTitle(), new Object[0]);
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		setTurnupLimit(chapter.getTurnupAmount());

		int num = (currTurnup + 1)*2;

		addPage(chapter.getPage(num-1), chapter.getPage(num), num, x, y);
	}

	private void addPage(Page page1, Page page2, int displayNum, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String text1 = "";
		String text2 = "";
		Object icon1 = null;
		Object icon2 = null;

		if(page1 != null){
			text1 = page1.getText();
			icon1 = page1.getIcon();
		}
		if(page2 != null){
			text2 = page2.getText();
			icon2 = page2.getIcon();
		}

		tooltipStack = null;

		writeTexts(icon1, icon2, text1, text2);

		writeText(1, String.valueOf(displayNum - 1), 165, 50);
		writeText(2, String.valueOf(displayNum), 165, 50);

		if(icon1 != null){
			if(icon1 instanceof ItemStack)
				renderItem(k + 60, b0 + 28,(ItemStack)icon1, x, y);
			if(icon1 instanceof ResourceLocation){
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture((ResourceLocation)icon1);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			}
			if(icon1 instanceof CraftingStack){
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				boolean unicode = fontRendererObj.getUnicodeFlag();
				fontRendererObj.setUnicodeFlag(false);
				renderItem(k + 93, b0 + 52,((CraftingStack)icon1).getOutput(), x, y);
				fontRendererObj.setUnicodeFlag(unicode);
				for(int i = 0; i <= 2; i++){
					renderItem(k + 24 +i*21, b0 + 31,((CraftingStack)icon1).getFirstArray()[i], x, y);
					renderItem(k + 24 +i*21, b0 + 52,((CraftingStack)icon1).getSecondArray()[i], x, y);
					renderItem(k + 24 +i*21, b0 + 73,((CraftingStack)icon1).getThirdArray()[i], x, y);
				}
			}
			if(icon1 instanceof String)
				if(failcache.contains(icon1)){
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				} else if(successcache.get(icon1) != null){
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.bindTexture(successcache.get(icon1).getGlTextureId());
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				} else {
					DynamicTexture t = null;
					try {
						t = new DynamicTexture(ImageIO.read(new URL((String)icon1)));
						successcache.put((String)icon1, t);
					} catch (Exception e) {
						failcache.add((String)icon1);
					}
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					if(t != null)
						GlStateManager.bindTexture(t.getGlTextureId());
					else mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				}
		}
		if(icon2 != null){
			int n = 123;
			if(icon2 instanceof ItemStack)
				renderItem(k + 60 + n, b0 + 28,(ItemStack)icon2, x, y);
			if(icon2 instanceof ResourceLocation){
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture((ResourceLocation)icon2);
				drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
			}
			if(icon2 instanceof CraftingStack){
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glPushMatrix();
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
				drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				GL11.glEnable(GL12.GL_RESCALE_NORMAL);
				GL11.glEnable(GL11.GL_DEPTH_TEST);
				GL11.glPopMatrix();
				GL11.glDisable(GL11.GL_LIGHTING);
				boolean unicode = fontRendererObj.getUnicodeFlag();
				fontRendererObj.setUnicodeFlag(false);
				renderItem(k + 93 + n, b0 + 52,((CraftingStack)icon2).getOutput(), x, y);
				fontRendererObj.setUnicodeFlag(unicode);
				for(int i = 0; i <= 2; i++){
					renderItem(k + 24 + n +i*21, b0 + 31,((CraftingStack)icon2).getFirstArray()[i], x, y);
					renderItem(k + 24 + n +i*21, b0 + 52,((CraftingStack)icon2).getSecondArray()[i], x, y);
					renderItem(k + 24 + n +i*21, b0 + 73,((CraftingStack)icon2).getThirdArray()[i], x, y);
				}
			}
			if(icon2 instanceof String)
				if(failcache.contains(icon2)){
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				} else if(successcache.get(icon2) != null){
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.bindTexture(successcache.get(icon2).getGlTextureId());
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				} else {
					DynamicTexture t = null;
					try {
						t = new DynamicTexture(ImageIO.read(new URL((String)icon2)));
						successcache.put((String)icon2, t);
					} catch (Exception e) {
						failcache.add((String)icon2);
					}
					GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
					if(t != null)
						GlStateManager.bindTexture(t.getGlTextureId());
					else mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				}
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
					s_ = TextFormatting.GRAY + s;
				parsedTooltip.add(s_);
				first = false;
			}
			GuiRenderHelper.renderTooltip(x, y, parsedTooltip);
		}
	}

	private void writeTexts(Object icon1, Object icon2, String text1, String text2){

		if(icon1 != null){
			if(icon1 instanceof ItemStack)
				writeText(1, text1, 50);
			if(icon1 instanceof ResourceLocation || icon1 instanceof String)
				writeText(1, text1, 100);
			if(icon1 instanceof CraftingStack)
				writeText(1, text1, 95);
		} else writeText(1, text1);
		if(icon2 != null){
			if(icon2 instanceof ItemStack)
				writeText(2, text2, 50);
			if(icon2 instanceof ResourceLocation || icon2 instanceof String)
				writeText(2, text2, 100);
			if(icon2 instanceof CraftingStack)
				writeText(2, text2, 95);
		} else writeText(2, text2);
	}

	@Override
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = I18n.format(data.getTitle(), new Object[0]);
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		if(data.getInformation() != null) writeText(2, data.getInformation());
	}

	private ItemStack tooltipStack;
	public void renderItem(int xPos, int yPos, ItemStack stack, int mx, int my)
	{

		if(stack == null) return;

		if(stack != null && stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			stack.setItemDamage(0);

		RenderItem render = Minecraft.getMinecraft().getRenderItem();
		if(mx > xPos && mx < xPos+16 && my > yPos && my < yPos+16)
			tooltipStack = stack;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		render.renderItemAndEffectIntoGUI(stack, xPos, yPos);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, stack, xPos, yPos, null);
		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
	}
}
