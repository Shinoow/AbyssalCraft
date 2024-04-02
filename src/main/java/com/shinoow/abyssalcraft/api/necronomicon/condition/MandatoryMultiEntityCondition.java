package com.shinoow.abyssalcraft.api.necronomicon.condition;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;

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
