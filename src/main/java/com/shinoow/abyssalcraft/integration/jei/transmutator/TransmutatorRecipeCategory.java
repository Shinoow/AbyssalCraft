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
package com.shinoow.abyssalcraft.integration.jei.transmutator;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeBackgrounds;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryBase;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class TransmutatorRecipeCategory<T extends IRecipeWrapper> extends ACRecipeCategoryBase<T> {
	protected static final int inputSlot = 0;
	protected static final int fuelSlot = 1;
	protected static final int outputSlot = 2;

	@Nonnull
	protected final IDrawableAnimated flame;
	@Nonnull
	protected final IDrawableAnimated arrow;

	public TransmutatorRecipeCategory(IGuiHelper guiHelper, String title, String uid) {
		super(title, uid);

		IDrawableStatic flameDrawable = guiHelper.createDrawable(ACRecipeBackgrounds.TRANSMUTATOR, 176, 0, 13, 13);
		flame = guiHelper.createAnimatedDrawable(flameDrawable, 300, IDrawableAnimated.StartDirection.TOP, true);

		IDrawableStatic arrowDrawable = guiHelper.createDrawable(ACRecipeBackgrounds.TRANSMUTATOR, 176, 14, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
	}
}
