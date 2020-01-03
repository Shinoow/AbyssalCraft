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

import java.util.Random;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.structures.dreadlands.chagarothlair;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDreadAltarTop extends Block {

	Random rand;

	public BlockDreadAltarTop() {
		super(Material.ROCK);
		setHarvestLevel("pickaxe", 6);
		setSoundType(SoundType.STONE);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return new AxisAlignedBB(0.2F, 0.0F, 0.2F, 0.8F, 0.7F, 0.8F);
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
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		if(placer != null && world.isRemote)
			if(world.provider.getDimension() == ACLib.dreadlands_id){
				if(world.getBiome(pos) == ACBiomes.dreadlands_mountains){
					if(world.getBlockState(pos.down()).getBlock() == ACBlocks.chagaroth_altar_bottom)
						if(pos.getY() == 41)
							placer.sendMessage(new TextComponentTranslation("message.dreadaltartop.enter"));
						else if(pos.getY() < 41)
							placer.sendMessage(new TextComponentString("You need to place the altar "+ (41 - pos.getY()) +" blocks higher."));
						else if(pos.getY() > 41)
							placer.sendMessage(new TextComponentString("You need to place the altar "+ (pos.getY() - 41) +" blocks lower."));
				} else
					placer.sendMessage(new TextComponentTranslation("message.dreadaltar.error.2"));
			} else
				placer.sendMessage(new TextComponentTranslation("message.dreadaltar.error.1"));
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, EnumFacing side, float par7, float par8, float par9) {
		if(par1World.provider.getDimension() == ACLib.dreadlands_id){
			if(par1World.getBiome(pos) == ACBiomes.dreadlands_mountains){
				if(par1World.getBlockState(pos.down()).getBlock() == ACBlocks.chagaroth_altar_bottom)
					if(pos.getY() == 41) {
						if(!par1World.isRemote){
							SpecialTextUtil.ChagarothGroup(par1World, I18n.translateToLocal("message.dreadaltartop.spawn"));
							//						par5EntityPlayer.addStat(ACAchievements.summon_chagaroth, 1);
							chagarothlair lair = new chagarothlair();
							lair.generate(par1World, par1World.rand, pos);
							par1World.getChunkFromBlockCoords(pos).markDirty();
						}
					}
					else if(pos.getY() < 41 && par1World.isRemote)
						par5EntityPlayer.sendMessage(new TextComponentString("You still need to place the altar "+ (41 - pos.getY()) +" blocks higher."));
					else if(pos.getY() > 41 && par1World.isRemote)
						par5EntityPlayer.sendMessage(new TextComponentString("You still need to place the altar "+ (pos.getY() - 41) +" blocks lower."));
			} else if(par1World.isRemote)
				par5EntityPlayer.sendMessage(new TextComponentTranslation("message.dreadaltar.error.2"));
		} else if(par1World.isRemote)
			par5EntityPlayer.sendMessage(new TextComponentTranslation("message.dreadaltar.error.3"));
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, BlockPos pos, IBlockState state, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, pos, state, par5Entity);

		if(par5Entity instanceof EntityLivingBase && !EntityUtil.isEntityDread((EntityLivingBase)par5Entity))
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
	}
}
