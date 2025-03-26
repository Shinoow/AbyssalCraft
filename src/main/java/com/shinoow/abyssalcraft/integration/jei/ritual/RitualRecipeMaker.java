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
package com.shinoow.abyssalcraft.integration.jei.ritual;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.ritual.*;
import com.shinoow.abyssalcraft.lib.util.IHiddenRitual;

public class RitualRecipeMaker {

	@Nonnull
	public static List<CreationRitualRecipeWrapper> getCreationRituals(){
		List<CreationRitualRecipeWrapper> recipes = new ArrayList();

		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual instanceof NecronomiconCreationRitual && !(ritual instanceof IHiddenRitual))
				recipes.add(new CreationRitualRecipeWrapper((NecronomiconCreationRitual) ritual));

		return recipes;
	}

	@Nonnull
	public static List<RitualRecipeWrapper> getRituals(){
		List<RitualRecipeWrapper> recipes = new ArrayList();

		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(!(ritual instanceof NecronomiconCreationRitual)
					&& !(ritual instanceof NecronomiconTransformationRitual)&& !(ritual instanceof IHiddenRitual))
				recipes.add(new RitualRecipeWrapper(ritual));

		return recipes;
	}

	@Nonnull
	public static List<TransformationRitualRecipeWrapper> getTransformationRituals(){
		List<TransformationRitualRecipeWrapper> recipes = new ArrayList();

		for(NecronomiconRitual ritual : RitualRegistry.instance().getRituals())
			if(ritual instanceof NecronomiconTransformationRitual && !(ritual instanceof IHiddenRitual))
				recipes.add(new TransformationRitualRecipeWrapper((NecronomiconTransformationRitual) ritual));

		return recipes;
	}
}
