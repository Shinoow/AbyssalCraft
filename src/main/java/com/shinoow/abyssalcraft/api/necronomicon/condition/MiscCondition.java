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

public class MiscCondition implements IUnlockCondition {

	String name;

	public MiscCondition(String name) {
		this.name = name;
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

		return 10;
	}

}
