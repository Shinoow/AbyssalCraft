/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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
import javax.annotation.Nullable;

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

public class ItemTransferConfiguration implements INBTSerializable<NBTTagCompound> {

	private BlockPos[] route;
	private NonNullList<ItemStack> filter = NonNullList.withSize(5, ItemStack.EMPTY);
	private NonNullList<ItemStack> subtypeFilter = NonNullList.withSize(5, ItemStack.EMPTY);
	private EnumFacing exitFacing, entryFacing;
	private boolean filterSubtypes, filterNBT;

	public ItemTransferConfiguration() {}

	public ItemTransferConfiguration(BlockPos[] route) {
		this.route = route;
	}

	public ItemTransferConfiguration setFilter(NonNullList<ItemStack> filter) {
		this.filter = filter;
		subtypeFilter = filter;
		return this;
	}

	public ItemTransferConfiguration setEntryFacing(EnumFacing facing) {
		entryFacing = facing;
		return this;
	}

	public ItemTransferConfiguration setExitFacing(EnumFacing facing) {
		exitFacing = facing;
		return this;
	}

	public ItemTransferConfiguration setFilterSubtypes(boolean filterSubtypes) {
		this.filterSubtypes = filterSubtypes;
		return this;
	}

	public ItemTransferConfiguration setFilterNBT(boolean filterNBT) {
		this.filterNBT = filterNBT;
		return this;
	}

	@Nullable
	public BlockPos[] getRoute() {
		return route;
	}

	@Nonnull
	public NonNullList<ItemStack> getFilter(){
		return filterSubtypes ? subtypeFilter : filter;
	}

	@Nullable
	public EnumFacing getExitFacing() {
		return exitFacing;
	}

	@Nullable
	public EnumFacing getEntryFacing() {
		return entryFacing;
	}

	public boolean filterBySubtypes() {
		return filterSubtypes;
	}

	public boolean filterByNBT() {
		return filterNBT;
	}

	public void setupSubtypeFilter() {
		if(filterSubtypes)
			for(ItemStack stack : subtypeFilter)
				if(!stack.isEmpty() && stack.getHasSubtypes())
					stack.setItemDamage(OreDictionary.WILDCARD_VALUE);
	}

	@Override
	public NBTTagCompound serializeNBT() {

		NBTTagCompound nbt = new NBTTagCompound();

		if(exitFacing != null)
			nbt.setInteger("exitFacing", exitFacing.getIndex());
		if(entryFacing != null)
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
		if(nbt.hasKey("exitFacing"))
			exitFacing = EnumFacing.getFront(nbt.getInteger("exitFacing"));
		if(nbt.hasKey("entryFacing"))
			entryFacing = EnumFacing.getFront(nbt.getInteger("entryFacing"));
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
