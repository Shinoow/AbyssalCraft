package com.shinoow.abyssalcraft.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemCorbone extends ItemFood {

	public ItemCorbone(int j, float f, boolean b) {
		super(j, f, b);

	}

	public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		super.onEaten(par1ItemStack, par2World, par3EntityPlayer);
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1));
		par3EntityPlayer.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 600, 0));
		par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 600, 0));
		return new ItemStack(Items.bone);
	}


}
