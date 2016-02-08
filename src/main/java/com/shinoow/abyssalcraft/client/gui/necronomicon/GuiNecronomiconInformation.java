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

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.PageData;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

public class GuiNecronomiconInformation extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage, buttonPreviousPage;
	private ButtonCategory buttonCat1, buttonCat2, buttonCat3, buttonCat4,
	buttonCat5, buttonCat6, buttonCat7, buttonCat8, buttonCat9, buttonCat10;
	private GuiButton buttonDone;
	private boolean isAC = false;
	private boolean isAN = false;
	private boolean isP = false;

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
		buttonList.add(buttonCat1 = new ButtonCategory(3, i + 10, b0 + 20, this, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, AbyssalCraft.necronomicon));
		buttonList.add(buttonCat2 = new ButtonCategory(4, i + 10, b0 + 37, this, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, AbyssalCraft.necronomicon));
		buttonList.add(buttonCat3 = new ButtonCategory(5, i + 10, b0 + 54, this, NecronomiconText.LABEL_INFORMATION_ABYSSALNOMICON, AbyssalCraft.abyssalnomicon));
		buttonList.add(buttonCat4 = new ButtonCategory(6, i + 10, b0 + 71, this, NecronomiconText.LABEL_PATRONS, AbyssalCraft.necronomicon));
		buttonList.add(buttonCat5 = new ButtonCategory(7, i + 10, b0 + 88, this, NecronomiconText.LABEL_INFORMATION_MACHINES, getBookType() >= 1 ? AbyssalCraft.necronomicon : AbyssalCraft.OC));
		buttonList.add(buttonCat6 = new ButtonCategory(8, i + 10, b0 + 105, this, NecronomiconText.LABEL_INFORMATION_OVERWORLD, AbyssalCraft.necronomicon));
		if(getBookType() >= 1)
			buttonList.add(buttonCat7 = new ButtonCategory(9, i + 10, b0 + 122, this, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND, AbyssalCraft.necronomicon_cor));
		if(getBookType() >= 2)
			buttonList.add(buttonCat8 = new ButtonCategory(10, i + 130, b0 + 20, this, NecronomiconText.LABEL_INFORMATION_DREADLANDS, AbyssalCraft.necronomicon_dre));
		if(getBookType() >= 3)
			buttonList.add(buttonCat9 = new ButtonCategory(11, i + 130, b0 + 37, this, NecronomiconText.LABEL_INFORMATION_OMOTHOL, AbyssalCraft.necronomicon_omt));
		if(getBookType() == 4)
			buttonList.add(buttonCat10 = new ButtonCategory(12, i + 130, b0 + 54, this, NecronomiconText.LABEL_INFORMATION_DARK_REALM, AbyssalCraft.necronomicon_omt));

		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo && !isAN && !isP;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		buttonCat1.visible = true;
		buttonCat2.visible = true;
		buttonCat3.visible = true;
		buttonCat4.visible = true;
		buttonCat5.visible = true;
		buttonCat6.visible = true;
		if(getBookType() >= 1)
			buttonCat7.visible = true;
		if(getBookType() >= 2)
			buttonCat8.visible = true;
		if(getBookType() >= 3)
			buttonCat9.visible = true;
		if(getBookType() == 4)
			buttonCat10.visible = true;

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
					isInfo = isAC = isAN = isP = false;
					setTurnupLimit(2);
				} else if (currTurnup > 0)
					--currTurnup;
			} else if(button.id == 3){
				isInfo = true;
				isAC = true;
				drawButtons();
			} else if(button.id == 4){
				PageData og = new PageData(4, NecronomiconText.LABEL_OUTER_GODS, NecronomiconResources.OUTER_GODS, NecronomiconText.OUTER_GODS);
				PageData goo = new PageData(3, NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, NecronomiconResources.GREAT_OLD_ONES, NecronomiconText.GREAT_OLD_ONES);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_GREAT_OLD_ONES, NecronomiconText.INFORMATION_GREAT_OLD_ONES, og, goo);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon));
			} else if(button.id == 5){
				isInfo = true;
				isAN = true;
				drawButtons();
			} else if(button.id == 6){
				isInfo = true;
				isP = true;
				drawButtons();
			} else if(button.id == 7){
				if(getBookType() >= 1)
					mc.displayGuiScreen(new GuiNecronomiconMachines(getBookType()));
			} else if(button.id == 8){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.abyore), new ItemStack(AbyssalCraft.Darkstone), new ItemStack(AbyssalCraft.Coraliumore),
						new ItemStack(AbyssalCraft.DLTSapling), new ItemStack(AbyssalCraft.nitreOre), new ItemStack(AbyssalCraft.anticwater),
						new ItemStack(AbyssalCraft.Darkgrass)};
				CraftingStack cis = new CraftingStack(AbyssalCraft.CoraliumInfusedStone, Blocks.stone, Blocks.stone, Blocks.stone, AbyssalCraft.Coraliumcluster3, AbyssalCraft.Coraliumcluster3,
						AbyssalCraft.Coraliumcluster3, Blocks.stone, Blocks.stone, Blocks.stone);
				CraftingStack sg = new CraftingStack(AbyssalCraft.shadowgem, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard,
						AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard);
				CraftingStack soo = new CraftingStack(AbyssalCraft.oblivionshard, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem,
						AbyssalCraft.Corb, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem, AbyssalCraft.shadowgem);
				CraftingStack gk = new CraftingStack(AbyssalCraft.gatewayKey, null, AbyssalCraft.Cpearl, AbyssalCraft.OC, null, Items.blaze_rod, AbyssalCraft.Cpearl, Items.blaze_rod, null, null);
				CraftingStack aws = new CraftingStack(new ItemStack(AbyssalCraft.skin, 1, 0), AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh,
						new ItemStack(AbyssalCraft.essence, 1, 0), AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh, AbyssalCraft.Corflesh);
				CraftingStack awn = new CraftingStack(AbyssalCraft.necronomicon_cor, new ItemStack(AbyssalCraft.skin, 1, 0), new ItemStack(AbyssalCraft.skin, 1, 0), new ItemStack(AbyssalCraft.skin, 1, 0),
						new ItemStack(AbyssalCraft.skin, 1, 0), AbyssalCraft.necronomicon, new ItemStack(AbyssalCraft.skin, 1, 0), new ItemStack(AbyssalCraft.skin, 1, 0), new ItemStack(AbyssalCraft.skin, 1, 0),
						new ItemStack(AbyssalCraft.skin, 1, 0));
				CraftingStack[] recipes = CraftingStack.arrayFrom(cis, sg, soo, gk, aws, awn);
				CraftingStack[] special = {new CraftingStack(AbyssalCraft.drainStaff, null, AbyssalCraft.shadowshard, AbyssalCraft.oblivionshard, null, AbyssalCraft.shadowshard, AbyssalCraft.shadowshard,
						AbyssalCraft.shadowshard, null, null)};
				PageData test1 = new PageData(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, materials, NecronomiconText.OVERWORLD_MATERIALS);
				PageData test2 = new PageData(3, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.OVERWORLD_PROGRESSION);
				PageData test3 = new PageData(8, NecronomiconText.LABEL_INFORMATION_ENTITIES, NecronomiconResources.OVERWORLD_ENTITIES, NecronomiconText.OVERWORLD_ENTITIES);
				PageData test4 = new PageData(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, recipes, NecronomiconText.OVERWORLD_CRAFTING);
				PageData test5 = new PageData(1, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, special, NecronomiconText.OVERWORLD_ARMOR_TOOLS);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_OVERWORLD_TITLE, NecronomiconText.INFORMATION_OVERWORLD, test1, test2, test3, test4, test5);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon));
			} else if(button.id == 9){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.abystone), new ItemStack(AbyssalCraft.AbyDiaOre), new ItemStack(AbyssalCraft.AbyCorOre),
						new ItemStack(AbyssalCraft.AbyLCorOre), new ItemStack(AbyssalCraft.AbyPCorOre), new ItemStack(AbyssalCraft.Cwater), new ItemStack(AbyssalCraft.PSDL)};
				CraftingStack psdlf = new CraftingStack(new ItemStack(AbyssalCraft.PSDLfinder, 4), AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, Items.ender_eye,
						AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium, AbyssalCraft.Coralium);
				CraftingStack trans = new CraftingStack(AbyssalCraft.transmutator, AbyssalCraft.cbrick, AbyssalCraft.cbrick, AbyssalCraft.cbrick, AbyssalCraft.cbrick, AbyssalCraft.Corb,
						AbyssalCraft.cbrick, AbyssalCraft.corblock, AbyssalCraft.Cbucket, AbyssalCraft.corblock);
				CraftingStack cc = new CraftingStack(AbyssalCraft.Cchunk, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Coraliumcluster9,
						AbyssalCraft.abystone, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Coraliumcluster9, AbyssalCraft.Coraliumcluster9);
				CraftingStack cp = new CraftingStack(AbyssalCraft.Cplate, AbyssalCraft.Cingot, AbyssalCraft.Cpearl, AbyssalCraft.Cingot, AbyssalCraft.Cingot, AbyssalCraft.Cpearl, AbyssalCraft.Cingot,
						AbyssalCraft.Cingot, AbyssalCraft.Cpearl, AbyssalCraft.Cingot);
				CraftingStack dls = new CraftingStack(new ItemStack(AbyssalCraft.skin, 1, 1), AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment,
						new ItemStack(AbyssalCraft.essence, 1, 1), AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment, AbyssalCraft.dreadfragment);
				CraftingStack dln = new CraftingStack(AbyssalCraft.necronomicon_dre, new ItemStack(AbyssalCraft.skin, 1, 1), new ItemStack(AbyssalCraft.skin, 1, 1), new ItemStack(AbyssalCraft.skin, 1, 1),
						new ItemStack(AbyssalCraft.skin, 1, 1), AbyssalCraft.necronomicon_cor, new ItemStack(AbyssalCraft.skin, 1, 1), new ItemStack(AbyssalCraft.skin, 1, 1), new ItemStack(AbyssalCraft.skin, 1, 1),
						new ItemStack(AbyssalCraft.skin, 1, 1));
				CraftingStack[] recipes = CraftingStack.arrayFrom(psdlf, trans, cc, cp, dls, dln);
				CraftingStack pch = new CraftingStack(AbyssalCraft.CorhelmetP, AbyssalCraft.Cpearl, AbyssalCraft.Cplate, AbyssalCraft.Cpearl, AbyssalCraft.Cplate, AbyssalCraft.Corhelmet, AbyssalCraft.Cplate,
						AbyssalCraft.Cingot, AbyssalCraft.Cingot, AbyssalCraft.Cingot);
				CraftingStack pcp = new CraftingStack(AbyssalCraft.CorplateP, AbyssalCraft.Cplate, null, AbyssalCraft.Cplate, AbyssalCraft.Cingot, AbyssalCraft.Corplate, AbyssalCraft.Cingot,
						AbyssalCraft.Cingot, AbyssalCraft.Cplate, AbyssalCraft.Cingot);
				CraftingStack pcl = new CraftingStack(AbyssalCraft.CorlegsP, AbyssalCraft.Cplate, AbyssalCraft.Corlegs, AbyssalCraft.Cplate, AbyssalCraft.Cingot, null, AbyssalCraft.Cingot,
						AbyssalCraft.Cingot, null, AbyssalCraft.Cingot);
				CraftingStack pcb = new CraftingStack(AbyssalCraft.CorbootsP, AbyssalCraft.Cingot, null, AbyssalCraft.Cingot, AbyssalCraft.Cplate, AbyssalCraft.Corboots, AbyssalCraft.Cplate, null, null, null);
				CraftingStack clb = new CraftingStack(AbyssalCraft.corbow, null, AbyssalCraft.Cingot, Items.string, AbyssalCraft.Cpearl, Items.bow, Items.string, null, AbyssalCraft.Cingot, Items.string);
				CraftingStack[] special = CraftingStack.arrayFrom(pch, pcp, pcl, pcb, clb);
				PageData test1 = new PageData(7, NecronomiconText.LABEL_INFORMATION_MATERIALS, materials, NecronomiconText.ABYSSAL_WASTELAND_MATERIALS);
				PageData test2 = new PageData(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.ABYSSAL_WASTELAND_PROGRESSION);
				PageData test3 = new PageData(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, NecronomiconResources.ABYSSAL_WASTELAND_ENTITIES, NecronomiconText.ABYSSAL_WASTELAND_ENTITIES);
				PageData test4 = new PageData(6, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, recipes, NecronomiconText.ABYSSAL_WASTELAND_CRAFTING);
				PageData test5 = new PageData(5, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, special, NecronomiconText.ABYSSAL_WASTELAND_ARMOR_TOOLS);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND_TITLE, NecronomiconText.INFORMATION_ABYSSAL_WASTELAND, test1, test2, test3, test4, test5);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon_cor));
			} else if(button.id == 10){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.dreadstone), new ItemStack(AbyssalCraft.abydreadstone), new ItemStack(AbyssalCraft.abydreadore),
						new ItemStack(AbyssalCraft.dreadore), new ItemStack(AbyssalCraft.dreadgrass), new ItemStack(AbyssalCraft.dreadsapling)};
				CraftingStack dreadium = new CraftingStack(AbyssalCraft.dreadiumblock, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot,
						AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot);
				CraftingStack cryst = new CraftingStack(AbyssalCraft.crystallizer, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadiumblock,
						Blocks.furnace, AbyssalCraft.dreadiumblock, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick, AbyssalCraft.dreadbrick);
				CraftingStack drc = new CraftingStack(AbyssalCraft.dreadcloth, Items.string, AbyssalCraft.dreadfragment, Items.string, AbyssalCraft.dreadfragment, Items.leather, AbyssalCraft.dreadfragment,
						Items.string, AbyssalCraft.dreadfragment, Items.string);
				CraftingStack daltarb = new CraftingStack(AbyssalCraft.dreadaltarbottom, Items.bone, AbyssalCraft.dreadcloth, Items.bone, AbyssalCraft.dreadiumingot, AbyssalCraft.gatewayKeyDL,
						AbyssalCraft.dreadiumingot, AbyssalCraft.dreadstone, AbyssalCraft.Dreadshard, AbyssalCraft.dreadstone);
				CraftingStack daltart = new CraftingStack(AbyssalCraft.dreadaltartop, Items.stick, Items.bucket, Items.stick, AbyssalCraft.dreadcloth, AbyssalCraft.dreadcloth, AbyssalCraft.dreadcloth,
						AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot);
				CraftingStack dp = new CraftingStack(AbyssalCraft.dreadplate, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot,
						AbyssalCraft.dreadcloth, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot);
				CraftingStack dh = new CraftingStack(AbyssalCraft.dreadhilt, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadcloth, AbyssalCraft.dreadplanks,
						AbyssalCraft.dreadcloth, AbyssalCraft.dreadcloth, AbyssalCraft.dreadplanks, AbyssalCraft.dreadcloth);
				CraftingStack db = new CraftingStack(AbyssalCraft.dreadblade, new ItemStack(AbyssalCraft.crystal, 1, 14), new ItemStack(AbyssalCraft.crystal, 1, 14), null,
						new ItemStack(AbyssalCraft.crystal, 1, 14), new ItemStack(AbyssalCraft.crystal, 1, 14), null, new ItemStack(AbyssalCraft.crystal, 1, 14), new ItemStack(AbyssalCraft.crystal, 1, 14),
						null);
				CraftingStack oms = new CraftingStack(new ItemStack(AbyssalCraft.skin, 1, 2), AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh,
						new ItemStack(AbyssalCraft.essence, 1, 2), AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh, AbyssalCraft.omotholFlesh);
				CraftingStack omn = new CraftingStack(AbyssalCraft.necronomicon_omt, new ItemStack(AbyssalCraft.skin, 1, 2), new ItemStack(AbyssalCraft.skin, 1, 2), new ItemStack(AbyssalCraft.skin, 1, 2),
						new ItemStack(AbyssalCraft.skin, 1, 2), AbyssalCraft.necronomicon_dre, new ItemStack(AbyssalCraft.skin, 1, 2), new ItemStack(AbyssalCraft.skin, 1, 2), new ItemStack(AbyssalCraft.skin, 1, 2),
						new ItemStack(AbyssalCraft.skin, 1, 2));
				CraftingStack[] recipes = CraftingStack.arrayFrom(dreadium, cryst, drc, daltarb, daltart, dp, dh, db, oms, omn);
				CraftingStack sh = new CraftingStack(AbyssalCraft.dreadiumShelmet, null, AbyssalCraft.dreadiumingot, null, AbyssalCraft.dreadplate, AbyssalCraft.dreadiumhelmet,
						AbyssalCraft.dreadplate, null, null, null);
				CraftingStack sp = new CraftingStack(AbyssalCraft.dreadiumSplate, AbyssalCraft.dreadplate, AbyssalCraft.dreadiumingot, AbyssalCraft.dreadplate, AbyssalCraft.dreadplate,
						AbyssalCraft.dreadiumplate, AbyssalCraft.dreadplate, AbyssalCraft.dreadcloth, AbyssalCraft.dreadcloth, AbyssalCraft.dreadcloth);
				CraftingStack sl = new CraftingStack(AbyssalCraft.dreadiumSlegs, AbyssalCraft.dreadplate, AbyssalCraft.dreadiumlegs, AbyssalCraft.dreadplate, AbyssalCraft.dreadcloth,
						AbyssalCraft.dreadcloth, AbyssalCraft.dreadcloth, null, null, null);
				CraftingStack sb = new CraftingStack(AbyssalCraft.dreadiumSboots, AbyssalCraft.dreadcloth, AbyssalCraft.dreadiumboots, AbyssalCraft.dreadcloth, AbyssalCraft.dreadplanks,
						AbyssalCraft.dreadplanks, AbyssalCraft.dreadplanks, null, null, null);
				CraftingStack dk = new CraftingStack(AbyssalCraft.dreadkatana, AbyssalCraft.dreadblade, null, null, AbyssalCraft.dreadhilt, null, null, null, null, null);
				CraftingStack[] special = CraftingStack.arrayFrom(sh, sp, sl, sb, dk);
				PageData test1 = new PageData(6, NecronomiconText.LABEL_INFORMATION_MATERIALS, materials, NecronomiconText.DREADLANDS_MATERIALS);
				PageData test2 = new PageData(2, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.DREADLANDS_PROGRESSION);
				PageData test3 = new PageData(10, NecronomiconText.LABEL_INFORMATION_ENTITIES, NecronomiconResources.DREADLANDS_ENTITIES, NecronomiconText.DREADLANDS_ENTITIES);
				PageData test4 = new PageData(10, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, recipes, NecronomiconText.DREADLANDS_CRAFTING);
				PageData test5 = new PageData(5, NecronomiconText.LABEL_INFORMATION_ARMOR_TOOLS, special, NecronomiconText.DREADLANDS_ARMOR_TOOLS);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_DREADLANDS_TITLE, NecronomiconText.INFORMATION_DREADLANDS, test1, test2, test3, test4, test5);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon_dre));
			} else if(button.id == 11){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.omotholstone), new ItemStack(AbyssalCraft.ethaxium), new ItemStack(AbyssalCraft.darkethaxiumbrick)};
				CraftingStack lc = new CraftingStack(AbyssalCraft.lifeCrystal, new ItemStack(AbyssalCraft.crystal, 1, 3),new ItemStack(AbyssalCraft.crystal, 1, 5),
						new ItemStack(AbyssalCraft.crystal, 1, 6), new ItemStack(AbyssalCraft.crystal, 1, 4), new ItemStack(AbyssalCraft.crystal, 1, 7), new ItemStack(AbyssalCraft.crystal, 1, 2), null, null, null);
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
				PageData test1 = new PageData(3, NecronomiconText.LABEL_INFORMATION_MATERIALS, materials, NecronomiconText.OMOTHOL_MATERIALS);
				PageData test2 = new PageData(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.OMOTHOL_PROGRESSION);
				PageData test3 = new PageData(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, NecronomiconResources.OMOTHOL_ENTITIES, NecronomiconText.OMOTHOL_ENTITIES);
				PageData test4 = new PageData(8, NecronomiconText.LABEL_INFORMATION_SPECIAL_MATERIALS, recipes, NecronomiconText.OMOTHOL_CRAFTING);
				NecroData data = new NecroData(NecronomiconText.LABEL_INFORMATION_OMOTHOL_TITLE, NecronomiconText.INFORMATION_OMOTHOL, test1, test2, test3, test4);
				mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), data, new GuiNecronomiconInformation(getBookType()), AbyssalCraft.necronomicon_omt));
			} else if(button.id == 12){
				ItemStack[] materials = {new ItemStack(AbyssalCraft.Darkstone)};
				PageData test1 = new PageData(1, NecronomiconText.LABEL_INFORMATION_MATERIALS, materials, NecronomiconText.DARK_REALM_MATERIALS);
				PageData test2 = new PageData(1, NecronomiconText.LABEL_INFORMATION_PROGRESSION, NecronomiconText.DARK_REALM_PROGRESSION);
				PageData test3 = new PageData(6, NecronomiconText.LABEL_INFORMATION_ENTITIES, NecronomiconResources.DARK_REALM_ENTITIES, NecronomiconText.DARK_REALM_ENTITIES);
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
	protected void drawInformationText(int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		if(isAC){
			stuff = StatCollector.translateToLocal(NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT);
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			setTurnupLimit(3);
			if(currTurnup == 0){
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_1, 100);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_2, 100);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ABYSSALCRAFT_1);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			} else if(currTurnup == 1){
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_3, 100);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_4, 100);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ABYSSALCRAFT_2);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			} else if(currTurnup == 2){
				writeText(1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_5, 100);
				writeText(2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_6, 100);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ABYSSALCRAFT_3);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			}
		} else if(isAN){
			stuff = StatCollector.translateToLocal(NecronomiconText.LABEL_INFORMATION_ABYSSALNOMICON);
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			setTurnupLimit(1);
			writeText(1, NecronomiconText.INFORMATION_ABYSSALNOMICON);
		} else if(isP){
			stuff = "Patrons";
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			setTurnupLimit(1);
			writeText(1, "Saice Shoop", 100);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/patreon/saice.png"));
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
		}
	}
}
