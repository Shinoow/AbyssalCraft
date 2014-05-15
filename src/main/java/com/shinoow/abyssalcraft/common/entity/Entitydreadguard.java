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

import java.util.Calendar;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.core.api.entity.DreadMob;

public class Entitydreadguard extends DreadMob
{

	public Entitydreadguard(World par1World) 
	{
		super(par1World);
		this.setSize(0.72F, 2.34F);
		this.isImmuneToFire = true;
		this.tasks.addTask(0, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.3D, true));
		this.tasks.addTask(1, new EntityAIMoveTowardsRestriction(this, 0.3D));
		this.tasks.addTask(2, new EntityAIWander(this, 0.3D));
		this.tasks.addTask(3, new EntityAILookIdle(this));
		this.tasks.addTask(4, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));

	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		// Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(120.0D);
		// Follow Range - default 32.0D - min 0.0D - max 2048.0D
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0D);
		// Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		// Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
		Calendar calendar = this.worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
		{
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.5D);
		}
	}

	@Override
	public boolean canBreatheUnderwater() 
	{
		return true;

	}

	public boolean attackEntityAsMob(Entity par1Entity)
	{

		if (super.attackEntityAsMob(par1Entity))
		{
			if (par1Entity instanceof EntityLivingBase)
			{
				((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Dplague.id, 200));
			}
		}
		return hasAttacked;
	}

	public int getTotalArmorValue()
	{
		int var1 = super.getTotalArmorValue() + 2;

		if (var1 > 20)
		{
			var1 = 20;
		}

		return var1;
	}

	protected boolean isAIEnabled()
	{
		return true;
	}

	public String getEntityName()
	{
		return "Dreadguard";
	}

	protected String getLivingSound()
	{
		return "mob.blaze.breathe";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.blaze.hit";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "abyssalcraft:dreadguard.death";
		//		return "mob.blaze.death";
	}

	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		this.worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
	}

	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEFINED;
	}

	protected Item func_146068_u()
	{
		return AbyssalCraft.Dreadshard;

	}

	public void onLivingUpdate()
	{
		if (this.worldObj.isRemote)
		{

			this.setCurrentItemOrArmor(1, new ItemStack(AbyssalCraft.bootsD));
			this.setCurrentItemOrArmor(3, new ItemStack(AbyssalCraft.plateD));
			this.setCurrentItemOrArmor(4, new ItemStack(AbyssalCraft.helmetD));
			this.setCurrentItemOrArmor(2, new ItemStack(AbyssalCraft.legsD));
		}

		if(this.isDead)
		{
			Minecraft.getMinecraft().thePlayer.addStat(AbyssalCraft.killdreadguard, 1);
		}

		super.onLivingUpdate();
	}

}
