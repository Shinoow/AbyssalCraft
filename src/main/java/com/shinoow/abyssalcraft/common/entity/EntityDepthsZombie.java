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
import java.util.UUID;

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
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;
import net.minecraftforge.common.ForgeModContainer;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.core.api.entity.CoraliumMob;

public class EntityDepthsZombie extends CoraliumMob
{

	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
	private EntityAINearestAttackableTarget player = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);

	public EntityDepthsZombie(World par1World)
	{
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, false));
		tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(3, new EntityAIWander(this, 0.35D));
		tasks.addTask(4, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIFleeSun(this, 0.35D));
		tasks.addTask(6, new EntityAIAttackOnCollide(this, EntityZombie.class, 0.35D, true));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(8, new EntityAIWatchClosest(this, EntityDepthsZombie.class, 6.0F));
		tasks.addTask(9, new EntityAIWatchClosest(this, EntityZombie.class, 6.0F));
		tasks.addTask(10, new EntityAIWatchClosest(this, EntityDepthsghoul.class, 6.0F));
		tasks.addTask(11, new EntityAIWatchClosest(this, EntitySkeleton.class, 6.0F));
		targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityZombie.class, 0, true));
		targetTasks.addTask(2, player);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		// Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
		// Follow Range - default 32.0D - min 0.0D - max 2048.0D
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(128.0D);
		// Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		// Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
		Calendar calendar = worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
		{
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(9.0D);
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		getDataWatcher().addObject(13, Byte.valueOf((byte)0));
		getDataWatcher().addObject(14, Byte.valueOf((byte)0));
		getDataWatcher().addObject(19, Byte.valueOf((byte)0));
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
		if(isChild()){
			pitch = this.rand.nextFloat() - this.rand.nextFloat() * 0.2F + 1.3F;
		}else{
			pitch = 0.9F;
		}
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
			{
				attributeinstance.applyModifier(babySpeedBoostModifier);
			}
		}
	}

	/**
	 * Return whether this  abyssal zombie is a zombie.
	 */
	public boolean isZombie()
	{
		return getDataWatcher().getWatchableObjectByte(13) == 1;
	}

	@Override
	public boolean isPlayer()
	{
		return getDataWatcher().getWatchableObjectByte(19) == 1;
	}
	/**
	 * Set whether this abyssal zombie is a zombie.
	 */
	public void setIsZombie(boolean par1)
	{
		getDataWatcher().updateObject(13, Byte.valueOf((byte)(par1 ? 1 : 0)));
	}

	public void setIsPlayer(boolean par1)
	{
		getDataWatcher().updateObject(19, Byte.valueOf((byte)(par1 ? 1 : 0)));
	}

	@Override
	public int getTotalArmorValue()
	{
		int var1 = super.getTotalArmorValue() + 2;

		if (var1 > 20)
		{
			var1 = 20;
		}

		return var1;
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	public int getZombieType()
	{
		return dataWatcher.getWatchableObjectByte(14);
	}

	public void setZombieType(int par1)
	{
		dataWatcher.updateObject(14, Byte.valueOf((byte)par1));
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
					setFire(16);
				}
			}
		}

		super.onLivingUpdate();
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

	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return AbyssalCraft.Corflesh;

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
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.hasKey("ZombieType"))
		{
			byte var2 = par1NBTTagCompound.getByte("ZombieType");
			setZombieType(var2);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (isChild())
		{
			par1NBTTagCompound.setBoolean("IsBaby", true);
		}

		if (isZombie())
		{
			par1NBTTagCompound.setBoolean("IsZombie", true);
		}
		if (isPlayer())
		{
			par1NBTTagCompound.setBoolean("IsPlayer", true);
		}
		par1NBTTagCompound.setByte("ZombieType", (byte)getZombieType());
	}



	@Override
	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);

		if (worldObj.difficultySetting.getDifficultyId() >= 2 && par1EntityLivingBase instanceof EntityZombie)
		{
			if (worldObj.difficultySetting.getDifficultyId() == 2 && rand.nextBoolean())
				return;

			EntityDepthsZombie EntityDephsZombie = new EntityDepthsZombie(worldObj);
			EntityDephsZombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
			worldObj.removeEntity(par1EntityLivingBase);
			EntityDephsZombie.onSpawnWithEgg((IEntityLivingData)null);
			EntityDephsZombie.setIsZombie(true);

			if (par1EntityLivingBase.isChild())
			{
				EntityDephsZombie.setChild(true);
			}

			worldObj.spawnEntityInWorld(EntityDephsZombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);

		}else if (worldObj.difficultySetting.getDifficultyId() >= 2 && par1EntityLivingBase instanceof EntityPlayer)
		{
			if (worldObj.difficultySetting.getDifficultyId() == 2 && rand.nextBoolean())
				return;

			EntityDepthsZombie EntityDephsZombie = new EntityDepthsZombie(worldObj);
			EntityDephsZombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
			worldObj.removeEntity(par1EntityLivingBase);
			EntityDephsZombie.onSpawnWithEgg((IEntityLivingData)null);
			EntityDephsZombie.setIsPlayer(true);

			worldObj.spawnEntityInWorld(EntityDephsZombie);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);
		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onSpawnWithEgg(par1EntityLivingData);

		if (worldObj.provider instanceof WorldProviderEnd && getRNG().nextInt(5) > 0)
		{
			setZombieType(2);
		}

		float f = worldObj.func_147462_b(posX, posY, posZ);
		setCanPickUpLoot(rand.nextFloat() < 0.55F * f);

		if (data == null)
		{
			data = new EntityDepthsZombie.GroupData(this.worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, this.worldObj.rand.nextFloat() < 0.05F, null);
		}

		if (data instanceof EntityDepthsZombie.GroupData)
		{
			EntityDepthsZombie.GroupData groupdata = (EntityDepthsZombie.GroupData)data;

			if (groupdata.field_142046_b)
			{
				this.setIsZombie(true);
			}

			if (groupdata.field_142048_a)
			{
				this.setChild(true);
			}
		}

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

		return (IEntityLivingData)data;
	}

	class GroupData implements IEntityLivingData
	{
		public boolean field_142048_a;
		public boolean field_142046_b;
		private GroupData(boolean par2, boolean par3)
		{
			this.field_142048_a = false;
			this.field_142046_b = false;
			this.field_142048_a = par2;
			this.field_142046_b = par3;
		}

		GroupData(boolean par2, boolean par3, Object par4EntityZombieINNER1)
		{
			this(par2, par3);
		}
	}
}