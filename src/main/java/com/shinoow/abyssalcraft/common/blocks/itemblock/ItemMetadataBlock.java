/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.common.blocks.IngotBlock.EnumIngotType;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

/**
 * Some sort of universal metadata itemblock thingy
 * @deprecated should be removed post-flattening
 *
 */
@Deprecated
public class ItemMetadataBlock extends ItemBlockAC {

	private static final String[] subNames = {
			"0", "1",  "2", "3", "4", "5", "6", "7",
			"8", "9", "10", "11", "12", "13", "14", "15"};

	public ItemMetadataBlock(Block b) {
		super(b);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return getUnlocalizedName() + "." + subNames[stack.getItemDamage()];
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		if(getUnlocalizedName().contains("darkethaxiumbrick"))
			return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
		else if(getUnlocalizedName().contains("ethaxiumbrick") || getUnlocalizedName().endsWith("stone") && par1ItemStack.getItemDamage() == 5)
			return TextFormatting.AQUA + super.getItemStackDisplayName(par1ItemStack);
		else if(getUnlocalizedName().contains("abybrick") || getUnlocalizedName().endsWith("stone") && par1ItemStack.getItemDamage() == 1)
			return TextFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
		else if(getUnlocalizedName().contains("ingotblock"))
			return EnumIngotType.byMetadata(par1ItemStack.getItemDamage()).getFormat() + super.getItemStackDisplayName(par1ItemStack);
		return super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public IUnlockCondition getUnlockCondition(ItemStack stack) {

		return super.getUnlockCondition(stack);
	}
}
