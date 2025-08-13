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
import java.util.*;

import org.lwjgl.input.Keyboard;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.NecronomiconCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.INecroDataCapability;
import com.shinoow.abyssalcraft.api.knowledge.condition.caps.NecroDataCapability;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.client.GuiRenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

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
	private ButtonNextPage buttonNextPage, buttonPreviousPage;
	private ButtonCategory buttonCat1, buttonCat2, buttonCat3, buttonCat4, buttonCat5, buttonCat6, buttonCat7;
	private GuiButton buttonDone;
	private int bookType;
	private static final int cycleTime = 1000;
	private long startTime, drawTime;
	/** Used to check if we're at a text entry (true), or a index (false) */
	protected boolean isInfo;
	private boolean isNecroInfo, isKnowledgeInfo;
	/** Used to invalidate the current Necronomicon Gui (like if a lower Necronomicon tries to read information for a higher one) */
	protected boolean isInvalid;
	public static final Map<String, DynamicTexture> successcache = new HashMap<>();
	public static final List<String> failcache = new ArrayList<>();
	private INecroDataCapability cap;
	protected String unknown50_1, unknown50_2, unknown95, unknownFull;
	protected static String sidebarIndex;
	protected boolean showNote;

	public GuiNecronomicon(){
		if(Minecraft.getMinecraft().player != null)
			cap = NecroDataCapability.getCap(Minecraft.getMinecraft().player);
		long time = System.currentTimeMillis();
		startTime = time - cycleTime;
		drawTime = time;
		unknown50_1 = NecronomiconText.getRandomAklo(0);
		unknown50_2 = NecronomiconText.getRandomAklo(0);
		unknown95 = NecronomiconText.getRandomAklo(1);
		unknownFull = NecronomiconText.getRandomAklo(2);
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
		drawTime = System.currentTimeMillis();
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
		buttonList.add(buttonCat4 = new ButtonCategory(6, i + 14, b0 + 75, this, NecronomiconText.LABEL_POTENTIAL_ENERGY, false, ACItems.necronomicon));
		if(bookType == 4)
			buttonList.add(buttonCat5 = new ButtonCategory(7, i + 14, b0 + 92, this, NecronomiconText.LABEL_HUH, false, ACItems.abyssalnomicon));
		else buttonList.add(buttonCat5 = new ButtonCategory(7, i + 14, b0 + 92, this, NecronomiconText.LABEL_HUH, false, ACItems.necronomicon));
		buttonList.add(buttonCat6 = new ButtonCategory(8, i + 14, b0 + 109, this, NecronomiconText.LABEL_KNOWLEDGE, false, ACItems.necronomicon));
		if(!AbyssalCraftAPI.getNecronomiconData().isEmpty())
			buttonList.add(buttonCat7 = new ButtonCategory(9, i + 132, b0 + 24, this, NecronomiconText.LABEL_OTHER, false, ACItems.necronomicon));
		updateButtons();
		sidebarIndex = "";
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
			if (button.id == 0){
				isInfo = false;
				isNecroInfo = false;
				isKnowledgeInfo = false;
				currTurnup = 0;
				mc.displayGuiScreen((GuiScreen)null);
			} else if (button.id == 1){
				if (currTurnup < bookTotalTurnups - 1)
					++currTurnup;

			} else if (button.id == 2){
				if(isInfo && currTurnup == 0){
					initGui();
					isInfo = false;
					isNecroInfo = false;
					isKnowledgeInfo = false;
				}
				else if (currTurnup > 0)
					--currTurnup;

			} else if (button.id == 3)
				mc.displayGuiScreen(new GuiNecronomiconEntry(bookType, AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("information"), this));
			else if (button.id == 4)
				mc.displayGuiScreen(new GuiNecronomiconSpells(bookType, Minecraft.getMinecraft().player.getHeldItem(EnumHand.MAIN_HAND)));
			else if (button.id == 5)
				mc.displayGuiScreen(new GuiNecronomiconEntry(bookType, AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("ritualinfo"), this));
			else if (button.id == 6)
				mc.displayGuiScreen(new GuiNecronomiconEntry(bookType, AbyssalCraftAPI.getInternalNDHandler().getInternalNecroData("potentialenergy"), this));
			else if (button.id == 7){
				isInfo = true;
				isNecroInfo = true;
				if(bookType == 4)
					bookTotalTurnups = 1;
				else bookTotalTurnups = 2;
				drawButtons();
			} else if(button.id == 8){
				isInfo = true;
				isKnowledgeInfo = true;
				bookTotalTurnups = 2;
				drawButtons();
			} else if(button.id == 9)
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
				writeText(2, unknown95, 28, true);
			}
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
		byte b0 = 2;
		drawTexturedModalRect(k, b0, 0, 0, guiWidth, guiHeight);
		String s;
		int l;
		String stuff;
		super.drawScreen(par1, par2, par3);

		if(isInfo){
			if(isNecroInfo || isKnowledgeInfo){
				stuff = localize(isNecroInfo ? NecronomiconText.LABEL_HUH : NecronomiconText.LABEL_KNOWLEDGE);
				fontRenderer.drawSplitString(stuff, k + 17, b0 + 16, 116, 0xC40000);
			}
			s = I18n.format("necronomicon.turnupindicator", Integer.valueOf(currTurnup + 1), Integer.valueOf(bookTotalTurnups));

			l = fontRenderer.getStringWidth(s);
			if(getTurnupLimit() > 1)
				fontRenderer.drawString(s, k - l + guiWidth - 22, b0 + 16, 0);
			drawInformationText(par1, par2);

			if(sidebarIndex != null && sidebarIndex.length() > 0)
				fontRenderer.drawSplitString(sidebarIndex, k + 17, b0 + 8, 256, 0xC40000);
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

	protected ItemStack tooltipStack;

	private boolean list(Object obj){
		return obj == null ? false : obj instanceof ItemStack[] || obj instanceof String || obj instanceof List || obj instanceof Ingredient ||
				obj instanceof ItemStack && ((ItemStack)obj).getHasSubtypes() &&  ((ItemStack)obj).getMetadata() == OreDictionary.WILDCARD_VALUE;
	}

	private List<ItemStack> getList(Object obj){
		List<ItemStack> l = new ArrayList<>();

		if(obj instanceof ItemStack[]) {
			for(ItemStack stack : (ItemStack[])obj)
				if(!stack.isEmpty())
					l.add(stack.copy());
		} else if(obj instanceof String) {
			for(ItemStack stack : OreDictionary.getOres((String)obj))
				if(!stack.isEmpty())
					l.add(stack.copy());
		} else if(obj instanceof List) {
			for(ItemStack stack : (List<ItemStack>) obj)
				if(!stack.isEmpty())
					l.add(stack.copy());
		} else if(obj instanceof Ingredient) {
			for(ItemStack stack : ((Ingredient) obj).getMatchingStacks())
				if(!stack.isEmpty())
					l.add(stack.copy());
		} else if(obj instanceof ItemStack) {
			NonNullList<ItemStack> list = NonNullList.create();
			((ItemStack)obj).getItem().getSubItems(((ItemStack)obj).getItem().getCreativeTab(), list);
			for(ItemStack stack : list)
				if(!stack.isEmpty())
					l.add(stack.copy());
		}
		return l;
	}

	public void renderObject(int xPos, int yPos, Object obj, int mx, int my) {
		if(list(obj)) {
			List<ItemStack> list = getList(obj);
			if(!list.isEmpty()) {
				int index = (int)((drawTime - startTime) / cycleTime) % list.size();
				renderItem(xPos, yPos, list.get(index), mx, my);
			} else if(obj instanceof ItemStack)
				renderItem(xPos, yPos, APIUtils.convertToStack(obj), mx, my);
		} else
			renderItem(xPos, yPos, APIUtils.convertToStack(obj), mx, my);
	}

	public void renderItem(int xPos, int yPos, ItemStack stack, int mx, int my)
	{
		if(stack == null || stack.isEmpty()) return;

		if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			stack.setItemDamage(0);

		RenderItem render = mc.getRenderItem();
		if(mx > xPos && mx < xPos+16 && my > yPos && my < yPos+16)
			tooltipStack = stack;

		GlStateManager.pushMatrix();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.enableRescaleNormal();
		GlStateManager.enableDepth();
		render.renderItemAndEffectIntoGUI(stack, xPos, yPos);
		render.renderItemOverlayIntoGUI(mc.fontRenderer, stack, xPos, yPos, null);
		RenderHelper.disableStandardItemLighting();
		GlStateManager.popMatrix();

		GlStateManager.disableLighting();
	}

	public void renderTooltip(int x, int y) {
		if(tooltipStack != null)
		{
			List<String> tooltipData = tooltipStack.getTooltip(mc.player, TooltipFlags.NORMAL);
			List<String> parsedTooltip = new ArrayList<>();
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

	protected void updateSidebarIndex(GuiNecronomicon gui) {
		sidebarIndex = buildSidebarIndex(gui);
	}

	protected String buildSidebarIndex(GuiNecronomicon gui) {
		String str = "";
		if(gui instanceof GuiNecronomiconEntry)
			str = rec((GuiNecronomiconEntry) gui, "");
		return str;
	}

	private String rec(GuiNecronomiconEntry gui, String str) {
		if(gui.data != null)
			str = appendTitle(localize(gui.data.getTitle()), str);
		if(gui.parent instanceof GuiNecronomiconEntry)
			return rec((GuiNecronomiconEntry) gui.parent, str);
		return str;
	}

	private String appendTitle(String str, String str1) {
		String res = str;
		if(str1 != null && str1.length() > 0)
			res += " > " + str1;
		return res;
	}
}
