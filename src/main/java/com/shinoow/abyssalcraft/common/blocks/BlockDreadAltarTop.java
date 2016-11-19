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
import net.minecraftforge.fml.client.FMLClientHandler;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.structures.dreadlands.chagarothlair;
import com.shinoow.abyssalcraft.lib.ACAchievements;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;

public class BlockDreadAltarTop extends Block {

	Random rand;

	public BlockDreadAltarTop() {
		super(Material.rock);
		setHarvestLevel("pickaxe", 6);
		setStepSound(SoundType.STONE);
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
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
		super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		if(world.isRemote)
			if(world.provider.getDimension() == ACLib.dreadlands_id){
				if(world.getBiomeGenForCoords(pos) == ACBiomes.dreadlands_mountains){
					if(world.getBlockState(pos.down()).getBlock() == ACBlocks.chagaroth_altar_bottom)
						if(pos.getY() == 41)
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("message.dreadaltartop.enter"));
						else if(pos.getY() < 41)
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("You need to place the altar "+ (41 - pos.getY()) +" blocks higher."));
						else if(pos.getY() > 41)
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentString("You need to place the altar "+ (pos.getY() - 41) +" blocks lower."));
				} else
					FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("message.dreadaltar.error.2"));
			} else
				FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("message.dreadaltar.error.1"));
		return getStateFromMeta(meta);
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, ItemStack heldItem, EnumFacing side, float par7, float par8, float par9) {
		if(par1World.provider.getDimension() == ACLib.dreadlands_id){
			if(par1World.getBiomeGenForCoords(pos) == ACBiomes.dreadlands_mountains){
				if(par1World.getBlockState(pos.down()).getBlock() == ACBlocks.chagaroth_altar_bottom && pos.getY() == 41)
					if(!par1World.isRemote){
						SpecialTextUtil.ChagarothGroup(par1World, I18n.translateToLocal("message.dreadaltartop.spawn"));
						par5EntityPlayer.addStat(ACAchievements.summon_chagaroth, 1);
						chagarothlair lair = new chagarothlair();
						lair.generate(par1World, rand, pos.down(2));
						par1World.getChunkFromBlockCoords(pos).setChunkModified();
						par1World.setBlockToAir(pos.down());
						par1World.setBlockToAir(pos);
					}
			} else if(par1World.isRemote)
				FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("message.dreadaltar.error.2"));
		} else if(par1World.isRemote)
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new TextComponentTranslation("message.dreadaltar.error.3"));
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, BlockPos pos, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, pos, par5Entity);

		if(par5Entity instanceof IDreadEntity){}
		else if(par5Entity instanceof EntityLivingBase)
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
	}
}