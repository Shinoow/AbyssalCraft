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

/**
 * Interface to use on Objects utilizing the Knowledge system, with the expectation of being locked behind<br>
 * a {@link IResearchItem } (with {@link IUnlockCondition }). Logic with font rendering should use the<br>
 * INecroDataCapability in conjunction with assigning the Aklo Font (accessible through AbyssalCraftAPI#getAkloFont)<br>
 * to obscure things haven't been unlocked knowledge-wise.
 *
 * @author Shinoow
 *
 * @since 2.0.0
 */
public interface IResearchable<T extends Object, S extends Object> {

	/**
	 * Sets the unlock condition for the Item
	 * @param condition Unlock Condition
	 */
	T setResearchItem(IResearchItem research);

	/**
	 * Getter for the Unlock Condition
	 * @param object object requesting the Unlock Condition
	 * @return the Unlock Condition associated with the ItemStack
	 */
	IResearchItem getResearchItem(S object);
}
