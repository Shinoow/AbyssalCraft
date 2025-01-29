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

import net.minecraft.util.ResourceLocation;

public class ResearchRegistry {

	private final List<IResearchItem> researches = new ArrayList<>();

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
}
