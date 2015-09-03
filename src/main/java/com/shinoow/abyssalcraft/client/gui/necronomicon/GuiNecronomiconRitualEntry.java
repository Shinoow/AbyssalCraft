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
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconInfusionRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

public class GuiNecronomiconRitualEntry extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private GuiButton buttonDone;
	private GuiNecronomicon parent;
	private Map<Integer, String> dimToString = Maps.newHashMap();
	/** Used to separate which rituals this entry should display */
	private int ritualnum;
	private List<NecronomiconRitual> rituals = Lists.newArrayList();

	public GuiNecronomiconRitualEntry(int bookType, GuiNecronomicon gui, int ritualnum){
		super(bookType);
		parent = gui;
		isInfo = true;
		this.ritualnum = ritualnum;
	}

	@Override
	public void initGui(){
		initStuff();
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));

		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1){
				if (currTurnup < getTurnupLimit() -1)
					++currTurnup;
			} else if (button.id == 2)
				if(currTurnup == 0){
					isInfo = false;
					mc.displayGuiScreen(parent);
				} else if (currTurnup > 0)
					--currTurnup;
		updateButtons();
	}

	@Override
	protected void drawInformationText(){
		if(currTurnup == 0)
			drawPage(rituals.get(0));
		else if(currTurnup == 1 && rituals.size() >= 2)
			drawPage(rituals.get(1));
		else if(currTurnup == 2 && rituals.size() >= 3)
			drawPage(rituals.get(2));
		else if(currTurnup == 3 && rituals.size() >= 4)
			drawPage(rituals.get(3));
		else if(currTurnup == 4 && rituals.size() >= 5)
			drawPage(rituals.get(4));
		else if(currTurnup == 5 && rituals.size() >= 6)
			drawPage(rituals.get(5));
		else if(currTurnup == 6 && rituals.size() >= 7)
			drawPage(rituals.get(6));
		else if(currTurnup == 7 && rituals.size() >= 8)
			drawPage(rituals.get(7));
		else if(currTurnup == 8 && rituals.size() >= 9)
			drawPage(rituals.get(8));
		else if(currTurnup == 9 && rituals.size() >= 10)
			drawPage(rituals.get(9));
		else if(currTurnup == 10 && rituals.size() >= 11)
			drawPage(rituals.get(10));
		else if(currTurnup == 11 && rituals.size() >= 12)
			drawPage(rituals.get(11));
		else if(currTurnup == 12 && rituals.size() >= 13)
			drawPage(rituals.get(12));
		else if(currTurnup == 13 && rituals.size() >= 14)
			drawPage(rituals.get(13));
		else if(currTurnup == 14 && rituals.size() >= 15)
			drawPage(rituals.get(14));
		else if(currTurnup == 15 && rituals.size() >= 16)
			drawPage(rituals.get(15));
		else if(currTurnup == 16 && rituals.size() >= 17)
			drawPage(rituals.get(16));
		else if(currTurnup == 17 && rituals.size() >= 18)
			drawPage(rituals.get(17));
		else if(currTurnup == 18 && rituals.size() >= 19)
			drawPage(rituals.get(18));
		else if(currTurnup == 19 && rituals.size() >= 20)
			drawPage(rituals.get(19));
	}

	private void drawPage(NecronomiconRitual ritual){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String title = ritual.getLocalizedName();
		ItemStack[] offerings = new ItemStack[8];
		if(ritual.getOfferings().length < 8)
			for(int i = 0; i < ritual.getOfferings().length; i++)
				offerings[i] = ritual.getOfferings()[i];
		else offerings = ritual.getOfferings();
		fontRendererObj.drawSplitString(title, k + 20, b0 + 16, 116, 0xC40000);

		writeText(2, NecronomiconText.LABEL_LOCATION + ": " + dimToString.get(ritual.getDimension()));
		writeText(2, ritual.getDescription(), 48);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(NecronomiconResources.RITUAL);
		drawTexturedModalRect(k, b0, 0, 0, 256, 256);
		if(ritual instanceof NecronomiconCreationRitual){
			mc.renderEngine.bindTexture(NecronomiconResources.RITUAL_CREATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			if(ritual instanceof NecronomiconInfusionRitual){
				mc.renderEngine.bindTexture(NecronomiconResources.RITUAL_INFUSION);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			}
		}

		//north
		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offerings[0], k + 58, b0 + 30);
		RenderHelper.disableStandardItemLighting();
		//north-east
		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offerings[1], k + 84, b0 + 40);
		RenderHelper.disableStandardItemLighting();
		//east
		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offerings[2], k + 94, b0 + 66);
		RenderHelper.disableStandardItemLighting();
		//south-east
		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offerings[3], k + 84, b0 + 92);
		RenderHelper.disableStandardItemLighting();
		//south
		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offerings[4], k + 58, b0 + 103);
		RenderHelper.disableStandardItemLighting();
		//south-west
		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offerings[5], k + 32, b0 + 92);
		RenderHelper.disableStandardItemLighting();
		//west
		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offerings[6], k + 22, b0 + 66);
		RenderHelper.disableStandardItemLighting();
		//north-west
		itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), offerings[7], k + 32, b0 + 40);
		RenderHelper.disableStandardItemLighting();

		if(ritual instanceof NecronomiconCreationRitual){
			itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), ((NecronomiconCreationRitual) ritual).getItem(), k + 58, b0 + 139);
			RenderHelper.disableStandardItemLighting();
			if(ritual instanceof NecronomiconInfusionRitual){
				itemRender.renderItemAndEffectIntoGUI(fontRendererObj, mc.getTextureManager(), ((NecronomiconInfusionRitual) ritual).getSacrifice(), k + 58, b0 + 66);
				RenderHelper.disableStandardItemLighting();
			}
		}
	}

	private void initStuff(){
		dimToString.put(-1, NecronomiconText.LABEL_ANYWHERE);
		dimToString.put(0, NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE);
		dimToString.put(AbyssalCraft.configDimId1, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE);
		dimToString.put(AbyssalCraft.configDimId2, NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE);
		dimToString.put(AbyssalCraft.configDimId3, NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE);
		dimToString.put(AbyssalCraft.configDimId4, NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE);

		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual.getBookType() == ritualnum)
				rituals.add(ritual);
		setTurnupLimit(rituals.size());
	}
}
