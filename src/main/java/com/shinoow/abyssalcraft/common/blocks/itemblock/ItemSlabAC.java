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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.item.IUnlockableItem;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSlabAC extends ItemSlab implements IUnlockableItem {

	private IUnlockCondition condition = new DefaultCondition();

	public ItemSlabAC(Block block, Block singleSlab, Block doubleSlab) {
		super(block, (BlockSlab)singleSlab, (BlockSlab)doubleSlab);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(getTranslationKey().contains("abyslab"))
			return TextFormatting.BLUE + super.getItemStackDisplayName(stack);
		else if(getTranslationKey().contains("darkethaxiumbrickslab"))
			return TextFormatting.DARK_RED + super.getItemStackDisplayName(stack);
		else if(getTranslationKey().contains("ethaxiumbrickslab"))
			return TextFormatting.AQUA + super.getItemStackDisplayName(stack);
		return super.getItemStackDisplayName(stack);
	}

	@Override
	public ItemSlab setUnlockCondition(IUnlockCondition condition) {
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
