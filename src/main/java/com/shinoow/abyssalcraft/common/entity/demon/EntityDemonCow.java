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
package com.shinoow.abyssalcraft.common.entity.demon;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

public class EntityDemonCow extends EntityDemonAnimal {

	public EntityDemonCow(World par1World) {
		super(par1World);
		setSize(0.9F, 1.3F);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
		} else getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.cow.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.ghast.scream";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.cow.hurt";
	}

	@Override
	protected void playStepSound(BlockPos pos, Block p_145780_4_)
	{
		playSound("mob.cow.step", 0.15F, 1.0F);
	}

	@Override
	protected float getSoundVolume()
	{
		return 0.4F;
	}
}
