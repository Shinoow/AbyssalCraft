/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.item;

import java.util.List;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.knowledge.IResearchableItem;
import com.shinoow.abyssalcraft.api.knowledge.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Simple item implementation
 * @author shinoow
 *
 */
public class ItemACBasic extends Item implements IResearchableItem {

	private IUnlockCondition condition = new DefaultCondition();
	private TextFormatting formatting;

	public ItemACBasic(String par1) {
		this(par1, null);
	}

	public ItemACBasic(String par1, TextFormatting formatting) {
		super();
		this.formatting = formatting;
		setTranslationKey(par1);
		setCreativeTab(ACTabs.tabItems);
	}

	@Override
	public Item setResearchItem(IUnlockCondition condition){
		this.condition = condition;
		return this;
	}

	@Override
	public IUnlockCondition getResearchItem(ItemStack stack){
		return condition;
	}

	@Override
	@SideOnly(Side.CLIENT)
	@Nullable
	public net.minecraft.client.gui.FontRenderer getFontRenderer(ItemStack stack)
	{
		return APIUtils.getFontRenderer(stack);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(formatting != null)
			return formatting + super.getItemStackDisplayName(par1ItemStack);
		if(this.getTranslationKey().contains("dreadshard") || this.getTranslationKey().contains("dreadchunk") ||
				this.getTranslationKey().contains("dreadium") || this.getTranslationKey().contains("dreadfragment"))
			return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
		else if(this.getTranslationKey().contains("abyingot") || this.getTranslationKey().contains("abyssalnite"))
			return TextFormatting.DARK_AQUA + super.getItemStackDisplayName(par1ItemStack);
		else if(this.getTranslationKey().contains("cpearl") || this.getTranslationKey().contains("cingot")
				|| this.getTranslationKey().contains("ethaxiumingot") || this.getTranslationKey().contains("nugget.coralium")
				|| this.getTranslationKey().contains("nugget.ethaxium"))
			return TextFormatting.AQUA + super.getItemStackDisplayName(par1ItemStack);

		return super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		if(stack.getItem() == ACItems.eye_of_the_abyss)
			tooltip.add(I18n.format("tooltip.eoa"));
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack)
	{
		return itemStack.getItem() == ACItems.methane ? 10000 : itemStack.getItem() == ACItems.charcoal ? 1600 : -1;
	}
}
