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

public class ItemCoraliumArmor extends ItemArmor {
	public ItemCoraliumArmor(ArmorMaterial par2EnumArmorMaterial, int par3, int par4){
		super(par2EnumArmorMaterial, par3, par4);
		setCreativeTab(AbyssalCraft.tabCombat);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return EnumChatFormatting.AQUA + StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		if(stack.getItem() == AbyssalCraft.Corhelmet || stack.getItem() == AbyssalCraft.Corplate || stack.getItem() == AbyssalCraft.Corboots)
			return "abyssalcraft:textures/armor/coralium_1.png";

		if(stack.getItem() == AbyssalCraft.Corlegs)
			return "abyssalcraft:textures/armor/coralium_2.png";
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
		if (itemstack.getItem() == AbyssalCraft.Corhelmet)
		{
			player.addPotionEffect(new PotionEffect(Potion.waterBreathing.getId(), 20, 0));
			player.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 260, 0));
		}
		if (itemstack.getItem() == AbyssalCraft.Corplate)
			player.addPotionEffect(new PotionEffect(Potion.resistance.getId(), 20, 0));
		if (itemstack.getItem() == AbyssalCraft.Corboots)
			player.addPotionEffect(new PotionEffect(Potion.moveSpeed.getId(), 20, 1));
	}
}
