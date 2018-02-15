/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.recipe;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ItemUpgradeKit;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class UpgradeKitRecipes {

	private static final UpgradeKitRecipes instance = new UpgradeKitRecipes();

	private final Map<ItemUpgradeKit, Map<ItemStack, ItemStack>> upgrades = new HashMap<ItemUpgradeKit, Map<ItemStack, ItemStack>>(){{
		put((ItemUpgradeKit)ACItems.cobblestone_upgrade_kit, Maps.newHashMap());
		put((ItemUpgradeKit)ACItems.iron_upgrade_kit, Maps.newHashMap());
		put((ItemUpgradeKit)ACItems.gold_upgrade_kit, Maps.newHashMap());
		put((ItemUpgradeKit)ACItems.diamond_upgrade_kit, Maps.newHashMap());
		put((ItemUpgradeKit)ACItems.abyssalnite_upgrade_kit, Maps.newHashMap());
		put((ItemUpgradeKit)ACItems.coralium_upgrade_kit, Maps.newHashMap());
		put((ItemUpgradeKit)ACItems.dreadium_upgrade_kit, Maps.newHashMap());
		put((ItemUpgradeKit)ACItems.ethaxium_upgrade_kit, Maps.newHashMap());
	}};

	public static UpgradeKitRecipes instance(){
		return instance;
	}

	public void addUpgradeKit(ItemUpgradeKit kit){
		upgrades.put(kit, Maps.newHashMap());
	}

	public void addUpgrade(ItemUpgradeKit kit, ItemStack input, ItemStack output){
		upgrades.get(kit).put(input, output);
	}

	public Map<ItemStack, ItemStack> getUpgrades(ItemUpgradeKit kit){
		return upgrades.get(kit);
	}

	public ItemStack getUpgrade(ItemUpgradeKit kit, ItemStack input){
		for(Entry<ItemStack, ItemStack> entry : upgrades.get(kit).entrySet())
			if(areStacksEqual(input, entry.getKey()))
				return entry.getValue();

		return ItemStack.EMPTY;
	}

	public Map<ItemUpgradeKit, Map<ItemStack, ItemStack>> getAllUpgrades(){
		return upgrades;
	}

	private boolean areStacksEqual(ItemStack input, ItemStack compare)
	{
		return compare.getItem() == input.getItem() && (compare.getItemDamage() == OreDictionary.WILDCARD_VALUE || compare.getItemDamage() == input.getItemDamage());
	}
}
