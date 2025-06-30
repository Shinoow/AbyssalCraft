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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.util.ACLogger;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.oredict.OreDictionary;

public class BlockCLiquid extends BlockFluidClassic {

	private List<IBlockState> dusts = new ArrayList<>();
	private List<IBlockState> metalloids = new ArrayList<>();
	private List<IBlockState> gems = new ArrayList<>();
	private List<IBlockState> stones = new ArrayList<>();
	private List<IBlockState> bricks = new ArrayList<>();
	private List<IBlockState> cobble = new ArrayList<>();

	public BlockCLiquid() {
		super(AbyssalCraftAPI.liquid_coralium_fluid, Material.WATER);
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_){
		return MapColor.LIGHT_BLUE;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if(world instanceof World && BiomeDictionary.hasType(((World)world).getBiome(pos), Type.OCEAN) && !ACConfig.destroyOcean && state.getBlock() == Blocks.COBBLESTONE)
			return false;
		if(isBlacklisted(state))
			return false;
		if(state.getMaterial().isLiquid() && state.getBlock() != this && state.getBlock() != ACBlocks.liquid_antimatter)
			return true;
		if(state.getBlock() == Blocks.LAVA || canBeTramsuted(state))
			return true;
		return super.canDisplace(world, pos);
	}

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {

		IBlockState state = world.getBlockState(pos);
		if(!world.isRemote && !isBlacklisted(state))
			if(ACConfig.shouldSpread || world.provider.getDimension() == ACLib.abyssal_wasteland_id){
				if(state.getMaterial().isLiquid() && state.getBlock() != this && world.getBlockState(pos).getBlock() != ACBlocks.liquid_antimatter)
					world.setBlockState(pos, getDefaultState());
				if(ACConfig.breakLogic && world.getBlockState(pos.up()).getMaterial().isLiquid()
						&& world.getBlockState(pos.up()).getBlock() != this && world.getBlockState(pos.up()).getBlock() != ACBlocks.liquid_antimatter)
					world.setBlockState(pos.up(), getDefaultState());
				if(dusts.contains(state))
					if(oresToBlocks(OreDictionary.getOres("oreSaltpeter")).contains(state))
						world.setBlockState(pos, ACBlocks.abyssal_nitre_ore.getDefaultState());
					else world.setBlockState(pos, ACBlocks.abyssal_coralium_ore.getDefaultState());
				else if(metalloids.contains(state))
					if(oresToBlocks(OreDictionary.getOres("oreIron")).contains(state))
						world.setBlockState(pos, ACBlocks.abyssal_iron_ore.getDefaultState());
					else if(oresToBlocks(OreDictionary.getOres("oreGold")).contains(state))
						world.setBlockState(pos, ACBlocks.abyssal_gold_ore.getDefaultState());
					else if(oresToBlocks(OreDictionary.getOres("oreTin")).contains(state))
						world.setBlockState(pos, ACBlocks.abyssal_tin_ore.getDefaultState());
					else if(oresToBlocks(OreDictionary.getOres("oreCopper")).contains(state))
						world.setBlockState(pos, ACBlocks.abyssal_copper_ore.getDefaultState());
					else world.setBlockState(pos, ACBlocks.liquified_coralium_ore.getDefaultState());
				else if(gems.contains(state))
					if(oresToBlocks(OreDictionary.getOres("oreDiamond")).contains(state))
						world.setBlockState(pos, ACBlocks.abyssal_diamond_ore.getDefaultState());
					else world.setBlockState(pos, ACBlocks.pearlescent_coralium_ore.getDefaultState());
				else if(stones.contains(state))
					world.setBlockState(pos, ACBlocks.stone.getStateFromMeta(1));
				else if(bricks.contains(state))
					world.setBlockState(pos, ACBlocks.abyssal_stone_brick.getDefaultState());
				else if(cobble.contains(state))
					if(BiomeDictionary.hasType(world.getBiome(pos), Type.OCEAN) && !ACConfig.destroyOcean){
						if(state.getBlock() != Blocks.COBBLESTONE)
							world.setBlockState(pos, ACBlocks.cobblestone.getStateFromMeta(1));
					} else world.setBlockState(pos, ACBlocks.cobblestone.getStateFromMeta(1));
			}
		return super.displaceIfPossible(world, pos);
	}

	@Override
	public void onBlockAdded(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state)
	{
		if(BiomeDictionary.hasType(world.getBiome(pos), Type.OCEAN) && !ACConfig.destroyOcean)
			world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
		super.onBlockAdded(world, pos, state);
	}

