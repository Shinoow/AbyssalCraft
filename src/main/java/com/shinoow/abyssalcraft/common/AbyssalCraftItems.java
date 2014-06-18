/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

public class AbyssalCraftItems extends Item {

	public AbyssalCraftItems() {
		super();
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(this.getUnlocalizedName().contains("DSOA") || this.getUnlocalizedName().contains("DAC"))
			return EnumChatFormatting.DARK_RED + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
		else if(this.getUnlocalizedName().contains("AI"))
			return EnumChatFormatting.DARK_AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
		else if(this.getUnlocalizedName().contains("CP") || this.getUnlocalizedName().contains("RCI"))
			return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");

		return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}
}