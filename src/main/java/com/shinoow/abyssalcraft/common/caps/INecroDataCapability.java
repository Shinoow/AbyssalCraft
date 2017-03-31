/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.caps;

import java.util.List;

import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

public interface INecroDataCapability {

	public boolean isUnlocked(IUnlockCondition cond);

	public void triggerEntityUnlock(String name);

	public void triggerBiomeUnlock(String name);

	public void triggerDimensionUnlock(int id);

	public List<String> getBiomeTriggers();

	public List<String> getEntityTriggers();

	public List<Integer> getDimensionTriggers();

	public void copy(INecroDataCapability cap);
}
