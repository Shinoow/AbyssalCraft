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

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.gui.necronomicon.entries.GuiNecronomiconEntry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.entries.GuiNecronomiconSpellEntry;
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.OpenSpellbookMessage;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.item.ItemStack;

public class GuiNecronomiconSpells extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage, buttonPreviousPage;
	private ButtonCategory buttonCat1, buttonCat2, buttonCat3;
	private GuiButton buttonDone;
	private ItemStack book;

	public GuiNecronomiconSpells(int bookType, ItemStack book){
		super(bookType);
		this.book = book;
	}

	@Override
	public void initGui()
	{
		currentNecro = this;
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		initBaseButtons();

		updateButtons();
	}

	@Override
	protected void initButtonsInner() {
		int i = (width - guiWidth) / 2;
		buttonList.add(buttonCat1 = new ButtonCategory(8, i + 14, 26, this, NecronomiconText.LABEL_CREATE_SPELLS));
		buttonList.add(buttonCat2 = new ButtonCategory(9, i + 14, 43, this, NecronomiconText.LABEL_OPEN_COMPENDIUM, hasSpells() ? ACItems.necronomicon : ACItems.oblivion_catalyst));
		buttonList.add(buttonCat3 = new ButtonCategory(10, i + 14, 60, this, NecronomiconText.LABEL_INFORMATION));

	}

	@Override
	protected void updateButtonsInner() {
		buttonCat2.visible = hasSpells();
	}

	private boolean hasSpells(){
		// TODO add logic that checks if any spells are unlocked
		return true;
	}

	@Override
	protected void actionPerformedInner(GuiButton button) {
		if (button.id == 8)
			PacketDispatcher.sendToServer(new OpenSpellbookMessage());
		else if (button.id == 9)
			mc.displayGuiScreen(new GuiNecronomiconSpellEntry(getBookType(), this));
		else if(button.id == 10)
			mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("spells"), this));
	}

	@Override
	protected void drawIndexText(){
		drawTitle(localize(NecronomiconText.LABEL_SPELLBOOK));
		writeText(2, book != null ? NecronomiconText.SPELL_INFO : "Whoops");
	}
}
