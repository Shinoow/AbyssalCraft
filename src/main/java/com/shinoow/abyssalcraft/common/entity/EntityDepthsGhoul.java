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
package com.shinoow.abyssalcraft.common.entity;

import java.util.Calendar;
import java.util.UUID;

import net.minecraft.block.Block;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class EntityDepthsGhoul extends EntityMob implements ICoraliumEntity {

	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier peteDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Pete Attack Damage Boost", 2.0D, 0);
	private static final AttributeModifier wilsonDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Wilson Attack Damage Boost", 4.0D, 0);
	private static final AttributeModifier orangeDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Orange Attack Damage Boost", 6.0D, 0);
	private static final AttributeModifier ghoulHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3.0D, 0);
	private static final AttributeModifier peteHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Pete Halloween Attack Damage Boost", 8.0D, 0);
	private static final AttributeModifier wilsonHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Wilson Halloween Attack Damage Boost", 10.0D, 0);
	private static final AttributeModifier orangeHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Orange Halloween Attack Damage Boost", 12.0D, 0);
	private static final UUID healthBoostUUID = UUID.fromString("5D6F0BA2-1186-46AC-B896-C61C5CEE99CC");
	private static final AttributeModifier peteHealthBoost = new AttributeModifier(healthBoostUUID, "Pete Health Boost", 10.0D, 0);
	private static final AttributeModifier wilsonHealthBoost = new AttributeModifier(healthBoostUUID, "Wilson Health Boost", 20.0D, 0);
	private static final AttributeModifier orangeHealthBoost = new AttributeModifier(healthBoostUUID, "Orange Health Boost", 30.0D, 0);

	public EntityDepthsGhoul(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0D, false));
		tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDepthsGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		setSize(1.5F, 3.0F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.3D);
		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(10.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0D);
		}
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public String getCommandSenderName()
	{
		switch (getGhoulType())
		{
		case 0:
			return StatCollector.translateToLocal("entity.abyssalcraft.depthsghoul.name");
		case 1:
			return "Pete";
		case 2:
			return "Mr. Wilson";
		case 3:
			return "Dr. Orange";
		default:
			return StatCollector.translateToLocal("entity.abyssalcraft.depthsghoul.name");
		}
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

		if (par1DamageSource.getEntity() instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getEntity();
			entityplayer.addStat(AbyssalCraft.killghoul,1);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if (super.attackEntityAsMob(par1Entity))
			if (par1Entity instanceof EntityLivingBase)
				if(worldObj.provider.dimensionId == AbyssalCraft.configDimId1 && !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity)
				|| AbyssalCraft.shouldInfect == true && !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraft.Cplague.id, 100));
		swingItem();
		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag && getHeldItem() == null && isBurning() && rand.nextFloat() < worldObj.difficultySetting.getDifficultyId() * 0.3F)
			par1Entity.setFire(2 * worldObj.difficultySetting.getDifficultyId());

		return flag;
	}

	@Override
	protected String getLivingSound()
	{
		switch (getGhoulType())
		{
		case 0:
			return "abyssalcraft:ghoul.normal.idle";
		case 1:
			return "abyssalcraft:ghoul.pete.idle";
		case 2:
			return "abyssalcraft:ghoul.normal.idle"; //abyssalcraft:ghoul.wilson.idle
		case 3:
			return "abyssalcraft:ghoul.normal.idle"; //abyssalcraft:ghoul.orange.idle
		default:
			return "abyssalcraft:ghoul.normal.idle";
		}
	}

	@Override
	protected String getHurtSound()
	{
		switch (getGhoulType())
		{
		case 0:
			return "mob.zombie.hurt"; //abyssalcraft:ghoul.normal.hit
		case 1:
			return "abyssalcraft:ghoul.pete.hit";
		case 2:
			return "mob.zombie.hurt"; //abyssalcraft:ghoul.wilson.hit
		case 3:
			return "mob.zombie.hurt"; //abyssalcraft:ghoul.orange.hit
		default:
			return "mob.zombie.hurt";
		}
	}

	@Override
	protected String getDeathSound()
	{
		return "abyssalcraft:ghoul.death";
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
		switch(getGhoulType()){
		case 0:
			dropItem(Item.getItemFromBlock(AbyssalCraft.DGhead),1);
			break;
		case 1:
			dropItem(Item.getItemFromBlock(AbyssalCraft.Phead),1);
			break;
		case 2:
			dropItem(Item.getItemFromBlock(AbyssalCraft.Whead),1);
			break;
		case 3:
			dropItem(Item.getItemFromBlock(AbyssalCraft.Ohead),1);
			break;
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if(par1NBTTagCompound.getBoolean("IsBaby"))
			setChild(true);

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

		if(isChild())
			par1NBTTagCompound.setBoolean("IsBaby", true);

		par1NBTTagCompound.setByte("GhoulType", (byte)getGhoulType());
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onSpawnWithEgg(par1EntityLivingData);

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

		if (data == null)
			data = new EntityDepthsGhoul.GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);

		if (data instanceof EntityDepthsGhoul.GroupData)
		{
			EntityDepthsGhoul.GroupData groupdata = (EntityDepthsGhoul.GroupData)data;

			if (groupdata.isBaby)
				setChild(true);
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

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.attackDamage);
		IAttributeInstance attribute1 = getEntityAttribute(SharedMonsterAttributes.maxHealth);
		Calendar calendar = worldObj.getCurrentDate();

		switch(getGhoulType()){
		case 0:
			if(worldObj != null && !worldObj.isRemote)
				clearModifiers(attribute, attribute1);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
				attribute.applyModifier(ghoulHDamageBoost);
			break;
		case 1:
			if(worldObj != null && !worldObj.isRemote)
				clearModifiers(attribute, attribute1);
			attribute.applyModifier(peteDamageBoost);
			attribute1.applyModifier(peteHealthBoost);
			setHealth(40);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F){
				attribute.removeModifier(peteDamageBoost);
				attribute.applyModifier(peteHDamageBoost);
			}
			break;
		case 2:
			if(worldObj != null && !worldObj.isRemote)
				clearModifiers(attribute, attribute1);
			attribute.applyModifier(wilsonDamageBoost);
			attribute1.applyModifier(wilsonHealthBoost);
			setHealth(50);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F){
				attribute.removeModifier(wilsonDamageBoost);
				attribute.applyModifier(wilsonHDamageBoost);
			}
			break;
		case 3:
			if(worldObj != null && !worldObj.isRemote)
				clearModifiers(attribute, attribute1);
			attribute.applyModifier(orangeDamageBoost);
			attribute1.applyModifier(orangeHealthBoost);
			setHealth(60);
			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F){
				attribute.removeModifier(orangeDamageBoost);
				attribute.applyModifier(orangeHDamageBoost);
			}
			break;
		}

		return (IEntityLivingData)data;
	}

	private void clearModifiers(IAttributeInstance dmg, IAttributeInstance hp){
		dmg.removeModifier(peteDamageBoost);
		dmg.removeModifier(wilsonDamageBoost);
		dmg.removeModifier(orangeDamageBoost);
		hp.removeModifier(peteHealthBoost);
		hp.removeModifier(wilsonHealthBoost);
		hp.removeModifier(orangeHealthBoost);
	}

	class GroupData implements IEntityLivingData
	{
		public boolean isBaby;
		private GroupData(boolean par2)
		{
			isBaby = false;
			isBaby = par2;
		}

		GroupData(boolean par2, Object par4EntityDepthsGhoulINNER1)
		{
			this(par2);
		}
	}
}
