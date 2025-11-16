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
package com.shinoow.abyssalcraft.common.util;

import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.necronomicon.GuiInstance;
import com.shinoow.abyssalcraft.api.recipe.AnvilForgingType;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomiconSpells;
import com.shinoow.abyssalcraft.client.gui.necronomicon.entries.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Put all GUiInstance stuff in one place
 *
 * @author shinoow
 *
 */
public class GuiInstanceBase extends GuiInstance {

	private final int id;

	public GuiInstanceBase(int displayIcon, String title, String identifier, int id) {
		super(displayIcon, title, identifier);
		this.id = id;
	}

	public GuiInstanceBase(int displayIcon, String title, String identifier, IResearchItem research, int id) {
		super(displayIcon, title, identifier, research);
		this.id = id;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen getOpenGui(int bookType, GuiScreen parent) {

		switch(id) {
		case 0:
			return new GuiNecronomiconPlacesOfPowerEntry(bookType, (GuiNecronomicon) parent);
		case 1:
			return new GuiNecronomiconSpells(bookType, Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND), (GuiNecronomicon) parent);
		case 2:
			return new GuiNecronomiconRitualEntry(bookType, (GuiNecronomicon) parent, displayIcon);
		case 3:
			return new GuiNecronomiconAnvilEntry(bookType, (GuiNecronomicon) parent);
		case 4:
			return new GuiNecronomiconTransmutatorEntry(bookType, (GuiNecronomicon) parent);
		case 5:
			return new GuiNecronomiconCrystallizerEntry(bookType, (GuiNecronomicon) parent);
		case 6:
			return new GuiNecronomiconMaterializerEntry(bookType, (GuiNecronomicon) parent);
		case 7:
			return new GuiNecronomiconAnvilEntry(bookType, (GuiNecronomicon) parent).withFilter(AnvilForgingType.RITUAL_CHARM);
		default:
			return null;
		}
	}
}
