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
package com.shinoow.abyssalcraft.common.world.data;

import java.util.*;
import java.util.Map.Entry;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class NecromancyWorldSavedData extends WorldSavedData {

	private static final String DATA_NAME = "abyssalcraft_necromancy_data";
	List<Tuple<String, NBTTagCompound>> data = new ArrayList<>();
	Map<String, Integer> sizes = new HashMap<>();

	public NecromancyWorldSavedData() {
		super(DATA_NAME);
	}

	public NecromancyWorldSavedData(String s) {
		super(s);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound properties = nbt;

		NBTTagCompound data = properties.getCompoundTag("Data");
		NBTTagCompound sizes = properties.getCompoundTag("Sizes");

		for(String name : data.getKeySet())
			storeData(name, data.getCompoundTag(name), sizes.getInteger(name));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();

		NBTTagCompound sizes = new NBTTagCompound();
		for(Entry<String, Integer> e : getSizeData().entrySet())
			sizes.setInteger(e.getKey(), e.getValue());

		properties.setTag("Sizes", sizes);

		NBTTagCompound data = new NBTTagCompound();
		for(Tuple<String, NBTTagCompound> t : getData())
			data.setTag(t.getFirst(), t.getSecond());

		properties.setTag("Data", data);

		return properties;
	}

	public NBTTagCompound getDataForName(String name) {

		for(Tuple<String, NBTTagCompound> t : data)
			if(t.getFirst().equals(name))
				return t.getSecond();
		return null;
	}

	public int getSizeForName(String name){
		return sizes.get(name) != null ? sizes.get(name) : 0;
	}

	public void storeData(String name, NBTTagCompound data, int size) {

		if(getDataForName(name) == null){
			if(this.data.size() == 5){
				sizes.remove(this.data.get(0).getFirst());
				this.data.remove(0);
			}
			this.data.add(new Tuple(name, data));
		} else
			for(Tuple<String, NBTTagCompound> t : this.data)
				if(t.getFirst().equals(name)){
					t = new Tuple(name, data);
					break;
				}
		sizes.put(name, size);
		markDirty();
	}

	public void clearEntry(String name) {
		for(Tuple<String, NBTTagCompound> t : data)
			if(t.getFirst().equals(name)){
				data.remove(t);
				break;
			}
		sizes.remove(name);
		markDirty();
	}

	public List<Tuple<String, NBTTagCompound>> getData() {

		return data;
	}

	public Map<String, Integer> getSizeData() {

		return sizes;
	}

	public static NecromancyWorldSavedData get(World world) {
		MapStorage storage = world.getMapStorage();
		NecromancyWorldSavedData instance = (NecromancyWorldSavedData) storage.getOrLoadData(NecromancyWorldSavedData.class, DATA_NAME);

		if (instance == null) {
			instance = new NecromancyWorldSavedData();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}
}
