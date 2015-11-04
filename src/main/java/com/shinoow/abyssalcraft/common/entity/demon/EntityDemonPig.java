/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2015 Shinoow.
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
import net.minecraft.world.World;

import com.shinoow.abyssalcraft.AbyssalCraft;

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
			getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0D);
			getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0D);
		} else getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(15.0D);
	}

	@Override
	protected String getLivingSound()
	{
		return "mob.pig.say";
	}

	@Override
	protected String getHurtSound()
	{
		return "mob.ghast.scream";
	}

	@Override
	protected String getDeathSound()
	{
		return "mob.pig.death";
	}

	@Override
	protected void func_145780_a(int par1, int par2, int par3, Block par4)
	{
		playSound("mob.pig.step", 0.15F, 1.0F);
	}
}