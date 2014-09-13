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

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.core.api.entity.CoraliumMob;

public class EntityDepthsghoul extends CoraliumMob {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier peteDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Attack Damage Boost", 2.0D, 0);
	private static final AttributeModifier wilsonDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Attack Damage Boost", 4.0D, 0);
	private static final AttributeModifier orangeDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Attack Damage Boost", 6.0D, 0);
	private static final AttributeModifier ghoulHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3D, 0);
	private static final AttributeModifier peteHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 4D, 0);
	private static final AttributeModifier wilsonHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5D, 0);
	private static final AttributeModifier orangeHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 6D, 0);
	private static final UUID healthBoostUUID = UUID.fromString("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC");
	private static final AttributeModifier peteHealthBoost = new AttributeModifier(healthBoostUUID, "Health Boost", 10.0D, 0);
	private static final AttributeModifier wilsonHealthBoost = new AttributeModifier(healthBoostUUID, "Health Boost", 20.0D, 0);
	private static final AttributeModifier orangeHealthBoost = new AttributeModifier(healthBoostUUID, "Health Boost", 30.0D, 0);

	public EntityDepthsghoul(World par1World) {
		super(par1World);
		setSize(1.5F, 3.0F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsghoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.3D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);
		getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
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
					setFire(8);

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
			if (par1Entity instanceof EntityLivingBase)
				if(worldObj.provider.dimensionId == AbyssalCraft.configDimId1)
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 200));

		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag && getHeldItem() == null && isBurning() && rand.nextFloat() < worldObj.difficultySetting.getDifficultyId() * 0.3F)
			par1Entity.setFire(2 * worldObj.difficultySetting.getDifficultyId());

		return flag;
	}

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

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
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
				dropItem(Item.getItemFromBlock(AbyssalCraft.DGhead),1);
			else if(getGhoulType() == 1)
				dropItem(Item.getItemFromBlock(AbyssalCraft.Phead),1);
			else if(getGhoulType() == 2)
				dropItem(Item.getItemFromBlock(AbyssalCraft.Whead),1);
			else if(getGhoulType() == 3)
				dropItem(Item.getItemFromBlock(AbyssalCraft.Ohead),1);
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

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.attackDamage);
		IAttributeInstance attribute1 = getEntityAttribute(SharedMonsterAttributes.maxHealth);
		Calendar calendar = worldObj.getCurrentDate();

		switch(getGhoulType()){
		case 0:
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
				attribute.applyModifier(ghoulHDamageBoost);
			break;
		case 1:
			attribute.applyModifier(peteDamageBoost);
			attribute1.applyModifier(peteHealthBoost);
			setHealth(40);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
				attribute.applyModifier(peteHDamageBoost);
			break;
		case 2:
			attribute.applyModifier(wilsonDamageBoost);
			attribute1.applyModifier(wilsonHealthBoost);
			setHealth(50);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
				attribute.applyModifier(wilsonHDamageBoost);
			break;
		case 3:
			attribute.applyModifier(orangeDamageBoost);
			attribute1.applyModifier(orangeHealthBoost);
			setHealth(60);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
				attribute.applyModifier(orangeHDamageBoost);
		}

		return par1EntityLivingData;
	}
}