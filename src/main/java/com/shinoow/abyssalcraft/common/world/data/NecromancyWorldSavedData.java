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
package com.shinoow.abyssalcraft.common.world.data;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.common.util.Constants.NBT;

public class NecromancyWorldSavedData extends WorldSavedData {

	private static final String DATA_NAME = "abyssalcraft_necromancy_data";
	private List<Tuple<String, NBTTagCompound>> data = new ArrayList<>();
	private List<Tuple<String, Integer>> clientData = new ArrayList<>();

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
		if(properties.hasKey("Version"))
			//Probably do a version check here in the future, when things have changed
			for(String name : data.getKeySet())
			{
				NBTTagList list = data.getTagList(name, NBT.TAG_COMPOUND);
				for(int i = 0; i < list.tagCount(); i++)
					storeDataInternal(name, list.getCompoundTagAt(i));
			}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound properties = new NBTTagCompound();

		properties.setInteger("Version", 1);

		NBTTagCompound data = new NBTTagCompound();
		for(Tuple<String, NBTTagCompound> t : getData())
			if(data.hasKey(t.getFirst()))
				data.getTagList(t.getFirst(), NBT.TAG_COMPOUND).appendTag(t.getSecond());
			else {
				NBTTagList list = new NBTTagList();
				list.appendTag(t.getSecond());
				data.setTag(t.getFirst(), list);
			}

		properties.setTag("Data", data);

		return properties;
	}

	public NBTTagCompound getDataForName(String name) {

		for(Tuple<String, NBTTagCompound> t : data)
			if(t.getFirst().equals(name))
				return t.getSecond();
		return null;
	}

	public void storeData(String name, NBTTagCompound data, int size) {

		data.setInteger("ResurrectionRitualCrystalSize", size);
		storeDataInternal(name, data);
		markDirty();
	}

	private void storeDataInternal(String name, NBTTagCompound data)
	{
		if(this.data.size() == 5)//TODO considering the list is shared, maybe remove this limitation?
			this.data.remove(0);
		this.data.add(new Tuple(name, data));
	}

	public void clearEntry(String name) {
		for(int i = 0; i < data.size(); i++)
			if(data.get(i).getFirst().equals(name))
			{
				data.remove(i);
				break;
			}
		markDirty();
	}

	public List<Tuple<String, NBTTagCompound>> getData() {

		return data;
	}

	public List<Tuple<String, Integer>> getClientData(){
		return clientData;
	}

	public void populateClientData(List<Tuple<String, Integer>> clientData) {
		this.clientData = clientData;
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
