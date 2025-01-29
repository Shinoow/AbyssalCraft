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
package com.shinoow.abyssalcraft.api.necronomicon;

import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.ResearchItems;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class GuiInstance implements INecroData {

	protected int displayIcon;
	protected String title, identifier;
	private IResearchItem research;

	protected GuiInstance(int displayIcon, String title, String identifier){
		this(displayIcon, title, identifier, ResearchItems.DEFAULT);
	}

	protected GuiInstance(int displayIcon, String title, String identifier, IResearchItem research){
		this.displayIcon = displayIcon;
		this.title = title;
		this.identifier = identifier;
		this.research = research;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public int getDisplayIcon() {
		return displayIcon;
	}

	@Override
	public String getText() {
		return null;
	}

	@Override
	public boolean hasText() {
		return false;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public GuiInstance setResearch(IResearchItem research) {
		this.research = research;
		return this;
	}

	@Override
	public IResearchItem getResearch() {

		return research;
	}

	/**
	 * Grabs a Gui to open. Since it's inside the Necronomicon, it would be logical that it's an instance of<br>
	 * com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon<br>
	 * But any Gui that's an instance of GuiScreen will do.<br>
	 * <b>Remember to {@link SideOnly} annotate this method in any implementation that isn't strictly on the client since it's client-side only</b>
	 * @param bookType Book type of the Necronomicon Gui invoking this
	 * @param parent Parent Gui (always an instance of GuiNecronomicon)
	 */
	@SideOnly(Side.CLIENT)
	public abstract GuiScreen getOpenGui(int bookType, GuiScreen parent);
}
