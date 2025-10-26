package com.shinoow.abyssalcraft.common.util;

import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.necronomicon.GuiInstance;
import com.shinoow.abyssalcraft.client.gui.necronomicon.*;
import com.shinoow.abyssalcraft.client.gui.necronomicon.entries.GuiNecronomiconRitualEntry;

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
			return new GuiNecronomiconPlacesOfPower(bookType, (GuiNecronomicon) parent);
		case 1:
			return new GuiNecronomiconMachines(bookType, (GuiNecronomicon) parent);
		case 2:
			return new GuiNecronomiconSpells(bookType, Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND), (GuiNecronomicon) parent);
		case 3:
			return new GuiNecronomiconRitualEntry(bookType, (GuiNecronomicon) parent, displayIcon);
		default:
			return null;
		}
	}

}
