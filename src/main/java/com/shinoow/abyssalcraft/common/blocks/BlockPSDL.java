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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityPSDL;

public class BlockPSDL extends BlockContainer {

	public BlockPSDL()
	{
		super(Material.rock);
		setBlockBounds(0.1F, 0.0F, 0.1F, 0.8F, 0.7F, 0.8F);
		this.setHarvestLevel("pickaxe", 5);
	}


	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TileEntityPSDL();
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int getRenderType() {
		return -1;
	}

	@Override
	public void randomDisplayTick(World par1World, int par2, int par3, int par4, Random par5Random)
	{
		super.randomDisplayTick(par1World, par2, par3, par4, par5Random);

		if(AbyssalCraft.particleBlock)
			if (par5Random.nextInt(10) == 0)
				par1World.spawnParticle("largesmoke", par2 + par5Random.nextFloat(), par3 + 1.1F, par4 + par5Random.nextFloat(), 0.0D, 0.0D, 0.0D);
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int dir = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4F / 360F + 0.5D) & 3;
		par1World.setBlockMetadataWithNotify(par2, par3, par4, dir, 0);
	}

}
