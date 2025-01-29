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
package com.shinoow.abyssalcraft.api.knowledge.condition.caps;

import net.minecraft.nbt.*;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

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
		l = new NBTTagList();
		for(String name : instance.getArtifactTriggers())
			if(name != null)
				l.appendTag(new NBTTagString(name));
		properties.setTag("artifactTriggers", l);
		l = new NBTTagList();
		for(String name : instance.getPageTriggers())
			if(name != null)
				l.appendTag(new NBTTagString(name));
		properties.setTag("pageTriggers", l);
		l = new NBTTagList();
		for(String name : instance.getWhisperTriggers())
			if(name != null)
				l.appendTag(new NBTTagString(name));
		properties.setTag("whisperTriggers", l);
		l = new NBTTagList();
		for(String name : instance.getMiscTriggers())
			if(name != null)
				l.appendTag(new NBTTagString(name));
		properties.setTag("miscTriggers", l);
		l = new NBTTagList();
		for(ResourceLocation name : instance.getCompletedResearches())
			if(name != null)
				l.appendTag(new NBTTagString(name.toString()));
		properties.setTag("completedResearches", l);
		if(instance.hasUnlockedAllKnowledge())
			properties.setBoolean("HasAllKnowledge", true);
		properties.setInteger("knowledgeLevel", instance.getKnowledgeLevel());
		properties.setInteger("knowledgePoints", instance.getKnowledgePoints());

		return properties;
	}

	@Override
	public void readNBT(Capability<INecroDataCapability> capability, INecroDataCapability instance, EnumFacing side, NBTBase nbt) {

		NBTTagCompound properties = (NBTTagCompound)nbt;

		NBTTagList l = properties.getTagList("entityTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			if(ForgeRegistries.ENTITIES.containsKey(new ResourceLocation(l.getStringTagAt(i))))
				instance.triggerEntityUnlock(l.getStringTagAt(i));
		l = properties.getTagList("biomeTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			if(ForgeRegistries.BIOMES.containsKey(new ResourceLocation(l.getStringTagAt(i))))
				instance.triggerBiomeUnlock(l.getStringTagAt(i));
		l = properties.getTagList("dimensionTriggers", 3);
		for(int i = 0; i < l.tagCount(); i++)
			if(DimensionManager.isDimensionRegistered(l.getIntAt(i)))
				instance.triggerDimensionUnlock(l.getIntAt(i));
		l = properties.getTagList("artifactTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			instance.triggerArtifactUnlock(l.getStringTagAt(i));
		l = properties.getTagList("pageTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			instance.triggerPageUnlock(l.getStringTagAt(i));
		l = properties.getTagList("whisperTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			instance.triggerWhisperUnlock(l.getStringTagAt(i));
		l = properties.getTagList("miscTriggers", 8);
		for(int i = 0; i < l.tagCount(); i++)
			instance.triggerMiscUnlock(l.getStringTagAt(i));
		l = properties.getTagList("completedResearches", 8);
		for(int i = 0; i < l.tagCount(); i++)
			instance.completeResearch(new ResourceLocation(l.getStringTagAt(i)));
		instance.unlockAllKnowledge(properties.getBoolean("HasAllKnowledge"));
		instance.setKnowledgeLevel(properties.getInteger("knowledgeLevel"));
		instance.setKnowledgePoints(properties.getInteger("knowledgePoints"));
	}

}
