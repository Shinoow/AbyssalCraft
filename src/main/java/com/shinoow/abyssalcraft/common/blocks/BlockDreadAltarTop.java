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

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.biome.ACBiomes;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDreadAltarTop;
import com.shinoow.abyssalcraft.common.structures.dreadlands.chagarothlair;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;

public class BlockDreadAltarTop extends BlockContainer {

	Random rand;

	public BlockDreadAltarTop() {
		super(Material.rock);
		setHarvestLevel("pickaxe", 6);
		setBlockBounds(0.2F, 0.0F, 0.2F, 0.8F, 0.7F, 0.8F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileEntityDreadAltarTop();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean isFullCube()
	{
		return false;
	}

	@Override
	public int getRenderType() {
		return 2;
	}

	@Override
	public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
		super.onBlockPlaced(world, pos, facing, hitX, hitY, hitZ, meta, placer);
		if(world.isRemote)
			if(world.provider.getDimensionId() == AbyssalCraft.configDimId2){
				if(world.getBiomeGenForCoords(pos) == ACBiomes.dreadlands_mountains){
					if(world.getBlockState(pos.down()).getBlock() == AbyssalCraft.dreadaltarbottom)
						if(pos.getY() == 41)
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltartop.enter")));
						else if(pos.getY() < 41)
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("You need to place the altar "+ (41 - pos.getY()) +" blocks higher."));
						else if(pos.getY() > 41)
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("You need to place the altar "+ (pos.getY() - 41) +" blocks lower."));
				} else
					FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.2")));
			} else
				FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.1")));
		return getStateFromMeta(meta);
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumFacing side, float par7, float par8, float par9) {
		if(par1World.provider.getDimensionId() == AbyssalCraft.configDimId2){
			if(par1World.getBiomeGenForCoords(pos) == ACBiomes.dreadlands_mountains){
				if(par1World.getBlockState(pos.down()).getBlock() == AbyssalCraft.dreadaltarbottom && pos.getY() == 41){
					if(par1World.isRemote)
						SpecialTextUtil.ChagarothGroup(par1World, StatCollector.translateToLocal("message.dreadaltartop.spawn"));
					if(!par1World.isRemote){
						par5EntityPlayer.addStat(AbyssalCraft.summonChagaroth, 1);
						chagarothlair lair = new chagarothlair();
						lair.generate(par1World, rand, pos.down(2));
						par1World.getChunkFromBlockCoords(pos).setChunkModified();
						par1World.setBlockToAir(pos.down());
						par1World.setBlockToAir(pos);
					}
				}
			} else if(par1World.isRemote)
				FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.2")));
		} else if(par1World.isRemote)
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.3")));
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, BlockPos pos, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, pos, par5Entity);

		if(par5Entity instanceof IDreadEntity){}
		else if(par5Entity instanceof EntityLivingBase)
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
	}
}