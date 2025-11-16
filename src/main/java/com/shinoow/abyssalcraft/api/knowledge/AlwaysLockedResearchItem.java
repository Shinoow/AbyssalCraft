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
import com.shinoow.abyssalcraft.api.knowledge.condition.ImpossibleCondition;

import net.minecraft.util.ResourceLocation;

public class AlwaysLockedResearchItem extends ResearchItem {

	public AlwaysLockedResearchItem() {
		super("", -2, new ResourceLocation("abyssalcraft:locked"));
	}

	@Override
	public IUnlockCondition[] getUnlockConditions() {

		return new IUnlockCondition[] {new ImpossibleCondition()};
	}

	@Override
	public String getName() {

		return "Always Locked Research Item";
	}

	@Override
	public String getHint() {

		return "This can never be unlocked!";
	}

	@Override
	public String getDescription() {

		return "This is a research item that's always locked.";
	}
}
