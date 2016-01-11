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

import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class BlockSolidLava extends BlockACBasic {

	public BlockSolidLava(String par1) {
		super(Material.lava, "pickaxe", 2, 10F, 100F, soundTypeStone);
		setBlockName(par1);
		setBlockTextureName("lava_still");
		setCreativeTab(AbyssalCraft.tabDecoration);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		float f = 0.125F;
		return AxisAlignedBB.getBoundingBox(par2, par3, par4, par2 + 1, par3 + 1 - f, par4 + 1);
	}

	@Override
	public boolean isBurning(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}
}
