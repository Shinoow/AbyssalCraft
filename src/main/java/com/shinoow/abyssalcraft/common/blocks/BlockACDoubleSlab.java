/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.*;

public class BlockACDoubleSlab extends BlockSlab {

	private static Block singleSlab;
	private static Block doubleSlab;

	public BlockACDoubleSlab(Block par1SingleSlab, Block par2DoubleSlab, Material par3Material, String tooltype, int harvestlevel)
	{
		super(true, par3Material);
		setCreativeTab(null);
		setLightOpacity(0);
		singleSlab = par1SingleSlab;
		doubleSlab = par2DoubleSlab;
		setHarvestLevel(tooltype, harvestlevel);
	}

	public BlockACDoubleSlab(Block par1SingleSlab, Block par2DoubleSlab, Material par3Material)
	{
		super(true, par3Material);
		setCreativeTab(null);
		setLightOpacity(0);
		singleSlab = par1SingleSlab;
		doubleSlab = par2DoubleSlab;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return singleSlab.getItemDropped(par1, par2Random, par3);
	}

	@SideOnly(Side.CLIENT)
	private static boolean func_150003_a(Block p_150003_0_)
	{
		return p_150003_0_ == doubleSlab;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return func_150003_a(this) ? Item.getItemFromBlock(this) : this == doubleSlab ? Item.getItemFromBlock(singleSlab) : Item.getItemFromBlock(doubleSlab);
	}

	@Override
	public String func_150002_b(int var1)
	{
		return super.getUnlocalizedName();
	}
}