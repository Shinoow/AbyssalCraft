package com.shinoow.abyssalcraft.common.caps;

import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;

public interface INecromancyCapability {

	public NBTTagCompound getDataForName(String name);

	public int getSizeForName(String name);

	public void storeData(String name, NBTTagCompound data, int size);

	public void clearEntry(String name);

	public List<Tuple<String, NBTTagCompound>> getData();
	
	public Map<String, Integer> getSizeData();

	public void copy(INecromancyCapability cap);
}
