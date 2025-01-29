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

import java.util.List;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
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
import net.minecraft.item.ItemStack;

public class GuiNecronomiconMachines extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage, buttonNextPageLong;
	private ButtonNextPage buttonPreviousPage, buttonPreviousPageLong;
	private GuiButton buttonDone;
	private ButtonHome buttonHome;
	private ButtonCategory info, transmutator, crystallizer, materializer;
	private boolean isMInfo, isTra, isCry, isMat;
	private GuiNecronomicon parent;

	public GuiNecronomiconMachines(int bookType, GuiNecronomicon parent){
		super(bookType);
		this.parent = parent;
	}

	@Override
	public GuiNecronomicon withBookType(int par1){
		if(par1 < 1)
			isInvalid = true;
		return super.withBookType(par1);
	}

	@Override
	public void initGui()
	{
		if(isInvalid)
			mc.displayGuiScreen(parent.withBookType(getBookType()));
		currentNecro = this;
		if(isCry && getBookType() == 1){
			isInfo = isMInfo = isTra = isCry = isMat = false;
			currTurnup = 0;
		}
		if(isMat && getBookType() <= 2){
			isInfo = isMInfo = isTra = isCry = isMat = false;
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
			buttonList.add(info = new ButtonCategory(6, i + 14, b0 + 24, this, NecronomiconText.LABEL_INFORMATION, false, ACItems.necronomicon));
			buttonList.add(transmutator = new ButtonCategory(7, i + 14, b0 + 41, this, "container.abyssalcraft.transmutator", false, getItem(1)));
			buttonList.add(crystallizer = new ButtonCategory(8, i + 14, b0 + 58, this, "container.abyssalcraft.crystallizer", false, getItem(2)));
			buttonList.add(materializer = new ButtonCategory(9, i + 14, b0 + 75, this, "container.abyssalcraft.materializer", false, getItem(3)));
			//	buttonList.add(engraver = new ButtonCategory(6, i + 14, b0 + 92, this, StatCollector.translateToLocal("container.abyssalcraft.engraver"), getItem(3)));
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
		transmutator.visible = true;
		crystallizer.visible = true;
		materializer.visible = true;
		//	engraver.visible = true;

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
					isInfo = isMInfo = isTra = isCry = isMat = false;
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
					isInfo = isMInfo = isTra = isCry = isMat = false;
					initGui();
					setTurnupLimit(2);
				}
			} else if(button.id == 6){
				isInfo = true;
				isMInfo = true;
				drawButtons();
			} else if(button.id == 7 && getBookType() >= 1){
				isInfo = true;
				isTra = true;
				drawButtons();
			} else if(button.id == 8 && getBookType() >= 2){
				isInfo = true;
				isCry = true;
				drawButtons();
			} else if(button.id == 9 && getBookType() >= 3){
				isInfo = true;
				isMat = true;
				drawButtons();
				//	} else if(button.id == 6 && getBookType() >= 3){
				//	isInfo = true;
				//	isEng = true;
				//	drawButtons();
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
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = localize(NecronomiconText.LABEL_INFORMATION_MACHINES);
		fontRenderer.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
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
		byte b0 = 2;
		String stuff;

		tooltipStack = null;

		if(isMInfo){
			stuff = localize(NecronomiconText.LABEL_INFORMATION);
			fontRenderer.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			setTurnupLimit(2);
			if(currTurnup == 0){
				writeText(1, NecronomiconText.MACHINE_INFO_1, 50);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ITEM);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				renderItem(k + 60, b0 + 28, new ItemStack(ACBlocks.transmutator_idle), x, y);

				writeText(2, NecronomiconText.MACHINE_INFO_2, 50);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ITEM);
				drawTexturedModalRect(k + 123, b0, 0, 0, 256, 256);
				renderItem(k + 183, b0 + 28, new ItemStack(ACBlocks.crystallizer_idle), x, y);

			} else if(currTurnup == 1){

				writeText(1, NecronomiconText.MACHINE_INFO_4, 50);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ITEM);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				renderItem(k + 60, b0 + 28, new ItemStack(ACBlocks.materializer), x, y);
			}
		}
		if(isTra){
			stuff = localize("container.abyssalcraft.transmutator");
			fontRenderer.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			List<Transmutation> trans = TransmutatorRecipes.instance().getTransmutationList();
			setTurnups(trans.size());
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(NecronomiconResources.TRANSMUTATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			for(int n = 0; n < trans.size(); n++){
				Transmutation entry = trans.get(n);
				drawTItems(entry, n, currTurnup*12, currTurnup*12+6, (currTurnup+1)*12, x, y);
			}
		}
		if(isCry){
			stuff = localize("container.abyssalcraft.crystallizer");
			fontRenderer.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			List<Crystallization> cryst = CrystallizerRecipes.instance().getCrystallizationList();
			setTurnups(cryst.size());
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(NecronomiconResources.TRANSMUTATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			mc.renderEngine.bindTexture(NecronomiconResources.CRYSTALLIZATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			for(int n = 0; n < cryst.size(); n++){
				Crystallization entry = cryst.get(n);
				drawCItems(entry, n, currTurnup*12, currTurnup*12+6, (currTurnup+1)*12, x, y);
			}
		}
		if(isMat){
			stuff = localize("container.abyssalcraft.materializer");
			fontRenderer.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			List<Materialization> mat = MaterializerRecipes.instance().getMaterializationList();
			setTurnups(mat.size(), true);
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(NecronomiconResources.MATERIALIZATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			for(int n = 0; n < mat.size(); n++){
				Materialization m = mat.get(n);
				if((currTurnup+1)*6 > n && n > currTurnup*6-1)
					drawMItems(m, n, currTurnup*6, 0, 0, x, y);
			}
		}

		renderTooltip(x, y);
	}

	private void drawTItems(Transmutation entry, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		if(num < mid && num > low-1){
			renderItem(k + 18, b0 + 28 + (num-low)*20 + num-low, entry.INPUT, x, y);
			renderItem(k + 62, b0 + 28 + (num-low)*20 + num-low, entry.OUTPUT, x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 141, b0 + 28 + (num-mid)*20 + num-mid, entry.INPUT, x, y);
			renderItem(k + 185, b0 + 28 + (num-mid)*20 + num-mid, entry.OUTPUT, x, y);
		}
		fontRenderer.setUnicodeFlag(unicode);
	}

	private void drawCItems(Crystallization entry, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		if(num < mid && num > low-1){
			renderItem(k + 18, b0 + 28 + (num-low)*20 + num-low, entry.INPUT, x, y);
			renderItem(k + 62, b0 + 28 + (num-low)*20 + num-low, entry.OUTPUT1, x, y);

			renderItem(k + 62 + 33, b0 + 28 + (num-low)*20 + num-low, entry.OUTPUT2, x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 141, b0 + 28 + (num-mid)*20 + num-mid, entry.INPUT, x, y);
			renderItem(k + 185, b0 + 28 + (num-mid)*20 + num-mid, entry.OUTPUT1, x, y);
			renderItem(k + 185 + 34, b0 + 28 + (num-mid)*20 + num-mid, entry.OUTPUT2, x, y);
		}
		fontRenderer.setUnicodeFlag(unicode);
	}

	private void drawMItems(Materialization mat, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		byte b0 = 2;

		renderItem(k + 38, b0 + 28 + (num-low)*20 + num-low, mat.input[0].copy(), x, y);
		if(mat.input.length >= 2)
			renderItem(k + 59, b0 + 28 + (num-low)*20 + num-low, mat.input[1].copy(), x, y);
		if(mat.input.length >= 3)
			renderItem(k + 59 + 21, b0 + 28 + (num-low)*20 + num-low, mat.input[2].copy(), x, y);
		if(mat.input.length >= 4)
			renderItem(k + 145, b0 + 28 + (num-low)*20 + num-low, mat.input[3].copy(), x, y);
		if(mat.input.length == 5)
			renderItem(k + 166, b0 + 28 + (num-low)*20 + num-low, mat.input[4].copy(), x, y);
		renderItem(k + 166 + 44, b0 + 28 + (num-low)*20 + num-low, mat.output.copy(), x, y);

		fontRenderer.setUnicodeFlag(unicode);
	}
}
