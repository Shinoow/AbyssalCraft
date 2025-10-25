package com.shinoow.abyssalcraft.client.util;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.client.gui.necronomicon.GuiNecronomicon;
import com.shinoow.abyssalcraft.client.gui.necronomicon.entries.GuiNecronomiconEntry;
import com.shinoow.abyssalcraft.lib.client.GuiRenderHelper;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.util.ITooltipFlag.TooltipFlags;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Logic stripped from GuiNecronomicon for slightly cleaner code
 */
public class NecronomiconGuiHelper {

	protected ItemStack tooltipStack;
	private static final int cycleTime = 1000;
	private long startTime, drawTime;
	private static String sidebarIndex;

	public void updateContext() {
		long time = System.currentTimeMillis();
		startTime = time - cycleTime;
		drawTime = time;
	}

	public void updateDrawTime() {
		drawTime = System.currentTimeMillis();
	}

	public List<ItemStack> getList(Object obj){
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

	public boolean isList(Object obj){
		return obj == null ? false : obj instanceof ItemStack[] || obj instanceof String || obj instanceof List || obj instanceof Ingredient ||
				obj instanceof ItemStack && ((ItemStack)obj).getHasSubtypes() &&  ((ItemStack)obj).getMetadata() == OreDictionary.WILDCARD_VALUE;
	}

	public void renderObject(int xPos, int yPos, Object obj, int mx, int my, Minecraft mc) {
		if(isList(obj)) {
			List<ItemStack> list = getList(obj);
			if(!list.isEmpty()) {
				int index = (int)((drawTime - startTime) / cycleTime) % list.size();
				renderItem(xPos, yPos, list.get(index), mx, my, mc);
			} else if(obj instanceof ItemStack)
				renderItem(xPos, yPos, APIUtils.convertToStack(obj), mx, my, mc);
		} else
			renderItem(xPos, yPos, APIUtils.convertToStack(obj), mx, my, mc);
	}

	public void renderItem(int xPos, int yPos, ItemStack stack, int mx, int my, Minecraft mc)
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

	public void renderTooltip(int x, int y, Minecraft mc) {
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

	public void clearTooltipStack() {
		tooltipStack = null;
	}


	public void updateSidebarIndex(GuiNecronomicon gui) {
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
			str = appendTitle(TranslationUtil.toLocalFormatted(gui.data.getTitle()), str);
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

	public void clearSidebar() {
		sidebarIndex = "";
	}

	public boolean isSiderbarNotNull() {
		return sidebarIndex != null && sidebarIndex.length() > 0;
	}

	public String getSidebar() {
		return sidebarIndex;
	}
}
