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
package com.shinoow.abyssalcraft.common.caps;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;

public class NecromancyCapabilityProvider implements ICapabilityProvider, INBTSerializable<NBTBase>{

	@CapabilityInject(INecromancyCapability.class)
	public static final Capability<INecromancyCapability> NECROMANCY_CAP = null;

	private INecromancyCapability capability;

	public NecromancyCapabilityProvider(){
		capability = new NecromancyCapability();
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

		return capability == NECROMANCY_CAP;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

		if(capability == NECROMANCY_CAP)
			return (T) this.capability;

		return null;
	}

	@Override
	public NBTBase serializeNBT() {
		return NecromancyCapabilityStorage.instance.writeNBT(NECROMANCY_CAP, capability, null);
	}

	@Override
	public void deserializeNBT(NBTBase nbt) {
		NecromancyCapabilityStorage.instance.readNBT(NECROMANCY_CAP, capability, null, nbt);
	}
}
