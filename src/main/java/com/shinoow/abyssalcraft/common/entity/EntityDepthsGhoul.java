/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2016 Shinoow.
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
import net.minecraft.entity.ai.EntityAIAttackMelee;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeModContainer;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.ICoraliumEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.util.EntityUtil;

public class EntityDepthsGhoul extends EntityMob implements ICoraliumEntity {

	private static final DataParameter<Integer> TYPE = EntityDataManager.createKey(EntityDepthsGhoul.class, DataSerializers.VARINT);
	private static final DataParameter<Byte> CHILD = EntityDataManager.createKey(EntityDepthsGhoul.class, DataSerializers.BYTE);
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
		setSize(1.0F, 3.0F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
		}
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
		dataWatcher.register(CHILD, Byte.valueOf((byte)0));
		dataWatcher.register(TYPE, Integer.valueOf(0));
	}

	@Override
	public boolean isChild()
	{
		return dataWatcher.get(CHILD).byteValue() == 1;
	}

	public void setChild(boolean par1)
	{
		dataWatcher.set(CHILD, Byte.valueOf((byte)(par1 ? 1 : 0)));

		if (worldObj != null && !worldObj.isRemote)
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
		return dataWatcher.get(TYPE);
	}

	public void setGhoulType(int par1)
	{
		dataWatcher.set(TYPE, Integer.valueOf(par1));
	}

	@Override
	public void onLivingUpdate()
	{
		if (worldObj.isDaytime() && !worldObj.isRemote && !isChild() && worldObj.provider.getDimension() != AbyssalCraft.configDimId1)
		{
			float var1 = getBrightness(1.0F);

			if (var1 > 0.5F && rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F && worldObj.canSeeSky(new BlockPos(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ))))
			{
				boolean var2 = true;
				ItemStack var3 = getItemStackFromSlot(EntityEquipmentSlot.HEAD);

				if (var3 != null)
				{
					if (var3.isItemStackDamageable())
					{
						var3.setItemDamage(var3.getItemDamage() + rand.nextInt(2));

						if (var3.getItemDamage() >= var3.getMaxDamage())
						{
							renderBrokenItemStack(var3);
							setItemStackToSlot(EntityEquipmentSlot.HEAD, null);
						}
					}

					var2 = false;
				}

				if (var2)
					setFire(8);

			}
		}

		if(worldObj.isRemote)
			setChildSize(isChild());

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
				if(worldObj.provider.getDimension() == AbyssalCraft.configDimId1 && !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity)
				|| AbyssalCraft.shouldInfect == true && !EntityUtil.isEntityCoralium((EntityLivingBase)par1Entity))
					((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.coralium_plague, 100));
		swingArm(EnumHand.MAIN_HAND);
		swingArm(EnumHand.OFF_HAND);
		boolean flag = super.attackEntityAsMob(par1Entity);

		if (flag && getHeldItemMainhand() == null && isBurning() && rand.nextFloat() < worldObj.getDifficulty().getDifficultyId() * 0.3F)
			par1Entity.setFire(2 * worldObj.getDifficulty().getDifficultyId());

		return flag;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		switch (getGhoulType())
		{
		case 0:
			return AbyssalCraft.ghoul_normal_ambient;
		case 1:
			return AbyssalCraft.ghoul_pete_ambient;
		case 2:
			return AbyssalCraft.ghoul_normal_ambient; //abyssalcraft:ghoul.wilson.idle
		case 3:
			return AbyssalCraft.ghoul_normal_ambient; //abyssalcraft:ghoul.orange.idle
		default:
			return AbyssalCraft.ghoul_normal_ambient;
		}
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		switch (getGhoulType())
		{
		case 0:
			return AbyssalCraft.ghoul_normal_hurt;
		case 1:
			return AbyssalCraft.ghoul_pete_hurt;
		case 2:
			return AbyssalCraft.ghoul_normal_hurt; //abyssalcraft:ghoul.wilson.hit
		case 3:
			return AbyssalCraft.ghoul_normal_hurt; //abyssalcraft:ghoul.orange.hit
		default:
			return AbyssalCraft.ghoul_normal_hurt;
		}
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return AbyssalCraft.ghoul_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.entity_zombie_step, 0.15F, 1.0F);
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

	//	@Override
	//	protected void addRandomDrop()
	//	{
	//		switch(getGhoulType()){
	//		case 0:
	//			dropItem(Item.getItemFromBlock(AbyssalCraft.DGhead),1);
	//			break;
	//		case 1:
	//			dropItem(Item.getItemFromBlock(AbyssalCraft.Phead),1);
	//			break;
	//		case 2:
	//			dropItem(Item.getItemFromBlock(AbyssalCraft.Whead),1);
	//			break;
	//		case 3:
	//			dropItem(Item.getItemFromBlock(AbyssalCraft.Ohead),1);
	//			break;
	//		}
	//	}

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
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		Object data = super.onInitialSpawn(difficulty, par1EntityLivingData);

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

		float f = difficulty.getClampedAdditionalDifficulty();
		setCanPickUpLoot(rand.nextFloat() < 0.55F * f);

		if (data == null)
			data = new EntityDepthsGhoul.GroupData(worldObj.rand.nextFloat() < ForgeModContainer.zombieBabyChance, null);

		if (data instanceof EntityDepthsGhoul.GroupData)
		{
			EntityDepthsGhoul.GroupData groupdata = (EntityDepthsGhoul.GroupData)data;

			if (groupdata.isBaby)
				setChild(true);
		}

		setEquipmentBasedOnDifficulty(difficulty);
		setEnchantmentBasedOnDifficulty(difficulty);

		if (getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null)
		{
			Calendar calendar = worldObj.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.lit_pumpkin : Blocks.pumpkin));
				inventoryArmorDropChances[4] = 0.0F;
			}
		}

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		IAttributeInstance attribute1 = getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH);
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
