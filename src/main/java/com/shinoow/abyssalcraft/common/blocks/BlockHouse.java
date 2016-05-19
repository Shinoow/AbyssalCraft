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
import net.minecraftforge.fml.client.FMLClientHandler;

import com.shinoow.abyssalcraft.common.structures.StructureHouse;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class BlockHouse extends Block {

	Random rand;

	public BlockHouse() {
		super(Material.WOOD);
		//		setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.8F, 0.8F);
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
			if(par1World.isRemote)
				FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Whoppidy-doo, a house."));
			if(!par1World.isRemote){
				StructureHouse house = new StructureHouse();
				house.generate(par1World, rand, pos);
				par1World.getChunkFromBlockCoords(pos).setChunkModified();
			}
		} else{
			if(par1World.isRemote)
				FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("Whoppidy-doo, a house."));
			if(!par1World.isRemote){
				int x = pos.getX();
				int y = pos.getY();
				int z = pos.getZ();
				par1World.setBlockToAir(pos);
				par1World.setBlockState(new BlockPos(x, y, z - 1), Blocks.STONE_STAIRS.getStateFromMeta(3), 2);
				for(int i = 0; i <= 4; i++)
					for(int j = 0; j <= 4; j++)
						par1World.setBlockState(new BlockPos(x -2 + i, y, z - (2 + j)), Blocks.COBBLESTONE.getDefaultState(), 2);
				for(int i = 0; i <= 2; i++){
					par1World.setBlockState(new BlockPos(x - 1, y + 1 + i, z - 2), Blocks.PLANKS.getDefaultState(), 2);
					par1World.setBlockState(new BlockPos(x + 1, y + 1 + i, z - 2), Blocks.PLANKS.getDefaultState(), 2);
					for(int j = 0; j <= 2; j++)
						par1World.setBlockState(new BlockPos(x - 1 + j, y + 1 + i, z - 6), Blocks.PLANKS.getDefaultState(), 2);
				} for(int i = 0; i <= 4; i++)
					for(int j = 0; j <= 2; j++){
						par1World.setBlockState(new BlockPos(x - 2, y + 1 + j, z - 2 - i), Blocks.PLANKS.getDefaultState(), 2);
						par1World.setBlockState(new BlockPos(x + 2, y + 1 + j, z - 2 - i), Blocks.PLANKS.getDefaultState(), 2);
					}
				par1World.setBlockState(new BlockPos(x, y + 3, z - 2), Blocks.PLANKS.getDefaultState(), 2);
				for(int i = 0; i <= 4; i++){
					par1World.setBlockState(new BlockPos(x - 2, y + 4, z - 2 - i), Blocks.LOG.getDefaultState(), 2);
					par1World.setBlockState(new BlockPos(x + 2, y + 4, z - 2 - i), Blocks.LOG.getDefaultState(), 2);
				} for(int i = 0; i <= 2; i++)
					for(int j = 0; j <= 1; j++)
						par1World.setBlockState(new BlockPos(x - 1 + i, y + 4, z - (2 + j*4)), Blocks.LOG.getDefaultState(), 2);
				for(int i = 0; i <= 2; i++)
					for(int j = 0; j <= 2; j++)
						par1World.setBlockState(new BlockPos(x -1 + i, y + 4, z - (3 + j)), Blocks.PLANKS.getDefaultState(), 2);
				for(int i = 0; i <= 2; i++)
					for(int j = 0; j <= 1; j++){
						par1World.setBlockState(new BlockPos(x - 2, y + 1 + i, z - (2 + j*4)), Blocks.COBBLESTONE.getDefaultState(), 2);
						par1World.setBlockState(new BlockPos(x + 2, y + 1 + i, z - (2 + j*4)), Blocks.COBBLESTONE.getDefaultState(), 2);
					}
			}
		}
		return false;
	}
}