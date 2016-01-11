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

import java.util.Random;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityShoggothBiomass;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class BlockShoggothBiomass extends BlockContainer {

	public BlockShoggothBiomass(){
		super(Material.ground);
		setHardness(1.0F);
		setResistance(18.0F);
		setStepSound(Block.soundTypeSand);
		setBlockName("shoggothBiomass");
		setBlockTextureName("abyssalcraft:shoggothBiomass");
		setCreativeTab(AbyssalCraft.tabBlock);
		setLightLevel(0.5F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		return new TileEntityShoggothBiomass();
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float f = 0.15F;
		return AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1 - f, z + 1);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if(!(entity instanceof EntityLesserShoggoth)){
			entity.motionX *= 0.4D;
			entity.motionZ *= 0.4D;
		}
	}
}
