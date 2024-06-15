/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.knowledge.condition;

public class DimensionCondition implements IUnlockCondition {

	int id;
	String hint;

	public DimensionCondition(int id){
		this.id = id;
	}

	@Override
	public boolean areConditionObjectsEqual(Object stuff) {

		return Integer.valueOf(id).equals(stuff);
	}

	@Override
	public Object getConditionObject() {

		return id;
	}

	@Override
	public int getType() {

		return 2;
	}

	@Override
	public String getHint() {

		return hint;
	}

	@Override
	public IUnlockCondition setHint(String str) {
		hint = str;
		return this;
	}

}
