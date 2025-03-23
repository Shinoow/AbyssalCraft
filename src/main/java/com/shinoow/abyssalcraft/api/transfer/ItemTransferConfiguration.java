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
package com.shinoow.abyssalcraft.api.transfer;

import java.util.*;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.oredict.OreDictionary;

/**
 * Data item in the Item Transfer System that information for
 * where to send things, what to send and so on
 * @author shinoow
 *
 * @since 1.30.0
 */
public class ItemTransferConfiguration implements INBTSerializable<NBTTagCompound> {

	private BlockPos[] route;
	private NonNullList<ItemStack> filter = NonNullList.withSize(5, ItemStack.EMPTY);
	private NonNullList<ItemStack> subtypeFilter = NonNullList.withSize(5, ItemStack.EMPTY);
	private EnumFacing exitFacing, entryFacing;
	private boolean filterSubtypes, filterNBT;

	/**
	 * Constructor without parameters (only used when the capability is reading from NBT)
	 */
	public ItemTransferConfiguration() {}

	/**
	 * Initializes an Item Transfer Configuration with a route
	 */
	public ItemTransferConfiguration(BlockPos[] route) {
		this.route = route;
	}

	/**
	 * (optional) Sets the filter for the Item Transfer Configuration
	 */
	public ItemTransferConfiguration setFilter(NonNullList<ItemStack> filter) {
		this.filter = filter;
		subtypeFilter = filter;
		return this;
	}

	/**
	 * Sets the facing to use for accessing the inventory at the destination block
	 */
	public ItemTransferConfiguration setEntryFacing(EnumFacing facing) {
		entryFacing = facing;
		return this;
	}

	/**
	 * Sets the facing to use for extracing items from the origin block
	 */
	public ItemTransferConfiguration setExitFacing(EnumFacing facing) {
		exitFacing = facing;
		return this;
	}

	/**
	 * Sets whether or not the filter should take subtypes into account
	 * <br>(For example, if used with vanilla planks in the filter, all types
	 * of vanilla planks would be valid in the filter)
	 */
	public ItemTransferConfiguration setFilterSubtypes(boolean filterSubtypes) {
		this.filterSubtypes = filterSubtypes;
		return this;
	}

	/**
	 * Sets whether or not the filter should take NBT into account
	 */
	public ItemTransferConfiguration setFilterNBT(boolean filterNBT) {
		this.filterNBT = filterNBT;
		return this;
	}

	/**
	 * Getter for the route
	 */
	@Nonnull
	public BlockPos[] getRoute() {
		return route;
	}

	/**
	 * Getter for the filter
	 */
	@Nonnull
	public NonNullList<ItemStack> getFilter(){
		return filterSubtypes ? subtypeFilter : filter;
	}

	/**
	 * Getter for the exit facing (where Items leave the origin block)
	 */
	@Nonnull
	public EnumFacing getExitFacing() {
		return exitFacing;
	}

	/**
	 * Getter for the entry facing (where Items enter the desination block)
	 */
	@Nonnull
	public EnumFacing getEntryFacing() {
		return entryFacing;
	}

	/**
	 * Checks whether or not the filter should take subtypes into account
	 */
	public boolean filterBySubtypes() {
		return filterSubtypes;
	}

	/**
	 * Checks whether or not the filter should take NBT into account
	 */
	public boolean filterByNBT() {
		return filterNBT;
	}

	/**
	 * Initializes the subtype filter (if filtering by subtypes is enabled)
	 */
	public void setupSubtypeFilter() {
		if(filterSubtypes)
			for(ItemStack stack : subtypeFilter)
				if(!stack.isEmpty() && stack.getHasSubtypes())
					stack.setItemDamage(OreDictionary.WILDCARD_VALUE);
	}

	@Override
	public NBTTagCompound serializeNBT() {

		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setInteger("exitFacing", exitFacing.getIndex());
		nbt.setInteger("entryFacing", entryFacing.getIndex());
		NBTTagList list = new NBTTagList();
		Arrays.stream(route).map(b -> new NBTTagLong(b.toLong())).forEach(list::appendTag);
		nbt.setTag("route", list);
		new NBTTagCompound();
		ItemStackHelper.saveAllItems(nbt, filter);
		nbt.setBoolean("FilterSubtypes", filterSubtypes);
		nbt.setBoolean("FilterNBT", filterNBT);

		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		exitFacing = EnumFacing.byIndex(nbt.getInteger("exitFacing"));
		entryFacing = EnumFacing.byIndex(nbt.getInteger("entryFacing"));
		NBTTagList list = nbt.getTagList("route", NBT.TAG_LONG);
		List<BlockPos> positions = new ArrayList<>();
		for(Iterator<NBTBase> i = list.iterator(); i.hasNext();)
			positions.add(BlockPos.fromLong(((NBTTagLong)i.next()).getLong()));
		route = positions.toArray(new BlockPos[0]);
		filter = NonNullList.withSize(5, ItemStack.EMPTY);
		subtypeFilter = NonNullList.withSize(5, ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(nbt, filter);
		ItemStackHelper.loadAllItems(nbt, subtypeFilter);
		filterSubtypes = nbt.getBoolean("FilterSubtypes");
		filterNBT = nbt.getBoolean("FilterNBT");
		setupSubtypeFilter();
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ItemTransferConfiguration))
			return false;
		ItemTransferConfiguration cfg = (ItemTransferConfiguration)obj;

		//if they are identical in serialized form, they are equal
		if(cfg.serializeNBT().toString().equals(serializeNBT().toString()))
			return true;
		boolean filtersMatch = false;
		List<ItemStack> temp = new ArrayList<>(filter);
		temp.removeIf(ItemStack::isEmpty);
		List<ItemStack> temp2 = new ArrayList<>(cfg.filter);
		temp2.removeIf(ItemStack::isEmpty);
		int len1 = temp.size();
		int len2 = temp2.size();
		if(temp.size() == temp2.size()) {//compare sizes, then contents
			for(ItemStack stack : temp)
				for(ItemStack stack2 : temp2)
					if(APIUtils.areStacksEqual(stack2, stack)) {
						stack.setCount(0);
						stack2.setCount(0);
					}
			if(temp.stream().allMatch(ItemStack::isEmpty) &&
					temp2.stream().allMatch(ItemStack::isEmpty))
				filtersMatch = true;
		}

		boolean desinationMatch = route[route.length-1].equals(cfg.route[cfg.route.length-1]);
		boolean filterDiff = len1 == 0 && len2 != 0 || len1 != 0 && len2 == 0;
		//if destination match, and either filters are identical,
		//or one has a filter while the other doesn't, equal
		return (filtersMatch || filterDiff) && desinationMatch;
	}
}
