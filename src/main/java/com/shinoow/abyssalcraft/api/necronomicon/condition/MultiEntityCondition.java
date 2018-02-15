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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class MultiEntityCondition implements IUnlockCondition {

	String[] names;

	public MultiEntityCondition(String...names){
		this.names = names;
	}

	public MultiEntityCondition(Class<? extends Entity>...entities){
		names = new String[entities.length];
		for(int i = 0; i < entities.length; i++)
			names[i] = EntityList.getKey(entities[i]).toString();
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
		return 4;
	}
}
