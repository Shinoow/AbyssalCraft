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

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;

public class BlockCLiquid extends BlockFluidClassic {

	public static final MaterialLiquid Cwater = new MaterialLiquid(MapColor.LIGHT_BLUE);

	List<IBlockState> dusts = Lists.newArrayList();
	List<IBlockState> metalloids = Lists.newArrayList();
	List<IBlockState> gems = Lists.newArrayList();
	List<IBlockState> stones = Lists.newArrayList();
	List<IBlockState> bricks = Lists.newArrayList();
	List<IBlockState> metals = Lists.newArrayList();

	public BlockCLiquid() {
		super(AbyssalCraft.CFluid, Material.WATER);
	}

	@Override
	public MapColor getMapColor(IBlockState state){
		return MapColor.LIGHT_BLUE;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if(world.getBlockState(pos).getMaterial().isLiquid() && world.getBlockState(pos).getBlock() != this && world.getBlockState(pos).getBlock() != ACBlocks.liquid_antimatter)
			return true;
		if(world.getBlockState(pos).getBlock() == Blocks.LAVA)
			return true;
		else if(dusts.contains(world.getBlockState(pos).getBlock()) || metalloids.contains(world.getBlockState(pos).getBlock()) || gems.contains(world.getBlockState(pos).getBlock()) ||
				stones.contains(world.getBlockState(pos).getBlock()) || bricks.contains(world.getBlockState(pos).getBlock()))
			return true;
		return super.canDisplace(world, pos);
	}

