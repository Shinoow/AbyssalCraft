/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemAbyssalniteCAxe extends ItemACAxe {

	public ItemAbyssalniteCAxe(ToolMaterial mat, String name) {
		super(mat, name, 8, EnumChatFormatting.AQUA);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B) {
		l.add(StatCollector.translateToLocal("tooltip.caxe.1"));
		l.add(StatCollector.translateToLocal("tooltip.caxe.2"));
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World w, int x, int y, int z, int l, float f, float f1, float f3){
		if(w.getBlock(x, y, z) == Blocks.planks){
			w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z)+1, 0);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.leaves){
			w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z)+1, 0);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.log){
			w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z)+1, 0);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.sapling){
			w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z)+1, 0);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.leaves2){
			w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z)+1, 0);
			is.damageItem(50, player);
		}else if(w.getBlock(x, y, z) == Blocks.log2){
			w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z)+1, 0);
			is.damageItem(50, player);
		}
		return false;
	}
}
