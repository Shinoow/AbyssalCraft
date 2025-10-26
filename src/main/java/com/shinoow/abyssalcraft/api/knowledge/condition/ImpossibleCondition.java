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
