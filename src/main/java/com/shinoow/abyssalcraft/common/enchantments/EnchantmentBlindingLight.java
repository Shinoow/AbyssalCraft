package com.shinoow.abyssalcraft.common.enchantments;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;

public class EnchantmentBlindingLight extends Enchantment {

	public EnchantmentBlindingLight() {
		super(Rarity.COMMON, EnumEnchantmentType.BREAKABLE, new EntityEquipmentSlot[]{EntityEquipmentSlot.OFFHAND});
		setName("abyssalcraft.blinding_light");
	}

	@Override
	public boolean canApplyAtEnchantingTable(ItemStack stack)
	{
		return stack.getItemUseAction() == EnumAction.BLOCK 
				|| stack.getItem() == Items.SHIELD;
	}

	@Override
	public int getMinEnchantability(int par1)
	{
		return 14;
	}

	@Override
	public int getMaxEnchantability(int par1)
	{
		return super.getMinEnchantability(par1) + 30;
	}

	@Override
	public int getMaxLevel(){
		return 1;
	}
}
