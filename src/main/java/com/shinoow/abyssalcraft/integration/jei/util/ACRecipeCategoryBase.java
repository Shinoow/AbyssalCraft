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
package com.shinoow.abyssalcraft.integration.jei.util;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.AbyssalCraft;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;

public abstract class ACRecipeCategoryBase<T extends IRecipeWrapper> implements IRecipeCategory<T> {

	@Nonnull
	protected IDrawable background;
	private final String title, uid;

	public ACRecipeCategoryBase(String title, String uid) {
		this.title = title;
		this.uid = uid;
	}

	@Override
	public String getUid() {
		return uid;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getModName() {
		return AbyssalCraft.name;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

}
