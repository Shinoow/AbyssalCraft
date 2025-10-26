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
package com.shinoow.abyssalcraft.client.gui.necronomicon.entries;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.api.ritual.*;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonHome;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.util.IHiddenRitual;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.oredict.OreDictionary;

public class GuiNecronomiconRitualEntry extends GuiNecronomicon {

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
		if(getKnowledgeLevel() > par1)
			isInvalid = true;
		return super.withBookType(par1);
	}

	@Override
	public void initGui(){
		if(isInvalid)
			mc.displayGuiScreen(parent.withBookType(getBookType()));
		if(rituals.isEmpty())
			initStuff();
		initCommon();
	}

	@Override
	protected void updateButtonsInner() {
		buttonHome.visible = true;
	}
	
	@Override
	protected void drawInformationText(int x, int y){

		drawPage(rituals.get(currTurnup), x, y);
	}

	private void drawPage(NecronomiconRitual ritual, int x, int y){
		int k = (width - guiWidth) / 2;
		drawTitle(ritual.getLocalizedName());

		if(ritual.requiresSacrifice())
			fontRenderer.drawSplitString(localize(NecronomiconText.LABEL_SACRIFICE), k + 138, 164, 107, 0xC40000);
		writeText(1, localize(NecronomiconText.LABEL_REQUIRED_ENERGY) + ": " + ritual.getReqEnergy() + " PE", 125);
		writeText(2, localize(NecronomiconText.LABEL_LOCATION) + ": " + getDimension(ritual.getDimension()));
		if(!ritual.getDescription().startsWith("ac.ritual."))
			writeText(2, ritual.getDescription(), 48);
		drawTexture(NecronomiconResources.RITUAL);
		if(ritual.getSacrifice() != null)
			drawTexture(NecronomiconResources.RITUAL_INFUSION);
		if(ritual instanceof NecronomiconCreationRitual)
			drawTexture(NecronomiconResources.RITUAL_CREATION);


		getHelper().clearTooltipStack();

		Object[] offerings = new Object[8];
		if(ritual.getOfferings().length < 8)
			for(int i = 0; i < ritual.getOfferings().length; i++)
				offerings[i] = ritual.getOfferings()[i];
		else offerings = ritual.getOfferings();

		if(ritual instanceof NecronomiconTransformationRitual)
			offerings = ((NecronomiconTransformationRitual) ritual).getCombinedContent();

		//north
		renderObject(k + 58, 32, offerings[0], x, y);
		//north-east
		renderObject(k + 84, 42, offerings[1], x, y);
		//east
		renderObject(k + 94, 68, offerings[2], x, y);
		//south-east
		renderObject(k + 84, 94, offerings[3], x, y);
		//south
		renderObject(k + 58, 105, offerings[4], x, y);
		//south-west
		renderObject(k + 32, 94, offerings[5], x, y);
		//west
		renderObject(k + 22, 68, offerings[6], x, y);
		//north-west
		renderObject(k + 32, 42, offerings[7], x, y);
		//center
		renderObject(k + 58, 68, ritual.getSacrifice(), x, y);

		if(ritual instanceof NecronomiconCreationRitual)
			renderItem(k + 58, 141, ((NecronomiconCreationRitual) ritual).getItem(), x, y);

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
