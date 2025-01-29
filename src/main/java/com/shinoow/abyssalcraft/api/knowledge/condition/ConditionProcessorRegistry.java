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
package com.shinoow.abyssalcraft.api.knowledge.condition;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Registry class for Condition Processors<br>
 * (used for processing {@link IUnlockCondition} when checking if they're unlocked)
 * @author shinoow
 *
 * @since 1.12.0
 */
public class ConditionProcessorRegistry {

	private final Map<Integer, IConditionProcessor> processors = new HashMap<>();

	private final Logger logger = LogManager.getLogger("ConditionProcessorRegistry");

	private static final ConditionProcessorRegistry INSTANCE = new ConditionProcessorRegistry();

	public static ConditionProcessorRegistry instance() {
		return INSTANCE;
	}

	private ConditionProcessorRegistry(){}

	/**
	 * Registers a Condition Processor
	 * @param type Processor ID (corresponds to the {@link IUnlockCondition} this processor is meant to process)
	 * @param processor Processor instance
	 */
	public void registerProcessor(int type, IConditionProcessor processor) {
		if(type > -1) {
			if(processors.putIfAbsent(type, processor) != null)
				logger.log(Level.ERROR, "Processor already registed for type {}", type);
		} else logger.log(Level.ERROR, "Invalid type: {}", type);
	}

	/**
	 * Fetches a Condition Processor (this should not be called by anything but the NecroData Capability)
	 * @param type Integer representing the processor type
	 * @return A IConditionProcessor or an empty one if none were found
	 */
	public IConditionProcessor getProcessor(int type) {
		return processors.getOrDefault(type, (condition, cap, player) -> false);
	}
}
