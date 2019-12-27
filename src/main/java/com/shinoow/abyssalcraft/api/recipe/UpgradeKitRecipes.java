/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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

import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.item.ItemUpgradeKit;

import net.minecraft.item.ItemStack;

public class UpgradeKitRecipes {

	private static final UpgradeKitRecipes instance = new UpgradeKitRecipes();

	private final Map<ItemUpgradeKit, Map<ItemStack, ItemStack>> upgrades = new HashMap<ItemUpgradeKit, Map<ItemStack, ItemStack>>(){{
		put((ItemUpgradeKit)ACItems.cobblestone_upgrade_kit, new HashMap<>());
		put((ItemUpgradeKit)ACItems.iron_upgrade_kit, new HashMap<>());
		put((ItemUpgradeKit)ACItems.gold_upgrade_kit, new HashMap<>());
		put((ItemUpgradeKit)ACItems.diamond_upgrade_kit, new HashMap<>());
		put((ItemUpgradeKit)ACItems.abyssalnite_upgrade_kit, new HashMap<>());
		put((ItemUpgradeKit)ACItems.coralium_upgrade_kit, new HashMap<>());
		put((ItemUpgradeKit)ACItems.dreadium_upgrade_kit, new HashMap<>());
		put((ItemUpgradeKit)ACItems.ethaxium_upgrade_kit, new HashMap<>());
	}};

	public static UpgradeKitRecipes instance(){
		return instance;
	}

	private UpgradeKitRecipes() {}

	public void addUpgradeKit(ItemUpgradeKit kit){
		upgrades.put(kit, new HashMap<>());
	}

	public void addUpgrade(ItemUpgradeKit kit, ItemStack input, ItemStack output){
		upgrades.get(kit).put(input, output);
	}

	public Map<ItemStack, ItemStack> getUpgrades(ItemUpgradeKit kit){
		return upgrades.get(kit);
	}

	public ItemStack getUpgrade(ItemUpgradeKit kit, ItemStack input){
		return upgrades.get(kit).entrySet().stream()
				.filter(e -> APIUtils.areStacksEqual(input, e.getKey()))
				.map(e -> e.getValue())
				.findFirst()
				.orElse(ItemStack.EMPTY);
	}

	public Map<ItemUpgradeKit, Map<ItemStack, ItemStack>> getAllUpgrades(){
		return upgrades;
	}
}
