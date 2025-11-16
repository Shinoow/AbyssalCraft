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

import com.shinoow.abyssalcraft.api.knowledge.KnowledgeType;

public class ImpossibleCondition implements IUnlockCondition {
	@Override
	public boolean areConditionObjectsEqual(Object stuff) {

		return true;
	}

	@Override
	public Object getConditionObject() {

		return false;
	}

	@Override
	public int getType() {

		return -2;
	}

	@Override
	public String getHint() {

		return "This condition can never be met";
	}

	@Override
	public IUnlockCondition setHint(String str) {

		return this;
	}

	@Override
	public int getPointsCost() {

		return 0;
	}

	@Override
	public IUnlockCondition setPointsCost(int i) {

		return this;
	}

	@Override
	public KnowledgeType getKnowledgeType() {

		return KnowledgeType.BASE;
	}

	@Override
	public IUnlockCondition setKnowledgeType(KnowledgeType type) {

		return this;
	}
}
