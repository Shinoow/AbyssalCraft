/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2018 Shinoow.
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
import com.shinoow.abyssalcraft.api.necronomicon.condition.DimensionCondition;
import com.shinoow.abyssalcraft.api.necronomicon.condition.IUnlockCondition;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemTieredEnergyRelayBlock extends ItemMetadataPEContainerBlock {

	public ItemTieredEnergyRelayBlock(Block b) {
		super(b);
	}

	@Override
	public IUnlockCondition getUnlockCondition(ItemStack stack) {
		if(stack.getMetadata() > 0)
			return new DimensionCondition(getDim(stack.getItem()));
		return super.getUnlockCondition(stack);
	}

	private int getDim(Item item){
		if(item == Item.getItemFromBlock(ACBlocks.abyssal_wasteland_energy_relay))
			return ACLib.abyssal_wasteland_id;
		else if(item == Item.getItemFromBlock(ACBlocks.dreadlands_energy_relay))
			return ACLib.dreadlands_id;
		else if(item == Item.getItemFromBlock(ACBlocks.omothol_energy_relay))
			return ACLib.omothol_id;
		else return 0;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List l, boolean B){
		super.addInformation(is, player, l, B);
		l.add(String.format("Range: %d Blocks", getRange(is.getItem())));
	}

	protected int getRange(Item item){

		if(item == Item.getItemFromBlock(ACBlocks.overworld_energy_relay))
			return 6;
		else if(item == Item.getItemFromBlock(ACBlocks.abyssal_wasteland_energy_relay))
			return 8;
		else if(item == Item.getItemFromBlock(ACBlocks.dreadlands_energy_relay))
			return 10;
		else if(item == Item.getItemFromBlock(ACBlocks.omothol_energy_relay))
			return 12;
		else return 0;
	}

	@Override
	public int getMaxEnergy(ItemStack stack) {
		return 500;
	}
}
