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

public class DefaultCondition implements IUnlockCondition {

	@Override
	public boolean areConditionObjectsEqual(Object stuff) {

		return true;
	}

	@Override
	public Object getConditionObject() {

		return true;
	}

	@Override
	public int getType() {

		return -1;
	}

	@Override
	public String getHint() {

		return "This condition is already met";
	}

	@Override
	public IUnlockCondition setHint(String str) {

		return this;
	}
}
