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

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class NecroDataCapabilityStorage implements IStorage<INecroDataCapability> {

	public static IStorage<INecroDataCapability> instance = new NecroDataCapabilityStorage();

	@Override
	public NBTBase writeNBT(Capability<INecroDataCapability> capability, INecroDataCapability instance, EnumFacing side) {

		//serialize stuff
		NBTTagCompound properties = new NBTTagCompound();

		NBTTagList l = new NBTTagList();
		for(String name : instance.getEntityTriggers())
			if(name != null)
				l.appendTag(new NBTTagString(name));
		properties.setTag("entityTriggers", l);
		l = new NBTTagList();
		for(String name : instance.getBiomeTriggers())
			if(name != null)
				l.appendTag(new NBTTagString(name));
		properties.setTag("biomeTriggers", l);
		l = new NBTTagList();
		for(int id : instance.getDimensionTriggers())
			l.appendTag(new NBTTagInt(id));
		properties.setTag("dimensionTriggers", l);

		return properties;
	}

	@Override
	public void readNBT(Capability<INecroDataCapability> capability, INecroDataCapability instance, EnumFacing side, NBTBase nbt) {

		NBTTagCompound properties = (NBTTagCompound)nbt;

		NBTTagList l = properties.getTagList("entityTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			instance.triggerEntityUnlock(l.getStringTagAt(i));
		l = properties.getTagList("biomeTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			instance.triggerBiomeUnlock(l.getStringTagAt(i));
		l = properties.getTagList("dimensionTriggers", 3);
		for(int i = 0; i < l.tagCount(); i++)
			instance.triggerDimensionUnlock(l.getIntAt(i));
	}

}
