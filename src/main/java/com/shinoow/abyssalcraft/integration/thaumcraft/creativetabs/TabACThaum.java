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
package com.shinoow.abyssalcraft.integration.thaumcraft.creativetabs;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.integration.thaumcraft.ACTC;

public class TabACThaum extends CreativeTabs {

	public static TabACThaum instance;

	public ArrayList<ItemStack> creativeTabQueue = new ArrayList<ItemStack>();
	List list = new ArrayList();

	public TabACThaum() {
		super("acthaum");
	}

	@Override
	public Item getTabIconItem() {

		return ACTC.wandCap;
	}

	@Override
	public void displayAllReleventItems(List list) {
		list.addAll(this.list);
	}

	public void addWands(){
		list.add(ACTC.darkWand);
		list.add(ACTC.corWand);
		list.add(ACTC.dreadWand);
		list.add(ACTC.omotholWand);
		list.add(ACTC.endWand);
	}

	public void addItem(Item item) {
		item.getSubItems(item, this, creativeTabQueue);
	}

	public void addBlock(Block block) {
		block.getSubBlocks(Item.getItemFromBlock(block), this, creativeTabQueue);
	}

	public void addAllItemsAndBlocks() {
		list.addAll(creativeTabQueue);
	}
}
