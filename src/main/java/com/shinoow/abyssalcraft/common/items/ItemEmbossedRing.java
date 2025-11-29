package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import com.shinoow.abyssalcraft.lib.item.ItemACBasic;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEmbossedRing extends ItemACBasic {

	private int tier;

	public ItemEmbossedRing(String name, int tier) {
		super(name);
		this.tier = tier;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add(TranslationUtil.toLocalFormatted("tooltip.tiereditem.tier", tier));
	}

}
