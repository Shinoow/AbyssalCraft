/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.anti;

import java.util.Calendar;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAmbientCreature;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityAntiBat extends EntityAmbientCreature implements IAntiEntity {

	private static final DataParameter<Byte> HANGING = EntityDataManager.<Byte>createKey(EntityAntiBat.class, DataSerializers.BYTE);
	/** Coordinates of where the bat spawned. */
	private BlockPos spawnPosition;
	public EntityAntiBat(World par1World)
	{
		super(par1World);
		setSize(0.5F, 0.9F);
		setIsBatHanging(true);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(HANGING, new Byte((byte)0));
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.1F;
	}

	@Override
	protected float getSoundPitch()
	{
		return super.getSoundPitch() * 0.95F;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return getIsBatHanging() && rand.nextInt(4) != 0 ? null : SoundEvents.ENTITY_BAT_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_BAT_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_BAT_DEATH;
	}

	@Override
	public boolean canBePushed()
	{
		return false;
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntityBat){
			boolean flag = worldObj.getGameRules().getBoolean("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
	}

	@Override
	protected void collideWithNearbyEntities() {}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
	}

	public boolean getIsBatHanging()
	{
		return (dataManager.get(HANGING) & 1) != 0;
	}

	public void setIsBatHanging(boolean par1)
	{
		byte b0 = dataManager.get(HANGING).byteValue();

		if (par1)
			dataManager.set(HANGING, Byte.valueOf((byte)(b0 | 1)));
		else
			dataManager.set(HANGING, Byte.valueOf((byte)(b0 & -2)));
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (getIsBatHanging())
		{
			motionX = motionY = motionZ = 0.0D;
			posY = MathHelper.floor_double(posY) + 1.0D - height;
		} else
			motionY *= 0.6000000238418579D;
	}

	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
		BlockPos blockpos = new BlockPos(this);
		BlockPos blockpos1 = blockpos.up();

		if (getIsBatHanging())
		{
			if (!worldObj.getBlockState(blockpos1).isNormalCube())
			{
				setIsBatHanging(false);
				worldObj.playEvent((EntityPlayer)null, 1025, blockpos, 0);
			}
			else
			{
				if (rand.nextInt(200) == 0)
					rotationYawHead = rand.nextInt(360);

				if (worldObj.getClosestPlayerToEntity(this, 4.0D) != null)
				{
					setIsBatHanging(false);
					worldObj.playEvent((EntityPlayer)null, 1025, blockpos, 0);
				}
			}
		}
		else
		{
			if (spawnPosition != null && (!worldObj.isAirBlock(spawnPosition) || spawnPosition.getY() < 1))
				spawnPosition = null;

			if (spawnPosition == null || rand.nextInt(30) == 0 || spawnPosition.distanceSq((int)posX, (int)posY, (int)posZ) < 4.0D)
				spawnPosition = new BlockPos((int)posX + rand.nextInt(7) - rand.nextInt(7), (int)posY + rand.nextInt(6) - 2, (int)posZ + rand.nextInt(7) - rand.nextInt(7));

			double d0 = spawnPosition.getX() + 0.5D - posX;
			double d1 = spawnPosition.getY() + 0.1D - posY;
			double d2 = spawnPosition.getZ() + 0.5D - posZ;
			motionX += (Math.signum(d0) * 0.5D - motionX) * 0.10000000149011612D;
			motionY += (Math.signum(d1) * 0.699999988079071D - motionY) * 0.10000000149011612D;
			motionZ += (Math.signum(d2) * 0.5D - motionZ) * 0.10000000149011612D;
			float f = (float)(MathHelper.atan2(motionZ, motionX) * 180.0D / Math.PI) - 90.0F;
			float f1 = MathHelper.wrapDegrees(f - rotationYaw);
			moveForward = 0.5F;
			rotationYaw += f1;

			if (rand.nextInt(100) == 0 && worldObj.getBlockState(blockpos1).isNormalCube())
				setIsBatHanging(true);
		}
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public void fall(float par1, float par2) {}

	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState blockIn, BlockPos pos) {}

	@Override
	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (isEntityInvulnerable(par1DamageSource))
			return false;
		else
		{
			if (!worldObj.isRemote && getIsBatHanging())
				setIsBatHanging(false);

			return super.attackEntityFrom(par1DamageSource, par2);
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);
		dataManager.set(HANGING, Byte.valueOf(par1NBTTagCompound.getByte("BatFlags")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("BatFlags", dataManager.get(HANGING).byteValue());
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_ANTI_BAT;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		BlockPos blockpos = new BlockPos(posX, getEntityBoundingBox().minY, posZ);

		if (blockpos.getY() >= worldObj.getSeaLevel())
			return false;
		else
		{
			int i = worldObj.getLightFromNeighbors(blockpos);
			int j = 4;

			if (isDateAroundHalloween(worldObj.getCurrentDate()))
				j = 7;
			else if (rand.nextBoolean())
				return false;

			return i > rand.nextInt(j) ? false : super.getCanSpawnHere();
		}
	}

	private boolean isDateAroundHalloween(Calendar p_175569_1_)
	{
		return p_175569_1_.get(2) + 1 == 10 && p_175569_1_.get(5) >= 20 || p_175569_1_.get(2) + 1 == 11 && p_175569_1_.get(5) <= 3;
	}

	@Override
	public float getEyeHeight()
	{
		return height / 2.0F;
	}
}