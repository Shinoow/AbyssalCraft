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

import com.shinoow.abyssalcraft.api.knowledge.condition.caps.INecroDataCapability;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Condition Processor<br>
 * Used to check if a specific {@link IUnlockCondition} type has been unlocked
 * @author shinoow
 *
 * @since 1.12.0
 */
public interface IConditionProcessor {

	/**
	 * Processes a Unlock Condition
	 * @param condition Condition to check
	 * @param cap NecroData Capability (holds all unlocked knowledge)
	 * @param player Player context for the capability holder
	 * @return True if the condition has been met, otherwise false
	 */
	boolean processUnlock(IUnlockCondition condition, INecroDataCapability cap, EntityPlayer player);
}
