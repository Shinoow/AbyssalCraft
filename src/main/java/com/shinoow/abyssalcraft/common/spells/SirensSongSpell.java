/*******************************************************************************
 * AbyssalCraft
 * Copyright (c) 2012 - 2024 Shinoow.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Contributors:
 *     Shinoow -  implementation
 ******************************************************************************/
package com.shinoow.abyssalcraft.common.spells;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.api.spell.EntityTargetSpell;
import com.shinoow.abyssalcraft.api.spell.SpellUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class SirensSongSpell extends EntityTargetSpell {

	public SirensSongSpell() {
		super("sirenssong", 1000F, Items.WHEAT);
		setParchment(new ItemStack(ACItems.lesser_scroll));
		setRequiresCharging();
		setColor(0x1c8edb);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {
		if(world.isRemote){
			RayTraceResult r = SpellUtils.rayTraceTarget(getRange());
			if(r != null)
				if(r.entityHit instanceof EntityTameable && !((EntityTameable)r.entityHit).isOwner(player) ||
						r.entityHit instanceof AbstractHorse && !player.getUniqueID().equals(((AbstractHorse)r.entityHit).getOwnerUniqueId()))
					return true;
		}
		return false;
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {
		RayTraceResult r = SpellUtils.rayTraceTarget(getRange());
		if(r != null)
			if(r.entityHit instanceof EntityTameable && !((EntityTameable)r.entityHit).isOwner(player) ||
					r.entityHit instanceof AbstractHorse && !player.getUniqueID().equals(((AbstractHorse)r.entityHit).getOwnerUniqueId()))
				SpellUtils.processEntitySpell(r.entityHit.getEntityId(), getID());
	}

	@Override
	protected boolean canCastSpellOnTarget(EntityLivingBase target) {

		return false;
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, EntityLivingBase target) {
		if(target instanceof EntityTameable && !((EntityTameable)target).isOwner(player)) {
			((EntityTameable)target).setTamedBy(player);
			target.setHealth(target.getMaxHealth());
		} else if(target instanceof AbstractHorse && !player.getUniqueID().equals(((AbstractHorse)target).getOwnerUniqueId())) {
			((AbstractHorse)target).setTamedBy(player);
			target.setHealth(target.getMaxHealth());
		}
	}

}
