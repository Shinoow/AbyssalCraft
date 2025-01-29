/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.lib.item;

import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.Crystals;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.item.ItemStack;

public class ItemCrystal extends ItemACBasic implements ICrystal {

	boolean suffix;
	String suffix_string;
	int type;

	public ItemCrystal(String name, int type){
		this(name, type, false);
	}

	public ItemCrystal(String name, int type, boolean suffix){
		super(String.format("%s.%s", "crystal", Crystals.crystalNames[type]));
		setCreativeTab(ACTabs.tabCrystals);
		setMaxDamage(0);
		this.suffix = suffix;
		this.type = type;
		suffix_string = name+".suffix";
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
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(suffix)
			return TranslationUtil.toLocalFormatted(suffix_string, super.getItemStackDisplayName(par1ItemStack));
		return super.getItemStackDisplayName(par1ItemStack);
	}

	@Override
	public int getColor(ItemStack stack) {

		return ACClientVars.getCrystalColors()[type];
	}

	@Override
	public String getFormula(ItemStack stack) {

		return Crystals.crystalAtoms[type];
	}
}
