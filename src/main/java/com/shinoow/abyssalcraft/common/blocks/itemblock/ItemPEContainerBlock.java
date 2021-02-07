/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
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
import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import com.shinoow.abyssalcraft.api.energy.PEUtils;

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
		//TODO handling for tiered energy relays
		if(Block.getBlockFromItem(is.getItem()) == ACBlocks.energy_relay)
			l.add("Range: 4 Blocks");
	}

	@Override
	public float getContainedEnergy(ItemStack stack) {
		return PEUtils.getContainedEnergy(stack);
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		Block block = Block.getBlockFromItem(stack.getItem());
		//TODO add some hook to the blocks where this value is fetched from
		if(block == ACBlocks.energy_pedestal || block == ACBlocks.sacrificial_altar || block == ACBlocks.rending_pedestal)
			return 5000;
		else if(block == ACBlocks.energy_relay)
			return 500;
		else if(block == ACBlocks.energy_collector)
			return 1000;
		else if(block == ACBlocks.energy_container)
			return 10000;
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
		//TODO handling for tiered sacrificial altar as well
		return Block.getBlockFromItem(stack.getItem()) != ACBlocks.sacrificial_altar && getContainedEnergy(stack) < getMaxEnergy(stack);
	}

	@Override
	public boolean canTransferPE(ItemStack stack) {
		return getContainedEnergy(stack) > 0;
	}
}
