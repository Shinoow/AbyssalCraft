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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.client.gui.necronomicon.buttons.ButtonNextPage;
import com.shinoow.abyssalcraft.client.lib.GuiRenderHelper;
import com.shinoow.abyssalcraft.lib.NecronomiconResources;
import com.shinoow.abyssalcraft.lib.NecronomiconText;
import com.shinoow.abyssalcraft.lib.util.IHiddenRitual;

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
		drawPage(rituals.get(currTurnup), x, y);
	}

	private void drawPage(NecronomiconRitual ritual, int x, int y){
		int k = (width - guiWidth) / 2;
		byte b0 = 2;
		String title = ritual.getLocalizedName();
		fontRendererObj.drawSplitString(title, k + 20, b0 + 16, 116, 0xC40000);

		if(ritual.requiresSacrifice())
			fontRendererObj.drawSplitString(localize(NecronomiconText.LABEL_SACRIFICE), k + 138, 164, 107, 0xC40000);
		writeText(1, localize(NecronomiconText.LABEL_REQUIRED_ENERGY) + ": " + ritual.getReqEnergy() + " PE", 125);
		writeText(2, localize(NecronomiconText.LABEL_LOCATION) + ": " + getDimension(ritual.getDimension()));
		writeText(2, ritual.getDescription(), 48);
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
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
				offerings[i] = APIUtils.convertToStack(ritual.getOfferings()[i]);
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
		renderItem(k + 58, b0 + 66, APIUtils.convertToStack(ritual.getSacrifice()), x, y);

		if(ritual instanceof NecronomiconCreationRitual)
			renderItem(k + 58, b0 + 139, ((NecronomiconCreationRitual) ritual).getItem(), x, y);

		if(tooltipStack != null)
		{
			List<String> tooltipData = tooltipStack.getTooltip(Minecraft.getMinecraft().player, false);
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

	private ItemStack[] getStacks(Object[] objects){
		ItemStack[] stacks = new ItemStack[objects.length];
		for(int i = 0; i < objects.length; i++)
			stacks[i] = APIUtils.convertToStack(objects[i]);
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
		if(stack == null || stack.isEmpty()) return;

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

	private void initStuff(){
		dimToString.put(-1, localize(NecronomiconText.LABEL_ANYWHERE));
		dimToString.putAll(RitualRegistry.instance().getDimensionNameMappings());

		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual.getBookType() == ritualnum && !(ritual instanceof IHiddenRitual))
				rituals.add(ritual);
		setTurnupLimit(rituals.size());
	}
}
