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

import com.shinoow.abyssalcraft.common.blocks.BlockDecorativeStatue;
import com.shinoow.abyssalcraft.common.blocks.tile.TileEntityStatue;
import com.shinoow.abyssalcraft.common.entity.EntityShoggothBase;
import com.shinoow.abyssalcraft.lib.ACSounds;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;

public class EntityAIWorship extends EntityAIBase
{
	/** The entity that is looking idle. */
	private final EntityLiving idleEntity;
	/** X offset to look at */
	private double lookX;
	/** Z offset to look at */
	private double lookZ;
	/** A decrementing tick that stops the entity from being idle once it reaches 0. */
	private int idleTime;
	private BlockPos statuePos;

	public EntityAIWorship(EntityLiving entitylivingIn)
	{
		idleEntity = entitylivingIn;
		setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		if(idleEntity.getRNG().nextFloat() < 0.01F) {
			MutableBlockPos pos = new MutableBlockPos();
			World world = idleEntity.getEntityWorld();
			BlockPos pos1 = idleEntity.getPosition();
			for(int x = -8; x < 9; x++)
				for(int y = -8; y < 9; y++)
					for(int z = -8; z < 9; z++){
						pos.setPos(pos1.getX() + x, pos1.getY() + y, pos1.getZ() + z);
						TileEntity te = world.getTileEntity(pos);
						if(te instanceof TileEntityStatue || world.getBlockState(pos).getBlock() instanceof BlockDecorativeStatue) {
							statuePos = pos.toImmutable();
							return true;
						}
					}
		}
		return false;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	@Override
	public boolean shouldContinueExecuting()
	{
		return idleTime >= 0;
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	@Override
	public void startExecuting()
	{
		double d0 = Math.PI * 2D * idleEntity.getRNG().nextDouble();
		lookX = Math.cos(d0);
		lookZ = Math.sin(d0);
		idleTime = 20 + idleEntity.getRNG().nextInt(20);
		if(statuePos != null) {
			idleEntity.getNavigator().tryMoveToXYZ(statuePos.getX() + (idleEntity.getRNG().nextBoolean() ? 1 : -1), idleEntity.posY, statuePos.getZ()+ (idleEntity.getRNG().nextBoolean() ? 1 : -1), 0.5F);
			if(!idleEntity.getEntityWorld().isRemote)
				idleEntity.playSound(ACSounds.remnant_priest_chant, 1.0F, idleEntity instanceof EntityShoggothBase ? idleEntity.isChild() ? 1.5F : 0.8F : (idleEntity.getRNG().nextFloat() - idleEntity.getRNG().nextFloat()) * 0.2F + 1.0F);
		}
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	@Override
	public void updateTask()
	{
		--idleTime;
		idleEntity.getLookHelper().setLookPosition(statuePos.getX(), statuePos.getY(), statuePos.getZ(), idleEntity.getHorizontalFaceSpeed(), idleEntity.getVerticalFaceSpeed());
	}
}
