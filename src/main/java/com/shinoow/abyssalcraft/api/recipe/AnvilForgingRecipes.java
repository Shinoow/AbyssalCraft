package com.shinoow.abyssalcraft.api.recipe;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.api.APIUtils;

import net.minecraft.item.ItemStack;

/**
 * Handling for custom Anvil recipes
 * @author shinoow
 *
 * @since 2.0
 */
public class AnvilForgingRecipes {

	private static final AnvilForgingRecipes INSTANCE = new AnvilForgingRecipes();

	private final List<AnvilForging> recipes = new ArrayList<>();

	public static AnvilForgingRecipes instance() {
		return INSTANCE;
	}

	private AnvilForgingRecipes() {}

	/**
	 * Creates a Anvil Forging recipe
	 * @param input1 Left ItemStack
	 * @param input2 Right ItemStack (can be empty)
	 * @param output Output ItemStack
	 */
	public void forge(ItemStack input1, ItemStack input2, ItemStack output) {
		forge(input1, input2, output, AnvilForgingType.DEFAULT, 1);
	}

	/**
	 * Creates a Anvil Forging recipe
	 * @param input1 Left ItemStack
	 * @param input2 Right ItemStack (can be empty)
	 * @param output Output ItemStack
	 * @param price Price in levels (optional, default is 1)
	 */
	public void forge(ItemStack input1, ItemStack input2, ItemStack output, int price) {
		forge(input1, input2, output, AnvilForgingType.DEFAULT, price);
	}

	/**
	 * Creates a Anvil Forging recipe
	 * @param input1 Left ItemStack
	 * @param input2 Right ItemStack (can be empty)
	 * @param output Output ItemStack
	 * @param type Forging Type (optional), used for recipe filtering in the Necronomicon
	 * @param price Price in levels (optional, default is 1)
	 */
	public void forge(ItemStack input1, ItemStack input2, ItemStack output, AnvilForgingType type, int price) {
		forge(new AnvilForging(input1, input2, output).setType(type).setPrice(price));
	}

	public void forge(AnvilForging forging) {
		if(forging.getPrice() < 1)
			forging.setPrice(1);
		recipes.add(forging);
	}

	public AnvilForging getAnvilForgingResult(ItemStack input1, ItemStack input2) {
		AnvilForging result = recipes.stream()
				.filter(e -> APIUtils.areStacksEqual(input1, e.INPUT1)
						&& innerAreStacksEquals(input2, e.INPUT2))
				.findFirst()
				.orElse(null);
		return result;
	}

	public List<AnvilForging> getForgingList() {
		return recipes;
	}

	/**
	 * Removes Anvil Forging(s) based on output
	 * @param output Output ItemStack
	 */
	public void removeForging(ItemStack output) {
		recipes.removeIf(e -> APIUtils.areStacksEqual(output, e.getOutput()));
	}

	/**
	 * Removes Anvil Forging(s) based on single input
	 * @param input Input ItemStack
	 */
	public void removeForgingInput(ItemStack input) {
		recipes.removeIf(e -> APIUtils.areStacksEqual(input, e.INPUT1));
	}

	/**
	 * Removes Anvil Forging based on both inputs matching
	 * @param input1 First input ItemStack
	 * @param input2 Second input ItemStack
	 */
	public void removeForgingInputs(ItemStack input1, ItemStack input2) {
		recipes.removeIf(e -> APIUtils.areStacksEqual(input1, e.INPUT1)
				&& APIUtils.areStacksEqual(input2, e.INPUT2));
	}

	/*
	 * areStacksEqual where both stack being empty counts as true
	 */
	private boolean innerAreStacksEquals(ItemStack stack1, ItemStack stack2 ) {
		if(stack1.isEmpty() && stack2.isEmpty())
			return true;
		return APIUtils.areStacksEqual(stack1, stack2);
	}
}
