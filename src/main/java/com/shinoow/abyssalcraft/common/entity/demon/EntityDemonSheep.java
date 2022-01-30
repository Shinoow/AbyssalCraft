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

import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.block.Block;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityDemonSheep extends EntityDemonAnimal {

	public EntityDemonSheep(World par1World) {
		super(par1World);
		setSize(0.9F, 1.3F);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();

		if(ACConfig.hardcoreMode){
			getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(24.0D);
			getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
		} else getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12.0D);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return SoundEvents.ENTITY_SHEEP_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source)
	{
		return SoundEvents.ENTITY_GHAST_HURT;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return SoundEvents.ENTITY_SHEEP_DEATH;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block par4)
	{
		playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_DEMON_SHEEP;
	}
}
