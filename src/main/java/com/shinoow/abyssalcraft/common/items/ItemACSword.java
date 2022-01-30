/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.item.IUnlockableItem;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemACSword extends ItemSword implements IUnlockableItem {

	private TextFormatting format;
	private IUnlockCondition condition = new DefaultCondition();

	public ItemACSword(ToolMaterial mat, String name){
		this(mat, name, null);
	}

	public ItemACSword(ToolMaterial mat, String name, TextFormatting format) {
		super(mat);
		setCreativeTab(ACTabs.tabCombat);
		setTranslationKey(name);
		this.format = format;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return format != null ? format + super.getItemStackDisplayName(par1ItemStack) : super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public Item setUnlockCondition(IUnlockCondition condition) {
		this.condition = condition;
		return this;
	}

	@Override
	public IUnlockCondition getUnlockCondition(ItemStack stack) {

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
