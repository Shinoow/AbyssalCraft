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

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFollowParent;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMate;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITempt;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityAntiChicken extends EntityAnimal implements IAntiEntity {

	public float field_70886_e;
	public float destPos;
	public float field_70884_g;
	public float field_70888_h;
	public float field_70889_i = 1.0F;
	/** The time until the next egg is spawned. */
	public int timeUntilNextEgg;

	public EntityAntiChicken(World par1World)
	{
		super(par1World);
		setSize(0.3F, 0.7F);
		timeUntilNextEgg = rand.nextInt(6000) + 6000;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 1.4D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.0D, Items.WHEAT_SEEDS, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.1D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		field_70888_h = field_70886_e;
		field_70884_g = destPos;
		destPos = (float)(destPos + (onGround ? -1 : 4) * 0.3D);

		if (destPos < 0.0F)
			destPos = 0.0F;

		if (destPos > 1.0F)
			destPos = 1.0F;

		if (!onGround && field_70889_i < 1.0F)
			field_70889_i = 1.0F;

		field_70889_i = (float)(field_70889_i * 0.9D);

		if (!onGround && motionY < 0.0D)
			motionY *= 0.6D;

		field_70886_e += field_70889_i * 2.0F;

		if (!isChild() && !worldObj.isRemote && --timeUntilNextEgg <= 0)
		{
			playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (rand.nextFloat() - rand.nextFloat()) * 0.2F + 1.0F);
			dropItem(Items.EGG, 1);
			timeUntilNextEgg = rand.nextInt(6000) + 6000;
		}
	}

	@Override
	public void fall(float par1, float par2) {}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_CHICKEN_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.ENTITY_CHICKEN_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_CHICKEN_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block p_145780_4_)
	{
		playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15F, 1.0F);
	}

	@Override
	protected Item getDropItem()
	{
		return Items.FEATHER;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_ANTI_CHICKEN;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int j = rand.nextInt(3) + rand.nextInt(1 + par2);

		for (int k = 0; k < j; ++k)
			dropItem(Items.FEATHER, 1);

		if (isBurning())
			dropItem(ACItems.anti_chicken, 1);
		else
			dropItem(ACItems.anti_chicken, 1);
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!worldObj.isRemote && par1Entity instanceof EntityChicken){
			boolean flag = worldObj.getGameRules().getBoolean("mobGriefing");
			worldObj.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public EntityAntiChicken createChild(EntityAgeable par1EntityAgeable)
	{
		return new EntityAntiChicken(worldObj);
	}

	@Override
	public boolean isBreedingItem(ItemStack par1ItemStack)
	{
		return par1ItemStack != null && par1ItemStack.getItem() instanceof ItemSeeds;
	}
}
