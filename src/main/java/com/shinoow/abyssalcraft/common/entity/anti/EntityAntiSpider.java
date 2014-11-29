/**
 * AbyssalCraft
 * Copyright 2012-2014 Shinoow
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

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.potion.*;
import net.minecraft.util.MathHelper;
import net.minecraft.world.*;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;

public class EntityAntiSpider extends EntityMob implements IAntiEntity {

	public EntityAntiSpider(World par1World)
	{
		super(par1World);
		setSize(1.4F, 0.9F);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(16, new Byte((byte)0));
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (!worldObj.isRemote)
			setBesideClimbableBlock(isCollidedHorizontally);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(32.0D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.800000011920929D);
	}

	@Override
	protected Entity findPlayerToAttack()
	{
		float f = getBrightness(1.0F);

		if (f < 0.5F)
		{
			double d0 = 16.0D;
			return worldObj.getClosestVulnerablePlayerToEntity(this, d0);
		} else
			return null;
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.spider.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.spider.say";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.spider.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4Block)
	{
		playSound("mob.spider.step", 0.15F, 1.0F);
	}

	@Override
	protected void attackEntity(Entity par1Entity, float par2)
	{
		float f1 = getBrightness(1.0F);

		if (f1 > 0.5F && rand.nextInt(100) == 0)
			entityToAttack = null;
		else if (par2 > 2.0F && par2 < 6.0F && rand.nextInt(10) == 0)
		{
			if (onGround)
			{
				double d0 = par1Entity.posX - posX;
				double d1 = par1Entity.posZ - posZ;
				float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
				motionX = d0 / f2 * 0.5D * 0.800000011920929D + motionX * 0.20000000298023224D;
				motionZ = d1 / f2 * 0.5D * 0.800000011920929D + motionZ * 0.20000000298023224D;
				motionY = 0.4000000059604645D;
			}
		} else
			super.attackEntity(par1Entity, par2);
	}

	@Override
	protected Item getDropItem()
	{
		return Items.string;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		super.dropFewItems(par1, par2);

		if (par1 && (rand.nextInt(3) == 0 || rand.nextInt(1 + par2) > 0))
			dropItem(AbyssalCraft.antiSpider_eye, 1);
	}

	@Override
	public boolean isOnLadder()
	{
		return isBesideClimbableBlock();
	}

	@Override
	public void setInWeb() {}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.ARTHROPOD;
	}

	@Override
	public boolean isPotionApplicable(PotionEffect par1PotionEffect)
	{
		return par1PotionEffect.getPotionID() == Potion.poison.id ? false : super.isPotionApplicable(par1PotionEffect);
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntitySpider){
			boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	/**
	 * Returns true if the WatchableObject (Byte) is 0x01 otherwise returns false. The WatchableObject is updated using
	 * setBesideClimableBlock.
	 */
	public boolean isBesideClimbableBlock()
	{
		return (dataWatcher.getWatchableObjectByte(16) & 1) != 0;
	}

	/**
	 * Updates the WatchableObject (Byte) created in entityInit(), setting it to 0x01 if par1 is true or 0x00 if it is
	 * false.
	 */
	public void setBesideClimbableBlock(boolean par1)
	{
		byte b0 = dataWatcher.getWatchableObjectByte(16);

		if (par1)
			b0 = (byte)(b0 | 1);
		else
			b0 &= -2;

		dataWatcher.updateObject(16, Byte.valueOf(b0));
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		Object p_110161_1_1 = super.onSpawnWithEgg(par1EntityLivingData);

		if (worldObj.rand.nextInt(100) == 0)
		{
			EntityAntiSkeleton entityskeleton = new EntityAntiSkeleton(worldObj);
			entityskeleton.setLocationAndAngles(posX, posY, posZ, rotationYaw, 0.0F);
			entityskeleton.onSpawnWithEgg((IEntityLivingData)null);
			worldObj.spawnEntityInWorld(entityskeleton);
			entityskeleton.mountEntity(this);
		}

		if (p_110161_1_1 == null)
		{
			p_110161_1_1 = new EntityAntiSpider.GroupData();

			if (worldObj.difficultySetting == EnumDifficulty.HARD && worldObj.rand.nextFloat() < 0.1F * worldObj.func_147462_b(posX, posY, posZ))
				((EntityAntiSpider.GroupData)p_110161_1_1).func_111104_a(worldObj.rand);
		}

		if (p_110161_1_1 instanceof EntityAntiSpider.GroupData)
		{
			int i = ((EntityAntiSpider.GroupData)p_110161_1_1).field_111105_a;

			if (i > 0 && Potion.potionTypes[i] != null)
				addPotionEffect(new PotionEffect(i, Integer.MAX_VALUE));
		}

		return (IEntityLivingData)p_110161_1_1;
	}

	public static class GroupData implements IEntityLivingData
	{
		public int field_111105_a;

		public void func_111104_a(Random par1Random)
		{
			int i = par1Random.nextInt(5);

			if (i <= 1)
				field_111105_a = Potion.moveSpeed.id;
			else if (i <= 2)
				field_111105_a = Potion.damageBoost.id;
			else if (i <= 3)
				field_111105_a = Potion.regeneration.id;
			else if (i <= 4)
				field_111105_a = Potion.invisibility.id;
		}
	}

}