	@Override
	public boolean displaceIfPossible(World world, BlockPos pos) {

		if(!world.isRemote){
			if(!world.provider.isSurfaceWorld()){
				if(world.getBlockState(pos).getBlock() == Blocks.WATER && AbyssalCraft.shouldSpread == false) return false;
				if(world.getBlockState(pos).getMaterial().isLiquid() && world.getBlockState(pos).getBlock() != this && world.getBlockState(pos).getBlock() != ACBlocks.liquid_antimatter)
					world.setBlockState(pos, getDefaultState());
				if(AbyssalCraft.breakLogic == true && world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getMaterial().isLiquid()
						&& world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock() != this && world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock() != ACBlocks.liquid_antimatter)
					world.setBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ()), getDefaultState());
			} else {
				if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(pos), Type.OCEAN) && world.getBlockState(pos).getBlock() == this)
					if(AbyssalCraft.destroyOcean)
						world.setBlockState(pos, getDefaultState());
					else world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());

				if(AbyssalCraft.shouldSpread){
					if(world.getBlockState(pos).getMaterial().isLiquid() && world.getBlockState(pos).getBlock() != this && world.getBlockState(pos).getBlock() != ACBlocks.liquid_antimatter)
						world.setBlockState(pos, getDefaultState());
					if(AbyssalCraft.breakLogic == true && world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getMaterial().isLiquid()
							&& world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock() != this && world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock() != ACBlocks.liquid_antimatter)
						world.setBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ()), getDefaultState());
				}
			}
			if(dusts.contains(world.getBlockState(pos)) && world.getBlockState(pos) != ACBlocks.abyssal_nitre_ore.getDefaultState() &&
					world.getBlockState(pos) != ACBlocks.abyssal_coralium_ore)
				if(oresToBlocks(OreDictionary.getOres("oreSaltpeter")).contains(world.getBlockState(pos)))
					world.setBlockState(pos, ACBlocks.abyssal_nitre_ore.getDefaultState());
				else world.setBlockState(pos, ACBlocks.abyssal_coralium_ore.getDefaultState());
			else if(metalloids.contains(world.getBlockState(pos)) && !metals.contains(world.getBlockState(pos)))
				if(oresToBlocks(OreDictionary.getOres("oreIron")).contains(world.getBlockState(pos)))
					world.setBlockState(pos, ACBlocks.abyssal_iron_ore.getDefaultState());
				else if(oresToBlocks(OreDictionary.getOres("oreGold")).contains(world.getBlockState(pos)))
					world.setBlockState(pos, ACBlocks.abyssal_gold_ore.getDefaultState());
				else if(oresToBlocks(OreDictionary.getOres("oreTin")).contains(world.getBlockState(pos)))
					world.setBlockState(pos, ACBlocks.abyssal_tin_ore.getDefaultState());
				else if(oresToBlocks(OreDictionary.getOres("oreCopper")).contains(world.getBlockState(pos)))
					world.setBlockState(pos, ACBlocks.abyssal_copper_ore.getDefaultState());
				else world.setBlockState(pos, ACBlocks.liquified_coralium_ore.getDefaultState());
			else if(gems.contains(world.getBlockState(pos)) && world.getBlockState(pos) != ACBlocks.abyssal_diamond_ore.getDefaultState())
				if(oresToBlocks(OreDictionary.getOres("oreDiamond")).contains(world.getBlockState(pos)))
					world.setBlockState(pos, ACBlocks.abyssal_diamond_ore.getDefaultState());
				else world.setBlockState(pos, ACBlocks.pearlescent_coralium_ore.getDefaultState());
			else if(stones.contains(world.getBlockState(pos)))
				if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(pos), Type.OCEAN)){
					if(world.getBlockState(pos).getBlock() != Blocks.COBBLESTONE)
						world.setBlockState(pos, ACBlocks.abyssal_stone.getDefaultState());
				}else world.setBlockState(pos, ACBlocks.abyssal_stone.getDefaultState());
			else if(bricks.contains(world.getBlockState(pos)))
				world.setBlockState(pos, ACBlocks.abyssal_stone_brick.getDefaultState());
		}
		return super.displaceIfPossible(world, pos);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, BlockPos pos, IBlockState state, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, pos, state, par5Entity);

		if(par5Entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)par5Entity) && ((EntityLivingBase)par5Entity).getActivePotionEffect(AbyssalCraftAPI.coralium_plague) == null)
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 200));
	}

	private List<IBlockState> oresToBlocks(List<ItemStack> list){
		List<IBlockState> blocks = Lists.newArrayList();
		for(ItemStack stack : list)
			if(Block.getBlockFromItem(stack.getItem()) != null && Block.getBlockFromItem(stack.getItem()) != Blocks.AIR)
				blocks.add(Block.getBlockFromItem(stack.getItem()).getStateFromMeta(stack.getItemDamage()));

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
		stones.addAll(oresToBlocks(OreDictionary.getOres("cobblestone")));
		stones.add(Blocks.MOSSY_COBBLESTONE.getDefaultState());
		stones.add(Blocks.NETHERRACK.getDefaultState());
		stones.add(Blocks.END_STONE.getDefaultState());
		stones.add(ACBlocks.darkstone.getDefaultState());
		stones.add(ACBlocks.abyssalnite_stone.getDefaultState());
		stones.add(ACBlocks.dreadstone.getDefaultState());
		stones.add(ACBlocks.darkstone_cobblestone.getDefaultState());
		bricks.add(Blocks.STONEBRICK.getStateFromMeta(0));
		bricks.add(Blocks.STONEBRICK.getStateFromMeta(1));
		bricks.add(Blocks.STONEBRICK.getStateFromMeta(2));
		bricks.add(Blocks.STONEBRICK.getStateFromMeta(3));
		bricks.add(Blocks.NETHER_BRICK.getDefaultState());
		bricks.add(ACBlocks.darkstone_brick.getDefaultState());
		bricks.add(ACBlocks.abyssalnite_stone_brick.getDefaultState());
		bricks.add(ACBlocks.dreadstone_brick.getDefaultState());
		metals.add(ACBlocks.abyssal_iron_ore.getDefaultState());
		metals.add(ACBlocks.abyssal_gold_ore.getDefaultState());
		metals.add(ACBlocks.abyssal_copper_ore.getDefaultState());
		metals.add(ACBlocks.abyssal_tin_ore.getDefaultState());
		metals.add(ACBlocks.liquified_coralium_ore.getDefaultState());
	}
}
