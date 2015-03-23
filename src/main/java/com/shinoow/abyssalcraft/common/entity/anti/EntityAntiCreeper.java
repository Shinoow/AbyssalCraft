/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.entity.anti;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAIAntiCreeperSwell;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityAntiCreeper extends EntityMob implements IAntiEntity {

	/**
	 * Time when this creeper was last in an active state (Messed up code here, probably causes creeper animation to go
	 * weird)
	 */
	private int lastActiveTime;
	/**
	 * The amount of time since the creeper was close enough to the player to ignite
	 */
	private int timeSinceIgnited;
	private int fuseTime = 30;
	/** Explosion radius for this creeper. */
	private int explosionRadius = 10;

	public EntityAntiCreeper(World par1World){
		super(par1World);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAntiCreeperSwell(this));
		tasks.addTask(3, new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
		tasks.addTask(4, new EntityAIAttackOnCollide(this, 1.0D, false));
		tasks.addTask(5, new EntityAIWander(this, 0.8D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		targetTasks.addTask(2, new EntityAIHurtByTarget(this, false));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
	}

	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public int getMaxSafePointTries()
	{
		return getAttackTarget() == null ? 3 : 3 + (int)(getHealth() - 1.0F);
	}

	@Override
	protected void fall(float par1)
	{
		super.fall(par1);
		timeSinceIgnited = (int)(timeSinceIgnited + par1 * 1.5F);

		if (timeSinceIgnited > fuseTime - 5)
			timeSinceIgnited = fuseTime - 5;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, Byte.valueOf((byte) - 1));
		dataWatcher.addObject(18, Byte.valueOf((byte)0));
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setShort("Fuse", (short)fuseTime);
		par1NBTTagCompound.setByte("ExplosionRadius", (byte)explosionRadius);
		par1NBTTagCompound.setBoolean("ignited", isIgnited());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.hasKey("Fuse", 99))
			fuseTime = par1NBTTagCompound.getShort("Fuse");

		if (par1NBTTagCompound.hasKey("ExplosionRadius", 99))
			explosionRadius = par1NBTTagCompound.getByte("ExplosionRadius");

		if (par1NBTTagCompound.getBoolean("ignited"))
			hasIgnited();
	}

	@Override
	public void onUpdate()
	{
		if (isEntityAlive())
		{
			lastActiveTime = timeSinceIgnited;

			if (isIgnited())
				setCreeperState(1);

			int i = getCreeperState();

			if (i > 0 && timeSinceIgnited == 0)
				playSound("creeper.primed", 1.0F, 0.5F);

			timeSinceIgnited += i;

			if (timeSinceIgnited < 0)
				timeSinceIgnited = 0;

			if (timeSinceIgnited >= fuseTime)
			{
				timeSinceIgnited = fuseTime;
				explode();
			}
		}

		super.onUpdate();
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.creeper.say";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.creeper.death";
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if (par1DamageSource.getEntity() instanceof EntityAntiSkeleton)
		{
			int i = Item.getIdFromItem(Items.record_13);
			int j = Item.getIdFromItem(Items.record_wait);
			int k = i + rand.nextInt(j - i + 1);
			dropItem(Item.getItemById(k), 1);
		}
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntityCreeper){
			boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		return true;
	}

	/**
	 * Params: (Float)Render tick. Returns the intensity of the creeper's flash when it is ignited.
	 */
	@SideOnly(Side.CLIENT)
	public float getCreeperFlashIntensity(float par1)
	{
		return (lastActiveTime + (timeSinceIgnited - lastActiveTime) * par1) / (fuseTime - 2);
	}

	@Override
	protected Item getDropItem()
	{
		return Items.gunpowder;
	}

	/**
	 * Returns the current state of creeper, -1 is idle, 1 is 'in fuse'
	 */
	public int getCreeperState()
	{
		return dataWatcher.getWatchableObjectByte(16);
	}

	/**
	 * Sets the state of creeper, -1 to idle and 1 to be 'in fuse'
	 */
	public void setCreeperState(int par1)
	{
		dataWatcher.updateObject(16, Byte.valueOf((byte)par1));
	}

	@Override
	protected boolean interact(EntityPlayer par1EntityPlayer)
	{
		ItemStack itemstack = par1EntityPlayer.inventory.getCurrentItem();

		if (itemstack != null && itemstack.getItem() == Items.flint_and_steel)
		{
			worldObj.playSoundEffect(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
			par1EntityPlayer.swingItem();

			if (!worldObj.isRemote)
			{
				hasIgnited();
				itemstack.damageItem(1, par1EntityPlayer);
				return true;
			}
		}

		return super.interact(par1EntityPlayer);
	}

	private void explode()
	{
		if (!worldObj.isRemote){
			boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, explosionRadius, flag);
			setDead();
		}
	}

	public boolean isIgnited()
	{
		return dataWatcher.getWatchableObjectByte(18) != 0;
	}

	public void hasIgnited()
	{
		dataWatcher.updateObject(18, Byte.valueOf((byte)1));
	}
}