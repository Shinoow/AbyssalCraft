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
package com.shinoow.abyssalcraft.api.transfer.caps;

import java.util.List;

import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;

/**
 * Interface for the Item Transfer Capability
 * @author shinoow
 *
 * @since 1.30.0
 */
public interface IItemTransferCapability {

	/**
	 * Adds a configuration to the capability
	 * @param config Item Transfer Configuraiton to add
	 */
	void addTransferConfiguration(ItemTransferConfiguration config);

	/**
	 * Returns a list of all stored configurations
	 */
	List<ItemTransferConfiguration> getTransferConfigurations();

	/**
	 * Clears all configurations in the capability
	 */
	void clearConfigurations();

	/**
	 * Allows you to toggle the active state of the capability
	 */
	void setRunning(boolean state);

	/**
	 * Returns whether or not the capability is active on the block
	 */
	boolean isRunning();

	void copy(IItemTransferCapability cap);
}
