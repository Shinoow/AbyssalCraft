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
import com.shinoow.abyssalcraft.lib.tileentity.TileEntityIdolBase;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class TileEntityIdolOfFading extends TileEntityIdolBase {

	@Override
	protected boolean isValidState() {
		return world.getDifficulty() != EnumDifficulty.PEACEFUL && world.getGameRules().getBoolean("doMobSpawning")
				&& !world.canBlockSeeSky(pos.up());
	}

	@Override
	protected int getMaxCooldown() {
		return 200;
	}

	@Override
	protected float getPEPerActivation() {
		return 100;
	}

	@Override
	protected void activate() {

		int num = getNum();

		if(world.getClosestPlayer(pos.getX(), pos.getY(), pos.getZ(), 32, false) != null){
			EntityLiving mob = getShadow(num, world);
			setPosition(mob, pos.getX(), pos.getY(), pos.getZ());
			mob.onInitialSpawn(world.getDifficultyForLocation(pos), (IEntityLivingData)null);
			world.spawnEntity(mob);
			consumeEnergy(getPE(num));
		}
	}

	private void setPosition(EntityLiving entity, int x, int y, int z){
		if(world.isAirBlock(pos.up()) && world.isAirBlock(pos.up(2))){
			entity.setLocationAndAngles(x, y + 1, z, entity.rotationYaw, entity.rotationPitch);
			return;
		}
	}

	private int getNum() {
		if(world.rand.nextInt(10) == 0)
			return 2;
		else if(world.rand.nextInt(3) == 0)
			return 1;
		return 0;
	}

	private EntityLiving getShadow(int num, World world) {
		switch(num) {
		case 1:
			return new EntityShadowMonster(world);
		case 2:
			return new EntityShadowBeast(world);
		default:
			return new EntityShadowCreature(world);
		}
	}

	private float getPE(int num) {
		switch(num) {
		case 1:
			return 50F;
		case 2:
			return 100F;
		default:
			return 25F;
		}
	}
}
