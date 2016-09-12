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
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import com.shinoow.abyssalcraft.common.entity.EntityOmotholGhoul;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityAntiGhoul extends EntityMob implements IAntiEntity {

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
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(42.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
		getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(10.0D);
		} else {
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
		}
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return AbyssalCraft.ghoul_normal_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return AbyssalCraft.ghoul_hurt;
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected SoundEvent getDeathSound()
	{
		return AbyssalCraft.ghoul_death;
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

	//	@Override
	//	protected void addRandomDrop() //TODO: loot pool
	//	{
	//		switch (rand.nextInt(3))
	//		{
	//		case 0:
	//			dropItem(Items.bone, 1);
	//			break;
	//		case 1:
	//			dropItem(Items.writable_book, 1);
	//			break;
	//		case 2:
	//			dropItem(Item.getItemFromBlock(AbyssalCraft.DGhead), 1);
	//			break;
	//		}
	//	}

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
		if(!AbyssalCraft.isItemBlacklisted(this, itemEntity.getEntityItem()))
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

		return par1EntityLivingData;
	}
}
