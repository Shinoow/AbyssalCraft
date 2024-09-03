package com.shinoow.abyssalcraft.integration.jei.spell;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryBase;
import com.shinoow.abyssalcraft.integration.jei.util.ACRecipeCategoryUid;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.client.resources.I18n;

public class SpellRecipeCategory extends ACRecipeCategoryBase<SpellRecipeWrapper> {

	public SpellRecipeCategory(IGuiHelper guiHelper) {
		super(I18n.format("container.abyssalcraft.spells.nei"), ACRecipeCategoryUid.SPELL);
		background = guiHelper.createBlankDrawable(166, 100);
		//TODO do the thing
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, SpellRecipeWrapper recipeWrapper, IIngredients ingredients) {
		
	}
}
