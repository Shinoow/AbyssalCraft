/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.api.ritual.*;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonHome;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.util.IHiddenRitual;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.oredict.OreDictionary;

public class GuiNecronomiconRitualEntry extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage, buttonNextPageLong;
	private ButtonNextPage buttonPreviousPage, buttonPreviousPageLong;
	private GuiButton buttonDone;
	private ButtonHome buttonHome;
	private GuiNecronomicon parent;
	/** Used to separate which rituals this entry should display */
	private int ritualnum;
	private List<NecronomiconRitual> rituals = new ArrayList<>();

	private String ANYWHERE = localize(NecronomiconText.LABEL_ANYWHERE);

	public GuiNecronomiconRitualEntry(int bookType, GuiNecronomicon gui, int ritualnum){
		super(bookType);
		parent = gui;
		isInfo = true;
		this.ritualnum = ritualnum;
	}

	@Override
	public GuiNecronomicon withBookType(int par1){
		if(getBookType() > par1)
			isInvalid = true;
		return super.withBookType(par1);
	}

	@Override
	public void initGui(){
		if(isInvalid)
			mc.displayGuiScreen(parent.withBookType(getBookType()));
		currentNecro = this;
		if(rituals.isEmpty())
			initStuff();
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true, false));
		buttonList.add(buttonNextPageLong = new ButtonNextPage(2, i + 203, b0 + 167, true, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(3, i + 18, b0 + 154, false, false));
		buttonList.add(buttonPreviousPageLong = new ButtonNextPage(4, i + 23, b0 + 167, false, true));
		buttonList.add(buttonHome = new ButtonHome(5, i + 118, b0 + 167));

		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1;
		buttonNextPageLong.visible = currTurnup < getTurnupLimit() -5;
		buttonPreviousPage.visible = true;
		buttonPreviousPageLong.visible = currTurnup > 4;
		buttonDone.visible = true;
		buttonHome.visible = true;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1){
				if(currTurnup < getTurnupLimit() -1)
					++currTurnup;
			} else if(button.id == 2){
				if(currTurnup < getTurnupLimit() -5)
					currTurnup += 5;
			} else if(button.id == 3){
				if(currTurnup == 0){
					isInfo = false;
					mc.displayGuiScreen(parent.withBookType(getBookType()));
				} else if(currTurnup > 0)
					--currTurnup;
			} else if(button.id == 4){
				if(currTurnup > 4)
					currTurnup -= 5;
			} else if(button.id == 5)
				mc.displayGuiScreen(new GuiNecronomicon(getBookType()));

		updateButtons();
	}

	@Override
	protected void drawInformationText(int x, int y){

		drawPage(rituals.get(currTurnup), x, y);
	}

	private void drawPage(NecronomiconRitual ritual, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String title = ritual.getLocalizedName();
		fontRenderer.drawSplitString(title, k + 20, b0 + 16, 116, 0xC40000);

		if(ritual.requiresSacrifice())
			fontRenderer.drawSplitString(localize(NecronomiconText.LABEL_SACRIFICE), k + 138, 164, 107, 0xC40000);
		writeText(1, localize(NecronomiconText.LABEL_REQUIRED_ENERGY) + ": " + ritual.getReqEnergy() + " PE", 125);
		writeText(2, localize(NecronomiconText.LABEL_LOCATION) + ": " + getDimension(ritual.getDimension()));
		if(!ritual.getDescription().startsWith("ac.ritual."))
			writeText(2, ritual.getDescription(), 48);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(NecronomiconResources.RITUAL);
		drawTexturedModalRect(k, b0, 0, 0, 256, 256);
		if(ritual.getSacrifice() != null){
			mc.renderEngine.bindTexture(NecronomiconResources.RITUAL_INFUSION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
		} if(ritual instanceof NecronomiconCreationRitual){
			mc.renderEngine.bindTexture(NecronomiconResources.RITUAL_CREATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
		}

		tooltipStack = null;

		Object[] offerings = new Object[8];
		if(ritual.getOfferings().length < 8)
			for(int i = 0; i < ritual.getOfferings().length; i++)
				offerings[i] = ritual.getOfferings()[i];
		else offerings = ritual.getOfferings();

		if(ritual instanceof NecronomiconTransformationRitual)
			offerings = ((NecronomiconTransformationRitual) ritual).getCombinedContent();

		//north
		renderObject(k + 58, b0 + 30, offerings[0], x, y);
		//north-east
		renderObject(k + 84, b0 + 40, offerings[1], x, y);
		//east
		renderObject(k + 94, b0 + 66, offerings[2], x, y);
		//south-east
		renderObject(k + 84, b0 + 92, offerings[3], x, y);
		//south
		renderObject(k + 58, b0 + 103, offerings[4], x, y);
		//south-west
		renderObject(k + 32, b0 + 92, offerings[5], x, y);
		//west
		renderObject(k + 22, b0 + 66, offerings[6], x, y);
		//north-west
		renderObject(k + 32, b0 + 40, offerings[7], x, y);
		//center
		renderObject(k + 58, b0 + 66, ritual.getSacrifice(), x, y);

		if(ritual instanceof NecronomiconCreationRitual)
			renderItem(k + 58, b0 + 139, ((NecronomiconCreationRitual) ritual).getItem(), x, y);

		renderTooltip(x, y);
	}

	private String getDimension(int dim){

		return dim == OreDictionary.WILDCARD_VALUE ? ANYWHERE : DimensionDataRegistry.instance().getDimensionName(dim);
	}

	private void initStuff(){
		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual.getBookType() == ritualnum && isUnlocked(ritual.getResearchItem(ritual)) && !(ritual instanceof IHiddenRitual))
				rituals.add(ritual);
		setTurnupLimit(rituals.size());
	}
}
