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

import com.shinoow.abyssalcraft.common.entity.EntityShoggothBase;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAIShoggothAttackMelee extends EntityAIBase
{
	World world;
	protected EntityShoggothBase attacker;
	/** An amount of decrementing ticks that allows the entity to attack once the tick reaches 0. */
	protected int attackTick, rangedAttackTick;
	/** The speed with which the mob will approach the target */
	double speedTowardsTarget;
	/** When true, the mob will continue chasing its target, even if it can't find a path to them right now. */
	boolean longMemory;
	/** The PathEntity of our entity. */
	Path path;
	private int delayCounter;
	private double targetX;
	private double targetY;
	private double targetZ;
	protected final int attackInterval = 20;
	private int failedPathFindingPenalty = 0;
	private boolean canPenalize = false;

	public EntityAIShoggothAttackMelee(EntityShoggothBase creature, double speedIn, boolean useLongMemory)
	{
		attacker = creature;
		world = creature.world;
		speedTowardsTarget = speedIn;
		longMemory = useLongMemory;
		setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = attacker.getAttackTarget();

		if (entitylivingbase == null || !entitylivingbase.isEntityAlive())
			return false;
		else {
			if (canPenalize)
				if (--delayCounter <= 0)
				{
					path = attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
					delayCounter = 4 + attacker.getRNG().nextInt(7);
					return path != null;
				} else
					return true;
			path = attacker.getNavigator().getPathToEntityLiving(entitylivingbase);

			if (path != null)
				return true;
			else
				return getAttackReachSqr(entitylivingbase) >= attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
		}
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting()
	{
		EntityLivingBase entitylivingbase = attacker.getAttackTarget();

		if (entitylivingbase == null || !entitylivingbase.isEntityAlive())
			return false;
		else if (!longMemory)
			return !attacker.getNavigator().noPath();
		else if (!attacker.isWithinHomeDistanceFromPosition(new BlockPos(entitylivingbase)))
			return false;
		else
			return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).isSpectator() && !((EntityPlayer)entitylivingbase).isCreative();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		attacker.getNavigator().setPath(path, speedTowardsTarget);
		delayCounter = 0;
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	@Override
	public void resetTask()
	{
		EntityLivingBase entitylivingbase = attacker.getAttackTarget();

		if (entitylivingbase instanceof EntityPlayer && (((EntityPlayer)entitylivingbase).isSpectator() || ((EntityPlayer)entitylivingbase).isCreative()))
			attacker.setAttackTarget((EntityLivingBase)null);

		attacker.getNavigator().clearPath();
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void updateTask()
	{
		EntityLivingBase entitylivingbase = attacker.getAttackTarget();
		attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
		double d0 = attacker.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
		--delayCounter;

		if ((longMemory || attacker.getEntitySenses().canSee(entitylivingbase)) && delayCounter <= 0 && (targetX == 0.0D && targetY == 0.0D && targetZ == 0.0D || entitylivingbase.getDistanceSq(targetX, targetY, targetZ) >= 1.0D || attacker.getRNG().nextFloat() < 0.05F))
		{
			targetX = entitylivingbase.posX;
			targetY = entitylivingbase.getEntityBoundingBox().minY;
			targetZ = entitylivingbase.posZ;
			delayCounter = 4 + attacker.getRNG().nextInt(7);

			if (canPenalize)
			{
				delayCounter += failedPathFindingPenalty;
				if (attacker.getNavigator().getPath() != null)
				{
					net.minecraft.pathfinding.PathPoint finalPathPoint = attacker.getNavigator().getPath().getFinalPathPoint();
					if (finalPathPoint != null && entitylivingbase.getDistanceSq(finalPathPoint.x, finalPathPoint.y, finalPathPoint.z) < 1)
						failedPathFindingPenalty = 0;
					else
						attacker.sprayAcidAt(entitylivingbase);
				}
				else
				{
					//                    failedPathFindingPenalty += 10;
				}
			}

			if (d0 > 1024.0D)
				delayCounter += 10;
			else if (d0 > 256.0D)
				delayCounter += 5;

			if (!attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, speedTowardsTarget))
				delayCounter += 15;
		}

		attackTick = Math.max(attackTick - 1, 0);
		if((attacker.isBig() || ACConfig.hardcoreMode) && ACConfig.acidSpitFrequency > 0)
			rangedAttackTick = Math.max(rangedAttackTick - 1, 0);
		checkAndPerformAttack(entitylivingbase, d0);
	}

	protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_)
	{
		double d0 = getAttackReachSqr(p_190102_1_);

		if(attackTick <= 0)
			if (p_190102_2_ <= d0)
			{
				attackTick = 20;
				attacker.swingArm(EnumHand.MAIN_HAND);
				attacker.attackEntityAsMob(p_190102_1_);
			} else {
				attackTick = 20;
				if(attacker.motionX == 0 && attacker.motionZ == 0 && world.getGameRules().getBoolean("mobGriefing"))
					attacker.sprayAcid(p_190102_1_.posY >= attacker.posY);
			}
		if(rangedAttackTick <= 0 && (attacker.isBig() || ACConfig.hardcoreMode) && ACConfig.acidSpitFrequency > 0)
			if(attacker.getDistanceSq(p_190102_1_) > 32D) {
				rangedAttackTick = ACConfig.acidSpitFrequency;
				attacker.sprayAcidAt(p_190102_1_);
			}
	}

	protected double getAttackReachSqr(EntityLivingBase attackTarget)
	{
		return attacker.width * 1.5F * attacker.width * 1.5F + attackTarget.width;
	}
}
