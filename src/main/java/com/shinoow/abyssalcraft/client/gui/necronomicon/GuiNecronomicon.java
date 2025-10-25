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

import java.io.IOException;
import java.net.URL;
import java.util.*;

import javax.imageio.ImageIO;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.NecronomiconCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.api.necronomicon.*;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Chapter;
import com.shinoow.abyssalcraft.api.necronomicon.NecroData.Page;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.*;
import com.shinoow.abyssalcraft.client.gui.necronomicon.entries.GuiNecronomiconChapterEntry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.entries.GuiNecronomiconEntry;
import com.shinoow.abyssalcraft.client.util.NecronomiconGuiHelper;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
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

	protected ButtonNextPage buttonNextPage, buttonNextPageLong;
	protected ButtonNextPage buttonPreviousPage, buttonPreviousPageLong;
	protected GuiButton buttonDone;
	protected ButtonHome buttonHome;
	protected ButtonInfo showNoteButtonLeft, showNoteButtonRight;

	// Page-specific buttons
	private ButtonCategory[] buttons;

	private int bookType;

	/** Used to check if we're at a text entry (true), or a index (false) */
	protected boolean isInfo;
	private boolean isNecroInfo, isKnowledgeInfo;
	/** Used to invalidate the current Necronomicon Gui (like if a lower Necronomicon tries to read information for a higher one) */
	protected boolean isInvalid;
	public static final Map<String, DynamicTexture> successcache = new HashMap<>();
	public static final List<String> failcache = new ArrayList<>();
	private INecroDataCapability cap;
	protected String unknown50_1, unknown50_2, unknown95, unknownFull;
	protected boolean showNote;
	private NecronomiconGuiHelper helper;
	protected Chapter reference1, reference2;
	public GuiNecronomicon parent;
	private int currentData;


	private NecroData rootData;

	public GuiNecronomicon(){
		createHelper();
		helper.updateContext();
		if(Minecraft.getMinecraft().player != null)
			cap = NecroDataCapability.getCap(Minecraft.getMinecraft().player);

		unknown50_1 = NecronomiconText.getRandomAklo(0);
		unknown50_2 = NecronomiconText.getRandomAklo(0);
		unknown95 = NecronomiconText.getRandomAklo(1);
		unknownFull = NecronomiconText.getRandomAklo(2);

		rootData = AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("root");
	}

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
		getHelper().updateDrawTime();
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

		initBaseButtons();

		updateButtons();
		getHelper().clearSidebar();
	}

	protected void initBaseButtons() {
		int i = (width - guiWidth) / 2;
		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, 156, true, false));
		buttonList.add(buttonNextPageLong = new ButtonNextPage(2, i + 203, 169, true, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(3, i + 18, 156, false, false));
		buttonList.add(buttonPreviousPageLong = new ButtonNextPage(4, i + 23, 169, false, true));
		buttonList.add(buttonHome = new ButtonHome(5, i + 118, 169));
		buttonList.add(showNoteButtonLeft = new ButtonInfo(6, i + 102, 170));
		buttonList.add(showNoteButtonRight = new ButtonInfo(7, i + 142, 170));

		initButtonsInner();
	}

	protected void initButtonsInner() {
		int i = (width - guiWidth) / 2;
		if(rootData != null)
			for(int n = 0; n < rootData.getContainedData().size(); n++){
				INecroData nd = rootData.getContainedData().get(n);
				buttonList.add(buttons[n] = new ButtonCategory(8 + n, i + (n < 7 ? 14 : 132), 26 + 17* (n < 7 ? n : n - 7),this, nd.getTitle(), getItem(nd.getDisplayIcon())).setLocked(!isUnlocked(nd.getResearch())));
			}
	}

	protected Item getItem(int par1){
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

	protected void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1 && isInfo;
		buttonNextPageLong.visible = currTurnup < getTurnupLimit() -5;
		buttonPreviousPage.visible = isInfo;
		buttonPreviousPageLong.visible = currTurnup > 4;
		buttonDone.visible = true;
		buttonHome.visible = false;
		showNoteButtonLeft.visible = reference1 != null && isInfo;
		showNoteButtonRight.visible = reference2 != null && isInfo;

		updateButtonsInner();
	}

	protected void updateButtonsInner() {
		if(rootData != null)
			for(int i = 0; i < rootData.getContainedData().size(); i++)
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
			} else if(button.id == 2){
				if(currTurnup < getTurnupLimit() -5)
					currTurnup += 5;
			} else if (button.id == 3){
				if(currTurnup == 0 && !isInfo) {
					if(this.getClass() != GuiNecronomicon.class)
						mc.displayGuiScreen(parent.withBookType(getBookType()));
					else mc.displayGuiScreen((GuiScreen)null);
				} else if(currTurnup == 0 && isInfo){
					isInfo = false;
					currentData = -1;
					initGui();
					setTurnupLimit(2);
				} else if (currTurnup > 0)
					--currTurnup;
			} else if(button.id == 4){
				if(currTurnup > 4)
					currTurnup -= 5;
			} else if(button.id == 5){
				if(!isInfo) {
					if(this.getClass() != GuiNecronomicon.class)
						mc.displayGuiScreen(parent.withBookType(getBookType()));
					else mc.displayGuiScreen((GuiScreen)null);
				}
				else {
					currTurnup = 0;
					isInfo = false;
					currentData = -1;
					initGui();
					setTurnupLimit(2);
				}
			} else if(button.id == 6) {
				if(reference1 != null)
					mc.displayGuiScreen(new GuiNecronomiconChapterEntry(getBookType(), reference1, this));
			} else if(button.id == 7) {
				if(reference2 != null)
					mc.displayGuiScreen(new GuiNecronomiconChapterEntry(getBookType(), reference2, this));
			} else actionPerformedInner(button);

			updateButtons();
		}
	}

	protected void actionPerformedInner(GuiButton button) {
		if(button.id >= 8 && rootData.getContainedData().size() >= button.id - 7){
			int i = button.id - 8;
			INecroData nd = rootData.getContainedData().get(i);
			if(isUnlocked(nd.getResearch()))
				if(nd instanceof GuiInstance)
					mc.displayGuiScreen(((GuiInstance)nd).getOpenGui(getBookType(), this));
				else if(nd instanceof NecroData)
					mc.displayGuiScreen(new GuiNecronomiconEntry(getBookType(), (NecroData)nd, this));
				else {
					currentData = i;
					isInfo = true;
				}
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

	/**
	 * Method used to write text on the page, override to add your own text to pages.
	 * @param x X-coordinate on screen
	 * @param y Y-coordinate on screen
	 */
	protected void drawInformationText(int x, int y){

		if(currentData != -1)
			drawChapterOrPage(rootData.getContainedData().get(currentData), x, y);
		updateButtons();
	}

	/**
	 * Index version of {@link #drawInformationText()}, called when {@link #isInfo} is false
	 */
	protected void drawIndexText(){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String stuff;
		stuff = localize(NecronomiconText.LABEL_INDEX);
		fontRenderer.drawString(stuff, k + 17, b0 + 16, 0);
	}

	/**
	 * 0 = Necronomicon<br>
	 * 1 = Abyssal Wasteland Necronomicon<br>
	 * 2 = Dreadlands Necronomicon<br>
	 * 3 = Omothol Necronomicon<br>
	 * 4 = Abyssalnomicon
	 */
	public int getBookType(){
		return bookType;
	}

	/**
	 * Highest Book Type or Knowledge Level
	 */
	public int getKnowledgeLevel() {
		return Math.max(bookType, cap != null ? cap.getKnowledgeLevel() : 0); // Shouldn't be null, but eh
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
		boolean unicode = fontRenderer.getUnicodeFlag();
		fontRenderer.setUnicodeFlag(true);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(bookGuiTextures);
		int k = (width - guiWidth) / 2;
		drawTexturedModalRect(k, 2, 0, 0, guiWidth, guiHeight);
		String s;
		int l;
		super.drawScreen(par1, par2, par3);

		if(isInfo){
			if(isNecroInfo || isKnowledgeInfo){
				drawTitle(localize(isNecroInfo ? NecronomiconText.LABEL_HUH : NecronomiconText.LABEL_KNOWLEDGE));
			}
			s = localize("necronomicon.turnupindicator", Integer.valueOf(currTurnup + 1), Integer.valueOf(bookTotalTurnups));

			l = fontRenderer.getStringWidth(s);
			if(getTurnupLimit() > 1)
				fontRenderer.drawString(s, k - l + guiWidth - 22, 18, 0);
			drawInformationText(par1, par2);

			renderSidebarIndex();
		} else
			drawIndexText();

		fontRenderer.setUnicodeFlag(unicode);
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
				getFontRenderer(aklo).drawSplitString(localize(text), k + 17 + width, height, 107, 0);
			if(page == 2)
				getFontRenderer(aklo).drawSplitString(localize(text), k + 135 + width, height, 107, 0);
		}
	}

	public FontRenderer getFontRenderer(boolean aklo){
		if(aklo)
			return AbyssalCraftAPI.getAkloFont();
		return fontRenderer;
	}

	protected String localize(String str){
		return localize(str, new Object[0]);
	}

	protected String localize(String str, Object...parameters) {
		return I18n.format(str, parameters);
	}

	protected boolean isUnlocked(IResearchItem ri){
		for(IUnlockCondition cnd : ri.getUnlockConditions())
			if(cnd instanceof NecronomiconCondition) // Re-name???
				return getKnowledgeLevel() >= (int)cnd.getConditionObject();
				return cap.isUnlocked(ri, mc.player);
	}

	public void renderObject(int xPos, int yPos, Object obj, int mx, int my) {
		getHelper().renderObject(xPos, yPos, obj, mx, my, mc);
	}

	public void renderItem(int xPos, int yPos, ItemStack stack, int mx, int my) {
		getHelper().renderItem(xPos, yPos, stack, mx, my, mc);
	}

	protected void renderTooltip(int x, int y) {
		getHelper().renderTooltip(x, y, mc);
	}

	protected void drawChapterOrPage(INecroData data, int x, int y){
		if(data instanceof Chapter)
			drawChapter((Chapter)data, x, y);
		else{
			drawTitle(localize(data.getTitle(), new Object[0]));
			setTurnupLimit(1);

			getHelper().clearTooltipStack();

			setReferences((Page)data, null);
			drawPage((Page)data, false, 1, x, y);
			renderTooltip(x, y);
		}
	}

	protected void drawChapter(Chapter chapter, int x, int y){
		drawTitle(localize(chapter.getTitle(), new Object[0]));
		setTurnupLimit(chapter.getTurnupAmount());

		int num = (currTurnup + 1)*2;
		Page page1 = chapter.getPage(num-1);
		Page page2 = chapter.getPage(num);
		setReferences(page1, page2);

		getHelper().clearTooltipStack();

		drawPage(page1, false, num-1, x, y);
		drawPage(page2, true, num, x, y);

		renderTooltip(x, y);
	}

	protected void setReferences(Page page1, Page page2) {
		reference1 = reference2 = null;
		if(page1 != null)
			reference1 = page1.getReference();
		if(page2 != null)
			reference2 = page2.getReference();
	}

	protected void drawPage(Page page, boolean right, int displayNum, int x, int y) {
		if(page == null) return;
		int k = (width - guiWidth) / 2;
		String text = page.getText();
		Object icon = page.getIcon();
		boolean locked = !isUnlocked(page.getResearch());
		int offset = right ? 123 : 0;
		int pageNum = right ? 2 : 1;

		// Write text based on what the 'icon' Object is
		if(icon instanceof ItemStack)	
			writeText(pageNum, locked ? unknown95 : text, 50, locked);
		if(icon instanceof ResourceLocation || icon instanceof String)
			writeText(pageNum, locked ? unknown50_1 : text, 100, locked);
		if(icon instanceof CraftingStack)
			writeText(pageNum, locked ? unknown50_2 : text, 95, locked);
		else writeText(pageNum, locked ? unknownFull : text, locked);

		writeText(pageNum, String.valueOf(displayNum), 165, 50);

		// draw the 'icon' Object
		drawIcon(icon, locked, offset, x, y);

	}

	protected void drawIcon(Object icon, boolean locked, int offset, int x, int y) {
		int k = (width - guiWidth) / 2;
		if(icon instanceof ItemStack){
			if(locked){
				drawTexture(MISSING_ITEM, offset);
			} else {
				drawTexture(NecronomiconResources.ITEM, offset);
				renderItem(k + 60 + offset, 30,(ItemStack)icon, x, y);
			}
		}
		if(icon instanceof ResourceLocation){
			drawTexture(locked ? MISSING_PICTURE : (ResourceLocation)icon, offset);
		}
		if(icon instanceof CraftingStack){
			if(locked){
				drawTexture(MISSING_RECIPE, offset);
			} else {
				drawTexture(NecronomiconResources.CRAFTING, offset);
				boolean unicode = fontRenderer.getUnicodeFlag();
				fontRenderer.setUnicodeFlag(false);
				renderItem(k + 93 + offset, 54,((CraftingStack)icon).getOutput(), x, y);
				fontRenderer.setUnicodeFlag(unicode);
				for(int i = 0; i <= 2; i++){
					renderObject(k + 24 + offset +i*21, 33,((CraftingStack)icon).getRecipe()[i], x, y);
					renderObject(k + 24 + offset +i*21, 54,((CraftingStack)icon).getRecipe()[3+i], x, y);
					renderObject(k + 24 + offset +i*21, 75,((CraftingStack)icon).getRecipe()[6+i], x, y);
				}
			}
		}
		if(icon instanceof String)
			if(locked || failcache.contains(icon)){
				drawTexture(MISSING_PICTURE, offset);
			} else if(successcache.get(icon) != null){
				GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
				GlStateManager.bindTexture(successcache.get(icon).getGlTextureId());
				drawTexturedModalRect(k + offset, 2, 0, 0, 256, 256);
			} else {
				drawTextureString((String)icon, offset);
			}
	}

	protected int getButtonHeight(int n) {
		return 26 + 17 * (n);
	}

	protected void drawTitle(String title) {
		int k = (width - guiWidth) / 2;
		fontRenderer.drawSplitString(title, k + 17, 18, 116, 0xC40000);
	}

	protected void renderSidebarIndex() {
		int k = (width - guiWidth) / 2;
		if(getHelper().isSiderbarNotNull())
			fontRenderer.drawSplitString(getHelper().getSidebar(), k + 17, 10, 256, 0xC40000);
	}

	protected void drawTexture(ResourceLocation texture) {
		drawTexture(texture, 0);
	}

	protected void drawTexture(ResourceLocation texture, int offset) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(texture);
		int k = (width - guiWidth) / 2;
		drawTexturedModalRect(k + offset, 2, 0, 0, guiWidth, guiHeight);
	}

	protected void drawTextureString(String str) {
		drawTextureString(str, 0);
	}

	protected void drawTextureString(String str, int offset) {
		int k = (width - guiWidth) / 2;
		DynamicTexture t = null;
		try {
			t = new DynamicTexture(ImageIO.read(new URL(str)));
			successcache.put(str, t);
		} catch (Exception e) {
			failcache.add(str);
		}
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		if(t != null)
			GlStateManager.bindTexture(t.getGlTextureId());
		else mc.renderEngine.bindTexture(MISSING_PICTURE);
		drawTexturedModalRect(k + offset, 2, 0, 0, 256, 256);
	}

	private void createHelper() {
		if(helper == null)
			helper = new NecronomiconGuiHelper();
	}

	// Grabs helper
	protected NecronomiconGuiHelper getHelper() {
		// Create a new if null
		createHelper();
		return helper;
	}
}
