/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.item;

import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Interface to use on Items whose information is expected to be locked behind a {@link IUnlockCondition }.<br>
 * The Items getFontRenderer(ItemStack stack) should check getUnlockCondition for the INecroDataCapability capability,<br>
 * then assign the Aklo Font (accessible through AbyssalCraftAPI#getAkloFont) if the condition hasn't been unlocked.
 *
 * @author Shinoow
 *
 * @since 1.12.0
 */
public interface IUnlockableItem {

	/**
	 * Sets the unlock condition for the Item
	 * @param condition Unlock Condition
	 */
	public Item setUnlockCondition(IUnlockCondition condition);

	/**
	 * Getter for the Unlock Condition
	 * @param stack ItemStack holding the item requesting the Unlock Condition
	 * @return the Unlock Condition associated with the ItemStack
	 */
	public IUnlockCondition getUnlockCondition(ItemStack stack);
}
