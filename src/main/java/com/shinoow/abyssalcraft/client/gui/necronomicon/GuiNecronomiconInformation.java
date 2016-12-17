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

import javax.imageio.ImageIO;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

import org.lwjgl.input.Keyboard;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

public class GuiNecronomiconInformation extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage, buttonPreviousPage;
	private ButtonCategory buttonCat1, buttonCat2, buttonCat3, buttonCat4,
	buttonCat5, buttonCat6, buttonCat7, buttonCat8, buttonCat9, buttonCat10;
	private GuiButton buttonDone;
	private boolean isAC = false;
	private boolean isAN = false;
	private boolean isP = false;
	private static NecroData data;

	public GuiNecronomiconInformation(int bookType){
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
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
		buttonList.add(buttonCat1 = new ButtonCategory(3, i + 14, b0 + 24, this, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, ACItems.necronomicon));
		buttonList.add(buttonCat2 = new ButtonCategory(4, i + 14, b0 + 41, this, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, ACItems.necronomicon));
		buttonList.add(buttonCat3 = new ButtonCategory(5, i + 14, b0 + 58, this, NecronomiconText.LABEL_INFORMATION_ABYSSALNOMICON, ACItems.abyssalnomicon));
		buttonList.add(buttonCat4 = new ButtonCategory(6, i + 14, b0 + 75, this, NecronomiconText.LABEL_PATRONS, ACItems.necronomicon));
		buttonList.add(buttonCat5 = new ButtonCategory(7, i + 14, b0 + 92, this, NecronomiconText.LABEL_INFORMATION_MACHINES, getBookType() >= 1 ? ACItems.necronomicon : ACItems.oblivion_catalyst));
		buttonList.add(buttonCat6 = new ButtonCategory(8, i + 14, b0 + 109, this, NecronomiconText.LABEL_INFORMATION_OVERWORLD, ACItems.necronomicon));
		if(getBookType() >= 1)
			buttonList.add(buttonCat7 = new ButtonCategory(9, i + 14, b0 + 126, this, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND, ACItems.abyssal_wasteland_necronomicon));
		if(getBookType() >= 2)
			buttonList.add(buttonCat8 = new ButtonCategory(10, i + 132, b0 + 24, this, NecronomiconText.LABEL_INFORMATION_DREADLANDS, ACItems.dreadlands_necronomicon));
		if(getBookType() >= 3)
			buttonList.add(buttonCat9 = new ButtonCategory(11, i + 132, b0 + 41, this, NecronomiconText.LABEL_INFORMATION_OMOTHOL, ACItems.omothol_necronomicon));
		if(getBookType() == 4)
			buttonList.add(buttonCat10 = new ButtonCategory(12, i + 132, b0 + 58, this, NecronomiconText.LABEL_INFORMATION_DARK_REALM, ACItems.omothol_necronomicon));

		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo && !isAN;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		buttonCat1.visible = true;
		buttonCat2.visible = true;
		buttonCat3.visible = true;
		buttonCat4.visible = true;
		buttonCat5.visible = true;
		buttonCat6.visible = true;
		if(getBookType() >= 1)
			buttonCat7.visible = true;
		if(getBookType() >= 2)
			buttonCat8.visible = true;
		if(getBookType() >= 3)
			buttonCat9.visible = true;
		if(getBookType() == 4)
			buttonCat10.visible = true;

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
					mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
				else if(currTurnup == 0 && isInfo){
					initGui();
					isInfo = isAC = isAN = isP = false;
					setTurnupLimit(2);
				} else if (currTurnup > 0)
					--currTurnup;
			} else if(button.id == 3){
				isInfo = true;
				isAC = true;
				drawButtons();
			} else if(button.id == 4)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("greatoldones"), this, ACItems.necronomicon));
			else if(button.id == 5){
				isInfo = true;
				isAN = true;
				drawButtons();
			} else if(button.id == 6){
				isInfo = true;
				isP = true;
				drawButtons();
			} else if(button.id == 7){
				if(getBookType() >= 1)
					mc.displayGuiScreen(new GuiNecronomiconMachines(getBookType()));
			} else if(button.id == 8)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("overworld"), this, ACItems.necronomicon));
			else if(button.id == 9)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("abyssalwasteland"), this, ACItems.abyssal_wasteland_necronomicon));
			else if(button.id == 10)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("dreadlands"), this, ACItems.dreadlands_necronomicon));
			else if(button.id == 11)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("omothol"), this, ACItems.omothol_necronomicon));
			else if(button.id == 12)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("darkrealm"), this, ACItems.omothol_necronomicon));
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

	public static void setPatreonInfo(Chapter info){
		data = new NecroData("information", "", new Chapter("acinfo", NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT,
				new Page(1, NecronomiconResources.ABYSSALCRAFT_1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_1),
				new Page(2, NecronomiconResources.BLANK, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_2),
				new Page(3, NecronomiconResources.ABYSSALCRAFT_2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_3),
				new Page(4, NecronomiconResources.BLANK, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_4),
				new Page(5, NecronomiconResources.ABYSSALCRAFT_3, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_5),
				new Page(6, NecronomiconResources.BLANK, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_6)),
				new Chapter("abyssalnomicon", NecronomiconText.LABEL_INFORMATION_ABYSSALNOMICON, new Page(1, NecronomiconText.INFORMATION_ABYSSALNOMICON)));
		if(Loader.instance().getLoaderState() == LoaderState.INITIALIZATION
				&& Loader.instance().activeModContainer().getModId().equals("abyssalcraft"))
			data.addChapter(info);
	}

	@Override
	protected void drawInformationText(int x, int y){
		if(isAC)
			drawChapter(data.getChapters()[0], x, y);
		if(isAN)
			drawChapter(data.getChapters()[1], x, y);
		if(isP)
			drawChapter(data.getChapters()[2], x, y);
	}

	private void drawChapter(Chapter chapter, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;

		stuff = chapter.getTitle();
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

		if(icon1 != null && icon1 instanceof ResourceLocation || icon1 instanceof String)
			writeText(1, text1, 100);
		else writeText(1, text1);
		if(icon2 != null && icon2 instanceof ResourceLocation || icon2 instanceof String)
			writeText(2, text2, 100);
		else writeText(2, text2);

		writeText(1, String.valueOf(displayNum - 1), 165, 50);
		writeText(2, String.valueOf(displayNum), 165, 50);

		if(icon1 != null){
			if(icon1 instanceof ResourceLocation){
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture((ResourceLocation)icon1);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			}
			if(icon1 instanceof String)
				if(failcache.contains(icon1)){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				} else if(successcache.get(icon1) != null){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					if(t != null)
						GlStateManager.bindTexture(t.getGlTextureId());
					else mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				}
		}
		if(icon2 != null){
			if(icon2 instanceof ResourceLocation){
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture((ResourceLocation)icon2);
				drawTexturedModalRect(k + 123, b0, 0, 0, 256, 256);
			}
			if(icon2 instanceof String)
				if(failcache.contains(icon2)){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k + 123, b0, 0, 0, 256, 256);
				} else if(successcache.get(icon2) != null){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.bindTexture(successcache.get(icon2).getGlTextureId());
					drawTexturedModalRect(k + 123, b0, 0, 0, 256, 256);
				} else {
					DynamicTexture t = null;
					try {
						t = new DynamicTexture(ImageIO.read(new URL((String)icon2)));
						successcache.put((String)icon2, t);
					} catch (Exception e) {
						failcache.add((String)icon2);
					}
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					if(t != null)
						GlStateManager.bindTexture(t.getGlTextureId());
					else mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k + 123, b0, 0, 0, 256, 256);
				}
		}
	}
}
