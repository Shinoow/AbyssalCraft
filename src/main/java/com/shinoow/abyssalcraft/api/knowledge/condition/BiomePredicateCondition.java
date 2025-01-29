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
package com.shinoow.abyssalcraft.api.knowledge.condition;

import com.google.common.base.Predicate;

import net.minecraft.world.biome.Biome;

public class BiomePredicateCondition implements IUnlockCondition {

	Predicate<Biome> predicate;
	String hint;

	public BiomePredicateCondition(Predicate<Biome> predicate){
		this.predicate = predicate;
	}

	@Override
	public boolean areConditionObjectsEqual(Object stuff) {

		return predicate.equals(stuff);
	}

	@Override
	public Object getConditionObject() {
		return predicate;
	}

	@Override
	public int getType() {
		return 5;
	}

	@Override
	public String getHint() {

		return hint;
	}

	@Override
	public IUnlockCondition setHint(String str) {
		hint = str;
		return this;
	}

}
