/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Tuple;

import org.lwjgl.input.Keyboard;
import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.PageData;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

public class GuiNecronomiconRituals extends GuiNecronomicon {

	private ButtonNextPage buttonPreviousPage;
	private GuiButton buttonDone;
	private ButtonCategory info, ritual0, ritual1, ritual2, ritual3, ritual4;

	public GuiNecronomiconRituals(int bookType){
		super(bookType);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonPreviousPage = new ButtonNextPage(1, i + 18, b0 + 154, false));
		buttonList.add(info = new ButtonCategory(2, i + 10, b0 + 20, this, NecronomiconText.LABEL_INFO, AbyssalCraft.necronomicon));
		buttonList.add(ritual0 = new ButtonCategory(3, i + 10, b0 + 37, this, NecronomiconText.LABEL_NORMAL, hasRituals(0) ? AbyssalCraft.necronomicon : AbyssalCraft.OC));
		if(getBookType() >= 1)
			buttonList.add(ritual1 = new ButtonCategory(4, i + 10, b0 + 54, this, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND, hasRituals(1) ? AbyssalCraft.necronomicon_cor : AbyssalCraft.OC));
		if(getBookType() >= 2)
			buttonList.add(ritual2 = new ButtonCategory(5, i + 10, b0 + 71, this, NecronomiconText.LABEL_INFORMATION_DREADLANDS, hasRituals(2) ? AbyssalCraft.necronomicon_dre : AbyssalCraft.OC));
		if(getBookType() >= 3)
			buttonList.add(ritual3 = new ButtonCategory(6, i + 10, b0 + 88, this, NecronomiconText.LABEL_INFORMATION_OMOTHOL, hasRituals(3) ? AbyssalCraft.necronomicon_omt : AbyssalCraft.OC));
		if(getBookType() == 4)
			buttonList.add(ritual4 = new ButtonCategory(7, i + 10, b0 + 105, this, StatCollector.translateToLocal(AbyssalCraft.abyssalnomicon.getUnlocalizedName() + ".name"), hasRituals(4) ? AbyssalCraft.abyssalnomicon : AbyssalCraft.OC));
		updateButtons();
	}

	private void updateButtons()
	{
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		info.visible = true;
		ritual0.visible = true;
		if(getBookType() >= 1)
			ritual1.visible = true;
		if(getBookType() >= 2)
			ritual2.visible = true;
		if(getBookType() >= 3)
			ritual3.visible = true;
		if(getBookType() == 4)
			ritual4.visible = true;

	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			if(button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1)
				mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
			else if(button.id == 2){
				Object[] materials = new Object[]{new Tuple(new ItemStack(AbyssalCraft.ritualaltar), new ItemStack(AbyssalCraft.ritualpedestal)), new ItemStack(AbyssalCraft.monolithStone)};
				CraftingStack ep = new CraftingStack(AbyssalCraft.energyPedestal, AbyssalCraft.monolithStone, AbyssalCraft.Cpearl, AbyssalCraft.monolithStone, AbyssalCraft.monolithStone,
						AbyssalCraft.shadowgem, AbyssalCraft.monolithStone, AbyssalCraft.monolithStone, AbyssalCraft.monolithStone, AbyssalCraft.monolithStone);
				CraftingStack msp = new CraftingStack(AbyssalCraft.monolithPillar, AbyssalCraft.monolithStone, AbyssalCraft.monolithStone, null, AbyssalCraft.monolithStone,
						AbyssalCraft.monolithStone, null, null, null, null);
				CraftingStack rc = new CraftingStack(new ItemStack(AbyssalCraft.charm, 1, 0), Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.diamond,
						Items.gold_ingot, Items.gold_ingot, Items.gold_ingot, Items.gold_ingot);
				CraftingStack sa = new CraftingStack(AbyssalCraft.sacrificialAltar, Blocks.torch, AbyssalCraft.Cpearl, Blocks.torch, AbyssalCraft.monolithStone, AbyssalCraft.shadowgem,
						AbyssalCraft.monolithStone, AbyssalCraft.monolithStone, AbyssalCraft.monolithStone, AbyssalCraft.monolithStone);
				Object[] recipes = new Object[] {ep, new Tuple(msp, rc), sa};
				Object[] tuples = new Object[]{NecronomiconResources.RITUAL_TUT_1, new Tuple(NecronomiconResources.RITUAL_TUT_2, NecronomiconResources.BLANK),
						NecronomiconResources.RITUAL_TUT_3};
				PageData test1 = new PageData(3, NecronomiconText.LABEL_GETTING_STARTED, tuples, NecronomiconText.RITUAL_TUT);
				PageData test2 = new PageData(2, NecronomiconText.LABEL_INFORMATION_MATERIALS, materials, NecronomiconText.RITUAL_MATERIALS);
				PageData test3 = new PageData(3, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, recipes, NecronomiconText.RITUAL_CRAFTING);
				Object[] pictures = new Object[] {new Tuple(null, NecronomiconResources.PE_TUT_1), new Tuple(NecronomiconResources.PE_TUT_2, NecronomiconResources.PE_TUT_3),
						new Tuple(null, NecronomiconResources.PE_TUT_4), new Tuple(NecronomiconResources.PE_TUT_5, NecronomiconResources.PE_TUT_6), NecronomiconResources.PE_TUT_7};
				PageData test4 = new PageData(5, NecronomiconText.LABEL_POTENTIAL_ENERGY, pictures, NecronomiconText.PE_TUT);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFO, test1, test2, test3, test4);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconRituals(getBookType()), AbyssalCraft.necronomicon));
			} else if(button.id == 3){
				if(hasRituals(0))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 0));
			} else if(button.id == 4){
				if(hasRituals(1))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 1));
			} else if(button.id == 5){
				if(hasRituals(2))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 2));
			} else if(button.id == 6){
				if(hasRituals(3))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 3));
			} else if(button.id == 7)
				if(hasRituals(4))
					mc.displayGuiScreen(new GuiNecronomiconRitualEntry(getBookType(), this, 4));

			updateButtons();
		}
	}

	private boolean hasRituals(int book){

		List<NecronomiconRitual> rituals = Lists.newArrayList();
		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual.getBookType() == book)
				rituals.add(ritual);

		return !rituals.isEmpty();
	}

	@Override
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = NecronomiconText.LABEL_RITUALS;
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		writeText(2, NecronomiconText.RITUAL_INFO);
	}
}
