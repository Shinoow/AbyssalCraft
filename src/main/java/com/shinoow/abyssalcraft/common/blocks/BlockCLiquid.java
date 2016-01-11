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

import net.minecraft.block.Block;
import net.minecraft.block.material.*;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCLiquid extends BlockFluidClassic {

	public static final MaterialLiquid Cwater = new MaterialLiquid(MapColor.lightBlueColor);

	@SideOnly(Side.CLIENT)
	protected IIcon[] theIcon;

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
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		theIcon = new IIcon[]{iconRegister.registerIcon("abyssalcraft:cwater_still"), iconRegister.registerIcon("abyssalcraft:cwater_flow")};

		AbyssalCraft.CFluid.setStillIcon(theIcon[0]);
		AbyssalCraft.CFluid.setFlowingIcon(theIcon[1]);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return side != 0 && side != 1 ? theIcon[1] : theIcon[0];
	}

	@Override
	public MapColor getMapColor(int meta){
		return MapColor.lightBlueColor;
	}

	@Override
	public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
		if(world.getBlock(x, y, z).getMaterial().isLiquid() && world.getBlock(x, y, z) != this && world.getBlock(x, y, z) != AbyssalCraft.anticwater)
			return true;
		if(world.getBlock(x, y, z) == Blocks.lava)
			return true;
		else if(dusts.contains(world.getBlock(x, y, z)) || metalloids.contains(world.getBlock(x, y, z)) || gems.contains(world.getBlock(x, y, z)) ||
				stones.contains(world.getBlock(x, y, z)) || bricks.contains(world.getBlock(x, y, z)))
			return true;
		return super.canDisplace(world, x, y, z);
	}

	@Override
	public boolean displaceIfPossible(World world, int x, int y, int z) {

		if(!world.isRemote){
			if(!world.provider.isSurfaceWorld()){
				if(world.getBlock(x, y, z) == Blocks.water && AbyssalCraft.shouldSpread == false) return false;
				if(world.getBlock(x, y, z).getMaterial().isLiquid() && world.getBlock(x, y, z) != this && world.getBlock(x, y, z) != AbyssalCraft.anticwater)
					world.setBlock(x, y, z, this);
				if(AbyssalCraft.breakLogic == true && world.getBlock(x, y+1, z).getMaterial().isLiquid()  && world.getBlock(x, y+1, z) != this && world.getBlock(x, y+1, z) != AbyssalCraft.anticwater)
					world.setBlock(x, y+1, z, this);
			} else {
				if(world.getBiomeGenForCoords(x, z) instanceof BiomeGenOcean && world.getBlock(x, y, z) == this)
					if(AbyssalCraft.destroyOcean)
						world.setBlock(x, y, z, this);
					else world.setBlock(x, y, z, Blocks.cobblestone);

				if(AbyssalCraft.shouldSpread){
					if(world.getBlock(x, y, z).getMaterial().isLiquid() && world.getBlock(x, y, z) != this && world.getBlock(x, y, z) != AbyssalCraft.anticwater)
						world.setBlock(x, y, z, this);
					if(AbyssalCraft.breakLogic == true && world.getBlock(x, y+1, z).getMaterial().isLiquid()  && world.getBlock(x, y+1, z) != this && world.getBlock(x, y+1, z) != AbyssalCraft.anticwater)
						world.setBlock(x, y+1, z, this);
				}
			}
			if(dusts.contains(world.getBlock(x, y, z)) && world.getBlock(x, y, z) != AbyssalCraft.AbyNitOre &&
					world.getBlock(x, y, z) != AbyssalCraft.AbyCorOre)
				if(oresToBlocks(OreDictionary.getOres("oreSaltpeter")).contains(world.getBlock(x, y, z)))
					world.setBlock(x, y, z, AbyssalCraft.AbyNitOre);
				else world.setBlock(x, y, z, AbyssalCraft.AbyCorOre);
			else if(metalloids.contains(world.getBlock(x, y, z)) && !metals.contains(world.getBlock(x, y, z)))
				if(oresToBlocks(OreDictionary.getOres("oreIron")).contains(world.getBlock(x, y, z)))
					world.setBlock(x, y, z, AbyssalCraft.AbyIroOre);
				else if(oresToBlocks(OreDictionary.getOres("oreGold")).contains(world.getBlock(x, y, z)))
					world.setBlock(x, y, z, AbyssalCraft.AbyGolOre);
				else if(oresToBlocks(OreDictionary.getOres("oreTin")).contains(world.getBlock(x, y, z)))
					world.setBlock(x, y, z, AbyssalCraft.AbyTinOre);
				else if(oresToBlocks(OreDictionary.getOres("oreCopper")).contains(world.getBlock(x, y, z)))
					world.setBlock(x, y, z, AbyssalCraft.AbyCopOre);
				else world.setBlock(x, y, z, AbyssalCraft.AbyLCorOre);
			else if(gems.contains(world.getBlock(x, y, z)) && world.getBlock(x, y, z) != AbyssalCraft.AbyDiaOre)
				if(oresToBlocks(OreDictionary.getOres("oreDiamond")).contains(world.getBlock(x, y, z)))
					world.setBlock(x, y, z, AbyssalCraft.AbyDiaOre);
				else world.setBlock(x, y, z, AbyssalCraft.AbyPCorOre);
			else if(stones.contains(world.getBlock(x, y, z)))
				world.setBlock(x, y, z, AbyssalCraft.abystone);
			else if(bricks.contains(world.getBlock(x, y, z)))
				world.setBlock(x, y, z, AbyssalCraft.abybrick);
		}
		return super.displaceIfPossible(world, x, y, z);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);

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
