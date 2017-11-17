/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.caps;

import java.util.Map.Entry;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Tuple;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class NecromancyCapabilityStorage implements IStorage<INecromancyCapability> {

	public static IStorage<INecromancyCapability> instance = new NecromancyCapabilityStorage();

	@Override
	public NBTBase writeNBT(Capability<INecromancyCapability> capability, INecromancyCapability instance, EnumFacing side) {

		//serialize stuff
		NBTTagCompound properties = new NBTTagCompound();

		NBTTagCompound sizes = new NBTTagCompound();
		for(Entry<String, Integer> e : instance.getSizeData().entrySet())
			sizes.setInteger(e.getKey(), e.getValue());

		properties.setTag("Sizes", sizes);

		NBTTagCompound data = new NBTTagCompound();
		for(Tuple<String, NBTTagCompound> t : instance.getData())
			data.setTag(t.getFirst(), t.getSecond());

		properties.setTag("Data", data);

		return properties;
	}

	@Override
	public void readNBT(Capability<INecromancyCapability> capability, INecromancyCapability instance, EnumFacing side, NBTBase nbt) {

		NBTTagCompound properties = (NBTTagCompound)nbt;

		NBTTagCompound data = properties.getCompoundTag("Data");
		NBTTagCompound sizes = properties.getCompoundTag("Sizes");

		for(String name : data.getKeySet())
			instance.storeData(name, data.getCompoundTag(name), sizes.getInteger(name));
	}

}
