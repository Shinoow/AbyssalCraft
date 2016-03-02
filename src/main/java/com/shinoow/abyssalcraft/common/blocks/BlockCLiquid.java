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
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class BlockCLiquid extends BlockFluidClassic {

	public static final MaterialLiquid Cwater = new MaterialLiquid(MapColor.lightBlueColor);

	List<Block> dusts = Lists.newArrayList();
	List<Block> metalloids = Lists.newArrayList();
	List<Block> gems = Lists.newArrayList();
	List<Block> stones = Lists.newArrayList();
	List<Block> bricks = Lists.newArrayList();
	List<Block> metals = Lists.newArrayList();

	public BlockCLiquid() {
		super(AbyssalCraft.CFluid, Material.water);
	}

	@Override
	public MapColor getMapColor(IBlockState state){
		return MapColor.lightBlueColor;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, BlockPos pos) {
		if(world.getBlockState(pos).getBlock().getMaterial().isLiquid() && world.getBlockState(pos).getBlock() != this && world.getBlockState(pos).getBlock() != AbyssalCraft.anticwater)
			return true;
		if(world.getBlockState(pos).getBlock() == Blocks.lava)
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
				if(world.getBlockState(pos).getBlock() == Blocks.water && AbyssalCraft.shouldSpread == false) return false;
				if(world.getBlockState(pos).getBlock().getMaterial().isLiquid() && world.getBlockState(pos).getBlock() != this && world.getBlockState(pos).getBlock() != AbyssalCraft.anticwater)
					world.setBlockState(pos, getDefaultState());
				if(AbyssalCraft.breakLogic == true && world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock().getMaterial().isLiquid()
						&& world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock() != this && world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock() != AbyssalCraft.anticwater)
					world.setBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ()), getDefaultState());
			} else {
				if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(pos), Type.OCEAN) && world.getBlockState(pos).getBlock() == this)
					if(AbyssalCraft.destroyOcean)
						world.setBlockState(pos, getDefaultState());
					else world.setBlockState(pos, Blocks.cobblestone.getDefaultState());

				if(AbyssalCraft.shouldSpread){
					if(world.getBlockState(pos).getBlock().getMaterial().isLiquid() && world.getBlockState(pos).getBlock() != this && world.getBlockState(pos).getBlock() != AbyssalCraft.anticwater)
						world.setBlockState(pos, getDefaultState());
					if(AbyssalCraft.breakLogic == true && world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock().getMaterial().isLiquid()
							&& world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock() != this && world.getBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ())).getBlock() != AbyssalCraft.anticwater)
						world.setBlockState(new BlockPos(pos.getX(), pos.getY()+1, pos.getZ()), getDefaultState());
				}
			}
			if(dusts.contains(world.getBlockState(pos).getBlock()) && world.getBlockState(pos) != AbyssalCraft.AbyNitOre.getDefaultState() &&
					world.getBlockState(pos) != AbyssalCraft.AbyCorOre)
				if(oresToBlocks(OreDictionary.getOres("oreSaltpeter")).contains(world.getBlockState(pos).getBlock()))
					world.setBlockState(pos, AbyssalCraft.AbyNitOre.getDefaultState());
				else world.setBlockState(pos, AbyssalCraft.AbyCorOre.getDefaultState());
			else if(metalloids.contains(world.getBlockState(pos).getBlock()) && !metals.contains(world.getBlockState(pos).getBlock()))
				if(oresToBlocks(OreDictionary.getOres("oreIron")).contains(world.getBlockState(pos).getBlock()))
					world.setBlockState(pos, AbyssalCraft.AbyIroOre.getDefaultState());
				else if(oresToBlocks(OreDictionary.getOres("oreGold")).contains(world.getBlockState(pos).getBlock()))
					world.setBlockState(pos, AbyssalCraft.AbyGolOre.getDefaultState());
				else if(oresToBlocks(OreDictionary.getOres("oreTin")).contains(world.getBlockState(pos).getBlock()))
					world.setBlockState(pos, AbyssalCraft.AbyTinOre.getDefaultState());
				else if(oresToBlocks(OreDictionary.getOres("oreCopper")).contains(world.getBlockState(pos).getBlock()))
					world.setBlockState(pos, AbyssalCraft.AbyCopOre.getDefaultState());
				else world.setBlockState(pos, AbyssalCraft.AbyLCorOre.getDefaultState());
			else if(gems.contains(world.getBlockState(pos).getBlock()) && world.getBlockState(pos) != AbyssalCraft.AbyDiaOre.getDefaultState())
				if(oresToBlocks(OreDictionary.getOres("oreDiamond")).contains(world.getBlockState(pos).getBlock()))
					world.setBlockState(pos, AbyssalCraft.AbyDiaOre.getDefaultState());
				else world.setBlockState(pos, AbyssalCraft.AbyPCorOre.getDefaultState());
			else if(stones.contains(world.getBlockState(pos).getBlock()))
				if(BiomeDictionary.isBiomeOfType(world.getBiomeGenForCoords(pos), Type.OCEAN)){
					if(world.getBlockState(pos).getBlock() != Blocks.cobblestone)
						world.setBlockState(pos, AbyssalCraft.abystone.getDefaultState());
				}else world.setBlockState(pos, AbyssalCraft.abystone.getDefaultState());
			else if(bricks.contains(world.getBlockState(pos).getBlock()))
				world.setBlockState(pos, AbyssalCraft.abybrick.getDefaultState());
		}
		return super.displaceIfPossible(world, pos);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, BlockPos pos, IBlockState state, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, pos, state, par5Entity);

		if(par5Entity instanceof EntityLivingBase && !EntityUtil.isEntityCoralium((EntityLivingBase)par5Entity) && ((EntityLivingBase)par5Entity).getActivePotionEffect(AbyssalCraft.Cplague) == null)
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 200));
	}

	private List<Block> oresToBlocks(List<ItemStack> list){
		List<Block> blocks = Lists.newArrayList();
		for(ItemStack stack : list)
			if(Block.getBlockFromItem(stack.getItem()) != Blocks.air)
				blocks.add(Block.getBlockFromItem(stack.getItem()));

		return blocks;
	}

	public void addBlocks(){
		dusts.addAll(oresToBlocks(OreDictionary.getOres("oreSaltpeter")));
		if(!OreDictionary.getOres("oreSulfur").isEmpty())
			dusts.addAll(oresToBlocks(OreDictionary.getOres("oreSulfur")));
		dusts.addAll(oresToBlocks(OreDictionary.getOres("oreLapis")));
		dusts.addAll(oresToBlocks(OreDictionary.getOres("oreRedstone")));
		dusts.addAll(oresToBlocks(OreDictionary.getOres("oreCoal")));
		dusts.add(AbyssalCraft.Coraliumore);
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
		stones.addAll(oresToBlocks(OreDictionary.getOres("sandstone")));
		stones.addAll(oresToBlocks(OreDictionary.getOres("cobblestone")));
		stones.add(Blocks.mossy_cobblestone);
		stones.add(Blocks.netherrack);
		stones.add(Blocks.end_stone);
		stones.add(AbyssalCraft.Darkstone);
		stones.add(AbyssalCraft.abydreadstone);
		stones.add(AbyssalCraft.dreadstone);
		stones.add(AbyssalCraft.Darkstone_cobble);
		bricks.add(Blocks.stonebrick);
		bricks.add(Blocks.nether_brick);
		bricks.add(AbyssalCraft.Darkstone_brick);
		bricks.add(AbyssalCraft.abydreadbrick);
		bricks.add(AbyssalCraft.dreadbrick);
		metals.add(AbyssalCraft.AbyIroOre);
		metals.add(AbyssalCraft.AbyGolOre);
		metals.add(AbyssalCraft.AbyCopOre);
		metals.add(AbyssalCraft.AbyTinOre);
		metals.add(AbyssalCraft.AbyLCorOre);
	}
}
