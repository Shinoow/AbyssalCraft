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
