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
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;

public class BlockShoggothOoze extends BlockACBasic {

	private static List<Block> blockBlacklist = new ArrayList<Block>();

	public BlockShoggothOoze(){
		super(Material.ground, 1.0F, 1.0F, Block.soundTypeSand);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		float f = 0.25F;
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

	@Override
	public boolean canPlaceBlockOnSide(World p_149707_1_, int p_149707_2_, int p_149707_3_, int p_149707_4_, int p_149707_5_)
	{
		return super.canPlaceBlockAt(p_149707_1_, p_149707_2_, p_149707_3_, p_149707_4_);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z){
		Block block = world.getBlock(x, y, z);
		if(AbyssalCraft.shoggothOoze)
			if(!block.getMaterial().isLiquid() && block.getMaterial() != Material.air && !block.hasTileEntity(world.getBlockMetadata(x, y, z))
			&& block.isOpaqueCube() && block.renderAsNormalBlock() && block.getCollisionBoundingBoxFromPool(world, x, y, z) != null)
				if(block.getMaterial() == Material.leaves && AbyssalCraft.oozeLeaves || block.getMaterial() == Material.grass && AbyssalCraft.oozeGrass
				|| block.getMaterial() == Material.ground && AbyssalCraft.oozeGround || block.getMaterial() == Material.sand && AbyssalCraft.oozeSand
				|| block.getMaterial() == Material.rock && AbyssalCraft.oozeRock || block.getMaterial() == Material.cloth && AbyssalCraft.oozeCloth
				|| block.getMaterial() == Material.wood && AbyssalCraft.oozeWood || block.getMaterial() == Material.gourd && AbyssalCraft.oozeGourd
				|| block.getMaterial() == Material.iron && AbyssalCraft.oozeIron || block.getMaterial() == Material.clay && AbyssalCraft.oozeClay)
					return !blockBlacklist.contains(block);
		return false;
	}

	public void initBlacklist(){
		blockBlacklist.add(AbyssalCraft.shoggothBlock);
		blockBlacklist.add(Blocks.bedrock);
		blockBlacklist.add(Blocks.crafting_table);
		blockBlacklist.add(AbyssalCraft.ethaxium);
		blockBlacklist.add(AbyssalCraft.ethaxiumbrick);
		blockBlacklist.add(AbyssalCraft.ethaxiumpillar);
		blockBlacklist.add(AbyssalCraft.darkethaxiumbrick);
		blockBlacklist.add(AbyssalCraft.darkethaxiumpillar);
		blockBlacklist.add(AbyssalCraft.monolithStone);
		blockBlacklist.add(AbyssalCraft.shoggothBiomass);
		blockBlacklist.addAll(AbyssalCraftAPI.getShoggothBlockBlacklist());
	}
}