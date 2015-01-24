/**
 * AbyssalCraft
 * Copyright 2012-2015 Shinoow
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shinoow.abyssalcraft.common.entity.anti;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.api.entity.IAntiEntity;

public class EntityAntiPlayer extends EntityMob implements IAntiEntity {

	public EntityAntiPlayer(World par1World) {
		super(par1World);
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.35D, true));
		tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 0.35D));
		tasks.addTask(4, new EntityAIWander(this, 0.35D));
		tasks.addTask(5, new EntityAILookIdle(this));
		tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 0, true));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0D);
	}

	@Override
	protected boolean isAIEnabled()
	{
		return true;
	}

	@Override
	public boolean canDespawn(){
		return false;
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
	{
		worldObj.playSoundAtEntity(this, "mob.zombie.step", 0.15F, 1.0F);
	}

	@Override
	public void onKillEntity(EntityLivingBase par1EntityLivingBase)
	{
		super.onKillEntity(par1EntityLivingBase);

		if (par1EntityLivingBase instanceof EntityPlayer)
		{
			if (rand.nextBoolean())
				return;

			EntityAntiPlayer antiPlayer = new EntityAntiPlayer(worldObj);
			antiPlayer.copyLocationAndAnglesFrom(par1EntityLivingBase);
			worldObj.removeEntity(par1EntityLivingBase);
			antiPlayer.onSpawnWithEgg((IEntityLivingData)null);

			worldObj.spawnEntityInWorld(antiPlayer);
			worldObj.playAuxSFXAtEntity((EntityPlayer)null, 1016, (int)posX, (int)posY, (int)posZ, 0);

		}
	}

	@Override
	public IEntityLivingData onSpawnWithEgg(IEntityLivingData par1EntityLivingData)
	{
		par1EntityLivingData = super.onSpawnWithEgg(par1EntityLivingData);

		setCanPickUpLoot(true);

		addRandomArmor();
		enchantEquipment();

		return par1EntityLivingData;
	}
}