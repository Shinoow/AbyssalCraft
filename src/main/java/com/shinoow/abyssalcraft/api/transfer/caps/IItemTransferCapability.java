/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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

public interface IItemTransferCapability {

	public void addTransferConfiguration(ItemTransferConfiguration config);

	public List<ItemTransferConfiguration> getTransferConfigurations();

	public void clearConfigurations();

	public void setRunning(boolean state);

	public boolean isRunning();

	public void copy(IItemTransferCapability cap);
}
