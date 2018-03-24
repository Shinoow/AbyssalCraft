/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.necronomicon.condition.caps;

import java.util.List;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.necronomicon.condition.ConditionProcessorRegistry;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

import net.minecraft.entity.player.EntityPlayer;

public class NecroDataCapability implements INecroDataCapability {

	List<String> biome_triggers = Lists.newArrayList();
	List<String> entity_triggers = Lists.newArrayList();
	List<Integer> dimension_triggers = Lists.newArrayList();
	List<String> artifact_triggers = Lists.newArrayList();
	List<String> page_triggers = Lists.newArrayList();
	List<String> whisper_triggers = Lists.newArrayList();
	List<String> misc_triggers = Lists.newArrayList();

	boolean hasAllKnowledge;

	public static INecroDataCapability getCap(EntityPlayer player){
		return player.getCapability(NecroDataCapabilityProvider.NECRO_DATA_CAP, null);
	}

	@Override
	public boolean isUnlocked(IUnlockCondition cond, EntityPlayer player) {

		if(cond.getType() == -1 || hasAllKnowledge) return true;
		else return ConditionProcessorRegistry.instance().getProcessor(cond.getType()).processUnlock(cond, this, player);
	}

	@Override
	public void triggerEntityUnlock(String name) {
		if(name != null && !entity_triggers.contains(name) && name.contains(":"))
			entity_triggers.add(name);
	}

	@Override
	public void triggerBiomeUnlock(String name) {
		if(name != null && !biome_triggers.contains(name))
			biome_triggers.add(name);
	}

	@Override
	public void triggerDimensionUnlock(int id) {
		if(!dimension_triggers.contains(id))
			dimension_triggers.add(id);
	}

	@Override
	public void triggerArtifactUnlock(String name) {
		if(name != null && !artifact_triggers.contains(name))
			artifact_triggers.add(name);
	}

	@Override
	public void triggerPageUnlock(String name) {
		if(name != null && !page_triggers.contains(name))
			page_triggers.add(name);
	}

	@Override
	public void triggerWhisperUnlock(String name) {
		if(name != null && !page_triggers.contains(name))
			page_triggers.add(name);
	}

	@Override
	public void triggerMiscUnlock(String name) {
		if(name != null && !misc_triggers.contains(name))
			misc_triggers.add(name);
	}

	@Override
	public void unlockAllKnowledge(boolean unlock) {
		hasAllKnowledge = unlock;
	}

	@Override
	public List<String> getBiomeTriggers() {

		return biome_triggers;
	}

	@Override
	public List<String> getEntityTriggers() {

		return entity_triggers;
	}

	@Override
	public List<Integer> getDimensionTriggers() {

		return dimension_triggers;
	}

	@Override
	public List<String> getArtifactTriggers() {

		return artifact_triggers;
	}

	@Override
	public List<String> getPageTriggers() {

		return page_triggers;
	}

	@Override
	public List<String> getWhisperTriggers() {

		return whisper_triggers;
	}

	@Override
	public List<String> getMiscTriggers(){

		return misc_triggers;
	}

	@Override
	public boolean hasUnlockedAllKnowledge(){
		return hasAllKnowledge;
	}

	@Override
	public void copy(INecroDataCapability cap) {

		biome_triggers = cap.getBiomeTriggers();
		entity_triggers = cap.getEntityTriggers();
		dimension_triggers = cap.getDimensionTriggers();
		artifact_triggers = cap.getArtifactTriggers();
		page_triggers = cap.getPageTriggers();
		whisper_triggers = cap.getWhisperTriggers();
		misc_triggers = cap.getMiscTriggers();
		hasAllKnowledge = cap.hasUnlockedAllKnowledge();
	}
}
