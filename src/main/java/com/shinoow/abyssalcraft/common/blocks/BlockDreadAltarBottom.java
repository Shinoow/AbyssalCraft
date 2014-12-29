/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityDreadAltarBottom;

import cpw.mods.fml.client.FMLClientHandler;

public class BlockDreadAltarBottom extends BlockContainer {

	public BlockDreadAltarBottom() {
		super(Material.rock);
		setHarvestLevel("pickaxe", 6);

	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileEntityDreadAltarBottom();
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
		return -5;
	}

	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9){
		super.onBlockPlaced(par1World, par2, par3, par4, par5, par6, par7, par8, par9);
		if(par1World.provider.dimensionId != AbyssalCraft.configDimId2  && par1World.isRemote)
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.1")));
		if(par1World.provider.dimensionId == AbyssalCraft.configDimId2 && par1World.getBiomeGenForCoords(par2, par4) != AbyssalCraft.MountainDreadlands  && par1World.isRemote)
			FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(StatCollector.translateToLocal("message.dreadaltar.error.2")));
		return par9;
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		super.onEntityCollidedWithBlock(par1World, par2, par3, par4, par5Entity);

		if(par5Entity instanceof IDreadEntity){}
		else if(par5Entity instanceof EntityLivingBase)
			((EntityLivingBase)par5Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 100));
	}
}