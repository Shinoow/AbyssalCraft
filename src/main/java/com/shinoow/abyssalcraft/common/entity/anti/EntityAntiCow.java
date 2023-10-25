/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2023 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.anti;

import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.util.ExplosionUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAntiCow extends EntityAnimal implements IAntiEntity {

	public EntityAntiCow(World par1World)
	{
		super(par1World);
		setSize(0.9F, 1.3F);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(1, new EntityAIPanic(this, 2.0D));
		tasks.addTask(2, new EntityAIMate(this, 1.0D));
		tasks.addTask(3, new EntityAITempt(this, 1.25D, Items.WHEAT, false));
		tasks.addTask(4, new EntityAIFollowParent(this, 1.25D));
		tasks.addTask(5, new EntityAIWander(this, 1.0D));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
		tasks.addTask(7, new EntityAILookIdle(this));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_COW_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_COW_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_COW_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4Block)
	{
		playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
	}

	@Override
	protected Item getDropItem()
	{
		return Items.LEATHER;
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
	{
		int j = rand.nextInt(3) + rand.nextInt(1 + par2);
		int k;

		for (k = 0; k < j; ++k)
			dropItem(Items.LEATHER, 1);

		j = rand.nextInt(3) + 1 + rand.nextInt(1 + par2);

		for (k = 0; k < j; ++k)
			if (isBurning())
				dropItem(ACItems.anti_beef, 1);
			else
				dropItem(ACItems.anti_beef, 1);
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_ANTI_COW;
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!world.isRemote && par1Entity instanceof EntityCow){
			boolean flag = world.getGameRules().getBoolean("mobGriefing");
			if(ACConfig.nuclearAntimatterExplosions)
				ExplosionUtil.newODBExplosion(world, this, posX, posY, posZ, ACConfig.antimatterExplosionSize, true, flag);
			else world.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public boolean processInteract(EntityPlayer par1EntityPlayer, EnumHand hand)
	{
		ItemStack stack = par1EntityPlayer.getHeldItem(hand);
		if (!stack.isEmpty() && stack.getItem() == Items.BUCKET && !par1EntityPlayer.capabilities.isCreativeMode && !isChild())
		{
			par1EntityPlayer.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
			stack.shrink(1);
			if (stack.isEmpty())
				par1EntityPlayer.setHeldItem(hand, ACItems.liquid_antimatter_bucket_stack.copy());
			else if (!par1EntityPlayer.inventory.addItemStackToInventory(ACItems.liquid_antimatter_bucket_stack.copy()))
				par1EntityPlayer.dropItem(ACItems.liquid_antimatter_bucket_stack.copy(), false);

			return true;
		} else
			return super.processInteract(par1EntityPlayer, hand);
	}

	@Override
	public EntityAntiCow createChild(EntityAgeable par1EntityAgeable)
	{
		return new EntityAntiCow(world);
	}
}
