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

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.IOmotholEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.EntityAbyssalZombie;
import com.shinoow.abyssalcraft.common.entity.EntitySkeletonGoliath;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.util.ParticleUtil;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityShadowGhoul extends EntityGhoulBase implements IOmotholEntity {

	public EntityShadowGhoul(World par1World) {
		super(par1World);
		setSize(1.0F, 1.7F);
		tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityShadowGhoul.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityAbyssalZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityZombie.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeleton.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntitySkeletonGoliath.class, 8.0F));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 60.0D : 30.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 10.0D : 5.0D);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return null;
	}
	@Override
	protected Item getDropItem()
	{
		return ACItems.shadow_shard; //TODO: change
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_SHADOW_MONSTER; //TODO: change
	}

	@Override
	public EnumCreatureAttribute getCreatureAttribute()
	{
		return AbyssalCraftAPI.SHADOW;
	}

	@Override
	public void onLivingUpdate()
	{
		ParticleUtil.spawnShadowParticles(this);

		super.onLivingUpdate();
	}

	@Override
	public void onAttacking(Entity entity) {}

	@Override
	public float getBonusDamage() {
		return 1.5f;
	}
}
