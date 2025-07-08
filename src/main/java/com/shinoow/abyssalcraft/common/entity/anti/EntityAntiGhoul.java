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
package com.shinoow.abyssalcraft.common.entity.anti;

import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.entity.IAntiEntity;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.common.entity.ghoul.*;
import com.shinoow.abyssalcraft.common.util.ExplosionUtil;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityAntiGhoul extends EntityGhoulBase implements IAntiEntity {

	public EntityAntiGhoul(World par1World) {
		super(par1World);
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiPlayer.class, 8.0F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiGhoul.class, 8.0F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiAbyssalZombie.class, 8.0F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiZombie.class, 8.0F));
		tasks.addTask(6, new EntityAIWatchClosest(this, EntityAntiSkeleton.class, 8.0F));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityDepthsGhoul.class, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityGhoul.class, true));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityDreadedGhoul.class, true));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 90.0D : 45.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 10.0D : 5.0D);
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.anti_ghoul_flesh;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_ANTI_GHOUL;
	}

	@Override
	protected void collideWithEntity(Entity par1Entity)
	{
		if(!world.isRemote && par1Entity instanceof EntityGhoulBase && !EntityUtil.isEntityAnti((EntityLivingBase) par1Entity)){
			boolean flag = world.getGameRules().getBoolean("mobGriefing");
			if(ACConfig.nuclearAntimatterExplosions)
				ExplosionUtil.newODBExplosion(world, this, posX, posY, posZ, ACConfig.antimatterExplosionSize, true, flag);
			else world.createExplosion(this, posX, posY, posZ, 5, flag);
			setDead();
		}
		else par1Entity.applyEntityCollision(this);
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {

		super.onDeath(par1DamageSource);

		if(par1DamageSource == AbyssalCraftAPI.coralium || par1DamageSource == AbyssalCraftAPI.dread){
			EntityOmotholGhoul entity = new EntityOmotholGhoul(world);
			entity.copyLocationAndAnglesFrom(this);
			world.removeEntity(this);
			entity.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
			world.spawnEntity(entity);
		}
	}

	@Override
	public void onAttacking(Entity entity) {}

	@Override
	public float getBonusDamage() {
		return 1.5F;
	}
}
