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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.GuiRenderHelper;
import com.shinoow.abyssalcraft.client.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.client.lib.NecronomiconText;

public class GuiNecronomiconRitualEntry extends GuiNecronomicon {

	private ButtonNextPage buttonNextPage;
	private ButtonNextPage buttonPreviousPage;
	private GuiButton buttonDone;
	private GuiNecronomicon parent;
	private Map<Integer, String> dimToString = Maps.newHashMap();
	/** Used to separate which rituals this entry should display */
	private int ritualnum;
	private List<NecronomiconRitual> rituals = Lists.newArrayList();

	public GuiNecronomiconRitualEntry(int bookType, GuiNecronomicon gui, int ritualnum){
		super(bookType);
		parent = gui;
		isInfo = true;
		this.ritualnum = ritualnum;
	}

	@Override
	public void initGui(){
		initStuff();
		buttonList.clear();
		Keyboard.enableRepeatEvents(true);

		buttonList.add(buttonDone = new GuiButton(0, width / 2 - 100, 4 + guiHeight, 200, 20, I18n.format("gui.done", new Object[0])));

		int i = (width - guiWidth) / 2;
		byte b0 = 2;
		buttonList.add(buttonNextPage = new ButtonNextPage(1, i + 215, b0 + 154, true));
		buttonList.add(buttonPreviousPage = new ButtonNextPage(2, i + 18, b0 + 154, false));

		updateButtons();
	}

	private void updateButtons()
	{
		buttonNextPage.visible = currTurnup < getTurnupLimit() - 1;
		buttonPreviousPage.visible = true;
		buttonDone.visible = true;
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		if (button.enabled)
			if (button.id == 0)
				mc.displayGuiScreen((GuiScreen)null);
			else if(button.id == 1){
				if (currTurnup < getTurnupLimit() -1)
					++currTurnup;
			} else if (button.id == 2)
				if(currTurnup == 0){
					isInfo = false;
					mc.displayGuiScreen(parent);
				} else if (currTurnup > 0)
					--currTurnup;
		updateButtons();
	}
	
