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
package com.shinoow.abyssalcraft.common.entity.anti;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;

public class EntityAntiSkeleton extends EntityMob implements IRangedAttackMob, IAntiEntity {

	private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F);

	public EntityAntiSkeleton(World par1World){
		super(par1World);
		tasks.addTask(1, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIRestrictSun(this));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAILookIdle(this));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
		tasks.addTask(4, aiArrowAttack);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25D);
		if(AbyssalCraft.hardcoreMode) getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0D);
		else getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		dataWatcher.addObject(13, new Byte((byte)0));
	}

	@Override
	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.skeleton.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.skeleton.hurt";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.skeleton.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4Block)
	{
		playSound("mob.skeleton.step", 0.15F, 1.0F);
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

		if (ridingEntity instanceof EntityCreature)
		{
			EntityCreature entitycreature = (EntityCreature)ridingEntity;
			renderYawOffset = entitycreature.renderYawOffset;
		}
	}

	@Override
	protected Item getDropItem()
	{
		return Items.arrow;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int j;
		int k;

		j = rand.nextInt(3 + par2);

		for (k = 0; k < j; ++k)
			dropItem(Items.arrow, 1);

		j = rand.nextInt(3 + par2);

		for (k = 0; k < j; ++k)
			dropItem(AbyssalCraft.antiBone, 1);
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntitySkeleton){
			boolean flag = worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

		tasks.addTask(4, aiArrowAttack);
		setCurrentItemOrArmor(0, new ItemStack(Items.bow));
		enchantEquipment();

		setCanPickUpLoot(rand.nextFloat() < 0.55F * worldObj.func_147462_b(posX, posY, posZ));

		return par1EntityLivingData;
	}

	/**
	 * Attack the specified entity using a ranged attack.
	 */
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2)
	{
		EntityArrow entityarrow = new EntityArrow(worldObj, this, par1EntityLivingBase, 1.6F, 14 - worldObj.difficultySetting.getDifficultyId() * 4);
		int i = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, getHeldItem());
		int j = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, getHeldItem());
		entityarrow.setDamage(par2 * 2.0F + rand.nextGaussian() * 0.25D + worldObj.difficultySetting.getDifficultyId() * 0.11F);

		if (i > 0)
			entityarrow.setDamage(entityarrow.getDamage() + i * 0.5D + 0.5D);

		if (j > 0)
			entityarrow.setKnockbackStrength(j);

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, getHeldItem()) > 0)
			entityarrow.setFire(100);

		playSound("random.bow", 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
		worldObj.spawnEntityInWorld(entityarrow);
	}

	@Override
	public double getYOffset()
	{
		return super.getYOffset() - 0.5D;
	}

}
