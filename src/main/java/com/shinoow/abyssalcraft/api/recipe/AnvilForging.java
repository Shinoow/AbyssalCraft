package com.shinoow.abyssalcraft.api.recipe;

import net.minecraft.item.ItemStack;

public class AnvilForging {

	public final ItemStack INPUT1, INPUT2;
	private final ItemStack OUTPUT;
	public AnvilForgingType TYPE;
	private int PRICE;

	public AnvilForging(ItemStack input1, ItemStack input2, ItemStack output) {
		INPUT1 = input1;
		INPUT2 = input2;
		OUTPUT = output;
		TYPE = AnvilForgingType.DEFAULT;
		PRICE = 1;
	}

	public AnvilForging setType(AnvilForgingType type) {
		TYPE = type;
		return this;
	}

	public AnvilForging setPrice(int price) {
		PRICE = price;
		return this;
	}

	public ItemStack getOutput() {
		return OUTPUT.copy();
	}

	public int getPrice() {
		return PRICE;
	}
}