	@Override
	protected void drawInformationText(int x, int y){
		if(currTurnup == 0)
			drawPage(rituals.get(0), x, y);
		else if(currTurnup == 1 && rituals.size() >= 2)
			drawPage(rituals.get(1), x, y);
		else if(currTurnup == 2 && rituals.size() >= 3)
			drawPage(rituals.get(2), x, y);
		else if(currTurnup == 3 && rituals.size() >= 4)
			drawPage(rituals.get(3), x, y);
		else if(currTurnup == 4 && rituals.size() >= 5)
			drawPage(rituals.get(4), x, y);
		else if(currTurnup == 5 && rituals.size() >= 6)
			drawPage(rituals.get(5), x, y);
		else if(currTurnup == 6 && rituals.size() >= 7)
			drawPage(rituals.get(6), x, y);
		else if(currTurnup == 7 && rituals.size() >= 8)
			drawPage(rituals.get(7), x, y);
		else if(currTurnup == 8 && rituals.size() >= 9)
			drawPage(rituals.get(8), x, y);
		else if(currTurnup == 9 && rituals.size() >= 10)
			drawPage(rituals.get(9), x, y);
		else if(currTurnup == 10 && rituals.size() >= 11)
			drawPage(rituals.get(10), x, y);
		else if(currTurnup == 11 && rituals.size() >= 12)
			drawPage(rituals.get(11), x, y);
		else if(currTurnup == 12 && rituals.size() >= 13)
			drawPage(rituals.get(12), x, y);
		else if(currTurnup == 13 && rituals.size() >= 14)
			drawPage(rituals.get(13), x, y);
		else if(currTurnup == 14 && rituals.size() >= 15)
			drawPage(rituals.get(14), x, y);
		else if(currTurnup == 15 && rituals.size() >= 16)
			drawPage(rituals.get(15), x, y);
		else if(currTurnup == 16 && rituals.size() >= 17)
			drawPage(rituals.get(16), x, y);
		else if(currTurnup == 17 && rituals.size() >= 18)
			drawPage(rituals.get(17), x, y);
		else if(currTurnup == 18 && rituals.size() >= 19)
			drawPage(rituals.get(18), x, y);
		else if(currTurnup == 19 && rituals.size() >= 20)
			drawPage(rituals.get(19), x, y);
		else if(currTurnup == 20 && rituals.size() >= 21)
			drawPage(rituals.get(20), x, y);
		else if(currTurnup == 21 && rituals.size() >= 22)
			drawPage(rituals.get(21), x, y);
		else if(currTurnup == 22 && rituals.size() >= 23)
			drawPage(rituals.get(22), x, y);
		else if(currTurnup == 23 && rituals.size() >= 24)
			drawPage(rituals.get(23), x, y);
		else if(currTurnup == 24 && rituals.size() >= 25)
			drawPage(rituals.get(24), x, y);
		else if(currTurnup == 25 && rituals.size() >= 26)
			drawPage(rituals.get(25), x, y);
		else if(currTurnup == 26 && rituals.size() >= 27)
			drawPage(rituals.get(26), x, y);
		else if(currTurnup == 27 && rituals.size() >= 28)
			drawPage(rituals.get(27), x, y);
		else if(currTurnup == 28 && rituals.size() >= 29)
			drawPage(rituals.get(28), x, y);
		else if(currTurnup == 29 && rituals.size() >= 30)
			drawPage(rituals.get(29), x, y);
		else if(currTurnup == 30 && rituals.size() >= 31)
			drawPage(rituals.get(30), x, y);
		else if(currTurnup == 31 && rituals.size() >= 32)
			drawPage(rituals.get(31), x, y);
		else if(currTurnup == 32 && rituals.size() >= 33)
			drawPage(rituals.get(32), x, y);
		else if(currTurnup == 33 && rituals.size() >= 34)
			drawPage(rituals.get(33), x, y);
		else if(currTurnup == 34 && rituals.size() >= 35)
			drawPage(rituals.get(34), x, y);
		else if(currTurnup == 35 && rituals.size() >= 36)
			drawPage(rituals.get(35), x, y);
		else if(currTurnup == 36 && rituals.size() >= 37)
			drawPage(rituals.get(36), x, y);
		else if(currTurnup == 37 && rituals.size() >= 38)
			drawPage(rituals.get(37), x, y);
		else if(currTurnup == 38 && rituals.size() >= 39)
			drawPage(rituals.get(38), x, y);
		else if(currTurnup == 39 && rituals.size() >= 40)
			drawPage(rituals.get(39), x, y);
		else if(currTurnup == 40 && rituals.size() >= 41)
			drawPage(rituals.get(40), x, y);
		else if(currTurnup == 41 && rituals.size() >= 42)
			drawPage(rituals.get(41), x, y);
		else if(currTurnup == 42 && rituals.size() >= 43)
			drawPage(rituals.get(42), x, y);
		else if(currTurnup == 43 && rituals.size() >= 44)
			drawPage(rituals.get(43), x, y);
		else if(currTurnup == 44 && rituals.size() >= 45)
			drawPage(rituals.get(44), x, y);
		else if(currTurnup == 45 && rituals.size() >= 46)
			drawPage(rituals.get(45), x, y);
		else if(currTurnup == 46 && rituals.size() >= 47)
			drawPage(rituals.get(46), x, y);
		else if(currTurnup == 47 && rituals.size() >= 48)
			drawPage(rituals.get(47), x, y);
		else if(currTurnup == 48 && rituals.size() >= 49)
			drawPage(rituals.get(48), x, y);
		else if(currTurnup == 49 && rituals.size() >= 50)
			drawPage(rituals.get(49), x, y);
		else if(currTurnup == 50 && rituals.size() >= 51)
			drawPage(rituals.get(50), x, y);
		else if(currTurnup == 51 && rituals.size() >= 52)
			drawPage(rituals.get(51), x, y);
		else if(currTurnup == 52 && rituals.size() >= 53)
			drawPage(rituals.get(52), x, y);
		else if(currTurnup == 53 && rituals.size() >= 54)
			drawPage(rituals.get(53), x, y);
		else if(currTurnup == 54 && rituals.size() >= 55)
			drawPage(rituals.get(54), x, y);
		else if(currTurnup == 55 && rituals.size() >= 56)
			drawPage(rituals.get(55), x, y);
		else if(currTurnup == 56 && rituals.size() >= 57)
			drawPage(rituals.get(56), x, y);
		else if(currTurnup == 57 && rituals.size() >= 58)
			drawPage(rituals.get(57), x, y);
		else if(currTurnup == 58 && rituals.size() >= 59)
			drawPage(rituals.get(58), x, y);
		else if(currTurnup == 59 && rituals.size() >= 60)
			drawPage(rituals.get(59), x, y);
		else if(currTurnup == 60 && rituals.size() >= 61)
			drawPage(rituals.get(60), x, y);
		else if(currTurnup == 61 && rituals.size() >= 62)
			drawPage(rituals.get(61), x, y);
		else if(currTurnup == 62 && rituals.size() >= 63)
			drawPage(rituals.get(62), x, y);
		else if(currTurnup == 63 && rituals.size() >= 64)
			drawPage(rituals.get(63), x, y);
		else if(currTurnup == 64 && rituals.size() >= 65)
			drawPage(rituals.get(64), x, y);
		else if(currTurnup == 65 && rituals.size() >= 66)
			drawPage(rituals.get(65), x, y);
		else if(currTurnup == 66 && rituals.size() >= 67)
			drawPage(rituals.get(66), x, y);
		else if(currTurnup == 67 && rituals.size() >= 68)
			drawPage(rituals.get(67), x, y);
		else if(currTurnup == 68 && rituals.size() >= 69)
			drawPage(rituals.get(68), x, y);
		else if(currTurnup == 69 && rituals.size() >= 70)
			drawPage(rituals.get(69), x, y);
		else if(currTurnup == 70 && rituals.size() >= 71)
			drawPage(rituals.get(70), x, y);
		else if(currTurnup == 71 && rituals.size() >= 72)
			drawPage(rituals.get(71), x, y);
		else if(currTurnup == 72 && rituals.size() >= 73)
			drawPage(rituals.get(72), x, y);
		else if(currTurnup == 73 && rituals.size() >= 74)
			drawPage(rituals.get(73), x, y);
		else if(currTurnup == 74 && rituals.size() >= 75)
			drawPage(rituals.get(74), x, y);
		else if(currTurnup == 75 && rituals.size() >= 76)
			drawPage(rituals.get(75), x, y);
		else if(currTurnup == 76 && rituals.size() >= 77)
			drawPage(rituals.get(76), x, y);
		else if(currTurnup == 77 && rituals.size() >= 78)
			drawPage(rituals.get(77), x, y);
		else if(currTurnup == 78 && rituals.size() >= 79)
			drawPage(rituals.get(78), x, y);
		else if(currTurnup == 79 && rituals.size() >= 80)
			drawPage(rituals.get(79), x, y);
		else if(currTurnup == 80 && rituals.size() >= 81)
			drawPage(rituals.get(80), x, y);
		else if(currTurnup == 81 && rituals.size() >= 82)
			drawPage(rituals.get(81), x, y);
		else if(currTurnup == 82 && rituals.size() >= 83)
			drawPage(rituals.get(82), x, y);
		else if(currTurnup == 83 && rituals.size() >= 84)
			drawPage(rituals.get(83), x, y);
		else if(currTurnup == 84 && rituals.size() >= 85)
			drawPage(rituals.get(84), x, y);
		else if(currTurnup == 85 && rituals.size() >= 86)
			drawPage(rituals.get(85), x, y);
		else if(currTurnup == 86 && rituals.size() >= 87)
			drawPage(rituals.get(86), x, y);
		else if(currTurnup == 87 && rituals.size() >= 88)
			drawPage(rituals.get(87), x, y);
		else if(currTurnup == 88 && rituals.size() >= 89)
			drawPage(rituals.get(88), x, y);
		else if(currTurnup == 89 && rituals.size() >= 90)
			drawPage(rituals.get(89), x, y);
		else if(currTurnup == 90 && rituals.size() >= 91)
			drawPage(rituals.get(90), x, y);
		else if(currTurnup == 91 && rituals.size() >= 92)
			drawPage(rituals.get(91), x, y);
		else if(currTurnup == 92 && rituals.size() >= 93)
			drawPage(rituals.get(92), x, y);
		else if(currTurnup == 93 && rituals.size() >= 94)
			drawPage(rituals.get(93), x, y);
		else if(currTurnup == 94 && rituals.size() >= 95)
			drawPage(rituals.get(94), x, y);
		else if(currTurnup == 95 && rituals.size() >= 96)
			drawPage(rituals.get(95), x, y);
		else if(currTurnup == 96 && rituals.size() >= 97)
			drawPage(rituals.get(96), x, y);
		else if(currTurnup == 97 && rituals.size() >= 98)
			drawPage(rituals.get(97), x, y);
		else if(currTurnup == 98 && rituals.size() >= 99)
			drawPage(rituals.get(98), x, y);
		else if(currTurnup == 99 && rituals.size() >= 100)
			drawPage(rituals.get(99), x, y);
	}

