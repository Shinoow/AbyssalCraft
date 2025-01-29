/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.util;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.BlockDGhead;
import com.shinoow.abyssalcraft.common.items.armor.ItemDepthsArmor;

import net.minecraft.inventory.EntityEquipmentSlot;

/**
 * Idk why I even need this, but suddenly I do.
 * Makes problem go away, everbody's happy
 */
public class SoftDepUtil {

	/**
	 * Declares items with a class that implements an interface from another mod<br>
	 * (already using the Interface annotation)
	 */
	public static void declareItems() {
		ACItems.depths_helmet = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.HEAD, "depthshelmet");
		ACItems.depths_chestplate = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.CHEST, "depthsplate");
		ACItems.depths_leggings = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.LEGS, "depthslegs");
		ACItems.depths_boots = new ItemDepthsArmor(AbyssalCraftAPI.depthsArmor, 5, EntityEquipmentSlot.FEET, "depthsboots");
	}

	/**
	 * Declares blocks with a class that implements an interface from another mod<br>
	 * (already using the Interface annotation)
	 */
	public static void declareBlocks() {
		ACBlocks.depths_ghoul_head = new BlockDGhead().setTranslationKey("dghead");
		ACBlocks.pete_head = new BlockDGhead().setTranslationKey("phead");
		ACBlocks.mr_wilson_head = new BlockDGhead().setTranslationKey("whead");
		ACBlocks.dr_orange_head = new BlockDGhead().setTranslationKey("ohead");
	}
}
