package com.shinoow.abyssalcraft.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemPlatefood extends ItemFood
{

	public ItemPlatefood(int j, float f, boolean b) {
		super(j, f, b);
		this.setCreativeTab(AbyssalCraft.tabFood);
		this.setMaxStackSize(1);

	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
		return new ItemStack(AbyssalCraft.dirtyplate);
	}

}
