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

/** Some sort of universal metadata itemblock thingy */
public class ItemMetadataBlock extends ItemBlock {

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
		if(getUnlocalizedName().contains("EB"))
			return EnumChatFormatting.AQUA + StatCollector.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
		return StatCollector.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
	}
}