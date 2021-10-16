/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.world;

import java.util.*;

import com.google.common.collect.Lists;
import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityODBPrimed;
import com.shinoow.abyssalcraft.common.entity.EntityODBcPrimed;
import com.shinoow.abyssalcraft.lib.util.ScheduledProcess;
import com.shinoow.abyssalcraft.lib.util.Scheduler;

import gnu.trove.set.hash.THashSet;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.play.server.SPacketChunkData;
import net.minecraft.server.management.PlayerChunkMap;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;

/**
 * Somewhat scalable explosion with spheroid craters
 * The chunk-related bits are based off of similar code in Draconic Evolution (with some minor alterations)
 * The sphere generation code used for the crater comes from WorldEdit (with some minor alterations)
 *
 */
public class ACExplosion extends Explosion
{
	/** whether or not the explosion converts nearby blocks to liquid antimatter */
	public boolean isAntimatter;
	/** whether or not this explosion spawns smoke particles */
	public boolean isSmoking = true;
	private Random explosionRNG = new Random();
	private World worldObj;
	public double explosionX;
	public double explosionY;
	public double explosionZ;
	public Entity exploder;
	public float explosionSize;
	/** A list of BlockPos of blocks affected by this explosion */
	public List<BlockPos> affectedBlockPositions = new ArrayList<>();
	private List<BlockPos> innerBlocks = new ArrayList<>();
	private List<BlockPos> outerBlocks = new ArrayList<>();
	private Map<EntityPlayer, Vec3d> playerKnockbackMap = new HashMap<>();
	private Map<ChunkPos, Chunk> chunkCache = new HashMap<>();
	private Set<Chunk> chunks = new THashSet<>();

	public ACExplosion(World world, Entity entity, double x, double y, double z, float strength, boolean antimatter, boolean smoke)
	{
		super(world, entity, x, y, z, strength, antimatter, smoke);
		worldObj = world;
		exploder = entity;
		explosionSize = strength;
		explosionX = x;
		explosionY = y;
		explosionZ = z;
		isAntimatter = antimatter;
		isSmoking = smoke;
	}

	private void checkAndAdd(BlockPos pos, List<BlockPos> list){

		if(!worldObj.isOutsideBuildHeight(pos)) {
			Chunk c = getChunk(pos);
			IBlockState iblockstate = c.getBlockState(pos);

			float f2 = exploder != null ? exploder.getExplosionResistance(this, worldObj, pos, iblockstate) : iblockstate.getBlock().getExplosionResistance(worldObj, pos, (Entity)null, this);

			if(f2 < 600000 && iblockstate.getMaterial() != Material.AIR)
				list.add(pos);
		}
	}

