/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.lib.ACTabs;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BlockWastelandsThorn extends BlockBush {

	public BlockWastelandsThorn(){
		super();
		setUnlocalizedName("wastelandsthorn");
		setCreativeTab(ACTabs.tabDecoration);
	}

	@Override
	protected boolean canPlaceBlockOn(Block ground)
	{
		return ground == ACBlocks.fused_abyssal_sand || ground == ACBlocks.abyssal_sand ||
				ground.getMaterial() == Material.grass || super.canPlaceBlockOn(ground);
	}

	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn)
	{
		if(entityIn instanceof EntityPlayer && ((EntityPlayer)entityIn).getCurrentArmor(0) == null &&
				((EntityPlayer)entityIn).getCurrentArmor(1) == null)
			entityIn.attackEntityFrom(DamageSource.cactus, 1.0F);
	}
}
