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

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;

import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

public class NecroDataCapability implements INecroDataCapability {

	List<String> biome_triggers = Lists.newArrayList();
	List<String> entity_triggers = Lists.newArrayList();
	List<Integer> dimension_triggers = Lists.newArrayList();

	@Override
	public boolean isUnlocked(IUnlockCondition cond) {

		if(cond.getType() == -1) return true;
		switch(cond.getType()){
		case 0:
			return biome_triggers.contains(cond.getConditionObject());
		case 1:
			return entity_triggers.contains(cond.getConditionObject());
		case 2:
			return dimension_triggers.contains(cond.getConditionObject());
		case 3:
			for(String name : (String[])cond.getConditionObject())
				if(biome_triggers.contains(name))
					return true;
			break;
		case 4:
			for(String name : (String[])cond.getConditionObject())
				if(entity_triggers.contains(name))
					return true;
			break;
		case 5:
			for(String name : biome_triggers)
				if(((Predicate<Biome>)cond.getConditionObject()).apply(Biome.REGISTRY.getObject(new ResourceLocation(name))))
					return true;
			break;

		case 6:
			for(String name : entity_triggers)
				if(((Predicate<Class<? extends Entity>>)cond.getConditionObject()).apply(EntityList.getClass(new ResourceLocation(name))))
					return true;
			break;
		}
		return false;
	}

	@Override
	public void triggerEntityUnlock(String name) {
		entity_triggers.add(name);
	}

	@Override
	public void triggerBiomeUnlock(String name) {
		biome_triggers.add(name);
	}

	@Override
	public void triggerDimensionUnlock(int id) {
		dimension_triggers.add(id);
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
	public void copy(INecroDataCapability cap) {
		biome_triggers.addAll(cap.getBiomeTriggers());
		entity_triggers.addAll(cap.getEntityTriggers());
		dimension_triggers.addAll(cap.getDimensionTriggers());
	}
}
