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

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
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
				|| par0 == AbyssalCraft.cstonebrickfence || par0 == AbyssalCraft.DrTfence || par0 == AbyssalCraft.ethaxiumfence || par0 == AbyssalCraft.darkethaxiumfence;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon(AbyssalCraft.modid + ":" + fenceIconName);
	}
}
