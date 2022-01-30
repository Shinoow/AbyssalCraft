/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityShoggothBase;
import com.shinoow.abyssalcraft.common.world.gen.WorldGenShoggothMonolith;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityAIShoggothBuildMonolith extends EntityAIBase {

	private EntityShoggothBase shoggoth;
	private double locationX, locationY, locationZ;
	private final World world;
	private List<EntityShoggothBase> shoggoths;
	private int attemptTimer;

	public EntityAIShoggothBuildMonolith(EntityShoggothBase shoggoth) {
		this.shoggoth = shoggoth;
		world = shoggoth.world;
		shoggoths = new ArrayList<>();
		setMutexBits(3);
	}

	@Override
	public boolean shouldExecute() {

		if(ACConfig.monolithBuildingCooldown == 0 || shoggoth.isChild() || shoggoth.isAssisting || shoggoth.isBuilding)
			return false;
		else if(shoggoth.getMonolithTimer() < ACConfig.monolithBuildingCooldown || world.getEntitiesWithinAABB(shoggoth.getClass(), shoggoth.getEntityBoundingBox().grow(16D, 16D, 16D), e -> !e.equals(shoggoth) && !e.isAssisting && !e.isBuilding).size() < 3)
			return false;
		else {

			Vec3d vec3d = findPossibleLocation();
			if(vec3d == null)
				return false;
			else {

				List<EntityShoggothBase> list = world.getEntitiesWithinAABB(shoggoth.getClass(), shoggoth.getEntityBoundingBox().grow(16D, 16D, 16D), e -> !e.equals(shoggoth) && !e.isAssisting && !e.isBuilding);
				for(EntityShoggothBase shoggoth1 : list) {
					if(shoggoths.size() == 3)
						break;
					if(shoggoth1.getDistance(shoggoth) <= 12) {
						shoggoth1.reduceMonolithTimer();
						shoggoth1.isAssisting = true;
						shoggoths.add(shoggoth1);
					}
				}

				if(shoggoths.size() != 3)
					return false;

				shoggoth.isBuilding = true;
				locationX = vec3d.x;
				locationY = vec3d.y;
				locationZ = vec3d.z;
				return true;
			}
		}
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		if(!shoggoth.isBuilding)
			return false;
		if(attemptTimer >= 0)
			return shoggoth.getDistance(locationX, locationY, locationZ) > 8 || !allShoggothsNearby();
			return false;
	}

	@Override
	public boolean isInterruptible()
	{
		return false;
	}

	@Override
	public void startExecuting()
	{
		attemptTimer = 400;
		shoggoth.getNavigator().tryMoveToXYZ(locationX + (shoggoth.getRNG().nextBoolean() ? 3 : -3), locationY, locationZ + (shoggoth.getRNG().nextBoolean() ? 3 : -3), 0.38D);
		shoggoths.get(0).getNavigator().tryMoveToXYZ(locationX - 3, locationY, locationZ - 3, 0.38D);
		shoggoths.get(1).getNavigator().tryMoveToXYZ(locationX + 3, locationY, locationZ + 3, 0.38D);
		shoggoths.get(2).getNavigator().tryMoveToXYZ(locationX + 3, locationY, locationZ - 3, 0.38D);
	}

	@Override
	public void updateTask()
	{
		--attemptTimer;
		if(shoggoth.getDistance(locationX, locationY, locationZ) <= 8 && allShoggothsNearby()) {
			shoggoth.resetMonolithTimer();
			for(EntityShoggothBase shoggoth : world.getEntitiesWithinAABB(shoggoth.getClass(), shoggoth.getEntityBoundingBox().grow(32D, 32D, 32D)))
				if(shoggoth.getDistance(locationX, locationY, locationZ) <= 5)
					shoggoth.getNavigator().tryMoveToXYZ(locationX + (shoggoth.getRNG().nextBoolean() ? 7 : -7), shoggoth.posY, locationZ + (shoggoth.getRNG().nextBoolean() ? 7 : -7), 0.7F);
			if(!world.isRemote) {
				BlockPos pos = new BlockPos(MathHelper.floor(locationX), MathHelper.floor(locationY), MathHelper.floor(locationZ));
				if(world.getBlockState(pos.down()).getBlock() == ACBlocks.shoggoth_ooze)
					pos = pos.down();
				new WorldGenShoggothMonolith().generate(world, shoggoth.getRNG(), pos);
			}
			shoggoth.isBuilding = false;
		}
	}

	@Override
	public void resetTask()
	{
		for(EntityShoggothBase shoggoth1 : shoggoths)
			shoggoth1.isBuilding = shoggoth1.isAssisting = false;
		shoggoths.clear();
		shoggoth.isBuilding = shoggoth.isAssisting = false;
		locationX = locationY = locationZ = 0;
	}

	private boolean allShoggothsNearby() {

		boolean temp = true;

		for(EntityShoggothBase shoggoth1 : shoggoths)
			if(shoggoth1.getDistance(shoggoth.posX, shoggoth.posY, shoggoth.posZ) > 8)
				temp = false;

		return temp;
	}

	@Nullable
	private Vec3d findPossibleLocation()
	{
		Random random = shoggoth.getRNG();
		BlockPos blockpos = new BlockPos(shoggoth.posX, shoggoth.getEntityBoundingBox().minY, shoggoth.posZ);

		for (int i = 0; i < 10; ++i)
		{
			BlockPos blockpos1 = blockpos.add(random.nextInt(20) - 10, random.nextInt(6) - 3, random.nextInt(20) - 10);

			if (world.isAirBlock(blockpos1.up()) && world.isAirBlock(blockpos1.up(1)) && world.getBlockState(blockpos1).getBlock().isReplaceable(world, blockpos1)
					&& world.getBlockState(blockpos1.down()) != ACBlocks.monolith_stone.getDefaultState()
					&& !world.isAirBlock(blockpos1.down()) && world.getBlockState(blockpos1.down()).isSideSolid(world, blockpos1.down(), EnumFacing.UP)
					&& world.getBlockState(blockpos1.down()) != ACBlocks.shoggoth_biomass.getDefaultState()) {

				for(BlockPos pos : BlockPos.getAllInBox(blockpos1.north().west().down(), blockpos1.south().east().up()))
					if(world.getBlockState(pos).getBlock() == ACBlocks.shoggoth_ooze)
						return new Vec3d(pos);

				return new Vec3d(blockpos1.getX(), blockpos1.getY(), blockpos1.getZ());
			}

		}

		return null;
	}
}
