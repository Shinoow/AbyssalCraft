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

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityGhoul extends EntityGhoulBase {

	public EntityGhoul(World par1World) {
		super(par1World);
		if(ACConfig.ghouls_burn)
			tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();

		getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ACConfig.hardcoreMode ? 60.0D : 30.0D);
		getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ACConfig.hardcoreMode ? 10.0D : 5.0D);
	}

	@Override
	public void onLivingUpdate() {
		if(ACConfig.ghouls_burn)
			EntityUtil.burnFromSunlight(this);

		super.onLivingUpdate();
	}

	@Override
	protected Item getDropItem()
	{
		return ACItems.ghoul_flesh;
	}

	@Override
	protected ResourceLocation getLootTable(){
		return ACLoot.ENTITY_GHOUL;
	}

	@Override
	public void onAttacking(Entity entity) {}

	@Override
	public float getBonusDamage() {
		return 1.5f;
	}
}
