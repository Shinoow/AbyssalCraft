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
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.common.entity.EntityODBcPrimed;
import com.shinoow.abyssalcraft.lib.ACTabs;

public class BlockODBcore extends Block {

	public static final PropertyBool EXPLODE = PropertyBool.create("explode");

	public BlockODBcore() {
		super(Material.IRON);
		setDefaultState(blockState.getBaseState().withProperty(EXPLODE, Boolean.valueOf(false)));
		setCreativeTab(ACTabs.tabBlock);
		setHarvestLevel("pickaxe", 3);
		setSoundType(SoundType.METAL);
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess par1World, BlockPos pos)
	{
		return new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
	}

	@Override
	public void onBlockAdded(World par1World, BlockPos pos, IBlockState state)
	{
		super.onBlockAdded(par1World, pos, state);

		if (par1World.isBlockPowered(pos))
		{
			onBlockDestroyedByPlayer(par1World, pos, state.withProperty(EXPLODE, Boolean.valueOf(true)));
			par1World.setBlockToAir(pos);
		}
	}

	@Override
	public void neighborChanged(IBlockState state, World par1World, BlockPos pos, Block par5Block)
	{
		if (par1World.isBlockPowered(pos))
		{
			onBlockDestroyedByPlayer(par1World, pos, state.withProperty(EXPLODE, Boolean.valueOf(true)));
			par1World.setBlockToAir(pos);
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state){
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 1;
	}

	@Override
	public void onBlockDestroyedByExplosion(World par1World, BlockPos pos, Explosion par5Explosion)
	{
		if (!par1World.isRemote)
		{
			EntityODBcPrimed var6 = new EntityODBcPrimed(par1World, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, par5Explosion.getExplosivePlacedBy());
			var6.fuse = par1World.rand.nextInt(var6.fuse / 4) + var6.fuse / 8;
			par1World.spawnEntityInWorld(var6);
		}
	}

	@Override
	public void onBlockDestroyedByPlayer(World par1World, BlockPos pos, IBlockState state)
	{
		explode(par1World, pos, state, (EntityLivingBase)null);
	}

	public void explode(World par1World, BlockPos pos, IBlockState state, EntityLivingBase par6)
	{
		if (!par1World.isRemote)
			if (state.getValue(EXPLODE).booleanValue())
			{
				EntityODBcPrimed var7 = new EntityODBcPrimed(par1World, pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, par6);
				par1World.spawnEntityInWorld(var7);
				par1World.playSound(null, var7.posX, var7.posY, var7.posZ, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
	}

	@Override
	public boolean onBlockActivated(World par1World, BlockPos pos, IBlockState state, EntityPlayer par5EntityPlayer, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if (heldItem != null)
		{
			Item item = heldItem.getItem();

			if (item == Items.FLINT_AND_STEEL || item == Items.FIRE_CHARGE)
			{
				explode(par1World, pos, state.withProperty(EXPLODE, Boolean.valueOf(true)), par5EntityPlayer);
				par1World.setBlockToAir(pos);

				if (item == Items.FLINT_AND_STEEL)
					heldItem.damageItem(1, par5EntityPlayer);
				else if (!par5EntityPlayer.capabilities.isCreativeMode)
					--heldItem.stackSize;

				return true;
			}
		}

		return super.onBlockActivated(par1World, pos, state, par5EntityPlayer, hand, heldItem, side, hitX, hitY, hitZ);
	}

	@Override
	public void onEntityCollidedWithBlock(World par1World, BlockPos pos, IBlockState state, Entity par5Entity)
	{
		if (par5Entity instanceof EntityArrow && !par1World.isRemote)
		{
			EntityArrow var6 = (EntityArrow)par5Entity;

			if (var6.isBurning())
			{
				explode(par1World, pos, par1World.getBlockState(pos).withProperty(EXPLODE, Boolean.valueOf(true)), var6.shootingEntity instanceof EntityLivingBase ? (EntityLivingBase)var6.shootingEntity : null);
				par1World.setBlockToAir(pos);
			}
		}
	}

	@Override
	public boolean canDropFromExplosion(Explosion par1Explosion)
	{
		return false;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(EXPLODE, Boolean.valueOf((meta & 1) > 0));
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(EXPLODE).booleanValue() ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, new IProperty[] {EXPLODE});
	}
}