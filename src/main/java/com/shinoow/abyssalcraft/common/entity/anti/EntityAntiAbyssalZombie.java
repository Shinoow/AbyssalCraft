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

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;

public class EntityAntiAbyssalZombie extends EntityMob implements IAntiEntity {

	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);

	public EntityAntiAbyssalZombie(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityAntiZombie.class, 1.0D, true));
		tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(8, new EntityAILookIdle(this));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityAntiPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityAntiAbyssalZombie.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityAntiZombie.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityAntiGhoul.class, 8.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityAntiSkeleton.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityAntiZombie.class, 0, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		getDataWatcher().addObject(13, Byte.valueOf((byte)0));
	}

	@Override
	public boolean isChild()
	{
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	@Override
	protected float getSoundPitch()
	{
		float pitch;
		if(isChild())
			pitch = rand.nextFloat() - rand.nextFloat() * 0.2F + 1.3F;
		else
			pitch = 0.9F;
		return pitch;
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(boolean par1)
	{
		getDataWatcher().updateObject(12, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (worldObj != null && !worldObj.isRemote)
		{
			IAttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			attributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1)
				attributeinstance.applyModifier(babySpeedBoostModifier);
		}
	}

	/**
	 * Return whether this  abyssal zombie is a zombie.
	 */
	public boolean isZombie()
	{
		return getDataWatcher().getWatchableObjectByte(13) == 1;
	}

	/**
	 * Set whether this abyssal zombie is a zombie.
	 */
	public void setIsZombie(boolean par1)
	{
		getDataWatcher().updateObject(13, Byte.valueOf((byte)(par1 ? 1 : 0)));
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		return "mob.zombie.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		return "mob.zombie.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
	{
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return AbyssalCraft.antiCorflesh;

	}
	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
	@Override
	protected void dropRareDrop(int par1)
	{
		switch (rand.nextInt(3))
		{
		case 0:
			dropItem(Items.bone, 1);
			break;
		case 1:
			dropItem(AbyssalCraft.sword, 1);
			break;
		case 2:
			dropItem(AbyssalCraft.Cpearl, 1);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (isChild())
			par1NBTTagCompound.setBoolean("IsBaby", true);

		if (isZombie())
			par1NBTTagCompound.setBoolean("IsZombie", true);
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntityAbyssalZombie){
			boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);

		if(worldObj.difficultySetting == EnumDifficulty.NORMAL || worldObj.difficultySetting == EnumDifficulty.HARD
				&& par1EntityLivingBase instanceof EntityAntiZombie) {
			if (rand.nextBoolean())
				return;

			EntityAntiAbyssalZombie antiAbyaalZombie = new EntityAntiAbyssalZombie(worldObj);
			antiAbyaalZombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
			worldObj.removeEntity(par1EntityLivingBase);
			antiAbyaalZombie.onSpawnWithEgg((IEntityLivingData)null);
			antiAbyaalZombie.setIsZombie(true);

			if (par1EntityLivingBase.isChild())
				antiAbyaalZombie.setChild(true);

			worldObj.spawnEntityInWorld(antiAbyaalZombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);

		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onSpawnWithEgg(par1EntityLivingData);

		float f = worldObj.func_147462_b(posX, posY, posZ);
		setCanPickUpLoot(rand.nextFloat() < 0.55F * f);

		if (data == null)
			data = new EntityAntiAbyssalZombie.GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, worldObj.rand.nextFloat() < 0.05F, null);

		if (data instanceof EntityAntiAbyssalZombie.GroupData)
		{
			EntityAntiAbyssalZombie.GroupData groupdata = (EntityAntiAbyssalZombie.GroupData)data;

			if (groupdata.field_142046_b)
				setIsZombie(true);

			if (groupdata.field_142048_a)
				setChild(true);
		}
		return (IEntityLivingData)data;
	}

	class GroupData implements IEntityLivingData
	{
		public boolean field_142048_a;
		public boolean field_142046_b;
		private GroupData(boolean par2, boolean par3)
		{
			field_142048_a = false;
			field_142046_b = false;
			field_142048_a = par2;
			field_142046_b = par3;
		}

		GroupData(boolean par2, boolean par3, Object par4EntityZombieINNER1)
		{
			this(par2, par3);
		}
	}
}