	/**
	 * Does the first part of the explosion (destroy blocks)
	 */
	@Override
	public void doExplosionA()
	{
		double radiusX = explosionSize + 0.5;
		double radiusY = explosionSize * 0.6 + 0.5;
		double radiusZ = explosionSize + 0.5;

		BlockPos pos = new BlockPos(explosionX, explosionY, explosionZ);

		final double invRadiusX = 1 / radiusX;
		final double invRadiusY = 1 / radiusY;
		final double invRadiusZ = 1 / radiusZ;

		final int ceilRadiusX = (int) Math.ceil(radiusX);
		final int ceilRadiusY = (int) Math.ceil(radiusY);
		final int ceilRadiusZ = (int) Math.ceil(radiusZ);

		double nextXn = 0;
		forX: for (int x = 0; x <= ceilRadiusX; ++x) {
			final double xn = nextXn;
			nextXn = (x + 1) * invRadiusX;
			double nextYn = 0;
			forY: for (int y = 0; y <= ceilRadiusY; ++y) {
				final double yn = nextYn;
				nextYn = (y + 1) * invRadiusY;
				double nextZn = 0;
				forZ: for (int z = 0; z <= ceilRadiusZ; ++z) {
					final double zn = nextZn;
					nextZn = (z + 1) * invRadiusZ;

					double distanceSq = xn*xn + yn*yn + zn*zn;
					if (distanceSq > 1) {
						if (z == 0) {
							if (y == 0)
								break forX;
							break forY;
						}
						break forZ;
					}

					if (nextXn*nextXn + yn*yn + zn*zn <= 1 && xn*xn + nextYn*nextYn + zn*zn <= 1 && xn*xn + yn*yn + nextZn*nextZn <= 1) {
						checkAndAdd(pos.add(x, y, z), innerBlocks);
						checkAndAdd(pos.add(-x, y, z), innerBlocks);
						checkAndAdd(pos.add(x, -y, z), innerBlocks);
						checkAndAdd(pos.add(x, y, -z), innerBlocks);
						checkAndAdd(pos.add(-x, -y, z), innerBlocks);
						checkAndAdd(pos.add(x, -y, -z), innerBlocks);
						checkAndAdd(pos.add(-x, y, -z), innerBlocks);
						checkAndAdd(pos.add(-x, -y, -z), innerBlocks);
					} else {
						checkAndAdd(pos.add(x, y, z), outerBlocks);
						checkAndAdd(pos.add(-x, y, z), outerBlocks);
						checkAndAdd(pos.add(x, -y, z), outerBlocks);
						checkAndAdd(pos.add(x, y, -z), outerBlocks);
						checkAndAdd(pos.add(-x, -y, z), outerBlocks);
						checkAndAdd(pos.add(x, -y, -z), outerBlocks);
						checkAndAdd(pos.add(-x, y, -z), outerBlocks);
						checkAndAdd(pos.add(-x, -y, -z), outerBlocks);
					}
				}
			}
		}

		if(explosionSize <= 32) {
			affectedBlockPositions.addAll(innerBlocks);
			affectedBlockPositions.addAll(outerBlocks);
		}

		if(!worldObj.isRemote){

			float f3 = explosionSize * 2.0F;
			int k1 = MathHelper.floor(explosionX - f3 - 1.0D);
			int l1 = MathHelper.floor(explosionX + f3 + 1.0D);
			int i2 = MathHelper.floor(explosionY - f3 - 1.0D);
			int i1 = MathHelper.floor(explosionY + f3 + 1.0D);
			int j2 = MathHelper.floor(explosionZ - f3 - 1.0D);
			int j1 = MathHelper.floor(explosionZ + f3 + 1.0D);
			List<Entity> list = worldObj.getEntitiesWithinAABBExcludingEntity(exploder, new AxisAlignedBB(k1, i2, j2, l1, i1, j1));
			net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(worldObj, this, list, f3);

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
							double d10 = 1.0D - d12;
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
									playerKnockbackMap.put(entityplayer, new Vec3d(d5 * d10, d7 * d10, d9 * d10));
							}
						}
					}
				}
			}
		}
	}

	private Chunk getChunk(BlockPos pos) {

		ChunkPos cp = new ChunkPos(pos);
		chunkCache.putIfAbsent(cp, worldObj.getChunkFromChunkCoords(cp.x, cp.z));

		return chunkCache.get(cp);
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

		int num = 1;
		int index = 0;
		if (isSmoking){
			List<List<BlockPos>> innerLists = Lists.partition(innerBlocks, 1000);
			for(List<BlockPos> innerList : innerLists) {
				Scheduler.schedule(new ScheduledProcess(num) {

					@Override
					public void execute() {
						for(BlockPos pos : innerList) {
							Chunk chunk = getChunk(pos);
							ExtendedBlockStorage storage = chunk.getBlockStorageArray()[pos.getY() >> 4];
							if(storage != null)
								storage.set(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, Blocks.AIR.getDefaultState());
							chunks.add(chunk);
						}

					}

				});
				index++;
				if(index % 1000 == 0)
					num++;
			}

			List<List<BlockPos>> outerLists = Lists.partition(outerBlocks, 100);
			for(List<BlockPos> outerList : outerLists) {
				ACExplosion explosion = this;
				Scheduler.schedule(new ScheduledProcess(num) {

					@Override
					public void execute() {
						for(BlockPos pos : outerList)
							if(par1)
								worldObj.getBlockState(pos).getBlock().onBlockExploded(worldObj, pos, explosion);
							else {
								Chunk chunk = getChunk(pos);
								ExtendedBlockStorage storage = chunk.getBlockStorageArray()[pos.getY() >> 4];
								if(storage != null)
									storage.set(pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15, Blocks.AIR.getDefaultState());
								chunks.add(chunk);
							}
					}

				});
				index++;
				if(index % 100 == 0)
					num++;
			}
		}

		if (isAntimatter) {
			List<List<BlockPos>> extraLists = Lists.partition(explosionSize <= 32 ? affectedBlockPositions : outerBlocks, 100);
			for(List<BlockPos> extraList : extraLists) {
				Scheduler.schedule(new ScheduledProcess(num) {

					@Override
					public void execute() {
						for(BlockPos pos1 : extraList)
						{
							IBlockState block = worldObj.getBlockState(pos1);
							IBlockState block1 = worldObj.getBlockState(pos1.down());

							if (block.getMaterial() == Material.AIR && block1.isFullBlock() && explosionRNG.nextInt(3) == 0)
								worldObj.setBlockState(pos1, ACBlocks.liquid_antimatter.getDefaultState());
						}
					}

				});
				index++;
				if(index % 100 == 0)
					num++;
			}
		}

		Scheduler.schedule(new ScheduledProcess(num+1) {

			@Override
			public void execute() {
				PlayerChunkMap pcm = ((WorldServer)worldObj).getPlayerChunkMap();
				if(pcm != null)
					for(Chunk c : chunks) {
						c.setModified(true);
						c.generateSkylightMap();

						PlayerChunkMapEntry w = pcm.getEntry(c.x, c.z);
						if(w != null)
							w.sendPacket(new SPacketChunkData(c, 65535));
					}
			}

		});

		if(exploder instanceof EntityODBPrimed)
			((EntityODBPrimed)exploder).finishExplosion(num + 10, this);
	}

	@Override
	public Map<EntityPlayer, Vec3d> getPlayerKnockbackMap()
	{
		return playerKnockbackMap;
	}

	@Override
	public List<BlockPos> getAffectedBlockPositions()
	{
		return explosionSize <= 32 ? affectedBlockPositions : innerBlocks;
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
