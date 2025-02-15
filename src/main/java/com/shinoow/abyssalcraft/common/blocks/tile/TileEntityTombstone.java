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
import com.shinoow.abyssalcraft.common.entity.ghoul.*;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;

public class TileEntityTombstone extends TileEntity implements ITickable {

	private int timer;
	private int timerMax = 200;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		timer = nbttagcompound.getInteger("Timer");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Timer", timer);

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
		if(world.getDifficulty() != EnumDifficulty.PEACEFUL && world.getGameRules().getBoolean("doMobSpawning")) {
			timer++;
			if(timer >= timerMax) {
				timer = 0;

				int size = 15;
				int maxAmount = 5;

				if(world.getEntitiesWithinAABB(EntityGhoulBase.class, new AxisAlignedBB(pos).grow(size)).size() < maxAmount) {
					EntityGhoulBase ghoul = getGhoul(world.getBlockState(pos).getBlock());
					setPosition(ghoul, pos.getX(), pos.getY(), pos.getZ());
					ghoul.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
					world.spawnEntity(ghoul);
					world.setEntityState(ghoul, (byte) 23);
				}
			}
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

	private EntityGhoulBase getGhoul(Block block) {

		if(block == ACBlocks.tombstone_abyssal_stone
				|| block == ACBlocks.tombstone_coralium_stone) {
			return new EntityDepthsGhoul(world);
		}
		if(block == ACBlocks.tombstone_stone) {
			// Ghoul
		}
		if(block == ACBlocks.tombstone_darkstone) {
			// Shadow Ghoul
			return new EntityShadowGhoul(world);
		}
		if(block == ACBlocks.tombstone_dreadstone
				|| block == ACBlocks.tombstone_elysian_stone) {
			return new EntityDreadedGhoul(world);
		}
		if(block == ACBlocks.tombstone_ethaxium
				|| block == ACBlocks.tombstone_omothol_stone) {
			return new EntityOmotholGhoul(world);
		}
		if(block == ACBlocks.tombstone_monolith_stone) {
			// atrocity goes here
		}

		return new EntityGhoul(world);
	}
}
