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

import com.shinoow.abyssalcraft.common.pathfinding.PatchedPathNavigateClimber;

import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.world.World;

/**
 * Base class for climbing mobs
 * @author shinoow
 *
 */
public class EntityClimbingMobBase extends EntityMobBase {

	private static final DataParameter<Byte> CLIMBING = EntityDataManager.<Byte>createKey(EntityClimbingMobBase.class, DataSerializers.BYTE);

	protected boolean takeFallDamage;

	public EntityClimbingMobBase(World worldIn) {
		super(worldIn);
	}

	@Override
	protected PathNavigate createNavigator(World worldIn)
	{
		return new PatchedPathNavigateClimber(this, worldIn);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(CLIMBING, (byte)0);
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!world.isRemote)
			setBesideClimbableBlock(collidedHorizontally);
	}

	@Override
	public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
	}

	/**
	 * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	 * setBesideClimableBlock.
	 */
	public boolean isBesideClimbableBlock()
	{
		return (dataManager.get(CLIMBING) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
	public void setBesideClimbableBlock(boolean par1)
	{
		byte b0 = dataManager.get(CLIMBING);

		if (par1)
			b0 = (byte)(b0 | 1);
		else
			b0 &= -2;

		dataManager.set(CLIMBING, b0);
	}

	@Override
	public void fall(float distance, float damageMultiplier) {
		if(takeFallDamage)
			super.fall(distance, damageMultiplier);
	}
}