	private void drawPage(NecronomiconRitual ritual, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String title = ritual.getLocalizedName();
		fontRendererObj.drawSplitString(title, k + 20, b0 + 16, 116, 0xC40000);

		if(ritual.canRemnantAid())
			fontRendererObj.drawSplitString(NecronomiconText.LABEL_REMNANT_HELP, k + 138, 164, 107, 0xC40000);
		writeText(1, NecronomiconText.LABEL_REQUIRED_ENERGY + ": " + ritual.getReqEnergy() + " PE", 125);
		writeText(2, NecronomiconText.LABEL_LOCATION + ": " + getDimension(ritual.getDimension()));
		writeText(2, ritual.getDescription(), 48);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(NecronomiconResources.RITUAL);
		drawTexturedModalRect(k, b0, 0, 0, 256, 256);
		if(ritual.getSacrifice() != null){
			mc.renderEngine.bindTexture(NecronomiconResources.RITUAL_INFUSION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
		} if(ritual instanceof NecronomiconCreationRitual){
			mc.renderEngine.bindTexture(NecronomiconResources.RITUAL_CREATION);
			drawTexturedModalRect(k, b0, 0, 0, 256, 256);
		}
		
		tooltipStack = null;
		
		ItemStack[] offerings = new ItemStack[8];
		if(ritual.getOfferings().length < 8)
			for(int i = 0; i < ritual.getOfferings().length; i++)
				offerings[i] = getStack(ritual.getOfferings()[i]);
		else offerings = getStacks(ritual.getOfferings());
		
		//north
		renderItem(k + 58, b0 + 30, offerings[0], x, y);
		//north-east
		renderItem(k + 84, b0 + 40, offerings[1], x, y);
		//east
		renderItem(k + 94, b0 + 66, offerings[2], x, y);
		//south-east
		renderItem(k + 84, b0 + 92, offerings[3], x, y);
		//south
		renderItem(k + 58, b0 + 103, offerings[4], x, y);
		//south-west
		renderItem(k + 32, b0 + 92, offerings[5], x, y);
		//west
		renderItem(k + 22, b0 + 66, offerings[6], x, y);
		//north-west
		renderItem(k + 32, b0 + 40, offerings[7], x, y);
		//center
		renderItem(k + 58, b0 + 66, getStack(ritual.getSacrifice()), x, y);

		if(ritual instanceof NecronomiconCreationRitual){
			renderItem(k + 58, b0 + 139, ((NecronomiconCreationRitual) ritual).getItem(), x, y);
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
					s_ = EnumChatFormatting.GRAY + s;
				parsedTooltip.add(s_);
				first = false;
			}
			GuiRenderHelper.renderTooltip(x, y, parsedTooltip);
		}
	}

