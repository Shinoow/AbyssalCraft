package com.shinoow.abyssalcraft.integration.thaumcraft.wands;

import java.util.List;

import net.minecraft.item.ItemStack;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;

public class WandCapAC extends WandCap {

	public WandCapAC(String tag, float discount, ItemStack item, int craftCost) {
		super(tag, discount, item, craftCost);
	}

	public WandCapAC(String tag, float discount, List<Aspect> specialAspects, float discountSpecial, ItemStack item, int craftCost) {
		super(tag, discount, specialAspects, discountSpecial, item, craftCost);
	}

	@Override
	public String getResearch() {
		return "";
	}
}