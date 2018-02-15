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
package com.shinoow.abyssalcraft.api.necronomicon;

import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;

public interface INecroData {

	public String getTitle();

	public int getDisplayIcon();

	public String getText();

	public boolean hasText();

	public String getIdentifier();

	public IUnlockCondition getCondition();
}
