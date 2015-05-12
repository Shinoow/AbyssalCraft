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
package com.shinoow.abyssalcraft.api.item;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * The "fuel" in Coin Engraving. Use this class if you want to make your own engravings.
 * 
 * @author shinoow
 *
 * @since 1.1
 */
public class ItemEngraving extends Item {

	/**
	 * The "fuel" in Coin Engraving. Use this class if you want to make your own engravings.
	 * @param par1 The unlocalized name, will be prefixed by "engraving."
	 * @param par2 The item damage, used as a durability check
	 */
	public ItemEngraving(String par1, int par2){
		super();
		setUnlocalizedName("engraving." + par1);
		setMaxDamage(par2);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(getMaxDamage() - getDamage(is) +"/"+ is.getMaxDamage());
	}
}