/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
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
	public IResearchItem setUnlockConditions(IUnlockCondition... conditions) {
		this.conditions = conditions;
		return this;
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
	public String getHint() {

		return getName() + ".hint";
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
