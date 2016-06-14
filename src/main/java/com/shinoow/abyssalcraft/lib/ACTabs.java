/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;

/**
 * Creative Tab references
 * @author shinoow
 */
public class ACTabs {

	public static final CreativeTabs tabBlock = new CreativeTabs("acblocks"){

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ACBlocks.darkstone);
		}
	};
	public static final CreativeTabs tabItems = new CreativeTabs("acitems"){

		@Override
		public Item getTabIconItem() {
			return ACItems.necronomicon;
		}
	};
	public static final CreativeTabs tabTools = new CreativeTabs("actools"){

		@Override
		public Item getTabIconItem() {
			return ACItems.darkstone_axe;
		}
	};
	public static final CreativeTabs tabCombat = new CreativeTabs("acctools"){

		@Override
		public Item getTabIconItem() {
			return ACItems.darkstone_sword;
		}
	};
	public static final CreativeTabs tabFood = new CreativeTabs("acfood"){

		@Override
		public Item getTabIconItem() {
			return ACItems.iron_plate;
		}
	};
	public static final CreativeTabs tabDecoration = new CreativeTabs("acdblocks"){

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ACBlocks.wooden_crate);
		}
	};
	public static final CreativeTabs tabCrystals = new CreativeTabs("accrystals"){

		@Override
		public Item getTabIconItem() {
			return Item.getItemFromBlock(ACBlocks.crystallizer_idle);
		}
	};
	public static final CreativeTabs tabCoins = new CreativeTabs("accoins"){

		@Override
		public Item getTabIconItem() {
			return ACItems.coin;
		}
	};
}
