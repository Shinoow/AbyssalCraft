/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import java.util.List;

import com.shinoow.abyssalcraft.common.blocks.BlockCrystalCluster.EnumCrystalType;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrystalClusterBlock extends ItemMetadataBlock {

	public ItemCrystalClusterBlock(Block block) {
		super(block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		return ACLib.crystalColors[par1ItemStack.getItemDamage()];
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List<String> l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.crystal")+ ": " + ACLib.crystalAtoms[is.getItemDamage()]);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return StatCollector.translateToLocalFormatted("crystalcluster.postfix", name(par1ItemStack.getItemDamage()));
	}

	public String name(int meta) {
		String s = EnumCrystalType.byMetadata(meta).getName();
		String name = s.substring(0, 1).toUpperCase() + s.substring(1);
		return StatCollector.translateToLocal("item.crystal."+name+".name");
	}
}
