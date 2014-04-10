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
import net.minecraft.entity.monster.EntityMob;
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

import com.shinoow.abyssalcraft.AbyssalCraft;

public class EntityDepthsZombie extends EntityMob
{

	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
	private EntityAINearestAttackableTarget player = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);

	public EntityDepthsZombie(World par1World)
	{
		super(par1World);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, false));
		this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.35D));
		this.tasks.addTask(3, new EntityAIWander(this, 0.35D));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(5, new EntityAIFleeSun(this, 0.35D));
		this.tasks.addTask(6, new EntityAIAttackOnCollide(this, EntityZombie.class, 0.35D, true));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityDepthsZombie.class, 6.0F));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityZombie.class, 6.0F));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityDepthsghoul.class, 6.0F));
		this.tasks.addTask(11, new EntityAIWatchClosest(this, EntitySkeleton.class, 6.0F));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(1, new EntityAINearestAttackableTarget(this, EntityZombie.class, 0, true));
		this.targetTasks.addTask(2, player);

	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		// Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(25.0D);
		// Follow Range - default 32.0D - min 0.0D - max 2048.0D
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(128.0D);
		// Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.0D);
		// Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
		Calendar calendar = this.worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
		{
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(9.0D);
		}
	}

	@Override
	public boolean canBreatheUnderwater() 
	{
		return true;

	}

	protected void entityInit()
	{
		super.entityInit();
		this.getDataWatcher().addObject(12, Byte.valueOf((byte)0));
		this.getDataWatcher().addObject(13, Byte.valueOf((byte)0));
		this.getDataWatcher().addObject(14, Byte.valueOf((byte)0));
		this.getDataWatcher().addObject(19, Byte.valueOf((byte)0));
	}

	public boolean isChild()
	{
		return this.getDataWatcher().getWatchableObjectByte(12) == 1;
	}

	/**
	 * Set whether this zombie is a child.
	 */
	public void setChild(boolean par1)
	{
		this.getDataWatcher().updateObject(12, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (this.worldObj != null && !this.worldObj.isRemote)
		{
			IAttributeInstance attributeinstance = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
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
		return this.getDataWatcher().getWatchableObjectByte(13) == 1;
	}

	public boolean isPlayer()
	{
		return this.getDataWatcher().getWatchableObjectByte(19) == 1;
	}
	/**
	 * Set whether this abyssal zombie is a zombie.
	 */
	public void setIsZombie(boolean par1)
	{
		this.getDataWatcher().updateObject(13, Byte.valueOf((byte)(par1 ? 1 : 0)));
	}

	public void setIsPlayer(boolean par1)
	{
		this.getDataWatcher().updateObject(19, Byte.valueOf((byte)(par1 ? 1 : 0)));
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

	public int getZombieType()
	{
		return this.dataWatcher.getWatchableObjectByte(14);
	}

	public void setZombieType(int par1)
	{
		this.dataWatcher.updateObject(14, Byte.valueOf((byte)par1));
	}

	public void onLivingUpdate()
	{
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild())
		{
			float var1 = this.getBrightness(1.0F);

			if (var1 > 0.5F && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)))
			{
				boolean var2 = true;
				ItemStack var3 = this.getEquipmentInSlot(4);

				if (var3 != null)
				{
					if (var3.isItemStackDamageable())
					{
						var3.setItemDamage(var3.getItemDamageForDisplay() + this.rand.nextInt(2));

						if (var3.getItemDamageForDisplay() >= var3.getMaxDamage())
						{
							this.renderBrokenItemStack(var3);
							this.setCurrentItemOrArmor(4, (ItemStack)null);
						}
					}

					var2 = false;
				}

				if (var2)
				{
					this.setFire(16);
				}
			}
		}

		super.onLivingUpdate();
	}

	public boolean attackEntityAsMob(Entity par1Entity)
	{

		if (super.attackEntityAsMob(par1Entity))
		{
			if (par1Entity instanceof EntityLivingBase)
			{
				if(this.worldObj.provider.dimensionId == AbyssalCraft.dimension)
				{
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 200));	
				}
			}
		}

		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag && this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < (float)this.worldObj.difficultySetting.getDifficultyId() * 0.3F)
		{
			par1Entity.setFire(2 * this.worldObj.difficultySetting.getDifficultyId());
		}

		return flag;
	}

	public String getEntityName()
	{
		return "Abyssal Zombie";
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound()
	{
		return "mob.zombie.say";
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		return "mob.zombie.hurt";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		return "mob.zombie.death";
	}

	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	protected Item getDropItem()
	{
		return AbyssalCraft.Corflesh;

	}
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}
	protected void dropRareDrop(int par1)
	{
		switch (this.rand.nextInt(3))
		{
		case 0:
			this.dropItem(Items.bone, 1);
			break;
		case 1:
			this.dropItem(AbyssalCraft.sword, 1);
			break;
		case 2:
			this.dropItem(AbyssalCraft.Cpearl, 1);
		}
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.hasKey("ZombieType"))
		{
			byte var2 = par1NBTTagCompound.getByte("ZombieType");
			this.setZombieType(var2);
		}
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if (this.isChild())
		{
			par1NBTTagCompound.setBoolean("IsBaby", true);
		}

		if (this.isZombie())
		{
			par1NBTTagCompound.setBoolean("IsZombie", true);
		}
		if (this.isPlayer())
		{
			par1NBTTagCompound.setBoolean("IsPlayer", true);
		}
		par1NBTTagCompound.setByte("ZombieType", (byte)this.getZombieType());
	}



	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);

		if (this.worldObj.difficultySetting.getDifficultyId() >= 2 && par1EntityLivingBase instanceof EntityZombie)
		{
			if (this.worldObj.difficultySetting.getDifficultyId() == 2 && this.rand.nextBoolean())
			{
				return;
			}

			EntityDepthsZombie EntityDephsZombie = new EntityDepthsZombie(this.worldObj);
			EntityDephsZombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
			this.worldObj.removeEntity(par1EntityLivingBase);
			EntityDephsZombie.onSpawnWithEgg((IEntityLivingData)null);
			EntityDephsZombie.setIsZombie(true);

			if (par1EntityLivingBase.isChild())
			{
				EntityDephsZombie.setChild(true);
			}

			this.worldObj.spawnEntityInWorld(EntityDephsZombie);
			this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);

		}else if (this.worldObj.difficultySetting.getDifficultyId() >= 2 && par1EntityLivingBase instanceof EntityPlayer)
		{
			if (this.worldObj.difficultySetting.getDifficultyId() == 2 && this.rand.nextBoolean())
			{
				return;
			}

			EntityDepthsZombie EntityDephsZombie = new EntityDepthsZombie(this.worldObj);
			EntityDephsZombie.copyLocationAndAnglesFrom(par1EntityLivingBase);
			this.worldObj.removeEntity(par1EntityLivingBase);
			EntityDephsZombie.onSpawnWithEgg((IEntityLivingData)null);
			EntityDephsZombie.setIsPlayer(true);

			this.worldObj.spawnEntityInWorld(EntityDephsZombie);
			this.worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)this.posX, (int)this.posY, (int)this.posZ, 0);
		}
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

		if (this.worldObj.provider instanceof WorldProviderEnd && this.getRNG().nextInt(5) > 0)
		{
			this.setZombieType(2);
		}

		float f = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);

		if (this.worldObj.rand.nextFloat() < 0.05F)
		{
			this.setIsZombie(true);
		}

		this.addRandomArmor();
		this.enchantEquipment();

		if (this.getEquipmentInSlot(4) == null)
		{
			Calendar calendar = this.worldObj.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			{
				this.setCurrentItemOrArmor(4, new ItemStack(this.rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				this.equipmentDropChances[4] = 0.0F;
			}
		}

		return par1EntityLivingData;
	}
}