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
package com.shinoow.abyssalcraft.common.blocks.itemblock;

import java.util.List;

import com.shinoow.abyssalcraft.api.energy.*;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemPEContainerBlock extends ItemBlockAC implements IEnergyContainerItem {

	public ItemPEContainerBlock(Block block) {
		super(block);
	}

	@Override
	public void addInformation(ItemStack is, World player, List<String> l, ITooltipFlag B){
		Block block = Block.getBlockFromItem(is.getItem());
		if(block instanceof IEnergyRelayBlock)
			l.add(String.format("Range: %d Blocks", ((IEnergyRelayBlock) block).getRange()));
	}

	@Override
	public float getContainedEnergy(ItemStack stack) {
		return PEUtils.getContainedEnergy(stack);
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		Block block = Block.getBlockFromItem(stack.getItem());
		if(block instanceof IEnergyBlock)
			return ((IEnergyBlock) block).getMaxEnergy(stack);
		return 0;
	}

	@Override
	public void addEnergy(ItemStack stack, float energy) {
		PEUtils.addEnergy(this, stack, energy);
	}

	@Override
	public float consumeEnergy(ItemStack stack, float energy) {
		return PEUtils.consumeEnergy(stack, energy);
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
