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

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class StealVigorSpell extends EntityTargetSpell {

	public StealVigorSpell() {
		super("stealvigor", 500F, Items.BEEF);
		setRequiresCharging();
		setColor(0xdb3d3d);
	}

	@Override
	protected boolean canCastSpellOnTarget(EntityLivingBase target, ScrollType scrollType) {

		return true;
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType, EntityLivingBase target) {

		int level = scrollType.getQuality() + 1; // 1, 2, 3, 4
		int duration = 600 + 600 * level; // 1200, 1800, 2400, 3000

		target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, duration, level));
		target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, duration, level));
		target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, duration, level));
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, duration, level));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, duration, level));
		player.addPotionEffect(new PotionEffect(MobEffects.HASTE, duration, level));
	}

}
