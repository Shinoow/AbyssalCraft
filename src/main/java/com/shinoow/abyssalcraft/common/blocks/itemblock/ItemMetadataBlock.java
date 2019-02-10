/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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
import net.minecraft.util.text.translation.I18n;

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
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		if(getUnlocalizedName().contains("darkethaxiumbrick"))
			return TextFormatting.DARK_RED + I18n.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
		else if(getUnlocalizedName().contains("ethaxiumbrick") || getUnlocalizedName().endsWith("stone") && par1ItemStack.getItemDamage() == 5)
			return TextFormatting.AQUA + I18n.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
		else if(getUnlocalizedName().contains("abybrick") || getUnlocalizedName().endsWith("stone") && par1ItemStack.getItemDamage() == 1)
			return TextFormatting.BLUE + I18n.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
		else if(getUnlocalizedName().contains("ingotblock"))
			return EnumIngotType.byMetadata(par1ItemStack.getItemDamage()).getFormat() + I18n.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
		return I18n.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
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
