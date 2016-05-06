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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;

public class BlockShoggothOoze extends BlockACBasic {

	private static List<Block> blockBlacklist = new ArrayList<Block>(){{
		add(AbyssalCraft.shoggothBlock);
		add(Blocks.bedrock);
		add(Blocks.crafting_table);
		add(AbyssalCraft.ethaxium);
		add(AbyssalCraft.ethaxiumbrick);
		add(AbyssalCraft.ethaxiumpillar);
		add(AbyssalCraft.darkethaxiumbrick);
		add(AbyssalCraft.darkethaxiumpillar);
		add(AbyssalCraft.monolithStone);
		add(AbyssalCraft.shoggothBiomass);
	}};

	public BlockShoggothOoze(){
		super(Material.ground, 1.0F, 1.0F, Block.soundTypeSand);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World world, BlockPos pos, IBlockState state)
	{
		float f = 0.25F;
		return AxisAlignedBB.fromBounds(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1 - f, pos.getZ() + 1);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 0;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, BlockPos pos, Entity entity)
	{
		if(!(entity instanceof EntityLesserShoggoth)){
			entity.motionX *= 0.4D;
			entity.motionZ *= 0.4D;
		}
	}

	@Override
	public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side)
	{
		return super.canPlaceBlockAt(worldIn, pos);
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos)
	{
		Block block = worldIn.getBlockState(pos).getBlock();
		if(!block.getMaterial().isLiquid() && block.getMaterial() != Material.air && !block.hasTileEntity(worldIn.getBlockState(pos))
				&& block.isOpaqueCube() && block.isFullCube() && block.getCollisionBoundingBox(worldIn, pos, block.getDefaultState()) != null)
			if(block.getMaterial() == Material.leaves && AbyssalCraft.oozeLeaves || block.getMaterial() == Material.grass && AbyssalCraft.oozeGrass
			|| block.getMaterial() == Material.ground && AbyssalCraft.oozeGround || block.getMaterial() == Material.sand && AbyssalCraft.oozeSand
			|| block.getMaterial() == Material.rock && AbyssalCraft.oozeRock || block.getMaterial() == Material.cloth && AbyssalCraft.oozeCloth
			|| block.getMaterial() == Material.wood && AbyssalCraft.oozeWood || block.getMaterial() == Material.gourd && AbyssalCraft.oozeGourd
			|| block.getMaterial() == Material.iron && AbyssalCraft.oozeIron || block.getMaterial() == Material.clay && AbyssalCraft.oozeClay)
				return !blockBlacklist.contains(block) && !AbyssalCraftAPI.getShoggothBlockBlacklist().contains(block);
		return false;
	}
}