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
import net.minecraft.util.ResourceLocation;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonCategory;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiNecronomicon extends GuiScreen {

	private static ResourceLocation bookGuiTextures = new ResourceLocation("abyssalcraft:textures/gui/necronomicon.png");
	public final int guiWidth = 254;
	public final int guiHeight = 192;
	private int bookTotalTurnups = 2;
	/** Current turn-up, use to switch text between multiple pages */
	protected int currnTurnup;
	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private ButtonCategory buttonCat1;
	private ButtonCategory buttonCat2;
	private ButtonCategory buttonCat3;
	private ButtonCategory buttonCat4;
	private ButtonCategory buttonCat5;
	private GuiButton buttonDone;
	private int bookType;
	/** Used to check if we're at a text entry (true), or a index (false) */
	protected boolean isInfo = false;
	private boolean isNecroInfo = false;

	public GuiNecronomicon(){
		this(0);
	}

	public GuiNecronomicon(int par1){
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
		buttonList.add(buttonCat1 = new ButtonCategory(3, i + 10, b0 + 30, this, NecronomiconText.LABEL_INFORMATION, AbyssalCraft.necronomicon));
		buttonList.add(buttonCat2 = new ButtonCategory(4, i + 10, b0 + 55, this, NecronomiconText.LABEL_SPELLBOOK, AbyssalCraft.necronomicon));
		buttonList.add(buttonCat3 = new ButtonCategory(5, i + 10, b0 + 80, this, NecronomiconText.LABEL_RITUALS, AbyssalCraft.necronomicon));
		if(bookType == 4)
			buttonList.add(buttonCat4 = new ButtonCategory(6, i + 10, b0 + 105, this, NecronomiconText.LABEL_HUH, AbyssalCraft.abyssalnomicon));
		else buttonList.add(buttonCat4 = new ButtonCategory(6, i + 10, b0 + 105, this, NecronomiconText.LABEL_HUH, AbyssalCraft.necronomicon));
		if(!AbyssalCraftAPI.getNecronomiconData().isEmpty())
			buttonList.add(buttonCat5 = new ButtonCategory(7, i + 10, b0 + 130, this, NecronomiconText.LABEL_OTHER, AbyssalCraft.necronomicon));
		updateButtons();
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
		buttonNextPage.visible = currnTurnup < bookTotalTurnups - 1 && isInfo;
		buttonPreviousPage.visible = isInfo;
		buttonDone.visible = true;
		buttonCat1.visible = true;
		buttonCat2.visible = true;
		buttonCat3.visible = true;
		buttonCat4.visible = true;
		if(!AbyssalCraftAPI.getNecronomiconData().isEmpty())
			buttonCat5.visible = true;

	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
		{
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if (button.id == 1)
			{
				if (currnTurnup < bookTotalTurnups - 1)
					++currnTurnup;

			} else if (button.id == 2)
			{
				if(isInfo && currnTurnup == 0){
					initGui();
					isInfo = false;
					isNecroInfo = false;
				}
				else if (currnTurnup > 0)
					--currnTurnup;

			} else if (button.id == 3)
				mc.displayGuiScreen(new GuiNecronomiconInformation(bookType));
			else if (button.id == 4)
				mc.displayGuiScreen(new GuiNecronomiconSpells(bookType));
			else if (button.id == 5)
				mc.displayGuiScreen(new GuiNecronomiconRituals(bookType));
			else if (button.id == 6)
			{
				isInfo = true;
				isNecroInfo = true;
				if(bookType == 4)
					bookTotalTurnups = 1;
				else bookTotalTurnups = 2;
				drawButtons();
			} else if (button.id == 7)
				mc.displayGuiScreen(new GuiNecronomiconOther(bookType));
			updateButtons();
		}
	}

	/**
	 * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
	 */
	@Override
	protected void keyTyped(char par1, int par2)
	{
		super.keyTyped(par1, par2);

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

	/**
	 * Method used to write text on the page, override to add your own text to pages.
	 */
	protected void drawInformationText(){
		if(currnTurnup == 0){
			if(bookType < 4){
				writeText(1, NecronomiconText.NECRONOMICON_PAGE_1);
				writeText(2, NecronomiconText.NECRONOMICON_PAGE_2);
			} else {
				writeText(1, NecronomiconText.ABYSSALNOMICON_PAGE_1);
				writeText(2, NecronomiconText.ABYSSALNOMICON_PAGE_2);
			}
		}
		else if(currnTurnup == 1)
			if(bookType < 4){
				writeText(1, NecronomiconText.NECRONOMICON_PAGE_3);
				writeText(2, NecronomiconText.NECRONOMICON_PAGE_4);
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
		stuff = NecronomiconText.LABEL_INDEX;
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
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(bookGuiTextures);
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		drawTexturedModalRect(k, b0, 0, 0, guiWidth, guiHeight);
		String s;
		int l;
		String stuff;

		if(isInfo){
			if(isNecroInfo){
				stuff = NecronomiconText.LABEL_HUH;
				fontRendererObj.drawSplitString(stuff, k + 20, b0 + 16, 116, 0xC40000);
			}
			drawInformationText();
			s = I18n.format("book.pageIndicator", new Object[] {Integer.valueOf(currnTurnup + 1), Integer.valueOf(bookTotalTurnups)});

			l = fontRendererObj.getStringWidth(s);
			fontRendererObj.drawString(s, k - l + guiWidth - 22, b0 + 16, 0);
		} else
			drawIndexText();

		super.drawScreen(par1, par2, par3);
	}

	/**
	 * Fixed version of writeText used for pages with titles.
	 * @param page Which open page to write in (can be either 1 or 2)
	 * @param text A long string of text (max is 255 characters)
	 */
	protected void writeText(int page, String text){
		writeText(page, text, 28);
	}

	/**
	 * Writes a bunch of text on a Necronomicon page.
	 * @param page Which open page to write in (can be either 1 or 2)
	 * @param text A long string of text (max is 256 characters)
	 * @param height The height where the text will appear at (0 is the top of the GUI)
	 */
	protected void writeText(int page, String text, int height){
		int k = (width - guiWidth) / 2;
		if(page > 2)
			throw new IndexOutOfBoundsException("Number is greater than 2 ("+page+")!");
		else if(page < 1)
			throw new IndexOutOfBoundsException("Number is smaller than 1 ("+page+")!");
		else if(text.length() > 256)
			throw new IndexOutOfBoundsException("Text is longer than 256 characters ("+text.length()+")!");
		else{
			if(page == 1)
				fontRendererObj.drawSplitString(text, k + 20, height, 107, 0);
			if(page == 2)
				fontRendererObj.drawSplitString(text, k + 140, height, 107, 0);
		}
	}
}
