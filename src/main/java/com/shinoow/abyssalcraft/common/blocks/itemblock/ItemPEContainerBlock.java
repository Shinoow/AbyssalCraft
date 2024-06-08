/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.energy.IEnergyBlock;
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.energy.IEnergyRelayBlock;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemPEContainerBlock extends ItemBlockAC implements IEnergyContainerItem {

	public ItemPEContainerBlock(Block block) {
		super(block);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs par2CreativeTab, NonNullList<ItemStack> par3List){
		if(isInCreativeTab(par2CreativeTab)){
			par3List.add(new ItemStack(this));
			ItemStack stack = new ItemStack(this);
			addEnergy(stack, getMaxEnergy(stack));
			par3List.add(stack);
		}
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		Block block = Block.getBlockFromItem(is.getItem());
		if(block instanceof IEnergyRelayBlock)
			l.add(String.format("Range: %d Blocks", ((IEnergyRelayBlock) block).getRange()));
		if(block == ACBlocks.idol_of_fading) {
			l.add("It can summon beings from beyond");
			l.add("when the sky can't reach it.");
		}
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		Block block = Block.getBlockFromItem(stack.getItem());
		if(block instanceof IEnergyBlock)
			return ((IEnergyBlock) block).getMaxEnergy(stack);
		return 0;
	}

	@Override
	public boolean canAcceptPE(ItemStack stack) {
		Block block = Block.getBlockFromItem(stack.getItem());
		return block instanceof IEnergyBlock && ((IEnergyBlock) block).canAcceptPE() && getContainedEnergy(stack) < getMaxEnergy(stack);
	}

	@Override
	public boolean canTransferPE(ItemStack stack) {
		Block block = Block.getBlockFromItem(stack.getItem());
		return block instanceof IEnergyBlock && ((IEnergyBlock) block).canTransferPE() && getContainedEnergy(stack) > 0;
	}
}
