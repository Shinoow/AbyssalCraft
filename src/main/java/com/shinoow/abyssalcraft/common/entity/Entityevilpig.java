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
package com.shinoow.abyssalcraft.common.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.init.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class Entityevilpig extends EntityMob
{

	private int field_70846_g;



	public Entityevilpig(World par1World)
	{
		super(par1World);
		setSize(0.9F, 0.9F);
		getNavigator().setAvoidsWater(true);
		isImmuneToFire = true;
		double var2 = 0.35D;
		setHealth(15.0F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, var2, true));
		tasks.addTask(2, new EntityAIWander(this, var2));
		tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(4, new EntityAILookIdle(this));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	/**
	 * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
	 * by a player and the player is holding a carrot-on-a-stick
	 */

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	public String getEntityName()
	{
		return "Pig";
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.pig.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.ghast.scream";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.pig.death";
	}

	/**
	 * Plays step sound at given x, y, z for the entity
	 */
	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.pig.step", 0.15F, 1.0F);
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if(!worldObj.isRemote)
		{
			EntityDemonPig demonpig = new EntityDemonPig(worldObj);
			demonpig.copyLocationAndAnglesFrom(this);
			worldObj.removeEntity(this);
			demonpig.onSpawnWithEgg((IEntityLivingData)null);
			worldObj.spawnEntityInWorld(demonpig);
		}
	}

	@Override
	protected void attackEntity(Entity par1Entity, float par2)
	{
		if (attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > boundingBox.minY && par1Entity.boundingBox.minY < boundingBox.maxY)
		{
			attackTime = 20;
			attackEntityAsMob(par1Entity);
		}
		else if (par2 < 30.0F)
		{
			double var3 = par1Entity.posX - posX;
			double var5 = par1Entity.boundingBox.minY + par1Entity.height / 2.0F - (posY + height / 2.0F);
			double var7 = par1Entity.posZ - posZ;

			if (attackTime == 0)
			{
				++field_70846_g;

				if (field_70846_g == 1)
				{
					attackTime = 60;
					func_70844_e(true);
				}
				else if (field_70846_g <= 4)
				{
					attackTime = 6;
				}
				else
				{
					attackTime = 100;
					field_70846_g = 0;
					func_70844_e(false);
				}

				if (field_70846_g > 1)
				{
					float var9 = MathHelper.sqrt_float(par2) * 0.5F;
					worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)posX, (int)posY, (int)posZ, 0);

					for (int var10 = 0; var10 < 1; ++var10)
					{
						EntitySmallFireball var11 = new EntitySmallFireball(worldObj, this, var3 + rand.nextGaussian() * var9, var5, var7 + rand.nextGaussian() * var9);
						var11.posY = posY + height / 2.0F + 0.5D;
						worldObj.spawnEntityInWorld(var11);
					}
				}
			}

			rotationYaw = (float)(Math.atan2(var7, var3) * 180.0D / Math.PI) - 90.0F;
			hasAttacked = true;
		}
	}

	public void func_70844_e(boolean par1)
	{
		byte var2 = dataWatcher.getWatchableObjectByte(16);

		if (par1)
		{
			var2 = (byte)(var2 | 1);
		}
		else
		{
			var2 &= -2;
		}

		dataWatcher.updateObject(16, Byte.valueOf(var2));
	}


	/**
	 * Drop 0-2 items of this living's type
	 */
	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = rand.nextInt(3) + 1 + rand.nextInt(1 + par2);

		for (int var4 = 0; var4 < var3; ++var4)
		{
			if (isBurning())
			{
				dropItem(Items.cooked_porkchop, 1);
			}
			else
			{
				dropItem(Items.porkchop, 1);
			}
		}
	}
}
