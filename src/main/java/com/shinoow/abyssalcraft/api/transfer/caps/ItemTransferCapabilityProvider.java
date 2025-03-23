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

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class ItemTransferCapabilityProvider implements ICapabilityProvider, INBTSerializable<NBTBase> {

	@CapabilityInject(IItemTransferCapability.class)
	public static final Capability<IItemTransferCapability> ITEM_TRANSFER_CAP = null;

	private IItemTransferCapability capability;

	public ItemTransferCapabilityProvider() {
		capability = new ItemTransferCapability();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

		return capability == ITEM_TRANSFER_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

		if(capability == ITEM_TRANSFER_CAP)
			return (T) this.capability;

		return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return ItemTransferCapabilityStorage.instance.writeNBT(ITEM_TRANSFER_CAP, capability, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		ItemTransferCapabilityStorage.instance.readNBT(ITEM_TRANSFER_CAP, capability, null, nbt);
	}

}
