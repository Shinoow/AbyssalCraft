package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemOC extends ItemFood

{

	public ItemOC(int j, float f, boolean b)
	{
		super(j, f, b);
		setAlwaysEdible();
	}
	public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer entityPlayer)
	{
		world.playSoundAtEntity(entityPlayer, "random.burp", 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

		entityPlayer.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.nightVision.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.invisibility.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.regeneration.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.jump.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.id, 6000, 6));
		entityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 6));


		return;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add("This item contains incredible powers");
	}

	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack)
	{
		return true;
	}

}

