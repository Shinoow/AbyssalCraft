/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
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
import com.shinoow.abyssalcraft.common.blocks.BlockACStone;
import com.shinoow.abyssalcraft.common.blocks.BlockACStone.EnumStoneType;
import com.shinoow.abyssalcraft.common.entity.EntityLesserShoggoth;
import com.shinoow.abyssalcraft.lib.ACConfig;

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
			if (cooldown >= ACConfig.biomassCooldown) {
				cooldown = world.rand.nextInt(10);
				resetNearbyBiomass(true);
				if(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), ACConfig.biomassPlayerDistance, false) != null)
					if(world.getEntitiesWithinAABB(EntityLesserShoggoth.class, new AxisAlignedBB(pos).grow(ACConfig.biomassShoggothDistance)).size() < ACConfig.biomassMaxSpawn){
						EntityLesserShoggoth mob = new EntityLesserShoggoth(world);
						setPosition(mob, pos.getX(), pos.getY(), pos.getZ());
						mob.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
						world.spawnEntity(mob);
						spawnedShoggoths++;
						if(spawnedShoggoths >= 5)
							world.setBlockState(pos, ACBlocks.stone.getDefaultState().withProperty(BlockACStone.TYPE, EnumStoneType.MONOLITH_STONE), 2);
					}
			}
		}
	}

	public void resetNearbyBiomass(boolean again){
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0, 0, 0);
		TileEntity[] tiles = {
				world.getTileEntity(new BlockPos(x - 1, y, z)),
				world.getTileEntity(new BlockPos(x, y, z - 1)),
				world.getTileEntity(new BlockPos(x - 1, y, z - 1)),
				world.getTileEntity(new BlockPos(x + 1, y, z)),
				world.getTileEntity(new BlockPos(x, y, z + 1)),
				world.getTileEntity(new BlockPos(x + 1, y, z + 1)),
				world.getTileEntity(new BlockPos(x - 1, y, z + 1)),
				world.getTileEntity(new BlockPos(x + 1, y, z - 1))
		};

		for(TileEntity tile : tiles)
			if(tile instanceof TileEntityShoggothBiomass) {
				((TileEntityShoggothBiomass) tile).setCooldown(world.rand.nextInt(30));
				if(again)
					((TileEntityShoggothBiomass) tile).resetNearbyBiomass(false);
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
}
