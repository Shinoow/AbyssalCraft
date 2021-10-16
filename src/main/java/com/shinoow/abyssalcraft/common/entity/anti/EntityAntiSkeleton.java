/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2020 Shinoow.
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

import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.ai.EntityAIAttackRangedBowAnti;
import com.shinoow.abyssalcraft.common.util.ExplosionUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAntiSkeleton extends EntityMob implements IRangedAttackMob, IAntiEntity {

	private static final DataParameter<Boolean> field_184728_b = EntityDataManager.<Boolean>createKey(EntityAntiSkeleton.class, DataSerializers.BOOLEAN);
	private EntityAIAttackRangedBowAnti aiArrowAttack = new EntityAIAttackRangedBowAnti(this, 1.0D, 20, 15.0F);

	public EntityAntiSkeleton(World par1World){
		super(par1World);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIRestrictSun(this));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntitySkeleton.class, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		tasks.addTask(4, aiArrowAttack);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
		if(ACConfig.hardcoreMode) getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(80.0D);
		else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataManager.register(field_184728_b, Boolean.valueOf(false));
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_SKELETON_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_SKELETON_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_SKELETON_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		playSound(SoundEvents.ENTITY_SKELETON_STEP, 0.15F, 1.0F);
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	public void updateRidden()
	{
		super.updateRidden();

		if (getRidingEntity() instanceof EntityCreature)
		{
			EntityCreature entitycreature = (EntityCreature)getRidingEntity();
			renderYawOffset = entitycreature.renderYawOffset;
		}
	}

	@Override
	protected Item getDropItem()
	{
		return Items.ARROW;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_ANTI_SKELETON;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int j;
		int k;

		j = rand.nextInt(3 + par2);

		for (k = 0; k < j; ++k)
			dropItem(Items.ARROW, 1);

		j = rand.nextInt(3 + par2);

		for (k = 0; k < j; ++k)
			dropItem(ACItems.anti_bone, 1);
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!world.isRemote && par1Entity instanceof EntitySkeleton){
			boolean flag = world.getGameRules().getBoolean("mobGriefing");
			if(ACConfig.nuclearAntimatterExplosions)
				ExplosionUtil.newODBExplosion(world, this, posX, posY, posZ, ACConfig.antimatterExplosionSize, true, flag);
			else world.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
	{
		super.setEquipmentBasedOnDifficulty(difficulty);
		setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
	}

	@Override
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);

		setEquipmentBasedOnDifficulty(difficulty);
		setEnchantmentBasedOnDifficulty(difficulty);

		setCanPickUpLoot(rand.nextFloat() < 0.55F * difficulty.getClampedAdditionalDifficulty());

		if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty())
		{
			Calendar calendar = world.getCurrentDate();

			if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && rand.nextFloat() < 0.25F)
			{
				setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack(rand.nextFloat() < 0.1F ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
				inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
			}
		}

		return par1EntityLivingData;
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
	{
		EntityArrow entityarrow = new EntityTippedArrow(world, this);
		double d0 = par1EntityLivingBase.posX - posX;
		double d1 = par1EntityLivingBase.getEntityBoundingBox().minY + par1EntityLivingBase.height / 3.0F - entityarrow.posY;
		double d2 = par1EntityLivingBase.posZ - posZ;
		double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
		entityarrow.shoot(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, 14 - world.getDifficulty().getDifficultyId() * 4);
		int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, this);
		int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, this);
		entityarrow.setDamage(par2 * 2.0F + rand.nextGaussian() * 0.25D + world.getDifficulty().getDifficultyId() * 0.11F);

		if (i > 0)
			entityarrow.setDamage(entityarrow.getDamage() + i * 0.5D + 0.5D);

		if (j > 0)
			entityarrow.setKnockbackStrength(j);

		if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, this) > 0)
			entityarrow.setFire(100);

		playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		world.spawnEntity(entityarrow);
	}

	@Override
	public double getYOffset()
	{
		return -0.35D;
	}

	@SideOnly(Side.CLIENT)
	public boolean getSwingingArms()
	{
		return dataManager.get(field_184728_b).booleanValue();
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
		dataManager.set(field_184728_b, Boolean.valueOf(swingingArms));
	}
}