	private ItemStack getStack(Object object){
		if(object instanceof ItemStack)
			return (ItemStack) object;
		else if(object instanceof Item)
			return new ItemStack((Item) object);
		else if(object instanceof Block)
			return new ItemStack((Block) object);
		else if(object instanceof String)
			return OreDictionary.getOres((String)object).iterator().next();
		return null;
	}
	private ItemStack[] getStacks(Object[] objects){
		ItemStack[] stacks = new ItemStack[objects.length];
		for(int i = 0; i < objects.length; i++)
			stacks[i] = getStack(objects[i]);
		return stacks;
	}

	private String getDimension(int dim){
		if(!dimToString.containsKey(dim))
			dimToString.put(dim, "DIM"+dim);
		return dimToString.get(dim);
	}

	private ItemStack tooltipStack;
	public void renderItem(int xPos, int yPos, ItemStack stack, int mx, int my) 
	{
		RenderItem render = new RenderItem();
		if(mx > xPos && mx < (xPos+16) && my > yPos && my < (yPos+16))
			tooltipStack = stack;

		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		render.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
		render.renderItemOverlayIntoGUI(Minecraft.getMinecraft().fontRenderer, Minecraft.getMinecraft().getTextureManager(), stack, xPos, yPos);
		RenderHelper.disableStandardItemLighting();
		GL11.glPopMatrix();

		GL11.glDisable(GL11.GL_LIGHTING);
	}

	private void initStuff(){
		dimToString.put(-1, NecronomiconText.LABEL_ANYWHERE);
		dimToString.putAll(RitualRegistry.instance().getDimensionNameMappings());

		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual.getBookType() == ritualnum)
				rituals.add(ritual);
		setTurnupLimit(rituals.size());
	}
}