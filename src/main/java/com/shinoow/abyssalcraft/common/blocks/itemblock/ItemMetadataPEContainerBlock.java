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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;

public class ItemMetadataPEContainerBlock extends ItemBlock {

	private static final String[] subNames = {
		"0", "1",  "2", "3", "4", "5", "6", "7",
		"8", "9", "10", "11", "12", "13", "14", "15"};

	public ItemMetadataPEContainerBlock(Block b) {
		super(b);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		l.add(String.format("%d/%d PE", (int)getContainedEnergy(is), getMaxEnergy(is)));
	}

	public float getContainedEnergy(ItemStack stack) {
		float energy;
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		if(stack.getTagCompound().hasKey("PotEnergy"))
			energy = stack.getTagCompound().getFloat("PotEnergy");
		else {
			energy = 0;
			stack.getTagCompound().setFloat("PotEnergy", energy);
		}
		return energy;
	}

	public int getMaxEnergy(ItemStack stack) {
		int base = 5000;
		switch(stack.getItemDamage()){
		case 0:
			return  (int) (base * 1.5);
		case 1:
			return base * 2;
		case 2:
			return (int) (base * 2.5);
		case 3:
			return base * 3;
		default:
			return base;
		}
	}

	@Override
	public int getMetadata(int meta) {
		return meta;
	}

	@Override
	public String getItemStackDisplayName(ItemStack par1ItemStack) {

		return I18n.translateToLocal(getUnlocalizedName() + "." + subNames[par1ItemStack.getItemDamage()] + ".name");
	}
}
