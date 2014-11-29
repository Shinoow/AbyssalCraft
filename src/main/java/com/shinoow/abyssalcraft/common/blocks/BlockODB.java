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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityODB;
import com.shinoow.abyssalcraft.common.entity.EntityODBPrimed;

public class BlockODB extends BlockContainer {

	public BlockODB() {
		super(Material.iron);
		setCreativeTab(AbyssalCraft.tabBlock);
		setBlockBounds(0.1F, 0.0F, 0.1F, 1.0F, 0.8F, 1.0F);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {

		return new TileEntityODB();
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return -6;
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);

		if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
		{
			onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5Block)
	{
		if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
		{
			onBlockDestroyedByPlayer(par1World, par2, par3, par4, 1);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
	{
		if (!par1World.isRemote)
		{
			EntityODBPrimed var5 = new EntityODBPrimed(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, par5Explosion.getExplosivePlacedBy());
			var5.fuse = par1World.rand.nextInt(var5.fuse / 4) + var5.fuse / 8;
			par1World.spawnEntityInWorld(var5);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5)
	{
		onExplosionPrimed(par1World, par2, par3, par4, par5, (EntityLivingBase)null);
	}

	public void onExplosionPrimed(World par1World, int par2, int par3, int par4, int par5, EntityLivingBase par6)
	{
		if (!par1World.isRemote)
			if ((par5 & 1) == 1)
			{
				EntityODBPrimed var7 = new EntityODBPrimed(par1World, par2 + 0.5F, par3 + 0.5F, par4 + 0.5F, par6);
				par1World.spawnEntityInWorld(var7);
				par1World.playSoundAtEntity(var7, "game.tnt.primed", 1.0F, 1.0F);
			}
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (par5EntityPlayer.getCurrentEquippedItem() != null && par5EntityPlayer.getCurrentEquippedItem().getItem() == Items.flint_and_steel)
		{
			onExplosionPrimed(par1World, par2, par3, par4, 1, par5EntityPlayer);
			par1World.setBlockToAir(par2, par3, par4);
			par5EntityPlayer.getCurrentEquippedItem().damageItem(1, par5EntityPlayer);
			return true;
		} else
			return super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
	{
		if (par5Entity instanceof EntityArrow && !par1World.isRemote)
		{
			EntityArrow var6 = (EntityArrow)par5Entity;

			if (var6.isBurning())
			{
				onExplosionPrimed(par1World, par2, par3, par4, 1, var6.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)var6.shootingEntity : null);
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}

	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion)
	{
		return false;
	}
}