/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2026 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.base;

import com.shinoow.abyssalcraft.api.entity.IEliteEntity;
import com.shinoow.abyssalcraft.common.pathfinding.PatchedPathNavigateGround;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

/**
 * Base class for mobs
 * @author shinoow
 *
 */
public class EntityMobBase extends EntityMob {

	public EntityMobBase(World worldIn) {
		super(worldIn);
	}

	@Override
	protected PathNavigate createNavigator(World worldIn)
	{
		return new PatchedPathNavigateGround(this, worldIn);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), getAmplifiedDamage());

		return super.attackEntityAsMob(par1Entity);
	}

	/**
	 * Amplified armor-piercing damage<br>
	 * Multiplied by a configured amplifier value
	 * <li>Base damage is 1.5
	 * <li>Elite damage is 3.0
	 * <li>Boss damage is 4.5
	 */
	private float getAmplifiedDamage() {
		float base = 1.5F;

		if(this instanceof IEliteEntity)
			base = 3.0F;
		if(!isNonBoss())
			base = 4.5F;

		return (float) (base * Math.max(ACConfig.damageAmpl, 1));
	}
}
