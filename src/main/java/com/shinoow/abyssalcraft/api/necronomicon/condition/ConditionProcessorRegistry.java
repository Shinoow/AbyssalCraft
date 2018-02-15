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
package com.shinoow.abyssalcraft.api.necronomicon.condition;

import java.util.Map;

import org.apache.logging.log4j.Level;

import com.google.common.collect.Maps;

import net.minecraftforge.fml.common.FMLLog;

/**
 * Registry class for Condition Processors<br>
 * (used for processing {@link IUnlockCondition} when checking if they're unlocked)
 * @author shinoow
 *
 * @since 1.12.0
 */
public class ConditionProcessorRegistry {

	private final Map<Integer, IConditionProcessor> processors = Maps.newHashMap();

	private static final ConditionProcessorRegistry INSTANCE = new ConditionProcessorRegistry();

	public static ConditionProcessorRegistry instance() {
		return INSTANCE;
	}

	/**
	 * Registers a Condition Processor
	 * @param type Processor ID (corresponds to the {@link IUnlockCondition} this processor is meant to process)
	 * @param processor Processor instance
	 */
	public void registerProcessor(int type, IConditionProcessor processor) {
		if(type > -1) {
			if(!processors.containsKey(type))
				processors.put(type, processor);
			else FMLLog.log("ConditionProcessorRegistry", Level.ERROR, "Processor already registed for type %d", type);
		} else FMLLog.log("ConditionProcessorRegistry", Level.ERROR, "Invalid type: %d", type);
	}

	/**
	 * Fetches a Condition Processor (this should not be called by anything but the NecroData Capability)
	 * @param type Integer representing the processor type
	 * @return A IConditionProcessor or an empty one if none were found
	 */
	public IConditionProcessor getProcessor(int type) {
		return processors.containsKey(type) ? processors.get(type) : (condition, cap, player) -> { return false; };
	}
}
