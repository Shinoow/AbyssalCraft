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
package com.shinoow.abyssalcraft.api.knowledge.condition;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

public class EntityCondition extends BaseUnlockCondition {

	String name;

	public EntityCondition(String str){
		name = str;
	}

	public EntityCondition(Class<? extends Entity> clz){
		name = EntityList.getKey(clz).toString();
	}

	@Override
	public boolean areConditionObjectsEqual(Object stuff) {

		return name.equals(stuff);
	}

	@Override
	public Object getConditionObject() {

		return name;
	}

	@Override
	public int getType() {

		return 1;
	}
}
