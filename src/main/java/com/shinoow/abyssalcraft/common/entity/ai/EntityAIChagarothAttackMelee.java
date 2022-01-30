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

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIAttackMelee;

public class EntityAIChagarothAttackMelee extends EntityAIAttackMelee {

	public EntityAIChagarothAttackMelee(EntityCreature creature, double speedIn, boolean useLongMemory) {
		super(creature, speedIn, useLongMemory);

	}

	@Override
	protected double getAttackReachSqr(EntityLivingBase attackTarget)
	{
		return attacker.width * 1.5F * attacker.width * 1.5F + attackTarget.width;
	}
}
