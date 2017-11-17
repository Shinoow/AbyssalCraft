/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2017 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.anti;

import java.util.Calendar;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;

public class EntityAntiGhoul extends EntityMob implements IAntiEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3.0D, 0);

	public EntityAntiGhoul(World par1World) {
		super(par1World);
		setSize(1.0F, 3.0F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(4, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAILookIdle(this));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiGhoul.class, 8.0F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiAbyssalZombie.class, 8.0F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiZombie.class, 8.0F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiSkeleton.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityDepthsGhoul.class, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
		}
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		swingArm(EnumHand.MAIN_HAND);
		swingArm(EnumHand.OFF_HAND);
		boolean flag = super.attackEntityAsMob(par1Entity);

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return ACSounds.ghoul_normal_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return ACSounds.ghoul_hurt;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
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
		return ACItems.anti_plagued_flesh_on_a_bone;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_ANTI_GHOUL;
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntityDepthsGhoul){
			boolean flag = worldObj.getGameRules().getBoolean("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	protected void updateEquipmentIfNeeded(EntityItem itemEntity)
	{
		if(!InitHandler.INSTANCE.isItemBlacklisted(this, itemEntity.getEntityItem()))
			super.updateEquipmentIfNeeded(itemEntity);
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {

		super.onDeath(par1DamageSource);

		if(par1DamageSource == AbyssalCraftAPI.coralium || par1DamageSource == AbyssalCraftAPI.dread){
			EntityOmotholGhoul entity = new EntityOmotholGhoul(worldObj);
			entity.copyLocationAndAnglesFrom(this);
			worldObj.removeEntity(this);
			entity.onInitialSpawn(worldObj.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
			worldObj.spawnEntityInWorld(entity);
		}
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

		float f = difficulty.getClampedAdditionalDifficulty();
		setCanPickUpLoot(rand.nextFloat() < 0.55F * f);

		setEquipmentBasedOnDifficulty(difficulty);
		setEnchantmentBasedOnDifficulty(difficulty);

		if (getItemStackFromSlot(EntityEquipmentSlot.HEAD) == null)
		{
			Calendar calendar = worldObj.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
				inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		Calendar calendar = worldObj.getCurrentDate();

		attribute.removeModifier(attackDamageBoost);

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
			attribute.applyModifier(attackDamageBoost);

		return par1EntityLivingData;
	}
}
