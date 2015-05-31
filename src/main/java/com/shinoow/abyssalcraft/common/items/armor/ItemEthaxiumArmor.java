/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items.armor;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemEthaxiumArmor extends ItemArmor {
	public ItemEthaxiumArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer) {
		if(stack.getItem() == AbyssalCraft.ethHelmet || stack.getItem() == AbyssalCraft.ethPlate || stack.getItem() == AbyssalCraft.ethBoots)
			return "abyssalcraft:textures/armor/ethaxium_1.png";

		if(stack.getItem() == AbyssalCraft.ethLegs)
			return "abyssalcraft:textures/armor/ethaxium_2.png";
		else return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		itemIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + this.getUnlocalizedName().substring(5));
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemstack) {
		if (itemstack.getItem() == AbyssalCraft.ethHelmet) {
			if(world.provider.isSurfaceWorld() && !world.provider.isDaytime())
				player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
			if(player.getActivePotionEffect(Potion.hunger) !=null)
				player.removePotionEffect(Potion.hunger.getId());
			if(player.getActivePotionEffect(Potion.poison) !=null)
				player.removePotionEffect(Potion.poison.getId());
			if(world.rand.nextInt(300) == 0)
				player.addPotionEffect(new PotionEffect(Potion.field_76443_y.getId(), 60, 0));
		}
		if(itemstack.getItem() == AbyssalCraft.ethPlate){
			if(player.isBurning() || world.provider.isHellWorld)
				player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 20, 2));
			if(player.getActivePotionEffect(AbyssalCraft.antiMatter) !=null)
				player.removePotionEffect(AbyssalCraft.antiMatter.getId());
			if(world.rand.nextInt(200) == 0)
				player.addPotionEffect(new PotionEffect(Potion.damageBoost.id, 60));
		}
		if(itemstack.getItem() == AbyssalCraft.ethLegs)
			if(world.rand.nextInt(200) == 0)
				player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 60));
		if(itemstack.getItem() == AbyssalCraft.ethBoots)
			if(player.isInWater()){
				player.addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 20, 2));
				player.addPotionEffect(new PotionEffect(Potion.waterBreathing.id, 20, 1));
			}
	}
}
