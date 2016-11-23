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
package com.shinoow.abyssalcraft.common.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import com.google.common.collect.Sets;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityODBPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityODBcPrimed;

public class ACExplosion extends Explosion
{
	/** whether or not the explosion converts nearby blocks to liquid antimatter */
	public boolean isAntimatter;
	/** whether or not this explosion spawns smoke particles */
	public boolean isSmoking = true;
	private int chunkSize;
	private Random explosionRNG = new Random();
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;
	/** A list of BlockPos of blocks affected by this explosion */
	public List<BlockPos> affectedBlockPositions = new ArrayList<BlockPos>();
	private Map<EntityPlayer, Vec3d> field_77288_k = new HashMap<EntityPlayer, Vec3d>();

	public ACExplosion(World par1World, Entity par2Entity, double par3, double par5, double par7, float par9, int par11, boolean par12, boolean par14)
	{
		super(par1World, par2Entity, par3, par5, par7, par9, par12, par14);
		worldObj = par1World;
		exploder = par2Entity;
		explosionSize = par9;
		explosionX = par3;
		explosionY = par5;
		explosionZ = par7;
		chunkSize = par11;
		isAntimatter = par12;
		isSmoking = par14;
	}

	/**
	 * Does the first part of the explosion (destroy blocks)
	 */
	@Override
	public void doExplosionA()
	{
		Set<BlockPos> set = Sets.<BlockPos>newHashSet();
		for (int j = 0; j < chunkSize; ++j)
			for (int k = 0; k < chunkSize; ++k)
				for (int l = 0; l < chunkSize; ++l)
					if (j == 0 || j == chunkSize - 1 || k == 0 || k == chunkSize - 1 || l == 0 || l == chunkSize - 1)
					{
						double d0 = (float)j / (chunkSize - 1) * 2.0F - 1.0F;
						double d1 = (float)k / (chunkSize - 1) * 2.0F - 1.0F;
						double d2 = (float)l / (chunkSize - 1) * 2.0F - 1.0F;
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 /= d3;
						d1 /= d3;
						d2 /= d3;
						float f = explosionSize * (0.7F + worldObj.rand.nextFloat() * 0.6F);
						double d4 = explosionX;
						double d6 = explosionY;
						double d8 = explosionZ;

						for (; f > 0.0F; f -= 0.22500001F)
						{
							BlockPos blockpos = new BlockPos(d4, d6, d8);
							IBlockState iblockstate = worldObj.getBlockState(blockpos);

							if (iblockstate.getMaterial() != Material.AIR)
							{
								float f2 = exploder != null ? exploder.getExplosionResistance(this, worldObj, blockpos, iblockstate) : iblockstate.getBlock().getExplosionResistance(worldObj, blockpos, (Entity)null, this);
								f -= (f2 + 0.3F) * 0.3F;
							}

							if (f > 0.0F && (exploder == null || exploder.verifyExplosion(this, worldObj, blockpos, iblockstate, f)))
								set.add(blockpos);

							d4 += d0 * 0.30000001192092896D;
							d6 += d1 * 0.30000001192092896D;
							d8 += d2 * 0.30000001192092896D;
						}
					}

		affectedBlockPositions.addAll(set);
		float f3 = explosionSize * 2.0F;
		int k1 = MathHelper.floor(explosionX - f3 - 1.0D);
		int l1 = MathHelper.floor(explosionX + f3 + 1.0D);
		int i2 = MathHelper.floor(explosionY - f3 - 1.0D);
		int i1 = MathHelper.floor(explosionY + f3 + 1.0D);
		int j2 = MathHelper.floor(explosionZ - f3 - 1.0D);
		int j1 = MathHelper.floor(explosionZ + f3 + 1.0D);
		List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
		net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(worldObj, this, list, f3);
		Vec3d vec3d = new Vec3d(explosionX, explosionY, explosionZ);

		for (int k2 = 0; k2 < list.size(); ++k2)
		{
			Entity entity = list.get(k2);

			if (!entity.isImmuneToExplosions())
			{
				double d12 = entity.getDistance(explosionX, explosionY, explosionZ) / f3;

				if (d12 <= 1.0D)
				{
					double d5 = entity.posX - explosionX;
					double d7 = entity.posY + entity.getEyeHeight() - explosionY;
					double d9 = entity.posZ - explosionZ;
					double d13 = MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

					if (d13 != 0.0D)
					{
						d5 /= d13;
						d7 /= d13;
						d9 /= d13;
						double d14 = worldObj.getBlockDensity(vec3d, entity.getEntityBoundingBox());
						double d10 = (1.0D - d12) * d14;
						entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (int)((d10 * d10 + d10) / 2.0D * 7.0D * f3 + 1.0D));
						double d11 = 1.0D;

						if (entity instanceof EntityLivingBase)
							d11 = EnchantmentProtection.getBlastDamageReduction((EntityLivingBase)entity, d10);

						entity.motionX += d5 * d11;
						entity.motionY += d7 * d11;
						entity.motionZ += d9 * d11;

						if (entity instanceof EntityPlayer)
						{
							EntityPlayer entityplayer = (EntityPlayer)entity;

							if (!entityplayer.isSpectator() && (!entityplayer.isCreative() || !entityplayer.capabilities.isFlying))
								field_77288_k.put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
						}
					}
				}
			}
		}
	}

	/**
	 * Does the second part of the explosion (sound, particles, drop spawn)
	 */
	@Override
	public void doExplosionB(boolean par1)
	{
		worldObj.playSound(null, explosionX, explosionY, explosionZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F);

		if (explosionSize >= 2.0F && isSmoking)
			worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D);
		else
			worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, explosionX, explosionY, explosionZ, 1.0D, 0.0D, 0.0D);


		if (isSmoking)
			for(BlockPos pos : affectedBlockPositions)
			{

				IBlockState block = worldObj.getBlockState(pos);

				if (par1)
				{
					double d0 = pos.getX() + worldObj.rand.nextFloat();
					double d1 = pos.getY() + worldObj.rand.nextFloat();
					double d2 = pos.getZ() + worldObj.rand.nextFloat();
					double d3 = d0 - explosionX;
					double d4 = d1 - explosionY;
					double d5 = d2 - explosionZ;
					double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
					d3 /= d6;
					d4 /= d6;
					d5 /= d6;
					double d7 = 0.5D / (d6 / explosionSize + 0.1D);
					d7 *= worldObj.rand.nextFloat() * worldObj.rand.nextFloat() + 0.3F;
					d3 *= d7;
					d4 *= d7;
					d5 *= d7;
					worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + explosionX * 1.0D) / 2.0D, (d1 + explosionY * 1.0D) / 2.0D, (d2 + explosionZ * 1.0D) / 2.0D, d3, d4, d5);
					worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
				}

				if (block.getMaterial() != Material.AIR)
					block.getBlock().onBlockExploded(worldObj, pos, this);
			}

		if (isAntimatter)
			for(BlockPos pos1 : affectedBlockPositions)
			{
				IBlockState block = worldObj.getBlockState(pos1);
				IBlockState block1 = worldObj.getBlockState(pos1.down());

				if (block.getMaterial() == Material.AIR && block1.isFullBlock() && explosionRNG.nextInt(3) == 0)
					worldObj.setBlockState(pos1, ACBlocks.liquid_antimatter.getDefaultState());
			}
	}

	@Override
	public Map<EntityPlayer, Vec3d> getPlayerKnockbackMap()
	{
		return field_77288_k;
	}

	/**
	 * Returns either the entity that placed the explosive block, the entity that caused the explosion or null.
	 */
	@Override
	public EntityLivingBase getExplosivePlacedBy()
	{
		return exploder == null ? null : exploder instanceof EntityODBPrimed ? ((EntityODBPrimed)exploder).getODBPlacedBy() : exploder instanceof EntityODBcPrimed ? ((EntityODBcPrimed)exploder).getODBCPlacedBy() : exploder instanceof EntityLivingBase ? (EntityLivingBase)exploder : null;
	}
}
