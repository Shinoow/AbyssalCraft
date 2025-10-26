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

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.spell.Spell;
import com.shinoow.abyssalcraft.api.spell.SpellRegistry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonHome;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class GuiNecronomiconSpellEntry extends GuiNecronomicon {

	private List<Spell> spells = new ArrayList<>();
	private List<ItemStack> scrolls = new ArrayList<>();

	public GuiNecronomiconSpellEntry(int bookType, GuiNecronomicon gui){
		super(bookType);
		parent = gui;
		isInfo = true;
	}

	@Override
	public GuiNecronomicon withBookType(int par1){
		if(getBookType() != par1) {
			currTurnup = 0;
			isInvalid = true;
		}
		return super.withBookType(par1);
	}

	@Override
	public void initGui(){
		if(isInvalid)
			spells.clear();
		if(spells.isEmpty())
			initStuff();
		initCommon();
	}

	@Override
	protected void updateButtonsInner() {

		scrolls.clear();
		scrolls = SpellRegistry.instance().getScrolls(spells.get(currTurnup).getScrollType());
	}

	@Override
	protected void drawInformationText(int x, int y){
		drawPage(spells.get(currTurnup), x, y);
	}

	private void drawPage(Spell spell, int x, int y){
		int k = (width - guiWidth) / 2;
		//		byte b0 = 2;
		drawTitle(spell.getLocalizedName());

		writeText(1, localize(NecronomiconText.LABEL_SPELL_PE)+": " + spell.getReqEnergy() + " PE", 125);
		writeText(1, localize(NecronomiconText.LABEL_SPELL_TYPE)+": "+ localize(NecronomiconText.getSpellType(spell.requiresCharging())), 135);
		writeText(2, spell.getDescription());
		drawTexture(NecronomiconResources.SPELL);

		getHelper().clearTooltipStack();

		ItemStack[] offerings = new ItemStack[5];
		if(spell.getReagents().length < 5)
			for(int i = 0; i < spell.getReagents().length; i++)
				offerings[i] = APIUtils.convertToStack(spell.getReagents()[i]);
		else offerings = getStacks(spell.getReagents());

		renderItem(k + 58, 43, offerings[0], x, y);
		renderItem(k + 83, 64, offerings[1], x, y);
		renderItem(k + 72, 93, offerings[2], x, y);
		renderItem(k + 45, 93, offerings[3], x, y);
		renderItem(k + 33, 64, offerings[4], x, y);
		//center
		if(!spell.getParchment().isEmpty())
			renderItem(k + 58, 68, spell.getParchment(), x, y);
		else renderObject(k + 58, 68, scrolls, x, y);

		renderTooltip(x, y);
	}

	private ItemStack[] getStacks(Object[] objects){
		ItemStack[] stacks = new ItemStack[objects.length];
		for(int i = 0; i < objects.length; i++)
			stacks[i] = APIUtils.convertToStack(objects[i]);
		return stacks;
	}
	private void initStuff(){
		for(Spell spell : SpellRegistry.instance().getSpells())
			if(isUnlocked(spell.getResearchItem(spell)) && spell.getBookType() <= getKnowledgeLevel())
				spells.add(spell);
		setTurnupLimit(spells.size());
	}
}
