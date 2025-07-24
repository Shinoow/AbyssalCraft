/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.blocks.baseblocks.BlockACBasic;
import com.shinoow.abyssalcraft.common.structures.dreadlands.chagarothlair;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACTabs;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.util.TranslationUtil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSealingLock extends BlockACBasic {

	public BlockSealingLock() {
		super(Material.ROCK, 2.5F, 20.0F, SoundType.STONE);
		setCreativeTab(ACTabs.tabDecoration);
		setTranslationKey("sealing_lock");
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1World, BlockPos pos)
	{
		return new AxisAlignedBB(0.20F, 0.0F, 0.20F, 0.8F, 0.95F, 0.8F);
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
	public Item getItemDropped(IBlockState state, Random random, int j)
	{
		return Item.getItemFromBlock(ACBlocks.chiseled_elysian_stone_brick);
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(world.isRemote) return true;
		ItemStack stack = player.getHeldItem(hand);
		if(stack.getItem() == ACItems.sealing_key)
			if(world.provider.getDimension() == ACLib.dreadlands_id){
				world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.PLAYERS, 0.5F, 1);
				SpecialTextUtil.ChagarothGroup(world, TranslationUtil.toLocal("message.dreadaltartop.spawn"));
				chagarothlair lair = new chagarothlair();
				world.destroyBlock(pos, false);
				lair.generate(world, world.rand, pos);
				world.getChunk(pos).markDirty();
				if(!player.capabilities.isCreativeMode)
					stack.shrink(1);
			} else player.sendMessage(new TextComponentTranslation("message.dreadaltar.error.3"));
		return false;
	}
}
