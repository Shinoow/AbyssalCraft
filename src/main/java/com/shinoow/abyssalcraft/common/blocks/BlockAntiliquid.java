/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2019 Shinoow.
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
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockAntiliquid extends BlockFluidClassic {

	public static final MaterialLiquid antimatter = new MaterialLiquid(MapColor.SILVER);


	public BlockAntiliquid() {
		super(AbyssalCraftAPI.liquid_antimatter_fluid, Material.WATER);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_){
		return MapColor.SILVER;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if(world.getBlockState(pos).getBlock() == ACBlocks.liquid_coralium || world.getBlockState(pos).getMaterial() == Material.WATER &&
				world.getBlockState(pos).getBlock() != ACBlocks.liquid_coralium && world.getBlockState(pos).getBlock() != this || world.getBlockState(pos).getMaterial() == Material.LAVA)
			return true;
		return super.canDisplace(world, pos);
	}

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {

		if(!world.isRemote && world.getBlockState(pos).getBlock() == ACBlocks.liquid_coralium)
			world.setBlockState(pos, ACBlocks.stone.getStateFromMeta(4));

		if(!world.isRemote && world.getBlockState(pos).getMaterial() == Material.WATER && world.getBlockState(pos).getBlock() != ACBlocks.liquid_coralium && world.getBlockState(pos).getBlock() != this)
			world.setBlockState(pos, Blocks.PACKED_ICE.getDefaultState());

		if(!world.isRemote && world.getBlockState(pos).getMaterial() == Material.LAVA)
			world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());

		return super.displaceIfPossible(world, pos);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, BlockPos pos, IBlockState state, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, pos, state, par5Entity);

		if(par5Entity instanceof EntityLivingBase && !EntityUtil.isEntityAnti((EntityLivingBase)par5Entity)){
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 400));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 400));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 400));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(MobEffects.HUNGER, 400));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400));
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.antimatter_potion, 200));
		}
		if(par5Entity instanceof EntityItem && ACConfig.antiItemDisintegration)
			par5Entity.setDead();
	}
}
