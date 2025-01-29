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

public class MandatoryMultiEntityCondition extends MultiEntityCondition {

	public MandatoryMultiEntityCondition(String...names){
		super(names);
	}

	public MandatoryMultiEntityCondition(Class<? extends Entity>...entities){
		super(entities);
	}

	@Override
	public boolean areConditionObjectsEqual(Object stuff) {
		return false;
	}

	@Override
	public int getType() {

		return 11;
	}
}
