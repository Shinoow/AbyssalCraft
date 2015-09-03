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

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemMetadata extends ItemACBasic {

	private String[] names;

	@SideOnly(Side.CLIENT)
	private IIcon[] icons;

	private boolean moreIcons;

	public ItemMetadata(String name, boolean icons, String...names){
		super(name);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.names = names;
		moreIcons = icons;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int i)
	{
		if(moreIcons){
			int j = MathHelper.clamp_int(i, 0, names.length);
			return icons[j];
		} else return super.getIconFromDamage(i);
	}

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

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		if(moreIcons){
			icons = new IIcon[names.length];
			for(int i = 0; i < names.length; i++)
				icons[i] = par1IconRegister.registerIcon(getIconString() + "_" + names[i]);
		}
		else super.registerIcons(par1IconRegister);
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {
		return StatCollector.translateToLocal(getUnlocalizedName() + "." + names[par1ItemStack.getItemDamage()] + ".name");
	}
}
