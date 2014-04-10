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
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class EntityDepthsghoul extends EntityMob
{

	private EntityAINearestAttackableTarget player = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true);

	public EntityDepthsghoul(World par1World)
	{
		super(par1World);
		this.setSize(1.5F, 3.0F);
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, false));
		this.tasks.addTask(2, new EntityAIMoveTowardsRestriction(this, 0.35D));
		this.tasks.addTask(3, new EntityAIWander(this, 0.35D));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.tasks.addTask(5, new EntityAIFleeSun(this, 0.35D));
		this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsghoul.class, 6.0F));
		this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityDepthsZombie.class, 6.0F));
		this.tasks.addTask(9, new EntityAIWatchClosest(this, EntityZombie.class, 6.0F));
		this.tasks.addTask(10, new EntityAIWatchClosest(this, EntitySkeleton.class, 6.0F));
		this.targetTasks.addTask(0, new EntityAIHurtByTarget(this, false));
		this.targetTasks.addTask(1, player);

	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		// Max Health - default 20.0D - min 0.0D - max Double.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
		// Follow Range - default 32.0D - min 0.0D - max 2048.0D
		this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		// Knockback Resistance - default 0.0D - min 0.0D - max 1.0D
		this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.3D);
		// Movement Speed - default 0.699D - min 0.0D - max Double.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.699D);
		// Attack Damage - default 2.0D - min 0.0D - max Doubt.MAX_VALUE
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
		Calendar calendar = this.worldObj.getCurrentDate();

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
		{
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.5D);
		}

		switch(this.getGhoulType())
		{
		case 0:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			{
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.5D);
			}
			break;
		case 1:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(50.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0D);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			{
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(9.0D);
			}
			break;
		case 2:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(7.0D);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			{
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(11.5D);
			}
			break;
		case 3:
			this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(70.0D);
			this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0D);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F)
			{
				this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(12.0D);
			}
		}
	}

	public String getEntityName()
	{
		String entityname = "Depths Ghoul";
		switch (this.getGhoulType())
		{
		case 0:
			entityname = "Depths Ghoul";
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

	protected void entityInit()
	{
		super.entityInit();
		this.getDataWatcher().addObject(13, Byte.valueOf((byte)0));
	}

	protected boolean isAIEnabled()
	{
		return true;
	}

	public int getGhoulType()
	{
		return this.dataWatcher.getWatchableObjectByte(13);
	}

	public void setGhoulType(int par1)
	{
		this.dataWatcher.updateObject(13, Byte.valueOf((byte)par1));
	}

	public void onLivingUpdate()
	{
		if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild() )
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
					this.setFire(8);
				}

			}
		}

		super.onLivingUpdate();
	}

	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if (par1DamageSource.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killghoul,1);
		}
		if (par1DamageSource.getEntity() instanceof EntityPlayer && this.getGhoulType() == 1)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killPete,1);
		}
		if (par1DamageSource.getEntity() instanceof EntityPlayer && this.getGhoulType() == 2)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killWilson,1);
		}
		if (par1DamageSource.getEntity() instanceof EntityPlayer && this.getGhoulType() == 3)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killOrange,1);
		}
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

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	protected String getLivingSound()
	{
		String entitysound = "mob.zombie.say";
		switch (this.getGhoulType())
		{
		case 0:
			entitysound = "mob.zombie.say";
			break;
		case 1:
			entitysound = "abyssalcraft:ghoul.pete.idle";
			break;
		case 2:
			entitysound = "mob.zombie.say";
			break;
		case 3:
			entitysound = "mob.zombie.say";
		}
		return entitysound;
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	protected String getHurtSound()
	{
		String entitysound = "mob.zombie.hurt";
		switch (this.getGhoulType())
		{
		case 0:
			entitysound = "mob.zombie.hurt";
			break;
		case 1:
			entitysound = "abyssalcraft:ghoul.pete.hit";
			break;
		case 2:
			entitysound = "mob.zombie.hurt";
			break;
		case 3:
			entitysound = "mob.zombie.hurt";
		}
		return entitysound;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	protected String getDeathSound()
	{
		String entitysound = "mob.zombie.death";
		switch (this.getGhoulType())
		{
		case 0:
			entitysound = "mob.zombie.death";
			break;
		case 1:
			entitysound = "abyssalcraft:ghoul.pete.death";
			break;
		case 2:
			entitysound = "mob.zombie.death";
			break;
		case 3:
			entitysound = "mob.zombie.death";
		}
		return entitysound;
	}

	protected void playStepSound(int par1, int par2, int par3, int par4)
	{
		this.playSound("mob.zombie.step", 0.15F, 1.0F);
	}

	protected Item getDropItem()
	{
		return AbyssalCraft.Corbone;

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
			this.dropItem(Items.writable_book, 1);
			break;
		case 2:
			if(this.getGhoulType() == 0)
			{
				this.dropItem(Item.getItemFromBlock(AbyssalCraft.DGhead),1);
			}
			else if(this.getGhoulType() == 1)
			{
				this.dropItem(Item.getItemFromBlock(AbyssalCraft.Phead),1);
			}
			else if(this.getGhoulType() == 2)
			{
				this.dropItem(Item.getItemFromBlock(AbyssalCraft.Whead),1);
			}
			else if(this.getGhoulType() == 3)
			{
				this.dropItem(Item.getItemFromBlock(AbyssalCraft.Ohead),1);
			}
		}
	}

	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if (par1NBTTagCompound.hasKey("GhoulType"))
		{
			byte var2 = par1NBTTagCompound.getByte("GhoulType");
			this.setGhoulType(var2);
		}
	}

	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		par1NBTTagCompound.setByte("GhoulType", (byte)this.getGhoulType());
	}

	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

		switch(this.worldObj.rand.nextInt(4))
		{
		case 0:
			this.setGhoulType(0);
			break;
		case 1:
			this.setGhoulType(1);
			break;
		case 2:
			this.setGhoulType(2);
			break;
		case 3:
			this.setGhoulType(3);
		}

		float f = this.worldObj.func_147462_b(this.posX, this.posY, this.posZ);
		this.setCanPickUpLoot(this.rand.nextFloat() < 0.55F * f);

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