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
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;
import com.shinoow.abyssalcraft.lib.ACLoot;

public class EntityDemonPig extends EntityDemonAnimal {

	public EntityDemonPig(World par1World)
	{
		super(par1World);
		setSize(0.9F, 0.9F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if(AbyssalCraft.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		} else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.entity_pig_ambient;
	}

	@Override
	protected SoundEvent getHurtSound()
	{
		return SoundEvents.entity_ghast_hurt;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.entity_pig_death;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.entity_pig_step, 0.15F, 1.0F);
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_DEMON_PIG;
	}
}