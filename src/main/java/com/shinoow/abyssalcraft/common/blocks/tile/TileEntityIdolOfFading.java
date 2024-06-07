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

import com.shinoow.abyssalcraft.common.entity.EntityShadowBeast;
import com.shinoow.abyssalcraft.common.entity.EntityShadowCreature;
import com.shinoow.abyssalcraft.common.entity.EntityShadowMonster;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class TileEntityIdolOfFading extends TileEntity implements ITickable {

	private int cooldown;
	private int spawnedShadows;

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		cooldown = nbttagcompound.getInteger("Cooldown");
		spawnedShadows = nbttagcompound.getInteger("SpawnedShadows");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("Cooldown", cooldown);
		nbttagcompound.setInteger("SpawnedShadows", spawnedShadows);

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
		if(world.getDifficulty() != EnumDifficulty.PEACEFUL && world.getGameRules().getBoolean("doMobSpawning")
				&& !world.canBlockSeeSky(pos.up())){
			cooldown++;
			if (cooldown >= 200) {
				cooldown = world.rand.nextInt(10);
				if(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 32, false) != null){
					EntityLiving mob = getShadow(world);
					setPosition(mob, pos.getX(), pos.getY(), pos.getZ());
					mob.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
					world.spawnEntity(mob);
					spawnedShadows++;
					if(spawnedShadows >= 10)
						world.setBlockToAir(pos);
				}
			}
		}
	}

	private void setPosition(EntityLiving entity, int x, int y, int z){
		if(world.isAirBlock(pos.up()) && world.isAirBlock(pos.up(2))){
			entity.setLocationAndAngles(x, y + 1, z, entity.rotationYaw, entity.rotationPitch);
			return;
		}
	}

	public int getCooldown(){
		return cooldown;
	}

	public void setCooldown(int cd){
		cooldown = cd;
	}

	private EntityLiving getShadow(World world) {

		if(world.rand.nextInt(10) == 0) {
			return new EntityShadowBeast(world);
		}
		else if(world.rand.nextInt(3) == 0)
			return new EntityShadowMonster(world);

		return new EntityShadowCreature(world);
	}
}
