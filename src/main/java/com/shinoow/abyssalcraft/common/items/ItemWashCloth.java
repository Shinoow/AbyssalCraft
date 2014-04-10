package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.common.ItemGeneralAC;

public class ItemWashCloth extends ItemGeneralAC {

	public ItemWashCloth() {
		super();
		setMaxDamage(20);
		setMaxStackSize(1);
	}

	@Override
	public ItemStack getContainerItem(ItemStack stack) {
	    ItemStack result = stack.copy();
	    result.setItemDamage(stack.getItemDamage() + 1);
		return result;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack) {
	    return stack.getItemDamage() < stack.getMaxDamage();
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add("This item has been used " + this.getDamage(is) + " out of " + this.getMaxDamage() + " times");
	}

}
