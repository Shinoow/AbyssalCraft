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
package com.shinoow.abyssalcraft.common.entity;

import java.util.List;

import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.lib.ACSounds;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityImplosion extends Entity
{
	public EntityJzahar shootingEntity;

	public EntityImplosion(World worldIn)
	{
		super(worldIn);
		preventEntitySpawning = true;
		setSize(2.0F, 2.0F);
	}

	public EntityImplosion(World worldIn, EntityJzahar entity)
	{
		this(worldIn);
		shootingEntity = entity;
		copyLocationAndAnglesFrom(entity);
	}

	/**
	 * returns if this entity triggers Block.onEntityWalking on the blocks they walk on. used for spiders and wolves to
	 * prevent them from trampling crops
	 */
	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void entityInit(){}

	double speed = 0.05D;

	/**
	 * Called to update the entity's position/logic.
	 */
	@Override
	public void onUpdate()
	{
		prevPosX = posX;
		prevPosY = posY;
		prevPosZ = posZ;

		setPosition(posX, posY, posZ);

		float size = 64F;

		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(size));

		if (ticksExisted > 360)
		{
			world.playEvent(3000, getPosition(), 0);
			playSound(ACSounds.jzahar_blast, 5F, 1F);

			for(Entity entity : list) {
				double scale = (size - entity.getDistance(posX, posY, posZ))/size;

				Vec3d dir = new Vec3d(entity.posX - posX, entity.posY - posY, entity.posZ - posZ);
				dir = dir.normalize();
				if (entity.isEntityAlive() && !(entity instanceof IOmotholEntity))
					//					if (entity.getDistanceSq(this) <= 25D)
					//					{
					//						entity.hurtResistantTime = 0;
					//						entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 100F);
					//					}
					entity.addVelocity(dir.x * 2.5D * scale, 1.5D + rand.nextDouble(), dir.z * 2.5D * scale);
			}

			setDead();
		}

		if(ticksExisted == 1)
			playSound(ACSounds.jzahar_charge, 5F, 1F);

		for(Entity entity : list) {
			double scale = (size - entity.getDistance(posX, posY, posZ))/size;
			Vec3d dir = new Vec3d(entity.posX - posX, entity.posY - posY, entity.posZ - posZ);
			dir = dir.normalize();

			if (entity.isEntityAlive() && !(entity instanceof IOmotholEntity))
			{
				entity.addVelocity(dir.x * -ticksExisted * 0.0005D * scale, dir.y * -ticksExisted * 0.0005D * scale, dir.z * -ticksExisted * 0.0005D * scale);
				if (entity.getDistanceSq(this) <= 4D)
					entity.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 4F);
			}
		}

		speed += 0.0001;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound){}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound){}

	@Override
	public EnumPushReaction getPushReaction()
	{
		return EnumPushReaction.IGNORE;
	}

	/**
	 * Called when the entity is attacked.
	 */
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount)
	{
		return false;
	}

	/**
	 * Checks if the entity is in range to render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		return true;
	}
}
