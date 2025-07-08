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
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IDreadEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.EntityDreadSpawn;
import com.shinoow.abyssalcraft.common.entity.EntityDreadguard;
import com.shinoow.abyssalcraft.common.entity.EntityDreadling;
import com.shinoow.abyssalcraft.common.entity.demon.EntityDemonAnimal;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityDreadedGhoul extends EntityGhoulBase implements IDreadEntity {

	public EntityDreadedGhoul(World par1World) {
		super(par1World);
		tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadling.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadSpawn.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDemonAnimal.class, 8.0F));
		tasks.addTask(7, new EntityAIWatchClosest(this, EntityDreadguard.class, 8.0F));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityGhoul.class, true));
		targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityDepthsGhoul.class, true));
		isImmuneToFire = true;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 70.0D : 35.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 12.0D : 6.0D);
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.dreaded_ghoul_flesh;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_DREADED_GHOUL;
	}

	@Override
	public void onAttacking(Entity entity) {
		if(entity instanceof EntityLivingBase && !EntityUtil.isEntityDread((EntityLivingBase) entity))
			((EntityLivingBase)entity).addPotionEffect(new PotionEffect(AbyssalCraftAPI.dread_plague, 100));
	}

	@Override
	public float getBonusDamage() {
		return 1.5f;
	}
}
