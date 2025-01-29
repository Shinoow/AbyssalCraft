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

import com.shinoow.abyssalcraft.api.knowledge.condition.DefaultCondition;
import com.shinoow.abyssalcraft.api.knowledge.condition.IUnlockCondition;

import net.minecraft.util.ResourceLocation;

public class DefaultResearchItem extends ResearchItem {

	public DefaultResearchItem() {
		super("", -1, new ResourceLocation("abyssalcraft:default"));
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
	public String getHint() {

		return "This is already unlocked!";
	}

	@Override
	public String getDescription() {

		return "This is the blank (unlocked) default research item.";
	}
}
