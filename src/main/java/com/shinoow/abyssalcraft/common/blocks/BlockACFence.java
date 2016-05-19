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
package com.shinoow.abyssalcraft.common.blocks;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class BlockACFence extends BlockFence {

	public BlockACFence(Material par3Material, String par4, int par5, SoundType stepSound) {
		super(par3Material, par3Material.getMaterialMapColor());
		setCreativeTab(AbyssalCraft.tabDecoration);
		this.setHarvestLevel(par4, par5);
		setSoundType(stepSound);
	}

	public BlockACFence(Material par3Material, SoundType stepSound) {
		super(par3Material, par3Material.getMaterialMapColor());
		setCreativeTab(AbyssalCraft.tabDecoration);
		setSoundType(stepSound);
	}

	@Override
	public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
		return true;
	}

	//	public static boolean func_149825_a(Block par0) {
	//		return par0 == AbyssalCraft.abyfence || par0 == AbyssalCraft.DSBfence || par0 == AbyssalCraft.DLTfence || par0 == AbyssalCraft.dreadbrickfence || par0 == AbyssalCraft.abydreadbrickfence
	//				|| par0 == AbyssalCraft.cstonebrickfence || par0 == AbyssalCraft.DrTfence || par0 == AbyssalCraft.ethaxiumfence || par0 == AbyssalCraft.darkethaxiumfence;
	//	}

	//	@Override
	//	@SideOnly(Side.CLIENT)
	//	public void registerBlockIcons(IIconRegister par1IconRegister) {
	//		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + fenceIconName);
	//	}
}
