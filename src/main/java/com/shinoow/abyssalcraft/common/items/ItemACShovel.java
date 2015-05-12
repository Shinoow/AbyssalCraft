/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
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
package com.shinoow.abyssalcraft.common.items;

import com.shinoow.abyssalcraft.AbyssalCraft;

import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class ItemACShovel extends ItemSpade {

	private EnumChatFormatting format;

	public ItemACShovel(ToolMaterial mat, String name, int harvestlevel){
		this(mat, name, harvestlevel, null);
	}

	public ItemACShovel(ToolMaterial mat, String name, int harvestlevel, EnumChatFormatting format) {
		super(mat);
		setCreativeTab(AbyssalCraft.tabTools);
		setHarvestLevel("shovel", harvestlevel);
		setUnlocalizedName(name);
		setTextureName(AbyssalCraft.modid + ":" + name);
		this.format = format;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return format != null ? format + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name") : super.getItemStackDisplayName(par1ItemStack);
	}
}