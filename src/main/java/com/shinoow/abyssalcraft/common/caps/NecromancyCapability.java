package com.shinoow.abyssalcraft.common.caps;

import java.util.List;
import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Tuple;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class NecromancyCapability implements INecromancyCapability {

	List<Tuple<String, NBTTagCompound>> data = Lists.newArrayList();
	Map<String, Integer> sizes = Maps.newHashMap();

	@Override
	public NBTTagCompound getDataForName(String name) {

		for(Tuple<String, NBTTagCompound> t : data)
			if(t.getFirst().equals(name))
				return t.getSecond();
		return null;
	}

	@Override
	public int getSizeForName(String name){
		return sizes.get(name) != null ? sizes.get(name) : 0;
	}

	@Override
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
	}

	@Override
	public void clearEntry(String name) {
		for(Tuple<String, NBTTagCompound> t : data)
			if(t.getFirst().equals(name)){
				data.remove(t);
				break;
			}
		sizes.remove(name);
	}

	@Override
	public List<Tuple<String, NBTTagCompound>> getData() {

		return data;
	}

	@Override
	public Map<String, Integer> getSizeData() {

		return sizes;
	}

	@Override
	public void copy(INecromancyCapability cap) {
		data = cap.getData();
		sizes = cap.getSizeData();
	}
}
