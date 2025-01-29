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
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class GraspofCthulhuSpell extends EntityTargetSpell {

	public GraspofCthulhuSpell() {
		super("graspofcthulhu", 20, Items.FISH);
		setScrollType(ScrollType.LESSER);
		setColor(0x2ba2ad);
	}

	@Override
	protected boolean canCastSpellOnTarget(EntityLivingBase target, ScrollType scrollType) {

		return true;
	}

	@Override
	public void castSpellOnTarget(World world, BlockPos pos, EntityPlayer player, ScrollType scrollType, EntityLivingBase target) {
		target.motionX = target.motionY = target.motionZ = 0;
		target.setSprinting(false);
		target.isAirBorne = false;
		target.onGround = true;
		target.setInWeb();
		target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 10, 14, false, false));
		if(target.ticksExisted % 20 == 0 && target.getHealth() > 1)
			target.attackEntityFrom(DamageSource.causePlayerDamage(player).setDamageBypassesArmor().setDamageIsAbsolute(), scrollType.getQuality());
	}
}
