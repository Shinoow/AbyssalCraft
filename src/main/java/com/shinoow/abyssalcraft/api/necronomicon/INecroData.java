/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.api.necronomicon;

import com.shinoow.abyssalcraft.api.knowledge.IResearchItem;

public interface INecroData {

	String getTitle();

	int getDisplayIcon();

	String getText();

	boolean hasText();

	String getIdentifier();

	//TODO use IResearchItem instead
	IResearchItem getResearch();
}
