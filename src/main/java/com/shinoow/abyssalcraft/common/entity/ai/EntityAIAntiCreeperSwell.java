/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;

import com.shinoow.abyssalcraft.common.entity.anti.EntityAntiCreeper;

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
		return swellingCreeper.getCreeperState() > 0 || entitylivingbase != null && swellingCreeper.getDistanceSqToEntity(entitylivingbase) < 9.0D;
	}

	@Override
	public void startExecuting()
	{
		swellingCreeper.getNavigator().clearPathEntity();
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
		if (creeperAttackTarget == null)
			swellingCreeper.setCreeperState(-1);
		else if (swellingCreeper.getDistanceSqToEntity(creeperAttackTarget) > 49.0D)
			swellingCreeper.setCreeperState(-1);
		else if (!swellingCreeper.getEntitySenses().canSee(creeperAttackTarget))
			swellingCreeper.setCreeperState(-1);
		else
			swellingCreeper.setCreeperState(1);
	}
}
