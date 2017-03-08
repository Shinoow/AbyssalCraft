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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

public class BlockShoggothOoze extends BlockACBasic {

	private static List<Block> blockBlacklist = new ArrayList<Block>();

	public BlockShoggothOoze(){
		super(Material.ground, 1.0F, 1.0F, Block.soundTypeSand);
		setTickRandomly(ACConfig.oozeExpire);
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
	public void updateTick(World par1World, BlockPos pos, IBlockState state, Random par5Random) {
		if(ACConfig.oozeExpire)
			if (!par1World.isRemote && par5Random.nextInt(10) == 0 && par1World.getLightFromNeighbors(pos.up()) >= 13)
				par1World.setBlockState(pos, getState(par1World));
	}

	private IBlockState getState(World world){
		if(world.provider.getDimensionId() == ACLib.abyssal_wasteland_id)
			return ACBlocks.abyssal_sand.getDefaultState();
		if(world.provider.getDimensionId() == ACLib.dreadlands_id)
			return ACBlocks.dreadlands_dirt.getDefaultState();
		if(world.provider.getDimensionId() == ACLib.omothol_id)
			return ACBlocks.omothol_stone.getDefaultState();
		if(world.provider.getDimensionId() == ACLib.dark_realm_id)
			return ACBlocks.darkstone.getDefaultState();
		if(world.provider.getDimensionId() == -1)
			return Blocks.netherrack.getDefaultState();
		if(world.provider.getDimensionId() == 1)
			return Blocks.end_stone.getDefaultState();
		return Blocks.dirt.getDefaultState();
	}

	@Override
	public int tickRate(World worldIn)
	{
		return ACConfig.oozeExpire ? 200 : super.tickRate(worldIn);
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
				&& block.isFullCube() && block.getCollisionBoundingBox(worldIn, pos, block.getDefaultState()) != null)
			if(block.getMaterial() == Material.leaves && ACConfig.oozeLeaves || block.getMaterial() == Material.grass && ACConfig.oozeGrass
			|| block.getMaterial() == Material.ground && ACConfig.oozeGround || block.getMaterial() == Material.sand && ACConfig.oozeSand
			|| block.getMaterial() == Material.rock && ACConfig.oozeRock || block.getMaterial() == Material.cloth && ACConfig.oozeCloth
			|| block.getMaterial() == Material.wood && ACConfig.oozeWood || block.getMaterial() == Material.gourd && ACConfig.oozeGourd
			|| block.getMaterial() == Material.iron && ACConfig.oozeIron || block.getMaterial() == Material.clay && ACConfig.oozeClay)
				return !blockBlacklist.contains(block) && !AbyssalCraftAPI.getShoggothBlockBlacklist().contains(block);
		return false;
	}

	public void initBlacklist(){
		blockBlacklist.add(ACBlocks.shoggoth_ooze);
		blockBlacklist.add(Blocks.bedrock);
		blockBlacklist.add(Blocks.crafting_table);
		blockBlacklist.add(ACBlocks.ethaxium);
		blockBlacklist.add(ACBlocks.ethaxium_brick);
		blockBlacklist.add(ACBlocks.ethaxium_pillar);
		blockBlacklist.add(ACBlocks.dark_ethaxium_brick);
		blockBlacklist.add(ACBlocks.dark_ethaxium_pillar);
		blockBlacklist.add(ACBlocks.monolith_stone);
		blockBlacklist.add(ACBlocks.shoggoth_biomass);
		blockBlacklist.add(ACBlocks.block_of_ethaxium);
	}
}
