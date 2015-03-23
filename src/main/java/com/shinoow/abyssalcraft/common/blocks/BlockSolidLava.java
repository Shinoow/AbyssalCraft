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