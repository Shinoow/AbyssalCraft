/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.necronomicon;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class GuiInstance implements INecroData {

	protected int displayIcon;
	protected String title, identifier;

	protected GuiInstance(int displayIcon, String title, String identifier){
		this.displayIcon = displayIcon;
		this.title = title;
		this.identifier = identifier;
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
