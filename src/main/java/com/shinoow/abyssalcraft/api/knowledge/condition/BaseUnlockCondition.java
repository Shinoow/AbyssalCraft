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

/**
 * Base class for Unlock Conditions (handles generic properties)
 * @author shinoow
 *
 */
public abstract class BaseUnlockCondition implements IUnlockCondition {

	protected String hint;
	protected int pointCost;
	protected KnowledgeType knowledgeType;

	@Override
	public IUnlockCondition setHint(String str) {
		hint = str;
		return this;
	}

	@Override
	public String getHint() {

		return hint;
	}

	@Override
	public int getPointsCost() {

		return pointCost;
	}

	@Override
	public IUnlockCondition setPointsCost(int i) {
		pointCost = i;
		return this;
	}

	@Override
	public KnowledgeType getKnowledgeType() {

		return knowledgeType;
	}

	@Override
	public IUnlockCondition setKnowledgeType(KnowledgeType type) {
		knowledgeType = type;
		return this;
	}

}
