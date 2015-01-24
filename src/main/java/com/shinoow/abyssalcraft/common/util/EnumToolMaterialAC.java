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
package com.shinoow.abyssalcraft.common.util;

import net.minecraft.item.Item;

import com.shinoow.abyssalcraft.AbyssalCraft;

public enum EnumToolMaterialAC {

	DARKSTONE(1, 180, 5.0F, 1, 15),
	ABYSSALNITE(4, 1261, 13.0F, 4, 10),
	CORALIUM(5, 2000, 14.0F, 5, 12),
	DREADIUM(6, 3000, 15.0F, 6, 15),
	ABYSSALNITE_C(8, 8000, 20.0F, 8, 30),
	ETHAXIUM(8, 4000, 16.0F, 8, 20);
	private final int harvestLevel;
	private final int maxUses;
	private final float efficiencyOnProperMaterial;
	private final int damageVsEntity;
	private final int enchantability;

	private EnumToolMaterialAC(int par3, int par4, float par5, int par6, int par7) {
		harvestLevel = par3;
		maxUses = par4;
		efficiencyOnProperMaterial = par5;
		damageVsEntity = par6;
		enchantability = par7;
	}

	public Item customCraftingMaterial = null;

	public int getMaxUses() {
		return maxUses;
	}

	public float getEfficiencyOnProperMaterial() {
		return efficiencyOnProperMaterial;
	}

	public int getDamageVsEntity() {
		return damageVsEntity;
	}

	public int getHarvestLevel() {
		return harvestLevel;
	}

	public int getEnchantability() {
		return enchantability;
	}

	public Item getToolCraftingMaterial() {
		switch(this) {
		case DARKSTONE: return Item.getItemFromBlock(AbyssalCraft.Darkstone_cobble);
		case ABYSSALNITE: return AbyssalCraft.abyingot;
		case CORALIUM: return AbyssalCraft.Cingot;
		case DREADIUM: return AbyssalCraft.dreadiumingot;
		case ABYSSALNITE_C: return AbyssalCraft.Cpearl;
		case ETHAXIUM: return AbyssalCraft.ethaxiumIngot;
		default:      return customCraftingMaterial;
		}
	}
}