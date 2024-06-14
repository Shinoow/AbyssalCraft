package com.shinoow.abyssalcraft.api.knowledge;

import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;

import net.minecraft.util.ResourceLocation;

public interface IResearchItem {

	void setUnlockConditions(IUnlockCondition[] conditions);

	IUnlockCondition[] getUnlockConditions();

	String getName();

	String getDescription();

	int getRequiredLevel();
	
	ResourceLocation getID();
}
