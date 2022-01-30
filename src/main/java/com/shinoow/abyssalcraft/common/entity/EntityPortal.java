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

import com.shinoow.abyssalcraft.api.dimension.DimensionData;
import com.shinoow.abyssalcraft.api.dimension.DimensionDataRegistry;
import com.shinoow.abyssalcraft.common.world.TeleporterAC;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPortal extends Entity {

	private static final DataParameter<Integer> DIMENSION = EntityDataManager.createKey(EntityPortal.class, DataSerializers.VARINT);

	private DimensionData data;
	private boolean triedFetch;

	public int clientTicks;

	public EntityPortal(World worldIn) {
		super(worldIn);
		setSize(1.0F, 2.0F);
	}

	public EntityPortal setDestination(int dim) {

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
		if(!world.isRemote && ticksExisted % 10 == 0 && data != null && data.getMobClass() != null) {
			boolean playerNearby = ACConfig.portalSpawnsNearPlayer ? world.getClosestPlayer(posX, posY, posZ, 32, false) != null : true;
			boolean nearbyMobs = world.getEntitiesWithinAABB(EntityAbyssalZombie.class, new AxisAlignedBB(getPosition()).grow(16)).size() < 10;

			if (world.provider.getDimension() != dataManager.get(DIMENSION) && world.getGameRules().getBoolean("doMobSpawning") && rand.nextInt(2000) < world.getDifficulty().getId()
					&& playerNearby && nearbyMobs)
			{
				int i = getPosition().getY();
				BlockPos blockpos;

				for (blockpos = getPosition(); !world.getBlockState(blockpos).isSideSolid(world, blockpos, EnumFacing.UP) && blockpos.getY() > 0; blockpos = blockpos.down())
					;

				if (i > 0 && !world.getBlockState(blockpos.up()).isNormalCube())
				{
					Entity entity = ItemMonsterPlacer.spawnCreature(world, EntityList.getKey(data.getMobClass()), blockpos.getX() + 0.5D, blockpos.getY() + 1.1D, blockpos.getZ() + 0.5D);

					if (entity != null)
						entity.timeUntilPortal = entity.getPortalCooldown();
				}
			}
		}
		if(!world.isRemote)
			for(Entity entity : world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox()))
				if(!(entity instanceof EntityPortal)) {
					if (!entity.isRiding() && !entity.isBeingRidden() && !world.isRemote && !entity.isDead && entity.isNonBoss())
						if(entity.timeUntilPortal > 0)
							entity.timeUntilPortal = entity instanceof EntityPlayerMP ? ACConfig.portalCooldown :  entity.getPortalCooldown();
						else {
							entity.timeUntilPortal = entity instanceof EntityPlayerMP ? ACConfig.portalCooldown :  entity.getPortalCooldown();
							TeleporterAC.changeDimension(entity, dataManager.get(DIMENSION));
						}
				} else entity.setDead();
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
