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
package com.shinoow.abyssalcraft.api.knowledge.condition.caps;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class NecroDataCapabilityProvider implements ICapabilityProvider, INBTSerializable<NBTBase>{

	@CapabilityInject(INecroDataCapability.class)
	public static final Capability<INecroDataCapability> NECRO_DATA_CAP = null;

	private INecroDataCapability capability;

	public NecroDataCapabilityProvider(){
		capability = new NecroDataCapability();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

		return capability == NECRO_DATA_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

		if(capability == NECRO_DATA_CAP)
			return (T) this.capability;

		return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return NecroDataCapabilityStorage.instance.writeNBT(NECRO_DATA_CAP, capability, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		NecroDataCapabilityStorage.instance.readNBT(NECRO_DATA_CAP, capability, null, nbt);
	}
}
