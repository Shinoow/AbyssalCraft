package com.shinoow.abyssalcraft.integration.jei.anvil;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.recipe.AnvilForging;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

public class AnvilForgingRecipeWrapper implements IRecipeWrapper {

	@Nonnull
	private final ItemStack INPUT1, INPUT2, OUTPUT;

	@Nonnull
	private final String EXPERIENCE_STRING;

	public AnvilForgingRecipeWrapper(@Nonnull AnvilForging forging) {
		INPUT1 = forging.INPUT1.copy();
		INPUT2 = forging.INPUT2.copy();
		OUTPUT = forging.getOutput();
		EXPERIENCE_STRING = I18n.format("gui.acjei.anvil.cost", forging.getPrice());
	}

	@Override
	public void drawInfo(@Nonnull Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		FontRenderer fontRendererObj = minecraft.fontRenderer;
		fontRendererObj.drawString(EXPERIENCE_STRING, 69 - fontRendererObj.getStringWidth(EXPERIENCE_STRING) / 2, 19, Color.gray.getRGB());
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		List<List<ItemStack>> input = new ArrayList<>();

		input.add(Collections.singletonList(INPUT1));
		input.add(Collections.singletonList(INPUT2));

		ingredients.setInputLists(VanillaTypes.ITEM, input);
		ingredients.setOutput(VanillaTypes.ITEM, OUTPUT);
	}

}
