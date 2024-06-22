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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class InvisibilitySpell extends EntityTargetSpell {

	public InvisibilitySpell() {
		super("invisibility", 500F, Items.GLASS_BOTTLE);
		setParchment(new ItemStack(ACItems.basic_scroll));
		setRequiresCharging();
		setColor(0xaeb1b7);
	}

	@Override
	public boolean canCastSpell(World world, BlockPos pos, EntityPlayer player) {
		if(super.canCastSpell(world, pos, player))
			return true;
		return canCastSpellOnTarget(player);
	}

	@Override
	protected void castSpellClient(World world, BlockPos pos, EntityPlayer player) {
		RayTraceResult r = SpellUtils.rayTraceTarget(getRange());
		if(r != null && SpellUtils.canPlayerHurt(player, r.entityHit)
				&& canCastSpellOnTarget((EntityLivingBase) r.entityHit))
			SpellUtils.processEntitySpell(r.entityHit.getEntityId(), getID());
		else if(canCastSpellOnTarget(player))
			SpellUtils.processEntitySpell(player.getEntityId(), getID());
	}

	@Override
	protected boolean canCastSpellOnTarget(EntityLivingBase target) {

		return !target.isPotionActive(MobEffects.INVISIBILITY);
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, EntityLivingBase target) {
		if(canCastSpellOnTarget(target))
			target.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 6000, 0, false, false));
		else if(canCastSpellOnTarget(player))
			player.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 6000, 0, false, false));
	}

}
