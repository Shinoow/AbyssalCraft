package com.shinoow.abyssalcraft.api.knowledge;

import com.shinoow.abyssalcraft.api.knowledge.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;

import net.minecraft.util.ResourceLocation;

public class DefaultResearchItem extends ResearchItem {

	public DefaultResearchItem() {
		super("", 0, new ResourceLocation("abyssalcraft:default"));
	}

	@Override
	public IUnlockCondition[] getUnlockConditions() {

		return new IUnlockCondition[] {new DefaultCondition()};
	}

	@Override
	public String getName() {

		return "Default Research Item";
	}

	@Override
	public String getDescription() {

		return "This is the blank (unlocked) default research item.";
	}
}
