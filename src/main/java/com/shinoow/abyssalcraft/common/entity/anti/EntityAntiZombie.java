/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.anti;

import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class EntityAntiZombie extends EntityMob implements IAntiEntity {

	protected static final IAttribute spawnReinforcementsAttribute = new RangedAttribute("zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D).setDescription("Spawn Reinforcements Chance");
	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
	private final EntityAIBreakDoor breakDoors = new EntityAIBreakDoor(this);
	private boolean canBreak = false;
	private float width = -1.0F;
	private float height;

	public EntityAntiZombie(World par1World)
	{
		super(par1World);
		getNavigator().setBreakDoors(true);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(6, new EntityAIMoveThroughVillage(this, 1.0D, false));
		tasks.addTask(7, new EntityAIWander(this, 1.0D));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(8, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		setSize(0.6F, 1.8F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0D);
		getAttributeMap().registerAttribute(spawnReinforcementsAttribute).setBaseValue(rand.nextDouble() * ForgeModContainer.zombieSummonBaseChance);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		getDataWatcher().addObject(13, Byte.valueOf((byte)0));
		getDataWatcher().addObject(14, Byte.valueOf((byte)0));
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	public boolean canBearkDoors()
	{
		return canBreak;
	}

	public void func_146070_a(boolean par1)
	{
		if (canBreak != par1)
		{
			canBreak = par1;

			if (par1)
				tasks.addTask(1, breakDoors);
			else
				tasks.removeTask(breakDoors);
		}
	}

	@Override
	public boolean isChild()
	{
		return getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	@Override
	protected int getExperiencePoints(EntityPlayer par1EntityPlayer)
	{
		if (this.isChild())
			experienceValue = (int)(experienceValue * 2.5F);

		return super.getExperiencePoints(par1EntityPlayer);
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(boolean par1)
	{
		getDataWatcher().updateObject(12, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (worldObj != null && !worldObj.isRemote)
		{
			IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.movementSpeed);
			iattributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1)
				iattributeinstance.applyModifier(babySpeedBoostModifier);
		}

		this.isChild(par1);
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		if (!super.attackEntityFrom(par1DamageSource, par2))
			return false;
		else
		{
			EntityLivingBase entitylivingbase = getAttackTarget();

			if (entitylivingbase == null && getEntityToAttack() instanceof EntityLivingBase)
				entitylivingbase = (EntityLivingBase)getEntityToAttack();

			if (entitylivingbase == null && par1DamageSource.getEntity() instanceof EntityLivingBase)
				entitylivingbase = (EntityLivingBase)par1DamageSource.getEntity();

			return true;
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag)
		{
			int i = worldObj.difficultySetting.getDifficultyId();

			if (getHeldItem() == null && isBurning() && rand.nextFloat() < i * 0.3F)
				par1Entity.setFire(2 * i);
		}

		return flag;
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.zombie.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.zombie.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4Block)
	{
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return AbyssalCraft.antiFlesh;
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
			dropItem(Items.iron_ingot, 1);
			break;
		case 1:
			dropItem(Items.carrot, 1);
			break;
		case 2:
			dropItem(Items.potato, 1);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (this.isChild())
			par1NBTTagCompound.setBoolean("IsBaby", true);

		par1NBTTagCompound.setBoolean("CanBreakDoors", canBearkDoors());
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.getBoolean("IsBaby"))
			setChild(true);

		func_146070_a(par1NBTTagCompound.getBoolean("CanBreakDoors"));
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntityZombie){
			boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		Object entity = super.onSpawnWithEgg(par1EntityLivingData);
		float f = worldObj.func_147462_b(posX, posY, posZ);
		setCanPickUpLoot(rand.nextFloat() < 0.55F * f);

		if (entity == null)
			entity = new EntityAntiZombie.GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, worldObj.rand.nextFloat() < 0.05F, null);

		if (entity instanceof EntityAntiZombie.GroupData)
		{
			EntityAntiZombie.GroupData groupdata = (EntityAntiZombie.GroupData)entity;

			if (groupdata.field_142048_a)
				setChild(true);
		}

		func_146070_a(rand.nextFloat() < f * 0.1F);

		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", rand.nextDouble() * 0.05000000074505806D, 0));
		double d0 = rand.nextDouble() * 1.5D * worldObj.func_147462_b(posX, posY, posZ);

		if (d0 > 1.0D)
			getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));

		if (rand.nextFloat() < f * 0.05F)
		{
			getEntityAttribute(spawnReinforcementsAttribute).applyModifier(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 0.25D + 0.5D, 0));
			getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier("Leader zombie bonus", rand.nextDouble() * 3.0D + 1.0D, 2));
			func_146070_a(true);
		}

		return (IEntityLivingData)entity;
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void handleHealthUpdate(byte par1)
	{
		if (par1 == 16)
			worldObj.playSound(posX + 0.5D, posY + 0.5D, posZ + 0.5D, "mob.zombie.remedy", 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
		else
			super.handleHealthUpdate(par1);
	}

	public void isChild(boolean par1)
	{
		modifySize(par1 ? 0.5F : 1.0F);
	}

	@Override
	protected final void setSize(float par1, float par2)
	{
		boolean flag = width > 0.0F && height > 0.0F;
		width = par1;
		height = par2;

		if (!flag)
			modifySize(1.0F);
	}

	protected final void modifySize(float par1)
	{
		super.setSize(width * par1, height * par1);
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
