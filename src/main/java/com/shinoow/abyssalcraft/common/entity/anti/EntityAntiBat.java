/**AbyssalCraft
 *Copyright 2012-2014 Shinoow
 *
 *Licensed under the Apache License, Version 2.0 (the "License");
 *you may not use this file except in compliance with the License.
 *You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing, software
 *distributed under the License is distributed on an "AS IS" BASIS,
 *WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *See the License for the specific language governing permissions and
 *limitations under the License.
 */
package com.shinoow.abyssalcraft.common.entity.anti;

import java.util.Calendar;

import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.core.api.entity.IAntiEntity;

public class EntityAntiBat extends EntityAmbientCreature implements IAntiEntity {

	/** Coordinates of where the bat spawned. */
	private ChunkCoordinates spawnPosition;
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
		dataWatcher.addObject(16, new Byte((byte)0));
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
	protected String getLivingSound()
	{
		return getIsBatHanging() && rand.nextInt(4) != 0 ? null : "mob.bat.idle";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.bat.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.bat.death";
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
			boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
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
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(12.0D);
	}

	public boolean getIsBatHanging()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	public void setIsBatHanging(boolean par1)
	{
		byte b0 = dataWatcher.getWatchableObjectByte(16);

		if (par1)
			dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 | 1)));
		else
			dataWatcher.updateObject(16, Byte.valueOf((byte)(b0 & -2)));
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
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

		if (getIsBatHanging())
		{
			if (!worldObj.getBlock(MathHelper.floor_double(posX), (int)posY + 1, MathHelper.floor_double(posZ)).isNormalCube())
			{
				setIsBatHanging(false);
				worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, (int)posX, (int)posY, (int)posZ, 0);
			}
			else
			{
				if (rand.nextInt(200) == 0)
					rotationYawHead = rand.nextInt(360);

				if (worldObj.getClosestPlayerToEntity(this, 4.0D) != null)
				{
					setIsBatHanging(false);
					worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1015, (int)posX, (int)posY, (int)posZ, 0);
				}
			}
		}
		else
		{
			if (spawnPosition != null && (!worldObj.isAirBlock(spawnPosition.posX, spawnPosition.posY, spawnPosition.posZ) || spawnPosition.posY < 1))
				spawnPosition = null;

			if (spawnPosition == null || rand.nextInt(30) == 0 || spawnPosition.getDistanceSquared((int)posX, (int)posY, (int)posZ) < 4.0F)
				spawnPosition = new ChunkCoordinates((int)posX + rand.nextInt(7) - rand.nextInt(7), (int)posY + rand.nextInt(6) - 2, (int)posZ + rand.nextInt(7) - rand.nextInt(7));

			double d0 = spawnPosition.posX + 0.5D - posX;
			double d1 = spawnPosition.posY + 0.1D - posY;
			double d2 = spawnPosition.posZ + 0.5D - posZ;
			motionX += (Math.signum(d0) * 0.5D - motionX) * 0.10000000149011612D;
			motionY += (Math.signum(d1) * 0.699999988079071D - motionY) * 0.10000000149011612D;
			motionZ += (Math.signum(d2) * 0.5D - motionZ) * 0.10000000149011612D;
			float f = (float)(Math.atan2(motionZ, motionX) * 180.0D / Math.PI) - 90.0F;
			float f1 = MathHelper.wrapAngleTo180_float(f - rotationYaw);
			moveForward = 0.5F;
			rotationYaw += f1;

			if (rand.nextInt(100) == 0 && worldObj.getBlock(MathHelper.floor_double(posX), (int)posY + 1, MathHelper.floor_double(posZ)).isNormalCube())
				setIsBatHanging(true);
		}
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	protected void fall(float par1) {}

	@Override
	protected void updateFallState(double par1, boolean par3) {}

	@Override
	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (isEntityInvulnerable())
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
		dataWatcher.updateObject(16, Byte.valueOf(par1NBTTagCompound.getByte("BatFlags")));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByte("BatFlags", dataWatcher.getWatchableObjectByte(16));
	}



	@Override
	public boolean getCanSpawnHere()
	{
		int i = MathHelper.floor_double(boundingBox.minY);

		if (i >= 63)
			return false;
		else
		{
			int j = MathHelper.floor_double(posX);
			int k = MathHelper.floor_double(posZ);
			int l = worldObj.getBlockLightValue(j, i, k);
			byte b0 = 4;
			Calendar calendar = worldObj.getCurrentDate();

			if ((calendar.get(2) + 1 != 10 || calendar.get(5) < 20) && (calendar.get(2) + 1 != 11 || calendar.get(5) > 3))
			{
				if (rand.nextBoolean())
					return false;
			} else
				b0 = 7;

			return l > rand.nextInt(b0) ? false : super.getCanSpawnHere();
		}
	}
}