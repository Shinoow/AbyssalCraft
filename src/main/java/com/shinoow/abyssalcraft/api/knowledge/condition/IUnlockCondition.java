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

import com.shinoow.abyssalcraft.api.knowledge.KnowledgeType;

/**
 * Base interface for locked Necronomicon information.<br>
 * Use any of the supplied implementations of this.
 * @author shinoow
 *
 * @since 1.9
 */
public interface IUnlockCondition {

	/**
	 * Compares a Condition Object to this
	 * @param stuff Condition Object to compare
	 * @return True if they're the same, otherwise false
	 */
	boolean areConditionObjectsEqual(Object stuff);

	/**
	 * Returns the Object this condition checks for
	 */
	Object getConditionObject();

	/**
	 * Integer associated with the {@link IConditionProcessor}<br>
	 * used to check if the condition has been met.<br>
	 * Be sure to register one in {@link ConditionProcessorRegistr}<br>
	 * when adding your own Unlock Conditions
	 */
	int getType();

	/**
	 * Assigns the hint message for this Unlock Condition
	 */
	IUnlockCondition setHint(String str);

	/**
	 * Returns a hint message for this Unlock Condition
	 */
	String getHint();

	/**
	 * Returns amount of Knowledge Points it takes to skip this Unlock Condition
	 */
	int getPointsCost();

	/**
	 * Assigns the Knowledge Point cost for this Unlock Condition
	 */
	IUnlockCondition setPointsCost(int i);

	/**
	 * Returns Knowledge Type for this Unlock Condition
	 */
	KnowledgeType getKnowledgeType();

	/**
	 * Assigns the Knowledge Type for this Unlock Condition
	 */
	IUnlockCondition setKnowledgeType(KnowledgeType type);
}
