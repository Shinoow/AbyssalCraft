/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

/**Shin = lazy. Deal with it.*/
public class ItemBlockColorName extends ItemBlock {

	public ItemBlockColorName(Block block) {
		super(block);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(getUnlocalizedName().contains("BOA"))
			return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(getUnlocalizedName() + ".name");
		else if(getUnlocalizedName().contains("BOC") || getUnlocalizedName().contains("Eth")
				|| getUnlocalizedName().contains("EB") || getUnlocalizedName().contains("BOE"))
			return EnumChatFormatting.AQUA + StatCollector.translateToLocal(getUnlocalizedName() + ".name");
		else if(getUnlocalizedName().contains("ODB") || getUnlocalizedName().contains("BOD"))
			return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(getUnlocalizedName() + ".name");
		else if(getUnlocalizedName().contains("AS"))
			return EnumChatFormatting.BLUE + StatCollector.translateToLocal(getUnlocalizedName() + ".name");

		return StatCollector.translateToLocal(getUnlocalizedName() + ".name");
	}
}