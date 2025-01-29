/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityCompassTentacle extends Entity {

	/**
	 * There it is!
	 */
	public final BlockPos THERE = new BlockPos(4, 54, 85);
	boolean hasLooked = false;

	public EntityCompassTentacle(World worldIn) {
		super(worldIn);

	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}

	@Override
	public void onEntityUpdate()
	{
		super.onEntityUpdate();
		if(!hasLooked)
			look();
		if(ticksExisted >= 120) {
			world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, posX, posY, posZ, 1.0D, 0.0D, 0.0D);
			setDead();
		}
	}

	/**
	 * Look at it!
	 */
	public void look() {
		double d0 = THERE.getX() - posX;
		double d2 = THERE.getZ() - posZ;
		float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
		rotationYaw += MathHelper.wrapDegrees(f - rotationYaw);
		hasLooked = true;
	}
}
