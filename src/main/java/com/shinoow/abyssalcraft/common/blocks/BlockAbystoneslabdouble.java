/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockAbystoneslabdouble extends BlockSlab {

	public BlockAbystoneslabdouble(boolean par2)
	{
		super(par2, Material.rock);
		setCreativeTab(null);
		this.setHarvestLevel("pickaxe", 2);
		setLightOpacity(0);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return AbyssalCraft.abyslab1.getItemDropped(par1, par2Random, par3);
	}

	@SideOnly(Side.CLIENT)
	private static boolean func_150003_a(Block p_150003_0_)
	{
		return p_150003_0_ == AbyssalCraft.abyslab2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Item getItem(World par1World, int par2, int par3, int par4)
	{
		return func_150003_a(this) ? Item.getItemFromBlock(this) : this == AbyssalCraft.abyslab2 ? Item.getItemFromBlock(AbyssalCraft.abyslab1) : Item.getItemFromBlock(AbyssalCraft.abyslab1);
	}

	@Override
	public String func_150002_b(int var1){
		return super.getUnlocalizedName();
	}
}