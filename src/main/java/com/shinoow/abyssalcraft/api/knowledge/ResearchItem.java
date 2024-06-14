package com.shinoow.abyssalcraft.api.knowledge;

import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;

import net.minecraft.util.ResourceLocation;

public class ResearchItem implements IResearchItem {

	private IUnlockCondition[] conditions;
	private ResourceLocation id;
	private String name;
	private int level;

	public ResearchItem(String name, int level, ResourceLocation id) {
		this.name = name;
		this.id = id;
		this.level = level;
	}

	@Override
	public void setUnlockConditions(IUnlockCondition[] conditions) {
		this.conditions = conditions;
	}

	@Override
	public IUnlockCondition[] getUnlockConditions() {

		return conditions;
	}

	@Override
	public String getName() {
		return "ac.research."+name;
	}

	@Override
	public String getDescription() {
		return getName() + ".desc";
	}

	@Override
	public ResourceLocation getID() {
		return id;
	}

	@Override
	public int getRequiredLevel() {
		return level;
	}

}
