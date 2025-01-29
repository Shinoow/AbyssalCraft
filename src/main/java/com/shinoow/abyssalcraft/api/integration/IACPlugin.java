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
package com.shinoow.abyssalcraft.api.integration;

/**
 * Simple interface for handling integrations. Should be used together with the<br>
 * {@literal @}{@link ACPlugin} annotation.<br>
 * The integration plugins follow the standard FML lifecycle (apart from no pre-init,<br>
 * any new Item/Block/Entity needs to be handled by the mod adding the plugin).
 *
 * @author shinoow, mezz (architecture is a JEI plugin rip-off)
 *
 * @since 1.3
 */
public interface IACPlugin {

	/**
	 * Used to fetch the mod name
	 * @return A String representing the mod's name
	 */
	String getModName();

	/**
	 * Determines whether or not this plugin can be loaded.<br>
	 * Should normally just return true, but can have a different<br>
	 * return value depending on factors (like a config option to load<br>
	 * the plugin, or just checking if the mod the plugin's for is present).
	 * @return True if the plugin can be loaded, otherwise false.
	 */
	boolean canLoad();

	/**
	 * Will be called at the end of the init stage
	 */
	void init();

	/**
	 * Will be called at the end of the post-init stage
	 */
	void postInit();
}
