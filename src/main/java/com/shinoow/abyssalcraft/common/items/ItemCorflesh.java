package com.shinoow.abyssalcraft.common.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemCorflesh extends ItemFood {

	public ItemCorflesh(int j, float f, boolean b) {
		super(j, f, b);

	}

	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		entityPlayer.addPotionEffect(new PotionEffect(Potion.hunger.id, 600, 1));
		entityPlayer.addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 600, 0));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.confusion.id, 600, 0));
		return;
	}

}
