/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.blocks;

import java.util.Random;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDreadAltarTop;
import com.shinoow.abyssalcraft.common.structures.dreadlands.chagarothlair;
import com.shinoow.abyssalcraft.common.util.SpecialTextUtil;

import cpw.mods.fml.client.FMLClientHandler;

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
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -4;
	}

	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9){
		super.onBlockPlaced(par1World, par2, par3, par4, par5, par6, par7, par8, par9);
		if(par1World.isRemote)
			if(par1World.provider.dimensionId == AbyssalCraft.configDimId2){
				if(par1World.getBiomeGenForCoords(par2, par4) == AbyssalCraft.MountainDreadlands){
					if(par1World.getBlock(par2, par3 - 1, par4) == AbyssalCraft.dreadaltarbottom)
						if(par3 == 41)
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltartop.enter")));
						else if(par3 < 41)
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("You need to place the altar "+ (41 - par3) +" blocks higher."));
						else if(par3 > 41)
							FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText("You need to place the altar "+ (par3 - 41) +" blocks lower."));
				} else
					FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.2")));
			} else
				FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.1")));
		return par9;
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9) {
		if(par1World.provider.dimensionId == AbyssalCraft.configDimId2){
			if(par1World.getBiomeGenForCoords(par2, par4) == AbyssalCraft.MountainDreadlands){
				if(par1World.getBlock(par2, par3 - 1, par4) == AbyssalCraft.dreadaltarbottom && par3 == 41){
					if(par1World.isRemote)
						SpecialTextUtil.ChagarothText(StatCollector.translateToLocal("message.dreadaltartop.spawn"));
					if(!par1World.isRemote){
						par5EntityPlayer.addStat(AbyssalCraft.summonChagaroth, 1);
						chagarothlair lair = new chagarothlair();
						lair.generate(par1World, rand, par2, par3 - 2, par4);
						par1World.getChunkFromBlockCoords(par2, par4).setChunkModified();
						par1World.setBlockToAir(par2, par3 - 1, par4);
						par1World.setBlockToAir(par2, par3, par4);
					}
				}
			} else if(par1World.isRemote)
				FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.2")));
		} else if(par1World.isRemote)
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.3")));
		return false;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);

		if(par5Entity instanceof IDreadEntity){}
		else if(par5Entity instanceof EntityLivingBase)
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
	}
}