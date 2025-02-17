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
package com.shinoow.abyssalcraft.integration.jei.util;

import java.util.*;

import javax.annotation.Nonnull;

import com.google.common.collect.ImmutableList;
import com.shinoow.abyssalcraft.api.APIUtils;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI.FuelType;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityCrystallizer;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityTransmutator;
import com.shinoow.abyssalcraft.lib.NecronomiconText;

import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Utility class for handling some stuff in regards to JEI.<br>
 * Most (if not all) the code in this class has been ripped off<br>
 * from something within JEI and adjusted for AbyssalCraft use.<br>
 * Because of the above statement, some of it could work less<br>
 * compared to the JEI code (since this class is initialized<br>
 * at a different time, among things).<br>
 * Also has AbyssalCraft-related utility stuff as well (whaaaat?????)
 */
public class JEIUtils {

	@Nonnull
	private final ImmutableList<ItemStack> transmutatorFuels;

	@Nonnull
	private final ImmutableList<ItemStack> crystallizerFuels;

	private static String ANYWHERE = I18n.format(NecronomiconText.LABEL_ANYWHERE, new Object[0]);

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

		for(ItemStack stack : registry.getAllIngredients(VanillaTypes.ITEM)){
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

	public static String getDimension(int dim){

		return dim == OreDictionary.WILDCARD_VALUE ? ANYWHERE : DimensionDataRegistry.instance().getDimensionName(dim);
	}

	public static ItemStack getItem(int par1){
		switch(par1){
		case 0:
			return new ItemStack(ACItems.necronomicon);
		case 1:
			return new ItemStack(ACItems.abyssal_wasteland_necronomicon);
		case 2:
			return new ItemStack(ACItems.dreadlands_necronomicon);
		case 3:
			return new ItemStack(ACItems.omothol_necronomicon);
		case 4:
			return new ItemStack(ACItems.abyssalnomicon);
		default:
			return new ItemStack(ACItems.necronomicon);
		}
	}

	public static boolean list(Object obj){
		return obj == null ? false : obj instanceof ItemStack[] || obj instanceof String || obj instanceof List;
	}

	public static List<ItemStack> getList(Object obj){
		if(obj instanceof ItemStack[])
			return Arrays.asList((ItemStack[])obj);
		if(obj instanceof String)
			return OreDictionary.getOres((String)obj);
		if(obj instanceof List)
			return (List)obj;
		return Collections.emptyList();
	}

	public static List<ItemStack> parseAsList(Object obj){
		return list(obj) ? getList(obj) : Collections.singletonList(APIUtils.convertToStack(obj));
	}
}
