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

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.transfer.ItemTransferConfiguration;

import net.minecraft.tileentity.TileEntity;

public class ItemTransferCapability implements IItemTransferCapability {

	private List<ItemTransferConfiguration> configurations = new ArrayList<>();
	private boolean isRunning;

	public static IItemTransferCapability getCap(TileEntity tile) {
		return tile.getCapability(ItemTransferCapabilityProvider.ITEM_TRANSFER_CAP, null);
	}

	@Override
	public void addTransferConfiguration(ItemTransferConfiguration config) {
		if(!configurations.stream().anyMatch(c -> c.equals(config)))
			configurations.add(config);
	}

	@Override
	public List<ItemTransferConfiguration> getTransferConfigurations() {

		return configurations;
	}

	@Override
	public void clearConfigurations() {
		configurations.clear();
	}

	@Override
	public void setRunning(boolean state) {
		isRunning = state;
	}

	@Override
	public boolean isRunning() {

		return isRunning;
	}

	@Override
	public void copy(IItemTransferCapability cap) {
		configurations = cap.getTransferConfigurations();
	}

}
