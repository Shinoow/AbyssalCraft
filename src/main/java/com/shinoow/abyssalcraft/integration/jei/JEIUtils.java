/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTransmutator;

import mezz.jei.api.ingredients.IIngredientRegistry;
import net.minecraft.item.ItemStack;

/**
 * Utility class for handling some stuff in regards to JEI.<br>
 * Most (if not all) the code in this class has been ripped off<br>
 * from something within JEI and adjusted for AbyssalCraft use.<br>
 * Because of the above statement, some of it could work less<br>
 * compared to the JEI code (since this class is initialized<br>
 * at a different time, among things).
 */
public class JEIUtils {

	@Nonnull
	private final ImmutableList<ItemStack> transmutatorFuels;

	@Nonnull
	private final ImmutableList<ItemStack> crystallizerFuels;

	/**
	 * Utility class for handling some stuff in regards to JEI.<br>
	 * Most (if not all) the code in this class has been ripped off<br>
	 * from something within JEI and adjusted for AbyssalCraft use.<br>
	 * Because of the above statement, some of it could work less<br>
	 * compared to the JEI code (since this class is initialized<br>
	 * at a different time, among things).
	 */
	public JEIUtils(IIngredientRegistry registry){
		List<ItemStack> fuelsTMutable = new ArrayList<>();
		List<ItemStack> fuelsCMutable = new ArrayList<>();

		for(ItemStack stack : registry.getAllIngredients(ItemStack.class)){
			addItemStack(stack, FuelType.TRANSMUTATOR, fuelsTMutable);
			addItemStack(stack, FuelType.CRYSTALLIZER, fuelsCMutable);
		}

		transmutatorFuels = ImmutableList.copyOf(fuelsTMutable);
		crystallizerFuels = ImmutableList.copyOf(fuelsCMutable);
	}

	@Nonnull
	public ImmutableList<ItemStack> getTransmutatorFuels() {
		return transmutatorFuels;
	}

	@Nonnull
	public ImmutableList<ItemStack> getCrystallizerFuels() {
		return crystallizerFuels;
	}

	private void addItemStack(@Nonnull ItemStack stack, @Nonnull FuelType type, @Nonnull List<ItemStack> fuels) {

		switch(type){
		case CRYSTALLIZER:
			if (TileEntityCrystallizer.isItemFuel(stack))
				fuels.add(stack);
			break;
		case FURNACE:
			break;
		case TRANSMUTATOR:
			if (TileEntityTransmutator.isItemFuel(stack))
				fuels.add(stack);
			break;
		default:
			break;
		}
	}
}
