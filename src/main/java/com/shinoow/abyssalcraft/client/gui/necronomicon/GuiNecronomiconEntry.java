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

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.necronomicon.CraftingStack;
import com.shinoow.abyssalcraft.api.necronomicon.GuiInstance;
import com.shinoow.abyssalcraft.api.necronomicon.INecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.NecronomiconCondition;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.GuiRenderHelper;
import com.shinoow.abyssalcraft.common.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.common.caps.NecroDataCapabilityProvider;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

public class GuiNecronomiconEntry extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private ButtonCategory[] buttons;
	private GuiButton buttonDone;
	private NecroData data;
	private GuiNecronomicon parent;
	private Item icon;
	private int currentData;
	private INecroDataCapability cap;

	public GuiNecronomiconEntry(int bookType, NecroData nd, GuiNecronomicon gui){
		super(bookType);
		data = nd;
		parent = gui;
		icon = getItem(nd.getDisplayIcon());
		cap = Minecraft.getMinecraft().thePlayer.getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null);
		buttons = new ButtonCategory[data.getContainedData().size()];
	}

	public GuiNecronomiconEntry(int bookType, NecroData nd, GuiNecronomicon gui, Item item){
		this(bookType, nd, gui);
		icon = item;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui(){
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));
		if(data != null)
			for(int n = 0; n < data.getContainedData().size(); n++){
				INecroData nd = data.getContainedData().get(n);
				buttonList.add(buttons[n] = new ButtonCategory(3 + n, i + (n < 7 ? 14 : 132), b0 + 24 + 17* (n < 7 ? n : n - 7),this, nd.getTitle(), !isUnlocked(nd.getCondition()), getItem(nd.getDisplayIcon())));
			}
		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
		if(data != null)
			for(int i = 0; i < data.getContainedData().size(); i++)
				buttons[i].visible = !isInfo;
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
					mc.displayGuiScreen(parent);
				else if(currTurnup == 0 && isInfo){
					initGui();
					isInfo = false;
					currentData = -1;
					setTurnupLimit(2);
				} else if (currTurnup > 0)
					--currTurnup;
			} else if(button.id >= 3 && data.getContainedData().size() >= button.id - 2){
				int i = button.id - 3;
				INecroData nd = data.getContainedData().get(i);
				if(isUnlocked(nd.getCondition()))
					if(nd instanceof GuiInstance)
						mc.displayGuiScreen(((GuiInstance)nd).getOpenGui(getBookType(), this));
					else if(nd instanceof NecroData)
						mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)nd, this));
					else {
						currentData = i;
						isInfo = true;
						drawButtons();
					}
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

		if(currentData != -1)
			drawChapterOrPage(data.getContainedData().get(currentData), x, y);
		updateButtons();
	}

	private boolean isUnlocked(IUnlockCondition cnd){
		if(cnd instanceof NecronomiconCondition)
			return getBookType() >= (int)cnd.getConditionObject();
			else return cap.isUnlocked(cnd) || Minecraft.getMinecraft().thePlayer.capabilities.isCreativeMode;
	}

	private void drawChapterOrPage(INecroData data, int x, int y){
		if(data instanceof Chapter)
			drawChapter((Chapter)data, x, y);
		else{
			setTurnupLimit(1);
			addPage((Page)data, null, 2, x, y);
		}
	}

	private void drawChapter(Chapter chapter, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;

		stuff = I18n.format(chapter.getTitle(), new Object[0]);
		fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
		setTurnupLimit(chapter.getTurnupAmount());

		int num = (currTurnup + 1)*2;

		addPage(chapter.getPage(num-1), chapter.getPage(num), num, x, y);
	}

	private void addPage(Page page1, Page page2, int displayNum, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String text1 = "";
		String text2 = "";
		Object icon1 = null;
		Object icon2 = null;
		boolean locked1 = false;
		boolean locked2 = false;

		if(page1 != null){
			text1 = page1.getText();
			icon1 = page1.getIcon();
			locked1 = !isUnlocked(page1.getCondition());
		}
		if(page2 != null){
			text2 = page2.getText();
			icon2 = page2.getIcon();
			locked2 = !isUnlocked(page2.getCondition());
		}

		tooltipStack = null;

		writeTexts(icon1, icon2, text1, text2, locked1, locked2);

		writeText(1, String.valueOf(displayNum - 1), 165, 50);
		writeText(2, String.valueOf(displayNum), 165, 50);

		if(icon1 != null){
			if(icon1 instanceof ItemStack){
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				if(locked1){
					mc.renderEngine.bindTexture(MISSING_ITEM);
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				} else {
					mc.renderEngine.bindTexture(NecronomiconResources.ITEM);
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
					renderItem(k + 60, b0 + 28,(ItemStack)icon1, x, y);
				}

			}
			if(icon1 instanceof ResourceLocation){
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(locked1 ? MISSING_PICTURE : (ResourceLocation)icon1);
				drawTexturedModalRect(k, b0, 0, 0, 256, 256);
			}
			if(icon1 instanceof CraftingStack){
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				if(locked1){
					mc.renderEngine.bindTexture(MISSING_RECIPE);
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				} else {
					mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
					boolean unicode = fontRendererObj.getUnicodeFlag();
					fontRendererObj.setUnicodeFlag(false);
					renderItem(k + 93, b0 + 52,((CraftingStack)icon1).getOutput(), x, y);
					fontRendererObj.setUnicodeFlag(unicode);
					for(int i = 0; i <= 2; i++){
						renderItem(k + 24 +i*21, b0 + 31,((CraftingStack)icon1).getFirstArray()[i], x, y);
						renderItem(k + 24 +i*21, b0 + 52,((CraftingStack)icon1).getSecondArray()[i], x, y);
						renderItem(k + 24 +i*21, b0 + 73,((CraftingStack)icon1).getThirdArray()[i], x, y);
					}
				}
			}
			if(icon1 instanceof String)
				if(locked1){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture(MISSING_PICTURE);
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				} else if(failcache.contains(icon1)){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				} else if(successcache.get(icon1) != null){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.bindTexture(successcache.get(icon1).getGlTextureId());
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				} else {
					DynamicTexture t = null;
					try {
						t = new DynamicTexture(ImageIO.read(new URL((String)icon1)));
						successcache.put((String)icon1, t);
					} catch (Exception e) {
						failcache.add((String)icon1);
					}
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					if(t != null)
						GlStateManager.bindTexture(t.getGlTextureId());
					else mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k, b0, 0, 0, 256, 256);
				}
		}
		if(icon2 != null){
			int n = 123;
			if(icon2 instanceof ItemStack){
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				if(locked2){
					mc.renderEngine.bindTexture(MISSING_ITEM);
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				} else {
					mc.renderEngine.bindTexture(NecronomiconResources.ITEM);
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
					renderItem(k + 60 + n, b0 + 28,(ItemStack)icon2, x, y);
				}
			}
			if(icon2 instanceof ResourceLocation){
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				mc.renderEngine.bindTexture(locked2 ? MISSING_PICTURE : (ResourceLocation)icon2);
				drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
			}
			if(icon2 instanceof CraftingStack){
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				if(locked2){
					mc.renderEngine.bindTexture(MISSING_RECIPE);
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				} else {
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.pushMatrix();
					GlStateManager.enableBlend();
					GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
					mc.renderEngine.bindTexture(NecronomiconResources.CRAFTING);
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
					GlStateManager.enableRescaleNormal();
					GlStateManager.enableDepth();
					GlStateManager.popMatrix();
					GlStateManager.disableLighting();
					boolean unicode = fontRendererObj.getUnicodeFlag();
					fontRendererObj.setUnicodeFlag(false);
					renderItem(k + 93 + n, b0 + 52,((CraftingStack)icon2).getOutput(), x, y);
					fontRendererObj.setUnicodeFlag(unicode);
					for(int i = 0; i <= 2; i++){
						renderItem(k + 24 + n +i*21, b0 + 31,((CraftingStack)icon2).getFirstArray()[i], x, y);
						renderItem(k + 24 + n +i*21, b0 + 52,((CraftingStack)icon2).getSecondArray()[i], x, y);
						renderItem(k + 24 + n +i*21, b0 + 73,((CraftingStack)icon2).getThirdArray()[i], x, y);
					}
				}
			}
			if(icon2 instanceof String)
				if(locked2){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture(MISSING_PICTURE);
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				} else if(failcache.contains(icon2)){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				} else if(successcache.get(icon2) != null){
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					GlStateManager.bindTexture(successcache.get(icon2).getGlTextureId());
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
				} else {
					DynamicTexture t = null;
					try {
						t = new DynamicTexture(ImageIO.read(new URL((String)icon2)));
						successcache.put((String)icon2, t);
					} catch (Exception e) {
						failcache.add((String)icon2);
					}
					GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
					if(t != null)
						GlStateManager.bindTexture(t.getGlTextureId());
					else mc.renderEngine.bindTexture(new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png"));
					drawTexturedModalRect(k + n, b0, 0, 0, 256, 256);
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

	private void writeTexts(Object icon1, Object icon2, String text1, String text2, boolean locked1, boolean locked2){

		if(icon1 != null){
			if(icon1 instanceof ItemStack)
				writeText(1, locked1 ? NecronomiconText.TEST_95 : text1, 50, locked1);
			if(icon1 instanceof ResourceLocation || icon1 instanceof String)
				writeText(1, locked1 ? NecronomiconText.TEST_50_1 : text1, 100, locked1);
			if(icon1 instanceof CraftingStack)
				writeText(1, locked1 ? NecronomiconText.TEST_50_2 : text1, 95, locked1);
		} else writeText(1, locked1 ? NecronomiconText.TEST : text1, locked1);
		if(icon2 != null){
			if(icon2 instanceof ItemStack)
				writeText(2, locked2 ? NecronomiconText.TEST_95 : text2, 50, locked2);
			if(icon2 instanceof ResourceLocation || icon2 instanceof String)
				writeText(2, locked2 ? NecronomiconText.TEST_50_1 : text2, 100, locked2);
			if(icon2 instanceof CraftingStack)
				writeText(2, locked2 ? NecronomiconText.TEST_50_2 : text2, 95, locked2);
		} else writeText(2, locked2 ? NecronomiconText.TEST : text2, locked2);
	}

	@Override
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = localize(data.getTitle());
		boolean b = !isUnlocked(data.getCondition());
		getFontRenderer(b).drawSplitString(b ? NecronomiconText.LABEL_TEST : stuff, k + 20, b0 + 16, 116, 0xC40000);
		if(data.hasText()) writeText(2, b ? NecronomiconText.TEST : data.getText(), b);
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
		GlStateManager.enableDepth();
		render.renderItemAndEffectIntoGUI(stack, xPos, yPos);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRendererObj, stack, xPos, yPos, null);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();

		GlStateManager.disableLighting();
	}
}
