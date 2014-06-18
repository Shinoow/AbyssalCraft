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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.core.api.entity.CoraliumMob;

public class EntityDepthsghoul extends CoraliumMob
{

	private EntityAINearestAttackableTarget player = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);

	public EntityDepthsghoul(World par1World)
	{
		super(par1World);
		setSize(1.5F, 3.0F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, false));
		tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(3, new EntityAIWander(this, 0.35D));
		tasks.addTask(4, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIFleeSun(this, 0.35D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsghoul.class, 6.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityDepthsZombie.class, 6.0F));
		tasks.addTask(9, new EntityAIWatchClosest(this, EntityZombie.class, 6.0F));
		tasks.addTask(10, new EntityAIWatchClosest(this, EntitySkeleton.class, 6.0F));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, player);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		// Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
		// Follow Range - default 32.0D - min 0.0D - max 2048.0D
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		// Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.3D);
		// Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
		Calendar calendar = worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
		{
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.5D);
		}

		switch(getGhoulType())
		{
		case 0:
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.5D);
			}
			break;
		case 1:
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(9.0D);
			}
			break;
		case 2:
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(11.5D);
			}
			break;
		case 3:
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(12.0D);
			}
		}
	}

	@Override
	public String getCommandSenderName()
	{
		String entityname = StatCollector.translateToLocal("entity.abyssalcraft.depthsghoul.name");
		switch (getGhoulType())
		{
		case 0:
			entityname = StatCollector.translateToLocal("entity.abyssalcraft.depthsghoul.name");
			break;
		case 1:
			entityname = "Pete";
			break;
		case 2:
			entityname = "Mr. Wilson";
			break;
		case 3:
			entityname = "Dr. Orange";
		}
		return entityname;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(13, Byte.valueOf((byte)0));
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	public int getGhoulType()
	{
		return dataWatcher.getWatchableObjectByte(13);
	}

	public void setGhoulType(int par1)
	{
		dataWatcher.updateObject(13, Byte.valueOf((byte)par1));
	}

	@Override
	public void onLivingUpdate()
	{
		if (worldObj.isDaytime() && !worldObj.isRemote && !isChild() && worldObj.provider.dimensionId != AbyssalCraft.configDimId1)
		{
			float var1 = getBrightness(1.0F);

			if (var1 > 0.5F && rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && worldObj.canBlockSeeTheSky(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)))
			{
				boolean var2 = true;
				ItemStack var3 = getEquipmentInSlot(4);

				if (var3 != null)
				{
					if (var3.isItemStackDamageable())
					{
						var3.setItemDamage(var3.getItemDamageForDisplay() + rand.nextInt(2));

						if (var3.getItemDamageForDisplay() >= var3.getMaxDamage())
						{
							renderBrokenItemStack(var3);
							setCurrentItemOrArmor(4, (ItemStack)null);
						}
					}

					var2 = false;
				}

				if (var2)
				{
					setFire(8);
				}

			}
		}

		super.onLivingUpdate();
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if (par1DamageSource.getEntity() instanceof EntityPlayer && getGhoulType() == 0)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killghoul,1);
		}
		if (par1DamageSource.getEntity() instanceof EntityPlayer && getGhoulType() == 1)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killPete,1);
		}
		if (par1DamageSource.getEntity() instanceof EntityPlayer && getGhoulType() == 2)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killWilson,1);
		}
		if (par1DamageSource.getEntity() instanceof EntityPlayer && getGhoulType() == 3)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killOrange,1);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if (super.attackEntityAsMob(par1Entity))
		{
			if (par1Entity instanceof EntityLivingBase)
			{
				if(worldObj.provider.dimensionId == AbyssalCraft.configDimId1)
				{
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 200));
				}
			}
		}

		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag && getHeldItem() == null && isBurning() && rand.nextFloat() < worldObj.difficultySetting.getDifficultyId() * 0.3F)
		{
			par1Entity.setFire(2 * worldObj.difficultySetting.getDifficultyId());
		}

		return flag;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound()
	{
		String entitysound = "mob.zombie.say";
		switch (getGhoulType())
		{
		case 0:
			entitysound = "mob.zombie.say"; //abyssalcraft:ghoul.normal.idle
			break;
		case 1:
			entitysound = "abyssalcraft:ghoul.pete.idle";
			break;
		case 2:
			entitysound = "mob.zombie.say"; //abyssalcraft:ghoul.wilson.idle
			break;
		case 3:
			entitysound = "mob.zombie.say"; //abyssalcraft:ghoul.orange.idle
		}
		return entitysound;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound()
	{
		String entitysound = "mob.zombie.hurt";
		switch (getGhoulType())
		{
		case 0:
			entitysound = "mob.zombie.hurt"; //abyssalcraft:ghoul.normal.hit
			break;
		case 1:
			entitysound = "abyssalcraft:ghoul.pete.hit";
			break;
		case 2:
			entitysound = "mob.zombie.hurt"; //abyssalcraft:ghoul.wilson.hit
			break;
		case 3:
			entitysound = "mob.zombie.hurt"; //abyssalcraft:ghoul.orange.hit
		}
		return entitysound;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound()
	{
		String entitysound = "mob.zombie.death";
		switch (getGhoulType())
		{
		case 0:
			entitysound = "mob.zombie.death"; //abyssalcraft:ghoul.normal.death
			break;
		case 1:
			entitysound = "abyssalcraft:ghoul.pete.death";
			break;
		case 2:
			entitysound = "mob.zombie.death"; //abyssalcraft:ghoul.wilson.death
			break;
		case 3:
			entitysound = "mob.zombie.death"; //abyssalcraft:ghoul.orange.death
		}
		return entitysound;
	}

	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return AbyssalCraft.Corbone;

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
			dropItem(Items.writable_book, 1);
			break;
		case 2:
			if(getGhoulType() == 0)
			{
				dropItem(Item.getItemFromBlock(AbyssalCraft.DGhead),1);
			}
			else if(getGhoulType() == 1)
			{
				dropItem(Item.getItemFromBlock(AbyssalCraft.Phead),1);
			}
			else if(getGhoulType() == 2)
			{
				dropItem(Item.getItemFromBlock(AbyssalCraft.Whead),1);
			}
			else if(getGhoulType() == 3)
			{
				dropItem(Item.getItemFromBlock(AbyssalCraft.Ohead),1);
			}
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.hasKey("GhoulType"))
		{
			byte var2 = par1NBTTagCompound.getByte("GhoulType");
			setGhoulType(var2);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setByte("GhoulType", (byte)getGhoulType());
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

		switch(worldObj.rand.nextInt(4))
		{
		case 0:
			setGhoulType(0);
			break;
		case 1:
			setGhoulType(1);
			break;
		case 2:
			setGhoulType(2);
			break;
		case 3:
			setGhoulType(3);
		}

		float f = worldObj.func_147462_b(posX, posY, posZ);
		setCanPickUpLoot(rand.nextFloat() < 0.55F * f);

		addRandomArmor();
		enchantEquipment();

		if (getEquipmentInSlot(4) == null)
		{
			Calendar calendar = worldObj.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				setCurrentItemOrArmor(4, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				equipmentDropChances[4] = 0.0F;
			}
		}

		return par1EntityLivingData;
	}
}