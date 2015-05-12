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