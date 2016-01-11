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

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.ForgeHooks;

import com.google.common.collect.Sets;
import com.shinoow.abyssalcraft.AbyssalCraft;

public class ItemEthaxiumPickaxe extends ItemACPickaxe {

	private static Set<Block> effectiveBlocks = Sets.newHashSet( new Block[] {AbyssalCraft.ethaxium, AbyssalCraft.ethaxiumbrick, AbyssalCraft.ethaxiumpillar, AbyssalCraft.ethaxiumfence, AbyssalCraft.ethaxiumslab1, AbyssalCraft.ethaxiumslab2, AbyssalCraft.ethaxiumstairs, AbyssalCraft.ethaxiumblock, AbyssalCraft.materializer, AbyssalCraft.darkethaxiumbrick, AbyssalCraft.darkethaxiumpillar, AbyssalCraft.darkethaxiumstairs, AbyssalCraft.darkethaxiumslab1, AbyssalCraft.darkethaxiumslab2, AbyssalCraft.darkethaxiumfence});

	public ItemEthaxiumPickaxe(ToolMaterial mat, String name)
	{
		super(mat, name, 8, EnumChatFormatting.AQUA);
	}

	@Override
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
		if(effectiveBlocks.contains(block))
			return efficiencyOnProperMaterial * 10;
		if (ForgeHooks.isToolEffective(stack, block, meta))
			return efficiencyOnProperMaterial;
		return super.getDigSpeed(stack, block, meta);
	}
}
