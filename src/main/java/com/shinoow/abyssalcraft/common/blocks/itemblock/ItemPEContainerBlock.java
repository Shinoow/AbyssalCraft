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

import com.shinoow.abyssalcraft.api.energy.IEnergyContainer;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPEContainerBlock extends ItemBlock {

	public ItemPEContainerBlock(Block block) {
		super(block);
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

	@SideOnly(Side.CLIENT)
	public int getMaxEnergy(ItemStack stack) {
		if(block.hasTileEntity(block.getStateFromMeta(stack.getItemDamage()))){
			TileEntity tile = block.createTileEntity(Minecraft.getMinecraft().world, block.getStateFromMeta(stack.getItemDamage()));
			if(tile instanceof IEnergyContainer)
				return ((IEnergyContainer)tile).getMaxEnergy();
		}
		return 0;
	}
}
