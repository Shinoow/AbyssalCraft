/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2025 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.ghoul;

import java.util.Calendar;
import java.util.UUID;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.init.InitHandler;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACSounds;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityGhoulBase extends EntityMob {

	private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
	private static AttributeModifier ghoulHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 3.0D, 0);

	public int fadeInTimer;
	public boolean doFadeIn;

	public EntityGhoulBase(World worldIn) {
		super(worldIn);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, 1.0D, false));
		tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(7, new EntityAILookIdle(this));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		//TODO: ghoul AI stuff
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity) {

		swingArm(EnumHand.MAIN_HAND);
		swingArm(EnumHand.OFF_HAND);
		boolean flag = super.attackEntityAsMob(par1Entity);

		if(flag)
			onAttacking(par1Entity);

		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), getBonusDamage() * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return flag;
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return ACSounds.ghoul_normal_ambient;
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
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected void updateEquipmentIfNeeded(EntityItem itemEntity)
	{
		if(!InitHandler.INSTANCE.isBlacklistedFromPickup(itemEntity.getItem()))
			super.updateEquipmentIfNeeded(itemEntity);
	}

	@Override
	public void onLivingUpdate()
	{
		//		if(ticksExisted == 0)
		//			fadeInTimer = 20;
		//		if(world.isRemote && fadeInTimer > 0) {
		//			fadeInTimer--;
		//		}

		super.onLivingUpdate();
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

		float f = difficulty.getClampedAdditionalDifficulty();
		setCanPickUpLoot(ACConfig.hardcoreMode ? true : rand.nextFloat() < 0.55F * f);

		if(ACConfig.hardcoreMode)
			EntityUtil.suitUp(this, false);
		else {
			setEquipmentBasedOnDifficulty(difficulty);
			setEnchantmentBasedOnDifficulty(difficulty);
		}

		EntityUtil.hahaPumpkinGoesBrrr(this, rand);

		IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
		Calendar calendar = world.getCurrentDate();

		setModifier();

		attribute.removeModifier(ghoulHDamageBoost);

		if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
			attribute.applyModifier(ghoulHDamageBoost);

		return par1EntityLivingData;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleStatusUpdate(byte id)
	{
		if (id == 23) {
			doFadeIn = true;
		}
		else
			super.handleStatusUpdate(id);
	}

	/**
	 * Do extra stuff on attack
	 * @param entity Target
	 */
	public abstract void onAttacking(Entity entity);

	/**
	 * Hardcore mode bonus damage
	 */
	public abstract float getBonusDamage();

	/**
	 * Halloween damage boost
	 */
	public float getDamageBoost() {
		return 3F;
	}

	private void setModifier() {
		ghoulHDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", getDamageBoost(), 0);
	}
}
