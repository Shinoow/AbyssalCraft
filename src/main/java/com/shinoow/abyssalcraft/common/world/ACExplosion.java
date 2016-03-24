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
import java.util.HashSet;
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

import com.shinoow.abyssalcraft.AbyssalCraft;
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
		float f = explosionSize;
		Set<BlockPos> hashset = new HashSet<BlockPos>();
		int i;
		int j;
		int k;
		double d5;
		double d6;
		double d7;

		for (i = 0; i < chunkSize; ++i)
			for (j = 0; j < chunkSize; ++j)
				for (k = 0; k < chunkSize; ++k)
					if (i == 0 || i == chunkSize - 1 || j == 0 || j == chunkSize - 1 || k == 0 || k == chunkSize - 1)
					{
						double d0 = i / (chunkSize - 1.0F) * 2.0F - 1.0F;
						double d1 = j / (chunkSize - 1.0F) * 2.0F - 1.0F;
						double d2 = k / (chunkSize - 1.0F) * 2.0F - 1.0F;
						double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
						d0 /= d3;
						d1 /= d3;
						d2 /= d3;
						float f1 = explosionSize * (0.7F + worldObj.rand.nextFloat() * 0.6F);
						d5 = explosionX;
						d6 = explosionY;
						d7 = explosionZ;

						for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F)
						{
							BlockPos pos = new BlockPos(d5, d6, d7);
							IBlockState state = worldObj.getBlockState(pos);

							if (state.getMaterial() != Material.air)
							{
								float f3 = exploder != null ? exploder.getExplosionResistance(this, worldObj, pos, state) : state.getBlock().getExplosionResistance(worldObj, pos, null, this);
								f1 -= (f3 + 0.3F) * f2;
							}

							if (f1 > 0.0F && (exploder == null || exploder.verifyExplosion(this, worldObj, pos, state, f1)))
								hashset.add(pos);

							d5 += d0 * 0.30000001192092896D;
							d6 += d1 * 0.30000001192092896D;
							d7 += d2 * 0.30000001192092896D;
						}
					}

		affectedBlockPositions.addAll(hashset);
		explosionSize *= 2.0F;
		i = MathHelper.floor_double(explosionX - explosionSize - 1.0D);
		j = MathHelper.floor_double(explosionX + explosionSize + 1.0D);
		k = MathHelper.floor_double(explosionY - explosionSize - 1.0D);
		int i2 = MathHelper.floor_double(explosionY + explosionSize + 1.0D);
		int l = MathHelper.floor_double(explosionZ - explosionSize - 1.0D);
		int j2 = MathHelper.floor_double(explosionZ + explosionSize + 1.0D);
		List<?> list = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, new AxisAlignedBB(i, k, l, j, i2, j2));
		Vec3d vec3 = new Vec3d(explosionX, explosionY, explosionZ);

		for (int i1 = 0; i1 < list.size(); ++i1)
		{
			Entity entity = (Entity)list.get(i1);
			double d4 = entity.getDistance(explosionX, explosionY, explosionZ) / explosionSize;

			if (d4 <= 1.0D)
			{
				d5 = entity.posX - explosionX;
				d6 = entity.posY + entity.getEyeHeight() - explosionY;
				d7 = entity.posZ - explosionZ;
				double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);

				if (d9 != 0.0D)
				{
					d5 /= d9;
					d6 /= d9;
					d7 /= d9;
					double d10 = worldObj.getBlockDensity(vec3, entity.getEntityBoundingBox());
					double d11 = (1.0D - d4) * d10;
					entity.attackEntityFrom(DamageSource.causeExplosionDamage(this), (int)((d11 * d11 + d11) / 2.0D * 8.0D * explosionSize + 1.0D));
					double d8 = 1.0D;

					if(entity instanceof EntityLivingBase)
						d8 = EnchantmentProtection.func_92092_a((EntityLivingBase) entity, d11);
					entity.motionX += d5 * d8;
					entity.motionY += d6 * d8;
					entity.motionZ += d7 * d8;

					if (entity instanceof EntityPlayer)
						field_77288_k.put((EntityPlayer)entity, new Vec3d(d5 * d11, d6 * d11, d7 * d11));
				}
			}
		}

		explosionSize = f;
	}

	/**
	 * Does the second part of the explosion (sound, particles, drop spawn)
	 */
	@Override
	public void doExplosionB(boolean par1)
	{
		worldObj.playSound(explosionX, explosionY, explosionZ, SoundEvents.entity_generic_explode, SoundCategory.BLOCKS, 4.0F, (1.0F + (worldObj.rand.nextFloat() - worldObj.rand.nextFloat()) * 0.2F) * 0.7F, false);

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
					double d6 = MathHelper.sqrt_double(d3 * d3 + d4 * d4 + d5 * d5);
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

				if (block.getMaterial() != Material.air)
					block.getBlock().onBlockExploded(worldObj, pos, this);
			}

		if (isAntimatter)
			for(BlockPos pos1 : affectedBlockPositions)
			{
				IBlockState block = worldObj.getBlockState(pos1);
				IBlockState block1 = worldObj.getBlockState(pos1.down());

				if (block.getMaterial() == Material.air && block1.isFullBlock() && explosionRNG.nextInt(3) == 0)
					worldObj.setBlockState(pos1, AbyssalCraft.anticwater.getDefaultState());
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
