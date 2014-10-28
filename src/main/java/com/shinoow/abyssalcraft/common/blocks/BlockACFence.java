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

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockACFence extends BlockFence {

	private String fenceIconName;

	public BlockACFence(String par2, Material par3Material, String par4, int par5) {
		super(par2, par3Material);
		fenceIconName = par2;
		setCreativeTab(AbyssalCraft.tabDecoration);
		this.setHarvestLevel(par4, par5);
	}

	public BlockACFence(String par2, Material par3Material) {
		super(par2, par3Material);
		fenceIconName = par2;
		setCreativeTab(AbyssalCraft.tabDecoration);
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}

	public static boolean func_149825_a(Block par0) {
		return par0 == AbyssalCraft.abyfence || par0 == AbyssalCraft.DSBfence || par0 == AbyssalCraft.DLTfence || par0 == AbyssalCraft.dreadbrickfence || par0 == AbyssalCraft.abydreadbrickfence
				|| par0 == AbyssalCraft.cstonebrickfence || par0 == AbyssalCraft.DrTfence || par0 == AbyssalCraft.ethaxiumfence;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + fenceIconName);
	}
}