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

import com.shinoow.abyssalcraft.api.spell.EntityTargetSpell;

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
	protected boolean canCastSpellOnTarget(EntityLivingBase target) {

		return true;
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, EntityLivingBase target) {

		target.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 1200, 1));
		target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 1200, 1));
		target.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 1200, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 1200, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 1200, 1));
		player.addPotionEffect(new PotionEffect(MobEffects.HASTE, 1200, 1));
	}

}
