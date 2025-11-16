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
package com.shinoow.abyssalcraft.api.knowledge;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ResearchRegistry {

	private final List<IResearchItem> researches = new ArrayList<>();
	private final List<ItemStack> baseOfferings = new ArrayList<>();
	private final List<ItemStack> abyssalOfferings = new ArrayList<>();
	private final List<ItemStack> dreadOfferings = new ArrayList<>();
	private final List<ItemStack> omotholOfferings = new ArrayList<>();
	private final List<ItemStack> shadowOfferings = new ArrayList<>();

	private final Logger logger = LogManager.getLogger("ResearchRegistry");

	private static final ResearchRegistry instance = new ResearchRegistry();

	public static ResearchRegistry instance() {
		return instance;
	}

	private ResearchRegistry() {}

	public void registerResearchItem(IResearchItem research) {

		if(researches.stream().anyMatch(r -> r.getID().equals(research.getID()))) {
			logger.log(Level.ERROR, "Research Item with ID already registered: {}", research.getID());
			return;
		}

		researches.add(research);
	}

	public List<IResearchItem> getResearchItems(){
		return researches;
	}

	@Nullable
	public IResearchItem getResearchItemById(ResourceLocation rel) {
		return researches.stream()
				.filter(r -> r.getID().equals(rel))
				.findFirst()
				.orElse(null);
	}

	public boolean isOffering(ItemStack stack) {
		for(KnowledgeType type : KnowledgeType.values())
			if(isOfferingOfType(type, stack))
				return true;
		return false;
	}

	public boolean isOfferingOfType(KnowledgeType type, ItemStack stack) {
		return getOfferingsForType(type).stream()
				.anyMatch(s -> APIUtils.areStacksEqual(stack, s));
	}

	public List<ItemStack> getOfferingsForType(KnowledgeType type){
		switch(type) {
		case ABYSSAL:
			return abyssalOfferings;
		case BASE:
			return baseOfferings;
		case DREAD:
			return dreadOfferings;
		case OMOTHOL:
			return omotholOfferings;
		case SHADOW:
			return shadowOfferings;
		default:
			return new ArrayList<>();
		}
	}

	public void addOffering(KnowledgeType type, ItemStack stack) {
		if(isOfferingOfType(type, stack)) {
			logger.log(Level.ERROR, "Offering is already registered: {}", stack.getDisplayName());
			return;
		}

		getOfferingsForType(type).add(stack);
	}
}
