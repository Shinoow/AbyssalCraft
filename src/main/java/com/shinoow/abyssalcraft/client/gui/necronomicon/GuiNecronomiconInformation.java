/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.gui.necronomicon;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.PageData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.PageData.PageType;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

public class GuiNecronomiconInformation extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private ButtonCategory buttonCat1;
	private ButtonCategory buttonCat2;
	private ButtonCategory buttonCat3;
	private ButtonCategory buttonCat4;
	private ButtonCategory buttonCat5;
	private ButtonCategory buttonCat6;
	private ButtonCategory buttonCat7;
	private ButtonCategory buttonCat8;
	private ButtonCategory buttonCat9;
	private GuiButton buttonDone;
	private boolean isAC = false;
	private boolean isAN = false;

	public GuiNecronomiconInformation(int bookType){
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
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
		buttonList.add(buttonCat1 = new ButtonCategory(3, i + 10, b0 + 30, this, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, AbyssalCraft.necronomicon));
		buttonList.add(buttonCat2 = new ButtonCategory(4, i + 10, b0 + 55, this, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, AbyssalCraft.necronomicon));
		buttonList.add(buttonCat3 = new ButtonCategory(5, i + 10, b0 + 80, this, NecronomiconText.LABEL_INFORMATION_ABYSSALNOMICON, AbyssalCraft.abyssalnomicon));
		buttonList.add(buttonCat4 = new ButtonCategory(6, i + 10, b0 + 105, this, NecronomiconText.LABEL_INFORMATION_INTEGRATION, AbyssalCraft.necronomicon));
		buttonList.add(buttonCat5 = new ButtonCategory(7, i + 10, b0 + 130, this, NecronomiconText.LABEL_INFORMATION_OVERWORLD, AbyssalCraft.necronomicon));
		if(getBookType() >= 1)
			buttonList.add(buttonCat6 = new ButtonCategory(8, i + 130, b0 + 30, this, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND, AbyssalCraft.necronomicon_cor));
		if(getBookType() >= 2)
			buttonList.add(buttonCat7 = new ButtonCategory(9, i + 130, b0 + 55, this, NecronomiconText.LABEL_INFORMATION_DREADLANDS, AbyssalCraft.necronomicon_dre));
		if(getBookType() >= 3)
			buttonList.add(buttonCat8 = new ButtonCategory(10, i + 130, b0 + 80, this, NecronomiconText.LABEL_INFORMATION_OMOTHOL, AbyssalCraft.necronomicon_omt));
		if(getBookType() == 4)
			buttonList.add(buttonCat9 = new ButtonCategory(11, i + 130, b0 + 105, this, NecronomiconText.LABEL_INFORMATION_DARK_REALM, AbyssalCraft.necronomicon_omt));

		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo && !isAN;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		buttonCat1.visible = true;
		buttonCat2.visible = true;
		buttonCat3.visible = true;
		buttonCat4.visible = true;
		buttonCat5.visible = true;
		if(getBookType() >= 1)
			buttonCat6.visible = true;
		if(getBookType() >= 2)
			buttonCat7.visible = true;
		if(getBookType() >= 3)
			buttonCat8.visible = true;
		if(getBookType() == 4)
			buttonCat9.visible = true;

	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1){
				if (currTurnup < getTurnupLimit() -1)
					++currTurnup;
			} else if (button.id == 2){
				if(currTurnup == 0 && !isInfo)
					mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
				else if(currTurnup == 0 && isInfo){
					initGui();
					isInfo = isAC = isAN = false;
					setTurnupLimit(2);
				} else if (currTurnup > 0)
					--currTurnup;
			} else if(button.id == 3){
				isInfo = true;
				isAC = true;
				drawButtons();
			} else if(button.id == 4){
				PageData og = new PageData(4, NecronomiconText.LABEL_OUTER_GODS, PageType.INFO, NecronomiconResources.OUTER_GODS, NecronomiconText.OUTER_GODS);
				PageData goo = new PageData(3, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, PageType.INFO, NecronomiconResources.GREAT_OLD_ONES, NecronomiconText.GREAT_OLD_ONES);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, NecronomiconText.INFORMATION_GREAT_OLD_ONES, og, goo);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon));
			} else if(button.id == 5){
				isInfo = true;
				isAN = true;
				drawButtons();
			} else if(button.id == 6){
				ItemStack[] neistuff = {new ItemStack(AbyssalCraft.transmutator), new ItemStack(AbyssalCraft.crystallizer), new ItemStack(AbyssalCraft.engraver), new ItemStack(AbyssalCraft.materializer)};
				PageData nei = new PageData(4, NecronomiconText.LABEL_INTEGRATION_NEI, PageType.ENTRY, neistuff, NecronomiconText.NEI_INTEGRATION);
				PageData tc = new PageData(4, NecronomiconText.LABEL_INTEGRATION_TC, NecronomiconText.TC_INTEGRATION);
				PageData morph = new PageData(1, NecronomiconText.LABEL_INTEGRATION_MORPH, NecronomiconText.INTEGRATION_MORPH_1, NecronomiconText.INTEGRATION_MORPH_2);
				PageData invtweaks = new PageData(1, NecronomiconText.LABEL_INTEGRATION_INVTWEAKS, NecronomiconText.INTEGRATION_INVTWEAKS_1);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_INTEGRATION, NecronomiconText.INFORMATION_INTEGRATION, nei, tc, morph, invtweaks);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon));
			} else if(button.id == 7){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.abyore), new ItemStack(AbyssalCraft.Darkstone), new ItemStack(AbyssalCraft.Coraliumore),
						new ItemStack(AbyssalCraft.DLTSapling), new ItemStack(AbyssalCraft.nitreOre), new ItemStack(AbyssalCraft.anticwater),
						new ItemStack(AbyssalCraft.Darkgrass)};
				CraftingStack cis = new CraftingStack(AbyssalCraft.CoraliumInfusedStone, Blocks.stone, Blocks.stone, Blocks.stone, AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coraliumcluster3,
						AbyssalCraft.Coraliumcluster3, Blocks.stone, Blocks.stone, Blocks.stone);
				CraftingStack sg = new CraftingStack(AbyssalCraft.shadowgem, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard,
						AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard);
				CraftingStack soo = new CraftingStack(AbyssalCraft.oblivionshard, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem,
						AbyssalCraft.Corb, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem);
				CraftingStack gk = new CraftingStack(AbyssalCraft.portalPlacer, null, AbyssalCraft.Cpearl, AbyssalCraft.OC, null, Items.blaze_rod, AbyssalCraft.Cpearl, Items.blaze_rod, null, null);
				CraftingStack awn = new CraftingStack(AbyssalCraft.necronomicon_cor, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.necronomicon,
						AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh);
				CraftingStack[] recipes = CraftingStack.arrayFrom(cis, sg, soo, gk, awn);
				PageData test1 = new PageData(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, PageType.ENTRY, materials, NecronomiconText.OVERWORLD_MATERIALS);
				PageData test2 = new PageData(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.OVERWORLD_PROGRESSION);
				PageData test3 = new PageData(7, NecronomiconText.LABEL_INFORMATION_ENTITIES, PageType.INFO, NecronomiconResources.OVERWORLD_ENTITIES, NecronomiconText.OVERWORLD_ENTITIES);
				PageData test4 = new PageData(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, PageType.CRAFTING, recipes, NecronomiconText.OVERWORLD_CRAFTING);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, NecronomiconText.INFORMATION_OVERWORLD, test1, test2, test3, test4);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon));
			} else if(button.id == 8){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.abystone), new ItemStack(AbyssalCraft.AbyDiaOre), new ItemStack(AbyssalCraft.AbyCorOre),
						new ItemStack(AbyssalCraft.AbyLCorOre), new ItemStack(AbyssalCraft.AbyPCorOre), new ItemStack(AbyssalCraft.Cwater), new ItemStack(AbyssalCraft.PSDL)};
				CraftingStack altar = new CraftingStack(AbyssalCraft.Altar, null, Items.bucket, null, Items.gold_ingot, AbyssalCraft.Corb, Items.gold_ingot, Items.gold_ingot, Blocks.enchanting_table,
						Items.gold_ingot);
				CraftingStack psdlf = new CraftingStack(AbyssalCraft.PSDLfinder, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, Items.ender_eye,
						AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
				CraftingStack gk = new CraftingStack(AbyssalCraft.portalPlacerDL, null, AbyssalCraft.Corb, null, AbyssalCraft.EoA, AbyssalCraft.portalPlacer, AbyssalCraft.PSDL, null, null, null);
				CraftingStack dln = new CraftingStack(AbyssalCraft.necronomicon_dre, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment,
						AbyssalCraft.necronomicon_cor, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment);
				CraftingStack[] recipes = CraftingStack.arrayFrom(altar, psdlf, gk, dln);
				PageData test1 = new PageData(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, PageType.ENTRY, materials, NecronomiconText.ABYSSAL_WASTELAND_MATERIALS);
				PageData test2 = new PageData(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.ABYSSAL_WASTELAND_PROGRESSION);
				PageData test3 = new PageData(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, PageType.INFO, NecronomiconResources.ABYSSAL_WASTELAND_ENTITIES, NecronomiconText.ABYSSAL_WASTELAND_ENTITIES);
				PageData test4 = new PageData(4, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, PageType.CRAFTING, recipes, NecronomiconText.ABYSSAL_WASTELAND_CRAFTING);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, NecronomiconText.INFORMATION_ABYSSAL_WASTELAND, test1, test2, test3, test4);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon_cor));
			} else if(button.id == 9){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.dreadstone), new ItemStack(AbyssalCraft.abydreadstone), new ItemStack(AbyssalCraft.abydreadore),
						new ItemStack(AbyssalCraft.dreadore), new ItemStack(AbyssalCraft.dreadgrass), new ItemStack(AbyssalCraft.dreadsapling)};
				CraftingStack trans = new CraftingStack(AbyssalCraft.transmutator_on, AbyssalCraft.cbrick, AbyssalCraft.cbrick, AbyssalCraft.cbrick, AbyssalCraft.cbrick, AbyssalCraft.Corb,
						AbyssalCraft.cbrick, AbyssalCraft.corblock, AbyssalCraft.Cbucket, AbyssalCraft.corblock);
				CraftingStack cryst = new CraftingStack(AbyssalCraft.crystallizer_on, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadiumblock,
						Blocks.furnace, AbyssalCraft.dreadiumblock, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick);
				CraftingStack daltarb = new CraftingStack(AbyssalCraft.dreadaltarbottom, Items.bone, AbyssalCraft.dreadcloth, Items.bone, AbyssalCraft.dreadiumingot, AbyssalCraft.portalPlacerDL,
						AbyssalCraft.dreadiumingot, AbyssalCraft.dreadstone, AbyssalCraft.Dreadshard, AbyssalCraft.dreadstone);
				CraftingStack daltart = new CraftingStack(AbyssalCraft.dreadaltartop, Items.stick, Items.bucket, Items.stick, AbyssalCraft.dreadcloth, AbyssalCraft.dreadcloth, AbyssalCraft.dreadcloth,
						AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot);
				CraftingStack omn = new CraftingStack(AbyssalCraft.necronomicon_omt, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh,
						AbyssalCraft.necronomicon_dre, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh);
				CraftingStack[] recipes = CraftingStack.arrayFrom(trans, cryst, daltarb, daltart, omn);
				PageData test1 = new PageData(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, PageType.ENTRY, materials, NecronomiconText.DREADLANDS_MATERIALS);
				PageData test2 = new PageData(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.DREADLANDS_PROGRESSION);
				PageData test3 = new PageData(9, NecronomiconText.LABEL_INFORMATION_ENTITIES, PageType.INFO, NecronomiconResources.DREADLANDS_ENTITIES, NecronomiconText.DREADLANDS_ENTITIES);
				PageData test4 = new PageData(5, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, PageType.CRAFTING, recipes, NecronomiconText.DREADLANDS_CRAFTING);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, NecronomiconText.INFORMATION_DREADLANDS, test1, test2, test3, test4);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon_dre));
			} else if(button.id == 10){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.omotholstone), new ItemStack(AbyssalCraft.ethaxium), new ItemStack(AbyssalCraft.darkethaxiumbrick)};
				CraftingStack lc = new CraftingStack(AbyssalCraft.lifeCrystal, AbyssalCraft.crystalCarbon, AbyssalCraft.crystalHydrogen, AbyssalCraft.crystalNitrogen, AbyssalCraft.crystalOxygen,
						AbyssalCraft.crystalPhosphorus, AbyssalCraft.crystalSulfur, null, null, null);
				CraftingStack ei = new CraftingStack(AbyssalCraft.ethaxiumIngot, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick,
						AbyssalCraft.lifeCrystal, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick);
				CraftingStack enb = new CraftingStack(AbyssalCraft.engravingBlank, new ItemStack(Blocks.stone_slab, 1, 0), new ItemStack(Blocks.stone_slab, 1, 0), new ItemStack(Blocks.stone_slab, 1, 0),
						new ItemStack(Blocks.stone_slab, 1, 0), Items.iron_ingot, new ItemStack(Blocks.stone_slab, 1, 0), new ItemStack(Blocks.stone_slab, 1, 0), new ItemStack(Blocks.stone_slab, 1, 0),
						new ItemStack(Blocks.stone_slab, 1, 0));
				CraftingStack coin = new CraftingStack(AbyssalCraft.coin, null, Items.iron_ingot, null, Items.iron_ingot, Items.flint, Items.iron_ingot, null, Items.iron_ingot, null);
				CraftingStack engra = new CraftingStack(AbyssalCraft.engraver, AbyssalCraft.engravingBlank, Blocks.stone, null, AbyssalCraft.engravingBlank, Blocks.stone, Blocks.lever,
						Blocks.anvil, Blocks.stone, null);
				CraftingStack cb = new CraftingStack(AbyssalCraft.crystalbag_s, Items.string, Items.leather, Items.string, Items.leather, Items.gold_ingot, Items.leather, Items.leather,
						Items.leather, Items.leather);
				CraftingStack mater = new CraftingStack(AbyssalCraft.materializer, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxium_brick,
						Blocks.obsidian, AbyssalCraft.ethaxium_brick, AbyssalCraft.ethaxiumblock, AbyssalCraft.antibucket, AbyssalCraft.ethaxiumblock);
				CraftingStack an = new CraftingStack(AbyssalCraft.abyssalnomicon, AbyssalCraft.ethaxiumIngot, AbyssalCraft.OC, AbyssalCraft.ethaxiumIngot, AbyssalCraft.eldritchScale,
						AbyssalCraft.necronomicon_omt, AbyssalCraft.eldritchScale, AbyssalCraft.ethaxiumIngot, AbyssalCraft.eldritchScale, AbyssalCraft.ethaxiumIngot);
				CraftingStack[] recipes = CraftingStack.arrayFrom(lc, ei, enb, coin, engra, cb, mater, an);
				PageData test1 = new PageData(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, PageType.ENTRY, materials, NecronomiconText.OMOTHOL_MATERIALS);
				PageData test2 = new PageData(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.OMOTHOL_PROGRESSION);
				PageData test3 = new PageData(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, PageType.INFO, NecronomiconResources.OMOTHOL_ENTITIES, NecronomiconText.OMOTHOL_ENTITIES);
				PageData test4 = new PageData(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, PageType.CRAFTING, recipes, NecronomiconText.OMOTHOL_CRAFTING);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, NecronomiconText.INFORMATION_OMOTHOL, test1, test2, test3, test4);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon_omt));
			} else if(button.id == 11){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.Darkstone)};
				PageData test1 = new PageData(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, PageType.ENTRY, materials, NecronomiconText.DARK_REALM_MATERIALS);
				PageData test2 = new PageData(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.DARK_REALM_PROGRESSION);
				PageData test3 = new PageData(5, NecronomiconText.LABEL_INFORMATION_ENTITIES, PageType.INFO, NecronomiconResources.DARK_REALM_ENTITIES, NecronomiconText.DARK_REALM_ENTITIES);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_DARK_REALM_TITLE, NecronomiconText.INFORMATION_DARK_REALM, test1, test2, test3);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon_omt));
			}
			updateButtons();
		}
	}

	@SuppressWarnings("unchecked")
	private void drawButtons(){
		buttonList.clear();
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
	}

	@Override
	protected void drawInformationText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		if(isAC){
			stuff = StatCollector.translateToLocal(NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT);
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			if(currTurnup == 0){
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_1);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_2);
			} else if(currTurnup == 1){
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_3);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_4);
			}
		} else if(isAN){
			stuff = StatCollector.translateToLocal(NecronomiconText.LABEL_INFORMATION_ABYSSALNOMICON);
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			setTurnupLimit(1);
			writeText(1, NecronomiconText.INFORMATION_ABYSSALNOMICON);
		}
	}
}