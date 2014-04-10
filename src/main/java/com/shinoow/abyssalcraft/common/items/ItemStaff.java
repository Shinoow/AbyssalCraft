package com.shinoow.abyssalcraft.common.items;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;

import com.shinoow.abyssalcraft.common.ItemGeneralAC;

public class ItemStaff extends ItemGeneralAC 

{
	public ItemStaff() {
		super();
		setMaxStackSize(1);

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add("The staff of J'zahar, Gatekeeper of the Abyss");
	}

	public EnumRarity getRarity(ItemStack is){
		return EnumRarity.epic;
	}

	public boolean isFull3D()
	{
		return true;
	}

}