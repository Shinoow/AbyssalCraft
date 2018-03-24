/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.client.gui.necronomicon;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.necronomicon.GuiInstance;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.NecronomiconCondition;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiNecronomicon extends GuiScreen {

	private static ResourceLocation bookGuiTextures = new ResourceLocation("abyssalcraft:textures/gui/necronomicon.png");
	protected static final ResourceLocation MISSING_PICTURE = new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing.png");
	protected static final ResourceLocation MISSING_ITEM = new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing_item.png");
	protected static final ResourceLocation MISSING_RECIPE = new ResourceLocation("abyssalcraft", "textures/gui/necronomicon/missing_recipe.png");
	public static GuiNecronomicon currentNecro = new GuiNecronomicon();
	public final int guiWidth = 255;
	public final int guiHeight = 192;
	private int bookTotalTurnups = 2;
	/** Current turn-up, use to switch text between multiple pages */
	protected int currTurnup;
	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private ButtonCategory buttonCat1;
	private ButtonCategory buttonCat2;
	private ButtonCategory buttonCat3;
	private ButtonCategory buttonCat4;
	private ButtonCategory buttonCat5;
	private ButtonCategory buttonCat6;
	private ButtonCategory buttonCat7;
	private GuiButton buttonDone;
	private int bookType;
	/** Used to check if we're at a text entry (true), or a index (false) */
	protected boolean isInfo;
	private boolean isNecroInfo, isKnowledgeInfo;
	/** Used to invalidate the current Necronomicon Gui (like if a lower Necronomicon tries to read information for a higher one) */
	protected boolean isInvalid;
	public static final Map<String, DynamicTexture> successcache = Maps.newHashMap();
	public static final List<String> failcache = Lists.newArrayList();
	private static Chapter patreon;

	public GuiNecronomicon(){}

	public GuiNecronomicon(int par1){
		this();
		withBookType(par1);
	}

	public GuiNecronomicon withBookType(int par1){
		if(bookType == par1) return this;
		bookType = par1;
		switch(par1){
		case 0:
			bookGuiTextures = new ResourceLocation("abyssalcraft:textures/gui/necronomicon.png");
			break;
		case 1:
			bookGuiTextures = new ResourceLocation("abyssalcraft:textures/gui/necronomicon_cor.png");
			break;
		case 2:
			bookGuiTextures = new ResourceLocation("abyssalcraft:textures/gui/necronomicon_dre.png");
			break;
		case 3:
			bookGuiTextures = new ResourceLocation("abyssalcraft:textures/gui/necronomicon_omt.png");
			break;
		case 4:
			bookGuiTextures = new ResourceLocation("abyssalcraft:textures/gui/abyssalnomicon.png");
			break;
		}
		return this;
	}

	/**
	 * Called from the main game loop to update the screen.
	 */
	@Override
	public void updateScreen()
	{
		super.updateScreen();
	}

	/**
	 * Adds the buttons (and other controls) to the screen in question.
	 */
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
		buttonList.add(buttonCat1 = new ButtonCategory(3, i + 14, b0 + 24, this, NecronomiconText.LABEL_INFORMATION, false, ACItems.necronomicon));
		buttonList.add(buttonCat2 = new ButtonCategory(4, i + 14, b0 + 41, this, NecronomiconText.LABEL_SPELLBOOK, false, ACItems.necronomicon));
		buttonList.add(buttonCat3 = new ButtonCategory(5, i + 14, b0 + 58, this, NecronomiconText.LABEL_RITUALS, false, ACItems.necronomicon));
		if(bookType == 4)
			buttonList.add(buttonCat4 = new ButtonCategory(6, i + 14, b0 + 75, this, NecronomiconText.LABEL_HUH, false, ACItems.abyssalnomicon));
		else buttonList.add(buttonCat4 = new ButtonCategory(6, i + 14, b0 + 75, this, NecronomiconText.LABEL_HUH, false, ACItems.necronomicon));
		buttonList.add(buttonCat5 = new ButtonCategory(7, i + 14, b0 + 92, this, NecronomiconText.LABEL_KNOWLEDGE, false, ACItems.necronomicon));
		buttonList.add(buttonCat6 = new ButtonCategory(8, i + 14, b0 + 109, this, NecronomiconText.LABEL_MISC_INFORMATION, false, ACItems.necronomicon));
		if(!AbyssalCraftAPI.getNecronomiconData().isEmpty())
			buttonList.add(buttonCat7 = new ButtonCategory(9, i + 14, b0 + 126, this, NecronomiconText.LABEL_OTHER, false, ACItems.necronomicon));
		updateButtons();
	}

	protected Item getItem(int par1){
		//		if(par1 > getBookType())
		//			return ACItems.oblivion_catalyst;
		switch(par1){
		case 0:
			return ACItems.necronomicon;
		case 1:
			return ACItems.abyssal_wasteland_necronomicon;
		case 2:
			return ACItems.dreadlands_necronomicon;
		case 3:
			return ACItems.omothol_necronomicon;
		case 4:
			return ACItems.abyssalnomicon;
		default:
			return ACItems.oblivion_catalyst;
		}
	}

	/**
	 * Called when the screen is unloaded. Used to disable keyboard repeat events
	 */
	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < bookTotalTurnups - 1 && isInfo;
		buttonPreviousPage.visible = isInfo;
		buttonDone.visible = true;
		buttonCat1.visible = true;
		buttonCat2.visible = true;
		buttonCat3.visible = true;
		buttonCat4.visible = true;
		buttonCat5.visible = true;
		buttonCat6.visible = true;
		if(!AbyssalCraftAPI.getNecronomiconData().isEmpty())
			buttonCat7.visible = true;

	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			if (button.id == 0) {
				isInfo = false;
				isNecroInfo = false;
				isKnowledgeInfo = false;
				currTurnup = 0;
				mc.displayGuiScreen((GuiScreen)null);
			}else if (button.id == 1)
			{
				if (currTurnup < bookTotalTurnups - 1)
					++currTurnup;

			} else if (button.id == 2)
			{
				if(isInfo && currTurnup == 0){
					initGui();
					isInfo = false;
					isNecroInfo = false;
					isKnowledgeInfo = false;
				}
				else if (currTurnup > 0)
					--currTurnup;

			} else if (button.id == 3){

				NecroData data = new NecroData("information", NecronomiconText.LABEL_INFORMATION, 0, new Chapter("acinfo", NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0,
					new Page(1, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.ABYSSALCRAFT_1, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_1),
					new Page(2, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.BLANK, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_2),
					new Page(3, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.ABYSSALCRAFT_2, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_3),
					new Page(4, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.BLANK, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_4),
					new Page(5, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.ABYSSALCRAFT_3, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_5),
					new Page(6, NecronomiconText.LABEL_INFORMATION_ABYSSALCRAFT, 0, NecronomiconResources.BLANK, NecronomiconText.INFORMATION_ABYSSALCRAFT_PAGE_6)),
					AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("greatoldones"),
					new Page(1, NecronomiconText.LABEL_INFORMATION_ABYSSALNOMICON, 4, NecronomiconText.INFORMATION_ABYSSALNOMICON),
					patreon, new GuiInstance(0, NecronomiconText.LABEL_INFORMATION_MACHINES, "machines"){
					@Override public IUnlockCondition getCondition() { return new NecronomiconCondition(1); }
					@Override public GuiScreen getOpenGui(int bookType, GuiScreen parent) { return new GuiNecronomiconMachines(bookType, (GuiNecronomicon) parent); }
				}, AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("overworld"), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("abyssalwasteland"),
					AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("dreadlands"), AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("omothol"),
					AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("darkrealm"));

				mc.displayGuiScreen(new GuiNecronomiconEntry(bookType, data, this));
			} else if (button.id == 4)
				mc.displayGuiScreen(new GuiNecronomiconSpells(bookType, Minecraft.getMinecraft().thePlayer.getHeldItem(EnumHand.MAIN_HAND)));
			else if (button.id == 5){

				NecroData data = new NecroData("ritualinfo", NecronomiconText.LABEL_RITUALS, 0, NecronomiconText.RITUAL_INFO, AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("rituals"),
					new RitualGuiInstance(0, NecronomiconText.LABEL_NORMAL, "ritualsoverworld"),
					new RitualGuiInstance(1, NecronomiconText.LABEL_INFORMATION_ABYSSAL_WASTELAND, "ritualsabyssalwasteland"),
					new RitualGuiInstance(2, NecronomiconText.LABEL_INFORMATION_DREADLANDS, "ritualsdreadlands"),
					new RitualGuiInstance(3, NecronomiconText.LABEL_INFORMATION_OMOTHOL, "ritualsomothol"),
					new RitualGuiInstance(4, ACItems.abyssalnomicon.getUnlocalizedName() + ".name", "ritualsabyssalnomicon"));

				mc.displayGuiScreen(new GuiNecronomiconEntry(bookType, data, this));
			} else if (button.id == 6)
			{
				isInfo = true;
				isNecroInfo = true;
				if(bookType == 4)
					bookTotalTurnups = 1;
				else bookTotalTurnups = 2;
				drawButtons();
			} else if(button.id == 7){
				isInfo = true;
				isKnowledgeInfo = true;
				bookTotalTurnups = 2;
				drawButtons();
			} else if(button.id == 8)
				mc.displayGuiScreen(new GuiNecronomiconEntry(bookType, AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("miscinfo"), this, ACItems.necronomicon));
			else if (button.id == 9)
				mc.displayGuiScreen(new GuiNecronomiconOther(bookType));
			updateButtons();
		}
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char par1, int par2) throws IOException
	{
		super.keyTyped(par1, par2);

	}

	private void drawButtons(){
		buttonList.clear();
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true, false));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false, false));
	}

	public static void setPatreonInfo(Chapter info){
		if(Loader.instance().getLoaderState() == LoaderState.INITIALIZATION
			&& Loader.instance().activeModContainer().getModId().equals("abyssalcraft"))
			patreon = info;
	}

	/**
	 * Method used to write text on the page, override to add your own text to pages.
	 * @param x X-coordinate on screen
	 * @param y Y-coordinate on screen
	 */
	protected void drawInformationText(int x, int y){
		if(isNecroInfo) {
			if(currTurnup == 0){
				if(bookType < 4){
					writeText(1, NecronomiconText.NECRONOMICON_PAGE_1);
					writeText(2, NecronomiconText.NECRONOMICON_PAGE_2);
				} else {
					writeText(1, NecronomiconText.ABYSSALNOMICON_PAGE_1);
					writeText(2, NecronomiconText.ABYSSALNOMICON_PAGE_2);
				}
			}
			else if(currTurnup == 1)
				if(bookType < 4){
					writeText(1, NecronomiconText.NECRONOMICON_PAGE_3);
					writeText(2, NecronomiconText.NECRONOMICON_PAGE_4);
				}
		} else if(isKnowledgeInfo)
			if(currTurnup == 0) {
				writeText(1, NecronomiconText.KNOWLEDGE_INFO_1);
				writeText(2, NecronomiconText.KNOWLEDGE_INFO_2);
			}
			else if(currTurnup == 1) {
				writeText(1, NecronomiconText.KNOWLEDGE_INFO_3);
				writeText(2, NecronomiconText.TEST_95, 28, true);
			}
	}
	/**
	 * Index version of {@link #drawInformationText()}, called when {@link #isInfo} is false
	 */
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		int length;
		stuff = localize(NecronomiconText.LABEL_INDEX);
		length = fontRendererObj.getStringWidth(stuff);
		fontRendererObj.drawString(stuff, k + 50 - length, b0 + 16, 0);
	}

	public int getBookType(){
		return bookType;
	}

	public ResourceLocation getGuiTexture(){
		return bookGuiTextures;
	}

	public int getTurnupLimit(){
		return bookTotalTurnups;
	}

	public void setTurnupLimit(int i){
		bookTotalTurnups = i;
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		boolean unicode = fontRendererObj.getUnicodeFlag();
		fontRendererObj.setUnicodeFlag(true);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(bookGuiTextures);
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		drawTexturedModalRect(k, b0, 0, 0, guiWidth, guiHeight);
		String s;
		int l;
		String stuff;
		super.drawScreen(par1, par2, par3);

		if(isInfo){
			if(isNecroInfo || isKnowledgeInfo){
				stuff = localize(isNecroInfo ? NecronomiconText.LABEL_HUH : NecronomiconText.LABEL_KNOWLEDGE);
				fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			}
			s = I18n.format("necronomicon.turnupindicator", new Object[] {Integer.valueOf(currTurnup + 1), Integer.valueOf(bookTotalTurnups)});

			l = fontRendererObj.getStringWidth(s);
			if(getTurnupLimit() > 1)
				fontRendererObj.drawString(s, k - l + guiWidth - 22, b0 + 16, 0);
			drawInformationText(par1, par2);
		} else
			drawIndexText();

		fontRendererObj.setUnicodeFlag(unicode);
	}

	/**
	 * Fixed version of writeText used for pages with titles.
	 * @param page Which open page to write in (can be either 1 or 2)
	 * @param text A long string of text (max is 368 characters)
	 */
	protected void writeText(int page, String text){
		writeText(page, text, 28);
	}

	/**
	 * Fixed version of writeText used for pages with titles.
	 * @param page Which open page to write in (can be either 1 or 2)
	 * @param text A long string of text (max is 368 characters)
	 */
	protected void writeText(int page, String text, boolean aklo){
		writeText(page, text, 28, aklo);
	}

	/**
	 * Writes a bunch of text on a Necronomicon page.
	 * @param page Which open page to write in (can be either 1 or 2)
	 * @param text A long string of text (max is 368 characters)
	 * @param height The height where the text will appear at (0 is the top of the GUI)
	 */
	protected void writeText(int page, String text, int height){
		writeText(page, text, height, 0);
	}

	/**
	 * Writes a bunch of text on a Necronomicon page.
	 * @param page Which open page to write in (can be either 1 or 2)
	 * @param text A long string of text (max is 368 characters)
	 * @param height The height where the text will appear at (0 is the top of the GUI)
	 * @param aklo Whether or not to use the Aklo font instead of the normal
	 */
	protected void writeText(int page, String text, int height, boolean aklo){
		writeText(page, text, height, 0, aklo);
	}

	/**
	 * Writes a bunch of text on a Necronomicon page.
	 * @param page Which open page to write in (can be either 1 or 2)
	 * @param text A long string of text (max is 368 characters)
	 * @param height The height where the text will appear at (0 is the top of the GUI)
	 * @param width The width where the text will appear at (0 is the leftmost part of the page)
	 */
	protected void writeText(int page, String text, int height, int width){
		writeText(page, text, height, width, false);
	}

	/**
	 * Writes a bunch of text on a Necronomicon page.
	 * @param page Which open page to write in (can be either 1 or 2)
	 * @param text A long string of text (max is 368 characters)
	 * @param height The height where the text will appear at (0 is the top of the GUI)
	 * @param width The width where the text will appear at (0 is the leftmost part of the page)
	 * @param aklo Whether or not to use the Aklo font instead of the normal
	 */
	protected void writeText(int page, String text, int height, int width, boolean aklo){
		int k = (this.width - guiWidth) / 2;
		if(page > 2)
			throw new IndexOutOfBoundsException("Number is greater than 2 ("+page+")!");
		else if(page < 1)
			throw new IndexOutOfBoundsException("Number is smaller than 1 ("+page+")!");
		else if(text.length() > 368)
			throw new IndexOutOfBoundsException("Text is longer than 368 characters ("+text.length()+")!");
		else{
			if(page == 1)
				getFontRenderer(aklo).drawSplitString(localize(text), k + 20 + width, height, 107, 0);
			if(page == 2)
				getFontRenderer(aklo).drawSplitString(localize(text), k + 138 + width, height, 107, 0);
		}
	}

	public FontRenderer getFontRenderer(boolean aklo){
		if(aklo)
			return AbyssalCraftAPI.getAkloFont();
		return fontRendererObj;
	}

	protected String localize(String str){
		return I18n.format(str, new Object[0]);
	}

	private class RitualGuiInstance extends GuiInstance {

		protected RitualGuiInstance(int displayIcon, String title, String identifier){
			super(displayIcon, title, identifier);
		}

		@Override
		public IUnlockCondition getCondition() {
			return new NecronomiconCondition(displayIcon);
		}

		@Override
		public GuiScreen getOpenGui(int bookType, GuiScreen parent) {
			return new GuiNecronomiconRitualEntry(bookType, (GuiNecronomicon) parent, displayIcon);
		}
	}
}
