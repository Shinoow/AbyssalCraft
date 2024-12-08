/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.lib.*;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

public class EntityDepthsGhoul extends EntityMob implements ICoraliumEntity {

	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityDepthsGhoul.class, DataSerializers.VARINT);
	private static final DataParameter<Boolean> CHILD = EntityDataManager.createKey(EntityDepthsGhoul.class, DataSerializers.BOOLEAN);
	private static final UUID babySpeedBoostUUID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
	private static final AttributeModifier babySpeedBoostModifier = new AttributeModifier(babySpeedBoostUUID, "Baby speed boost", 0.5D, 1);
	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier ghoulHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3.0D, 0);

	private float ghoulWidth = -1.0F;
	private float ghoulHeight;

	public EntityDepthsGhoul(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
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
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		setSize(1.0F, 2.0F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 60.0D : 30.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 10.0D : 5.0D);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public String getName()
	{
		switch (getGhoulType())
		{
		case 0:
			return super.getName();
		case 1:
			return "Pete";
		case 2:
			return "Mr. Wilson";
		case 3:
			return "Dr. Orange";
		default:
			return super.getName();
		}
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(CHILD, false);
		dataManager.register(TYPE, 0);
	}

	@Override
	public boolean isChild()
	{
		return dataManager.get(CHILD);
	}

	public void setChild(boolean par1)
	{
		dataManager.set(CHILD, par1);

		if (world != null && !world.isRemote)
		{
			IAttributeInstance attributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
			attributeinstance.removeModifier(babySpeedBoostModifier);

			if (par1)
				attributeinstance.applyModifier(babySpeedBoostModifier);
		}

		setChildSize(par1);
	}

	public int getGhoulType()
	{
		return dataManager.get(TYPE);
	}

	public void setGhoulType(int par1)
	{
		dataManager.set(TYPE, par1);
	}

	@Override
	public void onLivingUpdate()
	{
		if (world.isDaytime() && !world.isRemote && !isChild() && world.provider.getDimension() != ACLib.abyssal_wasteland_id)
		{
			float var1 = getBrightness();

			if (var1 > 0.5F && rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && world.canSeeSky(new BlockPos(MathHelper.floor(posX), MathHelper.floor(posY), MathHelper.floor(posZ))))
			{
				boolean var2 = true;
				ItemStack var3 = getItemStackFromSlot(EntityEquipmentSlot.HEAD);

				if (!var3.isEmpty())
				{
					if (var3.isItemStackDamageable())
					{
						var3.setItemDamage(var3.getItemDamage() + rand.nextInt(2));

						if (var3.getItemDamage() >= var3.getMaxDamage())
						{
							renderBrokenItemStack(var3);
							setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
						}
					}

					var2 = false;
				}

				if (var2)
					setFire(8);

			}
		}

		if(world.isRemote)
			setChildSize(isChild());

		super.onLivingUpdate();
	}

	//	@Override
	//	public void onDeath(DamageSource par1DamageSource)
	//	{
	//		super.onDeath(par1DamageSource);
	//
	//		if (par1DamageSource.getTrueSource() instanceof EntityPlayer)
	//		{
	//			EntityPlayer entityplayer = (EntityPlayer)par1DamageSource.getTrueSource();
	////			entityplayer.addStat(ACAchievements.kill_depths_ghoul,1);
	//		}
	//	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		swingArm(EnumHand.MAIN_HAND);
		swingArm(EnumHand.OFF_HAND);
		boolean flag = super.attackEntityAsMob(par1Entity);

		if(flag){
			if(par1Entity instanceof EntityLivingBase)
				if(world.provider.getDimension() == ACLib.abyssal_wasteland_id && !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity)
				|| ACConfig.shouldInfect == true && !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100));
			if(getHeldItemMainhand().isEmpty() && isBurning() && rand.nextFloat() < world.getDifficulty().getId() * 0.3F)
				par1Entity.setFire(2 * world.getDifficulty().getId());
		}

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		switch (getGhoulType())
		{
		case 0:
			return ACSounds.ghoul_normal_ambient;
		case 1:
			return ACSounds.ghoul_pete_ambient;
		case 2:
			return ACSounds.ghoul_wilson_ambient;
		case 3:
			return ACSounds.ghoul_orange_ambient;
		default:
			return ACSounds.ghoul_normal_ambient;
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return ACSounds.ghoul_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ACSounds.ghoul_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.coralium_plagued_flesh_on_a_bone;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected ResourceLocation getLootTable(){
		switch(getGhoulType()){
		case 0:
			return ACLoot.ENTITY_DEPTHS_GHOUL;
		case 1:
			return ACLoot.ENTITY_DEPTHS_GHOUL_PETE;
		case 2:
			return ACLoot.ENTITY_DEPTHS_GHOUL_WILSON;
		case 3:
			return ACLoot.ENTITY_DEPTHS_GHOUL_ORANGE;
		}
		return null;
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readEntityFromNBT(par1NBTTagCompound);

		if(par1NBTTagCompound.getBoolean("IsBaby"))
			setChild(true);

		if (par1NBTTagCompound.hasKey("GhoulType"))
		{
			int var2 = par1NBTTagCompound.getInteger("GhoulType");
			setGhoulType(var2);
		}
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeEntityToNBT(par1NBTTagCompound);

		if(isChild())
			par1NBTTagCompound.setBoolean("IsBaby", true);

		par1NBTTagCompound.setInteger("GhoulType", getGhoulType());
	}

	@Override
	protected void updateEquipmentIfNeeded(EntityItem itemEntity)
	{
		if(!InitHandler.INSTANCE.isItemBlacklisted(this, itemEntity.getItem()))
			super.updateEquipmentIfNeeded(itemEntity);
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onInitialSpawn(difficulty, par1EntityLivingData);

		int type = 0;

		if(world.rand.nextFloat() < 0.2F){
			int temp = world.rand.nextInt(4);
			if(temp < 4)
				type = temp;
		}

		setGhoulType(type);

		float f = difficulty.getClampedAdditionalDifficulty();
		setCanPickUpLoot(ACConfig.hardcoreMode ? true : rand.nextFloat() < 0.55F * f);

		if (data == null)
			data = new EntityDepthsGhoul.GroupData(world.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);

		if (data instanceof EntityDepthsGhoul.GroupData)
		{
			EntityDepthsGhoul.GroupData groupdata = (EntityDepthsGhoul.GroupData)data;

			if (groupdata.isBaby)
				setChild(true);
		}

		if(ACConfig.hardcoreMode)
			EntityUtil.suitUp(this, false);
		else {
			setEquipmentBasedOnDifficulty(difficulty);
			setEnchantmentBasedOnDifficulty(difficulty);
		}

		if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
		{
			Calendar calendar = world.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
				inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		Calendar calendar = world.getCurrentDate();

		attribute.removeModifier(ghoulHDamageBoost);

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
			attribute.applyModifier(ghoulHDamageBoost);

		return (IEntityLivingData)data;
	}

	public void setChildSize(boolean p_146071_1_)
	{
		multiplySize(p_146071_1_ ? 0.5F : 1.0F);
	}

	@Override
	protected final void setSize(float p_70105_1_, float p_70105_2_)
	{
		boolean flag = ghoulWidth > 0.0F && ghoulHeight > 0.0F;
		ghoulWidth = p_70105_1_;
		ghoulHeight = p_70105_2_;

		if (!flag)
			multiplySize(1.0F);
	}

	protected final void multiplySize(float p_146069_1_)
	{
		super.setSize(ghoulWidth * p_146069_1_, ghoulHeight * p_146069_1_);
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
