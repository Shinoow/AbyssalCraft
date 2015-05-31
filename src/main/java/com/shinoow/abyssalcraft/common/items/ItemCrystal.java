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
package com.shinoow.abyssalcraft.common.items;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.item.ICrystal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrystal extends Item implements ICrystal {

	private String atoms;
	private int crystalColor;

	public ItemCrystal(String name, int color, String formula){
		super();
		setCreativeTab(AbyssalCraft.tabCrystals);
		setUnlocalizedName(name);
		setTextureName(AbyssalCraft.modid + ":" + "crystal");
		atoms = formula;
		crystalColor = color;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
		if(par1ItemStack.getItem() == AbyssalCraft.crystalOxygen && par3EntityPlayer.isInWater() && par3EntityPlayer.getAir() != 300){
			par3EntityPlayer.setAir(300);
			par1ItemStack.stackSize--;
		}
		if(par1ItemStack.getItem() == AbyssalCraft.crystalSulfur)
			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 100));
		return par1ItemStack;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack par1ItemStack, int par2) {
		if(crystalColor == 0) crystalColor = 16777215;
		return crystalColor;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(StatCollector.translateToLocal("tooltip.crystal")+ ": " + atoms);
	}
}
