package com.shinoow.abyssalcraft.api.knowledge;

import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.ImpossibleCondition;

import net.minecraft.util.ResourceLocation;

public class AlwaysLockedResearchItem extends ResearchItem {

	public AlwaysLockedResearchItem() {
		super("", -1, new ResourceLocation("abyssalcraft:locked"));
	}

	@Override
	public IUnlockCondition[] getUnlockConditions() {

		return new IUnlockCondition[] {new ImpossibleCondition()};
	}

	@Override
	public String getName() {

		return "Default Research Item";
	}

	@Override
	public String getHint() {

		return "This is already unlocked!";
	}

	@Override
	public String getDescription() {

		return "This is the blank (unlocked) default research item.";
	}
}
