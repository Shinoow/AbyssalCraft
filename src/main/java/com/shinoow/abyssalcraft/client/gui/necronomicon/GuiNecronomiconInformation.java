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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
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
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo && !isAN && !isP;
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

	@Override
	protected void drawInformationText(int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		if(isAC){
			stuff = I18n.format(NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, new Object[0]);
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			setTurnupLimit(3);
			if(currTurnup == 0){
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_1, 100);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_2, 100);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ABYSSALCRAFT_1);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			} else if(currTurnup == 1){
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_3, 100);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_4, 100);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ABYSSALCRAFT_2);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			} else if(currTurnup == 2){
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_5, 100);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_6, 100);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ABYSSALCRAFT_3);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			}
		} else if(isAN){
			stuff = I18n.format(NecronomiconText.LABEL_INFORMATION_ABYSSALNOMICON, new Object[0]);
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			setTurnupLimit(1);
			writeText(1, NecronomiconText.INFORMATION_ABYSSALNOMICON);
		} else if(isP){
			stuff = "Patrons";
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			setTurnupLimit(1);
			writeText(1, "Saice Shoop", 100);
			writeText(2, "Minecreatr", 100);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/patreon/saice.png"));
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/patreon/minecreatr.png"));
			drawTexturedModalRect(k + 123, b0, 0, 0, 256, 256);
		}
	}
}
