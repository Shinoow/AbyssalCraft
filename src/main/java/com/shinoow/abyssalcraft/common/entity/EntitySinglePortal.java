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

import com.shinoow.abyssalcraft.api.dimension.DimensionData;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.common.world.TeleporterSinglePortal;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.util.ScheduledProcess;
import com.shinoow.abyssalcraft.lib.util.Scheduler;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySinglePortal extends Entity {

	private static final DataParameter<Integer> DIMENSION = EntityDataManager.createKey(EntitySinglePortal.class, DataSerializers.VARINT);

	private DimensionData data;
	private boolean triedFetch;

	public int clientTicks;

	public EntitySinglePortal(World worldIn) {
		super(worldIn);
		setSize(1.0F, 2.0F);
	}

	public EntitySinglePortal setDestination(int dim) {

		dataManager.set(DIMENSION, dim);
		data = DimensionDataRegistry.instance().getDataForDim(dim);
		return this;
	}

	public DimensionData getDimensionData() {

		if(data == null && !triedFetch) {
			data = DimensionDataRegistry.instance().getDataForDim(dataManager.get(DIMENSION));
			triedFetch = true;
		}

		return data;
	}

	@Override
	protected void entityInit()
	{
		dataManager.register(DIMENSION, 0);
	}

	@Override
	public void onUpdate() {
		if(!world.isRemote) {
			Entity toTeleport = null;
			for(Entity entity : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()))
				if(!(entity instanceof EntitySinglePortal)) {
					if (!entity.isRiding() && !entity.isBeingRidden() && !entity.isDead && entity.isNonBoss()) {
						toTeleport = entity;
						break;
					}
				} else entity.setDead();
			if(toTeleport != null)
				if(toTeleport.timeUntilPortal > 0)
					toTeleport.timeUntilPortal = toTeleport instanceof EntityPlayerMP ? ACConfig.portalCooldown :  toTeleport.getPortalCooldown();
				else {
					toTeleport.timeUntilPortal = toTeleport instanceof EntityPlayerMP ? ACConfig.portalCooldown :  toTeleport.getPortalCooldown();

					Entity toTeleport1 = toTeleport;

					// Delay the teleportation by a single tick, so the game has enough time to kill the portal
					Scheduler.schedule(new ScheduledProcess(1) {

						@Override
						public void execute() {
							TeleporterSinglePortal.changeDimension(toTeleport1, dataManager.get(DIMENSION));
						}
					});

					setDead();
				}
		}

	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

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

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		dataManager.set(DIMENSION, compound.getInteger("Dimension"));
		data = DimensionDataRegistry.instance().getDataForDim(dataManager.get(DIMENSION));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("Dimension", dataManager.get(DIMENSION));
	}
}
