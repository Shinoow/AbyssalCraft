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
package com.shinoow.abyssalcraft.integration.jei.crystallizer;

import javax.annotation.Nonnull;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.util.ResourceLocation;

public abstract class CrystallizerRecipeCategory implements IRecipeCategory {
	protected static final int inputSlot = 0;
	protected static final int fuelSlot = 1;
	protected static final int outputSlot = 2;
	protected static final int outputSlot2 = 3;

	protected final ResourceLocation backgroundLocation;
	@Nonnull
	protected final IDrawableAnimated flame;
	@Nonnull
	protected final IDrawableAnimated arrow;

	public CrystallizerRecipeCategory(IGuiHelper guiHelper) {
		backgroundLocation = new ResourceLocation("abyssalcraft", "textures/gui/container/crystallizer_NEI.png");

		IDrawableStatic flameDrawable = guiHelper.createDrawable(backgroundLocation, 176, 0, 14, 14);
		flame = guiHelper.createAnimatedDrawable(flameDrawable, 300, IDrawableAnimated.StartDirection.TOP, true);

		IDrawableStatic arrowDrawable = guiHelper.createDrawable(backgroundLocation, 176, 14, 24, 17);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
	}
}
