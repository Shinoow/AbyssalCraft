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

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiSkeleton;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBow;
import net.minecraft.util.EnumHand;

public class EntityAIAttackRangedBowAnti extends EntityAIBase
{
	private final EntityAntiSkeleton entity;
	private final double moveSpeedAmp;
	private final int field_188501_c;
	private final float maxAttackDistance;
	private int field_188503_e = -1;
	private int field_188504_f;
	private boolean field_188505_g;
	private boolean field_188506_h;
	private int field_188507_i = -1;

	public EntityAIAttackRangedBowAnti(EntityAntiSkeleton p_i46805_1_, double p_i46805_2_, int p_i46805_4_, float p_i46805_5_)
	{
		entity = p_i46805_1_;
		moveSpeedAmp = p_i46805_2_;
		field_188501_c = p_i46805_4_;
		maxAttackDistance = p_i46805_5_ * p_i46805_5_;
		setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		return entity.getAttackTarget() == null ? false : func_188498_f();
	}

	protected boolean func_188498_f()
	{
		return entity.getHeldItemMainhand() != null && entity.getHeldItemMainhand().getItem() == Items.BOW;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting()
	{
		return (shouldExecute() || !entity.getNavigator().noPath()) && func_188498_f();
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		super.startExecuting();
		entity.setSwingingArms(true);
	}

	/**
	 * Resets the task
	 */
	@Override
	public void resetTask()
	{
		super.startExecuting();
		entity.setSwingingArms(false);
		field_188504_f = 0;
		field_188503_e = -1;
		entity.resetActiveHand();
	}

	/**
	 * Updates the task
	 */
	@Override
	public void updateTask()
	{
		EntityLivingBase entitylivingbase = entity.getAttackTarget();

		if (entitylivingbase != null)
		{
			double d0 = entity.getDistanceSq(entitylivingbase.posX, entitylivingbase.getEntityBoundingBox().minY, entitylivingbase.posZ);
			boolean flag = entity.getEntitySenses().canSee(entitylivingbase);
			boolean flag1 = field_188504_f > 0;

			if (flag != flag1)
				field_188504_f = 0;

			if (flag)
				++field_188504_f;
			else
				--field_188504_f;

			if (d0 <= maxAttackDistance && field_188504_f >= 20)
			{
				entity.getNavigator().clearPath();
				++field_188507_i;
			}
			else
			{
				entity.getNavigator().tryMoveToEntityLiving(entitylivingbase, moveSpeedAmp);
				field_188507_i = -1;
			}

			if (field_188507_i >= 20)
			{
				if (entity.getRNG().nextFloat() < 0.3D)
					field_188505_g = !field_188505_g;

				if (entity.getRNG().nextFloat() < 0.3D)
					field_188506_h = !field_188506_h;

				field_188507_i = 0;
			}

			if (field_188507_i > -1)
			{
				if (d0 > maxAttackDistance * 0.75F)
					field_188506_h = false;
				else if (d0 < maxAttackDistance * 0.25F)
					field_188506_h = true;

				entity.getMoveHelper().strafe(field_188506_h ? -0.5F : 0.5F, field_188505_g ? 0.5F : -0.5F);
				entity.faceEntity(entitylivingbase, 30.0F, 30.0F);
			} else
				entity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);

			if (entity.isHandActive())
			{
				if (!flag && field_188504_f < -60)
					entity.resetActiveHand();
				else if (flag)
				{
					int i = entity.getItemInUseMaxCount();

					if (i >= 20)
					{
						entity.resetActiveHand();
						entity.attackEntityWithRangedAttack(entitylivingbase, ItemBow.getArrowVelocity(i));
						field_188503_e = field_188501_c;
					}
				}
			}
			else if (--field_188503_e <= 0 && field_188504_f >= -60)
				entity.setActiveHand(EnumHand.MAIN_HAND);
		}
	}
}
