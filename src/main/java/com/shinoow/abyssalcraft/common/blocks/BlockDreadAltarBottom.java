/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class BlockDreadAltarBottom extends Block {

	public BlockDreadAltarBottom() {
		super(Material.ROCK);
		setHarvestLevel("pickaxe", 6);
		setSoundType(SoundType.STONE);
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(placer != null && world.isRemote) {
			if(world.provider.getDimension() != ACLib.dreadlands_id)
				placer.sendMessage(new TextComponentTranslation("message.dreadaltar.error.1"));
			if(world.provider.getDimension() == ACLib.dreadlands_id && world.getBiome(pos) != ACBiomes.dreadlands_mountains)
				placer.sendMessage(new TextComponentTranslation("message.dreadaltar.error.2"));
		}
	}

	@Override
	public void onEntityCollision(World par1World, BlockPos pos, IBlockState state, Entity par5Entity) {
		super.onEntityCollision(par1World, pos, state, par5Entity);

		if(par5Entity instanceof EntityLivingBase && !EntityUtil.isEntityDread((EntityLivingBase)par5Entity))
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
	}
}
