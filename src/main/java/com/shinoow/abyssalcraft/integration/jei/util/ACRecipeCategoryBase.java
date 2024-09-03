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
