/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCrystal extends Item {

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