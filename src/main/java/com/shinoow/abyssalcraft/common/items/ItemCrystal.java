/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import com.shinoow.abyssalcraft.api.item.ICrystal;
import com.shinoow.abyssalcraft.lib.ACClientVars;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings("deprecation")
public class ItemCrystal extends ItemACBasic implements ICrystal {

	boolean postfix;

	public ItemCrystal(String name){
		this(name, false);
	}

	public ItemCrystal(String name, boolean postfix){
		super(name);
		setCreativeTab(ACTabs.tabCrystals);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.postfix = postfix;
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

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs par2CreativeTab, NonNullList<ItemStack> par3List){
		if(isInCreativeTab(par2CreativeTab))
			for(int i = 0; i < ACLib.crystalNames.length; ++i)
				par3List.add(new ItemStack(this, 1, i));
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		if(postfix)
			return I18n.translateToLocalFormatted(getTranslationKey().substring(5)+ ".postfix", I18n.translateToLocal("item.crystal." + ACLib.crystalNames[par1ItemStack.getItemDamage()] + ".name"));
		else return I18n.translateToLocal(getTranslationKey() + "." + ACLib.crystalNames[par1ItemStack.getItemDamage()] + ".name");
	}

	@Override
	public int getColor(ItemStack stack) {

		return ACClientVars.getCrystalColors()[stack.getMetadata()];
	}

	@Override
	public String getFormula(ItemStack stack) {

		return ACLib.crystalAtoms[stack.getMetadata()];
	}
}
