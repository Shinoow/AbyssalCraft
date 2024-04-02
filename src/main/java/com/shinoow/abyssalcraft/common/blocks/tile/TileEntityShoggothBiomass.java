/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.blocks.tile;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.*;
import com.shinoow.abyssalcraft.lib.ACLib;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class TileEntityShoggothBiomass extends TileEntity implements ITickable {

	private int cooldown;
	private int spawnedShoggoths;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		cooldown = nbttagcompound.getInteger("Cooldown");
		spawnedShoggoths = nbttagcompound.getInteger("SpawnedShoggoths");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Cooldown", cooldown);
		nbttagcompound.setInteger("SpawnedShoggoths", spawnedShoggoths);

		return nbttagcompound;
	}

	@Override
	public void onLoad()
	{
		if(world.isRemote)
			world.tickableTileEntities.remove(this);
	}

	@Override
	public void update() {
		if(world.getDifficulty() != EnumDifficulty.PEACEFUL && world.getGameRules().getBoolean("doMobSpawning")){
			cooldown++;
			if (cooldown >= 400) {
				cooldown = world.rand.nextInt(10);
				resetNearbyBiomass(true);
				if(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 32, false) != null)
					if(world.getEntitiesWithinAABB(EntityLesserShoggoth.class, new AxisAlignedBB(pos).grow(32)).size() <= 6){
						EntityShoggothBase mob = getShoggoth(world);
						setPosition(mob, pos.getX(), pos.getY(), pos.getZ());
						mob.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
						world.spawnEntity(mob);
						spawnedShoggoths++;
						if(spawnedShoggoths >= 5)
							world.setBlockState(pos, ACBlocks.monolith_stone.getDefaultState(), 2);
					}
			}
		}
	}

	public void resetNearbyBiomass(boolean again){
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0, 0, 0);
		TileEntity tile1 = world.getTileEntity(new BlockPos(x - 1, y, z));
		TileEntity tile2 = world.getTileEntity(new BlockPos(x, y, z - 1));
		TileEntity tile3 = world.getTileEntity(new BlockPos(x - 1, y, z - 1));
		TileEntity tile4 = world.getTileEntity(new BlockPos(x + 1, y, z));
		TileEntity tile5 = world.getTileEntity(new BlockPos(x, y, z + 1));
		TileEntity tile6 = world.getTileEntity(new BlockPos(x + 1, y, z + 1));
		TileEntity tile7 = world.getTileEntity(new BlockPos(x - 1, y, z + 1));
		TileEntity tile8 = world.getTileEntity(new BlockPos(x + 1, y, z - 1));
		if(again){
			if(tile1 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile1).setCooldown(world.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile1).resetNearbyBiomass(false);
			} if(tile2 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile2).setCooldown(world.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile2).resetNearbyBiomass(false);
			} if(tile3 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile3).setCooldown(world.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile3).resetNearbyBiomass(false);
			} if(tile4 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile4).setCooldown(world.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile4).resetNearbyBiomass(false);
			} if(tile5 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile5).setCooldown(world.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile5).resetNearbyBiomass(false);
			} if(tile6 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile6).setCooldown(world.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile6).resetNearbyBiomass(false);
			} if(tile7 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile7).setCooldown(world.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile7).resetNearbyBiomass(false);
			} if(tile8 instanceof TileEntityShoggothBiomass){
				((TileEntityShoggothBiomass) tile8).setCooldown(world.rand.nextInt(10));
				((TileEntityShoggothBiomass) tile8).resetNearbyBiomass(false);
			}
		}
		else {
			if(tile1 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile1).setCooldown(world.rand.nextInt(10));
			if(tile2 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile2).setCooldown(world.rand.nextInt(10));
			if(tile3 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile3).setCooldown(world.rand.nextInt(10));
			if(tile4 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile4).setCooldown(world.rand.nextInt(10));
			if(tile5 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile5).setCooldown(world.rand.nextInt(10));
			if(tile6 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile6).setCooldown(world.rand.nextInt(10));
			if(tile7 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile7).setCooldown(world.rand.nextInt(10));
			if(tile8 instanceof TileEntityShoggothBiomass)
				((TileEntityShoggothBiomass) tile8).setCooldown(world.rand.nextInt(10));

		}
	}

	private void setPosition(EntityLiving entity, int x, int y, int z){
		if(world.isAirBlock(pos.up()) && world.isAirBlock(pos.up(2))){
			entity.setLocationAndAngles(x, y + 1, z, entity.rotationYaw, entity.rotationPitch);
			return;
		}
		for(int i = -1; i < 2; i++)
			for(int j = -1; j < 2; j++)
				if(!world.getBlockState(new BlockPos(x + i, y + 1, z + j)).getMaterial().isSolid()) {
					entity.setLocationAndAngles(x + i, y + 1, z + j, entity.rotationYaw, entity.rotationPitch);
					return;
				}
		for(int i = -4; i < 5; i+=4)
			for(int j = -4; j < 5; j+=4)
				if(!world.getBlockState(new BlockPos(x + i, y + 1, z + j)).getMaterial().isSolid()) {
					entity.setLocationAndAngles(x + i, y + 1, z + j, entity.rotationYaw, entity.rotationPitch);
					return;
				}
		if(!world.getBlockState(new BlockPos(x, y + 2, z)).getMaterial().isSolid()) {
			entity.setLocationAndAngles(x, y + 2, z, entity.rotationYaw, entity.rotationPitch);
			return;
		}
		if(world.getBlockState(new BlockPos(x, y + 15, z)).getMaterial().isSolid()){
			entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10, 100));
			entity.setLocationAndAngles(x, y + 20, z, entity.rotationYaw, entity.rotationPitch);
		} else {
			entity.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 10, 100));
			entity.setLocationAndAngles(x, y + 15, z, entity.rotationYaw, entity.rotationPitch);
		}
	}

	public int getCooldown(){
		return cooldown;
	}

	public void setCooldown(int cd){
		cooldown = cd;
	}

	private EntityShoggothBase getShoggoth(World world) {
		if(world.rand.nextBoolean()) {
			int dim = world.provider.getDimension();
			boolean greater = world.rand.nextInt(100) == 0;
			if(dim == ACLib.abyssal_wasteland_id || dim == ACLib.dreadlands_id
					|| dim == ACLib.omothol_id || dim == ACLib.dark_realm_id)
				return greater ? new EntityGreaterShoggoth(world) : new EntityShoggoth(world);
		}

		return new EntityLesserShoggoth(world);
	}
}
