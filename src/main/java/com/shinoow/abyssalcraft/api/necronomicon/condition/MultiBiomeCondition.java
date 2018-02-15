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

import net.minecraft.world.biome.Biome;

public class MultiBiomeCondition implements IUnlockCondition {

	String[] names;

	public MultiBiomeCondition(Biome...biomes){
		names = new String[biomes.length];
		for(int i = 0; i < biomes.length; i++)
			names[i] = biomes[i].getRegistryName().toString();
	}

	@Override
	public boolean areConditionObjectsEqual(Object stuff) {
		for(String name : names)
			if(name.equals(stuff))
				return true;
		return false;
	}

	@Override
	public Object getConditionObject() {
		return names;
	}

	@Override
	public int getType() {
		return 3;
	}
}
