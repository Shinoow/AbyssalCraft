/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.gui.necronomicon;

import java.util.*;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.recipe.CrystallizerRecipes;
import com.shinoow.abyssalcraft.api.recipe.TransmutatorRecipes;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonHome;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.GuiRenderHelper;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

public class GuiNecronomiconMachines extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage, buttonNextPageLong;
	private ButtonNextPage buttonPreviousPage, buttonPreviousPageLong;
	private GuiButton buttonDone;
	private ButtonHome buttonHome;
	private ButtonCategory info, transmutator, crystallizer;
	private boolean isMInfo, isTra, isCry;
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
	@SuppressWarnings("unchecked")
	public void initGui()
	{
		if(isInvalid)
			mc.displayGuiScreen(parent.withBookType(getBookType()));
		currentNecro = this;
		if(isCry && getBookType() == 1){
			isInfo = isMInfo = isTra = isCry = false;
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
			buttonList.add(info = new ButtonCategory(6, i + 14, b0 + 24, this, NecronomiconText.LABEL_INFO, false, ACItems.necronomicon));
			buttonList.add(transmutator = new ButtonCategory(7, i + 14, b0 + 41, this, "container.abyssalcraft.transmutator", false, getItem(1)));
			buttonList.add(crystallizer = new ButtonCategory(8, i + 14, b0 + 58, this, "container.abyssalcraft.crystallizer", false, getItem(2)));
			//	buttonList.add(engraver = new ButtonCategory(6, i + 14, b0 + 75, this, StatCollector.translateToLocal("container.abyssalcraft.engraver"), getItem(3)));
			//	buttonList.add(materializer = new ButtonCategory(7, i + 14, b0 + 92, this, StatCollector.translateToLocal("container.abyssalcraft.materializer"), getItem(3)));
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
		//	engraver.visible = true;
		//	materializer.visible = true;

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
					isInfo = isMInfo = isTra = isCry = false;
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
					isInfo = isMInfo = isTra = isCry = false;
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
				//	} else if(button.id == 6 && getBookType() >= 3){
				//	isInfo = true;
				//	isEng = true;
				//	drawButtons();
				//	} else if(button.id == 7 && getBookType() >= 3){
				//	isInfo = true;
				//	isMat = true;
				//	drawButtons();
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
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		writeText(2, NecronomiconText.MACHINES_INFO);
	}

	private void setTurnups(int size){
		if(size <= 12)
			setTurnupLimit(1);
		else if(size > 12 && size <= 24)
			setTurnupLimit(2);
		else if(size > 24 && size <= 36)
			setTurnupLimit(3);
		else if(size > 36 && size <= 48)
			setTurnupLimit(4);
		else if(size > 48 && size <= 60)
			setTurnupLimit(5);
		else if(size > 60 && size <= 72)
			setTurnupLimit(6);
		else if(size > 72 && size <= 84)
			setTurnupLimit(7);
		else if(size > 84 && size <= 96)
			setTurnupLimit(8);
		else if(size > 96 && size <= 108)
			setTurnupLimit(9);
		else if(size > 108 && size <= 120)
			setTurnupLimit(10);
		else if(size > 120 && size <= 132)
			setTurnupLimit(11);
		else if(size > 132 && size <= 144)
			setTurnupLimit(12);
		else if(size > 144 && size <= 156)
			setTurnupLimit(13);
		else if(size > 156 && size <= 168)
			setTurnupLimit(14);
		else if(size > 168 && size <= 180)
			setTurnupLimit(15);
		else if(size > 180 && size <= 192)
			setTurnupLimit(16);
		else if(size > 192 && size <= 204)
			setTurnupLimit(17);
		else if(size > 204 && size <= 216)
			setTurnupLimit(18);
		else if(size > 216 && size <= 228)
			setTurnupLimit(19);
		else if(size > 228 && size <= 240)
			setTurnupLimit(20);
	}

	@Override
	protected void drawInformationText(int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;

		tooltipStack = null;

		if(isMInfo){
			stuff = localize(NecronomiconText.LABEL_INFO);
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			setTurnupLimit(4);
			if(currTurnup == 0){
				writeText(1, NecronomiconText.MACHINE_INFO_1, 50);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ITEM);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				renderItem(k + 60, b0 + 28, new ItemStack(ACBlocks.transmutator_idle), x, y);
			} else if(currTurnup == 1){
				writeText(1, NecronomiconText.MACHINE_INFO_2, 50);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ITEM);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				renderItem(k + 60, b0 + 28, new ItemStack(ACBlocks.crystallizer_idle), x, y);
			} else if(currTurnup == 2){
				writeText(1, NecronomiconText.MACHINE_INFO_3, 50);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ITEM);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				renderItem(k + 60, b0 + 28, new ItemStack(ACBlocks.engraver), x, y);
			} else if(currTurnup == 3){
				writeText(1, NecronomiconText.MACHINE_INFO_4, 50);
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(NecronomiconResources.ITEM);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				renderItem(k + 60, b0 + 28, new ItemStack(ACBlocks.materializer), x, y);
			}
		}
		if(isTra){
			stuff = localize("container.abyssalcraft.transmutator");
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			Map<ItemStack, ItemStack> trans = TransmutatorRecipes.instance().getTransmutationList();
			setTurnups(trans.size());
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(NecronomiconResources.TRANSMUTATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			for(int n = 0; n < trans.size(); n++){
				Entry<ItemStack, ItemStack> entry = (Entry<ItemStack, ItemStack>) trans.entrySet().toArray()[n];
				if(currTurnup == 0){
					drawTItems(entry, n, 0, 6, 12, x, y);
					if(n == 12) break;
				} else if(currTurnup == 1){
					drawTItems(entry, n, 12, 18, 24, x, y);
					if(n == 24) break;
				} else if(currTurnup == 2){
					drawTItems(entry, n, 24, 30, 36, x, y);
					if(n == 36) break;
				} else if(currTurnup == 3){
					drawTItems(entry, n, 36, 42, 48, x, y);
					if(n == 48) break;
				} else if(currTurnup == 4){
					drawTItems(entry, n, 48, 54, 60, x, y);
					if(n == 60) break;
				} else if(currTurnup == 5){
					drawTItems(entry, n, 60, 66, 72, x, y);
					if(n == 72) break;
				} else if(currTurnup == 6){
					drawTItems(entry, n, 72, 78, 84, x, y);
					if(n == 84) break;
				} else if(currTurnup == 7){
					drawTItems(entry, n, 84, 90, 96, x, y);
					if(n == 96) break;
				} else if(currTurnup == 8){
					drawTItems(entry, n, 96, 102, 108, x, y);
					if(n == 108) break;
				} else if(currTurnup == 9){
					drawTItems(entry, n, 108, 114, 120, x, y);
					if(n == 120) break;
				} else if(currTurnup == 10){
					drawTItems(entry, n, 120, 126, 132, x, y);
					if(n == 132) break;
				} else if(currTurnup == 11){
					drawTItems(entry, n, 132, 138, 144, x, y);
					if(n == 144) break;
				} else if(currTurnup == 12){
					drawTItems(entry, n, 144, 150, 156, x, y);
					if(n == 156) break;
				} else if(currTurnup == 13){
					drawTItems(entry, n, 156, 162, 168, x, y);
					if(n == 168) break;
				} else if(currTurnup == 14){
					drawTItems(entry, n, 168, 174, 180, x, y);
					if(n == 180) break;
				} else if(currTurnup == 15){
					drawTItems(entry, n, 180, 186, 192, x, y);
					if(n == 192) break;
				} else if(currTurnup == 16){
					drawTItems(entry, n, 192, 198, 204, x, y);
					if(n == 204) break;
				} else if(currTurnup == 17){
					drawTItems(entry, n, 204, 210, 216, x, y);
					if(n == 216) break;
				} else if(currTurnup == 18){
					drawTItems(entry, n, 216, 222, 228, x, y);
					if(n == 228) break;
				} else if(currTurnup == 19){
					drawTItems(entry, n, 228, 234, 240, x, y);
					if(n == 240) break;
				}
			}
		}
		if(isCry){
			stuff = localize("container.abyssalcraft.crystallizer");
			fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			Map<ItemStack, ItemStack[]> cryst = CrystallizerRecipes.instance().getCrystallizationList();
			setTurnups(cryst.size());
			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(NecronomiconResources.TRANSMUTATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			mc.renderEngine.bindTexture(NecronomiconResources.CRYSTALLIZATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			for(int n = 0; n < cryst.size(); n++){
				Entry<ItemStack, ItemStack[]> entry = (Entry<ItemStack, ItemStack[]>) cryst.entrySet().toArray()[n];
				if(currTurnup == 0){
					drawCItems(entry, n, 0, 6, 12, x, y);
					if(n == 12) break;
				} else if(currTurnup == 1){
					drawCItems(entry, n, 12, 18, 24, x, y);
					if(n == 24) break;
				} else if(currTurnup == 2){
					drawCItems(entry, n, 24, 30, 36, x, y);
					if(n == 36) break;
				} else if(currTurnup == 3){
					drawCItems(entry, n, 36, 42, 48, x, y);
					if(n == 48) break;
				} else if(currTurnup == 4){
					drawCItems(entry, n, 48, 54, 60, x, y);
					if(n == 60) break;
				} else if(currTurnup == 5){
					drawCItems(entry, n, 60, 66, 72, x, y);
					if(n == 72) break;
				} else if(currTurnup == 6){
					drawCItems(entry, n, 72, 78, 84, x, y);
					if(n == 84) break;
				} else if(currTurnup == 7){
					drawCItems(entry, n, 84, 90, 96, x, y);
					if(n == 96) break;
				} else if(currTurnup == 8){
					drawCItems(entry, n, 96, 102, 108, x, y);
					if(n == 108) break;
				} else if(currTurnup == 9){
					drawCItems(entry, n, 108, 114, 120, x, y);
					if(n == 120) break;
				} else if(currTurnup == 10){
					drawCItems(entry, n, 120, 126, 132, x, y);
					if(n == 132) break;
				} else if(currTurnup == 11){
					drawCItems(entry, n, 132, 138, 144, x, y);
					if(n == 144) break;
				} else if(currTurnup == 12){
					drawCItems(entry, n, 144, 150, 156, x, y);
					if(n == 156) break;
				} else if(currTurnup == 13){
					drawCItems(entry, n, 156, 162, 168, x, y);
					if(n == 168) break;
				} else if(currTurnup == 14){
					drawCItems(entry, n, 168, 174, 180, x, y);
					if(n == 180) break;
				} else if(currTurnup == 15){
					drawCItems(entry, n, 180, 186, 192, x, y);
					if(n == 192) break;
				} else if(currTurnup == 16){
					drawCItems(entry, n, 192, 198, 204, x, y);
					if(n == 204) break;
				} else if(currTurnup == 17){
					drawCItems(entry, n, 204, 210, 216, x, y);
					if(n == 216) break;
				} else if(currTurnup == 18){
					drawCItems(entry, n, 216, 222, 228, x, y);
					if(n == 228) break;
				} else if(currTurnup == 19){
					drawCItems(entry, n, 228, 234, 240, x, y);
					if(n == 240) break;
				}
			}
		}

		if(tooltipStack != null)
		{
			List<String> tooltipData = tooltipStack.getTooltip(Minecraft.getMinecraft().thePlayer, false);
			List<String> parsedTooltip = new ArrayList();
			boolean first = true;

			for(String s : tooltipData)
			{
				String s_ = s;
				if(!first)
					s_ = TextFormatting.GRAY + s;
				parsedTooltip.add(s_);
				first = false;
			}
			GuiRenderHelper.renderTooltip(x, y, parsedTooltip);
		}
	}

	private void drawTItems(Entry<ItemStack, ItemStack> entry, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRendererObj.getUnicodeFlag();
		fontRendererObj.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		if(num < mid && num > low-1){
			renderItem(k + 18, b0 + 28 + (num-low)*20 + num-low, entry.getKey(), x, y);
			renderItem(k + 62, b0 + 28 + (num-low)*20 + num-low, entry.getValue(), x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 141, b0 + 28 + (num-mid)*20 + num-mid, entry.getKey(), x, y);
			renderItem(k + 185, b0 + 28 + (num-mid)*20 + num-mid, entry.getValue(), x, y);
		}
		fontRendererObj.setUnicodeFlag(unicode);
	}

	private void drawCItems(Entry<ItemStack, ItemStack[]> entry, int num, int low, int mid, int high, int x, int y){
		boolean unicode = fontRendererObj.getUnicodeFlag();
		fontRendererObj.setUnicodeFlag(false);
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		if(num < mid && num > low-1){
			renderItem(k + 18, b0 + 28 + (num-low)*20 + num-low, entry.getKey(), x, y);
			renderItem(k + 62, b0 + 28 + (num-low)*20 + num-low, entry.getValue()[0], x, y);

			renderItem(k + 62 + 33, b0 + 28 + (num-low)*20 + num-low, entry.getValue()[1], x, y);
		} else if(num > mid-1 && num < high){
			renderItem(k + 141, b0 + 28 + (num-mid)*20 + num-mid, entry.getKey(), x, y);
			renderItem(k + 185, b0 + 28 + (num-mid)*20 + num-mid, entry.getValue()[0], x, y);
			renderItem(k + 185 + 34, b0 + 28 + (num-mid)*20 + num-mid, entry.getValue()[1], x, y);
		}
		fontRendererObj.setUnicodeFlag(unicode);
	}

	private ItemStack tooltipStack;
	public void renderItem(int xPos, int yPos, ItemStack stack, int mx, int my)
	{
		if(stack == null) return;

		if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			stack.setItemDamage(0);

		RenderItem render = Minecraft.getMinecraft().getRenderItem();
		if(mx > xPos && mx < xPos+16 && my > yPos && my < yPos+16)
			tooltipStack = stack;

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableBlend();
		render.renderItemAndEffectIntoGUI(stack, xPos, yPos);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, stack, xPos, yPos, null);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();

		GlStateManager.disableLighting();
	}
}
