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

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemAbyssalniteCPickaxe extends ItemACPickaxe {

	public ItemAbyssalniteCPickaxe(ToolMaterial mat, String name) {
		super(mat, name, 8, EnumChatFormatting.AQUA);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B) {
		l.add(StatCollector.translateToLocal("tooltip.cpickaxe.1"));
		l.add(StatCollector.translateToLocal("tooltip.cpickaxe.2"));
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int l, float f, float f1, float f3){
		if(w.getBlock(x, y, z) == Blocks.coal_ore){
			w.setBlock(x, y, z, Blocks.iron_ore);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.iron_ore){
			w.setBlock(x, y, z, Blocks.gold_ore);
			is.damageItem(60, player);
		}else if(w.getBlock(x, y, z) == Blocks.gold_ore){
			w.setBlock(x, y, z, Blocks.redstone_ore);
			is.damageItem(70, player);
		}else if(w.getBlock(x, y, z) == Blocks.lit_redstone_ore){
			w.setBlock(x, y, z, Blocks.lapis_ore);
			is.damageItem(80, player);
		}else if(w.getBlock(x, y, z) == Blocks.lapis_ore){
			w.setBlock(x, y, z, Blocks.emerald_ore);
			is.damageItem(90, player);
		}else if(w.getBlock(x, y, z) == Blocks.emerald_ore){
			w.setBlock(x, y, z, AbyssalCraft.Coraliumore);
			is.damageItem(100, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.Coraliumore){
			w.setBlock(x, y, z, Blocks.diamond_ore);
			is.damageItem(110, player);
		}else if(w.getBlock(x, y, z) == Blocks.diamond_ore){
			w.setBlock(x, y, z, AbyssalCraft.abyore);
			is.damageItem(120, player);
		}else if(w.getBlock(x,y,z) == AbyssalCraft.abyore) {
			w.setBlock(x, y, z, AbyssalCraft.CoraliumInfusedStone);
			is.damageItem(130, player);
		}else if(w.getBlock(x, y, z) == AbyssalCraft.CoraliumInfusedStone){
			w.setBlock(x, y, z, Blocks.coal_ore);
			is.damageItem(40, player);
		}else if(w.getBlock(x, y, z) == Blocks.netherrack){
			w.setBlock(x, y, z, Blocks.end_stone);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.end_stone){
			w.setBlock(x, y, z, Blocks.netherrack);
			is.damageItem(50, player);
		}
		return false;
	}
}