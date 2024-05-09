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
package com.shinoow.abyssalcraft.common.entity.ai;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCreeper;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityCreeper;

public class EntityAIAntiCreeperSwell extends EntityAIBase
{
	/** The anti-creeper that is swelling. */
	EntityAntiCreeper swellingCreeper;
	/**
	 * The anti-creeper's attack target. This is used for the changing of the anti-creeper's state.
	 */
	EntityLivingBase creeperAttackTarget;
	public EntityAIAntiCreeperSwell(EntityAntiCreeper par1EntityCreeper)
	{
		swellingCreeper = par1EntityCreeper;
		setMutexBits(1);
	}

	@Override
	public boolean shouldExecute()
	{
		EntityLivingBase entitylivingbase = swellingCreeper.getAttackTarget();

		if(entitylivingbase instanceof EntityCreeper) return false;

		return swellingCreeper.getCreeperState() > 0 || entitylivingbase != null && swellingCreeper.getDistanceSq(entitylivingbase) < 9.0D;
	}

	@Override
	public void startExecuting()
	{
		swellingCreeper.getNavigator().clearPath();
		creeperAttackTarget = swellingCreeper.getAttackTarget();
	}

	@Override
	public void resetTask()
	{
		creeperAttackTarget = null;
	}

	@Override
	public void updateTask()
	{
		if (creeperAttackTarget == null || swellingCreeper.getDistanceSq(creeperAttackTarget) > 49.0D || !swellingCreeper.getEntitySenses().canSee(creeperAttackTarget))
			swellingCreeper.setCreeperState(-1);
		else
			swellingCreeper.setCreeperState(1);
	}
}
