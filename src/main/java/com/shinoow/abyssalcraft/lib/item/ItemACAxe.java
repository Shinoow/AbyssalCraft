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
package com.shinoow.abyssalcraft.lib.item;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;
import com.shinoow.abyssalcraft.api.knowledge.IResearchableItem;
import com.shinoow.abyssalcraft.api.knowledge.ResearchItems;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Simple axe implementation
 * @author shinoow
 *
 */
public class ItemACAxe extends ItemAxe implements IResearchableItem {

	private IResearchItem condition = ResearchItems.DEFAULT;

	private TextFormatting format;

	public ItemACAxe(ToolMaterial mat, String name, int harvestlevel){
		this(mat, name, harvestlevel, null);
	}

	public ItemACAxe(ToolMaterial mat, String name, int harvestlevel, TextFormatting format) {
		super(mat, mat == AbyssalCraftAPI.darkstoneTool ? 8 : mat.getAttackDamage() + 5, -3.0F);
		setCreativeTab(ACTabs.tabTools);
		setHarvestLevel("axe", harvestlevel);
		setTranslationKey(name);
		this.format = format;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return format != null ? format + super.getItemStackDisplayName(par1ItemStack) : super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public Item setResearchItem(IResearchItem condition) {
		this.condition = condition;
		return this;
	}

	@Override
	public IResearchItem getResearchItem(ItemStack stack) {

		return condition;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public net.minecraft.client.gui.FontRenderer getFontRenderer(ItemStack stack)
	{
		return APIUtils.getFontRenderer(stack);
	}
}
