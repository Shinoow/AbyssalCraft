/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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