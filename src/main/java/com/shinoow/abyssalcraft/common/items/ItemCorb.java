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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemCorb extends Item {

	public ItemCorb() {
		super();
		maxStackSize = 1;
		setMaxDamage(1000);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int l, float f, float f1, float f3){ //Called when an item is right clicked on a block
		if(w.getBlock(x, y, z) == Blocks.stone){
			w.setBlock(x, y, z, AbyssalCraft.Darkstone);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.Darkstone){
			w.setBlock(x, y, z, Blocks.stone);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.cobblestone){
			w.setBlock(x, y, z, AbyssalCraft.Darkstone_cobble);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.Darkstone_cobble){
			w.setBlock(x, y, z, Blocks.cobblestone);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.stonebrick){
			w.setBlock(x, y, z, AbyssalCraft.Darkstone_brick);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.Darkstone_brick){
			w.setBlock(x, y, z, Blocks.stonebrick);
			is.damageItem(50, player);
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.corb.1"));
		l.add(StatCollector.translateToLocal("tooltip.corb.2"));
		l.add(StatCollector.translateToLocal("tooltip.corb.3"));
		l.add(StatCollector.translateToLocal("tooltip.corb.4"));
	}

	@Override
	public boolean hasEffect(ItemStack is, int pass){
		return true;
	}
}