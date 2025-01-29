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
package com.shinoow.abyssalcraft.api.rending;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Registry class for Rendings
 * @author shinoow
 *
 * @since 1.29.0
 *
 */
public class RendingRegistry {

	private final List<Rending> rendings = new ArrayList<>();

	private final Logger logger = LogManager.getLogger("RendingRegistry");

	private static final RendingRegistry instance = new RendingRegistry();

	public static RendingRegistry instance() {
		return instance;
	}

	private RendingRegistry() {}

	/**
	 * Registers a Rending
	 * @param rending The Rending object, contains all data used to collect energy
	 */
	public void registerRending(Rending rending) {
		if(rendings.stream().anyMatch(r -> r.getName().equalsIgnoreCase(rending.getName()))) {
			logger.log(Level.ERROR, "Rending already registered: {}", rending.getName());
			return;
		}

		rendings.add(rending);
	}

	/**
	 * Returns an ArrayList containing all registered Rendings
	 */
	public List<Rending> getRendings() {
		return rendings;
	}

	/**
	 * Removes a Rending based on name
	 * @param name
	 */
	public void removeRending(String name) {
		if(rendings.removeIf(r -> r.getName().equalsIgnoreCase(name)))
			logger.log(Level.ERROR, "Removed rending with name: {}", name);
	}
}
