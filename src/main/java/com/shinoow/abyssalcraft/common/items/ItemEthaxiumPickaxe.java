/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.items;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import com.google.common.collect.Sets;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.init.BlockHandler;

public class ItemEthaxiumPickaxe extends ItemACPickaxe {

	private static Set<Block> effectiveBlocks = Sets.newHashSet( new Block[] {ACBlocks.ethaxium, ACBlocks.ethaxium_brick, ACBlocks.ethaxium_pillar, ACBlocks.ethaxium_brick_fence, ACBlocks.ethaxium_brick_slab, BlockHandler.ethaxiumslab2, ACBlocks.ethaxium_brick_stairs, ACBlocks.block_of_ethaxium, ACBlocks.materializer, ACBlocks.dark_ethaxium_brick, ACBlocks.dark_ethaxium_pillar, ACBlocks.dark_ethaxium_brick_stairs, ACBlocks.dark_ethaxium_brick_slab, BlockHandler.darkethaxiumslab2, ACBlocks.dark_ethaxium_brick_fence});

	public ItemEthaxiumPickaxe(ToolMaterial mat, String name)
	{
		super(mat, name, 8, EnumChatFormatting.AQUA);
	}

	@Override
	public float getDigSpeed(ItemStack stack, IBlockState state)
	{
		if(effectiveBlocks.contains(state.getBlock()))
			return efficiencyOnProperMaterial * 10;
		if (state.getBlock().isToolEffective("pickaxe", state))
			return efficiencyOnProperMaterial;
		return super.getDigSpeed(stack, state);
	}
}
