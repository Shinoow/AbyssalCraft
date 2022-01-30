/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.integration.jei.rending;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.rending.RendingRegistry;

public class RendingRecipeMaker {

	@Nonnull
	public static List<RendingRecipeWrapper> getRending(){
		return RendingRegistry.instance().getRendings().stream()
				.map(RendingRecipeWrapper::new)
				.collect(Collectors.toList());
	}
}
