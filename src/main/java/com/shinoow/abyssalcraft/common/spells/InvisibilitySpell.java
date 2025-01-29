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
package com.shinoow.abyssalcraft.common.spells;

import com.shinoow.abyssalcraft.api.spell.EntityTargetSpell;
import com.shinoow.abyssalcraft.api.spell.SpellEnum.ScrollType;
import com.shinoow.abyssalcraft.api.spell.SpellUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class InvisibilitySpell extends EntityTargetSpell {

	public InvisibilitySpell() {
		super("invisibility", 500F, Items.GLASS_BOTTLE);
		setRequiresCharging();
		setColor(0xaeb1b7);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType) {
		if(super.canCastSpell(world, pos, player, scrollType))
			return true;
		return canCastSpellOnTarget(player, scrollType);
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType) {
		RayTraceResult r = SpellUtils.rayTraceTarget(getRange());
		if(r != null && SpellUtils.canPlayerHurt(player, r.entityHit)
				&& canCastSpellOnTarget((EntityLivingBase) r.entityHit, scrollType))
			SpellUtils.processEntitySpell(r.entityHit.getEntityId(), getID(), scrollType);
		else if(canCastSpellOnTarget(player, scrollType))
			SpellUtils.processEntitySpell(player.getEntityId(), getID(), scrollType);
	}

	@Override
	protected boolean canCastSpellOnTarget(EntityLivingBase target, ScrollType scrollType) {

		return !target.isPotionActive(MobEffects.INVISIBILITY);
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType, EntityLivingBase target) {
		if(canCastSpellOnTarget(target, scrollType))
			target.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 6000, 0, false, false));
		else if(canCastSpellOnTarget(player, scrollType))
			player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 6000, 0, false, false));
	}

}
