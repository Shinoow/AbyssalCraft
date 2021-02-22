/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2021 Shinoow.
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

import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityGatekeeperMinion extends EntityMob implements IOmotholEntity {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);

	public EntityGatekeeperMinion(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.35D, false));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityGatekeeperMinion.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityRemnant.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		setSize(0.8F, 2.7F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.2D);
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 200.0D : 100.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 36.0D : 18.0D);
	}

	@Override
	public boolean canBreatheUnderwater() {
		return true;
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		swingArm(EnumHand.MAIN_HAND);
		swingArm(EnumHand.OFF_HAND);
		boolean flag = super.attackEntityAsMob(par1Entity);

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 3 * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	protected boolean canDespawn()
	{
		return false;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return ACSounds.shadow_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
	}

	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, float par2)
	{
		EntityLivingBase enemy = null;
		if(par1DamageSource.getTrueSource() != null && par1DamageSource.getTrueSource() instanceof EntityLivingBase)
			enemy = (EntityLivingBase) par1DamageSource.getTrueSource();
		if(rand.nextInt(10) == 0){
			if(enemy != null)
				for(EntityRemnant rem : world.getEntitiesWithinAABB(EntityRemnant.class, getEntityBoundingBox().grow(16D, 16D, 16D)))
					rem.enrage(false, enemy);
			playSound(ACSounds.remnant_scream, 3F, 1F);
		}
		return super.attackEntityFrom(par1DamageSource, par2);
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_MINION_OF_THE_GATEKEEPER;
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

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

		attribute.removeModifier(attackDamageBoost);

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
			attribute.applyModifier(attackDamageBoost);

		return par1EntityLivingData;
	}

	@Override
	public float getEyeHeight()
	{
		return height * 0.925F;
	}
}
