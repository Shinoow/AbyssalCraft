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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLong;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ItemTransferConfiguration implements INBTSerializable<NBTTagCompound> {

	private BlockPos[] route;
	private NonNullList<ItemStack> filter = NonNullList.create();
	private EnumFacing exitFacing, entryFacing;

	public ItemTransferConfiguration() {}

	public ItemTransferConfiguration(BlockPos[] route) {
		this.route = route;
	}

	public ItemTransferConfiguration setFilter(NonNullList<ItemStack> filter) {
		this.filter = filter;
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

	@Nullable
	public BlockPos[] getRoute() {
		return route;
	}

	@Nonnull
	public NonNullList<ItemStack> getFilter(){
		return filter;
	}

	@Nullable
	public EnumFacing getExitFacing() {
		return exitFacing;
	}

	@Nullable
	public EnumFacing getEntryFacing() {
		return entryFacing;
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
		NBTTagCompound filterNBT = new NBTTagCompound();
		ItemStackHelper.saveAllItems(filterNBT, filter);
		nbt.setTag("filter", filterNBT);

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
		for(Iterator<NBTBase> i = list.iterator(); i.hasNext();) {
			positions.add(BlockPos.fromLong(((NBTTagLong)i.next()).getLong()));
		}
		route = positions.toArray(new BlockPos[0]);
		ItemStackHelper.loadAllItems(nbt.getCompoundTag("filter"), filter);
	}

}
