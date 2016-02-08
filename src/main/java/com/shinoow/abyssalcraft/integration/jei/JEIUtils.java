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
package com.shinoow.abyssalcraft.integration.jei;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import mezz.jei.Internal;
import mezz.jei.util.Log;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameData;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTransmutator;

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
	public JEIUtils(){
		List<ItemStack> fuelsTMutable = new ArrayList<>();
		List<ItemStack> fuelsCMutable = new ArrayList<>();

		for (Block block : GameData.getBlockRegistry().typeSafeIterable()) {
			addBlockAndSubBlocks(block, FuelType.TRANSMUTATOR, fuelsTMutable);
			addBlockAndSubBlocks(block, FuelType.CRYSTALLIZER, fuelsCMutable);
		}

		for (Item item : GameData.getItemRegistry().typeSafeIterable()) {
			addItemAndSubItems(item, FuelType.TRANSMUTATOR, fuelsTMutable);
			addItemAndSubItems(item, FuelType.CRYSTALLIZER, fuelsCMutable);
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

	private void addItemAndSubItems(@Nullable Item item, FuelType type, @Nonnull List<ItemStack> fuels) {
		if (item == null)
			return;

		List<ItemStack> items = Internal.getStackHelper().getSubtypes(item, 1);
		for (ItemStack stack : items)
			if (stack != null)
				addItemStack(stack, type, fuels);
	}

	private void addBlockAndSubBlocks(@Nullable Block block, @Nonnull FuelType type, @Nonnull List<ItemStack> fuels) {
		if (block == null)
			return;

		Item item = Item.getItemFromBlock(block);

		if (item == null)
			return;

		for (CreativeTabs itemTab : item.getCreativeTabs()) {
			List<ItemStack> subBlocks = new ArrayList<>();
			block.getSubBlocks(item, itemTab, subBlocks);
			for (ItemStack subBlock : subBlocks)
				if (subBlock == null)
					Log.error("Found null subBlock of {}", block);
				else if (subBlock.getItem() == null)
					Log.error("Found subBlock of {} with null item", block);
				else
					addItemStack(subBlock, type, fuels);
		}
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
