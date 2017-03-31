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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.structures.StructureHouse;

public class BlockHouse extends Block {

	Random rand;
	int temp = 0;

	public BlockHouse() {
		super(Material.WOOD);
		setSoundType(SoundType.WOOD);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.8F, 0.8F);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, ItemStack heldItem, EnumFacing side, float par7, float par8, float par9)
	{
		if(EntityUtil.isPlayerCoralium(par5EntityPlayer)){
			if(!par1World.isRemote){
				StructureHouse house = new StructureHouse();
				house.generate(par1World, rand, pos);
				par1World.getChunkFromBlockCoords(pos).setChunkModified();
				return true;
			}
		} else if(!par1World.isRemote) {
			if(par1World.rand.nextFloat() > 0.1F){
				par5EntityPlayer.addChatMessage(new TextComponentString("Not in a dev environment, aborting structure generation test."));
				par1World.setBlockToAir(pos);
				return true;
			}

			boolean stuff = par1World.rand.nextBoolean();

			par5EntityPlayer.addChatMessage(new TextComponentString(stuff ? "Inverted house... still no windows or a door..." : "A house! Without windows! Nor a door!"));

			IBlockState planks = stuff ? Blocks.COBBLESTONE.getDefaultState() : Blocks.PLANKS.getDefaultState();
			IBlockState cobble = stuff ? Blocks.PLANKS.getDefaultState() : Blocks.COBBLESTONE.getDefaultState();
			IBlockState stairs = stuff ? Blocks.OAK_STAIRS.getStateFromMeta(3) : Blocks.STONE_STAIRS.getStateFromMeta(3);
			IBlockState log = stuff ? Blocks.STONE.getDefaultState() : Blocks.LOG.getDefaultState();

			int x = pos.getX();
			int y = pos.getY();
			int z = pos.getZ();
			par1World.setBlockToAir(pos);
			par1World.setBlockState(new BlockPos(x, y, z - 1), stairs, 2);
			for(int i = 0; i <= 4; i++)
				for(int j = 0; j <= 4; j++)
					par1World.setBlockState(new BlockPos(x -2 + i, y, z - (2 + j)), cobble, 2);
			for(int i = 0; i <= 2; i++){
				par1World.setBlockState(new BlockPos(x - 1, y + 1 + i, z - 2), planks, 2);
				par1World.setBlockState(new BlockPos(x + 1, y + 1 + i, z - 2), planks, 2);
				for(int j = 0; j <= 2; j++)
					par1World.setBlockState(new BlockPos(x - 1 + j, y + 1 + i, z - 6), planks, 2);
			} for(int i = 0; i <= 4; i++)
				for(int j = 0; j <= 2; j++){
					par1World.setBlockState(new BlockPos(x - 2, y + 1 + j, z - 2 - i), planks, 2);
					par1World.setBlockState(new BlockPos(x + 2, y + 1 + j, z - 2 - i), planks, 2);
				}
			par1World.setBlockState(new BlockPos(x, y + 3, z - 2), planks, 2);
			for(int i = 0; i <= 4; i++){
				par1World.setBlockState(new BlockPos(x - 2, y + 4, z - 2 - i), log, 2);
				par1World.setBlockState(new BlockPos(x + 2, y + 4, z - 2 - i), log, 2);
			} for(int i = 0; i <= 2; i++)
				for(int j = 0; j <= 1; j++)
					par1World.setBlockState(new BlockPos(x - 1 + i, y + 4, z - (2 + j*4)), log, 2);
			for(int i = 0; i <= 2; i++)
				for(int j = 0; j <= 2; j++)
					par1World.setBlockState(new BlockPos(x -1 + i, y + 4, z - (3 + j)), planks, 2);
			for(int i = 0; i <= 2; i++)
				for(int j = 0; j <= 1; j++){
					par1World.setBlockState(new BlockPos(x - 2, y + 1 + i, z - (2 + j*4)), cobble, 2);
					par1World.setBlockState(new BlockPos(x + 2, y + 1 + i, z - (2 + j*4)), cobble, 2);
				}
			return true;
		}
		return true;
	}
}