	@Override
	public void onEntityCollision(World par1World, BlockPos pos, IBlockState state, Entity par5Entity) {
		super.onEntityCollision(par1World, pos, state, par5Entity);

		if(par5Entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)par5Entity) && !((EntityLivingBase)par5Entity).isPotionActive(AbyssalCraftAPI.coralium_plague))
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 200));
	}

	private boolean isBlacklisted(IBlockState state) {
		Block block = state.getBlock();
		if(state == ACBlocks.cobblestone.getStateFromMeta(1)
				|| state == ACBlocks.stone.getStateFromMeta(1)
				|| block == ACBlocks.abyssal_coralium_ore
				|| block == ACBlocks.abyssal_diamond_ore
				|| block == ACBlocks.abyssal_gold_ore
				|| block == ACBlocks.abyssal_iron_ore
				|| block == ACBlocks.abyssal_nitre_ore
				|| block == ACBlocks.abyssal_stone_brick
				|| state == ACBlocks.stone.getStateFromMeta(4)
				|| block == ACBlocks.liquified_coralium_ore)
			return true;
		return false;
	}

	private boolean canBeTramsuted(IBlockState state) {
		return dusts.contains(state) || metalloids.contains(state) || gems.contains(state) ||
				stones.contains(state) || bricks.contains(state) || cobble.contains(state);
	}

	private List<IBlockState> oresToBlocks(List<ItemStack> list){
		List<IBlockState> blocks = new ArrayList<>();
		for(ItemStack stack : list){
			Block block = Block.getBlockFromItem(stack.getItem());
			if(block != null && block != Blocks.AIR)
				try {
					blocks.add(block.getStateFromMeta(((ItemBlock)stack.getItem()).getMetadata(stack.getMetadata() == OreDictionary.WILDCARD_VALUE ? 0 : stack.getMetadata())));
				} catch(Exception e){
					ACLogger.severe("Unable to convert {} with meta {} into a BlockState", block.getRegistryName(), stack.getMetadata());
				}
		}

		return blocks;
	}

	public void addBlocks(){
		dusts.addAll(oresToBlocks(OreDictionary.getOres("oreSaltpeter")));
		if(!OreDictionary.getOres("oreSulfur").isEmpty())
			dusts.addAll(oresToBlocks(OreDictionary.getOres("oreSulfur")));
		dusts.addAll(oresToBlocks(OreDictionary.getOres("oreLapis")));
		dusts.addAll(oresToBlocks(OreDictionary.getOres("oreRedstone")));
		dusts.addAll(oresToBlocks(OreDictionary.getOres("oreCoal")));
		dusts.addAll(oresToBlocks(OreDictionary.getOres("oreCoralium")));
		metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreIron")));
		metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreGold")));
		metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreAbyssalnite")));
		metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreDreadedAbyssalnite")));
		metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreCopper")));
		metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreTin")));
		if(!OreDictionary.getOres("oreAluminum").isEmpty())
			metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreAluminum")));
		if(!OreDictionary.getOres("oreAluminium").isEmpty())
			metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreAluminium")));
		if(!OreDictionary.getOres("oreBrass").isEmpty())
			metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreBrass")));
		if(!OreDictionary.getOres("oreSilver").isEmpty())
			metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreSilver")));
		if(!OreDictionary.getOres("oreZinc").isEmpty())
			metalloids.addAll(oresToBlocks(OreDictionary.getOres("oreZinc")));
		gems.addAll(oresToBlocks(OreDictionary.getOres("oreDiamond")));
		gems.addAll(oresToBlocks(OreDictionary.getOres("oreEmerald")));
		gems.addAll(oresToBlocks(OreDictionary.getOres("oreQuartz")));
		if(!OreDictionary.getOres("oreSapphire").isEmpty())
			gems.addAll(oresToBlocks(OreDictionary.getOres("oreSapphire")));
		if(!OreDictionary.getOres("oreRuby").isEmpty())
			gems.addAll(oresToBlocks(OreDictionary.getOres("oreRuby")));
		gems.addAll(oresToBlocks(OreDictionary.getOres("oreCoraliumStone")));
		stones.addAll(oresToBlocks(OreDictionary.getOres("stone")));
		stones.addAll(oresToBlocks(OreDictionary.getOres("stoneGranite")));
		stones.addAll(oresToBlocks(OreDictionary.getOres("stoneGranitePolished")));
		stones.addAll(oresToBlocks(OreDictionary.getOres("stoneDiorite")));
		stones.addAll(oresToBlocks(OreDictionary.getOres("stoneDioritePolished")));
		stones.addAll(oresToBlocks(OreDictionary.getOres("stoneAndesite")));
		stones.addAll(oresToBlocks(OreDictionary.getOres("stoneAndesitePolished")));
		stones.addAll(oresToBlocks(OreDictionary.getOres("sandstone")));
		stones.add(Blocks.NETHERRACK.getDefaultState());
		stones.add(Blocks.END_STONE.getDefaultState());
		stones.add(ACBlocks.stone.getDefaultState());
		stones.add(ACBlocks.stone.getStateFromMeta(2));
		stones.add(ACBlocks.stone.getStateFromMeta(3));
		bricks.add(Blocks.STONEBRICK.getStateFromMeta(0));
		bricks.add(Blocks.STONEBRICK.getStateFromMeta(1));
		bricks.add(Blocks.STONEBRICK.getStateFromMeta(2));
		bricks.add(Blocks.STONEBRICK.getStateFromMeta(3));
		bricks.add(Blocks.NETHER_BRICK.getDefaultState());
		bricks.add(ACBlocks.darkstone_brick.getDefaultState());
		bricks.add(ACBlocks.darkstone_brick.getStateFromMeta(1));
		bricks.add(ACBlocks.darkstone_brick.getStateFromMeta(2));
		bricks.add(ACBlocks.abyssalnite_stone_brick.getDefaultState());
		bricks.add(ACBlocks.abyssalnite_stone_brick.getStateFromMeta(1));
		bricks.add(ACBlocks.abyssalnite_stone_brick.getStateFromMeta(2));
		bricks.add(ACBlocks.dreadstone_brick.getDefaultState());
		bricks.add(ACBlocks.dreadstone_brick.getStateFromMeta(1));
		bricks.add(ACBlocks.dreadstone_brick.getStateFromMeta(2));
		bricks.add(Blocks.END_BRICKS.getDefaultState());
		bricks.add(Blocks.RED_NETHER_BRICK.getDefaultState());
		cobble.addAll(oresToBlocks(OreDictionary.getOres("cobblestone")));
		cobble.add(Blocks.MOSSY_COBBLESTONE.getDefaultState());
		cobble.add(ACBlocks.cobblestone.getStateFromMeta(0));
		cobble.add(ACBlocks.cobblestone.getStateFromMeta(2));
		cobble.add(ACBlocks.cobblestone.getStateFromMeta(3));
		cobble.add(ACBlocks.cobblestone.getStateFromMeta(4));
	}
}
