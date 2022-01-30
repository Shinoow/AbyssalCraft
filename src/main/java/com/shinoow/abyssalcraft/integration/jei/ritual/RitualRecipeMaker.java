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
package com.shinoow.abyssalcraft.integration.jei.ritual;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.ritual.NecronomiconCreationRitual;
import com.shinoow.abyssalcraft.api.ritual.NecronomiconRitual;
import com.shinoow.abyssalcraft.api.ritual.RitualRegistry;
import com.shinoow.abyssalcraft.lib.util.IHiddenRitual;

public class RitualRecipeMaker {

	@Nonnull
	public static List<NecronomiconCreationRitual> getRituals(){
		List<NecronomiconCreationRitual> recipes = new ArrayList();

		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual instanceof NecronomiconCreationRitual && !(ritual instanceof IHiddenRitual))
				recipes.add((NecronomiconCreationRitual) ritual);

		return recipes;
	}
}
