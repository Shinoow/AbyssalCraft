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
