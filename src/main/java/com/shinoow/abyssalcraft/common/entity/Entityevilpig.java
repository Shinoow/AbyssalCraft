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
		this.setSize(0.9F, 0.9F);
		this.getNavigator().setAvoidsWater(true);
		this.isImmuneToFire = true;
		double var2 = 0.35D;
		this.setHealth(15.0F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, var2, true));
		this.tasks.addTask(2, new EntityAIWander(this, var2));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

	}

	/**
	 * Returns true if the newer Entity AI code should be run
	 */
	public boolean isAIEnabled()
	{
		return true;
	}

	protected void updateAITasks()
	{
		super.updateAITasks();
	}

	/**
	 * returns true if all the conditions for steering the entity are met. For pigs, this is true if it is being ridden
	 * by a player and the player is holding a carrot-on-a-stick
	 */

	protected void entityInit()
	{
		super.entityInit();
		this.dataWatcher.addObject(16, Byte.valueOf((byte)0));
	}

	public String getEntityName()
	{
		return "Pig";
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound()
	{
		return "mob.pig.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.ghast.scream";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.pig.death";
	}

	/**
	 * Plays step sound at given x, y, z for the entity
	 */
	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		this.playSound("mob.pig.step", 0.15F, 1.0F);
	}

	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if(!this.worldObj.isRemote)
		{
		EntityDemonPig demonpig = new EntityDemonPig(this.worldObj);
		demonpig.copyLocationAndAnglesFrom(this);
		this.worldObj.removeEntity(this);
		demonpig.onSpawnWithEgg((IEntityLivingData)null);
		this.worldObj.spawnEntityInWorld(demonpig);
		}
	}

	protected void attackEntity(Entity par1Entity, float par2)
	{
		if (this.attackTime <= 0 && par2 < 2.0F && par1Entity.boundingBox.maxY > this.boundingBox.minY && par1Entity.boundingBox.minY < this.boundingBox.maxY)
		{
			this.attackTime = 20;
			this.attackEntityAsMob(par1Entity);
		}
		else if (par2 < 30.0F)
		{
			double var3 = par1Entity.posX - this.posX;
			double var5 = par1Entity.boundingBox.minY + (double)(par1Entity.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
			double var7 = par1Entity.posZ - this.posZ;

			if (this.attackTime == 0)
			{
				++this.field_70846_g;

				if (this.field_70846_g == 1)
				{
					this.attackTime = 60;
					this.func_70844_e(true);
				}
				else if (this.field_70846_g <= 4)
				{
					this.attackTime = 6;
				}
				else
				{
					this.attackTime = 100;
					this.field_70846_g = 0;
					this.func_70844_e(false);
				}

				if (this.field_70846_g > 1)
				{
					float var9 = MathHelper.sqrt_float(par2) * 0.5F;
					this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1009, (int)this.posX, (int)this.posY, (int)this.posZ, 0);

					for (int var10 = 0; var10 < 1; ++var10)
					{
						EntitySmallFireball var11 = new EntitySmallFireball(this.worldObj, this, var3 + this.rand.nextGaussian() * (double)var9, var5, var7 + this.rand.nextGaussian() * (double)var9);
						var11.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
						this.worldObj.spawnEntityInWorld(var11);
					}
				}
			}

			this.rotationYaw = (float)(Math.atan2(var7, var3) * 180.0D / Math.PI) - 90.0F;
			this.hasAttacked = true;
		}
	}

	public void func_70844_e(boolean par1)
	{
		byte var2 = this.dataWatcher.getWatchableObjectByte(16);

		if (par1)
		{
			var2 = (byte)(var2 | 1);
		}
		else
		{
			var2 &= -2;
		}

		this.dataWatcher.updateObject(16, Byte.valueOf(var2));
	}


	/**
	 * Drop 0-2 items of this living's type
	 */
	protected void dropFewItems(boolean par1, int par2)
	{
		int var3 = this.rand.nextInt(3) + 1 + this.rand.nextInt(1 + par2);

		for (int var4 = 0; var4 < var3; ++var4)
		{
			if (this.isBurning())
			{
				this.dropItem(Items.cooked_porkchop, 1);
			}
			else
			{
				this.dropItem(Items.porkchop, 1);
			}
		}
	}
}
