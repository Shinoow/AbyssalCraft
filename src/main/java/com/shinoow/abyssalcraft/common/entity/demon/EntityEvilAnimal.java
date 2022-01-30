/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2022 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 * 
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.entity.demon;

import java.util.ArrayList;
import java.util.List;

import com.shinoow.abyssalcraft.common.entity.EntityShoggothBase;
import com.shinoow.abyssalcraft.lib.ACConfig;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

public abstract class EntityEvilAnimal extends EntityMob implements IShearable {

	public EntityEvilAnimal(World worldIn) {
		super(worldIn);
		isImmuneToFire = true;
		tasks.addTask(0, new EntityAISwimming(this));
		tasks.addTask(2, new EntityAIAttackMelee(this, 0.35D, true));
		targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
		targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
	}

	@Override
	public boolean attackEntityAsMob(Entity par1Entity)
	{
		if(ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
			par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(ACConfig.damageAmpl > 1.0D ? ACConfig.damageAmpl : 1));

		return super.attackEntityAsMob(par1Entity);
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_GHAST_HURT;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource)
	{
		super.onDeath(par1DamageSource);

		if(!world.isRemote)
			if(!(par1DamageSource.getTrueSource() instanceof EntityShoggothBase) && ACConfig.demonAnimalsSpawnOnDeath)
			{
				EntityDemonAnimal demon = getDemonAnimal();
				demon.copyLocationAndAnglesFrom(this);
				world.removeEntity(this);
				demon.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
				world.spawnEntity(demon);
			}
	}

	@Override public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos){ return true; }
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess w, BlockPos pos, int fortune)
	{
		int i = 1 + rand.nextInt(3);

		List<ItemStack> ret = new ArrayList<>();
		for (int j = 0; j < i; ++j)
			ret.add(getShearingDrop());

		playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
		playSound(SoundEvents.ENTITY_GHAST_HURT, 1.0F, 0.2F);
		if(!world.isRemote){
			EntityDemonAnimal demon = getDemonAnimal();
			demon.copyLocationAndAnglesFrom(this);
			world.removeEntity(this);
			demon.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(posX, posY, posZ)), (IEntityLivingData)null);
			world.spawnEntity(demon);
		}
		return ret;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		if(ACConfig.evilAnimalNewMoonSpawning) {
			if(!world.isDaytime())
				if(world.getCurrentMoonPhaseFactor() == 0)
					return super.getCanSpawnHere();

			return false;
		}
		return super.getCanSpawnHere();
	}

	public abstract EntityDemonAnimal getDemonAnimal();

	public abstract ItemStack getShearingDrop();
}
