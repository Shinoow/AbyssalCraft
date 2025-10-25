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

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.knowledge.ResearchItems;
import com.shinoow.abyssalcraft.api.recipe.*;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonHome;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class GuiNecronomiconMachines extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage, buttonNextPageLong;
	private ButtonNextPage buttonPreviousPage, buttonPreviousPageLong;
	private GuiButton buttonDone;
	private ButtonHome buttonHome;
	private ButtonCategory info, transmutator, crystallizer, materializer, anvil;
	private boolean isMInfo, isTra, isCry, isMat, isAnv;
	private GuiNecronomicon parent;
	private List<Transmutation> transmutations = new ArrayList<>();
	private List<Crystallization> crystallizations = new ArrayList<>();
	private List<Materialization> materalizations = new ArrayList<>();
	private List<AnvilForging> anvilForgings = new ArrayList<>();

	public GuiNecronomiconMachines(int bookType, GuiNecronomicon parent){
		super(bookType);
		this.parent = parent;
	}

	@Override
	public GuiNecronomicon withBookType(int par1){
//		if(par1 < 1)
//			isInvalid = true;
		return super.withBookType(par1);
	}
	
	@Override
	public void initGui()
	{
		if(isInvalid)
			mc.displayGuiScreen(parent.withBookType(getBookType()));
		currentNecro = this;
		if(transmutations.isEmpty() || crystallizations.isEmpty()
				|| materalizations.isEmpty() || anvilForgings.isEmpty())
			initStuff();
		if(isCry && getKnowledgeLevel() == 1){
			isInfo = isMInfo = isTra = isCry = isMat = isAnv = false;
			currTurnup = 0;
		}
		if(isMat && getKnowledgeLevel() <= 2){
			isInfo = isMInfo = isTra = isCry = isMat = isAnv = false;
			currTurnup = 0;
		}
		if(isInfo)
			drawButtons();
		else {
			buttonList.clear();
			Keyboard.enableRepeatEvents(true);

			buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

			int i = (width - guiWidth) / 2;
			byte b0 = 2;
			buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 220, b0 + 154, true, false));
			buttonList.add(buttonNextPageLong = new ButtonNextPage(2, i + 203, b0 + 167, true, true));
			buttonList.add(buttonPreviousPage = new ButtonNextPage(3, i + 18, b0 + 154, false, false));
			buttonList.add(buttonPreviousPageLong = new ButtonNextPage(4, i + 23, b0 + 167, false, true));
			buttonList.add(buttonHome = new ButtonHome(5, i + 118, b0 + 167));
			buttonList.add(info = new ButtonCategory(6, i + 14, getButtonHeight(0), this, NecronomiconText.LABEL_INFORMATION, ACItems.necronomicon));
			buttonList.add(anvil = new ButtonCategory(7, i + 14, getButtonHeight(1), this, "tile.anvil.name"));
			buttonList.add(transmutator = new ButtonCategory(8, i + 14, getButtonHeight(2), this, "container.abyssalcraft.transmutator", getItem(1)).setLocked(!isUnlocked(ResearchItems.getBookResearch(1))));
			buttonList.add(crystallizer = new ButtonCategory(9, i + 14, getButtonHeight(3), this, "container.abyssalcraft.crystallizer", getItem(2)).setLocked(!isUnlocked(ResearchItems.getBookResearch(2))));
			buttonList.add(materializer = new ButtonCategory(10, i + 14, getButtonHeight(4), this, "container.abyssalcraft.materializer", getItem(3)).setLocked(!isUnlocked(ResearchItems.getBookResearch(3))));
		}
		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo;
		buttonNextPageLong.visible = currTurnup < getTurnupLimit() -5;
		buttonPreviousPage.visible = true;
		buttonPreviousPageLong.visible = currTurnup > 4;
		buttonDone.visible = true;
		buttonHome.visible = true;
		info.visible = true;
		anvil.visible = true;
		transmutator.visible = true;
		crystallizer.visible = true;
		materializer.visible = true;

	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled){
			if(button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1){
				if (currTurnup < getTurnupLimit() -1)
					++currTurnup;
			} else if(button.id == 2){
				if(currTurnup < getTurnupLimit() -5)
					currTurnup += 5;
			} else if(button.id == 3){
				if(currTurnup == 0 && !isInfo)
					mc.displayGuiScreen(parent.withBookType(getBookType()));
				else if(currTurnup == 0 && isInfo){
					isInfo = isMInfo = isTra = isCry = isMat = isAnv = false;
					initGui();
					setTurnupLimit(2);
				} else if (currTurnup > 0)
					--currTurnup;
			} else if(button.id == 4){
				if(currTurnup > 4)
					currTurnup -= 5;
			} else if(button.id == 5){
				if(!isInfo)
					mc.displayGuiScreen(new GuiNecronomicon(getBookType()));
				else {
					currTurnup = 0;
					isInfo = isMInfo = isTra = isCry = isMat = isAnv = false;
					initGui();
					setTurnupLimit(2);
				}
			} else if(button.id > 5) {
				if(button.id == 6){
					isMInfo = true;
				} else if(button.id == 7) {
					isAnv = true;
				} else if(button.id == 8 && getKnowledgeLevel() >= 1){
					isTra = true;
				} else if(button.id == 9 && getKnowledgeLevel() >= 2){
					isCry = true;
				} else if(button.id == 10 && getKnowledgeLevel() >= 3){
					isMat = true;
				}
				isInfo = true;
				drawButtons();
			}
			updateButtons();
		}
	}

	private void drawButtons(){
		buttonList.clear();
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true, false));
		buttonList.add(buttonNextPageLong = new ButtonNextPage(2, i + 203, b0 + 167, true, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(3, i + 18, b0 + 154, false, false));
		buttonList.add(buttonPreviousPageLong = new ButtonNextPage(4, i + 23, b0 + 167, false, true));
		buttonList.add(buttonHome = new ButtonHome(5, i + 118, b0 + 167));
	}

	@Override
	protected void drawIndexText(){
		drawTitle(localize(NecronomiconText.LABEL_INFORMATION_MACHINES));
		writeText(2, NecronomiconText.MACHINES_INFO);
	}

	private void setTurnups(int size){
		setTurnups(size, false);
	}

	private void setTurnups(int size, boolean isMat){
		int i = !isMat ? 2 : 1;
		setTurnupLimit((size+6*i)/(6*i));
	}

	@Override
	protected void drawInformationText(int x, int y){
		int k = (width - guiWidth) / 2;

		getHelper().clearTooltipStack();

		if(isMInfo){
			drawTitle(localize(NecronomiconText.LABEL_INFORMATION));
			setTurnupLimit(2);
			if(currTurnup == 0){
				writeText(1, NecronomiconText.MACHINE_INFO_1, 50);
				drawTexture(NecronomiconResources.ITEM);
				renderItem(k + 60, 30, new ItemStack(ACBlocks.transmutator_idle), x, y);

				writeText(2, NecronomiconText.MACHINE_INFO_2, 50);
				drawTexture(NecronomiconResources.ITEM, 123);
				renderItem(k + 183, 30, new ItemStack(ACBlocks.crystallizer_idle), x, y);

			} else if(currTurnup == 1){
				writeText(1, NecronomiconText.MACHINE_INFO_4, 50);
				drawTexture(NecronomiconResources.ITEM);
				renderItem(k + 60, 30, new ItemStack(ACBlocks.materializer), x, y);
			}
		}
		if(isAnv) {
			drawTitle(localize("tile.anvil.name"));
			setTurnups(anvilForgings.size());
			drawTexture(NecronomiconResources.TRANSMUTATION, 28);
			drawTexture(NecronomiconResources.CRYSTALLIZATION, -77);
			for(int n = 0; n < anvilForgings.size(); n++){
				AnvilForging entry = anvilForgings.get(n);
				drawAItems(entry, n, currTurnup*12, currTurnup*12+6, (currTurnup+1)*12, x, y);
			}
		}
		if(isTra){
			drawTitle(localize("container.abyssalcraft.transmutator"));
			setTurnups(transmutations.size());
			drawTexture(NecronomiconResources.TRANSMUTATION);
			for(int n = 0; n < transmutations.size(); n++){
				Transmutation entry = transmutations.get(n);
				drawTItems(entry, n, currTurnup*12, currTurnup*12+6, (currTurnup+1)*12, x, y);
			}
		}
		if(isCry){
			drawTitle(localize("container.abyssalcraft.crystallizer"));
			setTurnups(crystallizations.size());
			drawTexture(NecronomiconResources.TRANSMUTATION);
			drawTexture(NecronomiconResources.CRYSTALLIZATION);
			for(int n = 0; n < crystallizations.size(); n++){
				Crystallization entry = crystallizations.get(n);
				drawCItems(entry, n, currTurnup*12, currTurnup*12+6, (currTurnup+1)*12, x, y);
			}
		}
		if(isMat){
			drawTitle(localize("container.abyssalcraft.materializer"));
			setTurnups(materalizations.size(), true);
			drawTexture(NecronomiconResources.MATERIALIZATION);
			for(int n = 0; n < materalizations.size(); n++){
				Materialization m = materalizations.get(n);
				if((currTurnup+1)*6 > n && n > currTurnup*6-1)
					drawMItems(m, n, currTurnup*6, 0, 0, x, y);
			}
		}

		renderTooltip(x, y);
	}

	private void drawAItems(AnvilForging entry, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		int offset = 124;
		if(num < mid && num > low-1){
			renderItem(k + 18, 30 + (num-low)*20 + num-low, entry.INPUT1, x, y);
			renderItem(k + 46, 30 + (num-low)*20 + num-low, entry.INPUT2, x, y);
			renderItem(k + 90, 30 + (num-low)*20 + num-low, entry.getOutput(), x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 18 + offset, 30 + (num-mid)*20 + num-mid, entry.INPUT1, x, y);
			renderItem(k + 45 + offset, 30 + (num-mid)*20 + num-mid, entry.INPUT2, x, y);
			renderItem(k + 89 + offset, 30 + (num-mid)*20 + num-mid, entry.getOutput(), x, y);
		}
		fontRenderer.setUnicodeFlag(unicode);
	}
	
	private void drawTItems(Transmutation entry, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		if(num < mid && num > low-1){
			renderItem(k + 18, 30 + (num-low)*20 + num-low, entry.INPUT, x, y);
			renderItem(k + 62, 30 + (num-low)*20 + num-low, entry.OUTPUT, x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 141, 30 + (num-mid)*20 + num-mid, entry.INPUT, x, y);
			renderItem(k + 185, 30 + (num-mid)*20 + num-mid, entry.OUTPUT, x, y);
		}
		fontRenderer.setUnicodeFlag(unicode);
	}

	private void drawCItems(Crystallization entry, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		if(num < mid && num > low-1){
			renderItem(k + 18, 30 + (num-low)*20 + num-low, entry.INPUT, x, y);
			renderItem(k + 62, 30 + (num-low)*20 + num-low, entry.OUTPUT1, x, y);

			renderItem(k + 62 + 33, 30 + (num-low)*20 + num-low, entry.OUTPUT2, x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 141, 30 + (num-mid)*20 + num-mid, entry.INPUT, x, y);
			renderItem(k + 185, 30 + (num-mid)*20 + num-mid, entry.OUTPUT1, x, y);
			renderItem(k + 185 + 34, 30 + (num-mid)*20 + num-mid, entry.OUTPUT2, x, y);
		}
		fontRenderer.setUnicodeFlag(unicode);
	}

	private void drawMItems(Materialization mat, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;

		renderItem(k + 38, 30 + (num-low)*20 + num-low, mat.input[0].copy(), x, y);
		if(mat.input.length >= 2)
			renderItem(k + 59, 30 + (num-low)*20 + num-low, mat.input[1].copy(), x, y);
		if(mat.input.length >= 3)
			renderItem(k + 59 + 21, 30 + (num-low)*20 + num-low, mat.input[2].copy(), x, y);
		if(mat.input.length >= 4)
			renderItem(k + 145, 30 + (num-low)*20 + num-low, mat.input[3].copy(), x, y);
		if(mat.input.length == 5)
			renderItem(k + 166, 30 + (num-low)*20 + num-low, mat.input[4].copy(), x, y);
		renderItem(k + 166 + 44, 30 + (num-low)*20 + num-low, mat.output.copy(), x, y);

		fontRenderer.setUnicodeFlag(unicode);
	}

	private void initStuff() {
		transmutations = ImmutableList.copyOf(TransmutatorRecipes.instance().getTransmutationList());
		crystallizations = ImmutableList.copyOf(CrystallizerRecipes.instance().getCrystallizationList());
		materalizations = ImmutableList.copyOf(MaterializerRecipes.instance().getMaterializationList());
		anvilForgings = ImmutableList.copyOf(AnvilForgingRecipes.instance().getForgingList());
	}

}
