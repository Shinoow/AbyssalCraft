/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class ItemCrystal extends Item implements ICrystal {

	public ItemCrystal(String name){
		super();
		setUnlocalizedName(name);
		setCreativeTab(ACTabs.tabCrystals);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	//	@Override
	//	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
	//		if(par1ItemStack.getItem() == AbyssalCraft.crystalOxygen && par3EntityPlayer.isInWater() && par3EntityPlayer.getAir() != 300){
	//			par3EntityPlayer.setAir(300);
	//			par1ItemStack.stackSize--;
	//		}
	//		if(par1ItemStack.getItem() == AbyssalCraft.crystalSulfur)
	//			par3EntityPlayer.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 100));
	//		return par1ItemStack;
	//	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item par1Item, CreativeTabs par2CreativeTab, List par3List){
		for(int i = 0; i < ACLib.crystalNames.length; ++i)
			par3List.add(new ItemStack(par1Item, 1, i));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(I18n.translateToLocal("tooltip.crystal")+ ": " + ACLib.crystalAtoms[is.getItemDamage()]);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return I18n.translateToLocal(getUnlocalizedName() + "." + ACLib.crystalNames[par1ItemStack.getItemDamage()] + ".name");
	}
}