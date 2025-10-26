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

import com.google.common.collect.ImmutableList;
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
import net.minecraft.item.ItemStack;

public class GuiNecronomiconMachines extends GuiNecronomicon {

	private ButtonCategory info, transmutator, crystallizer, materializer, anvil;
	private boolean isMInfo, isTra, isCry, isMat, isAnv;
	private List<Transmutation> transmutations = new ArrayList<>();
	private List<Crystallization> crystallizations = new ArrayList<>();
	private List<Materialization> materalizations = new ArrayList<>();
	private List<AnvilForging> anvilForgings = new ArrayList<>();

	public GuiNecronomiconMachines(int bookType, GuiNecronomicon parent){
		super(bookType);
		this.parent = parent;
	}

	@Override
	public void initGui()
	{
		if(isInvalid)
			mc.displayGuiScreen(parent.withBookType(getBookType()));
		if(transmutations.isEmpty() || crystallizations.isEmpty()
				|| materalizations.isEmpty() || anvilForgings.isEmpty())
			initStuff();
		if(isCry && getKnowledgeLevel() <= 1){
			isInfo = isMInfo = isTra = isCry = isMat = isAnv = false;
			currTurnup = 0;
		}
		if(isMat && getKnowledgeLevel() <= 2){
			isInfo = isMInfo = isTra = isCry = isMat = isAnv = false;
			currTurnup = 0;
		}

		initCommon();
	}

	@Override
	protected void initButtonsInner() {
		int i = (width - guiWidth) / 2;
		buttonList.add(info = new ButtonCategory(8, i + 14, getButtonHeight(0), this, NecronomiconText.LABEL_INFORMATION, ACItems.necronomicon));
		buttonList.add(anvil = new ButtonCategory(9, i + 14, getButtonHeight(1), this, "tile.anvil.name"));
		buttonList.add(transmutator = new ButtonCategory(10, i + 14, getButtonHeight(2), this, "container.abyssalcraft.transmutator", getItem(1)).setLocked(!isUnlocked(ResearchItems.getBookResearch(1))));
		buttonList.add(crystallizer = new ButtonCategory(11, i + 14, getButtonHeight(3), this, "container.abyssalcraft.crystallizer", getItem(2)).setLocked(!isUnlocked(ResearchItems.getBookResearch(2))));
		buttonList.add(materializer = new ButtonCategory(12, i + 14, getButtonHeight(4), this, "container.abyssalcraft.materializer", getItem(3)).setLocked(!isUnlocked(ResearchItems.getBookResearch(3))));
	}

	@Override
	protected void updateButtonsInner() {

		if(!isInfo)
			isMInfo = isTra = isCry = isMat = isAnv = false;

		info.visible = !isInfo;
		anvil.visible = !isInfo;
		transmutator.visible = !isInfo;
		crystallizer.visible = !isInfo;
		materializer.visible = !isInfo;
	}

	@Override
	protected void actionPerformedInner(GuiButton button) {
		if(button.id > 7) {
			if(button.id == 8){
				isMInfo = true;
			} else if(button.id == 9) {
				isAnv = true;
			} else if(button.id == 10 && getKnowledgeLevel() >= 1){
				isTra = true;
			} else if(button.id == 11 && getKnowledgeLevel() >= 2){
				isCry = true;
			} else if(button.id == 12 && getKnowledgeLevel() >= 3){
				isMat = true;
			}
			isInfo = true;
		}
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
