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

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.item.ICrystal;

public class ItemCrystal extends Item implements ICrystal {

	private String[] names = new String[]{"Iron", "Gold", "Sulfur", "Carbon", "Oxygen", "Hydrogen", "Nitrogen", "Phosphorus",
			"Potassium", "Nitrate", "Methane", "Redstone", "Abyssalnite", "Coralium", "Dreadium", "Blaze", "Tin", "Copper",
			"Silicon", "Magnesium", "Aluminium", "Silica", "Alumina", "Magnesia", "Zinc"};
	private String[] atoms = new String[]{"Fe", "Au", "S", "C", "O", "H", "N", "P", "K", "NO\u2083", "CH\u2084", "none", "An",
			"Cor", "Dr", "none", "Sn", "Cu", "Si", "Mg", "Al", "SiO\u2082", "Al\u2082O\u2083", "MgO", "Zn"};
	public ItemCrystal(String name){
		super();
		//		GameRegistry.registerItem(this, name);
		setUnlocalizedName(name);
		setCreativeTab(AbyssalCraft.tabCrystals);
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
		for(int i = 0; i < names.length; ++i)
			par3List.add(new ItemStack(par1Item, 1, i));
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(I18n.translateToLocal("tooltip.crystal")+ ": " + atoms[is.getItemDamage()]);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return I18n.translateToLocal(getUnlocalizedName() + "." + names[par1ItemStack.getItemDamage()] + ".name");
	}
}
