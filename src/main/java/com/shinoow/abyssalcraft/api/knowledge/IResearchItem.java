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
package com.shinoow.abyssalcraft.api.knowledge;

import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;

import net.minecraft.util.ResourceLocation;

public interface IResearchItem {

	IResearchItem setUnlockConditions(IUnlockCondition... conditions);

	IUnlockCondition[] getUnlockConditions();

	String getName();

	default String getDescription() {
		return getName() + ".description";
	}

	default String getHint() {
		return getName() + ".hint";
	}

	int getRequiredLevel();

	int getPointsCost();

	ResourceLocation getID();
}
