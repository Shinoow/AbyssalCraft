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
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockACSingleSlab extends BlockSlab {

	public BlockACSingleSlab(Material par3Material, String tooltype, int harvestlevel)
	{
		super(false, par3Material);
		setCreativeTab(AbyssalCraft.tabBlock);
		setLightOpacity(0);
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACSingleSlab(Material par3Material)
	{
		super(false, par3Material);
		setCreativeTab(AbyssalCraft.tabBlock);
		setLightOpacity(0);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return Item.getItemFromBlock(this);
	}

	@Override
	public String func_150002_b(int var1) {

		return super.getUnlocalizedName();
	}
}