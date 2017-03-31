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
package com.shinoow.abyssalcraft.api.necronomicon.condition;

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
	public boolean areConditionObjectsEqual(Object stuff);

	/**
	 * Returns the Object this condition checks for
	 */
	public Object getConditionObject();

	/**
	 * Internal number used for casting the Condition Object<br>
	 * when checking if the condition has been met
	 */
	public int getType();
}
