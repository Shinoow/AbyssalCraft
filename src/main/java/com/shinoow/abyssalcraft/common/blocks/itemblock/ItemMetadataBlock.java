/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import com.shinoow.abyssalcraft.api.biome.IDarklandsBiome;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.necronomicon.condition.BiomePredicateCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.DimensionCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.common.blocks.IngotBlock.EnumIngotType;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;

/** Some sort of universal metadata itemblock thingy */
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
	public String getTranslationKey(ItemStack stack)
	{
		return getTranslationKey() + "." + subNames[stack.getItemDamage()];
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		if(getTranslationKey().contains("darkethaxiumbrick"))
			return TextFormatting.DARK_RED + super.getItemStackDisplayName(par1ItemStack);
		else if(getTranslationKey().contains("ethaxiumbrick") || getTranslationKey().endsWith("stone") && par1ItemStack.getItemDamage() == 5)
			return TextFormatting.AQUA + super.getItemStackDisplayName(par1ItemStack);
		else if(getTranslationKey().contains("abybrick") || getTranslationKey().endsWith("stone") && par1ItemStack.getItemDamage() == 1)
			return TextFormatting.BLUE + super.getItemStackDisplayName(par1ItemStack);
		else if(getTranslationKey().contains("ingotblock"))
			return EnumIngotType.byMetadata(par1ItemStack.getItemDamage()).getFormat() + super.getItemStackDisplayName(par1ItemStack);
		return super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public IUnlockCondition getUnlockCondition(ItemStack stack) {

		if(block == ACBlocks.stone)
			switch(stack.getMetadata()){
			case 1:
			case 4:
				return new DimensionCondition(ACLib.abyssal_wasteland_id);
			case 2:
			case 3:
				return new DimensionCondition(ACLib.dreadlands_id);
			case 5:
			case 6:
				return new DimensionCondition(ACLib.omothol_id);
			}
		else if(block == ACBlocks.cobblestone)
			switch(stack.getMetadata()){
			case 1:
			case 4:
				return new DimensionCondition(ACLib.abyssal_wasteland_id);
			case 2:
			case 3:
				return new DimensionCondition(ACLib.dreadlands_id);
			}
		else if(block == ACBlocks.ingot_block)
			switch(stack.getMetadata()){
			case 0:
				return new BiomePredicateCondition(b -> b instanceof IDarklandsBiome);
			case 1:
				return new DimensionCondition(ACLib.abyssal_wasteland_id);
			case 2:
				return new DimensionCondition(ACLib.dreadlands_id);
			case 3:
				return new DimensionCondition(ACLib.omothol_id);
			}


		return super.getUnlockCondition(stack);
	}
}
