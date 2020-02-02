/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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
import com.shinoow.abyssalcraft.common.network.PacketDispatcher;
import com.shinoow.abyssalcraft.common.network.server.OpenSpellbookMessage;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
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

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true, false));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false, false));
		buttonList.add(buttonCat1 = new ButtonCategory(3, i + 14, b0 + 24, this, "Create/modify spells", false, ACItems.necronomicon));
		buttonList.add(buttonCat2 = new ButtonCategory(4, i + 14, b0 + 41, this, "Open Compendium", false, hasSpells() ? ACItems.necronomicon : ACItems.oblivion_catalyst));
		buttonList.add(buttonCat3 = new ButtonCategory(5, i + 14, b0 + 58, this, NecronomiconText.LABEL_INFO, false, ACItems.necronomicon));
		//		buttonList.add(buttonCat3 = new ButtonCategory(5, i + 10, b0 + 80, this, 0, "necronomicon.index.rituals", AbyssalCraft.necronomicon));
		//		if(bookType == 4)
		//			buttonList.add(buttonCat4 = new ButtonCategory(6, i + 10, b0 + 105, this, 0, "necronomicon.index.huh", AbyssalCraft.abyssalnomicon));
		//		else buttonList.add(buttonCat4 = new ButtonCategory(6, i + 10, b0 + 105, this, 0, "necronomicon.index.huh", AbyssalCraft.necronomicon));
		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = false;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		buttonCat1.visible = true;
		buttonCat2.visible = hasSpells();
		buttonCat3.visible = true;
		//		buttonCat3.visible = true;
		//		buttonCat4.visible = true;

	}

	private boolean hasSpells(){
		//		if(book.hasTagCompound())
		//			return book.getTagCompound().hasKey("Knowledge");
		return true;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if (button.id == 2)
				mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
			else if (button.id == 3)
				PacketDispatcher.sendToServer(new OpenSpellbookMessage());
			else if (button.id == 4)
				mc.displayGuiScreen(new GuiNecronomiconSpellEntry(getBookType(), this));
			else if(button.id == 5)
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("spells"), this));
			updateButtons();
		}
	}

	@Override
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = localize(NecronomiconText.LABEL_SPELLBOOK);
		fontRenderer.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		writeText(2, book != null ? NecronomiconText.SPELL_INFO : "Whoops");
	}
}
